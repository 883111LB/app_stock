package com.cvicse.stock.markets.presenter.contract;

import com.cvicse.annotations.MVProvides;
import com.cvicse.base.mvp.IPresenter;
import com.cvicse.base.mvp.IView;
import com.cvicse.stock.markets.presenter.DRQuoteListPresenter;
import com.mitake.core.bean.DRQuoteItem;

import java.util.List;

/**
 * Created by shi_yhui on 2018/11/24.
 */

public class DRQuoteListContract {
    public interface View extends IView {

        void requestDRQuoteListFail();

        void requestDRQuoteListSuccess(List<DRQuoteItem> mDRQuoteItemList);

        void requestPullDownDRQuoteListSuccess(List<DRQuoteItem> mDRQuoteItemList);

        void requestPullUpDRQuoteListSuccess(List<DRQuoteItem> mDRQuoteItemsList);
    }
    @MVProvides(DRQuoteListPresenter.class)
    public interface Presenter extends IPresenter<View> {

        void requestPullDownDRQuoteList(String code, String param);

        void requestPullUpDRQuoteList(String code, String param);

        void requestDRQuoteList(String currentCode, String currentParam);

    }
}
