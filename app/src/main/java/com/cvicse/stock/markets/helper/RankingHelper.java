package com.cvicse.stock.markets.helper;

//import com.mitake.core.keys.FuturesQuoteBaseField;
//import com.mitake.core.keys.FuturesQuoteDetailField;
//import com.mitake.core.keys.FuturesQuoteField;
import android.view.Gravity;

import com.mitake.core.keys.FuturesQuoteBaseField;
import com.mitake.core.keys.FuturesQuoteDetailField;
//import com.mitake.core.keys.KeysQuoteCff;
import com.mitake.core.keys.FuturesQuoteField;
import com.mitake.core.keys.quote.SortType;

/** 根据不同市场选择不同排序字段
 * Created by tang_xqing on 2018/7/30.
 */
public class RankingHelper {

    /**
     * 全球、外汇排序字段
     * @return
     */
    public String[][] getFEGIRankingKey(){
        return new String[][]{{"300","303","302","306","304","307","308","301"},{
                "最新","涨幅","涨跌","成交量","成交额","昨收","最高","最低"}};
    }

    /**
     * 沪深、新三板排序字段
     * @return
     */
    public String[][] getDefaultRankingKey(){
        return new String[][]{{"7","12","19","14","20","15","21","37","8","9","10","11","29", "42","40","26","27"},
                {"最新", "涨幅", "涨跌", "成交量", "成交额", "换手率", "量比", "振幅", "最高", "最低",
                        "开盘", "昨收", "市盈(动)","市盈(静)","委比","总市值","流通值"}};
    }
    /**
     * 沪深排序字段(五分钟涨幅）
     * @return
     */
    public String[][] getHSRankingKey(){
        return new String[][]{{
            "0", "3", "4", "5", "6",
            "7", "8", "9", "10", "11",
            "12", "13", "14", "15", "16",
            "17", "18", "19", "20", "21",
            "24", "25", "26",
            "27", "28", "29", "30", "31",
            "32",
            "37", "38", "53", "54",
            "42", "86", "87", "88", "89",
            "-19", "-20", "-21", "-22", "-34",
            "-35", "-36", "-37", "-38", "-39",
            "-40", "-41", "-42", "-47", "-48",
            "-58", "-59", "-60", "-61", "-62",
            "-63"
        },
                {"交易状态", "次类别", " 最新价", "最高价", "最低价", "今开价", "昨收价",
                "涨跌比率", "总手", "当前成交量", "换手率", "涨停价", "跌停价", "均价", "涨跌", "成交金额", "量比",
                "外盘量", "内盘量", "总市值", "流通市值", "净资产", "pe(动态市盈率)", "市净率", "总股本",
                "流通股", "振幅比率", "动态每股收益", "沪深委比", "委差",
                "静态市盈率", "本月涨跌幅", "本年涨跌幅", "近一月涨跌幅", "近一年涨跌幅", "超大单净流入", "大单净流入", "中单净流入", "小单净流入", "大单净差",
                "五日大单净差", "十日大单净差", "主力动向", "五日主力动向", "十日主力动向", "涨跌动因", "五日涨跌动因", "十日涨跌动因", "主力净流入", "五分钟涨跌幅",
                "5日主力资金净流入", "10日主力资金净流入", "20日主力资金净流入", "5日主力资金净流入占比", "10日主力资金净流入占比", "20日主力资金净流入占比"
                }};
    }
    /**
     * 沪深市场-增值指标
     * @return
     */
    public String[][] getAddvalueFiveRankingKey(){
        return new String[][]{{"7","12","-48","-58","-59","-60","","",""},
                {"最新","涨幅","5分钟涨幅","5日主净入","10日主净入","20日主净入","5日主净入比","10日主净入比","20日主净入比"}};
    }
//    FuturesQuoteBaseField.prev_close
    /**
     * 期货排序字段
     * @return
     */
    public String[][] getCFFRankingKey(){
        return new String[][] {{
            FuturesQuoteBaseField.prev_close, FuturesQuoteBaseField.open, FuturesQuoteBaseField.high, FuturesQuoteBaseField.low, FuturesQuoteBaseField.last,
            FuturesQuoteBaseField.avg, FuturesQuoteBaseField.change, FuturesQuoteBaseField.chg_rate, FuturesQuoteBaseField.volume, FuturesQuoteBaseField.amount,
            FuturesQuoteBaseField.now_vol, FuturesQuoteBaseField.sell_vol, FuturesQuoteBaseField.buy_vol, FuturesQuoteBaseField.tradingDay, FuturesQuoteBaseField.settlementGroupID,
            FuturesQuoteBaseField.settlementID, FuturesQuoteBaseField.preSettlement, FuturesQuoteBaseField.preOpenInterest, FuturesQuoteBaseField.opt, FuturesQuoteBaseField.position_chg,
            FuturesQuoteBaseField.close, FuturesQuoteBaseField.settlement, FuturesQuoteBaseField.upperLimit, FuturesQuoteBaseField.downLimit, FuturesQuoteBaseField.preDelta,
            FuturesQuoteBaseField.currDelta, FuturesQuoteBaseField.updateMillisec, FuturesQuoteBaseField.type, FuturesQuoteBaseField.underlyingLastPx, FuturesQuoteBaseField.underlyingPreClose,
            FuturesQuoteBaseField.underlyingchg, FuturesQuoteBaseField.tradeStatus, FuturesQuoteBaseField.change1, FuturesQuoteBaseField.amp_rate,
            FuturesQuoteBaseField.callOrPut, FuturesQuoteBaseField.excercisePx, FuturesQuoteBaseField.premiumRate, FuturesQuoteBaseField.remainingDays, FuturesQuoteBaseField.impliedVolatility,
            FuturesQuoteBaseField.riskFreeInterestRate, FuturesQuoteBaseField.riskIndicator, FuturesQuoteBaseField.leverageRatio, FuturesQuoteBaseField.intersectionNum, FuturesQuoteDetailField.entrustDiff,
            FuturesQuoteDetailField.entrustRatio, FuturesQuoteDetailField.currDiff, FuturesQuoteDetailField.underlyingType,
            FuturesQuoteDetailField.posDiff, FuturesQuoteDetailField.deliveryDay, FuturesQuoteDetailField.totalBid, FuturesQuoteDetailField.totalAsk, FuturesQuoteField.bidpx1,
            FuturesQuoteField.bidvol1, FuturesQuoteField.askpx1, FuturesQuoteField.askvol1
        }, {
            "昨收", "开盘", "最高", "最低", "最新", "均价", "涨跌", "涨跌幅", "成交量", "成交金额",
            "当前成交量", "内盘", "外盘", "交易日", "结算组代码", "结算编号", "昨结算", "昨持仓量", "持仓量", "日增",
            "今收盘价", "今结算价", "涨停价", "跌停价", "昨虚实度", "今虚实度", "最后修改毫秒", "类型", "标的现价", "标的昨收",
            "标的涨跌", "交易状态", "change1", "振幅", "期权类型", "行权价", "溢价率", "剩余天数", "隐含波动率",
            "无风险利率", "风险指标", "杠杆比率", "交割点数", "委差", "委比", "期现差", "标的品种",
            "仓差", "交割日期", "委买", "委卖", "买1价", "买1量", "卖1价", "卖1量"
        }};
    }
//    /**
//     * 期货排序字段
//     * @return
//     */
//    public String[][] getCFFRankingKey(){
//        return new String[][] {{
//            "7","8","9","10","11","12","13","14","15","513","514",
//                "500","19","20","21","515","516","24","25","26","27","28",
//                "29","30","31","32","37","38","53","525","42","510","511","512",
//                "517","518","519","521","522","527","528","529","531","532","530","526","501","502",
//                "503","505","506","507","508","509"
//        },
//                {
//                        "最新价","最高价","最低价","今开价","昨收价","涨跌比率","总手","当前成交量","换手率","涨停价","跌停价",
//                        "均价","涨跌","成交金额","量比","买一价","卖一价"," 外盘量","内盘量"," 总市值","流通市值","净资产",
//                        "动态市盈率","市净率","总股本","流通股","振幅比率","动态每股收益","委比","委差","静态市盈率","昨虚实度","今虚实度","最后修改毫秒",
//                        "标的现价","标的昨收", "标的涨跌","涨跌1","仓差","行权价","溢价率","剩余天数","隐含波动率","无风险利率","杠杆比率","交割点数","交易日","结算组代码",
//                        "结算编号","昨持仓量","持仓量","日增","今收盘价","今结算价"
////                    "最新", "涨跌幅", "涨跌1", "成交量", "成交金额", "最高", "最低", "开盘", "昨收", "当前成交量", "均价", "内盘", "外盘",
////                        "交易日", "结算组代码", "结算编号", "昨结算", "昨持仓量", "持仓量", "日增","今收盘价", "今结算价", "涨停价",
////                        "跌停价", "昨虚实度", "今虚实度", "最后修改毫秒",
//////                        "类型",
////                        "标的现价","标的昨收","标的涨跌",
//////                        "交易状态",
////                        "涨跌2",
//////                        "振幅",
////                        "仓差",
//////                        "期权类型","行权价","溢价率","剩余天数",
////                        "隐含波动率","无风险利率","风险指标","杠杆比率","交割点数","委差",
//////                        "买价与买量","卖价与卖量","委比",
////                        "期现差","标的品种","交割日期","委买","委卖",
//////                        "买1价","买1量","卖1价","卖1量"
//                }};
//    }

    /**
     * 根据市场获取对应的排序字段
     * @param type
     * @return
     */
    public String[][] getRankingKey(String type){
        if( "sh".equals(type) || "sz".equals(type) || "shsz".equals(type) || "hk".equals(type) || "bj".equals(type) ){
            return getDefaultRankingKey();
        }
        else if("cff".equals(type)|| "dce".equals(type) || "zce".equals(type)||"shfe".equals(type)||"ine".equals(type)){
            return getCFFRankingKey();
        }
        else if ("gi".equals(type) || "fe".equals(type)){
            return getFEGIRankingKey();
        }else if("addvalue".equals(type)){
//            return getAddvalueFiveRankingKey();
            return getHSRankingKey();
        }
        return getDefaultRankingKey();
    }
}
