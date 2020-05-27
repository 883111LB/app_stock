package com.cvicse.stock.markets.presenter.contract;

import com.cvicse.base.mvp.IPresenter;
import com.cvicse.base.mvp.IView;
import com.mitake.core.QuoteItem;

import java.util.ArrayList;

/**
 * Created by ding_syi on 17-2-20.
 */
public class SHQQPutConstract  {
    public interface View extends IView {

        /**
         *查询认沽期权成功
         */
        void onRequestPutSuccess(ArrayList<QuoteItem> list);

        /**
         *查询认沽期权失败
         */
        void onRequestSHQQPutFail();

    }

    public interface Presenter extends IPresenter<View> {
        /**
         * 查询认沽
         */
        void requestSHQQPut(String stockId);
    }
}
