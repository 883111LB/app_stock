package com.cvicse.stock.markets.ui.subnewstock;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.cvicse.annotations.MVPInject;
import com.cvicse.stock.R;
import com.cvicse.stock.base.PBaseActivity;
import com.cvicse.stock.base.ToolBar;
import com.cvicse.stock.common.ColorUtils;
import com.cvicse.stock.markets.ui.StockDetailActivity;
import com.cvicse.stock.markets.ui.subnewstock.adapter.SubNewStockListAdapter;
import com.cvicse.stock.markets.ui.subnewstock.presenter.contract.SubNewStockContract;
import com.cvicse.stock.pulltorefresh.PullToRefreshBase;
import com.cvicse.stock.pulltorefresh.PullToRefreshListView;
import com.cvicse.stock.seachstock.SearchHistoryActivity;
import com.cvicse.stock.view.CHScrollView;
import com.cvicse.stock.view.HVListView;
import com.mitake.core.QuoteItem;
import com.mitake.core.SubNewStockRankingModel;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * 次新股
 * Created by tang_h on 2020-2-27.
 */
public class SubNewStockActivity extends PBaseActivity implements SubNewStockContract.View, AdapterView.OnItemClickListener {

    @MVPInject
    SubNewStockContract.Presenter presenter;

    @BindView(R.id.topbar) ToolBar toolBar;
    @BindView(R.id.scroll_title) CHScrollView scrollTitle;
    @BindView(R.id.lsv_content) PullToRefreshListView lsvContent;
    @BindView(R.id.tev_name) TextView tev_name;// 股票名称
    @BindView(R.id.tev_originalPrice) TextView tev_originalPrice;// 发行价
    @BindView(R.id.tev_lastestPrice) TextView tev_lastestPrice;// 最新价
    @BindView(R.id.tev_originalData) TextView tev_originalData;// 发行日期
    @BindView(R.id.tev_cLimitedDays) TextView tev_cLimitedDays;// 连续涨停天数
    @BindView(R.id.tev_allRate) TextView tev_allRate;// 累计涨跌幅
    @BindView(R.id.tev_change) TextView tev_change;// 涨跌额
    @BindView(R.id.tev_turnoverRate) TextView tev_turnoverRate;// 换手率
    @BindView(R.id.tev_amount) TextView tev_amount;// 成交额
    @BindView(R.id.tev_pe) TextView tev_pe;// 市盈率
    @BindView(R.id.tev_totalValue) TextView tev_totalValue;// 总市值
    @BindView(R.id.tev_flowValue) TextView tev_flowValue;// 流通市值
    @BindView(R.id.tev_rate) TextView mRate;//当天涨幅
    @BindView(R.id.tev_preClosePrice) TextView mPreClosePrice;//昨收价
    @BindView(R.id.tev_mainNetInflow) TextView mMainNetInflow;//主力资金净流入

    private final static String TAG_UP = "0"; //升序
    private final static String TAG_DOWN = "1";  //降序
    private final static int TAG_NORMAL = 2;//不以当前属性排序

    private String[] titleNomalTextRaise = {"股票名称", "发行价", "最新价", "发行日期", "连续涨停天数", "累计涨跌幅", "涨跌额", "换手率", "成交额", "市盈率", "总市值", "流通市值"};
    private String[] titleUpTextRaise = {"股票名称", "发行价↑", "最新价↑", "发行日期↑", "连续涨停天数↑", "累计涨跌幅↑", "涨跌额↑", "换手率↑", "成交额↑", "市盈率↑", "总市值↑", "流通市值↑"};
    private String[] titleDownTextRaise = {"股票名称", "发行价↓", "最新价↓", "发行日期↓", "连续涨停天数↓", "累计涨跌幅↓", "涨跌额↓", "换手率↓", "成交额↓", "市盈率↓", "总市值↓", "流通市值↓"};
    // 排序说明:{0股票代码,2发行价,3最新价,4发行日期,5连续涨停天数,8累计涨跌幅,9涨跌额,10换手率,11成交额,13市盈率,14总市值,15流通市值} 1股票名称不支持排序
    // 每个字段的排序栏位标志，选中的时候将对应的标志赋值给sortField
    private String[] sortFields = {"0", "2", "3", "4", "5", "8", "9", "10", "11", "13", "14", "15"};

    private String[] titleNomalText;
    private String[] titleUpText;
    private String[] titleDownText;

    private TextView[] mTextViews;

    private SubNewStockListAdapter adapter;

    //下拉刷新,上拉加载，rankingType参数
    private String refreshRankingType;

    private int currentPage = 0;// 页码

    // 列表排序方式（默认按照发行价排序）
    private String sortField = "2";
    // 列表排序类型（默认升序）0:升序，1：降序
    private String sortType = TAG_UP;
    private int ADDFLAG_DEFAULT = 0;// 请求方式：默认
    private int ADDFLAG_REFRESH = 1;// 请求方式：下拉刷新
    private int ADDFLAG_ADDMORE = 2;// 请求方式：上拉加载

    /**
     * 股票类型，查询的排行榜类型(涨幅榜等)
     */
    public static void actionStart(Context context) {
        Intent intent = new Intent(context, SubNewStockActivity.class);
        context.startActivity(intent);
    }


    @Override
    protected int getLayoutId() {
        // 根据不同的排序字段加载不同的布局
        int layoutId = R.layout.activity_sub_new_stock;
        titleNomalText = titleNomalTextRaise;
        titleUpText = titleUpTextRaise;
        titleDownText = titleDownTextRaise;
        return layoutId;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {

        // 初始化数据
        mTextViews = new TextView[]{tev_name, tev_originalPrice,
                tev_lastestPrice, tev_originalData, tev_cLimitedDays, tev_allRate, tev_change,
                tev_turnoverRate, tev_amount, tev_pe, tev_totalValue, tev_flowValue
        };

        // 刷新加载
        lsvContent.setMode(PullToRefreshBase.Mode.BOTH);
        HVListView hvListView = (HVListView) lsvContent.getRefreshableView();
        hvListView.setScrollView(scrollTitle);
        adapter = new SubNewStockListAdapter(this);
        lsvContent.setAdapter(adapter);
        lsvContent.setOnItemClickListener(this);
        lsvContent.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                // 下拉刷新
                if (currentPage == 0) {
                    // 如果页码等于0，直接刷新
                    presenter.subNewStockRanking(ADDFLAG_REFRESH, 0, 20, sortField, sortType);
                } else {
                    presenter.subNewStockRanking(ADDFLAG_REFRESH, (currentPage - 1) * 20, 20, sortField, sortType);
                }
                lsvContent.onRefreshComplete();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                // 上拉加载
                presenter.subNewStockRanking(ADDFLAG_ADDMORE, (currentPage + 1) * 20, 20, sortField, sortType);
                lsvContent.onRefreshComplete();
            }
        });

        toolBar.setToolBarClickListener(new ToolBar.ToolBarClickListener() {
            @Override
            public void onClick(View view, ToolBar.Type type) {
                switch (type) {
                    case RIGHT_TYPE_1:
                        SearchHistoryActivity.startActivity(SubNewStockActivity.this);
                        break;
                }
            }
        });

        for (int i = 0; i < mTextViews.length; i++) {
            final int finalI = i;
            mTextViews[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (finalI == 0) {
                        return;
                    }
                    setAllViewNormal();
                    // 如果之前是按照这一项排序，改变排序方向
                    if (sortField == sortFields[finalI]) {
                        // 排序方式（升序、降序）
                        if (TAG_UP.equals(sortType)) {
                            // 如果本来是升序排序，变为降序排序
                            sortType = TAG_DOWN;
                            mTextViews[finalI].setText(titleDownText[finalI]);
                        } else if (TAG_DOWN.equals(sortType)) {
                            // 如果本来是降序排序，变为升序排序
                            sortType = TAG_UP;
                            mTextViews[finalI].setText(titleUpText[finalI]);
                        }
                    } else {
                        // 如果之前不是按照这一项排序，改为降序
                        sortType = TAG_DOWN;
                        mTextViews[finalI].setText(titleDownText[finalI]);
                    }
                    // 字体颜色
                    mTextViews[finalI].setTextColor(ColorUtils.BLUE);
                    // 改变排序标志为当前点击的项
                    sortField = sortFields[finalI];
                    // 页码改为0
                    currentPage = 0;
                    // 重新请求接口
                    presenter.subNewStockRanking(ADDFLAG_REFRESH, 0, 20, sortField, sortType);

                }
            });
        }

    }

    @Override
    protected void initData() {

        presenter.subNewStockRanking(ADDFLAG_DEFAULT, 0, 20, sortField, TAG_UP);
    }

    /**
     * 排行榜的点击事件
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (position >= 1) {
            presenter.getQuoteInfo(adapter.getData().get(position - 1).getCode());
        }
    }

    /**
     * 将所有控件转化为不按它排行状态
     */
    private void setAllViewNormal() {
        for (int i = 1; i < mTextViews.length; i++) {
            mTextViews[i].setText(titleNomalText[i]);
            mTextViews[i].setTextColor(ColorUtils.GRAY);
        }
    }

    /**
     * 次新股列表 请求成功
     */
    @Override
    public void subNewStockSuccess(ArrayList<SubNewStockRankingModel> result) {
        lsvContent.onRefreshComplete();
        if (result != null && result.size() > 0) {
            adapter.setData(result);
            adapter.notifyDataSetChanged();
        }
    }

    /**
     * 次新股列表 成功(下拉刷新）
     */
    @Override
    public void subNewStockRefreshSuccess(ArrayList<SubNewStockRankingModel> result) {
        lsvContent.onRefreshComplete();
        if (result != null && result.size() > 0) {
            if(0 != currentPage) {
                currentPage = currentPage - 1;
            }
            adapter.setData(result);
            adapter.notifyDataSetChanged();
        }
    }

    /**
     * 次新股列表 成功（上拉加载）
     */
    @Override
    public void subNewStockMoreSuccess(ArrayList<SubNewStockRankingModel> result) {
        lsvContent.onRefreshComplete();
        if (result != null && result.size() > 0) {
            currentPage = currentPage + 1;
            adapter.setData(result);
            adapter.notifyDataSetChanged();
        }
    }

    /**
     * 获取快照成功
     */
    @Override
    public void getQuoteInfoSuccess(QuoteItem quoteItem) {
        ArrayList<QuoteItem> quoteItems = new ArrayList<>();
        quoteItems.add(quoteItem);
        StockDetailActivity.startActivity(SubNewStockActivity.this,
                quoteItems, 0);
    }
}
