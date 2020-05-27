package com.cvicse.stock.common.Setting;

import android.content.Context;
import android.content.SharedPreferences;

import com.cvicse.base.ui.BaseApplication;
import com.mitake.core.util.Permissions;

/** cff Level设置
 * Created by tang_xqing on 17-11-21.
 */
public class CffLevelSetting {
    private static final String CHOOSE_CFF_LEVEL = "choose_cff_level";

    private static final String CFF_LEVEL_1 = Permissions.CFF_LEVEL_1;
    private static final String CFF_LEVEL_2 = Permissions.CFF_LEVEL_2;

    public static void toggleCffLevel(){
        SharedPreferences pref = BaseApplication.getInstance().getSharedPreferences("user_setting", Context.MODE_PRIVATE);
        String level = pref.getString(CHOOSE_CFF_LEVEL, CFF_LEVEL_1);
        if(level.equals(CFF_LEVEL_1)){
            level = CFF_LEVEL_2;
        } else {
            level = CFF_LEVEL_1;
        }
        pref.edit().putString(CHOOSE_CFF_LEVEL, level).apply();
    }

    public static String getLevel(){
        SharedPreferences pref = BaseApplication.getInstance().getSharedPreferences("user_setting", Context.MODE_PRIVATE);
        String level = pref.getString(CHOOSE_CFF_LEVEL, CFF_LEVEL_1);
        return level;
    }
    public static boolean isCffLevel1(){
        SharedPreferences pref = BaseApplication.getInstance().getSharedPreferences("user_setting", Context.MODE_PRIVATE);
        String level = pref.getString(CHOOSE_CFF_LEVEL, CFF_LEVEL_1);
        return level.equals(CFF_LEVEL_1);
    }

    public static boolean isCffLevel2(){
        SharedPreferences pref = BaseApplication.getInstance().getSharedPreferences("user_setting", Context.MODE_PRIVATE);
        String level = pref.getString(CHOOSE_CFF_LEVEL, CFF_LEVEL_1);
        return !level.equals(CFF_LEVEL_1);
    }
}
