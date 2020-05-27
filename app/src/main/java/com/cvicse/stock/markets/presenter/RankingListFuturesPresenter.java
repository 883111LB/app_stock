package com.cvicse.stock.markets.presenter;

import com.cvicse.base.mvp.BasePresenter;
import com.cvicse.stock.http.loop.CateSortingRunnable;
import com.cvicse.stock.http.loop.RequestManager;
import com.cvicse.stock.http.loop.RunTaskState;
import com.cvicse.stock.markets.presenter.contract.RankingListFuturesContract;
import com.mitake.core.keys.quote.AddValueCustomField;
import com.mitake.core.response.CateSortingResponse;

//import com.mitake.core.keys.quote.AddValueCustomField;
//import com.mitake.core.keys.quote.QuoteCustomField;

/**
 * 期货/全球、外汇/5分钟涨幅榜排序页面
 * Created by tang_xqing on 2018/7/30.
 */
public class RankingListFuturesPresenter extends BasePresenter implements RankingListFuturesContract.Presenter {
    private RankingListFuturesContract.View mView;
    private RunTaskState requestSign;
    private String id;  // 分类代码
    private int rankType = 0; // 排序类型（0-默认，1-下拉刷新，2-上拉加载）
    private boolean isAddValue = false; // 是否请求增值指标
    private int[] addvalueCustom = null;

    @Override
    public void setView(RankingListFuturesContract.View view) {
        this.mView = view;
    }

    @Override
    public void init(String id,boolean isAddValue) {
        this.id = id;
        this.isAddValue = isAddValue;
    }

    @Override
    public void requestRanking(String param) {
        RequestManager.cancel(requestSign);
        int[] addvalueCustom;
        if( isAddValue ){
            addvalueCustom = new int[]{AddValueCustomField.ALL_COLUMN};
        }else{
            addvalueCustom = null;
        }
        requestSign = RequestManager.request( new CateSortingRunnable(id,param,new int[]{-1} , addvalueCustom) {
            @Override
            public void onBack(CateSortingResponse response) {
                if( null == response || null == response.list || response.list.isEmpty()){
                    return;
                }
                if( 1 == rankType ){
                    mView.requestPullDownRankingSuccess(response);
                }else  if( 2 == rankType ){
                    mView.requestPullUpRankingSuccess(response);
                }else{
//                   for (int i=0;i<response.list.size();i++){
//                       System.out.println(response.list.get(i).id+"++++"+response.list.get(i).settlement+"+++"+response.list.get(i).close+"+++++++++"+i);
//                   }
                    mView.requestRankingSuccess(response);
                }
                rankType = 0;
            }

            @Override
            public void onError(int i, String error) {
                mView.requestRankingFail();
            }
        });
    }

    @Override
    public void requestPullDownRanking(String parm) {
        rankType = 1;
        requestRanking(parm);
    }

    @Override
    public void requestPullUpRanking(String parm) {
        rankType = 2;
        requestRanking(parm);
    }

    @Override
    public void onPause() {
        super.onPause();
        RequestManager.cancel(requestSign);
    }
}
