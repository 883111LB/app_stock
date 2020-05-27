package com.cvicse.stock.markets.adapter;

import android.content.Context;
import android.content.res.ColorStateList;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cvicse.stock.R;
import com.cvicse.stock.base.PBaseAdapter;
import com.cvicse.stock.common.ColorUtils;
import com.cvicse.stock.utils.TextUtils;
import com.mitake.core.BrokerInfoItem;
import com.mitake.core.response.QuoteResponse;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by liu_zlu on 2017/3/24 13:45
 */
public class StockBrokerInfoAdapter extends PBaseAdapter {

    private ArrayList<BrokerInfoItem> buyBrokerInfoItems = new ArrayList<>();
    private ArrayList<BrokerInfoItem> sellBrokerInfoItems = new ArrayList<>();

    private ColorStateList mColor = null;
    public StockBrokerInfoAdapter(Context context) {
        super(context);
    }

    public void setData(QuoteResponse quoteResponse){
        if(quoteResponse.BrokerInfoListBuy != null){
            buyBrokerInfoItems.clear();
            buyBrokerInfoItems.addAll(quoteResponse.BrokerInfoListBuy);
        }
        if(quoteResponse.BrokerInfoListSell != null){
            sellBrokerInfoItems.clear();
            sellBrokerInfoItems.addAll(quoteResponse.BrokerInfoListSell);
        }
        notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        return Math.max(buyBrokerInfoItems.size(), sellBrokerInfoItems.size());
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.item_lv_brokerinfo, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        setLeft(position,viewHolder);
        setRight(position,viewHolder);
        return convertView;
    }

    /**
     * 设置卖盘数据
     * @param position
     * @param viewHolder
     */
    private void setLeft(int position,ViewHolder viewHolder){
        if(sellBrokerInfoItems.size() > position){
            BrokerInfoItem brokerInfoItem = sellBrokerInfoItems.get(position);
            String id = brokerInfoItem.id;
            if(null != id && id.contains("S")){
                viewHolder.tevSellId.setTextColor(ColorUtils.UP);
            } else {
                viewHolder.tevSellId.setTextColor(mColor);
            }
            TextUtils.setText(viewHolder.tevSellId, id);
            TextUtils.setText(viewHolder.tevSellCompany,brokerInfoItem.corp,"");
        } else {
            TextUtils.setText(viewHolder.tevSellId,"","");
            TextUtils.setText(viewHolder.tevSellCompany,"","");
        }
    }
    /**
     * 设置买盘数据
     * @param position
     * @param viewHolder
     */
    private void setRight(int position,ViewHolder viewHolder){
        if(buyBrokerInfoItems.size() > position){
            BrokerInfoItem brokerInfoItem = buyBrokerInfoItems.get(position);
            String id = brokerInfoItem.id;
            if(null != id && id.contains("S")){
                TextUtils.setText(viewHolder.tevBuyId, id);
                viewHolder.tevBuyId.setTextColor(ColorUtils.UP);
            } else {
                viewHolder.tevBuyId.setTextColor(mColor);
            }
            TextUtils.setText(viewHolder.tevBuyId, id);
            TextUtils.setText(viewHolder.tevBuyCompany,brokerInfoItem.corp,"");
        } else {
            TextUtils.setText(viewHolder.tevBuyId,"","");
            TextUtils.setText(viewHolder.tevBuyCompany,"","");
        }
    }
    class ViewHolder {
        @BindView(R.id.tev_sell_id)
        TextView tevSellId;
        @BindView(R.id.tev_sell_company)
        TextView tevSellCompany;
        @BindView(R.id.tev_buy_id)
        TextView tevBuyId;
        @BindView(R.id.tev_buy_company)
        TextView tevBuyCompany;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
            if(mColor == null){
                mColor = tevSellId.getTextColors();
            }
        }
    }
}
