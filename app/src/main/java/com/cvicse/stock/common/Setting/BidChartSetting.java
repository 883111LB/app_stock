package com.cvicse.stock.common.Setting;

import android.content.Context;
import android.content.SharedPreferences;

import com.cvicse.base.ui.BaseApplication;

/**
 * 集合竞价展示状态
 * Created by tang_hua on 2020/3/9.
 */

public class BidChartSetting {
    private static final String CHOOSE_BITCHART = "choose_bitchart";

    public static final String STATE_NONE = "0"; // 默认权限,不展示
    public static final String STATE_SHOW = "1"; // 展示

    public static String getBitchartState(){
        SharedPreferences pref = BaseApplication.getInstance().getSharedPreferences("user_setting", Context.MODE_PRIVATE);
        String level = pref.getString(CHOOSE_BITCHART, STATE_NONE);
        return level;
    }

    public static void setBitchartState(String mode){
        SharedPreferences pref = BaseApplication.getInstance().getSharedPreferences("user_setting", Context.MODE_PRIVATE);
        pref.edit().putString(CHOOSE_BITCHART,mode).apply();
    }
}
