package com.cvicse.stock.http.loop;

import com.mitake.core.request.compound.CompoundUpDownRequest;
import com.mitake.core.response.compound.CompoundUpDownResponse;

/**
 * 复盘-涨跌分布
 * Created by tang_hua on 2020/2/28.
 */

public abstract class ReplayFPRunnable extends RunTaskState<CompoundUpDownResponse>  {

    private String market;
    private String time;
    private int dateType;

    public ReplayFPRunnable() {
    }
    public ReplayFPRunnable(String market, String time, int dateType) {
        this.market = market;
        this.time = time;
        this.dateType = dateType;
    }

    @Override
    public void runInner() {
        CompoundUpDownRequest request = new CompoundUpDownRequest();
        this.setRequest(request);
        request.send(market, time, dateType, this);
    }

}
