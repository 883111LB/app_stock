package com.cvicse.stock.markets.presenter.marketdetail;

import com.cvicse.base.mvp.BasePresenter;
import com.cvicse.stock.common.Setting.Setting;
import com.cvicse.stock.http.ResponseCallback;
import com.cvicse.stock.markets.presenter.marketdetail.contract.BoundsInterestContract;
import com.mitake.core.request.BndInterestPayRequest;
import com.mitake.core.request.F10Request;
import com.mitake.core.response.F10V2Response;
import com.mitake.core.response.Response;

/**
 * Created by Administrator on 2017/6/20.
 */

public class BoundsInterestPresenter extends BasePresenter implements BoundsInterestContract.Presenter {

    private String stockId;
    private BoundsInterestContract.View view;

    /**
     * 初始化股票代码
     *
     * @param stockId
     */
    @Override
    public void init(String stockId) {
        this.stockId = stockId;
    }

    /**
     * 查询付息情况
     */
    @Override
    public void queryBundInterestPay() {
        BndInterestPayRequest request = new BndInterestPayRequest();
        F10Request.SRC = Setting.getDataSource();  // new
        request.send(stockId, new ResponseCallback(request) {
            @Override
            public void onBack(Response response) {
                F10V2Response f10V2Response = (F10V2Response) response;
                view.onqueryBundInterestPaySuccess(f10V2Response.info);
            }

            @Override
            public void onError(int i, String s) {
                view.onqueryBundInterestPayFail();
            }
        });
    }

    @Override
    public void setView(BoundsInterestContract.View view) {
        this.view = view;
    }
}
