package com.cvicse.stock.http.loop;

import com.mitake.core.request.HSAmountRequest;
import com.mitake.core.response.HSAmountResponse;

/** 沪深股通额度
 * Created by tang_xqing on 2018/5/16.
 */
public abstract class HSAmountRunnable extends RunTaskState<HSAmountResponse>{

    @Override
    public void runInner() {
        HSAmountRequest hsAmountRequest = new HSAmountRequest();
        hsAmountRequest.send(this);
    }
}
