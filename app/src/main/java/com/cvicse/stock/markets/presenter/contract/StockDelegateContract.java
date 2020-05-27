package com.cvicse.stock.markets.presenter.contract;

import com.cvicse.annotations.MVProvides;
import com.cvicse.base.mvp.IPresenter;
import com.cvicse.base.mvp.IView;
import com.cvicse.stock.markets.presenter.StockDelegatePresenter;
import com.mitake.core.QuoteItem;
import com.mitake.core.response.OrderQuantityResponse;
import com.mitake.core.response.QuoteResponse;

/**
 * 委托队列页面
 * Created by liu_zlu on 2017/4/21 15:42
 */
public class StockDelegateContract {
    public interface View extends IView {

        /**
         * 返回买卖队列
         * @param quoteResponse
         */
        void onOrderQuantitySuccess(OrderQuantityResponse quoteResponse);
        /**
         * 返回股票快照行情
         * @param quoteResponse
         */
        void onQuoteSuccess(QuoteResponse quoteResponse);
    }
    @MVProvides(StockDelegatePresenter.class)
    public interface Presenter extends IPresenter<View> {
        /**
         * 初始化stockId
         * @param quoteItem
         */
        void init(QuoteItem quoteItem);
    }
}
