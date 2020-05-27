package com.cvicse.stock.markets.ui;

import android.os.Bundle;
import android.widget.TextView;

import com.cvicse.annotations.MVPInject;
import com.cvicse.base.widget.FixedListView;
import com.cvicse.stock.R;
import com.cvicse.stock.base.PBaseFragment;
import com.cvicse.stock.markets.adapter.StockDelegateAdapter;
import com.cvicse.stock.markets.presenter.contract.StockDelegateContract;
import com.cvicse.stock.utils.TextUtils;
import com.cvicse.stock.utils.UpDownUtils;
import com.mitake.core.QuoteItem;
import com.mitake.core.response.OrderQuantityResponse;
import com.mitake.core.response.QuoteResponse;

import butterknife.BindView;

/**
 * 委托队列
 * Created by liuzilu on 2017/3/6.
 */

public class StockDelegateFragment extends PBaseFragment implements StockDelegateContract.View{
    private static final String QUOTEITEM = "QuoteItem";
    @BindView(R.id.tev_sell_flag) TextView tevSellFlag;
    //卖一价格
    @BindView(R.id.tev_sell_price) TextView tevSellPrice;
    //卖一量
    @BindView(R.id.tev_sell_volume) TextView tevSellVolume;
    @BindView(R.id.tev_buy_flag) TextView tevBuyFlag;
    //买一价格
    @BindView(R.id.tev_buy_price) TextView tevBuyPrice;
    //买一量
    @BindView(R.id.tev_buy_volume) TextView tevBuyVolume;
    @BindView(R.id.flv_detegate) FixedListView flvDetegate;
    private StockDelegateAdapter stockDelegateAdapter;

    @MVPInject
    StockDelegateContract.Presenter presenter;

    public static StockDelegateFragment newInstance(QuoteItem quoteItem) {
        StockDelegateFragment stockDelegateFragment = new StockDelegateFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(QUOTEITEM, quoteItem);
        stockDelegateFragment.setArguments(bundle);
        return stockDelegateFragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_stock_more_delegate;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        QuoteItem item = getArguments().getParcelable(QUOTEITEM);
        presenter.init(item);
        stockDelegateAdapter = new StockDelegateAdapter(getActivity(),item);
        flvDetegate.setAdapter(stockDelegateAdapter);
    }

    @Override
    protected void initData() {

    }

    /**
     * 返回买卖队列
     *
     * @param quoteResponse
     */
    @Override
    public void onOrderQuantitySuccess(OrderQuantityResponse quoteResponse) {
       if(quoteResponse != null){
           stockDelegateAdapter.setData(quoteResponse);
       }
    }

    /**
     * 返回股票快照行情
     *
     * @param quoteResponse
     */
    @Override
    public void onQuoteSuccess(QuoteResponse quoteResponse) {
        QuoteItem quoteItem = quoteResponse.quoteItems.get(0);
        if(quoteItem == null) return;

//        String buyPrice = quoteItem.buyPrice;  // old
//        String sellPrice = quoteItem.sellPrice;
        //五档/十档 买量
        String buyVolume = quoteItem.buyVolumes == null|| quoteItem.buyVolumes.size()==0 ? "一":quoteItem.buyVolumes.get(quoteItem.buyVolumes.size()-1);
        String buyPrice = quoteItem.buyPrices == null|| quoteItem.buyPrices.size()==0 ? "一":quoteItem.buyPrices.get(quoteItem.buyPrices.size()-1);  // new

        //五档/十档 卖量
        String sellVolume = quoteItem.sellVolumes == null|| quoteItem.sellVolumes.size()==0 ? "一":quoteItem.sellVolumes.get(0);
        String sellPrice = quoteItem.sellPrices == null|| quoteItem.sellPrices.size()==0 ? "一":quoteItem.sellPrices.get(0);  // new

        String market = quoteItem.market;
        String subType = quoteItem.subtype;

   /*     if(FormatUtils.isStandard(sellVolume)){   // old
            TextUtils.setText(tevSellVolume, FormatUtility.formatVolume(sellVolume,market,subType), FillConfig.SIGNLE_LINE);
        }*/
//        TextUtils.setText(tevBuyVolume,FormatUtility.formatVolume(buyVolume,market,subType), FillConfig.SIGNLE_LINE);  // old

        TextUtils.setText(tevSellPrice,sellPrice);
        TextUtils.setText(tevSellVolume, sellVolume);

        TextUtils.setText(tevBuyPrice,buyPrice);
        TextUtils.setText(tevBuyVolume,buyVolume);  // new

        UpDownUtils.compare(quoteItem.preClosePrice,buyPrice,tevBuyPrice);
        UpDownUtils.compare(quoteItem.preClosePrice,sellPrice,tevSellPrice);
    }
}
