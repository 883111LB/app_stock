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

/** 股东深度挖掘
 * Created by tang_xqing on 2018/4/18.
 */

public class StockShareIinvholdchgAdapter extends PBaseAdapter {

    public List<HashMap<String, Object>> infoList;

    public StockShareIinvholdchgAdapter(Context context) {
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
        ViewHolder viewHolder = null;
        if (null == convertView) {
            convertView =  mLayoutInflater.inflate(R.layout.share_list_item, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        HashMap<String, Object> map = infoList.get(position);
        TextUtils.setText(viewHolder.tevDate, (String) map.get("REPORTDATE"));
        TextUtils.setText(viewHolder.tevId, (String) map.get("TRADING"));
        TextUtils.setText(viewHolder.tevName, (String) map.get("SESNAME"));
        TextUtils.setText(viewHolder.tevHoldamt, (String) map.get("HOLDAMT"));
        TextUtils.setText(viewHolder.tevHoldqtysumchg, (String) map.get("HOLDQTYSUMCHG"));
        TextUtils.setText(viewHolder.tevHoldqty, (String) map.get("HOLDQTY"));
        TextUtils.setText(viewHolder.tevSetypename, (String) map.get("SETYPENAME"));

        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.tev_date)
        TextView tevDate;
        @BindView(R.id.tev_name)
        TextView tevName;
        @BindView(R.id.tev_id)
        TextView tevId;
        @BindView(R.id.tev_holdamt)
        TextView tevHoldamt;  // 持股
        @BindView(R.id.tev_holdqty)
        TextView tevHoldqty;  // 市值
        @BindView(R.id.tev_holdqtysumchg)
        TextView tevHoldqtysumchg;   // 增减
        @BindView(R.id.tev_setypename)
        TextView tevSetypename;   // 股份类型

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
