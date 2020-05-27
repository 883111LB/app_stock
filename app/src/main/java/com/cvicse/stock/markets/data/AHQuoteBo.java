package com.cvicse.stock.markets.data;

/**  AH股 行情
 * Created by tang_xqing on 2018/5/11.
 */
public class AHQuoteBo {
    private String code;
    private String lastPrice;
    private String premiun;
    private String changeRate;
    public String upDownFlag;

    public AHQuoteBo() {
    }

    public AHQuoteBo(String code, String lastPrice, String premiun) {
        this.code = code;
        this.lastPrice = lastPrice;
        this.premiun = premiun;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLastPrice() {
        return lastPrice;
    }

    public void setLastPrice(String lastPrice) {
        this.lastPrice = lastPrice;
    }

    public String getPremiun() {
        return premiun;
    }

    public void setPremiun(String premiun) {
        this.premiun = premiun;
    }

    public String getChangeRate() {
        return changeRate;
    }

    public void setChangeRate(String changeRate) {
        this.changeRate = changeRate;
    }

    public String getUpDownFlag() {
        return upDownFlag;
    }

    public void setUpDownFlag(String upDownFlag) {
        this.upDownFlag = upDownFlag;
    }
}
