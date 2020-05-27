package com.cvicse.stock.skin;

import android.content.res.TypedArray;

import com.cvicse.base.skin.LoadTinable;
import com.cvicse.base.skin.ThemeUtils;
import com.cvicse.base.widget.BottomTabbar;

/**
 * Created by liu_zlu on 2017/4/6 12:18
 */
public class BottomBarHelper implements LoadTinable {
    private final BottomTabbar bottomTabbar;

    private int up_textColor;
    private int down_textColor;
    public BottomBarHelper(BottomTabbar bottomTabbar) {
        this.bottomTabbar = bottomTabbar;
    }

    public boolean loadFromAttributes(String attributeName ,String bgId) {
        if(attributeName.equals("up_textColor")){
            up_textColor = Integer.parseInt(bgId);
            return true;
        }
        if(attributeName.equals("down_textColor")){
            down_textColor = Integer.parseInt(bgId);
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
        if (up_textColor != 0) {
            bottomTabbar.setUpColor(ThemeUtils.getThemeAttrColorStateList(bottomTabbar.getContext(), up_textColor));
        }
        if (down_textColor != 0) {
            bottomTabbar.setDownColor(ThemeUtils.getThemeAttrColorStateList(bottomTabbar.getContext(), down_textColor));
        }
    }
}
