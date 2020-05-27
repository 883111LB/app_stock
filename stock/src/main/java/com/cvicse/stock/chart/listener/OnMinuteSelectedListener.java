package com.cvicse.stock.chart.listener;

import com.cvicse.stock.chart.data.MinuteEntity;
import com.mitake.core.OHLCItem;

/**
 * 分时五日高亮选择监听
 * Created by liu_zlu on 2017/2/27 13:34
 */
public interface OnMinuteSelectedListener {
    void onValueSelected(int position, OHLCItem ohlcItem,boolean last);

    void onValueSelected(int position, MinuteEntity mLineEntity);
}
