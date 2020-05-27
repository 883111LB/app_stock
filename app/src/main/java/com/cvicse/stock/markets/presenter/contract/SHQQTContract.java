package com.cvicse.stock.markets.presenter.contract;

import com.cvicse.annotations.MVProvides;
import com.cvicse.base.mvp.IPresenter;
import com.cvicse.base.mvp.IView;
import com.cvicse.stock.markets.presenter.SHQQTPresenter;
import com.mitake.core.QuoteItem;

import java.util.ArrayList;

/**
 * Created by ding_syi on 17-2-16.
 */
public class SHQQTContract {

    public interface View extends IView {

        /**
         *查询T型期权成功
         */
        void onRequestSHQQTSuccess(ArrayList<QuoteItem> list);

        /**
         *查询T型期权失败
         */
        void onRequestSHQQTFail();

        /**
         * 查询交割月成功
         */
        void requestExpireSuccess(String[] list, String[][] listD);

        /**
         * 查询交割月失败
         */
        void requestExpireFail();

    }

    @MVProvides(SHQQTPresenter.class)
    public interface Presenter extends IPresenter<View> {
        /**
         * 查询T型期权数据
         */
        void requestSHQQT(String stock,String data);

        /**
         * 获取交割月数据
         */
        void requestExpire(String stockId);
    }
}
