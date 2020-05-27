package com.cvicse.stock.markets.ui.stockdetail;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.widget.TextView;

import com.cvicse.stock.R;
import com.cvicse.stock.base.PBaseActivity;
import com.cvicse.stock.pulltorefresh.PullToRefreshHSListView;
import com.mitake.core.request.CategoryType;

import butterknife.BindView;

/**
 * 看资金
 * Created by tang_xqing on 18-05-15.
 */
public class SectionMainNetInflowActivity extends PBaseActivity {

    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.tev_ultralNetInflow)
    TextView tevUltralNetInflow;
    @BindView(R.id.tev_largeNetInflow)
    TextView tevLargeNetInflow;
    @BindView(R.id.tev_mediumNetInflow)
    TextView tevMediumNetInflow;
    @BindView(R.id.tev_smallNetInflow)
    TextView tevSmallNetInflow;
    @BindView(R.id.tev_bbd)
    TextView tevBbd;
    @BindView(R.id.tev_bbdFive)
    TextView tevBbdFive;
    @BindView(R.id.tev_bbdTen)
    TextView tevBbdTen;
    @BindView(R.id.tev_ddx)
    TextView tevDdx;
    @BindView(R.id.tev_ddxFive)
    TextView tevDdxFive;
    @BindView(R.id.tev_ddxTen)
    TextView tevDdxTen;
    @BindView(R.id.tev_ddy)
    TextView tevDdy;
    @BindView(R.id.tev_ddyFive)
    TextView tevDdyFive;
    @BindView(R.id.tev_ddyTen)
    TextView tevDdyTen;
    @BindView(R.id.tev_netCapitalInflow)
    TextView tevNetCapitalInflow;
    @BindView(R.id.lsv_content)
    PullToRefreshHSListView lsvContent;

    private String[] titleText={"超大单净入","大单净入","中单净入","小单净入","大单净差","5日大净差","10日大净差","主力动向","5日主力动","10日主力动","涨跌动因","5日涨","10日涨","主力净入"};
    private String[] titleTab = {"概念","地域","行业","港股行业"};
    private String[] categoryType = {CategoryType.CATE_NOTION,CategoryType.CATE_AREA,CategoryType.CATE_TRADE,CategoryType.HK_TRADE};
    private int currentPosition = 0;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_section_main_net_inflow;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        for (int i = 0; i < titleTab.length; i++) {
            tabLayout.addTab(tabLayout.newTab().setText(titleTab[i]));
        }

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                currentPosition = tab.getPosition();
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
}
