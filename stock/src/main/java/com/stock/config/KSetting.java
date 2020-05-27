package com.stock.config;

import android.content.Context;
import android.content.SharedPreferences;

import com.cvicse.base.ui.BaseApplication;
import com.cvicse.stock.chart.data.TechChartType;

/**
 * Created by liu_zlu on 2017/3/21 13:43
 */
public class KSetting {
    private static String AVERAGE_1_KEY = "average_1";
    private static String AVERAGE_2_KEY = "average_2";
    private static String AVERAGE_3_KEY = "average_3";
    private static String AVERAGE_VISIABLE_1_KEY = "average_visiable_1_key";
    private static String AVERAGE_VISIABLE_2_KEY = "average_visiable_2_key";
    private static String AVERAGE_VISIABLE_3_KEY = "average_visiable_3_key";
    private static String RIGHT_TYPE_KEY = "right_type_key";

    private static String K_TYPE_KEY = "K_type_key";

   static SharedPreferences sharedPreferences = BaseApplication.getInstance()
           .getSharedPreferences("k_setting", Context.MODE_PRIVATE);

    public static void setAverage_1(int num){
        sharedPreferences.edit().putInt(AVERAGE_1_KEY,num).commit();
    }
    public static void setAverage_2(int num){
        sharedPreferences.edit().putInt(AVERAGE_2_KEY,num).commit();
    }
    public static void setAverage_3(int num){
        sharedPreferences.edit().putInt(AVERAGE_3_KEY,num).commit();
    }

    public static int getAverage_1(){
        return sharedPreferences.getInt(AVERAGE_1_KEY,5);
    }
    public static int getAverage_2(){
        return sharedPreferences.getInt(AVERAGE_2_KEY,10);
    }
    public static int getAverage_3(){
        return sharedPreferences.getInt(AVERAGE_3_KEY,30);
    }


    public static void setAverageVisiable_1(boolean visable){
        sharedPreferences.edit().putBoolean(AVERAGE_VISIABLE_1_KEY,visable).commit();
    }
    public static void setAverageVisiable_2(boolean visable){
        sharedPreferences.edit().putBoolean(AVERAGE_VISIABLE_2_KEY,visable).commit();
    }
    public static void setAverageVisiable_3(boolean visable){
        sharedPreferences.edit().putBoolean(AVERAGE_VISIABLE_3_KEY,visable).commit();
    }

    public static boolean getAverage_Visiable1(){
        return sharedPreferences.getBoolean(AVERAGE_VISIABLE_1_KEY,true);
    }
    public static boolean getAverage_Visiable2(){
        return sharedPreferences.getBoolean(AVERAGE_VISIABLE_2_KEY,true);
    }
    public static boolean getAverage_Visiable3(){
        return sharedPreferences.getBoolean(AVERAGE_VISIABLE_3_KEY,true);
    }

    /**
     * 设置复权类型 0不复权 1前复权 2后复权
     * @return
     */
    public static void setRightType(int type){
         sharedPreferences.edit().putInt(RIGHT_TYPE_KEY,type).commit();
    }
    /**
     * 设置复权类型 0不复权 1前复权 2后复权
     * @return
     */
    public static int getKRightSubType(){
        return sharedPreferences.getInt(RIGHT_TYPE_KEY,0);
    }


    /**
     * 设置K线底部类型类
     * @return
     */
    public static void setKType(int type){
        sharedPreferences.edit().putInt(K_TYPE_KEY,type).commit();
    }

    /**
     * 设置K线底部类型类
     * @return
     */
    public static void setKType(TechChartType.KType type){
        sharedPreferences.edit().putInt(K_TYPE_KEY,type.intValue()).commit();
    }
    /**
     * 获取K线底部类型
     * @return
     */
    public static int getKType(){
        return sharedPreferences.getInt(K_TYPE_KEY,0);
    }
    /**
     * 将K线底部类型
     * @return
     */
    public static void setKNext(){
        sharedPreferences.edit().putInt(K_TYPE_KEY,(getKType()+1)%20).commit();
    }

    /**
     * 获取K线底部类型
     * @return
     */
    public static TechChartType.KType getKTypeAsType(){
        int type = sharedPreferences.getInt(K_TYPE_KEY,0);
        TechChartType.KType ktype = null;
        switch (type){
            default:
            case 0:
                ktype = TechChartType.KType.VOLUME;break;
            case 1:
                ktype = TechChartType.KType.MACD;break;
            case 2:
                ktype = TechChartType.KType.KDJ;break;
            case 3:
                ktype = TechChartType.KType.DMA;break;
            case 4:
                ktype = TechChartType.KType.BOLL;break;
            case 5:
                ktype = TechChartType.KType.RSI;break;
            case 6:
                ktype = TechChartType.KType.WR;break;
            case 7:
                ktype = TechChartType.KType.VR;break;
            case 8:
                ktype = TechChartType.KType.CR;break;
            case 9:
                ktype = TechChartType.KType.DMI; break;
            case 10:
                ktype = TechChartType.KType.BIAS; break;
            case 11:
                ktype = TechChartType.KType.OBV; break;
            case 12:
                ktype = TechChartType.KType.BBI; break;
            case 13:
                ktype = TechChartType.KType.AMO; break;
            case 14:
                ktype = TechChartType.KType.CCI; break;
            case 15:
                ktype = TechChartType.KType.MTM; break;
            case 16:
                ktype = TechChartType.KType.ROC; break;
            case 17:
                ktype = TechChartType.KType.BRAR; break;
            case 18:
                ktype = TechChartType.KType.NVIPVI; break;
            case 19:
                ktype = TechChartType.KType.PSY; break;
        }
        return ktype;
    }
}
