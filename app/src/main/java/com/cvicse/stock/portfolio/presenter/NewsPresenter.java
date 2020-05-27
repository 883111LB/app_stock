package com.cvicse.stock.portfolio.presenter;

import com.cvicse.base.mvp.BasePresenter;
import com.cvicse.stock.common.Setting.Setting;
import com.cvicse.stock.http.ResponseCallback;
import com.cvicse.stock.portfolio.presenter.constract.NewsConstract;
import com.cvicse.stock.utils.MyStocksUtils;
import com.mitake.core.StockNewsItem;
import com.mitake.core.request.F10Request;
import com.mitake.core.request.StockNewsListRequest;
import com.mitake.core.response.Response;
import com.mitake.core.response.StockNewsListResponse;

import java.util.ArrayList;

/**
 * Created by ding_syi on 17-1-6.
 */
public class NewsPresenter extends BasePresenter implements NewsConstract.Presenter {
    private  NewsConstract.View view;
    ArrayList<StockNewsItem> stockNewsItems;
    private ResponseCallback loadResponseCallback,refreshResponseCallback;
    @Override
    public void requestNews() {
        if(isRunning())return;
        /**
         * 下拉刷新
         * @param parameter vin或运转单编号
         */
        StockNewsListRequest stockNewsListRequest=new StockNewsListRequest();
        F10Request.SRC = Setting.getDataSource();  // new
        stockNewsListRequest.sendV2(MyStocksUtils.getSelectCode(),-1, null, refreshResponseCallback = new ResponseCallback(stockNewsListRequest) {

            @Override
            public void onBack(Response response) {
                StockNewsListResponse stockNewsListResponse= (StockNewsListResponse) response;
                stockNewsItems = stockNewsListResponse.list;
                view.newsSuccess(stockNewsItems);
            }

            @Override
            public void onError(int i, String s) {
                view.newsFail();
            }
        });
    }

    @Override
    public void requestNewsMore() {
        if(isRunning())return;
        /**
         * 下拉刷新
         * @param parameter vin或运转单编号
         */
        StockNewsListRequest stockNewsListRequest=new StockNewsListRequest();
        F10Request.SRC = Setting.getDataSource();  // new
        stockNewsListRequest.sendV2(MyStocksUtils.getSelectCode(),5, getLastId(), loadResponseCallback = new ResponseCallback(stockNewsListRequest) {

            @Override
            public void onBack(Response response) {
                StockNewsListResponse stockNewsListResponse= (StockNewsListResponse) response;
                ArrayList<StockNewsItem> list = stockNewsListResponse.list;
                if(stockNewsItems == null){
                    stockNewsItems = list;
                } else {
                    stockNewsItems.addAll(list);
                }
                view.newsSuccess(stockNewsItems);
            }

            @Override
            public void onError(int i, String s) {
                view.newsFail();
            }
        });
    }
    private String getLastId(){
        return stockNewsItems == null || stockNewsItems.size()== 0 ? null:stockNewsItems.get(stockNewsItems.size() - 1).ID_;
    }

    @Override
    public void setView(Object view) {
        this.view = (NewsConstract.View) view;
    }

    private boolean isRunning(){
        if(loadResponseCallback != null){
            if(!loadResponseCallback.isFinished())
                return true;
        }
        if(refreshResponseCallback != null){
            if(!refreshResponseCallback.isFinished())
                return true;
        }
        return false;
    }

}
