package com.cvicse.stock.chart.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cvicse.base.utils.NumberUtils;
import com.cvicse.stock.R;
import com.cvicse.stock.base.PBaseAdapter;
import com.cvicse.stock.markets.data.TickItemBo;
import com.cvicse.stock.utils.TextUtils;
import com.cvicse.stock.utils.UpDownUtils;
import com.mitake.core.QuoteItem;
import com.mitake.core.util.FormatUtility;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by liu_zlu on 2017/3/14 14:17
 */
public class ChartTickAdapter extends PBaseAdapter {
    private ArrayList<TickItemBo> tickItemBos;

    private ArrayList<View> views = new ArrayList<>();
    private QuoteItem quoteItem;
    private double closePrice = 0;
    public ChartTickAdapter(Context context,QuoteItem quoteItem) {
        super(context);
        this.quoteItem = quoteItem;
        this.closePrice = NumberUtils.parseDouble(quoteItem.preClosePrice);
    }

    public void setData(ArrayList<TickItemBo> tickItemBos){
        this.tickItemBos = tickItemBos;
        int count = getCount();
        for(int i=0;i < count;i++){
            views.add(null);
        }

        notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        return tickItemBos == null ? 0 : tickItemBos.size();
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
        convertView = views.get(position);
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.item_lv_cahrt_tick, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
            views.set(position,convertView);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        int length = getCount();
        TickItemBo tickItemBo = null;
        if(length>0){
            tickItemBo = tickItemBos.get(length - position -1);
        }

        TextUtils.setText(viewHolder.tevTime,tickItemBo.time);
        TextUtils.setText(viewHolder.tevPrice,tickItemBo.strPrice);
        TextUtils.setText(viewHolder.tevVolume, FormatUtility.formatVolume(tickItemBo.volume));
        if( null==tickItemBo.tickFlag){
            return convertView;
        }
        if("48".equals(tickItemBo.tickFlag) || "S".equals(tickItemBo.tickFlag)){
            UpDownUtils.setDown(viewHolder.tevVolume);
        } else {
            UpDownUtils.setUp(viewHolder.tevVolume);
        }

        UpDownUtils.compare(closePrice,tickItemBo.price,viewHolder.tevPrice);
        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.tev_time)
        TextView tevTime;
        @BindView(R.id.tev_price)
        TextView tevPrice;
        @BindView(R.id.tev_volume)
        TextView tevVolume;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
