package com.cvicse.stock.markets.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.cvicse.annotations.MVPInject;
import com.cvicse.stock.BaseApplication;
import com.cvicse.stock.R;
import com.cvicse.stock.base.PBaseActivity;
import com.cvicse.stock.base.ToolBar;
import com.cvicse.stock.markets.adapter.DRQuoteListAdapter;
import com.cvicse.stock.markets.presenter.contract.DRQuoteListContract;
import com.cvicse.stock.pulltorefresh.PullToRefreshBase;
import com.cvicse.stock.pulltorefresh.PullToRefreshHSListView;
import com.cvicse.stock.seachstock.SearchHistoryActivity;
import com.cvicse.stock.view.CHScrollView;
import com.cvicse.stock.view.HVListView;
import com.mitake.core.bean.DRQuoteItem;

import java.util.List;

import butterknife.BindView;

/**
 * Created by shi_yhui on 2018/11/24.
 */

public class DRQuoteListActivity extends PBaseActivity implements DRQuoteListContract.View{
    @MVPInject
    DRQuoteListContract.Presenter mPresenter;

    @BindView(R.id.topbar)
    ToolBar topbar;
    @BindView(R.id.tev_code_DR)
    TextView tevCodeDR;
    @BindView(R.id.tev_lastPrice_DR)
    TextView tevLastPriceDR;
    @BindView(R.id.tev_preClosePrice_DR)
    TextView tevPreClosePriceDR;
    @BindView(R.id.tev_changeRate_DR)
    TextView tevChangeRateDR;
//    @BindView(R.id.tev_dateTime_DR)
//    TextView tevDateTimeDR;
    @BindView(R.id.tev_codeBase_DR)
    TextView tevCodeBaseDR;
    @BindView(R.id.tev_nameBase_DR)
    TextView tevNameBaseDR;
    @BindView(R.id.tev_lastPriceBase_DR)
    TextView tevLastPriceBaseDR;
    @BindView(R.id.tev_preClosePriceBase_DR)
    TextView tevPreClosePriceBaseDR;
    @BindView(R.id.tev_changeRateBase_DR)
    TextView tevChangeRateBaseDR;
//    @BindView(R.id.tev_dateTimeBase_DR)
//    TextView tevDateTimeBaseDR;
    @BindView(R.id.tev_preMium_DR)
    TextView tevPreMium;
    @BindView(R.id.scroll_title)
    CHScrollView scrollTitle;
    @BindView(R.id.lsv_content)
    PullToRefreshHSListView lsvContent;

    public static String TYPE = "type";

    private static final int status_nomal = 0;
    private static final int status_up = 1;
    private static final int status_down = 2;

    private int colorBlue = ContextCompat.getColor(BaseApplication.getInstance(), R.color.text_blue);
    private int colorGray = ContextCompat.getColor(BaseApplication.getInstance(), R.color.text_gray);

    private String[] titleText = {"证券代码","最新价", "前收盘价","涨幅", "基础证券代码","基础证券名称","基础证券最新价", "基础证券前收盘价", "基础证券涨幅", "溢价"};

    private String[] codeDR_param = {"0,20,0,1", "0,20,0,0"};
    private String[] lasePriceDR_param = {"0,20,2,1", "0,20,2,0"};
    private String[] preClosePriceDR_param = {"0,20,3,1", "0,20,3,0"};
    private String[] changeRateDR_param = {"0,20,4,1", "0,20,4,0"};
//    private String[] dateTimeDR_param = {"0,20,5,1", "0,20,5,0"};
    private String[] codeBaseDR_param = {"0,20,5,1", "0,20,5,0"};
    private String[] nameBaseDR_param = {"0,20,6,1", "0,20,6,0"};
    private String[] lasePriceBaseDR_param = {"0,20,7,1", "0,20,7,0"};
    private String[] preClosePriceBaseDR_param = {"0,20,8,1", "0,20,8,0"};
    private String[] changeRateBaseDR_param = {"0,20,9,1", "0,20,9,0"};
//    private String[] dateTimeBaseDR_param = {"0,20,11,1", "0,20,11,0"};
    private String[] preMium_param = {"0,20,11,1", "0,20,11,0"};

    private String[][] sortParam={codeDR_param,lasePriceDR_param,preClosePriceDR_param,changeRateDR_param,codeBaseDR_param,nameBaseDR_param,lasePriceBaseDR_param,preClosePriceBaseDR_param,changeRateBaseDR_param,preMium_param};
    private TextView[] mTextViews;

    private DRQuoteListAdapter mDRQuoteListAdapter;

    private int currentPage = 0;
    private String currentCode;
    private String currentParam =lasePriceDR_param[0];


    public static void startActivity(Context context, String stockType, String title){
        Intent intent=new Intent(context,DRQuoteListActivity.class);
        intent.putExtra(TYPE, title);
        context.startActivity(intent);
    }
    @Override
    protected int getLayoutId() {
        return R.layout.activity_quote_list_dr;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        //获取标题
        String title = getIntent().getStringExtra(TYPE);
        if (title.equals("CDR溢价")){
            currentCode="cdr";
        }else {
            currentCode="gdr";
        }
        topbar.setTitle(title);

        mTextViews=new TextView[]{tevCodeDR,tevLastPriceDR,tevPreClosePriceDR,tevChangeRateDR,tevCodeBaseDR,tevNameBaseDR,tevLastPriceBaseDR,tevPreClosePriceBaseDR,tevChangeRateBaseDR,tevPreMium};

        ((HVListView) lsvContent.getRefreshableView()).setScrollView(scrollTitle);
        lsvContent.setMode(PullToRefreshBase.Mode.BOTH);
        mDRQuoteListAdapter = new DRQuoteListAdapter(this);
        lsvContent.setAdapter(mDRQuoteListAdapter);

        for (int i = 0; i < mTextViews.length; i++) {
            TextView textView = mTextViews[i];
            if( 0 == i ){
                textView.setText(titleText[i]+"↓");
                textView.setTextColor(colorBlue);
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
                        SearchHistoryActivity.startActivity(DRQuoteListActivity.this);
                        break;
                }
            }
        });

        mDRQuoteListAdapter.setDRItemClick(new DRQuoteListAdapter.DRItemClick() {
            @Override
            public void onDRItemClick(ViewGroup parent, View view, int position, boolean isDQuote) {
                if( isDQuote ){
                    StockDetailActivity.startActivity(DRQuoteListActivity.this,
                            mDRQuoteListAdapter.getDQuoteData(), position);
                }else{
                    StockDetailActivity.startActivity(DRQuoteListActivity.this,
                            mDRQuoteListAdapter.getRQuoteData(), position);
                }
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
                mPresenter.requestPullDownDRQuoteList(currentCode,currentParam);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                String[] param = currentParam.split(",");
                if( param.length >=4 ){
                    currentParam = (currentPage + 1) + "," + param[1] + "," + param[2] + "," + param[3];
                }
                mPresenter.requestPullUpDRQuoteList(currentCode,currentParam);
            }
        });

        for (int i = 0; i < mTextViews.length; i++) {
            final int finalI = i;
            mTextViews[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    convertAllToNomal();
                    currentPage = 0;
                    mTextViews[finalI].setTextColor(colorBlue);
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
                    mPresenter.requestDRQuoteList(currentCode,currentParam);
                }
            });
        }
    }
    @Override
    protected void initData() {
        mPresenter.requestDRQuoteList(currentCode,currentParam);
    }

    private void convertAllToNomal() {
        for (int i = 0; i < mTextViews.length; i++) {
            mTextViews[i].setText(titleText[i]);
            mTextViews[i].setTextColor(colorGray);
        }
    }
    /**
     * 请求DR列表成功
     * @param drQuoteItemList
     */
    @Override
    public void requestDRQuoteListSuccess(List<DRQuoteItem> drQuoteItemList) {
        lsvContent.onRefreshComplete();
        mDRQuoteListAdapter.setData(drQuoteItemList);
    }

    @Override
    public void requestPullDownDRQuoteListSuccess(List<DRQuoteItem> drQuoteItemList) {
        lsvContent.onRefreshComplete();
        if (currentPage >= 1) {
            currentPage = currentPage - 1;
        } else {
            currentPage = 0;
        }
        mDRQuoteListAdapter.setData(drQuoteItemList);
    }

    @Override
    public void requestPullUpDRQuoteListSuccess(List<DRQuoteItem> drQuoteItemList) {
        lsvContent.onRefreshComplete();
        currentPage = currentPage + 1;
        mDRQuoteListAdapter.setData(drQuoteItemList);
    }

    @Override
    public void requestDRQuoteListFail() {
        lsvContent.onRefreshComplete();
    }

}
