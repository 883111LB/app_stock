package com.cvicse.stock.markets.presenter.fp;

import com.cvicse.base.mvp.BasePresenter;
import com.cvicse.stock.http.loop.QuoteRunable;
import com.cvicse.stock.http.loop.ReplayFPRunnable;
import com.cvicse.stock.http.loop.RequestManager;
import com.cvicse.stock.http.loop.RunTaskState;
import com.cvicse.stock.markets.presenter.fp.contract.FPContract;
import com.mitake.core.QuoteItem;
import com.mitake.core.bean.compound.CompoundUpDownBean;
import com.mitake.core.bean.log.ErrorInfo;
import com.mitake.core.request.compound.CompoundUpDownRequest;
import com.mitake.core.response.IResponseInfoCallback;
import com.mitake.core.response.QuoteResponse;
import com.mitake.core.response.compound.CompoundUpDownResponse;
import com.mitake.core.response.offer.OfferQuoteResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * 复盘页面
 * Created by tang_hua on 2020/2/21.
 */

public class FPPresenter extends BasePresenter implements FPContract.Presenter {

    private FPContract.View view;
    private String str1 = "000001.sh,399001.sz,399006.sz,399005.sz,000847.sh,000300.sh";
    private RunTaskState requestSigns;

    @Override
    public void setView(FPContract.View view) {
        this.view = view;
    }

    /**
     * 涨跌分布
     * @param market   市场
     * @param time     日期
     * @param dateType 日期类型
     */
    @Override
    public void compoundUpDownRequest(String market, String time, int dateType) {
//        RequestManager.cancel(requestSigns[1]);
//        requestSigns[1] = RequestManager.request(new ReplayFPRunnable(market, time, dateType) {
//            @Override
//            public void onBack(CompoundUpDownResponse response) {
//                List<CompoundUpDownBean> list = response.compoundUpDownList;
//                view.compoundUpDownRequestSuccess(list);
//            }
//
//            @Override
//            public void onError(int i, String error) {
//                view.compoundUpDownRequestFailure(error);
//            }
//        });
        CompoundUpDownRequest request = new CompoundUpDownRequest();
        request.send(market, time, dateType, new IResponseInfoCallback<CompoundUpDownResponse>() {

            @Override
            public void callback(CompoundUpDownResponse response) {
                List<CompoundUpDownBean> list = response.compoundUpDownList;
                view.compoundUpDownRequestSuccess(list);
            }

            @Override
            public void exception(ErrorInfo errorInfo) {
                view.compoundUpDownRequestFailure(errorInfo.getMessage());
            }
        });
    }

    @Override
    public void quoteRequest() {
        RequestManager.cancel(requestSigns);
        requestSigns = RequestManager.request(new QuoteRunable(str1,null) {
            @Override
            public void onBack(QuoteResponse response) {
                ArrayList<QuoteItem> list = response.quoteItems;
                view.quoteRequestSuccess(list);
            }

            @Override
            public void onError(int i, String error) {
                view.quoteRequestFailire(error);
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        RequestManager.cancel(requestSigns);
//        RequestManager.cancel(requestSigns[1]);
    }
}
