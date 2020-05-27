package com.cvicse.stock.markets.data;

/**
 * Created by shi_yhui on 2018/11/28.
 */

public class DRQuoteBo {
    private String code;
    private String lastPrice;
    private String changeRate;
    private String premiun;
    private String subType;
    public String upDownFlag;

    public DRQuoteBo(){

    }

    public DRQuoteBo(String code, String lastPrice, String premiun, String changeRate, String subType, String upDownFlag){
        this.code=code;
        this.lastPrice=lastPrice;
        this.changeRate=changeRate;
        this.premiun=premiun;
        this.subType=subType;
        this.upDownFlag=upDownFlag;
    }

    public String getCode(){
        return code;
    }
    public void setCode(String code){
        this.code=code;
    }

    public String getLastPrice(){
        return lastPrice;
    }
    public void setLastPrice(String lastPrice){
        this.lastPrice=lastPrice;
    }

    public String getChangeRate(){
        return changeRate;
    }
    public void setChangeRate(String changeRate){
        this.changeRate=changeRate;
    }

    public String getPremiun(){
        return premiun;
    }
    public void setPremiun(String premiun){
        this.premiun=premiun;
    }

    public String getSubType(){
        return subType;
    }
    public void setSubType(String subType){
        this.subType=subType;
    }

    public String getUpDownFlag(){
        return upDownFlag;
    }
    public void setUpDownFlag(String upDownFlag){
        this.upDownFlag=upDownFlag;
    }

}
