package com.cvicse.stock.chart.helper;

import com.cvicse.stock.chart.control.StockTechChartControl;
import com.cvicse.stock.chart.data.KDataHelper;
import com.cvicse.stock.chart.data.KLineEntity;
import com.cvicse.stock.chart.data.TechChartType;
import com.cvicse.stock.chart.ui.StockTechChart;
import com.mitake.core.OHLCItem;
import com.mitake.core.QuoteItem;
import com.mitake.core.parser.FQItem;
import com.mitake.core.parser.GBItem;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by liu_zlu on 2017/1/10 12:07
 */
public class StockTechChartHelper {
    private StockTechChart graphicView;
    private  StockTechChartControl graphicViewControl;  // K线图控制类
    public KDataHelper dataHelper;

    private StockTechState typeHpler = new StockTechState();

    public StockTechChartHelper(StockTechChart graphicView){
        this.graphicView = graphicView;
        dataHelper = new KDataHelper(typeHpler);
        graphicViewControl = new StockTechChartControl(dataHelper,graphicView,typeHpler);
    }

    public void addRequestData(CopyOnWriteArrayList<OHLCItem> ohlcItems, QuoteItem quoteItem, ArrayList<GBItem> gbItems){
        dataHelper.addRequestData(ohlcItems,quoteItem, gbItems);
        graphicViewControl.updateChartSetting();
        graphicViewControl.notifyDataSetChanged();
    }

    public void addOHLCSubData(CopyOnWriteArrayList<FQItem> ohlcSubRsList){
        dataHelper.addRequestOHLCSUBData(ohlcSubRsList);
    }

    public void clear(){

    }

    public void setType(TechChartType.Type type){
        typeHpler.type = type;
    }

    /**
     * 设置K线指标，更新页面
     * @param type
     */
    public void setKType(TechChartType.KType type){
        if(!typeHpler.judgeSetKType(type)){
            return;
        }
        graphicViewControl.updateChartSetting();
        graphicViewControl.notifyDataSetChanged();
    }

    public void setLand(boolean isLand){
        graphicViewControl.setLand(isLand);
    }

    public KLineEntity getItem(int position){
        return dataHelper.getKLineEntities().get(position);
    }

    public KLineEntity getLastItem(){
        return dataHelper.getKLineEntities() == null || dataHelper.getKLineEntities().size() == 0? null:dataHelper.getKLineEntities().get(dataHelper.getKLineEntities().size()-1);
    }

    /**
     * 均线周期发生改变，更新页面
     */
    public void updateTopSetting(){
        dataHelper.notifyTopDataSetChanged();
        graphicViewControl.notifyTopDataSetChanged();
    }
}
