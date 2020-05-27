package com.cvicse.stock.common.Setting;

import com.cvicse.stock.http.RequestSetting;

/**
 * Created by liu_zlu on 2017/3/14 15:25
 */
public class Setting {

    public static void toggleLevel(){
        LevelSetting.toggleLevel();
    }

    public static String getLevel(){
        return LevelSetting.getLevel();
    }

    public static boolean isLevel1(){
        return LevelSetting.isLevel1();
    }
    public static boolean isLevel2(){
        return LevelSetting.isLevel2();
    }

    public static boolean isOLL1(){
        return LevelSetting.isOLL1();
    }
    public static String getOLL1(){
        return LevelSetting.getOLL1();
    }
    public static void setOLL1(){
        LevelSetting.setOLL1();
    }
    public static void removeOLL1(){
        LevelSetting.removeOLL1();
    }
    public static boolean isOLSHL1(){
        return LevelSetting.isOLSHL1();
    }
    public static String getOLSHL1(){
        return LevelSetting.getOLSHL1();
    }
    public static void setOLSHL1(){
        LevelSetting.setOLSHL1();
    }
    public static void removeOLSHL1(){
        LevelSetting.removeOLSHL1();
    }
    public static boolean isOLSZL1(){
        return LevelSetting.isOLSZL1();
    }
    public static String getOLSZL1(){
        return LevelSetting.getOLSZL1();
    }
    public static void setOLSZL1(){
        LevelSetting.setOLSZL1();
    }
    public static void removeOLSZL1(){
        LevelSetting.removeOLSZL1();
    }
    public static boolean isOLSHL2(){
        return LevelSetting.isOLSHL2();
    }
    public static String getOLSHL2(){
        return LevelSetting.getOLSHL2();
    }
    public static void setOLSHL2(){
        LevelSetting.setOLSHL2();
    }
    public static void removeOLSHL2(){
        LevelSetting.removeOLSHL2();
    }
    public static boolean isOLSZL2(){
        return LevelSetting.isOLSZL2();
    }
    public static String getOLSZL2(){
        return LevelSetting.getOLSZL2();
    }
    public static void setOLSZL2(){
        LevelSetting.setOLSZL2();
    }
    public static void removeOLSZL2(){
        LevelSetting.removeOLSZL2();
    }

    public static String getSkinType(){
        return SkinSetting.getSkinType();
    }
    public static void toggleSkin(){
        SkinSetting.toggleSkin();
    }
    public static boolean isDay(){
        return SkinSetting.isDay();
    }

    public static boolean isNight(){
        return SkinSetting.isNight();
    }

    public static void setRefreshRate(int rate){
        RequestSetting.setRefreshRate(rate);
    }

    public static int getRefreshRate(){
        return RequestSetting.getRefreshRate();
    }

    public static String getRefreshRateStr(){
        return RequestSetting.getRefreshRateStr();
    }


    public static void setTimeOut(int timeOut){
        RequestSetting.setTimeOut(timeOut);
    }

    public static int getTimeOut(){
        return RequestSetting.getTimeOut();
    }

    // F10 数据源
    public static void setDataSource(){
        DataSourceSetting.setDataSource();
    }
    public static String getDataSource(){
        return DataSourceSetting.getDataSource();
    }
    public static boolean isDataSourceG(){
        return DataSourceSetting.isDataSourceG();
    }
    public static boolean isDataSourceD(){
        return DataSourceSetting.isDataSourceD();
    }

    // 中金所
    public static void toggleCffLevel(){
        CffLevelSetting.toggleCffLevel();
    }

    public static String getCffLevel(){
        return CffLevelSetting.getLevel();
    }

    public static boolean isCffLevel1(){
        return CffLevelSetting.isCffLevel1();
    }
    public static boolean isCffLevel2(){
        return CffLevelSetting.isCffLevel2();
    }

    // 大商所
    public static void toggleDCELevel(){
        DCELevelSetting.toggleDceLevel();
    }

    public static String getDCELevel(){
        return DCELevelSetting.getLevel();
    }

    public static boolean isDceLevel1(){
        return DCELevelSetting.isDceLevel1();
    }
    public static boolean isDceLevel2(){
        return DCELevelSetting.isDceLevel2();
    }

    // 郑商所
    public static void toggleZCELevel(){
        ZCELevelSetting.toggleZceLevel();
    }

    public static String getZCELevel(){
        return ZCELevelSetting.getLevel();
    }

    public static boolean isZceLevel1(){
        return ZCELevelSetting.isZceLevel1();
    }
    public static boolean isZceLevel2(){
        return ZCELevelSetting.isZceLevel2();
    }

    // 上期所
    public static void toggleSHFELevel(){
        SHFELevelSetting.toggleShfeLevel();
    }

    public static String getSHFELevel(){
        return SHFELevelSetting.getLevel();
    }

    public static boolean isShfeLevel1(){
        return SHFELevelSetting.isShfeLevel1();
    }
    public static boolean isShfeLevel2(){
        return SHFELevelSetting.isShfeLevel2();
    }

    //上期所原油
    public static void toggleINELevel(){
        INELevelSetting.toggleIneLevel();
    }
    public static String getINELevel(){
        return INELevelSetting.getLevel();
    }
    public static boolean isIneLevel1(){
        return INELevelSetting.isIneLevel1();
    }
    public static boolean isIneLevel2(){
        return INELevelSetting.isIneLevel2();
    }


    // 全球
    public static void toggleGILevel(){
        GILevelSetting.toggleGiLevel();
    }

    public static String getGILevel(){
        return GILevelSetting.getLevel();
    }

    public static boolean isGILevel1(){
        return GILevelSetting.getGiLevel1();
    }
    public static boolean isGILevel2(){
        return GILevelSetting.getGiLevel2();
    }

    // 外汇
    public static void toggleFELevel(){
        FELevelSetting.toggleFeLevel();
    }

    public static String getFELevel(){
        return FELevelSetting.getLevel();
    }

    public static boolean isFELevel1(){
        return FELevelSetting.getFeLevel1();
    }
    public static boolean isFELevel2(){
        return FELevelSetting.getFeLevel2();
    }

    // 港股权限
    public static void setHKMode(String mode){
        HKLevelSetting.setHKMode(mode);
    }

    public static String getHKMode(){
        return HKLevelSetting.getHKMode();
    }
    // 港股指数权限
    public static void setHKZSMode(String mode){
        HKZSLevelSetting.setHKZSMode(mode);
    }

    public static String getHKZSMode(){
        return HKZSLevelSetting.getHKZSMode();
    }

}
