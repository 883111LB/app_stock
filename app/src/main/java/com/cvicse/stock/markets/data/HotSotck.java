package com.cvicse.stock.markets.data;

/**
 * Created by ding_syi on 17-1-20.
 */
public class HotSotck  {
    /**
     * 行业
     */
    private String stockType;
    /**
     * 增长率
     */
    private String changeRate;

    /**
     *公司名称
     */
    private String stockCompany;

    /**
     *股票值
     */
    private String StockNum;

    /**
     *股票增长
     */
    private String StockChange;

    public String getStockType() {
        return stockType;
    }

    public void setStockType(String stockType) {
        this.stockType = stockType;
    }

    public String getChangeRate() {
        return changeRate;
    }

    public void setChangeRate(String changeRate) {
        this.changeRate = changeRate;
    }

    public String getStockCompany() {
        return stockCompany;
    }

    public void setStockCompany(String stockCompany) {
        this.stockCompany = stockCompany;
    }

    public String getStockNum() {
        return StockNum;
    }

    public void setStockNum(String stockNum) {
        StockNum = stockNum;
    }

    public String getStockChange() {
        return StockChange;
    }

    public void setStockChange(String stockChange) {
        StockChange = stockChange;
    }
}
