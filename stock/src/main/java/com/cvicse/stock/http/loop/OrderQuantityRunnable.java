package com.cvicse.stock.http.loop;

import com.mitake.core.request.OrderQuantityRequest;
import com.mitake.core.response.OrderQuantityResponse;

/**
 * Created by liu_zlu on 2017/4/21 15:39
 */
public abstract class OrderQuantityRunnable extends RunTaskState<OrderQuantityResponse> {
    //证券代码
    private String symbolId;
    //市场类别
    private String maket;
    private String subtype;
    public OrderQuantityRunnable(String symbolId, String maket,String subtype){
        this.symbolId = symbolId;
        this.maket = maket;
        this.subtype = subtype;
    }
    @Override
    public void runInner() {
        OrderQuantityRequest orderQuantityRequest = new OrderQuantityRequest();
        this.setRequest(orderQuantityRequest);
        orderQuantityRequest.send(symbolId, maket,subtype,this);
//        orderQuantityRequest.send(symbolId,this);
    }
}
