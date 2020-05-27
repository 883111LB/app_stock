package com.cvicse.stock.chart.chart;

import android.content.Context;

import com.cvicse.stock.chart.renderer.FiveGXAxisRenderer;

/**
 * 五日图底部图表类
 * Created by liu_zlu on 2017/3/3 20:53
 */
public class FiveCombinedChart extends GCombinedChart {
    public FiveCombinedChart(Context context) {
        super(context);
        mXAxisRenderer = new FiveGXAxisRenderer(mViewPortHandler, mXAxis, mLeftAxisTransformer);
    }
}
