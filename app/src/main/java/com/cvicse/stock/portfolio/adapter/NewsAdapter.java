package com.cvicse.stock.portfolio.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cvicse.stock.R;
import com.cvicse.stock.base.PBaseAdapter;
import com.mitake.core.StockNewsItem;

import java.util.List;

/**
 * Created by ding_syi on 17-1-5.
 */
public class NewsAdapter extends PBaseAdapter {
    private List<StockNewsItem> myStockBeanList;

    public NewsAdapter(Context context) {
        super(context);
    }

    public void setData(List<StockNewsItem> myStockBeanList){
        this.myStockBeanList = myStockBeanList;
        notifyDataSetChanged();
    }



    @Override
    public int getCount() {
         return myStockBeanList != null ? myStockBeanList.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return myStockBeanList.get(position);
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
            convertView = mLayoutInflater.inflate(R.layout.item_mystock_news,parent,false);
            holder = new ViewHolder();
            /**得到各个控件的对象*/
            holder.name = (TextView) convertView.findViewById(R.id.stock_name);
            holder.id = (TextView) convertView.findViewById(R.id.stock_id);
            holder.stock_title = (TextView) convertView.findViewById(R.id.stock_title);
            holder.date = (TextView) convertView.findViewById(R.id.stock_time);
//            holder.source=(TextView)convertView.findViewById(R.id.stock_source);
            convertView.setTag(holder);//绑定ViewHolder对象
        }
        else{
            holder = (ViewHolder)convertView.getTag();//取出ViewHolder对象
        }

        StockNewsItem stockNewsItem = myStockBeanList.get(position);

        /**设置TextView显示的内容，即我们存放在动态数组中的数据*/
        holder.name.setText(stockNewsItem.STOCKNAME_ == null ? "-" : stockNewsItem.STOCKNAME_);
        holder.id.setText(stockNewsItem.ID_ == null ? "-" : stockNewsItem.ID_ );
        holder.stock_title.setText(stockNewsItem.REPORTTITLE_== null ? "-" : stockNewsItem.REPORTTITLE_);
//        holder.source.setText(stockNewsItem.MEDIANAME_==null?"-":stockNewsItem.MEDIANAME_);
        holder.date.setText(stockNewsItem.INIPUBDATE_ == null ? "-" : stockNewsItem.INIPUBDATE_);

        return convertView;
    }


    /**存放控件*/
    public final class ViewHolder{
        public TextView name;
        public TextView id;
        public TextView stock_title;
//        public TextView source;
        public TextView date;
    }
}
