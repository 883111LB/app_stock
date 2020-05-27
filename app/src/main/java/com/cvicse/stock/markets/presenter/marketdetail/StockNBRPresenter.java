package com.cvicse.stock.markets.presenter.marketdetail;

import android.text.TextUtils;
import android.util.Log;

import com.cvicse.base.mvp.BasePresenter;
import com.cvicse.stock.common.Setting.Setting;
import com.cvicse.stock.http.ResponseCallback;
import com.cvicse.stock.markets.data.MarketNBRBo;
import com.cvicse.stock.markets.presenter.marketdetail.contract.StockNBRContract;
import com.cvicse.stock.portfolio.activity.StockDetailActivity;
import com.mitake.core.QuoteItem;
import com.mitake.core.StockBulletinItem;
import com.mitake.core.StockNewsItem;
import com.mitake.core.StockReportItem;
import com.mitake.core.network.Network;
import com.mitake.core.request.F10Request;
import com.mitake.core.request.StockBulletinListRequest;
import com.mitake.core.request.StockNewsListRequest;
import com.mitake.core.request.StockReportListRequest;
import com.mitake.core.response.IResponseCallback;
import com.mitake.core.response.Response;
import com.mitake.core.response.StockBulletinListResponse;
import com.mitake.core.response.StockNewsListResponse;
import com.mitake.core.response.StockReportListResponse;
import com.mitake.core.util.MarketSiteType;

import java.util.ArrayList;

/**
 * Created by liu_zlu on 2017/2/7 19:56
 */
public class StockNBRPresenter extends BasePresenter implements StockNBRContract.Presenter {
    StockNBRContract.View view;

    //(债券需传此参数)来源
    private final String SRC = "d";

    private QuoteItem quoteItem;//股票代码
    private String type ;  //类型
    private String mLastNewsId = null;// 当前最后一条新闻的id

    private static final int  NEWEST_TYPE = -1;  //-1 更新最新新闻列表资料
    private static final int  BEHIND_TYPE = 5;   //5 查询newsID之后的资料
    private static final int  BEFORE_TYPE = 6;    //6 查询newsID之前的资料

    private ArrayList<MarketNBRBo> newsList = new ArrayList<>();

    @Override
    public void setView(StockNBRContract.View view) {
        this.view = view;
    }

    @Override
    public void init(QuoteItem quoteItem, String type) {
        this.quoteItem = quoteItem;
        this.type = type;
    }

    /**
     * 查询最新列表
     */
    @Override
    public void queryNBR() {
        if(TextUtils.isEmpty(type)){
            return;
        }
        // 新闻
        if(StockDetailActivity.TYPE_NEWS.equals(type)){
            queryNews(NEWEST_TYPE,null);
            ///queryNews();
        } else if(type.equals(StockDetailActivity.TYPE_NOTICE) || type.equals(StockDetailActivity.TYPE_REPORT)){
            // 研报，或者通知
            queryReport(NEWEST_TYPE,null);
        } else {
            //公告
            queryBulletinList(NEWEST_TYPE,null);
        }

    }

    /**
     * * 查询更多列表
     */
    @Override
    public  void queryNBRMore() {

        if(type.equals(StockDetailActivity.TYPE_NEWS)){
            queryNews(BEHIND_TYPE,mLastNewsId);
        } else if(type.equals(StockDetailActivity.TYPE_NOTICE) || type.equals(StockDetailActivity.TYPE_REPORT)){
            queryReport(BEHIND_TYPE,mLastNewsId);
        } else {
            queryBulletinList(BEHIND_TYPE,mLastNewsId);
        }

    }

    /**
     * 查询新闻列表
     * @param updateType 查询类型
     * @param lastNewsId 最后一条资讯的ID
     */
    private void queryNews(final int updateType, String lastNewsId) {

        F10Request.SRC = Setting.getDataSource();  // new
        StockNewsListRequest stockNewsListRequest = new StockNewsListRequest();
        stockNewsListRequest.sendV2(quoteItem.id, updateType,lastNewsId, F10Request.SRC,new ResponseCallback(stockNewsListRequest) {
            @Override
            public void onBack(Response response) {
                StockNewsListResponse stockNewsListResponse= (StockNewsListResponse) response;
                ArrayList<StockNewsItem> list = stockNewsListResponse.list;

                //从别的页面切换回来时，newList要清空，防止内容重复
                if(updateType == -1){
                    newsList.clear();
                    mLastNewsId = null;
                }

                if(list== null || list.size()<=0){
                    return;
                }

                for(StockNewsItem stockNewsItem :list){
                    newsList.add(new MarketNBRBo(
                            stockNewsItem.ID_,
                            stockNewsItem.REPORTTITLE_,
                            stockNewsItem.STOCKNAME_,
                            stockNewsItem.INIPUBDATE_,
                            stockNewsItem.MEDIANAME_
                    ));
                }
                //最后一条ID
                mLastNewsId = newsList.get(newsList.size() -1).id;
                view.onRequestSuccess(newsList);
            }

            @Override
            public void onError(int i, String s) {
                view.onRequestFail();
            }
        });
    }

    /**
     * 研报和通知
     * @param updateType 查询类型
     * @param lastNewsId 最后一条资讯的ID
     */
    public void queryReport(final int updateType, String lastNewsId) {
        StockReportListRequest stockReportListRequest = new StockReportListRequest();
        F10Request.SRC = Setting.getDataSource();
        stockReportListRequest.sendV2(quoteItem.id, updateType,lastNewsId, F10Request.SRC, new IResponseCallback(){

            @Override
            public void callback(Response response) {
                StockReportListResponse stockNewsListResponse= (StockReportListResponse) response;
                ArrayList<StockReportItem> list = stockNewsListResponse.list;
                //newsList.clear();
                // 从别的页面切换回来时，newList要清空，防止内容重复
                if(updateType == -1){
                    newsList.clear();
                    mLastNewsId = null;
                }

                if(list== null || list.size()<=0){
                    return;
                }
                for (StockReportItem stockReportItem:list) {
                    newsList.add(new MarketNBRBo(
                            stockReportItem.ID_,
                            stockReportItem.ReportTitle,
                            stockReportItem.STOCKNAME_,
                            stockReportItem.PUBDATE_,
                            stockReportItem.ComName
                    ));
                }
                //最后一条ID
                mLastNewsId = newsList.get(newsList.size() -1).id;
                view.onRequestSuccess(newsList);

            }

            @Override
            public void exception(int i, String s) {

            }
        });

//        StockReportListRequest stockNewsListRequest=new StockReportListRequest();
//        F10Request.SRC = Setting.getDataSource();  // new

//        stockNewsListRequest.sendV2(quoteItem.id,updateType, lastNewsId,new ResponseCallback(stockNewsListRequest) {
//            @Override
//            public void onBack(Response response) {
//                StockReportListResponse stockNewsListResponse= (StockReportListResponse) response;
//                ArrayList<StockReportItem> list = stockNewsListResponse.list;
//                //newsList.clear();
//                // 从别的页面切换回来时，newList要清空，防止内容重复
//                if(updateType == -1){
//                    newsList.clear();
//                    mLastNewsId = null;
//                }
//
//                if(list== null || list.size()<=0){
//                    return;
//                }
//                for (StockReportItem stockReportItem:list) {
//                    newsList.add(new MarketNBRBo(
//                            stockReportItem.ID_,
//                            stockReportItem.ReportTitle,
//                            stockReportItem.STOCKNAME_,
//                            stockReportItem.PUBDATE_,
//                            stockReportItem.ComName
//                    ));
//                }
//                //最后一条ID
//                mLastNewsId = newsList.get(newsList.size() -1).id;
//                view.onRequestSuccess(newsList);
//            }
//
//            @Override
//            public void onError(int i, String s) {
//                view.onRequestFail();
//            }
//        });
    }

    /**
     * 查询公告
     * @param updateType 查询类型
     * @param lastNewsId 最后一条资讯的ID
     */
    private void queryBulletinList(final int updateType,String lastNewsId) {
        StockBulletinListRequest stockBulletinListRequest = new StockBulletinListRequest();
        String stockId = quoteItem.id;
        String subType = quoteItem.subtype;

        /**
         * 债券公告
         */
        if("1300".equals(subType)){
            F10Request.SRC = Setting.getDataSource();  // new
            stockBulletinListRequest.sendV2(stockId, updateType, lastNewsId, F10Request.SRC, new IResponseCallback() {
                @Override
                public void callback(Response response) {
                    StockBulletinListResponse stockNewsListResponse= (StockBulletinListResponse) response;
                    ArrayList<StockBulletinItem> list = stockNewsListResponse.list;
                    //newsList.clear();

                    // 从别的页面切换回来时，newList要清空，防止内容重复
                    if(updateType == -1){
                        newsList.clear();
                        mLastNewsId = null;
                    }

                    if(list== null || list.size()<=0){
                        return;
                    }
                    for (StockBulletinItem stockBulletinItem:list) {
                        newsList.add(new MarketNBRBo(
                                stockBulletinItem.ID_,
                                stockBulletinItem.TITLE_,
                                stockBulletinItem.STOCKNAME_,
                                stockBulletinItem.PUBDATE_,
                                ""
                        ));
                    }
                    //最后一条ID
                    mLastNewsId = newsList.get(newsList.size() -1).id;
                    view.onRequestSuccess(newsList);
                }

                @Override
                public void exception(int i, String s) {
                    view.onRequestFail();
                }
            });
//            stockBulletinListRequest.sendV2(stockId,updateType, lastNewsId,new ResponseCallback(stockBulletinListRequest) {
//                @Override
//                public void onBack(Response response) {
//                    StockBulletinListResponse stockNewsListResponse= (StockBulletinListResponse) response;
//                    ArrayList<StockBulletinItem> list = stockNewsListResponse.list;
//                    //newsList.clear();
//
//                    // 从别的页面切换回来时，newList要清空，防止内容重复
//                    if(updateType == -1){
//                        newsList.clear();
//                        mLastNewsId = null;
//                    }
//
//                    if(list== null || list.size()<=0){
//                        return;
//                    }
//                    for (StockBulletinItem stockBulletinItem:list) {
//                        newsList.add(new MarketNBRBo(
//                                stockBulletinItem.ID_,
//                                stockBulletinItem.TITLE_,
//                                stockBulletinItem.STOCKNAME_,
//                                stockBulletinItem.PUBDATE_,
//                                ""
//                        ));
//                    }
//                    //最后一条ID
//                    mLastNewsId = newsList.get(newsList.size() -1).id;
//                    view.onRequestSuccess(newsList);
//                }
//
//                @Override
//                public void onError(int i, String s) {
//                    view.onRequestFail();
//                }
//            });
        }else{
            //其他公告
            F10Request.SRC = Setting.getDataSource();  // new
            stockBulletinListRequest.sendV2(stockId, updateType, lastNewsId, F10Request.SRC, new IResponseCallback() {
                @Override
                public void callback(Response response) {
                    StockBulletinListResponse stockNewsListResponse= (StockBulletinListResponse) response;
                    ArrayList<StockBulletinItem> list = stockNewsListResponse.list;
                    //newsList.clear();

                    //从别的页面切换回来时，newList要清空，防止内容重复
                    if(updateType == -1){
                        newsList.clear();
                        mLastNewsId = null;
                    }

                    if(list== null || list.size()<=0){
                        return;
                    }
                    for (StockBulletinItem stockBulletinItem:list) {
                        newsList.add(new MarketNBRBo(
                                stockBulletinItem.ID_,
                                stockBulletinItem.TITLE_,
                                stockBulletinItem.STOCKNAME_,
                                stockBulletinItem.PUBDATE_,
                                ""
                        ));
                    }
                    //最后一条ID
                    mLastNewsId = newsList.get(newsList.size() -1).id;
                    view.onRequestSuccess(newsList);
                }

                @Override
                public void exception(int i, String s) {
                    view.onRequestFail();
                }
            });
//            stockBulletinListRequest.sendV2(stockId,updateType, lastNewsId,new ResponseCallback(stockBulletinListRequest) {
//                @Override
//                public void onBack(Response response) {
//                    StockBulletinListResponse stockNewsListResponse= (StockBulletinListResponse) response;
//                    ArrayList<StockBulletinItem> list = stockNewsListResponse.list;
//                    //newsList.clear();
//
//                    //从别的页面切换回来时，newList要清空，防止内容重复
//                    if(updateType == -1){
//                        newsList.clear();
//                        mLastNewsId = null;
//                    }
//
//                    if(list== null || list.size()<=0){
//                        return;
//                    }
//                    for (StockBulletinItem stockBulletinItem:list) {
//                        newsList.add(new MarketNBRBo(
//                                stockBulletinItem.ID_,
//                                stockBulletinItem.TITLE_,
//                                stockBulletinItem.STOCKNAME_,
//                                stockBulletinItem.PUBDATE_,
//                               ""
//                        ));
//                    }
//                    //最后一条ID
//                    mLastNewsId = newsList.get(newsList.size() -1).id;
//                    view.onRequestSuccess(newsList);
//                }
//
//                @Override
//                public void onError(int i, String s) {
//                    view.onRequestFail();
//                }
//            });
        }
    }

}
