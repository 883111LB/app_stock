package com.cvicse.stock.markets.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.cvicse.stock.R;
import com.cvicse.stock.base.PBaseActivity;
import com.cvicse.stock.base.ToolBar;
import com.cvicse.stock.markets.adapter.GrdAdapter;
import com.cvicse.stock.seachstock.SearchHistoryActivity;
import com.cvicse.stock.view.MyGridView;

import butterknife.BindView;

import static com.cvicse.stock.markets.data.ParamUtil.RANKING_TYPE_RAISE_HSSC;
import static com.cvicse.stock.markets.data.ParamUtil.STOCK_NAME_ETF;
import static com.cvicse.stock.markets.data.ParamUtil.STOCK_NAME_FJA;
import static com.cvicse.stock.markets.data.ParamUtil.STOCK_NAME_FJB;
import static com.cvicse.stock.markets.data.ParamUtil.STOCK_NAME_FJJJ;
import static com.cvicse.stock.markets.data.ParamUtil.STOCK_NAME_LOF;
import static com.cvicse.stock.markets.data.ParamUtil.STOCK_NAME_SHJJ;
import static com.cvicse.stock.markets.data.ParamUtil.STOCK_NAME_SZJJ;

/**
 * Created by ruan_ytai on 17-2-15.
 */

public class SHSZfundActivity extends PBaseActivity implements AdapterView.OnItemClickListener{

    @BindView(R.id.grd_shsz_fund) MyGridView mGrdHsFund;
    @BindView(R.id.topbar) ToolBar mTopBar;

    /**
     * 沪深基金名称
     */
    private String[] fundName = { STOCK_NAME_SHJJ,STOCK_NAME_SZJJ,STOCK_NAME_LOF,STOCK_NAME_ETF,
            STOCK_NAME_FJJJ,STOCK_NAME_FJA,STOCK_NAME_FJB };

    @Override
    protected int getLayoutId() {
        return R.layout.activity_markets_fund;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {

        GrdAdapter shAdapter = new GrdAdapter(this,fundName);
        mGrdHsFund.setAdapter(shAdapter);
        mGrdHsFund.setOnItemClickListener(this);

        mTopBar.setToolBarClickListener(new ToolBar.ToolBarClickListener() {
            @Override
            public void onClick(View view, ToolBar.Type type) {
                switch (type){
                    case RIGHT_TYPE_1:
                        SearchHistoryActivity.startActivity(SHSZfundActivity.this);
                        break;
                }
            }
        });
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String stockType = null;
        switch (parent.getId()){
            case R.id.grd_shsz_fund:
                stockType = fundName[position];
                break;

            default:
                break;
        }
            RankingListAllActivity.actionStart(parent.getContext(),stockType,RANKING_TYPE_RAISE_HSSC);
    }
}
