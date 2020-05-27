package com.cvicse.stock.markets.presenter.contract;

import com.cvicse.annotations.MVProvides;
import com.cvicse.base.mvp.IPresenter;
import com.cvicse.base.mvp.IView;
import com.cvicse.stock.markets.data.AHQuoteBo;
import com.cvicse.stock.markets.data.DRQuoteBo;
import com.cvicse.stock.markets.presenter.MarketDetailPresenter;
import com.mitake.core.HKOthersInfo;
import com.mitake.core.QuoteItem;
import com.mitake.core.bean.UKQuoteItem;
import com.mitake.core.bean.UpdownsItem;
import com.mitake.core.bean.quote.ConvertibleBoundItem;
import com.mitake.core.response.QuoteResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liu_zlu on 2017/2/7 16:34
 */
public class MarketDetailContract {
    public interface View extends IView {
        void requestQuoteDetail(ArrayList<QuoteItem> quoteItems);

        //返回股票快照行情
        void onQuoteSuccess(QuoteResponse quoteResponse);

        //返回港股其他
        void onHKStockInfoSuccess(HKOthersInfo hkOthersInfo);

        // 查询指数涨跌平家数成功
        void onRequestUpDownSameSuccess(UpdownsItem updownsItem);

        void onRequestUpDownSameFail();

        // 查询AH股行情成功
        void onRequestAhQuoteSuccess(AHQuoteBo ahQuoteBo);

        void onRequestAhQuoteFail();

        //查询DR溢价成功
        void onRequestDRQuoteSuccess(DRQuoteBo drQuoteBo);

        void onRequestDRQuoteFail();

        //返回UK数据
        void onUKStockInfoSuccess(List<UKQuoteItem> ukQuoteItems);
        // 可转债溢价查询成功
        void onRequestConvertibleDebtSuccess(List<ConvertibleBoundItem> convertibleBoundItem );

        // 可转债溢价查询成功
        void onRequestConvertibleDebtFail();
    }

    @MVProvides(MarketDetailPresenter.class)
    public interface Presenter extends IPresenter<View> {
        // 初始化stockId
        void init(QuoteItem quoteItem);

        //查询港股其他
        void queryHKStockInfo();

        void requestAHQuote();

        void requestDRQuote();
        //查询UK独有数据
        void requsetUKQuote();

        // 查询指数涨跌平家数
        void requestUpDownSame(String code);

        // 可转债溢价查询
        void requestConvertibleDebt();

        // 查询行情快照
        void requestQuote(String code);
    }
}
