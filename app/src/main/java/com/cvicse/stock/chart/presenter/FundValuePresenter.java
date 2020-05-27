package com.cvicse.stock.chart.presenter;

import com.cvicse.base.mvp.BasePresenter;
import com.cvicse.stock.chart.presenter.contract.FundValueContract;
import com.cvicse.stock.chart.presenter.contract.FundValueContract.Presenter;
import com.cvicse.stock.http.ResponseCallback;
import com.mitake.core.QuoteItem;
import com.mitake.core.request.FndNavIndexRequest;
import com.mitake.core.response.FundValueResponse;
import com.mitake.core.response.Response;

/**
 * 基金净值
 * Created by liu_zlu on 2017/4/28 14:41
 */
public class FundValuePresenter extends BasePresenter implements Presenter {
    private QuoteItem quoteItem;
    private FundValueContract.View view;

    @Override
    public void setView(FundValueContract.View view) {
        this.view = view;
    }

    /**
     * 初始化数据
     *
     * @param quoteItem
     */
    @Override
    public void init(QuoteItem quoteItem) {
        this.quoteItem = quoteItem;
    }

    @Override
    public void onResume() {
        super.onResume();
        requestFundValue();
    }

   /* private void requestFundValue(){
        FundValueRequest fundValueRequest = new FundValueRequest();
        //12 个月
        fundValueRequest.send(quoteItem.id, "12", new ResponseCallback(fundValueRequest) {
            @Override
            public void onBack(Response response) {
                FundValueResponse fundValueResponse = (FundValueResponse) responsev;
                view.onRequestFundValueSuccess(fundValueResponse.info);
            }

            @Override
            public void onError(int i, String s) {
                view.onRequestFundValueFail();
            }
        });
    }*/

    private void requestFundValue(){
        //FundBasicRequest
        //FundValueRequest
        //FndNavIndexRequest

        FndNavIndexRequest fundValueRequest = new FndNavIndexRequest();
        //12 个月
        fundValueRequest.send(quoteItem.id, "12", new ResponseCallback(fundValueRequest) {
            @Override
            public void onBack(Response response) {
                FundValueResponse fundValueResponse = (FundValueResponse) response;
                view.onRequestFundValueSuccess(fundValueResponse.info);
            }

            @Override
            public void onError(int i, String s) {
                view.onRequestFundValueFail();
            }
        });
    }
}
