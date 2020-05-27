package com.cvicse.stock.chart.data;

import com.mitake.core.bean.exchange.TimeZone;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by liu_zlu on 2017/3/31 09:26
 */
public class TimezoneEntity {

    public int HughPlateTime;

    public int FirstPlateTime;//早上具体时间间隔，按分钟

    public int TradeTime;  //一天具体时间间隔，按分钟

    public int SecondPlateTime;// 下午具体时间间隔，按分钟

    public String openingTimeONE;// 早上开盘时间

    public String CloseTimeONE; //早上关闭时间

    public String openingTimeTWO;//下午开盘时间

    public String CloseTimeTWO; // 下午关闭时间

    //比如9:30转换为930 ，11:30转换为1130
    public int openTime1 = 0;
    public int closeTime1 = 0;
    public int openTime2 = 0;
    public int closeTime2 = 0;

    public int openHour1 = 0;  // 早上开盘时间小时部分

    public int openM1 = 0; // 早上开盘时间分钟部分

    public int openHour2 = 0; //下午开盘时间小时部分

    public int openM2 = 0;   // 下午开盘时间分钟部分

    public ArrayList<String> dayList;  // 日期列表

    public Map<String, List<TimeZone>> tradingTime;   // 交易时间段 key- 日期  value-交易时间段

    public int totalTimes;  // 绘图所需的总根数

}
