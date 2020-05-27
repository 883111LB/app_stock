package com.cvicse.stock.news.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import com.cvicse.annotations.MVPInject;
import com.cvicse.stock.R;
import com.cvicse.stock.base.PBaseFragment;
import com.cvicse.stock.http.ResponseCallback;
import com.cvicse.stock.news.adapter.NewsListAdapter;
import com.cvicse.stock.news.presenter.contract.NewsViewPagerContract;
import com.cvicse.stock.pulltorefresh.PullToRefreshBase;
import com.cvicse.stock.pulltorefresh.PullToRefreshListView;
import com.mitake.core.NewsList;
import com.mitake.core.request.NewsListRequest;
import com.mitake.core.request.NewsType;
import com.mitake.core.response.NewsListResponse;
import com.mitake.core.response.Response;

import java.util.ArrayList;

import butterknife.BindView;

import static com.cvicse.stock.news.presenter.NewsViewPagerPresenter.UPDATE_TYPE_DEFUALT;
import static com.cvicse.stock.news.presenter.NewsViewPagerPresenter.UPDATE_TYPE_NEWEST;
import static com.cvicse.stock.news.presenter.NewsViewPagerPresenter.UPDATE_TYPE_OLD;

/**
 * Created by ruan_ytai on 16-12-28.
 */

public class NewsViewPagerFragment extends PBaseFragment implements NewsViewPagerContract.View,
        AdapterView.OnItemClickListener,PullToRefreshBase.OnRefreshListener2 {

    private static final String KEY_NEWS_TYPE = "type";

    @MVPInject
    NewsViewPagerContract.Presenter presenter;

    @BindView(R.id.lsv_news)
    PullToRefreshListView lsv_news;
    private NewsListAdapter mNewsAdapter;

    private String type;

    /**
     * 数据有无的标志
     */
    private boolean isHasData = false;

    public static NewsViewPagerFragment newInstance(String type) {
        Bundle bundle = new Bundle();
        bundle.putString(KEY_NEWS_TYPE,type);
        NewsViewPagerFragment newsViewPagerFragment = new NewsViewPagerFragment();
        newsViewPagerFragment.setArguments(bundle);
        return newsViewPagerFragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_newsviewpager;
    }


    @Override
    protected void initViews(Bundle savedInstanceState) {
        type = getArguments().getString(KEY_NEWS_TYPE);

        mNewsAdapter = new NewsListAdapter(getActivity(),type);
        lsv_news.setAdapter(mNewsAdapter);
        lsv_news.setOnItemClickListener(this);
        lsv_news.setOnRefreshListener(this);
    }

    @Override
    protected void initData() {
        presenter.init(type);
        presenter.requestNewsList(UPDATE_TYPE_DEFUALT,null);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if(position > 0){
            //PullToRefreshListView 有头部，position从1开始
            NewsList news = mNewsAdapter.getItem(position-1);
            boolean isHasImage = type.equals(NewsType.NewsTypeImportant);
            NewsDetailActivity.actionIntent(getActivity(),news,isHasImage);
        }
    }


    /**
     * 请求新闻列表数据成功，包括下拉刷新请求数据回调
     */
    @Override
    public void onRequestSuccess(ArrayList<NewsList> newsList) {
        lsv_news.onRefreshComplete();
        if(newsList != null && newsList.size()>0){
            mNewsAdapter.setData(newsList);
            isHasData = true;
        }
    }

    /**
     * 获取新闻列表失败
     */
    @Override
    public void onRequestFail() {

    }

    /**
     * 上拉加载新闻列表数据成功
     *
     * @param list
     */
    @Override
    public void onRequestPullUpNewsListSuccess(ArrayList<NewsList> list) {
        lsv_news.onRefreshComplete();
        if(list != null && list.size()>0){
            mNewsAdapter.setData(list);
        }
    }

    /**
     * 上拉加载新闻列表数据失败
     *
     */
    @Override
    public void onRequestPullUpNewsListFail() {

    }

    /**
     * 下拉刷新
     * 最上面一位资讯的id
     */
    @Override
    public void onPullDownToRefresh(PullToRefreshBase refreshView) {
        if(isHasData){
            String id = mNewsAdapter.getData().get(0).ID_;
            presenter.requestNewsList(UPDATE_TYPE_NEWEST,id);
        }else{
            presenter.requestNewsList(UPDATE_TYPE_DEFUALT,null);
        }
        lsv_news.onRefreshComplete();

    }

    /**
     * 上拉加载
     */
    @Override
    public void onPullUpToRefresh(PullToRefreshBase refreshView) {
        if(isHasData){
            String id = mNewsAdapter.getItem(mNewsAdapter.getCount()-1).ID_;
            presenter.requestPullUpNewsList(UPDATE_TYPE_OLD,id);
        }else{
            presenter.requestNewsList(UPDATE_TYPE_DEFUALT,null);
        }
        lsv_news.onRefreshComplete();
    }

    private void netRequest(){
        NewsListRequest newListRequest = new NewsListRequest();
        //stock ="000",updateType,-1,5,6
        newListRequest.send("0000", UPDATE_TYPE_OLD,"84993884936" , new ResponseCallback(newListRequest) {
            @Override
            public void onBack(Response response) {
                NewsListResponse newsListResponse = (NewsListResponse) response;
                ArrayList<NewsList> list = newsListResponse.list;
                for(int i=0;i<list.size();i++){
                }
                //view.onRequestSuccess(newsList);
            }

            @Override
            public void onError(int i, String s) {

            }
        });
    }

}
