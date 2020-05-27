package com.cvicse.stock.markets.data;

/**
 * Created by ruan_ytai on 17-1-13.
 * 大盘指数类
 */

public class MarketIndex {

    private String name;
    private String value;
    private String percent;
    private boolean isRaise;

    public MarketIndex(String name,String value,String percent,boolean isRaise){
        this.name = name;
        this.value = value;
        this.percent = percent;
        this.isRaise = isRaise;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getPercent() {
        return percent;
    }

    public void setPercent(String percent) {
        this.percent = percent;
    }

    public boolean isRaise() {
        return isRaise;
    }

    public void setRaise(boolean raise) {
        isRaise = raise;
    }
}
