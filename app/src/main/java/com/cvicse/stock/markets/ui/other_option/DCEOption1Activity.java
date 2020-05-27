package com.cvicse.stock.markets.ui.other_option;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.cvicse.annotations.MVPInject;
import com.cvicse.base.utils.ToastUtils;
import com.cvicse.stock.R;
import com.cvicse.stock.base.PBaseActivity;
import com.cvicse.stock.base.ToolBar;
import com.cvicse.stock.markets.adapter.OptionListAdapter;
import com.cvicse.stock.markets.ui.SHQQActivity;
import com.cvicse.stock.markets.ui.StockDetailActivity;
import com.cvicse.stock.markets.ui.other_option.adapter.OtherOptionListAdapter;
import com.cvicse.stock.markets.ui.other_option.presenter.contract.DCEOption1Contract;
import com.cvicse.stock.pulltorefresh.PullToRefreshBase;
import com.cvicse.stock.pulltorefresh.PullToRefreshListView;
import com.cvicse.stock.seachstock.SearchHistoryActivity;
import com.cvicse.stock.view.CHScrollView;
import com.cvicse.stock.view.HVListView;
import com.mitake.core.QuoteItem;

import java.util.ArrayList;

import butterknife.BindView;

import static com.cvicse.stock.markets.data.ParamUtil.STOCK_NAME_DCEQQ;
import static com.cvicse.stock.markets.data.ParamUtil.STOCK_NAME_ZCEQQ;

/**
 * 大商所、郑商所期权 商品行情页面
 */

public class DCEOption1Activity extends PBaseActivity implements DCEOption1Contract.View {

    @MVPInject
    DCEOption1Contract.Presenter presenter;

    @BindView(R.id.topbar) ToolBar mTopbar;
    @BindView(R.id.item_scroll_title) CHScrollView mItemScrollTitle;
    @BindView(R.id.ptr_list) PullToRefreshListView mPtrList;

    private OtherOptionListAdapter adapter;

    String stockType;// 一级页面标题（大商所期权、郑商所期权）
    static String ID = null;

    public static void actionIntent(Context context, String stocktype, String id) {
        Intent intent = new Intent(context, DCEOption1Activity.class);
        intent.putExtra("stockType", stocktype);
        context.startActivity(intent);
        ID = id;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_other_optionlist;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        stockType = getIntent().getStringExtra("stockType");
        // 标题
        mTopbar.setTitle(stockType);
        adapter = new OtherOptionListAdapter(this);
        ((HVListView)mPtrList.getRefreshableView()).setScrollView(mItemScrollTitle);
        mPtrList.setAdapter(adapter);
        mPtrList.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                presenter.cateSortingRequest(stockType, ID);
                mPtrList.onRefreshComplete();
            }
        });
        mPtrList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position >= 1){
                    ArrayList<QuoteItem> arrayList = new ArrayList<>();
                    arrayList.add(adapter.getItem(position - 1));
                    StockDetailActivity.startActivity(DCEOption1Activity.this,arrayList,0);
                }
            }
        });
        // 左侧名称点击事件监听类
        adapter.setNameItemClick(new OtherOptionListAdapter.NameItemClick() {
            @Override
            public void onNameItemClick(int position) {
                // 如果是大商所期权或郑商所期权
                QuoteItem quoteItem = adapter.getItem(position);
                if (stockType.equals(STOCK_NAME_DCEQQ) || stockType.equals(STOCK_NAME_ZCEQQ)) {
                    DCEOption1Activity.actionIntent(DCEOption1Activity.this, quoteItem.name, quoteItem.id);
                } else {
                    //获取当前点击项的id
                    SHQQActivity.actionIntent(DCEOption1Activity.this, quoteItem);
                }
            }
        });
        mTopbar.setToolBarClickListener(new ToolBar.ToolBarClickListener() {
            @Override
            public void onClick(View view, ToolBar.Type type) {
                switch (type){
                    case RIGHT_TYPE_1:
                        SearchHistoryActivity.startActivity(DCEOption1Activity.this);
                        break;
                }
            }
        });
    }

    @Override
    protected void initData() {
        presenter.cateSortingRequest(stockType, ID);
    }

    /**
     * 请求期权标的证券列表成功
     */
    @Override
    public void cateSortingRequestSuccess(ArrayList<QuoteItem> list) {
        mPtrList.onRefreshComplete();
        if(list !=null && list.size() > 0){
            adapter.setData(list);
        }

    }

    /**
     * 请求期权标的证券列表失败
     */
    @Override
    public void cateSortingRequestFail(String message) {
        ToastUtils.showLongToast(message);
    }

}
