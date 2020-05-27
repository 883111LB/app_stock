package com.cvicse.stock.markets.presenter.marketdetail.contract;

import com.cvicse.annotations.MVProvides;
import com.cvicse.base.mvp.IPresenter;
import com.cvicse.base.mvp.IView;
import com.cvicse.stock.markets.presenter.marketdetail.FundBasicPresenter;

import java.util.HashMap;
import java.util.List;

/**
 * Created by ruan_ytai on 17-3-29.
 */

public class FundBasicContract {
    public interface View extends IView {

        /**
         * 查询基金概况成功
         */
        void onQueryFundBasicSuccess(HashMap<String, Object> info);

        /**
         * 查询资产配置成功
         */
        void onQueryFundAssetAllocationSuccess( List<HashMap<String, Object>> infos);

        /**
         * 查询份额结构成功
         */
        void onQueryFundShareStructureSuccess(List<HashMap<String, Object>> infos);

        /**
         * 查询行业组合成功
         */
        void onQueryFundIndustryPortfolioSuccess(List<HashMap<String, Object>> infos);

        /**
         * 查询股票组合成功
         */
        void onQueryFundStockPortfolioSuccess(List<HashMap<String, Object>> infos);
    }

    @MVProvides(FundBasicPresenter.class)
    public interface Presenter extends IPresenter<View> {
        /**
         * 初始化stockId
         * @param stockId
         */
        void init(String stockId);

        /**
         * 查询基金相关所有信息
         */
        void queryFundData();
    }
}
