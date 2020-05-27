package com.cvicse.stock.markets.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cvicse.stock.R;
import com.cvicse.stock.base.PBaseAdapter;
import com.cvicse.stock.markets.data.MarketNBRBo;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by liu_zlu on 2017/2/7 19:46
 */
public class MarketNewsAdapter extends PBaseAdapter {

    private ArrayList<MarketNBRBo> newsList;

    public MarketNewsAdapter(Context context) {
        super(context);
    }

    public void setData(ArrayList<MarketNBRBo> newsList) {
        if (newsList == null) {
            return;
        }
        this.newsList = newsList;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return newsList == null ? 0 : newsList.size();
    }

    @Override
    public MarketNBRBo getItem(int position) {
        return newsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            //convertView = mLayoutInflater.inflate(R.layout.item_market_news, parent, false);
            convertView = mLayoutInflater.inflate(R.layout.item_market_news,null);
            convertView.setTag(viewHolder = new ViewHolder(convertView));
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        MarketNBRBo news = newsList.get(position);
        viewHolder.tevTitle.setText(news.title);
        viewHolder.tevCompany.setText(news.stockName);
        viewHolder.tevDate.setText(news.date);
        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.tev_title)
        TextView tevTitle;
        @BindView(R.id.tev_company)
        TextView tevCompany;
        @BindView(R.id.tev_date)
        TextView tevDate;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
