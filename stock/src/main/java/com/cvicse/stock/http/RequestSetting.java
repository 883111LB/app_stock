package com.cvicse.stock.http;

import android.content.Context;
import android.content.SharedPreferences;

import com.cvicse.base.ui.BaseApplication;

/**
 * 网络刷新设置
 * Created by liu_zlu on 2017/3/15 11:00
 */
public class RequestSetting {

    public static final String CHOOSE_RATE = "choose_rate";

    public static final String KEY_TIMEOUT_REMINDER = "timeout";

    private static  int globRate = -2;
    public static void setRefreshRate(int rate){
        globRate = rate;
        SharedPreferences pref = BaseApplication.getInstance().getSharedPreferences("user_setting", Context.MODE_PRIVATE);
        pref.edit().putInt(CHOOSE_RATE,rate).apply();
    }

    public static int getRefreshRate(){
        if(globRate > -2){
            return globRate;
        }
        SharedPreferences pref = BaseApplication.getInstance().getSharedPreferences("user_setting", Context.MODE_PRIVATE);
        globRate = pref.getInt(CHOOSE_RATE,0);
        return globRate;
    }

    public static String getRefreshRateStr(){
        SharedPreferences pref = BaseApplication.getInstance().getSharedPreferences("user_setting", Context.MODE_PRIVATE);
        int rate =  pref.getInt(CHOOSE_RATE,0);
        String str = "";
        switch (rate) {
            case -1:
                str = "不刷新";
                break;

            case 0:
                str = "实时刷新";
                break;
            //20205013添加
            case 2:
                str = "2秒";
                break;

            case 3:
                str = "3秒";
                break;

            case 5:
                str = "5秒";
                break;

            case 15:
                str = "15秒";
                break;

            case 30:
                str = "30秒";
                break;

            case 60:
                str = "60秒";
                break;

            default:
                break;
        }
        return str;
    }


    public static void setTimeOut(int timeOut){
        SharedPreferences pref = BaseApplication.getInstance().getSharedPreferences("user_setting", Context.MODE_PRIVATE);
        pref.edit().putInt(KEY_TIMEOUT_REMINDER,timeOut).apply();
    }

    public static int getTimeOut(){
        SharedPreferences pref = BaseApplication.getInstance().getSharedPreferences("user_setting", Context.MODE_PRIVATE);
        return pref.getInt(KEY_TIMEOUT_REMINDER,5);
    }
}
