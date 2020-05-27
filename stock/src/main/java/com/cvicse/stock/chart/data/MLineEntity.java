package com.cvicse.stock.chart.data;

/**
 * 分时线图处理后数据
 * Created by tang_xqing on 2017/3/20
 */
public class MLineEntity extends MakerEntity{


    public boolean isUp; //是否是上升
    public String date; // 底部展示时间
    public String datetime;  // 成交时间

    public float open;
    public float high;
    public float low;
    public float close;

    public float volume; // 成交量
    public String volumeStrK; // 成交量
    public String volumeStr = ""; // 成交量

    public String ddx;
    public String ddy;
    public String ddz;
    public String bbd;
    public String ratioBs;
    public String ultraL;  // 超大
    public String large;   // 大单
    public String medium;  // 中单
    public String small;   // 小单

    public String getDatetime() {
        return date;
    }

    public float getOpenPrice() {
        return open;
    }

    public float getClosePrice() {
        return close;
    }

    public String getDdx() {
        return ddx;
    }

    public String getDdy() {
        return ddy;
    }

    public String getDdz() {
        return ddz;
    }

    public String getBbd() {
        return bbd;
    }

    public String getRatioBs() {
        return ratioBs;
    }

    public String getUltraL() {
        return ultraL;
    }

    public String getLarge() {
        return large;
    }

    public String getMedium() {
        return medium;
    }

    public String getSmall() {
        return small;
    }
}
