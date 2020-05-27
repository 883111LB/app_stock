package com.cvicse.stock.common.Setting;

import android.content.Context;
import android.content.SharedPreferences;

import com.cvicse.base.ui.BaseApplication;
import com.mitake.core.util.Permissions;

/**全球Level设置
 * Created by tang_xqing on 18-7-30.
 */
public class GILevelSetting {
    private static final String CHOOSE_GI_LEVEL = "choose_gi_level";

    private static final String GI_LEVEL_1 = Permissions.LEVEL_1;
    private static final String GI_LEVEL_2 = Permissions.LEVEL_2;

    public static void toggleGiLevel(){
        SharedPreferences pref = BaseApplication.getInstance().getSharedPreferences("user_setting", Context.MODE_PRIVATE);
        String level = pref.getString(CHOOSE_GI_LEVEL, GI_LEVEL_1);
        if(level.equals(GI_LEVEL_1)){
            level = GI_LEVEL_2;
        } else {
            level = GI_LEVEL_1;
        }
        pref.edit().putString(CHOOSE_GI_LEVEL, level).apply();
    }

    public static String getLevel(){
        SharedPreferences pref = BaseApplication.getInstance().getSharedPreferences("user_setting", Context.MODE_PRIVATE);
        String level = pref.getString(CHOOSE_GI_LEVEL, GI_LEVEL_1);
        return level;
    }
    public static boolean getGiLevel1(){
        SharedPreferences pref = BaseApplication.getInstance().getSharedPreferences("user_setting", Context.MODE_PRIVATE);
        String level = pref.getString(CHOOSE_GI_LEVEL, GI_LEVEL_1);
        return level.equals(GI_LEVEL_1);
    }

    public static boolean getGiLevel2(){
        SharedPreferences pref = BaseApplication.getInstance().getSharedPreferences("user_setting", Context.MODE_PRIVATE);
        String level = pref.getString(CHOOSE_GI_LEVEL, GI_LEVEL_1);
        return !level.equals(GI_LEVEL_1);
    }

}
