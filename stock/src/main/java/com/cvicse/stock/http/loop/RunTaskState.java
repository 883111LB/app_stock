package com.cvicse.stock.http.loop;

import com.cvicse.stock.http.RequestSetting;
import com.cvicse.stock.http.ResponseCallback;
import com.mitake.core.response.Response;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by liu_zlu on 2017/2/28 09:41
 */
public abstract class RunTaskState<T> extends ResponseCallback implements Runnable {

   //请求标示，唯一
    public int what = 0;
    //执行请求的时间点
    private long time = 0;
    public boolean isRunning() {
        return isRunning;
    }

    public void setRunning(boolean running) {
        isRunning = running;
    }

    /**
     * 判断当前循环任务有没有停止，true为正在执行，false为停止
     */
    boolean isRunning = true;

    @Override
    public final void run() {
        if(!isRunning())return;
        time = System.currentTimeMillis();
        runInner();
    }

    /**
     * 真实执行循环请求的方法
     */
    public abstract void runInner();

    public void onBack(Response response){
        onBack((T)response);
    }

    /**
     * 转换为明确的Response类型
     * @param response
     */
    public abstract void onBack(T response);
    public abstract void onError(int i, String error);
    /**
     * 执行完毕回调
     */
    @Override
    public void onComplete() {
        super.onComplete();
        if(!isRunning){
            return;
        }

        SimpleDateFormat sdf = new SimpleDateFormat("HHmmss");
        int currentTime = Integer.parseInt(sdf.format(new Date()));
        /**
         * 8点之前或5点之后不循环请求
         */
        if(currentTime < 80000 || currentTime > 170000){
            return;
        }

        float rate = RequestSetting.getRefreshRate();

        if(rate < 0){ // -1 时 不刷新
            return;
        }

        //实时刷新
        if(rate == 0){
            //默认请求间隔时间，秒
            rate = 1f;
        }
        long tempTime = System.currentTimeMillis();
        long runTime = tempTime - time;

        long delayed = Math.max((int)rate*1000 - runTime,0);
        //再次调用请求
        RequestManager.request(what,this, (int) delayed);
    }
}
