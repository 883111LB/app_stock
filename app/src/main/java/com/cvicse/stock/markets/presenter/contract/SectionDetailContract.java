package com.cvicse.stock.markets.presenter.contract;

import com.cvicse.annotations.MVProvides;
import com.cvicse.base.mvp.IPresenter;
import com.cvicse.base.mvp.IView;
import com.cvicse.stock.markets.presenter.SectionDetailPresenter;
import com.mitake.core.QuoteItem;

import java.util.ArrayList;

/**
 * Created by ding_syi on 17-2-23.
 */
public class SectionDetailContract {

    public interface View extends IView {
        /**
         * 请求成功
         */
        void onRequestSuccess(ArrayList<QuoteItem> quoteItemList);

        /**
         * 请求失败
         */
        void onRequestFail();

        /**
         * 下拉刷新请求成功
         */
        void onRequestPulldownSuccess(ArrayList<QuoteItem> quoteItemList);

        /**
         * 下拉刷新请求失败
         */
        void onRequestPulldownFail();

        /**
         * 上拉加载请求成功
         */
        void onRequestPullupSuccess(ArrayList<QuoteItem> quoteItemList);

        /**
         * 上拉加载请求失败
         */
        void onRequestPullupFail();
    }

    @MVProvides(SectionDetailPresenter.class)
    public interface Presenter extends IPresenter<View> {
        /**
         *
         * @param stockId  股票id
         * @param param   排序规则
         */
        void request(String stockId,String param);

        /**
         *
         * @param stockId  股票id
         * @param param   排序规则
         */
        void requestPulldownData(String stockId,String param);


        /**
         *
         * @param stockId  股票id
         * @param param   排序规则
         */
        void requestPullupData(String stockId,String param);


    }
}
