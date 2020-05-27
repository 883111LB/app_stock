package com.cvicse.stock.markets.data;

/**
 * 逐笔数据
 * Created by liu_zlu on 2017/3/7 14:06
 */
public class TickItemBo {
    /**
     * 交易时间
     */
    public String time;
    /**
     * 买卖标志 48/S表示卖 31/B 表示买
     */
    public String tickFlag;
    /**
     * 交易量
     */
    public String volume;
    /**
     * 交易价格
     */
    public double price;
    /**
     * 交易价格
     */
    public String strPrice;
}
