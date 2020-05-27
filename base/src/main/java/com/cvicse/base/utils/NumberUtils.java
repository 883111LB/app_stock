package com.cvicse.base.utils;

/**
 * 数字转换工具
 * Created by liu_zlu on 2017/4/24 14:49
 */
public class NumberUtils {

    public static double parseDouble(String s)  {
        if(StringUtils.isEmpty(s) || "-".equals(s) || "--".equals(s) ||"一".equals(s)){
            return 0;
        }
        double data = 0;
        try{
            data = Double.parseDouble(s);
        } catch (NumberFormatException e){
            e.printStackTrace();
        }
        return data;
    }

    public static float parseFloat(String s)  {
        if(StringUtils.isEmpty(s) || "-".equals(s) || "--".equals(s) ||"一".equals(s)){
            return 0f;
        }
        float data = 0f;
        try{
            //处理数据，可能后台传回的数据为1172.0.0
            String[] splitStr = s.split("\\.");
            if(splitStr.length>2){
                s = splitStr[0] +"."+ splitStr[1];
            }
            if (!"∞".equals(s))
                data = Float.parseFloat(s);

        } catch (NumberFormatException e){
            e.printStackTrace();
        }
        return data;
    }


    public static long parseLong(String s)  {
        if(StringUtils.isEmpty(s)){
            return 0;
        }
        long data = 0;
        try{
            data = Long.parseLong(s);
        } catch (NumberFormatException e){
            e.printStackTrace();
        }
        return data;
    }

    public static int parseInt(String s)  {
        if(StringUtils.isEmpty(s)){
            return 0;
        }
        int data = 0;
        try{
            data = Integer.parseInt(s);
        } catch (NumberFormatException e){
            e.printStackTrace();
        }
        return data;
    }
}
