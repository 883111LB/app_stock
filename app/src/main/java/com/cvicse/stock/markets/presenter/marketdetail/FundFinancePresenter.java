package com.cvicse.stock.markets.presenter.marketdetail;

import com.cvicse.base.mvp.BasePresenter;
import com.cvicse.stock.common.Setting.Setting;
import com.cvicse.stock.http.ResponseCallback;
import com.cvicse.stock.markets.presenter.marketdetail.contract.FundFinanceContract;
import com.mitake.core.request.F10Request;
import com.mitake.core.request.FndFinanceRequest;
import com.mitake.core.response.F10V2Response;
import com.mitake.core.response.Response;

/**
 * Created by Administrator on 2017/6/20.
 */

public class FundFinancePresenter extends BasePresenter implements FundFinanceContract.Presenter {

    private FundFinanceContract.View view ;

    private String stockId;

    /**
     * 初始化stockId
     *
     * @param stockId
     */
    @Override
    public void init(String stockId) {
        this.stockId = stockId;
    }

    /**
     * 查询基金财务信息
     */
    @Override
    public void queryFinaMessage() {
        FndFinanceRequest request = new FndFinanceRequest();
        F10Request.SRC = Setting.getDataSource();  // new
        request.send(stockId, new ResponseCallback(request) {
            @Override
            public void onBack(Response response) {
                F10V2Response f10V2Response = (F10V2Response) response;
                view.onFinanceSuccess(f10V2Response.info);
            }

            @Override
            public void onError(int i, String s) {
                view.onFinanceFail();
            }
        });
    }

    @Override
    public void setView(FundFinanceContract.View view) {
        this.view = view;
    }
}
