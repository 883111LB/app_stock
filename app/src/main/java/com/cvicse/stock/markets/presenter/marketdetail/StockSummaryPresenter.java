package com.cvicse.stock.markets.presenter.marketdetail;

import com.cvicse.base.mvp.BasePresenter;
import com.cvicse.stock.common.Setting.Setting;
import com.cvicse.stock.http.ResponseCallback;
import com.cvicse.stock.markets.helper.MarginTradingHelper;
import com.cvicse.stock.markets.presenter.marketdetail.contract.StockSummaryContract;
import com.mitake.core.f10request.FinanceMrgninRequest;
import com.mitake.core.request.BlockTradeRequest;
import com.mitake.core.request.BonusFinanceRequest;
import com.mitake.core.request.F10Request;
import com.mitake.core.request.F10Type;
import com.mitake.core.request.F10V2Request;
import com.mitake.core.request.ForecastRatingRequest;
import com.mitake.core.request.ForecastyearRequest;
import com.mitake.core.request.ImportantnoticeRequest;
import com.mitake.core.request.NewIndexRequest;
import com.mitake.core.request.TradeDetailRequest;
import com.mitake.core.response.BonusFinanceResponse;
import com.mitake.core.response.F10V2Response;
import com.mitake.core.response.ForecastRatingResponse;
import com.mitake.core.response.ForecastyearResponse;
import com.mitake.core.response.ImportantnoticeResponse;
import com.mitake.core.response.NewIndexResponse;
import com.mitake.core.response.Response;
import com.mitake.core.response.TradeDetailResponse;

import java.util.HashMap;
import java.util.List;

/**
 * 概要页面
 * Created by liu_zlu on 2017/2/9 16:42
 */
public class StockSummaryPresenter extends BasePresenter implements StockSummaryContract.Presenter {

    private StockSummaryContract.View view;
    private MarginTradingHelper mMarginTradingHelper;
    private ResponseCallback newIndexCallback,importantnoticeCallback,
    bonusFinanceCallback,tradeDetailInfoCallback,forecastyearCallback,forecastRatingCallback;

    private String stockId;
    private int bigPageIndex = 0;  // 大事提醒页码

    @Override
    public void setView(StockSummaryContract.View view) {
        this.view = view;
    }

    @Override
    public void init(String stockId) {
        mMarginTradingHelper = new MarginTradingHelper();
        this.stockId = stockId;
    }

    @Override
    public void queryMarketSummary() {
        queryNewIndex();
        queryBoundsFinance();
        queryTradeDetail();
        queryForecastYear();
        queryForecastRating();
    }

    /**
     * 大事提醒
     * @param apiType
     */
    @Override
    public void queryImportantNotice(String apiType) {
        bigPageIndex = 0;
        F10V2Request importantnoticeRequest = new F10V2Request();
        F10Request.SRC = Setting.getDataSource();  // new
        importantnoticeRequest.send(stockId,F10Request.SRC,bigPageIndex+",10",apiType, new ResponseCallback(importantnoticeRequest) {
            @Override
            public void onBack(Response response) {
                F10V2Response mF10V2Response = (F10V2Response) response;
                view.onImportantNoticeSuccess(mF10V2Response.infos);
            }

            @Override
            public void onError(int i, String s) {

            }
        });
    }

    /**
     * 加载更多大事提醒
     * @param apiType
     */
    @Override
    public void loadImportantNotice(String apiType) {
        F10V2Request importantnoticeRequest = new F10V2Request();
        F10Request.SRC = Setting.getDataSource();  // new
        importantnoticeRequest.send(stockId,F10Request.SRC,(bigPageIndex+1)+",10",apiType, new ResponseCallback(importantnoticeRequest) {
            @Override
            public void onBack(Response response) {
                F10V2Response mF10V2Response = (F10V2Response) response;
                view.onImportantNoticeSuccess(mF10V2Response.infos);
            }

            @Override
            public void onError(int i, String s) {

            }
        });
    }

    /**
     * 大宗交易
     * @param
     */
    @Override
    public void requestBlockTrade() {
        BlockTradeRequest blockTradeRequest = new BlockTradeRequest();
        F10Request.SRC = Setting.getDataSource();  // new
        blockTradeRequest.send(stockId, F10Request.SRC, new ResponseCallback(blockTradeRequest) {
            @Override
            public void onBack(Response response) {
                F10V2Response mF10V2Response = (F10V2Response) response;
                view.onBlockTradeSuccess(mF10V2Response.infos);
            }

            @Override
            public void onError(int i, String s) {
                view.onBlockTradeSuccess(null);
            }
        });
    }
    //董秘问答
    @Override
    public void requestIntearActive() {
        bigPageIndex=0;
        final F10V2Request intearActiveRequest=new F10V2Request();
        F10Request.SRC = Setting.getDataSource();  // new
        intearActiveRequest.send(stockId, F10Request.SRC,bigPageIndex+",20", F10Type.NEWS_INTEARACTIVE,null,"QUESTIONTIME_1", new ResponseCallback(intearActiveRequest) {
            @Override
            public void onBack(Response response) {
                F10V2Response mF10V2Response= (F10V2Response) response;
                if( null == mF10V2Response || null == mF10V2Response.info || mF10V2Response.info.isEmpty()){
                    return;
                }
                List<HashMap<String,Object>> infoList= (List<HashMap<String, Object>>) mF10V2Response.info.get("List");
                view.onIntearActiveSuccess(infoList);
            }

            @Override
            public void onError(int i, String s) {

            }
        });
    }

    /**
     * 龙虎榜
     */
    @Override
    public void requestCharts5BuysSells() {
        // 龙虎榜-买入前5营业部
        F10V2Request buyRequest = new F10V2Request();
        F10Request.SRC = Setting.getDataSource();  // new
        buyRequest.send(stockId, F10Request.SRC, F10Type.D_CHARTS5BUYS, new ResponseCallback(buyRequest) {
            @Override
            public void onBack(Response response) {
                F10V2Response response1 = (F10V2Response) response;
                if( null == response1 || null == response1.infos || response1.infos.isEmpty()){
                    return;
                }
                view.onCharts5BuysSuccess(response1.infos);
            }

            @Override
            public void onError(int i, String s) {

            }
        });

        // 龙虎榜-卖出前5营业部
        F10V2Request sellRequest = new F10V2Request();
        F10Request.SRC = Setting.getDataSource();  // new
        sellRequest.send(stockId, F10Request.SRC, F10Type.D_CHARTS5SELLS, new ResponseCallback(sellRequest) {
            @Override
            public void onBack(Response response) {
                F10V2Response response1 = (F10V2Response) response;
                if( null == response1 || null == response1.infos || response1.infos.isEmpty()){
                    return;
                }
                view.onCharts5SellsSuccess(response1.infos);
            }

            @Override
            public void onError(int i, String s) {

            }
        });
    }

    /**
     * 个股融资融券
     */
    @Override
    public void requestStockMarginTrade() {
        FinanceMrgninRequest financeMrgninRequest = new FinanceMrgninRequest();
        financeMrgninRequest.sendStock(stockId, Setting.getDataSource(), "0,100", "",new ResponseCallback(financeMrgninRequest) {
            @Override
            public void onBack(Response response) {
                F10V2Response f10V2Response = (F10V2Response) response;
                if (null == f10V2Response.infos){
                    return;
                }
                view.onStockMarginTrade(mMarginTradingHelper.getMarginSubMarket(f10V2Response.infos));
            }

            @Override
            public void onError(int i, String s) {

            }
        });
    }

    /**
     * 最新指标
     */
    private void queryNewIndex() {
        NewIndexRequest newIndexRequest = new NewIndexRequest();
        F10Request.SRC = Setting.getDataSource();  // new
        newIndexRequest.sendV2(stockId, newIndexCallback = new ResponseCallback(newIndexRequest) {
            @Override
            public void onBack(Response response) {
                NewIndexResponse newIndexResponse = (NewIndexResponse) response;
                view.onNewIndexSuccess(newIndexResponse.info);
            }

            @Override
            public void onError(int i, String s) {

            }
        });
    }

    private void queryImportantNoticeOld() {
        ImportantnoticeRequest importantnoticeRequest = new ImportantnoticeRequest();
        F10Request.SRC = Setting.getDataSource();  // new
        importantnoticeRequest.sendV2(stockId, importantnoticeCallback = new ResponseCallback(importantnoticeRequest) {
            @Override
            public void onBack(Response response) {
                ImportantnoticeResponse importantnoticeResponse = (ImportantnoticeResponse) response;
//                view.onImportantNoticeSuccess(importantnoticeResponse.list);
            }

            @Override
            public void onError(int i, String s) {

            }
        });
    }

    /**
     * 分红配送
     */
    private void queryBoundsFinance() {
        BonusFinanceRequest bonusFinanceRequest = new BonusFinanceRequest();
        F10Request.SRC = Setting.getDataSource();  // new
        bonusFinanceRequest.sendV2(stockId,bonusFinanceCallback = new ResponseCallback(bonusFinanceRequest) {
            @Override
            public void onBack(Response response) {
                BonusFinanceResponse bonusFinanceResponse = (BonusFinanceResponse) response;
                view.onBonusFinanceSuccess(bonusFinanceResponse.list);
            }

            @Override
            public void onError(int i, String s) {

            }
        });
    }

    /**
     * 融资证券
     */
    private void queryTradeDetail() {
        TradeDetailRequest tradeDetailInfoRequest = new TradeDetailRequest();
        F10Request.SRC = Setting.getDataSource();  // new
        tradeDetailInfoRequest.sendV2(stockId,tradeDetailInfoCallback = new ResponseCallback(tradeDetailInfoRequest) {
            @Override
            public void onBack(Response response) {
                TradeDetailResponse tradeDetailInfoResponse = (TradeDetailResponse) response;
                view.onTradeDetailSuccess(tradeDetailInfoResponse.info);
            }

            @Override
            public void onError(int i, String s) {

            }
        });

    }

    /**
     * 机构预测
     */
    private void queryForecastYear() {
        ForecastyearRequest forecastyearRequest = new ForecastyearRequest();
        F10Request.SRC = Setting.getDataSource();  // new
        forecastyearRequest.sendV2(stockId,forecastyearCallback = new ResponseCallback(forecastyearRequest) {
            @Override
            public void onBack(Response response) {
                ForecastyearResponse forecastyearResponse = (ForecastyearResponse) response;
                view.onForecastyearSuccess(forecastyearResponse.info);
            }

            @Override
            public void onError(int i, String s) {

            }
        });
    }

    /**
     * 机构评等
     */
    private void queryForecastRating() {
        ForecastRatingRequest forecastRatingRequest = new ForecastRatingRequest();
        F10Request.SRC = Setting.getDataSource();  // new
        forecastRatingRequest.sendV2(stockId,forecastRatingCallback = new ResponseCallback(forecastRatingRequest) {
            @Override
            public void onBack(Response response) {
                ForecastRatingResponse forecastRatingResponse = (ForecastRatingResponse) response;
                view.onForecastRatingSuccess(forecastRatingResponse.info);
            }

            @Override
            public void onError(int i, String s) {

            }
        });
    }

    /**
     * 所有请求是否全部结束
     * @return
     */
    private boolean isFinished(){
        if(first){
            first = !first;
            return true;
        }
        return newIndexCallback.isFinished() && importantnoticeCallback.isFinished()
                && bonusFinanceCallback.isFinished() && tradeDetailInfoCallback.isFinished()
                && forecastyearCallback.isFinished() && forecastRatingCallback.isFinished();
    }
    private boolean first = true;
}
