package com.cvicse.stock.chart.theme;

/**
 * Created by liu_zlu on 2017/5/2 20:36
 */
public interface ChartTheme {
    /**
     * 视图背景色
     *
     * @return 16进制颜色值 hex color
     */
    int colorBG();

    /**
     * x轴字体颜色
     *
     * @return 16进制颜色值 hex color
     */
    int colorXAxis();
    /**
     * Y轴左侧字体颜色
     *
     * @return 16进制颜色值 hex color
     */
    int colorYAxisLeft();
    /**
     * Y轴右侧字体颜色
     *
     * @return 16进制颜色值 hex color
     */
    int colorYAxisRight();
    /**
     * 绿色
     *
     * @return 16进制颜色值 hex color
     */
    int colorGreen();
    /**
     * 红色
     *
     * @return 16进制颜色值 hex color
     */
    int colorRed();
    /**
     * 黄色
     *
     * @return 16进制颜色值 hex color
     */
    int colorYellow();
    /**
     * 紫红色
     *
     * @return 16进制颜色值 hex color
     */
    int colorFuchsia();
    /**
     * 白、黑色
     *
     * @return 16进制颜色值 hex color
     */
    int colorWhiteBlack();
}
