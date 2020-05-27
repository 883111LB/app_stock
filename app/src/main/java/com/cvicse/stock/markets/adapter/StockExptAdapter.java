package com.cvicse.stock.markets.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cvicse.stock.R;
import com.cvicse.stock.base.PBaseAdapter;
import com.cvicse.stock.utils.TextUtils;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StockExptAdapter extends PBaseAdapter {
    private List<HashMap<String, Object>> infoList;

    public StockExptAdapter(Context context) {
        super(context);
    }

    public void setData(List<HashMap<String, Object>> infoList) {
        this.infoList = infoList;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return null == infoList ? 0 : infoList.size();
    }

    @Override
    public Object getItem(int position) {
        return null == infoList ? null : infoList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return null == infoList ? 0 : position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        StockExptAdapter.ViewHolder viewHolder = null;
        if (null == convertView) {
            convertView = mLayoutInflater.inflate(R.layout.item_expt, parent,false);
            viewHolder = new StockExptAdapter.ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (StockExptAdapter.ViewHolder) convertView.getTag();
        }

        HashMap<String, Object> objectHashMap = infoList.get(position);

        TextUtils.setText(viewHolder.tevSesname, (String) objectHashMap.get("SESNAME"));
        TextUtils.setText(viewHolder.tevPublishdate,(String) objectHashMap.get("PUBLISHDATE"));
        TextUtils.setText(viewHolder.tevMaxprofitsmk,(String) objectHashMap.get("RETAMAXPROFITSMK"));
        TextUtils.setText(viewHolder.tevMaxprofitsinc,(String) objectHashMap.get("RETAMAXPROFITSINC"));

        return convertView;
    }

    class ViewHolder {
        @BindView(R.id.tev_sesname)
        TextView tevSesname;
        @BindView(R.id.tev_publishdate)
        TextView tevPublishdate;
        @BindView(R.id.tev_maxprofitsmk)
        TextView tevMaxprofitsmk;
        @BindView(R.id.tev_maxprofitsinc)
        TextView tevMaxprofitsinc;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
