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
    private static final String OLL1_LEVEL = "oll1_level";
    private static final String OLSHL1_LEVEL = "olshl1_level";
    private static final String OLSZL1_LEVEL = "olszl1_level";
    private static final String OLSHL2_LEVEL = "olshl2_level";
    private static final String OLSZL2_LEVEL = "olszl2_level";

    private static final String LEVEL_1 = Permissions.LEVEL_1;
    private static final String LEVEL_2 = Permissions.LEVEL_2;
    private static final String OLL1 = Permissions.OL_LEVEL_1;
    private static final String OLSHL1 = Permissions.OL_SH_LEVEL_1;
    private static final String OLSZL1 = Permissions.OL_SZ_LEVEL_1;
    private static final String OLSHL2 = Permissions.OL_SH_LEVEL_2;
    private static final String OLSZL2 = Permissions.OL_SZ_LEVEL_2;

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
    // oll1
    public static boolean isOLL1(){
        SharedPreferences pref = BaseApplication.getInstance().getSharedPreferences("user_setting", Context.MODE_PRIVATE);
        String level = pref.getString(OLL1_LEVEL,"");
        return level.equals(OLL1);
    }
    public static String getOLL1(){
        SharedPreferences pref = BaseApplication.getInstance().getSharedPreferences("user_setting", Context.MODE_PRIVATE);
        String level = pref.getString(OLL1_LEVEL,"");
        return level;
    }
    public static void setOLL1(){
        SharedPreferences pref = BaseApplication.getInstance().getSharedPreferences("user_setting", Context.MODE_PRIVATE);
        pref.edit().putString(OLL1_LEVEL, OLL1).apply();
    }
    public static void removeOLL1(){
        SharedPreferences pref = BaseApplication.getInstance().getSharedPreferences("user_setting", Context.MODE_PRIVATE);
        pref.edit().putString(OLL1_LEVEL, "").apply();
    }
    // olshl1
    public static boolean isOLSHL1(){
        SharedPreferences pref = BaseApplication.getInstance().getSharedPreferences("user_setting", Context.MODE_PRIVATE);
        String level = pref.getString(OLSHL1_LEVEL,"");
        return level.equals(OLSHL1);
    }
    public static String getOLSHL1(){
        SharedPreferences pref = BaseApplication.getInstance().getSharedPreferences("user_setting", Context.MODE_PRIVATE);
        String level = pref.getString(OLSHL1_LEVEL,"");
        return level;
    }
    public static void setOLSHL1(){
        SharedPreferences pref = BaseApplication.getInstance().getSharedPreferences("user_setting", Context.MODE_PRIVATE);
        pref.edit().putString(OLSHL1_LEVEL, OLSHL1).apply();
    }
    public static void removeOLSHL1(){
        SharedPreferences pref = BaseApplication.getInstance().getSharedPreferences("user_setting", Context.MODE_PRIVATE);
        pref.edit().putString(OLSHL1_LEVEL, "").apply();
    }
    // OLSZL1
    public static boolean isOLSZL1(){
        SharedPreferences pref = BaseApplication.getInstance().getSharedPreferences("user_setting", Context.MODE_PRIVATE);
        String level = pref.getString(OLSZL1_LEVEL,"");
        return level.equals(OLSZL1);
    }
    public static String getOLSZL1(){
        SharedPreferences pref = BaseApplication.getInstance().getSharedPreferences("user_setting", Context.MODE_PRIVATE);
        String level = pref.getString(OLSZL1_LEVEL,"");
        return level;
    }
    public static void setOLSZL1(){
        SharedPreferences pref = BaseApplication.getInstance().getSharedPreferences("user_setting", Context.MODE_PRIVATE);
        pref.edit().putString(OLSZL1_LEVEL, OLSZL1).apply();
    }
    public static void removeOLSZL1(){
        SharedPreferences pref = BaseApplication.getInstance().getSharedPreferences("user_setting", Context.MODE_PRIVATE);
        pref.edit().putString(OLSZL1_LEVEL, "").apply();
    }

    public static boolean isOLSHL2(){
        SharedPreferences pref = BaseApplication.getInstance().getSharedPreferences("user_setting", Context.MODE_PRIVATE);
        String level = pref.getString(OLSHL2_LEVEL,"");
        return level.equals(OLSHL2);
    }
    public static String getOLSHL2(){
        SharedPreferences pref = BaseApplication.getInstance().getSharedPreferences("user_setting", Context.MODE_PRIVATE);
        String level = pref.getString(OLSHL2_LEVEL,"");
        return level;
    }
    public static void setOLSHL2(){
        SharedPreferences pref = BaseApplication.getInstance().getSharedPreferences("user_setting", Context.MODE_PRIVATE);
        pref.edit().putString(OLSHL2_LEVEL, OLSHL2).apply();
    }
    public static void removeOLSHL2(){
        SharedPreferences pref = BaseApplication.getInstance().getSharedPreferences("user_setting", Context.MODE_PRIVATE);
        pref.edit().putString(OLSHL2_LEVEL, "").apply();
    }
    public static boolean isOLSZL2(){
        SharedPreferences pref = BaseApplication.getInstance().getSharedPreferences("user_setting", Context.MODE_PRIVATE);
        String level = pref.getString(OLSZL2_LEVEL,"");
        return level.equals(OLSZL2);
    }
    public static String getOLSZL2(){
        SharedPreferences pref = BaseApplication.getInstance().getSharedPreferences("user_setting", Context.MODE_PRIVATE);
        String level = pref.getString(OLSZL2_LEVEL,"");
        return level;
    }
    public static void setOLSZL2(){
        SharedPreferences pref = BaseApplication.getInstance().getSharedPreferences("user_setting", Context.MODE_PRIVATE);
        pref.edit().putString(OLSZL2_LEVEL, OLSZL2).apply();
    }
    public static void removeOLSZL2(){
        SharedPreferences pref = BaseApplication.getInstance().getSharedPreferences("user_setting", Context.MODE_PRIVATE);
        pref.edit().putString(OLSZL2_LEVEL, "").apply();
    }
}
