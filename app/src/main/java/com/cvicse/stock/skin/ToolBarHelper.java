package com.cvicse.stock.skin;

import android.content.res.TypedArray;

import com.cvicse.base.skin.LoadTinable;
import com.cvicse.base.skin.ThemeUtils;
import com.cvicse.stock.base.ToolBar;

/**
 * Created by liuzilu on 2017/3/5.
 */

public class ToolBarHelper implements LoadTinable{
    private final ToolBar mToolBar;

    private int mTitleTextColorAttr;

    private int filterColor;
    public ToolBarHelper(ToolBar mToolBar) {
        this.mToolBar = mToolBar;
    }

    public boolean loadFromAttributes(String attributeName ,String bgId) {
        if(attributeName.equals("textColor_title")){
            mTitleTextColorAttr = Integer.parseInt(bgId);
            return true;
        }
        if(attributeName.equals("filtercolor")){
            filterColor = Integer.parseInt(bgId);
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
        if (mTitleTextColorAttr != 0) {
            mToolBar.setTitleTextColor(ThemeUtils.getThemeAttrColorStateList(mToolBar.getContext(), mTitleTextColorAttr));
        }
        if (filterColor != 0) {
            mToolBar.setColorFilter(ThemeUtils.getThemeAttrColor(mToolBar.getContext(), filterColor));
        }
    }
}
