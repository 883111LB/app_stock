package com.cvicse.stock.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

import com.cvicse.stock.R;

/**
 * Created by shi_yhui on 2018/12/3.
 */

public abstract  class HSUpDownChartView extends View {

    private Context mContext;
    //画笔
    private Paint mPaint;
    //视图的宽
    public int width;
    //视图的高
    public int height;
    //原始起点的X,Y坐标值
    public int originalX = 80;
    public int originalY = 500;
    //X,Y轴等份划分
    public int chartDividedSizeX;
    public int chartDividedSizeY;
    //第一个维度为值,第二个维度为颜色
    public int[][] columnInfo;
    //图表标题
    private String mChartTitle;
    //X轴Name
    private String mXChartName;
    //Y轴Name
    private String mYChartName;
    //坐标轴上字体的大小
    private float mChartTextSize;
    //坐标轴字体的颜色
    public int mChartTextColor;

    public HSUpDownChartView(Context context) {
        this(context, null);
    }

    public HSUpDownChartView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, -1);
    }


    /**
     * 设置X轴的刻度份数
     * @param chartDividedSizeX
     */
    public void setChartDividedSizeX(int chartDividedSizeX) {
        this.chartDividedSizeX = chartDividedSizeX;
    }
    /**
     * 设置Y轴的刻度份数
     * @param chartDividedSizeY
     */
    public void setChartDividedSizeY(int chartDividedSizeY) {
        this.chartDividedSizeY = chartDividedSizeY;
    }
    /**
     * 设置条形图的数值和颜色
     * @param columnInfo
     */
    public void setColumnInfo(int[][] columnInfo) {
        this.columnInfo = columnInfo;
    }

    public HSUpDownChartView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;

        //获取自定义样式
//        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.HSUpDownChartStyle);

        //取出自定义的设置
//        mChartTitle = typedArray.getString(R.styleable.HSUpDownChartStyle_chartTitle);
//        mXChartName = typedArray.getString(R.styleable.HSUpDownChartStyle_xchartName);
//        mYChartName = typedArray.getString(R.styleable.HSUpDownChartStyle_ychartName);
//
//        mChartTextColor = typedArray.getColor(R.styleable.HSUpDownChartStyle_chartTextColor, context.getResources().getColor(android.R.color.black));
//        mChartTextSize = typedArray.getDimension(R.styleable.HSUpDownChartStyle_chartTextSize, 12);

        //若不为null
//        if (typedArray != null){
            //回收
//            typedArray.recycle();
//        }

        //初始化画笔
        initPaint();
    }
    //初始化画笔
    private void initPaint() {
        if (mPaint == null){
            mPaint = new Paint();
            //防抖动
            mPaint.setDither(true);
            //去锯齿
            mPaint.setAntiAlias(true);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //视图的宽 为 屏幕的宽 - 起始位置
        width = getWidth() - originalX - 80;
        //视图的高度 为 若原始位置超过屏幕高度 则设置 屏幕高度为视图高度  否则 设置原始位置为视图高度
        height = (originalY > getHeight() ? getHeight():originalY) - 100;


        //画X轴
        drawXChart(canvas, mPaint);
        //画Y轴
        drawYChart(canvas, mPaint);
        //画标题
        drawTitle(canvas, mPaint);
        //画X刻度
        drawXChartScale(canvas, mPaint);
        //画X刻度值
        drawXChartScaleValue(canvas, mPaint);
        //画Y刻度
        drawYChartScale(canvas, mPaint);
        //画Y刻度值
        drawYChartScaleValue(canvas, mPaint);
        //画X箭头
        drawXChartArrow(canvas, mPaint);
        //画Y箭头
        drawYChartArrow(canvas, mPaint);
        //画柱形图
        drawColumn(canvas, mPaint);
        //画柱形图上的值
        drawColumnValue(canvas, mPaint);

    }

    /**
     * 画柱形图上的值
     * @param canvas
     * @param mPaint
     */
    protected abstract void drawColumnValue(Canvas canvas, Paint mPaint);

    /**
     * 画柱形条
     * @param canvas
     * @param mPaint
     */
    protected abstract void drawColumn(Canvas canvas, Paint mPaint);

    /**
     * 画Y轴的箭头
     * @param canvas
     * @param mPaint
     */
    private void drawYChartArrow(Canvas canvas, Paint mPaint) {

        Path mPathY = new Path();
        //画法介绍:画一个三角形,将箭头顶点路径移动到 Y轴顶点-30的位置(), 然后X轴左右各+-10 封闭起来
        mPathY.moveTo(originalX, originalY - height - 30);
        mPathY.lineTo(originalX - 10, originalY - height);
        mPathY.lineTo(originalX + 10, originalY - height);

        mPathY.close();
        mPaint.setColor(mChartTextColor);
        canvas.drawPath(mPathY, mPaint);
        canvas.drawText(mYChartName, originalX - 50, originalY - height - 30, mPaint);
    }

    /**
     * 画X轴的箭头
     * @param canvas
     * @param mPaint
     */
    private void drawXChartArrow(Canvas canvas, Paint mPaint) {
        Path mPathX = new Path();
        //画法介绍:其实就是画一个三角形,将箭头顶点路径移动到 X轴顶点+30的位置, 然后Y轴上下各+-10 封闭起来
        mPathX.moveTo(originalX + width + 30, originalY);
        mPathX.lineTo(originalX + width, originalY + 10);
        mPathX.lineTo(originalX + width, originalY - 10);

        mPathX.close();
        mPaint.setColor(mChartTextColor);
        canvas.drawPath(mPathX, mPaint);
        canvas.drawText(mXChartName, originalX + width, originalY + 50, mPaint);
    }

    /**
     * 画Y轴的刻度值
     * @param canvas
     * @param mPaint
     */
    protected abstract void drawYChartScaleValue(Canvas canvas, Paint mPaint);

    /**
     * 画Y轴的刻度
     * @param canvas
     * @param mPaint
     */
    protected abstract void drawYChartScale(Canvas canvas, Paint mPaint);

    /**
     * 画X轴的刻度值
     * @param canvas
     * @param mPaint
     */
    protected abstract void drawXChartScaleValue(Canvas canvas, Paint mPaint);

    /**
     * 画X轴刻度
     * @param canvas
     * @param mPaint
     */
    protected abstract void drawXChartScale(Canvas canvas, Paint mPaint);

    /**
     * 画图表标题
     * @param canvas
     * @param mPaint
     */
    private void drawTitle(Canvas canvas, Paint mPaint) {

        if (!TextUtils.isEmpty(mChartTitle)){
            mPaint.setTextSize(mChartTextSize);
            mPaint.setColor(mChartTextColor);
//            mPaint.setFakeBoldText(true);//粗体

            //要求文字宽度的中点 在其屏幕横向的中点
            canvas.drawText(mChartTitle,
                    (getWidth()/2) - (mPaint.measureText(mChartTitle))/2,
                    originalY + 70, mPaint );
        }
    }

    /**
     * 画Y轴
     * @param canvas
     * @param mPaint
     */
    protected abstract void drawYChart(Canvas canvas, Paint mPaint);

    /**
     * 画X轴
     * @param canvas
     * @param mPaint
     */
    protected abstract void drawXChart(Canvas canvas, Paint mPaint);


}
