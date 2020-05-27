package com.cvicse.stock;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.Looper;

import com.mitake.core.AppInfo;
import com.mitake.core.network.MitakeMonitor;
import com.mitake.core.network.ThreadPoolManager;
import com.mitake.core.request.RegisterRequest;
import com.mitake.core.response.IResponseCallback;
import com.mitake.core.response.RegisterResponse;
import com.mitake.core.response.Response;

import java.util.concurrent.CountDownLatch;

/**
 * Created by liu_zlu on 2016/12/22 17:00
 */
public class BaseTest {
    final static CountDownLatch latch = new CountDownLatch(1);
    static {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                init();
            }
        });
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void init()
    {
        /**
         * 初始化元件註冊所需資訊
         */
        AppInfo.build(com.cvicse.base.ui.BaseApplication.getInstance());
        /**
         * 填入所申请的key
         */
//        AppInfo.appKey = "gzreyQFcje5eWB8m+MSnRZKty3Ibsytt6fAfd2RCqAg=";
        AppInfo.appKey = "J6IPlk5AEU+2/Yi59rfYnsFQtdtOgAo9GAzysx8ciOM=";
        AppInfo.setLevel("1");
        /**
         * 进行认证
         */
        sendRegister();
        /**
         * 啟動背景監控機制
         */
        MitakeMonitor mitakeMonitor = MitakeMonitor.getInstance();
        mitakeMonitor.setNetworkListener(new MitakeMonitor.INetworkListener()
        {
            @Override
            public boolean isNetworkAvailable()
            {
                /**
                 * 通知背景更新機制,手機端是否有可連線
                 */
                ConnectivityManager connectivityManager = (ConnectivityManager)com.cvicse.base.ui.BaseApplication.getInstance().getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

                if(networkInfo != null && true == networkInfo.isAvailable() && true == networkInfo.isConnected())
                {
                    return true;
                }
                else
                {
                    return false;
                }
            }

            @Override
            public int getRefreshMillisSecond()
            {
                /**
                 * 背景更新秒數
                 */
                return 5000;
            }

            @Override
            public void networkStatus(int status)
            {
                if(status == MitakeMonitor.NETWORK_AVAILABLE)
                {
                    /**
                     * 重新發送Auth去取得相關資訊
                     */
                    sendRegister();
                }
                else
                {
                    /**
                     * 無可建立的連線
                     */
                }
            }
        });

        mitakeMonitor.running = true;

        ThreadPoolManager.execute(mitakeMonitor);
    }

    private static void sendRegister()
    {
        /**
         * 目前需连到全真环境
         */
//        Network.getInstance().setNetworkType(Network.UAT);

        RegisterRequest request = new RegisterRequest();
        request.send(AppInfo.appKey, "2", com.cvicse.base.ui.BaseApplication.getInstance(), new IResponseCallback()
        {
            @Override
            public void callback(Response response)
            {
                RegisterResponse registerResponse = (RegisterResponse)response;

                if(registerResponse.sucess == true)
                {
                    /**
                     * 认证成功
                     */
                    latch.countDown();
                }
                else
                {

                }
            }

            @Override
            public void exception(int code, String message)
            {
                /**
                 * 顯示認證失敗
                 */
            }
        });
    }
}
