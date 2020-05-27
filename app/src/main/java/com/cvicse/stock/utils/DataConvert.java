package com.cvicse.stock.utils;

import com.cvicse.stock.common.FillConfig;
import com.cvicse.stock.util.FormatUtils;
import com.mitake.core.QuoteItem;

import java.util.ArrayList;

/**
 * Created by liu_zlu on 2017/1/14 20:52
 */
public class DataConvert {

    public static QuoteItem QuoteItem(QuoteItem quoteItem){
        if(quoteItem == null){
            return null;
        }
        String changeRate = quoteItem.changeRate;
        String change = quoteItem.change;
        if(!FormatUtils.isStandard(changeRate)){
            quoteItem.changeRate = FillConfig.DEFALUT;
        } else {
            if(changeRate.contains("-") || changeRate.contains("+")){
                return quoteItem;
            }

            if(change !=null && change.contains("-")){
                quoteItem.changeRate = "-"+ changeRate;
            } else if(change !=null && change.contains("+")){
                quoteItem.changeRate = "+"+ changeRate;
            }
        }
        return quoteItem;
    }

    public static ArrayList<QuoteItem> QuoteItemList(ArrayList<QuoteItem> quoteItems){
        if(quoteItems == null){
            return null;
        }
        int length = quoteItems.size();
        QuoteItem quote = null;
        for(int i = length-1;i > -1;i--){
            quote = quoteItems.get(i);
            if(quote == null){
                quoteItems.remove(i);
            } else {
                QuoteItem(quote);
            }
        }
        return quoteItems;
    }

    /**
     * 数据整合，保证数据完整
     * @param quoteItem
     * @param src
     */
    public static QuoteItem copy(QuoteItem quoteItem,QuoteItem src){
        if( null== src.id){
            return quoteItem;
        }

        if( null != src.status ){
            quoteItem.status = src.status;
        }

        if(null!= src.buySingleVolumes ){
            quoteItem.buySingleVolumes = src.buySingleVolumes;
        }
        if(null != src.buyVolumes){
            quoteItem.buyVolumes = src.buyVolumes;
        }
        if(null != src.sellVolumes){
            quoteItem.sellVolumes = src.sellVolumes;
        }
        if( null!= src.buyPrices){
            quoteItem.buyPrices = src.buyPrices;
        }
        if( null!= src.sellPrices){
            quoteItem.sellPrices = src.sellPrices;
        }
        if( null!= src.name){
            quoteItem.name = src.name;
        }
        if( null != src.market ){
            quoteItem.market = src.market;
        }

        if( null != src.subtype ){
            quoteItem.subtype = src.subtype;
        }

        if(null != src.amount){
            quoteItem.amount = src.amount;
        }
        if( null!= src.datetime){
            quoteItem.datetime = src.datetime;
        }
        if( null!= src.averageBuy){
            quoteItem.averageBuy = src.averageBuy;
        }
        if(  null!=src.averageSell){
            quoteItem.averageSell = src.averageSell;
        }
        if(null!= src.averageValue ){
            quoteItem.averageValue = src.averageValue;
        }
        if(null!=src.buyPrice  ){
            quoteItem.buyPrice = src.buyPrice;
        }
        if(null!=src.buyPrices  ){
            quoteItem.buyPrices = src.buyPrices;
        }
        if( null!=src.change ){
            quoteItem.change = src.change;
        }
        if( null!=src.changeRate ){
            quoteItem.changeRate = src.changeRate;
        }
        if(null!=src.openPrice  ){
            quoteItem.openPrice = src.openPrice;
        }

        if( null!=src.lastPrice ){
            quoteItem.lastPrice = src.lastPrice;
        }

        if(null!=src.lowPrice  ){
            quoteItem.lowPrice = src.lowPrice;
        }

        if( null!=src.highPrice ){
            quoteItem.highPrice = src.highPrice;
        }

        if(null != src.volume){
            quoteItem.volume = src.volume;
        }

        if( null!=src.buyVolume ){
            quoteItem.buyVolume = src.buyVolume;
        }
        if( null!=src.sellVolume ){
            quoteItem.sellVolume = src.sellVolume;
        }
        if( null!=src.sumBuy ){
            quoteItem.sumBuy = src.sumBuy;
        }
        if( null!= src.sumSell){
            quoteItem.sumSell = src.sumSell;
        }
        if( null!=src.averageBuy ){
            quoteItem.averageBuy = src.averageBuy;
        }
        if( null!=src.averageSell ){
            quoteItem.averageSell = src.averageSell;
        }
        if( null!= src.tradeTick){
            quoteItem.tradeTick = src.tradeTick;
        }
        if( null!=src.orderRatio ){
            quoteItem.orderRatio = src.orderRatio;
        }

        if( null!=src.receipts ){
            quoteItem.receipts = src.receipts;
        }

        if(null != src.orderRatio){
            quoteItem.orderRatio = src.orderRatio;
        }

        if( null!= src.preClosePrice){
            quoteItem.preClosePrice = src.preClosePrice;
        }

        if( null!=src.upDownFlag ){
            quoteItem.upDownFlag = src.upDownFlag;
        }
        if( null!= src.srcPreClosePrice){
            quoteItem.srcPreClosePrice = src.srcPreClosePrice;
        }
        if( null!=src.turnoverRate ){
            quoteItem.turnoverRate = src.turnoverRate;
        }

        if( null!=src.limitDown ){
            quoteItem.limitDown = src.limitDown;
        }

        if( null!= src.limitUP){
            quoteItem.limitUP = src.limitUP;
        }

        if( null!= src.amount){
            quoteItem.amount = src.amount;
        }

        if(null != src.totalValue){
            quoteItem.totalValue = src.totalValue;
        }

        if( null!= src.capitalization){
            quoteItem.capitalization = src.capitalization;
        }
        if( null!=src.circulatingShares ){
            quoteItem.circulatingShares = src.circulatingShares;
        }

        if( null!=src.buyVolume ){
            quoteItem.buyVolume = src.buyVolume;
        }
        if( null!=src.sellVolume ){
            quoteItem.sellVolume = src.sellVolume;
        }

        if( null!= src.flowValue){
            quoteItem.flowValue = src.flowValue;
        }

        if(null != src.averageValue){
            quoteItem.averageValue = src.averageValue;
        }
        if( null!= src.orderRatio){
            quoteItem.orderRatio = src.orderRatio;
        }
        if( null!=src.volumeRatio ){
            quoteItem.volumeRatio = src.volumeRatio;
        }
        if(null != src.pe){
            quoteItem.pe = src.pe;
        }
        if( null!=src.amplitudeRate ){
            quoteItem.amplitudeRate = src.amplitudeRate;
        }

        if( null!= src.upCount){
            quoteItem.upCount = src.upCount;
        }

        if( null!=src.sameCount ){
            quoteItem.sameCount = src.sameCount;
        }

        if( null!= src.downCount){
            quoteItem.downCount = src.downCount;
        }

        if( null!=src.hh ){
            quoteItem.hh = src.hh;
        }
        if( null!=src.zh  ){
            quoteItem.zh = src.zh;
        }
        if(  null!=src.st){
            quoteItem.st = src.st;
        }
        if( null!=  src.bu){
            quoteItem.bu = src.bu;
        }
        if( null != src.buy_cancel_count ){
            quoteItem.buy_cancel_count = src.buy_cancel_count;
        }
        if( null !=src.buy_cancel_num ){
            quoteItem.buy_cancel_num = src.buy_cancel_num;
        }
        if( null != src.buy_cancel_amount){
            quoteItem.buy_cancel_amount = src.buy_cancel_amount;
        }
        if( null != src.sell_cancel_count ){
            quoteItem.sell_cancel_count = src.sell_cancel_count;
        }
        if( null !=  src.sell_cancel_num){
            quoteItem.sell_cancel_num = src.sell_cancel_num;
        }
        if( null !=  src.sell_cancel_amount){
            quoteItem.sell_cancel_amount = src.sell_cancel_amount;
        }

        if( null !=  src.preSettlement){
            quoteItem.preSettlement = src.preSettlement;
        }
        if( null !=  src.position_chg){
            quoteItem.position_chg = src.position_chg;
        }
        if( null !=  src.openInterest){
            quoteItem.openInterest = src.openInterest;
        }

        if( null !=  src.rp){
            quoteItem.rp = src.rp;
        }
        if( null !=  src.cd){
            quoteItem.cd = src.cd;
        }
        if( null !=  src.rpd){
            quoteItem.rpd = src.rpd;
        }
        if( null !=  src.cdd){
            quoteItem.cdd = src.cdd;
        }

        if( null != src.IOPV ){
            quoteItem.IOPV = src.IOPV;
        }

        if( null != src.preIOPV ){
            quoteItem.preIOPV = src.preIOPV;
        }
        if( null != src.pe2 ){
            quoteItem.pe2 = src.pe2;
        }
        if( null != src.earningsPerShare ){
            quoteItem.earningsPerShare = src.earningsPerShare;
        }
        if( null != src.earningsPerShareReportingPeriod ){
            quoteItem.earningsPerShareReportingPeriod = src.earningsPerShareReportingPeriod;
        }
        if( null != src.entrustDiff ){
            quoteItem.entrustDiff = src.entrustDiff;
        }
        if( null != src.ah ){
            quoteItem.ah = src.ah;
        }
        return quoteItem;
    }
}
