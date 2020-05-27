package com.cvicse.stock.markets.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.cvicse.stock.R;
import com.cvicse.stock.base.PBaseActivity;
import com.cvicse.stock.markets.ui.ztsorting.ZTSortingListActivity;
import com.cvicse.stock.markets.ui.stockdetail.MarginTradingActivity;
import com.cvicse.stock.markets.ui.stockdetail.PriceAnalysisActivity;
import com.cvicse.stock.markets.ui.stockdetail.ReplayActivity;
import com.cvicse.stock.markets.ui.subnewbondstock.SubNewBondStockActivity;
import com.cvicse.stock.markets.ui.subnewstock.SubNewStockActivity;

import butterknife.BindView;

/**
 * 数据中心
 * Created by tang_xqing on 18-8-3.
 */
public class DataCenterActivity extends PBaseActivity {

    @BindView(R.id.lly_margin_trading)
    LinearLayout llyMarginTrading;   // 融资融券
    @BindView(R.id.lly_price_analysis)
    LinearLayout llyPriceAnalysis;//涨跌分析
    @BindView(R.id.lly_replay)
    LinearLayout lly_replay;//复盘涨跌
    @BindView(R.id.lly_cxg)
    LinearLayout lly_cxg;//次新股
    @BindView(R.id.lly_cxz)
    LinearLayout lly_cxz;//次新债
    @BindView(R.id.lly_ztzt)
    LinearLayout lly_ztzt;//涨停专题

    @Override
    protected int getLayoutId() {
        return R.layout.activity_data_center;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        llyMarginTrading.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             // 跳转到融资融券界面
                MarginTradingActivity.actionStart(DataCenterActivity.this);
            }
        });
        llyPriceAnalysis.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //跳转到涨跌分析界面
                PriceAnalysisActivity.actionStart(DataCenterActivity.this);
            }
        });
        lly_replay.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //跳转到复盘涨跌界面
                ReplayActivity.actionStart(DataCenterActivity.this);
            }
        });
        lly_cxg.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //跳转到次新股界面
                SubNewStockActivity.actionStart(DataCenterActivity.this);
            }
        });
        lly_cxz.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //跳转到次新债界面
                SubNewBondStockActivity.actionStart(DataCenterActivity.this);
            }
        });
        lly_ztzt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转到涨停专题界面
                ZTSortingListActivity.actionStart(DataCenterActivity.this);
            }
        });
    }

    @Override
    protected void initData() {

    }
}
