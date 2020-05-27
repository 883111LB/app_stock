package com.cvicse.stock.chart.renderer;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.github.mikephil.charting.renderer.Renderer;
import com.github.mikephil.charting.utils.ViewPortHandler;

/**
 * Created by liuzilu on 2017/2/15.
 */

public class LoadRenderer extends Renderer{
    private Paint mPaint = new Paint();
    public LoadRenderer(ViewPortHandler viewPortHandler) {
        super(viewPortHandler);
        mPaint.setColor(Color.WHITE);
        mPaint.setTextSize(50);
    }

    public void onDraw(Canvas canvas){
        // make sure the data cannot be drawn outside the content-rect
        int clipRestoreCount = canvas.save();
        canvas.clipRect(mViewPortHandler.getContentRect());
        if(mViewPortHandler.getTransX() > 0){
            canvas.drawText("jiazai...",mViewPortHandler.getTransX()-100,mViewPortHandler.getContentCenter().y,mPaint);
        }
        // Removes clipping rectangle
        canvas.restoreToCount(clipRestoreCount);
    }
}
