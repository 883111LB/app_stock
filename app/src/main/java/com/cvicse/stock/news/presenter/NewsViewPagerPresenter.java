package com.cvicse.stock.news.presenter;

import com.cvicse.base.mvp.BasePresenter;
import com.cvicse.stock.http.ResponseCallback;
import com.cvicse.stock.news.presenter.contract.NewsViewPagerContract;
import com.mitake.core.NewsList;
import com.mitake.core.request.NewsListRequest;
import com.mitake.core.response.NewsListResponse;
import com.mitake.core.response.Response;

import java.util.ArrayList;

/**
 * Created by ruan_ytai on 17-1-9.
 */

public class NewsViewPagerPresenter  extends BasePresenter implements NewsViewPagerContract.Presenter{

    private String type ;

    public static final int UPDATE_TYPE_DEFUALT = -1;
    public static final int UPDATE_TYPE_NEWEST = 6;
    public static final int UPDATE_TYPE_OLD = 5;

    private NewsViewPagerContract.View view;

    /**
     *  第一次进来的数据，或者下拉刷新的基础数据
     */
    private  ArrayList<NewsList> newsList;

    /**
     * 初始化fragment类型
     */
    @Override
    public void init(String type){
        this.type = type;
    }

    /**
     * 请求新闻列表数据，包括下拉刷新
     * String stockId 要闻，滚动，财经
     * int updateType -1(请求最新数据),5+NewsID 请求以前的数据，6+NewsID请求最新的数据
     * String newsID
     */
    @Override
    public void requestNewsList(int updateType,String newsID) {
        NewsListRequest newListRequest = new NewsListRequest();
        newListRequest.send(type, updateType, newsID, new ResponseCallback(newListRequest) {
            @Override
            public void onBack(Response response) {
                NewsListResponse newsListResponse = (NewsListResponse) response;
                ArrayList<NewsList> list = newsListResponse.list;
                if(list != null && list.size()>0 ){
                    newsList = list;
                }
                view.onRequestSuccess(list);
            }

            @Override
            public void onError(int i, String s) {
                view.onRequestFail();
            }
        });
    }

    /**
     * 上拉加载新闻列表数据
     *
     * @param updateType
     * @param newsID
     */
    @Override
    public void requestPullUpNewsList(int updateType, String newsID) {
        NewsListRequest newListRequest = new NewsListRequest();
        newListRequest.send(type, updateType, newsID, new ResponseCallback(newListRequest) {
            @Override
            public void onBack(Response response) {
                NewsListResponse newsListResponse = (NewsListResponse) response;
                ArrayList<NewsList> list = newsListResponse.list;
                if(list != null && list.size() > 0){
                    newsList.addAll(list);
                    view.onRequestPullUpNewsListSuccess(newsList);
                }else{
                    view.onRequestPullUpNewsListSuccess(list);
                }

            }

            @Override
            public void onError(int i, String s) {
                view.onRequestPullUpNewsListFail();
            }
        });

    }

    @Override
    public void setView(NewsViewPagerContract.View view) {
        this.view = view;
    }


}
