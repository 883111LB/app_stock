
package com.cvicse.stock.chart.listener;

import android.graphics.Matrix;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AnimationUtils;

import com.cvicse.stock.chart.chart.GCombinedChart;
import com.github.mikephil.charting.charts.BarLineChartBase;
import com.github.mikephil.charting.data.BarLineScatterCandleBubbleData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IBarLineScatterCandleBubbleDataSet;
import com.github.mikephil.charting.listener.BarLineChartTouchListener;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.utils.Utils;

/**
 * 重写触摸事件
 * {@link GBarLineChartTouchListener}
 * Created by liu_zlu on 2017/1/23 10:02
 */
public class GBarLineChartTouchListener extends BarLineChartTouchListener {

    private float dsrTranx = 0;
    /**
     * 设置高亮
     */
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            mChart.setHighlightPerDragEnabled(true);
            performHighlight((Highlight)msg.obj, null);
        }
    };
    /**
     * Constructor with initialization parameters.
     *
     * @param chart               instance of the chart
     * @param touchMatrix         the touch-matrix of the chart
     * @param dragTriggerDistance the minimum movement distance that will be interpreted as a "drag" gesture in dp (3dp equals
     */
    public GBarLineChartTouchListener(BarLineChartBase<? extends BarLineScatterCandleBubbleData<? extends IBarLineScatterCandleBubbleDataSet<? extends Entry>>> chart, Matrix touchMatrix, float dragTriggerDistance) {
        super(chart, touchMatrix, dragTriggerDistance);
    }

    /**
     * 长按1秒触发高亮
     * @param e
     */
    @Override
    public void onLongPress(MotionEvent e) {
        mLastGesture = ChartGesture.LONG_PRESS;
        OnChartGestureListener l = mChart.getOnChartGestureListener();

        if (l != null) {
            l.onChartLongPressed(e);
        }
        if(mDecelerationVelocity.x == 0){
            Highlight h = mChart.getHighlightByTouchPoint(e.getX(), e.getY());
            Message message = handler.obtainMessage();
            message.obj = h;
            handler.sendMessageDelayed(message,1000);
        }

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {

        mLastGesture = ChartGesture.SINGLE_TAP;

       /* OnChartGestureListener l = mChart.getOnChartGestureListener();

        if (l != null) {
            l.onChartSingleTapped(e);
        }*/

        if (!mChart.isHighlightPerTapEnabled()) {
            return false;
        }

        return true;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
               /* mChart.setHighlightPerDragEnabled(false);
                setFullyZoomedOut(false);*/
                break;
            case MotionEvent.ACTION_UP:
                super.onTouch(v, event);
                if(mDecelerationVelocity.x == 0.f){
                    reset();
                }
                handler.removeCallbacksAndMessages(null);
                //mChart.highlightValue(null, true);
                return true;
            case MotionEvent.ACTION_CANCEL:
                if(mDecelerationVelocity.x == 0.f){
                    reset();
                }
                handler.removeCallbacksAndMessages(null);
               // mChart.highlightValue(null, true);
                break;
        }
        return super.onTouch(v, event);
    }

    public void setFullyZoomedOut(boolean interception){
        if(mChart instanceof GCombinedChart){
            ((GCombinedChart) mChart).setFullyZoomedOut(interception);
        }
    }
    @Override
    public void computeScroll() {
        if (mDecelerationVelocity.x == 0.f && mDecelerationVelocity.y == 0.f && dsrTranx == 0)
            return; // There's no deceleration in progress
        final long currentTime = AnimationUtils.currentAnimationTimeMillis();
        if(mChart.getTransX() >= 0){
            mDecelerationCurrentPoint.x += dsrTranx;
            dsrTranx = 0;
            stopDeceleration();
        } else {
            if (mDecelerationVelocity.x == 0.f && mDecelerationVelocity.y == 0.f)
                return;
            mDecelerationVelocity.x *= mChart.getDragDecelerationFrictionCoef();
            mDecelerationVelocity.y *= mChart.getDragDecelerationFrictionCoef();

            final float timeInterval = (float) (currentTime - mDecelerationLastTime) / 1000.f;

            float distanceX = mDecelerationVelocity.x * timeInterval;
            float distanceY = mDecelerationVelocity.y * timeInterval;

            mDecelerationCurrentPoint.x += distanceX;
            mDecelerationCurrentPoint.y += distanceY;
        }


        MotionEvent event = MotionEvent.obtain(currentTime, currentTime, MotionEvent.ACTION_MOVE, mDecelerationCurrentPoint.x,
                mDecelerationCurrentPoint.y, 0);
        performDrag(event);
        event.recycle();
        mMatrix = mChart.getViewPortHandler().refresh(mMatrix, mChart, false);

        mDecelerationLastTime = currentTime;

        if (Math.abs(mDecelerationVelocity.x) >= 0.01 || Math.abs(mDecelerationVelocity.y) >= 0.01)
            Utils.postInvalidateOnAnimation(mChart); // This causes computeScroll to fire, recommended for this by Google
        else {
            // Range might have changed, which means that Y-axis labels
            // could have changed in size, affecting Y-axis size.
            // So we need to recalculate offsets.
            mChart.calculateOffsets();
            mChart.postInvalidate();

            stopDeceleration();
        }
    }

    private void reset(){
        if(mChart.getTransX() < 150){
            dsrTranx = -mChart.getTransX();
        } else {
            dsrTranx = -mChart.getTransX()-200;
        }
        Utils.postInvalidateOnAnimation(mChart);
    }
}
