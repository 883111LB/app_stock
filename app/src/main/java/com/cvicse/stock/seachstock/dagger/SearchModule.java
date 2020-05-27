package com.cvicse.stock.seachstock.dagger;

import com.cvicse.base.dagger.BaseModule;
import com.cvicse.base.mvp.IView;
import com.cvicse.stock.seachstock.SearchPresenter;
import com.cvicse.stock.seachstock.seachConstract.SearchConstract;

import dagger.Module;
import dagger.Provides;

/**
 * Created by liu_zlu on 2016/8/14 19:58
 */
@Module
public class SearchModule extends BaseModule {
    private IView baseView;
    public SearchModule(IView baseView){
        this.baseView = baseView;
    }


    /**
     * 搜索页面
     * @return
     */
    @Provides
    public SearchConstract.Presenter getSearchPresenter(){
        return getPersenter(baseView, SearchPresenter.class);
    }




  /*  *//**
     * 公告页
     * @return
     *//*
    @Provides
    public MyStockTaskConstract.Presenter getMyStockReportPresenter(){
        return getPersenter(baseView,MyStockTaskPresenter.class);
    }*/

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
