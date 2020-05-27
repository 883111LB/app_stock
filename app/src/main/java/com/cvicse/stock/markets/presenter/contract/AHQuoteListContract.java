package com.cvicse.stock.markets.presenter.contract;

import com.cvicse.annotations.MVProvides;
import com.cvicse.base.mvp.IPresenter;
import com.cvicse.base.mvp.IView;
import com.cvicse.stock.markets.presenter.AHQuoteListPresenter;
import com.mitake.core.QuoteItem;
import com.mitake.core.bean.AHQuoteItem;

import java.util.ArrayList;
import java.util.List;

/** 请求AH股列表
 * Created by tang_xqing on 2018/5/14.
 */
public class AHQuoteListContract {
    public interface View extends IView{
        void requestAHQuoteListSuccess(List<AHQuoteItem> ahQuoteItemList);
        void requestPullDownAHQuoteListSuccess(List<AHQuoteItem> ahQuoteItemList);
        void requestPullUpAHQuoteListSuccess(List<AHQuoteItem> ahQuoteItemList);
        void requestAHQuoteListFail();
        void requestQuoteSuccess( ArrayList<QuoteItem> quoteItems);
        void requestQuoteFail();
    }

    @MVProvides(AHQuoteListPresenter.class)
    public interface Presenter extends IPresenter<View>{
        void requestAHQuoteList(String param);
        void requestPullDownAHQuoteList(String parm);
        void requestPullUpAHQuoteList(String parm);
        void requestQuote(String code);
    }
}
