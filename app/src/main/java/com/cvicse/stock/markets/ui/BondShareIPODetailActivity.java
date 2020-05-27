package com.cvicse.stock.markets.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cvicse.annotations.MVPInject;
import com.cvicse.stock.R;
import com.cvicse.stock.base.PBaseActivity;
import com.cvicse.stock.base.ToolBar;
import com.cvicse.stock.markets.presenter.contract.BondTradingCalendarContract;
import com.cvicse.stock.utils.TextUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;

/**
 * 新债详情
 * Created by tang_xqing on 18-4-25.
 */
public class BondShareIPODetailActivity extends PBaseActivity implements BondTradingCalendarContract.View{

    @MVPInject
    BondTradingCalendarContract.Presenter presenter;

    @BindView(R.id.topbar)
    ToolBar topbar;
    @BindView(R.id.lly_sgjc)
    LinearLayout llySgjc;
//    @BindView(R.id.lly_fxzl)
//    LinearLayout llyFxzl;

    private static final String BOND_DETAIL_CODE = "bond_detail_code";
    private String mCode;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_bond_share_ipodetail;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        mCode = getIntent().getStringExtra(BOND_DETAIL_CODE);
    }

    @Override
    protected void initData() {
        presenter.requestBndShareDetail(mCode);
    }

    public static void startActivity(Context context,String code){
        Intent intent = new Intent(context,BondShareIPODetailActivity.class);
        intent.putExtra(BOND_DETAIL_CODE,code);
        context.startActivity(intent);
    }

    /**
     * 新股详情
     * @param bndShartDetail
     */
    @Override
    public void onRequestBndShareDetailSuccess(HashMap<String, Object> bndShartDetail) {
        if( null == bndShartDetail ){
            return;
        }
        topbar.setTitle((CharSequence) bndShartDetail.get("PREFERREDPLACINGNAME"));
        addView("申购代码", (String) bndShartDetail.get("APPLYCODE"));
        addView("债券代码", (String) bndShartDetail.get("TRADINGCODE"));
        addView("正股代码", (String) bndShartDetail.get("STOCKTRADINGCODE"));
        addView("正股名称", (String) bndShartDetail.get("STOCKSECUABBR"));
        addView("发行价格", (String) bndShartDetail.get("ISSUEPRICE"));
        addView("初始转股价格", (String) bndShartDetail.get("CONVERTPRICE"));
        addView("配售代码", (String) bndShartDetail.get("PREFERREDPLACINGCODE"));
        addView("配售简称", (String) bndShartDetail.get("PREFERREDPLACINGNAME"));
        addView("信用评级", (String) bndShartDetail.get("ISSUERRATING"));
        addView("债券发行总额", (String) bndShartDetail.get("ISSUEVAL"));
        addView("利率", (String) bndShartDetail.get("INTERESTTERM"));
        addView("申购上限", (String) bndShartDetail.get("CAPPLYVOL"));
        addView("申购日期", (String) bndShartDetail.get("BOOKSTARTDATEON"));
        addView("中签公告日", (String) bndShartDetail.get("SUCCRESULTNOTICEDATE"));
        addView("上市日期", (String) bndShartDetail.get("LISTINGDATE"));
        addView("中签率", (String) bndShartDetail.get("ALLOTRATEON"));
    }

    private void addView(String title,String value){
        ViewGroup viewGroup = (ViewGroup) LayoutInflater.from(this).inflate(R.layout.item_stock_detail,llySgjc,false);
        TextUtils.setText((TextView)viewGroup.findViewById(R.id.tev_title),title);
        TextUtils.setText((TextView)viewGroup.findViewById(R.id.tev_data),value);
        llySgjc.addView(viewGroup);
    }

    @Override
    public void onRequestBndShareDetailFail() {

    }

    @Override
    public void onRequestBondTradingDaySuccess(List<HashMap<String, Object>> bndTradList, ArrayList<String> holidayList) {

    }

    @Override
    public void onRequestBondTradingDayFail() {

    }

    @Override
    public void onRequestBndNewSharesCalSuccess(HashMap<String, Object> bndNewSharesCal) {

    }

    @Override
    public void onRequestBndNewSharesCalFail() {

    }

}
