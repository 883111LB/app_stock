package com.cvicse.stock.markets.presenter.marketdetail.contract;

import com.cvicse.annotations.MVProvides;
import com.cvicse.base.mvp.IPresenter;
import com.cvicse.base.mvp.IView;
import com.cvicse.stock.markets.data.MarginTradingBo;
import com.cvicse.stock.markets.presenter.marketdetail.MarginTradingPresenter;
import com.mitake.core.QuoteItem;

import java.util.ArrayList;

/** 融资融券
 * Created by tang_xqing on 2018/8/3.
 */

public class MarginTradingContract {

    public interface View extends IView{
        void requestSubMarketSuccess(ArrayList<MarginTradingBo> tradingBoArrayList);
        void requestStockSuccess(ArrayList<MarginTradingBo> tradingBoArrayList);
        void loadMoreSubMarketSuccess(ArrayList<MarginTradingBo> tradingBoArrayList);

        // 请求沪市场 融资融券差额
        void requestFinDifSHSuccess(ArrayList<MarginTradingBo> tradingBoArrayList);
        // 请求深市场 融资融券差额
        void requestFinDifSZSuccess(ArrayList<MarginTradingBo> tradingBoArrayList);
        // 请求沪深市场 融资融券差额
        void requestFinDifSHSZSuccess(ArrayList<MarginTradingBo> tradingBoArrayList);

        void requestQuoteSuccess(ArrayList<QuoteItem> quoteItemList);
    }

    @MVProvides(MarginTradingPresenter.class)
    public interface Presenter extends IPresenter<View>{
        // 请求分市场提供最近交易日融资融券数据
        void requestSubMarket(String code,String param);

        // 请求融资融券差额接口
        void requestFinDifferencr(String code,String param);

        // 请求个股融资融券接口
        void requestStock(String code,String  param);

        // 加载更多分市场提供最近交易日融资融券数据
        void loadMoreSubMarket(String code,String param);

        // 请求行情列表
        void requestQuote(String codeId);
    }

}
