package com.cvicse.stock.markets.presenter.contract;

import com.cvicse.annotations.MVProvides;
import com.cvicse.base.mvp.IPresenter;
import com.cvicse.base.mvp.IView;
import com.cvicse.stock.markets.presenter.NewStockCalendarPresenter;
import com.mitake.core.NewShareDates;

import java.util.ArrayList;

/**
 * Created by ruan_ytai on 17-2-23.
 */

public class NewStockCalendarContract {

    public interface View extends IView {
        /**
         * 请求新股日历列表成功
         */
        void onRequestNewCalendarSuccess(ArrayList<NewShareDates> infos, ArrayList<String> holidayList);

        /**
         * 请求新股日历列表失败
         */
        void onRequestNewCalendarFail();

        /**
         * 请求节假日成功
         *//*
        void onRequestHolidaySuccess(ArrayList<String> info);

        *//**
         * 请求节假日失败
         *//*
        void onRequestHolidayFail();*/
    }

    @MVProvides(NewStockCalendarPresenter.class)
    public interface Presenter extends IPresenter<View> {
        /**
         * 请求新股日历列表数据
         */
        void requestNewCalendar();

       /* *//**
         * 请求节假日
         *//*
        void requestHoliday();*/
    }

}
