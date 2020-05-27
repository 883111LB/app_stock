package com.android.haitong;

import com.mitake.core.AppInfo;
import com.mitake.core.network.MitakeMonitor;
import com.mitake.core.network.ThreadPoolManager;
import com.mitake.core.request.RegisterRequest;
import com.mitake.core.response.IResponseCallback;
import com.mitake.core.response.RegisterResponse;
import com.mitake.core.response.Response;

import org.robolectric.RuntimeEnvironment;

/**
 * Created by liu_zlu on 2016/12/22 14:32
 */
public class BaseTest {
    static {
        init();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void init()
    {
        /**
         * 初始化元件註冊所需資訊
         */
        AppInfo.build(RuntimeEnvironment.application);
        /**
         * 填入所申请的key
         */
//        AppInfo.appKey = "gzreyQFcje5eWB8m+MSnRZKty3Ibsytt6fAfd2RCqAg=";    //生产
        AppInfo.appKey = "J6IPlk5AEU+2/Yi59rfYnsFQtdtOgAo9GAzysx8ciOM=";
        AppInfo.setLevel("1");
//        AppInfo.infoLevel = "1";
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
               /* ConnectivityManager connectivityManager = (ConnectivityManager)MainActivity.this.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

                if(networkInfo != null && true == networkInfo.isAvailable() && true == networkInfo.isConnected())
                {
                    return true;
                }
                else
                {
                    return false;
                }*/
                return true;
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
        request.send(AppInfo.appKey, "2", RuntimeEnvironment.application, new IResponseCallback()
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
