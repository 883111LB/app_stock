package com.cvicse.stock.portfolio.presenter;

import com.cvicse.base.mvp.BasePresenter;
import com.cvicse.stock.common.Setting.Setting;
import com.cvicse.stock.http.ResponseCallback;
import com.cvicse.stock.portfolio.presenter.constract.ReportConstract;
import com.cvicse.stock.utils.MyStocksUtils;
import com.mitake.core.StockReportItem;
import com.mitake.core.request.F10Request;
import com.mitake.core.request.StockReportListRequest;
import com.mitake.core.response.Response;
import com.mitake.core.response.StockReportListResponse;

import java.util.ArrayList;

/**
 * 研报页面
 * Created by ding_syi on 17-1-11.
 */
public class ReportPresenter extends BasePresenter implements ReportConstract.Presenter{
    private  ReportConstract.View view;
    ArrayList<StockReportItem> stockBulletinItems;
    private ResponseCallback loadResponseCallback,refreshResponseCallback;
    @Override
    public void requestReport() {
        if(isRunning())return;
        StockReportListRequest stockNewsListRequest=new StockReportListRequest();
        F10Request.SRC = Setting.getDataSource();  // new
        stockNewsListRequest.sendV2(MyStocksUtils.getSelectCode(),-1, null,null,refreshResponseCallback = new ResponseCallback(stockNewsListRequest) {
            @Override
            public void onBack(Response response) {
                StockReportListResponse stockNewsListResponse= (StockReportListResponse) response;
                stockBulletinItems = stockNewsListResponse.list;
                view.reportSuccess(stockBulletinItems);
            }

            @Override
            public void onError(int i, String s) {
                view.reportFail();
            }
        });
    }

    @Override
    public void requestReportLoad() {
        if(isRunning())return;
        StockReportListRequest stockNewsListRequest=new StockReportListRequest();
        F10Request.SRC = Setting.getDataSource();  // new
        stockNewsListRequest.sendV2(MyStocksUtils.getSelectCode(),5, getLastId(),null,loadResponseCallback = new ResponseCallback(stockNewsListRequest) {
            @Override
            public void onBack(Response response) {
                StockReportListResponse stockNewsListResponse= (StockReportListResponse) response;
                ArrayList<StockReportItem> list = stockNewsListResponse.list;
                if(stockBulletinItems == null){
                    stockBulletinItems = list;
                } else {
                    stockBulletinItems.addAll(list);
                }
                view.reportSuccess(stockBulletinItems);
            }

            @Override
            public void onError(int i, String s) {
                view.reportFail();
            }
        });
    }

    private String getLastId(){
        return stockBulletinItems == null || stockBulletinItems.size()== 0 ? null:stockBulletinItems.get(stockBulletinItems.size() - 1).ID_;
    }
    @Override
    public void setView(Object view) {
        this.view = (ReportConstract.View) view;
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
