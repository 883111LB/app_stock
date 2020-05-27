package com.cvicse.stock.markets.presenter.marketdetail.contract;

import com.cvicse.annotations.MVProvides;
import com.cvicse.base.mvp.IPresenter;
import com.cvicse.base.mvp.IView;
import com.cvicse.stock.markets.presenter.marketdetail.PriceAnalysisPresenter;
import com.mitake.core.QuoteItem;
import com.mitake.core.bean.quote.MarketUpDownItem;

import java.util.ArrayList;

/**
 * Created by shi_yhui on 2018/11/16.
 */

public class PriceAnalysisContract {
    public interface View extends IView {
        //获取涨跌数据成功
        void onMarketUpDownSuccesss(MarketUpDownItem upDownItem);
        //获取上证指数时间成功
//        void onMaeketUpDownTimeSuccess(ArrayList<QuoteItem> list);
    }
    @MVProvides(PriceAnalysisPresenter.class)
    public interface Presenter extends IPresenter<View>{
        // 请求涨跌数据接口
        void requestMarketUpDown();
        //请求上证指数时间
//        void requestMarketUpDownTime();
    }
}
