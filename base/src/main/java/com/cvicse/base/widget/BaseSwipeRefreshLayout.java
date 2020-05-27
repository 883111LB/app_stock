package com.cvicse.base.widget;

import android.content.Context;
import android.graphics.RectF;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;

import java.util.ArrayList;

/**
 * Created by liu_zlu on 2016/8/27 20:30
 */
public class BaseSwipeRefreshLayout extends SwipeRefreshLayout {
    private boolean mMeasured = false;
    private boolean mRefresh = false;
    private View mTarget;
    private int rawX = -1;
    private int rawY = -1;

    private boolean enable = true;
    private float mPrevX;

    public BaseSwipeRefreshLayout(Context context)
    {
        super(context);
    }

    private static ArrayList<Class<? extends View>> classArrayList = new ArrayList<>();
    public static void addInterruptView(Class<? extends View> cls){
        classArrayList.add(cls);
    }

    public static void removeInterruptView(Class<? extends View> cls){
        classArrayList.remove(cls);
    }
    public BaseSwipeRefreshLayout(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (mTarget == null) {
            sureTarget();
        }
    }
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if(mTarget == null){
            sureTarget();
        }
        if (!mMeasured) {
            mMeasured = true;
            setRefreshing(mRefresh);
        }
    }
    private void sureTarget() {
        // Don't bother getting the parent height if the parent hasn't been laid
        // out yet.
        if (mTarget == null) {
            for (int i = 0; i < getChildCount(); i++) {
                View child = getChildAt(i);
                if (!(child instanceof ImageView)) {
                    mTarget = child;
                    break;
                }
            }
        }
    }
    @Override
    public void setRefreshing(boolean refreshing)
    {
        sureTarget();
        if (mMeasured) {
            super.setRefreshing(refreshing);
        }
        else{
            mRefresh = refreshing;
        }
    }
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        setEnabled(enable);
        // MotionEvent event;
// event.getX(); 获取相对于控件自身左上角的 x 坐标值
// event.getY(); 获取相对于控件自身左上角的 y 坐标值
        rawX = (int) ev.getRawX(); // 获取相对于屏幕左上角的 x 坐标值
        rawY = (int) ev.getRawY(); // 获取相对于屏幕左上角的 y 坐标值
        sureTarget();
        if(ev.getAction() == MotionEvent.ACTION_UP){
            rawX = -1; // 获取相对于屏幕左上角的 x 坐标值
            rawY = -1; // 获取相对于屏幕左上角的 y 坐标值
        }
        return onSefeInterceptTouchEvent(ev);
    }

    public boolean onSefeInterceptTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mPrevX = event.getX();
                break;
            case MotionEvent.ACTION_MOVE:
                float eventX = event.getX();
                float xDiff = Math.abs(eventX - mPrevX);
                // Log.d("refresh" ,"move----" + eventX + "   " + mPrevX + "   " + mTouchSlop);
                // 增加60的容差，让下拉刷新在竖直滑动时就可以触发
                if (xDiff > mTouchSlop + 60) {
                    return false;
                }
        }
        return super.onInterceptTouchEvent(event);
    }

    private int mTouchSlop;
    @Override
    public boolean canChildScrollUp() {
        View view = getOnTouchView();
        if(view == null){
            return super.canChildScrollUp();
        }
        while (!view.equals(this)){
            if(check(view)){
                return true;
            } else {
                view = (View) view.getParent();
            }
        }
        return super.canChildScrollUp();
    }
    private View getOnTouchView(){

        View view = findViewonTouch(this);
        while (view instanceof ViewGroup){
            view= findViewonTouch(view);
        }
        return view;
    }
    private View findViewonTouch(View view) {
        if(!(view instanceof ViewGroup)){
            return view;
        }
        ViewGroup viewGroup = (ViewGroup) view;
        int count = viewGroup.getChildCount();
        View retView = null;

        for(int i = 0;i < count;i++){
            retView = viewGroup.getChildAt(i);
            if(calcViewScreenLocation(retView)){
               break;
            }
        }
        return retView;
    }


    private boolean check(View view){
        for(Class<? extends View> cls : classArrayList){
            if(cls.isAssignableFrom(view.getClass())){
                return true;
            }
        }
        if (android.os.Build.VERSION.SDK_INT < 14) {
            if (view instanceof AbsListView) {
                final AbsListView absListView = (AbsListView) view;
                return absListView.getChildCount() > 0
                        && (absListView.getFirstVisiblePosition() > 0 || absListView.getChildAt(0)
                        .getTop() < absListView.getPaddingTop());
            } else {
                return ViewCompat.canScrollVertically(view, -1) || view.getScrollY() > 0;
            }
        } else {
            return ViewCompat.canScrollVertically(view, -1);
        }
    }
    /**
     * 计算指定的 View 在屏幕中的坐标。
     */
    private boolean calcViewScreenLocation(View view) {
        int[] location = new int[2];
        // 获取控件在屏幕中的位置，返回的数组分别为控件左顶点的 x、y 的值
        view.getLocationOnScreen(location);
        RectF rect = new RectF(location[0], location[1], location[0] + view.getWidth(),
                location[1] + view.getHeight());
        return rect.contains(rawX, rawY);
    }
}
