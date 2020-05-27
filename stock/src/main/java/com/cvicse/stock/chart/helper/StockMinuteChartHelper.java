package com.cvicse.stock.chart.helper;

import com.cvicse.stock.chart.control.StockMinuteChartControl;
import com.cvicse.stock.chart.data.MinuteDataHelper;
import com.cvicse.stock.chart.data.OHLCItemBo;
import com.cvicse.stock.chart.ui.StockMinuteChart;
import com.mitake.core.QuoteItem;

import java.util.ArrayList;

/**
 * 分时图帮助类
 * Created by liuzilu on 2017/2/21.
 */
public class StockMinuteChartHelper {
    private StockMinuteChart graphicView;

    private StockMinuteChartControl graphicViewControl;  // 分时图控制类

    public MinuteDataHelper mDataHelper;

    public StockMinuteChartHelper(StockMinuteChart graphicView){
        this.graphicView = graphicView;
        mDataHelper = new MinuteDataHelper();
        graphicViewControl = new StockMinuteChartControl(mDataHelper,graphicView);
    }

    public void setQuoteItem(QuoteItem quoteItem){
        mDataHelper.setQuoteItem(quoteItem);
        graphicViewControl.notifyDataSetChanged();
    }

    public void addRequestData(ArrayList<OHLCItemBo> ohlcItems){
        mDataHelper.addRequestData(ohlcItems);
        graphicViewControl.notifyDataSetChanged();
    }

    public void setLand(boolean isLand){
        graphicViewControl.setLand(isLand);
    }
}
