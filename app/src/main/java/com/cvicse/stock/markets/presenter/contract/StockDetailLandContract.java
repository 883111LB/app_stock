package com.cvicse.stock.markets.presenter.contract;

import com.cvicse.annotations.MVProvides;
import com.cvicse.base.mvp.IPresenter;
import com.cvicse.base.mvp.IView;
import com.cvicse.stock.markets.presenter.StockDetailLandPresenter;
import com.mitake.core.QuoteItem;
import com.mitake.core.response.QuoteResponse;

/**
 * Created by liu_zlu on 2017/2/28 09:23
 */
public class StockDetailLandContract {
    public interface View extends IView {

        void onQuoteSuccess(QuoteResponse response);
    }
    @MVProvides(StockDetailLandPresenter.class)
    public interface Presenter extends IPresenter<View> {
        /**
         * 初始化stockId
         * @param quoteItem
         */
        void init(QuoteItem quoteItem);
    }
}
