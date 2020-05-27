package com.cvicse.stock.markets.adapter;

import android.content.Context;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.cvicse.stock.R;
import com.cvicse.stock.base.PBaseAdapter;
import com.cvicse.stock.markets.data.MarginTradingBo;
import com.cvicse.stock.util.FormatUtils;
import com.cvicse.stock.utils.TextUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 融资融券适配器
 * Created by tang_xqing on 2018/8/3.
 */
public class MarginTradingAdapter extends PBaseAdapter {
    private ArrayList<MarginTradingBo> mTradingBoArrayList;
    private boolean isStock;  // 是否是个股

    public MarginTradingAdapter(Context context,boolean isStock,ListView listView) {
        super(context);
        this.isStock = isStock;
    }

    public void setDataList(ArrayList<MarginTradingBo> mTradingBoArrayList) {
        this.mTradingBoArrayList = mTradingBoArrayList;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return null == mTradingBoArrayList ? 0 : mTradingBoArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return null == mTradingBoArrayList ? null : mTradingBoArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return null == mTradingBoArrayList ? 0 : position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (null == convertView) {
            convertView = mLayoutInflater.inflate(R.layout.item_margintrading,null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        MarginTradingBo marginTradingBo = mTradingBoArrayList.get(position);
        TextUtils.setText(viewHolder.tevFinbalance, FormatUtils.format(marginTradingBo.finbalance));
        TextUtils.setText(viewHolder.tevFinbuyamt, FormatUtils.format(marginTradingBo.finbuyamt));
        TextUtils.setText(viewHolder.tevFinrepayamt, FormatUtils.format(marginTradingBo.finrepayamt));
        TextUtils.setText(viewHolder.tevFinroebuy, FormatUtils.format(marginTradingBo.finroebuy));
        TextUtils.setText(viewHolder.tevMrggbal, FormatUtils.format(marginTradingBo.mrggbal));
        TextUtils.setText(viewHolder.tevMrgnresqty, FormatUtils.format(marginTradingBo.mrgnresqty));
        TextUtils.setText(viewHolder.tevMrgnsellamt, FormatUtils.format(marginTradingBo.mrgnsellamt));
        TextUtils.setText(viewHolder.tevMrgnrepayamt, FormatUtils.format(marginTradingBo.mrgnrepayamt));
        TextUtils.setText(viewHolder.tevMrgnroesell, FormatUtils.format(marginTradingBo.mrgnroesell));
        TextUtils.setText(viewHolder.tevFinmrghbal, FormatUtils.format(marginTradingBo.finmrghbal));
        TextUtils.setText(viewHolder.tevFinmrgnbal, FormatUtils.format(marginTradingBo.finmrgnbal));

        if( !isStock ){
            TextUtils.setText(viewHolder.tevName,marginTradingBo.trading);
            TextUtils.setText(viewHolder.tevTradedate,marginTradingBo.tradeDate);
            viewHolder.tevTradedate.setTextSize(TypedValue.COMPLEX_UNIT_DIP,FormatUtils.setDynamicSize(marginTradingBo.tradeDate)-8);
        }else{  // 个股详情
            viewHolder.tevTradedate.setVisibility(View.GONE);
            TextUtils.setText(viewHolder.tevName,marginTradingBo.tradeDate);
            viewHolder.tevName.setTextSize(TypedValue.COMPLEX_UNIT_DIP,FormatUtils.setDynamicSize(marginTradingBo.tradeDate)-8);
        }
        viewHolder.tevId.setVisibility(View.GONE);
        return convertView;
    }

    public void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }
        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {

            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);

            totalHeight += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();

        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));

//        ((MarginLayoutParams) params).setMargins(10, 10, 10, 10); // 可删除
        listView.setLayoutParams(params);
    }

    class ViewHolder {
        @BindView(R.id.tev_name)
        TextView tevName;
        @BindView(R.id.tev_id)
        TextView tevId;
        @BindView(R.id.tev_tradedate)
        TextView tevTradedate;
        @BindView(R.id.tev_finbalance)
        TextView tevFinbalance;
        @BindView(R.id.tev_finbuyamt)
        TextView tevFinbuyamt;
        @BindView(R.id.tev_finrepayamt)
        TextView tevFinrepayamt;
        @BindView(R.id.tev_finroebuy)
        TextView tevFinroebuy;
        @BindView(R.id.tev_mrggbal)
        TextView tevMrggbal;
        @BindView(R.id.tev_mrgnresqty)
        TextView tevMrgnresqty;
        @BindView(R.id.tev_mrgnsellamt)
        TextView tevMrgnsellamt;
        @BindView(R.id.tev_mrgnrepayamt)
        TextView tevMrgnrepayamt;
        @BindView(R.id.tev_mrgnroesell)
        TextView tevMrgnroesell;
        @BindView(R.id.tev_finmrghbal)
        TextView tevFinmrghbal;
        @BindView(R.id.tev_finmrgnbal)
        TextView tevFinmrgnbal;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
