package com.cvicse.stock.chart.control;

import com.cvicse.stock.chart.ui.FundValueChart;
import com.cvicse.stock.chart.chart.GCombinedChart;
import com.cvicse.stock.chart.components.GYAxis;
import com.cvicse.stock.chart.data.FundValueDataHelper;
import com.cvicse.stock.chart.utils.ChartUtils;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

/**
 * 基金净值图控制类
 * Created by liu_zlu on 2017/4/28 14:57
 */
public class FundValueChartControl {
    /**
     * 基金净值图表
     */
    private GCombinedChart gCombinedChart;
    /**
     * 基金净值数据帮助类
     */
    private FundValueDataHelper dataHelper;
    /**
     * 基金净值侧边栏
     */
    private GYAxis axisLeft,axisRight;
    /**
     * 基金净值x轴
     */
    private XAxis xAxis;
    /**
     * 是否横屏
     */
    private boolean isLand = false;
    public FundValueChartControl(FundValueChart fundValueChart, FundValueDataHelper dataHelper){
        this.gCombinedChart = fundValueChart.getChart();
        this.dataHelper = dataHelper;
        axisLeft = (GYAxis) gCombinedChart.getAxisLeft();
        axisRight = (GYAxis) gCombinedChart.getAxisRight();
        xAxis = gCombinedChart.getXAxis();
        init();
    }

    private void init(){
        //bar x y轴
        axisLeft.setLabelCount(5);
        axisLeft.removeAllLimitLines();
        axisRight.setDrawLabels(false);
        axisRight.setDrawGridLines(false);
        axisRight.setDrawAxisLine(true);
        axisLeft.setShowOnlyMinMaxEnabled(true);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        //设置左侧的最小边距
        gCombinedChart.setmMinBottomOffset(100);
        gCombinedChart.setScaleYEnabled(false);
        gCombinedChart.setDescription(null);
        Legend topchartLegend = gCombinedChart.getLegend();
        topchartLegend.setEnabled(false);
        xAxis.setEnabled(true);
        xAxis.setDrawLabels(true);
        xAxis.setDrawGridLines(false);
        xAxis.setValueFormatter(dataHelper.getValueFormatter());
        //侧边栏数据格式化
        axisLeft.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return String.format("%.3f",value);
            }
        });
        gCombinedChart.setMarkerBottomFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                if(dataHelper != null){
                    return dataHelper.getBottomLable((int) value);
                }
                return "";
            }
        });
        gCombinedChart.setMarkerLeftFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                if(dataHelper != null){
                    return dataHelper.getLeftLable((int) value);
                }
                return "";
            }
        });
    }
    public void notifyDataSetChanged(){

        gCombinedChart.setData(dataHelper.getChartData());
        //图表缩放
        gCombinedChart.postScale(dataHelper.getCount(),4);
        //移到最后一位
        gCombinedChart.moveViewToX(dataHelper.getCount());
        gCombinedChart.postDelayed(new Runnable() {
            @Override
            public void run() {
                gCombinedChart.setAutoScaleMinMaxEnabled(true);
                gCombinedChart.notifyDataSetChanged();
                gCombinedChart.invalidate();
            }
        }, 100);
    }

    public void setLand(boolean isLand) {
        this.isLand = isLand;
        if (isLand) {
            axisLeft.setLabelCount(5);
            axisLeft.setShowOnlyMinMaxEnabled(false);
            ChartUtils.showLeft(gCombinedChart);
            gCombinedChart.setFullyZoomedOut(false);
            gCombinedChart.setScaleEnabled(true);
            axisLeft.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        } else {
            ChartUtils.setOnlyDrage(gCombinedChart);
            axisLeft.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART);
            gCombinedChart.setmMinBottomOffset(100);
            gCombinedChart.setFullyZoomedOut(true);
            gCombinedChart.setHighlightPerDragEnabled(true);
        }
    }
}
