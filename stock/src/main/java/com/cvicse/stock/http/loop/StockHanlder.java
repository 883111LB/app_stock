package com.cvicse.stock.http.loop;

import android.os.Handler;
import android.os.Message;

import java.lang.ref.WeakReference;

/**
 * Created by liu_zlu on 2017/2/27 21:39
 */
public class StockHanlder extends Handler {

    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
    }

    /**
     * 接收任务，处理执行
     * @param msg
     */
    @Override
    public void dispatchMessage(Message msg) {
        super.dispatchMessage(msg);
        if( null==msg ){
            return ;
        }
        RunTaskState taskState = ((WeakReference<RunTaskState>) msg.obj).get();
        if( null==taskState  || !taskState.isRunning()){
            return;
        }
        taskState.what = msg.what;
        taskState.run();
    }
}
