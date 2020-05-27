package com.cvicse.stock.markets.ui;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.cvicse.base.skin.ThemeUtils;
import com.cvicse.stock.R;
import com.cvicse.stock.base.PBaseFragment;
import com.cvicse.stock.news.adapter.ViewPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;



/**
 * 行情模块主页
 * Created by liu_zlu on 2017/1/3 16:52
 */
public class MarketsFragment extends PBaseFragment {

    @BindView(R.id.tab_title) TabLayout mTabTitle;
    @BindView(R.id.vip_content) ViewPager mVipContent;

    private View view;
    private String[] tableTitleArray = {"沪深","板块","港股","港股通","更多"};
    private List<Fragment> fragmentList = new ArrayList();

    public static MarketsFragment newInstance() {
        return new MarketsFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_markets_main;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        for(int i=0;i<tableTitleArray.length;i++){
            mTabTitle.addTab(mTabTitle.newTab().setText(tableTitleArray[i]));
        }
        fragmentList.add(SHSZFragment.newInstance());
        fragmentList.add(SectionFragment.newInstance());

        fragmentList.add(HKFragment.newInstance());
        fragmentList.add(HKTFragment.newInstance());
        fragmentList.add(MoreSortFragment.newInstance());

        mTabTitle.setTabMode(TabLayout.MODE_FIXED);//固定模式
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager(),fragmentList,tableTitleArray);
        mVipContent.setAdapter(adapter);
        mTabTitle.setupWithViewPager(mVipContent);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void tint() {
        super.tint();
        for(Fragment fragment:fragmentList){
            ThemeUtils.refreshUI(fragment);
        }
    }
}
