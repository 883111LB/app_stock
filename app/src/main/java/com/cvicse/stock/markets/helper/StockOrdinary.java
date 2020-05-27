package com.cvicse.stock.markets.helper;

import android.view.View;

import com.cvicse.stock.R;
import com.cvicse.stock.view.AutofitTextView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by liu_zlu on 2017/2/7 16:05
 */
public class StockOrdinary {

    /**
     * 涨停
     */
    @BindView(R.id.tev_daily_limit_value) AutofitTextView tevDailyLimitValue;
    /**
     * 跌停
     */
    @BindView(R.id.tev_drop_limit_value) AutofitTextView tevDropLimitValue;
    /**
     * 总股本
     */
    @BindView(R.id.tev_total_share_capital) AutofitTextView tevTotalShareCapital;
    /**
     * 流通股本
     */
    @BindView(R.id.tev_circulating_capital) AutofitTextView tevCirculatingCapital;
    /**
     * 昨收
     */
    @BindView(R.id.tev_preclose) AutofitTextView tevPreclose;
    /**
     * 内盘
     */
    @BindView(R.id.tev_Internal_disk) AutofitTextView tevInternalDisk;
    /**
     * 外盘
     */
    @BindView(R.id.tev_external_disk) AutofitTextView tevExternalDisk;
    /**
     * 总市值
     */
    @BindView(R.id.tev_total_marketvalue) AutofitTextView tevTotalMarketvalue;
    /**
     * 流通市值
     */
    @BindView(R.id.tev_market_capitalization) AutofitTextView tevMarketCapitalization;
    /**
     * 均价
     */
    @BindView(R.id.tev_average_price) AutofitTextView tevAveragePrice;
    /**
     * 委比
     */
    @BindView(R.id.tev_commissioned_proportion) AutofitTextView tevCommissionedProportion;
    /**
     * 量比
     */
    @BindView(R.id.tev_volume_proportion) AutofitTextView tevVolumeProportion;
    /**
     * 市盈(动)
     */
    @BindView(R.id.tev_market_profit) AutofitTextView tevMarketProfit;
    /**
     * 振幅
     */
    @BindView(R.id.tev_amplitude) AutofitTextView tevAmplitude;

    StockOrdinary(View view){
        ButterKnife.bind(this, view);
    }
}
