package com.cvicse.stock.http.loop;

import com.mitake.core.request.offer.OfferQuoteRequest;
import com.mitake.core.response.offer.OfferQuoteResponse;

/**
 * 要约
 * Created by tang_hua on 2020/2/28.
 */

public abstract class YaoyueInfoRunnable extends RunTaskState<OfferQuoteResponse>  {

    private String code;

    public YaoyueInfoRunnable() {
    }
    public YaoyueInfoRunnable(String code) {
        this.code = code;
    }

    @Override
    public void runInner() {
        OfferQuoteRequest request = new OfferQuoteRequest();
        this.setRequest(request);
        request.send(code, this);
    }

}
