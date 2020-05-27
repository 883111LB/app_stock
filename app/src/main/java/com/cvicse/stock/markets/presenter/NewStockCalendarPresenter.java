package com.cvicse.stock.markets.presenter;

import com.cvicse.base.mvp.BasePresenter;
import com.cvicse.stock.http.ResponseCallback;
import com.cvicse.stock.markets.presenter.contract.NewStockCalendarContract;
import com.mitake.core.NewShareDates;
import com.mitake.core.request.CalendarRequest;
import com.mitake.core.request.HolidayRequest;
import com.mitake.core.response.DatesResponse;
import com.mitake.core.response.HolidayResponse;
import com.mitake.core.response.Response;

import java.util.ArrayList;


/**
 * Created by ruan_ytai on 17-2-23.
 */

public class NewStockCalendarPresenter extends BasePresenter implements NewStockCalendarContract.Presenter {

    private NewStockCalendarContract.View view;

    /**
     * 请求新股日历列表数据
     */
    @Override
    public void requestNewCalendar() {
          CalendarRequest calendarRequest = new CalendarRequest();
          calendarRequest.send(new ResponseCallback(calendarRequest) {
            @Override
            public void onBack(Response response) {
                DatesResponse datesResponse = (DatesResponse) response;
                ArrayList<NewShareDates> infos = datesResponse.infos;

                // 请求节假日数据，和新股日历数据一起返回
                requestHoliday(infos);
            }

            @Override
            public void onError(int i, String s) {

            }
        });
    }

    /**
     * 请求节假日
     */
    public void requestHoliday(final ArrayList<NewShareDates> infos) {
        HolidayRequest mHolidayRequest = new HolidayRequest();
        mHolidayRequest.send(new ResponseCallback(mHolidayRequest) {
            @Override
            public void onBack(Response response) {
                HolidayResponse holidayResponse = (HolidayResponse)response;
                ArrayList<String> holidayList = holidayResponse.info;
                view.onRequestNewCalendarSuccess(infos,holidayList);
            }

            @Override
            public void onError(int i, String s) {

            }
        });
    }

    @Override
    public void setView(NewStockCalendarContract.View view) {
        this.view = view;
    }

}
