package com.cvicse.stock.markets.presenter.marketdetail.contract;

import com.cvicse.annotations.MVProvides;
import com.cvicse.base.mvp.IPresenter;
import com.cvicse.base.mvp.IView;
import com.cvicse.stock.markets.presenter.marketdetail.FundFinancePresenter;

import java.util.HashMap;

/**
 * Created by Administrator on 2017/6/20.
 * 基金财务契约类
 */

public class FundFinanceContract {

    public interface View extends IView{

        /**
         * 查询基金财务成功
         */
        void onFinanceSuccess(HashMap<String, Object> info);

        /**
         * 查询基金财务失败
         */
        void onFinanceFail();
    }

    @MVProvides(FundFinancePresenter.class)
    public interface Presenter extends IPresenter<View>{
        /**
         * 初始化stockId
         * @param stockId
         */
        void init(String stockId);

        /**
         * 查询基金财务信息
         */
        void queryFinaMessage();
    }
}
