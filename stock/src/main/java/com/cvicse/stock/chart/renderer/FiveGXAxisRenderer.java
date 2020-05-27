package com.cvicse.stock.chart.renderer;

import android.graphics.Canvas;

import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.utils.MPPointF;
import com.github.mikephil.charting.utils.Transformer;
import com.github.mikephil.charting.utils.ViewPortHandler;

/**
 * 五日图底部图表x轴重写
 * Created by liu_zlu on 2017/3/3 20:52
 */
public class FiveGXAxisRenderer extends GXAxisRenderer {
    public FiveGXAxisRenderer(ViewPortHandler viewPortHandler, XAxis xAxis, Transformer trans) {
        super(viewPortHandler, xAxis, trans);
    }

    /**
     * draws the x-labels on the specified y-position
     *
     * @param pos
     */
    protected void drawLabels(Canvas c, float pos, MPPointF anchor) {
        final float labelRotationAngleDegrees = mXAxis.getLabelRotationAngle();
        float width = mViewPortHandler.contentWidth();
        float five_width = width/5;
        for (int i = 0; i < 5; i += 1) {
            String label = mXAxis.getValueFormatter().getFormattedValue(-(i+1), mXAxis);
            drawLabel(c, label, five_width*(i+0.5f)+mViewPortHandler.offsetLeft(), pos, anchor, labelRotationAngleDegrees);
        }
    }

 /*   @Override
    public void renderGridLines(Canvas c) {
        if (!mXAxis.isDrawGridLinesEnabled() || !mXAxis.isEnabled())
            return;

        int clipRestoreCount = c.save();
        c.clipRect(getGridClippingRect());

        if(mRenderGridLinesBuffer.length != mAxis.mEntryCount * 2){
            mRenderGridLinesBuffer = new float[mXAxis.mEntryCount * 2];
        }
        float[] positions = mRenderGridLinesBuffer;

        for (int i = 0; i < positions.length; i += 2) {
            positions[i] = mXAxis.mEntries[i / 2];
            positions[i + 1] = mXAxis.mEntries[i / 2];
        }

        mTrans.pointValuesToPixel(positions);

        setupGridPaint();

        Path gridLinePath = mRenderGridLinesPath;
        gridLinePath.reset();

        for (int i = 0; i < positions.length; i += 2) {

            drawGridLine(c, positions[i], positions[i + 1], gridLinePath);
        }

        c.restoreToCount(clipRestoreCount);
    }*/
}
