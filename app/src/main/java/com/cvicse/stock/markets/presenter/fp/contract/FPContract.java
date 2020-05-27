package com.cvicse.stock.markets.presenter.fp.contract;

import com.cvicse.annotations.MVProvides;
import com.cvicse.base.mvp.IPresenter;
import com.cvicse.base.mvp.IView;
import com.cvicse.stock.markets.presenter.fp.FPPresenter;
import com.mitake.core.QuoteItem;
import com.mitake.core.bean.compound.CompoundUpDownBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 复盘页面
 * Created by tang_hua on 2020/2/21.
 */

public class FPContract {
    public interface View extends IView {

        /**
         * 涨跌分布 成功
         */
        void compoundUpDownRequestSuccess(List<CompoundUpDownBean> list);
        /**
         * 涨跌分布 成功
         */
        void compoundUpDownRequestFailure(String message);

        /**
         * 指数行情请求 成功
         */
        void quoteRequestSuccess(ArrayList<QuoteItem> list);
        /**
         * 指数行情请求 失败
         */
        void quoteRequestFailire(String message);
    }

    @MVProvides(FPPresenter.class)
    public interface Presenter extends IPresenter<View> {

        /**
         * 涨跌分布
         * @param market   市场
         * @param time 日期
         * @param dateType 日期类型
         */
        void compoundUpDownRequest(String market, String time, int dateType);

        /**
         * 请求行情
         */
        void quoteRequest();
    }
}
