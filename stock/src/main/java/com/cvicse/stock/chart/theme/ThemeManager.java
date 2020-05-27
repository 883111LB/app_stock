package com.cvicse.stock.chart.theme;

import android.graphics.Color;
import android.util.Log;

/**
 * 图表基础颜色换肤
 * Created by liu_zlu on 2017/5/2 20:53
 */
public class ThemeManager{
    public static ChartTheme chartTheme;

    public static void init(){
        if(ThemeColor.isNight){
            chartTheme = new NightChartTheme();
            Log.e("ThemeManager", "NightChartTheme");
        } else {
            chartTheme = new DayChartTheme();
            Log.e("ThemeManager", "DayChartTheme");
        }
    }
    /**
     * 视图背景色
     *
     * @return 16进制颜色值 hex color
     */
    public static int colorBG() {
        return chartTheme.colorBG();
    }

    /**
     * x轴字体颜色
     *
     * @return 16进制颜色值 hex color
     */
    public static int colorXAxis() {
        return chartTheme.colorXAxis();
    }

    /**
     * Y轴左侧字体颜色
     *
     * @return 16进制颜色值 hex color
     */
    public static int colorYAxisLeft() {
        return chartTheme.colorYAxisLeft();
    }

    /**
     * Y轴右侧字体颜色
     *
     * @return 16进制颜色值 hex color
     */
    public static int colorYAxisRight() {
        return chartTheme.colorYAxisRight();
    }

    /**
     * 绿色
     *
     * @return 16进制颜色值 hex color
     */
    public static int colorGreen() {
        return chartTheme.colorGreen();
    }

    /**
     * 红色
     *
     * @return 16进制颜色值 hex color
     */
    public static int colorRed() {
        return chartTheme.colorRed();
    }

    /**
     * 黄色
     *
     * @return 16进制颜色值 hex color
     */
    public static int colorYellow() {
        return chartTheme.colorYellow();
    }

    /**
     * 紫红色
     *
     * @return 16进制颜色值 hex color
     */
    public static int colorFuchsia() {
        return chartTheme.colorFuchsia();
    }

    /**
     * 白、黑色
     *
     * @return 16进制颜色值 hex color
     */
    public static int colorWhiteBlack() {
        return chartTheme.colorWhiteBlack();
    }
}

