package com.cvicse.stock.chart.renderer;

import android.graphics.Canvas;
import android.graphics.Path;

import com.cvicse.stock.chart.data.GCandleData;
import com.cvicse.stock.chart.theme.ThemeColor;
import com.github.mikephil.charting.animation.ChartAnimator;
import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.data.BarLineScatterCandleBubbleDataSet;
import com.github.mikephil.charting.data.ChartData;
import com.github.mikephil.charting.data.CombinedData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IDataSet;
import com.github.mikephil.charting.renderer.BarChartRenderer;
import com.github.mikephil.charting.renderer.BubbleChartRenderer;
import com.github.mikephil.charting.renderer.CandleStickChartRenderer;
import com.github.mikephil.charting.renderer.CombinedChartRenderer;
import com.github.mikephil.charting.renderer.DataRenderer;
import com.github.mikephil.charting.renderer.LineChartRenderer;
import com.github.mikephil.charting.renderer.ScatterChartRenderer;
import com.github.mikephil.charting.utils.MPPointD;
import com.github.mikephil.charting.utils.ViewPortHandler;

/**
 * Created by liu_zlu on 2017/1/21 21:50
 */
public class GCombinedChartRenderer extends CombinedChartRenderer {
    public GCombinedChartRenderer(CombinedChart chart, ChartAnimator animator, ViewPortHandler viewPortHandler) {
        super(chart, animator, viewPortHandler);
    }

    @Override
    public void createRenderers() {
        mRenderers.clear();

        CombinedChart chart = (CombinedChart)mChart.get();
        if (chart == null)
            return;

        CombinedChart.DrawOrder[] orders = chart.getDrawOrder();

        for (CombinedChart.DrawOrder order : orders) {

            switch (order) {
                case BAR:
                    //重写了柱状图
                    if (chart.getBarData() != null)
                        mRenderers.add(new GBarChartRenderer(chart, mAnimator, mViewPortHandler));
                    break;
                case BUBBLE:
                    if (chart.getBubbleData() != null)
                        mRenderers.add(new BubbleChartRenderer(chart, mAnimator, mViewPortHandler));
                    break;
                case LINE:
                    if (chart.getLineData() != null)
                        mRenderers.add(new LineChartRenderer(chart, mAnimator, mViewPortHandler));
                    break;
                case CANDLE:
                    //重写了图
                    if (chart.getCandleData() != null){
                        if(chart.getCandleData() instanceof GCandleData){
                            mRenderers.add(new GCandleStickChartRenderer(chart, mAnimator, mViewPortHandler));
                        } else {
                            mRenderers.add(new CandleStickChartRenderer(chart, mAnimator, mViewPortHandler));
                        }
                    }
                    break;
                case SCATTER:
                    if (chart.getScatterData() != null)
                        mRenderers.add(new ScatterChartRenderer(chart, mAnimator, mViewPortHandler));
                    break;
            }
        }
    }

    /**
     * 图表底部图表
     * @param isBottom
     */
    public void setIsBottom(boolean isBottom){
        this.isBottom = isBottom;
    }
    private boolean isBottom = false;

    /**
     * 高亮
     * @param c
     * @param indices
     */
    @Override
    public void drawHighlighted(Canvas c, Highlight[] indices) {
        //上层图表使用默认图表
        if(!isBottom){
            super.drawHighlighted(c,indices);
            return;
        }
        Chart chart = mChart.get();
        if (chart == null) return;

        for (DataRenderer renderer : mRenderers) {
            ChartData data = null;

            if (renderer instanceof BarChartRenderer)
                data = ((CombinedChart)chart).getBarData();
            else if (renderer instanceof LineChartRenderer)
                data = ((CombinedChart)chart).getLineData();
            else if (renderer instanceof CandleStickChartRenderer)
                data = ((CombinedChart)chart).getCandleData();
            else if (renderer instanceof ScatterChartRenderer)
                data = ((CombinedChart)chart).getScatterData();
            else if (renderer instanceof BubbleChartRenderer)
                data = ((CombinedChart)chart).getBubbleData();

            int dataIndex = data == null ? -1
                    : ((CombinedData)chart.getData()).getAllData().indexOf(data);

            mHighlightBuffer.clear();

            for (Highlight h : indices) {
                //if (h.getDataIndex() == dataIndex || h.getDataIndex() == -1)
                    mHighlightBuffer.add(h);
            }

            if(caculHighlighted(c, mHighlightBuffer.toArray(new Highlight[mHighlightBuffer.size()]),data)){
                return;
            }
        }
    }

    public boolean caculHighlighted(Canvas c, Highlight[] indices,ChartData chartData) {
        Chart chart = mChart.get();
        for (Highlight high : indices) {

            BarLineScatterCandleBubbleDataSet set = (BarLineScatterCandleBubbleDataSet) chartData.getDataSetByIndex(high.getDataSetIndex());

            if (set == null || !set.isHighlightEnabled())
                continue;

            Entry e = set.getEntryForXValue(high.getX(), high.getY());

            if (!isInBoundsX(e, set))
                continue;

            MPPointD pix = ((CombinedChart)chart).getTransformer(set.getAxisDependency()).getPixelForValues(e.getX(), e.getY() * mAnimator
                    .getPhaseY());

            high.setDraw((float) pix.x, (float) pix.y);
            // draw the lines
            drawHighlightLines(c, (float) pix.x, (float) pix.y, set);

            return true;
        }
        return false;
    }
    private Path mHighlightLinePath = new Path();
    /**
     * Draws vertical & horizontal highlight-lines if enabled.
     *
     * @param c
     * @param x x-position of the highlight line intersection
     * @param y y-position of the highlight line intersection
     * @param set the currently drawn dataset
     */
    protected void drawHighlightLines(Canvas c, float x, float y, BarLineScatterCandleBubbleDataSet set) {

        // set color and stroke-width
       // mHighlightPaint.setColor(set.getHighLightColor());
        mHighlightPaint.setColor(ThemeColor.blackWhite());
       // mHighlightPaint.setStrokeWidth(4);

        // draw highlighted lines (if enabled)
       // mHighlightPaint.setPathEffect(set.getDashPathEffectHighlight());

        // draw vertical highlight lines
            // create vertical path
        mHighlightLinePath.reset();
        mHighlightLinePath.moveTo(x, mViewPortHandler.contentTop());
        mHighlightLinePath.lineTo(x, mViewPortHandler.contentBottom());

        c.drawPath(mHighlightLinePath, mHighlightPaint);
    }
    /**
     * Checks if the provided entry object is in bounds for drawing considering the current animation phase.
     *
     * @param e
     * @param set
     * @return
     */
    protected boolean isInBoundsX(Entry e, IDataSet set) {

        if (e == null)
            return false;

        float entryIndex = set.getEntryIndex(e);

        if (e == null || entryIndex >= set.getEntryCount() * mAnimator.getPhaseX()) {
            return false;
        } else {
            return true;
        }
    }
}
