package com.cvicse.stock.news.ui;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.cvicse.annotations.MVPInject;
import com.cvicse.stock.R;
import com.cvicse.stock.base.PBaseFragment;
import com.cvicse.stock.news.adapter.ViewPagerAdapter;
import com.cvicse.stock.news.presenter.contract.NewsContract;

import java.util.ArrayList;
import java.util.List;

import static com.mitake.core.request.NewsType.NewsTypeBond;
import static com.mitake.core.request.NewsType.NewsTypeFinance;
import static com.mitake.core.request.NewsType.NewsTypeForeignExchange;
import static com.mitake.core.request.NewsType.NewsTypeFund;
import static com.mitake.core.request.NewsType.NewsTypeFuture;
import static com.mitake.core.request.NewsType.NewsTypeGold;
import static com.mitake.core.request.NewsType.NewsTypeImportant;
import static com.mitake.core.request.NewsType.NewsTypeIndustry;
import static com.mitake.core.request.NewsType.NewsTypeOthers;
import static com.mitake.core.request.NewsType.NewsTypeRoll;
import static com.mitake.core.request.NewsType.NewsTypeStock;

/**
 *
 * Created by ruan_ytai on 2017/1/4 11:34
 */
public class NewsFragment extends PBaseFragment implements NewsContract.View{

    @MVPInject
    NewsContract.Presenter presenter;
    //String stocId = "000",其他
    //int updateType = -1时，newsId可为null(更新最新新闻列表资料),5,6(5,6用到newsId)
    //String newsId
    private TabLayout tabTitle;
    private ViewPager vipContent;
    private List<Fragment> mFragmentList;
    private String[] tableTitleArray = {"要闻","滚动","财经","行业","股票","期货","外汇","基金","债券","黄金","其他"};
    /**
     * 新闻类型
     */
    private String[] newsType = { NewsTypeImportant,NewsTypeRoll,NewsTypeFinance,NewsTypeIndustry,
            NewsTypeStock,NewsTypeFuture,NewsTypeForeignExchange,NewsTypeFund,NewsTypeBond,
            NewsTypeGold,NewsTypeOthers };

    private View view;

    public static NewsFragment newInstance() {
        return new NewsFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_news_main;
    }


    @Override
    public void onResume() {
        super.onResume();
        //netRequest();
        //netRequest("99",-1,null);
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        view = getView();

        //DaggerNewsComponent.builder().newsModule(new NewsModule(this)).build().inject(this);

        tabTitle = (TabLayout) view.findViewById(R.id.tab_title);
        vipContent = (ViewPager) view.findViewById(R.id.vip_content);
        mFragmentList = new ArrayList<>();
        vipContent.setOffscreenPageLimit(3);
        vipContent.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            //滑动中运行
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            //滑动到某个页面执行
            @Override
            public void onPageSelected(int position) {

            }

            //状态改变时候调用
            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        setupWithVip();
    }

    @Override
    protected void initData() {

    }

    /**
     *
     */
    private void setupWithVip() {
        for(int i = 0; i<tableTitleArray.length;i++) {
            tabTitle.addTab(tabTitle.newTab().setText(tableTitleArray[i]));
            mFragmentList.add(NewsViewPagerFragment.newInstance(newsType[i]));
        }
        //设置标签的模式，有Fixed固定模式和Scrollable滑动模式
        tabTitle.setTabMode(TabLayout.MODE_SCROLLABLE);

        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager(),mFragmentList,tableTitleArray);
        vipContent.setAdapter(adapter);
        tabTitle.setupWithViewPager(vipContent);
    }
}
