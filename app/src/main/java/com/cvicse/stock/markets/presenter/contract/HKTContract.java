package com.cvicse.stock.markets.presenter.contract;

import com.cvicse.annotations.MVProvides;
import com.cvicse.base.mvp.IPresenter;
import com.cvicse.base.mvp.IView;
import com.cvicse.stock.markets.presenter.HKTPresenter;
import com.mitake.core.QuoteItem;
import com.mitake.core.bean.HSAmountItem;
import com.mitake.core.response.HKMarInfoResponse;

import java.util.ArrayList;

/**
 * 港股通
 * Created by ding_syi on 17-2-6.
 */
public class HKTContract {
    public interface View extends IView {
        /**
         * 请求港股通成功
         */
        void onRequestHKTSuccess(ArrayList<QuoteItem> quoteItems,int position);

        /**
         * 请求港股失败
         */
        void onRequestHTKFail();

        /**
         * 请求港股通(深,沪)余额数据成功
         */
        void requestBalanceHKTSuccess(HKMarInfoResponse response);

        /**
         * 请求港股通余额数据失败
         */
        void requestBalanceHKTFail();

        // 请求沪深股通额度成功
        void requestHSAmountSuccess(HSAmountItem hsAmountItem);

        void requestHSAmountFail();
    }

    @MVProvides(HKTPresenter.class)
    public interface Presenter extends IPresenter<View> {
        /**
         * 请求港股通
         */
        void requestHKT(int position);

        /**
         * 请求港股通余额
         */
        void requestBalanceHKT();

        /**
         * 请求港股排行榜
         */
        void requestRefesh();

        // 请求沪深股通额度
        void  requestHSAmount();

    }
}
