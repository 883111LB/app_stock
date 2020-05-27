package com.cvicse.stock.markets.ui.stockdetail;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.cvicse.annotations.MVPInject;
import com.cvicse.base.utils.SizeUtils;
import com.cvicse.stock.R;
import com.cvicse.stock.base.PBaseActivity;
import com.cvicse.stock.base.ToolBar;
import com.cvicse.stock.common.ColorUtils;
import com.cvicse.stock.markets.data.Param;
import com.cvicse.stock.markets.data.StockFinanceUtil;
import com.cvicse.stock.markets.helper.StockFinanceDetailDataHelper;
import com.cvicse.stock.markets.presenter.marketdetail.contract.StockFinanceDetailContract;
import com.mitake.core.MainFinaDataNas;
import com.mitake.core.QuoteItem;
import com.mitake.core.request.F10Type;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;

/**
 * 财务报表详细列表（主要指标、利润表、资产负债表、现金流量表）
 */
public class StockFinanceActivity extends PBaseActivity implements StockFinanceDetailContract.View  {

    @BindView(R.id.topbar)
    ToolBar topbar;
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;

    @BindView(R.id.lly_chl)
    LinearLayout llyChl;
    @BindView(R.id.lly_detail)
    LinearLayout llyDetail;
    @BindView(R.id.rab_all)
    RadioButton rabAll;
    @BindView(R.id.rab_four)
    RadioButton rabFour;
    @BindView(R.id.rab_two)
    RadioButton rabTwo;
    @BindView(R.id.rab_one)
    RadioButton rabOne;
    @BindView(R.id.rab_three)
    RadioButton rabThree;
    @BindView(R.id.rag_bottom)
    RadioGroup ragBottom;
    @BindView(R.id.lly_title)
    LinearLayout llyTitle;

    @MVPInject
    StockFinanceDetailContract.Presenter presenter;

    public final static String STOCKID = "STOCKID";
    public final static String FINANCE_TYPE = "finance_type";
    public final String[] mTabTitle ={"主要指标","利润表","资产负债表","现金流量表"};

    private String mApiType;  // 请求类型
    private String mQuarterType;  // 季度类型
    private int mTypePosition;

    private QuoteItem mQuoteItem;
    private StockFinanceDetailDataHelper mDetailDataHelper;
    private StockFinanceUtil mStockFinanceUtil;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_stock_finance;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        Intent intent = getIntent();
        mQuoteItem = intent.getParcelableExtra(STOCKID);
        mTypePosition = intent.getIntExtra(FINANCE_TYPE,1);

        for (int i = 0; i < mTabTitle.length; i++) {
            tabLayout.addTab(tabLayout.newTab().setText(mTabTitle[i]));
        }
        tabLayout.setTabMode(TabLayout.MODE_FIXED);//固定模式
        tabLayout.getTabAt(mTypePosition).select();

        setApiType(mTypePosition);

        mStockFinanceUtil = StockFinanceUtil.getInstance(mQuoteItem);
        mDetailDataHelper = new StockFinanceDetailDataHelper();

        addViewByType();

        ragBottom.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rab_all:
                        mQuarterType = StockFinanceUtil.EPSBASIC_0;break;
                    case R.id.rab_four:
                        mQuarterType = StockFinanceUtil.EPSBASIC_4;break;
                    case R.id.rab_one:
                        mQuarterType = StockFinanceUtil.EPSBASIC_1;break;
                    case R.id.rab_three:
                        mQuarterType = StockFinanceUtil.EPSBASIC_3;break;
                    case R.id.rab_two:
                        mQuarterType = StockFinanceUtil.EPSBASIC_2;break;
                }
                presenter.requestFinanceDetail(mQuarterType, mApiType);
            }
        });

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                setApiType(tab.getPosition());
                addViewByType();
                llyChl.removeAllViews();
                presenter.requestFinanceDetail(mQuarterType, mApiType);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        topbar.setToolBarClickListener(new ToolBar.ToolBarClickListener() {
            @Override
            public void onClick(View view, ToolBar.Type type) {
                switch (type){
                    case LEFT_TYPE:
                        onBackPressed();break;
                    default:break;
                }
            }
        });
    }

    @Override
    protected void initData() {
        presenter.init(mQuoteItem.id, mQuoteItem.market);
        presenter.requestFinanceDetail(mQuarterType, mApiType);
    }

    public static void startActivity(Context context,QuoteItem quoteItem,int type){
        Intent intent = new Intent(context,StockFinanceActivity.class);
        intent.putExtra(STOCKID,quoteItem);
        intent.putExtra(FINANCE_TYPE,type);
        context.startActivity(intent);
    }

    /**
     * 请求沪深财务报表
     * @param infoList
     */
    @Override
    public void requestFinanceDetailSuccess(List<HashMap<String, Object>> infoList) {
        if (null == infoList || infoList.isEmpty()) {
            return;
        }
        llyChl.removeAllViews();
        // 根据不同的请求类型，获取不同的数据
        HashMap<String, List<String>> profinmainindexData = mDetailDataHelper.getFinanceDetailData(mApiType,infoList);
        String[] codeByType = mStockFinanceUtil.getCodeByQuarterType(mApiType);
        for (int i = 0; i < codeByType.length; i++) {
            setViewData(profinmainindexData.get(codeByType[i]));
        }
    }

    @Override
    public void requestFinanceDetailFail(int code, String msg) {
        llyChl.removeAllViews();
        TextView textView = new TextView(this);
        textView.setLayoutParams(new LinearLayout.LayoutParams( LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        textView.setTextSize(SizeUtils.dp2px(this,8));
        textView.setTextColor(ColorUtils.DEFALUT());
        textView.setText("无相关信息");
        llyChl.addView(textView);
    }

    /**
     * 请求港股财务报表成功
     * @param mainFinaDataNasList
     */
    @Override
    public void requestHKFinanceDetailSuccess(List<MainFinaDataNas> mainFinaDataNasList) {
        if (null == mainFinaDataNasList || mainFinaDataNasList.isEmpty()) {
            return;
        }
        llyChl.removeAllViews();
        HashMap<String, List<String>> hkFinanceDetailData = mDetailDataHelper.getHKFinanceDetailData(mApiType, mainFinaDataNasList);
        String[] codeByType = mStockFinanceUtil.getCodeByQuarterType(mApiType);
        for (int i = 0; i < codeByType.length; i++) {
            setViewData(hkFinanceDetailData.get(codeByType[i]));
        }
    }

    @Override
    public void requestHKFinanceDetailFail(int code, String msg) {
        llyChl.removeAllViews();
        TextView textView = new TextView(this);
        textView.setLayoutParams(new LinearLayout.LayoutParams( LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        textView.setTextSize(SizeUtils.dp2px(this,8));
        textView.setTextColor(ColorUtils.DEFALUT());
        textView.setText("无相关信息");
        llyChl.addView(textView);
    }

    private void setApiType(int position){
        topbar.setTitle(mQuoteItem.name+"一"+ mTabTitle[position]);
        switch (position){
            case 0: mApiType = F10Type.D_PROFINMAININDEX;break;
            case 1: mApiType = F10Type.D_PROINCSTATEMENTNEW;break;
            case 2:
                mApiType = F10Type.D_PROBALSHEETNEW; break;
            case 3:
                mApiType = F10Type.D_PROCFSTATEMENTNEW; break;
        }
    }

    /**
     * 根据接口返回的数据，动态添加数据
     * @param reportList
     */
    private void setViewData(List<String> reportList) {
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, SizeUtils.dp2px(this, 40)));
        for (String s : reportList) {
            TextView textView = new TextView(this);
            textView.setLayoutParams(new LinearLayout.LayoutParams(SizeUtils.dp2px(this, 120), LinearLayout.LayoutParams.MATCH_PARENT));
            textView.setText(s);
            textView.setGravity(Gravity.CENTER);
            textView.setTextColor(ColorUtils.GRAY);
            linearLayout.addView(textView);
        }
        llyChl.addView(linearLayout);
    }

    /**
     * 添加标题（左侧列表）
     */
    private void addViewByType() {
        llyTitle.removeAllViews();

        List<Param> dataListByType = mStockFinanceUtil.getDataListByType(mApiType);
        CheckBox checkBox  = new CheckBox(this);
        checkBox.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,SizeUtils.dp2px(this, 40)));
        checkBox.setVisibility(View.INVISIBLE);
        llyTitle.addView(checkBox);

        for (Param param : dataListByType) {
            TextView textView = new TextView(this);
            textView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,SizeUtils.dp2px(this, 40)));
            textView.setText(param.getSortParam());
            textView.setTextColor(ColorUtils.PRIMARY);
            textView.setGravity(Gravity.CENTER_VERTICAL);
            textView.setTag(param.getStockCode());

            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String codeType = (String) v.getTag();
                    StockFinanceDetailActivity.startActivity(StockFinanceActivity.this, mQuoteItem, mApiType,codeType);
                }
            });
            llyTitle.addView(textView);
        }
    }
}
