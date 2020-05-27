package com.cvicse.stock.http.loop;

import com.mitake.core.request.BankuaisortingRequest;
import com.mitake.core.response.BankuaisortingResponse;

/**
 * 板块排序循环请求
 * Created by tang_xqing on 2017/11/29
 */
public abstract class BankuaiSortingRunable extends RunTaskState<BankuaisortingResponse> {
    // 股票名
    private String symbol;
    private String param;

    public BankuaiSortingRunable(String symbol,String param ){
        this.symbol = symbol;
        this.param = param;
    }

    @Override
    public void runInner() {
        BankuaisortingRequest bankuaisortingRequest = new BankuaisortingRequest();
        this.setRequest(bankuaisortingRequest);
        bankuaisortingRequest.send(symbol,param,this);
    }
}
