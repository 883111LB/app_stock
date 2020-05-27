package com.cvicse.stock.utils;

import com.mitake.core.QuoteItem;


/**
 * 股票类型判断类
 * Created by liu_zlu on 2017/3/22 14:25
 */
public class StockType {
    //大盘指数
    private final static String SUBTYPE_INDEX ="1400";
    // 外汇指数
    private final static String SUBTYPE_FOEIGN_INDEX ="1001";
    // 债券
    private final static String SUBTYPE_BONDS ="1300";
    // 基金
    private final static String SUBTYPE_FUND ="1100";
    // 期权
    private final static String SUBTYPE_OPTION ="3002";

    private final static String MARKET_SH ="sh";
    private final static String MARKET_SZ ="sz";
    private final static String MARKET_HK ="hk";
    private final static String MARKET_BJ ="bj";
    private final static String MARKET_CFF = "cff";  // 中金所
    private final static String MARKET_GB = "gb";
    private final static String MARKET_GI = "gi";  // 全球指数
    private final static String MARKET_FE = "fe";  // 外汇
    private final static String MARKET_DCE = "dce"; // 大商所
    private final static String MARKET_ZCE = "zce"; // 郑商所
    private final static String MARKET_SHFE="shfe";//上期所
    private final static String MARKET_INE="ine";//上期所原油
    private final static String MARKET_UK="uk";
    private final static String MARKET_BK="bk";// 板块

    public static Type getType(QuoteItem quoteItem){
        Type marketType = null;
        if( null == quoteItem ){
            return Type.SH;
        }
        if( null != quoteItem.id ){
            String[] split = quoteItem.id.split("\\.");
            String type = split[split.length - 1];
            String subtype = quoteItem.subtype;

            if( null != subtype && !MARKET_GI.equals(type)&& !MARKET_FE.equals(type)){
                // 不包括全球指数、外汇市场
                switch (subtype){
                    case SUBTYPE_INDEX:  marketType = Type.INDEX; break;
                    case SUBTYPE_BONDS:  marketType = Type.BONDS; break;
                    case SUBTYPE_FUND:  marketType = Type.FUND; break;
                    case SUBTYPE_OPTION:  marketType = Type.OPTION; break;
                }
            }
            if(null == marketType ) {
                switch (type) {
                    case MARKET_HK:marketType = Type.HONGKONG;break;
                    case MARKET_SH:marketType = Type.SH;break;
                    case MARKET_SZ:marketType = Type.SZ;break;
                    case MARKET_BJ:marketType = Type.BJ;break;
                    case MARKET_CFF:marketType = Type.CFF;break;
                    case MARKET_GI:marketType = Type.GI;break;
                    case MARKET_FE:marketType = Type.FE;break;
                    case MARKET_DCE:marketType = Type.DCE;break;
                    case MARKET_ZCE:marketType = Type.ZCE;break;
                    case MARKET_SHFE:marketType = Type.SHFE;break;
                    case MARKET_INE:marketType = Type.INE;break;
                    case MARKET_UK:marketType = Type.UK;break;
                    case MARKET_BK:marketType = Type.BK;break;
                }
            }
/*
            if(null != subtype && SUBTYPE_INDEX.equals(subtype)){
                marketType = Type.INDEX;
            } else if(null != subtype && SUBTYPE_BONDS.equals(subtype)){
                marketType = Type.BONDS;
            }else if(SUBTYPE_FUND.equals(subtype)){
                marketType = Type.FUND;
            }else if(MARKET_HK.equals(type)){
                marketType = Type.HONGKONG;
            } else if(MARKET_SH.equals(type)){
                marketType = Type.SH;
            } else if(MARKET_SZ.equals(type)){
                marketType = Type.SZ;
            }else if(MARKET_BJ.equals(type)){
                marketType = Type.BJ;
            }else if( MARKET_CFF.equals(type) ){
                marketType = Type.CFF;
            }

            if(null != subtype &&  SUBTYPE_FOEIGN_INDEX.equals(subtype) &&  MARKET_GB.equals(type)){
                marketType = Type.INDEX;
            }
            */
            return marketType;
        }
        return Type.SH;
    }

    public static enum Type{
        SH,
        SZ,
        BJ,
        CFF,
        HONGKONG,
        GI,     // 全球
        FE,      // 外汇
        DCE,     // 大商所
        ZCE,    // 郑商所
        SHFE,
        INE,
        INDEX,  // 指数
        BONDS,  // 债券
        OPTION, // 期权
        FUND,  // 基金
        UK,
        BK;
        public boolean isIndex(){
            return this == INDEX;
        }

        public boolean isHongKong(){
            return this == HONGKONG;
        }

        public boolean isOrdinary(){
            return this == SZ || this == SH;
        }

        public boolean isOption(){
            return this == OPTION;
        }

        public boolean isFund(){
            return this == FUND;
        }

        public boolean isSZ(){
            return this == SZ;
        }

        public boolean isSH(){
            return this == SH;
        }

        public boolean isCFF(){
            return this == CFF;
        }

        public boolean isFE(){
            return this == FE;
        }
        public boolean isGI(){
            return this == GI;
        }
        public boolean isDCE(){
            return this == DCE;
        }
        public boolean isZCE(){
            return this == ZCE;
        }
        public boolean isSHFE(){
            return this == SHFE;
        }
        public boolean isINE(){
            return this == INE;
        }
        public boolean isUK(){
            return this==UK;
        }

        public boolean isBk(){
            return this == BK;
        }
    }
}
