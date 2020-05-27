package com.cvicse.stock.markets.ui.other_option.presenter;

import android.util.Log;

import com.cvicse.base.mvp.BasePresenter;
import com.cvicse.stock.markets.ui.other_option.presenter.contract.DCEOption1Contract;
import com.mitake.core.CateType;
import com.mitake.core.QuoteItem;
import com.mitake.core.bean.log.ErrorInfo;
import com.mitake.core.request.CateSortingRequest;
import com.mitake.core.response.CateSortingResponse;
import com.mitake.core.response.IResponseInfoCallback;

import java.util.ArrayList;

import static com.cvicse.stock.markets.data.ParamUtil.STOCK_NAME_ZCEQQ;

/**
 * 大商所、郑商所期权 商品行情页面
 * Created by tang_hua on 2020/3/26.
 */

public class DCEOption1Presenter extends BasePresenter implements DCEOption1Contract.Presenter {

    DCEOption1Contract.View view;

    @Override
    public void setView(DCEOption1Contract.View view) {
        this.view = view;
    }

    /**
     * 请求连续合约列表
     * @param stockType 大商所/郑商所
     * @param ID 点击的列表项的id，如果是从更多页面进入的为null
     */
    @Override
    public void cateSortingRequest(String stockType, String ID) {
        String flag = null;
        if(null == ID) {
            if (stockType.equals(STOCK_NAME_ZCEQQ)) {
                // 郑商所期权
                flag = CateType.CZCE_UNDERLYING;
            } else {
                // 大商所期权
                flag = CateType.DCE_UNDERLYING;
            }
        } else {
            String[] stockIdSp = ID.split("\\.");
            if (stockIdSp.length > 1 && (stockIdSp[1].equals("dce"))) {
                // 大商所期权的参数格式：dce_expiremonth_加上id第一位
                flag = "dce_expiremonth_" + ID.substring(0,1);
            } else if (stockIdSp.length > 1 && stockIdSp[1].equals("czce")) {
                // 郑商所期权的参数格式：czce_expiremonth_加上id前两位
                flag = "czce_expiremonth_" + ID.substring(0,2);
            } else {
                return;
            }
        }
        CateSortingRequest catesortingRequest = new CateSortingRequest();
        // 默认每页20条，按照最新价排序
        catesortingRequest.send(flag, "0,99,7,0,1", new IResponseInfoCallback<CateSortingResponse>(){
            public void callback(CateSortingResponse catesortingResponse){
                ArrayList<QuoteItem> list = catesortingResponse.list;
                view.cateSortingRequestSuccess(list);
            }

            public void exception(ErrorInfo errorInfo){
                // 错误处理
                view.cateSortingRequestFail(errorInfo.getMessage());
            }
        });
    }
}
