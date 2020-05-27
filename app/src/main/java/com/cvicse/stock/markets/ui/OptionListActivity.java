package com.cvicse.stock.markets.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.cvicse.annotations.MVPInject;
import com.cvicse.stock.R;
import com.cvicse.stock.base.PBaseActivity;
import com.cvicse.stock.base.ToolBar;
import com.cvicse.stock.markets.adapter.OptionListAdapter;
import com.cvicse.stock.markets.presenter.contract.OptionListContract;
import com.cvicse.stock.pulltorefresh.PullToRefreshBase;
import com.cvicse.stock.pulltorefresh.PullToRefreshListView;
import com.cvicse.stock.seachstock.SearchHistoryActivity;
import com.cvicse.stock.view.CHScrollView;
import com.cvicse.stock.view.HVListView;
import com.mitake.core.QuoteItem;

import java.util.ArrayList;

import butterknife.BindView;

import static com.cvicse.stock.markets.data.ParamUtil.STOCK_NAME_SHQQ;

/**
 * Created by ruan_ytai on 17-3-17.
 * 期权 商品行情页面
 */

public class OptionListActivity extends PBaseActivity implements OptionListContract.View {

    @MVPInject
    OptionListContract.Presenter presenter;

    @BindView(R.id.topbar) ToolBar mTopbar;
    @BindView(R.id.item_scroll_title) CHScrollView mItemScrollTitle;
    @BindView(R.id.ptr_list) PullToRefreshListView mPtrList;

    private OptionListAdapter adapter;

    static String stockType;

    public static void actionIntent(Context context, String stocktype) {
        Intent intent = new Intent(context, OptionListActivity.class);
        context.startActivity(intent);
        stockType = stocktype;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_optionlist;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        // 标题
        if (stockType.equals(STOCK_NAME_SHQQ)) {
            mTopbar.setTitle("上证期权");
        } else {
            mTopbar.setTitle("深圳期权");
        }
        adapter = new OptionListAdapter(this);
        ((HVListView)mPtrList.getRefreshableView()).setScrollView(mItemScrollTitle);
        mPtrList.setAdapter(adapter);
        mPtrList.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                presenter.requestOptionList(stockType);
                mPtrList.onRefreshComplete();
            }
        });
        mPtrList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position >= 1){
                    //获取当前点击项的id
                    SHQQActivity.actionIntent(OptionListActivity.this,adapter.getItem(position-1));
                }
            }
        });
        mTopbar.setToolBarClickListener(new ToolBar.ToolBarClickListener() {
            @Override
            public void onClick(View view, ToolBar.Type type) {
                switch (type){
                    case RIGHT_TYPE_1:
                        SearchHistoryActivity.startActivity(OptionListActivity.this);
                        break;
                }
            }
        });
    }

    @Override
    protected void initData() {
        presenter.requestOptionList(stockType);
    }

    /**
     * 请求期权标的证券列表成功
     */
    @Override
    public void requestOptionListSuccess(ArrayList<QuoteItem> list) {
        mPtrList.onRefreshComplete();
        if(list !=null && list.size() > 0){
            adapter.setData(list);
        }

    }

    /**
     * 请求期权标的证券列表成功
     */
    @Override
    public void requestOptionListFail() {

    }

}
