package com.cvicse.stock.http.loop;

import com.cvicse.base.utils.StringUtils;
import com.mitake.core.AppInfo;
import com.mitake.core.QuoteItem;
import com.mitake.core.request.QuoteDetailRequest;
import com.mitake.core.response.QuoteResponse;
import com.mitake.core.response.Response;

import java.util.ArrayList;

/**
 * Created by liu_zlu on 2017/2/28 09:31
 * 行情快照循环请求
 */
public abstract class QuoteDetailRunable extends RunTaskState<QuoteResponse> {

    private String code;
    private int type = -1;
    private QuoteItem quoteItem;
    //明细个数
    private String count;
    // 中金所自定义栏位
    private String selectCff;
    // 是否是中金所
    private boolean isCff = false;

    public QuoteDetailRunable(String code){
        this.code = code;
        if(AppInfo.getInfoLevel().equals("2")){
            this.type = 10;
            this.count =  "20";
        }

        isCff = false;
        if(null != code && code.endsWith("cff") ){
            isCff = true;
        }
    }

    public QuoteDetailRunable(String code,int type){
        this.code = code;
        this.type = type;
    }

    public QuoteDetailRunable(String code, String selectCff){
        this.code = code;
        this.selectCff = selectCff;
        isCff = true;
    }

    @Override
    public void runInner() {
        QuoteDetailRequest quoteDetailRequest = new QuoteDetailRequest();
        this.setRequest(quoteDetailRequest);
        if( isCff ){
            quoteDetailRequest.send(code,selectCff,this);
        }else {
            if (type > 0 && null == count) {
                quoteDetailRequest.send(code, null, this);
            } else if (type > 0 && count != null) {
                quoteDetailRequest.send(code,count, null, null, this);
            } else {
                quoteDetailRequest.send(code, this);
            }
        }
    }

    @Override
    public void onBack(Response response) {
        ArrayList<QuoteItem> quoteItems = ((QuoteResponse)response).quoteItems;
        if( null!=quoteItems  && quoteItems.size() > 0){

            QuoteItem quoteItem = quoteItems.get(0);

            if(this.quoteItem == null){
                this.quoteItem = quoteItem;
                super.onBack(response);
            } else if(quoteItem != null){
                /**
                 * datetime 为此种形式20170706094633
                 */
                //int newTime = NumberUtils.parseInt(quoteItem.datetime);
                //int oldTime = NumberUtils.parseInt(this.quoteItem.datetime);
                if(StringUtils.isEmpty(quoteItem.datetime) || "null".equals(quoteItem.datetime)){
                    return;
                }
                long newTime = Long.parseLong(quoteItem.datetime);
                long oldTime = null == this.quoteItem.datetime ? 0L : Long.parseLong(this.quoteItem.datetime);

                if(newTime > oldTime){
                    this.quoteItem = quoteItem;
                    super.onBack(response);
                }
            }
        }
    }
}
