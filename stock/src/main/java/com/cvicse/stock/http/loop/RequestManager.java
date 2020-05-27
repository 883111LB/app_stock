package com.cvicse.stock.http.loop;

import android.os.Looper;
import android.os.Message;

import java.lang.ref.WeakReference;
import java.util.HashMap;

/**
 * 循环请求管理类
 * Created by liu_zlu on 2017/2/27 21:41
 */
public class RequestManager {
    static StockHanlder stockHanlder;
    //分配请求标示
    static int requestSign = 0;
    static final Object object = new Object();
    private static HashMap<String,WeakReference<RunTaskState>> stringWeakReferenceHashMap = new HashMap<>();

    /**
     * 启动方法，必须调用
     */
    public static void start(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                Looper.prepare();
                stockHanlder = new StockHanlder();
                Looper.loop();
            }
        }).start();
    }

    /**
     * 执行网络请求
     * @param runnable
     * @return
     */
    public static RunTaskState request(RunTaskState runnable){
        requestSign++;
        request(requestSign,runnable);
        return runnable;
    }

    public static RunTaskState request(int requestSign, RunTaskState runnable){
        request(requestSign,runnable,0);
        return runnable;
    }

    public static RunTaskState request(int requestSign, RunTaskState runnable,int delayed){
        synchronized (object){
            Message message = stockHanlder.obtainMessage(requestSign,new WeakReference<>(runnable));
            if( 0 == runnable.what ){
                runnable.what = requestSign;
            }
            stockHanlder.sendMessageDelayed(message,delayed);
            stringWeakReferenceHashMap.put(requestSign+"",new WeakReference<>(runnable));
        }
        return runnable;
    }

    /**
     * 取消网络请求
     * @param runTaskState 具体网络请求
     */
    public static void cancel(RunTaskState runTaskState){
        if( null==runTaskState ){
            return;
        }
        synchronized (object) {
            stringWeakReferenceHashMap.remove(runTaskState.what + "");
            if ( null!= runTaskState) {
                runTaskState.setRunning(false);
            }
            stockHanlder.removeMessages(runTaskState.what);
        }
    }
    /**
     * 取消网络请求
     * @param requestSign 具体网络请求标示
     */
    public static void cancel(int requestSign){
        synchronized (object) {
            WeakReference<RunTaskState> weakReference = stringWeakReferenceHashMap.remove(requestSign + "");
            if ( null==weakReference ) {
                return;
            }
            RunTaskState runTaskState = weakReference.get();
            if ( null!=runTaskState ) {
                runTaskState.setRunning(false);
            }
            stockHanlder.removeMessages(requestSign);
        }
    }
}
