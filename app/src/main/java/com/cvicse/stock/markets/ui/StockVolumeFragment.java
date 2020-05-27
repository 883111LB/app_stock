package com.cvicse.stock.markets.ui;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.cvicse.annotations.MVPInject;
import com.cvicse.stock.R;
import com.cvicse.stock.base.PBaseFragment;
import com.cvicse.stock.markets.adapter.StockVolumeAdapter;
import com.cvicse.stock.markets.presenter.contract.StockVolumeContract;
import com.cvicse.stock.pulltorefresh.PullToRefreshBase;
import com.cvicse.stock.pulltorefresh.PullToRefreshListView;
import com.cvicse.stock.view.HVListView;
import com.mitake.core.QuoteItem;
import com.mitake.core.bean.log.ErrorInfo;
import com.mitake.core.request.MoreVolumeRequest;
import com.mitake.core.response.IResponseInfoCallback;
import com.mitake.core.response.MoreVolumeResponse;

import butterknife.BindView;

/**
 * 分量
 * Created by liuzilu on 2017/3/6.
 */

public class StockVolumeFragment extends PBaseFragment implements StockVolumeContract.View {

    private static final String QUOTEITEM = "QuoteItem";

    @MVPInject
    StockVolumeContract.Presenter presenter;

    @BindView(R.id.lsv_content)
    PullToRefreshListView lsv_content;

    StockVolumeAdapter adapter;
    QuoteItem quoteItem;

    private String[] qj1 = {
            "0-10", "10-20", "20-30", "30-50", "50-70", "70-100",
            "100-150", "150-200", "200-300", "300-500", "500-700", "700-1000",
            "1000-1500", "1500-2000", "2000-3000", "3000-5000", "5000-7000", "7000-10000",
            "10000-15000", "15000-20000", "20000-30000", "30000-50000", "50000-70000", "70000以上",
    };
    private String[] qj2 = {
            "0-1", "1-2", "2-3", "3-5", "5-7", "7-10",
            "10-15", "15-20", "20-30", "30-50", "50-70", "70-100",
            "100-150", "150-200", "200-300", "300-500", "500-700", "700-1000",
            "1000-1500", "1500-2000", "2000-3000", "3000-5000", "5000-7000", "7000以上",
    };

    private String[] qj;


    public static StockVolumeFragment newInstance(QuoteItem quoteItem) {
        StockVolumeFragment sharPriceFragment = new StockVolumeFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(QUOTEITEM, quoteItem);
        sharPriceFragment.setArguments(bundle);
        return sharPriceFragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_stock_volume;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        quoteItem = getArguments().getParcelable(QUOTEITEM);

        if ("sh".equals(quoteItem.market) && ("1300".equals(quoteItem.subtype) || "1311".equals(quoteItem.subtype))) {
            qj = qj1;
        } else {
            qj = qj2;
        }
        lsv_content.setMode(PullToRefreshBase.Mode.PULL_FROM_START);// 设置只允许下拉刷新
        adapter = new StockVolumeAdapter(getContext());
        lsv_content.setAdapter(adapter);
        lsv_content.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                // 下拉刷新
                presenter.moreVolumeRequest(quoteItem.id, quoteItem.subtype);
            }
        });
    }

    @Override
    protected void initData() {
//        presenter.queryMorePrice();
        presenter.moreVolumeRequest(quoteItem.id, quoteItem.subtype);
    }

    /**
     * 分量列表请求成功
     * @param volumes 成交量
     * @param buyVolumes 买量
     * @param sellVolumes 卖量
     */
    @Override
    public void moreVolumeSuccess(String[] volumes, String[] buyVolumes, String[] sellVolumes) {
        lsv_content.onRefreshComplete();
        if (null != volumes && null != buyVolumes && null != sellVolumes)
            adapter.setData(qj, volumes, buyVolumes, sellVolumes);
    }
}
