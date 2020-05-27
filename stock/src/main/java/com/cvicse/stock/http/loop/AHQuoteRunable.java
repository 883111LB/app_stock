package com.cvicse.stock.http.loop;

import com.mitake.core.request.AHQuoteRequest;
import com.mitake.core.response.AHQuoteResponse;

/** AH股行情
 * Created by tang_xqing on 2018/5/11
 */
public abstract class AHQuoteRunable extends RunTaskState<AHQuoteResponse> {
    private String code;

    public AHQuoteRunable(String code){
        this.code = code;
    }

    @Override
    public void runInner() {
        AHQuoteRequest ahQuoteRequest = new AHQuoteRequest ();
        ahQuoteRequest.send(code,this);
    }
}
