package com.cvicse.stock.chart.utils;

import android.text.TextUtils;

import com.cvicse.stock.chart.data.TimezoneEntity;
import com.mitake.core.MarketInfo;
import com.mitake.core.MarketInfoItem;
import com.mitake.core.QuoteItem;
import com.mitake.core.bean.exchange.TimeZone;
import com.mitake.core.response.ChartResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by liu_zlu on 2017/3/31 09:24
 */
public class TimezoneUtils {
    private static Map<String,TimezoneEntity> maps = new HashMap<>();
    public static TimezoneEntity sTimezoneEntity;
    private static ChartResponse mChartResponse;
    private static QuoteItem mQuoteItem;

    /**
     * 大商所、郑商所、全球、外汇相关处理方法
     * @param chartResponse
     * @param quoteItem
     */
    public static TimezoneEntity setTimezoneEntity(ChartResponse chartResponse,QuoteItem quoteItem){
        if( null == chartResponse || null == quoteItem ){
            return null;
        }
        mChartResponse = chartResponse;
        mQuoteItem = quoteItem;

        TimezoneEntity timezoneEntity = maps.get(quoteItem.id);
        if( null != timezoneEntity ){
            return  sTimezoneEntity = timezoneEntity;
        }

        if(!(quoteItem.id.contains("dce") || quoteItem.id.contains("zce")|| quoteItem.id.contains("shfe")||quoteItem.id.contains("ine") || quoteItem.id.contains("gi") || quoteItem.id.contains("fe"))){
            return getSHSZTimeEntity(chartResponse,quoteItem);
        }
        timezoneEntity = new TimezoneEntity();
        timezoneEntity.dayList = chartResponse.dayList;
        timezoneEntity.tradingTime = chartResponse.tradingTime;
        timezoneEntity.totalTimes = chartResponse.totalTimes;

        maps.put(quoteItem.id,timezoneEntity);
        return sTimezoneEntity = timezoneEntity;
    }

    /**
     * 规范其他市场交易时间段 与大商所一致
     * @param chartResponse
     * @param quoteItem
     */
    public static TimezoneEntity getSHSZTimeEntity(ChartResponse chartResponse, QuoteItem quoteItem){
        String market = quoteItem.market;
        String subtype = quoteItem.subtype;
        if( "tf".equals(subtype) ){   // 国债期货与其他市场的开盘时间、休市时间不一样
            market = market+subtype;
        }
        if("sh".equals(market)&&"1311".equals(subtype)){  //上证债券sh1311的结束时间不一样
            market=market+subtype;
        }

        TimezoneEntity timezoneEntity = null;
        MarketInfoItem infoItem = MarketInfo.get(market);   // 获取市场资讯
        if( null!=infoItem  &&  null!= infoItem.timezone) {
            String[][] OPCLTIME = infoItem.timezone;
            timezoneEntity = new TimezoneEntity();
            timezoneEntity.dayList = chartResponse.dayList;

            /************* 交易时间段 ***************/
            Map<String, List<TimeZone>> tradingTime = new HashMap<>();
            for (int i = 0; i < chartResponse.dayList.size(); i++) {
                String s = chartResponse.dayList.get(i);    // 日期
                if( TextUtils.isEmpty(s) ){
                    continue;
                }
                List<TimeZone> timeZoneList = new ArrayList<>();
                TimeZone timeZone = new TimeZone();
                timeZone.openTime = s+OPCLTIME[0][0];  //201808020930
                timeZone.closeTime = s+OPCLTIME[0][1];
                timeZone.setOpenHhMm(OPCLTIME[0][0] );  // 0930
                timeZone.setCloseHhMm(OPCLTIME[0][1] );
                timeZoneList.add(timeZone);

                timeZone = new TimeZone();
                timeZone.openTime = s+OPCLTIME[1][0];  //201808020930
                timeZone.closeTime = s+OPCLTIME[1][1];
                timeZone.setOpenHhMm(OPCLTIME[1][0] );  // 0930
                timeZone.setCloseHhMm(OPCLTIME[1][1] );
                timeZoneList.add(timeZone);

                tradingTime.put(s,timeZoneList);
            }
            timezoneEntity.tradingTime = tradingTime;
            timezoneEntity.totalTimes = chartResponse.totalTimes;
            maps.put(quoteItem.id,timezoneEntity);
        }
        return sTimezoneEntity = timezoneEntity;
    }

    public static TimezoneEntity getEntity(QuoteItem quoteItem){
//      return getEntity(quoteItem.market);  // old
        return getEntity3(quoteItem);  // new
    }

    //    public static TimezoneEntity getEntity(String market){  // old
    public static TimezoneEntity getEntity3(QuoteItem quoteItem){  // new
        String market = quoteItem.market;
        String subtype = quoteItem.subtype;
        if( "tf".equals(subtype) ){   // 国债期货与其他市场的开盘时间、休市时间不一样
            market = market+subtype;
        }
        if("sh".equals(market)&&"1311".equals(subtype)){  //上证债券sh1311的结束时间不一样
            market=market+subtype;
        }

        TimezoneEntity timezoneEntity =  maps.get(market);
        if( null!=timezoneEntity ){
            return timezoneEntity;
        }

        MarketInfoItem infoItem = MarketInfo.get(market);
        String[][] OPCLTIME = null;
        if(infoItem != null && infoItem.timezone != null) {
            OPCLTIME = infoItem.timezone;

            timezoneEntity = new TimezoneEntity();
            timezoneEntity.openingTimeONE = Stringinsert(OPCLTIME[0][0], ":", 2);
            timezoneEntity.CloseTimeONE = Stringinsert(OPCLTIME[0][1], ":", 2);
            timezoneEntity.openingTimeTWO = Stringinsert(OPCLTIME[1][0], ":", 2);

            timezoneEntity.CloseTimeTWO = Stringinsert(OPCLTIME[1][1], ":", 2);

            timezoneEntity.openTime1 = Integer.parseInt(OPCLTIME[0][0]);
            timezoneEntity.closeTime1 = Integer.parseInt(OPCLTIME[0][1]);
            timezoneEntity.openTime2 = Integer.parseInt(OPCLTIME[1][0]);
            timezoneEntity.closeTime2 = Integer.parseInt(OPCLTIME[1][1]);
            timezoneEntity.HughPlateTime = (timezoneEntity.openTime2/100 - timezoneEntity.closeTime1/100)*60+(timezoneEntity.openTime2%100 - timezoneEntity.closeTime1%100);
            timezoneEntity.FirstPlateTime = (timezoneEntity.closeTime1/100 - timezoneEntity.openTime1/100)*60+(timezoneEntity.closeTime1%100 - timezoneEntity.openTime1%100) ;
            ++timezoneEntity.FirstPlateTime;
            timezoneEntity.SecondPlateTime = (timezoneEntity.closeTime2/100 - timezoneEntity.openTime2/100)*60+(timezoneEntity.closeTime2%100 - timezoneEntity.openTime2%100);
            ++timezoneEntity.SecondPlateTime;
            timezoneEntity.TradeTime =timezoneEntity.FirstPlateTime + timezoneEntity.SecondPlateTime;

            timezoneEntity.openHour1 = timezoneEntity.openTime1/100;
            timezoneEntity.openM1 = timezoneEntity.openTime1%100;
            timezoneEntity.openHour2 = timezoneEntity.openTime2/100;
            timezoneEntity.openM2 = timezoneEntity.openTime2%100;
            maps.put(market,timezoneEntity);
        }
        return timezoneEntity;
    }

    public static String Stringinsert(String a, String b, int t) {
        String aa = a.substring(0, t);
        String bb = a.substring(t, a.length());
        return aa + b + bb;
    }

    public static void removeData(String stockID){
        if(!TextUtils.isEmpty(stockID)){
            sTimezoneEntity = null;
            mChartResponse = null;
            mQuoteItem = null;
            maps.remove(stockID);
        }
    }
}
