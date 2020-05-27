package com.cvicse.stock.markets.ui.hkt.presenter;

import com.cvicse.base.mvp.BasePresenter;
import com.cvicse.stock.http.loop.HKTRunable;
import com.cvicse.stock.http.loop.RequestManager;
import com.cvicse.stock.http.loop.RunTaskState;
import com.cvicse.stock.markets.ui.hkt.presenter.contract.HKTContract;
import com.mitake.core.bean.HKTItem;
import com.mitake.core.response.chart.TongLineResponse;

import java.util.List;

/**
 * 沪深港通
 * Created by tang_hua on 2020/5/13.
 */

public class HKTPresenter extends BasePresenter implements HKTContract.Presenter {

    private HKTContract.View view;
    private RunTaskState requestSign;// 循环请求状态
    private String flag;

    @Override
    public void setView(HKTContract.View view) {
        this.view = view;
    }

    @Override
    public void tongLineRequest(String flag) {
        RequestManager.cancel(requestSign);
        this.flag = flag;
        requestSign = RequestManager.request(new HKTRunable(flag) {
            @Override
            public void onBack(TongLineResponse response) {
                List<HKTItem> hktItems = response.hktItems;
                view.tongLineRequestSuccess(hktItems);
            }

            @Override
            public void onError(int i, String error) {
                view.tongLineRequestFailure(error);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        tongLineRequest(flag);
    }

    @Override
    public void onPause() {
        super.onPause();
        RequestManager.cancel(requestSign);
    }
}
