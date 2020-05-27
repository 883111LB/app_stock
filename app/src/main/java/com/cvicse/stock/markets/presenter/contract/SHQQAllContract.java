package com.cvicse.stock.markets.presenter.contract;

import com.cvicse.annotations.MVProvides;
import com.cvicse.base.mvp.IPresenter;
import com.cvicse.base.mvp.IView;
import com.cvicse.stock.markets.presenter.SHQQAllPresenter;
import com.mitake.core.QuoteItem;

import java.util.ArrayList;

/**
 * Created by ding_syi on 17-2-17.
 */
public class SHQQAllContract {
    public interface View extends IView {

        /**
         *查询全部期权成功
         */
        void onRequestSHQQSuccess(ArrayList<QuoteItem> list);

        /**
         *查询全部期权失败
         */
        void onRequestSHQQFail();



    }
    @MVProvides(SHQQAllPresenter.class)
    public interface Presenter extends IPresenter<View> {

        void init(String stockId,String tag);
        /**
         * 查询全部期权
         */
        void requestSHQQAll();
        /**
         * 查询全部期权
         */
        void requestSHQQAllMore();
    }
}
