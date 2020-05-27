package com.stock.config;

import com.cvicse.base.exception.BaseException;
import com.cvicse.base.utils.SPUtils;
import com.cvicse.base.utils.StringUtils;

/**
 * 系统设置
 * Created by liu_zlu on 2017/1/3 08:54
 */
public class SystemSetting {
    static SPUtils spUtils = new SPUtils("");

    private static final String LEVEL_KEY = "level_key";

    public static final String LEVEL_1 = "1";

    public static final String LEVEL_2 = "2";

    private static String current_level = "";

    /**
     * 行情刷新间隔
     */
    private static final String REFRESH_TIME_KEY = "refresh_time_key";
    public static int refresh_time = 0;
    /**
     * k线复权设置
     */
    private static final String KLINE_ISSUE_KEY = "kline_issue_key";
    /**
     * 不复权
     */
    public static final int NO_ISSUE = 0;
    /**
     * 前复权
     */
    public static final int PRE_ISSUE = 1;
    /**
     * 后复权
     */
    public static final int AFT_ISSUE = 2;

    /**
     * k线指数设置
     */
    private static final String KLINE_INDEX_KEY = "kline_issue_key";
    /**
     * 成交量
     */
    public static final int INDEX_VOLUME = 0;
    /**
     * MACD
     */
    public static final int INDEX_MACD = 1;
    /**
     * DMI
     */
    public static final int INDEX_DMI = 2;
    /**
     * WR
     */
    public static final int INDEX_WR = 3;
    /**
     * BOLL
     */
    public static final int INDEX_BOLL = 4;
    /**
     * KDJ
     */
    public static final int INDEX_KDJ = 5;
    /**
     * OBV
     */
    public static final int INDEX_OBV = 6;
    /**
     * RSI
     */
    public static final int INDEX_RSI = 7;
    /**
     * SAR
     */
    public static final int INDEX_SAR = 8;


    /**
     *  获取请求等级
     * @return 返回请求等级，默认返回level_1
     */
    public static String getLevel(){
        if(!StringUtils.isEmpty(current_level)){
            return current_level;
        }
        current_level = spUtils.getString("level",LEVEL_1);
        return current_level;
    }

    /**
     * 设置请求等级
     * @param level 请求等级只能为 level_1、level_2
     */
    public static void setLevel(String level){
        if(level == null ||(!level.equals(LEVEL_1) && !level.equals(LEVEL_2))){
            return;
        }
        spUtils.putString(LEVEL_KEY,level);
        current_level = level;
    }

    /**
     * 获取行情刷新时间间隔
     * @return
     */
    public static int getRefreshTime(){
        return spUtils.getInt(REFRESH_TIME_KEY);
    }

    /**
     * 设置行情刷新时间间隔
     * @param time 0 表示不刷新  >0 表示刷新
     */
    public static void setRefreshTime(int time){
        if(time < 0){
            throw new BaseException("请输入正确的行情刷新时间");
        }
        spUtils.putInt(REFRESH_TIME_KEY,time);
    }

    /**
     * 获取复权类型
     * @return 0 不复权 1 前复权 2 后复权
     */
    public static int getKineIssue(){
        return spUtils.getInt(KLINE_ISSUE_KEY,0);
    }

    /**
     * 设置复权类型
     * @return
     */
    public static void setKineIssue(int type){
        if(type != NO_ISSUE && type != PRE_ISSUE && type != AFT_ISSUE){
            throw new BaseException("请输入正确的K线图复权类型");
        }
         spUtils.putInt(KLINE_ISSUE_KEY,type);
    }

    /**
     * 获取指数类型
     * @return
     */
    public static int getKineIndex(){
        return spUtils.getInt(KLINE_ISSUE_KEY,0);
    }

    /**
     * 设置指数类型
     * @return
     */
    public static void setKineIndex(int type){
        if(type < 0 || type > 8){
            throw new BaseException("请输入正确的K线图指数类型");
        }
        spUtils.putInt(KLINE_ISSUE_KEY,type);
    }
}
