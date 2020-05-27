/**/package com.cvicse.stock.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ListView;
import android.widget.OverScroller;

/**
 * Created by ding_syi on 17-1-13.
 */
public class CHScrollView extends HorizontalScrollView {

    public static String tag = null;

    public String tagStr = this.toString();

    private OverScroller scroller;
    private GestureDetector detector;
    private int width;

    /**
     * 父布局中的listView
     */
    private ListView listView;

    /**
     *  子项，ListView或者其子类的子项
     */
    private View itemView;

    //左边还是右边的标志 默认右边
    private String leftOrRight = "right";

    public CHScrollView(Context context) {
        super(context);
        init(context);
    }

    public CHScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CHScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    public void setLeftOrRight(String leftOrRight){
        this.leftOrRight = leftOrRight;
    }

    public String getLeftOrRight(){
        return leftOrRight;
    }

    public void init(Context context){
        this.setClickable(false);
        //只有当视图可以滚动时，此项设置才起作用
        this.setOverScrollMode(OVER_SCROLL_NEVER);
        // 触摸模式
        this.setFocusableInTouchMode(false);
        mGestureDetector = new GestureDetector(new HScrollDetector());
    }

    private boolean userParent =false ;

    public void setUserParent(boolean userParent){
        this.userParent = userParent;
    }

    private int eventX = 0 ;
    private int eventY = 0;

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return super.onInterceptTouchEvent(ev) && mGestureDetector.onTouchEvent(ev);
    }

    private GestureDetector mGestureDetector;
    // Return false if we're scrolling in the y direction
    class HScrollDetector extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            if(Math.abs(distanceX) > Math.abs(distanceY)) {
                return true;
            }
            return false;
        }
    }
  /*  @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                eventX = (int) ev.getX();
                eventY = (int) ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                if(Math.abs(eventX - ev.getX())*2-Math.abs(eventY - ev.getY()) > 0){
                    eventX = (int) ev.getX();
                    eventY = (int) ev.getY();
                    return true;
                }

                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                if(Math.abs(eventX - ev.getX())*2-Math.abs(eventY - ev.getY()) > 0){
                    return true;
                }
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }*/

    /**
     * 当此view附加到附加到窗体上时调用该方法。在view还没有画出来时，
     * 可以在此方法中进行一些初始化的操作。
     * 要保证在onDraw之前调用，但可能在调用onDraw之前的任何时刻
     * 包括调用onMeasure()之前或之后
     */
    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        try {
            listView = getListView();

            /**
             * 获取此view的第一个子布局，给子布局设置点击事件
             */
            getChildAt(0).setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    //获取位置
                    int position = listView.getPositionForView(itemView);
                    final long id = listView.getItemIdAtPosition(position);
                    if(listView.getOnItemClickListener() != null){
                        if(userParent){
                            listView.getOnItemClickListener().onItemClick(listView, (View) v.getParent(),position,id);
                        } else {
                            listView.getOnItemClickListener().onItemClick(listView,itemView,position,id);
                        }

                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取CHScollView父亲节点中的listView
     */
    private ListView getListView(){
        itemView = (View) getParent();//获取view的父亲节点

        while (!(itemView.getParent() instanceof ListView) ) {
            itemView = (View) itemView.getParent();
            //当获取的父控件中没有listView时
            if(itemView == null){
                return null;
            }
        }
        return (ListView) itemView.getParent();
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        //进行触摸赋值
        tag = tagStr;
        /*switch(ev.getAction()){
            case MotionEvent.ACTION_MOVE:

                break;
        }*/
        return super.onTouchEvent(ev);
    }

     @Override
     protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        //当当前的CHSCrollView被触摸时，滑动其它
        if(tag == null || tag.equals(tagStr)) {
            if(scrollChangedListener != null){
                scrollChangedListener.onScrollChanged(this,l, t, oldl, oldt);
            }
        }/*else{*/
            super.onScrollChanged(l, t, oldl, oldt);
        /*}*/
    }

    private ScrollChangedListener scrollChangedListener;

    public void setScrollChangedListener(ScrollChangedListener scrollChangedListener){
        this.scrollChangedListener = scrollChangedListener;
    }

    public interface ScrollChangedListener {
        void onScrollChanged(View view, int l, int t, int oldl, int oldt);
    }
}
