package com.cvicse.stock.markets.presenter.marketdetail;

import com.cvicse.base.mvp.BasePresenter;
import com.cvicse.stock.common.Setting.Setting;
import com.cvicse.stock.http.ResponseCallback;
import com.cvicse.stock.markets.presenter.marketdetail.contract.FundBasicContract;
import com.mitake.core.request.AssetAllocationRequest;
import com.mitake.core.request.F10Request;
import com.mitake.core.request.FundBasicRequest;
import com.mitake.core.request.IndustryPortfolioRequest;
import com.mitake.core.request.ShareStructureRequest;
import com.mitake.core.request.StockPortfolioRequest;
import com.mitake.core.response.F10V2Response;
import com.mitake.core.response.Response;

/**
 * Created by ruan_ytai on 17-3-29.
 */

public class FundBasicPresenter extends BasePresenter implements FundBasicContract.Presenter {

    private static final String SRC = "d";

    private FundBasicContract.View view;
    private String stockId;

    private ResponseCallback fundBasicCallback, fundAssetAllocationCallback,
            fundShareStructureCallback,fundIndustryPortfolioCallback,
            fundStockPortfolioCallback;

    @Override
    public void init(String stockId) {
        this.stockId = stockId;
    }

    @Override
    public void queryFundData() {
        queryFundBasic();
        queryFundAssetAllocation();
        queryFundShareStructure();
        queryFundIndustryPortfolio();
        queryFundStockPortfolio();
    }

    /**
     * 查询基金概况
     */
    private void queryFundBasic(){
        FundBasicRequest request = new FundBasicRequest();
        F10Request.SRC = Setting.getDataSource();  // new
        request.send(stockId,fundBasicCallback =  new ResponseCallback(request) {
            @Override
            public void onBack(Response response) {
                F10V2Response mF10V2Response = (F10V2Response) response;
                view.onQueryFundBasicSuccess(mF10V2Response.info);
            }

            @Override
            public void onError(int i, String s) {

            }
        });
    }

    /**
     * 查询资产配置
     */
    private void queryFundAssetAllocation(){
        AssetAllocationRequest request = new AssetAllocationRequest();
        F10Request.SRC = Setting.getDataSource();  // new
        request.send(stockId, new ResponseCallback(request) {
            @Override
            public void onBack(Response response) {
                F10V2Response mF10V2Response = (F10V2Response) response;
                view.onQueryFundAssetAllocationSuccess(mF10V2Response.infos);
            }

            @Override
            public void onError(int i, String s) {

            }
        });
    }

    /**
     * 查询份额结构
     */
    private void queryFundShareStructure(){
        ShareStructureRequest request = new ShareStructureRequest();
        F10Request.SRC = Setting.getDataSource();  // new
        request.send(stockId, new ResponseCallback(request) {
            @Override
            public void onBack(Response response) {
                F10V2Response mF10V2Response = (F10V2Response) response;
                view.onQueryFundShareStructureSuccess(mF10V2Response.infos);
            }

            @Override
            public void onError(int i, String s) {

            }
        });

    }

    /**
     * 查询行业组合
     */
    private void queryFundIndustryPortfolio(){
        IndustryPortfolioRequest request = new IndustryPortfolioRequest();
        F10Request.SRC = Setting.getDataSource();  // new
        request.send(stockId, new ResponseCallback(request) {
            @Override
            public void onBack(Response response) {
                F10V2Response mF10V2Response = (F10V2Response) response;
                view.onQueryFundIndustryPortfolioSuccess(mF10V2Response.infos);
            }

            @Override
            public void onError(int i, String s) {

            }
        });
    }

    /**
     * 查询股票组合
     */
    private void queryFundStockPortfolio(){
        StockPortfolioRequest request = new StockPortfolioRequest();
        F10Request.SRC = Setting.getDataSource();  // new
        request.send(stockId, new ResponseCallback(request) {
            @Override
            public void onBack(Response response) {
                F10V2Response mF10V2Response = (F10V2Response) response;
                view.onQueryFundStockPortfolioSuccess(mF10V2Response.infos);
            }

            @Override
            public void onError(int i, String s) {

            }
        });
    }

    @Override
    public void setView(FundBasicContract.View view) {
        this.view = view;
    }
}
