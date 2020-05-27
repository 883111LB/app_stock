package com.cvicse.stock.chart.chart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

import com.cvicse.stock.chart.components.GYAxis;
import com.cvicse.stock.chart.components.TextMarkerView;
import com.cvicse.stock.chart.helper.GViewPortHandler;
import com.cvicse.stock.chart.listener.GBarLineChartTouchListener;
import com.cvicse.stock.chart.renderer.GCombinedChartRenderer;
import com.cvicse.stock.chart.renderer.GXAxisRenderer;
import com.cvicse.stock.chart.renderer.GYAxisRenderer;
import com.cvicse.stock.chart.renderer.LoadRenderer;
import com.cvicse.stock.chart.theme.ThemeManager;
import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.CombinedData;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.renderer.CombinedChartRenderer;
import com.github.mikephil.charting.utils.Transformer;
import com.github.mikephil.charting.utils.ViewPortHandler;

/**
 * 自定义chart图表
 * Created by liu_zlu on 2017/1/10 15:33
 */
public class GCombinedChart extends CombinedChart {

    int size;
    /**
     * 待研究，加载更多
     */
    LoadRenderer loadRenderer;
    /**
     * 左侧自定义标记
     */
    TextMarkerView myMarkerViewLeft;
    /**
     * 右侧自定义标记
     */
    TextMarkerView myMarkerViewRight;
    /**
     * 底部侧自定义标记
     */
    TextMarkerView mBottomMarkerView;
    /**
     * 顶部中心自定义标记
     */
    TextMarkerView mTopRightMarkerView;

    TextMarkerView mTopMarkerView;  // 顶部自定义标记

    boolean isShowFqMsg = true;  // 是否显示复权信息

    int bottomOffest = 0;

    public GCombinedChart(Context context) {
        super(context);
        init(context);
    }
    public GCombinedChart(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public GCombinedChart(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    @Override
    protected void init() {
        mViewPortHandler = new GViewPortHandler();
        super.init();

        myMarkerViewLeft = new TextMarkerView(getContext());
        myMarkerViewRight = new TextMarkerView(getContext());
        mBottomMarkerView = new TextMarkerView(getContext());
        mTopMarkerView = new TextMarkerView(getContext());
        mTopRightMarkerView = new TextMarkerView(getContext());
    }

    private void init(Context context) {
        mAxisLeft = new GYAxis();
        mAxisRight = new GYAxis(YAxis.AxisDependency.RIGHT);
        mXAxisRenderer = new GXAxisRenderer(mViewPortHandler, mXAxis, mLeftAxisTransformer);
        mRightAxisTransformer = new Transformer(mViewPortHandler);
        mAxisRendererLeft = new GYAxisRenderer(mViewPortHandler, mAxisLeft, mLeftAxisTransformer);
        mAxisRendererRight = new GYAxisRenderer(mViewPortHandler, mAxisRight, mRightAxisTransformer);
        mRenderer = new GCombinedChartRenderer(this, mAnimator, mViewPortHandler);
        mChartTouchListener = new GBarLineChartTouchListener(this, mViewPortHandler.getMatrixTouch(), 3f);
        loadRenderer = new LoadRenderer(mViewPortHandler);

        getXAxis().setSpaceMin(0.5f);
        getXAxis().setSpaceMax(0.5f);

        //禁止y轴缩放
        this.setScaleYEnabled(false);
        getViewPortHandler().setMaximumScaleX(4);
        Rect rect = new Rect();
        mXAxisRenderer.getPaintAxisLabels().getTextBounds("2010",0,3,rect);
        bottomOffest = rect.centerY() + 20;

        //设置字体颜色
        mAxisLeft.setTextColor(ThemeManager.colorYAxisLeft());
        mAxisRight.setTextColor(ThemeManager.colorYAxisRight());
        mXAxis.setTextColor(ThemeManager.colorXAxis());
    }

    float lastX = 0;
    float lastY = 0;

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                lastX = event.getX();
                lastY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                float nowX = event.getX();
                float nowY = event.getY();
                if(Math.abs(nowY - lastY) > 2*(Math.abs(nowX - lastX)) || mIndicesToHighlight == null){
                } else {
                    getParent().requestDisallowInterceptTouchEvent(true);
                }
                lastX = nowX;
                lastY = nowY;

                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:

        }
        return super.dispatchTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        if (mChartTouchListener == null || mData == null)
            return false;

        if (!mTouchEnabled)
            return false;
        else{
            return mChartTouchListener.onTouch(this, event);
        }
    }

    /**
     * 设置是否是底部Chart
     * @param isBottom
     */
    public void setIsBottom(boolean isBottom) {
        ((GCombinedChartRenderer)mRenderer).setIsBottom(isBottom);
    }

    /**
     * x轴缩放
     * @param size
     * @param xscaleCombin
     */
    public void postScale(int size,float xscaleCombin){
        this.size = size;
        ((GViewPortHandler)mViewPortHandler).reset();
        final ViewPortHandler viewPortHandlerCombin = getViewPortHandler();
        float currentScale = culcMaxscale(size);
        viewPortHandlerCombin.setMaximumScaleX(currentScale);
        Matrix matrixCombin = viewPortHandlerCombin.getMatrixTouch();
        matrixCombin.reset();
        matrixCombin.postScale(xscaleCombin, 1f);
    }

    /**
     * 计算缩放倍数
     * @param count
     * @return
     */
    private float culcMaxscale(float count) {
        float max = 1;
        max = count / 127 * 5;
        return max;
    }

    /**
     * 清理相关设置
     */
    @Override
    public void clear() {
        super.clear();
    }

    /**
     * 设置图表数据
     * @param data
     */
    @Override
    public void setData(CombinedData data) {
        ((CombinedChartRenderer)mRenderer).getSubRenderers().clear();
        getXAxis().mDecimals = 0;
        clear();
        super.setData(data);
    }

    private boolean isPoxy = false;

    @Override
    public void setDescendantFocusability(int focusability) {
        super.setDescendantFocusability(focusability);
        if(!isPoxy){
            mViewPortHandler = new GViewPortHandler();
            isPoxy = true;
        }
    }

    /**
     * 重写改方法，实现禁止图表滑动
     * @return
     */
    @Override
    public boolean isFullyZoomedOut() {
        if(interception){
            return true;
        }
        return super.isFullyZoomedOut();
    }

    public boolean interception;

    /**
     * 禁止图表本身滑动，只可以高亮滑动
     * @param interception
     */
    public void setFullyZoomedOut(boolean interception){
        this.interception = interception;
    }

   /* GCombinedChart gCombinedChart ;
    public void setOther(GCombinedChart gCombinedChart){
        this.gCombinedChart = gCombinedChart;
    }*/


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        loadRenderer.onDraw(canvas);
    }

    @Override
    protected void drawMarkers(Canvas canvas) {
        //判断是否高亮，如果否退出，如果是则画出标志
        if (!valuesToHighlight())
            return;

        for (int i = 0; i < mIndicesToHighlight.length; i++) {

            Highlight highlight = mIndicesToHighlight[i];

            float[] pos = getMarkerPosition(highlight);

            String yValForXIndex1 = "";

            // 绘制高亮左侧标识
            if(mAxisLeft.isDrawLabelsEnabled() && iAxisLeftValueFormatter != null){
                yValForXIndex1 = iAxisLeftValueFormatter.getFormattedValue(highlight.getX(),mAxisLeft);
                myMarkerViewLeft.setTextView(yValForXIndex1);

                myMarkerViewLeft.measure(MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED),
                        MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));

                myMarkerViewLeft.layout(0, 0, myMarkerViewLeft.getMeasuredWidth(),
                        myMarkerViewLeft.getMeasuredHeight());

                if(mAxisLeft.getLabelPosition() == YAxis.YAxisLabelPosition.OUTSIDE_CHART){
                    myMarkerViewLeft.draw(canvas, mViewPortHandler.contentLeft() - myMarkerViewLeft.getWidth(),
                            pos[1] - myMarkerViewLeft.getHeight() / 2);
                } else {
                    if(pos[1] + myMarkerViewRight.getHeight()/2 > mViewPortHandler.contentBottom()){
                        myMarkerViewLeft.draw(canvas, mViewPortHandler.contentLeft(),
                                mViewPortHandler.contentBottom() - myMarkerViewRight.getHeight());
                    }else {
                        myMarkerViewLeft.draw(canvas, mViewPortHandler.contentLeft(), pos[1] - myMarkerViewLeft.getHeight() / 2);
                    }
                }
                // 绘制高亮右上角标识（换手率）
                String yValForXIndex2 = "";
                if (null != iAxisTopRightValueFormatter) {
                    yValForXIndex2 = iAxisTopRightValueFormatter.getFormattedValue(highlight.getX(),mAxisLeft);
                    if(null == yValForXIndex2) {
                        return;
                    }
                    mTopRightMarkerView.setTextView(yValForXIndex2);
                    mTopRightMarkerView.measure(MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED),
                            MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
                    mTopRightMarkerView.layout(0, 0, mTopRightMarkerView.getMeasuredWidth(),
                            mTopRightMarkerView.getMeasuredHeight());
                    mTopRightMarkerView.draw(canvas, mViewPortHandler.contentRight()- mTopRightMarkerView.getWidth(), mViewPortHandler.contentTop());
                }
            }

            // 绘制高亮右侧标识
            if(mAxisRight.isDrawLabelsEnabled() && iAxisRightValueFormatter != null){
                yValForXIndex1 = iAxisRightValueFormatter.getFormattedValue(highlight.getX(),mAxisRight);
                myMarkerViewRight.setTextView(yValForXIndex1);

                myMarkerViewRight.measure(MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED),
                        MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));

                myMarkerViewRight.layout(0, 0, myMarkerViewRight.getMeasuredWidth(),
                        myMarkerViewRight.getMeasuredHeight());

                if(mAxisRight.getLabelPosition() == YAxis.YAxisLabelPosition.OUTSIDE_CHART){
                    myMarkerViewRight.draw(canvas, mViewPortHandler.contentRight(), pos[1] - myMarkerViewRight.getHeight() / 2);
                } else {
                    if(pos[1] + myMarkerViewRight.getHeight()/2 > mViewPortHandler.contentBottom()){
                        myMarkerViewRight.draw(canvas, mViewPortHandler.contentRight()-myMarkerViewRight.getWidth(),
                                mViewPortHandler.contentBottom() - myMarkerViewRight.getHeight());
                    } else {
                        myMarkerViewRight.draw(canvas, mViewPortHandler.contentRight()-myMarkerViewRight.getWidth(),
                                pos[1] - myMarkerViewRight.getHeight() / 2);
                    }
                }
            }

            // 绘制高亮底部标识
            if(mXAxis.isDrawLabelsEnabled() && iAxisValueFormatter != null){
                yValForXIndex1 = iAxisValueFormatter.getFormattedValue(highlight.getX(),mXAxis);
                mBottomMarkerView.setTextView(yValForXIndex1);

                mBottomMarkerView.measure(MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED),
                        MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));

                mBottomMarkerView.layout(0, 0, mBottomMarkerView.getMeasuredWidth(),
                        mBottomMarkerView.getMeasuredHeight());
                //mBottomMarkerView.draw(canvas, pos[0]-mBottomMarkerView.getWidth()/2, mViewPortHandler.contentBottom());

                float width = Math.min(pos[0] - mBottomMarkerView.getWidth()/2,mViewPortHandler.contentRight() - mBottomMarkerView.getWidth());

                if(mAxisLeft.getLabelPosition() == YAxis.YAxisLabelPosition.OUTSIDE_CHART){
                    mBottomMarkerView.draw(canvas,width, mViewPortHandler.contentBottom());
                } else {
                    mBottomMarkerView.draw(canvas, width,mViewPortHandler.contentBottom());
                }
            }

            // 绘制高亮顶部标识
            if( isShowFqMsg && null != iAxisTopValueFormatter ){
                yValForXIndex1 = iAxisTopValueFormatter.getFormattedValue(highlight.getX(),mXAxis);

                if( null == yValForXIndex1 ){
                    return;
                }

                mTopMarkerView.setTextView(yValForXIndex1);

                mTopMarkerView.measure(MeasureSpec.makeMeasureSpec(0,MeasureSpec.UNSPECIFIED),
                        MeasureSpec.makeMeasureSpec(0,MeasureSpec.UNSPECIFIED));

                mTopMarkerView.layout(0,0,mTopMarkerView.getMeasuredWidth(),mTopMarkerView.getMeasuredHeight());

                mTopMarkerView.draw(canvas,mViewPortHandler.contentLeft(), mViewPortHandler.contentTop());
            }
            // 绘制高亮右上角标识（换手率）
//            Log.e("绘制高亮右上角标识 判断", (iAxisTopRightValueFormatter != null) + "");
//            if(mAxisLeft.isDrawLabelsEnabled() && iAxisLeftValueFormatter != null){
//                String yValForXIndex2 = "";
//                yValForXIndex2 = iAxisTopRightValueFormatter.getFormattedValue(highlight.getX(),mAxisLeft);
//                mTopRightMarkerView.setTextView(yValForXIndex2);
//                Log.e("绘制高亮顶部右侧标识", "1");
//                mTopRightMarkerView.measure(MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED),
//                        MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
//                mTopRightMarkerView.layout(0, 0, mTopRightMarkerView.getMeasuredWidth(),
//                        mTopRightMarkerView.getMeasuredHeight());
//                mTopRightMarkerView.draw(canvas, mViewPortHandler.contentRight()- mTopRightMarkerView.getWidth(), mViewPortHandler.contentTop());
//            }
        }
    }

    /**
     * 高亮数据提供者
     */
    IAxisValueFormatter iAxisValueFormatter;
    IAxisValueFormatter iAxisLeftValueFormatter;
    IAxisValueFormatter iAxisRightValueFormatter;
    IAxisValueFormatter iAxisTopValueFormatter;
    IAxisValueFormatter iAxisTopRightValueFormatter;

    public void setMarkerBottomFormatter(IAxisValueFormatter iAxisValueFormatter){
        this.iAxisValueFormatter = iAxisValueFormatter;
    }

    public void setMarkerLeftFormatter(IAxisValueFormatter iAxisLeftValueFormatter){
        this.iAxisLeftValueFormatter = iAxisLeftValueFormatter;
    }

    public void setMarkerRightFormatter(IAxisValueFormatter iAxisRightValueFormatter){
        this.iAxisRightValueFormatter = iAxisRightValueFormatter;
    }

    public void setMarkerTopFormatter(IAxisValueFormatter iAxisTopValueFormatter){
        this.iAxisTopValueFormatter = iAxisTopValueFormatter;
    }
    public void setMarkerTopRightFormatter(IAxisValueFormatter iAxisTopRightValueFormatter){
        this.iAxisTopRightValueFormatter = iAxisTopRightValueFormatter;
    }
}
