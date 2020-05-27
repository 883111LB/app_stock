package com.cvicse.stock.markets.presenter.marketdetail;

import com.cvicse.base.mvp.BasePresenter;
import com.cvicse.stock.common.Setting.Setting;
import com.cvicse.stock.http.ResponseCallback;
import com.cvicse.stock.markets.presenter.marketdetail.contract.BondsRepurchaseContract;
import com.mitake.core.request.BndBuyBacksRequest;
import com.mitake.core.request.F10Request;
import com.mitake.core.response.F10V2Response;
import com.mitake.core.response.Response;

/**
 * Created by Administrator on 2017/6/20.
 */

public class BondsRepurchasePresenter extends BasePresenter implements BondsRepurchaseContract.Presenter {

    private String stockId;
    private BondsRepurchaseContract.View view;

    /**
     * 初始化股票Id
     *
     * @param stockId
     */
    @Override
    public void init(String stockId) {
        this.stockId = stockId;
    }

    /**
     * 查询债券回购情况
     */
    @Override
    public void queryBndBuyBacks() {
        BndBuyBacksRequest request = new BndBuyBacksRequest();
        F10Request.SRC = Setting.getDataSource();  // new
        request.send(stockId, new ResponseCallback(request) {
            @Override
            public void onBack(Response response) {
                F10V2Response f10V2Response = (F10V2Response) response;
                view.onQuerybndBuyBacks(f10V2Response.info);
            }

            @Override
            public void onError(int i, String s) {
                view.onQuerybndBuyBacksFail();
            }
        });
    }

    @Override
    public void setView(BondsRepurchaseContract.View view) {
        this.view = view;
    }
}
