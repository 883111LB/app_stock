package com.cvicse.stock.markets.presenter;

import com.cvicse.base.mvp.BasePresenter;
import com.cvicse.stock.http.ResponseCallback;
import com.cvicse.stock.http.loop.AHQuoteListRunable;
import com.cvicse.stock.http.loop.RequestManager;
import com.cvicse.stock.http.loop.RunTaskState;
import com.cvicse.stock.markets.presenter.contract.AHQuoteListContract;
import com.mitake.core.QuoteItem;
import com.mitake.core.bean.AHQuoteItem;
import com.mitake.core.bean.log.ErrorInfo;
import com.mitake.core.request.AHQuoteListRequest;
import com.mitake.core.request.QuoteDetailRequest;
import com.mitake.core.request.SearchRequest;
import com.mitake.core.response.AHQuoteListResponse;
import com.mitake.core.response.AHQuoteResponse;
import com.mitake.core.response.IResponseInfoCallback;
import com.mitake.core.response.QuoteResponse;
import com.mitake.core.response.Response;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tang_xqing on 2018/5/14.
 */
public class AHQuoteListPresenter extends BasePresenter implements AHQuoteListContract.Presenter{

    AHQuoteListContract.View mView;
    private RunTaskState requestSign;
    private int rankType = 0; // 排序类型（0-默认，1-下拉刷新，2-上拉加载）
    private String param1;
    @Override
    public void requestAHQuoteList(String param) {
        param1=param;
        RequestManager.cancel(requestSign);
        requestSign = RequestManager.request(new AHQuoteListRunable(param) {
            @Override
            public void onBack(AHQuoteListResponse response) {
                AHQuoteListRequest ahQuoteListRequest = new AHQuoteListRequest();
                ahQuoteListRequest.send(param1, new ResponseCallback(ahQuoteListRequest) {
                    @Override
                    public void onBack(Response response) {
                        AHQuoteListResponse ahQuoteListResponse=(AHQuoteListResponse)response;
                        List<AHQuoteItem> mAHQuoteItems=ahQuoteListResponse.mAHQuoteItems;
                        if( null == response || null == mAHQuoteItems || mAHQuoteItems.isEmpty()){
                            return;
                        }
                        if( rankType == 0 ){
                            mView.requestAHQuoteListSuccess(mAHQuoteItems);
                        }else if(rankType == 1){
                            mView.requestPullDownAHQuoteListSuccess(mAHQuoteItems);
                        }else{
                            mView.requestPullUpAHQuoteListSuccess(mAHQuoteItems);
                        }
                        rankType = 0;
                    }

                    @Override
                    public void onError(int i, String s) {
                        mView.requestAHQuoteListFail();
                    }
                });
//                if( null == response || null == response.mAHQuoteItems || response.mAHQuoteItems.isEmpty()){
//                    return;
//                }
//                if( rankType == 0 ){
//                    mView.requestAHQuoteListSuccess(response.mAHQuoteItems);
//                }else if(rankType == 1){
//                    mView.requestPullDownAHQuoteListSuccess(response.mAHQuoteItems);
//                }else{
//                    mView.requestPullUpAHQuoteListSuccess(response.mAHQuoteItems);
//                }
//                rankType = 0;
            }

            @Override
            public void onError(int i, String error) {
                mView.requestAHQuoteListFail();
            }
        });
    }

    @Override
    public void requestPullDownAHQuoteList(String parm) {
        rankType = 1;
        requestAHQuoteList(parm);
    }

    @Override
    public void requestPullUpAHQuoteList(String parm) {
        rankType = 2;
        requestAHQuoteList(parm);
    }


    @Override
    public void requestQuote(String code) {
        QuoteDetailRequest quoteDetailRequest = new QuoteDetailRequest();
        quoteDetailRequest.send(code, new ResponseCallback(quoteDetailRequest) {
            @Override
            public void onBack(Response response) {
                QuoteResponse quoteResponse = (QuoteResponse) response;
                ArrayList<QuoteItem> quoteItems = quoteResponse.quoteItems;
                if(null == quoteResponse || null == quoteItems || quoteItems.isEmpty()){
                    mView.requestQuoteFail();
                }else {
                    mView.requestQuoteSuccess(quoteItems);
                }
            }

            @Override
            public void onError(int i, String s) {
                mView.requestQuoteFail();
            }
        });
    }

    @Override
    public void setView(AHQuoteListContract.View view) {
        this.mView = view;
    }

    @Override
    public void onPause() {
        super.onPause();
        RequestManager.cancel(requestSign);
    }
}
