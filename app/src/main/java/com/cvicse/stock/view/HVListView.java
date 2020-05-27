package com.cvicse.stock.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Scroller;

import com.github.mikephil.charting.utils.Utils;

/**
 * Created by liu_zlu on 2017/5/4 21:40
 */
public class HVListView extends ListView {

    /**
     * 手指按下X的坐标
     */
    private int downY;
    /**
     * 手指按下Y的坐标
     */
    private int downX;
    /**
     * ListView的item
     */
    private CHScrollView itemView;
    /**
     * 滑动类
     */
    private Scroller scroller;

    private static final int SNAP_VELOCITY = 600;
    /**
     * 速度追踪对象
     */
    private VelocityTracker velocityTracker;
    /**
     * 是否响应滑动，默认为不响应
     */
    private boolean isSlide = false;
    /**
     * 认为是用户滑动的最小距离
     */
    private int mTouchSlop;

    public void setScrollView(CHScrollView itemView){
        this.itemView = itemView;
        itemView.tag = itemView.tagStr;
        itemView.setScrollChangedListener(new CHScrollView.ScrollChangedListener() {
            @Override
            public void onScrollChanged(View view, int l, int t, int oldl, int oldt) {
                update();
            }
        });

        setRecyclerListener(new RecyclerListener() {
            @Override
            public void onMovedToScrapHeap(View view) {
                final View child = ((ViewGroup)view).getChildAt(1);
                if(child != null){
                    update();
                }
            }
        });
    }

    private void update(){
        for (int i = 0, j = getChildCount(); i < j; i++) {
            View child = ((ViewGroup) getChildAt(i)).getChildAt(1);
            if(child != null){
                child.scrollTo(HVListView.this.itemView.getScrollX(), 0);
            }
        }
    }
    public HVListView(Context context) {
        this(context, null);
    }

    public HVListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        scroller = new Scroller(context);
        // 触发移动事件的最小距离
        mTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
    }

    /**
     * 分发事件，主要做的是判断点击的是那个item, 以及通过postDelayed来设置响应左右滑动事件
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                addVelocityTracker(event);

                // 假如scroller滚动还没有结束，我们直接返回
                if (!scroller.isFinished()) {
                    return super.dispatchTouchEvent(event);
                }
                downX = (int) event.getX();
                downY = (int) event.getY();
                break;
            }
            case MotionEvent.ACTION_MOVE: {
                if (Math.abs(getScrollVelocity()) > SNAP_VELOCITY
                        || (Math.abs(event.getX() - downX) > mTouchSlop && Math
                        .abs(event.getY() - downY) < mTouchSlop)) {
                    isSlide = true;

                }
                break;
            }
            case MotionEvent.ACTION_UP:
                //recycleVelocityTracker();
                break;
        }

        return super.dispatchTouchEvent(event);
    }


    /**
     * 处理我们拖动ListView item的逻辑
     */
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (isSlide) {
            requestDisallowInterceptTouchEvent(true);
            final int action = ev.getAction();
            if(action != MotionEvent.ACTION_DOWN){
                addVelocityTracker(ev);
            }
            int x = (int) ev.getX();
            switch (action) {
                case MotionEvent.ACTION_DOWN:
                    break;
                case MotionEvent.ACTION_MOVE:

                    MotionEvent cancelEvent = MotionEvent.obtain(ev);
                    cancelEvent.setAction(MotionEvent.ACTION_CANCEL |
                            (ev.getActionIndex()<< MotionEvent.ACTION_POINTER_INDEX_SHIFT));
                    onTouchEvent(cancelEvent);

                    int deltaX = downX - x;
                    downX = x;

                    // 手指拖动itemView滚动, deltaX大于0向左滚动，小于0向右滚
                    itemView.scrollBy(deltaX, 0);

                    return true;  //拖动的时候ListView不滚动
                case MotionEvent.ACTION_UP:
                    final int pointerId = ev.getPointerId(0);
                    velocityTracker.getXVelocity();
                    velocityTracker.computeCurrentVelocity(100, Utils.getMaximumFlingVelocity());
                    final int velocityX = (int) velocityTracker.getXVelocity(pointerId);
                    scroller.startScroll(itemView.getScrollX(), 0, -velocityX, 0,
                            Math.abs(velocityX));
                    /*int velocityX = getScrollVelocity();
                    if (velocityX > SNAP_VELOCITY) {
                        scrollRight();
                    } else if (velocityX < -SNAP_VELOCITY) {
                        scrollLeft();
                    } else {
                        scrollByDistanceX();
                    }*/

                    recycleVelocityTracker();
                    // 手指离开的时候就不响应左右滚动
                    isSlide = false;
                    break;
            }
        }

        //否则直接交给ListView来处理onTouchEvent事件
        return super.onTouchEvent(ev);
    }

    @Override
    public void computeScroll() {
        // 调用startScroll的时候scroller.computeScrollOffset()返回true，
        if (scroller.computeScrollOffset()) {
            // 让ListView item根据当前的滚动偏移量进行滚动
            itemView.scrollTo(scroller.getCurrX(), scroller.getCurrY());

            postInvalidate();
        }
    }

    /**
     * 添加用户的速度跟踪器
     *
     * @param event
     */
    private void addVelocityTracker(MotionEvent event) {
        if (velocityTracker == null) {
            velocityTracker = VelocityTracker.obtain();
        }

        velocityTracker.addMovement(event);
    }

    /**
     * 移除用户速度跟踪器
     */
    private void recycleVelocityTracker() {
        if (velocityTracker != null) {
            velocityTracker.recycle();
            velocityTracker = null;
        }
    }

    /**
     * 获取X方向的滑动速度,大于0向右滑动，反之向左
     *
     * @return
     */
    private int getScrollVelocity() {
        velocityTracker.computeCurrentVelocity(1000);
        int velocity = (int) velocityTracker.getXVelocity();
        return velocity;
    }

    /**
     * 重写该方法，达到使ListView适应ScrollView的效果
     */
//    @Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, View.MeasureSpec.AT_MOST);
//        super.onMeasure(widthMeasureSpec, expandSpec);
//    }

}