package com.cvicse.stock.http.loop;

import com.mitake.core.request.OptionQuoteRequest;
import com.mitake.core.response.OptionQuoteResponse;

/* 期权-商品行情
 * Created by tang_xqing on 2018/8/2.
 */

public abstract class OptionQuoteRunnable extends RunTaskState<OptionQuoteResponse> {

    private String stockID;
    private String page;

    public OptionQuoteRunnable(String stockID, String page) {
        this.stockID = stockID;
        this.page = page;
    }

    @Override
    public void runInner() {
        OptionQuoteRequest optionQuoteRequest = new OptionQuoteRequest();
        optionQuoteRequest.send(stockID, page,this);
    }
}
