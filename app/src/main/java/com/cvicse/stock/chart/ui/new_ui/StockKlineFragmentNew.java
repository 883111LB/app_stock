package com.cvicse.stock.chart.ui.new_ui;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.MotionEvent;

import com.cvicse.stock.chart.utils.StockTechType;
import com.cvicse.stock.markets.helper.QuoteCallback;
import com.cvicse.stock.markets.ui.new_ui.StockDetailLandActivityNew;
import com.mitake.core.QuoteItem;
import com.mitake.core.response.QuoteResponse;
import com.mitake.diagram.StockChartActivityPackage.TechChartPackage.Formula.FormulaChangeManager;
import com.mitake.diagram.StockChartActivityPackage.TechChartPackage.Formula.KFormula;
import com.mitake.diagram.StockChartActivityPackage.TechChartPackage.Formula.LineFormula;
import com.mitake.diagram.StockChartActivityPackage.TechChartPackage.StockTechChart;
import com.mitake.diagram.StockChartActivityPackage.TechChartPackage.entry.BaseKEntity;
import com.mitake.utility.object.QueryLineTechCallBack;
import com.mitake.utility.object.UIDoubleTapCallBack;
import com.mitake.variable.object.CommonInfo;

import java.util.List;

/** 新K线图
 * Created by tang_xqing on 2017/8/24.
 */

public class StockKlineFragmentNew implements QuoteCallback, QueryLineTechCallBack, UIDoubleTapCallBack {

    private StockTechChart stockTechChart;
    private QuoteItem quoteItem;
    private String kType;
    private Context mContext;

    public StockKlineFragmentNew(Context context, QuoteItem quoteItem , String kType ){
        this.mContext = context;
        this.quoteItem = quoteItem;
        this.kType = kType;
        initView();
    }

    public Fragment newInstance(){
        return stockTechChart;
    }

    private void initView( ) {
        CommonInfo.refreshSecond = 5;  // new

        // 主视图
        int[] KSTYLEs = new int[]{-1, -2};
        List<KFormula> KformulaList = FormulaChangeManager.getKFormula(mContext, KSTYLEs);
        // 副视图
        int[] STYLEs = new int[]{0,22,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21};
        List<LineFormula> formulaList = FormulaChangeManager.getFormulas(mContext, STYLEs);

        BaseKEntity kEntity = new BaseKEntity();
        kEntity.setMajorFormula(KformulaList);//主视图必要
        kEntity.setMinorFormula(formulaList);//副视图必要

        kEntity.setMASettingButton(0, 0);//主视图切换
        kEntity.setChangeIndexButton(0, 0);//主图设均线
        kEntity.setChangeSubButton(0, 0);//复权
        kEntity.setSettingValueButton(0, 0);//副视图切换
        kEntity.setSubMATypeButton(0, 0);//副图设均线

        kEntity.setZoom_in_bg(0);
        kEntity.setZoom_out_bg(0);
        kEntity.setTimeTextModel(1); // 设定时间文字模式
        kEntity.setTimeTextArea(0); // 设置时间文字区域
        kEntity.setMidAreaHeight(0); // 设定主副图间距
        kEntity.setIfTimeTextGoneWithLineGone(true); // 设定竖线是否要随着文字隐藏而隐藏
        kEntity.setTimeTextSize(14);
        kEntity.setCouldDoubleTap(false); //设定是否开启双击切换横竖屏

        stockTechChart = new StockTechChart(this);
        stockTechChart.setStock(quoteItem.id);
        stockTechChart.setChartType(kType);  // K线类型
        stockTechChart.setStyle(kEntity);
//        stockTechChart.setTapCallBack(this); // 设定自定义双击事件

        Bundle config = new Bundle();
        config.putInt("position", 0);
        stockTechChart.setArguments(config);

        Configuration mConfiguration = mContext.getResources().getConfiguration(); //获取设置的配置信息
        int ori = mConfiguration.orientation; //获取屏幕方向
        if (!(ori == mConfiguration.ORIENTATION_LANDSCAPE)) {
            stockTechChart.setTapCallBack(this); // 设定自定义双击事件
        }
    }

    @Override
    public void update(QuoteResponse quoteResponse) {
        if(null == quoteResponse || null == quoteResponse.quoteItems || quoteResponse.quoteItems.size() <= 0){
            return;
        }
        quoteItem = quoteResponse.quoteItems.get(0);
        boolean added = stockTechChart.isAdded();
        refreshData();
        if( added ){
            refreshData();
        }else{
            new Handler().post(new Runnable() {
                @Override
                public void run() {
                    refreshData();
                }
            });
        }
    }

    public void refreshData( ){
        stockTechChart.setSTKItem(quoteItem);
        stockTechChart.refreshData();
    }

    /**
     * 查价线接口回调
     */
    @Override
    public void callback(String time, boolean isQuery, String qOpen, String qHigh, String qLow, String qPrice, String qVolume, String qchange,
                         String qchangeRate, String turnoverRate, String qyPrice, String amount) {
    }

    /**
     * 自定义双击事件（横屏切换）
     * @param motionEvent
     */
    @Override
    public void callback(MotionEvent motionEvent) {
        StockDetailLandActivityNew.startActivity((Activity) mContext, quoteItem, StockTechType.asType(kType).ordinal()+2);
    }
}
