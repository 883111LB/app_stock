package com.cvicse.stock.chart.listener;


import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;

import com.github.mikephil.charting.charts.BarLineChartBase;
import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.listener.OnChartGestureListener;

/**
 * http://stackoverflow.com/questions/28521004/mpandroidchart-have-one-graph-mirror-the-zoom-swipes-on-a-sister-graph
 */
public class CoupleChartGestureListener implements OnChartGestureListener {

    private static final String TAG = CoupleChartGestureListener.class.getSimpleName();

    private BarLineChartBase srcChart;
    private Chart[] dstCharts;
    private boolean touch = false;

    private boolean mIsCanLoad = false;  // new

   //去除高亮
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            srcChart.setHighlightPerDragEnabled(false);
    //        ((GBarLineChartTouchListener)srcChart.getOnTouchListener()).setFullyZoomedOut(false);
            srcChart.highlightValue(null,true);
        //    srcChart.getParent().requestDisallowInterceptTouchEvent(false);
        }
    };

    public CoupleChartGestureListener(BarLineChartBase srcChart) {
        this.srcChart = srcChart;
     //   this.dstCharts = dstCharts;
    }

    @Override
    public void onChartGestureStart(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {
       // syncCharts();
        mIsCanLoad = false;
        handler.removeMessages(1);
    }

    /**
     * 触摸事件结束，去除高亮
     * @param me
     * @param lastPerformedGesture
     */
    @Override
    public void onChartGestureEnd(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {

        handler.sendEmptyMessageDelayed(1,1200);
      //  syncCharts();

        // 以下代码为new -- start
//        int rightXIndex = srcChart.getHighestVisibleXIndex();    //获取可视区域中，显示在x轴最右边的index
//        int size = srcChart.getBarData().getXVals().size();

        float rightXIndex = srcChart.getHighestVisibleX();    //获取可视区域中，显示在x轴最右边的index
        float leftXIndex = srcChart.getLowestVisibleX();    //获取可视区域中，显示在x轴最左边的index
        float max = srcChart.getData().getXMax();
        float min = srcChart.getData().getXMin();

        Log.e("onChartGestureEnd","leftXIndex="+leftXIndex+",rightXIndex="+rightXIndex+" ,max="+max+" ,min = "+min);
        if(lastPerformedGesture == ChartTouchListener.ChartGesture.DRAG){
            mIsCanLoad = true;
//            if(rightXIndex == size-1 || rightXIndex == size){
            if(leftXIndex <= 0.0 ){
                mIsCanLoad = false;
                //加载日期前的数据
                if( null != onKlineSelectedListener ){

//                    onKlineSelectedListener.onLoadMoreData(true);  // 取消注释
                }           }
          /*
            if(  rightXIndex >= max ){
                mIsCanLoad = false;
                // 加载日期后的数据
                onKlineSelectedListener.onLoadMoreData(false);
            }*/
            // 以上代码为new - end
        }
    }

    @Override
    public void onChartLongPressed(MotionEvent me) {
       // syncCharts();
    }

    @Override
    public void onChartDoubleTapped(MotionEvent me) {
     //   syncCharts();
    }

    @Override
    public void onChartSingleTapped(MotionEvent me) {
      // syncCharts();
    }

    @Override
    public void onChartFling(MotionEvent me1, MotionEvent me2, float velocityX, float velocityY) {
       // syncCharts();
    }

    @Override
    public void onChartScale(MotionEvent me, float scaleX, float scaleY) {
        //syncCharts();
    }

    @Override
    public void onChartTranslate(MotionEvent me, float dX, float dY) {
        //syncCharts();
        // 以下代码为new -- start
        if(mIsCanLoad){
//            int rightXIndex = mBarChart.getHighestVisibleXIndex();     //获取可视区域中，显示在x轴最右边的index
//            int size = mBarChart.getBarData().getXVals().size();

            float rightXIndex = srcChart.getHighestVisibleX();     //获取可视区域中，显示在x轴最右边的index
            float leftXIndex = srcChart.getLowestVisibleX();     //获取可视区域中，显示在x轴最左边的index
            float max = srcChart.getData().getXMax();
            float min = srcChart.getData().getXMin();

            Log.e("onChartTranslate","leftXIndex="+leftXIndex+",rightXIndex="+rightXIndex+" ,max="+max+" ,min = "+min);
//            if(rightXIndex == size-1 || rightXIndex == size){
            if(leftXIndex <= 0.0 ){
                mIsCanLoad = false;
                //加载日期前的数据
                if( null != onKlineSelectedListener ){
//                    onKlineSelectedListener.onLoadMoreData(true);  // 取消注释
                }
            }
          /*
            if(  rightXIndex >= max ){
                mIsCanLoad = false;
                // 加载日期后的数据
                onKlineSelectedListener.onLoadMoreData(false);
            }*/
            // 以上代码为new - end
        }
    }

    OnKlineSelectedListener onKlineSelectedListener; // new
    /**
     * 用于K线加载更多的数据。2018.2.11 new
     */
    public void setOnKlineSelectedListener( OnKlineSelectedListener onKlineSelectedListener) {
        this.onKlineSelectedListener = onKlineSelectedListener;
    }

    /*public void syncCharts() {
        Matrix srcMatrix;
        float[] srcVals = new float[9];
        Matrix dstMatrix;
        float[] dstVals = new float[9];
        // get src chart translation matrix:
        srcMatrix = srcChart.getViewPortHandler().getMatrixTouch();
        srcMatrix.getValues(srcVals);
        // apply X axis scaling and position to dst charts:
        for (Chart dstChart : dstCharts) {
            if (dstChart.getVisibility() == View.VISIBLE) {
                dstMatrix = dstChart.getViewPortHandler().getMatrixTouch();
                dstMatrix.getValues(dstVals);

                dstVals[Matrix.MSCALE_X] = srcVals[Matrix.MSCALE_X];
                dstVals[Matrix.MSKEW_X] = srcVals[Matrix.MSKEW_X];
                dstVals[Matrix.MTRANS_X] = srcVals[Matrix.MTRANS_X];
                dstVals[Matrix.MSKEW_Y] = srcVals[Matrix.MSKEW_Y];
                dstVals[Matrix.MSCALE_Y] = srcVals[Matrix.MSCALE_Y];
                dstVals[Matrix.MTRANS_Y] = srcVals[Matrix.MTRANS_Y];
                dstVals[Matrix.MPERSP_0] = srcVals[Matrix.MPERSP_0];
                dstVals[Matrix.MPERSP_1] = srcVals[Matrix.MPERSP_1];
                dstVals[Matrix.MPERSP_2] = srcVals[Matrix.MPERSP_2];

                dstMatrix.setValues(dstVals);
                dstChart.getViewPortHandler().refresh(dstMatrix, dstChart, true);
            }
        }
    }*/
}