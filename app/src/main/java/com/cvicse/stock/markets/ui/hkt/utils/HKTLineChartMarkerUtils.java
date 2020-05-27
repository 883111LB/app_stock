package com.cvicse.stock.markets.ui.hkt.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.Html;
import android.widget.TextView;

import com.cvicse.stock.R;
import com.cvicse.stock.common.ColorUtils;
import com.cvicse.stock.util.FormatUtils;
import com.cvicse.stock.view.fupan.ValueFormatter;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.MPPointF;
import com.mitake.core.bean.HKTItem;

import java.text.DecimalFormat;
import java.util.List;

/**
 * 柱状图marker
 * Created by tang_hua on 2020/2/21.
 */

public class HKTLineChartMarkerUtils extends MarkerView {
    private final TextView tvContent;
    private final ValueFormatter xAxisValueFormatter;
    private List<HKTItem> hktItems;

//    private final DecimalFormat format;
    final String[] labelY = {"涨停", "9,+∞", "8,9", "7,8", "6,7", "5,6",
            "4,5", "3,4", "2,3", "1,2", "0,1", "0", "-1,0", "-2,-1",
            "-3,-2", "-4,-3", "-5,-4", "-6,-5", "-7,-6", "-8,-7", "-9,-8", "-∞,-9", "跌停"};
    public HKTLineChartMarkerUtils(Context context, ValueFormatter xAxisValueFormatter, List<HKTItem> hktItems) {
        super(context, R.layout.marker_view_hkt);

        this.xAxisValueFormatter = xAxisValueFormatter;
        this.hktItems = hktItems;
        tvContent = (TextView) findViewById(R.id.line_tvContent);
//        format = new DecimalFormat("###.0");
    }

    // runs every time the MarkerView is redrawn, can be used to update the
    // content (user-interface)
    @SuppressLint("SetTextI18n")
    @Override
    public void refreshContent(Entry e, Highlight highlight) {

//        tvContent.setText(String.format("x: %s, y: %s", xAxisValueFormatter.getFormattedValue(e.getX()), format.format(e.getY())));
        HKTItem item = hktItems.get((int)e.getX());
        tvContent.setText(Html.fromHtml(item.datetime + "<br>"
                        + "经沪市流向港股：" + getDataColor(item.shInflowAmount) + FormatUtils.format(item.shInflowAmount) + "</font><br>"
                        + "经深市流向港股：" + getDataColor(item.szInflowAmount) + FormatUtils.format(item.szInflowAmount) + "</font><br>"
                        + "合计流向：" + getDataColor(item.shSzInflowAmount) + FormatUtils.format(item.shSzInflowAmount)+ "</font>")
                );

        super.refreshContent(e, highlight);
    }

    private String getDataColor(String data) {
        if (data.startsWith("-")) {
            return "<font color=" +ColorUtils.DOWN + ">";
        } else if ("0".equals(data)) {
            return "<font color=" + ColorUtils.DEFALUT() + ">";
        }
        return "<font color=" +ColorUtils.UP + ">+";
    }

    @Override
    public MPPointF getOffset() {
        return new MPPointF(-(getWidth() / 2), -getHeight());
    }

    private float getNum(String value) {
        if (null == value || "".equals(value)) {
            return 0;
        }
        if (value.startsWith("+") || value.startsWith("-")) {
            return Float.valueOf(value.substring(1));
        }
        return Float.valueOf(value);
    }
}
