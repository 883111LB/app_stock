package com.cvicse.stock.markets.ui.ztsorting.presenter;

import com.cvicse.base.mvp.BasePresenter;
import com.cvicse.base.utils.ToastUtils;
import com.cvicse.stock.http.loop.RequestManager;
import com.cvicse.stock.http.loop.RunTaskState;
import com.cvicse.stock.http.loop.ZTSortingRunable;
import com.cvicse.stock.markets.ui.ztsorting.presenter.contract.ZTSortingListContract;
import com.mitake.core.QuoteItem;
import com.mitake.core.bean.ZTSortingItem;
import com.mitake.core.bean.log.ErrorInfo;
import com.mitake.core.request.QuoteDetailRequest;
import com.mitake.core.response.IResponseInfoCallback;
import com.mitake.core.response.QuoteResponse;
import com.mitake.core.response.ZTSortingResponse;

import java.util.List;

public class ZTSortingListPresenter extends BasePresenter implements ZTSortingListContract.Presenter {
    private ZTSortingListContract.View view;

    private int ADDFLAG_DEFAULT = 0;// 请求方式：默认
    private int ADDFLAG_REFRESH = 1;// 请求方式：下拉刷新
    private int ADDFLAG_ADDMORE = 2;// 请求方式：上拉加载

    private RunTaskState requestSign;

    @Override
    public void setView(ZTSortingListContract.View view) {
        this.view = view;
    }
    @Override
    public void ztSortingRanking(final int addFlag, int page, int pageSize, String sortField,String sortType,String ztType) {
        String param = page + "," + pageSize + "," +  sortField + "," + sortType;
        RequestManager.cancel(requestSign);
        requestSign = RequestManager.request(new ZTSortingRunable(param,ztType) {
            @Override
            public void onBack(ZTSortingResponse response) {
                List<ZTSortingItem> ztSortingItems  = response.ztSortingItems;
                if (ADDFLAG_REFRESH == addFlag) {
                    // 下拉刷新
                    view.ztSortingRefreshSuccess(ztSortingItems);
                } else if (ADDFLAG_ADDMORE == addFlag) {
                    // 上拉加载
                    view.ztSortingMoreSuccess(ztSortingItems);
                } else {
                    view.ztSortingSuccess(ztSortingItems);
                }
            }

            @Override
            public void onError(int i, String error) {
                ToastUtils.showLongToast(error);
            }
        });
    }

    /**
     * 获取快照
     */
    @Override
    public void getQuoteInfo(String code) {
        QuoteDetailRequest request = new QuoteDetailRequest();
        request.send(code, new IResponseInfoCallback<QuoteResponse>() {
            @Override
            public void callback(QuoteResponse response) {
                // 请求成功
                QuoteItem quoteItem = response.quoteItems.get(0);
                view.getQuoteInfoSuccess(quoteItem);
            }

            @Override
            public void exception(ErrorInfo errorInfo) {
                // 请求失败
                ToastUtils.showLongToast("QuoteDetailRequest：" + errorInfo.getMessage());
            }
        });
    }
}
