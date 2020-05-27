package com.cvicse.stock.chart.theme;

import android.graphics.Color;

/**
 * Created by liu_zlu on 2017/5/2 20:49
 */
public class NightChartTheme implements ChartTheme {
    /**
     * 视图背景色
     *
     * @return 16进制颜色值 hex color
     */
    @Override
    public int colorBG() {
        return 0;
    }

    /**
     * x轴字体颜色
     *
     * @return 16进制颜色值 hex color
     */
    @Override
    public int colorXAxis() {
        return ThemeColor.WHITE;
    }

    /**
     * Y轴左侧字体颜色
     *
     * @return 16进制颜色值 hex color
     */
    @Override
    public int colorYAxisLeft() {
        return ThemeColor.WHITE;
    }

    /**
     * Y轴右侧字体颜色
     *
     * @return 16进制颜色值 hex color
     */
    @Override
    public int colorYAxisRight() {
        return ThemeColor.WHITE;
    }

    /**
     * 绿色
     *
     * @return 16进制颜色值 hex color
     */
    @Override
    public int colorGreen() {
        return Color.GREEN;
    }

    /**
     * 红色
     *
     * @return 16进制颜色值 hex color
     */
    @Override
    public int colorRed() {
        return Color.RED;
    }

    /**
     * 黄色
     *
     * @return 16进制颜色值 hex color
     */
    @Override
    public int colorYellow() {
        return 0xFFE7B448;
    }

    /**
     * 紫红色
     *
     * @return 16进制颜色值 hex color
     */
    @Override
    public int colorFuchsia() {
        return 0xFFFF0097;
    }

    /**
     * 白、黑色
     *
     * @return 16进制颜色值 hex color
     */
    @Override
    public int colorWhiteBlack() {
        return Color.WHITE;
    }
}
