package com.cvicse.stock.markets.data;

/**
 * Created by ruan_ytai on 17-3-1.
 */

public class NewStockDetailBo {

    private String identify;
    private String data;

    public NewStockDetailBo(){

    }

    public NewStockDetailBo(String identify, String data){
        this.identify = identify;
        this.data = data;
    }

    public String getIdentify() {
        return identify;
    }

    public void setIdentify(String identify) {
        this.identify = identify;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
