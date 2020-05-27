package com.cvicse.stock.http.netinfo;

import java.util.ArrayList;

/**
 * 发送实体类
 * Created by liu_zlu on 2017/2/9 09:53
 */
public class SendBo {

    private ArrayList<NetInfo> netInfos;

    private Phone phone;

    public ArrayList<NetInfo> getNetInfos() {
        return netInfos;
    }

    public void setNetInfos(ArrayList<NetInfo> netInfos) {
        this.netInfos = netInfos;
    }

    public Phone getPhone() {
        return phone;
    }

    public void setPhone(Phone phone) {
        this.phone = phone;
    }
}
