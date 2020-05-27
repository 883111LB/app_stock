package com.cvicse.stock.markets.adapter;

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

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by liu_zlu on 2017/4/6 14:38
 */
public class StockTradeAdapter extends PBaseAdapter {
    private ArrayList<TickItemBo> tickItemBos;
    private double closePrice;
    private int count = 0;
    public StockTradeAdapter(Context context,String closePrice) {
        super(context);
        this.closePrice = NumberUtils.parseDouble(closePrice);
    }

    public void setData(ArrayList<TickItemBo> tickItemBos) {
        this.tickItemBos = tickItemBos;
        count = tickItemBos == null ? 0 : tickItemBos.size();
        notifyDataSetChanged();
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
            convertView = mLayoutInflater.inflate(R.layout.item_lv_stock_trade, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        TickItemBo tickItemBo = tickItemBos.get(position);


        TextUtils.setText(viewHolder.tevTime,tickItemBo.time);

        //tevChange成交
        //TextUtils.setText(viewHolder.tevChange,tickItemBo.strPrice);
        TextUtils.setText(viewHolder.tevChange,tickItemBo.strPrice);
        UpDownUtils.compare(closePrice,tickItemBo.price,viewHolder.tevChange);

        TextUtils.setText(viewHolder.tevVolume,tickItemBo.volume);

        if("48".equals(tickItemBo.tickFlag) || "S".equals(tickItemBo.tickFlag)){
            UpDownUtils.setDown(viewHolder.tevVolume);
        } else {
            UpDownUtils.setUp(viewHolder.tevVolume);
        }
        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.tev_time)
        TextView tevTime;

        /**
         * 成交
         */
        @BindView(R.id.tev_change)
        TextView tevChange;

        /**
         * 单量
         */
        @BindView(R.id.tev_volume)
        TextView tevVolume;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
