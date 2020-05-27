package com.cvicse.stock.http.loop;

import com.cvicse.stock.http.ResponseCallback;
import com.mitake.core.request.UpdownsRequest;
import com.mitake.core.response.UpdownsResponse;

/** 沪深A股及指数涨跌平家数
 * Created by tang_xqing on 2018/5/11
 */
public abstract class UpDownSameRunable extends RunTaskState<UpdownsResponse> {
    private String code;

    public UpDownSameRunable(String code){
        this.code = code;
    }

    @Override
    public void runInner() {
        UpdownsRequest updownsRequest = new UpdownsRequest();
        this.setRequest(updownsRequest);
        updownsRequest.send(code,this);
    }
}
