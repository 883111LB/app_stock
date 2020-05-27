package com.cvicse.stock.markets.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cvicse.stock.R;
import com.cvicse.stock.base.PBaseAdapter;
import com.cvicse.stock.utils.TextUtils;
import com.mitake.core.QuoteItem;
import com.mitake.core.bean.DRQuoteItem;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by shi_yhui on 2018/11/27.
 */

public class DRQuoteListAdapter extends PBaseAdapter {

    private List<DRQuoteItem> mDRQuoteItemList;
    private ArrayList<QuoteItem> mDQuoteItemList;
    private ArrayList<QuoteItem> mRQuoteItemList;

    public DRQuoteListAdapter(Context context) {
        super(context);
    }

    public void setData(List<DRQuoteItem> drQuoteItemList) {
        this.mDRQuoteItemList = drQuoteItemList;
        setQuoteDataList(drQuoteItemList);
        notifyDataSetChanged();
    }

    private void setQuoteDataList(List<DRQuoteItem> drQuoteItemList) {
        if (null != drQuoteItemList && !drQuoteItemList.isEmpty()) {
            mDQuoteItemList = new ArrayList<>();
            mRQuoteItemList = new ArrayList<>();
            QuoteItem quoteItem = null;
            for (DRQuoteItem drQuoteItem : mDRQuoteItemList) {
                quoteItem = new QuoteItem();
                String code = drQuoteItem.code;
                quoteItem.id = code;
                quoteItem.name = drQuoteItem.name;
                quoteItem.lastPrice = drQuoteItem.lastPrice;
                quoteItem.preClosePrice = drQuoteItem.preClosePrice;
                quoteItem.changeRate = drQuoteItem.changeRate;
//                quoteItem.datetime = drQuoteItem.dateTime;
                quoteItem.market = code.split("\\.")[code.split("\\.").length - 1];

                mDQuoteItemList.add(quoteItem);

                quoteItem = new QuoteItem();
                code = drQuoteItem.baseCode;
                quoteItem.id = code;
                quoteItem.name = drQuoteItem.baseName;
                quoteItem.lastPrice = drQuoteItem.baseLastPrice;
                quoteItem.preClosePrice = drQuoteItem.basePreClosePrice;
                quoteItem.changeRate = drQuoteItem.baseChangeRate;
//                quoteItem.datetime = drQuoteItem.baseDateTime;
                quoteItem.market = code.split("\\.")[code.split("\\.").length - 1];

                mRQuoteItemList.add(quoteItem);
            }
        }
    }

    public ArrayList<QuoteItem> getDQuoteData() {
        return mDQuoteItemList;
    }

    public ArrayList<QuoteItem> getRQuoteData() {
        return mRQuoteItemList;
    }

    @Override
    public int getCount() {
        return null == mDRQuoteItemList ? 0 : mDRQuoteItemList.size();
    }

    @Override
    public Object getItem(int position) {
        return null == mDRQuoteItemList ? null : mDRQuoteItemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return null == mDRQuoteItemList ? 0 : position;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (null == convertView) {
            convertView = mLayoutInflater.inflate(R.layout.dr_quote_list_item, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        DRQuoteItem drQuoteItem = mDRQuoteItemList.get(position);

        TextUtils.setText(viewHolder.tevNameDR, drQuoteItem.name);
        TextUtils.setText(viewHolder.tevCodeDR, drQuoteItem.code);
        TextUtils.setText(viewHolder.tevLastPriceDR, drQuoteItem.lastPrice);
        TextUtils.setText(viewHolder.tevPreClosePriceDR, drQuoteItem.preClosePrice);
        TextUtils.setText(viewHolder.tevChangeRateDR, drQuoteItem.changeRate);
//        TextUtils.setText(viewHolder.tevDateTimeDR, drQuoteItem.dateTime);
        TextUtils.setText(viewHolder.tevCodeBaseDR, drQuoteItem.baseCode);
        TextUtils.setText(viewHolder.tevNameBaseDR, drQuoteItem.baseName);
        TextUtils.setText(viewHolder.tevLastPriceBaseDR, drQuoteItem.baseLastPrice);
        TextUtils.setText(viewHolder.tevPreClosePriceBaseDR, drQuoteItem.basePreClosePrice);
        TextUtils.setText(viewHolder.tevChangeRateBaseDR, drQuoteItem.baseChangeRate);
//        TextUtils.setText(viewHolder.tevDateTimeBaseDR, drQuoteItem.baseDateTime);
        TextUtils.setText(viewHolder.tevPreMiumDR, drQuoteItem.premium);

        final View finalConvertView = convertView;
//        viewHolder.tevNameDR.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mDRItemClick.onDRItemClick(parent, finalConvertView,position,true);
//            }
//        });
        viewHolder.tevCodeDR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDRItemClick.onDRItemClick( parent, finalConvertView,position,true);
            }
        });
        viewHolder.tevLastPriceDR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDRItemClick.onDRItemClick( parent, finalConvertView,position,true);
            }
        });
        viewHolder.tevPreClosePriceDR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDRItemClick.onDRItemClick(parent, finalConvertView,position,false);
            }
        });
        viewHolder.tevChangeRateDR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDRItemClick.onDRItemClick(parent, finalConvertView,position,false);
            }
        });

//        viewHolder.tevDateTimeDR.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mDRItemClick.onDRItemClick(parent, finalConvertView,position,false);
//            }
//        });
        viewHolder.tevCodeBaseDR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDRItemClick.onDRItemClick( parent, finalConvertView,position,true);
            }
        });
        viewHolder.tevNameBaseDR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDRItemClick.onDRItemClick( parent, finalConvertView,position,true);
            }
        });
        viewHolder.tevLastPriceBaseDR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDRItemClick.onDRItemClick( parent, finalConvertView,position,true);
            }
        });

        viewHolder.tevPreClosePriceBaseDR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDRItemClick.onDRItemClick(parent, finalConvertView,position,false);
            }
        });

        viewHolder.tevChangeRateBaseDR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDRItemClick.onDRItemClick(parent, finalConvertView,position,false);
            }
        });

//        viewHolder.tevDateTimeBaseDR.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mDRItemClick.onDRItemClick(parent, finalConvertView,position,false);
//            }
//        });
//        viewHolder.tevPreMiumDR.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mDRItemClick.onDRItemClick(parent, finalConvertView,position,false);
//            }
//        });
        return convertView;
    }
    private DRItemClick mDRItemClick;

    public void setDRItemClick(DRItemClick drItemClick){
        this.mDRItemClick = drItemClick;
    }

    public interface DRItemClick{
        void onDRItemClick(ViewGroup parent, View view, int position, boolean isDQuote);
    }

    class ViewHolder {
        @BindView(R.id.tev_name_DR)
        TextView tevNameDR;
        @BindView(R.id.tev_code_DR)
        TextView tevCodeDR;
        @BindView(R.id.tev_lastPrice_DR)
        TextView tevLastPriceDR;
        @BindView(R.id.tev_preClosePrice_DR)
        TextView tevPreClosePriceDR;
        @BindView(R.id.tev_changeRate_DR)
        TextView tevChangeRateDR;
//        @BindView(R.id.tev_dateTime_DR)
//        TextView tevDateTimeDR;

        @BindView(R.id.tev_codeBase_DR)
        TextView tevCodeBaseDR;
        @BindView(R.id.tev_nameBase_DR)
        TextView tevNameBaseDR;
        @BindView(R.id.tev_lastPriceBase_DR)
        TextView tevLastPriceBaseDR;
        @BindView(R.id.tev_preClosePriceBase_DR)
        TextView tevPreClosePriceBaseDR;
        @BindView(R.id.tev_changeRateBase_DR)
        TextView tevChangeRateBaseDR;
//        @BindView(R.id.tev_dateTimeBase_DR)
//        TextView tevDateTimeBaseDR;
        @BindView(R.id.tev_preMium_DR)
        TextView tevPreMiumDR;
        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
