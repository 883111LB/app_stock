package com.cvicse.stock.markets.presenter.marketdetail;

import com.cvicse.base.mvp.BasePresenter;
import com.cvicse.stock.common.Setting.Setting;
import com.cvicse.stock.http.ResponseCallback;
import com.cvicse.stock.markets.presenter.marketdetail.contract.StockShareContract;
import com.cvicse.stock.markets.ui.stockdetail.StockShareFragment;
import com.mitake.core.request.F10Request;
import com.mitake.core.request.F10Type;
import com.mitake.core.request.F10V2Request;
import com.mitake.core.request.FundShareHolderInfoRequest;
import com.mitake.core.request.ShareHolderHistoryInfoRequest;
import com.mitake.core.request.StockShareChangeInfoRequest;
import com.mitake.core.request.StockShareInfoRequest;
import com.mitake.core.response.F10V2Response;
import com.mitake.core.response.FundShareHolderInfoResponse;
import com.mitake.core.response.Response;
import com.mitake.core.response.ShareHolderHistoryInfoResponse;
import com.mitake.core.response.StockShareChangeInfoResponse;
import com.mitake.core.response.StockShareInfoResponse;

import java.util.HashMap;
import java.util.List;

/**
 * 股东页面
 * Created by liu_zlu on 2017/2/10 15:36
 */
public class StockSharePresenter extends BasePresenter implements StockShareContract.Presenter {

    private StockShareContract.View view;
    private String stockId;
    private String type;
    private ResponseCallback stockShareInfoCallback,stockShareChangeInfoCallback,
            shareHolderHistoryInfoCallback,topLiquidShareHolderCallback,
            topShareHolderCallback,fundShareHolderInfoCallback;
    @Override
    public void setView(StockShareContract.View view) {
        this.view = view;
    }

    /**
     * 初始化stockId
     *
     * @param stockId
     */
    @Override
    public void init(String stockId,String type) {
        this.stockId = stockId;
        this.type = type;
    }

    /**
     * 查询股东相关信息
     */
    @Override
    public void queryStockShare() {

        if(!type.equals(StockShareFragment.HONGKONG)){
            queryStockShareSuccess();//查询股本结构
            queryFundShareHolderSuccess();//查询最新基金持股
        }

        //共有的
        queryStockShareChangeSuccess();//股本变动
        queryShareHolderHistorySuccess();//股东变动
        queryTopLiquidShareHolderSuccess();//最新十大流通股股东
        queryTopShareHolderSuccess();//最新十大股东
    }

    /**
     * 查询股本结构成功
     */
    private void queryStockShareSuccess() {
        StockShareInfoRequest stockShareInfoRequest = new StockShareInfoRequest();
        F10Request.SRC = Setting.getDataSource();  // new
        stockShareInfoRequest.sendV2(stockId,stockShareInfoCallback = new ResponseCallback(stockShareInfoRequest) {
            @Override
            public void onBack(Response response) {
                StockShareInfoResponse stockShareInfoResponse = (StockShareInfoResponse) response;
                view.onStockShareSuccess(stockShareInfoResponse.info);
            }

            @Override
            public void onError(int i, String s) {

            }
        });
    }
    /**
     * 查询股本变动成功
     */
    private void queryStockShareChangeSuccess() {
        StockShareChangeInfoRequest stockShareChangeInfoRequest = new StockShareChangeInfoRequest();
        if("hongkong".equals(type)){
            F10Request.SRC = Setting.getDataSource();  // new
            stockShareChangeInfoRequest.sendV2(stockId,stockShareChangeInfoCallback = new ResponseCallback(stockShareChangeInfoRequest) {
                @Override
                public void onBack(Response response) {
                    StockShareChangeInfoResponse stockShareChangeInfoResponse = (StockShareChangeInfoResponse) response;
                    view.onStockShareChangeSuccess(stockShareChangeInfoResponse.list);
                }

                @Override
                public void onError(int i, String s) {

                }
            });
        }else{
            F10Request.SRC = Setting.getDataSource();  // new
            stockShareChangeInfoRequest.sendV2(stockId,stockShareChangeInfoCallback = new ResponseCallback(stockShareChangeInfoRequest) {
                @Override
                public void onBack(Response response) {
                    StockShareChangeInfoResponse stockShareChangeInfoResponse = (StockShareChangeInfoResponse) response;
                    view.onStockShareChangeSuccess(stockShareChangeInfoResponse.list);
                }

                @Override
                public void onError(int i, String s) {

                }
            });
        }

    }
    /**
     * 查询股东变动成功
     */
    private void queryShareHolderHistorySuccess() {
        ShareHolderHistoryInfoRequest shareHolderHistoryInfoRequest = new ShareHolderHistoryInfoRequest();
        if("hongkong".equals(type)){
            F10Request.SRC = Setting.getDataSource();  // new
            shareHolderHistoryInfoRequest.sendV2(stockId,shareHolderHistoryInfoCallback = new ResponseCallback(shareHolderHistoryInfoRequest) {
                @Override
                public void onBack(Response response) {
                    ShareHolderHistoryInfoResponse shareHolderHistoryInfoResponse = (ShareHolderHistoryInfoResponse) response;
                    view.onShareHolderHistorySuccess(shareHolderHistoryInfoResponse.list);
                }

                @Override
                public void onError(int i, String s) {

                }
            });
        }else{
            F10Request.SRC = Setting.getDataSource();  // new
            shareHolderHistoryInfoRequest.sendV2(stockId,shareHolderHistoryInfoCallback = new ResponseCallback(shareHolderHistoryInfoRequest) {
                @Override
                public void onBack(Response response) {
                    ShareHolderHistoryInfoResponse shareHolderHistoryInfoResponse = (ShareHolderHistoryInfoResponse) response;
                    view.onShareHolderHistorySuccess(shareHolderHistoryInfoResponse.list);
                }

                @Override
                public void onError(int i, String s) {

                }
            });
        }

    }

    /**
     * 查询最新十大流通股股东成功
     */
    private void queryTopLiquidShareHolderSuccess() {
        F10V2Request topLiquidShareHolderRequest = new F10V2Request();
        F10Request.SRC = Setting.getDataSource();  // new
        topLiquidShareHolderRequest.send(stockId,F10Request.SRC, F10Type.D_OTSHOLDER10,new ResponseCallback(topLiquidShareHolderRequest) {
            @Override
            public void onBack(Response response) {
                F10V2Response mCompanyInfoResponse = (F10V2Response) response;
                List<HashMap<String, Object>> infos = mCompanyInfoResponse.infos;
                if(null==infos || infos.isEmpty()){
                    return;
                }
                view.onTopLiquidShareHolderSuccess(infos);
            }

            @Override
            public void onError(int i, String s) {

            }
        });
    }

    /**
     * 查询最新十大股东成功
     */
    private void queryTopShareHolderSuccess() {
        F10V2Request topShareHolderRequest = new F10V2Request();
        F10Request.SRC = Setting.getDataSource();  // new
        topShareHolderRequest.send(stockId,F10Request.SRC, F10Type.D_SHAREHOLDER10,new ResponseCallback(topShareHolderRequest) {
            @Override
            public void onBack(Response response) {
                F10V2Response mCompanyInfoResponse = (F10V2Response) response;
                List<HashMap<String, Object>> infos = mCompanyInfoResponse.infos;
                if(null==infos || infos.isEmpty()){
                   return;
                }
                view.onTopShareHolderSuccess(infos);
            }

            @Override
            public void onError(int i, String s) {

            }
        });
    }

    /**
     * 查询最新基金持股
     */
    private void queryFundShareHolderSuccess() {
        FundShareHolderInfoRequest fundShareHolderInfoRequest = new FundShareHolderInfoRequest();
        F10Request.SRC = Setting.getDataSource();  // new
        fundShareHolderInfoRequest.sendV2(stockId,fundShareHolderInfoCallback = new ResponseCallback(fundShareHolderInfoRequest) {
            @Override
            public void onBack(Response response) {
                FundShareHolderInfoResponse fundShareHolderInfoResponse = (com.mitake.core.response.FundShareHolderInfoResponse) response;
                view.onFundShareHolderSuccess(fundShareHolderInfoResponse.info);
            }

            @Override
            public void onError(int i, String s) {

            }
        });
    }
}
