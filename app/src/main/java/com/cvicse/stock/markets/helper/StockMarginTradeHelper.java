package com.cvicse.stock.markets.helper;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.cvicse.base.utils.SizeUtils;
import com.cvicse.stock.R;
import com.cvicse.stock.common.ColorUtils;
import com.cvicse.stock.markets.adapter.MarginTradingAdapter;
import com.cvicse.stock.markets.data.MarginTradingBo;
import com.cvicse.stock.pulltorefresh.PullToRefreshBase;
import com.cvicse.stock.pulltorefresh.PullToRefreshHSListView;
import com.cvicse.stock.view.CHScrollView;
import com.cvicse.stock.view.HVListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/** 个股融资融券
 * Created by tang_xqing on 2018/8/4.
 */
public class StockMarginTradeHelper {
    @BindView(R.id.fly_chart)
    FrameLayout mFlyChart;
    @BindView(R.id.lly_sort_type)
    LinearLayout mLlySortType;
    @BindView(R.id.tev_name)
    TextView mTevtName;
    @BindView(R.id.scroll_title)
    CHScrollView mScrollView;
    @BindView(R.id.lsv_content)
    PullToRefreshHSListView mLsvContent;

    private Context mContext;
    private ArrayList<MarginTradingBo> mTradingBoList = new ArrayList<>();
    private MarginTradingAdapter mTradingAdapter;
    private MarginTradingHelper mTradingHelper;
    private StockMarginTradeChartHelper mStockMarginTradeChartHelper;  // 图表帮助类

    public StockMarginTradeHelper(View view, Context context){
        ButterKnife.bind(this, view);
        this.mContext = context;

        mTradingHelper = new MarginTradingHelper();
        initView();
        addView();
    }

    private void addView() {
        mLlySortType.removeAllViews();
        String[] stockMarginTradeKey = mTradingHelper.getStockMarginTradeKey();
        for (String key : stockMarginTradeKey) {
            TextView textView = new TextView(mContext);
            textView.setText(key);
            textView.setText(key);
            textView.setTextColor(ColorUtils.GRAY);
            textView.setGravity(Gravity.CENTER);
            textView.setLayoutParams(new LinearLayout.LayoutParams(SizeUtils.dp2px(mContext, 80), ViewGroup.LayoutParams.MATCH_PARENT));
            mLlySortType.addView(textView);
        }
    }

    private void initView() {

        mTevtName.setText("日期");
        mStockMarginTradeChartHelper = new StockMarginTradeChartHelper(mContext,mFlyChart);

        mTradingAdapter = new MarginTradingAdapter(mContext,true,mLsvContent.getRefreshableView());
        ((HVListView) mLsvContent.getRefreshableView()).setScrollView(mScrollView);
        mLsvContent.setMode(PullToRefreshBase.Mode.BOTH);
        mLsvContent.setAdapter(mTradingAdapter);

        mLsvContent.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {

            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                // TODO: 2018/8/4  加载更多
            }
        });
    }

    public void onStockMarginTradeData(List<MarginTradingBo> marginTradingBoList){
        if( null == marginTradingBoList || marginTradingBoList.isEmpty() ){
            return;
        }
        this.mTradingBoList = (ArrayList<MarginTradingBo>) marginTradingBoList;
        mTradingAdapter.setDataList((ArrayList<MarginTradingBo>) marginTradingBoList);

        mFlyChart.removeAllViews();
        mStockMarginTradeChartHelper.setMarginData(mTradingBoList);   // 图标数据
    }
}
