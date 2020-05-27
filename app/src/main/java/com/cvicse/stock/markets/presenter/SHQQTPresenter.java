package com.cvicse.stock.markets.presenter;

import com.cvicse.base.mvp.BasePresenter;
import com.cvicse.base.utils.ToastUtils;
import com.cvicse.stock.http.ResponseCallback;
import com.cvicse.stock.http.loop.OptionTQuoteRunnable;
import com.cvicse.stock.http.loop.RequestManager;
import com.cvicse.stock.http.loop.RunTaskState;
import com.cvicse.stock.markets.presenter.contract.SHQQTContract;
import com.cvicse.stock.utils.DataConvert;
import com.github.mikephil.charting.formatter.IFillFormatter;
import com.mitake.core.QuoteItem;
import com.mitake.core.network.Network;
import com.mitake.core.request.OptionExpireRequest;
import com.mitake.core.request.OptionTQuoteRequest;
import com.mitake.core.response.OptionExpireResponse;
import com.mitake.core.response.OptionTQuoteResponse;
import com.mitake.core.response.Response;
import com.mitake.core.util.MarketSiteType;

import java.util.ArrayList;

/**
 * Created by ding_syi on 17-2-16.
 */
public class SHQQTPresenter extends BasePresenter implements SHQQTContract.Presenter {
    private SHQQTContract.View mView;
    private RunTaskState requestSign;
    private String stockId1;
    private String date1;

    /**
     * 查询T型期权数据
     * @param stockId 510050.sh"
     * @param date 类似于1703,1704
     */
    @Override
    public void requestSHQQT(String stockId,String date) {
        RequestManager.cancel(requestSign);
        this.stockId1=stockId;
        this.date1=date;
        requestSign = RequestManager.request(new OptionTQuoteRunnable(stockId,date) {
            @Override
            public void onBack(OptionTQuoteResponse response) {
                OptionTQuoteRequest optionTQuoteRequest = new OptionTQuoteRequest();
                optionTQuoteRequest.send(stockId1,date1, new ResponseCallback(optionTQuoteRequest) {
                    @Override
                    public void onBack(Response response) {
                        OptionTQuoteResponse optionTQuoteResponse=(OptionTQuoteResponse)response;
                        ArrayList<QuoteItem> list=optionTQuoteResponse.list;
                        mView.onRequestSHQQTSuccess(list);
                    }

                    @Override
                    public void onError(int i, String s) {

                    }
                });
//                ArrayList<QuoteItem> list = response.list;
//                mView.onRequestSHQQTSuccess(DataConvert.QuoteItemList(list));
            }

            @Override
            public void onError(int i, String error) {

            }
        });
    }

    /**
     * 获取期权交割月列表数据
     * @param stockId  标的证券代码
     */
    @Override
    public void requestExpire(final String stockId) {
        OptionExpireRequest optionExpireRequest = new OptionExpireRequest ();
        optionExpireRequest.send(stockId,new ResponseCallback(optionExpireRequest) {
            @Override
            public void onBack(Response response) {
                OptionExpireResponse optionExpireResponse = (OptionExpireResponse)response;
                String[] list = optionExpireResponse.list;
//                mView.requestExpireSuccess(list);
                requestExpire2(stockId, list);
            }

            @Override
            public void onError(int i, String s) {
                requestExpire2(stockId, null);
            }
        });
    }

    /**
     * 获取期权交割月列表数据 方法2
     * @param stockId  标的证券代码
     * @param list 期权交割月列表数据
     */
    public void requestExpire2(String stockId, final String[] list) {
        OptionExpireRequest optionExpireRequest = new OptionExpireRequest();
        optionExpireRequest.send(stockId, true, new ResponseCallback(optionExpireRequest) {
            @Override
            public void onBack(Response response) {
                OptionExpireResponse optionExpireResponse = (OptionExpireResponse)response;
                String[][] listD = optionExpireResponse.datas;
                mView.requestExpireSuccess(list, listD);
            }

            @Override
            public void onError(int i, String s) {
                ToastUtils.showLongToast(s);
            }
        });
    }

    @Override
    public void setView(SHQQTContract.View view) {
        mView = view;
    }

    @Override
    public void onPause() {
        super.onPause();
        RequestManager.cancel(requestSign);
    }
}
