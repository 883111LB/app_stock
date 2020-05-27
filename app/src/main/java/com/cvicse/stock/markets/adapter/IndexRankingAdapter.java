package com.cvicse.stock.markets.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cvicse.stock.R;
import com.cvicse.stock.base.PBaseAdapter;
import com.cvicse.stock.common.ColorUtils;
import com.cvicse.stock.markets.ui.stockdetail.IndexRankingFragment;
import com.cvicse.stock.util.FormatUtils;
import com.cvicse.stock.utils.TextUtils;
import com.mitake.core.QuoteItem;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by liu_zlu on 2017/2/13 13:44
 */
public class IndexRankingAdapter extends PBaseAdapter {

    private ArrayList<QuoteItem> quoteItems;
    private String[] ids;
    private String[] datas;
    private String[] rates;
    private String type;

    public IndexRankingAdapter(Context context,String type) {
        super(context);
        this.type = type;
    }

    public ArrayList<QuoteItem> getData() {
        return quoteItems;
    }

    public void setData(ArrayList<QuoteItem> quoteItems){
        //this.quoteItems = DataConvert.QuoteItemList(quoteItems);
        this.quoteItems = quoteItems;
        if(quoteItems != null && quoteItems.size() > 0){
            ids = new String[quoteItems.size()];
            datas = new String[quoteItems.size()];
            rates = new String[quoteItems.size()];
        }
        notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        return quoteItems == null ? 0 : quoteItems.size();
    }

    @Override
    public QuoteItem getItem(int position) {
        return quoteItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            int color = 0;
            convertView = mLayoutInflater.inflate(R.layout.item_stock_ranking, parent, false);
            viewHolder = new ViewHolder(convertView);
            switch (type){
                case IndexRankingFragment.TYPEINCREASE:
                    color = ColorUtils.UP;
                    break;
                case IndexRankingFragment.TYPEDECLINE:
                    color = ColorUtils.DOWN;
                    break;
                case IndexRankingFragment.TYPETURNOVERRATE:
                    // color = ContextCompat.getColor(mContext,R.color.text_white);
                    break;
                case IndexRankingFragment.TYPETURNOVER:
                    //color = ContextCompat.getColor(mContext,R.attr.text_withe_black);
                    break;
            }
            if(color != 0){
                viewHolder.tevData.setTextColor(color);
                viewHolder.tevDatarate.setTextColor(color);
            }
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        QuoteItem quoteItem = quoteItems.get(position);
        if( null == quoteItem.id ||  null == quoteItem.name){
            return convertView;
        }
        TextUtils.setText(viewHolder.tevName,quoteItem.name);

        String temp = ids[position];

        if( null == temp){
            temp = quoteItem.id.substring(0,quoteItem.id.length()-3);
            ids[position] = temp;
        }
        TextUtils.setText(viewHolder.tevCode,temp);
        TextUtils.setText(viewHolder.tevData,quoteItem.lastPrice);

        temp = rates[position];

        if( null == temp){
            if(type.equals(IndexRankingFragment.TYPETURNOVER)){
                temp = FormatUtils.format(quoteItem.amount);
            } else {
                temp = quoteItem.changeRate+"%";
            }
            rates[position] = temp;

        }

        TextUtils.setText(viewHolder.tevDatarate,temp);
        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.tev_name)
        TextView tevName;
        @BindView(R.id.tev_code)
        TextView tevCode;
        @BindView(R.id.tev_data)
        TextView tevData;
        @BindView(R.id.tev_datarate)
        TextView tevDatarate;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
