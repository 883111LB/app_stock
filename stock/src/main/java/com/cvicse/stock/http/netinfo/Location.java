package com.cvicse.stock.http.netinfo;

/**
 * Created by liu_zlu on 2017/1/5 11:28
 */
public class Location {

    private double latitude;

    private double langitude;

    private String address;

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLangitude() {
        return langitude;
    }

    public void setLangitude(double langitude) {
        this.langitude = langitude;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
