package com.cvicse.stock.chart.presenter;

import com.cvicse.base.mvp.BasePresenter;
import com.cvicse.stock.chart.presenter.contract.StockKlineContract;
import com.cvicse.stock.http.loop.OHLCRunnable;
import com.cvicse.stock.http.loop.RequestManager;
import com.cvicse.stock.http.loop.RunTaskState;
import com.cvicse.stock.util.FormatUtils;
import com.mitake.core.CacheDayK;
import com.mitake.core.CacheFifteenK;
import com.mitake.core.CacheFiveK;
import com.mitake.core.CacheMonthK;
import com.mitake.core.CacheOnehunderedTwentyk;
import com.mitake.core.CacheSixtyK;
import com.mitake.core.CacheThirtyK;
import com.mitake.core.CacheWeekK;
import com.mitake.core.OHLCItem;
import com.mitake.core.QuoteItem;
import com.mitake.core.parser.FQItem;
import com.mitake.core.parser.GBItem;
import com.mitake.core.request.OHLChartType;
import com.mitake.core.response.OHLCResponse;

import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by liu_zlu on 2017/2/27 10:47
 */
public class StockKlinePresenter extends BasePresenter implements StockKlineContract.Presenter {
    private StockKlineContract.View view;
    private QuoteItem quoteItem;
    private String ktype;
    private RunTaskState requestSugn;

    private String oldKType;
    @Override
    public void setView(StockKlineContract.View view) {
        this.view = view;
    }

    /**
     * 初始化数据
     *
     * @param quoteItem
     * @param ktype
     */
    @Override
    public void init(QuoteItem quoteItem, String ktype) {
        this.quoteItem = quoteItem;
        this.ktype = ktype;
    }

    private long datetime = 0;

    // 作用于历史K线
    private String dateTimeS;

    /**
     * 请求K线图数据
     */
    @Override
    public void requestOHLC() {
        RequestManager.cancel(requestSugn);
        if( !ktype.equals(oldKType) ){
            oldKType = ktype;
            dateTimeS = FormatUtils.formatDate(new Date(System.currentTimeMillis()),"yyyyMMddhhmmss");
            if( !isMinuteK() ){
                dateTimeS = dateTimeS.substring(0,8);
            }
        }
        requestSugn = RequestManager.request(new OHLCRunnable(quoteItem,ktype,dateTimeS) {
            @Override
            public void onBack(OHLCResponse response) {
                remove(quoteItem.id,ktype);
                CopyOnWriteArrayList<OHLCItem> historyItems = response.historyItems;
                CopyOnWriteArrayList<FQItem> fq = response.fq;
                view.onRequestOHLCSubSuccess(response.fq);
                ArrayList<GBItem> gbItems = response.gb;

                /*  FIXME: 2018/5/7 舍弃一下代码（作用：数据相同时不更新）。原因：当数据相同时，切换复权出现无效
                if( null!=historyItems  && historyItems.size() > 0){
                    String textTime =historyItems.get(0).datetime;
                    long temp = NumberUtils.parseLong(historyItems.get(historyItems.size() - 1).datetime);
                    if(datetime >= temp){
                        return;
                    }
                    datetime = temp;
                    dateTimeS = textTime;; // new
                }
                */
                view.onRequestOHLCSuccess(historyItems,quoteItem, gbItems);
            }

            @Override
            public void onError(int i, String error) {

            }
        });
    }

    /**
     * 请求复权接口
     */
    @Override
    public void requestOHLCSub() {
//        final OHLCSubRequest ohlcSubRequest = new OHLCSubRequest();
//        ohlcSubRequest.send(quoteItem.id, new ResponseCallback(ohlcSubRequest) {
//            @Override
//            public void onBack(Response response) {
//                OHLCSubResponse response1 = (OHLCSubResponse) response;
//                CopyOnWriteArrayList<OHLCSubR> fqList = response1.fq;
//                view.onRequestOHLCSubSuccess(fqList);
//            }
//
//            @Override
//            public void onError(int i, String s) {
//                view.onRequestOHLCSubFail();
//            }
//        });
    }

    /**
     * 加载更多的数据
     * @param isOld
     */
    @Override
    public void loadMoreData(boolean isOld) {
        String dateTime = dateTimeS;
        if(!isMinuteK()){
            dateTime = dateTime.substring(0,8);
        }
        if( !isOld ){
            dateTime +="#";  // 获取日期后的数据，需添加"#"
        }
        dateTimeS = dateTime;
        requestOHLC();
    }

    @Override
    public void onPause() {
        super.onPause();
        RequestManager.cancel(requestSugn);
    }

    @Override
    public void onResume() {
        super.onResume();
        requestOHLC();
    }

    private boolean isMinuteK(){
        if( OHLChartType.CHART_DAY.equals(ktype) ||  OHLChartType.CHART_WEEK.equals(ktype) ||
                OHLChartType.CHART_MONTH.equals(ktype) ||  OHLChartType.CHART_YEAR.equals(ktype)){
            return false;
        }
        return true;
    }

    private void remove(String type,String code){
        if(type.equals("dayk ")) {
            CacheDayK.getInstance().removeCache(code);
        } else if(type.equals("weekk")) {
            CacheWeekK.getInstance().removeCache(code);
        } else if(type.equals("monthk")) {
            CacheMonthK.getInstance().removeCache(code);
        } else if(type.equals("m5")) {
            CacheFiveK.getInstance().removeCache(code);
        } else if(type.equals("m15")) {
            CacheFifteenK.getInstance().removeCache(code);
        } else if(type.equals("m30")) {
            CacheThirtyK.getInstance().removeCache(code);
        } else if(type.equals("m60")) {
            CacheSixtyK.getInstance().removeCache(code);
        } else if(type.equals("m120")) {
            CacheOnehunderedTwentyk.getInstance().removeCache(code);
        }
    }
}
