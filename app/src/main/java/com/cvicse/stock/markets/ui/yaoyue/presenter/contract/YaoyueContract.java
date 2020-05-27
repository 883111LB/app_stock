package com.cvicse.stock.markets.ui.yaoyue.presenter.contract;

import com.cvicse.annotations.MVProvides;
import com.cvicse.base.mvp.IPresenter;
import com.cvicse.base.mvp.IView;
import com.cvicse.stock.markets.ui.yaoyue.presenter.YaoyuePresenter;
import com.mitake.core.bean.offer.OfferQuoteBean;

import java.util.ArrayList;

/**
 * 要约
 * Created by tang_hua on 2020/2/28.
 */

public class YaoyueContract {
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

    @MVProvides(YaoyuePresenter.class)
    public interface Presenter extends IPresenter<View> {

        /**
         * 要约收购列表
         * @param addFlag   加载标志（0正常、1下拉刷新、2上拉加载）
         * @param start 起始条数位置,第一条数据的起始位置是0,依次类推
         * @param count 查询条数
         * @param sortField 排序栏位
         * @param sortType 排序类型（升序、降序）
         */
        void offerQuoteRequest (int addFlag, int start, int count, int sortField, String sortType);
    }
}
