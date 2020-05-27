package com.cvicse.stock.markets.helper;

/**
 * Created by Administrator on 2017/6/21.
 * 股票状态帮助类
 */

public class StockStatusHelper {

    public static String statusConvert(String status){
        if(status == null){
            return "";
        }

       switch(status){
           case "00":
               return "未开市";

           case "01":
               return "盘前集合竞价";

           case "02":
               return "收盘集合竞价";

           case "03":
               return "连续竞价";

           case "05":
               return "收盘 ";

           case "06":
               return "午间休市";

           case "10":
               return "停牌暂停交易";

           case "20":
               return "停牌";

           default:
            return "";

       }

    }
}
