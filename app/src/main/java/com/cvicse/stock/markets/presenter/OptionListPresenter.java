package com.cvicse.stock.markets.presenter;

import com.cvicse.base.mvp.BasePresenter;
import com.cvicse.stock.http.ResponseCallback;
import com.cvicse.stock.markets.presenter.contract.OptionListContract;
import com.cvicse.stock.utils.DataConvert;
import com.mitake.core.QuoteItem;
import com.mitake.core.request.OptionListRequest;
import com.mitake.core.response.OptionListResponse;
import com.mitake.core.response.Response;

import java.util.ArrayList;

import static com.cvicse.stock.markets.data.ParamUtil.STOCK_NAME_SZQQ;

/**
 * Created by ruan_ytai on 17-3-17.
 */

public class OptionListPresenter extends BasePresenter implements OptionListContract.Presenter {

    private OptionListContract.View view;
    /**
     * 请求期权标的证券列表
     * @param stockType
     */
    @Override
    public void requestOptionList(String stockType) {
        String flag = "sh";// 默认上证期权
        if (stockType.equals(STOCK_NAME_SZQQ)) {// 深圳期权
            flag = "sz";
        }
        OptionListRequest optionListRequest = new OptionListRequest();
        optionListRequest.send(flag, new ResponseCallback(optionListRequest) {
            @Override
            public void onBack(Response response) {
                OptionListResponse optionListResponse = (OptionListResponse) response;
                ArrayList<QuoteItem> list = optionListResponse.list;
                if(list!=null && list.size()>0){
                    view.requestOptionListSuccess(DataConvert.QuoteItemList(list));
                }

            }

            @Override
            public void onError(int i, String s) {

            }
        });
    }

    @Override
    public void setView(OptionListContract.View view) {
            this.view = view;
    }
}
