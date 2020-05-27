package com.cvicse.stock.markets.ui.subnewbondstock.presenter.contract;

import com.cvicse.annotations.MVProvides;
import com.cvicse.base.mvp.IPresenter;
import com.cvicse.base.mvp.IView;
import com.cvicse.stock.markets.ui.subnewbondstock.presenter.SubNewBondStockPresenter;
import com.mitake.core.QuoteItem;
import com.mitake.core.SubNewStockRankingModel;

import java.util.ArrayList;

/**
 * 次新股
 * Created by tang_hua on 2020/3/24.
 */

public class SubNewBondStockContract {
    public interface View extends IView {
        /**
         * 次新债列表 成功
         */
        void subNewBondSuccess(ArrayList<SubNewStockRankingModel> result);
        /**
         * 次新债列表 成功(下拉刷新）
         */
        void subNewBondRefreshSuccess(ArrayList<SubNewStockRankingModel> result);
        /**
         * 次新债列表 成功（上拉加载）
         */
        void subNewBondMoreSuccess(ArrayList<SubNewStockRankingModel> result);

        /**
         * 获取快照成功
         */
        void getQuoteInfoSuccess(QuoteItem quoteItem);
    }
    @MVProvides(SubNewBondStockPresenter.class)
    public interface Presenter extends IPresenter<View> {
        /**
         * 次新债列表
         * @param addFlag 请求类型（默认、下拉刷新、上拉加载）
         * @param page      第几页,
         * @param pageSize 每页多少条,
         * @param sortField 排序栏位,
         * @param sortType 升降序)
         */
        void subNewBondStockRanking(int addFlag, int page, int pageSize, String sortField, String sortType);

        /**
         * 获取快照
         */
        void getQuoteInfo(String code);
    }
}
