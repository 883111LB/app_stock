package com.cvicse.stock.markets.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.cvicse.annotations.MVPInject;
import com.cvicse.stock.R;
import com.cvicse.stock.base.PBaseFragment;
import com.cvicse.stock.markets.adapter.StockBrokerInfoAdapter;
import com.cvicse.stock.markets.presenter.contract.StockTenContract;
import com.cvicse.stock.pulltorefresh.PullToRefreshListView;
import com.cvicse.stock.utils.TextUtils;
import com.mitake.core.QuoteItem;
import com.mitake.core.response.QuoteResponse;

import butterknife.BindView;

/**
 * 经纪席位页面
 * Created by liu_zlu on 2017/3/24 13:27
 */
public class StockBrokerInfoFragment extends PBaseFragment implements StockTenContract.View{
    private static final String QUOTEITEM = "QuoteItem";

    public static StockBrokerInfoFragment newInstance(QuoteItem quoteItem) {
        StockBrokerInfoFragment stockBrokerInfoFragment = new StockBrokerInfoFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(QUOTEITEM, quoteItem);
        stockBrokerInfoFragment.setArguments(bundle);
        return stockBrokerInfoFragment;
    }
    //卖盘价
    TextView tevSellPrice;
    //卖盘量
    TextView tevSellVolume;
    //买盘价
    TextView tevBuyPrice;
    //买盘量
    TextView tevBuyVolume;
    @BindView(R.id.flv_brokerinfo) PullToRefreshListView flvBrokerinfo;
    private StockBrokerInfoAdapter stockBrokerInfoAdapter;
    @MVPInject
    StockTenContract.Presenter presenter;
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_stock_more_brokerinfo;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        presenter.init((QuoteItem) getArguments().getParcelable(QUOTEITEM));
        View header = LayoutInflater.from(getActivity()).inflate(R.layout.layout_header_brokerinfo,flvBrokerinfo.getRefreshableView(),false);
        flvBrokerinfo.getRefreshableView().addHeaderView(header);
        tevSellPrice = (TextView) header.findViewById(R.id.tev_sell_price);
        tevSellVolume = (TextView) header.findViewById(R.id.tev_sell_volume);
        tevBuyPrice = (TextView) header.findViewById(R.id.tev_buy_price);
        tevBuyVolume = (TextView) header.findViewById(R.id.tev_buy_volume);
        stockBrokerInfoAdapter = new StockBrokerInfoAdapter(getActivity());
        flvBrokerinfo.setAdapter(stockBrokerInfoAdapter);
    }

    @Override
    protected void initData() {

    }

    /**
     * 返回经纪席位页面
     *
     * @param quoteResponse
     */
    @Override
    public void onQuoteSuccess(QuoteResponse quoteResponse) {
        stockBrokerInfoAdapter.setData(quoteResponse);
        QuoteItem quoteItem = quoteResponse.quoteItems.get(0);
        if(quoteItem == null) return;

        // new
        //五档/十档 买量
        String buyVolume = quoteItem.buyVolumes == null|| quoteItem.buyVolumes.size()==0 ? "一":quoteItem.buyVolumes.get(quoteItem.buyVolumes.size()-1);
        String buyPrice = quoteItem.buyPrices == null|| quoteItem.buyPrices.size()==0 ? "一":quoteItem.buyPrices.get(quoteItem.buyPrices.size()-1);

        //五档/十档 卖量
        String sellVolume = quoteItem.sellVolumes == null|| quoteItem.sellVolumes.size()==0 ? "一":quoteItem.sellVolumes.get(0);
        String sellPrice = quoteItem.sellPrices == null|| quoteItem.sellPrices.size()==0 ? "一":quoteItem.sellPrices.get(0);

        TextUtils.setText(tevSellPrice,sellPrice);
        TextUtils.setText(tevSellVolume, sellVolume);
        TextUtils.setText(tevBuyPrice,buyPrice);
        TextUtils.setText(tevBuyVolume,buyVolume);
    }
}
