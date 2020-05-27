package com.cvicse.stock.markets.presenter;

import com.cvicse.base.mvp.BasePresenter;
import com.cvicse.stock.http.ResponseCallback;
import com.cvicse.stock.http.loop.OptionQuoteRunnable;
import com.cvicse.stock.http.loop.RequestManager;
import com.cvicse.stock.http.loop.RunTaskState;
import com.cvicse.stock.markets.presenter.contract.SHQQAllContract;
import com.cvicse.stock.utils.DataConvert;
import com.mitake.core.QuoteItem;
import com.mitake.core.request.OptionQuoteRequest;
import com.mitake.core.response.OptionQuoteResponse;
import com.mitake.core.response.Response;

import java.util.ArrayList;

import static com.cvicse.stock.markets.ui.SHQQActivity.STOCK_NAME;

/**
 * Created by ding_syi on 17-2-20.
 */
public class SHQQAllPresenter extends BasePresenter implements SHQQAllContract.Presenter {
    private  SHQQAllContract.View mView;
    private String stockId = "";
    private int page = 0;
    private ResponseCallback responseCallback;
    private ArrayList<QuoteItem> quoteItems;
    private RunTaskState requestSign;
    private boolean isMore = false;

    @Override
    public void init(String stockId,String tag) {
        this.stockId = stockId+tag;
    }

    /**
     * 查询全部期权
     */
    @Override
    public void requestSHQQAll() {
        if(responseCallback != null && !responseCallback.isFinished()){
            return;
        }
        page = 0;
        RequestManager.cancel(requestSign);
        requestSign = RequestManager.request(new OptionQuoteRunnable(stockId,page+""){
            @Override
            public void onBack(OptionQuoteResponse response) {
                OptionQuoteRequest optionQuoteRequest = new OptionQuoteRequest();
                optionQuoteRequest.send(stockId,page+"", new ResponseCallback(optionQuoteRequest) {
                    @Override
                    public void onBack(Response response) {
                        OptionQuoteResponse optionQuoteResponse=(OptionQuoteResponse)response;
                        ArrayList<QuoteItem> list=optionQuoteResponse.list;

                        quoteItems = null;
                        for(QuoteItem quoteItem : list){
                            if(STOCK_NAME.equals(quoteItem.name)){
                                list.remove(quoteItem);
                                break;
                            }
                        }
                        mView.onRequestSHQQSuccess(quoteItems = DataConvert.QuoteItemList(list));
                    }

                    @Override
                    public void onError(int i, String s) {

                    }
                });
//                ArrayList<QuoteItem> list = response.list;
//                quoteItems = null;
//                for(QuoteItem quoteItem : list){
//                    if(STOCK_NAME.equals(quoteItem.name)){
//                        list.remove(quoteItem);
//                        break;
//                    }
//                }
//                mView.onRequestSHQQSuccess(quoteItems = DataConvert.QuoteItemList(list));
            }

            @Override
            public void onError(int i, String error) {

            }
        });
    }
    /**
     * 查询更多全部期权
     */
    @Override
    public void requestSHQQAllMore() {
        if(responseCallback != null && !responseCallback.isFinished()){
            return;
        }
        isMore = true;
        RequestManager.cancel(requestSign);
        requestSign = RequestManager.request(new OptionQuoteRunnable(stockId,(page+1)+"") {
            @Override
            public void onBack(OptionQuoteResponse response) {
                OptionQuoteRequest optionQuoteRequest = new OptionQuoteRequest();
                optionQuoteRequest.send(stockId,(page+1)+"", new ResponseCallback(optionQuoteRequest) {
                    @Override
                    public void onBack(Response response) {
                        OptionQuoteResponse optionQuoteResponse=(OptionQuoteResponse)response;
                        ArrayList<QuoteItem> list=optionQuoteResponse.list;

                        if( null== list || list.isEmpty()){
                            return;
                        }
                        if (isMore){
                            isMore = false;
                            page = page+1;
                        }

                        for(QuoteItem quoteItem : list){
                            if(STOCK_NAME.equals(quoteItem.name)){
                                list.remove(quoteItem);
                                break;
                            }
                        }

                        quoteItems.addAll(DataConvert.QuoteItemList(list));
//                mView.onRequestSHQQSuccess(quoteItems); // old 2018-8-2
                        mView.onRequestSHQQSuccess(DataConvert.QuoteItemList(list));
                    }

                    @Override
                    public void onError(int i, String s) {

                    }
                });
//                ArrayList<QuoteItem> list = response.list;
//                if( null== list || list.isEmpty()){
//                    return;
//                }
//                if (isMore){
//                    isMore = false;
//                    page = page+1;
//                }
//
//                for(QuoteItem quoteItem : list){
//                    if(STOCK_NAME.equals(quoteItem.name)){
//                        list.remove(quoteItem);
//                        break;
//                    }
//                }
//
//                quoteItems.addAll(DataConvert.QuoteItemList(list));
////                mView.onRequestSHQQSuccess(quoteItems); // old 2018-8-2
//                mView.onRequestSHQQSuccess(DataConvert.QuoteItemList(list));
            }

            @Override
            public void onError(int i, String error) {

            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        RequestManager.cancel(requestSign);
    }

    @Override
    public void setView(SHQQAllContract.View view) {
        mView = view;
    }
}