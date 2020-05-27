package com.cvicse.stock.markets.ui.stockdetail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.cvicse.annotations.MVPInject;
import com.cvicse.base.utils.LogUtils;
import com.cvicse.stock.R;
import com.cvicse.stock.base.PBaseActivity;
import com.cvicse.stock.base.ToolBar;
import com.cvicse.stock.markets.adapter.StockShareIinvholdchgAdapter;
import com.cvicse.stock.markets.presenter.marketdetail.contract.StockShareIinvholdchgContract;
import com.cvicse.stock.pulltorefresh.PullToRefreshBase;
import com.cvicse.stock.pulltorefresh.PullToRefreshHSListView;
import com.cvicse.stock.view.CHScrollView;
import com.cvicse.stock.view.HVListView;
import com.mitake.core.request.F10Type;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;

/** 股东深度挖掘
 * Created by tang_xqing on 2018/4/18.
 */

public class StockShareIinvholdchgActivity extends PBaseActivity implements StockShareIinvholdchgContract.View{

    @BindView(R.id.topbar)
    ToolBar mTopbar;
    @BindView(R.id.tev_title)
    TextView mTevTitle;
    @BindView(R.id.tev_empty)
    TextView mTevEmpty;
    @BindView(R.id.scroll_title)
    CHScrollView mScrollView;
    @BindView(R.id.lsv_content)
    PullToRefreshHSListView mLsvContent;

    @MVPInject
    StockShareIinvholdchgContract.Presenter presenter;

    private String mCode;
    private String mName;
    public final static  String SHARE_CODE = "share_code";
    public final static String SHARE_NAME = "share_name";
    private final static String TAG = StockShareIinvholdchgActivity.class.getSimpleName();

    private StockShareIinvholdchgAdapter mIinvholdchgAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_share_iinvholdchg;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        mCode = getIntent().getStringExtra(SHARE_CODE);
        mName = getIntent().getStringExtra(SHARE_NAME);

        mTevTitle.setText(mName);

        mLsvContent.setEmptyView(mTevEmpty);
        mLsvContent.setMode(PullToRefreshBase.Mode.BOTH_NO_REFRESH);
        HVListView hvListView = (HVListView) mLsvContent.getRefreshableView();
        hvListView.setScrollView(mScrollView);
        mIinvholdchgAdapter = new StockShareIinvholdchgAdapter(this);
        mLsvContent.setAdapter(mIinvholdchgAdapter);

        mTopbar.setToolBarClickListener(new ToolBar.ToolBarClickListener() {
            @Override
            public void onClick(View view, ToolBar.Type type) {
                switch (type){
                    case LEFT_TYPE: onBackPressed(); break;
                    default: break;
                }
            }
        });
    }

    @Override
    protected void initData() {
        presenter.requestShareIivholdchg(mCode, F10Type.IINVHOLDCHG);
    }

    public static void startActivity(Context mcontext,String code,String name){
        Intent intent = new Intent(mcontext,StockShareIinvholdchgActivity.class);
        intent.putExtra(SHARE_NAME,name);
        intent.putExtra(SHARE_CODE,code);
        mcontext.startActivity(intent);
    }

    @Override
    public void requestShareIivholdchgSuccesss(List<HashMap<String, Object>> shareIivholdchg) {
        if( null == shareIivholdchg || shareIivholdchg.isEmpty() ){
            return;
        }
        mIinvholdchgAdapter.setData(shareIivholdchg);
    }

    @Override
    public void requestShareIivholdchgFail(int code, String msg) {
        LogUtils.e(TAG +"  mCode ="+code +"  msg="+msg);
    }
}
