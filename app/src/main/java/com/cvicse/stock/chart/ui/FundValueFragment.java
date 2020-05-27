package com.cvicse.stock.chart.ui;

import android.os.Bundle;

import com.cvicse.annotations.MVPInject;
import com.cvicse.stock.R;
import com.cvicse.stock.base.ChartBaseFragment;
import com.cvicse.stock.chart.listener.OnChartListener;
import com.cvicse.stock.chart.presenter.contract.FundValueContract;
import com.cvicse.stock.markets.ui.StockDetailLandActivity;
import com.mitake.core.FundValueInfo;
import com.mitake.core.QuoteItem;

/**
 * 基金净值
 * Created by liu_zlu on 2017/4/28 14:47
 */
public class FundValueFragment extends ChartBaseFragment implements FundValueContract.View{
    private static final String QUOTEITEM = "QuoteItem";
    private FundValueChart fundvalueChart;


    @MVPInject
    FundValueContract.Presenter presenter;

    public static FundValueFragment newInstance(QuoteItem quoteItem) {
        FundValueFragment fundValueFragment = new FundValueFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(QUOTEITEM,quoteItem);
        fundValueFragment.setArguments(bundle);
        return fundValueFragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_stock_fundvalue_chart;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {

        presenter.init((QuoteItem) getArguments().getParcelable(QUOTEITEM));

        fundvalueChart = (FundValueChart) getView().findViewById(R.id.fundvalue_chart);
        fundvalueChart.setLand(isLand);

        fundvalueChart.setOnChartListener(new OnChartListener() {
            @Override
            public void onChangeLanded() {
                StockDetailLandActivity.startActivity(getActivity(),(QuoteItem) getArguments().getParcelable(QUOTEITEM),9);
            }
        });
    }

    @Override
    protected void initData() {

    }

    /**
     * 请求基金净值成功
     *
     * @param fundValueInfo
     */
    @Override
    public void onRequestFundValueSuccess(FundValueInfo fundValueInfo) {
        fundvalueChart.setRequestData(fundValueInfo);
    }

    /**
     * 请求基金净值失败
     */
    @Override
    public void onRequestFundValueFail() {

    }
}
