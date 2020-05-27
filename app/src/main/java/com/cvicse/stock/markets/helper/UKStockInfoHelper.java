package com.cvicse.stock.markets.helper;

import android.view.View;
import android.widget.TextView;

import com.cvicse.stock.R;
import com.cvicse.stock.utils.TextUtils;
import com.mitake.core.QuoteItem;
import com.mitake.core.bean.UKQuoteItem;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by shi_yhui on 2018/11/26.
 */

public class UKStockInfoHelper {


    //收盘买一价
    @BindView(R.id.tev_closeBuyPrice)
    TextView tevCloseBuyPrice;
    //收盘卖一价
    @BindView(R.id.tev_closeSellPrice)
    TextView tevCloseSellPrice;
    //日内最高价(自动交易)
    @BindView(R.id.tev_highPriceDayAuto)
    TextView tevHighPriceDayAuto;
    //日内最高价(非自动交易)
    @BindView(R.id.tev_highPriceDayNonAuto)
    TextView tevHighPriceDayNonAuto;
    //日内最低价(自动交易)
    @BindView(R.id.tev_lowPriceDayAuto)
    TextView tevLowPriceDayAuto;
    //日内最低价(非自动交易)
    @BindView(R.id.tev_lowPriceDayNonAuto)
    TextView tevLowPriceDayNonAuto;
    //近一年来最高价(自动交易)
    @BindView(R.id.tev_highPriceYearAuto)
    TextView tevHighPriceYearAuto;
    //近一年来最高价(非自动交易)
    @BindView(R.id.tev_highPriceYearNonAuto)
    TextView tevHighPriceYearNonAuto;
    //近一年来最低价(自动交易)
    @BindView(R.id.tev_lowPriceYearAuto)
    TextView tevLowPriceYearAuto;
    //近一年来最低价(非自动交易)
    @BindView(R.id.tev_lowPriceYearNonAuto)
    TextView tevLowPriceYearNonAuto;
    //近一年来最高价出现日期(自动交易)
    @BindView(R.id.tev_highPriceTimeYearAuto)
    TextView tevHighPriceTimeYearAuto;
    //近一年来最高价出现日期(非自动交易)
    @BindView(R.id.tev_highPriceTimeYearNonAuto)
    TextView tevHighPriceTimeYearNonAuto;
    //近一年来最低价出现日期(自动交易)
    @BindView(R.id.tev_lowPriceTimeYearAuto)
    TextView tevLowPriceTimeYearAuto;
    //近一年来最低价出现日期(非自动交易)
    @BindView(R.id.tev_lowPriceTimeYearNonAuto)
    TextView tevLowPriceTimeYearNonAuto;
    //成交笔数
    @BindView(R.id.tev_transactionNumber)
    TextView tevTransactionNumber;
    //币种
    @BindView(R.id.tev_currency)
    TextView tevCurrency;

    private QuoteItem quoteItem;

    public UKStockInfoHelper(View view, QuoteItem quoteItem){
        ButterKnife.bind(this, view);
        this.quoteItem=quoteItem;
    }

    public void onUKStockInfoSuccess(List<UKQuoteItem> ukQuoteItems) {
//        System.out.println(quoteItem.id+"66666666666666666666666666");
        for (int i=0;i<ukQuoteItems.size();i++){
//            System.out.println(ukQuoteItems.get(i).code+"1111111111111");
//            System.out.println(ukQuoteItems.get(0).code+"555555555555555555");
            if (ukQuoteItems.get(0).code.equals(quoteItem.id)){
//                System.out.println(ukQuoteItems.get(0).code+"222222");
                TextUtils.setText(tevCloseSellPrice,ukQuoteItems.get(0).closeBuyPrice,"--");
                TextUtils.setText(tevCloseBuyPrice,ukQuoteItems.get(0).closeSellPrice,"--");
                TextUtils.setText(tevHighPriceDayAuto,ukQuoteItems.get(0).highPriceDayAuto,"--");
                TextUtils.setText(tevHighPriceDayNonAuto,ukQuoteItems.get(0).highPriceDayNonAuto,"--");

                TextUtils.setText(tevLowPriceDayAuto,ukQuoteItems.get(0).lowPriceDayAuto,"--");
                TextUtils.setText(tevLowPriceDayNonAuto,ukQuoteItems.get(0).lowPriceDayNonAuto,"--");
                TextUtils.setText(tevHighPriceYearAuto,ukQuoteItems.get(0).highPriceYearAuto);
                TextUtils.setText(tevHighPriceYearNonAuto,ukQuoteItems.get(0).highPriceYearNonAuto,"--");

                TextUtils.setText(tevLowPriceYearAuto,ukQuoteItems.get(0).lowPriceYearAuto,"--");
                TextUtils.setText(tevLowPriceYearNonAuto,ukQuoteItems.get(0).lowPriceYearNonAuto,"--");
                //时间日期格式化
                if(ukQuoteItems.get(0).highPriceTimeYearAuto != null){
                    String timestamp = ukQuoteItems.get(0).highPriceTimeYearAuto;
                    timestamp = timestamp.substring(0,4)+"-"+timestamp.substring(4,6)+"-"
                            + timestamp.substring(6,8);
                    tevHighPriceTimeYearAuto.setText(timestamp);
                }else{
                    TextUtils.setText(tevHighPriceTimeYearAuto,ukQuoteItems.get(0).highPriceTimeYearAuto,"--");
                }
//            System.out.println(ukQuoteItems.get(0).highPriceTimeYearAuto+"44444444444444");
                if(ukQuoteItems.get(0).highPriceTimeYearNonAuto != null){
                    String timestamp = ukQuoteItems.get(0).highPriceTimeYearNonAuto;
                    timestamp = timestamp.substring(0,4)+"-"+timestamp.substring(4,6)+"-"
                            + timestamp.substring(6,8);
                    tevHighPriceTimeYearNonAuto.setText(timestamp);
                }else{
                    TextUtils.setText(tevHighPriceTimeYearNonAuto,ukQuoteItems.get(0).highPriceTimeYearNonAuto,"--");
                }
                if(ukQuoteItems.get(0).lowPriceTimeYearAuto != null){
                    String timestamp = ukQuoteItems.get(0).lowPriceTimeYearAuto;
                    timestamp = timestamp.substring(0,4)+"-"+timestamp.substring(4,6)+"-"
                            + timestamp.substring(6,8);
                    tevLowPriceTimeYearAuto.setText(timestamp);
                }else{
                    TextUtils.setText(tevLowPriceTimeYearAuto,ukQuoteItems.get(0).lowPriceTimeYearAuto,"--");
                }
                if(ukQuoteItems.get(0).lowPriceTimeYearNonAuto != null){
                    String timestamp = ukQuoteItems.get(0).lowPriceTimeYearNonAuto;
                    timestamp = timestamp.substring(0,4)+"-"+timestamp.substring(4,6)+"-"
                            + timestamp.substring(6,8);
                    tevLowPriceTimeYearNonAuto.setText(timestamp);
                }else{
                    TextUtils.setText(tevLowPriceTimeYearNonAuto,ukQuoteItems.get(0).lowPriceTimeYearNonAuto,"--");
                }
                TextUtils.setText(tevTransactionNumber,ukQuoteItems.get(0).transactionNumber,"--");
                TextUtils.setText(tevCurrency,ukQuoteItems.get(0).currency,"--");
            }
        }
    }
}
