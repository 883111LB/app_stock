package com.cvicse.stock.portfolio.presenter;

import com.cvicse.base.mvp.BasePresenter;
import com.cvicse.base.utils.ListUtils;
import com.cvicse.base.utils.LogUtils;
import com.cvicse.stock.http.ResponseCallback;
import com.cvicse.stock.http.TcpManager;
import com.cvicse.stock.http.loop.QuoteRunable;
import com.cvicse.stock.http.loop.RequestManager;
import com.cvicse.stock.http.loop.RunTaskState;
import com.cvicse.stock.portfolio.presenter.constract.MianConstract;
import com.cvicse.stock.utils.DataConvert;
import com.cvicse.stock.utils.MyStocksUtils;
import com.mitake.core.OrderQuantityItem;
import com.mitake.core.QuoteItem;
import com.mitake.core.StockInfoListItem;
import com.mitake.core.network.NetworkManager;
import com.mitake.core.network.TCPManager;
import com.mitake.core.request.QuoteRequest;
import com.mitake.core.request.StockInfoRequest;
import com.mitake.core.response.QuoteResponse;
import com.mitake.core.response.Response;
import com.mitake.core.response.StockInfoResponse;

import java.util.ArrayList;
import java.util.HashMap;

import static com.cvicse.stock.utils.MyStocksUtils.getSelectCodes;

/**
 *自选主页面
 * Created by ding_syi on 17-1-9.
 */
public class MianPresenter extends BasePresenter implements MianConstract.Presenter,NetworkManager.IPush {
    private final String TAG = "MianPresenter";

    private  MianConstract.View view;

    private String[] subscribeArray;

    final String[] array = {"000001.sh","399001.sz","399006.sz"};
    final String quoteIndex = "000001.sh,399001.sz,399006.sz";

    private String[] selects;

    private RunTaskState indexSign;

    /**
     * 存放后台返回的自选股票数据
     */
    private ArrayList<QuoteItem> quoteItemSeleteds = new ArrayList<>();

    /**
     * 查询指数
     */
    private void queryIndex() {
        // 获取所有存储在本地的股票名
        String stockCode = MyStocksUtils.getSelectCode();
        //20200521修改
        selects = getSelectCodes();
        String[] codes1=(quoteIndex+","+stockCode).split(",");
        TCPManager.getInstance().subscribe(codes1);
        NetworkManager.getInstance().addIPush(new NetworkManager.IPush() {

            @Override
            public void push(QuoteItem quoteItem, ArrayList<OrderQuantityItem> arrayList, ArrayList<OrderQuantityItem> arrayList1) {
                if(isBounds(quoteItem,array)){
                    view.onIndexTcp(quoteItem);
                }
                if(isBounds(quoteItem,selects)){
                    QuoteResponse response = new QuoteResponse();
                    ArrayList<QuoteItem>  quoteItems = new ArrayList<>();
                    quoteItems.add(quoteItem);
                    response.quoteItems = quoteItems;
                    view.onSelectedSuccess(DataConvert.QuoteItemList(response.quoteItems));
                }
            }

            @Override
            public void pushHttp(QuoteResponse quoteResponse) {

            }
        });
//        RequestManager.cancel(indexSign);
//
//        indexSign = RequestManager.request(new QuoteRunable(quoteIndex+","+stockCode,null) {
//            @Override
//            public void onBack(QuoteResponse response) {
//
//                ArrayList<QuoteItem> list = response.quoteItems;
//                quoteItemSeleteds.clear();
//                if(null !=list ){
//                    for(int j = 0;j < array.length;j++){
//                        //取出指数的值
//                        if(list.get(j) != null && array[j].equals(list.get(j).id)){
//                            view.onIndexTcp(list.get(j));
//                        }
//                    }
//                    //quoteItemSeleteds 赋值
//                    ListUtils.copy(list, array.length, quoteItemSeleteds, list.size(), new ListUtils.Filter<QuoteItem>() {
//                        @Override
//                        public boolean filter(QuoteItem quoteItem) {
//                            return quoteItem != null && quoteItem.id != null;
//                        }
//                    });
//                    view.onSelectedSuccess(DataConvert.QuoteItemList(quoteItemSeleteds));
//                }
//            }
//
//            @Override
//            public void onError(int i, String error) {
//
//            }
//        });
    }

    /**
     * 直接订阅了商品
     */
    @Override
    public void onResume() {
        super.onResume();
        TcpManager.registerIPush(this);
        queryIndex();//查询指数
        selects = getSelectCodes();
        if( null!=selects ){
            //请求个股静态数据
            requestStockInfo(MyStocksUtils.getSelectCode(),null);
            /**
             * 自选的股票数量 + 三个指数
             */
            String[] stringReal = new String[selects.length + array.length];
            //数组复制，StringReal数组复制为array数组
            System.arraycopy(array,0,stringReal,0,array.length);
            //复制selects数组到StringReal数组后面
            System.arraycopy(selects,0,stringReal,array.length,selects.length);

            subscribeArray = stringReal;
        } else {
            subscribeArray = array;
        }
        TcpManager.subscribe(subscribeArray);
    }

    private boolean isBounds(QuoteItem quoteItem,String[] strings){
        if( null==strings ){
            return false;
        }

        for(String string:strings){
            if(string.equals(quoteItem.id)){
                return true;
            }
        }
        return false;
    }


    @Override
    public void onPause() {
        super.onPause();
        RequestManager.cancel(indexSign);
        TcpManager.unregisterIPush(this);
        TcpManager.unsubscribe(subscribeArray);
    }

    @Override
    public void setView(MianConstract.View view) {
        this.view = view;
    }

    @Override
    public void push(QuoteItem quoteItem, ArrayList<OrderQuantityItem> arrayList, ArrayList<OrderQuantityItem> arrayList1) {
        if(isBounds(quoteItem,array)){
            view.onIndexTcp(quoteItem);
        }
        if(isBounds(quoteItem,selects)){
            view.onSelectTcp(DataConvert.QuoteItem(quoteItem));
        }
    }
//20200512换生产后改
    @Override
    public void pushHttp(QuoteResponse quoteResponse) {

    }

    /**
     * 请求个股静态数据
     *
     * @param code
     * @param param
     */
    public void requestStockInfo(String code, String param) {
        StockInfoRequest request = new StockInfoRequest();
        request.send(code, param, new ResponseCallback(request) {
            @Override
            public void onBack(Response response) {
                ArrayList<StockInfoListItem> list = ((StockInfoResponse)response).list;
                if( null==list  || list.isEmpty()){
                    return;
                }
                HashMap<String,StockInfoListItem> items = new HashMap<String, StockInfoListItem>();
               String[] requestCode = MyStocksUtils.getSelectCodes();
                if( null== requestCode){
                    return;
                }
                for(int i=0;i<requestCode.length;i++){
                    items.put(requestCode[i],list.get(i));
                }
                view.requestStockInfoSuccess(items);
            }

            @Override
            public void onError(int i, String s) {

            }
        });
    }

    /**
     * 查询自选
     */
    @Override
    public void onRequestSelects(){
       String selects =  MyStocksUtils.getSelectCode();
       if( null== selects){
           return;
       }
        requestStockInfo(selects,null);
        String[] codd = selects.split(",");
        //20200520修改
        TCPManager.getInstance().subscribe(codd);
        NetworkManager.getInstance().addIPush(new NetworkManager.IPush() {

            @Override
            public void push(QuoteItem quoteItem, ArrayList<OrderQuantityItem> arrayList, ArrayList<OrderQuantityItem> arrayList1) {
                QuoteResponse response = new QuoteResponse();
                ArrayList<QuoteItem>  quoteItems = new ArrayList<>();
                quoteItems.add(quoteItem);
                response.quoteItems = quoteItems;
                view.onSelectedSuccess(DataConvert.QuoteItemList(response.quoteItems));
            }

            @Override
            public void pushHttp(QuoteResponse quoteResponse) {

            }
        });
//        QuoteRequest request = new QuoteRequest();
//        request.send(codd, new ResponseCallback(request) {
////        request.send(new String[]{selects}, new ResponseCallback(request) {
//            @Override
//            public void onBack(Response response) {
//                QuoteResponse reponse = (QuoteResponse) response;
//                view.onSelectedSuccess(DataConvert.QuoteItemList(reponse.quoteItems));
//            }
//
//            @Override
//            public void onError(int i, String s) {
//                LogUtils.e(TAG,"code = "+i+"  ,message = "+s);
//            }
//        });
    }

   /* *//**
     * 查询指数
     *//*
    @Override
    public void onQueryIndex(){
        QuoteRequest request = new QuoteRequest();
        request.send(quoteIndex, null, new ResponseCallback() {
            @Override
            public void onBack(Response response) {
                QuoteResponse reponse = (QuoteResponse) response;
                view.onIndexTcp(DataConvert.QuoteItemList(reponse.quoteItems));
            }

            @Override
            public void onError(int i, String s) {

            }
        });
    }*/

}
