package com.cvicse.stock.markets.helper;

import com.cvicse.stock.markets.data.MarginTradingBo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/** 融资融券数值转换帮助类
 * Created by tang_xqing on 2018/8/3.
 */

public class MarginTradingHelper {
    public String[][] getMarginTradingRankingKey(){
        return new String[][]{{"TRADEDATE","FINBALANCE","FINBUYAMT","FINREPAYAMT","FINROEBUY","MRGGBAL","MRGNRESQTY","MRGNSELLAMT","MRGNREPAYAMT","MRGNROESELL","FINMRGHBAL","FINMRGNBAL"},
                {"日期","融资余额","融资买额","融资偿额","融资净买","融券余额","融券余量","融券卖量","融券偿量","融券净卖","两融余额","两融差额"}};
    }

    /**
     * 个股融资融券字段
     * @return
     */
    public String[] getStockMarginTradeKey(){
        return new String[]{
                "融资余额","融资买额","融资偿额","融资净买","融券余额","融券余量","融券卖量","融券偿量","融券净卖","两融余额","两融差额"};
    }

    /**
     * 分市场提供最近交易日、个股
     * @return
     */
    public ArrayList<MarginTradingBo> getMarginSubMarket(List<HashMap<String,Object>> infoList){
        if( null == infoList || infoList.isEmpty() ){
            return null;
        }
        ArrayList<MarginTradingBo> arrayList = new ArrayList<>();
        for (HashMap<String, Object> stringMap : infoList) {
            MarginTradingBo marginTradingBo = new MarginTradingBo();
            marginTradingBo.tradeDate = (String) stringMap.get("TRADEDATE");
            marginTradingBo.trading = (String) stringMap.get("TRADING");
            marginTradingBo.finbalance = (String) stringMap.get("FINBALANCE");
            marginTradingBo.finbuyamt = (String) stringMap.get("FINBUYAMT");
            marginTradingBo.finrepayamt = (String) stringMap.get("FINREPAYAMT");
            marginTradingBo.finroebuy = (String) stringMap.get("FINROEBUY");
            marginTradingBo.mrggbal = (String) stringMap.get("MRGGBAL");
            marginTradingBo.mrgnresqty = (String) stringMap.get("MRGNRESQTY");
            marginTradingBo.mrgnsellamt = (String) stringMap.get("MRGNSELLAMT");
            marginTradingBo.mrgnrepayamt = (String) stringMap.get("MRGNREPAYAMT");
            marginTradingBo.mrgnroesell = (String) stringMap.get("MRGNROESELL");
            marginTradingBo.finmrghbal = (String) stringMap.get("FINMRGHBAL");
            marginTradingBo.finmrgnbal = (String) stringMap.get("FINMRGNBAL");
            arrayList.add(marginTradingBo);
        }
        return arrayList;
    }

    /**
     * 融资融券差额
     * @param infoList
     * @return
     */
    public ArrayList<MarginTradingBo> getMarginFinDiff(List<HashMap<String,Object>> infoList){
        if( null == infoList || infoList.isEmpty() ){
            return null;
        }
        ArrayList<MarginTradingBo> arrayList = new ArrayList<>();
        for (HashMap<String, Object> stringMap : infoList) {
            MarginTradingBo marginTradingBo = new MarginTradingBo();
            marginTradingBo.tradeDate = (String) stringMap.get("TRADEDATE");
            marginTradingBo.finbalance = (String) stringMap.get("FINBALANCESUM");
            marginTradingBo.finbuyamt = (String) stringMap.get("FINBUYAMTSUM");
            marginTradingBo.finrepayamt = (String) stringMap.get("FINREPAYAMTSUM");
            marginTradingBo.mrggbal = (String) stringMap.get("MRGGBALSUM");
            marginTradingBo.finmrghbal = (String) stringMap.get("FINMRGHBALSUM");
            marginTradingBo.finmrgnbal = (String) stringMap.get("FINMRGNBALSUM");
            arrayList.add(marginTradingBo);
        }
        return arrayList;
    }

}
