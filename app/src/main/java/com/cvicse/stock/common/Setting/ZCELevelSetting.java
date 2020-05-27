package com.cvicse.stock.common.Setting;

import android.content.Context;
import android.content.SharedPreferences;

import com.cvicse.base.ui.BaseApplication;
import com.mitake.core.util.Permissions;

/** 郑商所 Level设置
 * Created by tang_xqing on 18-7-30.
 */
public class ZCELevelSetting {
    private static final String CHOOSE_ZCE_LEVEL = "choose_zce_level";

    private static final String ZCE_LEVEL_1 = Permissions.LEVEL_1;
    private static final String ZCE_LEVEL_2 = Permissions.LEVEL_2;

    public static void toggleZceLevel(){
        SharedPreferences pref = BaseApplication.getInstance().getSharedPreferences("user_setting", Context.MODE_PRIVATE);
        String level = pref.getString(CHOOSE_ZCE_LEVEL, ZCE_LEVEL_1);
        if(level.equals(ZCE_LEVEL_1)){
            level = ZCE_LEVEL_2;
        } else {
            level = ZCE_LEVEL_1;
        }
        pref.edit().putString(CHOOSE_ZCE_LEVEL, level).apply();
    }

    public static String getLevel(){
        SharedPreferences pref = BaseApplication.getInstance().getSharedPreferences("user_setting", Context.MODE_PRIVATE);
        String level = pref.getString(CHOOSE_ZCE_LEVEL, ZCE_LEVEL_1);
        return level;
    }
    public static boolean isZceLevel1(){
        SharedPreferences pref = BaseApplication.getInstance().getSharedPreferences("user_setting", Context.MODE_PRIVATE);
        String level = pref.getString(CHOOSE_ZCE_LEVEL, ZCE_LEVEL_1);
        return level.equals(ZCE_LEVEL_1);
    }

    public static boolean isZceLevel2(){
        SharedPreferences pref = BaseApplication.getInstance().getSharedPreferences("user_setting", Context.MODE_PRIVATE);
        String level = pref.getString(CHOOSE_ZCE_LEVEL, ZCE_LEVEL_1);
        return !level.equals(ZCE_LEVEL_1);
    }
}
