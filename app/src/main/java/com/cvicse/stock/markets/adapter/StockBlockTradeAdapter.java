package com.cvicse.stock.markets.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cvicse.stock.R;
import com.cvicse.stock.base.PBaseAdapter;
import com.cvicse.stock.common.FillConfig;
import com.cvicse.stock.util.FormatUtils;
import com.cvicse.stock.utils.TextUtils;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 大宗交易
 * Created by tang_xqing on 2018/4/23.
 */
public class StockBlockTradeAdapter extends PBaseAdapter {
    private List<HashMap<String, Object>> infoList;

    public StockBlockTradeAdapter(Context context) {
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
            convertView = mLayoutInflater.inflate(R.layout.item_block_trade, parent,false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        HashMap<String, Object> objectHashMap = infoList.get(position);

        String tradeprice = (String) objectHashMap.get("TRADEPRICE");
        String volume = (String) objectHashMap.get("VOLUME");
        String amount = (String) objectHashMap.get("AMOUNT");
        String prc = (String) objectHashMap.get("PRC");
        String buyername = (String) objectHashMap.get("BUYERNAME");
        String sellername = (String) objectHashMap.get("SELLERNAME");

        TextUtils.setText(viewHolder.tevDate, (String) objectHashMap.get("TRADEDATE"));
        TextUtils.setText(viewHolder.tevTradePrice,"成交价(元)："+ (FormatUtils.isStandard(tradeprice) ? tradeprice : FillConfig.DEFALUT));
        TextUtils.setText(viewHolder.tevTradeVolume,"成交额(元)："+ (FormatUtils.isStandard(amount) ? amount : FillConfig.DEFALUT));
        TextUtils.setText(viewHolder.tevTradeAmount,"成交量(股)："+ (FormatUtils.isStandard(volume) ? volume : FillConfig.DEFALUT));
        TextUtils.setText(viewHolder.tevTradePrc,"折溢率(%)："+ (FormatUtils.isStandard(prc) ? prc : FillConfig.DEFALUT));
        TextUtils.setText(viewHolder.tevBuyername,"买方营业部："+ (FormatUtils.isStandard(buyername) ? buyername : FillConfig.DEFALUT));
        TextUtils.setText(viewHolder.tevSellername,"卖方营业部："+ (FormatUtils.isStandard(sellername) ? sellername : FillConfig.DEFALUT));

        return convertView;
    }

    class ViewHolder {
        @BindView(R.id.tev_date)
        TextView tevDate;
        @BindView(R.id.tev_trade_price)
        TextView tevTradePrice;
        @BindView(R.id.tev_trade_volume)
        TextView tevTradeVolume;
        @BindView(R.id.tev_trade_amount)
        TextView tevTradeAmount;
        @BindView(R.id.tev_trade_prc)
        TextView tevTradePrc;
        @BindView(R.id.tev_buyername)
        TextView tevBuyername;
        @BindView(R.id.tev_sellername)
        TextView tevSellername;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
