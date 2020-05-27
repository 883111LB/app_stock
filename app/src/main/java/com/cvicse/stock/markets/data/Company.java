package com.cvicse.stock.markets.data;

/**
 * Created by ruan_ytai on 17-1-12.
 */

public class Company {
    private String name;
    private String code;
    private String newestPrice;
    private String raiseRange;

    public Company(){

    }
    public Company(String name,String code,String newestPrice,String raiseRange){
        this.name = name;
        this.code = code;
        this.newestPrice = newestPrice;
        this.raiseRange = raiseRange;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getNewestPrice() {
        return newestPrice;
    }

    public void setNewestPrice(String newestPrice) {
        this.newestPrice = newestPrice;
    }

    public String getRaiseRange() {
        return raiseRange;
    }

    public void setRaiseRange(String raiseRange) {
        this.raiseRange = raiseRange;
    }
}
