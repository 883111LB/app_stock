package com.cvicse.stock.portfolio.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.cvicse.stock.R;
import com.cvicse.stock.base.PBaseActivity;
import com.cvicse.stock.base.ToolBar;
import com.cvicse.stock.news.adapter.ViewPagerAdapter;
import com.cvicse.stock.portfolio.fragment.NewsFragement;
import com.cvicse.stock.portfolio.fragment.NoticeFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 新闻公告页面
 */
public class NewsAndReportActivity extends PBaseActivity {
    TabLayout mTabTitle;
    @BindView(R.id.vip_content)
    ViewPager mVipContent;
    @BindView(R.id.topbar) ToolBar topbar;

    private List<Fragment> mFragmentList;
    private String[] tableTitleArray = {"新闻", "公告"};

    /**
     * 启动查找页面
     *
     * @param activity
     */
    public static void startActivity(Activity activity) {
        activity.startActivity(new Intent(activity, NewsAndReportActivity.class));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_news;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        View view = topbar.setCenterId(R.layout.layout_toolbar_tablayout);
        mTabTitle = (TabLayout) view.findViewById(R.id.tab_title);
        mFragmentList = new ArrayList<>();

        for (int i = 0; i < tableTitleArray.length; i++) {
            mTabTitle.addTab(mTabTitle.newTab().setText(tableTitleArray[i]));
        }

        mFragmentList.add(NewsFragement.newInstance());
        mFragmentList.add(NoticeFragment.newInstance());

        //设置标签的模式，有Fixed固定模式和Scrollable滑动模式
        mTabTitle.setTabMode(TabLayout.MODE_FIXED);

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(), mFragmentList, tableTitleArray);
        mVipContent.setAdapter(adapter);
        mTabTitle.setupWithViewPager(mVipContent);
    }

    @Override
    protected void initData() {
    }


   /* *//**
     * 返回上一级菜单
     *//*
    @OnClick(R.id.fl_back)
    public void onClick() {
           finish();
    }*/

}
