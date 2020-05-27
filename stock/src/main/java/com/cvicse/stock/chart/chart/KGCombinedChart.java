package com.cvicse.stock.chart.chart;

import android.content.Context;

import com.cvicse.stock.chart.renderer.KGXAxisRenderer;

/**
 * k线图底部图表类
 * Created by liu_zlu on 2017/5/1 09:28
 */
public class KGCombinedChart extends GCombinedChart{
    public KGCombinedChart(Context context) {
        super(context);
        mXAxisRenderer = new KGXAxisRenderer(mViewPortHandler, mXAxis, mLeftAxisTransformer);
    }
}
