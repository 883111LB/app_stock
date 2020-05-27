package com.cvicse.stock.markets.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.cvicse.base.utils.ToastUtils;
import com.cvicse.stock.R;
import com.cvicse.stock.base.PBaseActivity;
import com.cvicse.stock.base.ToolBar;
import com.cvicse.stock.chart.theme.ThemeManager;
import com.cvicse.stock.markets.adapter.StockDetailAdapter;
import com.cvicse.stock.markets.helper.StockToolBarHelper;
import com.cvicse.stock.seachstock.SearchHistoryActivity;
import com.cvicse.stock.utils.MyBrowseUtils;
import com.cvicse.stock.utils.MyStocksUtils;
import com.mitake.core.QuoteItem;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * 股票ViewPager展示页面
 * Created by liu_zlu on 2017/1/15 14:49
 */
public class StockDetailActivity extends PBaseActivity {

    private static String POSITION = "position";
    @BindView(R.id.toolbar) ToolBar toolbar;
    @BindView(R.id.vp_stockdetail) ViewPager vpStockdetail;
    StockDetailAdapter stockDetailAdapter;
    private static ArrayList<QuoteItem> quoteItems;

    private StockToolBarHelper stockToolBarHelper;

    /**
     * -2 默认状态，如果为基金展示基金净值图，其他分时图
     */
    public static int type = -2;

    public static void startActivity(Activity activity, ArrayList<QuoteItem> quoteItems, int position) {
        Intent intent = new Intent(activity, StockDetailActivity.class);
        StockDetailActivity.quoteItems = quoteItems;
        intent.putExtra(POSITION, position);
        activity.startActivity(intent);
        activity.overridePendingTransition(0,0);
    }

    @Override
    protected int getLayoutId() {
        ThemeManager.init();
        return R.layout.activity_stockdetail;
    }

    @Override
    protected void initViews(final Bundle savedInstanceState) {
        initDelayedViews(savedInstanceState);
    }

    private void initDelayedViews(Bundle savedInstanceState) {
        ArrayList<QuoteItem> quoteItems = StockDetailActivity.quoteItems;

        StockDetailActivity.quoteItems = null;
        if( null == quoteItems || quoteItems.isEmpty() || null == quoteItems.get(0)){
            finish();
            return;
        }
        stockDetailAdapter = new StockDetailAdapter(getSupportFragmentManager(), quoteItems);
        ViewGroup viewGroup = (ViewGroup) toolbar.setCenterId(R.layout.layout_toolbar_stock_center);
        stockToolBarHelper = new StockToolBarHelper(viewGroup,vpStockdetail,quoteItems);

        toolbar.setToolBarClickListener(new ToolBar.ToolBarClickListener() {
            @Override
            public void onClick(View view, ToolBar.Type type) {
                switch (type){
                    case RIGHT_TYPE_1:
                        QuoteItem quoteItem = stockDetailAdapter.getItemData(getSelected());
                        if(quoteItem == null){
                            return;
                        }
                        if(view.isSelected()){
                            view.setSelected(false);
                            MyStocksUtils.removeSelect(quoteItem.id,quoteItem.name);
                            ToastUtils.showLongToast("取消成功！");
                        } else {
                            MyStocksUtils.saveSelect(quoteItem.id,quoteItem.name);
                            view.setSelected(true);
                            ToastUtils.showLongToast("添加成功！");
                        }
                        break;
                    case RIGHT_TYPE_2:
                        SearchHistoryActivity.startActivity(StockDetailActivity.this);
                        break;
                }
            }
        });

        vpStockdetail.setAdapter(stockDetailAdapter);
        vpStockdetail.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                MyBrowseUtils.saveBrowse(stockDetailAdapter.getItemData(position).id);
                if(MyStocksUtils.contains(stockDetailAdapter.getItemData(position).id)){
                    toolbar.getRight1().setSelected(true);
                } else {
                    toolbar.getRight1().setSelected(false);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        int position = getIntent().getIntExtra(POSITION, 0);

        vpStockdetail.setCurrentItem(position);
        QuoteItem quoteItem = stockDetailAdapter.getItemData(position);
        if(quoteItem != null && MyStocksUtils.contains(quoteItem.id)){
            toolbar.getRight1().setSelected(true);
        }
        stockToolBarHelper.setSelected(position);
    }

    public StockToolBarHelper getStockToolBarHelper() {
        return stockToolBarHelper;
    }

    /**
     *
     * @return
     */
    public int getSelected(){
        return vpStockdetail.getCurrentItem();
    }


    @Override
    protected void initData() {
        if(stockDetailAdapter != null && stockDetailAdapter.getItemData(getSelected()) != null){
            MyBrowseUtils.saveBrowse(stockDetailAdapter.getItemData(getSelected()).id);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        type = -2;
    }
}
