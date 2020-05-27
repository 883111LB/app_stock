package com.cvicse.stock.chart.helper;

import com.cvicse.stock.chart.data.TechChartType;
import com.stock.config.KSetting;

/**
 * Created by liuzilu on 2017/1/26.
 */

public class StockTechState {
    public TechChartType.Type type = TechChartType.Type.DAY;
    public TechChartType.KType ktype = TechChartType.KType.VOLUME;

    public StockTechState(){
        ktype = KSetting.getKTypeAsType();
    }

    public boolean judgeSetKType(TechChartType.KType ktype){
        if(ktype == this.ktype){
            return false;
        }
        this.ktype = ktype;
        return true;
    }

    public boolean isMinute(){
        return type.isMinute();
    }
}
