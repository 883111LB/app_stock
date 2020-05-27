package com.cvicse.stock.markets.presenter.marketdetail;

import android.util.Log;

import com.cvicse.base.mvp.BasePresenter;
import com.cvicse.stock.common.Setting.Setting;
import com.cvicse.stock.http.ResponseCallback;
import com.cvicse.stock.markets.presenter.marketdetail.contract.HKBasicContract;
import com.mitake.core.request.CompanyInfoRequest;
import com.mitake.core.request.F10Request;
import com.mitake.core.request.LeaderPersonInfoRequest;
import com.mitake.core.response.F10V2Response;
import com.mitake.core.response.Response;

import java.util.HashMap;
import java.util.List;

/**
 * 港股简况
 * Created by liu_zlu on 2017/2/13 19:09
 */
public class HKBasicPresenter extends BasePresenter implements HKBasicContract.Presenter {

    private static final String STRING_SRC = "d";

    private HKBasicContract.View view;
    private String stockId;
    private ResponseCallback companyInfoCallback,
            leaderPersonInfoCallback;
    @Override
    public void setView(HKBasicContract.View view) {
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
     * 查询公司基本信息
     */
    @Override
    public void queryBasic() {
        queryCompanyInfo();
        queryLeaderPersonInfo();
    }
    /**
     * 查询公司基本情况
     */
    private void queryCompanyInfo() {
        CompanyInfoRequest companyInfoRequest = new CompanyInfoRequest();
//        companyInfoRequest.sendV2(stockId,STRING_SRC,companyInfoCallback = new ResponseCallback(companyInfoRequest) {  // old
        F10Request.SRC = Setting.getDataSource();  // new
        companyInfoRequest.sendV2(stockId,companyInfoCallback = new ResponseCallback(companyInfoRequest) {
            @Override
            public void onBack(Response response) {
               // CompanyInfoResponse companyInfoResponse = (CompanyInfoResponse) response;
                //view.onCompanyInfoSuccess(companyInfoResponse.info);
                F10V2Response companyInfoResponse = (F10V2Response) response;
                HashMap<String, Object> info = companyInfoResponse.info;

                if(info == null || info.size() <=0){
                    return;
                }

                view.onCompanyInfoSuccess(info);
            }

            @Override
            public void onError(int i, String s) {
                Log.e("","");
            }
        });
    }

    /**
     * 查询领导层
     */
    private void queryLeaderPersonInfo() {
        LeaderPersonInfoRequest leaderPersonInfoRequest = new LeaderPersonInfoRequest();
//        leaderPersonInfoRequest.sendV2(stockId,STRING_SRC,leaderPersonInfoCallback = new ResponseCallback(leaderPersonInfoRequest) {  // old
        F10Request.SRC = Setting.getDataSource();  // new
        leaderPersonInfoRequest.sendV2(stockId,leaderPersonInfoCallback = new ResponseCallback(leaderPersonInfoRequest) {
            @Override
            public void onBack(Response response) {
               // LeaderPersonInfoResponse leaderPersonInfoResponse = (LeaderPersonInfoResponse) response;
                //view.onLeaderPersonInfoSuccess(leaderPersonInfoResponse.list);

                F10V2Response mLeaderPersonInfoResponse = (F10V2Response) response;
                List<HashMap<String,Object>> infos = mLeaderPersonInfoResponse.infos;

                if(infos == null || infos.size()<=0){
                    return;
                }

                view.onLeaderPersonInfoSuccess(infos);
            }

            @Override
            public void onError(int i, String s) {

            }
        });
    }
}
