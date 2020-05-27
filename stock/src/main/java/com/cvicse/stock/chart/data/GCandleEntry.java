package com.cvicse.stock.chart.data;

import com.github.mikephil.charting.data.CandleEntry;

/** 蜡烛图（增加复权标识）
 * Created by tang_xqing on 2018/5/7.
 */
public class GCandleEntry extends CandleEntry {

    private boolean mIsSub = false;  // 是否复权

    public GCandleEntry(float x, float shadowH, float shadowL, float open, float close) {
        super(x, shadowH, shadowL, open, close);
    }

    public boolean isSub() {
        return mIsSub;
    }

    public void setSub(boolean sub) {
        mIsSub = sub;
    }
}
