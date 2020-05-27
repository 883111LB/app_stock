package com.cvicse.stock.portfolio.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cvicse.stock.R;
import com.mitake.core.StockBulletinItem;

import java.util.List;

/**
 * Created by ding_syi on 17-1-5.
 */
public class NoticeAdapter extends BaseAdapter {
    private List<StockBulletinItem> myStockBeanList;


   public void setData(List<StockBulletinItem> myStockBeanList){
       this.myStockBeanList = myStockBeanList;
       notifyDataSetChanged();
   }


    @Override
    public int getCount() {
        return myStockBeanList==null?0:myStockBeanList.size();
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
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mystock_news,parent,false);
            holder = new ViewHolder();
            /**得到各个控件的对象*/
            holder.name = (TextView) convertView.findViewById(R.id.stock_name);
            holder.id = (TextView) convertView.findViewById(R.id.stock_id);
            holder.stock_title = (TextView) convertView.findViewById(R.id.stock_title);
            holder.date = (TextView) convertView.findViewById(R.id.stock_time);
            convertView.setTag(holder);//绑定ViewHolder对象
        }
        else{
            holder = (ViewHolder)convertView.getTag();//取出ViewHolder对象
        }

        StockBulletinItem stockReportItem = myStockBeanList.get(position);

        /**设置TextView显示的内容，即我们存放在动态数组中的数据*/
        holder.name.setText(stockReportItem.STOCKNAME_ == null ? "-" : stockReportItem.STOCKNAME_);
        holder.id.setText(stockReportItem.ID_ == null ? "-" : stockReportItem.ID_ );
        holder.stock_title.setText(stockReportItem.TITLE_== null ? "-" : stockReportItem.TITLE_);
        holder.date.setText(stockReportItem.PUBDATE_ == null ? "-" : stockReportItem.PUBDATE_);

        return convertView;
    }


    /**存放控件*/
    public final class ViewHolder{
        public TextView name;
        public TextView id;
        public TextView stock_title;
        public TextView date;
    }
}
