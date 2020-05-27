package com.cvicse.stock.markets.presenter;


import com.cvicse.base.mvp.BasePresenter;
import com.cvicse.stock.http.ResponseCallback;
import com.cvicse.stock.markets.presenter.contract.HKTMoreConstract;
import com.cvicse.stock.utils.DataConvert;
import com.mitake.core.CateType;
import com.mitake.core.QuoteItem;
import com.mitake.core.request.CateSortingRequest;
import com.mitake.core.response.CateSortingResponse;
import com.mitake.core.response.Response;

import java.util.ArrayList;

/**
 * Created by ding_syi on 17-2-7.
 */
public class HKTMorePresenter extends BasePresenter implements HKTMoreConstract.Presenter {
    private HKTMoreConstract.View view;

    public static final String HKT  = "港股通";
    public static final String HKTSH  = "港股通(沪)";
    public static final String HKTSZ  = "港股通(深)";

    public static final String HKT_CODE = CateType.HKTong;
    public static final String HKTSH_CODE = "HKUA2301";
    public static final String HKTSZ_CODE = "SZHK";

    /**
     * 请求排行榜数据
     *
     * @param stockType
     * @param param
     */
    @Override
    public void requestRankingData(String stockType, String param) {
        String id = null;
        if(HKTSH.equals(stockType)){
            id = HKTSH_CODE;
        } else if(HKTSZ.equals(stockType)){
            id = HKTSZ_CODE;
        }else if(HKT.equals(stockType)){
            id = HKT_CODE;
        }

        CateSortingRequest cateSortingRequest = new CateSortingRequest();
        cateSortingRequest.send(id,param, new ResponseCallback(cateSortingRequest) {
            @Override
            public void onBack(Response response) {
                CateSortingResponse cateSortingResponse = (CateSortingResponse) response;
                ArrayList<QuoteItem> list = cateSortingResponse.list;
                view.requestRankingDataSuccess(DataConvert.QuoteItemList(list));
            }
            @Override
            public void onError(int i, String s) {

            }
        });
    }

    /**
     * 请求下拉刷新数据
     *
     * @param stockType
     * @param param
     */
    @Override
    public void requestPulldownData(String stockType, String param) {
        String id = null;
        if(HKTSH.equals(stockType)){
            id = HKTSH_CODE;
        } else if(HKTSZ.equals(stockType)){
            id = HKTSZ_CODE;
        }else if(HKT.equals(stockType)){
            id = HKT_CODE;
        }

        CateSortingRequest cateSortingRequest = new CateSortingRequest();
        cateSortingRequest.send(id,param, new ResponseCallback(cateSortingRequest) {
            @Override
            public void onBack(Response response) {
                CateSortingResponse cateSortingResponse = (CateSortingResponse) response;
                ArrayList<QuoteItem> list = cateSortingResponse.list;
                view.requestPulldownDataSuccess(DataConvert.QuoteItemList(list));
            }
            @Override
            public void onError(int i, String s) {

            }
        });
    }

    /**
     * 请求上拉加载数据
     *
     * @param stockType
     * @param param
     */
    @Override
    public void requestPullUpData(String stockType, String param) {
        String id = null;
        if(HKTSH.equals(stockType)){
            id = HKTSH_CODE;
        } else if(HKTSZ.equals(stockType)){
            id = HKTSZ_CODE;
        }else if(HKT.equals(stockType)){
            id = HKT_CODE;
        }

        CateSortingRequest cateSortingRequest = new CateSortingRequest();
        cateSortingRequest.send(id,param, new ResponseCallback(cateSortingRequest) {
            @Override
            public void onBack(Response response) {
                CateSortingResponse cateSortingResponse = (CateSortingResponse) response;
                ArrayList<QuoteItem> list = cateSortingResponse.list;
                view.requestPullUpDataSuccess(DataConvert.QuoteItemList(list));
            }
            @Override
            public void onError(int i, String s) {

            }
        });
    }

    @Override
    public void setView(HKTMoreConstract.View view) {
        this.view = view;
    }

}
