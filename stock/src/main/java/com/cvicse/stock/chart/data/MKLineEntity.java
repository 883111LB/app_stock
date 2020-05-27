package com.cvicse.stock.chart.data;

/** 走势、K线公共类，用于显示某个时间点的值
 * Created by tang_xqing on 2018/3/23.
 */

public class MKLineEntity {
    public boolean isUp;  // 涨| 跌

    public String date; // 底部展示时间
    public String datetime;  // 成交时间

    public String openPriceS;
    public String highPriceS;
    public String lowPriceS;
    public String closePriceS;
    public String avPriceS;   // 均价
    public String rePriceS;  // 参考价
    public String tranPriceS; // 成交额
    public String tradeVolumeStrK;
    public String tradeVolumeStr = "";
    public String changeS;   // 涨跌
    public String changeRateS; // 涨跌幅

    public float openPrice;
    public float highPrice;
    public float lowPrice;
    public float closePrice;
    public float tradeVolume; // 成交量
    public float avPrice;
    public float rePrice;  // 参考价
    public float tranPrice; // 成交额
    public float change;   // 涨跌
    public float changeRate; // 涨跌幅

}
