package com.cvicse.stock.portfolio.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.cvicse.annotations.MVPInject;
import com.cvicse.stock.R;
import com.cvicse.stock.base.PBaseActivity;
import com.cvicse.stock.base.ToolBar;
import com.cvicse.stock.portfolio.adapter.ReSearchReportAdapter;
import com.cvicse.stock.portfolio.presenter.constract.ReportConstract;
import com.cvicse.stock.pulltorefresh.PullToRefreshBase;
import com.cvicse.stock.pulltorefresh.PullToRefreshListView;
import com.mitake.core.StockReportItem;

import java.util.List;

import butterknife.BindView;

/**
 *研报页面
 */
public class ReportActivity extends PBaseActivity implements ReportConstract.View {
    @BindView(R.id.lst_my_report) PullToRefreshListView lstMyReport;
    @BindView(R.id.tev_content) TextView mTevContent;
    List<StockReportItem> list;
    ReSearchReportAdapter mReSearchReportAdapter;
    @MVPInject
    ReportConstract.Presenter presenter;
    @BindView(R.id.topbar) ToolBar mTopbar;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_myreport;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        mTopbar.setTitle("研究报告");

        mReSearchReportAdapter = new ReSearchReportAdapter(this);
        lstMyReport.setAdapter(mReSearchReportAdapter);
        lstMyReport.setMode(PullToRefreshBase.Mode.BOTH);
        lstMyReport.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                StockReportItem item = (StockReportItem) mReSearchReportAdapter.getItem(position-1);
                StockDetailActivity.actionIntent(ReportActivity.this, item.ID_,
                        item.ReportTitle, item.PUBDATE_,item.ComName,
                        StockDetailActivity.TYPE_REPORT);
            }
        });

        lstMyReport.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                presenter.requestReport();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                presenter.requestReportLoad();
            }
        });
    }

    @Override
    protected void initData() {
        presenter.requestReport();
    }


    @Override
    public void reportSuccess(Object result) {
        lstMyReport.onRefreshComplete();
        if (result != null) {
            list = (List<StockReportItem>) result;
            if (list.size() == 0) {
                showMessage();
            } else {
                mTevContent.setVisibility(View.GONE);
                lstMyReport.setVisibility(View.VISIBLE);
                mReSearchReportAdapter.setData(list);
            }
        } else {
            showMessage();
        }
        mReSearchReportAdapter.notifyDataSetChanged();
    }

    @Override
    public void reportFail() {
        lstMyReport.onRefreshComplete();
        showMessage();
    }

    /**
     * 没有返回时提示信息
     */
    public void showMessage() {
        mTevContent.setVisibility(View.VISIBLE);
        mTevContent.setText("暂无研报");
        lstMyReport.setVisibility(View.GONE);
    }

}
