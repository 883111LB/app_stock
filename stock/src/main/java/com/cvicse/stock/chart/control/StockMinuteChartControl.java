package com.cvicse.stock.chart.control;

import android.util.SparseArray;

import com.android.haitong.R;
import com.cvicse.stock.chart.chart.GCombinedChart;
import com.cvicse.stock.chart.components.GYAxis;
import com.cvicse.stock.chart.data.MinuteDataHelper;
import com.cvicse.stock.chart.formatter.CommonValueFormatter;
import com.cvicse.stock.chart.ui.StockMinuteChart;
import com.cvicse.stock.chart.utils.ChartUtils;
import com.cvicse.stock.chart.utils.MyUtils;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.mitake.core.QuoteItem;

/**
 * 分时图控制类
 * Created by liuzilu on 2017/2/21.
 */

public class StockMinuteChartControl {
    // 分时图图表
    private StockMinuteChart graphicView;

    // 分时图顶部图表
    private GCombinedChart topchart;

    //分时图底部图表
    private GCombinedChart bottomchart;

    //x轴
    private XAxis xAxisBottom, xAxisTop;

    //  左侧侧边栏
    private GYAxis yAxisLeftBottom, yAxisLeftTop;

    //右侧侧边栏
    private GYAxis yAxisRightBottom, yAxisRightTop;

    //分时图数据帮助类
    MinuteDataHelper iDataHelper ;

    private SparseArray<String> xLabels = null;

        public StockMinuteChartControl(MinuteDataHelper mDataHelper,StockMinuteChart graphicView){
        this.iDataHelper = mDataHelper;
        this.graphicView = graphicView;
        topchart = graphicView.topchart;
        bottomchart = graphicView.bottomchart;
        xAxisTop = topchart.getXAxis();
        yAxisLeftTop = (GYAxis) topchart.getAxisLeft();
        yAxisRightTop = (GYAxis) topchart.getAxisRight();

        xAxisBottom = bottomchart.getXAxis();
        yAxisLeftBottom = (GYAxis) bottomchart.getAxisLeft();
        yAxisRightBottom = (GYAxis) bottomchart.getAxisRight();
        init();
    }

    public void notifyDataSetChanged(){
        setAxisMaximum(iDataHelper.getXLength());
        yAxisLeftTop.setAxisMaximum(iDataHelper.getDataMax());
        yAxisLeftTop.setAxisMinimum(iDataHelper.getDataMin()); // Y轴左侧顶部最小值
        yAxisRightTop.setAxisMinimum(iDataHelper.getPercentMin());
        yAxisRightTop.setAxisMaximum(iDataHelper.getPercentMax());

        topchart.setData(iDataHelper.getTopChartData());
        bottomchart.setData(iDataHelper.getBottomChartData());
        graphicView.postDelayed(new Runnable() {
            @Override
            public void run() {
                bottomchart.setAutoScaleMinMaxEnabled(true);
                topchart.setAutoScaleMinMaxEnabled(true);
                topchart.notifyDataSetChanged();
                bottomchart.notifyDataSetChanged();
                topchart.invalidate();
                bottomchart.invalidate();
            }
        }, 100);
    }

    /**
     * 设置第一个时间，中间时间以及最后一个时间
     * @return
     */
    private SparseArray<String> setXLabels() {
        xLabels = new SparseArray<>();
        xLabels.put(0,  iDataHelper.timezoneEntity.openingTimeONE);    // new 开盘价

        xLabels.put(iDataHelper.timezoneEntity.FirstPlateTime,
                iDataHelper.timezoneEntity.CloseTimeONE+"/"+iDataHelper.timezoneEntity.openingTimeTWO);

        xLabels.put(iDataHelper.timezoneEntity.TradeTime, iDataHelper.timezoneEntity.CloseTimeTWO);

        return xLabels;
    }

    private void init(){
        //设置顶部高亮标志
        topchart.setMarkerLeftFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                if(iDataHelper != null)return iDataHelper.getTopLeftLable((int) value);
                return "";
            }
        });
        topchart.setMarkerRightFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                if(iDataHelper != null)return iDataHelper.getTopRightLable((int) value);
                return "";
            }
        });

        bottomchart.setMarkerBottomFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                if(iDataHelper != null)return iDataHelper.getBottomLable((int) value);
                return "";
            }
        });
        setTopSetting();
        setBottomSetting();

        xAxisTop.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                if( null==xLabels ){
                    setXLabels();
                }
                return xLabels.get((int) value);
            }
        });

        yAxisLeftTop.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                QuoteItem quoteItem = iDataHelper.getQuoteItem();
                if( null==quoteItem  || null ==quoteItem.subtype ){
                    return "";
                }
                return CommonValueFormatter.formatValue(quoteItem.market,quoteItem.subtype,value);
            }
        });
        yAxisRightTop.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return CommonValueFormatter.formatPercent(value);
            }
        });

        xAxisBottom.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                if( null== xLabels){
                    setXLabels();
                }
                return xLabels.get((int) value);
            }
        });
    }

    private void setTopSetting(){
        topchart = graphicView.getTopChart();
        topchart.setDrawBorders(true);
        topchart.setBorderWidth(1);
        topchart.setBorderColor(graphicView.getResources().getColor(R.color.minute_grayLine));
        topchart.setDragEnabled(true);
        topchart.setScaleYEnabled(false);
        topchart.setDescription(null);
        Legend topchartLegend = topchart.getLegend();
        topchartLegend.setEnabled(false);
        setTopXAxisSetting();
        setTopAxisLeftSetting();
        setTopAxisRightSetting();
    }

    private void setTopXAxisSetting(){
        xAxisTop.setDrawLabels(false);
        xAxisTop.setDrawGridLines(true);
        xAxisTop.setDrawAxisLine(false);
        xAxisTop.setAxisMinimum(0);
        //bar x y轴
        xAxisTop.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxisTop.setGridColor(graphicView.getResources().getColor(R.color.minute_grayLine));
    }

    private void setTopAxisLeftSetting(){
        // 设置左侧顶部刻度数量
        yAxisLeftTop.setLabelCount(3);
        yAxisLeftTop.setDrawGridLines(true);
        yAxisLeftTop.setDrawAxisLine(false);
        yAxisLeftTop.setDrawLabels(true);
        ((GYAxis) yAxisLeftTop).setShowOnlyMinMaxEnabled(true);
        yAxisLeftTop.setGridColor(graphicView.getResources().getColor(R.color.minute_grayLine));
        yAxisLeftTop.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
    }

    private void setTopAxisRightSetting(){
        // 设置右侧顶部刻度数量
        yAxisRightTop.setLabelCount(3);
        //     yAxisRightTop.setDrawLabels(false);
        yAxisRightTop.setDrawGridLines(false);
        yAxisRightTop.setDrawAxisLine(false);
        ((GYAxis) yAxisRightTop).setShowOnlyMinMaxEnabled(true);
        yAxisRightTop.setGridColor(graphicView.getResources().getColor(R.color.minute_grayLine));
    }

    private void setBottomSetting(){
        bottomchart.setDrawBorders(true);
        bottomchart.setBorderWidth(1);
        bottomchart.setBorderColor(graphicView.getResources().getColor(R.color.minute_grayLine));
        bottomchart.setDescription(null);
        bottomchart.setDragEnabled(true);
        bottomchart.setScaleYEnabled(false);
        Legend bottomchartLegend = bottomchart.getLegend();
        bottomchartLegend.setEnabled(false);
        xAxisBottom.setAxisMinimum(0);
        setBottomXAxisSetting();
        setBottomAxisLeftSetting();
        setBottomAxisRightSetting();
    }

    private void setBottomXAxisSetting(){
        xAxisBottom.setAvoidFirstLastClipping(true);
        xAxisBottom.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxisBottom.setGridColor(graphicView.getResources().getColor(R.color.minute_grayLine));
    }

    private void setBottomAxisLeftSetting(){
        yAxisLeftBottom.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                if(value == 0){
                    return iDataHelper.unitStr;
                }
                return MyUtils.getVolNum(value/100);
            }
        });
        yAxisLeftBottom.setDrawAxisLine(false);
        yAxisLeftBottom.setDrawLabels(true);
        yAxisLeftBottom.setSpaceTop(0);
        yAxisLeftBottom.setDrawGridLines(false);
        yAxisLeftBottom.setAxisMinimum(0);
        yAxisLeftBottom.resetAxisMaximum();
        // 设置左侧底部刻度数量
        yAxisLeftBottom.setLabelCount(6);
        yAxisLeftBottom.setShowOnlyMinMaxEnabled(true);
    }

    private void setBottomAxisRightSetting(){
        yAxisRightBottom.setDrawLabels(false);
        yAxisRightBottom.setDrawGridLines(false);
        yAxisRightBottom.setDrawAxisLine(false);
    }

    public void setLand(boolean isLand){
        if(!isLand){
            topchart.setScaleEnabled(false);
            bottomchart.setScaleEnabled(false);
            yAxisLeftTop.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART);
            yAxisRightTop.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART);
            yAxisLeftBottom.setEnabled(false);
            topchart.setmMinBottomOffset(0);
            topchart.setmMinTopOffset(0);
            bottomchart.setmMinTopOffset(0);
            bottomchart.setmMinBottomOffset(100);
        } else {
            topchart.setmMinTopOffset(0);
            bottomchart.setmMinBottomOffset(100);
            yAxisRightBottom.setNeedsOffset(true);
            yAxisLeftTop.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
            yAxisRightTop.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
            ChartUtils.showLeft(topchart,bottomchart);
            ChartUtils.showRight(topchart,bottomchart);
        }
    }

    private void setAxisMaximum(float max){
        xAxisBottom.setAxisMaximum(max);
        xAxisTop.setAxisMaximum(max);
    }
}

