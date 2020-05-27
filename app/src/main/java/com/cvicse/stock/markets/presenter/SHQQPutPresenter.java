package com.cvicse.stock.markets.presenter;

import com.cvicse.base.mvp.BasePresenter;
import com.cvicse.stock.http.ResponseCallback;
import com.cvicse.stock.markets.presenter.contract.SHQQPutConstract;
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
public class SHQQPutPresenter extends BasePresenter implements SHQQPutConstract.Presenter {
    SHQQPutConstract.View mView;

    /**
     * 查询认沽期权
     * "510050.sh_PUT"
     */
    @Override
    public void requestSHQQPut(String stockId) {
        OptionQuoteRequest optionQuoteRequest = new OptionQuoteRequest();
        optionQuoteRequest.send(stockId+"_PUT", "0", new ResponseCallback(optionQuoteRequest) {
            @Override
            public void onBack(Response response) {
                OptionQuoteResponse optionQuoteResponse= (OptionQuoteResponse) response;
                ArrayList<QuoteItem> list = optionQuoteResponse.list;
                for(QuoteItem quoteItem : list){
                    if(STOCK_NAME.equals(quoteItem.name)){
                        list.remove(quoteItem);
                        break;
                    }
                }
                mView.onRequestPutSuccess(DataConvert.QuoteItemList(list));
            }

            @Override
            public void onError(int i, String s) {

            }
        });
    }


    @Override
    public void setView(SHQQPutConstract.View view) {
        mView = view;
    }


}
