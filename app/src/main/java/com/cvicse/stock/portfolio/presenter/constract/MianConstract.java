package com.cvicse.stock.portfolio.presenter.constract;

import com.cvicse.annotations.MVProvides;
import com.cvicse.base.mvp.IPresenter;
import com.cvicse.base.mvp.IView;
import com.cvicse.stock.portfolio.presenter.MianPresenter;
import com.mitake.core.QuoteItem;
import com.mitake.core.StockInfoListItem;

import java.util.HashMap;
import java.util.List;

/**
 * Created by liu_zlu on 2017/1/12 09:23
 */
public class MianConstract {

    public interface View extends IView {
        /**
         * 下拉刷新成功
         */
        void onSelectedSuccess(List<QuoteItem> result);

        /**
         * 下拉刷新失败
         */
        void onRefreshFail();

        /**
         * 指数实时更新
         * @param quoteItem
         */
        void onIndexTcp(QuoteItem quoteItem);

        /**
         * 自选模块实时更新
         * @param quoteItem
         */
        void onSelectTcp(QuoteItem quoteItem);

        /**
         * 请求个股静态数据成功
         */
        void requestStockInfoSuccess(HashMap<String,StockInfoListItem > infoListItems);

        /**
         * 请求个股静态数据失败
         */
        void requestStockInfoFail();
    }
    @MVProvides(MianPresenter.class)
    public interface Presenter extends IPresenter<View> {
            void onRequestSelects();

          /*  void onQueryIndex();*/
    }
}
