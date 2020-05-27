package com.cvicse.stock.markets.presenter;

import com.cvicse.base.mvp.BasePresenter;
import com.cvicse.stock.http.loop.OrderQuantityRunnable;
import com.cvicse.stock.http.loop.QuoteDetailRunable;
import com.cvicse.stock.http.loop.RequestManager;
import com.cvicse.stock.http.loop.RunTaskState;
import com.cvicse.stock.markets.presenter.contract.StockDelegateContract;
import com.mitake.core.QuoteItem;
import com.mitake.core.response.OrderQuantityResponse;
import com.mitake.core.response.QuoteResponse;

/**
 * Created by liu_zlu on 2017/4/21 15:42
 */
public class StockDelegatePresenter extends BasePresenter implements StockDelegateContract.Presenter {
    private StockDelegateContract.View view;
    private QuoteItem quoteItem;
    //循环请求标示
    private RunTaskState requestQuoteSign,requestOrderSign;

    @Override
    public void setView(StockDelegateContract.View view) {
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
        queryOrderQuantity();
    }

    @Override
    public void onPause() {
        super.onPause();
        RequestManager.cancel(requestQuoteSign);
        RequestManager.cancel(requestOrderSign);
    }
    /**
     * 查询股票行情快照
     */
    private void queryQuote() {
        RequestManager.cancel(requestQuoteSign);
        requestQuoteSign = RequestManager.request(new QuoteDetailRunable(quoteItem.id,10) {
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
     * 查询买卖队列
     */
    private void queryOrderQuantity() {
        RequestManager.cancel(requestOrderSign);
        requestOrderSign = RequestManager.request(new OrderQuantityRunnable(quoteItem.id,quoteItem.market,quoteItem.subtype) {

            @Override
            public void onBack(OrderQuantityResponse response) {
                view.onOrderQuantitySuccess(response);
            }

            @Override
            public void onError(int i, String error) {

            }
        });
    }
}
