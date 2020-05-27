package com.cvicse.stock.portfolio.dagger;

import com.cvicse.base.dagger.BaseModule;
import com.cvicse.base.mvp.IView;
import com.cvicse.stock.portfolio.presenter.DetailPresenter;
import com.cvicse.stock.portfolio.presenter.EditPresenter;
import com.cvicse.stock.portfolio.presenter.NewsPresenter;
import com.cvicse.stock.portfolio.presenter.NoticePresenter;
import com.cvicse.stock.portfolio.presenter.MianPresenter;
import com.cvicse.stock.portfolio.presenter.RecentlyPresenter;
import com.cvicse.stock.portfolio.presenter.ReportPresenter;
import com.cvicse.stock.portfolio.presenter.constract.MianConstract;
import com.cvicse.stock.portfolio.presenter.constract.DetailConstract;
import com.cvicse.stock.portfolio.presenter.constract.EditConstract;
import com.cvicse.stock.portfolio.presenter.constract.NewsConstract;
import com.cvicse.stock.portfolio.presenter.constract.NoticeConstract;
import com.cvicse.stock.portfolio.presenter.constract.RecentlyConstract;
import com.cvicse.stock.portfolio.presenter.constract.ReportConstract;

import dagger.Module;
import dagger.Provides;

/**
 * Created by liu_zlu on 2016/8/14 19:58
 */
@Module
public class MyStockModule extends BaseModule {
    private IView baseView;
    public MyStockModule(IView baseView){
        this.baseView = baseView;
    }


    /**
     * 自选主页
     * @return
     */
    @Provides
    public MianConstract.Presenter getMyStockPresenter(){
        return getPersenter(baseView, MianPresenter.class);
    }

    /**
     * 新闻页
     * @return
     */
    @Provides
    public NewsConstract.Presenter getMyStockNewsPresenter(){
        return getPersenter(baseView,NewsPresenter.class);
    }

    /**
     * 公告页
     * @return
     */
    @Provides
    public NoticeConstract.PresenterNotice getMyStockNoticePresenter(){
        return getPersenter(baseView,NoticePresenter.class);
    }

    /**
     * 报告页
     * @return
     */
    @Provides
    public ReportConstract.Presenter getMyStockReportPresenter(){
        return getPersenter(baseView,ReportPresenter.class);
    }


    /**
     * 最近浏览
     * @return
     */
    @Provides
    public RecentlyConstract.Presenter getMyStockRecentlyDetailPresenter(){
        return getPersenter(baseView, RecentlyPresenter.class);
    }


    /**
     * 编辑
     * @return
     */
    @Provides
    public EditConstract.Presenter getMyStockEditPresenter(){
        return getPersenter(baseView, EditPresenter.class);
    }



  /**
     * 公告页
     * @return
     *//*


 /*   *//**
     * 提醒
     * @return
     *//*
    @Provides
    public NotifyContract.Presenter getNotifyPresenter(){
        NotifyPresenter presenter;
        presenter = getPersenter(baseView,NotifyPresenter.class,NotifyContract.View.class);
        return create(presenter,NotifyContract.Presenter.class);
    }

    *//**
     * 任务跟踪
     * @return
     *//*
    @Provides
    public TaskTrackingContract.Presenter getTaskTrackingPresenter() {
        TaskTrackingPresenter presenter;
        presenter = getPersenter(baseView, TaskTrackingPresenter.class, TaskTrackingContract.View.class);
        return create(presenter, TaskTrackingContract.Presenter.class);
    }
    *//**
     * 任务跟踪详情
     * @return
     *//*
    @Provides
    public TaskTrackingDetailContract.Presenter getTaskTrackingDetailPresenter() {
        TaskTrackingDetailPresenter presenter;
        presenter = getPersenter(baseView, TaskTrackingDetailPresenter.class, TaskTrackingDetailContract.View.class);
        return create(presenter, TaskTrackingDetailContract.Presenter.class);
    }
    *//**
     * 历史任务
     * @return
     *//*
    @Provides
    public HistoryTaskContract.Presenter getHistoryTaskPresenter() {
        HistoryTaskPresenter presenter;
        presenter = getPersenter(baseView, HistoryTaskPresenter.class, HistoryTaskContract.View.class);
        return create(presenter, HistoryTaskContract.Presenter.class);
    }
    *//**
     * 历史任务详情
     * @return
     *//*
    @Provides
    public HistoryTaskDetailContract.Presenter getHistoryTaskDetailPresenter() {
        HistoryTaskDetailPresenter presenter;
        presenter = getPersenter(baseView, HistoryTaskDetailPresenter.class, HistoryTaskDetailContract.View.class);
        return create(presenter, HistoryTaskDetailContract.Presenter.class);
    }*/
}
