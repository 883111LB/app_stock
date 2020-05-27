package com.cvicse.stock.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.cvicse.base.ui.BaseApplication;

import java.util.ArrayList;

/**
 * 存储最近浏览过的页面
 *
 * Created by ding_syi on 17-1-19.
 */
public class MyBrowseUtils {

    private static  final String SELECT_KEY = "MyStocks_browse";

    static SharedPreferences sharedPreferences = BaseApplication.getInstance()
            .getSharedPreferences("MyStocks_browse", Context.MODE_PRIVATE);

    /**
     * 记录最近浏览
     * @param code
     */
    public static void saveBrowse(String code){
        String selectCode = sharedPreferences.getString(SELECT_KEY,"");
        //本地sp文件为空，直接存进去
        if("".equals(selectCode)){
            sharedPreferences.edit().putString(SELECT_KEY,code).apply();
            return;
        }

        ArrayList listCode = new ArrayList();
        //只有一个code时，无",",即不包含分隔符，返回数组只包含一个元素(该字符串本身)
        String[] codes = selectCode.split(",");

        for(int i=0;i<codes.length;i++){
            listCode.add(codes[i]);
        }

        //code不存在，remove返回false
        listCode.remove(code);
        listCode.add(0,code);
        int length = listCode.size();
        //最多200条浏览记录
        length = Math.min(length,200);
        StringBuffer stringBuffer = new StringBuffer();
        for(int i=0;i< length;i++){
            if(i!=listCode.size()-1){
                stringBuffer.append(listCode.get(i)).append(",");
            }else{
                stringBuffer.append(listCode.get(i));
            }

        }
        sharedPreferences.edit().putString(SELECT_KEY,stringBuffer.toString()).apply();
    }

    public static String getSelectCode(){
        String selectName = sharedPreferences.getString(SELECT_KEY,"");
        return selectName;
    }
}
