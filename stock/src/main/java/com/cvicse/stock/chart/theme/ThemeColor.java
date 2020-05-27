package com.cvicse.stock.chart.theme;

import android.graphics.Color;

import com.mitake.core.AppInfo;

/**
 * Created by liu_zlu on 2017/4/28 09:20
 */
public class ThemeColor {
    public static int BLACK = Color.BLACK;

    public static int WHITE = Color.WHITE;

    public static boolean isNight = true;

    public static int blackWhite(){
         if(isNight){
             return WHITE;
         }
        return BLACK;
    }

    public static int background(){
        if(isNight){
            return WHITE;
        }
        return BLACK;
    }

    private boolean isLevel_1(){
        return AppInfo.getInfoLevel().equals("1");
    }


    public static void setNight(boolean isNight){
        ThemeColor.isNight = isNight;
    }
}
