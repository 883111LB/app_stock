package com.cvicse.stock.markets.presenter.marketdetail;

import com.cvicse.base.mvp.BasePresenter;
import com.cvicse.stock.common.Setting.Setting;
import com.cvicse.stock.http.ResponseCallback;
import com.cvicse.stock.markets.presenter.marketdetail.contract.StockShareIinvholdchgContract;
import com.mitake.core.request.F10Request;
import com.mitake.core.request.F10V2Request;
import com.mitake.core.response.F10V2Response;
import com.mitake.core.response.Response;

import java.util.HashMap;
import java.util.List;

/** 股东深度挖掘
 * Created by tang_xqing on 2018/4/18.
 */

public class StockShareIinvholdchgPresenter extends BasePresenter implements StockShareIinvholdchgContract.Presenter {
    private StockShareIinvholdchgContract.View view;

    @Override
    public void requestShareIivholdchg(String code, String type) {
        F10V2Request f10Request = new F10V2Request();
        F10Request.SRC = Setting.getDataSource();  // new
        f10Request.send(code,F10Request.SRC, type, new ResponseCallback(f10Request) {
            @Override
            public void onBack(Response response) {
                F10V2Response f10V2Response = (F10V2Response) response;
                List<HashMap<String, Object>> infos = f10V2Response.infos;
                if(null == f10V2Response || null == infos || infos.isEmpty()){
                    return;
                }
                view.requestShareIivholdchgSuccesss(infos);
            }

            @Override
            public void onError(int i, String s) {
                view.requestShareIivholdchgFail(i,s);
            }
        });
    }

    @Override
    public void setView(StockShareIinvholdchgContract.View view) {
        this.view = view;
    }
}
