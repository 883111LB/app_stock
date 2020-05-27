package com.cvicse.stock.markets.presenter.marketdetail;

import com.cvicse.base.mvp.BasePresenter;
import com.cvicse.stock.http.ResponseCallback;
import com.cvicse.stock.http.loop.BankuaiQuoteRunable;
import com.cvicse.stock.http.loop.RequestManager;
import com.cvicse.stock.http.loop.RunTaskState;
import com.cvicse.stock.markets.presenter.marketdetail.contract.StockPKContract;
import com.mitake.core.AddValueModel;
import com.mitake.core.keys.quote.AddValueCustomField;
import com.mitake.core.request.AddValueRequest;
import com.mitake.core.response.AddValueResponse;
import com.mitake.core.response.Bankuaisorting;
import com.mitake.core.response.BankuaisortingResponse;
import com.mitake.core.response.Response;

import java.util.ArrayList;
import java.util.List;

import static com.mitake.core.keys.quote.AddValueCustomField.ALL_COLUMN;

/** 盘口页面
 * Created by Lenovo on 2017/8/16.
 */

public class StockPKPresenter extends BasePresenter implements StockPKContract.Presenter {

    private StockPKContract.View view;
    // 股票代码
    private String codes;
    // 	快照自定义栏位
    private int[] ints1;
    // 增值指标自定义栏位
    private int[] ints2;

    private RunTaskState requestSign;

    @Override
    public void setView(StockPKContract.View view) {
        this.view = view;
    }

    /**
     * 初始化
     * @param codes
     * @param ints1
     * @param ints2
     */
    @Override
    public void init(String codes, int[] ints1, int[] ints2) {
        this.codes = codes;
        this.ints1 = ints1;
        this.ints2 = ints2;
    }

    @Override
    public void onPause() {
        super.onPause();
        RequestManager.cancel(requestSign);
    }

    /**
     * 增值指标
     */
    @Override
    public void requestAddValue(String market, String subtype) {
        AddValueRequest addValueRequest = new AddValueRequest();
        addValueRequest.send(codes,market,subtype,new int[]{ALL_COLUMN}, new ResponseCallback(addValueRequest) {
            @Override
            public void onBack(Response response) {
                AddValueResponse addValueResponse = (AddValueResponse) response;
                // 增值指标数据
                ArrayList<AddValueModel> addValueModel = (ArrayList<AddValueModel>) addValueResponse.list;
                if(null == addValueResponse || null == addValueModel || addValueModel.isEmpty()){
                    return;
                }
                view.onRequestAddValueSuccess(addValueModel);
            }

            @Override
            public void onError(int i, String s) {
                view.onRequestAddValueFail();
            }
        });

    }

    /**
     * 请求个股所属板块
     * @param code
     */
    @Override
    public void requestBankuaiQuote(String code, String params) {
        RequestManager.cancel(requestSign);
        requestSign = RequestManager.request(new BankuaiQuoteRunable(code, params) {
            @Override
            public void onBack(BankuaisortingResponse response) {
                List<Bankuaisorting> list = response.list;
                if( null == list || list.isEmpty() ){
                    view.onRequestBankuaiQuoteFail(0,"无数据");
                }
                view.onRequestBankuaiQuoteSuccess(list);
            }

            @Override
            public void onError(int i, String error) {
            view.onRequestBankuaiQuoteFail(i,error);
            }
        });
    }
}
