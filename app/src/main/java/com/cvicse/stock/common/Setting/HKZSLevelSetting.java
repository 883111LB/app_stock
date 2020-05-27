package com.cvicse.stock.common.Setting;

import android.content.Context;
import android.content.SharedPreferences;

import com.cvicse.base.ui.BaseApplication;

/** 港股指数权限设置
 * Created by tang_xqing on 17-11-21.
 */
public class HKZSLevelSetting {
    private static final String CHOOSE_HKZS_LEVEL = "choose_hkzs_level";

    private static final String HKZS_NONE = ""; // 默认港股指数无权限

    public static String getHKZSMode(){
        SharedPreferences pref = BaseApplication.getInstance().getSharedPreferences("user_setting", Context.MODE_PRIVATE);
        String level = pref.getString(CHOOSE_HKZS_LEVEL, HKZS_NONE);
        return level;
    }

    public static void setHKZSMode(String mode){
        SharedPreferences pref = BaseApplication.getInstance().getSharedPreferences("user_setting", Context.MODE_PRIVATE);
        pref.edit().putString(CHOOSE_HKZS_LEVEL,mode).apply();
    }

}
