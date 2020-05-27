package com.cvicse.stock.http.loop;

import com.mitake.core.QuoteItem;
import com.mitake.core.request.ChartSubRequest;
import com.mitake.core.response.ChartSubResponse;

/** 走势副图
 * Created by liu_zlu on 2017/5/1 17:34
 */
public abstract class ChartSubRunnable extends RunTaskState<ChartSubResponse> {
    private int indexType = -1;
    private String type;
    private QuoteItem quoteItem;

    public ChartSubRunnable(QuoteItem quoteItem,String type){
        this.quoteItem = quoteItem;
        this.type = type;
    }

    public ChartSubRunnable(QuoteItem quoteItem,String type,int indexType){
        this.quoteItem = quoteItem;
        this.type = type;
        this.indexType = indexType;
    }
    @Override
    public void runInner() {
        ChartSubRequest chartSubRequest = new ChartSubRequest();
        this.setRequest(chartSubRequest);

        if(null == quoteItem){
            return;
        }

        if( -1 == indexType ){
            chartSubRequest.send(quoteItem,type,this);
        }else{
            chartSubRequest.send(quoteItem,type, 0, -1, getSelect(indexType),this);
        }
    }

    private String getSelect(int indexType){
        String select = null;
        switch (indexType){
            case 1: select = "time,ddx";break;
            case 2: select = "time,ddy";break;
            case 3: select = "time,ddz";break;
            case 4: select = "time,bbd";break;
            case 5: select = "time,ratioBS";break;
            case 6: select = "time,largeMoneyInflow,bigMoneyInflow,midMoneyInflow,smallMoneyInflow";break;
            case 7: select = "time,largeTradeNum,bigTradeNum,midTradeNum,smallTradeNum";break;
            case 8: select="time,bigNetVolume";break;
            case 9: select="time,largeAmountMoney";break;
        }
       return select;
    }
}

