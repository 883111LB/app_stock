package com.cvicse.stock.markets.presenter.marketdetail;

import com.cvicse.base.mvp.BasePresenter;
import com.cvicse.stock.http.ResponseCallback;
import com.cvicse.stock.markets.presenter.marketdetail.contract.PriceAnalysisContract;
import com.mitake.core.QuoteItem;
import com.mitake.core.bean.log.ErrorInfo;
import com.mitake.core.bean.quote.MarketUpDownItem;
import com.mitake.core.request.QuoteDetailRequest;
import com.mitake.core.request.quote.MarketUpDownRequest;
import com.mitake.core.response.IResponseCallback;
import com.mitake.core.response.IResponseInfoCallback;
import com.mitake.core.response.QuoteResponse;
import com.mitake.core.response.Response;
import com.mitake.core.response.quote.MarketUpDownResponse;

import java.util.ArrayList;

/**
 * 沪深涨跌分析
 * Created by shi_yhui on 2018/11/19.
 */

public class PriceAnalysisPresenter extends BasePresenter implements PriceAnalysisContract.Presenter {

    PriceAnalysisContract.View pView;
    //获取沪深涨跌数据
    public void requestMarketUpDown(){
        MarketUpDownRequest marketUpDownRequest= new MarketUpDownRequest();
        marketUpDownRequest.send(new ResponseCallback(marketUpDownRequest) {
            @Override
            public void onBack(Response response) {
                MarketUpDownResponse marketUpDownResponse=(MarketUpDownResponse)response;
                MarketUpDownItem upDownItem = marketUpDownResponse.upDownItem;
                pView.onMarketUpDownSuccesss(upDownItem);
            }

            @Override
            public void onError(int i, String s) {

            }
        });
//        marketUpDownRequest.send(new IResponseInfoCallback<MarketUpDownResponse>() {
//            @Override
//            public void callback(MarketUpDownResponse response) {
//                MarketUpDownItem upDownItem = response.upDownItem;
//                if (upDownItem!=null){
//                    pView.onMarketUpDownSuccesss(upDownItem);
//                }else {
//                    System.out.println("暂无数据");
//                }
//            }
//
//            @Override
//            public void exception(ErrorInfo errorInfo) {
//            }
//        });
    }
    //获取时间
//    public void requestMarketUpDownTime(){
//        QuoteDetailRequest quoteDetailRequest = new QuoteDetailRequest();
//        quoteDetailRequest.send("000001.sh", new IResponseCallback<QuoteResponse>()
//        {
//            public void callback(QuoteResponse quoteResponse)
//            {
//                ArrayList<QuoteItem> list = quoteResponse.quoteItems;
//                if (list!=null){
//                    pView.onMaeketUpDownTimeSuccess(list);
//                }
//            }
//
//            public void exception(int code, String messaeg)
//            {
//                // TODO  错误处理
//            }
//        });
//    }
    @Override
    public void setView(PriceAnalysisContract.View view) {
        pView=view;
    }
}
