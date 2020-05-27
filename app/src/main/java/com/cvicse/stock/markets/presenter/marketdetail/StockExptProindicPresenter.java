package com.cvicse.stock.markets.presenter.marketdetail;

import com.cvicse.base.mvp.BasePresenter;
import com.cvicse.stock.common.Setting.Setting;
import com.cvicse.stock.http.ResponseCallback;
import com.cvicse.stock.markets.presenter.marketdetail.contract.StockExptProindicContract;
import com.mitake.core.request.F10Request;
import com.mitake.core.request.F10V2Request;
import com.mitake.core.response.F10V2Response;
import com.mitake.core.response.Response;

import java.util.HashMap;
import java.util.List;

public class StockExptProindicPresenter extends BasePresenter implements StockExptProindicContract.Presenter {
    private StockExptProindicContract.View view;
    private String stockId;

    @Override
    public void setView(StockExptProindicContract.View view) {
        this.view = view;
    }
    @Override
    public void init(String mCode) {
        this.stockId=mCode;
    }
    @Override
    public void requestProindic(String mApiType) {
        F10V2Request proindicRequest = new F10V2Request();
//        F10Request.SRC = Setting.getDataSource();
        proindicRequest.send(stockId, "d", mApiType, new ResponseCallback() {
            @Override
            public void onBack(Response response) {
                F10V2Response f10V2Response= (F10V2Response) response;
                if( null == f10V2Response || null == f10V2Response.info || f10V2Response.info.isEmpty()){
                    return;
                }
                List<HashMap<String,Object>> infoList= (List<HashMap<String, Object>>) f10V2Response.info.get("List");
                view.onProindicSuccess(infoList);
            }

            @Override
            public void onError(int i, String s) {

            }
        });
    }

    @Override
    public void requestExpt(String mApiType) {
        F10V2Request exptRequest = new F10V2Request();
//        F10Request.SRC = Setting.getDataSource();
        exptRequest.send(stockId, "d", mApiType, new ResponseCallback(exptRequest) {

            @Override
            public void onBack(Response response) {
                F10V2Response f10V2Response= (F10V2Response) response;
                if( null == f10V2Response || null == f10V2Response.info || f10V2Response.info.isEmpty()){
                    return;
                }
                List<HashMap<String,Object>> infoList= (List<HashMap<String, Object>>) f10V2Response.info.get("List");
                view.onExptSuccess(infoList);
            }

            @Override
            public void onError(int i, String s) {

            }
        });
    }


}
