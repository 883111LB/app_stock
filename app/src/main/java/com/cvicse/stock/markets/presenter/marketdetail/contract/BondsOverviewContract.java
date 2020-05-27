package com.cvicse.stock.markets.presenter.marketdetail.contract;

import com.cvicse.annotations.MVProvides;
import com.cvicse.base.mvp.IPresenter;
import com.cvicse.base.mvp.IView;
import com.cvicse.stock.markets.presenter.marketdetail.BondsOverviewPresenter;

import java.util.HashMap;

/**
 * Created by Administrator on 2017/6/20.
 * 债券概况 契约类
 */

public class BondsOverviewContract {

    public interface View extends IView{

        /**
         * 请求债券概况信息成功
         * @param info
         */
       void onQueryBondBasicSuccess(HashMap<String,Object> info);

    }

    @MVProvides(BondsOverviewPresenter.class)
    public interface Presenter extends IPresenter<View> {

        /**
         *  初始化股票代码
         * @param stockId
         */
        void init(String stockId);

        /**
         * 查询债券概况
         */
        void queryBondBasic();
    }
}
