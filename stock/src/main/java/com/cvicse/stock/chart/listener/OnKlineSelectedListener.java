package com.cvicse.stock.chart.listener;

import com.cvicse.stock.chart.data.KLineEntity;

/**
 * k线图高亮选择监听
 * Created by liu_zlu on 2017/2/27 13:57
 */
public interface OnKlineSelectedListener {
    void onValueSelected(int position,KLineEntity kLineEntity);

    // 加载更多的数据 isOld = true：日期之前的数据  false：日期之后的数据
    void onLoadMoreData(boolean isOld);
}
