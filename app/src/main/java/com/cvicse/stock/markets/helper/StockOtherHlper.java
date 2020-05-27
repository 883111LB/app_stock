package com.cvicse.stock.markets.helper;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.cvicse.stock.R;
import com.cvicse.stock.markets.ui.StockDetailFragment;
import com.cvicse.stock.markets.ui.stockdetail.BondsOverviewFragment;
import com.cvicse.stock.markets.ui.stockdetail.BondsRepurchaseFragment;
import com.cvicse.stock.markets.ui.stockdetail.BoundsInterestFragment;
import com.cvicse.stock.markets.ui.stockdetail.FundBasicFragment;
import com.cvicse.stock.markets.ui.stockdetail.FundFinanceFragment;
import com.cvicse.stock.markets.ui.stockdetail.FundSummaryFragment;
import com.cvicse.stock.markets.ui.stockdetail.HKBasicFragment;
import com.cvicse.stock.markets.ui.stockdetail.HKSummaryFragment;
import com.cvicse.stock.markets.ui.stockdetail.IndexRankingFragment;
import com.cvicse.stock.markets.ui.stockdetail.StockBasicFragment;
import com.cvicse.stock.markets.ui.stockdetail.StockFinanceFragment;
import com.cvicse.stock.markets.ui.stockdetail.StockNBRFragment;
import com.cvicse.stock.markets.ui.stockdetail.StockShareFragment;
import com.cvicse.stock.markets.ui.stockdetail.StockSummaryFragment;
import com.cvicse.stock.markets.ui.stockdetail.StockTurnoverFragment;
import com.cvicse.stock.portfolio.activity.StockDetailActivity;
import com.cvicse.stock.pulltorefresh.PullToRefreshBase;
import com.cvicse.stock.pulltorefresh.PullToRefreshScrollView;
import com.cvicse.stock.utils.StockType;
import com.mitake.core.QuoteItem;

/**
 * Created by liu_zlu on 2017/2/7 21:24
 */
public class StockOtherHlper {
    //新闻
    private static final String TYPENEWS = StockDetailActivity.TYPE_NEWS;
    //公告
    private static final String TYPENOTICE = StockDetailActivity.TYPE_BULLETIN;
    //研报
    private static final String TYPEREPORT = StockDetailActivity.TYPE_REPORT;

    private static final String TYPEINCREASE = IndexRankingFragment.TYPEINCREASE;
    private static final String TYPEDECLINE = IndexRankingFragment.TYPEDECLINE;
    private static final String TYPETURNOVERRATE = IndexRankingFragment.TYPETURNOVERRATE;
    private static final String TYPETURNOVER = IndexRankingFragment.TYPETURNOVER;

    //默认状态
    private static String[] ordinary = new String[]{"新闻","公告","摘要","简况","财务","股东","研报"};

    // 沪深市场
    private static String[] shszOrdinary = new String[]{"新闻","成交","公告","摘要","简况","财务","股东","研报"};
    //指数
    private static String[] index = new String[]{"涨幅榜","跌幅榜","成交额榜"};
    //港股
    private static String[] hongkong = new String[]{"摘要","简况","财务","股东"};
    // 基金
    private static String[] fund = new String[]{"成交","公告","摘要","简况","财务"};

    // 债券
    private static String[] bunds = new String[]{"成交","公告","概况","付息","回购"};

    String[] tabs = null;
    TabLayout tabLayout;
    FragmentManager fragmentManager;

    Fragment[] fragments;
    StockType.Type marketType;
    PullToRefreshScrollView psvMarket;
    private QuoteItem quoteItem;

    public StockOtherHlper(PullToRefreshScrollView psvMarket, TabLayout tabLayout, FragmentManager fragmentManager, QuoteItem quoteItem){
        this.tabLayout = tabLayout;
        this.psvMarket = psvMarket;
        this.fragmentManager = fragmentManager;
        this.quoteItem = quoteItem;

        // 上证期权、中金所、全球、外汇、大商所、郑商所
        if(quoteItem.id.length() == 11 || StockType.getType(quoteItem).isOption() || StockType.getType(quoteItem).isCFF()||
                StockType.getType(quoteItem).isGI()|| StockType.getType(quoteItem).isFE()||
                StockType.getType(quoteItem).isDCE()|| StockType.getType(quoteItem).isZCE()||
                StockType.getType(quoteItem).isSHFE()|| StockType.getType(quoteItem).isINE()){
            tabLayout.setVisibility(View.GONE);
            psvMarket.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
            return;
        }
        marketType = StockType.getType(quoteItem);
        initTabs();
    }

    private void initTabs() {
        switch (marketType){
            case SH:
            case SZ:
                tabs = shszOrdinary;
                break;

            case BJ:
            case CFF:
            case SHFE:
            case INE:
                tabs = ordinary;
                break;

            case INDEX:
            case BK:
                tabs = index;
                psvMarket.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
                break;

            case HONGKONG:
                tabs = hongkong;
                break;

            case FUND:
                tabs = fund;
                break;

            case BONDS:
                tabs = bunds;
                break;
        }
        if (null == tabs) {
            return;
        }
        if(tabs.length < 6){
            tabLayout.setTabMode(TabLayout.MODE_FIXED);
        } else {
            tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        }

        for (String string:tabs){
            tabLayout.addTab(tabLayout.newTab().setText(string));
        }

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                showFragment(tab.getPosition());
                psvMarket.getRefreshableView().smoothScrollBy(0,1);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        fragments = new Fragment[tabs.length];
    }

    public void beginShow(){
        showFragment(0);
    }

    private Fragment getFragment(String tab){
        String stockId = quoteItem.id;
        switch (tab){
            case "新闻":
                StockDetailFragment.EVENT_STR = "新闻";
                return StockNBRFragment.newInstance(quoteItem,TYPENEWS);

            case "公告":
                StockDetailFragment.EVENT_STR = "公告";
                return StockNBRFragment.newInstance(quoteItem,TYPENOTICE);

            case "研报":
                StockDetailFragment.EVENT_STR = "研报";
                return StockNBRFragment.newInstance(quoteItem,TYPEREPORT);

            case "摘要":
                StockDetailFragment.EVENT_STR = "摘要";
                if(marketType == StockType.Type.FUND){
                    return FundSummaryFragment.newInstance(stockId);
                }
                if(marketType == StockType.Type.HONGKONG){
                    return HKSummaryFragment.newInstance(stockId);
                }
                return StockSummaryFragment.newInstance(quoteItem);

            case "简况":
                StockDetailFragment.EVENT_STR = "简况";
                //基金
                if(marketType == StockType.Type.FUND){
                    return FundBasicFragment.newInstance(stockId);
                }
                //港股
                if(marketType == StockType.Type.HONGKONG){
                    return HKBasicFragment.newInstance(stockId);
                }
                return StockBasicFragment.newInstance(stockId);

            case "财务":
                StockDetailFragment.EVENT_STR = "财务";
                if(marketType == StockType.Type.FUND){
                    return FundFinanceFragment.newInstance(stockId);
                }
//                else if(marketType == StockType.Type.HONGKONG){
//                    return StockFinanceFragmentOld.newInstance(quoteItem);
//                }
                return StockFinanceFragment.newInstance(quoteItem);

            case "股东":
                StockDetailFragment.EVENT_STR = "股东";
                String type = StockShareFragment.SZ;
                //港股
                if(marketType == StockType.Type.HONGKONG){
                    type = StockShareFragment.HONGKONG;
                }
                return StockShareFragment.newInstance(stockId,type);

            case "概况":
                StockDetailFragment.EVENT_STR = "概况";
                return BondsOverviewFragment.newInstance(stockId);

            case "付息":
                StockDetailFragment.EVENT_STR = "付息";
                return BoundsInterestFragment.newInstance(stockId);

            case "回购":
                StockDetailFragment.EVENT_STR = "回购";
                return BondsRepurchaseFragment.newInstance(stockId);

            case "涨幅榜":
                StockDetailFragment.EVENT_STR = "涨幅榜";
                return IndexRankingFragment.newInstance(stockId,TYPEINCREASE);

            case "跌幅榜":
                StockDetailFragment.EVENT_STR = "跌幅榜";
                return IndexRankingFragment.newInstance(stockId,TYPEDECLINE);

            case "换手率榜":
                StockDetailFragment.EVENT_STR = "换手率榜";
                return IndexRankingFragment.newInstance(stockId,TYPETURNOVERRATE);

            case "成交额榜":
                StockDetailFragment.EVENT_STR = "成交额榜";
                return IndexRankingFragment.newInstance(stockId,TYPETURNOVER);

           case "成交":
                StockDetailFragment.EVENT_STR = "成交";
                    return StockTurnoverFragment.newInstance(stockId, quoteItem.market, quoteItem.subtype);
        }
        return null;
    }

    private Fragment lastFragment;

    private void showFragment(int position){
        if (null == fragments) {
            return;
        }
        boolean isNew = false;
        if( null== fragments[position]) {
            fragments[position] = getFragment(tabs[position]);
            isNew = true;
        }
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if( null!=lastFragment ){
            transaction.hide(lastFragment);
        }
        lastFragment = fragments[position];
        if(isNew) {
            if( null!= lastFragment)
                transaction.add(R.id.frl_market_related, lastFragment);
        } else {
            if( null!=lastFragment )
                transaction.show(lastFragment);
        }
        transaction.commitAllowingStateLoss();
    }
}
