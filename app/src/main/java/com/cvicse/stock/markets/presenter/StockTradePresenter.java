package com.cvicse.stock.markets.presenter;

import com.cvicse.base.mvp.BasePresenter;
import com.cvicse.base.utils.NumberUtils;
import com.cvicse.stock.http.ResponseCallback;
import com.cvicse.stock.http.loop.DetailRunnable;
import com.cvicse.stock.http.loop.RequestManager;
import com.cvicse.stock.http.loop.RunTaskState;
import com.cvicse.stock.markets.data.TickItemBo;
import com.cvicse.stock.markets.presenter.contract.StockTradeContract;
import com.mitake.core.QuoteItem;
import com.mitake.core.bean.TickItem;
import com.mitake.core.request.TickRequest;
import com.mitake.core.response.Response;
import com.mitake.core.response.TickResponse;
import com.mitake.core.util.FormatUtility;

import java.util.ArrayList;
import java.util.List;

/**
 * 成交明细Presenter
 * Created by liu_zlu on 2017/4/6 15:05
 */
public class StockTradePresenter extends BasePresenter implements StockTradeContract.Presenter {

    private StockTradeContract.View view;
    private QuoteItem quoteItem;
    private ArrayList<TickItemBo> tickItemBos = new ArrayList<>();
    private ResponseCallback responseCallback;
    private String left;
    private String pageType ;
    private RunTaskState requestSigns;

    @Override
    public void setView(StockTradeContract.View view) {
        this.view = view;
    }
    @Override
    public void init(QuoteItem quoteItem) {
        this.quoteItem = quoteItem;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        RequestManager.cancel(requestSigns);
    }

    @Override
    public void queryTradeItems() {
        pageType = "0,30,-1";
        RequestManager.cancel(requestSigns);
        requestSigns = RequestManager.request(new DetailRunnable(quoteItem.id,pageType,
                quoteItem.market, quoteItem.subtype) {
            @Override
            public void onBack(TickResponse response) {
                List<TickItem> tickItems = response.tickItems;
                String headerParams = response.headerParams;
                tickItemBos.clear();
                if( null == tickItems || null == headerParams){
                    view.onTickItemsSuccess(tickItemBos);
                    return;
                }
                cacuTickItem(tickItems,headerParams);
                view.onTickItemsSuccess(tickItemBos);
            }

            @Override
            public void onError(int i, String error) {

            }
        });
    }
    /**
     * 加载更多逐笔数据
     */
    @Override
    public void loadTradeItems() {
        RequestManager.cancel(requestSigns);
        if(responseCallback != null && !responseCallback.isFinished()){
            return;
        }
        pageType = left+",15,1";
        setTickRequest();
        RequestManager.request(requestSigns);
    }

    private void setTickRequest() {
        TickRequest tickRequest = new TickRequest();
        tickRequest.send(quoteItem.id,pageType,quoteItem.subtype,responseCallback = new ResponseCallback(tickRequest){

            @Override
            public void onBack(Response response) {
                TickResponse tickResponse = (TickResponse) response;
                List<TickItem> tickItems = tickResponse.tickItems;
                String headerParams = tickResponse.headerParams;
                if( null == tickItems || null == headerParams){
                    view.onTickItemsSuccess(tickItemBos);
                    return;
                }
                cacuTickItem(tickItems,headerParams);
                view.onTickItemsSuccess(tickItemBos);
            }

            @Override
            public void onError(int i, String s) {
                view.onTickItemsFail();
            }
        });
    }

    private void cacuTickItem(List<TickItem> tickItems,String headerParams) {
        TickItemBo tickItemBo;
        if( null!=tickItems ){
            for(TickItem tickItem:tickItems){
                tickItemBo = new TickItemBo();
                tickItemBo.tickFlag = tickItem.getTransactionStatus();

                String time = tickItem.getTransactionTime();
                if( quoteItem.id.contains("cff") && time.length() < 6){
                    time = "0"+time;
                }

                String tempTime = time.substring(0,2)+":"+time.substring(2,4)+":"+time.substring(4,6);
                tickItemBo.time = tempTime;
                tickItemBo.volume = FormatUtility.formatVolume(tickItem.getSingleVolume());
                tickItemBo.strPrice = tickItem.getTransactionPrice();
                tickItemBo.price = NumberUtils.parseDouble(tickItem.getTransactionPrice());
                tickItemBos.add(tickItemBo);
            }

            String str1 = headerParams.split(",")[0];
            String str2 = headerParams.split(",")[1];
            if( quoteItem.id.contains("cff")  ){
                left = str1.compareTo(str2) > 0 ? str2 : str1;
            }else{
                left =  Double.parseDouble(str1) < Double.parseDouble(str2) ? str1:str2;
            }
        }
    }
}
