package com.cvicse.stock.markets.helper;

import com.cvicse.stock.chart.data.TechChartType;
import com.cvicse.stock.markets.ui.StockDetailActivity;
import com.stock.config.KSetting;

/**
 * stock 状态类 保存了股票行情的状态信息
 * Created by liu_zlu on 2017/5/3 15:51
 */
public class StockState {
    /**
     * 图表种类 0（分时图）、1（五日图）、2（日K线图）、3（周K线图）、4（月K线图）、
     * 5（五分钟K线图）、6（15分钟K线图）、7（30分钟K线图）、8（60分钟K线图）、9（基金净值图）
     */
    public int type;
    /**
     * k线图种类
     */
    public TechChartType.KType kType;
    /**
     * 复权类型
     */
    public int right;

    /**
     * 判断当前的Activity的股票图表种类是否改变
     * @return
     */
    public boolean isChange(){
        if(type != StockDetailActivity.type) return true;
        if(kType != KSetting.getKTypeAsType()) return true;
        if(right != KSetting.getKRightSubType()) return true;
        return false;
    }
}
