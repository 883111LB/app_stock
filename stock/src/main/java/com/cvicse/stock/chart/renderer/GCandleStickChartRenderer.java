package com.cvicse.stock.chart.renderer;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import com.cvicse.stock.chart.data.GCandleData;
import com.cvicse.stock.chart.data.GCandleDataSet;
import com.cvicse.stock.chart.data.GCandleEntry;
import com.github.mikephil.charting.animation.ChartAnimator;
import com.github.mikephil.charting.data.CandleEntry;
import com.github.mikephil.charting.interfaces.dataprovider.CandleDataProvider;
import com.github.mikephil.charting.interfaces.datasets.ICandleDataSet;
import com.github.mikephil.charting.renderer.CandleStickChartRenderer;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.Transformer;
import com.github.mikephil.charting.utils.ViewPortHandler;

/** 蜡烛图（增加复权标识）
 * Created by tang_xqing on 2018/5/7.
 */
public class GCandleStickChartRenderer  extends CandleStickChartRenderer {

    private float[] mShadowBuffers = new float[8];
    private float[] mBodyBuffers = new float[4];
    private float[] mRangeBuffers = new float[4];
    private float[] mOpenBuffers = new float[4];
    private float[] mCloseBuffers = new float[4];
    public GCandleStickChartRenderer(CandleDataProvider chart, ChartAnimator animator, ViewPortHandler viewPortHandler) {
        super(chart, animator, viewPortHandler);
    }

    @Override
    public void drawData(Canvas c) {

        GCandleData candleData = (GCandleData) mChart.getCandleData();  // new 2018.5.7

        for (ICandleDataSet set : candleData.getDataSets()) {

            if (set.isVisible())
                drawDataSet(c, set);
        }
    }

    @SuppressWarnings("ResourceAsColor")
    @Override
    protected void drawDataSet(Canvas c, ICandleDataSet candleDataSet) {

        GCandleDataSet dataSet = (GCandleDataSet) candleDataSet;  // new 2018.5.7

        Transformer trans = mChart.getTransformer(dataSet.getAxisDependency());

        float phaseY = mAnimator.getPhaseY();
        float barSpace = dataSet.getBarSpace();
        boolean showCandleBar = dataSet.getShowCandleBar();

        mXBounds.set(mChart, dataSet);

        mRenderPaint.setStrokeWidth(dataSet.getShadowWidth());

        int lastColor = dataSet.getNeutralColor();  // 上一根数据的颜色
        boolean showSubFlag = dataSet.isShowSubFlag();  // 是否展示复权
        int subType = dataSet.getSub();  // 复权类型

        // draw the body
        for (int j = mXBounds.min; j <= mXBounds.range + mXBounds.min; j++) {
            // get the entry
            GCandleEntry e = (GCandleEntry) dataSet.getEntryForIndex(j);  // new 2018.5.7

            if ( null == e )
                continue;

            final float xPos = e.getX();

            final float open = e.getOpen();
            final float close = e.getClose();
            final float high = e.getHigh();
            final float low = e.getLow();

            int neutralColor = dataSet.getNeutralColor();
            int increasingColor = dataSet.getIncreasingColor();
            int decreasingColor = dataSet.getDecreasingColor();
            if (showCandleBar) {
                // calculate the shadow
                mShadowBuffers[0] = xPos;
                mShadowBuffers[2] = xPos;
                mShadowBuffers[4] = xPos;
                mShadowBuffers[6] = xPos;

                if (open > close) {
                    mShadowBuffers[1] = high * phaseY;
                    mShadowBuffers[3] = open * phaseY;
                    mShadowBuffers[5] = low * phaseY;
                    mShadowBuffers[7] = close * phaseY;
                } else if (open < close) {
                    mShadowBuffers[1] = high * phaseY;
                    mShadowBuffers[3] = close * phaseY;
                    mShadowBuffers[5] = low * phaseY;
                    mShadowBuffers[7] = open * phaseY;
                } else {
                    mShadowBuffers[1] = high * phaseY;
                    mShadowBuffers[3] = open * phaseY;
                    mShadowBuffers[5] = low * phaseY;
                    mShadowBuffers[7] = mShadowBuffers[3];
                }

                trans.pointValuesToPixel(mShadowBuffers);

                // draw the shadows
                if (dataSet.getShadowColorSameAsCandle()) {

                    if (open > close)
                        mRenderPaint.setColor(
                                lastColor = decreasingColor == ColorTemplate.COLOR_NONE ?
                                        dataSet.getColor(j) :
                                        decreasingColor
                        );

                    else if (open < close)
                        mRenderPaint.setColor(
                                lastColor = increasingColor == ColorTemplate.COLOR_NONE ?
                                        dataSet.getColor(j) :
                                        increasingColor
                        );

                    else{
                        // TODO: 2018/3/28 修改蜡烛图中，当开盘价与收盘价相同时：根据上一根数据的收盘价计算
                        // 开盘价 == 收盘价
                        CandleEntry temp = dataSet.getEntryForIndex(0 == j ? j : (j - 1));
                        float tempClose = temp.getClose();
                        if( tempClose < open ){
                            neutralColor = increasingColor;
                        }else if(tempClose > open) {
                            neutralColor = decreasingColor;
                        }else{
                            neutralColor = lastColor ;
                        }

                        lastColor = neutralColor;
                        mRenderPaint.setColor(
                                neutralColor == ColorTemplate.COLOR_NONE ?
                                        dataSet.getColor(j) :
                                        neutralColor
                        );
                    }
                } else {
                    mRenderPaint.setColor(
                            dataSet.getShadowColor() == ColorTemplate.COLOR_NONE ?
                                    dataSet.getColor(j) :
                                    dataSet.getShadowColor()
                    );
                }

                mRenderPaint.setStyle(Paint.Style.STROKE);
                c.drawLines(mShadowBuffers, mRenderPaint);

                // calculate the body
                mBodyBuffers[0] = xPos - 0.5f + barSpace;
                mBodyBuffers[1] = close * phaseY;
                mBodyBuffers[2] = (xPos + 0.5f - barSpace);
                mBodyBuffers[3] = open * phaseY;

                trans.pointValuesToPixel(mBodyBuffers);

                // draw body differently for increasing and decreasing entry
                if (open > close) { // decreasing

                    if (decreasingColor == ColorTemplate.COLOR_NONE) {
                        mRenderPaint.setColor(dataSet.getColor(j));
                    } else {
                        mRenderPaint.setColor(decreasingColor);
                    }

                    mRenderPaint.setStyle(dataSet.getDecreasingPaintStyle());

                    c.drawRect(
                            mBodyBuffers[0], mBodyBuffers[3],
                            mBodyBuffers[2], mBodyBuffers[1],
                            mRenderPaint);

                } else if (open < close) {

                    if (increasingColor == ColorTemplate.COLOR_NONE) {
                        mRenderPaint.setColor(dataSet.getColor(j));
                    } else {
                        mRenderPaint.setColor(increasingColor);
                    }

                    mRenderPaint.setStyle(dataSet.getIncreasingPaintStyle());

                    c.drawRect(
                            mBodyBuffers[0], mBodyBuffers[1],
                            mBodyBuffers[2], mBodyBuffers[3],
                            mRenderPaint);
                } else { // equal values

                    // TODO: 2018/3/28 修改蜡烛图中，当开盘价与收盘价相同时：根据上一根数据的收盘价计算
                    // 开盘价 == 收盘价
                    CandleEntry temp = dataSet.getEntryForIndex(0 == j ? j : (j - 1));
                    float tempClose = temp.getClose();
                    if( tempClose < open ){
                        neutralColor = increasingColor;
                    }else if(tempClose > open) {
                        neutralColor = decreasingColor;
                    }else{
                        neutralColor = lastColor ;
                    }
                    lastColor = neutralColor;

                    if (neutralColor == ColorTemplate.COLOR_NONE) {
                        mRenderPaint.setColor(dataSet.getColor(j));
                    } else {
                        mRenderPaint.setColor(neutralColor);
                    }

                    c.drawLine(
                            mBodyBuffers[0], mBodyBuffers[1],
                            mBodyBuffers[2], mBodyBuffers[3],
                            mRenderPaint);
                }
                // TODO: 2018/5/7  增加复权标识
                addSubView(c, dataSet, showSubFlag, subType, e, mShadowBuffers[0]);
            } else {

                mRangeBuffers[0] = xPos;
                mRangeBuffers[1] = high * phaseY;
                mRangeBuffers[2] = xPos;
                mRangeBuffers[3] = low * phaseY;

                mOpenBuffers[0] = xPos - 0.5f + barSpace;
                mOpenBuffers[1] = open * phaseY;
                mOpenBuffers[2] = xPos;
                mOpenBuffers[3] = open * phaseY;

                mCloseBuffers[0] = xPos + 0.5f - barSpace;
                mCloseBuffers[1] = close * phaseY;
                mCloseBuffers[2] = xPos;
                mCloseBuffers[3] = close * phaseY;

                trans.pointValuesToPixel(mRangeBuffers);
                trans.pointValuesToPixel(mOpenBuffers);
                trans.pointValuesToPixel(mCloseBuffers);

                // draw the ranges
                int barColor;

                if (open > close)
                    barColor = decreasingColor == ColorTemplate.COLOR_NONE
                            ? dataSet.getColor(j)
                            : decreasingColor;
                else if (open < close)
                    barColor = increasingColor == ColorTemplate.COLOR_NONE
                            ? dataSet.getColor(j)
                            : increasingColor;
                else
                    barColor = neutralColor == ColorTemplate.COLOR_NONE
                            ? dataSet.getColor(j)
                            : neutralColor;

                mRenderPaint.setColor(barColor);
                c.drawLine(
                        mRangeBuffers[0], mRangeBuffers[1],
                        mRangeBuffers[2], mRangeBuffers[3],
                        mRenderPaint);
                c.drawLine(
                        mOpenBuffers[0], mOpenBuffers[1],
                        mOpenBuffers[2], mOpenBuffers[3],
                        mRenderPaint);
                c.drawLine(
                        mCloseBuffers[0], mCloseBuffers[1],
                        mCloseBuffers[2], mCloseBuffers[3],
                        mRenderPaint);
            }
        }
    }

    /**
     * 添加复权标识
     * @param c
     * @param dataSet
     * @param showSubFlag
     * @param subType
     * @param e
     * @param xPos
     */
    private void addSubView(Canvas c, GCandleDataSet dataSet, boolean showSubFlag, int subType, GCandleEntry e, float xPos) {
        if(showSubFlag && e.isSub()){   // 展示复权并有复权信息
            int subColor = dataSet.getSubColor();  // 复权标识颜色

            mRenderPaint.setAntiAlias(true);
            mRenderPaint.setColor(subColor);
            mRenderPaint.setStyle(Paint.Style.STROKE);

            // 前后复权
            float offset = 5;
            float radius = 20;
            float cx = xPos;
            float cy = mViewPortHandler.contentBottom()-radius-offset;
            c.drawCircle(cx,cy, radius,mRenderPaint);

            mRenderPaint.setStyle(Paint.Style.FILL);
            String text = "q";
            // 得到文字的宽、高
            mRenderPaint.setTextAlign(Paint.Align.CENTER);
            mRenderPaint.setTextSize(35);
            Rect rect= new Rect();
            mRenderPaint.getTextBounds(text,0,text.length(),rect);

            int height = rect.height();
            float x = cx ;
            float y = cy + (height/2)-offset;
            c.drawText(text,x,y,mRenderPaint);

            if( 1 != subType && 2!= subType){
                // 不复权 画斜线
                float startX = cx-radius +offset;
                float startY = cy-radius +offset;
                float endX = cx+radius -offset;
                float endY = cy+radius -offset;
                c.drawLine(startX,startY,endX,endY,mRenderPaint);
            }
        }
    }
}
