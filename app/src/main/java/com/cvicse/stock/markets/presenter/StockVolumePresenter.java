package com.cvicse.stock.markets.presenter;

import android.util.Log;

import com.cvicse.base.mvp.BasePresenter;
import com.cvicse.base.utils.ToastUtils;
import com.cvicse.stock.http.loop.RequestManager;
import com.cvicse.stock.http.loop.RunTaskState;
import com.cvicse.stock.http.loop.StockVolumeRunnable;
import com.cvicse.stock.http.loop.YaoyueInfoRunnable;
import com.cvicse.stock.markets.adapter.StockVolumeAdapter;
import com.cvicse.stock.markets.presenter.contract.StockVolumeContract;
import com.cvicse.stock.markets.ui.yaoyue.presenter.contract.YaoyueInfoContract;
import com.mitake.core.bean.log.ErrorInfo;
import com.mitake.core.bean.offer.OfferQuoteBean;
import com.mitake.core.request.MoreVolumeRequest;
import com.mitake.core.response.IResponseInfoCallback;
import com.mitake.core.response.MoreVolumeResponse;
import com.mitake.core.response.offer.OfferQuoteResponse;

import java.util.ArrayList;

/**
 * 分量统计
 * Created by tang_hua on 2020/2/28.
 */

public class StockVolumePresenter extends BasePresenter implements StockVolumeContract.Presenter {

    private StockVolumeContract.View view;

    @Override
    public void setView(StockVolumeContract.View view) {
        this.view = view;
    }

    private RunTaskState requestSign;// 循环请求状态

    /**
     * 分量统计
     */
    @Override
    public void moreVolumeRequest(String code, String subtype) {
        RequestManager.cancel(requestSign);
        requestSign = RequestManager.request(new StockVolumeRunnable(code, subtype) {
            @Override
            public void onBack(MoreVolumeResponse response) {
                String[][] values=response.values;
                // 成交量 数组长度为24
                String[] volumes=values[0];
                // 买量   数组长度为24
                String[] buyVolumes=values[1];
                // 卖量   数组长度为24
                String[] sellVolumes=values[2];
//                adapter.setData(volumes, buyVolumes, sellVolumes);
                view.moreVolumeSuccess(volumes, buyVolumes, sellVolumes);
            }

            @Override
            public void onError(int i, String error) {
                ToastUtils.showLongToast("moreVolumeRequest" + error);
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        RequestManager.cancel(requestSign);
    }
}
