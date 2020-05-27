package com.cvicse.stock.chart.utils;

import com.cvicse.stock.chart.chart.GCombinedChart;
import com.github.mikephil.charting.components.YAxis;

/**
 * Created by liu_zlu on 2017/3/29 09:50
 */
public class ChartUtils {
    /**
     * 设置左侧边栏展示
     * @param combinedCharts
     */
    public static void showLeft(GCombinedChart ...combinedCharts){
        for(GCombinedChart combinedChart: combinedCharts){
            YAxis axisLeft = combinedChart.getAxisLeft();
            combinedChart.setmMinLeftOffset(200);
            axisLeft.setEnabled(true);
        }
    }
    /**
     * 设置左侧边栏展示(分时图专用）
     * @param combinedCharts
     */
    public static void showLeftFs(GCombinedChart ...combinedCharts){
        for(GCombinedChart combinedChart: combinedCharts){
            YAxis axisLeft = combinedChart.getAxisLeft();
            combinedChart.setmMinLeftOffset(0);
            axisLeft.setEnabled(true);
        }
    }
    /**
     * 设置右侧边栏展示
     * @param combinedCharts
     */
    public static void showRight(GCombinedChart ...combinedCharts){
        for(GCombinedChart combinedChart: combinedCharts){
            YAxis axisRight = combinedChart.getAxisRight();
            combinedChart.setmMinRightOffset(200);
            axisRight.setEnabled(true);
        }
    }

    public static void setDrageScroll(GCombinedChart ...combinedCharts){
        for(GCombinedChart combinedChart: combinedCharts){
            combinedChart.setScaleEnabled(false);
            combinedChart.setFullyZoomedOut(true);
        }
    }

    /**
     * 设置只能高亮滑动，图表本身不能滑动
     * @param combinedCharts
     */
    public static void setOnlyDrage(GCombinedChart ...combinedCharts){
        for(GCombinedChart combinedChart: combinedCharts){
            combinedChart.setScaleEnabled(false);
            combinedChart.setFullyZoomedOut(true);
        }
    }
}
