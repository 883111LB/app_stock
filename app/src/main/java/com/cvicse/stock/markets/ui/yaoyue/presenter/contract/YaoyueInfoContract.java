package com.cvicse.stock.markets.ui.yaoyue.presenter.contract;

import com.cvicse.annotations.MVProvides;
import com.cvicse.base.mvp.IPresenter;
import com.cvicse.base.mvp.IView;
import com.cvicse.stock.markets.ui.yaoyue.presenter.YaoyueInfoPresenter;
import com.mitake.core.bean.offer.OfferQuoteBean;

import java.util.ArrayList;

/**
 * 要约详情
 * Created by tang_hua on 2020/2/28.
 */

public class YaoyueInfoContract {
    public interface View extends IView {


        /**
         * 要约接口请求 成功
         */
        void offerQuoteSuccess(ArrayList<OfferQuoteBean> result);
        /**
         * 要约接口请求 成功(下拉刷新）
         */
        void offerQuoteRefreshSuccess(ArrayList<OfferQuoteBean> result);
        /**
         * 要约接口请求 成功（上拉加载）
         */
        void offerQuoteMoreSuccess(ArrayList<OfferQuoteBean> result);

    }

    @MVProvides(YaoyueInfoPresenter.class)
    public interface Presenter extends IPresenter<View> {

        /**
         * 要约收购详情
         * @param addFlag   加载标志（0正常、1下拉刷新、2上拉加载）
         * @param code 证券代码
         */
        void offerQuoteRequest(int addFlag, String code);
    }
}
