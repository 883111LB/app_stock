package com.cvicse.stock.markets.presenter.contract;

import com.cvicse.annotations.MVProvides;
import com.cvicse.base.mvp.IPresenter;
import com.cvicse.base.mvp.IView;
import com.cvicse.stock.markets.presenter.OptionListPresenter;
import com.mitake.core.QuoteItem;

import java.util.ArrayList;

/**
 * Created by ruan_ytai on 17-3-17.
 */

public class OptionListContract {

    public interface View extends IView {

        /**
         * 请求期权标的证券列表成功
         */
        void requestOptionListSuccess( ArrayList<QuoteItem> list);

        /**
         * 请求期权标的证券列表失败
         */
        void requestOptionListFail();
    }

    @MVProvides(OptionListPresenter.class)
    public interface Presenter extends IPresenter<View>{

        /**
         * 请求期权标的证券列表
         * @param stockType
         */
        void requestOptionList(String stockType);
    }
}
