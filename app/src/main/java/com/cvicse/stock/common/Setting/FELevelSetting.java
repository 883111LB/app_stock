package com.cvicse.stock.common.Setting;

import android.content.Context;
import android.content.SharedPreferences;

import com.cvicse.base.ui.BaseApplication;
import com.mitake.core.util.Permissions;

/**外汇Level设置
 * Created by tang_xqing on 18-7-30.
 */
public class FELevelSetting {
    private static final String CHOOSE_FE_LEVEL = "choose_fe_level";

    private static final String FE_LEVEL_1 = Permissions.LEVEL_1;
    private static final String FE_LEVEL_2 = Permissions.LEVEL_2;

    public static void toggleFeLevel(){
        SharedPreferences pref = BaseApplication.getInstance().getSharedPreferences("user_setting", Context.MODE_PRIVATE);
        String level = pref.getString(CHOOSE_FE_LEVEL, FE_LEVEL_1);
        if(level.equals(FE_LEVEL_1)){
            level = FE_LEVEL_2;
        } else {
            level = FE_LEVEL_1;
        }
        pref.edit().putString(CHOOSE_FE_LEVEL, level).apply();
    }

    public static String getLevel(){
        SharedPreferences pref = BaseApplication.getInstance().getSharedPreferences("user_setting", Context.MODE_PRIVATE);
        String level = pref.getString(CHOOSE_FE_LEVEL, FE_LEVEL_1);
        return level;
    }
    public static boolean getFeLevel1(){
        SharedPreferences pref = BaseApplication.getInstance().getSharedPreferences("user_setting", Context.MODE_PRIVATE);
        String level = pref.getString(CHOOSE_FE_LEVEL, FE_LEVEL_1);
        return level.equals(FE_LEVEL_1);
    }

    public static boolean getFeLevel2(){
        SharedPreferences pref = BaseApplication.getInstance().getSharedPreferences("user_setting", Context.MODE_PRIVATE);
        String level = pref.getString(CHOOSE_FE_LEVEL, FE_LEVEL_1);
        return !level.equals(FE_LEVEL_1);
    }
}
