package com.cvicse.stock.seachstock;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.cvicse.annotations.MVPInject;
import com.cvicse.base.utils.EmptyUtils;
import com.cvicse.stock.R;
import com.cvicse.stock.base.PBaseActivity;
import com.cvicse.stock.base.ToolBar;
import com.cvicse.stock.markets.ui.StockDetailActivity;
import com.cvicse.stock.seachstock.seachConstract.SearchConstract;
import com.cvicse.stock.utils.SearchHistoryUtils;
import com.mitake.core.AppInfo;
import com.mitake.core.QuoteItem;
import com.mitake.core.SearchResultItem;

import java.util.ArrayList;

import butterknife.BindView;


/**
 * 股票搜索页面
 */
public class SearchHistoryActivity extends PBaseActivity implements SearchConstract.View{
    //搜索结果列表
    @BindView(R.id.lsv_search) ListView lstSearch;
    //搜索框
    @BindView(R.id.edt_serach) SerachEditView edtSearch;
    // 搜索选中历史
    @BindView(R.id.lel_history)LinearLayout lelHistory;
    // 历史结果列表
    @BindView(R.id.lsv_search_history) ListView lstHistory;
    //头部
    @BindView(R.id.toobar) ToolBar toolBar;
    // 查询结果适配器
    SearchHistoryAdapter serachAdapter;
    //历史结果适配器
    SearchHistoryAdapter historyAdapter;

    private SearchResultItem searchResultItem;

    @MVPInject
    SearchConstract.Presenter presenter;

    /**
     * 启动搜索页面
     * @param activity Activity实例
     */
    public static void startActivity(Activity activity){
        activity.startActivity(new Intent(activity,SearchHistoryActivity.class));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_searchstock;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {

        serachAdapter = new SearchHistoryAdapter(this);
        historyAdapter = new SearchHistoryAdapter(this);
        lstSearch.setAdapter(serachAdapter);
        lstHistory.setAdapter(historyAdapter);
        edtSearch.setITextChangedLister(new SerachEditView.ITextChangedLister() {
            @Override
            public void onTextChanged(Editable s) {
                if(!EmptyUtils.isEmpty(s.toString())){
                    presenter.searchStock(SearchHistoryActivity.this.getApplicationContext(),edtSearch.getText().toString());
                    lelHistory.setVisibility(View.GONE);
                    lstSearch.setVisibility(View.VISIBLE);
                } else {
                    presenter.searchStock(null,null);
                    lelHistory.setVisibility(View.VISIBLE);
                    lstSearch.setVisibility(View.GONE);
                }

            }
        });

        lstSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                searchResultItem = serachAdapter.getItem(position);
                SearchHistoryUtils.saveHistory(searchResultItem);
                presenter.requestQuote(searchResultItem.stockID);
                  /*  // 2018-9-29
                QuoteItem quoteItem = new QuoteItem();
                quoteItem.name = searchResultItem.name;
                quoteItem.id = searchResultItem.stockID;
                quoteItem.subtype = searchResultItem.subtype;
                String[] split = searchResultItem.stockID.split("\\.");
                quoteItem.market = split[split.length-1];
                ArrayList<QuoteItem> quoteItems = new ArrayList<>();
                quoteItems.add(quoteItem);
                StockDetailActivity.startActivity(SearchHistoryActivity.this,quoteItems,0);
                */
            }
        });

        lstHistory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                searchResultItem = historyAdapter.getItem(position);
                SearchHistoryUtils.saveHistory(searchResultItem);
                presenter.requestQuote(searchResultItem.stockID);
               /*  // 2018-9-29
               QuoteItem quoteItem = new QuoteItem();
                quoteItem.name = searchResultItem.name;
                quoteItem.id = searchResultItem.stockID;
                quoteItem.subtype = searchResultItem.subtype;
                String[] split = searchResultItem.stockID.split("\\.");
                quoteItem.market = split[split.length-1];
                ArrayList<QuoteItem> quoteItems = new ArrayList<QuoteItem>();
                quoteItems.add(quoteItem);
                StockDetailActivity.startActivity(SearchHistoryActivity.this,quoteItems,0);*/
            }
        });
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onSearchSuccess(ArrayList<SearchResultItem> result) {
        serachAdapter.setData(result);
    }

    /**
     * 调用网络接口成功成功
     *
     * @param result
     */
    @Override
    public void onHistorySuccess(ArrayList<SearchResultItem> result) {
        historyAdapter.setData(result);
    }

    @Override
    public void onSearchFail() {

    }

    @Override
    public void onRequestQuoteSuccess(ArrayList<QuoteItem> quoteItems) {
        startStockDetailActivity(quoteItems);
    }

    @Override
    public void onRequestQuoteFail() {
        startStockDetailActivity(null);
    }
    private void startStockDetailActivity(ArrayList<QuoteItem> quoteItems){
        if( null == quoteItems || quoteItems.isEmpty() ){
            quoteItems = new ArrayList<>();
            QuoteItem quoteItem = new QuoteItem();
            String[] split = searchResultItem.stockID.split("\\.");
            quoteItem.name = searchResultItem.name;
            quoteItem.id = searchResultItem.stockID;
            quoteItem.subtype = searchResultItem.subtype;
            quoteItem.market = split[split.length-1];
            quoteItems.add(quoteItem);
        }
        StockDetailActivity.startActivity(SearchHistoryActivity.this,quoteItems,0);
    }
}
