package com.cvicse.stock.skin;

import android.content.res.TypedArray;
import android.support.design.widget.TabLayout;

import com.cvicse.base.skin.LoadTinable;
import com.cvicse.base.skin.ThemeUtils;

/**
 * Created by liu_zlu on 2017/3/13 09:45
 */
public class TintTabLayoutHelper implements LoadTinable {
    private final TabLayout mView;

    private int mTabIndicatorColor;
    private int mTabSelectedTextColor;
    private int mTabTextColor;
    public TintTabLayoutHelper(TabLayout view) {
        mView = view;
    }
    @Override
    public boolean loadFromAttributes(String attributeName, String bgId) {
        if(attributeName.equals("tabIndicatorColor")){
            mTabIndicatorColor = Integer.parseInt(bgId);
            return true;
        }
        if(attributeName.equals("tabSelectedTextColor")){
            mTabSelectedTextColor = Integer.parseInt(bgId);
            return true;
        }
        if(attributeName.equals("tabTextColor")){
            mTabTextColor = Integer.parseInt(bgId);
            return true;
        }
        return false;
    }

    @Override
    public boolean loadFromStyle(int index, TypedArray typedArray) {
        return false;
    }

    @Override
    public void tint() {
        if(mTabIndicatorColor != 0){
            mView.setSelectedTabIndicatorColor(ThemeUtils.getThemeAttrColor(mView.getContext(),mTabIndicatorColor));
        }
        if(mTabSelectedTextColor != 0){
            mView.setTabTextColors(ThemeUtils.getThemeAttrColor(mView.getContext(),mTabTextColor),
                    ThemeUtils.getThemeAttrColor(mView.getContext(),mTabSelectedTextColor));
        }
    }
}
