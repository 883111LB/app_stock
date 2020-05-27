package com.cvicse.stock.markets.ui.other_option.presenter.contract;

import com.cvicse.annotations.MVProvides;
import com.cvicse.base.mvp.IPresenter;
import com.cvicse.base.mvp.IView;
import com.cvicse.stock.markets.ui.other_option.presenter.DCEOption1Presenter;
import com.mitake.core.QuoteItem;

import java.util.ArrayList;

/**
 * 大商所、郑商所期权 商品行情页面
 * Created by tang_hua on 2020/3/26.
 */

public class DCEOption1Contract {
    public interface View extends IView {

        /**
         * 请求连续合约列表 成功
         */
        void cateSortingRequestSuccess(ArrayList<QuoteItem> list);

        /**
         * 请求连续合约列表 失败
         */
        void cateSortingRequestFail(String message);
    }

    @MVProvides(DCEOption1Presenter.class)
    public interface Presenter extends IPresenter<View> {

        /**
         * 请求连续合约列表
         * @param stockType 大商所/郑商所
         * @param ID 点击的列表项的id，如果是从更多页面进入的为null
         */
        void cateSortingRequest(String stockType, String ID);
    }
}
