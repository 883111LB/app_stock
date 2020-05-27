package com.cvicse.stock.markets.presenter.marketdetail;

import android.util.Log;

import com.cvicse.base.mvp.BasePresenter;
import com.cvicse.stock.common.Setting.Setting;
import com.cvicse.stock.http.ResponseCallback;
import com.cvicse.stock.markets.presenter.marketdetail.contract.StockBasicContract;
import com.mitake.core.LeaderPersonInfo;
import com.mitake.core.network.Network;
import com.mitake.core.request.CompanyInfoRequest;
import com.mitake.core.request.CoreBusinessRequest;
import com.mitake.core.request.F10Request;
import com.mitake.core.request.F10Type;
import com.mitake.core.request.F10V2Request;
import com.mitake.core.request.LeaderPersonInfoRequest;
import com.mitake.core.response.CoreBusinessResponse;
import com.mitake.core.response.F10V2Response;
import com.mitake.core.response.Response;
import com.mitake.core.util.MarketSiteType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 公司基本详情页面
 * Created by liu_zlu on 2017/2/10 09:04
 */
public class StockBasicPresenter extends BasePresenter implements StockBasicContract.Presenter {

    private StockBasicContract.View view;
    private String stockId;

    @Override
    public void setView(StockBasicContract.View view) {
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
//        queryCompanyInfo();
        queryCompanyInfo2();
        queryCoreBusiness();
        queryLeaderPersonInfo();
    }
    /**
     * 查询公司基本情况
     */
    private void queryCompanyInfo() {
        CompanyInfoRequest companyInfoRequest = new CompanyInfoRequest();
        F10Request.SRC = Setting.getDataSource();  // new
        companyInfoRequest.sendV2(stockId,new ResponseCallback(companyInfoRequest) {
            @Override
            public void onBack(Response response) {
             /*   CompanyInfoResponse companyInfoResponse = (CompanyInfoResponse) response;
                view.onCompanyInfoSuccess(companyInfoResponse.info);*/

                F10V2Response mCompanyInfoResponse = (F10V2Response) response;
                HashMap<String, Object> info = mCompanyInfoResponse.info;
                if(info!=null &&info.size()>0){
                    view.onCompanyInfoSuccess(info);
                }
            }

            @Override
            public void onError(int i, String s) {
                Log.e("","code = "+i+"  message="+s);
            }
        });
    }

    private void queryCompanyInfo2(){
        F10V2Request fundRequest = new F10V2Request();
        F10Request.SRC = Setting.getDataSource();  // new
        fundRequest.send(stockId,F10Request.SRC, F10Type.D_COMPANYINFO,new ResponseCallback(fundRequest) {

            @Override
            public void onBack(Response response) {
                F10V2Response mCompanyInfoResponse = (F10V2Response) response;
                HashMap<String, Object> info = mCompanyInfoResponse.info;
                if(null!=info && !info.isEmpty()){
                    view.onCompanyInfoSuccess(info);
                }
            }

            @Override
            public void onError(int i, String s) {
                Log.e("公司基本信息","code = "+i+"  message="+s);
            }
        });
    }

    /**
     * 查询公司主营业务
     */
    private void queryCoreBusiness() {
        CoreBusinessRequest coreBusinessRequest = new CoreBusinessRequest();
//        coreBusinessRequest.send(stockId,coreBusinessCallback = new ResponseCallback(coreBusinessRequest) {
        F10Request.SRC = Setting.getDataSource();  // new
        coreBusinessRequest.sendV2(stockId,new ResponseCallback(coreBusinessRequest) {
            @Override
            public void onBack(Response response) {
                CoreBusinessResponse coreBusinessResponse = (CoreBusinessResponse) response;
                view.onCoreBusinessSuccess(coreBusinessResponse.list);
            }

            @Override
            public void onError(int i, String s) {

            }
        });
    }

    /**
     * 查询领导层
     */
    private void queryLeaderPersonInfo() {
        F10V2Request leaderPersonInfoRequest = new F10V2Request();
        F10Request.SRC = Setting.getDataSource();  // new
        leaderPersonInfoRequest.send(stockId,F10Request.SRC,F10Type.D_COMPANYMANAGER,new ResponseCallback(leaderPersonInfoRequest) {
            @Override
            public void onBack(Response response) {

                F10V2Response mLeaderPersonInfoResponse = (F10V2Response) response;
                List<HashMap<String,Object>> infos = mLeaderPersonInfoResponse.infos;
                if(infos == null || infos.size() <=0){
                    return;
                }
                view.onLeaderPersonInfoSuccess(infos);
            }

            @Override
            public void onError(int i, String s) {

            }
        });
    }

    private void queryLeaderPersonInfo2() {
        LeaderPersonInfoRequest leaderPersonInfoRequest = new LeaderPersonInfoRequest();
//        leaderPersonInfoRequest.send(stockId,leaderPersonInfoCallback = new ResponseCallback(leaderPersonInfoRequest) {  // old
        F10Request.SRC = Setting.getDataSource();  // new
        leaderPersonInfoRequest.sendV2(stockId,new ResponseCallback(leaderPersonInfoRequest) {
            @Override
            public void onBack(Response response) {
               /* LeaderPersonInfoResponse leaderPersonInfoResponse = (LeaderPersonInfoResponse) response;
                view.onLeaderPersonInfoSuccess(leaderPersonInfoResponse.list);*/

                F10V2Response mLeaderPersonInfoResponse = (F10V2Response) response;
                List<HashMap<String,Object>> list = mLeaderPersonInfoResponse.infos;
                if(list == null || list.size() <=0){
                    return;
                }

                /**
                 * 创建回调的ArrayList
                 */
                ArrayList<LeaderPersonInfo> infos = new ArrayList<>();

                for(int i=0;i<list.size();i++){
                    LeaderPersonInfo leaderPersonInfo = new LeaderPersonInfo();
                    leaderPersonInfo.positionName = (String)list.get(i).get("POSITIONNAME");
                    leaderPersonInfo.leaderNmae = (String)list.get(i).get("LEADERNAME");
                    leaderPersonInfo.age = (String)list.get(i).get("AGE");
                    leaderPersonInfo.gender = (String)list.get(i).get("GENDER");
                    leaderPersonInfo.education = (String)list.get(i).get("EDUCATION");

                    infos.add(leaderPersonInfo);
                }
//                view.onLeaderPersonInfoSuccess(infos);
            }

            @Override
            public void onError(int i, String s) {

            }
        });
    }
}
