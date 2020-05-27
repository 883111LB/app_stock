package com.cvicse.stock.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by liu_zlu on 2017/3/7 17:42
 */
public class CustomProgressBar extends View {

    private int progress = 0;
    private int cricle = 0;
    private int rectWidth = 0;
    private Paint mPaint;
    private int height = 0;
    private int width = 0;

    public CustomProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public CustomProgressBar(Context context) {
        super(context);
        init();
    }
    void init(){
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.GRAY);
    }

    /*@Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(getDefaultSize(widthMeasureSpec,heightMeasureSpec), getDefaultSize(heightMeasureSpec,heightMeasureSpec));
    }*/

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        height = getMeasuredHeight();
        width = getMeasuredWidth();
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

        cricle = height/2;
        rectWidth = width*progress/100 - cricle;
        if(rectWidth > 0){
            canvas.drawRect(0,0,rectWidth,height,mPaint);
            canvas.drawCircle(rectWidth,cricle,cricle,mPaint);
        } else {
            canvas.drawCircle(0,cricle,cricle,mPaint);
        }

    }

    public void setProgress(int progress){
        this.progress = progress;
       // requestLayout();
        invalidate();
    }

}
