package com.cvicse.stock.http.loop;

import com.mitake.core.request.HKMarInfoRequest;
import com.mitake.core.response.HKMarInfoResponse;

/**两市港股通额度资讯
 * Created by liu_zlu on 2017/3/20 15:50
 */
public abstract class HKMarInfoRunnable  extends RunTaskState<HKMarInfoResponse> {

    @Override
    public void runInner() {
        HKMarInfoRequest mHKMarInfoRequest  = new HKMarInfoRequest();
        this.setRequest(mHKMarInfoRequest);
        mHKMarInfoRequest.send(this);
    }
}
