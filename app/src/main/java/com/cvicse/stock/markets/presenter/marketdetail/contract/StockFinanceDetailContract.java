package com.cvicse.stock.markets.presenter.marketdetail.contract;

import com.cvicse.annotations.MVProvides;
import com.cvicse.base.mvp.IPresenter;
import com.cvicse.base.mvp.IView;
import com.cvicse.stock.markets.presenter.marketdetail.StockFinanceDetailPresenter;
import com.mitake.core.MainFinaDataNas;

import java.util.HashMap;
import java.util.List;

/** 财务指标详情
 * Created by tang_xqing on 2018/4/19.
 */
public class StockFinanceDetailContract {

    public interface View extends IView{
        // 请求沪深财务报表
        void requestFinanceDetailSuccess(List<HashMap<String,Object>> infoList);
        void requestFinanceDetailFail(int code,String msg);

        // 请求港股财务报表
        void requestHKFinanceDetailSuccess(List<MainFinaDataNas> mainFinaDataNasList);
        void requestHKFinanceDetailFail(int code,String msg);
    }

    @MVProvides(StockFinanceDetailPresenter.class)
    public interface Presenter extends IPresenter<View>{
        void init(String code,String market);
        void requestFinanceDetail(String quarterType,String apiType);
        void requestFinanceDetailByQuarterType(String quarterType,String apiType);
    }
}
