package com.cvicse.stock.common.Setting;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Pair;

import com.cvicse.base.ui.BaseApplication;
import com.cvicse.base.utils.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * 推送时间设置
 * Created by liu_zlu on 2017/3/22 09:09
 */
public class PushSetting {

    static SharedPreferences pref = BaseApplication.getInstance().getSharedPreferences("user_setting", Context.MODE_PRIVATE);
    private static final String KEY_PUSH_TIME = "pushtime";

    private static final String SEPARATOR = "e_e";

    public static void setPushTime(String date){
        pairs.clear();
        pref.edit().putString(KEY_PUSH_TIME,date).apply();
    }
    public static void setPushTime(ArrayList<String> dateList){
        pairs.clear();
        if(dateList == null || dateList.size() == 0){
            pref.edit().putString(KEY_PUSH_TIME,"").apply();
            return;
        }
        StringBuffer stringBuffer = new StringBuffer(dateList.get(0));
        for(int i = 1;i<dateList.size();i++){
            stringBuffer.append(SEPARATOR);
            stringBuffer.append(dateList.get(i));
        }
        pref.edit().putString(KEY_PUSH_TIME,stringBuffer.toString()).apply();
    }
    public static String getPushTime(){
        pairs.clear();
        return pref.getString(KEY_PUSH_TIME,"");
    }

    public static ArrayList<String> getPushTimeList(){
        String date = pref.getString(KEY_PUSH_TIME,"");
        if(StringUtils.isEmpty(date)){
            return new ArrayList<>();
        }
        String[] dateArray = date.split(SEPARATOR);
        if(dateArray == null || dateArray.length == 0){
            return new ArrayList<>();
        }
        ArrayList<String> dateList = new ArrayList<>();
        for(String temp:dateArray){
            dateList.add(temp);
        }
        return dateList;
    }

    public static String[] getPushTimeArray(){
        String date = pref.getString(KEY_PUSH_TIME,"");
        if(StringUtils.isEmpty(date)){
            return null;
        }
        String[] dateArray = date.split(SEPARATOR);
        return dateArray;
    }

    public static boolean isContainNow(){
        return contains(System.currentTimeMillis());
    }

    private static boolean contains(long time){
        if(pairs != null && pairs.size() > 0){
            for(Pair<Integer,Integer> pair: pairs){
                if(time > pair.first && time < pair.second){
                    return true;
                }
            }
            return false;
        }
        String date = pref.getString(KEY_PUSH_TIME,"");
        if(StringUtils.isEmpty(date)){
            return true;
        }
        String[] dateArray = date.split(SEPARATOR);
        if(dateArray == null || dateArray.length == 0){
            return true;
        }
        for(String dateStr:dateArray){
            if(isContain(dateStr,time)){
                return true;
            }
            continue;
        }
        return false;
    }
    private static ArrayList<Pair<Integer,Integer>> pairs = new ArrayList<>();
    private static boolean isContain(String str,long time){
        String[] temp = str.split("--");
        long begin = convartTime(temp[0]);
        long end = convartTime(temp[1]);
        pairs.add(new Pair(begin,end));
        if(time > begin && time < end){
            return true;
        }
        return false;
    }

    static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
    private static long convartTime(String time){
        long longTime = -1;
        try {
            Date date =simpleDateFormat.parse(time);
            longTime = date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return longTime;
    }
}
