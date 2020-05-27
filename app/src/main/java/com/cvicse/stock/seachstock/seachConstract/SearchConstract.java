package com.cvicse.stock.seachstock.seachConstract;

import android.content.Context;

import com.cvicse.annotations.MVProvides;
import com.cvicse.base.mvp.IPresenter;
import com.cvicse.base.mvp.IView;
import com.cvicse.stock.seachstock.SearchPresenter;
import com.mitake.core.QuoteItem;
import com.mitake.core.SearchResultItem;

import java.util.ArrayList;

/**
 * 股票搜索页面
 * Created by ding_syi on 17-1-6.
 */
public class SearchConstract {
    public interface View extends IView{
        /**
         * 调用网络接口成功成功
         * @param result
         */
        void onSearchSuccess(ArrayList<SearchResultItem> result);
        /**
         * 调用网络接口成功成功
         * @param result
         */
        void onHistorySuccess(ArrayList<SearchResultItem> result);
        /**
         * 调用网络接口失败
         */
        void onSearchFail();

        void onRequestQuoteSuccess(ArrayList<QuoteItem> quoteItems);

        void onRequestQuoteFail();
    }

    @MVProvides(SearchPresenter.class)
    public interface Presenter extends IPresenter {
        /**
         * 根据参数搜索股票
         * @param context Context实例
         * @param parameter 输入参数
         */
        void searchStock(Context context,String parameter);

        void requestQuote(String code);
    }

}

