package com.cvicse.stock.markets.presenter.contract;

import com.cvicse.annotations.MVProvides;
import com.cvicse.base.mvp.IPresenter;
import com.cvicse.base.mvp.IView;
import com.cvicse.stock.markets.presenter.StockTenPresenter;
import com.mitake.core.QuoteItem;
import com.mitake.core.response.QuoteResponse;

/**
 * 十档详细
 * Created by liu_zlu on 2017/3/7 10:14
 */
public class StockTenContract {
    public interface View extends IView {

        /**
         * 返回股票快照行情
         * @param quoteResponse
         */
        void onQuoteSuccess(QuoteResponse quoteResponse);
    }
    @MVProvides(StockTenPresenter.class)
    public interface Presenter extends IPresenter<View> {
        /**
         * 初始化stockId
         * @param quoteItem
         */
        void init(QuoteItem quoteItem);
    }
}
