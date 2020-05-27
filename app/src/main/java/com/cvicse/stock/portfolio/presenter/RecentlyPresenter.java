package com.cvicse.stock.portfolio.presenter;

import android.text.TextUtils;

import com.cvicse.base.mvp.BasePresenter;
import com.cvicse.stock.http.loop.QuoteRunable;
import com.cvicse.stock.http.loop.RequestManager;
import com.cvicse.stock.http.loop.RunTaskState;
import com.cvicse.stock.portfolio.presenter.constract.RecentlyConstract;
import com.cvicse.stock.utils.MyBrowseUtils;
import com.mitake.core.QuoteItem;
import com.mitake.core.response.QuoteResponse;

import java.util.ArrayList;

/**
 * Created by ding_syi on 17-1-14.
 */
public class RecentlyPresenter extends BasePresenter implements RecentlyConstract.Presenter{
    RecentlyConstract.View view;
    private RunTaskState requestSign;

    @Override
    public void requestMyStock() {
        if(TextUtils.isEmpty(MyBrowseUtils.getSelectCode())){
            return;
        }
        requestSign = RequestManager.request(new QuoteRunable(MyBrowseUtils.getSelectCode(),null) {
            @Override
            public void onBack(QuoteResponse response) {
                QuoteResponse quoteResponse = (QuoteResponse)response;
                ArrayList<QuoteItem> list = quoteResponse.quoteItems;
                view.requesetSuccess(list);
            }

            @Override
            public void onError(int i, String error) {

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        requestMyStock();
    }

    @Override
    public void onPause() {
        super.onPause();
        RequestManager.cancel(requestSign);
    }

    @Override
    public void setView(Object view) {
        this.view = (RecentlyConstract.View) view;
    }
}
