package com.cvicse.stock.markets.helper;

import android.view.View;
import android.widget.TextView;

import com.cvicse.stock.R;
import com.cvicse.stock.utils.TextUtils;
import com.mitake.core.bean.UpdownsItem;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 指数涨跌数帮助类
 * Created by liu_zlu on 2017/3/1 11:46
 */
public class StockViewIndex {
    //涨家数
    @BindView(R.id.tev_up_num) TextView tevUpNum;
    //平家数
    @BindView(R.id.tev_nomarl_num) TextView tevNomarlNum;
    //跌家数
    @BindView(R.id.tev_down_num) TextView tevDownNum;
    StockViewIndex(View view){
        ButterKnife.bind(this, view);
    }

    /**
     * 更新股票数据
     * @param updownsItem
     */
/*    protected void setData(QuoteItem quoteItem){
        if(quoteItem == null){
            return;
        }
        TextUtils.setText(tevUpNum,quoteItem.upCount);
        TextUtils.setText(tevNomarlNum,quoteItem.sameCount);
        TextUtils.setText(tevDownNum,quoteItem.downCount);
    }*/

    protected void setData(UpdownsItem updownsItem){
        if( null == updownsItem){
            return;
        }
        TextUtils.setText(tevUpNum,updownsItem.upCount);
        TextUtils.setText(tevNomarlNum,updownsItem.sameCount);
        TextUtils.setText(tevDownNum,updownsItem.downCount);
    }
}
