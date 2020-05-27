package com.cvicse.stock.markets.presenter.marketdetail.contract;

import com.cvicse.annotations.MVProvides;
import com.cvicse.base.mvp.IPresenter;
import com.cvicse.base.mvp.IView;
import com.cvicse.stock.markets.presenter.marketdetail.StockExptProindicPresenter;

import java.util.HashMap;
import java.util.List;

public class StockExptProindicContract {
    public interface View extends IView {

        void onExptSuccess(List<HashMap<String, Object>> infoList);

        void onProindicSuccess(List<HashMap<String, Object>> infoList);
    }

    @MVProvides(StockExptProindicPresenter.class)
    public interface Presenter extends IPresenter<View> {

        void requestProindic(String mApiType);

        void requestExpt(String mApiType);

        void init(String mCode);
    }
}
