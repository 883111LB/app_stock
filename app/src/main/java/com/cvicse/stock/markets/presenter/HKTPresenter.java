package com.cvicse.stock.markets.presenter;

import com.cvicse.base.mvp.BasePresenter;
import com.cvicse.stock.http.loop.CateSortingRunnable;
import com.cvicse.stock.http.loop.HKMarInfoRunnable;
import com.cvicse.stock.http.loop.HSAmountRunnable;
import com.cvicse.stock.http.loop.RequestManager;
import com.cvicse.stock.http.loop.RunTaskState;
import com.cvicse.stock.markets.presenter.contract.HKTContract;
import com.cvicse.stock.utils.DataConvert;
import com.mitake.core.CateType;
import com.mitake.core.QuoteItem;
import com.mitake.core.response.CateSortingResponse;
import com.mitake.core.response.HKMarInfoResponse;
import com.mitake.core.response.HSAmountResponse;

import java.util.ArrayList;

/**
 * Created by ding_syi on 17-2-6.
 */
public class HKTPresenter extends BasePresenter implements HKTContract.Presenter {
    HKTContract.View view;
    //第一个参数为港股通(沪),第二个参数为港股通(深)
    private String[] id = {CateType.HKTong,CateType.HKUA2301,CateType.SZHK,CateType.HKAHG};

    private String param = "0,10,12,1,0";

    //默认请求第一条
    private boolean[] isRequests = new boolean[]{true,false,false,false};

    private boolean[] requests = new boolean[]{false,false,false,false};

    private RunTaskState[] requestSigns = new RunTaskState[]{null,null,null,null};

    private RunTaskState hKMarInfoSign;
    private RunTaskState hsAmountSign;

    @Override
    public void onResume() {
        super.onResume();
        requestHKT();
        requestBalanceHKT();
        requestHSAmount();
    }

    public void requestHKT() {
        for(int i = 0; i < id.length;i++){
            if(isRequests[i]){
                requestHKTInner(i);
            }
        }
    }
    /**
     * 请求港股通
     */
    @Override
    public void requestHKT(int position) {
        if(isRequests[position]){
            return;
        }
        isRequests[position] = true;
        requestHKTInner(position);
    }
    /**
     * 请求港股通
     */
    public void requestHKTInner(final int position) {
        //是否正在请求
        if(requests[position]) return;

        RequestManager.cancel(requestSigns[position]);
        requestSigns[position] = RequestManager.request(new CateSortingRunnable(id[position], param){

            @Override
            public void onBack(CateSortingResponse response) {
                ArrayList<QuoteItem> list = response.list;
                view.onRequestHKTSuccess(DataConvert.QuoteItemList(list),position);
                requests[position] = false;
            }

            @Override
            public void onError(int i, String error) {
                requests[position] = false;
            }
        });
    }
    /**
     * 请求港股通余额
     */
    @Override
    public void requestBalanceHKT() {
        RequestManager.cancel(hKMarInfoSign);
        hKMarInfoSign = RequestManager.request(new HKMarInfoRunnable() {
            @Override
            public void onBack(HKMarInfoResponse response) {
                view.requestBalanceHKTSuccess(response);
            }

            @Override
            public void onError(int i, String error) {
                view.requestBalanceHKTFail();
            }
        });
    }

    /**
     * 请求港股排行榜
     */
    @Override
    public void requestRefesh() {
        requestHKT();
    }

    /**
     * 请求沪深股额度
     */
    @Override
    public void requestHSAmount() {
        RequestManager.cancel(hsAmountSign);
        hsAmountSign = RequestManager.request(new HSAmountRunnable() {
            @Override
            public void onBack(HSAmountResponse response) {
                if( null == response || null == response.mHSAmountItem){
                    view.requestHSAmountFail();
                    return;
                }
                view.requestHSAmountSuccess(response.mHSAmountItem);
            }

            @Override
            public void onError(int i, String error) {
                view.requestHSAmountFail();
            }
        });
    }


    @Override
    public void onPause() {
        super.onPause();
        for(int i = 0; i < id.length;i++){
            RequestManager.cancel(requestSigns[i]);
        }
        RequestManager.cancel(hKMarInfoSign);
        RequestManager.cancel(hsAmountSign);
    }
    @Override
    public void setView(HKTContract.View view) {
        this.view = view;
    }
}
