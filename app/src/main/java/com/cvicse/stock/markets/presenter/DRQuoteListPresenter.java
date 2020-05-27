package com.cvicse.stock.markets.presenter;

import com.cvicse.base.mvp.BasePresenter;
import com.cvicse.stock.http.loop.DRQuoteListRunable;
import com.cvicse.stock.http.loop.RequestManager;
import com.cvicse.stock.http.loop.RunTaskState;
import com.cvicse.stock.markets.presenter.contract.DRQuoteListContract;
import com.mitake.core.response.DRQuoteListResponse;

/**
 * Created by shi_yhui on 2018/11/24.
 */

public class DRQuoteListPresenter extends BasePresenter implements DRQuoteListContract.Presenter{

    DRQuoteListContract.View mView;
    private RunTaskState requestSign;
    private int rankType = 0; // 排序类型（0-默认，1-下拉刷新，2-上拉加载）

    @Override
    public void requestPullDownDRQuoteList(String code,String param) {
        rankType = 1;
        requestDRQuoteList(code,param);
    }

    @Override
    public void requestPullUpDRQuoteList(String code,String param) {
        rankType = 2;
        requestDRQuoteList(code, param);
    }

    public void requestDRQuoteList(String code, String param){
        RequestManager.cancel(requestSign);
        requestSign= RequestManager.request(new DRQuoteListRunable(code,param){
            @Override
            public void onBack(DRQuoteListResponse response) {
                if( null == response || null == response.mDRQuoteItems || response.mDRQuoteItems.isEmpty()){
                    return;
                }
                if( rankType == 0 ){
                    mView.requestDRQuoteListSuccess(response.mDRQuoteItems);
                }else if(rankType == 1){
                    mView.requestPullDownDRQuoteListSuccess(response.mDRQuoteItems);
                }else{
                    mView.requestPullUpDRQuoteListSuccess(response.mDRQuoteItems);
                }
                rankType = 0;

            }

            @Override
            public void onError(int i, String error) {
                mView.requestDRQuoteListFail();
            }
        });
    }
    @Override
    public void setView(DRQuoteListContract.View view) {
        this.mView=view;
    }

    @Override
    public void onPause() {
        super.onPause();
        RequestManager.cancel(requestSign);
    }
}
