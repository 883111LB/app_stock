package com.cvicse.stock.chart.helper.new_helper;

import com.cvicse.stock.chart.ui.new_ui.StockMinuteChartNew;
import com.stock.config.MSetting;

/** 分时图状态
 * Created by liuzilu on 2017/1/26.
 */

public class StockMState {
//    public StockTechChart.Type type = StockTechChart.Type.DAY;
    public StockMinuteChartNew.MSubType  mSubType = StockMinuteChartNew.MSubType.VOLUME;

    public StockMState(){
        mSubType = MSetting.getMTypeAsType();
    }

    public boolean judgeSetKType(StockMinuteChartNew.MSubType mSubType){
        if(mSubType == this.mSubType){
            return false;
        }
        this.mSubType = mSubType;
        return true;
    }

   /* public boolean isMinute(){
        return type.isMinute();
    }*/
}
