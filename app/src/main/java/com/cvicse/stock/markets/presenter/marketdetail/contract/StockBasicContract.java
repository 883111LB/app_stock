package com.cvicse.stock.markets.presenter.marketdetail.contract;

import com.cvicse.annotations.MVProvides;
import com.cvicse.base.mvp.IPresenter;
import com.cvicse.base.mvp.IView;
import com.cvicse.stock.markets.presenter.marketdetail.StockBasicPresenter;
import com.mitake.core.CoreBusiness;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 公司基本详情页面
 * Created by liu_zlu on 2017/2/9 21:24
 */
public class StockBasicContract {
    public interface View extends IView {
        /**
         * 获取公司基本信息成功
         */
        //void onCompanyInfoSuccess(CompanyInfo companyInfo);
        void onCompanyInfoSuccess(HashMap<String, Object> info);


        /**
         * 获取主要业务成功
         */
        void  onCoreBusinessSuccess(ArrayList<CoreBusiness> coreBusinesses);

        /**
         * 获取管理层信息成功
         */
//        void  onLeaderPersonInfoSuccess(ArrayList<LeaderPersonInfo> leaderPersonInfos);
        void  onLeaderPersonInfoSuccess(List<HashMap<String,Object>> infos);
    }
    @MVProvides(StockBasicPresenter.class)
    public interface Presenter extends IPresenter<View> {
        /**
         * 初始化stockId
         * @param stockId
         */
        void init(String stockId);
        /**
         * 查询公司基本信息
         */
        void queryBasic();

    }
}
