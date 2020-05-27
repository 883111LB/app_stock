package com.cvicse.stock.http;

import com.cvicse.base.connectionclass.ConnectionClassManager;
import com.cvicse.base.utils.NetworkUtils;
import com.cvicse.stock.http.netinfo.RequestStatus;

/**
 * Created by liu_zlu on 2017/4/7 09:27
 */
public class RequestStatusUtils {
    /**
     * 获取当前的网络状态
     * @return
     */
    public static RequestStatus getCurrent(){
        RequestStatus requestStatus =  new RequestStatus();
        //网速
        requestStatus.setNetSpeed(String.format("%.2f", ConnectionClassManager.getInstance().getDownloadKBitsPerSecond()));
        /**
         * 当前时间
         */
        requestStatus.setTime(System.currentTimeMillis());
        /**
         * 任务数量
         */
        requestStatus.setAllCount(StockConfig.newPool.getCorePoolSize());
        /**
         * 执行中的数量
         */
        requestStatus.setAliveCount(StockConfig.newPool.getActiveCount());
        /**
         * 网络类型
         */
        requestStatus.setNetType(NetworkUtils.getNetworkType()+"");
        return requestStatus;
    }
}
