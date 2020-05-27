package com.cvicse.stock.http.loop;

import com.mitake.core.request.OptionTQuoteRequest;
import com.mitake.core.response.OptionTQuoteResponse;

/* 期权-T型报价
 * Created by tang_xqing on 2018/8/2.
 */

public abstract class OptionTQuoteRunnable extends RunTaskState<OptionTQuoteResponse> {

    private String stockID;
    private String date;

    public OptionTQuoteRunnable(String stockID, String date) {
        this.stockID = stockID;
        this.date = date;
    }

    @Override
    public void runInner() {
        OptionTQuoteRequest optionTQuoteRequest = new OptionTQuoteRequest();
        optionTQuoteRequest.send(stockID,date,this);
    }
}
