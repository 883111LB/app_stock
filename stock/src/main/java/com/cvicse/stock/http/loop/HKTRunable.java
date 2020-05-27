package com.cvicse.stock.http.loop;

import com.mitake.core.request.chart.TongLineRequest;
import com.mitake.core.response.chart.TongLineResponse;

/**
 * Created by liu_zlu on 2017/3/14 13:15
 */
public abstract class HKTRunable extends RunTaskState<TongLineResponse> {
    private String flag;

    public HKTRunable(String flag){
        this.flag = flag;
    }

    @Override
    public void runInner() {
        TongLineRequest request = new TongLineRequest();
        this.setRequest(request);
        request.send(flag, this);
    }
}
