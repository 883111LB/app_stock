package com.cvicse.stock.news.dagger;

import com.cvicse.stock.news.ui.NewsDetailActivity;
import com.cvicse.stock.news.ui.NewsFragment;
import com.cvicse.stock.news.ui.NewsViewPagerFragment;

import dagger.Component;

/**
 * Created by ruan_ytai on 17-1-7.
 */
@Component(modules = NewsModule.class)
public interface NewsComponent {


    /**
     * 资讯主页面
     */
    NewsFragment inject(NewsFragment newsFragment);

    /**
     * 资讯列表页面
     */
    NewsViewPagerFragment inject(NewsViewPagerFragment newsFragment);

    /**
     * 资讯详情页面
     */
    NewsDetailActivity inject(NewsDetailActivity newsDetailActivity);
}
