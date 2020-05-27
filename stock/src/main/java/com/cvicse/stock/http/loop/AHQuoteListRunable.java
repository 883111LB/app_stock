package com.cvicse.stock.http.loop;

import com.mitake.core.request.AHQuoteListRequest;
import com.mitake.core.response.AHQuoteListResponse;

/**
 * Created by tang_xqing on 2018/5/14.
 */
public abstract class AHQuoteListRunable extends RunTaskState<AHQuoteListResponse> {

    private String param;

    public AHQuoteListRunable(String param) {
        this.param = param;
    }

    @Override
    public void runInner() {
        AHQuoteListRequest ahQuoteListRequest = new AHQuoteListRequest ();
        ahQuoteListRequest.send(param,this);
    }
}
