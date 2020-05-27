package com.cvicse.stock.markets.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cvicse.stock.R;
import com.cvicse.stock.base.PBaseAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ruan_ytai on 17-1-19.
 */

public class GrdAdapter extends PBaseAdapter {

    private String[] data;

    public GrdAdapter(Context context,String[]data) {
        super(context);
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.length;
    }

    @Override
    public String getItem(int position) {
        return data[position];
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if ( null== convertView) {
            convertView = mLayoutInflater.inflate(R.layout.grd_item, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        String stockName = data[position];
        viewHolder.mTevStockType.setText(stockName);

        return convertView;
    }

    class ViewHolder {
        @BindView(R.id.tev_stock_type)
        TextView mTevStockType;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
