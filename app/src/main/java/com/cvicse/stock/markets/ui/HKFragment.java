package com.cvicse.stock.markets.ui;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ExpandableListView;

import com.cvicse.annotations.MVPInject;
import com.cvicse.stock.R;
import com.cvicse.stock.base.PBaseFragment;
import com.cvicse.stock.markets.adapter.ExplsvAdapter;
import com.cvicse.stock.markets.adapter.StockTopAdapter;
import com.cvicse.stock.markets.presenter.contract.HKContract;
import com.cvicse.stock.pulltorefresh.PullToRefreshBase;
import com.cvicse.stock.pulltorefresh.PullToRefreshExpandableListView;
import com.cvicse.stock.view.DivideViewPager;
import com.mitake.core.QuoteItem;
import com.mitake.core.response.CateSortingResponse;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by ruan_ytai on 17-1-18.
 * 港股Fragment
 */

public class HKFragment extends PBaseFragment implements HKContract.View {

    @MVPInject
    HKContract.Presenter presenter;
    @BindView(R.id.explsv_charts)
    PullToRefreshExpandableListView mExplsvCharts;

    public static final String STOCK_TYPE_HK = "港股";

    private View headerView;
    private DivideViewPager mVipSh;
    private ExplsvAdapter adapter;
    private StockTopAdapter stockTopAdapter;

    public static HKFragment newInstance() {
        return new HKFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_markets_hk;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        initHeadView();
        stockTopAdapter = new StockTopAdapter(getActivity());
        stockTopAdapter.setCount(3);
        mVipSh.setAdapter(stockTopAdapter);

        adapter = new ExplsvAdapter(getContext(), STOCK_TYPE_HK);
        mExplsvCharts.getRefreshableView().setAdapter(adapter);

        initListener();
    }

    private void initListener() {
        mVipSh.setOnItemClickListener(new DivideViewPager.OnItemClickListener() {
            @Override
            public void onItemClick(DivideViewPager parent, View view, int position) {
                StockDetailActivity.startActivity(getActivity(), stockTopAdapter.getData(), position);
            }
        });

        //去掉groupitem中系统自带的下拉箭头图标
        mExplsvCharts.getRefreshableView().setGroupIndicator(null);
        //监听组点击
        mExplsvCharts.getRefreshableView().setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                presenter.requestRankingList(groupPosition);
                return false;
            }
        });

        //监听每个分组里子控件的点击事件
        mExplsvCharts.getRefreshableView().setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                StockDetailActivity.startActivity(getActivity(), adapter.getGroup(groupPosition).list, childPosition);
                return false;
            }
        });

        mExplsvCharts.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ExpandableListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ExpandableListView> refreshView) {
                presenter.requestRefesh();
                mExplsvCharts.onRefreshComplete();

            }
        });
    }

    private void initHeadView() {
        headerView = LayoutInflater.from(getContext()).inflate(R.layout.fragment_markets_hk_header,
                mExplsvCharts, false);
        headerView.setLayoutParams(new AbsListView.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT,
                ActionBar.LayoutParams.WRAP_CONTENT));
        mExplsvCharts.getRefreshableView().addHeaderView(headerView);

        mVipSh = (DivideViewPager) headerView.findViewById(R.id.vip_sh);

    }

    @Override
    protected void initData() {
        //设置自动展开第一行
        mExplsvCharts.getRefreshableView().expandGroup(0);
    }

    /**
     * 请求港股指数成功
     */
    @Override
    public void onRequestIndexSuccess(ArrayList<QuoteItem> result) {
        mExplsvCharts.onRefreshComplete();
        stockTopAdapter.setData(result);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
    }

    /**
     * 请求港股指数失败
     */
    @Override
    public void onRequestIndexFail() {

    }

    /**
     * 请求港股排行榜数据成功
     */
    @Override
    public void onRequestRankingSuccess(CateSortingResponse response, int position) {
        adapter.setData(response, position);
    }

//    @Override
    public void onRequestRankingSuccess(ArrayList<QuoteItem> quoteItems, int position) {
        adapter.setData(quoteItems, position);
    }

    /**
     * 请求港股排行榜数据失败
     */
    @Override
    public void onRequestRankingFail() {

    }
}
