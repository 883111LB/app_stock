package com.cvicse.stock.markets.presenter.marketdetail.contract;

import com.cvicse.annotations.MVProvides;
import com.cvicse.base.mvp.IPresenter;
import com.cvicse.base.mvp.IView;
import com.cvicse.stock.markets.presenter.marketdetail.IndexRankingPresenter;
import com.mitake.core.QuoteItem;

import java.util.ArrayList;

/**
 * 排行页面
 * Created by liu_zlu on 2017/2/13 11:42
 */
public class IndexRankingContract {
    public interface View extends IView {
        /**
         * 获取排行信息成功
         */
        void onCateRankingSuccess(ArrayList<QuoteItem> quoteItems);

        /**
         * 获取排行信息失败
         */
        void onCateRankingFail();

        void onDataSuccess(String stockType,String rankingType);

    }

    @MVProvides(IndexRankingPresenter.class)
    public interface Presenter extends IPresenter<View> {
        /**
         * 初始化stockId
         * @param stockId
         */
        void init(String stockId,String type);
        /**
         * 查询成分股
         */
        void queryIndexRanking();

        /**
         * 页面获取presenter层信息
         */
        void getData();

    }
}
