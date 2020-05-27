package com.cvicse.stock.markets.presenter;

import com.cvicse.base.mvp.BasePresenter;
import com.cvicse.stock.http.ResponseCallback;
import com.cvicse.stock.markets.presenter.contract.SectionMoreConstract;
import com.mitake.core.request.BankuaisortingRequest;
import com.mitake.core.response.Bankuaisorting;
import com.mitake.core.response.BankuaisortingResponse;
import com.mitake.core.response.Response;

import java.util.List;

import static com.cvicse.stock.markets.ui.SectionMoreActivity.DOWN_REQUEST;
import static com.cvicse.stock.markets.ui.SectionMoreActivity.NOMAL_REQUEST;
import static com.cvicse.stock.markets.ui.SectionMoreActivity.UP_REQUEST;

/**
 * Created by ding_syi on 17-1-23.
 */
public class SectionMorePresenter extends BasePresenter implements SectionMoreConstract.Presenter {
    SectionMoreConstract.View view;
    private String requestType = NOMAL_REQUEST;  // 下拉、上拉、正常

    @Override
    public void request(String symbol,String param,final String type) {
        requestType = type;
        final BankuaisortingRequest bankuaisortingRequest = new BankuaisortingRequest();
        bankuaisortingRequest.send(symbol,param, new ResponseCallback(bankuaisortingRequest) {
            @Override
            public void onBack(Response response) {
                BankuaisortingResponse bankuaisortingResponse = (BankuaisortingResponse)response;
                List<Bankuaisorting> bankuaisortings =  bankuaisortingResponse.list;
                switch(requestType){
                    case NOMAL_REQUEST:
                        view.onSectionSucess(bankuaisortings);
                        break;

                    case DOWN_REQUEST:
                        view.onSectionDownSucess(bankuaisortings);
                        break;

                    case UP_REQUEST:
                        view.onSectionUpSucess(bankuaisortings);
                        break;

                    default:
                        break;
                }
                requestType = NOMAL_REQUEST;
            }

            @Override
            public void onError(int i, String s) {

            }
        });
    }

    @Override
    public void setView(SectionMoreConstract.View view) {
           this.view = view;
    }


}
