package com.cvicse.stock.markets.ui.ztsorting.presenter.contract;

import com.cvicse.annotations.MVProvides;
import com.cvicse.base.mvp.IPresenter;
import com.cvicse.base.mvp.IView;
import com.cvicse.stock.markets.ui.ztsorting.presenter.ZTSortingListPresenter;
import com.mitake.core.QuoteItem;
import com.mitake.core.bean.ZTSortingItem;

import java.util.List;

public class ZTSortingListContract {
    public interface View extends IView {

        void ztSortingRefreshSuccess(List<ZTSortingItem> ztSortingItems);

        void ztSortingMoreSuccess(List<ZTSortingItem> ztSortingItems);

        void ztSortingSuccess(List<ZTSortingItem> ztSortingItems);

        void getQuoteInfoSuccess(QuoteItem quoteItem);
    }

    @MVProvides(ZTSortingListPresenter.class)
    public interface Presenter extends IPresenter<View> {

        void ztSortingRanking(int addflag_refresh, int i, int i1, String sortField, String sortType,String ztType);

        void getQuoteInfo(String code);
    }
}
