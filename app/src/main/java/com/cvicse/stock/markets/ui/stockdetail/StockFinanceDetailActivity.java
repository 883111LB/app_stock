package com.cvicse.stock.markets.ui.stockdetail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.cvicse.annotations.MVPInject;
import com.cvicse.base.utils.SizeUtils;
import com.cvicse.stock.R;
import com.cvicse.stock.base.PBaseActivity;
import com.cvicse.stock.base.ToolBar;
import com.cvicse.stock.common.ColorUtils;
import com.cvicse.stock.markets.data.FinanceChartData;
import com.cvicse.stock.markets.data.StockFinanceUtil;
import com.cvicse.stock.markets.helper.StockFinanceChartHelper;
import com.cvicse.stock.markets.helper.StockFinanceDetailDataHelper;
import com.cvicse.stock.markets.presenter.marketdetail.contract.StockFinanceDetailContract;
import com.cvicse.stock.util.FormatUtils;
import com.cvicse.stock.utils.TextUtils;
import com.mitake.core.MainFinaDataNas;
import com.mitake.core.QuoteItem;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;

/**
 * 财务对比，图表界面
 */
public class StockFinanceDetailActivity extends PBaseActivity implements StockFinanceDetailContract.View {

    @MVPInject
    StockFinanceDetailContract.Presenter presenter;

    @BindView(R.id.topbar)
    ToolBar mTopbar;
    @BindView(R.id.tabLayout)
    TabLayout mTabLayout;
    @BindView(R.id.fl_data)
    LinearLayout mFlData;
    @BindView(R.id.rag_bottom)
    RadioGroup mRagBottom;
    @BindView(R.id.fl_chart)
    FrameLayout mFlChart;    // 图表
    @BindView(R.id.rag_report)
    RadioGroup mRagReport;   // 单季度、报告期

    public final static String STOCKID = "STOCKID";
    public static final String FINANCE_QUARTER_TYPE = "finance_quarter_type";
    public static final String FINANCE_API_TYPE = "finance_api_type";

    private int mPosition = 0;
    private String mApiType;   // 请求类型
    private String mQuarterType;  // 季度类型
    private String[] mCodeByQuarterType;  // 字段类型
    private String[] mTitleByQuarterType;  // 字段名
    private String mQuarterPosition = "_0";
    private QuoteItem mQuoteItem;
    private StockFinanceUtil mStockFinanceUtil;   // 静态字段帮助类
    private StockFinanceDetailDataHelper mDetailHelper;  // 数值转换帮助类
    private StockFinanceChartHelper mFinanceChartHelper;   // 图表帮助类

    @Override
    protected int getLayoutId() {
        return R.layout.activity_stock_finance_detail;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        init();
        initView();
        initClickListener();
    }

    private void initClickListener() {
        mTopbar.setToolBarClickListener(new ToolBar.ToolBarClickListener() {
            @Override
            public void onClick(View view, ToolBar.Type type) {
                switch (type) {
                    case LEFT_TYPE:
                        onBackPressed();
                        break;
                    default:
                        break;
                }
            }
        });

        mTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mPosition = tab.getPosition();
                mQuarterType = mCodeByQuarterType[mPosition + 1];

                mFlChart.removeAllViews();
                mFlData.removeAllViews();
                presenter.requestFinanceDetailByQuarterType(mQuarterType + mQuarterPosition, mApiType);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        mRagReport.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // 单季度、报告期
                switch (checkedId){
                    // 报告期
                    case R.id.rdb_report_all:
                        mFinanceChartHelper.changeType("report_all");
                        break;
                    // 单季度
                    case R.id.rdb_report_single:
//                        mFinanceChartHelper.changeType("report_single");
                        mFinanceChartHelper.changeType("report_all");
                        break;
                }
            }
        });

        mRagBottom.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rab_all:
                        mQuarterPosition = "_0";
                        mRagReport.setVisibility(View.VISIBLE);
                        break;
                    case R.id.rab_four:
                        mRagReport.setVisibility(View.GONE);
                        mQuarterPosition = "_4";
                        break;
                    case R.id.rab_one:
                        mRagReport.setVisibility(View.GONE);
                        mQuarterPosition = "_1";
                        break;
                    case R.id.rab_three:
                        mRagReport.setVisibility(View.GONE);
                        mQuarterPosition = "_3";
                        break;
                    case R.id.rab_two:
                        mRagReport.setVisibility(View.GONE);
                        mQuarterPosition = "_2";
                        break;
                }

                mFlChart.removeAllViews();
                mFlData.removeAllViews();
                presenter.requestFinanceDetailByQuarterType(mQuarterType + mQuarterPosition, mApiType);
            }
        });
    }

    private void initView() {
        mTopbar.setTitle(mQuoteItem.name + "一" + mStockFinanceUtil.getNameByApiType(mApiType));

        mTitleByQuarterType = mStockFinanceUtil.getTitleByQuarterType(mApiType);
        mCodeByQuarterType = mStockFinanceUtil.getCodeByQuarterType(mApiType);
        for (int i = 0; i < mTitleByQuarterType.length; i++) {
            mTabLayout.addTab(mTabLayout.newTab().setText(mTitleByQuarterType[i]));
            if (mQuarterType.equals(mCodeByQuarterType[i + 1])) {
                mPosition = i;
            }
        }
        if( mTitleByQuarterType.length < 3 ){
            mTabLayout.setTabMode(TabLayout.MODE_FIXED);
        }else{
            mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);//滑动模式
        }
        recomputeTlOffset1(mPosition);   // 根据选中的位置，计算偏移量并滚动
    }

    private void init() {
        Intent intent = getIntent();
        mQuoteItem = intent.getParcelableExtra(STOCKID);
        mApiType = intent.getStringExtra(FINANCE_API_TYPE);
        mQuarterType = intent.getStringExtra(FINANCE_QUARTER_TYPE);

        mStockFinanceUtil = StockFinanceUtil.getInstance(mQuoteItem);
        mDetailHelper = new StockFinanceDetailDataHelper();
        mFinanceChartHelper = new StockFinanceChartHelper(this, mFlChart);
    }

    @Override
    protected void initData() {
        presenter.init(mQuoteItem.id, mQuoteItem.market);
        presenter.requestFinanceDetailByQuarterType(mQuarterType + mQuarterPosition, mApiType);
    }

    public static void startActivity(Context context, QuoteItem quoteItem, String apiType, String quarterType) {
        Intent intent = new Intent(context, StockFinanceDetailActivity.class);
        intent.putExtra(STOCKID,quoteItem);
        intent.putExtra(FINANCE_API_TYPE, apiType);
        intent.putExtra(FINANCE_QUARTER_TYPE, quarterType);
        context.startActivity(intent);
    }

    /**
     * 请求沪深财务对比数据成功
     * @param infoList
     */
    @Override
    public void requestFinanceDetailSuccess(List<HashMap<String, Object>> infoList) {
        String title = mTitleByQuarterType[mPosition];  // 字段名
        List<FinanceChartData> financeChartData = mDetailHelper.getFinanceChartData(mCodeByQuarterType[mPosition + 1], infoList);
        addChartView(title, financeChartData);
    }

    /**
     * 添加视图
     * @param title
     * @param financeChartData
     */
    private void addChartView(String title, List<FinanceChartData> financeChartData) {
        // 添加图表
        mFinanceChartHelper.setData(financeChartData,title);
        mFinanceChartHelper.changeQuarterType(mQuarterPosition);

        // 添加数据
        String text = "环比增减幅";
        if (!"_0".equals(mQuarterPosition)) {
            text = "同比增减幅";
        }
        LayoutInflater inflater = LayoutInflater.from(this);
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.item_finance_detail, mFlData, false);
        TextUtils.setText((TextView) view.findViewById(R.id.tev_finance_report_date), "报告日期");
        TextUtils.setText((TextView) view.findViewById(R.id.tev_finance_value), title);
        TextUtils.setText((TextView) view.findViewById(R.id.tev_finance_memo), text);
        mFlData.addView(view);

        for (int i = 0; i < financeChartData.size(); i++) {
            FinanceChartData data = financeChartData.get(i);
            inflater = LayoutInflater.from(this);
            view = (ViewGroup) inflater.inflate(R.layout.item_finance_detail, mFlData, false);
            TextUtils.setText((TextView) view.findViewById(R.id.tev_finance_report_date),data.getDate() );
            TextUtils.setText((TextView) view.findViewById(R.id.tev_finance_value),data.getValueStr());
            TextView tevMom = (TextView) view.findViewById(R.id.tev_finance_memo);
            String mom = data.getMom();
            TextUtils.setText(tevMom, mom);
            if (!FormatUtils.isStandard(mom) || "0.0%".equals(mom)) {
                tevMom.setTextColor(ColorUtils.DEFALUT());
            } else if (mom.startsWith("-") && mom.endsWith("%")) {
                tevMom.setTextColor(ColorUtils.DOWN);
            } else {
                tevMom.setTextColor(ColorUtils.UP);
            }
            mFlData.addView(view);
        }
    }

    @Override
    public void requestFinanceDetailFail(int code, String msg) {
        TextView textView = new TextView(this);
        textView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        textView.setTextSize(SizeUtils.dp2px(this, 6));
        textView.setTextColor(ColorUtils.DEFALUT());
        textView.setText("无相关信息");
        mFlData.addView(textView);
    }

    /**
     * 请求港股财务报表对比数据成功
     * @param mainFinaDataNasList
     */
    @Override
    public void requestHKFinanceDetailSuccess(List<MainFinaDataNas> mainFinaDataNasList) {
        String title = mTitleByQuarterType[mPosition];  // 字段名
        List<FinanceChartData> financeChartData = mDetailHelper.getHKFinanceChartData(mCodeByQuarterType[mPosition + 1], mainFinaDataNasList);

        addChartView(title, financeChartData);
    }

    @Override
    public void requestHKFinanceDetailFail(int code, String msg) {
        TextView textView = new TextView(this);
        textView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        textView.setTextSize(SizeUtils.dp2px(this, 6));
        textView.setTextColor(ColorUtils.DEFALUT());
        textView.setText("无相关信息");
        mFlData.addView(textView);
    }

    /**
     * 根据每个字符计算偏移量
     * @param index
     * @return
     */
    private int getTablayoutOffsetWidth(int index) {
        String str = "";
        for (int i = 0; i < index; i++) {
            //channelNameList是一个List<String>的对象，里面转载的是30个词条
            //取出直到index的tab的文字，计算长度
            str += mTitleByQuarterType[i];
        }
        return str.length() * 14 + index * 16;
    }

    /**
     * 计算需要滚动的距离
     *
     * @param index
     */
    private void recomputeTlOffset1(int index) {
        if (null != mTabLayout.getTabAt(index)) {
            mTabLayout.getTabAt(index).select();
        }
        final int width = (int) (getTablayoutOffsetWidth(index) * this.getResources().getDisplayMetrics().density);
        mTabLayout.post(new Runnable() {
            @Override
            public void run() {
                mTabLayout.smoothScrollTo(width, 0);
            }
        });
    }

}
