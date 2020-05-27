package com.cvicse.stock.markets.presenter;

import com.cvicse.base.mvp.BasePresenter;
import com.cvicse.stock.http.loop.QuoteRunable;
import com.cvicse.stock.http.loop.RequestManager;
import com.cvicse.stock.http.loop.RunTaskState;
import com.cvicse.stock.markets.presenter.contract.SHSZTopContract;
import com.cvicse.stock.utils.DataConvert;
import com.cvicse.stock.utils.MyBrowseUtils;
import com.mitake.core.OrderQuantityItem;
import com.mitake.core.QuoteItem;
import com.mitake.core.network.NetworkManager;
import com.mitake.core.network.TCPManager;
import com.mitake.core.response.QuoteResponse;

import java.util.ArrayList;

/**
 * Created by ruan_ytai on 17-1-16.
 */

public class SHSZTopPresenter extends BasePresenter implements SHSZTopContract.Presenter {
    private SHSZTopContract.View view;

    String[] subscribeArray;

    //上，深，创指数
    private String str1 = "000001.sh,399001.sz,399006.sz";
    //中小，腾讯济安，沪深300
    private String str2 = "399005.sz,000847.sh,000300.sh";
    private String[] array = {"000001.sh","399001.sz","399006.sz","399005.sz","000847.sh","000300.sh"};
    private String id;
    private RunTaskState requestSign;
    public SHSZTopPresenter(){

    }

    /**
     * 请求指数
     */
    @Override
    public void requestIndex(String type) {
        if(type.equals("firstTop")){
            id = str1;
        }else if(type.equals("secondTop")){
            id = str2;
        }
        RequestManager.cancel(requestSign);
        requestSign = RequestManager.request(new QuoteRunable(MyBrowseUtils.getSelectCode(),null) {
            @Override
            public void onBack(QuoteResponse response) {
                QuoteResponse quoteResponse = (QuoteResponse)response;
                ArrayList<QuoteItem> list = quoteResponse.quoteItems;
                view.onIndexSuccess(DataConvert.QuoteItemList(list));
            }

            @Override
            public void onError(int i, String error) {

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        String[] queryIndex1 = str1.split(",");
        String[] queryIndex2 = str2.split(",");
        int length = queryIndex1.length + queryIndex2.length;
        String[] stringReal = new String[length];
        for(int i=0;i<queryIndex1.length;i++){
            stringReal[i] = queryIndex1[i];
        }
        for(int i=0;i<queryIndex2.length;i++){
            stringReal[i+queryIndex1.length] = queryIndex2[i];
        }

        subscribeArray = stringReal;

        NetworkManager.getInstance().setIPush(new NetworkManager.IPush() {
            @Override
            public void push(QuoteItem quoteItem, ArrayList<OrderQuantityItem> arrayList, ArrayList<OrderQuantityItem> arrayList1) {
                if(isBounds(quoteItem,array)){
                    view.onIndexTcp(quoteItem);
                }
            }

//            @Override
//            public void pushHttp(QuoteResponse quoteResponse) {
//
//            }
        });
        TCPManager.getInstance().subscribe(subscribeArray);
    }

    private boolean isBounds(QuoteItem quoteItem,String[] strings){
        if(strings == null){
            return false;
        }
        for(String string:strings){
            if(quoteItem.id.equals(string)){
                return true;
            }
        }
        return false;
    }
    @Override
    public void onPause() {
        super.onPause();
        TCPManager.getInstance().unsubscribe(subscribeArray);
    }

    @Override
    public void setView(SHSZTopContract.View view) {
        this.view = view;
    }
}
