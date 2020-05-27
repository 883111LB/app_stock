package com.cvicse.base.utils;

import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by liu_zlu on 2016/11/17 15:29
 */
public class ViewUtils {
    /**
     * 清理view和父View之间的联系
     * @param viewClear
     */
    public static void clearView(View viewClear){
        if(viewClear instanceof ViewGroup){
            int length = ((ViewGroup)viewClear).getChildCount();
            View view;
            for(int i=0;i< length;i++){
                view = ((ViewGroup) viewClear).getChildAt(i);
                clearView(view);
            }
            try {
                ((ViewGroup) viewClear).removeAllViews();
            } catch (UnsupportedOperationException e) {

            }
        }
    }

    public static View findViewFocus(ViewGroup viewGroup,MotionEvent event){
        View view;
        int childCount = viewGroup.getChildCount();
        for(int i = 0;i< childCount ;i++ ){
            view = viewGroup.getChildAt(i);
            Rect outRect = new Rect();
            view.getGlobalVisibleRect(outRect);
            if (outRect.contains((int)event.getRawX(), (int)event.getRawY())) {
                if(view instanceof ViewGroup){
                    return findViewFocus((ViewGroup) view,event);
                } else {
                    return view;
                }
            }
        }
        return null;
    }
}
