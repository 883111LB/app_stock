package com.cvicse.stock.markets.presenter;

import com.cvicse.base.mvp.BasePresenter;
import com.cvicse.stock.http.loop.CateSortingRunnable;
import com.cvicse.stock.http.loop.QuoteRunable;
import com.cvicse.stock.http.loop.RequestManager;
import com.cvicse.stock.http.loop.RunTaskState;
import com.cvicse.stock.markets.presenter.contract.HKContract;
import com.cvicse.stock.utils.DataConvert;
import com.mitake.core.QuoteItem;
import com.mitake.core.response.CateSortingResponse;
import com.mitake.core.response.QuoteResponse;

import java.util.ArrayList;

/**
 * Created by ruan_ytai on 17-1-18.
 */

public class HKPresenter extends BasePresenter implements HKContract.Presenter {
    private HKContract.View view;

    //恒生指数、国企指数、红筹指数
    private String id = "HSI.hk,HSCEI.hk,HSCCI.hk";

    private String[] param = {"0,10,12,1,0","0,10,12,0,0", "0,10,15,1,0","0,10,20,1,0"};
    private RunTaskState requestSign;

    private boolean[] isRequests = new boolean[]{true,false,false,false};
    private boolean[] requests = new boolean[]{false,false,false,false};
    private RunTaskState[] requestSigns = new RunTaskState[]{null,null,null,null};

    /**
     *  请求港股指数
     */
    @Override
    public void requestIndex() {
        requestSign = RequestManager.request(new QuoteRunable(id,null) {
            @Override
            public void onBack(QuoteResponse response) {
                ArrayList<QuoteItem> list = response.quoteItems;
                view.onRequestIndexSuccess(DataConvert.QuoteItemList(list));
            }

            @Override
            public void onError(int i, String error) {
                view.onRequestIndexFail();
            }
        });
    }

    /**
     * 请求港股排行榜
     */
    @Override
    public void requestRefesh() {
        requestRankingList();
        requestIndex();
    }

    /**
     * 请求港股四个排行榜
     * HK1010
     */
    public void requestRankingList() {
        for(int i = 0; i < 4;i++){
            if(isRequests[i]){
                requestRankInner(i);
            }
        }
    }
    /**
     * 查询各类排行榜数据
     * 页码，笔数，排序栏位，正倒序(1倒序，0顺序)，是否显示停牌股(0不显示，1显示)
     *  0,10,12,1,0(涨幅榜); 0,10,12,0,0(跌幅榜); 0,10,15,1,0(换手率);0,10,20,1,0(成交额)
     */
    @Override
    public void requestRankingList(int position) {
        if(isRequests[position]){
            return;
        }
        isRequests[position] = true;
        requestRankInner(position);
    }

    private void requestRankInner(final int i){
        if(requests[i]) return;
        RequestManager.cancel(requestSigns[i]);
        requestSigns[i] = RequestManager.request(new CateSortingRunnable("HK1010", param[i]){
            @Override
            public void onBack(CateSortingResponse response) {
                response.list = DataConvert.QuoteItemList(response.list);
                view.onRequestRankingSuccess(response,i);
                requests[i] = false;
            }

            @Override
            public void onError(int code, String error) {
                requests[i] = false;
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        requestIndex();
        requestRankingList();
    }

    @Override
    public void onPause() {
        super.onPause();
        for(int i = 0; i < 4;i++){
            RequestManager.cancel(requestSigns[i]);
        }
        RequestManager.cancel(requestSign);
    }
    @Override
    public void setView(HKContract.View view) {
        this.view = view;
    }

}
