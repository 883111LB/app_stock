package com.cvicse.stock.http.loop;

//import com.mitake.core.keys.quote.QuoteCustomField;
import com.mitake.core.keys.quote.QuoteCustomField;
import com.mitake.core.request.CateSortingRequest;
import com.mitake.core.response.CateSortingResponse;

import static com.mitake.core.keys.quote.BaseTypeCodes.ALL_COLUMN;

/**
 * Created by liu_zlu on 2017/3/16 21:14
 */
public abstract class CateSortingRunnable  extends RunTaskState<CateSortingResponse> {
    private String code;
    private String param;
    private int[] quoteCustom ={-1};
//    private int[] quoteCustom ={-1};
    private int[] addvalueCustom = null;

    public CateSortingRunnable() {
    }

    public CateSortingRunnable(String type, String param){
        this.code = type;
        this.param = param;
    }

    public CateSortingRunnable(String type, String param, int[] quoteCustom, int[] addvalueCustom) {
        this.code = type;
        this.param = param;
        this.quoteCustom = quoteCustom;
        this.addvalueCustom = addvalueCustom;
    }

    public void setQuoteCustom(int[] quoteCustom){
        this.quoteCustom = quoteCustom;
    }

    public void setAddvalueCustom(int[] addvalueCustom){
        this.addvalueCustom = addvalueCustom;
    }

    public void setCustom(int[] quoteCustom,int[] addvalueCustom){
        this.quoteCustom = quoteCustom;
        this.addvalueCustom = addvalueCustom;
    }

    @Override
    public void runInner() {
        CateSortingRequest cateSortingRequest = new CateSortingRequest();
        this.setRequest(cateSortingRequest);
        if(null == quoteCustom || quoteCustom.length <= 0){
            cateSortingRequest.send(code, param,this);
        }else{
            cateSortingRequest.send(code, param,quoteCustom,addvalueCustom,this);
        }
    }
}
