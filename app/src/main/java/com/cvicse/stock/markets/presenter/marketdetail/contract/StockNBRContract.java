package com.cvicse.stock.markets.presenter.marketdetail.contract;

import com.cvicse.annotations.MVProvides;
import com.cvicse.base.mvp.IPresenter;
import com.cvicse.base.mvp.IView;
import com.cvicse.stock.markets.data.MarketNBRBo;
import com.cvicse.stock.markets.presenter.marketdetail.StockNBRPresenter;
import com.mitake.core.QuoteItem;

import java.util.ArrayList;

/**
 *
 * 新闻、公共、研报列表页面
 * Created by liu_zlu on 2017/2/7 19:56
 */
public class StockNBRContract {
    public interface View extends IView {
        /**
         * 获取新闻列表成功
         */
        void onRequestSuccess(ArrayList<MarketNBRBo> newsArrayList);

        /**
         * 获取新闻列表失败
         */
        void  onRequestFail();

    }

    @MVProvides(StockNBRPresenter.class)
    public interface Presenter extends IPresenter<View> {
        /**
         * 初始化stockId
         * @param quoteItem
         */
        void init(QuoteItem quoteItem, String type);

        /**
         * 查询新闻、公共、研报
         */
        void queryNBR();


        /**
         * 查询更多
         */
        void queryNBRMore();

    }
}
