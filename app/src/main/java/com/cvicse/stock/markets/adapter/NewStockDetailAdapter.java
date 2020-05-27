package com.cvicse.stock.markets.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cvicse.stock.R;
import com.cvicse.stock.base.PBaseAdapter;
import com.cvicse.stock.markets.data.NewStockDetailBo;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ruan_ytai on 17-3-1.
 */

public class NewStockDetailAdapter extends PBaseAdapter {

    private ArrayList<NewStockDetailBo> dataList;

    public NewStockDetailAdapter(Context context) {
        super(context);
    }

    public void setData(ArrayList<NewStockDetailBo> dataList) {
        this.dataList = dataList;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return dataList == null ? 0: dataList.size();
    }

    @Override
    public NewStockDetailBo getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder = null;
        if(convertView == null){
            //convertView = mLayoutInflater.inflate(R.layout.item_stock_detail, parent,false);
            convertView = mLayoutInflater.inflate(R.layout.item_stock_detail, null);
            viewHolder = new ViewHolder(convertView);

            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.mTevTitle.setText(dataList.get(position).getIdentify());
        viewHolder.mTevData.setText(dataList.get(position).getData());

        return convertView;
    }


    static class ViewHolder {
        @BindView(R.id.tev_title) TextView mTevTitle;
        @BindView(R.id.tev_data)  TextView mTevData;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
