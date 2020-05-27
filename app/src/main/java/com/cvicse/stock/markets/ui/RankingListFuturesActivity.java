package com.cvicse.stock.markets.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.cvicse.stock.R;
import com.cvicse.stock.base.PBaseActivity;
import com.cvicse.stock.base.ToolBar;
import com.cvicse.stock.markets.data.Param;
import com.cvicse.stock.markets.data.ParamUtil;
import com.cvicse.stock.seachstock.SearchHistoryActivity;

import java.util.ArrayList;
import java.util.Map;

import butterknife.BindView;

import static com.cvicse.stock.R.id.tabLayout;
import static com.cvicse.stock.markets.data.ParamUtil.STOCK_NAME_ADDVALUE_FIVE;
import static com.cvicse.stock.markets.data.ParamUtil.STOCK_NAME_DCEQQ;
import static com.cvicse.stock.markets.data.ParamUtil.STOCK_NAME_QHDSS;
import static com.cvicse.stock.markets.data.ParamUtil.STOCK_NAME_QHSQS;
import static com.cvicse.stock.markets.data.ParamUtil.STOCK_NAME_QHSQSYY;
import static com.cvicse.stock.markets.data.ParamUtil.STOCK_NAME_QHZJS;
import static com.cvicse.stock.markets.data.ParamUtil.STOCK_NAME_QHZSS;
import static com.cvicse.stock.markets.data.ParamUtil.STOCK_NAME_ZCEQQ;

/**
 * 期货排行榜页面（中金所、大商所、郑商所、全球、外汇）
 * Created by tang_xqing on 18-7-27.
 */
public class RankingListFuturesActivity extends PBaseActivity {

    @BindView(R.id.topbar)
    ToolBar topbar;
    @BindView(tabLayout)
    TabLayout mTabLayout;
    @BindView(R.id.frl_sort)
    FrameLayout frlSort;

    private final static String STOCK_TYPE = "mStockType";

    private FragmentManager mFragmentManager;
    private FragmentTransaction mFragmentTransaction;
    private RankingListFuturesFragment mFuturesFragment;
    private ArrayList<Param> mParamArrayList;
    private String mStockType;
    private Param mParam;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_ranking_list_futures;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        mStockType = getIntent().getStringExtra(STOCK_TYPE);
        topbar.setTitle(mStockType);

        if (STOCK_NAME_QHZJS.equals(mStockType) || STOCK_NAME_QHDSS.equals(mStockType)
                ||STOCK_NAME_DCEQQ.equals(mStockType)||STOCK_NAME_ZCEQQ.equals(mStockType)
                || STOCK_NAME_QHZSS.equals(mStockType) ||STOCK_NAME_QHSQS.equals(mStockType)
                ||STOCK_NAME_QHSQSYY.equals(mStockType) || STOCK_NAME_ADDVALUE_FIVE.equals(mStockType)) {
            Map<String, ArrayList<Param>> listMap = ParamUtil.getInstance().getQHParamMap();
            mParamArrayList = listMap.get(mStockType);
            mParam = mParamArrayList.get(0);
            initView();
        } else {
            mTabLayout.setVisibility(View.GONE);
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
            params.addRule(RelativeLayout.BELOW,topbar.getId());
            frlSort.setLayoutParams(params);
            mParam = ParamUtil.getInstance().getParamMap().get(mStockType);
        }

        mFuturesFragment = RankingListFuturesFragment.newInstance();
        mFragmentManager = getSupportFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.add(R.id.frl_sort, mFuturesFragment);
        mFragmentTransaction.commitAllowingStateLoss();

        mFuturesFragment.setStockType(mParam);
        initClickListener();
    }

    /**
     * 添加期货排序页底部子分类
     */
    private void initView() {
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams)frlSort.getLayoutParams();
        layoutParams.addRule(RelativeLayout.ABOVE,mTabLayout.getId());
        frlSort.setLayoutParams(layoutParams);

        for (int i = 0; i < mParamArrayList.size(); i++) {
            mTabLayout.addTab(mTabLayout.newTab().setText(mParamArrayList.get(i).getStockName()));
        }

        if (mParamArrayList.size() <= 3) {
            mTabLayout.setTabMode(TabLayout.MODE_FIXED);
        } else {
            mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);//滑动模式
        }
        mTabLayout.getTabAt(0).select();  // 默认选中第一个
    }

    private void initClickListener() {
        topbar.setToolBarClickListener(new ToolBar.ToolBarClickListener() {
            @Override
            public void onClick(View view, ToolBar.Type type) {
                switch (type) {
                    case RIGHT_TYPE_1:
                        SearchHistoryActivity.startActivity(RankingListFuturesActivity.this);
                        break;
                }
            }
        });

        mTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                mParam = mParamArrayList.get(position);
                mFuturesFragment.setStockType(mParam);   // 排序代码发生改变
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    protected void initData() {

    }

    public static void actionStart(Context context, String stockType) {
        Intent intent = new Intent(context, RankingListFuturesActivity.class);
        intent.putExtra(STOCK_TYPE, stockType);
        context.startActivity(intent);
    }
}
