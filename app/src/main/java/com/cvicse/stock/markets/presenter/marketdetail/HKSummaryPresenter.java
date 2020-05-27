package com.cvicse.stock.markets.presenter.marketdetail;

import com.cvicse.base.mvp.BasePresenter;
import com.cvicse.stock.common.Setting.Setting;
import com.cvicse.stock.http.ResponseCallback;
import com.cvicse.stock.markets.presenter.marketdetail.contract.HKSummaryContract;
import com.mitake.core.request.BonusFinanceRequest;
import com.mitake.core.request.F10Request;
import com.mitake.core.request.NewIndexRequest;
import com.mitake.core.response.BonusFinanceResponse;
import com.mitake.core.response.NewIndexResponse;
import com.mitake.core.response.Response;

/**
 * 港股摘要
 * Created by liu_zlu on 2017/2/13 16:46
 */
public class HKSummaryPresenter extends BasePresenter implements HKSummaryContract.Presenter {

    private static final String SRC = "d";

    private HKSummaryContract.View view;
    private String stockId;
    private ResponseCallback newIndexCallback,bonusFinanceCallback;


    @Override
    public void setView(HKSummaryContract.View view) {
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
     * 查询概要信息
     */
    @Override
    public void queryHKSummary() {
        queryNewIndex();
        queryBoundsFinance();
    }
    /**
     * 最新指标
     */
    private void queryNewIndex() {
        NewIndexRequest newIndexRequest = new NewIndexRequest();

//        newIndexRequest.sendV2(stockId, SRC,newIndexCallback = new ResponseCallback(newIndexRequest) {

        F10Request.SRC = Setting.getDataSource();  // new
        newIndexRequest.sendV2(stockId,newIndexCallback = new ResponseCallback(newIndexRequest) {
            @Override
            public void onBack(Response response) {
                NewIndexResponse newIndexResponse = (NewIndexResponse) response;
                view.onNewIndexSuccess(newIndexResponse.info);
            }

            @Override
            public void onError(int i, String s) {

            }
        });
    }

    /**
     * 分红配送
     */
    private void queryBoundsFinance() {
        BonusFinanceRequest bonusFinanceRequest = new BonusFinanceRequest();
        F10Request.SRC = Setting.getDataSource();  // new
//        bonusFinanceRequest.sendV2(stockId,SRC,bonusFinanceCallback = new ResponseCallback(bonusFinanceRequest) {
        bonusFinanceRequest.sendV2(stockId,bonusFinanceCallback = new ResponseCallback(bonusFinanceRequest) {
            @Override
            public void onBack(Response response) {
                BonusFinanceResponse bonusFinanceResponse = (BonusFinanceResponse) response;
                view.onBonusFinanceSuccess(bonusFinanceResponse.list);
            }

            @Override
            public void onError(int i, String s) {

            }
        });
    }
}
