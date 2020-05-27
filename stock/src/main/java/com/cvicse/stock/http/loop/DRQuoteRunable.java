package com.cvicse.stock.http.loop;

import com.mitake.core.request.DRLinkQuoteRequest;
import com.mitake.core.response.DRLinkQuoteResponse;

/**
 * Created by shi_yhui on 2018/11/28.
 */

public abstract class DRQuoteRunable extends RunTaskState<DRLinkQuoteResponse> {

    private String code;

    public DRQuoteRunable(String code){
        this.code=code;
    }
    @Override
    public void runInner(){
        DRLinkQuoteRequest drLinkQuoteRequest=new DRLinkQuoteRequest();
        drLinkQuoteRequest.send(code,this);
    }
}
