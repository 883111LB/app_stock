package com.cvicse.stock.http.loop;

import com.cvicse.stock.http.requestWrap.QuoteWrapRequest;
import com.mitake.core.response.QuoteResponse;

/**
 * Created by liu_zlu on 2017/3/14 13:15
 */
public abstract class QuoteRunable extends RunTaskState<QuoteResponse> {
    private String id;
    private String market;
    private QuoteWrapRequest quoteWrapRequest;
    public QuoteRunable(String id, String market){
        this.id = id;
        this.market = market;
        quoteWrapRequest = new QuoteWrapRequest(id,this);
        //setRequest(quoteWrapRequest);
    }
    @Override
    public void runInner() {
        quoteWrapRequest.request();
    }
}
