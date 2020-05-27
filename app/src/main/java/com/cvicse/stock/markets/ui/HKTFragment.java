package com.cvicse.stock.markets.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ExpandableListView;

import com.cvicse.annotations.MVPInject;
import com.cvicse.stock.R;
import com.cvicse.stock.base.PBaseFragment;
import com.cvicse.stock.markets.adapter.HKTAdapter;
import com.cvicse.stock.markets.helper.HKTAmountHelper;
import com.cvicse.stock.markets.presenter.contract.HKTContract;
import com.cvicse.stock.markets.ui.hkt.HKTAvtivity;
import com.cvicse.stock.pulltorefresh.PullToRefreshBase;
import com.cvicse.stock.pulltorefresh.PullToRefreshExpandableListView;
import com.mitake.core.QuoteItem;
import com.mitake.core.bean.HSAmountItem;
import com.mitake.core.response.HKMarInfoResponse;

import java.util.ArrayList;

import butterknife.BindView;


/**
 * 港股通页面
 * Created by ding_syi on 17-1-24.
 */
public class HKTFragment extends PBaseFragment implements HKTContract.View {
    @MVPInject
    HKTContract.Presenter presenter;

    // 港股通列表
    @BindView(R.id.explsv_htk)
    PullToRefreshExpandableListView explsvHtk;

    // 二级适配器
    private HKTAdapter hktAdapter;
    private HKTAmountHelper mHKTAmountHelper;  // 每日额度帮助类

    public static HKTFragment newInstance() {
        return new HKTFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_hkt;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        View headerView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_hkt_header,
                explsvHtk, false);

        headerView.setLayoutParams(new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        explsvHtk.getRefreshableView().addHeaderView(headerView);
        mHKTAmountHelper = new HKTAmountHelper(headerView);

        hktAdapter = new HKTAdapter(getActivity());
        explsvHtk.getRefreshableView().setGroupIndicator(null);
        explsvHtk.getRefreshableView().setAdapter(hktAdapter);

        //设置自动展开第一行
        explsvHtk.getRefreshableView().expandGroup(0);
        explsvHtk.getRefreshableView().setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                presenter.requestHKT(groupPosition);
                return false;
            }
        });
        explsvHtk.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ExpandableListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ExpandableListView> refreshView) {
                presenter.requestRefesh();
                explsvHtk.onRefreshComplete();

            }
        });
        //监听每个分组里子控件的点击事件
        explsvHtk.getRefreshableView().setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                StockDetailActivity.startActivity(getActivity(), (ArrayList<QuoteItem>) hktAdapter.getGroup(groupPosition), childPosition);
                return false;
            }
        });

        headerView.findViewById(R.id.frl_amount_more).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), HKTAvtivity.class));
            }
        });
    }

    @Override
    protected void initData() {

    }

    /**
     * 请求港股通成功
     */
    @Override
    public void onRequestHKTSuccess(ArrayList<QuoteItem> quoteItems, int position) {
        explsvHtk.onRefreshComplete();
        hktAdapter.setData(quoteItems, position);
    }

    /**
     * 请求港股失败
     */
    @Override
    public void onRequestHTKFail() {

    }

    /**
     * 请求港股通(沪)余额数据成功
     */
    @Override
    public void requestBalanceHKTSuccess(HKMarInfoResponse response) {
        mHKTAmountHelper.setHKMarInfoData(response);
    }

    /**
     * 请求港股通余额数据失败
     */
    @Override
    public void requestBalanceHKTFail() {

    }

    /**请求沪深股通额度
     *
     * @param hsAmountItem
     */
    @Override
    public void requestHSAmountSuccess(HSAmountItem hsAmountItem) {
        mHKTAmountHelper.setHSAmount(hsAmountItem);
    }

    @Override
    public void requestHSAmountFail() {

    }
}
