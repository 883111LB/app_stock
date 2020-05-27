package com.cvicse.stock.http.netinfo;

/**
 * Created by liu_zlu on 2017/1/5 11:18
 */
public class RequestStatus {
    /**
     * 执行时间
     */
    private long time;
    /**
     * 当前执行的线程数
     */
    private int aliveCount = -1;
    /**
     * 共用多少线程数
     */
    private int allCount = -1;
    /**
     * 网络类型
     */
    private String netType = "";
    /**
     * 网速
     */
    private String netSpeed = "";


    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public int getAliveCount() {
        return aliveCount;
    }

    public void setAliveCount(int aliveCount) {
        this.aliveCount = aliveCount;
    }

    public int getAllCount() {
        return allCount;
    }

    public void setAllCount(int allCount) {
        this.allCount = allCount;
    }

    public String getNetType() {
        return netType;
    }

    public void setNetType(String netType) {
        this.netType = netType;
    }

    public String getNetSpeed() {
        return netSpeed;
    }

    public void setNetSpeed(String netSpeed) {
        this.netSpeed = netSpeed;
    }


}
