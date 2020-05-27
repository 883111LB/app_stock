package com.cvicse.stock.markets.presenter.marketdetail.contract;

import com.cvicse.annotations.MVProvides;
import com.cvicse.base.mvp.IPresenter;
import com.cvicse.base.mvp.IView;
import com.cvicse.stock.markets.presenter.marketdetail.FundSummaryPresenter;

import java.util.HashMap;
import java.util.List;

/**
 * Created by liu_zlu on 2017/2/13 21:26
 */
public class FundSummaryContract {
    public interface View extends IView {

        /**
         * 获取基金净值信息成功
         */
        void onFundValueSuccess(List<HashMap<String, Object>> infos);

        /**
         * 获取基金分红信息成功
         */
        void onFundNetWorthSuccess(HashMap<String, Object> info);
    }

    @MVProvides(FundSummaryPresenter.class)
    public interface Presenter extends IPresenter<View> {
        /**
         * 初始化stockId
         * @param stockId
         */
        void init(String stockId);
        /**
         * 查询公司摘要信息
         */
        void querySummary();

    }
}
