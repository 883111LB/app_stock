package com.cvicse.stock.news.dagger;

import com.cvicse.base.dagger.BaseModule;
import com.cvicse.base.mvp.IView;
import com.cvicse.stock.news.presenter.NewsDetailPresenter;
import com.cvicse.stock.news.presenter.NewsPresenter;
import com.cvicse.stock.news.presenter.NewsViewPagerPresenter;
import com.cvicse.stock.news.presenter.contract.NewsContract;
import com.cvicse.stock.news.presenter.contract.NewsDetailContract;
import com.cvicse.stock.news.presenter.contract.NewsViewPagerContract;

import dagger.Module;
import dagger.Provides;

/**
 * Created by ruan_ytai on 17-1-7.
 */
@Module
public class NewsModule extends BaseModule {

    private IView view;

    public NewsModule(IView view) {
        this.view = view;
    }

    /**
     * 资讯主页面
     */
    @Provides
    public NewsContract.Presenter getNewPresenter() {
        //NewsPresenter presenter;
        return getPersenter(view,NewsPresenter.class);
    }

    /**
     * 资讯列表页面
     */
    @Provides
    public NewsViewPagerContract.Presenter getNewsViewPagerPresenter() {
        //NewsPresenter presenter;
        return getPersenter(view,NewsViewPagerPresenter.class);
    }

    /**
     * 资讯详情页面
     */
    @Provides
    public NewsDetailContract.Presenter getNewsDetailPresenter(){
        return getPersenter(view,NewsDetailPresenter.class);
    }
}
