package com.cvicse.stock.portfolio.dagger;


import com.cvicse.stock.portfolio.PortfolioMianFragment;
import com.cvicse.stock.portfolio.activity.StockDetailActivity;
import com.cvicse.stock.portfolio.activity.StockEditActivity;
import com.cvicse.stock.portfolio.activity.RecentlyActivity;
import com.cvicse.stock.portfolio.activity.ReportActivity;
import com.cvicse.stock.portfolio.fragment.NewsFragement;
import com.cvicse.stock.portfolio.fragment.NoticeFragment;

import dagger.Component;

/**
 * Created by liu_zlu on 2016/8/14 19:57
 */
@Component(modules = {MyStockModule.class})
public interface MyStockComponent {
    /**
     * 自选首页
     *
     * @return
     */
    PortfolioMianFragment inject(PortfolioMianFragment myStocksMainFragment);

    /**
     * 自选新闻页面
     *
     * @return
     */
    NewsFragement inject(NewsFragement loHomePageFragment);


    /**
     * 自选公告页面
     *
     * @return
     */
    NoticeFragment inject(NoticeFragment noticeFragment);

    /**
     * 报告
     * @param reportActivity
     * @return
     */
    ReportActivity inject(ReportActivity reportActivity);


    /**
     * 报告详情
     * @param
     * @return
     */
    StockDetailActivity inject(StockDetailActivity stockDetailActivity);


    /**
     * 最近浏览页面
     * @param
     * @return
     */
     RecentlyActivity inject(RecentlyActivity recentlyActivity);

     StockEditActivity inject(StockEditActivity stockEditActivity);

}