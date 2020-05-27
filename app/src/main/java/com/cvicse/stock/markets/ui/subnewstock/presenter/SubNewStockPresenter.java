package com.cvicse.stock.markets.ui.subnewstock.presenter;

import com.cvicse.base.mvp.BasePresenter;
import com.cvicse.base.utils.ToastUtils;
import com.cvicse.stock.http.loop.RequestManager;
import com.cvicse.stock.http.loop.RunTaskState;
import com.cvicse.stock.http.loop.SubNewStockRunable;
import com.cvicse.stock.markets.ui.subnewstock.presenter.contract.SubNewStockContract;
import com.mitake.core.QuoteItem;
import com.mitake.core.SubNewStockRankingModel;
import com.mitake.core.bean.log.ErrorInfo;
import com.mitake.core.request.QuoteDetailRequest;
import com.mitake.core.response.IResponseInfoCallback;
import com.mitake.core.response.QuoteResponse;
import com.mitake.core.response.SubNewStockRankingResponse;

import java.util.ArrayList;

/**
 * 次新股
 * Created by tang_hua on 2020/3/24.
 */

public class SubNewStockPresenter extends BasePresenter implements SubNewStockContract.Presenter {

    private SubNewStockContract.View view;
    private int ADDFLAG_DEFAULT = 0;// 请求方式：默认
    private int ADDFLAG_REFRESH = 1;// 请求方式：下拉刷新
    private int ADDFLAG_ADDMORE = 2;// 请求方式：上拉加载

    private RunTaskState requestSign;

    @Override
    public void setView(SubNewStockContract.View view) {
        this.view = view;
    }

    /**
     * 次新股列表
     * @param addFlag 请求类型（默认、下拉刷新、上拉加载）
     * @param page      第几页,
     * @param pageSize 每页多少条,
     * @param sortField 排序栏位,
     * @param sortType 升降序)
     */
    @Override
    public void subNewStockRanking(final int addFlag, int page, int pageSize, String sortField,String sortType) {
        String param = page + "," + pageSize + "," +  sortField + "," + sortType;
        RequestManager.cancel(requestSign);
        requestSign = RequestManager.request(new SubNewStockRunable(param) {
            @Override
            public void onBack(SubNewStockRankingResponse response) {
                ArrayList<SubNewStockRankingModel> datas = response.list;
                if (ADDFLAG_REFRESH == addFlag) {
                    // 下拉刷新
                    view.subNewStockRefreshSuccess(datas);
                } else if (ADDFLAG_ADDMORE == addFlag) {
                    // 上拉加载
                    view.subNewStockMoreSuccess(datas);
                } else {
                    view.subNewStockSuccess(datas);
                }
            }

            @Override
            public void onError(int i, String error) {
                ToastUtils.showLongToast(error);
            }
        });
//        SubNewStockRankingRequest subNewStockRankingRequest = new SubNewStockRankingRequest();
//        subNewStockRankingRequest.send(param, new IResponseInfoCallback() {
//            @Override
//            public void callback(Response response) {
//                SubNewStockRankingResponse result = (SubNewStockRankingResponse) response;
//                ArrayList<SubNewStockRankingModel> datas = result.list;
//                if (ADDFLAG_REFRESH == addFlag) {
//                    // 下拉刷新
//                    view.subNewStockRefreshSuccess(datas);
//                } else if (ADDFLAG_ADDMORE == addFlag) {
//                    // 上拉加载
//                    view.subNewStockMoreSuccess(datas);
//                } else {
//                    view.subNewStockSuccess(datas);
//                }
//            }
//
//            @Override
//            public void exception(ErrorInfo errorInfo) {
//                //执行错误处理
//                ToastUtils.showLongToast(errorInfo.getErr_code() + errorInfo.getMessage());
//            }
//        });

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
