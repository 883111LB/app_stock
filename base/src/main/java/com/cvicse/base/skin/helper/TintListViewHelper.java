package com.cvicse.base.skin.helper;

import android.content.res.TypedArray;
import android.widget.ListView;

import com.cvicse.base.skin.LoadTinable;
import com.cvicse.base.skin.ThemeUtils;

/**
 * Created by liu_zlu on 2017/4/23 10:25
 */
public class TintListViewHelper implements LoadTinable {
    final ListView mView;

    private int divider;

    public TintListViewHelper(ListView view) {
        mView = view;
    }

    public boolean loadFromAttributes(String attributeName, String bgId) {
        if(attributeName.equals("divider")){
            divider = Integer.parseInt(bgId);
            return true;
        }
        return false;
    }

    @Override
    public boolean loadFromStyle(int index, TypedArray typedArray) {
       /* if(R.styleable.Skin_change_android_textColor == index){
            ColorStateList colorStateList = typedArray.getColorStateList(index);
            if(colorStateList != null){
                mView.setTextColor(colorStateList);
            }
            return true;
        }*/
        return false;
    }

    @Override
    public void tint() {
        if(divider != 0)
            mView.setDivider(ThemeUtils.getThemeAttrDrawable(mView.getContext(),divider));
    }
}
