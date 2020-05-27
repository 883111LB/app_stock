package com.cvicse.stock.chart.data;

import android.annotation.SuppressLint;

import com.cvicse.base.utils.NumberUtils;
import com.mitake.core.OHLCItem;

/**
 * Created by liu_zlu on 2017/5/9 09:56
 */
@SuppressLint("ParcelCreator")
public class OHLCItemBo extends OHLCItem {

    public long datetimeL = 0;
    public float tradeVolumeL = 0f;
    public float closePriceF = 0f;
    public float openPriceF = 0f;
    public float highPriceF = 0f;
    public float lowPriceF = 0f;
    public float averagePriceF = 0f;

    public OHLCItemBo(OHLCItem ohlcItem){
        this.datetime = ohlcItem.datetime;
        this.openPrice = ohlcItem.openPrice;
        this.highPrice = ohlcItem.highPrice;
        this.lowPrice = ohlcItem.lowPrice;
        this.tradeVolume = ohlcItem.tradeVolume;
        this.volRatio = ohlcItem.volRatio;
        this.averagePrice = ohlcItem.averagePrice;
        this.change = ohlcItem.change;
        this.changeRate = ohlcItem.changeRate;
        this.reference_price = ohlcItem.reference_price;
        this.transaction_price = ohlcItem.transaction_price;
        this.time = ohlcItem.time;
        this.iopv = ohlcItem.iopv;
        this.iopvPre = ohlcItem.iopvPre;

        datetimeL = NumberUtils.parseLong(this.datetime);
        tradeVolumeL = NumberUtils.parseFloat(this.tradeVolume);
        openPriceF = NumberUtils.parseFloat(this.openPrice);
        highPriceF = NumberUtils.parseFloat(this.highPrice);
        lowPriceF = NumberUtils.parseFloat(this.lowPrice);
        averagePriceF = NumberUtils.parseFloat(this.averagePrice);

        // 大商所，郑商所，全球，外汇
        this.closePrice = ohlcItem.closePrice ;
        closePriceF = NumberUtils.parseFloat( this.closePrice);
    }
}
