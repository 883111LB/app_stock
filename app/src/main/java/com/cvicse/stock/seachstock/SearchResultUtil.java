package com.cvicse.stock.seachstock;


import com.mitake.core.SearchResultItem;

/**
 * Created by ruan_ytai on 17-4-20.
 */

public class SearchResultUtil {

    public static  final String A_STOCK = "1001";//A股
    public static  final String B_STOCK = "1002";//B股
    public static  final String CY_STOCK = "1004";// 创业板
    public static  final String DPZS_STOCK = "1400";//大盘指数
    public static  final String JJ_STOCK = "1100";// 基金
    public static  final String LOF = "1110";//LOF
    public static  final String ETF = "1120";//ETF
    public static  final String CEF = "1140";//CEF
    public static  final String FJA = "1131";//分级A基金
    public static  final String FJB = "1132";//分级B基金
    public static  final String ZQ = "1300";//债券
    public static  final String SZQQ = "3002";//上证期权
    public static  final String WL = "1500";//涡轮
    public static  final String ZB = "1501";//主板
    public static  final String CY = "1502";//创业
    public static  final String YS = "1503";//衍生
    public static  final String IF = "if";//指数期货
    public static  final String TF = "tf";//国债期货
    public static  boolean isGB = false;
    public static final String RZ="2011";
    public static final String RQ="2012";

    public static String setTypeSymbol(SearchResultItem searchResultItem){
        String needStr = "";
        String market = searchResultItem.market;
        String st=searchResultItem.st;
        isGB = false;

        if("sh".equals(market)){
            if (st.indexOf("2011")!=-1||st.indexOf("2012")!=-1){
                needStr="融";
            }else if ("1001".equals(searchResultItem.subtype)){
                needStr = "沪A";
            }else if ("1002".equals(searchResultItem.subtype)){
                needStr = "沪B";
            }
        }else if("sz".equals(market)){
            if (st.indexOf("2011")!=-1||st.indexOf("2012")!=-1){
                needStr="融";
            }else {
                needStr = "深";
            }
        }else if("hk".equals(market)){
            if (st.indexOf("2011")!=-1||st.indexOf("2012")!=-1){
                needStr="融";
            }else {
                needStr = "港";
            }
        } else if("bj".equals(market)){
            if (st.indexOf("2011")!=-1||st.indexOf("2012")!=-1){
                needStr="融";
            }else {
                needStr = "BJ";
            }
        }else if( "cff".equals(market) ){
            if (st.indexOf("2011")!=-1||st.indexOf("2012")!=-1){
                needStr="融";
            }else {
                needStr = "期";
            }
        }else if("gb".equals(market)){
            if (st.indexOf("2011")!=-1||st.indexOf("2012")!=-1){
                needStr="融";
            }else {
                isGB = true;
            }
        }
//        if ("2011".equals(subtype)){
//            needStr="融";
//        }else if ("2012".equals(subtype)){
//            needStr="融";
//        }

        switch(searchResultItem.subtype){
            case JJ_STOCK:
            case LOF:
            case ETF:
            case FJA:
            case FJB:
            case CEF:needStr = needStr +"基";break;

            case A_STOCK:needStr = isGB ? "外汇":needStr;break;

//            case B_STOCK:needStr = needStr +"B";break;

            case CY_STOCK:needStr = needStr +"创";break;

            case DPZS_STOCK:needStr = isGB ? "全球": (needStr +"指");break;

            case ZQ:needStr = needStr +"债";break;

            case SZQQ:needStr = needStr +"期";break;

            case WL:needStr = needStr +"涡";break;

            case ZB:needStr = needStr +"主";break;

            case CY:needStr = needStr +"创业";break;

            case YS:needStr = needStr +"衍";break;

            case IF:needStr = needStr + "指";break;

            case TF:needStr = needStr +"货";break;

            default:break;
        }

        if( "hk".equals(market) ){
            switch (searchResultItem.hkType){
                case "0": needStr = "港股";break;
                case "1": needStr = "沪港";break;
                case "2": needStr = "深港";break;
                case "3": needStr = "沪深港";break;
            }
        }

        return "".equals(needStr) ? "未知" : needStr ;
    }
}
