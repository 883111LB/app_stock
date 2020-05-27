package com.cvicse.stock.markets.presenter.marketdetail;

import com.cvicse.base.mvp.BasePresenter;
import com.cvicse.stock.common.Setting.Setting;
import com.cvicse.stock.http.ResponseCallback;
import com.cvicse.stock.markets.presenter.marketdetail.contract.StockFinanceDetailContract;
import com.mitake.core.request.F10Request;
import com.mitake.core.request.F10V2Request;
import com.mitake.core.request.MainFinaDataNasRequest;
import com.mitake.core.response.F10V2Response;
import com.mitake.core.response.MainFinaDataNasResponse;
import com.mitake.core.response.Response;

/** 财务指标详情
 * Created by tang_xqing on 2018/4/19.
 */
public class StockFinanceDetailPresenter extends BasePresenter implements StockFinanceDetailContract.Presenter {
    private StockFinanceDetailContract.View view;
    private String code;
    private String market;

    @Override
    public void init(String code,String market) {
        this.code = code;
        this.market = market;
    }

    @Override
    public void requestFinanceDetail(String quarterType, String apiType) {

        if( null != market && "hk".equals(market) ){
            requestHKFinance("N", apiType);
        }else{
            requestFinance(null, apiType);
        }
    }

    @Override
    public void requestFinanceDetailByQuarterType(String quarterType, String apiType) {
        if( null != market && "hk".equals(market) ){
            requestHKFinance(quarterType, apiType);
        }else{
            requestFinance(quarterType, apiType);
        }
    }

    /**
     * 请求沪深财务对比
     * @param quarterType
     * @param apiType
     */
    private void requestFinance(String quarterType, String apiType) {
        F10V2Request financeDetailRequest = new F10V2Request();
        F10Request.SRC = Setting.getDataSource();  // new
        financeDetailRequest.sendV2(code, F10Request.SRC,quarterType, apiType,new ResponseCallback(financeDetailRequest) {
            @Override
            public void onBack(Response response) {
                F10V2Response f10V2Response = (F10V2Response) response;
                if( null == f10V2Response || null == f10V2Response.infos || f10V2Response.infos.isEmpty() ){
                    view.requestFinanceDetailFail(-200,"");
                    return;
                }
                view.requestFinanceDetailSuccess(f10V2Response.infos);
            }

            @Override
            public void onError(int i, String s) {
                view.requestFinanceDetailFail(i,s);
            }
        });
    }

    /**
     * 请求港股财务对比
     * @param quarterType
     * @param apiType
     */
    private void requestHKFinance(String quarterType, String apiType){
        final MainFinaDataNasRequest mainFinaDataNasRequest = new MainFinaDataNasRequest();
        F10Request.SRC = Setting.getDataSource();  // new
        mainFinaDataNasRequest.sendV2(code, F10Request.SRC,quarterType, new ResponseCallback(mainFinaDataNasRequest) {

            @Override
            public void onBack(Response response) {
                MainFinaDataNasResponse mainResponse = (MainFinaDataNasResponse) response;
                if( null == mainResponse || null == mainResponse.mMainFinaDataNasList || mainResponse.mMainFinaDataNasList.isEmpty()){
                    view.requestHKFinanceDetailFail(200,"");
                    return;
                }
                view.requestHKFinanceDetailSuccess(mainResponse.mMainFinaDataNasList);
            }

            @Override
            public void onError(int i, String s) {
                view.requestHKFinanceDetailFail(i,s);
            }
        });
    }

    @Override
    public void setView(StockFinanceDetailContract.View view) {
        this.view = view;
    }
}
