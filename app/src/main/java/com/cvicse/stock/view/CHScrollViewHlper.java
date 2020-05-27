package com.cvicse.stock.view;

import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ding_syi on 17-1-14.
 */
public class CHScrollViewHlper {

    //装入所有的HScrollView
    protected List<CHScrollView> mHScrollViews =new ArrayList<>();

    private int disPlayWidth;

    public void addHViews(final CHScrollView hScrollView) {
        mHScrollViews.add(hScrollView);
        hScrollView.setScrollChangedListener(new CHScrollView.ScrollChangedListener() {
            @Override
            public void onScrollChanged(View view,int l, int t, int oldl, int oldt) {
                CHScrollViewHlper.this.onScrollChanged(view,l, t, oldl, oldt);
            }
        });
    }

    //设置屏幕的宽
    public void setDisPalyout(int witdh){
        disPlayWidth = witdh;
    }

    /**
     * l代表滑动后当前ScrollView可视界面的左上角在整个ScrollView的X轴中的位置
     * oldl就是滑动前的X轴位置
     */
    public void onScrollChanged(View mTouchView,int l, int t, int oldl, int oldt) {
        //点击的View是左边
        if ("left".equals(((CHScrollView) mTouchView).getLeftOrRight())) {
            for (CHScrollView scrollView : mHScrollViews) {
                //防止重复滑动
                if (mTouchView != scrollView)
                    if ("left".equals(scrollView.getLeftOrRight())) {
                        scrollView.scrollTo(l, t);
                    } else {
                        //滑动的其他View是右边的
                        /*int length = disPlayWidth-l+disPlayWidth/5;
                        scrollView.scrollTo(length,t);*/
                        scrollView.scrollBy(oldl-l,t);
                    }
            }
        } else {

            /**
             * 点击的View是右边的
             */
            for (CHScrollView scrollView : mHScrollViews) {
                //防止重复滑动
                if (mTouchView != scrollView)
                    if ("left".equals(scrollView.getLeftOrRight())) {
                       /*int length = disPlayWidth-l+disPlayWidth/5;
                        scrollView.scrollTo(length, t);*/
                        scrollView.scrollBy(oldl-l, t);
                    } else {
                        //右边的和点击的右边View是一样的效果
                        scrollView.scrollTo(l,t);
                    }
            }
        }
    }
}

