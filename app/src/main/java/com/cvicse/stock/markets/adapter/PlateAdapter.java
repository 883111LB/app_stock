package com.cvicse.stock.markets.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cvicse.stock.BaseApplication;
import com.cvicse.stock.R;
import com.cvicse.stock.base.PBaseAdapter;
import com.mitake.core.response.Bankuaisorting;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 所属板块适配器
 * Created by tang_xqing on 17-11-29
 */
public class PlateAdapter extends PBaseAdapter {
    List<Bankuaisorting> hotSotckList = new ArrayList<>();

    int colorRed = ContextCompat.getColor(BaseApplication.getInstance(), R.color.text_red);
    int colorGreen = ContextCompat.getColor(BaseApplication.getInstance(), R.color.text_green);
    int colorGray = ContextCompat.getColor(BaseApplication.getInstance(), R.color.text_gray);

    public PlateAdapter(Context context) {
        super(context);
    }

    public void setData(List<Bankuaisorting> hotSotckList) {
        this.hotSotckList = hotSotckList;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return null == hotSotckList ? 0 : hotSotckList.size();
    }

    @Override
    public Object getItem(int position) {
        return null == hotSotckList ? null : hotSotckList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return null == hotSotckList ? 0 : position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if ( null==convertView ) {
            convertView = mLayoutInflater.inflate(R.layout.item_markets_plate, parent, false);
            viewHolder = new ViewHolder(convertView);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.tevType.setText(hotSotckList.get(position).n);
        String jzf = hotSotckList.get(position).jzf;
        if ( null!=jzf ) {
            if (jzf.startsWith("-")) {
                viewHolder.tevRate.setText(jzf + "%");
                viewHolder.tevRate.setTextColor(colorGreen);

            } else if (Float.parseFloat(jzf) > 0) {
                viewHolder.tevRate.setText("+" + jzf + "%");
                viewHolder.tevRate.setTextColor(colorRed);

            } else {
                viewHolder.tevRate.setText(jzf + "%");
                viewHolder.tevRate.setTextColor(colorGray);
            }
        } else {
            viewHolder.tevRate.setText("--");
        }
        return convertView;
    }

    class ViewHolder {
        @BindView(R.id.tev_type)
        TextView tevType;
        @BindView(R.id.tev_rate)
        TextView tevRate;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
