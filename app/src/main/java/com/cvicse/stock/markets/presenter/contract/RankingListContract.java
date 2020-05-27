package com.cvicse.stock.markets.presenter.contract;

import com.cvicse.annotations.MVProvides;
import com.cvicse.base.mvp.IPresenter;
import com.cvicse.base.mvp.IView;
import com.cvicse.stock.markets.presenter.RankingListPresenter;
import com.mitake.core.AddValueModel;
import com.mitake.core.QuoteItem;

import java.util.ArrayList;

/**
 * Created by ruan_ytai on 17-1-17.
 */

public class RankingListContract {

    public interface View extends IView {
        /**
         * 请求排行榜详细数据成功
         */
        void onRequestSuccess(ArrayList<QuoteItem> quoteItemList, ArrayList<AddValueModel> addValueModels);

        /**
         * 请求排行榜详细数据失败
         */
        void onRequestFail();

        /**
         * 上拉加载请求数据成功
         */
        void onRequestPullupSuccess(ArrayList<QuoteItem> quoteItemList, ArrayList<AddValueModel> addValueModels);

        /**
         * 上拉加载请求数据失败
         */
        void onRequestPullupFail();

        /**
         * 下拉刷新请求数据成功
         */
        void onRequestPulldownSuccess(ArrayList<QuoteItem> quoteItemList, ArrayList<AddValueModel> addValueModels);

        /**
         * 下拉刷新请求数据失败
         */
        void onRequestPulldownFail();
    }

    @MVProvides(RankingListPresenter.class)
    public interface Presenter extends IPresenter<View> {

        void init(String stockType);
        /**
         * 进入页面调用的接口
         */
        void requestRankingData(String param);

        /**
         * 点击排序调用的接口（下拉刷新，上拉加载）
         */
        void requestSortRankingData(String param);

        /**
         * 下拉刷新
         */
        void requestPulldownRankingData(String param);

        /**
         * 上拉加载
         */
        void requestPullupRankingData(String param);


    }
}
