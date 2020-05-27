package com.cvicse.stock.chart.presenter.contract;

import com.cvicse.annotations.MVProvides;
import com.cvicse.base.mvp.IPresenter;
import com.cvicse.base.mvp.IView;
import com.cvicse.stock.chart.data.OHLCItemBo;
import com.cvicse.stock.chart.presenter.StockDayPresenterNew;
import com.cvicse.stock.markets.data.TickItemBo;
import com.mitake.core.QuoteItem;
import com.mitake.core.response.OrderQuantityResponse;

import java.util.ArrayList;

/**新五日图
 * Created by liu_zlu on 2017/2/23 21:22
 */
public class StockDayContractNew {

    public interface View extends IView {

        /**
         *   请求行情成功
         */
        void onRequestQuote(QuoteItem quoteItem);

        /**
         * 请求走势数据成功
         */
        void onRequestChartSuccess(ArrayList<OHLCItemBo> ohlcItems);

        /**
         * 请求走势数据失败
         */
        void onRequestChartFail();

        /**
         * 返回股票逐笔数据
         * @param tickItemBos
         */
        void onTickItemsSuccess(ArrayList<TickItemBo> tickItemBos);

        /**
         * 返回买卖队列
         * @param quoteResponse
         */
        void onOrderQuantitySuccess(OrderQuantityResponse quoteResponse);

    }

    @MVProvides(StockDayPresenterNew.class)
    public interface Presenter extends IPresenter<View> {

        /**
         * 初始化数据
         */
        void init(QuoteItem quoteItem);

        /**
         * 请求走势数据
         */
        void requestChart();
    }
}
