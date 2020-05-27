package com.cvicse.stock.markets.ui.stockdetail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.cvicse.annotations.MVPInject;
import com.cvicse.stock.R;
import com.cvicse.stock.base.PBaseActivity;
import com.cvicse.stock.base.ToolBar;
import com.cvicse.stock.markets.helper.StockExptHelper;
import com.cvicse.stock.markets.helper.StockProindicHelper;
import com.cvicse.stock.markets.presenter.marketdetail.contract.StockExptProindicContract;
import com.mitake.core.request.F10Type;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;


public class StockExptProindicActivity extends PBaseActivity implements StockExptProindicContract.View {
    @MVPInject
    StockExptProindicContract.Presenter presenter;

    @BindView(R.id.topbar)
    ToolBar topbar;
    @BindView(R.id.vs_expt)
    ViewStub vsExpt;
    @BindView(R.id.vs_proindic)
    ViewStub vsProindic;

    private String mCode;
    private String mName;
    private String mApiType=F10Type.EXPT_PERFORMANCE;

    private static final String SUMMARY_CODE = "summary_code";
    private static final String SUMMARY_NAME = "summary_name";
    private static final String SUMMARY_APITYPE = "summary_apiType";

    private StockExptHelper mStockExptHelper;
    private StockProindicHelper mStockProindicHelper;

    public static void startActivity(Context context, String code, String name, String apiType) {
        Intent intent = new Intent(context, StockExptProindicActivity.class);
        intent.putExtra(SUMMARY_CODE, code);
        intent.putExtra(SUMMARY_NAME, name);
        intent.putExtra(SUMMARY_APITYPE, apiType);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_stock_summary_expt_proindic;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        mName = getIntent().getStringExtra(SUMMARY_NAME);
        mCode = getIntent().getStringExtra(SUMMARY_CODE);
        mApiType = getIntent().getStringExtra(SUMMARY_APITYPE);

        Button btnExpt= (Button) findViewById(R.id.btn_expt);
        Button btnProindic= (Button) findViewById(R.id.btn_proindic);
        btnExpt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vsExpt.setVisibility(View.VISIBLE);
                vsProindic.setVisibility(View.GONE);
                mApiType=F10Type.EXPT_PERFORMANCE;
                topbar.setTitle("年报预披露" + " - 预告");
                presenter.init(mCode);
                presenter.requestExpt(mApiType);

            }
        });
        btnProindic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vsExpt.setVisibility(View.GONE);
                vsProindic.setVisibility(View.VISIBLE);
                mApiType=F10Type.PROINDIC_DATA;
                topbar.setTitle("年报预披露" + " - 公告 ");
                presenter.init(mCode);
                presenter.requestProindic(mApiType);
            }
        });
        if (F10Type.EXPT_PERFORMANCE.equals(mApiType)){
            topbar.setTitle("年报预披露" + " - 预告");
            mStockExptHelper = new StockExptHelper(vsExpt.inflate(), this, mName);
        }else if (F10Type.PROINDIC_DATA.equals(mApiType)){
            mStockProindicHelper = new StockProindicHelper(vsProindic.inflate(), this, mName);
        }
    }

    @Override
    protected void initData() {
        presenter.init(mCode);
        if (F10Type.EXPT_PERFORMANCE.equals(mApiType)){
            presenter.requestExpt(mApiType);
        }else if (F10Type.PROINDIC_DATA.equals(mApiType)){
            presenter.requestProindic(mApiType);
        }
    }

    @Override
    public void onExptSuccess(List<HashMap<String, Object>> infoList) {
        if (null != mStockExptHelper) {
            mStockExptHelper.setExptData(infoList);
        }
    }

    @Override
    public void onProindicSuccess(List<HashMap<String, Object>> infoList) {
        if (null != mStockProindicHelper) {
            mStockProindicHelper.setProindicData(infoList);
        }
    }
}
