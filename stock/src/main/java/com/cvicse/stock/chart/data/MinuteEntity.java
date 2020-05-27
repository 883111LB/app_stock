package com.cvicse.stock.chart.data;

/**
 * 分时处理数据
 * Created by liu_zlu on 2017/1/22 13:23
 */
public class MinuteEntity extends MakerEntity{
    public float cjprice;                      //成交价
    public String cjpriceStr;                      //成交价
    public float volume = 0;                        //成交量
    public float averagePrice = 0;          //总的成交金额除以成交股数就是这个时间的平均价
    public String averagePriceStr = "";          //总的成交金额除以成交股数就是这个时间的平均价

    public String ipovStr;  // 基金净值
    public float ipov;
    public String ipovPreStr;  // 基金净值参考价
    public float ipovPre;

    public int time = 0;
    public String datetime;   // 交易时间 yyyyMMddHHmmss

    public String timeStr = ""; // 时分 HH:mm
    public float open;
    public float high;
    public float low;
    public float close;

    public float ddx;
    public float ddy;
    public float ddz;
    public float bbd;
    public float ratioBs ;  // 单数比
    public float largeMoneyInflow;  // 超大单净流入
    public float bigMoneyInflow;   // 大单净流入
    public float midMoneyInflow;  // 中单净流入
    public float smallMoneyInflow;   // 小单净流入
    public float largeTradeNum ;  // 超大单成交单数
    public float bigTradeNum ; // 大单成交单数
    public float midTradeNum ; // 中单成交单数
    public float smallTradeNum ; // 小单成交单数
    public float bigNetVolume;//大单净量
    public float volRatio; //量比

    public String getDatetime() {
        return datetime;
    }

    public float getOpenPrice() {
        return open;
    }

    public float getClosePrice() {
        return close;
    }

    public float getDdx() {
        return ddx;
    }

}
