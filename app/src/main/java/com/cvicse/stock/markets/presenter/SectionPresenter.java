package com.cvicse.stock.markets.presenter;

import com.cvicse.base.mvp.BasePresenter;
import com.cvicse.stock.http.loop.BankuaiSortingRunable;
import com.cvicse.stock.http.loop.RequestManager;
import com.cvicse.stock.http.loop.RunTaskState;
import com.cvicse.stock.markets.presenter.contract.SectionContract;
import com.mitake.core.request.CategoryType;
import com.mitake.core.response.Bankuaisorting;
import com.mitake.core.response.BankuaisortingResponse;

import java.util.List;

/**
 * 板块首页
 * Created by ding_syi on 17-1-22.
 */
public class SectionPresenter extends BasePresenter implements SectionContract.Presenter {
    SectionContract.View view;

    private RunTaskState requestTrade;
    private RunTaskState requestNotion;
    private RunTaskState requestArea;
    private RunTaskState requestHKTrade;
    private RunTaskState requestCateFound;
    private RunTaskState requestTradeSW1;
    private RunTaskState requestTradeSW2;
    private RunTaskState requestTradeSzyp;
    private RunTaskState requestAreaSzyp;
    private RunTaskState requestNotionSzyp;
    private RunTaskState requestFz;

    @Override
    public void onPause() {
        super.onPause();
        RequestManager.cancel(requestTrade);
        RequestManager.cancel(requestNotion);
        RequestManager.cancel(requestArea);
        RequestManager.cancel(requestHKTrade);
        RequestManager.cancel(requestCateFound);
        RequestManager.cancel(requestTradeSW1);
        RequestManager.cancel(requestTradeSW2);
        RequestManager.cancel(requestTradeSzyp);
        RequestManager.cancel(requestAreaSzyp);
        RequestManager.cancel(requestNotionSzyp);
    }

    /**
     * 请求热门行业
     */
    @Override
    public void requestHot() {
        RequestManager.cancel(requestTrade);
        requestTrade = RequestManager.request(new BankuaiSortingRunable(CategoryType.CATE_TRADE,"0,6,jzf,0") {
            @Override
            public void onBack(BankuaisortingResponse response) {
                List<Bankuaisorting> bankuaisortings =  response.list;
                view.onTradeRequest(bankuaisortings);
            }

            @Override
            public void onError(int i, String error) {

            }
        });
    }

    /**
     * 请求概念
     */
    @Override
    public void requestConcept() {
        RequestManager.cancel(requestNotion);
        requestNotion = RequestManager.request(new BankuaiSortingRunable(CategoryType.CATE_NOTION,"0,6,jzf,0") {
            @Override
            public void onBack(BankuaisortingResponse response) {
                List<Bankuaisorting> bankuaisortings =  response.list;
                view.onNotionRequest(bankuaisortings);
            }

            @Override
            public void onError(int i, String error) {

            }
        });
    }

    /**
     * 请求地域
     */
    @Override
    public void requestArea() {
        RequestManager.cancel(requestArea);
        requestArea = RequestManager.request(new BankuaiSortingRunable(CategoryType.CATE_AREA,"0,6,jzf,0") {
            @Override
            public void onBack(BankuaisortingResponse response) {
                List<Bankuaisorting> bankuaisortings =  response.list;
                view.onAreaRequest(bankuaisortings);
            }

            @Override
            public void onError(int i, String error) {

            }
        });

    }

    /**
     * 请求港股行业
     */
    @Override
    public void requestHKTrade() {
        RequestManager.cancel(requestHKTrade);
        requestHKTrade = RequestManager.request(new BankuaiSortingRunable(CategoryType.HK_TRADE,"0,6,jzf,0") {
            @Override
            public void onBack(BankuaisortingResponse response) {
                List<Bankuaisorting> bankuaisortings =  response.list;
                view.onHKTradeRequest(bankuaisortings);
            }

            @Override
            public void onError(int i, String error) {

            }
        });
    }
    /**
     * 请求申万一级行业
     */
    @Override
    public void requestTradeSW1() {
        RequestManager.cancel(requestTradeSW1);
        requestTradeSW1 = RequestManager.request(new BankuaiSortingRunable(CategoryType.TRADE_SW1,"0,6,jzf,0") {
            @Override
            public void onBack(BankuaisortingResponse response) {
                List<Bankuaisorting> bankuaisortings =  response.list;
                view.onTradeSW1Request(bankuaisortings);
            }

            @Override
            public void onError(int i, String error) {

            }
        });
    }

    /**
     * 请求申万二级行业
     */
    @Override
    public void requestTradeSW2() {
        RequestManager.cancel(requestTradeSW2);
        requestTradeSW2 = RequestManager.request(new BankuaiSortingRunable(CategoryType.TRADE_SW,"0,6,jzf,0") {
            @Override
            public void onBack(BankuaisortingResponse response) {
                List<Bankuaisorting> bankuaisortings =  response.list;
                view.onTradeSW2Request(bankuaisortings);
            }

            @Override
            public void onError(int i, String error) {

            }
        });
    }

    /**
     * 请求优品行业
     */
    public void requestTradeSzyp(){
        RequestManager.cancel(requestTradeSzyp);
        requestTradeSzyp=RequestManager.request(new BankuaiSortingRunable(CategoryType.TRADE_SZYP,"0,6,jzf,0") {
            @Override
            public void onBack(BankuaisortingResponse response) {
                List<Bankuaisorting> bankuaisortings=response.list;
                view.onTradeSzypRequest(bankuaisortings);
            }

            @Override
            public void onError(int i, String error) {

            }
        });
    }
    /**
     * 请求优品地区
     */
    @Override
    public void requestAreaSzyp() {
        RequestManager.cancel(requestAreaSzyp);
        requestAreaSzyp=RequestManager.request(new BankuaiSortingRunable(CategoryType.AREA_SZYP,"0,6,jzf,0") {
            @Override
            public void onBack(BankuaisortingResponse response) {
                List<Bankuaisorting> bankuaisortings=response.list;
                view.onAreaSzypRequest(bankuaisortings);
            }

            @Override
            public void onError(int i, String error) {

            }
        });
    }
    /**
     * 请求优品概念
     */
    @Override
    public void requestNotionSzyp() {
        RequestManager.cancel(requestNotionSzyp);
        requestNotionSzyp=RequestManager.request(new BankuaiSortingRunable(CategoryType.NOTION_SZYP,"0,6,jzf,0") {
            @Override
            public void onBack(BankuaisortingResponse response) {
                List<Bankuaisorting> bankuaisortings=response.list;
                view.onNotionSzypRequest(bankuaisortings);
            }

            @Override
            public void onError(int i, String error) {

            }
        });
    }

    /**
     * 方正行业
     */
    @Override
    public void requestFzSzyp() {
        RequestManager.cancel(requestFz);
        requestFz=RequestManager.request(new BankuaiSortingRunable("Trade_fz","0,6,jzf,0") {
            @Override
            public void onBack(BankuaisortingResponse response) {
                List<Bankuaisorting> bankuaisortings=response.list;
                view.onFzRequest(bankuaisortings);
            }

            @Override
            public void onError(int i, String error) {

            }
        });
    }

    @Override
    public void setView(SectionContract.View view) {
        this.view = view;
    }
}
