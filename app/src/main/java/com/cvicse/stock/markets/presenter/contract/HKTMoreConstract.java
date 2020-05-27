package com.cvicse.stock.markets.presenter.contract;

import com.cvicse.annotations.MVProvides;
import com.cvicse.base.mvp.IPresenter;
import com.cvicse.base.mvp.IView;
import com.cvicse.stock.markets.presenter.HKTMorePresenter;
import com.mitake.core.QuoteItem;

import java.util.ArrayList;

/**
 * Created by ding_syi on 17-2-7.
 */
public class HKTMoreConstract {

    public interface View extends IView {

        /**
         * 请求排行榜数据成功
         */
        void requestRankingDataSuccess(ArrayList<QuoteItem> list);

        /**
         * 请求排行榜数据失败
         */
        void requestRankingDataFail(String stockType, String param);

        /**
         * 请求下拉刷新数据成功
         */
        void requestPulldownDataSuccess(ArrayList<QuoteItem> list);

        /**
         * 请求下拉刷新数据失败
         */
        void requestPulldownDataFail(String stockType, String param);

        /**
         * 请求上拉加载数据成功
         */
        void requestPullUpDataSuccess(ArrayList<QuoteItem> list);

        /**
         * 请求上拉加载数据失败
         */
        void requestPullUpDataFail(String stockType, String param);
    }

    @MVProvides(HKTMorePresenter.class)
    public interface Presenter extends IPresenter<View> {

        /**
         * 请求排行榜数据
         */
        void requestRankingData(String stockType, String param);

        /**
         * 请求下拉刷新数据
         */
        void requestPulldownData(String stockType, String param);

        /**
         * 请求上拉加载数据
         */
        void requestPullUpData(String stockType, String param);

    }
}
