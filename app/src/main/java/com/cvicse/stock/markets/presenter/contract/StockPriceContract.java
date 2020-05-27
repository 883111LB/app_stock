package com.cvicse.stock.markets.presenter.contract;

import com.cvicse.annotations.MVProvides;
import com.cvicse.base.mvp.IPresenter;
import com.cvicse.base.mvp.IView;
import com.cvicse.stock.markets.data.MorePriceBo;
import com.cvicse.stock.markets.presenter.StockPricePresenter;
import com.mitake.core.QuoteItem;

import java.util.ArrayList;

/**
 * 分价
 *
 * Created by liu_zlu on 2017/3/7 16:48
 */
public class StockPriceContract {
    public interface View extends IView {

        /**
         * 返回股票分价信息
         * @param morePriceBos
         */
        void onMorePriceSuccess(ArrayList<MorePriceBo> morePriceBos);
        /**
         * 返回股票分价信息
         */
        void onMorePriceFail();
    }
    @MVProvides(StockPricePresenter.class)
    public interface Presenter extends IPresenter<View> {
        /**
         * 初始化stockId
         * @param quoteItem
         */
        void init(QuoteItem quoteItem);
        /**
         * 查询分价信息
         */
        void queryMorePrice();

        /**
         * 价格排序
         */
        void sortPrice();
        /**
         * 成交量排序
         */
        void sortVolume();
    }
}
