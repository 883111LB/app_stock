package com.cvicse.stock.chart.helper.new_helper;

import com.cvicse.stock.chart.control.new_control.StockMinuteChartControlNew;
import com.cvicse.stock.chart.data.MinuteEntity;
import com.cvicse.stock.chart.data.OHLCItemBo;
import com.cvicse.stock.chart.data.new_data.MinuteDataHelperNew;
import com.cvicse.stock.chart.ui.new_ui.StockMinuteChartNew;
import com.mitake.core.QuoteItem;
import com.mitake.core.response.ChartResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * 分时图帮助类
 * Created by tang_xqing on 2017/3/19.
 */
public class StockMinuteChartHelperNew {
    // 分时图控制类
    private StockMinuteChartControlNew graphicViewControl;

    // 分时图数据帮助类
    public MinuteDataHelperNew mDataHelper;

    private StockMState mState = new StockMState();

    public StockMinuteChartHelperNew(StockMinuteChartNew graphicView){
        mDataHelper = new MinuteDataHelperNew(mState);
        graphicViewControl = new StockMinuteChartControlNew(mDataHelper,graphicView,mState);
    }

    public void setQuoteItem(QuoteItem quoteItem){
        mDataHelper.setQuoteItem(quoteItem);
        graphicViewControl.notifyDataSetChanged();
    }

    public void addRequestData(ChartResponse chartResponse){
        mDataHelper.addRequestData(chartResponse);
        graphicViewControl.updateChartSetting();
        graphicViewControl.notifyDataSetChanged();
    }

    /**
     * 走势副图指标
     */
    public void addRequestMSubData(ArrayList<MinuteEntity> mSubEntities){
        mDataHelper.addRequestSubData(mSubEntities);
        graphicViewControl.updateChartSetting();
        graphicViewControl.notifyDataSetChanged();
    }

    public void setLand(boolean isLand){
        graphicViewControl.setLand(isLand);
    }

    public void setMSubType(StockMinuteChartNew.MSubType type){
        if(!mState.judgeSetKType(type)){
            return;
        }
        graphicViewControl.updateChartSetting();
        graphicViewControl.notifyDataSetChanged();
    }

    public MinuteEntity getMSubItem(int position){
        List<MinuteEntity> mLineEntities = mDataHelper.getMSubLineEntities();
        return mLineEntities.get(position >= mLineEntities.size() ? (mLineEntities.size()-1) :position );
    }

    public OHLCItemBo getChartItems(int position){
        ArrayList<OHLCItemBo> chartItemsList = mDataHelper.getChartItemsList();
        if( null == chartItemsList ){
            return null;
        }
        return chartItemsList.get(position >= chartItemsList.size() ? (chartItemsList.size()-1) :position );
    }

    public MinuteEntity getMSubLastItem(){
        return (null == mDataHelper.getMSubLineEntities()  || mDataHelper.getMSubLineEntities().isEmpty())?
                null: mDataHelper.getMSubLineEntities().get(mDataHelper.getMSubLineEntities().size()-1);
    }

    public void updateTopSetting(){
        mDataHelper.notifyTopDataSetChanged();
        graphicViewControl.notifyTopDataSetChanged();
    }
}
