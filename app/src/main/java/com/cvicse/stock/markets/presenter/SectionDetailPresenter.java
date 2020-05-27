package com.cvicse.stock.markets.presenter;

import com.cvicse.base.mvp.BasePresenter;
import com.cvicse.stock.http.ResponseCallback;
import com.cvicse.stock.markets.presenter.contract.SectionDetailContract;
import com.cvicse.stock.utils.DataConvert;
import com.mitake.core.QuoteItem;
import com.mitake.core.request.CateSortingRequest;
import com.mitake.core.response.CateSortingResponse;
import com.mitake.core.response.Response;

import java.util.ArrayList;

/**
 * Created by ding_syi on 17-2-23.
 */
public class SectionDetailPresenter extends BasePresenter implements SectionDetailContract.Presenter{
    SectionDetailContract.View mView;

    @Override
    public void request(String stockId,String param) {
        CateSortingRequest cateSortingRequest = new CateSortingRequest();
        cateSortingRequest.send(stockId,param, new ResponseCallback(cateSortingRequest) {
            @Override
            public void onBack(Response response) {
                CateSortingResponse cateSortingResponse = (CateSortingResponse) response;
                ArrayList<QuoteItem> list = cateSortingResponse.list;
                mView.onRequestSuccess(DataConvert.QuoteItemList(list));

            }
            @Override
            public void onError(int i, String s) {

            }
        });
    }

    /**
     * @param stockId 股票id
     * @param param   排序规则
     */
    @Override
    public void requestPulldownData(String stockId, String param) {
        CateSortingRequest cateSortingRequest = new CateSortingRequest();
        cateSortingRequest.send(stockId,param, new ResponseCallback(cateSortingRequest) {
            @Override
            public void onBack(Response response) {
                CateSortingResponse cateSortingResponse = (CateSortingResponse) response;
                ArrayList<QuoteItem> list = cateSortingResponse.list;
                mView.onRequestPulldownSuccess(list);

            }
            @Override
            public void onError(int i, String s) {

            }
        });
    }

    /**
     * @param stockId 股票id
     * @param param   排序规则
     */
    @Override
    public void requestPullupData(String stockId, String param) {
        CateSortingRequest cateSortingRequest = new CateSortingRequest();
        cateSortingRequest.send(stockId,param, new ResponseCallback(cateSortingRequest) {
            @Override
            public void onBack(Response response) {
                CateSortingResponse cateSortingResponse = (CateSortingResponse) response;
                ArrayList<QuoteItem> list = cateSortingResponse.list;
                mView.onRequestPullupSuccess(list);

            }
            @Override
            public void onError(int i, String s) {

            }
        });
    }

    @Override
    public void setView(SectionDetailContract.View view) {
        mView = view;
    }
}
