package com.cvicse.stock.markets.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.cvicse.stock.markets.ui.StockDetailFragment;
import com.mitake.core.QuoteItem;

import java.util.ArrayList;

/**
 * Created by liu_zlu on 2017/2/12 14:59
 */
public class StockDetailAdapter extends FragmentPagerAdapter {

    private ArrayList<QuoteItem> quoteItems;
    public StockDetailAdapter(FragmentManager fm,ArrayList<QuoteItem> quoteItems){
        super(fm);
        this.quoteItems = quoteItems;
    }

    @Override
    public Fragment getItem(int position) {
        return StockDetailFragment.newInstance(quoteItems.get(position),position);
    }

    public QuoteItem getItemData(int position){
        return  null== quoteItems ? null:quoteItems.get(position);
    }
    @Override
    public int getCount() {
        return  null!=quoteItems  ? quoteItems.size():0;
    }
}
