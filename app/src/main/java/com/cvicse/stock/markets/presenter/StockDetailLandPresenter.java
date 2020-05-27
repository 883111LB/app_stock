package com.cvicse.stock.markets.presenter;

import com.cvicse.base.mvp.BasePresenter;
import com.cvicse.stock.http.loop.QuoteDetailRunable;
import com.cvicse.stock.http.loop.RequestManager;
import com.cvicse.stock.http.loop.RunTaskState;
import com.cvicse.stock.markets.presenter.contract.StockDetailLandContract;
import com.mitake.core.QuoteItem;
import com.mitake.core.response.QuoteResponse;

/**
 * Created by liu_zlu on 2017/2/28 09:24
 */
public class StockDetailLandPresenter extends BasePresenter implements StockDetailLandContract.Presenter {

    private StockDetailLandContract.View view;
    private QuoteItem quoteItem;
    private RunTaskState requestSign;

    @Override
    public void setView(StockDetailLandContract.View view) {
        this.view = view;
    }

    /**
     * 初始化stockId
     * @param quoteItem
     */
    @Override
    public void init(QuoteItem quoteItem) {
        this.quoteItem = quoteItem;
    }

    /**
     * 查询股票行情快照
     */
    public void queryQuote() {
        RequestManager.cancel(requestSign);
        requestSign = RequestManager.request(new QuoteDetailRunable(quoteItem.id) {
            @Override
            public void onBack(QuoteResponse response) {
                view.onQuoteSuccess(response);
            }

            @Override
            public void onError(int i, String error) {

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        queryQuote();
    }

    @Override
    public void onPause() {
        super.onPause();
        RequestManager.cancel(requestSign);
    }
}
