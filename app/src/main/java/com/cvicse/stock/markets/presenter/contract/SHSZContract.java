package com.cvicse.stock.markets.presenter.contract;

import com.cvicse.annotations.MVProvides;
import com.cvicse.base.mvp.IPresenter;
import com.cvicse.base.mvp.IView;
import com.cvicse.stock.markets.presenter.SHSZPresenter;
import com.mitake.core.NewShareList;
import com.mitake.core.QuoteItem;
import com.mitake.core.bean.UpdownsItem;
import com.mitake.core.response.CateSortingResponse;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by ruan_ytai on 17-1-16.
 */

public class SHSZContract {

   public interface View extends IView{
       /**
        * 请求成功
        */
       void onIndexSuccess(ArrayList<QuoteItem> result);
       /**
        *查询排行榜成功
        */
//       void onRequestRankSuccess(List<QuoteItem> quoteItems,int position);
       void onRequestRankSuccess(CateSortingResponse response,int position);

       /**
        *查询排行榜失败
        */
       void onRequestRankError();

       /**
        * 查询当天新股列表成功
        */
       void onRequestCurrentCalendarSuccess(ArrayList<NewShareList> infos);

       /**
        * 查询当天新股列表失败
        */
       void onRequestCurrentCalendarFail();

       void onRequestCurrentBndNewSharesCalSuccess(HashMap<String,Object> bndNewCal);

       void onRequestCurrentBndNewSharesCalFail(int code,String msg);

       // 查询沪深A股涨跌平家数成功
       void onRequestUpDownSameSuccess(UpdownsItem updownsItem);

       void onRequestUpDownSameFail();
   }


    @MVProvides(SHSZPresenter.class)
    public interface Presenter extends IPresenter<View>{

        /**
         * 查询排行榜数据
         */
        void requestRank();
        /**
         * 查询排行榜数据
         */
        void requestRank(int position);

        /**
         * 查询当天的新股日历
         */
        void requestCurrentCalendar();

        // 查询当日新债
        void requestCurrentBndNewSharesCal();

        // 查询沪深A股涨跌平家数
        void requestUpDownSame(String code);

    }
}
