package com.cvicse.stock.http;

import android.util.Log;

import com.cvicse.stock.http.netinfo.NetInfo;
import com.mitake.core.network.MitakeHttpGet;
//import com.mitake.core.network.MitakeHttpGetA;
import com.mitake.core.network.MitakeHttpParams;
import com.mitake.core.network.MitakeHttpPost;
//import com.mitake.core.network.MitakeHttpPostA;

/**
 * 封装执行网络请求
 * Created by liu_zlu on 2017/1/5 10:49
 */
public class MockRunnable implements Runnable {

    public static ThreadLocal<NetInfo> threadLocal = new ThreadLocal<>();
    /**
     * 该请求的具体网络状态数据
     */
    private NetInfo netInfo;
    /**
     * 真实的网络请求
     */
    Runnable runnable;
    public MockRunnable(Runnable runnable){
        this.runnable = runnable;
        netInfo = new NetInfo();
        saveRequest(runnable,netInfo);
        netInfo.setStartStatus(RequestStatusUtils.getCurrent());
    }
    @Override
    public void run() {
        netInfo.setRunStatus(RequestStatusUtils.getCurrent());
        threadLocal.set(netInfo);
        Runnable runnable1 = runnable;
        runnable = null;
        try {
            /**
             * 执行真正的网络请求
             */
            runnable1.run();
        } catch (Exception e){
            Log.e("","");
            e.printStackTrace();
        }
        // netInfo.setEndStatus(RequestStatusUtils.getCurrent());
    }

    protected void saveRequest(Runnable r,NetInfo netInfo) {
        MitakeHttpParams params = null;

        if(r instanceof MitakeHttpGet){
            params = ((MitakeHttpGet)r).getMitakeHttpParams();
        }  else if(r instanceof MitakeHttpPost){
            params = ((MitakeHttpPost)r).getMitakeHttpParams();
        }
        if(params != null){
            /**
             * 获取真实的请求路径
             */
            netInfo.setUrl(params.ip+params.api);
        }
    }
}
