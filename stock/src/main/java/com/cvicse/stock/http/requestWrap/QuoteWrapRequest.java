package com.cvicse.stock.http.requestWrap;

import android.support.v4.util.ArrayMap;
import android.text.TextUtils;
import android.util.SparseArray;

import com.cvicse.stock.http.ResponseCallback;
import com.cvicse.stock.http.loop.QuoteRunable;
import com.mitake.core.QuoteItem;
import com.mitake.core.request.QuoteRequest;
import com.mitake.core.response.QuoteResponse;
import com.mitake.core.response.Response;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by liu_zlu on 2017/3/20 11:40
 */
public class QuoteWrapRequest {

    private ArrayList<String> markets = new ArrayList<>();
    private ArrayList<String> idList = new ArrayList<>();
    private ArrayMap<String,String> arrayId = new ArrayMap<>();
    private ArrayMap<String,QuoteItem> quoteItemArrayMap = new ArrayMap<>();
    private ArrayMap<String,StringBuffer> stringBufferArrayMap = new ArrayMap<>();
    private volatile AtomicInteger requestNum;


    private ResponseCallback responseCallback;
    public QuoteWrapRequest(String id,ResponseCallback responseCallback){
        this.responseCallback = responseCallback;
        String[] ids = id.split(",");
        String market = "";
        StringBuffer stringBuffer;
        for(String temp : ids){
            if(TextUtils.isEmpty(temp) || "null".equals(temp)){
                continue;
            }
            idList.add(temp);
            market = temp.substring(temp.indexOf(".")+1);
            if(!markets.contains(market)){
                markets.add(market);
                stringBufferArrayMap.put(market,new StringBuffer());
            }
            stringBuffer = stringBufferArrayMap.get(market);
            stringBuffer.append(temp);
            stringBuffer.append(",");
        }
        for(String temp : markets){
            if(TextUtils.isEmpty(temp)|| "null".equals(temp)){
                continue;
            }
            stringBuffer = stringBufferArrayMap.get(temp);
            stringBuffer.setLength(stringBuffer.length()-1);
            arrayId.put(temp,stringBuffer.toString());
        }
    }


    public void request(){
        if(requestNum != null && requestNum.get() != 0){
            return;
        }
        requestNum = new AtomicInteger(markets.size());

       for(String market:markets){
            QuoteRequest quoteRequest = new QuoteRequest();
           final String[] codes = arrayId.get(market).split(",");

           quoteRequest.send(codes,null,null, new ResponseCallback(quoteRequest) {
                @Override
                public void onBack(Response response) {
                    QuoteResponse quoteResponse = (QuoteResponse) response;

                    if( null==quoteResponse  || null ==  quoteResponse.quoteItems || quoteResponse.quoteItems.isEmpty()){
                        return;
                    }
                    for(QuoteItem quoteItem:quoteResponse.quoteItems){
                        if(null != quoteItem && null!=quoteItem.id ) {
                            quoteItemArrayMap.put(quoteItem.id,quoteItem);
                        }
                    }

                    if(requestNum.decrementAndGet() == 0){
                        ArrayList<QuoteItem> quoteItems = new ArrayList<QuoteItem>();
                        QuoteItem quoteItem;
                        for(String id:idList){
                            quoteItem = quoteItemArrayMap.get(id);
                            quoteItems.add(quoteItem);
                        }
                        ((QuoteResponse) response).quoteItems = quoteItems;
                        responseCallback.callback(response);
                    }
                }

                @Override
                public void onError(int i, String s) {
                    if(requestNum.decrementAndGet() == 0){
                        responseCallback.onError(i,s);
                    }
                }
            });
        }
    }


    private String codeComp(SparseArray<String> sparseArray){
        int length = sparseArray.size();
        StringBuffer stringBuffer = new StringBuffer();
        for(int i = 0;i < length;i++){
            stringBuffer.append(sparseArray.get(i));
            stringBuffer.append(",");
        }
        stringBuffer.setLength(stringBuffer.length()-1);
        return stringBuffer.toString();
    }
}
