package com.cvicse.stock.markets.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.cvicse.annotations.MVPInject;
import com.cvicse.stock.R;
import com.cvicse.stock.base.PBaseActivity;
import com.cvicse.stock.base.ToolBar;
import com.cvicse.stock.markets.adapter.CalendarLsvAdapter;
import com.cvicse.stock.markets.presenter.contract.CalendarDayContract;
import com.cvicse.stock.pulltorefresh.PullToRefreshBase;
import com.cvicse.stock.pulltorefresh.PullToRefreshScrollView;
import com.cvicse.stock.seachstock.SearchHistoryActivity;
import com.cvicse.stock.view.ListViewForScrollView;
import com.mitake.core.NewShareItem;
import com.mitake.core.NewShareList;

import java.util.ArrayList;

import butterknife.BindView;

import static android.view.View.GONE;
import static com.cvicse.stock.markets.adapter.CalendarLsvAdapter.JJFX;
import static com.cvicse.stock.markets.adapter.CalendarLsvAdapter.JRSG;
import static com.cvicse.stock.markets.adapter.CalendarLsvAdapter.JRSS;
import static com.cvicse.stock.markets.adapter.CalendarLsvAdapter.JRZQ;
import static com.cvicse.stock.markets.adapter.CalendarLsvAdapter.WSS;
import static com.cvicse.stock.markets.data.DateUtil.DateConvert;

/**
 * Created by ruan_ytai on 17-2-24.
 */

public class CalendarDayActivity extends PBaseActivity implements CalendarDayContract.View,
        AdapterView.OnItemClickListener {

    @MVPInject
    CalendarDayContract.Presenter presenter;

    @BindView(R.id.lsv_sg) ListViewForScrollView mLsvSg;
    @BindView(R.id.lsv_ss) ListViewForScrollView mLsvSs;
    @BindView(R.id.lsv_zq) ListViewForScrollView mLsvZq;
    @BindView(R.id.lsv_jjfx) ListViewForScrollView mLsvJjfx;
    @BindView(R.id.lsv_wss) ListViewForScrollView mLsvWss;
    @BindView(R.id.pull_scl_calendar) PullToRefreshScrollView mPullSclCalendar;

    @BindView(R.id.topbar) ToolBar mTopbar;
    @BindView(R.id.tev_sg_no_data) TextView mTevSgNoData;
    @BindView(R.id.tev_ss_no_data) TextView mTevSsNoData;
    @BindView(R.id.tev_zq_no_data) TextView mTevZqNoData;
    @BindView(R.id.tev_jjfx_no_data) TextView mTevJjfxNoData;
    @BindView(R.id.tev_wss_no_data) TextView mTevWssNoData;

    private static final String KEY_DATE = "datetime";
    private String date;

    private CalendarLsvAdapter sgAdapter;
    private CalendarLsvAdapter ssAdapter;
    private CalendarLsvAdapter zqAdapter;
    private CalendarLsvAdapter jjfxAdapter;
    private CalendarLsvAdapter wssAdapter;

    public static void startAction(Context context, String date) {
        Intent intent = new Intent(context, CalendarDayActivity.class);
        intent.putExtra(KEY_DATE, date);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_calendar_day;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
//        DaggerMarketsComponent.builder().marketsModule(new MarketsModule(this)).build().inject(this);
        date = DateConvert(getIntent().getStringExtra(KEY_DATE));

        sgAdapter = new CalendarLsvAdapter(this, JRSG);
        mLsvSg.setAdapter(sgAdapter);
        mLsvSg.setOnItemClickListener(this);

        ssAdapter = new CalendarLsvAdapter(this, JRSS);
        mLsvSs.setAdapter(ssAdapter);
        mLsvSs.setOnItemClickListener(this);

        zqAdapter = new CalendarLsvAdapter(this, JRZQ);
        mLsvZq.setAdapter(zqAdapter);
        mLsvZq.setOnItemClickListener(this);

        jjfxAdapter = new CalendarLsvAdapter(this, JJFX);
        mLsvJjfx.setAdapter(jjfxAdapter);
        mLsvJjfx.setOnItemClickListener(this);

        wssAdapter = new CalendarLsvAdapter(this, WSS);
        mLsvWss.setAdapter(wssAdapter);
        mLsvWss.setOnItemClickListener(this);

        mPullSclCalendar.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ScrollView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ScrollView> refreshView) {
                presenter.requestNewStockDetail(date);
            }
        });

        mTopbar.setToolBarClickListener(new ToolBar.ToolBarClickListener() {
            @Override
            public void onClick(View view, ToolBar.Type type) {
                switch (type){
                    case RIGHT_TYPE_1:
                        SearchHistoryActivity.startActivity(CalendarDayActivity.this);
                        break;
                }
            }
        });
    }

    @Override
    protected void initData() {
        presenter.requestNewStockDetail(date);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //第一行为标题，不设点击事件
        if(position >=1){
            String tradingCode = null;
            switch (parent.getId()) {
                case R.id.lsv_sg:
                    tradingCode = sgAdapter.getItem(position).getTradingCode();
                    break;

                case R.id.lsv_ss:
                    tradingCode = ssAdapter.getItem(position).getTradingCode();
                    break;

                case R.id.lsv_zq:
                    tradingCode = zqAdapter.getItem(position).getTradingCode();
                    break;

                case R.id.lsv_jjfx:
                    tradingCode = jjfxAdapter.getItem(position).getTradingCode();
                    break;

                case R.id.lsv_wss:
                    tradingCode = wssAdapter.getItem(position).getTradingCode();
                    break;

                default:
                    break;
            }
            NewStockDetailActivity.actionStart(this, tradingCode);
        }

    }


    /**
     * 请求单个新股详情成功
     */
    @Override
    public void onRequestNewStockDetailSuccess(ArrayList<NewShareList> infos) {
        mPullSclCalendar.onRefreshComplete();
        if( null == infos || infos.isEmpty() ){
            return;
        }
        for (NewShareList list : infos) {
            ArrayList<NewShareItem> newShareItemList = list.getDataList();

            switch (list.getTitle()) {
                case JRSG:
                    mTevSgNoData.setVisibility(GONE);
                    sgAdapter.setData(newShareItemList);
                    break;

                case JRSS:
                    mTevSsNoData.setVisibility(GONE);
                    ssAdapter.setData(newShareItemList);
                    break;

                case JRZQ:
                    mTevZqNoData.setVisibility(GONE);
                    zqAdapter.setData(newShareItemList);
                    break;

                case JJFX:
                    mTevJjfxNoData.setVisibility(GONE);
                    jjfxAdapter.setData(newShareItemList);
                    break;

                case WSS:
                    mTevWssNoData.setVisibility(GONE);
                    wssAdapter.setData(newShareItemList);
                    break;

                default:
                    break;
            }
        }
    }

    /**
     * 请求新股详情失败
     */
    @Override
    public void onRrequestNewStockDetailFail() {

    }

}
