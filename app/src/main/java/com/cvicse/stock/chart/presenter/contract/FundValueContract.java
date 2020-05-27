package com.cvicse.stock.chart.presenter.contract;

import com.cvicse.annotations.MVProvides;
import com.cvicse.base.mvp.IPresenter;
import com.cvicse.base.mvp.IView;
import com.cvicse.stock.chart.presenter.FundValuePresenter;
import com.mitake.core.FundValueInfo;
import com.mitake.core.QuoteItem;

/**
 * Created by liu_zlu on 2017/4/28 14:36
 */
public class FundValueContract {
    public interface View extends IView {
        /**
         * 请求基金净值成功
         */
        void onRequestFundValueSuccess(FundValueInfo fundValueInfo);

        /**
         * 请求走势数据失败
         */
        void onRequestFundValueFail();

    }
    @MVProvides(FundValuePresenter.class)
    public interface Presenter extends IPresenter<View> {

        /**
         * 初始化数据
         */
        void init(QuoteItem quoteItem);
    }
}
