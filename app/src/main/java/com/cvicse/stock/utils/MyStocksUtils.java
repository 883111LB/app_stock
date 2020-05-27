package com.cvicse.stock.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.cvicse.base.ui.BaseApplication;

/**
 * 自选帮助类
 * Created by liu_zlu on 2017/1/11 14:16
 */
public class MyStocksUtils {

    private static  final String SELECT_KEY = "MyStocks_SELECT_CODE";
    private static  final String SELECT_NAME = "MyStocks_SELECT_NAME";
    static SharedPreferences sharedPreferences = BaseApplication.getInstance()
            .getSharedPreferences("MyStocks_SELECT", Context.MODE_PRIVATE);

    /**
     * 保存自选的股票
     * @param code 股票名
     * @param name 股票代码
     */
    public static void saveSelect(String code,String name){
        String selectCode = sharedPreferences.getString(SELECT_KEY,"--");
        String selectName = sharedPreferences.getString(SELECT_NAME,"--");
        if("--".equals(selectCode)){
            sharedPreferences.edit().putString(SELECT_KEY,("".equals(code)?"--":code)).apply();
            sharedPreferences.edit().putString(SELECT_NAME,("".equals(name)?"--":name)).apply();
            return;
        }

        String[] codes = selectCode.split(",");

        for(String str :codes){
            if(str.equals(code)){
                return;
            }
        }
        sharedPreferences.edit().putString(SELECT_KEY,("".equals(code)?"--":code)+","+selectCode).apply();
        sharedPreferences.edit().putString(SELECT_NAME,("".equals(name)?"--":name)+","+selectName).apply();
    }
    /**
     * 获取所有股票名 例如： 12345.sh,2333545.hk,3434354.sz
     * @return
     */
    public static String getSelectCode(){
        String selectName = sharedPreferences.getString(SELECT_KEY,"--");
        if("--".equals(selectName )){
            return null;
        }
        return selectName;
    }
    /**
     * 获取所有股票名 例如： {"12345.sh","2333545.hk","3434354.sz"}
     * @return
     */
    public static String[] getSelectCodes(){
        //默认是空字符串
        String selectName = sharedPreferences.getString(SELECT_KEY,"--");

        if("--".equals(selectName)){
            return null;
        }
        String[] strings = selectName.split(",");

        return strings;
    }

    /**
     * 获取所有股票名 例如： 恒生指数,上证指数,浦东银行
     * @return
     */
    public static String getSelectName(){
        String selectCode = sharedPreferences.getString(SELECT_NAME,"--");
        return selectCode;
    }

    /**
     * 删除自选的股票
     * @param code 股票名
     * @param name 股票代码
     */
    public static void removeSelect(String code,String name){
        String selectCode = sharedPreferences.getString(SELECT_KEY,null);
        String selectName = sharedPreferences.getString(SELECT_NAME,null);
        if(selectCode == null){
            sharedPreferences.edit().putString(SELECT_KEY,code).apply();
            sharedPreferences.edit().putString(SELECT_NAME,name).apply();
            return;
        }
        String[] codes = selectCode.split(",");
        String[] names = selectName.split(",");

        StringBuilder sbCode = new StringBuilder();
        StringBuilder sbName = new StringBuilder();

        for(int i=0;i<codes.length;i++){
            if(!codes[i].equals(code)){
                sbCode.append(codes[i]).append(",");
                sbName.append(names[i]).append(",");
            }
        }
        sharedPreferences.edit().putString(SELECT_KEY,sbCode.length()>1?sbCode.substring(0,sbCode.length()-1).toString():"--").apply();
        sharedPreferences.edit().putString(SELECT_NAME,sbName.length()>1?sbName.substring(0,sbName.length()-1).toString():"--").apply();
    }

    /**
     * 判断是否包已经自选该股票
     * @param stockId 股票代码
     * @return true 表示已经自选该股票 false 表示没有
     */
    public static boolean contains(String stockId){
        String selectCode = sharedPreferences.getString(SELECT_KEY,"--");
        if(stockId == null){
            return false;
        }

        String[] codes = selectCode.split(",");

        for(String str :codes){
            if(str.equals(stockId)){
                return true;
            }
        }
        return false;
    }

    /**
     * 移除所有股票
     */
    public static void removeAll(){
        sharedPreferences.edit().clear().apply();
    }
}
