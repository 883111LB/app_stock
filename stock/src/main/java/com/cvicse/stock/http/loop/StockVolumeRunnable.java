package com.cvicse.stock.http.loop;

import com.mitake.core.request.MoreVolumeRequest;
import com.mitake.core.response.MoreVolumeResponse;

/**
 * 分量
 * Created by tang_hua on 2020/2/28.
 */

public abstract class StockVolumeRunnable extends RunTaskState<MoreVolumeResponse>  {

    private String code;
    private String subtype;

    public StockVolumeRunnable() {
    }
    public StockVolumeRunnable(String code, String subtype) {
        this.code = code;
        this.subtype = subtype;
    }

    @Override
    public void runInner() {
        MoreVolumeRequest request = new MoreVolumeRequest();
        this.setRequest(request);
        request.send(code, subtype, this);
    }

}
