package com.cvicse.stock.common.Setting;

import android.content.Context;
import android.content.SharedPreferences;

import com.cvicse.base.ui.BaseApplication;
import com.mitake.core.util.Permissions;

/** 港股权限设置
 * Created by tang_xqing on 17-11-21.
 */
public class HKLevelSetting {
    private static final String CHOOSE_HK_LEVEL = "choose_hk_level";

    private static final String HK_LEVEL_10 = Permissions.HK10; // 默认港股10档

    public static String getHKMode(){
        SharedPreferences pref = BaseApplication.getInstance().getSharedPreferences("user_setting", Context.MODE_PRIVATE);
        String level = pref.getString(CHOOSE_HK_LEVEL, HK_LEVEL_10);
        return level;
    }

    public static void setHKMode(String mode){
        SharedPreferences pref = BaseApplication.getInstance().getSharedPreferences("user_setting", Context.MODE_PRIVATE);
        pref.edit().putString(CHOOSE_HK_LEVEL,mode).apply();
    }

}
