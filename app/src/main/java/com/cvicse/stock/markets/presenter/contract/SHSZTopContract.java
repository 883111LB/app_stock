package com.cvicse.stock.markets.presenter.contract;

import com.cvicse.annotations.MVProvides;
import com.cvicse.base.mvp.IPresenter;
import com.cvicse.base.mvp.IView;
import com.cvicse.stock.markets.presenter.SHSZTopPresenter;
import com.mitake.core.QuoteItem;

import java.util.ArrayList;

/**
 * Created by ruan_ytai on 17-1-16.
 */

public interface SHSZTopContract {

    interface View extends IView{

        /**
         * 请求成功
         */
        void onIndexSuccess(ArrayList<QuoteItem> result);

        /**
         * 请求失败
         */
        void onIndexFail();

        /**
         * 指数实时更新
         * @param quoteItem
         */
        void onIndexTcp(QuoteItem quoteItem);

    }

    @MVProvides(SHSZTopPresenter.class)
    interface Presenter extends IPresenter<View>{
        /**
         * 请求指数
         */
        void requestIndex(String type);

    }
}
