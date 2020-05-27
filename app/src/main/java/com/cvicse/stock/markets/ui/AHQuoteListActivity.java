package com.cvicse.stock.markets.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.cvicse.annotations.MVPInject;
import com.cvicse.stock.R;
import com.cvicse.stock.base.PBaseActivity;
import com.cvicse.stock.base.ToolBar;
import com.cvicse.stock.common.ColorUtils;
import com.cvicse.stock.markets.adapter.AHQuoteListAdapter;
import com.cvicse.stock.markets.presenter.contract.AHQuoteListContract;
import com.cvicse.stock.pulltorefresh.PullToRefreshBase;
import com.cvicse.stock.pulltorefresh.PullToRefreshHSListView;
import com.cvicse.stock.seachstock.SearchHistoryActivity;
import com.cvicse.stock.view.CHScrollView;
import com.cvicse.stock.view.HVListView;
import com.mitake.core.QuoteItem;
import com.mitake.core.bean.AHQuoteItem;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * AH股排行
 * Created by tang_xqing on 2018/5/14.
 */
public class AHQuoteListActivity extends PBaseActivity implements AHQuoteListContract.View {

    @MVPInject
    AHQuoteListContract.Presenter mPresenter;

    @BindView(R.id.topbar)
    ToolBar topbar;
    @BindView(R.id.tev_lastPrice_A)
    TextView tevLastPriceA;
    @BindView(R.id.tev_changeRate_A)
    TextView tevChangeRateA;
    @BindView(R.id.tev_lastPrice_H)
    TextView tevLastPriceH;
    @BindView(R.id.tev_changeRate_H)
    TextView tevChangeRateH;
    @BindView(R.id.tev_premium_ah)
    TextView tevPremiumAh;
    @BindView(R.id.tev_closePrice_A)
    TextView tevClosePriceA;
    @BindView(R.id.tev_closePrice_H)
    TextView tevClosePriceH;
    @BindView(R.id.scroll_title)
    CHScrollView scrollTitle;
    @BindView(R.id.lsv_content)
    PullToRefreshHSListView lsvContent;

    public static String TYPE = "type";

    private static final int status_nomal = 0;
    private static final int status_up = 1;
    private static final int status_down = 2;


    private String[] titleText = {"A股最新", "A股涨幅(%)", "A股昨收","H股最新", "H股涨幅(%)","H股昨收", "溢价(H/A)"};

    private String[] lastpriceA_param = {"0,20,2,1", "0,20,2,0"};
    private String[] changrateA_param = {"0,20,3,1", "0,20,3,0"};
    private String[] closePriceA_param = {"0,20,4,1", "0,20,4,0"};
    private String[] lastpriceH_param = {"0,20,6,1", "0,20,6,0"};
    private String[] changrateH_param = {"0,20,7,1", "0,20,7,0"};
    private String[] closePriceH_param = {"0,20,8,1", "0,20,8,0"};
    private String[] premium_param = {"0,20,9,1", "0,20,9,0"};

    private String[] [] sortParam = {lastpriceA_param,changrateA_param,closePriceA_param,lastpriceH_param,changrateH_param,closePriceH_param, premium_param};
    private TextView[] mTextViews;

    private AHQuoteListAdapter mAHQuoteListAdapter;

    private int currentPage = 0;
    private String currentParam =lastpriceA_param[0];

    public static void startActivity(Context context, String title) {
        Intent intent = new Intent(context, AHQuoteListActivity.class);
        intent.putExtra(TYPE, title);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_quote_list_ah;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        //获取标题
        String title = getIntent().getStringExtra(TYPE);
        topbar.setTitle(title);

        mTextViews = new TextView[]{tevLastPriceA, tevChangeRateA, tevClosePriceA,tevLastPriceH, tevChangeRateH, tevClosePriceH,tevPremiumAh};

        ((HVListView) lsvContent.getRefreshableView()).setScrollView(scrollTitle);
        lsvContent.setMode(PullToRefreshBase.Mode.BOTH);
        mAHQuoteListAdapter = new AHQuoteListAdapter(this);
        lsvContent.setAdapter(mAHQuoteListAdapter);

        for (int i = 0; i < mTextViews.length; i++) {
            TextView textView = mTextViews[i];
            if( 0 == i ){
                textView.setText(titleText[i]+"↓");
                textView.setTextColor(ColorUtils.BLUE);
                textView.setTag(status_down);
            }else {
                textView.setTag(status_nomal);
            }
        }
        initClick();
    }

    private void initClick() {
        topbar.setToolBarClickListener(new ToolBar.ToolBarClickListener() {
            @Override
            public void onClick(View view, ToolBar.Type type) {
                switch (type) {
                    case RIGHT_TYPE_1:
                        SearchHistoryActivity.startActivity(AHQuoteListActivity.this);
                        break;
                }
            }
        });

        mAHQuoteListAdapter.setAHItemClick(new AHQuoteListAdapter.AHItemClick() {
            @Override
            public void onAHItemClick(String code, int position, boolean isAQuote) {
                mPresenter.requestQuote(code);
            }
        });

        lsvContent.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                if( currentPage >=1 ){
                    String[] param = currentParam.split(",");
                    if( param.length >=4 ){
                        currentParam = (currentPage - 1) + "," + param[1] + "," + param[2] + "," + param[3];
                    }
                }
                mPresenter.requestPullDownAHQuoteList(currentParam);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                String[] param = currentParam.split(",");
                if( param.length >=4 ){
                    currentParam = (currentPage + 1) + "," + param[1] + "," + param[2] + "," + param[3];
                }
                mPresenter.requestPullUpAHQuoteList(currentParam);
            }
        });

        for (int i = 0; i < mTextViews.length; i++) {
            final int finalI = i;
            mTextViews[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    convertAllToNomal();
                    currentPage = 0;
                    mTextViews[finalI].setTextColor(ColorUtils.BLUE);
                    Integer tag = (Integer) mTextViews[finalI].getTag();

                    switch (tag){
                        case status_nomal:
                            mTextViews[finalI].setTag(status_down);
                            currentParam = sortParam[finalI][0];
                            mTextViews[finalI].setText(titleText[finalI]+"↓");
                            break;
                        case status_down:
                            mTextViews[finalI].setTag(status_up);
                            currentParam = sortParam[finalI][1];
                            mTextViews[finalI].setText(titleText[finalI]+"↑");
                            break;
                        case status_up:
                            mTextViews[finalI].setTag(status_down);
                            currentParam = sortParam[finalI][0];
                            mTextViews[finalI].setText(titleText[finalI]+"↓");
                            break;
                        default: break;
                    }
                    mPresenter.requestAHQuoteList(currentParam);
                }
            });
        }
    }

    @Override
    protected void initData() {
        mPresenter.requestAHQuoteList(currentParam);
    }

    private void convertAllToNomal() {
        for (int i = 0; i < mTextViews.length; i++) {
            mTextViews[i].setText(titleText[i]);
            mTextViews[i].setTextColor(ColorUtils.GRAY);
        }
    }

    /**
     * 请求AH股列表成功
     * @param ahQuoteItemList
     */
    @Override
    public void requestAHQuoteListSuccess(List<AHQuoteItem> ahQuoteItemList) {
        lsvContent.onRefreshComplete();
        mAHQuoteListAdapter.setData(ahQuoteItemList);
    }

    @Override
    public void requestPullDownAHQuoteListSuccess(List<AHQuoteItem> ahQuoteItemList) {
        lsvContent.onRefreshComplete();
        if (currentPage >= 1) {
            currentPage = currentPage - 1;
        } else {
            currentPage = 0;
        }
        mAHQuoteListAdapter.setData(ahQuoteItemList);
    }

    @Override
    public void requestPullUpAHQuoteListSuccess(List<AHQuoteItem> ahQuoteItemList) {
        lsvContent.onRefreshComplete();
        currentPage = currentPage + 1;
        mAHQuoteListAdapter.setData(ahQuoteItemList);
    }

    @Override
    public void requestAHQuoteListFail() {
        lsvContent.onRefreshComplete();
    }

    @Override
    public void requestQuoteSuccess(ArrayList<QuoteItem> quoteItems) {
        StockDetailActivity.startActivity(AHQuoteListActivity.this, quoteItems, 0);
    }

    @Override
    public void requestQuoteFail() {

    }
}
