package com.cvicse.stock.chart.formatter;

import android.util.Log;

import com.cvicse.stock.chart.data.TimezoneEntity;
import com.mitake.core.MarketInfo;
import com.mitake.core.MarketInfoItem;
import com.mitake.core.QuoteItem;
import com.stock.config.FillConfig;

import java.text.DecimalFormat;

/**  数据格式转换
 * Created by tang_xqing on 2018/3/21.
 */
public class CommonValueFormatter {

    public static String formatPercent(float value){
        if( !isNorm(String.valueOf(value)) ){
            return FillConfig.DEFALUT;
        }
        DecimalFormat mFormat = new DecimalFormat("0.00");
        if(value > 0){
            return "+"+mFormat.format(value)+"%";
        } else if(value < 0){
            return mFormat.format(value)+"%";
        }
        return mFormat.format(value)+"%";
    }

    public static String formatValue(QuoteItem quoteItem,float value){
        if( !isNorm(String.valueOf(value)) ){
            return FillConfig.DEFALUT;
        }
        return formatValue(quoteItem.market,quoteItem.subtype,value);
    }

    public static String formatValue(QuoteItem quoteItem,String value){
        if( !isNorm(value) ){
            return FillConfig.DEFALUT;
        }
        return formatValue(quoteItem.market,quoteItem.subtype,value);
    }

    public static String formatValue(String market,String subtype,String value){
        if( !isNorm(String.valueOf(value)) ){
            return FillConfig.DEFALUT;
        }
        return formatValue(market,subtype,Float.valueOf(value));
    }

    public static String formatValue(String market,String subtype,float value){
        if( !isNorm(String.valueOf(value)) ){
            return FillConfig.DEFALUT;
        }
        DecimalFormat mFormat = new DecimalFormat("0.00");
        DecimalFormat mFormatTh = new DecimalFormat("0.000");
        DecimalFormat mFormatFo = new DecimalFormat("0.0000");

//        if(value < 0.01 || subtype.equals("3002")){
        if(null != subtype && subtype.equals("3002")){
            return mFormatFo.format(value);
        }
        if(null != subtype && (subtype.equals("1100") || subtype.equals("tf") || market.contains("hk"))){
            return mFormatTh.format(value);
        }
        return mFormat.format(value);
    }

    public static String autoComplete(int position,TimezoneEntity timezoneEntity){
        int currentH = 0;
        int currentM = 0;
        if(position < timezoneEntity.FirstPlateTime){
            currentH = position/60 + timezoneEntity.openHour1;
            if((position%60 + timezoneEntity.openM1) >= 60){
                currentH++;
                currentM = position%60 + timezoneEntity.openM1 - 60;
            } else {
                currentM = position%60 + timezoneEntity.openM1;
            }
        } else {
            currentH = (position-timezoneEntity.FirstPlateTime)/60 + timezoneEntity.openHour2;
            currentM = (position-timezoneEntity.FirstPlateTime)%60 + timezoneEntity.openM2;
            if(currentM >= 60){
                currentH++;
                currentM = currentM - 60;
            }
        }
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(currentH);
        stringBuffer.append(":");
        if(currentM < 10){
            stringBuffer.append("0");
        }
        stringBuffer.append(currentM);
        Log.e("currentM",stringBuffer.toString());
        return stringBuffer.toString();
    }

    public static boolean isNorm(String text){
        if(null == text || "null".equals(text) || "-".equals(text) ||
                "--".equals(text) || "一".equals(text) || "".equals(text) || " ".equals(text) ){
            return false;
        }
        return true;
    }

    public static int getDriftLen(String market,String subtype){
        int driftLen = 0;
        int suffixRetainLen = 0;
        String src = "";
        MarketInfoItem mainMarket = MarketInfo.get(market);
        if(mainMarket != null) {
            driftLen = mainMarket.driftLen;
            suffixRetainLen = mainMarket.suffixRetainLen;
        }

        MarketInfoItem subMarket = MarketInfo.get(market + subtype);
        if(subMarket != null) {
            if(subMarket.driftLen > 0) {
                driftLen = subMarket.driftLen;
            }

            if(subMarket.suffixRetainLen > 0) {
                suffixRetainLen = subMarket.suffixRetainLen;
            }
        }
        return driftLen;
    }
}
