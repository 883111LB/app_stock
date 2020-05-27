package com.cvicse.stock.markets.presenter.marketdetail;

import com.cvicse.base.mvp.BasePresenter;
import com.cvicse.stock.common.Setting.Setting;
import com.cvicse.stock.http.ResponseCallback;
import com.cvicse.stock.markets.presenter.marketdetail.contract.FundSummaryContract;
import com.mitake.core.request.F10Request;
import com.mitake.core.request.FndDivideEndRequest;
import com.mitake.core.request.FundValueRequest;
import com.mitake.core.response.F10V2Response;
import com.mitake.core.response.Response;

/**
 * Created by liu_zlu on 2017/2/13 21:29
 */
public class FundSummaryPresenter extends BasePresenter implements FundSummaryContract.Presenter {

    private static final String SRC = "d";

    private FundSummaryContract.View view;

    private String stockId;

    @Override
    public void setView(FundSummaryContract.View view) {
        this.view = view;
    }

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
     * 查询公司摘要信息
     */
    @Override
    public void querySummary() {
        queryFundValue();
        queryFundNetWorth();
    }

    /**
     * 查询基金净值（五日）
     */
    private void queryFundValue(){
        FundValueRequest request = new FundValueRequest();
       // fundValueRequest.send(stockId, type + "", new ResponseCallback(fundValueRequest) {

        F10Request.SRC = Setting.getDataSource();  // new
        request.send(stockId, new ResponseCallback(request) {
            @Override
            public void onBack(Response response) {
                F10V2Response f10V2Response = (F10V2Response) response;
                view.onFundValueSuccess(f10V2Response.infos);
            }

            @Override
            public void onError(int i, String s) {

            }
        });
    }

    /**
     * 查询基金分红
     */
    private void queryFundNetWorth(){
        FndDivideEndRequest request = new FndDivideEndRequest();
        F10Request.SRC = Setting.getDataSource();  // new
        request.sendV2(stockId, new ResponseCallback(request) {
            @Override
            public void onBack(Response response) {
                F10V2Response mF10V2Response = (F10V2Response) response;
                view.onFundNetWorthSuccess(mF10V2Response.info);
            }

            @Override
            public void onError(int i, String s) {

            }
        });
    }
}
