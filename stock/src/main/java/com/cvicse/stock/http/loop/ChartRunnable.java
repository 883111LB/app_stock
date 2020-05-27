package com.cvicse.stock.http.loop;

import com.mitake.core.QuoteItem;
import com.mitake.core.request.ChartRequestV2;
import com.mitake.core.request.PointAddType;
import com.mitake.core.response.ChartResponse;

/**
 * Created by liu_zlu on 2017/5/1 17:34
 */
public abstract class ChartRunnable extends RunTaskState<ChartResponse> {
    private String code;
    private String type;
    private QuoteItem quoteItem;
    public ChartRunnable(String code, String type){
        this.code = code;
        this.type = type;
    }
    public ChartRunnable(String code, QuoteItem quoteItem, String type){
        this.code = code;
        this.quoteItem = quoteItem;
        this.type = type;
    }
    @Override
    public void runInner() {
        ChartRequestV2 chartRequest = new ChartRequestV2();
        this.setRequest(chartRequest);
        chartRequest.send(code, type, PointAddType.ADDALL_2,this);
    }
}

