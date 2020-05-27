package com.cvicse.stock.common;

import android.support.v4.content.ContextCompat;

import com.cvicse.stock.BaseApplication;
import com.cvicse.stock.R;
import com.cvicse.stock.common.Setting.Setting;

/**
 * 颜色帮助类
 * Created by liu_zlu on 2017/3/16 17:40
 */
public class ColorUtils {
    //股票上涨颜色
    public static int UP = ContextCompat.getColor(BaseApplication.getInstance(), R.color.text_red);
    // 股票下跌颜色
    public static int DOWN = ContextCompat.getColor(BaseApplication.getInstance(),R.color.text_green);
    public static int WHITE = ContextCompat.getColor(BaseApplication.getInstance(),R.color.text_white);
    public static int BLACK = ContextCompat.getColor(BaseApplication.getInstance(),R.color.text_black);
    public static int Yellow = ContextCompat.getColor(BaseApplication.getInstance(),R.color.text_yellow);
    public static int BLUE = ContextCompat.getColor(BaseApplication.getInstance(),R.color.text_blue);
    public static int GRAY = ContextCompat.getColor(BaseApplication.getInstance(),R.color.text_gray);

    //成交统计颜色
    public static int STOCK_TOTAL_BUY_U =ContextCompat.getColor(BaseApplication.getInstance(),R.color.stock_total_red1);
    public static int STOCK_TOTAL_BUY_L =ContextCompat.getColor(BaseApplication.getInstance(),R.color.stock_total_red2);
    public static int STOCK_TOTAL_BUY_M =ContextCompat.getColor(BaseApplication.getInstance(),R.color.stock_total_red3);
    public static int STOCK_TOTAL_BUY_S =ContextCompat.getColor(BaseApplication.getInstance(),R.color.stock_total_red4);
    public static int STOCK_TOTAL_SELL_U =ContextCompat.getColor(BaseApplication.getInstance(),R.color.stock_total_green1);
    public static int STOCK_TOTAL_SELL_L =ContextCompat.getColor(BaseApplication.getInstance(),R.color.stock_total_green2);
    public static int STOCK_TOTAL_SELL_M =ContextCompat.getColor(BaseApplication.getInstance(),R.color.stock_total_green3);
    public static int STOCK_TOTAL_SELL_S =ContextCompat.getColor(BaseApplication.getInstance(),R.color.stock_total_green4);

    // 文字颜色
    public static int TEXT_BLUE = ContextCompat.getColor(BaseApplication.getInstance(),R.color.text_blue);
    public static int CALENDER_SELECTED = ContextCompat.getColor(BaseApplication.getInstance(),R.color.calendar_threeday_color);
    public static int CALENDER_DEFALUT = ContextCompat.getColor(BaseApplication.getInstance(),R.color.calendar_enable_color);
    public static int CALENDER_HOLIDAY = ContextCompat.getColor(BaseApplication.getInstance(),R.color.calendar_disable_color);

    public static int PRIMARY = ContextCompat.getColor(BaseApplication.getInstance(),R.color.colorPrimary);

    public static int BAR_LEGEND_ONE = ContextCompat.getColor(BaseApplication.getInstance(),R.color.bar_legend_one);
    public static int BAR_LEGEND_TWO = ContextCompat.getColor(BaseApplication.getInstance(),R.color.bar_legend_two);
    public static int BAR_LEGEND_THREE = ContextCompat.getColor(BaseApplication.getInstance(),R.color.bar_legend_three);
    public static int BAR_LEGEND_FOUR = ContextCompat.getColor(BaseApplication.getInstance(),R.color.bar_legend_four);
    public static int LINE_LEGEND = ContextCompat.getColor(BaseApplication.getInstance(),R.color.line_color);

    public static int DEFALUT(){
        if(Setting.isNight()){
            return WHITE;
        } else {
            return BLACK;
        }
    }
}
