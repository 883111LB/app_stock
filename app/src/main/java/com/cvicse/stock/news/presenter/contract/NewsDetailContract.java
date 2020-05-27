package com.cvicse.stock.news.presenter.contract;

import com.cvicse.annotations.MVProvides;
import com.cvicse.base.mvp.IPresenter;
import com.cvicse.base.mvp.IView;
import com.cvicse.stock.news.presenter.NewsDetailPresenter;

/**
 * Created by ruan_ytai on 17-1-9.
 */

public class NewsDetailContract {

    public interface View extends IView{
        /**
         * 获取新闻详情成功
         */
         void onRequestSuccess(String content);

        /**
         * 获取新闻详情失败
         */
         void  onRequestFail();
    }

    @MVProvides(NewsDetailPresenter.class)
    public interface Presenter extends IPresenter<View>{

        /**
         * 请求新闻内容数据
         */
         void requestNewsDetail(String newsId);

    }
}
