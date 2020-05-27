package com.cvicse.stock.markets.presenter.marketdetail;

import com.cvicse.base.mvp.BasePresenter;
import com.cvicse.stock.http.loop.CateSortingRunnable;
import com.cvicse.stock.http.loop.RequestManager;
import com.cvicse.stock.http.loop.RunTaskState;
import com.cvicse.stock.markets.presenter.marketdetail.contract.IndexRankingContract;
import com.cvicse.stock.markets.ui.stockdetail.IndexRankingFragment;
import com.cvicse.stock.utils.DataConvert;
import com.mitake.core.QuoteItem;
import com.mitake.core.response.CateSortingResponse;

import java.util.ArrayList;

/**
 * 排行页面
 * Created by liu_zlu on 2017/2/13 11:47
 */
public class IndexRankingPresenter extends BasePresenter implements IndexRankingContract.Presenter {
    private IndexRankingContract.View view;
    private String stockId;
    private String type,code;

    //每次请求20条数据,分别对应涨幅榜，跌幅榜，换手率和成交额
    private String[] param = {"0,20,12,1,0","0,20,12,0,0", "0,20,15,1,0","0,20,20,1,0"};
    private RunTaskState requestSign;

    @Override
    public void setView(IndexRankingContract.View view) {
        this.view = view;
    }

    @Override
    public void init(String stockId, String type) {
        this.stockId = stockId;
        this.type = type;
        switch (type){
            case IndexRankingFragment.TYPEINCREASE:
                code = param[0];
                break;
            case IndexRankingFragment.TYPEDECLINE:
                code = param[1];
                break;
            case IndexRankingFragment.TYPETURNOVERRATE:
                code = param[2];
                break;
            case IndexRankingFragment.TYPETURNOVER:
                code = param[3];
                break;
        }
    }

    @Override
    public void queryIndexRanking() {
        RequestManager.cancel(requestSign);
        requestSign = RequestManager.request(new CateSortingRunnable(stockId, code){

            @Override
            public void onBack(CateSortingResponse response) {
                CateSortingResponse cateSortingResponse = (CateSortingResponse) response;
                ArrayList<QuoteItem> list = cateSortingResponse.list;
                view.onCateRankingSuccess(DataConvert.QuoteItemList(list));
            }

            @Override
            public void onError(int i, String error) {
                view.onCateRankingFail();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        queryIndexRanking();
    }

    @Override
    public void onPause() {
        super.onPause();
        RequestManager.cancel(requestSign);
    }

    /**
     * 页面获取presenter层信息
     */
    @Override
    public void getData() {
        //view.onDataSuccess(stockType,rankingType);
    }
}
