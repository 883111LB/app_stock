package com.cvicse.stock.portfolio.presenter;

import com.cvicse.base.mvp.BasePresenter;
import com.cvicse.stock.common.Setting.Setting;
import com.cvicse.stock.http.ResponseCallback;
import com.cvicse.stock.portfolio.presenter.constract.NoticeConstract;
import com.cvicse.stock.utils.MyStocksUtils;
import com.mitake.core.StockBulletinItem;
import com.mitake.core.request.F10Request;
import com.mitake.core.request.StockBulletinListRequest;
import com.mitake.core.response.Response;
import com.mitake.core.response.StockBulletinListResponse;

import java.util.ArrayList;

/**
 * Created by ding_syi on 17-1-9.
 */
public class NoticePresenter extends BasePresenter implements NoticeConstract.PresenterNotice{
    private  NoticeConstract.View view;
    ArrayList<StockBulletinItem> stockBulletinItems;
    private ResponseCallback loadResponseCallback,refreshResponseCallback;
    @Override
    public void pullToRefreshNotice() {
        if(isRunning())return;
        F10Request.SRC = Setting.getDataSource();  // new
        StockBulletinListRequest stockBulletinListRequest=new StockBulletinListRequest();
        stockBulletinListRequest.sendV2(MyStocksUtils.getSelectCode(),-1,null,null, refreshResponseCallback = new ResponseCallback(stockBulletinListRequest) {

            @Override
            public void onBack(Response response) {
                StockBulletinListResponse stockBulletinListResponse = (StockBulletinListResponse) response;
                stockBulletinItems = stockBulletinListResponse.list;

                view.noticeSuccess(stockBulletinItems);
            }

            @Override
            public void onError(int i, String s) {
                view.noticeFail();
            }

        });
    }

    @Override
    public void pullToLoadNotice() {
        if(isRunning())return;
        F10Request.SRC = Setting.getDataSource();  // new
        StockBulletinListRequest stockBulletinListRequest=new StockBulletinListRequest();
        stockBulletinListRequest.sendV2(MyStocksUtils.getSelectCode(),5,getLastId(), null,loadResponseCallback = new ResponseCallback(stockBulletinListRequest) {

            @Override
            public void onBack(Response response) {
                StockBulletinListResponse stockBulletinListResponse = (StockBulletinListResponse) response;
                ArrayList<StockBulletinItem> list = stockBulletinListResponse.list;
                stockBulletinItems.addAll(list);
                view.noticeSuccess(stockBulletinItems);
            }

            @Override
            public void onError(int i, String s) {
                view.noticeFail();
            }

        });
    }

    private String getLastId(){
        return stockBulletinItems == null || stockBulletinItems.size()== 0 ? null:stockBulletinItems.get(stockBulletinItems.size() - 1).ID_;
    }
    @Override
    public void setView(NoticeConstract.View view) {
       this.view = view;
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