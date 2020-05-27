package com.cvicse.stock.markets.presenter.marketdetail;

import com.cvicse.base.mvp.BasePresenter;
import com.cvicse.stock.common.Setting.Setting;
import com.cvicse.stock.http.ResponseCallback;
import com.cvicse.stock.markets.presenter.marketdetail.contract.StockFinanceContract;
import com.mitake.core.QuoteItem;
import com.mitake.core.request.F10Request;
import com.mitake.core.request.F10Type;
import com.mitake.core.request.F10V2Request;
import com.mitake.core.request.MainFinaDataNasRequest;
import com.mitake.core.request.MainFinaIndexNasRequest;
import com.mitake.core.response.F10V2Response;
import com.mitake.core.response.MainFinaDataNasResponse;
import com.mitake.core.response.MainFinaIndexNasResponse;
import com.mitake.core.response.Response;


/**
 * 财务页面
 * Created by liu_zlu on 2017/2/10 13:09
 */
public class StockFinancePresenter extends BasePresenter implements StockFinanceContract.Presenter {
    private StockFinanceContract.View view;

    private String stockId;
    private String market;

    @Override
    public void setView(StockFinanceContract.View view) {
        this.view = view;
    }

    /**
     * 初始化stockId
     *
     * @param quoteItem
     */
    @Override
    public void init(QuoteItem quoteItem) {
        ///this.quoteItem = quoteItem;
        stockId = quoteItem.id;
        market = quoteItem.market;
    }

    /**
     * 查询财务相关信息( )
     */
    @Override
    public void queryFinance() {
        queryMainFinaIndex();
        queryMainFinaDataNas();

    }

    /**
     * 查询财务指数
     */
    private void queryMainFinaIndex() {
        MainFinaIndexNasRequest mainFinaIndexNasRequest = new MainFinaIndexNasRequest();
        F10Request.SRC = Setting.getDataSource();  // new
        mainFinaIndexNasRequest.sendV2(stockId, new ResponseCallback(mainFinaIndexNasRequest) {
            @Override
            public void onBack(Response response) {
                MainFinaIndexNasResponse mainFinaIndexNasResponse = (MainFinaIndexNasResponse) response;
                view.onMainFinaIndexHasSuccess(mainFinaIndexNasResponse.info);
            }

            @Override
            public void onError(int i, String s) {

            }
        });
    }

    /**
     * 查询财务报表报表
     */
    private void queryMainFinaDataNas() {
        if( "hk".equals(market)){
            queryFinanceHK();
        }else{
            quueryFinanceOnProfinmainindex();
            quueryFinanceOnProincstatementnew();
            quueryFinanceOnProbalsheetnew();
            quueryFinanceOnProcfstatementnew();
        }
    }

    private void queryFinanceHK() {
        MainFinaDataNasRequest mainFinaDataNasRequest = new MainFinaDataNasRequest();
        F10Request.SRC = Setting.getDataSource();  // new
        mainFinaDataNasRequest.sendV2(stockId, new ResponseCallback(mainFinaDataNasRequest) {
            @Override
            public void onBack(Response response) {
                MainFinaDataNasResponse mMainFinaDataNasResponse = (MainFinaDataNasResponse) response;
                view.onMainFinaDataNasSuccess(mMainFinaDataNasResponse.mMainFinaDataNasList);
            }

            @Override
            public void onError(int i, String s) {

            }
        });
    }

    /**
     * 主要指标
     */
    private void quueryFinanceOnProfinmainindex(){
        F10V2Request profinmainindexRequest = new F10V2Request();
        F10Request.SRC = Setting.getDataSource();
        profinmainindexRequest.send(stockId,F10Request.SRC,F10Type.D_PROFINMAININDEX, new ResponseCallback(profinmainindexRequest) {
            @Override
            public void onBack(Response response) {
                F10V2Response  f10V2Response = (F10V2Response) response;
                if( null == f10V2Response || null == f10V2Response.infos || f10V2Response.infos.isEmpty()){
                    return;
                }
                view.onProfinmainindexSuccess(f10V2Response.infos.get(0));
            }

            @Override
            public void onError(int i, String s) {

            }
        });

    }

    /**
     * 利润表
     */
    private void quueryFinanceOnProincstatementnew(){
        F10V2Request profinmainindexRequest = new F10V2Request();
        F10Request.SRC = Setting.getDataSource();
        profinmainindexRequest.send(stockId,F10Request.SRC,F10Type.D_PROINCSTATEMENTNEW, new ResponseCallback(profinmainindexRequest) {
            @Override
            public void onBack(Response response) {
                F10V2Response  f10V2Response = (F10V2Response) response;
                if( null == f10V2Response || null == f10V2Response.infos || f10V2Response.infos.isEmpty()){
                    return;
                }
                view.onProincstatementnewSuccess(f10V2Response.infos.get(0));
            }

            @Override
            public void onError(int i, String s) {

            }
        });

    }

    /**
     * 资产负债表
     */
    private void quueryFinanceOnProbalsheetnew(){
        F10V2Request profinmainindexRequest = new F10V2Request();
        F10Request.SRC = Setting.getDataSource();
        profinmainindexRequest.send(stockId,F10Request.SRC,F10Type.D_PROBALSHEETNEW, new ResponseCallback(profinmainindexRequest) {
            @Override
            public void onBack(Response response) {
                F10V2Response  f10V2Response = (F10V2Response) response;
                if( null == f10V2Response || null == f10V2Response.infos || f10V2Response.infos.isEmpty()){
                    return;
                }
                view.onProbalsheetnewSuccess(f10V2Response.infos.get(0));
            }

            @Override
            public void onError(int i, String s) {

            }
        });

    }

    /**
     * 现金流量表
     */
    private void quueryFinanceOnProcfstatementnew(){
        F10V2Request profinmainindexRequest = new F10V2Request();
        F10Request.SRC = Setting.getDataSource();
        profinmainindexRequest.send(stockId,F10Request.SRC,F10Type.D_PROCFSTATEMENTNEW, new ResponseCallback(profinmainindexRequest) {
            @Override
            public void onBack(Response response) {
                F10V2Response  f10V2Response = (F10V2Response) response;
                if( null == f10V2Response || null == f10V2Response.infos || f10V2Response.infos.isEmpty()){
                    return;
                }
                view.onProcfstatementnewSuccess(f10V2Response.infos.get(0));
            }

            @Override
            public void onError(int i, String s) {

            }
        });

    }
}
