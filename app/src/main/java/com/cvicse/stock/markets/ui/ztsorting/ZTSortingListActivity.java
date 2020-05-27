package com.cvicse.stock.markets.ui.ztsorting;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.cvicse.annotations.MVPInject;
import com.cvicse.stock.R;
import com.cvicse.stock.base.PBaseActivity;
import com.cvicse.stock.base.ToolBar;
import com.cvicse.stock.common.ColorUtils;
import com.cvicse.stock.markets.ui.StockDetailActivity;
import com.cvicse.stock.markets.ui.ztsorting.adapter.ZTSortingListAdapter;
import com.cvicse.stock.markets.ui.ztsorting.presenter.contract.ZTSortingListContract;
import com.cvicse.stock.pulltorefresh.PullToRefreshBase;
import com.cvicse.stock.pulltorefresh.PullToRefreshListView;
import com.cvicse.stock.seachstock.SearchHistoryActivity;
import com.cvicse.stock.view.CHScrollView;
import com.cvicse.stock.view.HVListView;
import com.mitake.core.QuoteItem;
import com.mitake.core.bean.ZTSortingItem;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class ZTSortingListActivity extends PBaseActivity implements ZTSortingListContract.View, AdapterView.OnItemClickListener{
    @MVPInject
    ZTSortingListContract.Presenter presenter;

    @BindView(R.id.topbar)
    ToolBar toolBar;
    @BindView(R.id.scroll_title)
    CHScrollView scrollTitle;
    @BindView(R.id.lsv_content)
    PullToRefreshListView lsvContent;
    @BindView(R.id.tev_name)
    TextView tev_name;// 股票名称
    @BindView(R.id.tev_lastPrice)
    TextView tev_lastPrice;// 最新价
    @BindView(R.id.tev_changeRate)
    TextView tev_changeRate;// 涨跌幅
    @BindView(R.id.tev_ztDatetime)
    TextView tev_ztDatetime;// 涨停时间
    @BindView(R.id.tev_buyVolumes)
    TextView tev_buyVolumes;// 封板单数
    @BindView(R.id.tev_preClosePrice)
    TextView tev_preClosePrice;// 昨收价
    @BindView(R.id.lly_syzt)
    LinearLayout lly_syzt;  //所有涨停
    @BindView(R.id.lly_yzzt)
    LinearLayout lly_yzzt;  //一字涨停
    @BindView(R.id.lly_zrzt)
    LinearLayout lly_zrzt;  //自然涨停

    private String ztType="allzt";//所有涨停
    private final static String TAG_UP = "0"; //升序
    private final static String TAG_DOWN = "1";  //降序
    private final static int TAG_NORMAL = 2;//不以当前属性排序

    private String[] titleNomalTextRaise = {"最新价", "涨跌幅", "涨停时间"};
    private String[] titleUpTextRaise = {"最新价↑", "涨跌幅↑", "涨停时间↑"};
    private String[] titleDownTextRaise = {"最新价↓", "涨跌幅↓", "涨停时间↓"};
    // 每个字段的排序栏位标志，选中的时候将对应的标志赋值给sortField
    private String[] sortFields = {"6", "7", "0"};

    private String[] titleNomalText;
    private String[] titleUpText;
    private String[] titleDownText;

    private TextView[] mTextViews;

    private ZTSortingListAdapter adapter;

    //下拉刷新,上拉加载，rankingType参数
    private String refreshRankingType;

    private int currentPage = 0;// 页码

    // 列表排序方式（默认按照最新价排序）
    private String sortField = "0";
    // 列表排序类型（默认升序）0:升序，1：降序
    private String sortType = TAG_UP;
    private int ADDFLAG_DEFAULT = 0;// 请求方式：默认
    private int ADDFLAG_REFRESH = 1;// 请求方式：下拉刷新
    private int ADDFLAG_ADDMORE = 2;// 请求方式：上拉加载


    public static void actionStart(Context context) {
        Intent intent = new Intent(context, ZTSortingListActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        // 根据不同的排序字段加载不同的布局
        int layoutId = R.layout.activity_zt_sorting;
        titleNomalText = titleNomalTextRaise;
        titleUpText = titleUpTextRaise;
        titleDownText = titleDownTextRaise;
        return layoutId;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        lly_syzt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ztType="allzt";
                TextView txt1=(TextView) findViewById(R.id.tev_zt1);
                TextView txt2=(TextView) findViewById(R.id.tev_zt2);
                TextView txt3=(TextView) findViewById(R.id.tev_zt3);
                txt1.setTextColor(Color.BLUE);
                txt2.setTextColor(Color.GRAY);
                txt3.setTextColor(Color.GRAY);
                presenter.ztSortingRanking(ADDFLAG_REFRESH, 0, 20, sortField, sortType,ztType);
            }
        });
        lly_yzzt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ztType="yzzt";
                TextView txt1=(TextView) findViewById(R.id.tev_zt1);
                TextView txt2=(TextView) findViewById(R.id.tev_zt2);
                TextView txt3=(TextView) findViewById(R.id.tev_zt3);
                txt1.setTextColor(Color.GRAY);
                txt2.setTextColor(Color.BLUE);
                txt3.setTextColor(Color.GRAY);
                presenter.ztSortingRanking(ADDFLAG_REFRESH, 0, 20, sortField, sortType,ztType);
            }
        });
        lly_zrzt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ztType="zrzt";
                TextView txt1=(TextView) findViewById(R.id.tev_zt1);
                TextView txt2=(TextView) findViewById(R.id.tev_zt2);
                TextView txt3=(TextView) findViewById(R.id.tev_zt3);
                txt1.setTextColor(Color.GRAY);
                txt2.setTextColor(Color.GRAY);
                txt3.setTextColor(Color.BLUE);
                presenter.ztSortingRanking(ADDFLAG_REFRESH, 0, 20, sortField, sortType,ztType);
            }
        });
        // 初始化数据
        mTextViews = new TextView[]{tev_lastPrice,tev_changeRate,tev_ztDatetime};
        // 刷新加载
        lsvContent.setMode(PullToRefreshBase.Mode.BOTH);
        HVListView hvListView = (HVListView) lsvContent.getRefreshableView();
        hvListView.setScrollView(scrollTitle);
        adapter = new ZTSortingListAdapter(this);
        lsvContent.setAdapter(adapter);

        lsvContent.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                // 下拉刷新
                if (currentPage == 0) {
                    // 如果页码等于0，直接刷新
                    presenter.ztSortingRanking(ADDFLAG_REFRESH, 0, 20, sortField, sortType,ztType);
                } else {
                    presenter.ztSortingRanking(ADDFLAG_REFRESH, (currentPage - 1) * 20, 20, sortField, sortType,ztType);
                }
                lsvContent.onRefreshComplete();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                // 上拉加载
                presenter.ztSortingRanking(ADDFLAG_ADDMORE, (currentPage + 1) * 20, 20, sortField, sortType,ztType);
                lsvContent.onRefreshComplete();
            }
        });

        toolBar.setToolBarClickListener(new ToolBar.ToolBarClickListener() {
            @Override
            public void onClick(View view, ToolBar.Type type) {
                switch (type) {
                    case RIGHT_TYPE_1:
                        SearchHistoryActivity.startActivity(ZTSortingListActivity.this);
                        break;
                }
            }
        });

        for (int i = 0; i < mTextViews.length; i++) {
            final int finalI = i;
            mTextViews[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    if (finalI == 0) {
//                        return;
//                    }
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
                    presenter.ztSortingRanking(ADDFLAG_REFRESH, 0, 20, sortField, sortType,ztType);

                }
            });
        }
    }

    @Override
    protected void initData() {
        presenter.ztSortingRanking(ADDFLAG_DEFAULT, 0, 20, sortField, TAG_UP,ztType);
    }

    private void setAllViewNormal() {
        for (int i = 0; i < mTextViews.length; i++) {
            mTextViews[i].setText(titleNomalText[i]);
            mTextViews[i].setTextColor(ColorUtils.GRAY);
        }
    }

    @Override
    public void ztSortingRefreshSuccess(List<ZTSortingItem> ztSortingItems) {
        lsvContent.onRefreshComplete();
        if (ztSortingItems != null && ztSortingItems.size() > 0) {
            if(0 != currentPage) {
                currentPage = currentPage - 1;
            }
            adapter.setData(ztSortingItems);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void ztSortingMoreSuccess(List<ZTSortingItem> ztSortingItems) {
        lsvContent.onRefreshComplete();
        if (ztSortingItems != null && ztSortingItems.size() > 0) {
            currentPage = currentPage + 1;
            adapter.setData(ztSortingItems);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void ztSortingSuccess(List<ZTSortingItem> ztSortingItems) {
        lsvContent.onRefreshComplete();
        if (ztSortingItems != null && ztSortingItems.size() > 0) {
            adapter.setData(ztSortingItems);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (position >= 1) {
            presenter.getQuoteInfo(adapter.getData().get(position - 1).code);
        }
    }

    /**
     * 获取快照成功
     */
    @Override
    public void getQuoteInfoSuccess(QuoteItem quoteItem) {
        ArrayList<QuoteItem> quoteItems = new ArrayList<>();
        quoteItems.add(quoteItem);
        StockDetailActivity.startActivity(ZTSortingListActivity.this,
                quoteItems, 0);
    }

}
