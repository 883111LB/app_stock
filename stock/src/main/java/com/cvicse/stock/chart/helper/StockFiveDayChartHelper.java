package com.cvicse.stock.chart.helper;

import com.cvicse.stock.chart.ui.StockFiveDayChart;
import com.cvicse.stock.chart.control.StockFiveDayChartControl;
import com.cvicse.stock.chart.data.FiveDayDataHelper;
import com.cvicse.stock.chart.data.OHLCItemBo;
import com.mitake.core.QuoteItem;

import java.util.ArrayList;

/**
 * Created by liu_zlu on 2017/3/2 16:42
 */
public class StockFiveDayChartHelper {
    StockFiveDayChart graphicView;
    StockFiveDayChartControl graphicViewControl;
    public FiveDayDataHelper dataHelper;

    public StockFiveDayChartHelper(StockFiveDayChart graphicView){
        this.graphicView = graphicView;
        dataHelper = new FiveDayDataHelper();
        graphicViewControl = new StockFiveDayChartControl(graphicView,dataHelper);
    }
    public void setQuoteItem(QuoteItem quoteItem){
        dataHelper.setQuoteItem(quoteItem);
    }
    public void addRequestData(ArrayList<OHLCItemBo> ohlcItems){
        dataHelper.addRequestData(ohlcItems);
        graphicViewControl.notifyDataSetChanged();
    }

    public void clear(){
        dataHelper.reset();
    }

    public void setLand(boolean isLand){
        graphicViewControl.setLand(isLand);
    }
}
