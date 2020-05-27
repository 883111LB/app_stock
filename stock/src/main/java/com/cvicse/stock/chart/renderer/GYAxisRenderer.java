package com.cvicse.stock.chart.renderer;

import com.cvicse.stock.chart.components.GYAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.renderer.YAxisRenderer;
import com.github.mikephil.charting.utils.Transformer;
import com.github.mikephil.charting.utils.Utils;
import com.github.mikephil.charting.utils.ViewPortHandler;

/**
 * 侧边栏绘画类
 * Created by liuzilu on 2017/1/11.
 */

public class GYAxisRenderer extends YAxisRenderer {
    public GYAxisRenderer(ViewPortHandler viewPortHandler, YAxis yAxis, Transformer trans) {
        super(viewPortHandler, yAxis, trans);
    }

    /**
     * 从可见范围内的最小值到最大值等差地生成一个label数组
     * @param min
     * @param max
     */
    @Override
    protected void computeAxisValues(float min, float max) {
        float yMin = min;
        float yMax = max;
        if(!((GYAxis)mYAxis).isShowOnlyMinMaxEnabled()){
            super.computeAxisValues(min,max);
            return;
        }
        int labelCount = mYAxis.getLabelCount();
        double range = Math.abs(yMax - yMin);

        if (labelCount == 0 || range <= 0) {
            mYAxis.mEntries = new float[]{};
            mYAxis.mEntryCount = 0;
            return;
        }

        // Find out how much spacing (in y value space) between axis values
        double rawInterval = range / labelCount;
        double interval = Utils.roundToNextSignificant(rawInterval);

        // If granularity is enabled, then do not allow the interval to go below specified granularity.
        // This is used to avoid repeated values when rounding values for display.
        if (mYAxis.isGranularityEnabled())
            interval = interval < mYAxis.getGranularity() ? mYAxis.getGranularity() : interval;

        // Normalize interval
        double intervalMagnitude = Utils.roundToNextSignificant(Math.pow(10, (int) Math.log10(interval)));
        int intervalSigDigit = (int) (interval / intervalMagnitude);
        if (intervalSigDigit > 5) {
            // Use one order of magnitude higher, to avoid intervals like 0.9 or
            // 90
            interval = Math.floor(10 * intervalMagnitude);
        }
        // if the labels should only show min and max
        if (mYAxis instanceof GYAxis && ((GYAxis)mYAxis).isShowOnlyMinMaxEnabled()) {
            if(labelCount == 3){
                mYAxis.mEntryCount = 3;
                mYAxis.mEntries = new float[3];
                if(((GYAxis)mYAxis).isAverage()){
                    mYAxis.mEntries[0] = 20;
                    mYAxis.mEntries[1] = 50;
                    mYAxis.mEntries[2] = 80;
                } else {
                    mYAxis.mEntries[0] = yMin;
                    mYAxis.mEntries[1] = (yMin+yMax)/2;
                    mYAxis.mEntries[2] = yMax;
                }
            } else if(labelCount == 5) {
                mYAxis.mEntryCount = 5;
                mYAxis.mEntries = new float[5];
                float item = (yMax-yMin)/4;
                mYAxis.mEntries[0] = yMin;
                mYAxis.mEntries[1] = yMin+item;
                mYAxis.mEntries[2] = yMin + item*2;
                mYAxis.mEntries[3] = yMin + item*3;
                mYAxis.mEntries[4] = yMax;
            }else{
                mYAxis.mEntryCount = 2;
                mYAxis.mEntries = new float[2];
                mYAxis.mEntries[0] = yMin;
                mYAxis.mEntries[1] = yMax;
            }

        }

       /* // set decimals
        if (interval < 1) {
            mYAxis.mDecimals = (int) Math.ceil(-Math.log10(interval));
        } else {
            mYAxis.mDecimals = 0;
        }*/
    }

    /**
     * 将具体数据变为位置信息
     * @return
     */
    @Override
    protected float[] getTransformedPositions() {

        if (mYAxis instanceof GYAxis && ((GYAxis)mYAxis).isShowOnlyMinMaxEnabled()) {
            if(mYAxis.getLabelCount() == 3){
                if(((GYAxis)mYAxis).isAverage()){
                    if(averageBuffer != null){
                        return averageBuffer;
                    }
                    averageBuffer = new float[6];
                    float height = mViewPortHandler.contentHeight();
                    averageBuffer[1] = (float) (mViewPortHandler.contentTop() + height*0.8);
                    averageBuffer[3] = (float) (mViewPortHandler.contentTop()+height*0.5);
                    averageBuffer[5] = (float) (mViewPortHandler.contentTop()+height*0.2);
                    return averageBuffer;
                } else {
                    if(topMiddleBottomBuffer != null){
                        return topMiddleBottomBuffer;
                    }
                    topMiddleBottomBuffer = new float[6];
                    topMiddleBottomBuffer[1] = mViewPortHandler.contentBottom();
                    topMiddleBottomBuffer[3] = (mViewPortHandler.contentBottom()+mViewPortHandler.contentTop())/2;
                    topMiddleBottomBuffer[5] = mViewPortHandler.contentTop();
                    return topMiddleBottomBuffer;
                }

            } else if(mYAxis.getLabelCount() == 5){
                if(fiveBottomBuffer != null){
                    return fiveBottomBuffer;
                }
                float itemHeight = (mViewPortHandler.contentTop() - mViewPortHandler.contentBottom())/4;
                fiveBottomBuffer = new float[10];
                fiveBottomBuffer[1] = mViewPortHandler.contentBottom();
                fiveBottomBuffer[3] = mViewPortHandler.contentBottom()+itemHeight;
                fiveBottomBuffer[5] = (mViewPortHandler.contentBottom()+mViewPortHandler.contentTop())/2;
                fiveBottomBuffer[7] = mViewPortHandler.contentTop()-itemHeight;
                fiveBottomBuffer[9] = mViewPortHandler.contentTop();
                return fiveBottomBuffer;
            }else{
                if(topBottomBuffer != null){
                    return topBottomBuffer;
                }
                topBottomBuffer = new float[4];
                topBottomBuffer[1] = mViewPortHandler.contentBottom();
                topBottomBuffer[3] = mViewPortHandler.contentTop();
                return topBottomBuffer;
            }
        }
        return super.getTransformedPositions();
    }

    private float[] averageBuffer;
    private float[] topMiddleBottomBuffer;
    private float[] fiveBottomBuffer;
    private float[] topBottomBuffer;

}
