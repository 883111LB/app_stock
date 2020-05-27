package com.cvicse.stock.http.loop;

import com.mitake.core.request.TickRequest;
import com.mitake.core.response.TickResponse;

/**
 * Created by Administrator on 2017/7/6.
 * 成交明细循环请求
 * 分时明细接口 level 1回的是分笔数据、level 2则是逐笔数据
 */

public abstract class DetailRunnable extends RunTaskState<TickResponse> {

    private String id;
    private String page;
    private String subtype;

    public DetailRunnable(String id, String page, String market, String subtype){
        this.id = id;
        this.page = page;
        this.subtype = subtype;
    }

    /**
     * 真实执行循环请求的方法
     */
    @Override
    public void runInner() {
        TickRequest tickRequest = new TickRequest();
        this.setRequest(tickRequest);
        tickRequest.send(id,page,subtype,this);
    }
}
