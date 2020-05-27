package com.cvicse.stock.http.loop;

import com.mitake.core.request.DRQuoteListRequest;
import com.mitake.core.response.DRQuoteListResponse;

/**
 * Created by shi_yhui on 2018/11/26.
 */

public abstract class DRQuoteListRunable extends RunTaskState<DRQuoteListResponse> {

    private String param;
    private String code;

//    public DRQuoteListRunable(String code,String param) {
//        this.param = param;
//        this.code = code;
//    }

    public DRQuoteListRunable(String code, String param) {
        super();
        this.param = param;
        this.code = code;
    }

    @Override
    public void runInner() {
        DRQuoteListRequest DRQuoteListRequest = new DRQuoteListRequest();
        DRQuoteListRequest.send(code,param,this);
    }
}
