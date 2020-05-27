package com.cvicse.stock.markets.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cvicse.stock.R;
import com.cvicse.stock.base.PBaseAdapter;
import com.cvicse.stock.util.FormatUtils;
import com.cvicse.stock.utils.TextUtils;
import com.mitake.core.QuoteItem;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 复盘涨跌-行情概览
 * Created by liu_zlu on 2017/3/13 20:50
 */
public class FpzdTopAdapter extends PBaseAdapter {

    private ArrayList<QuoteItem> quoteItems;
    private int[] imgUpOrDown = { R.drawable.up, R.drawable.down };
    private int count = 0;

    public FpzdTopAdapter(Context context) {
        super(context);
    }

    public void setData(ArrayList<QuoteItem> quoteItems){
        this.quoteItems = quoteItems;
        notifyDataSetChanged();
    }
    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public int getCount() {
        return count;
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
            convertView = mLayoutInflater.inflate(R.layout.item_fp_hqgl_top, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        if(quoteItems != null && quoteItems.size() > position){
            QuoteItem quoteItem = quoteItems.get(position);
            if(quoteItem == null){
                return convertView;
            }
            if(quoteItem.change != null && quoteItem.change.startsWith("+")){
                viewHolder.tevIndex1.setTextColor(Color.parseColor("#d83f31"));
                viewHolder.tevChangerate1.setTextColor(Color.parseColor("#d83f31"));
                TextUtils.setText(viewHolder.tevChangerate1, "+" + FormatUtils.formatPercent(quoteItem.changeRate));
            } else if(quoteItem.change != null){
                viewHolder.tevIndex1.setTextColor(Color.parseColor("#2d7c2d"));
                if ("0".equals(quoteItem.change)) {
                    viewHolder.tevChangerate1.setTextColor(Color.parseColor("#999999"));
                    TextUtils.setText(viewHolder.tevChangerate1, FormatUtils.formatPercent(quoteItem.changeRate));
                } else {
                    viewHolder.tevChangerate1.setTextColor(Color.parseColor("#2d7c2d"));
                    TextUtils.setText(viewHolder.tevChangerate1, "-" + FormatUtils.formatPercent(quoteItem.changeRate));
                }
            }
            TextUtils.setText(viewHolder.tevName1,quoteItem.name);
//            TextUtils.setText(viewHolder.tevChangerate1, FormatUtils.formatPercent(quoteItem.changeRate));
            TextUtils.setText(viewHolder.tevIndex1,quoteItem.lastPrice);
        }
        return convertView;
    }

    public ArrayList<QuoteItem> getData() {
        return quoteItems;
    }

    static class ViewHolder {
        @BindView(R.id.tev_name1) TextView tevName1;
        @BindView(R.id.tev_index1) TextView tevIndex1;
        @BindView(R.id.tev_changerate1) TextView tevChangerate1;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
