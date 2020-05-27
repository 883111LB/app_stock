package com.cvicse.stock.portfolio.presenter.constract;

import com.cvicse.annotations.MVProvides;
import com.cvicse.base.mvp.IPresenter;
import com.cvicse.base.mvp.IView;
import com.cvicse.stock.portfolio.data.StockNewsBean;
import com.cvicse.stock.portfolio.presenter.DetailPresenter;

/**
 *
 * 公告,新闻及报告详情
 *
 */
public class DetailConstract {

    @MVProvides(DetailPresenter.class)
    public interface PresenterDetail extends IPresenter {
        /**
         * 初始化请求信息
         * @param id 咨询id
         * @param type 展示类型、包括新闻、公告、通知
         */
        void init(String id,String type);

        void queryDetail();
    };

    public interface View extends IView {
        /**
         * 调用网络接口成功成功,返回字符串数据
         * @param result
         */
        void onTastSuccess(StockNewsBean result);

        /**
         * 调用网络接口失败
         */
        void onTaskFail();
    }

}
