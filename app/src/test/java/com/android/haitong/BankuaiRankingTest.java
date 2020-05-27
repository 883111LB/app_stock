package com.android.haitong;

import android.util.Log;

import com.mitake.core.RankingItem;
import com.mitake.core.request.BankuaiRankingRequest;
import com.mitake.core.response.BankuaiRankingResponse;
import com.mitake.core.response.IResponseCallback;
import com.mitake.core.response.Response;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;

import java.util.ArrayList;

/**
 * Created by liu_zlu on 2016/12/22 14:18
 */
@RunWith(RobolectricGradleTestRunner.class)
//@Config(constants = com.cvicse.stock.BuildConfig.class, sdk = 21)
public class BankuaiRankingTest extends BaseRobolectricTestCase{

    @Test
    public void testQuery(){
        BankuaiRankingRequest bankuaiRankingRequest=new BankuaiRankingRequest();
        bankuaiRankingRequest.send("Trade",0, new IResponseCallback() {
            @Override
            public void callback(Response response) {
                BankuaiRankingResponse bankuaiRankingResponse= (BankuaiRankingResponse) response;
                ArrayList<RankingItem> list = bankuaiRankingResponse.list;
            }

            @Override
            public void exception(int i, String s) {
                Log.e("","");
            }
        });
    }
}
