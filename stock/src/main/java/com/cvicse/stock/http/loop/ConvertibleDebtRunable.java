package com.cvicse.stock.http.loop;

import com.mitake.core.request.ConvertibleDebtRequest;
import com.mitake.core.response.ConvertibleBoundResponse;

/** AH股行情
 * Created by tang_xqing on 2018/5/11
 */
public abstract class ConvertibleDebtRunable extends RunTaskState<ConvertibleBoundResponse> {
    private String code;

    public ConvertibleDebtRunable(String code){
        this.code = code;
    }

    @Override
    public void runInner() {
        ConvertibleDebtRequest convertibleDebtRequest = new ConvertibleDebtRequest();
        convertibleDebtRequest.send(code,this);
    }
}
