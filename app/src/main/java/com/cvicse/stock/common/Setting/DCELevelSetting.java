package com.cvicse.stock.common.Setting;

import android.content.Context;
import android.content.SharedPreferences;

import com.cvicse.base.ui.BaseApplication;
import com.mitake.core.util.Permissions;

/** 大商所 Level设置
 * Created by tang_xqing on 18-7-30.
 */
public class DCELevelSetting {
    private static final String CHOOSE_DCE_LEVEL = "choose_dce_level";

    private static final String DCE_LEVEL_1 = Permissions.LEVEL_1;
    private static final String DCE_LEVEL_2 = Permissions.LEVEL_2;

    public static void toggleDceLevel(){
        SharedPreferences pref = BaseApplication.getInstance().getSharedPreferences("user_setting", Context.MODE_PRIVATE);
        String level = pref.getString(CHOOSE_DCE_LEVEL, DCE_LEVEL_1);
        if(level.equals(DCE_LEVEL_1)){
            level = DCE_LEVEL_2;
        } else {
            level = DCE_LEVEL_1;
        }
        pref.edit().putString(CHOOSE_DCE_LEVEL, level).apply();
    }

    public static String getLevel(){
        SharedPreferences pref = BaseApplication.getInstance().getSharedPreferences("user_setting", Context.MODE_PRIVATE);
        String level = pref.getString(CHOOSE_DCE_LEVEL, DCE_LEVEL_1);
        return level;
    }
    public static boolean isDceLevel1(){
        SharedPreferences pref = BaseApplication.getInstance().getSharedPreferences("user_setting", Context.MODE_PRIVATE);
        String level = pref.getString(CHOOSE_DCE_LEVEL, DCE_LEVEL_1);
        return level.equals(DCE_LEVEL_1);
    }

    public static boolean isDceLevel2(){
        SharedPreferences pref = BaseApplication.getInstance().getSharedPreferences("user_setting", Context.MODE_PRIVATE);
        String level = pref.getString(CHOOSE_DCE_LEVEL, DCE_LEVEL_1);
        return !level.equals(DCE_LEVEL_1);
    }
}
