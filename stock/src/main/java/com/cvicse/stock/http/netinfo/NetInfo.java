package com.cvicse.stock.http.netinfo;

/**
 * Created by liu_zlu on 2016/12/29 11:38
 */
public class NetInfo {
    /**
     * 请求名
     */
    private String name = "";
    /**
     * 请求路径
     */
    private String url = "";
    /**
     * 请求参数
     */
    private String params = "";
    /**
     * 从开始到结束花费的时间
     */
    private int startend = 0;
    /**
     * 从执行到结束花费的时间
     */
    private int runend = 0;

    private String state = "";

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    /**
     * 请求开始时网络状态
     */
    private RequestStatus startStatus = new RequestStatus();
    /**
     * 请求执行时网络状态
     */
    private RequestStatus runStatus = new RequestStatus();
    /**
     * 请求结束时网络状态
     */
    private RequestStatus endStatus = new RequestStatus();
    /**
     * 请求定位地址
     */
    private Location location;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public int getStartend() {
        return startend;
    }

    public void setStartend(int startend) {
        this.startend = startend;
    }

    public int getRunend() {
        return runend;
    }

    public void setRunend(int runend) {
        this.runend = runend;
    }

    public RequestStatus getStartStatus() {
        return startStatus;
    }

    public void setStartStatus(RequestStatus startStatus) {
        this.startStatus = startStatus;
    }

    public RequestStatus getEndStatus() {
        return endStatus;
    }

    public void setEndStatus(RequestStatus endStatus) {
        this.endStatus = endStatus;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public RequestStatus getRunStatus() {
        return runStatus;
    }

    public void setRunStatus(RequestStatus runStatus) {
        this.runStatus = runStatus;
    }

    /**
     * 运营商
     */
    private String caName = "";
    public String getCaName() {
        return caName;
    }

    public void setCaName(String caName) {
        this.caName = caName;
    }
}
