package com.cvicse.stock.markets.presenter.marketdetail.contract;

import com.cvicse.annotations.MVProvides;
import com.cvicse.base.mvp.IPresenter;
import com.cvicse.base.mvp.IView;
import com.cvicse.stock.markets.presenter.marketdetail.HKBasicPresenter;

import java.util.HashMap;
import java.util.List;

/**
 * 港股简况
 * Created by liu_zlu on 2017/2/13 19:08
 */
public class HKBasicContract {
    public interface View extends IView {
        /**
         * 获取公司基本信息成功
         */
        //void onCompanyInfoSuccess(CompanyInfo companyInfo);
        void onCompanyInfoSuccess(HashMap<String, Object> info);

        /**
         * 获取管理层信息成功
         */
        //void  onLeaderPersonInfoSuccess(ArrayList<LeaderPersonInfo> leaderPersonInfos);
        void  onLeaderPersonInfoSuccess(List<HashMap<String,Object>> infos);
    }
    @MVProvides(HKBasicPresenter.class)
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
