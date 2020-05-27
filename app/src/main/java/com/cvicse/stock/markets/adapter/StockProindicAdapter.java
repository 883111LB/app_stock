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

public class StockProindicAdapter extends PBaseAdapter {
    private List<HashMap<String, Object>> infoList;

    public StockProindicAdapter(Context context) {
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
        StockProindicAdapter.ViewHolder viewHolder = null;
        if (null == convertView) {
            convertView = mLayoutInflater.inflate(R.layout.item_proindic, parent,false);
            viewHolder = new StockProindicAdapter.ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (StockProindicAdapter.ViewHolder) convertView.getTag();
        }

        HashMap<String, Object> objectHashMap = infoList.get(position);

        TextUtils.setText(viewHolder.tevSesname, (String) objectHashMap.get("SESNAME"));
        TextUtils.setText(viewHolder.tevPublishdate,(String) objectHashMap.get("PUBLISHDATE"));
        TextUtils.setText(viewHolder.tevEpsbasick,(String) objectHashMap.get("EPSBASIC"));
        TextUtils.setText(viewHolder.tevTagrt,(String) objectHashMap.get("TAGRT"));
        TextUtils.setText(viewHolder.tevNpgrt,(String) objectHashMap.get("NPGRT"));

        return convertView;
    }

    class ViewHolder {
        @BindView(R.id.tev_sesname)
        TextView tevSesname;
        @BindView(R.id.tev_publishdate)
        TextView tevPublishdate;
        @BindView(R.id.tev_epsbasic)
        TextView tevEpsbasick;
        @BindView(R.id.tev_tagrt)
        TextView tevTagrt;
        @BindView(R.id.tev_npgrt)
        TextView tevNpgrt;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
