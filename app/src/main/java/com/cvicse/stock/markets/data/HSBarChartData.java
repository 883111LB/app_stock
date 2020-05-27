package com.cvicse.stock.markets.data;

/**
 * Created by shi_yhui on 2018/12/3.
 */

public class HSBarChartData {

    private String name = "";
    //颜色
    private int mColor = 0;
    //数值
    private String mValue = "";

    private String mValueformat = "";

    //是否是正
    private boolean isPlus = true;

    //高度
    private float mHeight;

    public HSBarChartData(String name,String value,int color){
        this.name = name;
        this.mValue = value;
        this.mColor = color;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getmColor() {
        return mColor;
    }

    public void setmColor(int mColor) {
        this.mColor = mColor;
    }

    public String getmValue() {
        return mValue;
    }

    public void setmValue(String mValue) {
        this.mValue = mValue;
    }

    public boolean isPlus() {
        return isPlus;
    }

    public void setPlus(boolean plus) {
        isPlus = plus;
    }

    public float getmHeight() {
        return mHeight;
    }

    public void setmHeight(float mHeight) {
        this.mHeight = mHeight;
    }

    public String getmValueformat() {
        return mValueformat;
    }

    public void setmValueformat(String mValueformat) {
        this.mValueformat = mValueformat;
    }
}
