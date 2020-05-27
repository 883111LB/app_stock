package com.cvicse.stock;

import android.os.Bundle;

import com.cvicse.stock.base.PBaseActivity;
import com.mitake.core.request.SearchRequest;

/**
 * 启动页
 * Created by liu_zlu on 2017/1/4 14:42
 */
public class SplashActivity extends PBaseActivity {

    //是否有本地文件
    public static boolean isHasLocalSearchFile = false;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.dowmLoadCodes("sh,hk,sz,bj,gb,cff");
        // TODO: 2017/11/14  待确定。下载股名表？
       /* searchRequest.send(this, null, null, 0, true, new ResponseCallback() {
            @Override
            public void onBack(Response response) {
                Log.d("SplashActivity","search local file download success");
                SplashActivity.isHasLocalSearchFile = true;
            }

            @Override
            public void onError(int i, String s) {

            }
        });*/
    }

    @Override
    protected void initData() {
        MainActivity.startActivity(SplashActivity.this);
        finish();
    }
}
