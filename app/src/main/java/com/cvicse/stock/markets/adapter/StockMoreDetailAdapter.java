package com.cvicse.stock.markets.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.cvicse.stock.base.PFragmentPagerAdapter;
import com.cvicse.stock.markets.ui.StockBrokerInfoFragment;
import com.cvicse.stock.markets.ui.StockDelegateFragment;
import com.cvicse.stock.markets.ui.StockPriceFragment;
import com.cvicse.stock.markets.ui.StockTenFragment;
import com.cvicse.stock.markets.ui.StockTickFragment;
import com.cvicse.stock.markets.ui.StockTradeFragment;
import com.cvicse.stock.markets.ui.StockVolumeFragment;
import com.mitake.core.QuoteItem;

/**
 * Created by liu_zlu on 2017/4/5 20:18
 */
public class StockMoreDetailAdapter extends PFragmentPagerAdapter {
    private QuoteItem quoteItem;

    //标题栏
    private String tabs[];

    public StockMoreDetailAdapter(FragmentManager fm,QuoteItem quoteItem,String tabs[]) {
        super(fm);
        this.quoteItem = quoteItem;
        this.tabs = tabs;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabs[position];
    }
    @Override
    public Fragment getItem(int position)
    {
        return getFragment(tabs[position]);
    }

    @Override
    public int getCount() {
        return tabs == null ? 0 : tabs.length;
    }

    private Fragment getFragment(String tab){
        switch (tab) {
            case "十档":
                return StockTenFragment.newInstance(quoteItem);

            case "委托队列":
                return StockDelegateFragment.newInstance(quoteItem);

            case "经纪席位":
                return StockBrokerInfoFragment.newInstance(quoteItem);

            case "逐笔明细":
                return StockTickFragment.newInstance(quoteItem);

            case "成交明细":
                return StockTradeFragment.newInstance(quoteItem);

            case "分价":
                return StockPriceFragment.newInstance(quoteItem);

            case "分量":
                return StockVolumeFragment.newInstance(quoteItem);
        }
        return null;
    }
}
