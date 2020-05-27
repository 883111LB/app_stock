package com.cvicse.stock.markets.ui.stockdetail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.cvicse.annotations.MVPInject;
import com.cvicse.base.utils.SizeUtils;
import com.cvicse.stock.R;
import com.cvicse.stock.base.PBaseActivity;
import com.cvicse.stock.base.ToolBar;
import com.cvicse.stock.common.ColorUtils;
import com.cvicse.stock.markets.adapter.MarginTradingAdapter;
import com.cvicse.stock.markets.data.MarginTradingBo;
import com.cvicse.stock.markets.helper.MarginTradingHelper;
import com.cvicse.stock.markets.presenter.marketdetail.contract.MarginTradingContract;
import com.cvicse.stock.markets.ui.StockDetailActivity;
import com.cvicse.stock.pulltorefresh.PullToRefreshBase;
import com.cvicse.stock.pulltorefresh.PullToRefreshHSListView;
import com.cvicse.stock.util.FormatUtils;
import com.cvicse.stock.utils.TextUtils;
import com.cvicse.stock.view.CHScrollView;
import com.cvicse.stock.view.HVListView;
import com.mitake.core.QuoteItem;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * 融资融券
 * Created by tang_xqing on 18-8-3.
 */
public class MarginTradingActivity extends PBaseActivity implements MarginTradingContract.View{

    @MVPInject
    MarginTradingContract.Presenter mPresenter;

    @BindView(R.id.toolbar)
    ToolBar toolbar;
    @BindView(R.id.tev_datetime)
    TextView tevDatetime;           // 日期
    @BindView(R.id.tev_sh_finbalance)
    TextView tevShFinbalance;       // 沪-融资余额
    @BindView(R.id.tev_sz_finbalance)
    TextView tevSzFinbalance;
    @BindView(R.id.tev_shsz_finbalance)
    TextView tevShszFinbalance;
    @BindView(R.id.tev_sh_finbuyamt)
    TextView tevShFinbuyamt;         // 沪-融资买入
    @BindView(R.id.tev_sz_finbuyamt)
    TextView tevSzFinbuyamt;
    @BindView(R.id.tev_shsz_finbuyamt)
    TextView tevShszFinbuyamt;
    @BindView(R.id.tev_sh_mrggbalsum)
    TextView tevShMrggbalsum;        // 沪-融券余量
    @BindView(R.id.tev_sz_mrggbalsum)
    TextView tevSzMrggbalsum;
    @BindView(R.id.tev_shsz_mrggbalsum)
    TextView tevShszMrggbalsum;
    @BindView(R.id.tev_sh_finmrgnbalsum)
    TextView tevShFinmrgnbalsum;    // 沪-融资融券余量
    @BindView(R.id.tev_sz_finmrgnbalsum)
    TextView tevSzFinmrgnbalsum;
    @BindView(R.id.tev_shsz_finmrgnbalsum)
    TextView tevShszFinmrgnbalsum;
    @BindView(R.id.scroll_title)
    CHScrollView scrollTitle;
    @BindView(R.id.lly_sort_type)
    LinearLayout llySortType;     // 排序字段布局
    @BindView(R.id.lsv_content)
    PullToRefreshHSListView lsvContent;
    @BindView(R.id.spi_market)
    Spinner spiMarket;

    private MarginTradingAdapter mTradingAdapter;
    private MarginTradingHelper mMarginTradingHelper;
    private String[][] marginTradingRankingKey;
    private static final int status_nomal = 0;
    private static final int status_up = 1;
    private static final int status_down = 2;

    private int currentPage = 0;
    private String currentParam = "TRADEDATE";
    private String marketType="";  // 市场代码标识

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, MarginTradingActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_margin_trading;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        HVListView refreshableView = (HVListView) lsvContent.getRefreshableView();
        refreshableView.setScrollView(scrollTitle);
        lsvContent.setMode(PullToRefreshBase.Mode.PULL_FROM_END);
        marketType = spiMarket.getSelectedItem().toString();

        mMarginTradingHelper = new MarginTradingHelper();
        marginTradingRankingKey = mMarginTradingHelper.getMarginTradingRankingKey();
        mTradingAdapter = new MarginTradingAdapter(this,false,refreshableView);
        lsvContent.setAdapter(mTradingAdapter);

        addView();
        initClick();
    }

    @Override
    protected void initData() {
        mPresenter.requestFinDifferencr("sh","0,90");
        mPresenter.requestFinDifferencr("sz","0,90");
        mPresenter.requestFinDifferencr("sh_sz","0,90");

        setCurrentParam(0,"0");
        mPresenter.requestSubMarket(currentParam,"0,20");
    }

    private void initClick() {
        spiMarket.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                currentPage = 0;
                marketType = (String) parent.getAdapter().getItem(position);
                setCurrentParam(0,"0");
                mPresenter.requestSubMarket(currentParam,"0,20");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        lsvContent.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // 跳转行情页面
                mPresenter.requestQuote(((MarginTradingBo)mTradingAdapter.getItem(position-1)).trading);
            }
        });

        lsvContent.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {

            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                mPresenter.loadMoreSubMarket(currentParam,(currentPage + 1)+",20");
            }
        });
    }

    private void addView() {
        llySortType.removeAllViews();
        for (int i = 0; i < marginTradingRankingKey[1].length; i++) {
            TextView textView = new TextView(this);
            textView.setText(marginTradingRankingKey[1][i]);
            if (0 == i) {
                textView.setText(marginTradingRankingKey[1][i] + "↓");
                textView.setTextColor(ColorUtils.BLUE);
                textView.setTag(status_down);
            } else {
                textView.setTextColor(ColorUtils.GRAY);
                textView.setTag(status_nomal);
            }
            textView.setGravity(Gravity.CENTER);
            textView.setLayoutParams(new LinearLayout.LayoutParams(SizeUtils.dp2px(this, 80), ViewGroup.LayoutParams.MATCH_PARENT));
            initClickListener(textView, i);
            llySortType.addView(textView);
        }
    }

    private void initClickListener(final TextView textView, final int index) {
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAllViewNormal();
                currentPage = 0;
                textView.setTextColor(ColorUtils.BLUE);
                Integer tag = (Integer) textView.getTag();
                String text = marginTradingRankingKey[1][index];
                switch (tag) {
                    case status_nomal:
                        textView.setTag(status_down);
                        setCurrentParam(index, "0");
                        textView.setText(text + "↓");
                        break;
                    case status_down:
                        textView.setTag(status_up);
                        setCurrentParam(index, "1");
                        textView.setText(text + "↑");
                        break;
                    case status_up:
                        textView.setTag(status_down);
                        setCurrentParam(index, "0");
                        textView.setText(text + "↓");
                        break;
                    default:
                        break;
                }
                // 点击排序字段，从第一页开始
                mPresenter.requestSubMarket(currentParam,"0,20");
            }
        });
    }

    /**
     * 将所有控件转化为不按它排行状态
     */
    private void setAllViewNormal() {
        for (int i = 0; i < llySortType.getChildCount(); i++) {
            TextView mTextView = (TextView) llySortType.getChildAt(i);
            mTextView.setText(marginTradingRankingKey[1][i]);
            mTextView.setTextColor(ColorUtils.GRAY);
        }
    }

    /**
     * 设置排序参数。例：FINBALANCE_sh_0
     * @param index
     * @param order 排序顺序  1-升序 0-降序
     */
    public void setCurrentParam(int index, String order) {
        currentParam = marginTradingRankingKey[0][index] + "_" + marketType + "_" + order;
    }


    /**
     * 返回分市场融资融券数据
     * @param tradingBoArrayList
     */
    @Override
    public void requestSubMarketSuccess(ArrayList<MarginTradingBo> tradingBoArrayList) {
        lsvContent.onRefreshComplete();
        mTradingAdapter.setDataList(tradingBoArrayList);
    }

    /**
     * 返回个股融资融券数据
     * @param tradingBoArrayList
     */
    @Override
    public void requestStockSuccess(ArrayList<MarginTradingBo> tradingBoArrayList) {

    }

    /**
     * 返回加载更多分市场融资融券数据
     * @param tradingBoArrayList
     */
    @Override
    public void loadMoreSubMarketSuccess(ArrayList<MarginTradingBo> tradingBoArrayList) {
        lsvContent.onRefreshComplete();
        if( null == tradingBoArrayList || tradingBoArrayList.isEmpty() ){
            return;
        }
        currentPage = currentPage +1;
        mTradingAdapter.setDataList(tradingBoArrayList);
    }

    /**
     * 返回沪市场融资融券差额
     * @param tradingBoArrayList
     */
    @Override
    public void requestFinDifSHSuccess(ArrayList<MarginTradingBo> tradingBoArrayList) {
        if( null == tradingBoArrayList || tradingBoArrayList.isEmpty() ){
            return;
        }
        MarginTradingBo marginTradingBo = tradingBoArrayList.get(0);
        TextUtils.setText(tevDatetime,marginTradingBo.tradeDate);
        TextUtils.setText(tevShFinbalance, FormatUtils.format(marginTradingBo.finbalance));
        TextUtils.setText(tevShFinbuyamt,FormatUtils.format(marginTradingBo.finbuyamt));
        TextUtils.setText(tevShMrggbalsum,FormatUtils.format(marginTradingBo.mrggbal));
        TextUtils.setText(tevShFinmrgnbalsum,FormatUtils.format(marginTradingBo.finmrgnbal));
    }

    /**
     * 返回深市场融资融券差额
     * @param tradingBoArrayList
     */
    @Override
    public void requestFinDifSZSuccess(ArrayList<MarginTradingBo> tradingBoArrayList) {
        if( null == tradingBoArrayList || tradingBoArrayList.isEmpty() ){
            return;
        }
        MarginTradingBo marginTradingBo = tradingBoArrayList.get(0);
        TextUtils.setText(tevDatetime,marginTradingBo.tradeDate);
        TextUtils.setText(tevSzFinbalance,FormatUtils.format(marginTradingBo.finbalance));
        TextUtils.setText(tevSzFinbuyamt,FormatUtils.format(marginTradingBo.finbuyamt));
        TextUtils.setText(tevSzMrggbalsum,FormatUtils.format(marginTradingBo.mrggbal));
        TextUtils.setText(tevSzFinmrgnbalsum,FormatUtils.format(marginTradingBo.finmrgnbal));
    }

    /**
     * 返回沪深市场融资融券差额
     * @param tradingBoArrayList
     */
    @Override
    public void requestFinDifSHSZSuccess(ArrayList<MarginTradingBo> tradingBoArrayList) {
        if( null == tradingBoArrayList || tradingBoArrayList.isEmpty() ){
            return;
        }
        MarginTradingBo marginTradingBo = tradingBoArrayList.get(0);
        TextUtils.setText(tevDatetime,marginTradingBo.tradeDate);
        TextUtils.setText(tevShszFinbalance,FormatUtils.format(marginTradingBo.finbalance));
        TextUtils.setText(tevShszFinbuyamt,FormatUtils.format(marginTradingBo.finbuyamt));
        TextUtils.setText(tevShszMrggbalsum,FormatUtils.format(marginTradingBo.mrggbal));
        TextUtils.setText(tevShszFinmrgnbalsum,FormatUtils.format(marginTradingBo.finmrgnbal));
    }

    @Override
    public void requestQuoteSuccess(ArrayList<QuoteItem> quoteItemList) {
        StockDetailActivity.startActivity(this, quoteItemList, 0);
    }
}
