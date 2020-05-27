package com.cvicse.stock.chart.helper.new_helper;

import com.cvicse.stock.chart.control.new_control.StockFiveDayChartControlNew;
import com.cvicse.stock.chart.data.new_data.FiveDayDataHelperNew;
import com.cvicse.stock.chart.ui.StockFiveDayChart;
import com.mitake.core.QuoteItem;
import com.mitake.core.response.ChartResponse;

/** 五日图帮助类
 * Created by liu_zlu on 2017/3/2 16:42
 */
public class StockFiveDayChartHelperNew {
    private StockFiveDayChart graphicView;
    private StockFiveDayChartControlNew graphicViewControl;  // 五日图控制类
    public FiveDayDataHelperNew mDataHelper;   // 五日图数据帮助类

    public StockFiveDayChartHelperNew(StockFiveDayChart graphicView){
        this.graphicView = graphicView;
        mDataHelper = new FiveDayDataHelperNew();
        graphicViewControl = new StockFiveDayChartControlNew(graphicView, mDataHelper);
    }
    public void setQuoteItem(QuoteItem quoteItem){
        mDataHelper.setQuoteItem(quoteItem);
    }

    public void addRequestData(ChartResponse chartResponse){
        mDataHelper.addRequestData(chartResponse);
        graphicViewControl.setTopAxisLimitLine(chartResponse);
        graphicViewControl.notifyDataSetChanged();
    }

    public void clear(){
        mDataHelper.reset();
    }

    public void setLand(boolean isLand){
        graphicViewControl.setLand(isLand);
    }
}
