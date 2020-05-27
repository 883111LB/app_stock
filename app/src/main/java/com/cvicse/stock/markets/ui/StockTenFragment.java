package com.cvicse.stock.markets.ui;

import android.os.Bundle;
import android.widget.TextView;

import com.cvicse.annotations.MVPInject;
import com.cvicse.stock.R;
import com.cvicse.stock.base.PBaseFragment;
import com.cvicse.stock.common.FillConfig;
import com.cvicse.stock.markets.presenter.contract.StockTenContract;
import com.cvicse.stock.pulltorefresh.PullToRefreshScrollView;
import com.cvicse.stock.utils.TextUtils;
import com.cvicse.stock.utils.UpDownUtils;
import com.mitake.core.QuoteItem;
import com.mitake.core.response.QuoteResponse;
import com.mitake.core.util.FormatUtility;

import java.util.ArrayList;
import java.util.Collections;

import butterknife.BindView;

/**
 * 十档详细页面
 * Created by liuzilu on 2017/3/6.
 */

public class StockTenFragment extends PBaseFragment implements StockTenContract.View{

    private static final String QUOTEITEM = "QuoteItem";
    @BindView(R.id.psv_stock_more_ten) PullToRefreshScrollView psvStockMoreTen;
    @BindView(R.id.tev_sell_price_1) TextView tevSellPrice1;
    @BindView(R.id.tev_sell_volume_1) TextView tevSellVolume1;
    @BindView(R.id.tev_buy_price_1) TextView tevBuyPrice1;
    @BindView(R.id.tev_buy_volume_1) TextView tevBuyVolume1;
    @BindView(R.id.tev_sell_price_2) TextView tevSellPrice2;
    @BindView(R.id.tev_sell_volume_2) TextView tevSellVolume2;
    @BindView(R.id.tev_buy_price_2) TextView tevBuyPrice2;
    @BindView(R.id.tev_buy_volume_2) TextView tevBuyVolume2;
    @BindView(R.id.tev_sell_price_3) TextView tevSellPrice3;
    @BindView(R.id.tev_sell_volume_3) TextView tevSellVolume3;
    @BindView(R.id.tev_buy_price_3) TextView tevBuyPrice3;
    @BindView(R.id.tev_buy_volume_3) TextView tevBuyVolume3;
    @BindView(R.id.tev_buy_price_4) TextView tevBuyPrice4;
    @BindView(R.id.tev_buy_volume_4) TextView tevBuyVolume4;
    @BindView(R.id.tev_sell_price_4) TextView tevSellPrice4;
    @BindView(R.id.tev_sell_volume_4) TextView tevSellVolume4;
    @BindView(R.id.tev_sell_price_5) TextView tevSellPrice5;
    @BindView(R.id.tev_sell_volume_5) TextView tevSellVolume5;
    @BindView(R.id.tev_buy_price_5) TextView tevBuyPrice5;
    @BindView(R.id.tev_buy_volume_5) TextView tevBuyVolume5;
    @BindView(R.id.tev_sell_price_6) TextView tevSellPrice6;
    @BindView(R.id.tev_sell_volume_6) TextView tevSellVolume6;
    @BindView(R.id.tev_buy_price_6) TextView tevBuyPrice6;
    @BindView(R.id.tev_buy_volume_6) TextView tevBuyVolume6;
    @BindView(R.id.tev_sell_price_7) TextView tevSellPrice7;
    @BindView(R.id.tev_sell_volume_7) TextView tevSellVolume7;
    @BindView(R.id.tev_buy_price_7) TextView tevBuyPrice7;
    @BindView(R.id.tev_buy_volume_7) TextView tevBuyVolume7;
    @BindView(R.id.tev_sell_price_8) TextView tevSellPrice8;
    @BindView(R.id.tev_sell_volume_8) TextView tevSellVolume8;
    @BindView(R.id.tev_buy_price_8) TextView tevBuyPrice8;
    @BindView(R.id.tev_buy_volume_8) TextView tevBuyVolume8;
    @BindView(R.id.tev_sell_price_9) TextView tevSellPrice9;
    @BindView(R.id.tev_sell_volume_9) TextView tevSellVolume9;
    @BindView(R.id.tev_buy_price_9) TextView tevBuyPrice9;
    @BindView(R.id.tev_buy_volume_9) TextView tevBuyVolume9;
    @BindView(R.id.tev_sell_price_10) TextView tevSellPrice10;
    @BindView(R.id.tev_sell_volume_10) TextView tevSellVolume10;
    @BindView(R.id.tev_buy_price_10) TextView tevBuyPrice10;
    @BindView(R.id.tev_buy_volume_10) TextView tevBuyVolume10;
    @BindView(R.id.tev_sell_volume_sum) TextView tevSellVolumeSum;
    @BindView(R.id.tev_buy_volume_sum) TextView tevBuyVolumeSum;
    private TextView[] buyPrices = new TextView[10];
    private TextView[] buyVolumes = new TextView[10];
    private TextView[] sellPrices = new TextView[10];
    private TextView[] sellVolumes = new TextView[10];

    @MVPInject
    StockTenContract.Presenter presenter;

    public static StockTenFragment newInstance(QuoteItem quoteItem) {
        StockTenFragment stockTenFragment = new StockTenFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(QUOTEITEM, quoteItem);
        stockTenFragment.setArguments(bundle);
        return stockTenFragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_stock_more_ten;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        buyPrices[0] = tevBuyPrice1;
        buyPrices[1] = tevBuyPrice2;
        buyPrices[2] = tevBuyPrice3;
        buyPrices[3] = tevBuyPrice4;
        buyPrices[4] = tevBuyPrice5;
        buyPrices[5] = tevBuyPrice6;
        buyPrices[6] = tevBuyPrice7;
        buyPrices[7] = tevBuyPrice8;
        buyPrices[8] = tevBuyPrice9;
        buyPrices[9] = tevBuyPrice10;
        buyVolumes[0] = tevBuyVolume1;
        buyVolumes[1] = tevBuyVolume2;
        buyVolumes[2] = tevBuyVolume3;
        buyVolumes[3] = tevBuyVolume4;
        buyVolumes[4] = tevBuyVolume5;
        buyVolumes[5] = tevBuyVolume6;
        buyVolumes[6] = tevBuyVolume7;
        buyVolumes[7] = tevBuyVolume8;
        buyVolumes[8] = tevBuyVolume9;
        buyVolumes[9] = tevBuyVolume10;
        sellPrices[0] = tevSellPrice1;
        sellPrices[1] = tevSellPrice2;
        sellPrices[2] = tevSellPrice3;
        sellPrices[3] = tevSellPrice4;
        sellPrices[4] = tevSellPrice5;
        sellPrices[5] = tevSellPrice6;
        sellPrices[6] = tevSellPrice7;
        sellPrices[7] = tevSellPrice8;
        sellPrices[8] = tevSellPrice9;
        sellPrices[9] = tevSellPrice10;
        sellVolumes[0] = tevSellVolume1;
        sellVolumes[1] = tevSellVolume2;
        sellVolumes[2] = tevSellVolume3;
        sellVolumes[3] = tevSellVolume4;
        sellVolumes[4] = tevSellVolume5;
        sellVolumes[5] = tevSellVolume6;
        sellVolumes[6] = tevSellVolume7;
        sellVolumes[7] = tevSellVolume8;
        sellVolumes[8] = tevSellVolume9;
        sellVolumes[9] = tevSellVolume10;
        presenter.init((QuoteItem) getArguments().getParcelable(QUOTEITEM));
    }

    @Override
    protected void initData() {

    }

    /**
     * 返回股票快照行情
     *
     */
    @Override
    public void onQuoteSuccess(QuoteResponse quoteResponse) {
        QuoteItem quoteItem = quoteResponse.quoteItems.get(0);
        if(null ==quoteItem )return;
        updateView(quoteItem);
    }

    private void updateView(QuoteItem quoteItem){
        ArrayList<String> buyPriceList =  quoteItem.buyPrices;
        if( null!= buyPriceList){
            Collections.reverse(buyPriceList);
            String buyPrice;
            for(int i = 0;i < buyPriceList.size();i++){
                buyPrice = buyPriceList.get(i);
                TextUtils.setText(buyPrices[i],buyPrice);
                UpDownUtils.compare(quoteItem.preClosePrice,buyPrice,buyPrices[i]);
            }
        }

        ArrayList<String> buyVolumeList =  quoteItem.buyVolumes;
        if( null!= buyVolumeList){
            Collections.reverse(buyVolumeList);
            String buyVolume;
            for(int i = 0;i < buyVolumeList.size();i++){
                buyVolume = buyVolumeList.get(i);
                TextUtils.setText(buyVolumes[i], FormatUtility.formatVolume(buyVolume,quoteItem.market,quoteItem.subtype));
            }
        }


        ArrayList<String> sellPriceList =  quoteItem.sellPrices;
        if( null!= sellPriceList){
            String sellPrice;
            for(int i = 0;i < sellPriceList.size();i++){
                sellPrice = sellPriceList.get(i);
                TextUtils.setText(sellPrices[i],sellPrice);
                UpDownUtils.compare(quoteItem.preClosePrice,sellPrice,sellPrices[i]);
            }
        }

        ArrayList<String> sellVolumeList =  quoteItem.sellVolumes;
        if(null != sellVolumeList){
            String sellVolume;
            for(int i = 0;i < sellVolumeList.size();i++){
                sellVolume = sellVolumeList.get(i);
                TextUtils.setText(sellVolumes[i],FormatUtility.formatVolume(sellVolume,quoteItem.market,quoteItem.subtype));
            }
        }
        if(FillConfig.EMPTY.equals(sumVolume(sellVolumeList)) ||
           FillConfig.SIGNLE_LINE.equals(sumVolume(sellVolumeList))|| FillConfig.DEFALUT.equals(sumVolume(sellVolumeList))||
           FillConfig.DOUBLE_LINE.equals(sumVolume(sellVolumeList))||FillConfig.NULL.equals(sumVolume(sellVolumeList))){
            tevSellVolumeSum.setText( FillConfig.DEFALUT);
        }else{
            TextUtils.setText(tevSellVolumeSum, FormatUtility.formatVolume(sumVolume(sellVolumeList),quoteItem.market,quoteItem.subtype));
        }

        if( FillConfig.EMPTY.equals(sumVolume(buyVolumeList)) ||
                FillConfig.SIGNLE_LINE.equals(sumVolume(buyVolumeList))|| FillConfig.DEFALUT.equals(sumVolume(buyVolumeList))||
                FillConfig.DOUBLE_LINE.equals(sumVolume(buyVolumeList))||FillConfig.NULL.equals(sumVolume(buyVolumeList))){
            tevSellVolumeSum.setText( FillConfig.DEFALUT);
        }else{
            TextUtils.setText(tevBuyVolumeSum, FormatUtility.formatVolume(sumVolume(buyVolumeList),quoteItem.market,quoteItem.subtype));
        }
    }

    private String sumVolume(ArrayList<String> arrayList){
        if(arrayList == null){
            return FillConfig.SIGNLE_LINE;
        }
        double sum = 0;
        for(String temp:arrayList){
            if(android.text.TextUtils.isEmpty(temp)|| FillConfig.EMPTY.equals(temp) ||
                    FillConfig.SIGNLE_LINE.equals(temp)|| FillConfig.DEFALUT.equals(temp)||
                    FillConfig.DOUBLE_LINE.equals(temp)||FillConfig.NULL.equals(temp)){
                continue;
            }
            if(temp.contains("万")){
                sum = (int) (sum+Double.parseDouble(temp.substring(0,temp.length()-1))*10000);
            } else {
                sum = sum+Double.parseDouble(temp);
            }
        }
        if(sum == 0){
            return FillConfig.DEFALUT;
        }
        return sum+"";
    }
}
