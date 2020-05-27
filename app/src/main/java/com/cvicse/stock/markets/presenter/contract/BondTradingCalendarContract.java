package com.cvicse.stock.markets.presenter.contract;

import com.cvicse.annotations.MVProvides;
import com.cvicse.base.mvp.IPresenter;
import com.cvicse.base.mvp.IView;
import com.cvicse.stock.markets.presenter.BondTradingCalendarPresenter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/** 新债
 * Created by tang_xqing on 2018/4/25.
 */

public class BondTradingCalendarContract {

    public interface View extends IView{
        // 请求新债日历成功
        void onRequestBondTradingDaySuccess(List<HashMap<String,Object>> bndTradList, ArrayList<String> holidayList);
        // 请求新债日历失败
        void onRequestBondTradingDayFail();

        // 请求当日新债
        void onRequestBndNewSharesCalSuccess(HashMap<String,Object> bndNewSharesCal) ;
        void onRequestBndNewSharesCalFail();

        //请求新债详情
        void onRequestBndShareDetailSuccess(HashMap<String,Object> bndShartDetail);
        void onRequestBndShareDetailFail();
    }

    @MVProvides(BondTradingCalendarPresenter.class)
    public interface Presenter extends IPresenter<View>{
        void requestBondTradingDay();

        // 当日新债
        void requestBndNewSharesCal(String date);

        // 新债详情
        void requestBndShareDetail(String code);
    }
}
