package com.cvicse.stock.markets.ui.yaoyue;

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
import com.cvicse.stock.markets.adapter.RankingListAdapter;
import com.cvicse.stock.markets.presenter.contract.RankingListContract;
import com.cvicse.stock.markets.ui.StockDetailActivity;
import com.cvicse.stock.markets.ui.yaoyue.adapter.YaoyueListAdapter;
import com.cvicse.stock.markets.ui.yaoyue.presenter.contract.YaoyueContract;
import com.cvicse.stock.pulltorefresh.PullToRefreshBase;
import com.cvicse.stock.pulltorefresh.PullToRefreshListView;
import com.cvicse.stock.seachstock.SearchHistoryActivity;
import com.cvicse.stock.view.CHScrollView;
import com.cvicse.stock.view.HVListView;
import com.mitake.core.QuoteItem;
import com.mitake.core.bean.offer.OfferQuoteBean;
import com.mitake.core.request.offer.OfferQuoteSort;

import java.util.ArrayList;

import butterknife.BindView;

import static com.cvicse.stock.markets.data.ParamUtil.RANKING_TYPE_RAISE;

/**
 * 要约页面（排序页）
 * Created by tang_h on 2020-2-27.
 */
public class YYActivity extends PBaseActivity implements YaoyueContract.View, AdapterView.OnItemClickListener {

    @MVPInject
    YaoyueContract.Presenter presenter;

    @BindView(R.id.topbar) ToolBar toolBar;
    @BindView(R.id.scroll_title) CHScrollView scrollTitle;
    @BindView(R.id.lsv_content) PullToRefreshListView lsvContent;
    @BindView(R.id.tev_name) TextView tev_name;// 证券名称
    @BindView(R.id.tev_offer_id) TextView tev_offer_id;// 收购编码
    @BindView(R.id.tev_offer_name) TextView tev_offer_name;// 收购人名称
    @BindView(R.id.tev_price) TextView tev_price;// 收购价格
    @BindView(R.id.tev_start_date) TextView tev_start_date;// 收购起始日
    @BindView(R.id.tev_end_date) TextView tev_end_date;// 收购截止日

    private final static int TAG_UP = 0; //升序
    private final static int TAG_DOWN = 1;  //降序
    private final static int TAG_NORMAL = 2;//不以当前属性排序

    private String[] titleNomalTextRaise = {"证券名称", "收购编码", "收购人名称", "收购价格", "收购起始日", "收购截止日"};
    private String[] titleUpTextRaise = {"证券名称", "收购编码↑", "收购人名称↑", "收购价格↑", "收购起始日↑", "收购截止日↑"};
    private String[] titleDownTextRaise = {"证券名称", "收购编码↓", "收购人名称↓", "收购价格↓", "收购起始日↓", "收购截止日↓"};
    // 每个字段的排序栏位标志，选中的时候将对应的标志赋值给sortField
    private int[] sortFields = {OfferQuoteSort.SortField.NAME,
            OfferQuoteSort.SortField.OFFER_ID, OfferQuoteSort.SortField.OFFER_NAME,
            OfferQuoteSort.SortField.PRICE, OfferQuoteSort.SortField.START_DATE,
            OfferQuoteSort.SortField.END_DATE};

    private String[] titleNomalText;
    private String[] titleUpText;
    private String[] titleDownText;

    private TextView[] mTextViews;

    public static final String KEY_STOCK_TYPE = "stocktype";
    public static final String KEY_RANKING_TYPE = "rankingtype";

    private YaoyueListAdapter adapter;

    //下拉刷新,上拉加载，rankingType参数
    private String refreshRankingType;

    private int currentPage = 0;// 页码

    // 列表排序方式（默认按照收购编码排序）
    private int sortField = OfferQuoteSort.SortField.OFFER_ID;
    // 列表排序类型（默认升序）
    private String sortType = OfferQuoteSort.SortType.ASC;
    private int ADDFLAG_DEFAULT = 0;// 请求方式：默认
    private int ADDFLAG_REFRESH = 1;// 请求方式：下拉刷新
    private int ADDFLAG_ADDMORE = 2;// 请求方式：上拉加载

    /**
     * 股票类型，查询的排行榜类型(涨幅榜等)
     */
    public static void actionStart(Context context, String stockType, String rankingType) {
        Intent intent = new Intent(context, YYActivity.class);
        intent.putExtra(KEY_STOCK_TYPE, stockType);
        intent.putExtra(KEY_RANKING_TYPE, rankingType);
        context.startActivity(intent);
    }


    @Override
    protected int getLayoutId() {
        // 根据不同的排序字段加载不同的布局
        int layoutId = R.layout.activity_yaoyue;
        titleNomalText = titleNomalTextRaise;
        titleUpText = titleUpTextRaise;
        titleDownText = titleDownTextRaise;
        return layoutId;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {

//        presenter.init(stockType);
        // 初始化数据
        mTextViews = new TextView[]{tev_name, tev_offer_id, tev_offer_name,
                tev_price, tev_start_date, tev_end_date};

        // 刷新加载
        lsvContent.setMode(PullToRefreshBase.Mode.BOTH);
        HVListView hvListView = (HVListView) lsvContent.getRefreshableView();
        hvListView.setScrollView(scrollTitle);
        adapter = new YaoyueListAdapter(this);
        lsvContent.setAdapter(adapter);
        lsvContent.setOnItemClickListener(this);
        lsvContent.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                // 下拉刷新
                if (currentPage == 0) {
                    // 如果页码等于0，直接刷新
                    presenter.offerQuoteRequest(ADDFLAG_REFRESH, 0, 20, sortField, sortType);
                } else {
                    presenter.offerQuoteRequest(ADDFLAG_REFRESH, (currentPage - 1) * 20, 20, sortField, sortType);
                }
                lsvContent.onRefreshComplete();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                // 上拉加载
                presenter.offerQuoteRequest(ADDFLAG_ADDMORE, (currentPage + 1) * 20, 20, sortField, sortType);
                lsvContent.onRefreshComplete();
            }
        });

        toolBar.setToolBarClickListener(new ToolBar.ToolBarClickListener() {
            @Override
            public void onClick(View view, ToolBar.Type type) {
                switch (type) {
                    case RIGHT_TYPE_1:
                        SearchHistoryActivity.startActivity(YYActivity.this);
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
                        if (OfferQuoteSort.SortType.ASC.equals(sortType)) {
                            // 如果本来是升序排序，变为降序排序
                            sortType = OfferQuoteSort.SortType.DESC;
                            mTextViews[finalI].setText(titleDownText[finalI]);
                        } else if (OfferQuoteSort.SortType.DESC.equals(sortType)) {
                            // 如果本来是降序排序，变为升序排序
                            sortType = OfferQuoteSort.SortType.ASC;
                            mTextViews[finalI].setText(titleUpText[finalI]);
                        }
                    } else {
                        // 如果之前不是按照这一项排序，改为降序
                        sortType = OfferQuoteSort.SortType.DESC;
                        mTextViews[finalI].setText(titleDownText[finalI]);
                    }
                    // 字体颜色
                    mTextViews[finalI].setTextColor(ColorUtils.BLUE);
                    // 改变排序标志为当前点击的项
                    sortField = sortFields[finalI];
                    // 页码改为0
                    currentPage = 0;
                    // 重新请求接口
                    presenter.offerQuoteRequest(ADDFLAG_REFRESH, 0, 20, sortField, sortType);

                }
            });
        }

    }

    @Override
    protected void initData() {
        presenter.offerQuoteRequest(ADDFLAG_DEFAULT, 0, 20, sortField, OfferQuoteSort.SortType.ASC);
    }

    /**
     * 排行榜的点击事件
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (position >= 1) {
//            StockDetailActivity.startActivity(YYActivity.this,
//                    (ArrayList<OfferQuoteBean>) adapter.getData(),
//                    position - 1);
            YYInfoActivity.actionStart(YYActivity.this, (adapter.getData()).get(position).code);
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
     * 要约列表请求成功
     */
    @Override
    public void offerQuoteSuccess(ArrayList<OfferQuoteBean> result) {
        lsvContent.onRefreshComplete();
        if (result != null && result.size() > 0) {
            adapter.setData(result);
            adapter.notifyDataSetChanged();
        }
    }

    /**
     * 要约列表请求成功(下拉刷新）
     */
    @Override
    public void offerQuoteRefreshSuccess(ArrayList<OfferQuoteBean> result) {
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
     * 要约列表请求成功（上拉加载）
     */
    @Override
    public void offerQuoteMoreSuccess(ArrayList<OfferQuoteBean> result) {
        lsvContent.onRefreshComplete();
        if (result != null && result.size() > 0) {
            currentPage = currentPage + 1;
            adapter.setData(result);
            adapter.notifyDataSetChanged();
        }
    }
}
