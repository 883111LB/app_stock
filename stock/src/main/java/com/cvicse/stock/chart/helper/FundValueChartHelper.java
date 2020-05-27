package com.cvicse.stock.chart.helper;

import com.cvicse.stock.chart.ui.FundValueChart;
import com.cvicse.stock.chart.control.FundValueChartControl;
import com.cvicse.stock.chart.data.FundValueDataHelper;
import com.mitake.core.FundValueInfo;

/**
 * 基金净值图帮助类
 * Created by liu_zlu on 2017/4/28 15:03
 */
public class FundValueChartHelper {

    private FundValueChartControl graphicViewControl;
    private FundValueDataHelper dataHelper;

    public FundValueChartHelper(FundValueChart fundValueChart){
        dataHelper = new FundValueDataHelper();
        graphicViewControl = new FundValueChartControl(fundValueChart,dataHelper);
    }
    public void setRequestData(FundValueInfo fundValueInfo){
        dataHelper.setRequestData(fundValueInfo);
        graphicViewControl.notifyDataSetChanged();
    }

    /**
     * 设置是否横屏
     * @param isLand true 为横屏 false为竖屏
     */
    public void setLand(boolean isLand){
        graphicViewControl.setLand(isLand);
    }
}
