package com.cvicse.stock.chart.renderer;

import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.renderer.XAxisRenderer;
import com.github.mikephil.charting.utils.Transformer;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.util.ArrayList;

/**
 * x轴计算绘画类（通用的）
 * Created by liuzilu on 2017/1/11.
 */

public class GXAxisRenderer extends XAxisRenderer {
    public XAxis xAxis;
    int yMin = 0;
    int yMax = 0;
    public GXAxisRenderer(ViewPortHandler viewPortHandler, XAxis xAxis, Transformer trans) {
        super(viewPortHandler, xAxis, trans);
        this.xAxis = xAxis;
    }

    /**
     * 计算x周展示数据
     * @param min 当前图表显示的最小值
     * @param max 当前图表显示的最大值
     */
    @Override
    protected void computeAxisValues(float min, float max) {
        yMin = (int) min;
        yMax = (int) max;
        try {
            mXAxis.getValueFormatter().getFormattedValue(yMin, mXAxis);
            mXAxis.getValueFormatter().getFormattedValue(yMax, mXAxis);
        }catch (IndexOutOfBoundsException e) {
            return;
        }
        double range = Math.abs(yMax - yMin);

        if (range <= 0 || Double.isInfinite(range)) {
            mAxis.mEntries = new float[]{};
            mAxis.mCenteredEntries = new float[]{};
            mAxis.mEntryCount = 0;
            return;
        }

        String label;
        ArrayList<Integer> integers = new ArrayList<>();
        for(int i = yMin;i <= yMax;i++){
            /**
             * 只要有数据就展示
             */
            label = mXAxis.getValueFormatter().getFormattedValue(i, mXAxis);
            if( null== label || label.equals("")){
                continue;
            }
            integers.add(i);
        }
        mAxis.mEntryCount = integers.size();

        if (mAxis.mEntries.length < mAxis.mEntryCount) {
            // Ensure stops contains at least numStops elements.
            mAxis.mEntries = new float[mAxis.mEntryCount];
        }

        for (int i = 0;i < mAxis.mEntryCount;i++) {
            mAxis.mEntries[i] = integers.get(i);
        }
    }

   /* @Override
    protected void drawLabels(Canvas c, float pos, MPPointF anchor) {
        final float labelRotationAngleDegrees = mXAxis.getLabelRotationAngle();
        //super.drawLabels(c, pos, anchor);
        String label = mXAxis.getValueFormatter().getFormattedValue(yMin, mXAxis);
        drawLabel(c, label, mViewPortHandler.offsetLeft()+200, pos, anchor, labelRotationAngleDegrees);
    }*/
}
