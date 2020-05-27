package com.cvicse.stock.portfolio.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cvicse.stock.R;
import com.cvicse.stock.base.PBaseAdapter;
import com.mitake.core.StockReportItem;

import java.util.List;

/**
 * 研究报告适配器
 * Created by ding_syi on 17-1-5.
 */
public class ReSearchReportAdapter extends PBaseAdapter {
    private List<StockReportItem> myStockReportList;

    public ReSearchReportAdapter(Context context) {
        super(context);
    }

    public void setData(List<StockReportItem> StockReportItem){
        this.myStockReportList = StockReportItem;
        notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        return myStockReportList != null ? myStockReportList.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return myStockReportList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        //观察convertView随ListView滚动情况
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.item_mystock_news,parent,false);
            holder = new ViewHolder();
            /**得到各个控件的对象*/
            holder.name = (TextView) convertView.findViewById(R.id.stock_name);
            holder.id = (TextView) convertView.findViewById(R.id.stock_id);
            holder.title = (TextView) convertView.findViewById(R.id.stock_title);
            holder.time = (TextView) convertView.findViewById(R.id.stock_time);
            convertView.setTag(holder);//绑定ViewHolder对象
        }
        else{
            holder = (ViewHolder)convertView.getTag();//取出ViewHolder对象
        }
        /**设置TextView显示的内容，即我们存放在动态数组中的数据*/
        holder.name.setText(myStockReportList.get(position).STOCKNAME_);
        holder.id.setText(myStockReportList.get(position).ID_);
        holder.title.setText(myStockReportList.get(position).ReportTitle);
        holder.time.setText(myStockReportList.get(position).PUBDATE_);

        return convertView;
    }


    /**存放控件*/
    public final class ViewHolder{
        public TextView name;
        public TextView id;
        public TextView title;
        public TextView time;
    }
}
