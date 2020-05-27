package com.cvicse.stock.markets.presenter.contract;

import com.cvicse.annotations.MVProvides;
import com.cvicse.base.mvp.IPresenter;
import com.cvicse.base.mvp.IView;
import com.cvicse.stock.markets.presenter.CalendarDayPresenter;
import com.mitake.core.NewShareList;

import java.util.ArrayList;

/**
 * Created by ruan_ytai on 17-2-27.
 */

public class CalendarDayContract {

    public interface View extends IView{
        /**
         * 请求单个新股详情成功
         */
        void onRequestNewStockDetailSuccess(ArrayList<NewShareList> infos);

        /**
         * 请求新股详情失败
         */
        void onRrequestNewStockDetailFail();
    }

    @MVProvides(CalendarDayPresenter.class)
    public interface Presenter extends IPresenter<View>{

        /**
         * 请求新股详情
         */
        void requestNewStockDetail(String date);

    }
}
