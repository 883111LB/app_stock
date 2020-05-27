package com.cvicse.stock.http;

import android.Manifest;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.cvicse.base.helper.PermissionHelper;
import com.cvicse.stock.http.loop.RequestManager;
import com.mitake.core.AppInfo;
import com.mitake.core.config.HttpChangeMode;
import com.mitake.core.config.MitakeConfig;
import com.mitake.core.config.SseSdk;
import com.mitake.core.network.MitakeMonitor;
import com.mitake.core.network.MitakeMonitorServerIP;
import com.mitake.core.network.ThreadPoolManager;
//import com.mitake.core.network.threadpool.ThreadPoolManager;
import com.mitake.core.request.RegisterRequest;
import com.mitake.core.response.IResponseCallback;
import com.mitake.core.response.RegisterResponse;
import com.mitake.core.response.Response;

import java.lang.reflect.Field;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

/**
 * Created by liu_zlu on 2016/12/28 21:18
 */
public class StockConfig {
    private static boolean isRquest = false;
    protected static MockThreadPoolExecutor newPool;

    public static void init(final Context context,final String key){
        RequestManager.start();
        try {
            // 替换客户jar包线程池，为了获取网络信息
//            Class.forName(ThreadPoolManager.class.getName());
//            Field field = ThreadPoolManager.class.getDeclaredField("threadPool");
//            field.setAccessible(true);
//            ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) field.get(null);
            /**
             * 替换客户jar包线程池，为了获取网络信息
             */
            Class.forName(ThreadPoolManager.class.getName());
            Field field = ThreadPoolManager.class.getDeclaredField("a");
            field.setAccessible(true);
            ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) field.get(null);
            newPool = new MockThreadPoolExecutor(threadPoolExecutor.getCorePoolSize()
                    ,threadPoolExecutor.getMaximumPoolSize(), (long)5000,TimeUnit.MILLISECONDS,threadPoolExecutor.getQueue(),
                    threadPoolExecutor.getThreadFactory(),threadPoolExecutor.getRejectedExecutionHandler());
            field.set(null,newPool);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        // 获取app所有权限
        getRequestPermission();

        PermissionHelper.requestInternet(new PermissionHelper.OnPermissionGrantedListener() {
            @Override
            public void onPermissionGranted() {
                //拥有权限
                initSelf(context.getApplicationContext(),key);
            }
        });

        PermissionHelper.requestPhone(new PermissionHelper.OnPermissionGrantedListener() {
            @Override
            public void onPermissionGranted() {
                //拥有权限
                initSelf(context.getApplicationContext(),key);
            }
        });

        ((Application)context.getApplicationContext()).registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                if(isRquest){
                    return;
                }
                isRquest = true;
                if (ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
                    //拥有权限
                    initSelf(context.getApplicationContext(),key);
                } else {
                    //没有权限,判断是否会弹权限申请对话框
                    boolean shouldShow = ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.READ_PHONE_STATE);
                    // if (shouldShow) {
                    //申请权限
                    requestPermission(activity);
                   /* } else {

                    }*/
                }
                if( ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION)  != PackageManager.PERMISSION_GRANTED ||
                        ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)  != PackageManager.PERMISSION_GRANTED){
                    requestPermission(activity);
                }

                if(ContextCompat.checkSelfPermission(context,Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED||
                        ContextCompat.checkSelfPermission(context,Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED  ){
                    requestPermission(activity);
                }
            }

            @Override
            public void onActivityStarted(Activity activity) {

            }

            @Override
            public void onActivityResumed(Activity activity) {

            }

            @Override
            public void onActivityPaused(Activity activity) {

            }

            @Override
            public void onActivityStopped(Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {

            }
        });

        if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(context, Manifest.permission.INTERNET) == PackageManager.PERMISSION_GRANTED) {
            //拥有权限
            initSelf(context.getApplicationContext(),key);
        }

        if( ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION)  == PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)  == PackageManager.PERMISSION_GRANTED){
            LocationUtils.init();

            //谷歌地图初始化
            GLocationUtils.init();
        }

        PermissionHelper.requestLocation(new PermissionHelper.OnPermissionGrantedListener() {
            @Override
            public void onPermissionGranted() {
                LocationUtils.init();
                //谷歌地图初始化
                GLocationUtils.init();
            }
        });
    }

    private static void requestPermission(Activity activity) {
        ActivityCompat.requestPermissions(activity, new String[] {Manifest.permission.READ_PHONE_STATE,Manifest.permission.INTERNET,
                Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS, Manifest.permission.WRITE_EXTERNAL_STORAGE ,Manifest.permission.ACCESS_COARSE_LOCATION,
                ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_NETWORK_STATE}, 3);
    }

    /**
     * 获取权限
     */
    private static void getRequestPermission() {
        PermissionHelper.requestStorage(new PermissionHelper.OnPermissionGrantedListener() {
            @Override
            public void onPermissionGranted() {
            }
        });
    }

    private static void initSelf(final Context context, final String key) {
        // 初始化元件註冊所需資訊
        AppInfo.build(context);
        AppInfo.refreshTime=5*1000;
        //进行认证
        sendRegister(context,key);

        // 啟動背景監控機制
        MitakeMonitor mitakeMonitor = MitakeMonitor.getInstance();
        mitakeMonitor.setNetworkListener(new MitakeMonitor.INetworkListener() {
            @Override
            public boolean isNetworkAvailable() {
                /**
                 * 通知背景更新機制,手機端是否有可連線
                 */
                ConnectivityManager connectivityManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

                if(networkInfo != null && true == networkInfo.isAvailable() && true == networkInfo.isConnected()) {
                    return true;
                }
                else {
                    return false;
                }
            }

            @Override
            public int getRefreshMillisSecond() {
                /**
                 * 背景更新秒數
                 */
                return 5000;
            }

            @Override
            public void networkStatus(int status) {
                if(status == MitakeMonitor.NETWORK_AVAILABLE) {
                    /**
                     * 重新發送Auth去取得相關資訊
                     */
                    sendRegister(context,key);
                }
                else {
                    /**
                     * 無可建立的連線
                     */
                }
            }
        });

        mitakeMonitor.running = true;
        ThreadPoolManager.execute(mitakeMonitor);
    }

    public static boolean register = false ;

    private static void sendRegister(final Context context,String key) {
        // 填入所申请的key
        SseSdk.setDebug(true);  // 进入调试模式
        MitakeConfig config1 = new MitakeConfig();
        config1.setAppkey(key).setContext(context)
                .setHttpChangeMode(HttpChangeMode.DEFAULT);
        SseSdk.setConfig(config1);

        RegisterRequest request = new RegisterRequest();
        request.send(new IResponseCallback(){
            @Override
            public void callback(Response response) {
                RegisterResponse registerResponse = (RegisterResponse)response;
                //认证成功
                if(registerResponse.sucess == true) {
                    register = true;
                    handler.sendEmptyMessage(success);
                }
                else {
                    handler.sendEmptyMessage(error);
                }
            }

            @Override
            public void exception(int code, String message) {
                handler.sendEmptyMessage(error);
            }
        });

        MitakeMonitorServerIP.getInstance().setNetworkListener(new MitakeMonitorServerIP.INetworkListener() {
            @Override
            public int getRefreshMillisSecond() {
                return 3000;
            }
        });
    }

    public static void initPermissions(String level,String cffLevel,String hkLevel,String hkzsLevel, String dceLevel,String zceLevel,String shfeLevel,String ineLevel,String feLevel,String giLevel){
       // 清除港股权限
        SseSdk.permission().clearHkPermission();
        //权限配置
        SseSdk.permission().setLevel(level)      //此处配置沪深权限
                .addHkPermission(hkLevel)  //添加港股权限
                .setCffLevel(cffLevel);    //中金所权限
        SseSdk.permission().setDceLevel(dceLevel);
        SseSdk.permission().setCzceLevel(zceLevel);
        SseSdk.permission().setShfeLevel(shfeLevel);//上期所
        SseSdk.permission().setIneLevel(ineLevel);
        SseSdk.permission().setFeLevel(feLevel);
        SseSdk.permission().setGILevel(giLevel);
        if (null != hkzsLevel && !"".equals(hkzsLevel)) {
            SseSdk.permission().addHkPermission(hkzsLevel);
        }
    }

    private static Listener listener;

    private static Handler handler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Listener listener1 = listener;
            listener = null;
            switch (msg.what){
                case timeOut:
                    if(listener1 != null){
                        listener1.onRespone(timeOut);
                    }
                    break;
                case success:
                    if(listener1 != null){
                        listener1.onRespone(success);
                    }
                    break;
                default:
                    if(listener1 != null){
                        listener1.onRespone(error);
                    }
                    break;
            }
        }
    };

    public static void setListener(int outTime,Listener listener){
        StockConfig.listener = listener;
        if(register){
            handler.sendEmptyMessage(success);
            return;
        }
        handler.sendEmptyMessageDelayed(timeOut,outTime);
    }

    public interface Listener {
        void onRespone(int type);
    }

    public static final int timeOut = 1;

    public static final int success = 0;

    public static final int error = 2;
}
