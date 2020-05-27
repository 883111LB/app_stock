package com.cvicse.stock.portfolio.presenter.constract;

import com.cvicse.annotations.MVProvides;
import com.cvicse.base.mvp.IPresenter;
import com.cvicse.base.mvp.IView;
import com.cvicse.stock.portfolio.presenter.NewsPresenter;

/**
 * Created by ding_syi on 17-1-12.
 */
public class NewsConstract {

    @MVProvides(NewsPresenter.class)
    public interface Presenter extends IPresenter {
        void requestNews();
        void requestNewsMore();
    };

    public interface View extends IView {
        /**
         * 调用网络接口成功成功
         * @param result
         */
        void newsSuccess(Object result);

        /**
         * 调用网络接口失败
         */
        void newsFail();
    }
}
