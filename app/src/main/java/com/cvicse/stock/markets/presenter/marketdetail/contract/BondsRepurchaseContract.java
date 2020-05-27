package com.cvicse.stock.markets.presenter.marketdetail.contract;

import com.cvicse.annotations.MVProvides;
import com.cvicse.base.mvp.IPresenter;
import com.cvicse.base.mvp.IView;
import com.cvicse.stock.markets.presenter.marketdetail.BondsRepurchasePresenter;

import java.util.HashMap;

/**
 * Created by Administrator on 2017/6/20.
 * 债券回购契约类
 */

public class BondsRepurchaseContract {

    public interface View extends IView{

        /**
         * 请求债券回购信息成功
         * @param info
         */
        void onQuerybndBuyBacks(HashMap<String,Object> info);

        /**
         * 请求债券回购信息失败
         */
        void onQuerybndBuyBacksFail();
    }

    @MVProvides(BondsRepurchasePresenter.class)
    public interface Presenter extends IPresenter<View> {

        /**
         * 初始化股票Id
         * @param stockId
         */
        void init(String stockId);

        /**
         * 查询债券回购情况
         */
        void queryBndBuyBacks();
    }
}
