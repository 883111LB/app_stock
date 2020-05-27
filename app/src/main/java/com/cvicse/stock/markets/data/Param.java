package com.cvicse.stock.markets.data;

/**
 * Created by ruan_ytai on 17-1-19.
 */
public class Param {

    private String stockName;
    private String stockCode;  // 排序分类代码
    private String sortParam;   // 排序参数
    private String type;

    public Param(String stockName,String stockCode,String sortParam,String type){
        this.stockName = stockName;
        this.stockCode = stockCode;
        this.sortParam = sortParam;
        this.type = type;
    }

    public Param(String stockCode,String sortParam){
        this.stockCode = stockCode;
        this.sortParam = sortParam;
    }

    public Param(String stockCode,String sortParam,String type){
        this.stockCode = stockCode;
        this.sortParam = sortParam;
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStockName() {
        return stockName;
    }

    public void setStockName(String stockName) {
        this.stockName = stockName;
    }

    public String getStockCode() {
        return stockCode;
    }

    public void setStockCode(String stockCode) {
        this.stockCode = stockCode;
    }

    public String getSortParam() {
        return sortParam;
    }

    public void setSortParam(String sortParam) {
        this.sortParam = sortParam;
    }
}
