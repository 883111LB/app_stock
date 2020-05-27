package com.cvicse.stock.chart.presenter.contract;

import com.cvicse.annotations.MVProvides;
import com.cvicse.base.mvp.IPresenter;
import com.cvicse.base.mvp.IView;
import com.cvicse.stock.chart.data.MinuteEntity;
import com.cvicse.stock.chart.presenter.StockDayPresenter;
import com.cvicse.stock.markets.data.TickItemBo;
import com.mitake.core.QuoteItem;
import com.mitake.core.response.ChartResponse;
import com.mitake.core.response.OrderQuantityResponse;

import java.util.ArrayList;

/**
 * Created by liu_zlu on 2017/2/23 21:22
 */
public class StockDayContract {

    public interface View extends IView {

        //  请求行情成功
        void onRequestQuote(QuoteItem quoteItem);

        // 请求走势数据成功
        void onRequestChartSuccess(ChartResponse response);
//        void onRequestChartSuccess(ArrayList<OHLCItemBo> ohlcItems);  // old 2018-8-8

        //请求走势数据失败
        void onRequestChartFail();

        // 请求走势副图成功
        void onRequestChartSubSuccess(ArrayList<MinuteEntity> minuteEntities);

        // 请求走势副图失败
        void onRequestChartSubFail(int i, String error);

        //返回股票逐笔数据
        void onTickItemsSuccess(ArrayList<TickItemBo> tickItemBos);

        // 返回买卖队列
        void onOrderQuantitySuccess(OrderQuantityResponse quoteResponse);

    }

    @MVProvides(StockDayPresenter.class)
    public interface Presenter extends IPresenter<View> {

        //初始化数据
        void init(QuoteItem quoteItem,String chartType,int indexType);

        //请求走势数据
        void requestChart();

        // 走势副图  indexType：指标类别
        void requestChartSub(int indexType);

        // 请求分时明细接口
        void requestTick(String code);
    }
}
