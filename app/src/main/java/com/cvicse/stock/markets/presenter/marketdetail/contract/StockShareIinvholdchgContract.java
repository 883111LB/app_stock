package com.cvicse.stock.markets.presenter.marketdetail.contract;

import com.cvicse.annotations.MVProvides;
import com.cvicse.base.mvp.IPresenter;
import com.cvicse.base.mvp.IView;
import com.cvicse.stock.markets.presenter.marketdetail.StockShareIinvholdchgPresenter;

import java.util.HashMap;
import java.util.List;

/** 股东深度挖掘
 * Created by tang_xqing on 2018/4/18.
 */

public class StockShareIinvholdchgContract {

    public interface View extends IView{
       void requestShareIivholdchgSuccesss (List<HashMap<String,Object>> shareIivholdchg);
        void requestShareIivholdchgFail(int code,String msg);
    }

    @MVProvides(StockShareIinvholdchgPresenter.class)
    public interface Presenter extends IPresenter<View>{
       void requestShareIivholdchg(String code,String type);
    }

}
