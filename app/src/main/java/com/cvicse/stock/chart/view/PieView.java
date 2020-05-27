package com.cvicse.stock.chart.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.cvicse.stock.markets.data.PieData;

import java.util.ArrayList;

/** 自定义饼图
 * Created by tang_xqing on 2017/8/17.
 */

public class PieView extends View {
    // 颜色表 (注意: 此处定义颜色使用的是ARGB，带Alpha通道的)
    private int[] mColors = {0xFFCCFF00, 0xFF6495ED, 0xFFE32636, 0xFF800000, 0xFF808000, 0xFFFF8C69, 0xFF808080,
            0xFFE6B800, 0xFF7CFC00};

    private Paint mPaint = new Paint();//画笔
    private float mStartAngle;//起始角度

    private int mWidth,mHeight;//屏幕的宽高
    private float radius = 100;  // 半径

    private int defaultResult = 100 ;
    private ArrayList<PieData> mData;

    public PieView(Context context) {
        this(context,null);
    }

    public PieView(Context context, AttributeSet attrs) {
        super(context, attrs);
        //填充，描边
        mPaint.setStyle(Paint.Style.FILL);
        //抗锯齿
        mPaint.setAntiAlias(true);
    }

    /**
     * 确定View的大小
     * @param w
     * @param h
     * @param oldw
     * @param oldh
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(measureDimension(widthMeasureSpec),measureDimension(heightMeasureSpec));
    }

    private int measureDimension(int widthMeasureSpec) {
        int result = 0;
        int size = MeasureSpec.getSize(widthMeasureSpec);
        int mode = MeasureSpec.getMode(widthMeasureSpec);

        if(mode == MeasureSpec.EXACTLY){
            result = size;
        }else{
            result = defaultResult;
            if(mode == MeasureSpec.AT_MOST){
                result = Math.min(size,result);
            }
        }
        return result;
    }

    /**
     * 绘制View
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(mData == null || mData.size() <= 0){
            return;
        }
        float currentAngle = mStartAngle;
        radius = Math.min(mWidth/2,mHeight/2);  // 圆的半径

        canvas.translate(mWidth/2,mHeight/2);//将坐标系平移到屏幕中心

        RectF rectF = new RectF(-radius,-radius, radius, radius);
        mPaint.setColor(Color.BLACK);
        mPaint.setStyle(Paint.Style.STROKE);
        //画圆的外接矩形
        //canvas.drawRect(rectF,mPaint);

        mPaint.setStyle(Paint.Style.FILL);

        for(int i=0;i<mData.size();i++){
            PieData pieData = mData.get(i);
            mPaint.setColor(pieData.getColor());
            canvas.drawArc(rectF,currentAngle,pieData.getAngle(),true,mPaint);
            //角度更新
            currentAngle+= pieData.getAngle();
        }
    }

    /**
     *  设置起始角度
     * @param angle
     */
    public void setStartAngle(float angle){
        this.mStartAngle = angle;
        invalidate();
    }

    /**
     * 设置半径
     * @param radius
     */
    public void setRadius(float radius){
        this.radius = radius;
    }

    /**
     * 饼图数据
     * @param data
     */
    private void initData(ArrayList<PieData> data) {
        if(data == null || data.size() <= 0){
            return;
        }
    }

    public void setData(ArrayList<PieData> data){
        this.mData = data;
        initData(mData);
        invalidate();
    }
}
