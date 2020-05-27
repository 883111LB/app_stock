package com.cvicse.stock.chart.presenter;

import com.cvicse.base.mvp.BasePresenter;
import com.cvicse.stock.chart.data.OHLCItemBo;
import com.cvicse.stock.chart.presenter.contract.StockDayContractNew;
import com.cvicse.stock.http.loop.ChartRunnable;
import com.cvicse.stock.http.loop.OrderQuantityRunnable;
import com.cvicse.stock.http.loop.QuoteDetailRunable;
import com.cvicse.stock.http.loop.RequestManager;
import com.cvicse.stock.http.loop.RunTaskState;
import com.cvicse.stock.markets.data.TickItemBo;
import com.cvicse.stock.utils.StockType;
import com.mitake.core.CacheChartFiveDay;
import com.mitake.core.CacheChartOneDay;
import com.mitake.core.OHLCItem;
import com.mitake.core.QuoteItem;
import com.mitake.core.response.ChartResponse;
import com.mitake.core.response.OrderQuantityResponse;
import com.mitake.core.response.QuoteResponse;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 分时图的presenter
 * Created by liu_zlu on 2017/2/23 21:21
 */
public class StockDayPresenterNew extends BasePresenter implements StockDayContractNew.Presenter {
    private StockDayContractNew.View view;
    private QuoteItem quoteItem;
    private ArrayList<TickItemBo> tickItemBos = new ArrayList<>();

    //循环请求标示
    private RunTaskState requestSign;

    private RunTaskState requestOrderSign;

    private String chartType;

    @Override
    public void setView(StockDayContractNew.View view) {
        this.view = view;
    }

    /**
     * 初始化数据
     *
     * @param quoteItem
     */
    @Override
    public void init(QuoteItem quoteItem ) {
        this.quoteItem = quoteItem;
    }

    /**
     * 请求走势数据
     */
    @Override
    public void requestChart() {
        //清除缓存
       // CacheChartOneDay.getInstance().removeCache(quoteItem.id);
        RequestManager.cancel(requestSign);
        requestSign = RequestManager.request(new ChartRunnable(quoteItem.id, chartType) {
            @Override
            public void onBack(ChartResponse response) {
                CopyOnWriteArrayList<OHLCItem> ohlcItems = response.historyItems;
                if(!checkNew(ohlcItems)){
                    return;
                }
                CacheChartOneDay.getInstance().removeCache(quoteItem.id);
                CacheChartFiveDay.getInstance().removeCache(quoteItem.id);
                view.onRequestChartSuccess(convert(ohlcItems));
            }

            @Override
            public void onError(int i, String error) {

            }
        });

        requestSign = RequestManager.request(new QuoteDetailRunable(quoteItem.id) {
            @Override
            public void onBack(QuoteResponse response) {
                QuoteResponse response1 = response;
                QuoteItem quoteItem = response1.quoteItems.get(0);
                // 行情请求
                view.onRequestQuote(quoteItem);
            }

            @Override
            public void onError(int i, String error) {
                view.onRequestChartFail();
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();

        requestChart();//请求走势数据
        if(!StockType.getType(quoteItem).isHongKong() && !StockType.getType(quoteItem).isIndex()){
            queryOrderQuantity(); //查询买卖队列
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        RequestManager.cancel(requestSign);
        RequestManager.cancel(requestOrderSign);
    }


    private OHLCItem lohlcItem;


    /**
     * 判断是否为最新的
     * @return
     * @param ohlcItems
     */
    private boolean checkNew(CopyOnWriteArrayList<OHLCItem> ohlcItems){
        OHLCItem temp = ohlcItems != null&& ohlcItems.size() > 0 ? ohlcItems.get(ohlcItems.size()-1): null;
        if(lohlcItem != null && temp!= null){
            if(temp.datetime != null && lohlcItem.datetime != null){
                if(temp.datetime.equals(lohlcItem.datetime)){
                    return false;
                }
            }
        }
        lohlcItem = temp;
        return true;
    }

    private ArrayList<OHLCItemBo> convert(CopyOnWriteArrayList<OHLCItem> ohlcItems){
        if(ohlcItems == null){
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
     * 查询买卖队列
     */
    private void queryOrderQuantity() {
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
