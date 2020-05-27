package com.cvicse.stock.view.fupan;

import android.content.Context;
import android.widget.TextView;

import com.cvicse.stock.R;
import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.MPPointF;

import java.text.DecimalFormat;

/**
 * 柱状图marker
 * Created by tang_hua on 2020/2/21.
 */

public class FpBarChartMarkerUtils extends MarkerView {
    private final TextView tvContent;
    private final ValueFormatter xAxisValueFormatter;

    private final DecimalFormat format;
    final String[] labelY = {"涨停", "9,+∞", "8,9", "7,8", "6,7", "5,6",
            "4,5", "3,4", "2,3", "1,2", "0,1", "0", "-1,0", "-2,-1",
            "-3,-2", "-4,-3", "-5,-4", "-6,-5", "-7,-6", "-8,-7", "-9,-8", "-∞,-9", "跌停"};
    public FpBarChartMarkerUtils(Context context, ValueFormatter xAxisValueFormatter) {
        super(context, R.layout.custom_marker_view);

        this.xAxisValueFormatter = xAxisValueFormatter;
        tvContent = (TextView) findViewById(R.id.fp_tvContent);
        format = new DecimalFormat("###.0");
    }

    // runs every time the MarkerView is redrawn, can be used to update the
    // content (user-interface)
    @Override
    public void refreshContent(Entry e, Highlight highlight) {

//        tvContent.setText(String.format("x: %s, y: %s", xAxisValueFormatter.getFormattedValue(e.getX()), format.format(e.getY())));
        tvContent.setText(labelY[(int)e.getX()] + "\n" + "个股指数" + (int)e.getY());

        super.refreshContent(e, highlight);
    }

    @Override
    public MPPointF getOffset() {
        return new MPPointF(-(getWidth() / 2), -getHeight());
    }
}
