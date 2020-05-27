package com.cvicse.stock.markets.presenter.marketdetail.contract;

import com.cvicse.annotations.MVProvides;
import com.cvicse.base.mvp.IPresenter;
import com.cvicse.base.mvp.IView;
import com.cvicse.stock.markets.presenter.marketdetail.BoundsInterestPresenter;

import java.util.HashMap;

/**
 * Created by Administrator on 2017/6/20.
 * 债券付息契约类
 */

public class BoundsInterestContract {

    public interface View extends IView{

        /**
         * 查询债券付息情况成功
         */
        void onqueryBundInterestPaySuccess(HashMap<String, Object> info);

        /**
         * 查询债券付息情况失败
         */
        void onqueryBundInterestPayFail();
    }

    @MVProvides(BoundsInterestPresenter.class)
    public interface Presenter extends IPresenter<View>{

        /**
         * 初始化股票代码
         * @param stockId
         */
        void  init(String stockId);

        /**
         * 查询付息情况
         */
        void queryBundInterestPay();
    }

}
