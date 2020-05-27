package com.cvicse.stock.chart.renderer;

import android.graphics.Canvas;

import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.utils.MPPointF;
import com.github.mikephil.charting.utils.Transformer;
import com.github.mikephil.charting.utils.ViewPortHandler;

/**
 * k线图底部底部图表x轴绘画类
 * Created by liu_zlu on 2017/5/1 09:26
 */
public class KGXAxisRenderer extends GXAxisRenderer {
    private int labelWidth = 0;
    public KGXAxisRenderer(ViewPortHandler viewPortHandler, XAxis xAxis, Transformer trans) {
        super(viewPortHandler, xAxis, trans);
    }

    /**
     * x轴具体绘画方法，只展示第一个数据以及最后一个数据
     * @param c
     * @param pos
     * @param anchor
     */
    @Override
    protected void drawLabels(Canvas c, float pos, MPPointF anchor) {

        final float labelRotationAngleDegrees = mXAxis.getLabelRotationAngle();
        //super.drawLabels(c, pos, anchor);
        String label = mXAxis.getValueFormatter().getFormattedValue(yMin, mXAxis);
        if(labelWidth == 0){
            labelWidth = (int) mAxisLabelPaint.measureText(label);
        }
        /**
         * 画开始时间
         */
        drawLabel(c, label, mViewPortHandler.offsetLeft()+labelWidth/2, pos, anchor, labelRotationAngleDegrees);

        label = mXAxis.getValueFormatter().getFormattedValue(yMax, mXAxis);
        /**
         * 画结束时间
         */
        drawLabel(c, label, mViewPortHandler.contentRight()-labelWidth/2, pos, anchor, labelRotationAngleDegrees);
    }

}
