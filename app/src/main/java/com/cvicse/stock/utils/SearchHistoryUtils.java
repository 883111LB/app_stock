package com.cvicse.stock.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.cvicse.base.ui.BaseApplication;
import com.cvicse.base.utils.StringUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mitake.core.SearchResultItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liu_zlu on 2017/3/15 19:48
 */
public class SearchHistoryUtils {

    private static  final String SELECT_KEY = "History_SELECT_CODE";
    static SharedPreferences sharedPreferences = BaseApplication.getInstance().getSharedPreferences("Search_History", Context.MODE_PRIVATE);

    public static void saveHistory(SearchResultItem searchResultItem){
        String searchHistory = sharedPreferences.getString(SELECT_KEY,"");
        Gson gson = new Gson();
        if(StringUtils.isEmpty(searchHistory)){
            List<SearchResultItem> list = new ArrayList<>();
            list.add(searchResultItem);
            sharedPreferences.edit().putString(SELECT_KEY,gson.toJson(list)).apply();
            return;
        }

        List<SearchResultItem> list = gson.fromJson(searchHistory,new TypeToken<List<SearchResultItem>>() {
        }.getType());
        int length = list.size();
        SearchResultItem tempItem;
        for(int i = 0;i < length ;i++){
            tempItem = list.get(i);
            if(tempItem.stockID.equals(searchResultItem.stockID) ){
                list.remove(i);
                break;
            }
        }
        length = list.size();
        if(length == 10){
            list.remove(9);
        }
        list.add(0,searchResultItem);
        sharedPreferences.edit().putString(SELECT_KEY,gson.toJson(list)).apply();
    }

    public static List<SearchResultItem> getHistoryCode(){
        Gson gson = new Gson();
        String selectName = sharedPreferences.getString(SELECT_KEY,"");
        List<SearchResultItem> list = gson.fromJson(selectName,new TypeToken<List<SearchResultItem>>() {
        }.getType());
        return list;
    }

    public static void removeAll(){
        sharedPreferences.edit().clear().apply();
    }

}
