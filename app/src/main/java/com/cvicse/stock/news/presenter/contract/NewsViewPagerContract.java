package com.cvicse.stock.news.presenter.contract;

import com.cvicse.annotations.MVProvides;
import com.cvicse.base.mvp.IPresenter;
import com.cvicse.base.mvp.IView;
import com.cvicse.stock.news.presenter.NewsViewPagerPresenter;
import com.mitake.core.NewsList;

import java.util.ArrayList;

/**
 * Created by ruan_ytai on 17-1-9.
 */

public class NewsViewPagerContract {

    public interface View extends IView {
        /**
         * 获取新闻列表成功
         */
        void onRequestSuccess(ArrayList<NewsList> list);

        /**
         * 获取新闻列表失败
         */
        void  onRequestFail();

        /**
         * 上拉加载新闻列表数据成功
         */
        void onRequestPullUpNewsListSuccess(ArrayList<NewsList> list);

        /**
         * 上拉加载新闻列表数据失败
         */
        void onRequestPullUpNewsListFail();

    }

    @MVProvides(NewsViewPagerPresenter.class)
    public interface Presenter extends IPresenter<View> {
        /**
         *初始化fragment类型
         */
        void init(String type);

        /**
         * 请求新闻列表数据
         */
        void requestNewsList(int updateType,String newsID);

        /**
         * 上拉加载新闻列表数据
         */
        void requestPullUpNewsList(int updateType,String newsID);

    }
}
