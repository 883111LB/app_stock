package com.cvicse.stock.common.Setting;

import android.content.Context;
import android.content.SharedPreferences;

import com.cvicse.base.ui.BaseApplication;

/**
 * Created by liu_zlu on 2017/3/15 10:59
 */
public class SkinSetting {

    private static final String KEY_SKIN_TYPE = "skin_type";
    private static final String STR_SKIN_DAY = "日间模式";
    private static final String STR_SKIN_NIGHT = "夜间模式";

    public static String getSkinType(){
        SharedPreferences pref = BaseApplication.getInstance().getSharedPreferences("user_setting", Context.MODE_PRIVATE);
        String skinType = pref.getString(KEY_SKIN_TYPE,STR_SKIN_NIGHT);
        return skinType;
    }
    public static void toggleSkin(){
        SharedPreferences pref = BaseApplication.getInstance().getSharedPreferences("user_setting", Context.MODE_PRIVATE);
        String level = pref.getString(KEY_SKIN_TYPE,STR_SKIN_NIGHT);
        if(level.equals(STR_SKIN_DAY)){
            level = STR_SKIN_NIGHT;
        } else {
            level = STR_SKIN_DAY;
        }
        pref.edit().putString(KEY_SKIN_TYPE, level).apply();
    }
    public static boolean isDay(){
        SharedPreferences pref = BaseApplication.getInstance().getSharedPreferences("user_setting", Context.MODE_PRIVATE);
        String level = pref.getString(KEY_SKIN_TYPE,STR_SKIN_NIGHT);
        return level.equals(STR_SKIN_DAY);
    }

    public static boolean isNight(){
        SharedPreferences pref = BaseApplication.getInstance().getSharedPreferences("user_setting", Context.MODE_PRIVATE);
        String level = pref.getString(KEY_SKIN_TYPE,STR_SKIN_NIGHT);
        return !level.equals(STR_SKIN_DAY);
    }

}
