package com.cvicse.stock.chart.data;

import android.graphics.Color;

import com.github.mikephil.charting.data.CandleDataSet;
import com.github.mikephil.charting.data.CandleEntry;

import java.util.List;

/** 蜡烛图（增加复权标识）
 * Created by tang_xqing on 2018/5/7.
 */
public class GCandleDataSet extends CandleDataSet {

    private int mSub = 0;  // 复权类型( 默认：不复权  1-前复权 1-后复权 0-不复权)
    private int mSubColor = Color.GRAY; // 复权标识颜色
    private boolean mIsShowSubFlag = false;  // 是否展示复权标识

    public GCandleDataSet(List<CandleEntry> yVals, String label) {
        super(yVals, label);
    }

    public int getSub() {
        return mSub;
    }

    public void setSub(int sub) {
        mSub = sub;
    }

    public int getSubColor() {
        return mSubColor;
    }

    public void setSubColor(int subColor) {
        mSubColor = subColor;
    }

    public boolean isShowSubFlag() {
        return mIsShowSubFlag;
    }

    public void setShowSubFlag(boolean showSubFlag) {
        mIsShowSubFlag = showSubFlag;
    }
}
