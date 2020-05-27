package com.cvicse.stock.http.loop;

import com.mitake.core.request.SubNewBondStockRankingRequest;
import com.mitake.core.request.SubNewStockRankingRequest;
import com.mitake.core.response.SubNewStockRankingResponse;

/**
 * 次新股
 */

public abstract class SubNewBondStockRunable extends RunTaskState<SubNewStockRankingResponse> {

    private String param;

    public SubNewBondStockRunable(String param){
        this.param=param;
    }
    @Override
    public void runInner(){
        SubNewBondStockRankingRequest request = new SubNewBondStockRankingRequest();
        request.send(param,this);
    }
}
