package com.cvicse.stock.portfolio.presenter.constract;

import com.cvicse.annotations.MVProvides;
import com.cvicse.base.mvp.IPresenter;
import com.cvicse.base.mvp.IView;
import com.cvicse.stock.portfolio.presenter.RecentlyPresenter;

/**
 * Created by ding_syi on 17-1-14.
 */
public class RecentlyConstract {

    @MVProvides(RecentlyPresenter.class)
    public interface Presenter extends IPresenter{
        void requestMyStock();
    }

    public interface View extends IView {
        /**
         * 调用网络接口成功成功
         * @param result
         */
        void requesetSuccess(Object result);

        /**
         * 调用网络接口失败
         */
        void requestFail();
    }
}
