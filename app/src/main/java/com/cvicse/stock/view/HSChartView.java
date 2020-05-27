package com.cvicse.stock.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

/**
 * Created by shi_yhui on 2018/12/3.
 */

public class HSChartView extends HSUpDownChartView{

    public HSChartView(Context context) {
        super(context);
    }

    public HSChartView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public HSChartView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    /**
     * 画柱形图上的值
     * @param canvas
     * @param mPaint
     */
    @Override
    protected void drawColumnValue(Canvas canvas, Paint mPaint) {
        float cellWidth = width/chartDividedSizeX;
        if (columnInfo != null){
            mPaint.setColor(Color.parseColor("#4682B4"));
            for (int i = 0; i < columnInfo.length; i++) {
                float leftTopY = originalY - height*(columnInfo[i][0])/chartDividedSizeY;
                canvas.drawText(columnInfo[i][0]+"",
                        (originalX + cellWidth*(i+1)) + cellWidth/2,
                        leftTopY - 10, mPaint);
            }

        }
    }

    /**
     * 画柱形条
     * @param canvas
     * @param mPaint
     */
    @Override
    protected void drawColumn(Canvas canvas, Paint mPaint) {

        if (columnInfo != null){
            float cellWidth = width/chartDividedSizeX;
            for (int i = 0; i < columnInfo.length; i++) {
                mPaint.setColor(columnInfo[i][1]);
                float leftTopY = originalY - height*(columnInfo[i][0])/chartDividedSizeY;
                canvas.drawRect(originalX + cellWidth * (i + 1),
                        leftTopY, originalX+cellWidth*(i+2), originalY, mPaint);
            }
        }
    }

    /**
     * 画Y轴的刻度值
     * @param canvas
     * @param mPaint
     */
    @Override
    protected void drawYChartScaleValue(Canvas canvas, Paint mPaint) {

        mPaint.setColor(mChartTextColor);
        mPaint.setStrokeWidth(2);
        float cellHeight = height / chartDividedSizeY;
        for (int i = 0; i < chartDividedSizeY; i++) {
            canvas.drawText(String.valueOf(i),
                    originalX - 30,
                    originalY - cellHeight * i + 10, mPaint);
        }
    }

    /**
     * 画Y轴的刻度
     * @param canvas
     * @param mPaint
     */
    @Override
    protected void drawYChartScale(Canvas canvas, Paint mPaint) {

        mPaint.setColor(mChartTextColor);
        mPaint.setStrokeWidth(2);
        float cellHeight = height / chartDividedSizeY;
        for (int i = 0; i < chartDividedSizeY - 1; i++) {
            canvas.drawLine(originalX, (originalY - cellHeight * (i+1)),
                    originalX + 10, (originalY - cellHeight * (i+1)), mPaint);
        }
    }

    /**
     * 画X轴的刻度值
     * @param canvas
     * @param mPaint
     */
    @Override
    protected void drawXChartScaleValue(Canvas canvas, Paint mPaint) {

        mPaint.setColor(mChartTextColor);
        mPaint.setTextSize(16);
//        mPaint.setFakeBoldText(true);
        float cellWidth = width/chartDividedSizeX;
        for (int i = 0; i < chartDividedSizeX; i++) {
            canvas.drawText(String.valueOf(i), cellWidth * (i+1) + originalX - 35,
                    originalY + 30, mPaint);
        }

    }

    /**
     * 画X轴刻度
     * @param canvas
     * @param mPaint
     */
    @Override
    protected void drawXChartScale(Canvas canvas, Paint mPaint) {

        mPaint.setColor(mChartTextColor);
        mPaint.setStrokeWidth(2);
        float cellWidh = width / chartDividedSizeX;
        for (int i = 0; i < chartDividedSizeX - 1; i++) {
            canvas.drawLine(cellWidh * (i+1) + originalX,
                    originalY,
                    cellWidh * (i+1) + originalX,
                    originalY- 10, mPaint );
        }
    }

    /**
     * 画Y轴
     * @param canvas
     * @param mPaint
     */
    @Override
    protected void drawYChart(Canvas canvas, Paint mPaint) {

        mPaint.setColor(mChartTextColor);
        mPaint.setStrokeWidth(3);
        canvas.drawLine(originalX, originalY, originalX, originalY- height, mPaint);

    }

    /**
     * 画X轴
     * @param canvas
     * @param mPaint
     */
    @Override
    protected void drawXChart(Canvas canvas, Paint mPaint) {

        mPaint.setColor(mChartTextColor);
        mPaint.setStrokeWidth(3);
        canvas.drawLine(originalX, originalY, originalX + width, originalY, mPaint);
    }
}
