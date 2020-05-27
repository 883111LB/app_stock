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
import com.cvicse.stock.markets.adapter.BondCalLsvAdapter;
import com.cvicse.stock.markets.data.DateUtil;
import com.cvicse.stock.markets.presenter.contract.BondTradingCalendarContract;
import com.cvicse.stock.pulltorefresh.PullToRefreshBase;
import com.cvicse.stock.pulltorefresh.PullToRefreshScrollView;
import com.cvicse.stock.view.ListViewForScrollView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;

/**
 * 当日新债列表
 * Created by tang_xqing on 18-4-25.
 */
public class BondNewSharesCalActivity extends PBaseActivity implements BondTradingCalendarContract.View, AdapterView.OnItemClickListener {
    @MVPInject
    BondTradingCalendarContract.Presenter presenter;

    @BindView(R.id.topbar)
    ToolBar topbar;
    @BindView(R.id.lsv_sg)
    ListViewForScrollView lsvSg;
    @BindView(R.id.tev_sg_no_data)
    TextView tevSgNoData;
    @BindView(R.id.lsv_jjsg)
    ListViewForScrollView lsvJjsg;
    @BindView(R.id.tev_jjsg_no_data)
    TextView tevJjsgNoData;
    @BindView(R.id.lsv_dss)
    ListViewForScrollView lsvDss;
    @BindView(R.id.tev_dss_no_data)
    TextView tevDssNoData;
    @BindView(R.id.pull_scl_calendar)
    PullToRefreshScrollView pullSclCalendar;

    private BondCalLsvAdapter mCalLsvAdapter;  // 申购
    private BondCalLsvAdapter mCalLsvAdapterDSG;  // 待申购
    private BondCalLsvAdapter mCalLsvAdapterDSS;  // 待上市

    private static final String BOND_DATE = "bond_date";
    private String date;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_bond_new_shares_cal;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        date =  DateUtil.DateConvert(getIntent().getStringExtra(BOND_DATE));

        mCalLsvAdapter = new BondCalLsvAdapter(this,BondCalLsvAdapter.JRSG);
        mCalLsvAdapterDSG = new BondCalLsvAdapter(this,BondCalLsvAdapter.JJSG);
        mCalLsvAdapterDSS = new BondCalLsvAdapter(this,BondCalLsvAdapter.JJSS);

        lsvSg.setAdapter(mCalLsvAdapter);
        lsvJjsg.setAdapter(mCalLsvAdapterDSG);
        lsvDss.setAdapter(mCalLsvAdapterDSS);

        lsvSg.setOnItemClickListener(this);
        lsvJjsg.setOnItemClickListener(this);
        lsvDss.setOnItemClickListener(this);

        pullSclCalendar.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ScrollView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ScrollView> refreshView) {
                presenter.requestBndNewSharesCal(date);
            }
        });
    }

    @Override
    protected void initData() {
        presenter.requestBndNewSharesCal(date);
    }

    public static void startActivity(Context context,String date){
        Intent intent = new Intent(context,BondNewSharesCalActivity.class);
        intent.putExtra(BOND_DATE,date);
        context.startActivity(intent);
    }

    /**
     * 当日新债
     * @param bndNewSharesCal
     */
    @Override
    public void onRequestBndNewSharesCalSuccess(HashMap<String, Object> bndNewSharesCal) {
        if( null == bndNewSharesCal ){
            return;
        }

        // 申购
        List<HashMap<String,Object>> sgList = (List<HashMap<String, Object>>) bndNewSharesCal.get("sglist");
        mCalLsvAdapter.setData(sgList);

        // 待申购
        List<HashMap<String,Object>> jjsglist = (List<HashMap<String, Object>>) bndNewSharesCal.get("jjsglist");
        mCalLsvAdapterDSG.setData(jjsglist);

        // 待上市
        List<HashMap<String,Object>> dsslist = (List<HashMap<String, Object>>) bndNewSharesCal.get("dsslist");
        mCalLsvAdapterDSS.setData(dsslist);

        if( null != sgList && !sgList.isEmpty()){
            tevSgNoData.setVisibility(View.GONE);
        }
        if( null != jjsglist && !jjsglist.isEmpty() ){
            tevJjsgNoData.setVisibility(View.GONE);
        }
        if( null != dsslist && !dsslist.isEmpty() ){
            tevDssNoData.setVisibility(View.GONE);
        }
    }

    @Override
    public void onRequestBndNewSharesCalFail() {

    }

    @Override
    public void onRequestBondTradingDaySuccess(List<HashMap<String, Object>> bndTradList, ArrayList<String> holidayList) {

    }

    @Override
    public void onRequestBondTradingDayFail() {

    }

    @Override
    public void onRequestBndShareDetailSuccess(HashMap<String, Object> bndShartDetail) {

    }

    @Override
    public void onRequestBndShareDetailFail() {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if(position < 1){
            return;
        }
        String tradeCode = null;
        switch (parent.getId()){
            // 申购
            case R.id.lsv_sg: tradeCode = (String) mCalLsvAdapter.getItem(position).get("TRADINGCODE"); break;
            // 待申购
            case R.id.lsv_jjsg: tradeCode = (String) mCalLsvAdapterDSG.getItem(position).get("TRADINGCODE"); break;
            // 待上市
            case R.id.lsv_dss:tradeCode = (String) mCalLsvAdapterDSS.getItem(position).get("TRADINGCODE"); break;

        }
        // 新债详情
        BondShareIPODetailActivity.startActivity(this,tradeCode);
    }
}
