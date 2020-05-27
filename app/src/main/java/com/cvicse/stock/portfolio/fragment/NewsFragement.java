package com.cvicse.stock.portfolio.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.cvicse.annotations.MVPInject;
import com.cvicse.stock.R;
import com.cvicse.stock.base.PBaseFragment;
import com.cvicse.stock.portfolio.activity.StockDetailActivity;
import com.cvicse.stock.portfolio.adapter.NewsAdapter;
import com.cvicse.stock.portfolio.presenter.constract.NewsConstract;
import com.cvicse.stock.pulltorefresh.PullToRefreshBase;
import com.cvicse.stock.pulltorefresh.PullToRefreshListView;
import com.mitake.core.StockNewsItem;

import java.util.List;

import butterknife.BindView;

/**
 * 自选新闻
 * Created by ding_syi on 17-1-6.
 */
public class NewsFragement extends PBaseFragment implements NewsConstract.View {
    @MVPInject
    NewsConstract.Presenter presenter;
    NewsAdapter myStockAdpater;
    @BindView(R.id.pull_to_refresh_list)
    PullToRefreshListView mPullToRefreshList;

    @BindView(R.id.tev_content)
    TextView mTevContent;

    /**
     * 初始化NewsFragement
     *
     * @return
     */
    public static NewsFragement newInstance() {
        NewsFragement newsFragement = new NewsFragement();
        return newsFragement;
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mystock_news;
    }


    @Override
    protected void initViews(Bundle savedInstanceState) {
        View view = getView();
        mPullToRefreshList = (PullToRefreshListView) view.findViewById(R.id.pull_to_refresh_list);

        mPullToRefreshList.setMode(PullToRefreshBase.Mode.BOTH);//两端刷新

        myStockAdpater = new NewsAdapter(getActivity());
        mPullToRefreshList.setAdapter(myStockAdpater);

        mPullToRefreshList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                StockNewsItem myStockBean = (StockNewsItem) myStockAdpater.getItem(position-1);
                StockDetailActivity.actionIntent(getActivity(), myStockBean.ID_, myStockBean.REPORTTITLE_,
                        myStockBean.INIPUBDATE_,myStockBean.MEDIANAME_, StockDetailActivity.TYPE_NEWS);
            }
        });


        mPullToRefreshList.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                presenter.requestNews();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                presenter.requestNewsMore();
            }
        });
    }


    @Override
    protected void initData() {
        presenter.requestNews();
    }


    @Override
    public void showLoading(int time) {

    }

    @Override
    public void stopLoading() {

    }

    @Override
    public void showDialog() {

    }

    @Override
    public void hideDialog() {

    }

    @Override
    public void newsSuccess(Object result) {
        mPullToRefreshList.onRefreshComplete();
        if (result != null) {
            List<StockNewsItem> stockNewsItems = (List<StockNewsItem>) result;
            if(stockNewsItems.size()!=0){
                mPullToRefreshList.setVisibility(View.VISIBLE);
                mTevContent.setVisibility(View.GONE);
                myStockAdpater.setData((List<StockNewsItem>) result);
            }else{
                showMessage();
            }
        } else {
            showMessage();
        }
    }

    @Override
    public void newsFail() {
        mPullToRefreshList.onRefreshComplete();
        showMessage();
    }

    /**
     * 没有数据时，显示提示信息
     */
    void showMessage(){
        mPullToRefreshList.onRefreshComplete();
        mTevContent.setVisibility(View.VISIBLE);
        mTevContent.setText("暂无新闻");
        mPullToRefreshList.setVisibility(View.GONE);
    }

}
