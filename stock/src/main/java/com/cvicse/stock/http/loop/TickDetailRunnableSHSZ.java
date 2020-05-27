package com.cvicse.stock.http.loop;

import com.mitake.core.request.L2TickDetailRequestV2;
import com.mitake.core.response.L2TickDetailResponseV2;

/** 沪深市场L2逐笔
 * Created by Administrator on 2017/7/6.
 */

public abstract class TickDetailRunnableSHSZ extends RunTaskState<L2TickDetailResponseV2> {
    //股票代码
    private String stockId;

    //分页
    private String param;

    //市场类别
    private String market;

    //次类别
    private String subType;

    public TickDetailRunnableSHSZ(String stockId, String param, String market, String subType){
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
        L2TickDetailRequestV2 tickDetailRequest = new L2TickDetailRequestV2();
        setRequest(tickDetailRequest);
        tickDetailRequest.send(stockId,param,market+"1000",this);
    }
}
