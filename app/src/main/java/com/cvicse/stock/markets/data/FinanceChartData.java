package com.cvicse.stock.markets.data;


import com.cvicse.stock.util.FormatUtils;

/** 财务对比，图表数据
 * Created by tang_xqing on 2018/4/24.
 */
public class FinanceChartData {
    private String date=""; // 日期
    private String dateText=""; // 2015年年报
    private String valueStr=""; // 值(包含单位)
    private double value;  // 值
    private String mom="";  // 比率
    private String unit=""; // 单位

    public FinanceChartData(String date, String valueStr, String mom) {
        setDate(date);
        setValueStr(valueStr);
        setMom(mom);
    }

    public void setDate(String date) {
        this.date = date;
        setDateQ(this.date);
    }

    public void setValue(double value) {
        this.value = value;
    }

    public void setValueStr(String valueStr) {
        this.valueStr = valueStr;
    }

    public void setDateText(String dateText) {
        this.dateText = dateText;
    }

    public void setMom(String mom) {
        this.mom = mom;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getDate() {
        return date;
    }

    public String getDateText() {
        return dateText;
    }

    public double getValue() {
        return value;
    }

    public String getValueStr() {
        return valueStr;
    }

    public String getMom() {
        return mom;
    }

    public String setDateQ(String date){
        if(!FormatUtils.isStandard(date)){
            return "";
        }
        if( date.length() >= 10 ) {
            String text = date.substring(5, 10);
            switch (text) {
                case "12-31":
                    this.dateText = date.substring(0, 4) + "年 年报";
                    break;
                case "06-30":
                    this.dateText = date.substring(0, 4) + "年 中报";
                    break;
                case "03-31":
                    this.dateText = date.substring(0, 4) + "年 一季报";
                    break;
                case "09-30":
                    this.dateText = date.substring(0, 4) + "年 三季报";
                    break;
                default:
                    this.dateText = date;
                    break;
            }
        }else{
            this.dateText = date;
        }
        return this.dateText;
    }
}
