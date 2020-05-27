package com.stock.config;

import android.content.Context;
import android.content.SharedPreferences;

import com.cvicse.base.ui.BaseApplication;
import com.cvicse.stock.chart.ui.new_ui.StockMinuteChartNew;

/**
 * Created by tang_xqing on 2018/3/19
 */
public class MSetting {

    private static String M_SUB_TYPE_KEY = "M_sub_type_key";

   static SharedPreferences sharedPreferences = BaseApplication.getInstance()
           .getSharedPreferences("M_setting", Context.MODE_PRIVATE);

    /**
     * 设置走势副图指标类别
     * @return
     */
    public static void setMSubType(int type){
        sharedPreferences.edit().putInt(M_SUB_TYPE_KEY,type).apply();
    }

    /**
     * 设置走势副图指标类别
     * @return
     */
    public static void setMSubType(StockMinuteChartNew.MSubType type){
        sharedPreferences.edit().putInt(M_SUB_TYPE_KEY,type.intValue()).apply();
    }
    /**
     * 获取走势副图指标类别
     * @return
     */
    public static int getMSubType(){
        return sharedPreferences.getInt(M_SUB_TYPE_KEY,0);
    }
    /**
     * 设置下一个走势副图指标类别
     */
    public static void setMSubNext(){
        sharedPreferences.edit().putInt(M_SUB_TYPE_KEY,(getMSubType()+1)%10).apply();
    }

    /**
     * 获取走势副图指标类别
     * @return
     */
    public static StockMinuteChartNew.MSubType getMTypeAsType(){
        int type = sharedPreferences.getInt(M_SUB_TYPE_KEY,0);
        StockMinuteChartNew.MSubType mType = null;
        switch (type){
            default:
            case 0:
                mType = StockMinuteChartNew.MSubType.VOLUME;
                break;
            case 1:
                mType = StockMinuteChartNew.MSubType.DDX;
                break;
            case 2:
                mType = StockMinuteChartNew.MSubType.DDY;
                break;
            case 3:
                mType = StockMinuteChartNew.MSubType.DDZ;
                break;
            case 4:
                mType = StockMinuteChartNew.MSubType.BBD;
                break;
            case 5:
                mType = StockMinuteChartNew.MSubType.RATIOBS;
                break;
            case 6:
                mType = StockMinuteChartNew.MSubType.CAPTIALGAME;
                break;
            case 7:
                mType = StockMinuteChartNew.MSubType.ORDERNUM;
                break;
            case 8:
                mType = StockMinuteChartNew.MSubType.BIGNETVOLUME;
                break;
            case 9:
                mType = StockMinuteChartNew.MSubType.VOLRatio;
                break;
        }
        return mType;
    }
}
