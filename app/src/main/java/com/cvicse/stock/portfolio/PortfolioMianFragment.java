package com.cvicse.stock.portfolio;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cvicse.annotations.MVPInject;
import com.cvicse.base.widget.verticalbanner.VerticalBannerView;
import com.cvicse.stock.R;
import com.cvicse.stock.base.PBaseFragment;
import com.cvicse.stock.base.ToolBar;
import com.cvicse.stock.markets.ui.StockDetailActivity;
import com.cvicse.stock.portfolio.activity.NewsAndReportActivity;
import com.cvicse.stock.portfolio.activity.RecentlyActivity;
import com.cvicse.stock.portfolio.activity.ReportActivity;
import com.cvicse.stock.portfolio.activity.StockEditActivity;
import com.cvicse.stock.portfolio.adapter.StockAdpater;
import com.cvicse.stock.portfolio.adapter.VerticalBannerAdapter;
import com.cvicse.stock.portfolio.presenter.constract.MianConstract;
import com.cvicse.stock.pulltorefresh.PullToRefreshBase;
import com.cvicse.stock.pulltorefresh.PullToRefreshListView;
import com.cvicse.stock.seachstock.SearchHistoryActivity;
import com.cvicse.stock.utils.ListViewUtils;
import com.cvicse.stock.utils.MyStocksUtils;
import com.mitake.core.QuoteItem;
import com.mitake.core.StockInfoListItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;



/**
 * 自选主页面
 * Created by liu_zlu on 2016/12/28 11:46
 */
public class PortfolioMianFragment extends PBaseFragment implements MianConstract.View{

    @MVPInject
    MianConstract.Presenter  presenter;

    @BindView(R.id.pull_to_refresh_list) PullToRefreshListView mPullRefreshListView;

    /** 涨跌按钮 */
    @BindView(R.id.topbar)ToolBar topBar;
    /**
     * 研究报告
     */
    @BindView(R.id.my_report) LinearLayout lelReport;
    /**
     * 当自选为空展示
     */
    @BindView(R.id.img_empty) ImageView img_empty;
    /**
     * 指数滚动控件
     */
    @BindView(R.id.my_maquee) VerticalBannerView verticalBannerView;
    /**
     * 新闻
     */
    @BindView(R.id.my_news) LinearLayout my_news;
    /**
     *
     * 其他排序按钮
     */
    @BindView(R.id.tev_quote_change) SortTextView tev_quote_change;
    /**
     * 最新价排序按钮
     */
    @BindView(R.id.tev_newprice) SortTextView  tev_newprice;

    private StockAdpater myStockAdpater;

    private VerticalBannerAdapter verticalBannerAdapter;

    /**
     * 创建自选主页面
     * @return
     */
    public static PortfolioMianFragment newInstance() {
        PortfolioMianFragment mySelectFragment = new PortfolioMianFragment();
        return mySelectFragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mystocks_mian;
    }


    @Override
    protected void initViews(Bundle savedInstanceState) {
        //设置listView可以超出滑动，但不能刷新、加载
        mPullRefreshListView.setMode(PullToRefreshBase.Mode.BOTH_NO_REFRESH);
        myStockAdpater = new StockAdpater(tev_quote_change);
        mPullRefreshListView.setAdapter(myStockAdpater);

        if( null==MyStocksUtils.getSelectCode()){
            mPullRefreshListView.setVisibility(View.GONE);
            img_empty.setVisibility(View.VISIBLE);
        }

        lelReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ReportActivity.class);
                startActivity(intent);
            }
        });
        verticalBannerAdapter = new VerticalBannerAdapter(null);
        verticalBannerView.setAdapter(verticalBannerAdapter);

        topBar.setToolBarClickListener(new ToolBar.ToolBarClickListener() {
            @Override
            public void onClick(View view, ToolBar.Type type) {
                switch (type){
                    case LEFT_TYPE:
                        if(((TextView)view).getText().length() > 3){
                            tev_newprice.reset();
                            tev_quote_change.reset();
                            topBar.setLeftText("编辑");
                            myStockAdpater.setSortType(false, StockAdpater.DEFAULT);

                        } else {
                            StockEditActivity.actionIntent(getActivity());
                        }
                        break;
                    case RIGHT_TYPE_1:
                        SearchHistoryActivity.startActivity(getActivity());
                        break;
                }
            }
        });
        tev_quote_change.setTypeChangedListener(new SortTextView.TypeChangedListener() {
            @Override
            public void onChanged(int change, int pre) {
                tev_newprice.reset();
                topBar.setLeftText("取消排序");
                if(change == SortTextView.DOWN_TYPE){
                    myStockAdpater.setSortType(false, StockAdpater.DOWN);
                } else {
                    myStockAdpater.setSortType(false, StockAdpater.UP);
                }

            }
        });
        tev_newprice.setTypeChangedListener(new SortTextView.TypeChangedListener() {
            @Override
            public void onChanged(int change, int pre) {
                tev_quote_change.reset();
                topBar.setLeftText("取消排序");
                if(change == SortTextView.DOWN_TYPE){
                    myStockAdpater.setSortType(true, StockAdpater.DOWN);
                } else {
                    myStockAdpater.setSortType(true, StockAdpater.UP);
                }
            }
        });

        /**
         * 自选列表点击事件
         */
        mPullRefreshListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                StockDetailActivity.startActivity(getActivity(),myStockAdpater.getData(),position-1);
            }
        });

        mPullRefreshListView.getRefreshableView().setDrawSelectorOnTop(true);

        verticalBannerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(verticalBannerAdapter.getDatas() != null){
                    TextView textView = (TextView) verticalBannerView.getChildAt(0).findViewById(R.id.tev_name);
                    String text = (String) textView.getText();
                    String flag = "";
                    if(text.equals("深")){
                        flag = "399001";
                    } else if(text.equals("创")){
                        flag = "399006";
                    } else {
                        flag = "000001";
                    }
                    ArrayList<QuoteItem> quoteItems = (ArrayList<QuoteItem>) verticalBannerAdapter.getDatas();
                    int length = quoteItems.size();
                    int position = 0;
                    for(int i = 0;i < length;i++){
                        if(quoteItems.get(i).id.contains(flag)){
                            position = i;
                            break;
                        }
                    }
                    StockDetailActivity.startActivity(getActivity(),quoteItems,position);
                }
            }
        });
    }

    @Override
    protected void initData() {
        // 显示自选
        presenter.onRequestSelects();
    }

    @OnClick({R.id.img_empty,R.id.my_news,R.id.my_browser})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.img_empty:
                SearchHistoryActivity.startActivity(getActivity());
                break;
            case  R.id.my_news:
                NewsAndReportActivity.startActivity(getActivity());
                break;
            case R.id.my_browser:
                RecentlyActivity.startActivity(getActivity());
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        verticalBannerView.start();
    }

    @Override
    public void onPause() {
        super.onPause();
        verticalBannerView.stop();
    }

    /**
     * 下拉刷新成功
     */
    @Override
    public void onSelectedSuccess(List<QuoteItem> result) {
        if( null!= result && result.size() >0 ){
            img_empty.setVisibility(View.INVISIBLE);
            mPullRefreshListView.setVisibility(View.VISIBLE);
            myStockAdpater.setData(result);
            mPullRefreshListView.onRefreshComplete();
        } else {
            img_empty.setVisibility(View.VISIBLE);
            mPullRefreshListView.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onRefreshFail() {
        img_empty.setVisibility(View.VISIBLE);
        mPullRefreshListView.setVisibility(View.INVISIBLE);
        mPullRefreshListView.onRefreshComplete();
    }


    /**
     * 更新滑动列表
     * @param quoteItem
     */
    @Override
    public void onIndexTcp(QuoteItem quoteItem) {
        verticalBannerAdapter.addItem(quoteItem);
    }

    /**
     * 自选模块实时更新
     *
     * @param quoteItem
     */
    @Override
    public void onSelectTcp(QuoteItem quoteItem) {
        int position = myStockAdpater.updateItem(quoteItem);
        ListViewUtils.updatePosition(position,mPullRefreshListView.getRefreshableView());
    }

    /**
     * 请求个股静态数据成功
     *
     * @param infoListItems
     */
    @Override
    public void requestStockInfoSuccess(HashMap<String,StockInfoListItem> infoListItems) {
        myStockAdpater.setSignData(infoListItems);
    }

    /**
     * 请求个股静态数据失败
     */
    @Override
    public void requestStockInfoFail() {

    }

}
