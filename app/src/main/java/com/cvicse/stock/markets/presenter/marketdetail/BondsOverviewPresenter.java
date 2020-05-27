package com.cvicse.stock.markets.presenter.marketdetail;

import com.cvicse.base.mvp.BasePresenter;
import com.cvicse.stock.common.Setting.Setting;
import com.cvicse.stock.http.ResponseCallback;
import com.cvicse.stock.markets.presenter.marketdetail.contract.BondsOverviewContract;
import com.mitake.core.request.BondBasicRequest;
import com.mitake.core.request.F10Request;
import com.mitake.core.response.F10V2Response;
import com.mitake.core.response.Response;

/**
 * Created by Administrator on 2017/6/20.
 */

public class BondsOverviewPresenter extends BasePresenter implements BondsOverviewContract.Presenter {

    private BondsOverviewContract.View view;
    private String stockId;

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
     * 查询债券概况
     */
    @Override
    public void queryBondBasic() {
        BondBasicRequest request = new BondBasicRequest();
        F10Request.SRC = Setting.getDataSource();  // new
        request.send(stockId, new ResponseCallback(request) {
            @Override
            public void onBack(Response response) {
                F10V2Response f10V2Response = (F10V2Response) response;
                view.onQueryBondBasicSuccess(f10V2Response.info);
            }

            @Override
            public void onError(int i, String s) {

            }
        });
    }

    @Override
    public void setView(BondsOverviewContract.View view) {
        this.view = view;
    }
}
