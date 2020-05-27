package com.cvicse.stock.markets.presenter.contract;

import com.cvicse.annotations.MVProvides;
import com.cvicse.base.mvp.IPresenter;
import com.cvicse.base.mvp.IView;
import com.cvicse.stock.markets.presenter.RankingListFuturesPresenter;
import com.mitake.core.response.CateSortingResponse;

/**
 *期货/全球、外汇/5分钟涨幅榜排序页面
 * Created by tang_xqing on 2018/7/30.
 */
public class RankingListFuturesContract {

    public interface View extends IView{
        void requestRankingSuccess(CateSortingResponse cateSortingResponse);
        void requestPullDownRankingSuccess(CateSortingResponse cateSortingResponse);
        void requestPullUpRankingSuccess(CateSortingResponse cateSortingResponse);
        void requestRankingFail();
    }

    @MVProvides(RankingListFuturesPresenter.class)
    public interface Presenter extends IPresenter<View>{
         void init(String id,boolean isAddValue);
        // 请求排序
         void requestRanking(String param);
        // 下拉刷新
         void requestPullDownRanking(String parm);
        // 上拉加载
         void requestPullUpRanking(String parm);
    }
}
