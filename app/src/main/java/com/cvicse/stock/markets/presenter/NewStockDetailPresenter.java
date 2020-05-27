package com.cvicse.stock.markets.presenter;

import com.cvicse.base.mvp.BasePresenter;
import com.cvicse.stock.common.Setting.Setting;
import com.cvicse.stock.http.ResponseCallback;
import com.cvicse.stock.markets.presenter.contract.NewStockDetailContract;
import com.mitake.core.NewShareDetail;
import com.mitake.core.request.F10Request;
import com.mitake.core.request.NewShareDetailRequest;
import com.mitake.core.response.NewShareDetailResponse;
import com.mitake.core.response.Response;

/**
 * Created by ruan_ytai on 17-3-1.
 */

public class NewStockDetailPresenter extends BasePresenter implements NewStockDetailContract.Presenter{

    private NewStockDetailContract.View view;
    /**
     * 请求股票详细信息
     * @param tradingCode 股票代码
     */
    @Override
    public void requestStockDetail(String tradingCode) {
        NewShareDetailRequest newShareDetailRequest = new NewShareDetailRequest();
        F10Request.SRC = Setting.getDataSource();
        newShareDetailRequest.sendv2(tradingCode,F10Request.SRC, new ResponseCallback(newShareDetailRequest) {
            @Override
            public void onBack(Response response) {
                NewShareDetailResponse newShareDetailResponse = (NewShareDetailResponse) response;
                NewShareDetail info = newShareDetailResponse.info;
                view.onRequestStockDetailSuccess(info);
            }

            @Override
            public void onError(int i, String s) {

            }
        });
    }

    @Override
    public void setView(NewStockDetailContract.View view) {
        this.view = view;
    }
}
