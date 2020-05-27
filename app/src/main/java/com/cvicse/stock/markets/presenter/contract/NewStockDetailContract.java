package com.cvicse.stock.markets.presenter.contract;

import com.cvicse.annotations.MVProvides;
import com.cvicse.base.mvp.IPresenter;
import com.cvicse.base.mvp.IView;
import com.cvicse.stock.markets.presenter.NewStockDetailPresenter;
import com.mitake.core.NewShareDetail;

/**
 * Created by ruan_ytai on 17-3-1.
 */

public class NewStockDetailContract {

    public interface View extends IView{
        /**
         * 请求股票详细信息成功
         */
        void onRequestStockDetailSuccess(NewShareDetail info);

        /**
         * 请求股票详细信息失败
         */
        void onRequestStockDetailFail();

    }

    @MVProvides(NewStockDetailPresenter.class)
    public interface Presenter extends IPresenter<View>{

        /**
         * 请求股票详细信息
         */
        void requestStockDetail(String tradingCode);
    }
}
