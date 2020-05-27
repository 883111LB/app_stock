package com.cvicse.stock.http.loop;

import com.mitake.core.request.BanKuaiQuoteRequest;
import com.mitake.core.response.BankuaisortingResponse;

/**
 * 个股所属板块循环请求
 * Created by tang_xqing on 2017/11/29
 */
public abstract class BankuaiQuoteRunable extends RunTaskState<BankuaisortingResponse> {
    // 股票名
    private String code;
    // 地区板块
    private String params;

    public BankuaiQuoteRunable(String code){
        this.code = code;
        this.params = null;
    }

    public BankuaiQuoteRunable(String code, String params){
        this.code = code;
        this.params = params;
    }

    @Override
    public void runInner() {
        if(null == params || "".equals(params)) {
            BanKuaiQuoteRequest banKuaiQuoteRequest = new BanKuaiQuoteRequest();
            this.setRequest(banKuaiQuoteRequest);
            banKuaiQuoteRequest.send(code,this);
        } else {
            BanKuaiQuoteRequest banKuaiQuoteRequest = new BanKuaiQuoteRequest();
            this.setRequest(banKuaiQuoteRequest);
            banKuaiQuoteRequest.send(code, params, this);
        }
    }
}
