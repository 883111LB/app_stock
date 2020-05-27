package com.cvicse.stock.markets.presenter;

import android.text.TextUtils;

import com.cvicse.base.mvp.BasePresenter;
import com.cvicse.stock.http.ResponseCallback;
import com.cvicse.stock.http.TcpManager;
import com.cvicse.stock.http.loop.AHQuoteRunable;
import com.cvicse.stock.http.loop.ConvertibleDebtRunable;
import com.cvicse.stock.http.loop.DRQuoteRunable;
import com.cvicse.stock.http.loop.QuoteDetailRunable;
import com.cvicse.stock.http.loop.RequestManager;
import com.cvicse.stock.http.loop.RunTaskState;
import com.cvicse.stock.http.loop.UpDownSameRunable;
import com.cvicse.stock.markets.data.AHQuoteBo;
import com.cvicse.stock.markets.data.DRQuoteBo;
import com.cvicse.stock.markets.presenter.contract.MarketDetailContract;
import com.cvicse.stock.utils.DataConvert;
import com.cvicse.stock.utils.StockType;
import com.mitake.core.OrderQuantityItem;
import com.mitake.core.QuoteItem;
import com.mitake.core.bean.UpdownsItem;
import com.mitake.core.bean.quote.ConvertibleBoundItem;
import com.mitake.core.network.NetworkManager;
import com.mitake.core.request.AHQuoteRequest;
import com.mitake.core.request.ConvertibleDebtRequest;
import com.mitake.core.request.HKStockInfoRequest;
import com.mitake.core.request.QuoteDetailRequest;
import com.mitake.core.request.Request;
import com.mitake.core.request.UKQuoteRequest;
import com.mitake.core.request.UpdownsRequest;
import com.mitake.core.response.AHQuoteResponse;
import com.mitake.core.response.ConvertibleBoundResponse;
import com.mitake.core.response.DRLinkQuoteResponse;
import com.mitake.core.response.HKStockInfoResponse;
import com.mitake.core.response.IResponseCallback;
import com.mitake.core.response.QuoteResponse;
import com.mitake.core.response.Response;
import com.mitake.core.response.UKQuoteResponse;
import com.mitake.core.response.UpdownsResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liu_zlu on 2017/2/7 17:08
 */
public class MarketDetailPresenter extends BasePresenter implements MarketDetailContract.Presenter, NetworkManager.IPush {

    MarketDetailContract.View view;
    private QuoteItem quoteItem;
    private RunTaskState requestSign;
    private RunTaskState requestUpDownSign;
    private RunTaskState requestAhQuoteSign;
    private RunTaskState requestConverSign;
    private RunTaskState requsetDRQuoteSign;

    private QuoteResponse mQuoteResponse;

    @Override
    public void setView(MarketDetailContract.View view) {
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
     * 查询港股其他
     */
    @Override
    public void queryHKStockInfo() {
        if (StockType.getType(quoteItem)!=null){
            if(!StockType.getType(quoteItem).isHongKong()){
                return;
            }
        }else {
            return;
        }

        HKStockInfoRequest hkStockInfoRequest = new HKStockInfoRequest();
        hkStockInfoRequest.send(quoteItem.id, quoteItem.subtype, new ResponseCallback(hkStockInfoRequest) {
            @Override
            public void onBack(Response response) {
                HKStockInfoResponse hkStockInfoResponse = (HKStockInfoResponse) response;
                if(null != hkStockInfoResponse.info){
                    view.onHKStockInfoSuccess(hkStockInfoResponse.info);
                }
            }

            @Override
            public void onError(int i, String s) {

            }
        });
    }

    /**
     * 查询AH股行情
     */
    @Override
    public void requestAHQuote() {
        if(TextUtils.isEmpty(quoteItem.ah) ){
            return;
        }
        RequestManager.cancel(requestAhQuoteSign);
        requestAhQuoteSign = RequestManager.request(new AHQuoteRunable(quoteItem.id) {
            @Override
            public void onBack(AHQuoteResponse response) {
                AHQuoteRequest ahQuoteRequest = new AHQuoteRequest();
                ahQuoteRequest.send(quoteItem.id, new ResponseCallback(ahQuoteRequest) {
                    @Override
                    public void onBack(Response response) {
                        AHQuoteResponse ahQuoteResponse=(AHQuoteResponse)response;
                        AHQuoteBo ahQuoteBo = new AHQuoteBo();
                        if( null != response ){
                            ahQuoteBo.setCode(ahQuoteResponse.code);
                            ahQuoteBo.setLastPrice(ahQuoteResponse.lastPrice);
                            ahQuoteBo.setPremiun(ahQuoteResponse.premium);
                            ahQuoteBo.setChangeRate(ahQuoteResponse.changeRate);
                            ahQuoteBo.setUpDownFlag(ahQuoteResponse.upDownFlag);
                        }
                        view.onRequestAhQuoteSuccess(ahQuoteBo);
                    }

                    @Override
                    public void onError(int i, String s) {
                        view.onRequestAhQuoteFail();
                    }
                });
//                AHQuoteBo ahQuoteBo = new AHQuoteBo();
//                if( null != response ){
//                    ahQuoteBo.setCode(response.code);
//                    ahQuoteBo.setLastPrice(response.lastPrice);
//                    ahQuoteBo.setPremiun(response.premium);
//                    ahQuoteBo.setChangeRate(response.changeRate);
//                    ahQuoteBo.setUpDownFlag(response.upDownFlag);
//                }
//                view.onRequestAhQuoteSuccess(ahQuoteBo);
            }

            @Override
            public void onError(int i, String error) {
                view.onRequestAhQuoteFail();
            }
        });
    }

    @Override
    public void requestDRQuote(){
        RequestManager.cancel(requsetDRQuoteSign);
        requsetDRQuoteSign=RequestManager.request(new DRQuoteRunable(quoteItem.id) {
            @Override
            public void onBack(DRLinkQuoteResponse response) {
                DRQuoteBo drQuoteBo=new DRQuoteBo();
                if (null!=response.drLinkQuoteitem){
                    drQuoteBo.setCode(response.drLinkQuoteitem.code);
                    drQuoteBo.setLastPrice(response.drLinkQuoteitem.lastPrice);
                    drQuoteBo.setChangeRate(response.drLinkQuoteitem.changeRate);
                    drQuoteBo.setPremiun(response.drLinkQuoteitem.premium);
                    drQuoteBo.setSubType(response.drLinkQuoteitem.subType);
                    drQuoteBo.setUpDownFlag(response.drLinkQuoteitem.upDownFlag);
                }
                view.onRequestDRQuoteSuccess(drQuoteBo);
            }

            @Override
            public void onError(int i, String error) {
                view.onRequestDRQuoteFail();
            }
        });
    }
    //查询UK独有数据
    @Override
    public void requsetUKQuote() {
        if (StockType.getType(quoteItem)!=null){
            if (!StockType.getType(quoteItem).isUK()){
                return;
            }
        }else {
            return;
        }
        UKQuoteRequest request = new UKQuoteRequest();
        request.send(quoteItem.id, new IResponseCallback<UKQuoteResponse>() {
            @Override
            public void callback(UKQuoteResponse response) {
                UKQuoteResponse ukQuoteResponse=response;
                if (ukQuoteResponse.ukQuoteItems!=null){
                    view.onUKStockInfoSuccess(ukQuoteResponse.ukQuoteItems);
                }
            }

            @Override
            public void exception(int code, String message) {
            }
        });
    }

    /**
     * 查询指数涨跌平家数
     * @param code
     */
    public void requestUpDownSame(String code) {
        if( null == quoteItem || null == quoteItem.subtype || !"1400".equals(quoteItem.subtype)){
            return;
        }

        RequestManager.cancel(requestUpDownSign);
        requestUpDownSign = RequestManager.request(new UpDownSameRunable(code) {

            @Override
            public void onBack(UpdownsResponse response) {

                UpdownsRequest updownsRequest = new UpdownsRequest();
                updownsRequest.send(quoteItem.id,new ResponseCallback(updownsRequest) {
                    @Override
                    public void onBack(Response response) {
                        UpdownsResponse updownsResponse =(UpdownsResponse)response;
                        UpdownsItem mUpdownsItem=updownsResponse.mUpdownsItem;
                        if( null == updownsResponse ||null==mUpdownsItem){
                            return;
                        }
                        view.onRequestUpDownSameSuccess(mUpdownsItem);
                    }

                    @Override
                    public void onError(int i, String s) {
                        view.onRequestUpDownSameFail();
                    }
                });
//                if( null == response || null == response.mUpdownsItem){
//                    return;
//                }
//                view.onRequestUpDownSameSuccess(response.mUpdownsItem);
            }

            @Override
            public void onError(int i, String error) {
//                view.onRequestUpDownSameFail();
            }
        });
    }

    /**
     * 可转债溢价查询
     */
    public void requestConvertibleDebt(){
        if( TextUtils.isEmpty(quoteItem.zgConvertCodes) ){
            return;
        }
        RequestManager.cancel(requestConverSign);
        requestConverSign = RequestManager.request(new ConvertibleDebtRunable(quoteItem.id) {
            @Override
            public void onBack(ConvertibleBoundResponse response) {
                ConvertibleDebtRequest convertibleDebtRequest= new ConvertibleDebtRequest();
                convertibleDebtRequest.send(quoteItem.id, new ResponseCallback(convertibleDebtRequest) {
                    @Override
                    public void onBack(Response response) {
                        ConvertibleBoundResponse convertibleBoundResponse=(ConvertibleBoundResponse)response;
                        List<ConvertibleBoundItem> convertibleBoundItem = convertibleBoundResponse.items;
                        if( null == response || null == convertibleBoundItem || convertibleBoundItem.isEmpty()){
                            view.onRequestConvertibleDebtFail();
                        }else{
                            view.onRequestConvertibleDebtSuccess(convertibleBoundItem);
                        }
                    }

                    @Override
                    public void onError(int i, String s) {
                        view.onRequestConvertibleDebtFail();
                    }
                });
//                List<ConvertibleBoundItem> convertibleBoundItemList = response.items;
//                if( null == response || null == convertibleBoundItemList || convertibleBoundItemList.isEmpty()){
//                    view.onRequestConvertibleDebtFail();
//                }else{
//                    view.onRequestConvertibleDebtSuccess(convertibleBoundItemList);
//                }
            }

            @Override
            public void onError(int i, String error) {
//                view.onRequestConvertibleDebtFail();
            }
        });
    }

    @Override
    public void requestQuote(String code) {
        QuoteDetailRequest quoteDetailRequest = new QuoteDetailRequest();
        quoteDetailRequest.send(code, new ResponseCallback(quoteDetailRequest) {
            @Override
            public void onBack(Response response) {
                QuoteResponse quoteResponse = (QuoteResponse) response;
                if( null == quoteResponse || null == quoteResponse.quoteItems || quoteResponse.quoteItems.isEmpty() ){
                    return;
                }
              view.requestQuoteDetail(quoteResponse.quoteItems);
            }

            @Override
            public void onError(int i, String s) {

            }
        });
    }

    /**
     * 查询股票行情快照，实时刷新
     */
    private void queryQuote() {
        RequestManager.cancel(requestSign);
        requestSign = RequestManager.request(new QuoteDetailRunable(quoteItem.id) {
            @Override
            public void onBack(QuoteResponse response) {
//                if(response != null){
//                    mQuoteResponse = response;
//                }  // old

                // TODO: 2018/2/4  为什么http要保证数据的完整性，TCP推送没有
                QuoteItem quote = response.quoteItems.get(0);
                quoteItem = DataConvert.copy(quoteItem,quote);
                response.quoteItems.set(0, quoteItem);
                // new 保存补全后的response
                if( null!=response ){
                    mQuoteResponse = response;
                }

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
        requestAHQuote();
//        requestDRQuote();
        requestConvertibleDebt();

        TcpManager.registerIPush(this);
        TcpManager.subscribe(quoteItem.id);
    }

    @Override
    public void onPause() {
        super.onPause();
        RequestManager.cancel(requestSign);
        RequestManager.cancel(requestUpDownSign);
        RequestManager.cancel(requestAhQuoteSign);
//        RequestManager.cancel(requsetDRQuoteSign);
        RequestManager.cancel(requestConverSign);

        TcpManager.unregisterIPush(this);
        TcpManager.unsubscribe(quoteItem.id);
    }

    @Override
    public void push(QuoteItem quoteItem, ArrayList<OrderQuantityItem> arrayList, ArrayList<OrderQuantityItem> arrayList1) {

        if( null!= quoteItem && this.quoteItem.id.equals(quoteItem.id)){
            // TODO: 2018/2/4 以下2行，为什么注释？
//            if( null != mQuoteResponse &&  null != mQuoteResponse.quoteItems && !mQuoteResponse.quoteItems.isEmpty()&& null != mQuoteResponse.quoteItems.get(0)){
//                QuoteItem desQuote = mQuoteResponse.quoteItems.get(0);
//                QuoteItemUtility.compare(quoteItem,desQuote);
//            }

            // old
            QuoteResponse response = new QuoteResponse();
            ArrayList<QuoteItem>  quoteItems = new ArrayList<>();
            quoteItems.add(quoteItem);
            response.quoteItems = quoteItems;
            view.onQuoteSuccess(response);

    /*        // new start  20218.3.6
            if( null != mQuoteResponse &&  null != mQuoteResponse.quoteItems && !mQuoteResponse.quoteItems.isEmpty()&& null != mQuoteResponse.quoteItems.get(0)){
                DataConvert.copy(quoteItem,mQuoteResponse.quoteItems.get(0));
                mQuoteResponse.quoteItems.set(0,quoteItem);
            }else{
                mQuoteResponse = new QuoteResponse();
                ArrayList<QuoteItem>  quoteItems = new ArrayList<>();
                quoteItems.add(quoteItem);
                mQuoteResponse.quoteItems = quoteItems;
            }
            view.onQuoteSuccess(mQuoteResponse);
            // new end
            */
        }
    }

//    @Override
//    public void pushHttp(QuoteResponse quoteResponse) {
//
//    }

}
