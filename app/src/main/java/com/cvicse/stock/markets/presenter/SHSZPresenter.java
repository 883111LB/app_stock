package com.cvicse.stock.markets.presenter;

import android.util.Log;

import com.cvicse.base.mvp.BasePresenter;
import com.cvicse.stock.common.Setting.Setting;
import com.cvicse.stock.http.ResponseCallback;
import com.cvicse.stock.http.loop.CateSortingRunnable;
import com.cvicse.stock.http.loop.QuoteRunable;
import com.cvicse.stock.http.loop.RequestManager;
import com.cvicse.stock.http.loop.RunTaskState;
import com.cvicse.stock.http.loop.UpDownSameRunable;
import com.cvicse.stock.markets.data.DateUtil;
import com.cvicse.stock.markets.presenter.contract.SHSZContract;
import com.cvicse.stock.utils.DataConvert;
import com.mitake.core.NewShareList;
import com.mitake.core.QuoteItem;
//import com.mitake.core.keys.quote.AddValueCustomField;
//import com.mitake.core.keys.quote.QuoteCustomField;
import com.mitake.core.keys.quote.AddValueCustomField;
import com.mitake.core.keys.quote.QuoteCustomField;
import com.mitake.core.request.BndNewSharesCalRequest;
import com.mitake.core.request.CateRankingType;
import com.mitake.core.request.F10Request;
import com.mitake.core.request.NewShareListRequest;
import com.mitake.core.response.CateSortingResponse;
import com.mitake.core.response.F10V2Response;
import com.mitake.core.response.NewShareListResponse;
import com.mitake.core.response.QuoteResponse;
import com.mitake.core.response.Response;
import com.mitake.core.response.UpdownsResponse;

import java.util.ArrayList;

import static com.mitake.core.keys.quote.BaseTypeCodes.ALL_COLUMN;

/**
 * Created by ruan_ytai on 17-1-16.
 */

public class SHSZPresenter extends BasePresenter implements SHSZContract.Presenter{

    private SHSZContract.View view;
    private String[] param = {"0,10,12,1,0","0,10,12,0,0", "0,10,15,1,0","0,10,20,1,0","0,10,-48,1,0"};
    //上，深，创指数//中小，腾讯济安，沪深300
    private String str1 = "000001.sh,399001.sz,399006.sz,399005.sz,000847.sh,000300.sh";

//    private int[] quoteCustom ={QuoteCustomField.ALL_COLUMN};
    private int[] quoteCustom ={-1};
    private int[] addvalueCustom = null;

    private RunTaskState requestSign;
    private RunTaskState requestUpDownSign;

    private boolean[] isRequests = new boolean[]{true,false,false,false,false};
    private boolean[] requests = new boolean[]{false,false,false,false,false};
    private RunTaskState[] requestSigns = new RunTaskState[]{null,null,null,null,null};
//    private int addValue_FIVE_MINUTES_CHANGERATE;

    /**
     * 查询排行榜数据
     */
    @Override
    public void requestRank() {
        for(int i = 0; i < isRequests.length;i++){
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
    public void requestRank(int position) {
        if(isRequests[position]){
            return;
        }
        isRequests[position] = true;
        requestRankInner(position);
    }

    private void requestRankInner(final int i){
        if(requests[i]) return;
        RequestManager.cancel(requestSigns[i]);
        addvalueCustom = new int[]{AddValueCustomField.ALL_COLUMN};
//        addvalueCustom = new int[]{-1};
//        if( i == (param.length-1) ){
//            // 五分钟涨幅榜
//            addvalueCustom = new int[]{ALL_COLUMN};
////            addvalueCustom = new int[]{AddValueCustomField.addValue_FIVE_MINUTES_CHANGERATE};
//        }else{
//            addvalueCustom = null;
//        }

        requestSigns[i] = RequestManager.request(new CateSortingRunnable(CateRankingType.ALL, param[i],quoteCustom,addvalueCustom){

            @Override
            public void onBack(CateSortingResponse response) {
                ArrayList<QuoteItem> list = response.list;
//                view.onRequestRankSuccess(DataConvert.QuoteItemList(list),i);
                response.list = DataConvert.QuoteItemList(list);
                view.onRequestRankSuccess(response,i);
                requests[i] = false;
            }

            @Override
            public void onError(int code, String error) {
                requests[i] = false;
            }
        });
    }
    /**
     * 查询当天的新股日历
     */
    @Override
    public void requestCurrentCalendar() {
        String date = DateUtil.CurrentDateConvert();
        NewShareListRequest mNewShareListRequest = new NewShareListRequest();
        mNewShareListRequest.send(date, new ResponseCallback(mNewShareListRequest) {
            @Override
            public void onBack(Response response) {
                NewShareListResponse newShareListRequest = (NewShareListResponse) response;
                ArrayList<NewShareList> infos = newShareListRequest.infos;
                view.onRequestCurrentCalendarSuccess(infos);
            }

            @Override
            public void onError(int i, String s) {
                view.onRequestCurrentCalendarFail();
            }
        });
    }

    /**
     * 查询当日新债
     */
    @Override
    public void requestCurrentBndNewSharesCal() {
        String date = DateUtil.CurrentDateConvert();
        BndNewSharesCalRequest bndNewSharesCalRequest = new BndNewSharesCalRequest();
        F10Request.SRC = Setting.getDataSource();  // new
        bndNewSharesCalRequest.send(date, F10Request.SRC, new ResponseCallback(bndNewSharesCalRequest) {
            @Override
            public void onBack(Response response) {
                F10V2Response f10V2Response = (F10V2Response) response;
                if( null == f10V2Response || null == f10V2Response.info){
                    return;
                }
                view.onRequestCurrentBndNewSharesCalSuccess(f10V2Response.info);
            }

            @Override
            public void onError(int i, String s) {
                view.onRequestCurrentBndNewSharesCalFail(i,s);
            }
        });
    }

    /**
     * 查询沪深A股涨跌平家数
     * @param code
     */
    @Override
    public void requestUpDownSame(String code) {
        RequestManager.cancel(requestUpDownSign);
        requestUpDownSign = RequestManager.request(new UpDownSameRunable(code) {
            @Override
            public void onBack(UpdownsResponse response) {
                if( null == response || null == response.mUpdownsItem){
                    return;
                }
                view.onRequestUpDownSameSuccess(response.mUpdownsItem);
            }

            @Override
            public void onError(int i, String error) {
                view.onRequestUpDownSameFail();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        queryQuoteIndex();
        requestRank();
    }

    @Override
    public void onPause() {
        super.onPause();
        for(int i = 0; i < 4;i++){
            RequestManager.cancel(requestSigns[i]);
        }
        RequestManager.cancel(requestSign);
        RequestManager.cancel(requestUpDownSign);
    }

    private void queryQuoteIndex(){
        requestSign = RequestManager.request(new QuoteRunable(str1,null) {
            @Override
            public void onBack(QuoteResponse response) {
                ArrayList<QuoteItem> list = response.quoteItems;
                view.onIndexSuccess(list);
            }

            @Override
            public void onError(int i, String error) {

            }
        });
    }
    @Override
    public void setView(SHSZContract.View view) {
        this.view = view;
    }

}
