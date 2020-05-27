package com.cvicse.stock.markets.presenter.presenter_new;

import com.cvicse.base.mvp.BasePresenter;
import com.cvicse.base.utils.NumberUtils;
import com.cvicse.stock.http.ResponseCallback;
import com.cvicse.stock.http.loop.RequestManager;
import com.cvicse.stock.http.loop.RunTaskState;
import com.cvicse.stock.http.loop.TickDetailRunnableSHSZ;
import com.cvicse.stock.markets.data.TickItemBo;
import com.cvicse.stock.markets.presenter.contract.StockTickContract;
import com.mitake.core.QuoteItem;
import com.mitake.core.bean.TickDetailItem;
import com.mitake.core.request.L2TickDetailRequestV2;
import com.mitake.core.response.L2TickDetailResponseV2;
import com.mitake.core.response.Response;

import java.util.ArrayList;
import java.util.List;

/** 逐笔分笔接口分开
 * Created by liu_zlu on 2017/3/7 13:45
 */
public class StockTickPresenter extends BasePresenter implements StockTickContract.Presenter {
    private StockTickContract.View view;
    private QuoteItem quoteItem;
    private ArrayList<TickItemBo> tickItemBos = new ArrayList<>();
    private String lastTime;
    private ResponseCallback responseCallback;
    private int page = 0;
    private int pageNum = 20;
    private int type = 1;
    private String left;
    private RunTaskState requestSign;

    @Override
    public void setView(StockTickContract.View view) {
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

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        RequestManager.cancel(requestSign);
    }

    /**
     * 刷新逐笔数据
     */
    @Override
    public void queryTickItems() {
        RequestManager.cancel(requestSign);
        if( null!=responseCallback  && !responseCallback.isFinished()){
            return;
        }
        page = 0;
        String param = page + "," + pageNum + ",-1";
        // 沪深市场
        queryTickSHSZ(param);
    }

    /**
     * 刷新沪深逐笔数据
     */
    private void queryTickSHSZ(String param) {
        requestSign = RequestManager.request(new TickDetailRunnableSHSZ(
                quoteItem.id, param,quoteItem.market,quoteItem.subtype) {
            @Override
            public void onBack(L2TickDetailResponseV2 response) {
                // 逐笔列表
                List<TickDetailItem> tickItems = response.tickDetailItems;
                tickItemBos.clear();
                if( null == tickItems || tickItems.isEmpty()){
                    view.onTickItemsSuccess(tickItemBos);
                    return;
                }
                cacuTickItem(response, tickItems);
                view.onTickItemsSuccess(tickItemBos);
            }

            @Override
            public void onError(int i, String error) {
                view.onTickItemsFail();
            }
        });
    }

    /**
     * 加载更多逐笔数据
     */
    @Override
    public void loadTickItems() {
        RequestManager.cancel(requestSign);
        if( null != responseCallback  && !responseCallback.isFinished()){
            return;
        }
        page++;
        String param = left + "," + pageNum + "," + type;
        // 沪深市场
        loadTickSHSZ(param);

        RequestManager.request(requestSign);
    }

    /**
     * 加载沪深更多数据
     */
    private void loadTickSHSZ( String param) {
        L2TickDetailRequestV2 tickDetailRequest = new L2TickDetailRequestV2();
        String subtype = quoteItem.market + "1000";
        tickDetailRequest.send(quoteItem.id, param, subtype,responseCallback = new ResponseCallback(tickDetailRequest){

            @Override
            public void onBack(Response response) {
                L2TickDetailResponseV2 tickDetailResponse = (L2TickDetailResponseV2) response;
                List<TickDetailItem> tickItems = tickDetailResponse.tickDetailItems;

                if( null== tickItems  || tickItems.isEmpty()){
                    view.onTickItemsSuccess(tickItemBos);
                    return;
                }
                cacuTickItem(tickDetailResponse, tickItems);
                view.onTickItemsSuccess(tickItemBos);
            }

            @Override
            public void onError(int i, String s) {
                view.onTickItemsFail();
            }
        });
    }

    private void cacuTickItem(L2TickDetailResponseV2 tickDetailResponse, List<TickDetailItem> tickItems) {
        TickItemBo tickItemBo;
        for(TickDetailItem tickItem:tickItems){
            tickItemBo = new TickItemBo();
            tickItemBo.tickFlag = tickItem.getTransactionStatus(); // 买卖方向

            String time = tickItem.getTransactionTime();
            String  tempTime = time.substring(0,2)+ ":" + time.substring(2,4)+":"
                    + time.substring(4,6);
            if(!tempTime.equals(lastTime)){
                lastTime = tempTime ;
                tickItemBo.time = lastTime;
            }
            tickItemBo.volume = tickItem.getSingleVolume();
            tickItemBo.strPrice = tickItem.getTransactionPrice();
            tickItemBo.price = NumberUtils.parseDouble(tickItem.getTransactionPrice());
            tickItemBos.add(tickItemBo);
        }

        String headerParams = tickDetailResponse.headerParams;
        String str1 = headerParams.split(",")[0];
        String str2 = headerParams.split(",")[1];
        if( quoteItem.id.contains("cff")  ){
            left = str1.compareTo(str2) > 0 ? str2 : str1;
        }else{
            left =  Double.parseDouble(str1)<Double.parseDouble(str2) ? str1:str2;
        }
    }
}
