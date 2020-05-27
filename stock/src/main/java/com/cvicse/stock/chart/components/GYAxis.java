package com.cvicse.stock.chart.components;

import android.graphics.Paint;

import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.utils.Utils;

/**
 * 自定义侧边栏，保存边距不变，改变字体，边距开关控制
 * Created by liu_zlu on 2017/1/10 14:29
 */
public class GYAxis extends YAxis {

    private boolean relative = false;

    public void setRelative(boolean relative) {
        this.relative = relative;
    }

    /**]
     * 固定边距
     */
    private float fixedWidth = Utils.convertDpToPixel(35);
    private float maxTextSize = Utils.convertDpToPixel(14);
    private float minTextSize = Utils.convertDpToPixel(5);
    public GYAxis(){

    }
    public GYAxis(YAxis.AxisDependency axisDependency){
        super(axisDependency);
    }
    @Override
    public float getRequiredWidthSpace(Paint p) {
        mTextSize = this.getTextSize();
        p.setTextSize(this.getTextSize());

        String label = this.getLongestLabel();
        float width = (float) Utils.calcTextWidth(p, label) + getXOffset() * 2f;
        if(label == null || label.equals("")){
            return fixedWidth;
        }
        if(fixedWidth > width){
            while (width < fixedWidth && mTextSize <= maxTextSize) {
                width = calcTextWidth(p,label,false);
            }
            mTextSize--;
        } else {
            while (width > fixedWidth && mTextSize >= minTextSize) {
                width = calcTextWidth(p,label,true);
            }
        }
        this.setTextSize(Utils.convertPixelsToDp(mTextSize));
        p.setTextSize(mTextSize);
        return fixedWidth;
    }

    private float calcTextWidth(Paint paint, String label,boolean dec){
        if(dec){
            mTextSize--;
        } else {
            mTextSize++;
        }
        paint.setTextSize(mTextSize);
        return (float) Utils.calcTextWidth(paint, label) + getXOffset() * 2f;
    }

    /**
     * 只展示最大最小值，GYAxisRenderer是否起作用的标志
     * @return
     */
    public boolean isShowOnlyMinMaxEnabled(){
        return showOnlyMinMaxEnabled;
    }

    private boolean showOnlyMinMaxEnabled = false;
    public void setShowOnlyMinMaxEnabled(boolean showOnlyMinMaxEnabled){
        this.showOnlyMinMaxEnabled = showOnlyMinMaxEnabled;
    }

    @Override
    public void calculate(float dataMin, float dataMax) {
        super.calculate(dataMin, dataMax);
        if(!relative){
            return;
        }
        float temp = Math.abs(this.mAxisMaximum);
        float temp2 = Math.abs(this.mAxisMinimum);
        float max = temp > temp2 ? temp: temp2;
        mAxisMaximum = max;
        mAxisMinimum = - max;
        // calc actual range
        this.mAxisRange = Math.abs(this.mAxisMaximum - this.mAxisMinimum);
    }

    /**
     * 侧边栏是否平分
     * @return
     */
    public boolean isAverage() {
        return average;
    }

    boolean average = false;
    public void setAverage(boolean average){
        this.average = average;
    }

    @Override
    public boolean needsOffset() {
        if(needsOffset){
            return needsOffset;
        }
        return super.needsOffset();
    }

    /**
     * 需不需要边距
     */
    private boolean needsOffset;
    public void setNeedsOffset(boolean needsOffset){
        this.needsOffset = needsOffset;
    }
}
