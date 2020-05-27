package com.cvicse.stock.markets.ui.new_ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.widget.FrameLayout;

import com.cvicse.stock.R;
import com.cvicse.stock.base.PBaseActivity;
import com.cvicse.stock.base.ToolBar;
import com.mitake.newshare.NewShareDetailFram;
import com.mitake.newshare.widget.NewEquityDetailView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ruan_ytai on 17-3-1.
 */

public class NewStockDetailActivityNew extends PBaseActivity implements NewEquityDetailView.OnSecuAbbrClickListener {

    @BindView(R.id.topbar)
    ToolBar topbar;
    @BindView(R.id.fry_calendar)
    FrameLayout fryCalendar;

    private String tradingCode;
    private String tradingName;

    private NewShareDetailFram newShareDetailFram;
    private FragmentManager fragmentManager;

    private static final String KEY_TRADING_CODE = "tradingCode";
    private static final String KEY_TRADING_NAME = "tradingName";

    public static void actionStart(Context context, String tradingCode, String name) {
        Intent intent = new Intent(context, NewStockDetailActivityNew.class);
        intent.putExtra(KEY_TRADING_CODE, tradingCode);
        intent.putExtra(KEY_TRADING_NAME, name);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.calendar_selector_new;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {

        Intent intent = getIntent();
        tradingCode = intent.getStringExtra(KEY_TRADING_CODE);
        tradingName = intent.getStringExtra(KEY_TRADING_NAME);

        fragmentManager = getSupportFragmentManager();
        /*************注册流程 范例*********************/
//        CommonInfo.prodID = this.getString(R.string.prod_id);
//        CommonInfo.productName = this.getString(R.string.app_name);
//        CommonInfo.developShowMode = this.getString(R.string.develop_show_mode);

        intoNewShareDemo();
    }

    private void intoNewShareDemo() {
        newShareDetailFram = new NewShareDetailFram();
        newShareDetailFram.setOnSecuAbbrClickListener(this);
        Bundle bundle = new Bundle();
        bundle.putString("code",tradingCode);
        bundle.putString("name",tradingName);
        newShareDetailFram.setArguments(bundle);
        fragmentManager.beginTransaction().replace(R.id.fry_calendar, newShareDetailFram, "newShareDetailFram").addToBackStack("newShareDetailFram").commit();
    }

    @Override
    protected void initData() {
        topbar.setTitle(tradingName);
        newShareDetailFram.refreshData();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    /**
     * 点击股票名称事件监听
     * @param id
     * @param name
     * @param marketType
     * @param subType
     */
    @Override
    public void setOnSecuAbbrClick(String id, String name, String marketType, String subType) {

    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
