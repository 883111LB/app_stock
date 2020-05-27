package com.cvicse.stock.markets.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cvicse.stock.R;
import com.cvicse.stock.base.PBaseAdapter;
import com.cvicse.stock.common.FillConfig;
import com.cvicse.stock.markets.data.TickItemBo;
import com.cvicse.stock.util.FormatUtils;
import com.cvicse.stock.utils.TextUtils;
import com.cvicse.stock.utils.UpDownUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 逐笔明细适配器
 * Created by liu_zlu on 2017/3/7 1
 */
public class StockTickAdapter extends PBaseAdapter {

    private ArrayList<TickItemBo> tickItemBos = new ArrayList<>();

    private double closePrice;

    public StockTickAdapter(Context context,String closePrice) {
        super(context);
        if(!FormatUtils.isStandard((closePrice))){
            this.closePrice = 0;
        }else{
            this.closePrice = Double.parseDouble(closePrice);
        }
    }

    public void setData(ArrayList<TickItemBo> tickItemBos) {
        if(tickItemBos == null || tickItemBos.size() <= 0){
            return;
        }
        this.tickItemBos = tickItemBos;
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
        if(convertView == null){
            convertView = mLayoutInflater.inflate(R.layout.item_lsv_stock_more_tick,parent,false);
            viewHolder = new ViewHolder(convertView);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        TickItemBo tickItemBo = tickItemBos.get(position);
        UpDownUtils.compare(closePrice,tickItemBo.price,viewHolder.tevTickPrice);
        TextUtils.setText(viewHolder.tevTickTime,tickItemBo.time, FillConfig.EMPTY);
        TextUtils.setText(viewHolder.tevTickFlag,tickItemBo.tickFlag,FillConfig.DEFALUT);
        TextUtils.setText(viewHolder.tevTickPrice,tickItemBo.strPrice,FillConfig.DEFALUT);
        TextUtils.setText(viewHolder.tevTickVolume,tickItemBo.volume,FillConfig.DEFALUT);
        //如果是买入则展示红色，卖出展示绿色
        if(tickItemBo.tickFlag.equals("B")){
            UpDownUtils.setUp(viewHolder.tevTickFlag,viewHolder.tevTickVolume);
        } else {
            UpDownUtils.setDown(viewHolder.tevTickFlag,viewHolder.tevTickVolume);
        }
        return convertView;
    }


    static class ViewHolder {
        @BindView(R.id.tev_tick_time)
        TextView tevTickTime;
        @BindView(R.id.tev_tick_price)
        TextView tevTickPrice;
        @BindView(R.id.tev_tick_volume)
        TextView tevTickVolume;
        @BindView(R.id.tev_tick_flag)
        TextView tevTickFlag;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
            view.setTag(this);
        }
    }
}
