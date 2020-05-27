package com.cvicse.stock.common.Setting;

import android.content.Context;
import android.content.SharedPreferences;

import com.cvicse.base.ui.BaseApplication;
import com.mitake.core.util.Permissions;

/**
 * 上期所 Level设置
 * Created by liu_bin on 2019/6/25.
 */

public class SHFELevelSetting {
    private static final String CHOOSE_SHFE_LEVEL = "choose_shfe_level";

    private static final String SHFE_LEVEL_1 = Permissions.LEVEL_1;
    private static final String SHFE_LEVEL_2 = Permissions.LEVEL_2;

    public static void toggleShfeLevel(){
        SharedPreferences pref = BaseApplication.getInstance().getSharedPreferences("user_setting", Context.MODE_PRIVATE);
        String level = pref.getString(CHOOSE_SHFE_LEVEL, SHFE_LEVEL_1);
        if(level.equals(SHFE_LEVEL_1)){
            level = SHFE_LEVEL_2;
        } else {
            level = SHFE_LEVEL_1;
        }
        pref.edit().putString(CHOOSE_SHFE_LEVEL, level).apply();
    }

    public static String getLevel(){
        SharedPreferences pref = BaseApplication.getInstance().getSharedPreferences("user_setting", Context.MODE_PRIVATE);
        String level = pref.getString(CHOOSE_SHFE_LEVEL, SHFE_LEVEL_1);
        return level;
    }
    public static boolean isShfeLevel1(){
        SharedPreferences pref = BaseApplication.getInstance().getSharedPreferences("user_setting", Context.MODE_PRIVATE);
        String level = pref.getString(CHOOSE_SHFE_LEVEL, SHFE_LEVEL_1);
        return level.equals(SHFE_LEVEL_1);
    }

    public static boolean isShfeLevel2(){
        SharedPreferences pref = BaseApplication.getInstance().getSharedPreferences("user_setting", Context.MODE_PRIVATE);
        String level = pref.getString(CHOOSE_SHFE_LEVEL, SHFE_LEVEL_1);
        return !level.equals(SHFE_LEVEL_1);
    }
}
