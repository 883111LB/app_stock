package com.cvicse.stock.portfolio.presenter.constract;

import com.cvicse.annotations.MVProvides;
import com.cvicse.base.mvp.IPresenter;
import com.cvicse.base.mvp.IView;
import com.cvicse.stock.portfolio.presenter.NoticePresenter;

/**
 * Created by ding_syi on 17-1-12.
 */
public class NoticeConstract {

    @MVProvides(NoticePresenter.class)
    public interface PresenterNotice extends IPresenter<View> {
        void pullToRefreshNotice();
        void pullToLoadNotice();
    };

    public interface View extends IView {
        /**
         * 调用网络接口成功成功
         * @param result
         */
        void noticeSuccess(Object result);

        /**
         * 调用网络接口失败
         */
        void noticeFail();
    }
}
