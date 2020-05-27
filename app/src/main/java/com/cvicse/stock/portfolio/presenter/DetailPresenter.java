package com.cvicse.stock.portfolio.presenter;

import com.cvicse.base.mvp.BasePresenter;
import com.cvicse.stock.common.Setting.Setting;
import com.cvicse.stock.http.ResponseCallback;
import com.cvicse.stock.portfolio.data.StockNewsBean;
import com.cvicse.stock.portfolio.presenter.constract.DetailConstract;
import com.mitake.core.StockBulletinDetailItem;
import com.mitake.core.StockNewsDetailItem;
import com.mitake.core.StockReportDetailItem;
import com.mitake.core.request.F10Request;
import com.mitake.core.request.StockBulletinRequest;
import com.mitake.core.request.StockNewsRequest;
import com.mitake.core.request.StockReportRequest;
import com.mitake.core.response.Response;
import com.mitake.core.response.StockBulletinResponse;
import com.mitake.core.response.StockNewsResponse;
import com.mitake.core.response.StockReportResponse;

/**
 * 公告,新闻及报告详情
 * Created by ding_syi on 17-1-11.
 */
public class DetailPresenter extends BasePresenter implements DetailConstract.PresenterDetail {
    private  DetailConstract.View view;
    // 新闻id
    private String id = "";
    //类型 公告"type_report" 新闻"type_news" 通知"type_notice"
    private String type = "";

    /**
     * 查询公告
     */
    private void requestBulletinDetail() {
        StockBulletinRequest stockBulletinRequest=new StockBulletinRequest();
        F10Request.SRC = Setting.getDataSource();  // new
        stockBulletinRequest.sendV2(id, F10Request.SRC, new ResponseCallback(stockBulletinRequest) {
            @Override
            public void onBack(Response response) {
                StockBulletinResponse stockBulletinResponse= (StockBulletinResponse) response;
                StockBulletinDetailItem info = stockBulletinResponse.info;
                if (null != info) {
                    StockNewsBean stockNewsBean = new StockNewsBean();
                    stockNewsBean.setContent(info.Content);
                    stockNewsBean.setPurl(info.PURL_);
                    stockNewsBean.setTitle(info.title);
                    stockNewsBean.setDatasouce(info.dataSource);
                    view.onTastSuccess(stockNewsBean);
                }
            }

            @Override
            public void onError(int i, String s) {
            }
        });

    }

    /**
     * 查询新闻
     */
    private void requestNewsDetail() {
        StockNewsRequest stockNewsRequest=new StockNewsRequest();
        F10Request.SRC = Setting.getDataSource();  // new
        stockNewsRequest.sendV2(id, F10Request.SRC, new ResponseCallback(stockNewsRequest) {
            @Override
            public void onBack(Response response) {
                StockNewsResponse stockNewsResponse= (StockNewsResponse) response;
                StockNewsDetailItem info = stockNewsResponse.info;
                StockNewsBean stockNewsBean = new StockNewsBean();
                if (null != info) {
                    stockNewsBean.setContent(info.ABSTRACT_);
                    stockNewsBean.setPurl(info.PURL_);
                    stockNewsBean.setReportTitle(info.reportTitle);
                    view.onTastSuccess(stockNewsBean);
                }
            }

            @Override
            public void onError(int i, String s) {

            }
        });
    }

    /**
     * 查询通知/研报
     */
    private void requestNoticeDetail() {
        StockReportRequest stockNewsRequest = new StockReportRequest();
        F10Request.SRC = Setting.getDataSource();  // new
        stockNewsRequest.sendV2(id, F10Request.SRC, new ResponseCallback(stockNewsRequest) {
            @Override
            public void onBack(Response response) {
                StockReportResponse stockNewsResponse=
                        (StockReportResponse) response;
                StockReportDetailItem info = stockNewsResponse.info;
                if (null != info) {
                    StockNewsBean stockNewsBean = new StockNewsBean();
                    stockNewsBean.setContent(info.ABSTRACT_);
                    stockNewsBean.setPurl(info.PURL_);
                    stockNewsBean.setReportTitle(info.reportTitle);
                    stockNewsBean.setDatasouce(info.dataSource);
                    view.onTastSuccess(stockNewsBean);
                }

            }

            @Override
            public void onError(int i, String s) {
                view.onTaskFail();
            }
        });
    }



    @Override
    public void setView(Object view) {
        this.view = (DetailConstract.View) view;
    }

    /**
     * 初始化请求信息
     * @param id 咨询id
     * @param type 展示类型、包括新闻、公告、通知
     */
    @Override
    public void init(String id,String type){
        this.id = id;
        this.type =  type;
    }

    @Override
    public void queryDetail() {
        if(type.equals("type_report")){
            requestNoticeDetail();
        } else if(type.equals("type_news")){
            requestNewsDetail();
        } else if(type.equals("type_bulletin")){
            requestBulletinDetail();
        } else {
            requestNoticeDetail();
        }
    }
}
