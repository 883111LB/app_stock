package com.cvicse.stock.markets.presenter.contract;

import com.cvicse.annotations.MVProvides;
import com.cvicse.base.mvp.IPresenter;
import com.cvicse.base.mvp.IView;
import com.cvicse.stock.markets.presenter.SectionMorePresenter;
import com.mitake.core.response.Bankuaisorting;

import java.util.List;

/**
 * Created by ding_syi on 17-1-23.
 */
public class SectionMoreConstract {

    public interface View extends IView {
        /**
         * 请求排行榜行业详细数据成功
         */
        void onSectionSucess(List<Bankuaisorting> industryItemList);

        /**
         * 上拉加载
         */
        void onSectionUpSucess(List<Bankuaisorting> industryItemList);

        /**
         * 下拉刷新
         */
        void onSectionDownSucess(List<Bankuaisorting> industryItemList);

        void onRequestFail();
    }

    @MVProvides(SectionMorePresenter.class)
    public interface Presenter extends IPresenter<View> {
        /**
         * 请求热门行业
         */
        void request(String type,String param,String requestType);


    }
}
