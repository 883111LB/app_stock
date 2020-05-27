package com.cvicse.stock.common.Setting;

import android.content.Context;
import android.content.SharedPreferences;

import com.cvicse.base.ui.BaseApplication;
import com.mitake.core.util.Permissions;
/**
 * 上期所原油 Level设置
 * Created by liu_bin on 2019/10/8.
 */
public class INELevelSetting {
    private static final String CHOOSE_INE_LEVEL = "choose_ine_level";

    private static final String INE_LEVEL_1 = Permissions.LEVEL_1;
    private static final String INE_LEVEL_2 = Permissions.LEVEL_2;

    public static void toggleIneLevel(){
        SharedPreferences pref = BaseApplication.getInstance().getSharedPreferences("user_setting", Context.MODE_PRIVATE);
        String level = pref.getString(CHOOSE_INE_LEVEL, INE_LEVEL_1);
        if(level.equals(INE_LEVEL_1)){
            level = INE_LEVEL_2;
        } else {
            level = INE_LEVEL_1;
        }
        pref.edit().putString(CHOOSE_INE_LEVEL, level).apply();
    }

    public static String getLevel(){
        SharedPreferences pref = BaseApplication.getInstance().getSharedPreferences("user_setting", Context.MODE_PRIVATE);
        String level = pref.getString(CHOOSE_INE_LEVEL, INE_LEVEL_1);
        return level;
    }
    public static boolean isIneLevel1(){
        SharedPreferences pref = BaseApplication.getInstance().getSharedPreferences("user_setting", Context.MODE_PRIVATE);
        String level = pref.getString(CHOOSE_INE_LEVEL, INE_LEVEL_1);
        return level.equals(INE_LEVEL_1);
    }

    public static boolean isIneLevel2(){
        SharedPreferences pref = BaseApplication.getInstance().getSharedPreferences("user_setting", Context.MODE_PRIVATE);
        String level = pref.getString(CHOOSE_INE_LEVEL, INE_LEVEL_1);
        return !level.equals(INE_LEVEL_1);
    }

}
