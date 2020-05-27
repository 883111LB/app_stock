package com.cvicse.stock.http.loop;

import com.mitake.core.request.TickDetailRequest;
import com.mitake.core.response.TickDetailResponse;

/**
 * Created by Administrator on 2017/7/6.
 */

public abstract class TickDetailRunnable extends RunTaskState<TickDetailResponse> {
    //股票代码
    private String stockId;

    //分页
    private String param;

    //市场类别
    private String market;

    //次类别
    private String subType;

    public TickDetailRunnable(String stockId,String param,String market,String subType){
        this.stockId = stockId;
        this.param = param;
        this.market = market;
        this.subType = subType;
    }


    /**
     * 真实执行循环请求的方法
     */
    @Override
    public void runInner() {
        TickDetailRequest tickDetailRequest = new TickDetailRequest();
        setRequest(tickDetailRequest);
        tickDetailRequest.send(stockId,param,market+subType,this);
//        tickDetailRequest.send(stockId,param,market,subType,this);
    }
}
