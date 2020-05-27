package com.cvicse.stock.chart.presenter.contract;

import com.cvicse.annotations.MVProvides;
import com.cvicse.base.mvp.IPresenter;
import com.cvicse.base.mvp.IView;
import com.cvicse.stock.chart.presenter.StockKlinePresenter;
import com.mitake.core.OHLCItem;
import com.mitake.core.QuoteItem;
import com.mitake.core.parser.FQItem;
import com.mitake.core.parser.GBItem;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by liu_zlu on 2017/2/27 09:35
 */
public class StockKlineContract {
    public interface View extends IView {
        /**
         * 请求走势数据成功
         */
        void onRequestOHLCSuccess(CopyOnWriteArrayList<OHLCItem> ohlcItems, QuoteItem quoteItem, ArrayList<GBItem> gbItems);

        /**
         * 请求走势数据失败
         */
        void onRequestChartFail();

        // 请求复权接口成功
        void onRequestOHLCSubSuccess(CopyOnWriteArrayList<FQItem> ohlcSubRsList);

        // 请求复权接口失败
        void onRequestOHLCSubFail();
    }
    @MVProvides(StockKlinePresenter.class)
    public interface Presenter extends IPresenter<View> {

        /**
         * 初始化数据
         */
        void init(QuoteItem quoteItem,String ktype);

        /**
         * 请求K线图数据
         */
        void requestOHLC();

        void requestOHLCSub();

        void loadMoreData(boolean isOld);
    }
}
