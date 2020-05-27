package com.cvicse.stock.markets.presenter;

import com.cvicse.base.mvp.BasePresenter;
import com.cvicse.stock.common.Setting.Setting;
import com.cvicse.stock.http.ResponseCallback;
import com.cvicse.stock.markets.data.DateUtil;
import com.cvicse.stock.markets.presenter.contract.BondTradingCalendarContract;
import com.mitake.core.request.BndNewSharesCalRequest;
import com.mitake.core.request.BndshareIPODetaiRequest;
import com.mitake.core.request.BondTradingDayRequest;
import com.mitake.core.request.F10Request;
import com.mitake.core.request.HolidayRequest;
import com.mitake.core.response.F10V2Response;
import com.mitake.core.response.HolidayResponse;
import com.mitake.core.response.Response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/** 新债
 * Created by tang_xqing on 2018/4/25.
 */
public class BondTradingCalendarPresenter extends BasePresenter implements BondTradingCalendarContract.Presenter {

    BondTradingCalendarContract.View view;

    /**
     * 新债日历
     */
    @Override
    public void requestBondTradingDay() {
        BondTradingDayRequest bondTradingDayRequest = new BondTradingDayRequest();
        F10Request.SRC = Setting.getDataSource();
        bondTradingDayRequest.send(F10Request.SRC, new ResponseCallback(bondTradingDayRequest) {
            @Override
            public void onBack(Response response) {
                F10V2Response mF10V2Response = (F10V2Response) response;
                if( null == mF10V2Response || null == mF10V2Response.infos || mF10V2Response.infos.isEmpty() ){
                    return;
                }
                requestHoliday(mF10V2Response.infos);
            }

            @Override
            public void onError(int i, String s) {
                view.onRequestBondTradingDayFail();
            }
        });
    }

    /**
     * 请求节假日
     */
    public void requestHoliday(final List<HashMap<String,Object>> bndTradList) {
        HolidayRequest mHolidayRequest = new HolidayRequest();
        mHolidayRequest.send(new ResponseCallback(mHolidayRequest) {
            @Override
            public void onBack(Response response) {
                HolidayResponse holidayResponse = (HolidayResponse)response;
                ArrayList<String> holidayList = holidayResponse.info;
                if( null == holidayList || holidayList.isEmpty() ){
                    return;
                }
                view.onRequestBondTradingDaySuccess(bndTradList,holidayList);
            }

            @Override
            public void onError(int i, String s) {

            }
        });
    }

    /**
     * 当日新债
     * @param date
     */
    @Override
    public void requestBndNewSharesCal(String date) {
        date = null == date ? DateUtil.CurrentDateConvert():date;
        BndNewSharesCalRequest bndNewSharesCalRequest = new BndNewSharesCalRequest();
        F10Request.SRC = Setting.getDataSource();
        bndNewSharesCalRequest.send(date, F10Request.SRC, new ResponseCallback(bndNewSharesCalRequest) {
            @Override
            public void onBack(Response response) {
                F10V2Response mF10V2Response = (F10V2Response) response;
                if( null == mF10V2Response ||  null == mF10V2Response.info){
                    return;
                }
                view.onRequestBndNewSharesCalSuccess(mF10V2Response.info);
            }

            @Override
            public void onError(int i, String s) {
                view.onRequestBndNewSharesCalFail();
            }
        });
    }

    /**
     * 新债详情
     * @param code
     */
    @Override
    public void requestBndShareDetail(String code) {
        BndshareIPODetaiRequest bndshareIPODetaiRequest = new BndshareIPODetaiRequest();
        F10Request.SRC = Setting.getDataSource();
        bndshareIPODetaiRequest.send(code, F10Request.SRC, new ResponseCallback(bndshareIPODetaiRequest) {
            @Override
            public void onBack(Response response) {
                F10V2Response mF10V2Response = (F10V2Response) response;
                if( null == mF10V2Response || null == mF10V2Response.info ){
                    return;
                }
                view.onRequestBndShareDetailSuccess(mF10V2Response.info);
            }

            @Override
            public void onError(int i, String s) {
                view.onRequestBndShareDetailFail();
            }
        });
    }

    @Override
    public void setView(BondTradingCalendarContract.View view) {
        this.view = view;
    }
}
