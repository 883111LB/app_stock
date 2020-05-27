package com.cvicse.stock.http.loop;

import android.text.TextUtils;

import com.mitake.core.QuoteItem;
import com.mitake.core.request.OHLCRequest;
import com.mitake.core.request.OHLCRequestV3;
import com.mitake.core.response.OHLCResponse;
import com.stock.config.KSetting;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by liu_zlu on 2017/5/16 13:48
 */
public abstract class OHLCRunnable extends RunTaskState<OHLCResponse> {
    private QuoteItem quoteItem;
    private String ktype;
    private String dateTime;

    public OHLCRunnable(QuoteItem quoteItem, String ktype,String dateTime){
        this.ktype = ktype;
        this.quoteItem = quoteItem;
        this.dateTime = TextUtils.isEmpty(dateTime) ? getDateTime() : dateTime;
    }

    @Override
    public void runInner() {
        String id = quoteItem.id;
        if( null == id || null == ktype){
            return;
        }
        int rightType = KSetting.getKRightSubType();
        if( id.contains("hk") || id.contains("bj")){
            OHLCRequest ohlcRequest = new OHLCRequest();
            setRequest(ohlcRequest);
            ohlcRequest.send(id,ktype, rightType -1,this);
        }else{
            // 历史K线
            OHLCRequestV3 ohlcRequestV3 = new OHLCRequestV3();
            setRequest(ohlcRequestV3);
            ohlcRequestV3.send(id,ktype, rightType -1,null,this);
//            ohlcRequestV3.send(id,dateTime,ktype, rightType -1,this);
        }
    }

    private String getDateTime(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddhhmmss");
        return dateFormat.format(new Date(System.currentTimeMillis()));
    }
}