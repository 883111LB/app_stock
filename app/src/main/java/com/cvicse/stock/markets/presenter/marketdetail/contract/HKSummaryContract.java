package com.cvicse.stock.markets.presenter.marketdetail.contract;

import com.cvicse.annotations.MVProvides;
import com.cvicse.base.mvp.IPresenter;
import com.cvicse.base.mvp.IView;
import com.cvicse.stock.markets.presenter.marketdetail.HKSummaryPresenter;
import com.mitake.core.BonusFinance;
import com.mitake.core.NewIndex;

import java.util.ArrayList;

/**
 * 港股摘要
 * Created by liu_zlu on 2017/2/13 16:44
 */
public class HKSummaryContract {
    public interface View extends IView {
        /**
         * 获取最新指标成功
         */
        void onNewIndexSuccess(NewIndex newIndex);

        /**
         * 获取大事提醒成功
         *//*
        void onImportantnoticeSuccess();*/

        /**
         * 获取分红配送成功
         */
        void onBonusFinanceSuccess(ArrayList<BonusFinance> bonusFinances);


    }

    @MVProvides(HKSummaryPresenter.class)
    public interface Presenter extends IPresenter<View> {

        /**
         * 初始化stockId
         * @param stockId
         */
        void init(String stockId);

        /**
         * 查询概要信息
         */
        void queryHKSummary();
    }
}
