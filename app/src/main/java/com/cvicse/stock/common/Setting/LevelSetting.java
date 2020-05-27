package com.cvicse.stock.common.Setting;

import android.content.Context;
import android.content.SharedPreferences;

import com.cvicse.base.ui.BaseApplication;
import com.mitake.core.util.Permissions;

/**
 * Created by liu_zlu on 2017/3/15 10:57
 */
public class LevelSetting {
    private static final String CHOOSE_LEVEL = "choose_level";

    private static final String LEVEL_1 = Permissions.LEVEL_1;
    private static final String LEVEL_2 = Permissions.LEVEL_2;

    public static void toggleLevel(){
        SharedPreferences pref = BaseApplication.getInstance().getSharedPreferences("user_setting", Context.MODE_PRIVATE);
        String level = pref.getString(CHOOSE_LEVEL,LEVEL_1);
        if(level.equals(LEVEL_1)){
            level = LEVEL_2;
        } else {
            level = LEVEL_1;
        }
        pref.edit().putString(CHOOSE_LEVEL, level).apply();
    }

    public static String getLevel(){
        SharedPreferences pref = BaseApplication.getInstance().getSharedPreferences("user_setting", Context.MODE_PRIVATE);
        String level = pref.getString(CHOOSE_LEVEL,LEVEL_1);
        return level;
    }
    public static boolean isLevel1(){
        SharedPreferences pref = BaseApplication.getInstance().getSharedPreferences("user_setting", Context.MODE_PRIVATE);
        String level = pref.getString(CHOOSE_LEVEL,LEVEL_1);
        return level.equals(LEVEL_1);
    }

    public static boolean isLevel2(){
        SharedPreferences pref = BaseApplication.getInstance().getSharedPreferences("user_setting", Context.MODE_PRIVATE);
        String level = pref.getString(CHOOSE_LEVEL,LEVEL_1);
        return !level.equals(LEVEL_1);
    }
}
