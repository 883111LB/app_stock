package com.cvicse.stock.chart.presenter;

import android.util.Log;

import com.cvicse.base.mvp.BasePresenter;
import com.cvicse.base.utils.NumberUtils;
import com.cvicse.stock.chart.data.MinuteEntity;
import com.cvicse.stock.chart.data.OHLCItemBo;
import com.cvicse.stock.chart.presenter.contract.StockDayContract;
import com.cvicse.stock.chart.utils.TimezoneUtils;
import com.cvicse.stock.http.loop.ChartRunnable;
import com.cvicse.stock.http.loop.ChartSubRunnable;
import com.cvicse.stock.http.loop.DetailRunnable;
import com.cvicse.stock.http.loop.OrderQuantityRunnable;
import com.cvicse.stock.http.loop.RequestManager;
import com.cvicse.stock.http.loop.RunTaskState;
import com.cvicse.stock.markets.data.TickItemBo;
import com.cvicse.stock.util.FormatUtils;
import com.cvicse.stock.utils.StockType;
import com.mitake.core.AppInfo;
import com.mitake.core.CacheChartFiveDay;
import com.mitake.core.CacheChartOneDay;
import com.mitake.core.OHLCItem;
import com.mitake.core.QuoteItem;
import com.mitake.core.bean.TickItem;
import com.mitake.core.network.Network;
import com.mitake.core.request.ChartType;
import com.mitake.core.response.ChartResponse;
import com.mitake.core.response.ChartSubResponse;
import com.mitake.core.response.OrderQuantityResponse;
import com.mitake.core.response.TickResponse;
import com.mitake.core.util.MarketSiteType;
import com.mitake.core.util.Permissions;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 分时图的presenter
 * Created by liu_zlu on 2017/2/23 21:21
 */
public class StockDayPresenter extends BasePresenter implements StockDayContract.Presenter{
    private StockDayContract.View view;
    private QuoteItem quoteItem;
    private String chartType;

    //循环请求标示
    private RunTaskState requestSign;
    private RunTaskState requestOrderSign;
    private RunTaskState requestChartSubSign;
    private RunTaskState requestTickSign;  // 分时明细
    private ArrayList<TickItemBo> mTickItemBos;
    private OHLCItem lohlcItem;
    private int indexType;

    @Override
    public void setView(StockDayContract.View view) {
        this.view = view;
    }

    /**
     * 初始化数据
     *
     * @param quoteItem
     */
    @Override
    public void init(QuoteItem quoteItem,String chartType,int indexType) {
        this.quoteItem = quoteItem;
        this.chartType = chartType;
        this.indexType = indexType;
    }

    @Override
    public void onResume() {
        super.onResume();

        requestChart();//请求走势数据
        requestChartSub(indexType);// 请求走势副图
        queryOrderQuantity(); //查询买卖队列
        requestTick(quoteItem.id);
    }

    @Override
    public void onPause() {
        super.onPause();
        TimezoneUtils.removeData(quoteItem.id);
        RequestManager.cancel(requestSign);
        RequestManager.cancel(requestOrderSign);
        RequestManager.cancel(requestChartSubSign);
        RequestManager.cancel(requestTickSign);
    }

    /**
     * 请求走势数据
     */
    @Override
    public void requestChart() {
        //清除缓存
//         CacheChartOneDay.getInstance().removeCache(quoteItem.id);
        RequestManager.cancel(requestSign);
        requestSign = RequestManager.request(new ChartRunnable(quoteItem.id, chartType) {
            @Override
            public void onBack(ChartResponse response) {
                CopyOnWriteArrayList<OHLCItem> ohlcItems = response.historyItems;
           /* if(!checkNew(ohlcItems)){  // 2018.3.22
                return;
            }*/
                CacheChartOneDay.getInstance().removeCache(quoteItem.id);
                CacheChartFiveDay.getInstance().removeCache(quoteItem.id);

                // 处理时间
                TimezoneUtils.setTimezoneEntity(response,quoteItem);
                if (response.historyItems==null){
                    view.onRequestChartSuccess(null);
                }else {
                    view.onRequestChartSuccess(response);
                }
//                view.onRequestChartSuccess(convert(ohlcItems));  // old 2018-8-8
            }

            @Override
            public void onError(int i, String error) {
            }
        });

    }

    /**
     * 请求走势副图( 只有沪深市场有走势副图)
     * @param indexType
     */
    @Override
    public void requestChartSub(final int indexType) {
        RequestManager.cancel(requestChartSubSign);
        if( 0 == indexType || indexType == 9){   // 0：走势指标为成交量，9：量比使用走势接口，不用走势副图接口
//            requestChart();
            return;
        }

        if(null == chartType || ChartType.FIVE_DAY == chartType || !(quoteItem.id.contains("sh") || quoteItem.id.contains("sz"))){
            return;
        }
        this.indexType = indexType;
//        RequestManager.cancel(requestChartSubSign);
        requestChartSubSign = RequestManager.request(new ChartSubRunnable(quoteItem,chartType,indexType) {
            @Override
            public void onBack(ChartSubResponse response) {
                String[][] line = response.line;
                if(null == response || null == line || line.length <= 0 ){
                    return;
                }

                view.onRequestChartSubSuccess(convertMSub(line));
            }

            @Override
            public void onError(int i, String error) {
                view.onRequestChartSubFail(i,error);
            }
        });
    }

    /**
     * 请求分时明细接口
     * 作用于中金所市场
     * @param code
     */
    @Override
    public void requestTick(String code) {
        if( !code.contains("cff") && !code.contains("gi") && !code.contains("fe")  && !code.contains("dce") && !code.contains("zce")&&!code.contains("shfe")&&!code.contains("ine")){
            return;
        }
        String page = "0,10,-1";
        if(Permissions.LEVEL_2.equals(AppInfo.getInfoLevel())){
            page = "0,20,-1";
        }

        mTickItemBos = new ArrayList<>();
        RequestManager.cancel(requestTickSign);
        requestTickSign =  RequestManager.request(new DetailRunnable(quoteItem.id,page,
                quoteItem.market, quoteItem.subtype) {
            @Override
            public void onBack(TickResponse response) {
                List<TickItem> tickItems = response.tickItems;
                if( null == response ||  null == tickItems || tickItems.isEmpty() ){
                    return;
                }
                mTickItemBos.clear();
                TickItemBo tickItemBo;
                for(TickItem tickItem:tickItems){
                    if (!FormatUtils.isStandard(tickItem.getTransactionTime())) {
                        continue;
                    }
                    tickItemBo = new TickItemBo();
                    tickItemBo.tickFlag = tickItem.getTransactionStatus();
                    String time = tickItem.getTransactionTime();
                    String tempTime = time.substring(0,2)+":"+time.substring(2,4);
                    tickItemBo.time = tempTime;
                    tickItemBo.volume =tickItem.getSingleVolume();
                    tickItemBo.strPrice = tickItem.getTransactionPrice();
                    tickItemBo.price = NumberUtils.parseDouble(tickItem.getTransactionPrice());
                    mTickItemBos.add(tickItemBo);
                }
                view.onTickItemsSuccess(mTickItemBos);
            }

            @Override
            public void onError(int i, String error) {

            }
        });
    }

    /**
     * 判断是否为最新的
     * @return
     * @param ohlcItems
     */
    private boolean checkNew(CopyOnWriteArrayList<OHLCItem> ohlcItems){
        OHLCItem temp =  null!= ohlcItems && ohlcItems.size() > 0 ? ohlcItems.get(ohlcItems.size()-1): null;
        if( null!=lohlcItem  && null!=temp ){
            if( null!= temp.datetime &&  null!=lohlcItem.datetime ){
                if(temp.datetime.equals(lohlcItem.datetime)){
                    return false;
                }
            }
        }
        lohlcItem = temp;
        return true;
    }

    private ArrayList<OHLCItemBo> convert(CopyOnWriteArrayList<OHLCItem> ohlcItems){
        if( null == ohlcItems){
            return null;
        }
        ArrayList<String> keyList = new ArrayList<>();
        ArrayList<OHLCItemBo> ohlcItemBoArrayList = new ArrayList<>();
        OHLCItem ohlcItem = null;
        int length = ohlcItems.size();
        for(int i = 0;i < length;i++){
            ohlcItem = ohlcItems.get(i);
            if(keyList.contains(ohlcItem.datetime)){
                continue;
            }
            keyList.add(ohlcItem.datetime);
            ohlcItemBoArrayList.add(new OHLCItemBo(ohlcItem));
        }
        keyList.clear();
        return ohlcItemBoArrayList;
    }

    /**
     * 走势副图数据转换
     * @return
     */
    private ArrayList<MinuteEntity> convertMSub(String[][] line){
        if( null == line || line.length <= 0 ){
            return null;
        }
        ArrayList<String> keyList = new ArrayList<>();
        ArrayList<MinuteEntity>  mSubEntities = new ArrayList<>();
        for (int i = 0; i < line.length; i++) {
            MinuteEntity minuteEntity = new MinuteEntity();
            String time = line[i][0];
            if(keyList.contains(time)){
                continue;
            }
            minuteEntity.datetime = time.length() == 9 ? "0"+time : time;
            switch (this.indexType){
                case 1: minuteEntity.ddx = Float.parseFloat(line[i][1]); break;
                case 2: minuteEntity.ddy = Float.parseFloat(line[i][1]); break;
                case 3: minuteEntity.ddz = Float.parseFloat(line[i][1]); break;
                case 4: minuteEntity.bbd = Float.parseFloat(line[i][1]); break;
                case 5: minuteEntity.ratioBs = Float.parseFloat(line[i][1]); break;
                case 6:
                    minuteEntity.largeMoneyInflow = Float.parseFloat(line[i][1]);
                    if (line[i].length < 4) {
                        break;
                    }
                    minuteEntity.bigMoneyInflow = Float.parseFloat(line[i][2]);
                    minuteEntity.midMoneyInflow = Float.parseFloat(line[i][3]);
                    minuteEntity.smallMoneyInflow = Float.parseFloat(line[i][4]);
                    break;
                case 7:
                    minuteEntity.largeTradeNum = Float.parseFloat(line[i][1]);
                    minuteEntity.bigTradeNum = Float.parseFloat(line[i][2]);
                    minuteEntity.midTradeNum = Float.parseFloat(line[i][3]);
                    minuteEntity.smallTradeNum = Float.parseFloat(line[i][4]);
                    break;
                case 8:
//                    minuteEntity.bigNetVolume= Float.parseFloat(String.valueOf(line[i][1]));
                    try {
                        minuteEntity.bigNetVolume= Float.parseFloat(line[i][1]);
                    }catch (NumberFormatException e){
                        minuteEntity.bigNetVolume=(float)0;
                    }
//                    minuteEntity.bigNetVolume= String.valueOf(line[i][1]);
                    break;
//                case 9:// 量比
//                    try {
//                        minuteEntity.volRatio= Float.parseFloat(line[i][1]);
//                    }catch (NumberFormatException e){
//                        minuteEntity.volRatio=(float)0;
//                    }
////                    minuteEntity.bigNetVolume= String.valueOf(line[i][1]);
//                    break;
            }
            keyList.add(time);
            mSubEntities.add(minuteEntity);
        }
        keyList.clear();
        return mSubEntities;
    }

    /**
     * 查询买卖队列
     */
    private void queryOrderQuantity() {
        if(!Permissions.LEVEL_2.equals(AppInfo.getInfoLevel()) || quoteItem.id.contains("hk") || !StockType.getType(quoteItem).isIndex()) {
            return;
        }
        RequestManager.cancel(requestOrderSign);
        requestOrderSign = RequestManager.request(new OrderQuantityRunnable(quoteItem.id,quoteItem.market,quoteItem.subtype) {

            @Override
            public void onBack(OrderQuantityResponse response) {
                view.onOrderQuantitySuccess(response);
            }

            @Override
            public void onError(int i, String error) {

            }
        });
    }
}
