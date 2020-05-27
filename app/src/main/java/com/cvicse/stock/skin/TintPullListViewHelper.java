package com.cvicse.stock.skin;

import android.content.res.TypedArray;
import android.widget.ListView;

import com.cvicse.base.skin.LoadTinable;
import com.cvicse.base.skin.ThemeUtils;
import com.cvicse.stock.pulltorefresh.PullToRefreshListView;

/**
 * Created by liu_zlu on 2017/4/23 10:25
 */
public class TintPullListViewHelper implements LoadTinable {
    final PullToRefreshListView mView;

    private int divider;
    private int dividerHeight;
    public TintPullListViewHelper(PullToRefreshListView view) {
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
        if(divider != 0){
            dividerHeight = mView.getRefreshableView().getDividerHeight();
            mView.getRefreshableView().setDivider(ThemeUtils.getThemeAttrDrawable(mView.getContext(),divider));
            mView.getRefreshableView().setDividerHeight(dividerHeight);
        }
    }
}
