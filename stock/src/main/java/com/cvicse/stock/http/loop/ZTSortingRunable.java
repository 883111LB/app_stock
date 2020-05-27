package com.cvicse.stock.http.loop;

import com.mitake.core.request.ZTSortingRequest;
import com.mitake.core.response.ZTSortingResponse;

public abstract class ZTSortingRunable extends RunTaskState<ZTSortingResponse>{
    private String param;
    private String type;

    public ZTSortingRunable(String param,String type){
        this.param=param;
        this.type=type;
    }
    @Override
    public void runInner(){
        ZTSortingRequest request = new ZTSortingRequest();
        request.send(param,type,this);
    }
}
