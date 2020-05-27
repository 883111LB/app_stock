package com.cvicse.stock.markets.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.cvicse.stock.R;
import com.cvicse.stock.base.PBaseActivity;
import com.cvicse.stock.base.ToolBar;
import com.cvicse.stock.common.Setting.Setting;
import com.cvicse.stock.markets.adapter.StockMoreDetailAdapter;
import com.cvicse.stock.seachstock.SearchHistoryActivity;
import com.cvicse.stock.utils.StockType;
import com.mitake.core.CateType;
import com.mitake.core.QuoteItem;

import butterknife.BindView;

/**
 * 行情更多信息页面
 * Created by liuzilu on 2017/3/6.
 */

public class StockMoreDetailActivity extends PBaseActivity {

    private static final String QUOTEITEM = "QuoteItem";
    private static final String SELECTED = "selected";

    public static void startActivity(Activity activity,QuoteItem quoteItem, int selected){
        Intent intent = new Intent(activity,StockMoreDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable(QUOTEITEM,quoteItem);
        intent.putExtra(QUOTEITEM,bundle);
        intent.putExtra(SELECTED,selected);
        activity.startActivity(intent);
    }
    @BindView(R.id.topbar) ToolBar topbar;
    @BindView(R.id.tabLayout) TabLayout tabLayout;

    @BindView(R.id.vp_stockdetail) ViewPager vpStockdetail;

    //level 2状态下的
    private String[] tabs_l2 = {"十档","委托队列","逐笔明细","分价", "分量"};

    // level 2状态下的带分量的
//    private String[] tabs_l2fl = {"十档","委托队列","逐笔明细","分价", "分量"};

    private String[] tabs_l2_hk = {"十档","经纪席位","逐笔明细","分价"};

    private String[] tabs_l1 = {"成交明细","分价"};

    private String tabs[];

    private StockMoreDetailAdapter stockMoreDetailAdapter;

    private QuoteItem quoteItem;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_stock_more_detai;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        quoteItem = (QuoteItem)getIntent().getBundleExtra(QUOTEITEM).getParcelable(QUOTEITEM);
        if(Setting.isLevel1()){
            tabs = tabs_l1;
        } else {
            if(StockType.getType(quoteItem).isHongKong()){
                tabs = tabs_l2_hk;
            } else {
                tabs = tabs_l2;
            }
        }
        stockMoreDetailAdapter = new StockMoreDetailAdapter(getSupportFragmentManager(),
                quoteItem,tabs);

        vpStockdetail.setAdapter(stockMoreDetailAdapter);

        int position = getIntent().getIntExtra(SELECTED,0) % tabs.length;

        tabLayout.setupWithViewPager(vpStockdetail);

        vpStockdetail.setCurrentItem(position);
        if(quoteItem == null){
            return;
        }

        String name = null == quoteItem.name ? "" : quoteItem.name;
        String id =  null ==  quoteItem.id ? "" : quoteItem.id;
        topbar.setTitle(name +"("+ id +")");
        //标题栏的返回事件
        topbar.setToolBarClickListener(new ToolBar.ToolBarClickListener() {
            @Override
            public void onClick(View view, ToolBar.Type type) {
                switch (type){
                    case RIGHT_TYPE_1:
                        SearchHistoryActivity.startActivity(StockMoreDetailActivity.this);
                        break;
                }
            }
        });
    }

    @Override
    protected void initData() {
    }
}
