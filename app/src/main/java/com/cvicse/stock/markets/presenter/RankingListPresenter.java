package com.cvicse.stock.markets.presenter;

import android.text.TextUtils;

import com.cvicse.base.mvp.BasePresenter;
import com.cvicse.base.utils.StringUtils;
import com.cvicse.base.utils.ToastUtils;
import com.cvicse.stock.http.loop.CateSortingRunnable;
import com.cvicse.stock.http.loop.RequestManager;
import com.cvicse.stock.http.loop.RunTaskState;
import com.cvicse.stock.markets.data.Param;
import com.cvicse.stock.markets.data.ParamUtil;
import com.cvicse.stock.markets.presenter.contract.RankingListContract;
import com.cvicse.stock.markets.ui.HKFragment;
import com.cvicse.stock.markets.ui.SHSZFragment;
import com.cvicse.stock.utils.DataConvert;
import com.github.mikephil.charting.formatter.IFillFormatter;
import com.mitake.core.QuoteItem;
import com.mitake.core.keys.quote.AddValueCustomField;
import com.mitake.core.keys.quote.QuoteCustomField;
import com.mitake.core.keys.quote.SortType;
import com.mitake.core.network.Network;
import com.mitake.core.request.CateRankingType;
import com.mitake.core.response.CateSortingResponse;
import com.mitake.core.util.MarketSiteType;

import java.util.ArrayList;
import java.util.Map;

import static com.cvicse.stock.markets.data.ParamUtil.RANKING_TYPE_AMOUNT;
import static com.cvicse.stock.markets.data.ParamUtil.RANKING_TYPE_AMOUNT_HSSC;
import static com.cvicse.stock.markets.data.ParamUtil.RANKING_TYPE_DECLINE;
import static com.cvicse.stock.markets.data.ParamUtil.RANKING_TYPE_DECLINE_HSSC;
import static com.cvicse.stock.markets.data.ParamUtil.RANKING_TYPE_RAISE;
import static com.cvicse.stock.markets.data.ParamUtil.RANKING_TYPE_RAISE_HSSC;
import static com.cvicse.stock.markets.data.ParamUtil.RANKING_TYPE_TURNOVERRATE;
import static com.cvicse.stock.markets.data.ParamUtil.RANKING_TYPE_TURNOVERRATE_HSSC;
import static com.cvicse.stock.markets.data.ParamUtil.STOCK_NAME_GZQH;
import static com.cvicse.stock.markets.data.ParamUtil.STOCK_NAME_HK;
import static com.cvicse.stock.markets.data.ParamUtil.STOCK_NAME_SHSZ;
import static com.cvicse.stock.markets.data.ParamUtil.STOCK_NAME_ZSQH;

/**
 * Created by ruan_ytai on 17-1-17.
 */
public class RankingListPresenter extends BasePresenter implements RankingListContract.Presenter {
    private RankingListContract.View view;
    //沪深，港股
    private String[] stockTypeArr = { CateRankingType.ALL,"HK1010"};

    //每次请求20条数据,分别对应涨幅榜，跌幅榜，换手率和成交额
    private String[] param = {"0,20,12,1,0","0,20,12,0,0", "0,20,15,1,0","0,20,20,1,0"};
    private String[] group = {"涨幅榜", "跌幅榜", "换手率", "成交额", "上证A股"};
    private String id = "";
    private String stockType = "";
    private boolean isCFF = false;
    private Map<String,Param> paramMap; //参数辅助类

    private int rankType = 0; // 排序类型（0-默认，1-下拉刷新，2-上拉加载）
    private RunTaskState requestSign;

    /**
     * @param rankingType 除沪深和港股外，均为涨幅榜,榜单类型
     */
    @Override
    public void requestRankingData(String rankingType) {
        String code = null;
        if(STOCK_NAME_SHSZ.equals(stockType) || STOCK_NAME_HK.equals(stockType)) {
            for (int i = 0; i < group.length; i++) {
                if (group[i].equals(rankingType)) {
                    code = param[i];
                    break;
                }
            }
        } else if(!TextUtils.isEmpty(stockType) &&!TextUtils.isEmpty(rankingType)){
            Param param = paramMap.get(stockType);
            if(param != null){
                code = param.getSortParam();
            }
        }
        if(null == code){
            if (RANKING_TYPE_RAISE_HSSC.equals(rankingType)) {// 沪深市场-涨幅榜
                code = ParamUtil.getInstance().getTypeById(RANKING_TYPE_RAISE);
            } else if (RANKING_TYPE_DECLINE_HSSC.equals(rankingType)) {// 沪深市场-跌幅榜
                code = ParamUtil.getInstance().getTypeById(RANKING_TYPE_DECLINE);
            } else if (RANKING_TYPE_AMOUNT_HSSC.equals(rankingType)) {
                code = ParamUtil.getInstance().getTypeById(RANKING_TYPE_AMOUNT);
            } else if (RANKING_TYPE_TURNOVERRATE_HSSC.equals(rankingType)) {
                code = ParamUtil.getInstance().getTypeById(RANKING_TYPE_TURNOVERRATE);
            } else {
                code = ParamUtil.getInstance().getTypeById(rankingType);
            }
        }
        rankType = 0;
        cateSortingRequest(code);
    }

    /**
     * 点击排序调用的接口（下拉刷新，上拉加载）
     * stockId还是之前的思路，rankingType直接使用
     */
    @Override
    public void requestSortRankingData(String rankingType) {
        paramMap = ParamUtil.getInstance().getParamMap();
        rankType = 0;
        cateSortingRequest(rankingType);
    }

    /**
     * 实时刷新
     */
    private void cateSortingRequest(String sortType) {
        RequestManager.cancel(requestSign);
        requestSign = RequestManager.request(new CateSortingRunnable(id,sortType, new int[]{-1}, new int[]{-1}) {
            @Override
            public void onBack(CateSortingResponse response) {
                ArrayList<QuoteItem> list = response.list;
                if( rankType == 0 ){
                    view.onRequestSuccess(DataConvert.QuoteItemList(list), response.addValueModel);
                }else if(rankType == 1){
                    view.onRequestPulldownSuccess(DataConvert.QuoteItemList(list), response.addValueModel);
                }else{
                    view.onRequestPullupSuccess(DataConvert.QuoteItemList(list), response.addValueModel);
                }
                rankType = 0;
            }

            @Override
            public void onError(int i, String error) {
                ToastUtils.showLongToast("CateSortingRequest:" + error);
            }
        });
    }

    /**
     * 下拉刷新
     * @param rankingType
     */
    @Override
    public void requestPulldownRankingData(String rankingType) {
        rankType = 1;
        cateSortingRequest(rankingType);
    }

    /**
     *  上拉加载
     * @param rankingType
     */
    @Override
    public void requestPullupRankingData(String rankingType) {
        String[] split = rankingType.split(",");
        rankType = 2;
        cateSortingRequest(rankingType);
    }

    @Override
    public void setView(RankingListContract.View view) {
        this.view = view;
    }

    public void init(String stockType){
        paramMap = ParamUtil.getInstance().getParamMap();
        this.stockType = stockType;
        if(STOCK_NAME_SHSZ.equals(stockType) || STOCK_NAME_HK.equals(stockType)) {

            if (SHSZFragment.STOCK_TYPE_HS.equals(stockType)) {
                id = stockTypeArr[0];
            } else if (HKFragment.STOCK_TYPE_HK.equals(stockType)) {
                id = stockTypeArr[1];
            }
        } else if(!TextUtils.isEmpty(stockType)){
            //从ParamUtil中取
            Param param = paramMap.get(stockType);
            if(param != null){
                id = param.getStockCode();
            }
        }
        if(StringUtils.isEmpty(id)){
            id = stockType;
        }

        if (STOCK_NAME_ZSQH.equals(stockType) || STOCK_NAME_GZQH.equals(stockType)) {
            isCFF = true;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        RequestManager.cancel(requestSign);
    }
}
