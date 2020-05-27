package com.cvicse.stock.markets.presenter.contract;

import com.cvicse.annotations.MVProvides;
import com.cvicse.base.mvp.IPresenter;
import com.cvicse.base.mvp.IView;
import com.cvicse.stock.markets.presenter.SectionPresenter;
import com.mitake.core.response.Bankuaisorting;

import java.util.List;

/**
 * Created by ding_syi on 17-1-22.
 */
public class SectionContract {
    public interface View extends IView {
        //请求行业成功
        void onTradeRequest(List<Bankuaisorting> industryItemList);

        //请求概念成功
        void onNotionRequest(List<Bankuaisorting> industryItemList);

        // 请求地域成功
        void onAreaRequest(List<Bankuaisorting> industryItemList);

        // 请求港股行业成功
        void onHKTradeRequest(List<Bankuaisorting> industryItemList);

        // 请求申万一级行业成功
        void onTradeSW1Request(List<Bankuaisorting> industryItemList);
        // 请求申万二级行业成功
        void onTradeSW2Request(List<Bankuaisorting> industryItemList);
        //请求失败
        void onRequestFail();
        //请求优品行业
        void onTradeSzypRequest(List<Bankuaisorting> industryItemList);
        //请求优品地区
        void onAreaSzypRequest(List<Bankuaisorting> industryItemList);

        void onNotionSzypRequest(List<Bankuaisorting> industryItemList);
        // 方正行业
        void onFzRequest(List<Bankuaisorting> industryItemList);
    }


    @MVProvides(SectionPresenter.class)
    public interface Presenter extends IPresenter<View> {
        void requestHot();

        void requestConcept();

        void requestArea();

        void requestHKTrade();

        void requestTradeSW1();

        void requestTradeSW2();

        void requestTradeSzyp();

        void requestAreaSzyp();

        void requestNotionSzyp();

        void requestFzSzyp();
    }
}
