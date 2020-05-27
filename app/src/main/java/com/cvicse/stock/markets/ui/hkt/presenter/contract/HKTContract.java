package com.cvicse.stock.markets.ui.hkt.presenter.contract;

import com.cvicse.annotations.MVProvides;
import com.cvicse.base.mvp.IPresenter;
import com.cvicse.base.mvp.IView;
import com.cvicse.stock.markets.ui.hkt.presenter.HKTPresenter;
import com.mitake.core.bean.HKTItem;

import java.util.List;

/**
 * 沪深港通
 * Created by tang_hua on 2020/5/13.
 */

public class HKTContract {

    public interface View extends IView {

        /**
         * 沪深港通走势 成功
         */
        void tongLineRequestSuccess(List<HKTItem> hktItems);
        /**
         * 沪深港通走势 失败
         */
        void tongLineRequestFailure(String message);
    }

    @MVProvides(HKTPresenter.class)
    public interface Presenter extends IPresenter<View> {

        /**
         * 沪深港通走势
         * @param flag :
         * TongLineRequest.Type.TYPE_HKT(获取港股通额度走势)
         * TongLineRequest.Type.TYPE_HST(获取沪深股通额度走势)
         */
        void tongLineRequest(String flag);
    }
}
