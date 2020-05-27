package com.cvicse.stock.markets.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cvicse.stock.R;
import com.cvicse.stock.base.PBaseAdapter;
import com.cvicse.stock.common.ColorUtils;
import com.cvicse.stock.markets.data.PieData;
import com.cvicse.stock.util.FormatUtils;
import com.cvicse.stock.utils.TextUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 成交统计适配器
 * Created by tang_xqing on 2017/8/29.
 */

public class TurnoverLsvAdapter extends PBaseAdapter {
    private static final String TAG = "TurnoverLsvAdapter";
    private String[] tevVolume = {"超大", "大单", "中单", "小单", "超大", "大单", "中单", "小单"};

    // 颜色表 (注意: 此处定义颜色使用的是ARGB，带Alpha通道的)
    private int[] mColors = {ColorUtils.STOCK_TOTAL_BUY_U, ColorUtils.STOCK_TOTAL_BUY_L, ColorUtils.STOCK_TOTAL_BUY_M,
            ColorUtils.STOCK_TOTAL_BUY_S,ColorUtils.STOCK_TOTAL_SELL_U, ColorUtils.STOCK_TOTAL_SELL_L,
            ColorUtils.STOCK_TOTAL_SELL_M, ColorUtils.STOCK_TOTAL_SELL_S};

    private ArrayList<PieData> pieDateList;

    public TurnoverLsvAdapter(Context context) {
        super(context);
    }

    public void setData(ArrayList<PieData> pieDateList){
        this.pieDateList = pieDateList;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mColors == null ? 0 : mColors.length;
    }

    @Override
    public PieData getItem(int position) {
        return pieDateList.get(position) == null ? null : pieDateList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if( convertView == null ){
            convertView = mLayoutInflater.inflate(R.layout.listview_turnover, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.lstView.setBackgroundColor(mColors[position]);
        viewHolder.lstName.setText(tevVolume[position]);

        if( null != pieDateList  ){
            PieData pieData = pieDateList.get(position);
            TextUtils.setText(viewHolder.lstBuy, FormatUtils.format(String.valueOf(pieData.getValue())));
            TextUtils.setText(viewHolder.lstBuyPer, formatData( pieData.getPercent()) );
        }
        return convertView;
    }

    private String formatData( double data ){
        return String.format("%.0f",data*100)+"%";
    }

    static class ViewHolder {
        @BindView(R.id.lst_view)
        View lstView;
        @BindView(R.id.lst_name)
        TextView lstName;
        @BindView(R.id.lst_buy)
        TextView lstBuy;
        @BindView(R.id.lst_buy_per)
        TextView lstBuyPer;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
