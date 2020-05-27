package com.cvicse.stock.chart.listener;

import com.cvicse.stock.chart.data.MKLineEntity;

/** 用于走势、K线高亮监听
 * Created by tang_xqing on 2018/3/23.
 */
public interface OnMKSelectedListener {
    void onValueSelected(MKLineEntity mkLineEntity,String lastClose);
}
