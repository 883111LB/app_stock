package com.cvicse.stock.markets.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cvicse.base.utils.StringUtils;
import com.cvicse.stock.R;
import com.cvicse.stock.base.PBaseAdapter;
import com.cvicse.stock.common.ColorUtils;
import com.cvicse.stock.common.FillConfig;
import com.cvicse.stock.util.FormatUtils;
import com.cvicse.stock.utils.TextUtils;
import com.mitake.core.QuoteItem;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ruan_ytai on 17-3-20.
 */

public class OptionListAdapter extends PBaseAdapter {

    private ArrayList<QuoteItem> list;

    public OptionListAdapter(Context context) {
        super(context);
    }

    public void setData(ArrayList<QuoteItem> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public QuoteItem getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if(convertView == null){
            convertView = mLayoutInflater.inflate(R.layout.option_list_item1, parent, false);
            viewHolder = new ViewHolder(convertView);

            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        QuoteItem quoteItem = list.get(position);

        TextUtils.setText(viewHolder.mTevName,quoteItem.name, FillConfig.DOUBLE_LINE);
        TextUtils.setText(viewHolder.mTevId,quoteItem.id,FillConfig.DOUBLE_LINE);

        if(TextUtils.setText(viewHolder.mTevLastPrice,quoteItem.lastPrice,FillConfig.DOUBLE_LINE)){
            if(quoteItem.change.startsWith("+")){
                viewHolder.mTevLastPrice.setTextColor(ColorUtils.UP);
            }else if(quoteItem.change.startsWith("-")){
                viewHolder.mTevLastPrice.setTextColor(ColorUtils.DOWN);
            }
        }

        //TextUtils.setText(viewHolder.mTevChangeRate,quoteItem.changeRate,"--");
        if(TextUtils.setTextPercent(viewHolder.mTevChangeRate,quoteItem.changeRate)){
            if(quoteItem.changeRate.startsWith("+")){
                viewHolder.mTevChangeRate.setTextColor(ColorUtils.UP);
            }else if(quoteItem.changeRate.startsWith("-")){
                viewHolder.mTevChangeRate.setTextColor(ColorUtils.DOWN);
            }
        }

        //TextUtils.setText(viewHolder.mTevChange,quoteItem.change,"--");
        if(TextUtils.setText(viewHolder.mTevChange,quoteItem.change,FillConfig.DOUBLE_LINE)){
            if(quoteItem.change.startsWith("+")){
                viewHolder.mTevChange.setTextColor(ColorUtils.UP);
            }else if(quoteItem.change.startsWith("-")){
                viewHolder.mTevChange.setTextColor(ColorUtils.DOWN);
            }
        }

        if(TextUtils.setText(viewHolder.mTevBuyprices,quoteItem.buyPrice,FillConfig.DOUBLE_LINE)){
            if(quoteItem.change.startsWith("+")){
                viewHolder.mTevBuyprices.setTextColor(ColorUtils.UP);
            }else if(quoteItem.change.startsWith("-")){
                viewHolder.mTevBuyprices.setTextColor(ColorUtils.DOWN);
            }
        }
        if(TextUtils.setText(viewHolder.mTevSellprices,quoteItem.sellPrice,FillConfig.DOUBLE_LINE)){
            if(quoteItem.change.startsWith("+")){
                viewHolder.mTevSellprices.setTextColor(ColorUtils.UP);
            }else if(quoteItem.change.startsWith("-")){
                viewHolder.mTevSellprices.setTextColor(ColorUtils.DOWN);
            }
        }
        //TextUtils.setText(viewHolder.mTevVolume, FormatUtils.getVol(quoteItem.volume),"--");
        if(!StringUtils.isEmpty(quoteItem.volume)){
//            TextUtils.setText(viewHolder.mTevVolume,FormatUtils.getVol(Long.parseLong(quoteItem.volume)/100+""),"--");  // old
            TextUtils.setText(viewHolder.mTevVolume,FormatUtils.format(quoteItem.volume),FillConfig.DOUBLE_LINE); // new
        } else{
            viewHolder.mTevVolume.setText(FillConfig.DOUBLE_LINE);
        }

        TextUtils.setText(viewHolder.mTevAmount,FormatUtils.format(quoteItem.amount),FillConfig.DOUBLE_LINE);

        TextUtils.setText(viewHolder.mTevCcl,quoteItem.openInterest,FillConfig.DOUBLE_LINE);

        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.tev_name) TextView mTevName;
        @BindView(R.id.tev_id) TextView mTevId;
        @BindView(R.id.tev_lastPrice) TextView mTevLastPrice;
        @BindView(R.id.tev_changeRate) TextView mTevChangeRate;
        @BindView(R.id.tev_change) TextView mTevChange;
        @BindView(R.id.tev_buyprices) TextView mTevBuyprices;
        @BindView(R.id.tev_sellprices) TextView mTevSellprices;
        @BindView(R.id.tev_volume) TextView mTevVolume;
        @BindView(R.id.tev_amount) TextView mTevAmount;
        //持仓量
        @BindView(R.id.tev_openinterest) TextView mTevCcl;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
