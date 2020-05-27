package com.cvicse.stock.markets.ui.subnewbondstock.presenter;

import com.cvicse.base.mvp.BasePresenter;
import com.cvicse.base.utils.ToastUtils;
import com.cvicse.stock.http.loop.RequestManager;
import com.cvicse.stock.http.loop.RunTaskState;
import com.cvicse.stock.http.loop.SubNewBondStockRunable;
import com.cvicse.stock.http.loop.SubNewStockRunable;
import com.cvicse.stock.markets.ui.subnewbondstock.presenter.contract.SubNewBondStockContract;
import com.mitake.core.QuoteItem;
import com.mitake.core.SubNewStockRankingModel;
import com.mitake.core.bean.log.ErrorInfo;
import com.mitake.core.request.QuoteDetailRequest;
import com.mitake.core.response.IResponseInfoCallback;
import com.mitake.core.response.QuoteResponse;
import com.mitake.core.response.SubNewStockRankingResponse;

import java.util.ArrayList;

/**
 * Created by tang_hua on 2020/3/25.
 */

public class SubNewBondStockPresenter extends BasePresenter implements SubNewBondStockContract.Presenter {

    private SubNewBondStockContract.View view;
    private int ADDFLAG_DEFAULT = 0;// 请求方式：默认
    private int ADDFLAG_REFRESH = 1;// 请求方式：下拉刷新
    private int ADDFLAG_ADDMORE = 2;// 请求方式：上拉加载

    private RunTaskState requestSign;

    @Override
    public void setView(SubNewBondStockContract.View view) {
        this.view = view;
    }

    /**
     * 次新债列表
     * @param addFlag   请求类型（默认、下拉刷新、上拉加载）
     * @param page      第几页,
     * @param pageSize  每页多少条,
     * @param sortField 排序栏位,
     * @param sortType  升降序)
     */
    @Override
    public void subNewBondStockRanking(final int addFlag, int page, int pageSize, String sortField, String sortType) {
        String param = page + "," + pageSize + "," +  sortField + "," + sortType;
        RequestManager.cancel(requestSign);

        requestSign = RequestManager.request(new SubNewBondStockRunable(param) {
            @Override
            public void onBack(SubNewStockRankingResponse response) {
                ArrayList<SubNewStockRankingModel> datas = response.list;
                if (ADDFLAG_REFRESH == addFlag) {
                    // 下拉刷新
                    view.subNewBondRefreshSuccess(datas);
                } else if (ADDFLAG_ADDMORE == addFlag) {
                    // 上拉加载
                    view.subNewBondMoreSuccess(datas);
                } else {
                    view.subNewBondSuccess(datas);
                }
            }

            @Override
            public void onError(int i, String error) {
                ToastUtils.showLongToast(error);
            }
        });
    }

    /**
     * 请求快照（作为下个页面的参数）
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
