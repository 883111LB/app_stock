package com.cvicse.stock.chart.helper.new_helper;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cvicse.stock.R;
import com.cvicse.stock.chart.ui.new_ui.StockMinuteFragmentNew;
import com.cvicse.stock.common.FillConfig;
import com.cvicse.stock.markets.ui.StockMoreDetailActivity;
import com.cvicse.stock.util.FormatUtils;
import com.cvicse.stock.utils.TextUtils;
import com.mitake.core.QuoteItem;
import com.mitake.core.response.OrderQuantityResponse;
import com.mitake.core.util.FormatUtility;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by liu_zlu on 2017/3/22 15:40
 * 沪深股票
 */
public class MinuteSZHlperNew {

    @BindView(R.id.tev_sell_price) TextView tevSellPrice;
    @BindView(R.id.tev_sell_volume) TextView tevSellVolume;

    @BindView(R.id.tev_buy_price) TextView tevBuyPrice;
    @BindView(R.id.tev_buy_volume) TextView tevBuyVolume;

    @BindView(R.id.tev_sell_volume_1) TextView tevSellVolume1;
    @BindView(R.id.tev_sell_volume_2) TextView tevSellVolume2;
    @BindView(R.id.tev_sell_volume_3) TextView tevSellVolume3;

    @BindView(R.id.tev_buy_volume_1) TextView tevBuyVolume1;
    @BindView(R.id.tev_buy_volume_2) TextView tevBuyVolume2;
    @BindView(R.id.tev_buy_volume_3) TextView tevBuyVolume3;

    @BindView(R.id.tev_sell_volume_4) TextView tevSellVolume4;
    @BindView(R.id.tev_sell_volume_5) TextView tevSellVolume5;
    @BindView(R.id.tev_sell_volume_6) TextView tevSellVolume6;

    @BindView(R.id.tev_buy_volume_4) TextView tevBuyVolume4;
    @BindView(R.id.tev_buy_volume_5) TextView tevBuyVolume5;
    @BindView(R.id.tev_buy_volume_6) TextView tevBuyVolume6;

    @BindView(R.id.tev_sell_volume_7) TextView tevSellVolume7;
    @BindView(R.id.tev_sell_volume_8) TextView tevSellVolume8;
    @BindView(R.id.tev_sell_volume_9) TextView tevSellVolume9;

    @BindView(R.id.tev_buy_volume_7) TextView tevBuyVolume7;
    @BindView(R.id.tev_buy_volume_8) TextView tevBuyVolume8;
    @BindView(R.id.tev_buy_volume_9) TextView tevBuyVolume9;

    private StockMinuteFragmentNew stockMinuteFragment;
//    private StockMinuteFragment stockMinuteFragment;
    private QuoteItem quoteItem;
    private ArrayList<TextView> buyTexts = new ArrayList<>();
    private ArrayList<TextView> sellTexts = new ArrayList<>();

    private ArrayList<String> buySingleVolumes;
    private ArrayList<String> sellSingleVolumes;

//    public MinuteSZHlper(LinearLayout linearLayout, StockMinuteFragment stockMinuteFragment, QuoteItem quoteItem) {
        public MinuteSZHlperNew(LinearLayout linearLayout, StockMinuteFragmentNew stockMinuteFragment, QuoteItem quoteItem) {
        this.stockMinuteFragment = stockMinuteFragment;
        View view = LayoutInflater.from(linearLayout.getContext()).inflate(R.layout.layout_sz_chart_more, linearLayout);
        ButterKnife.bind(this,view);
        initViews();
        this.quoteItem = quoteItem;
    }

    private void initViews() {
        sellTexts.add(tevSellVolume1);
        sellTexts.add(tevSellVolume2);
        sellTexts.add(tevSellVolume3);
        sellTexts.add(tevSellVolume4);
        sellTexts.add(tevSellVolume5);
        sellTexts.add(tevSellVolume6);
        sellTexts.add(tevSellVolume7);
        sellTexts.add(tevSellVolume8);
        sellTexts.add(tevSellVolume9);

        buyTexts.add(tevBuyVolume1);
        buyTexts.add(tevBuyVolume2);
        buyTexts.add(tevBuyVolume3);
        buyTexts.add(tevBuyVolume4);
        buyTexts.add(tevBuyVolume5);
        buyTexts.add(tevBuyVolume6);
        buyTexts.add(tevBuyVolume7);
        buyTexts.add(tevBuyVolume8);
        tevBuyVolume9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StockMoreDetailActivity.startActivity(stockMinuteFragment.getActivity(),quoteItem,2);
            }
        });
    }

    public void updateData(QuoteItem quoteItem){
        this.quoteItem = quoteItem;
        /*if(quoteItem == null|| quoteItem.sellVolumes== null || quoteItem.buyVolumes==null){
            return;
        }
*/
        String market = quoteItem.market;
        String subType = quoteItem.subtype;
        String sellVolume = quoteItem.sellVolumes == null || quoteItem.sellVolumes.size()==0 ?
                "-":quoteItem.sellVolumes.get(0);

        String buyVolume = quoteItem.buyVolumes == null || quoteItem.buyVolumes.size()==0 ?
                "-":quoteItem.buyVolumes.get(quoteItem.buyVolumes.size()-1);

        String sellPrice = quoteItem.sellPrices == null || quoteItem.sellPrices.size()==0 ?
                "-":quoteItem.sellPrices.get(0);

        String buyPrice = quoteItem.buyPrices == null || quoteItem.buyPrices.size()==0 ?
                "-":quoteItem.buyPrices.get(quoteItem.buyPrices.size()-1);

        //卖一价
        TextUtils.setText(tevSellPrice,sellPrice, FillConfig.SIGNLE_LINE);
        //卖一量
        if( FormatUtils.isStandard(sellVolume)){
            TextUtils.setText(tevSellVolume, FormatUtility.formatVolume(sellVolume,market,subType), FillConfig.SIGNLE_LINE);
        }
        //买一价
        TextUtils.setText(tevBuyPrice,buyPrice, FillConfig.SIGNLE_LINE);
        //买一量
        if( FormatUtils.isStandard(buyVolume) ){
            TextUtils.setText(tevBuyVolume,FormatUtility.formatVolume(buyVolume,market,subType), FillConfig.SIGNLE_LINE);
        }
/*
//       int buyCount = quoteItem.buySingleVolumes == null ? -1 :quoteItem.buySingleVolumes.size()-1;
//        int sellCount = quoteItem.sellSingleVolumes == null ? -1 :quoteItem.sellSingleVolumes.size()-1;
        int buyCount = quoteItem.buyVolumes == null ? -1 :quoteItem.buyVolumes.size()-1;
        int sellCount = quoteItem.sellVolumes == null ? -1 :quoteItem.sellVolumes.size()-1;
        for(int i = 0;i < 8       ;i++){
            if(i > buyCount){
                buyTexts.get(i).setVisibility(View.INVISIBLE);
            } else {
                buyTexts.get(i).setVisibility(View.VISIBLE);
                buyTexts.get(i).setText(quoteItem.buyVolumes.get(i));
            }

            if(i > sellCount){
                sellTexts.get(i).setVisibility(View.INVISIBLE);
            } else {
                sellTexts.get(i).setVisibility(View.VISIBLE);
                sellTexts.get(i).setText(quoteItem.sellVolumes.get(i));
            }
        }*/
    }

    /**
     * 设置买卖队列数据
     */
    public void setOrderData(OrderQuantityResponse quoteResponse){
        if(quoteResponse.buyList != null &&quoteResponse.buyList.size()>0
                && quoteResponse.buyList.get(0) != null){
            buySingleVolumes = quoteResponse.buyList.get(0).QUANTITY_;
        }
        if(quoteResponse.sellList != null &&quoteResponse.sellList.size()>0
                && quoteResponse.sellList.size() > 9 && quoteResponse.sellList.get(9) != null){
            sellSingleVolumes = quoteResponse.sellList.get(9).QUANTITY_;
        }

        int buyCount = buySingleVolumes == null ? -1 :buySingleVolumes.size()-1;
        int sellCount = sellSingleVolumes == null ? -1 :sellSingleVolumes.size()-1;
        for(int i = 0;i < 8;i++){
            if(i > buyCount){
                buyTexts.get(i).setVisibility(View.INVISIBLE);
            } else {
                buyTexts.get(i).setVisibility(View.VISIBLE);
                //buyTexts.get(i).setText(buySingleVolumes.get(i));
                buyTexts.get(i).setText(FormatUtility.formatVolume(buySingleVolumes.get(i),quoteItem.market,quoteItem.subtype));
            }

            if(i > sellCount){
                sellTexts.get(i).setVisibility(View.INVISIBLE);
            } else {
                sellTexts.get(i).setVisibility(View.VISIBLE);
                //sellTexts.get(i).setText(sellSingleVolumes.get(i));
                sellTexts.get(i).setText(FormatUtility.formatVolume(sellSingleVolumes.get(i),quoteItem.market,quoteItem.subtype));
            }
        }
    }
}
