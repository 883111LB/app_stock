package com.cvicse.stock.common.Setting;

import android.content.Context;
import android.content.SharedPreferences;

import com.cvicse.base.ui.BaseApplication;

/** F10数据源
 * Created by tang_xqing on 2017/9/21.
 */

public class DataSourceSetting {

    private static final String F10_DATASOURCE = "f10_datasource";

    private static final String DATASOURCE_G = "g";  // 港澳
    private static final String DATASOURCE_D = "d";  // 财会

    public static void setDataSource(){
        SharedPreferences sharedPreferences = BaseApplication.getInstance().getSharedPreferences("user_setting", Context.MODE_PRIVATE);
        String dataSource = sharedPreferences.getString(F10_DATASOURCE, DATASOURCE_G);
        if( DATASOURCE_G.equals(dataSource) ){
            dataSource = DATASOURCE_D;
        }else{
            dataSource = DATASOURCE_G;
        }
        sharedPreferences.edit().putString(F10_DATASOURCE,dataSource).apply();
    }

    public static boolean isDataSourceG(){
        String dataSource = getDataSource();
        return DATASOURCE_G.equals(dataSource);
    }

    public static boolean isDataSourceD(){
        String dataSource = getDataSource();
        return DATASOURCE_D.equals(dataSource);
    }

    public static String getDataSource(){
        SharedPreferences sharedPreferences = BaseApplication.getInstance().getSharedPreferences("user_setting", Context.MODE_PRIVATE);
        String dataSource = sharedPreferences.getString(F10_DATASOURCE, DATASOURCE_G);
        return  dataSource;
    }
}
