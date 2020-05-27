package com.cvicse.stock.http.loop;

import com.mitake.core.request.offer.OfferQuoteRequest;
import com.mitake.core.response.offer.OfferQuoteResponse;

/**
 * 要约
 * Created by tang_hua on 2020/2/28.
 */

public abstract class YaoyueRunnable extends RunTaskState<OfferQuoteResponse>  {

    private int start;
    private int count;
    private int sortField;
    private String sortType;

    public YaoyueRunnable() {
    }
    public YaoyueRunnable(int start, int count, int sortField, String sortType) {
        this.start = start;
        this.count = count;
        this.sortField = sortField;
        this.sortType = sortType;
    }

    @Override
    public void runInner() {
        OfferQuoteRequest request = new OfferQuoteRequest();
        this.setRequest(request);
        request.send(start, count, sortField, sortType, this);
    }

}
