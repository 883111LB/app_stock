package com.cvicse.stock.markets.presenter;

import com.cvicse.base.mvp.BasePresenter;
import com.cvicse.stock.http.ResponseCallback;
import com.cvicse.stock.http.loop.QuoteDetailRunable;
import com.cvicse.stock.http.loop.RequestManager;
import com.cvicse.stock.http.loop.RunTaskState;
import com.cvicse.stock.markets.presenter.contract.StockTenContract;
import com.mitake.core.QuoteItem;
import com.mitake.core.request.BrokerInfoRequest;
import com.mitake.core.response.QuoteResponse;
import com.mitake.core.response.Response;

/**
 * 十档详细
 * Created by liu_zlu on 2017/3/7 10:16
 */
public class StockTenPresenter extends BasePresenter implements StockTenContract.Presenter {
    private StockTenContract.View view;
    private QuoteItem quoteItem;
    private RunTaskState requestSign;

    @Override
    public void setView(StockTenContract.View view) {
        this.view = view;
    }

    /**
     * 初始化stockId
     *
     * @param quoteItem
     */
    @Override
    public void init(QuoteItem quoteItem) {
        this.quoteItem = quoteItem;
    }

    @Override
    public void onResume() {
        super.onResume();
        queryQuote();
        queryBrokerInfo();
    }

    @Override
    public void onPause() {
        super.onPause();
        RequestManager.cancel(requestSign);
    }

    /**
     * 查询股票行情快照
     */
    private void queryQuote() {
        RequestManager.cancel(requestSign);
        requestSign = RequestManager.request(new QuoteDetailRunable(quoteItem.id,10) {
            @Override
            public void onBack(QuoteResponse response) {
                view.onQuoteSuccess(response);
            }

            @Override
            public void onError(int i, String error) {

            }
        });
    }

    /**
     * 查询经济席位
     * 仅仅是为了调用此接口，保存此接口的数据
     */

    private void queryBrokerInfo(){
        BrokerInfoRequest request  = new BrokerInfoRequest();
        request.send(quoteItem.id, new ResponseCallback(request) {
            @Override
            public void onBack(Response response) {

            }

            @Override
            public void onError(int i, String s) {

            }
        });
    }
}
