package com.cvicse.stock.portfolio.presenter.constract;

import com.cvicse.annotations.MVProvides;
import com.cvicse.base.mvp.IPresenter;
import com.cvicse.base.mvp.IView;
import com.cvicse.stock.portfolio.presenter.EditPresenter;

/**
 * Created by ding_syi on 17-1-18.
 */
public class EditConstract {

    @MVProvides(EditPresenter.class)
    public interface Presenter extends IPresenter<View> {
        /**
         * 获取自选股票信息
         */
        void getStock();
    };

    public interface View extends IView {
        /**
         * 调用网络接口成功成功
         * @param result
         */
        void getData(Object result);

    }
}
