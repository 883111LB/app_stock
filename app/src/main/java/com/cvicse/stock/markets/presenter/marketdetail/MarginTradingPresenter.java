package com.cvicse.stock.markets.presenter.marketdetail;

import com.cvicse.base.mvp.BasePresenter;
import com.cvicse.stock.common.Setting.Setting;
import com.cvicse.stock.http.ResponseCallback;
import com.cvicse.stock.markets.data.MarginTradingBo;
import com.cvicse.stock.markets.helper.MarginTradingHelper;
import com.cvicse.stock.markets.presenter.marketdetail.contract.MarginTradingContract;
import com.mitake.core.f10request.FinanceMrgninRequest;
import com.mitake.core.request.QuoteDetailRequest;
import com.mitake.core.response.F10V2Response;
import com.mitake.core.response.QuoteResponse;
import com.mitake.core.response.Response;

import java.util.ArrayList;

/** 融资融券
 * Created by tang_xqing on 2018/8/3.
 */

public class MarginTradingPresenter extends BasePresenter implements MarginTradingContract.Presenter{
    MarginTradingContract.View mView;
    private MarginTradingHelper mMarginTradingHelper;
    private boolean isLoadMore = false;
    private ArrayList<MarginTradingBo> mBoArrayList;

    /**
     * 分市场提供最近交易日
     * @param code
     * @param param
     */
    @Override
    public void requestSubMarket(String code, String param) {
        if( code.contains("沪深")){
            code =code.replace("沪深","sh_sz");
        }else if( code.contains("深")){
            code =code.replace("深","sz");
        }else{
            code =code.replace("沪","sh");
        }
        FinanceMrgninRequest financeMrgninRequest = new FinanceMrgninRequest();
        financeMrgninRequest.sendSubMarket(code, Setting.getDataSource(), param,"", new ResponseCallback(financeMrgninRequest) {
            @Override
            public void onBack(Response response) {
                F10V2Response f10V2Response = (F10V2Response) response;
                ArrayList<MarginTradingBo> marginSubMarket = mMarginTradingHelper.getMarginSubMarket(f10V2Response.infos);
                if( null == marginSubMarket || marginSubMarket.isEmpty() ){
                    return;
                }
                if( isLoadMore ){
                    mBoArrayList.addAll(marginSubMarket);
                    mView.loadMoreSubMarketSuccess(mBoArrayList);
                }else{
                    mBoArrayList = null;
                    mView.requestSubMarketSuccess(mBoArrayList = marginSubMarket);
                }
                isLoadMore = false;
            }

            @Override
            public void onError(int i, String s) {

            }
        });
    }

    /**
     * 融资融券差额接口
     * @param code
     * @param param
     */
    @Override
    public void requestFinDifferencr(final String code, String param) {
        FinanceMrgninRequest financeMrgninRequest = new FinanceMrgninRequest();
        financeMrgninRequest.sendFinDifference(code, Setting.getDataSource(), param,"", new ResponseCallback(financeMrgninRequest) {
            @Override
            public void onBack(Response response) {
                F10V2Response f10V2Response = (F10V2Response) response;
                if (null == f10V2Response.infos){
                    return;
                }
                ArrayList<MarginTradingBo> marginFinDiff = mMarginTradingHelper.getMarginFinDiff(f10V2Response.infos);
                if("sh".equals(code) ){
                    mView.requestFinDifSHSuccess(marginFinDiff);
                }else if("sz".equals(code)){
                    mView.requestFinDifSZSuccess(marginFinDiff);
                }else if("sh_sz".equals(code)){
                    mView.requestFinDifSHSZSuccess(marginFinDiff);
                }
            }

            @Override
            public void onError(int i, String s) {

            }
        });
    }

    /**
     * 个股融资融券接口
     * @param code
     * @param param
     */
    @Override
    public void requestStock(String code, String param) {
        FinanceMrgninRequest financeMrgninRequest = new FinanceMrgninRequest();
        financeMrgninRequest.sendStock(code, Setting.getDataSource(), param, "",new ResponseCallback(financeMrgninRequest) {
            @Override
            public void onBack(Response response) {
                F10V2Response f10V2Response = (F10V2Response) response;
                if (null == f10V2Response.infos){
                    return;
                }
                mView.requestStockSuccess(mMarginTradingHelper.getMarginSubMarket(f10V2Response.infos));
            }

            @Override
            public void onError(int i, String s) {

            }
        });
    }

    @Override
    public void loadMoreSubMarket(String code, String param) {
        isLoadMore = true;
        requestSubMarket(code,param);
    }

    /**
     * 请求行情列表
     * @param codeId
     */
    @Override
    public void requestQuote(String codeId) {
        final QuoteDetailRequest quoteRequest = new QuoteDetailRequest();
        quoteRequest.send(codeId, new ResponseCallback(quoteRequest) {
            @Override
            public void onBack(Response response) {
                QuoteResponse quoteResponse = (QuoteResponse) response;
                if( null == quoteResponse.quoteItems || quoteResponse.quoteItems.isEmpty()){
                    return;
                }
                mView.requestQuoteSuccess(quoteResponse.quoteItems);
            }

            @Override
            public void onError(int i, String s) {

            }
        });
    }

    @Override
    public void setView(MarginTradingContract.View view) {
        mView = view;
    }

    @Override
    public void onResume() {
        super.onResume();
        mMarginTradingHelper  = new MarginTradingHelper();
    }
}
