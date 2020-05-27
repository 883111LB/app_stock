package com.cvicse.stock.http.loop;

import com.mitake.core.request.SubNewStockRankingRequest;
import com.mitake.core.response.SubNewStockRankingResponse;

/**
 * 次新股
 */

public abstract class SubNewStockRunable extends RunTaskState<SubNewStockRankingResponse> {

    private String param;

    public SubNewStockRunable(String param){
        this.param=param;
    }
    @Override
    public void runInner(){
        SubNewStockRankingRequest request = new SubNewStockRankingRequest();
        request.send(param,this);
    }
}
