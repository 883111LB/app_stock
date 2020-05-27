package com.cvicse.stock.markets.presenter.contract;

import com.cvicse.annotations.MVProvides;
import com.cvicse.base.mvp.IPresenter;
import com.cvicse.base.mvp.IView;
import com.cvicse.stock.markets.presenter.HKPresenter;
import com.mitake.core.QuoteItem;
import com.mitake.core.response.CateSortingResponse;

import java.util.ArrayList;

/**
 * Created by ruan_ytai on 17-1-18.
 * 港股页面数据
 */

public class HKContract {

    public interface View extends IView{
        /**
         * 请求港股指数成功
         */
        void onRequestIndexSuccess(ArrayList<QuoteItem> result);

        /**
         * 请求港股指数失败
         */
        void onRequestIndexFail();

        /**
         * 请求港股排行榜数据成功
         */
//        void onRequestRankingSuccess(ArrayList<QuoteItem> list,int position);
        void onRequestRankingSuccess(CateSortingResponse response,int position);

        /**
         * 请求港股排行榜数据失败
         */
        void onRequestRankingFail();
    }

    @MVProvides(HKPresenter.class)
    public interface Presenter extends IPresenter<View>{
        /**
         * 请求港股指数
         */
        void requestIndex();

        /**
         * 请求港股排行榜
         */
        void requestRefesh();

        /**
         * 请求港股排行榜
         */
        void requestRankingList(int position);
    }
}
