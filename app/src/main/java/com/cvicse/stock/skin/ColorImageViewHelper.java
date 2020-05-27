package com.cvicse.stock.skin;

import android.content.res.TypedArray;

import com.cvicse.base.skin.LoadTinable;
import com.cvicse.base.skin.ThemeUtils;
import com.cvicse.stock.view.ColorImageView;

/**
 * Created by liu_zlu on 2017/3/24 16:10
 */
public class ColorImageViewHelper  implements LoadTinable {
    private final ColorImageView colorImageView;

    private int filterColor;
    public ColorImageViewHelper(ColorImageView colorImageView) {
        this.colorImageView = colorImageView;
    }

    public boolean loadFromAttributes(String attributeName ,String bgId) {
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
        if (filterColor != 0) {
            colorImageView.setColorFilter(ThemeUtils.getThemeAttrColor(colorImageView.getContext(), filterColor));
        }
    }
}
