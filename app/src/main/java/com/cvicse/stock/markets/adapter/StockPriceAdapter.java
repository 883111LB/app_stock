package com.cvicse.stock.markets.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cvicse.stock.R;
import com.cvicse.stock.base.PBaseAdapter;
import com.cvicse.stock.markets.data.MorePriceBo;
import com.cvicse.stock.utils.TextUtils;
import com.cvicse.stock.utils.UpDownUtils;
import com.cvicse.stock.view.CustomProgressBar;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by liu_zlu on 2017/3/7 16:37
 */
public class StockPriceAdapter extends PBaseAdapter {

    private ArrayList<MorePriceBo> morePriceBos;

    private String closePrice;
    public StockPriceAdapter(Context context,String closePrice) {
        super(context);
        this.closePrice = closePrice;
    }

    public void setData(ArrayList<MorePriceBo> morePriceBos){
        this.morePriceBos = morePriceBos;
        notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        return morePriceBos == null ? 0 : morePriceBos.size();
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
            convertView = mLayoutInflater.inflate(R.layout.item_lsv_stock_more_price,parent,false);
            viewHolder = new ViewHolder(convertView);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        MorePriceBo morePriceBo = morePriceBos.get(position);
        TextUtils.setText(viewHolder.tevMorePrice,morePriceBo.price,"");
        UpDownUtils.compare(closePrice,morePriceBo.price,viewHolder.tevMorePrice);
        TextUtils.setText(viewHolder.tevMoreVolume,morePriceBo.volume,"");
        viewHolder.pgbMorePrice.setProgress(morePriceBo.prencet);
        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.tev_more_price)
        TextView tevMorePrice;
        @BindView(R.id.pgb_more_price)
        CustomProgressBar pgbMorePrice;
        @BindView(R.id.tev_more_volume)
        TextView tevMoreVolume;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
            view.setTag(this);
        }
    }
}
