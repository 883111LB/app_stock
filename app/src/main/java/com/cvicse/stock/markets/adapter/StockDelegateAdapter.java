package com.cvicse.stock.markets.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cvicse.stock.R;
import com.cvicse.stock.base.PBaseAdapter;
import com.cvicse.stock.utils.TextUtils;
import com.mitake.core.QuoteItem;
import com.mitake.core.response.OrderQuantityResponse;
import com.mitake.core.util.FormatUtility;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 委托队列适配器
 * Created by liu_zlu on 2017/3/24 09:16
 */
public class StockDelegateAdapter extends PBaseAdapter {

    private ArrayList<String> buySingleVolumes;
    private ArrayList<String> sellSingleVolumes;
    private QuoteItem quoteItem;

    public StockDelegateAdapter(Context context,QuoteItem item) {
        super(context);
        quoteItem = item;
    }

    public void setData(OrderQuantityResponse quoteResponse) {
        if(quoteResponse == null){
            return;
        }
        if(quoteResponse.buyList != null &&  quoteResponse.buyList.size()>0
                && quoteResponse.buyList.get(0) != null){
            buySingleVolumes = quoteResponse.buyList.get(0).QUANTITY_;
        }
        if(quoteResponse.sellList != null && quoteResponse.sellList.size() > 9 && quoteResponse.sellList.get(9) != null){
            sellSingleVolumes = quoteResponse.sellList.get(9).QUANTITY_;
        }
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return (int) Math.max(buySingleVolumes == null ? 0 : Math.ceil(buySingleVolumes.size()/3f), sellSingleVolumes == null ? 0 : Math.ceil(sellSingleVolumes.size()/3f));
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
            convertView = mLayoutInflater.inflate(R.layout.item_list_detagate, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        setPosition(viewHolder.tevSell1,sellSingleVolumes,position*3);
        setPosition(viewHolder.tevSell2,sellSingleVolumes,position*3+1);
        setPosition(viewHolder.tevSell3,sellSingleVolumes,position*3+2);

        setPosition(viewHolder.tevBuy1,buySingleVolumes,position*3);
        setPosition(viewHolder.tevBuy2,buySingleVolumes,position*3+1);
        setPosition(viewHolder.tevBuy3,buySingleVolumes,position*3+2);
        return convertView;
    }

    static class ViewHolder {
        // 每一行第一个卖量
        @BindView(R.id.tev_sell_1) TextView tevSell1;
        //　每一行第二个卖量
        @BindView(R.id.tev_sell_2) TextView tevSell2;
        //　每一行第三个卖量
        @BindView(R.id.tev_sell_3) TextView tevSell3;
        //　每一行第一个买量
        @BindView(R.id.tev_buy_1) TextView tevBuy1;
        //　每一行第二个买量
        @BindView(R.id.tev_buy_2) TextView tevBuy2;
        //　每一行第三个买量
        @BindView(R.id.tev_buy_3) TextView tevBuy3;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    private void setPosition(TextView textView,ArrayList<String> strings,int position){
        if(strings != null && strings.size() > position ){
            TextUtils.setText(textView, FormatUtility.formatVolume(strings.get(position),
                    quoteItem.market,quoteItem.subtype));
        } else {
            TextUtils.setText(textView," "," ");
        }
    }
}
