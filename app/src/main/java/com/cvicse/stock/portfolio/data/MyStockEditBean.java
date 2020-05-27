package com.cvicse.stock.portfolio.data;

/**
 * Created by ding_syi on 17-1-16.
 */
public class MyStockEditBean {
    /** 是否选中 */
    private boolean ifChoose=false;
    /** name */
    private String name;
    /** id*/
    private String id;

    public boolean isIfChoose() {
        return ifChoose;
    }

    public void setIfChoose(boolean ifChoose) {
        this.ifChoose = ifChoose;
    }

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
}
