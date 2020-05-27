package com.cvicse.stock.markets.helper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cvicse.stock.R;
import com.cvicse.stock.utils.TextUtils;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/** 龙虎榜
 * Created by tang_xqing on 2018/5/2.
 */

public class StockChartsBuySellHelper {
    private Context mContext;
    // 买入
    @BindView(R.id.lly_buy)
    LinearLayout llyBuy;

    // 卖出
    @BindView(R.id.lly_sell)
    LinearLayout llySell;

    public StockChartsBuySellHelper(View view, Context context){
        ButterKnife.bind(this, view);

        this.mContext = context;
    }

    public void onCharts5BuysSuccess(List<HashMap<String, Object>> buyList) {
        llyBuy.removeAllViews();

        if( null != buyList && !buyList.isEmpty()){
            for (HashMap<String, Object> objectHashMap : buyList) {
                ViewGroup viewGroup = (ViewGroup) LayoutInflater.from(mContext).inflate(R.layout.item_charts_buy_sell,llyBuy,false);
                TextUtils.setText((TextView)viewGroup.findViewById(R.id.tev_time), (String) objectHashMap.get("TRADEDATE"));
                TextUtils.setText((TextView)viewGroup.findViewById(R.id.tev_name), (String) objectHashMap.get("BIZSUNITNAME"));
                TextUtils.setText((TextView)viewGroup.findViewById(R.id.tev_buy_price), (String) objectHashMap.get("BUYAMT"));
                TextUtils.setText((TextView)viewGroup.findViewById(R.id.tev_sell_price), (String) objectHashMap.get("SALEAMT"));
                llyBuy.addView(viewGroup);
            }
        }
    }

    public void onCharts5SellsSuccess(List<HashMap<String, Object>> sellsList) {
        llySell.removeAllViews();

        if( null != sellsList && !sellsList.isEmpty())
            for (HashMap<String, Object> objectHashMap : sellsList) {
                ViewGroup viewGroup = (ViewGroup) LayoutInflater.from(mContext).inflate(R.layout.item_charts_buy_sell,llySell,false);
                TextUtils.setText((TextView)viewGroup.findViewById(R.id.tev_time), (String) objectHashMap.get("TRADEDATE"));
                TextUtils.setText((TextView)viewGroup.findViewById(R.id.tev_name), (String) objectHashMap.get("BIZSUNITNAME"));
                TextUtils.setText((TextView)viewGroup.findViewById(R.id.tev_buy_price), (String) objectHashMap.get("BUYAMT"));
                TextUtils.setText((TextView)viewGroup.findViewById(R.id.tev_sell_price), (String) objectHashMap.get("SALEAMT"));
                llySell.addView(viewGroup);
            }
    }
}
