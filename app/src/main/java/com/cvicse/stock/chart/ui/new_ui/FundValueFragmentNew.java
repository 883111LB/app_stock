package com.cvicse.stock.chart.ui.new_ui;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.cvicse.stock.markets.helper.QuoteCallback;
import com.mitake.core.QuoteItem;
import com.mitake.core.response.QuoteResponse;
import com.mitake.diagram.StockChartActivityPackage.FundValueChartPackage.EntityFundValue;
import com.mitake.diagram.StockChartActivityPackage.FundValueChartPackage.FundValueChart;
import com.mitake.utility.object.QueryFundValueCallBack;

/**
 * 新基金净值
 * Created by liu_zlu on 2017/4/28 14:47
 */
public class FundValueFragmentNew implements QuoteCallback, QueryFundValueCallBack {

    private FundValueChart fundValueChart;
    private QuoteItem quoteItem;
    private Context mContext;

    public FundValueFragmentNew(Context context,QuoteItem quoteItem){
        this.quoteItem = quoteItem;
        this.mContext = context;
        initFundValue();

        refreshData();
    }

    private void initFundValue(){
        EntityFundValue entityFundValue = new EntityFundValue();
        entityFundValue.setCouldDoubleTap(true); // 设定双击是否开启横竖屏

        fundValueChart = new FundValueChart();
        fundValueChart.setStyle(entityFundValue); // 设定样式
        fundValueChart.setStockCode(quoteItem.id);
        fundValueChart.setFundValueCallBack(this);  // 设置查价线接口回调
        Bundle config = new Bundle();
        config.putInt("position", 0);
        fundValueChart.setArguments(config);

        Configuration mConfiguration = mContext.getResources().getConfiguration(); //获取设置的配置信息
        int ori = mConfiguration.orientation; //获取屏幕方向
        if (!(ori == mConfiguration.ORIENTATION_LANDSCAPE)) {
//            fundValueChart.setTapCallBack(this); // 设定自定义双击事件
        }
    }

    public Fragment newInstance(){
        return fundValueChart;
    }

    @Override
    public void update(QuoteResponse quoteResponse) {
        if(null == quoteResponse || null == quoteResponse.quoteItems || quoteResponse.quoteItems.size() <= 0){
            return;
        }
        quoteItem = quoteResponse.quoteItems.get(0);
            refreshData();
    }

    public void refreshData( ){
        fundValueChart.setSTKItem(quoteItem);
        fundValueChart.refreshData();
    }

    /**
     * 查价线接口回调
     * @param isQuery
     * @param s
     * @param time
     * @param i
     */
    @Override
    public void callback(boolean isQuery, String s, String time, int i) {
    }
}
