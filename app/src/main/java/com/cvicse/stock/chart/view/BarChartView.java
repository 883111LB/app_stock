package com.cvicse.stock.chart.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import com.cvicse.base.utils.SizeUtils;
import com.cvicse.stock.markets.data.BarChartData;
import com.cvicse.stock.util.FormatUtils;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/8/16.
 */

public class BarChartView extends View{


    private Context mContext;

    //默认的比例
    private  float mRatio;

    //柱状图画笔
    private Paint mPaint = new Paint();

    //文字画笔
    private Paint mTextPaint = new Paint();

    //横线画笔
    private Paint mLinePaint = new Paint();

    private Paint mNamePaint = new Paint();

    private ArrayList<BarChartData> mData;

    //控件的宽高
    private int mWidth,mHeight;

    //小矩形的宽度
    private float mRectWidth;

    private float sum;

    //每次偏移的距离
    private int offset;

    public BarChartView(Context context) {
        super(context);
        init(context);
    }

    public BarChartView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public BarChartView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mContext = context;

        mRatio = SizeUtils.dp2px(context,500);
        offset = SizeUtils.dp2px(context,4);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setAntiAlias(true);

        mTextPaint.setColor(Color.WHITE);
        mTextPaint.setTextSize(SizeUtils.dp2px(context,8));

        mLinePaint.setColor(Color.WHITE);
        mLinePaint.setStyle(Paint.Style.FILL);
        mLinePaint.setStrokeWidth(2f);

        mNamePaint.setColor(Color.WHITE);
        mNamePaint.setTextSize(30);
        mNamePaint.setTextSize(SizeUtils.dp2px(context,13));
        mNamePaint.setStrokeWidth(2f);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(measureWidth(widthMeasureSpec),measureHeight(heightMeasureSpec));
    }

    private int measureWidth(int widthMeasureSpec) {
        int result = 0;
        int size = MeasureSpec.getSize(widthMeasureSpec);
        int mode = MeasureSpec.getMode(widthMeasureSpec);

        if(mode == MeasureSpec.EXACTLY){
            result = size;
        }else{
            result = 200;
            if(mode == MeasureSpec.AT_MOST){
                result = Math.min(size,result);
            }
        }
        return result;
    }

    private int measureHeight(int heightMeasureSpec) {
        int result = 0;
        int size = MeasureSpec.getSize(heightMeasureSpec);
        int mode = MeasureSpec.getMode(heightMeasureSpec);

        if(mode == MeasureSpec.EXACTLY){
            result = size;
        }else{
            result = 200;
            if(mode == MeasureSpec.AT_MOST){
                result = Math.min(size,result);
            }
        }
        return result;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if( 0 == sum ){
            initData();
        }

        float y =0.0f;
        float left = 0.0f;
        float right = 0.0f;
        float top = 0.0f;
        float bottom = 0.0f;
        float startX = 0.0f;

        int width = mWidth/4;
        mRectWidth = SizeUtils.dp2px(mContext,mWidth /24f);

        canvas.translate(0,mHeight/2);//平移至屏幕中心
        canvas.drawLine(0,0,mWidth,0,mLinePaint);

        for(int i = 0;i<mData.size();i++) {
            canvas.save();
            BarChartData bar = mData.get(i);

            startX = width * i + offset*(i+1);  // 每个小矩阵起点X
            //  startX = mWidth*i + offset;

            int color = bar.getmColor();
            mPaint.setColor(color);

            /* // 旋转
            if(color == Color.RED){
                canvas.rotate(180, (startX +(mRectWidth * (i+1)))/2f,0);
            }
            */
           // RectF rect = new RectF((mRectWidth*i + offset), 0,(mRectWidth * (i+1)), bar.getmHeight());
            //canvas.drawRect( startX, 0, mRectWidth * (i+1),bar.getmHeight(), mPaint);
            bottom = bar.getmHeight();
            right =  mRectWidth + startX;
            left = startX;
            if (color == Color.RED) {
//                canvas.drawRect( startX, -bottom, mRectWidth + startX,0,mPaint);
                canvas.restore();
                y = 50;
                top = -bottom;
                bottom = 0;
            } else {
//                canvas.drawRect( startX,  0, mRectWidth + startX,bottom, mPaint);
                y = -20;
                top = 0;
            }
            canvas.drawRect(left,top,right,bottom, mPaint);
//            canvas.drawText(bar.getName(), startX, mHeight / 2, mNamePaint);
            canvas.drawText(bar.getName(),startX,(mHeight)/2-10, mNamePaint);
            if( !"0".equals(bar.getmValue()) ){
                canvas.drawText(bar.getmValue(), startX, y, mTextPaint);
            }
        }
    }

    public void setData(ArrayList<BarChartData> datas){
        if(datas != null && datas.size() > 0){
            this.mData = datas;
//            initData();
            invalidate();
        }
    }

    private void initData(){
        if( mHeight == 0 ){
            return;
        }
        mRatio = mHeight / 3;
        sum = 0;
        for(int i = 0;i<mData.size();i++){
            BarChartData data = mData.get(i);
            if(FormatUtils.isStandard(data.getmValue())){
                sum = sum + Float.parseFloat(data.getmValue());
            }

        }
        // 计算每个矩阵的高度
        for(int i = 0;i<mData.size();i++){
            BarChartData data = mData.get(i);
            String value = data.getmValue();
            if(FormatUtils.isStandard(value)){
                data.setmHeight((Float.parseFloat(value)/sum) * mRatio*2);
            }
            data.setmValue(FormatUtils.format(value));
        }
    }

    /**
     * 获得文本宽高
     * @param text
     * @param paint
     * @return
     */
    private Rect getTextBound( String text,Paint paint ){
        Rect rect = new Rect();
        paint.getTextBounds(text,0,text.length(), rect);
        return rect;
    }
}
