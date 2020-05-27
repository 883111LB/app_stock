package com.cvicse.stock.markets.presenter.contract;

import com.cvicse.annotations.MVProvides;
import com.cvicse.base.mvp.IPresenter;
import com.cvicse.base.mvp.IView;
import com.cvicse.stock.markets.data.TickItemBo;
import com.cvicse.stock.markets.presenter.StockTradePresenter;
import com.mitake.core.QuoteItem;

import java.util.ArrayList;

/**
 * Created by liu_zlu on 2017/4/6 15:04
 */
public class StockTradeContract {
    public interface View extends IView {

        /**
         * 返回股票逐笔数据
         * @param tickItemBos
         */
        void onTickItemsSuccess(ArrayList<TickItemBo> tickItemBos);
        /**
         * 返回股票逐笔数据
         */
        void onTickItemsFail();
    }
    @MVProvides(StockTradePresenter.class)
    public interface Presenter extends IPresenter<View> {
        /**
         * 初始化stockId
         * @param quoteItem
         */
        void init(QuoteItem quoteItem);

        /**
         * 刷新逐笔数据
         */
        void queryTradeItems();

        /**
         * 加载更多逐笔数据
         */
        void loadTradeItems();
    }
}
