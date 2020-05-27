package com.cvicse.stock.chart.components;

import android.content.Context;
import android.widget.TextView;

import com.android.haitong.R;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;



/**
 * Created by liu_zlu on 2017/3/1 15:22
 * 高亮的标志View
 */
public class TextMarkerView extends MarkerView {

    TextView tevMarkerChart;
    public TextMarkerView(Context context) {
        super(context, R.layout.chart_markerview);
        tevMarkerChart = (TextView) findViewById(R.id.tev_marker_chart);
    }

    public void setTextView(String text){
        tevMarkerChart.setText(text);
    }

    @Override
    public void refreshContent(Entry e, Highlight highlight) {
        super.refreshContent(e, highlight);
    }
}
