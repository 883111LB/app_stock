package com.cvicse.stock.markets.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cvicse.stock.R;
import com.cvicse.stock.base.PBaseAdapter;
import com.cvicse.stock.common.FillConfig;
import com.cvicse.stock.utils.TextUtils;
import com.mitake.core.bean.offer.OfferQuoteBean;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


/** 分量列表页
 * Created by tang_h
 */

public class StockVolumeAdapter extends PBaseAdapter {
    private String[] volumes;
    private String[] buyVolumes;
    private String[] sellVolumes;
    private String[] qj;

    public StockVolumeAdapter(Context context) {
        super(context);
    }

    public void setData(String[] qj, String[] volumes, String[] buyVolumes, String[] sellVolumes) {
        this.qj = qj;
        this.volumes = volumes;
        this.buyVolumes = buyVolumes;
        this.sellVolumes = sellVolumes;
        notifyDataSetChanged();
    }

//    public ArrayList<OfferQuoteBean> getData() {
//        return offerQuoteList;
//    }

    @Override
    public int getCount() {
        return volumes == null ? 0 : volumes.length;
    }

    @Override
    public String getItem(int position) {
        return volumes[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        //观察convertView随ListView滚动情况
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.item_volume, parent, false);
            holder = new ViewHolder(convertView);

            convertView.setTag(holder);//绑定ViewHolder对象
        } else {
            holder = (ViewHolder) convertView.getTag();//取出ViewHolder对象

        }

        /**设置TextView显示的内容，即我们存放在动态数组中的数据*/
        TextUtils.setText(holder.tev_qj, qj[position], FillConfig.DEFALUT);
//        TextUtils.setText(holder.tev_volumes, volumes[position], FillConfig.DEFALUT);
        TextUtils.setText(holder.tev_buyvolumes, buyVolumes[position], FillConfig.DEFALUT);
        TextUtils.setText(holder.tev_sellvolumes, sellVolumes[position], FillConfig.DEFALUT);

        return convertView;
    }

    class ViewHolder {
        //分量区间
        @BindView(R.id.tev_qj) TextView tev_qj;
        //成交量
//        @BindView(R.id.tev_volumes) TextView tev_volumes;
        //买量
        @BindView(R.id.tev_buyvolumes) TextView tev_buyvolumes;
        //卖量
        @BindView(R.id.tev_sellvolumes) TextView tev_sellvolumes;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
