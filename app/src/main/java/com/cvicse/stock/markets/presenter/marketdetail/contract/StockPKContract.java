package com.cvicse.stock.markets.presenter.marketdetail.contract;

import com.cvicse.annotations.MVProvides;
import com.cvicse.base.mvp.IPresenter;
import com.cvicse.base.mvp.IView;
import com.cvicse.stock.markets.presenter.marketdetail.StockPKPresenter;
import com.mitake.core.AddValueModel;
import com.mitake.core.response.Bankuaisorting;

import java.util.ArrayList;
import java.util.List;

/** 盘口页面
 * Created by Lenovo on 2017/8/16.
 */
public class StockPKContract {

    public interface View extends IView {

        /**
         * 请求增值指标成功
         */
        void onRequestAddValueSuccess(ArrayList<AddValueModel> addValueModel);

        /**
         * 请求增值指标失败
         */
        void onRequestAddValueFail();

        // 请求个股所属板块成功
        void onRequestBankuaiQuoteSuccess(List<Bankuaisorting> bankuaisortingList);

        // 请求个股所属板块失败
        void onRequestBankuaiQuoteFail(int code,String message);
    }

    @MVProvides(StockPKPresenter.class)
    public interface Presenter extends IPresenter<StockPKContract.View> {
        /**
         * 初始化
         */
        void init(String codes,int[] ints1,int[] ints2);
        /**
         *请求增值指标数据
         */
        void requestAddValue(String market, String subtype);

        /**
         * 请求个股所属板块
         */
        void requestBankuaiQuote(String code, String params);
    }
}
