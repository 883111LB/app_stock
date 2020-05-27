package com.cvicse.stock.markets.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.cvicse.annotations.MVPInject;
import com.cvicse.stock.R;
import com.cvicse.stock.base.PBaseActivity;
import com.cvicse.stock.base.ToolBar;
import com.cvicse.stock.markets.adapter.NewStockDetailAdapter;
import com.cvicse.stock.markets.data.NewStockDetailBo;
import com.cvicse.stock.markets.presenter.contract.NewStockDetailContract;
import com.cvicse.stock.view.ListViewForScrollView;
import com.mitake.core.NewShareDetail;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by ruan_ytai on 17-3-1.
 */

public class NewStockDetailActivity extends PBaseActivity implements NewStockDetailContract.View {

    @MVPInject
    NewStockDetailContract.Presenter presenter;

    @BindView(R.id.topbar) ToolBar mTopBar;
    @BindView(R.id.lsv_sgprocess) ListViewForScrollView mLsvSgprocess;
    @BindView(R.id.lsv_fxdata) ListViewForScrollView mLsvFxdata;

    private static final String KEY_TRADING_CODE = "tradingCode";

    private String tradingCode;

    private NewStockDetailAdapter sgProcessAdapter;
    private NewStockDetailAdapter fxDataAdapter;

    public static void actionStart(Context context,String tradingCode){
        Intent intent = new Intent(context,NewStockDetailActivity.class);
        intent.putExtra(KEY_TRADING_CODE,tradingCode);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_newstockdetail;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
//        DaggerMarketsComponent.builder().marketsModule(new MarketsModule(this))
//                .build().inject(this);

        tradingCode = getIntent().getStringExtra(KEY_TRADING_CODE);

        sgProcessAdapter = new NewStockDetailAdapter(this);
        mLsvSgprocess.setAdapter(sgProcessAdapter);
        fxDataAdapter = new NewStockDetailAdapter(this);
        mLsvFxdata.setAdapter(fxDataAdapter);
    }

    @Override
    protected void initData() {
        presenter.requestStockDetail(tradingCode);
    }

    /**
     * 请求股票详细信息成功
     */
    @Override
    public void onRequestStockDetailSuccess(NewShareDetail info) {
        ArrayList<NewStockDetailBo> sgDataList = new ArrayList<>();
        ArrayList<NewStockDetailBo> fxDataList = new ArrayList<>();
        if( null == info  ){
            return;
        }
        mTopBar.setTitle(null == info.getSecuabbr() ? "--" : info.getSecuabbr());

        sgDataList.add(new NewStockDetailBo("股票名称",info.getSecuabbr()));
        sgDataList.add(new NewStockDetailBo("股票代码",info.getTradingCode()));
        sgDataList.add(new NewStockDetailBo("申购代码",info.getApplyCode()));
        sgDataList.add(new NewStockDetailBo("申购日期",info.getBookStartDateOn()));
        sgDataList.add(new NewStockDetailBo("中签号公布日",info.getSuccResultNoticeDate()));
        if(info.getAllotrateon() !=null && !info.getAllotrateon().equals("-")){
            String str = info.getAllotrateon()+"%";
            sgDataList.add(new NewStockDetailBo("中签率",str));
        }else{
            sgDataList.add(new NewStockDetailBo("中签率",info.getAllotrateon()));
        }

        sgDataList.add(new NewStockDetailBo("中签号",info.getIssueAllotnOn()));
        sgDataList.add(new NewStockDetailBo("资金解冻日",info.getRefundDateOn()));
        sgDataList.add(new NewStockDetailBo("上市日期",info.getListingDate()));
        sgProcessAdapter.setData(sgDataList);

        fxDataList.add(new NewStockDetailBo("发行价格",info.getIssuePrice()));
        fxDataList.add(new NewStockDetailBo("发行市盈率",info.getPeaIssue()));
        fxDataList.add(new NewStockDetailBo("发行总数",info.getIssueShare()));
        fxDataList.add(new NewStockDetailBo("网上发行",info.getIssueShareOn()));
        fxDataList.add(new NewStockDetailBo("申购数量上限",info.getCapplyShare()));
        fxDataList.add(new NewStockDetailBo("申购资金上限",info.getCapplyPrice()));
        fxDataList.add(new NewStockDetailBo("公司简介",info.getComProfile()));
        fxDataList.add(new NewStockDetailBo("经营范围",info.getBusinessScope()));
        fxDataList.add(new NewStockDetailBo("所属板块",info.getBoradName()));
        fxDataList.add(new NewStockDetailBo("是否是科创板",info.getKeyCode()));
        fxDataAdapter.setData(fxDataList);

    }

    /**
     * 请求股票详细信息失败
     */
    @Override
    public void onRequestStockDetailFail() {

    }

}
