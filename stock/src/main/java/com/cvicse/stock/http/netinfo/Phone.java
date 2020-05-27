package com.cvicse.stock.http.netinfo;

/**
 * Created by liu_zlu on 2017/2/9 09:44
 */
public class Phone {
    /**
     * 手机类型
     */
    private String type = "Android";
    /**
     * mac地址
     */
    private String mac = "";

    /**
     * mac地址
     */
    private String version = "";
    /**
     * 手机名
     */
    private String name = "";

    /**
     * 手机名
     */
    private String  carrier= "";

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getCarrier() {
        return carrier;
    }

    public void setCarrier(String carrier) {
        this.carrier = carrier;
    }
}
