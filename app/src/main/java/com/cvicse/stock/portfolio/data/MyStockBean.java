package com.cvicse.stock.portfolio.data;

/**
 * Created by ding_syi on 17-1-5.
 * 自选证券model
 */
public class MyStockBean {
    /** 名称 */
    private String name;
    /**  id*/
    private String id;
    /** 最新价 */
    private String lastPrice;
    /**  成交额*/
    private String account;

    public String getName() {
        return (name);
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return (id);
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLastPrice() {
        return (lastPrice);
    }

    public void setLastPrice(String lastPrice) {
        this.lastPrice = lastPrice;
    }

    public String getAccount() {
        return (account);
    }

    public void setAccount(String account) {
        this.account = account;
    }
}
