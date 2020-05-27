package com.cvicse.stock.markets.data;

/** 融资融券
 * Created by tang_xqing on 2018/8/3.
 */
public class MarginTradingBo {

    public String tradeDate;  // 交易日期
    public String trading;    // 股票代码
    public String finbalance;  // 融资余额（元）
    public String finbuyamt;  // 融资买入额（元）
    public String finrepayamt;  // 融资偿还额（元）
    public String finroebuy; // 融资净买入
    public String mrggbal;     // 融券余量金额（元）
    public String mrgnresqty;  // 融券余量（元）
    public String mrgnsellamt; // 融券卖出量（元）
    public String mrgnrepayamt; // 融券偿还量（元）
    public String mrgnroesell; // 融券净卖出
    public String finmrghbal;  // 融资融券余额
    public String finmrgnbal;  // 融资融券余额差额
    public String unit;  // 单位

    public double fFinbalance;
    public double fFinbuyamt;
    public double fFinrepayamt;
    public double fFnroebuy;
    public double fMrggbal;
    public double fMrgnresqty;
    public double fMrgnsellamt;
    public double fMrgnrepayamt;
    public double fMrgnroesell;
    public double fFinmrghbal;  // 融资融券余额
    public double fFinmrgnbal;  // 融资融券差额

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getTradeDate() {
        return tradeDate;
    }

    public void setTradeDate(String tradeDate) {
        this.tradeDate = tradeDate;
    }

    public String getTrading() {
        return trading;
    }

    public void setTrading(String trading) {
        this.trading = trading;
    }

    public String getFinbalance() {
        return finbalance;
    }

    public void setFinbalance(String finbalance) {
        this.finbalance = finbalance;
    }

    public String getFinbuyamt() {
        return finbuyamt;
    }

    public void setFinbuyamt(String finbuyamt) {
        this.finbuyamt = finbuyamt;
    }

    public String getFinrepayamt() {
        return finrepayamt;
    }

    public void setFinrepayamt(String finrepayamt) {
        this.finrepayamt = finrepayamt;
    }

    public String getFinroebuy() {
        return finroebuy;
    }

    public void setFinroebuy(String finroebuy) {
        this.finroebuy = finroebuy;
    }

    public String getMrggbal() {
        return mrggbal;
    }

    public void setMrggbal(String mrggbal) {
        this.mrggbal = mrggbal;
    }

    public String getMrgnresqty() {
        return mrgnresqty;
    }

    public void setMrgnresqty(String mrgnresqty) {
        this.mrgnresqty = mrgnresqty;
    }

    public String getMrgnsellamt() {
        return mrgnsellamt;
    }

    public void setMrgnsellamt(String mrgnsellamt) {
        this.mrgnsellamt = mrgnsellamt;
    }

    public String getMrgnrepayamt() {
        return mrgnrepayamt;
    }

    public void setMrgnrepayamt(String mrgnrepayamt) {
        this.mrgnrepayamt = mrgnrepayamt;
    }

    public String getMrgnroesell() {
        return mrgnroesell;
    }

    public void setMrgnroesell(String mrgnroesell) {
        this.mrgnroesell = mrgnroesell;
    }

    public String getFinmrghbal() {
        return finmrghbal;
    }

    public void setFinmrghbal(String finmrghbal) {
        this.finmrghbal = finmrghbal;
    }
}
