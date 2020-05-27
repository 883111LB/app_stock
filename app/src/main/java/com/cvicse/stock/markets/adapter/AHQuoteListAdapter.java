package com.cvicse.stock.markets.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cvicse.stock.R;
import com.cvicse.stock.base.PBaseAdapter;
import com.cvicse.stock.utils.TextUtils;
import com.mitake.core.bean.AHQuoteItem;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by tang_xqing on 2018/5/14.
 */
public class AHQuoteListAdapter extends PBaseAdapter {

    private List<AHQuoteItem> mAHQuoteItemList;

    public AHQuoteListAdapter(Context context) {
        super(context);
    }

    public void setData(List<AHQuoteItem> ahQuoteItemList) {
        this.mAHQuoteItemList = ahQuoteItemList;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return null == mAHQuoteItemList ? 0 : mAHQuoteItemList.size();
    }

    @Override
    public Object getItem(int position) {
        return null == mAHQuoteItemList ? null : mAHQuoteItemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return null == mAHQuoteItemList ? 0 : position;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (null == convertView) {
            convertView = mLayoutInflater.inflate(R.layout.ah_quote_list_item, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        final AHQuoteItem ahQuoteItem = mAHQuoteItemList.get(position);
        TextUtils.setText(viewHolder.tevName, ahQuoteItem.name);
        TextUtils.setText(viewHolder.tevLastPriceA, ahQuoteItem.lastPriceA);
        TextUtils.setText(viewHolder.tevChangeRateA, ahQuoteItem.changeRateA);
        TextUtils.setText(viewHolder.tevLastPriceH, ahQuoteItem.lastPriceH);
        TextUtils.setText(viewHolder.tevChangeRateH, ahQuoteItem.changeRateH);
        TextUtils.setText(viewHolder.tevPremiumAh, ahQuoteItem.premiumAH);
        TextUtils.setText(viewHolder.tevClosePriceA, ahQuoteItem.preClosePriceA);
        TextUtils.setText(viewHolder.tevClosePriceH, ahQuoteItem.preClosePriceH);

        final View finalConvertView = convertView;
        viewHolder.tevLastPriceA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAHItemClick.onAHItemClick(ahQuoteItem.codeA,position,true);
            }
        });
        viewHolder.tevChangeRateA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAHItemClick.onAHItemClick(ahQuoteItem.codeA,position,true);
            }
        });
        viewHolder.tevClosePriceA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAHItemClick.onAHItemClick(ahQuoteItem.codeA,position,true);
            }
        });

        viewHolder.tevLastPriceH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAHItemClick.onAHItemClick(ahQuoteItem.codeH,position,false);
            }
        });
        viewHolder.tevChangeRateH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAHItemClick.onAHItemClick(ahQuoteItem.codeH,position,false);
            }
        });
        viewHolder.tevClosePriceH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAHItemClick.onAHItemClick(ahQuoteItem.codeH ,position,false);
            }
        });
        return convertView;
    }

    private AHItemClick mAHItemClick;

    public void setAHItemClick(AHItemClick ahItemClick){
        this.mAHItemClick = ahItemClick;
    }

    public interface AHItemClick{
        void onAHItemClick(String code, int position, boolean isAQuote);
    }

    class ViewHolder {
        @BindView(R.id.tev_name)
        TextView tevName;
        @BindView(R.id.tev_lastPrice_A)
        TextView tevLastPriceA;
        @BindView(R.id.tev_changeRate_A)
        TextView tevChangeRateA;
        @BindView(R.id.tev_closePrice_A)
        TextView tevClosePriceA;
        @BindView(R.id.tev_lastPrice_H)
        TextView tevLastPriceH;
        @BindView(R.id.tev_changeRate_H)
        TextView tevChangeRateH;
        @BindView(R.id.tev_closePrice_H)
        TextView tevClosePriceH;
        @BindView(R.id.tev_premium_ah)
        TextView tevPremiumAh;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
