package com.cvicse.stock.http;

import com.mitake.core.OrderQuantityItem;
import com.mitake.core.QuoteItem;
import com.mitake.core.network.NetworkManager;
import com.mitake.core.network.TCPManager;
import com.mitake.core.response.QuoteResponse;

import java.util.ArrayList;

/**
 * tcp 统一管理类
 * Created by liu_zlu on 2017/3/16 13:41
 */
public class TcpManager {

    private static NetworkManager.IPush globalPush = new NetworkManager.IPush() {
        @Override
        public void push(QuoteItem quoteItem, ArrayList<OrderQuantityItem> arrayList, ArrayList<OrderQuantityItem> arrayList1) {
            for (NetworkManager.IPush iPush:iPushs){
            iPush.push(quoteItem,arrayList,arrayList1);
            }
        }

//        @Override
//        public void pushHttp(QuoteResponse quoteResponse) {
//
//        }
    };

    static {
        NetworkManager.getInstance().setIPush(globalPush);
    }

    //存放所有的推送
    private static ArrayList<NetworkManager.IPush> iPushs = new ArrayList<>();

    /**
     * 注册tcp
     */
    public static void registerIPush(NetworkManager.IPush iPush){
        if(!iPushs.contains(iPush)){
            iPushs.add(iPush);
        }
    }

    public static void unregisterIPush(NetworkManager.IPush iPush){
        iPushs.remove(iPush);
    }

    public static void subscribe(String[] stocks){
        //tcp没有连接时，初始tcp连线
        TCPManager.getInstance().subscribe(stocks);
    }

    public static void subscribe(String stocks){
        if(stocks == null)return;
        String[] strings = stocks.split(",");
        subscribe(strings);
    }

    public static void unsubscribe(String[] stocks) {
        TCPManager.getInstance().unsubscribe(stocks);
    }

    public static void unsubscribe(String stocks) {
        if(stocks == null)return;
        String[] strings = stocks.split(",");
        unsubscribe(strings);
    }
}
