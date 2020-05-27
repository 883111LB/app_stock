package com.cvicse.stock.markets.data;

/**
 * 个股新闻、公共、研报item
 * Created by liu_zlu on 2017/2/8 11:08
 */
public class MarketNBRBo {
    public String id = "";
    /**
     * 标题
     */
    public String title = "";
    /**
     * 股票公司名
     */
    public String stockName = "";
    /**
     * 发布日期
     */
    public String date = "";
    /**
     * 来源说明
     */
    public String medianame = "";
    public MarketNBRBo(String id,String title,String stockName,String date,String medianame){
        this.id = id;
        this.title = title;
        this.stockName = stockName;
        this.date = date;
        this.medianame = medianame;
    }
}
