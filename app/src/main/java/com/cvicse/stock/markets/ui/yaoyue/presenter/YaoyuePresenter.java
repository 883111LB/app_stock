package com.cvicse.stock.markets.ui.yaoyue.presenter;

import android.util.Log;

import com.cvicse.base.mvp.BasePresenter;
import com.cvicse.base.utils.ToastUtils;
import com.cvicse.stock.http.loop.RequestManager;
import com.cvicse.stock.http.loop.RunTaskState;
import com.cvicse.stock.http.loop.YaoyueRunnable;
import com.cvicse.stock.markets.ui.yaoyue.presenter.contract.YaoyueContract;
import com.mitake.core.bean.offer.OfferQuoteBean;
import com.mitake.core.response.offer.OfferQuoteResponse;

import java.util.ArrayList;

/**
 * 要约
 * Created by tang_hua on 2020/2/28.
 */

public class YaoyuePresenter extends BasePresenter implements YaoyueContract.Presenter {

    private YaoyueContract.View view;

    private int ADDFLAG_DEFAULT = 0;// 请求方式：默认
    private int ADDFLAG_REFRESH = 1;// 请求方式：下拉刷新
    private int ADDFLAG_ADDMORE = 2;// 请求方式：上拉加载

    private RunTaskState requestSign;// 循环请求状态

    @Override
    public void setView(YaoyueContract.View view) {
        this.view = view;
    }



    /**
     * 要约收购列表
     * @param addFlag   加载标志（0正常、1下拉刷新、2上拉加载）
     * @param start     起始条数位置,第一条数据的起始位置是0,依次类推
     * @param count     查询条数
     * @param sortField 排序栏位
     * @param sortType  排序类型（升序、降序）
     */
    @Override
    public void offerQuoteRequest(final int addFlag, int start, int count, int sortField, final String sortType) {
        RequestManager.cancel(requestSign);
        requestSign = RequestManager.request(new YaoyueRunnable(start, count, sortField, sortType) {
            @Override
            public void onBack(OfferQuoteResponse response) {
                ArrayList<OfferQuoteBean> list = (ArrayList<OfferQuoteBean>) response.offerQuoteList;
                if (ADDFLAG_REFRESH == addFlag) {
                    // 下拉刷新
                    view.offerQuoteSuccess(list);
                } else if (ADDFLAG_ADDMORE == addFlag) {
                    // 上拉加载
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
