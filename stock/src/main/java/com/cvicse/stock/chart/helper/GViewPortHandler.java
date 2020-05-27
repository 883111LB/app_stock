package com.cvicse.stock.chart.helper;

import android.graphics.Rect;
import android.graphics.RectF;

import com.github.mikephil.charting.utils.ViewPortHandler;

/**
 * Created by liuzilu on 2017/1/11.
 */

public class GViewPortHandler extends ViewPortHandler {

    public GViewPortHandler(){
    }
    private static Rect mDrawTextRectBuffer = new Rect();
   /* @Override
    public boolean isInBoundsLeft(float x) {
        String str = xAxisRenderer.xAxis.getValueFormatter().getXValue(xAxisRenderer.xAxis.getValues().get(0),0,null);
        xAxisRenderer.getPaintAxisLine().getTextBounds(str,0,str.length(),mDrawTextRectBuffer);
        return mContentRect.left <= x+mDrawTextRectBuffer.width()/2? true : false;
    }

    @Override
    public boolean isInBoundsRight(float x) {
        return mContentRect.right >= x-mDrawTextRectBuffer.width()/2 ? true : false;
    }*/

    @Override
    public RectF getContentRect() {
        return super.getContentRect();
    }

    @Override
    public boolean isInBoundsLeft(float x) {
        return mContentRect.left <= x+2;
    }

    @Override
    public boolean isInBoundsRight(float x) {
        return mContentRect.right >= x-2;
    }

    public void reset(){
        for(int i =0;i<matrixBuffer.length;i++){
            matrixBuffer[i] = 0;
        }
    }

    /*public void limitTransAndScale(Matrix matrix, RectF content) {

        matrix.getValues(matrixBuffer);

        float curTransX = matrixBuffer[Matrix.MTRANS_X];
        float curScaleX = matrixBuffer[Matrix.MSCALE_X];

        float curTransY = matrixBuffer[Matrix.MTRANS_Y];
        float curScaleY = matrixBuffer[Matrix.MSCALE_Y];

        // min scale-x is 1f
        mScaleX = Math.min(Math.max(mMinScaleX, curScaleX), mMaxScaleX);

        // min scale-y is 1f
        mScaleY = Math.min(Math.max(mMinScaleY, curScaleY), mMaxScaleY);

        float width = 0f;
        float height = 0f;

        if (content != null) {
            width = content.width();
            height = content.height();
        }

        float maxTransX = -width * (mScaleX - 1f);
        mTransX = Math.min(Math.max(curTransX, maxTransX - mTransOffsetX), mTransOffsetX);

        float maxTransY = height * (mScaleY - 1f);
        mTransY = Math.max(Math.min(curTransY, maxTransY + mTransOffsetY), -mTransOffsetY);

        matrixBuffer[Matrix.MTRANS_X] = mTransX;
        matrixBuffer[Matrix.MSCALE_X] = mScaleX;

        matrixBuffer[Matrix.MTRANS_Y] = mTransY;
        matrixBuffer[Matrix.MSCALE_Y] = mScaleY;

        matrix.setValues(matrixBuffer);
    }*/

   /* @Override
    public void limitTransAndScale(Matrix matrix, RectF content) {
        matrix.getValues(matrixBuffer);

        float curTransX = matrixBuffer[Matrix.MTRANS_X];
        if(curTransX <= 0){
            setDragOffsetX(0);
        } else {
            setDragOffsetX(100);
        }
        super.limitTransAndScale(matrix, content);
    }*/
}
