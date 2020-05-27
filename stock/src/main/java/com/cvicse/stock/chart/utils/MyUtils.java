package com.cvicse.stock.chart.utils;

/**
 * author：ajiang
 * mail：1025065158@qq.com
 * blog：http://blog.csdn.net/qqyanjiang
 */
public class MyUtils {
    /**
     * Prevent class instantiation.
     */
    private MyUtils() {
    }
    public static String getVol(float valum){
        String val;
        if(valum > 10000){
            valum = valum / 10000.0f;
            val = valum + "";
            if(valum > 1000){
                val = (valum + "").substring(0,4);
            } else {
                if(val.length() > 5){
                    val = (valum + "").substring(0,5);
                } else {
                    val = (valum + "").substring(0,val.length());
                }

            }
            val = val + "亿";
        } else {
            if(valum/1000f > 1){
                val = (valum + "").substring(0,4);
                val = val + "万";
            } else {
                val = valum + "";
                val = val.substring(0,val.length() > 5 ? 5:val.length());
            }
        }
        return val;
    }
    public static String getVolUnit(float num) {

        int e = (int) Math.floor(Math.log10(num));
        if (e >= 8) {
            return "亿手";
        } else if (e >= 4) {
            return "万手";
        } else {
            return "手";
        }
    }

    public static String getObvUnit(float num) {

        int e = (int) Math.floor(Math.log10(num));
        if (e >= 8) {
            return "亿";
        } else if (e >= 4) {
            return "万";
        } else {
            return "";
        }
    }

    public static String getVolNum(float valum){
        String val;
        if(valum > 10000){
            valum = valum / 10000.0f;
            val = valum + "";
            if(valum > 1000){
                val = String.format("%.2f",valum);
            } else {
                if(val.length() > 5){
                    val = (valum + "").substring(0,5);
                } else {
                    val = (valum + "").substring(0,val.length());
                }

            }
        } else {
            if(valum/1000f > 1){
                val = (valum + "").substring(0,4);
            } else {
                val = valum + "";
                val = val.substring(0,val.length() > 5 ? 5:val.length());
            }
        }
        return val;
    }
}
