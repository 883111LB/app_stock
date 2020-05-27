package com.cvicse.stock.markets.ui.yaoyue.presenter;

import android.util.Log;

import com.cvicse.base.mvp.BasePresenter;
import com.cvicse.base.utils.ToastUtils;
import com.cvicse.stock.http.loop.RequestManager;
import com.cvicse.stock.http.loop.RunTaskState;
import com.cvicse.stock.http.loop.YaoyueInfoRunnable;
import com.cvicse.stock.markets.ui.yaoyue.presenter.contract.YaoyueContract;
import com.cvicse.stock.markets.ui.yaoyue.presenter.contract.YaoyueInfoContract;
import com.mitake.core.bean.offer.OfferQuoteBean;
import com.mitake.core.response.offer.OfferQuoteResponse;

import java.util.ArrayList;

/**
 * 要约详情
 * Created by tang_hua on 2020/2/28.
 */

public class YaoyueInfoPresenter extends BasePresenter implements YaoyueInfoContract.Presenter {

    private YaoyueInfoContract.View view;

    @Override
    public void setView(YaoyueInfoContract.View view) {
        this.view = view;
    }
    private int ADDFLAG_DEFAULT = 0;// 请求方式：默认
    private int ADDFLAG_REFRESH = 1;// 请求方式：下拉刷新
    private int ADDFLAG_ADDMORE = 2;// 请求方式：上拉加载

    private RunTaskState requestSign;// 循环请求状态

    /**
     * 要约收购详情
     * @param addFlag   加载标志（0正常、1下拉刷新、2上拉加载）
     * @param code  证券代码
     */
    @Override
    public void offerQuoteRequest(final int addFlag, String code) {
        RequestManager.cancel(requestSign);
        requestSign = RequestManager.request(new YaoyueInfoRunnable(code) {
            @Override
            public void onBack(OfferQuoteResponse response) {
                ArrayList<OfferQuoteBean> list = (ArrayList<OfferQuoteBean>) response.offerQuoteList;
                if (ADDFLAG_REFRESH == addFlag) {
                    // 下拉刷新
                    view.offerQuoteSuccess(list);
                } else {
                    view.offerQuoteSuccess(list);
                }
            }

            @Override
            public void onError(int i, String error) {
                ToastUtils.showLongToast("offerQuoteRequest:" + error);
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        RequestManager.cancel(requestSign);
    }
}
