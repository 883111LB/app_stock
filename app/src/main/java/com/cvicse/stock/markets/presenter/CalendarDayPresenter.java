package com.cvicse.stock.markets.presenter;

import com.cvicse.base.mvp.BasePresenter;
import com.cvicse.stock.common.Setting.Setting;
import com.cvicse.stock.http.ResponseCallback;
import com.cvicse.stock.markets.presenter.contract.CalendarDayContract;
import com.mitake.core.NewShareList;
import com.mitake.core.request.F10Request;
import com.mitake.core.request.NewShareListRequest;
import com.mitake.core.response.NewShareListResponse;
import com.mitake.core.response.Response;

import java.util.ArrayList;

/**
 * Created by ruan_ytai on 17-2-27.
 */

public class CalendarDayPresenter  extends BasePresenter implements CalendarDayContract.Presenter {

    private CalendarDayContract.View view;

    /**
     * 请求当天新股详情
     * @param date
     */
    @Override
    public void requestNewStockDetail(String date) {
        NewShareListRequest mNewShareListRequest = new NewShareListRequest();
        F10Request.SRC = Setting.getDataSource();
        mNewShareListRequest.sendV2(date,F10Request.SRC, new ResponseCallback(mNewShareListRequest) {
            @Override
            public void onBack(Response response) {
                NewShareListResponse newShareListRequest = (NewShareListResponse) response;
                ArrayList<NewShareList> infos = newShareListRequest.infos;
                view.onRequestNewStockDetailSuccess(infos);
            }

            @Override
            public void onError(int i, String s){
            }
        });
    }

    @Override
    public void setView(CalendarDayContract.View view) {
        this.view = view;
    }
}
