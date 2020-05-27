package com.cvicse.stock.seachstock;

import android.content.Context;

import com.cvicse.base.mvp.BasePresenter;
import com.cvicse.stock.http.ResponseCallback;
import com.cvicse.stock.seachstock.seachConstract.SearchConstract;
import com.cvicse.stock.utils.SearchHistoryUtils;
import com.mitake.core.SearchResultItem;
import com.mitake.core.request.QuoteDetailRequest;
import com.mitake.core.request.SearchRequest;
import com.mitake.core.response.QuoteResponse;
import com.mitake.core.response.Response;
import com.mitake.core.response.SearchResponse;

import java.util.ArrayList;

/**
 * Created by ding_syi on 17-1-10.
 */
public class SearchPresenter extends BasePresenter implements SearchConstract.Presenter {
    private  SearchConstract.View view;

    @Override
    public void searchStock(Context context,String parameter) {
        if( null== context){
            searchHistory();
            return;
        }
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.send(parameter, null, 0, true,new ResponseCallback(searchRequest) {
            @Override
            public void onBack(Response response) {
                //SplashActivity.isHasLocalSearchFile = true;
                SearchResponse searchResponse = (SearchResponse) response;
                ArrayList<SearchResultItem> list = searchResponse.results;
                view.onSearchSuccess(list);
            }

            @Override
            public void onError(int i, String s) {
                view.onSearchFail();
            }
        });
    }

    @Override
    public void requestQuote(String code) {
        QuoteDetailRequest quoteDetailRequest = new QuoteDetailRequest();
        quoteDetailRequest.send(code, new ResponseCallback() {
            @Override
            public void onBack(Response response) {
                QuoteResponse quoteResponse = (QuoteResponse) response;
                if( null == quoteResponse || null == quoteResponse.quoteItems || quoteResponse.quoteItems.isEmpty()){
                    return;
                }
                view.onRequestQuoteSuccess(quoteResponse.quoteItems);
            }

            @Override
            public void onError(int i, String s) {
                view.onRequestQuoteFail();
            }
        });
    }

    private void searchHistory() {
        view.onHistorySuccess((ArrayList<SearchResultItem>) SearchHistoryUtils.getHistoryCode());

    }
    @Override
    public void onResume() {
        super.onResume();
        searchHistory();
    }

    @Override
    public void setView(Object view) {
        this.view  = (SearchConstract.View) view;
    }

}
