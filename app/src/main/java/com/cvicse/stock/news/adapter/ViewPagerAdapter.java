package com.cvicse.stock.news.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by ruan_ytai on 16-12-28.
 */

public class ViewPagerAdapter extends FragmentPagerAdapter {
    List<Fragment> mFragmentList;
    private String[] mTabTitleArray;

    public ViewPagerAdapter(FragmentManager fragmentManager, List<Fragment> fragments, String[] tabtitleArray) {
        super(fragmentManager);
        mFragmentList = fragments;
        mTabTitleArray = tabtitleArray;
    }

    @Override
    public Fragment getItem(int position)
    {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount()
    {
        return mFragmentList.size();
    }

    /**
     * 重写与Tablayout配合
     */
    @Override
    public CharSequence getPageTitle(int position) {
        return mTabTitleArray[position % mTabTitleArray.length];
    }

   /* @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return true;
    }*/
}
