package com.cvicse.stock.markets.data;

/**
 * Created by ding_syi on 17-2-6.
 */
public class HKTStock {
    public String name;
    public String id;
    public String number;
    public String present;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getPresent() {
        return present;
    }

    public void setPresent(String present) {
        this.present = present;
    }
}
