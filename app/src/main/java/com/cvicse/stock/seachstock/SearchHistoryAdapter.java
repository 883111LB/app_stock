package com.cvicse.stock.seachstock;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cvicse.stock.R;
import com.cvicse.stock.base.PBaseAdapter;
import com.cvicse.stock.utils.MyStocksUtils;
import com.mitake.core.SearchResultItem;

import java.util.ArrayList;
import java.util.List;

/**
 * 搜索结果适配器
 * Created by ding_syi on 17-1-10.
 */
public class SearchHistoryAdapter extends PBaseAdapter {

    private List<SearchResultItem> myHistoryBeanList;

    //取出已存储在本地的添加自选的股票ID
    private ArrayList<String> arrayListCode = new ArrayList<>() ;

    public SearchHistoryAdapter(Context context){
        super(context);
    }

    public void setData(List<SearchResultItem> myHistoryBeanList){
       if( null != this.myHistoryBeanList ){
           this.myHistoryBeanList.clear();
       }
        this.myHistoryBeanList = myHistoryBeanList;
        if(null != myHistoryBeanList ){
            for(SearchResultItem searchResultItem :myHistoryBeanList){
                if(searchResultItem != null){
                    searchResultItem.pinyin =  null== searchResultItem.stockID?"-":searchResultItem.stockID.substring(0,searchResultItem.stockID.indexOf("."));
                    searchResultItem.pinyin = null;
                }
            }
        }

        String[] stringCode = MyStocksUtils.getSelectCodes();
        if(stringCode != null){
            for(String str : stringCode){
                arrayListCode.add(str);
            }
        }
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return myHistoryBeanList == null ? 0:myHistoryBeanList.size();
    }

    @Override
    public SearchResultItem getItem(int position) {
        return myHistoryBeanList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        //观察convertView随ListView滚动情况
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.item_history,parent,false);
            holder = new ViewHolder();
            /**得到各个控件的对象*/
            holder.type = (TextView) convertView.findViewById(R.id.tev_type);
            holder.name = (TextView) convertView.findViewById(R.id.stock_name);
            holder.id = (TextView) convertView.findViewById(R.id.stock_id);
            holder.imgAdd = (ImageView) convertView.findViewById(R.id.img_add);
            convertView.setTag(holder);//绑定ViewHolder对象
        }
        else{
            holder = (ViewHolder)convertView.getTag();//取出ViewHolder对象
        }

        final SearchResultItem searchResultItem = myHistoryBeanList.get(position);

        /**设置TextView显示的内容，即我们存放在动态数组中的数据*/
        if( null== searchResultItem.pinyin){
            searchResultItem.pinyin = SearchResultUtil.setTypeSymbol(searchResultItem);
        }
        if(searchResultItem.pinyin.equals("")){
            holder.type.setVisibility(View.INVISIBLE);
        } else {
            holder.type.setVisibility(View.VISIBLE);
            holder.type.setText(searchResultItem.pinyin);
        }

        holder.name.setText(searchResultItem.name == null ? "-" : searchResultItem.name);
        holder.id.setText(searchResultItem.stockID == null?"-":searchResultItem.stockID);

        if(arrayListCode.contains(searchResultItem.stockID)){
            holder.imgAdd.setImageResource(R.drawable.btn_add_ss);
        } else {
            holder.imgAdd.setImageResource(R.drawable.btn_add_nn);
        }
        holder.imgAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String stockID = searchResultItem.stockID;

                arrayListCode.add(stockID);
                MyStocksUtils.saveSelect(stockID,searchResultItem.name);
                ((ImageView)v).setImageResource(R.drawable.btn_add_ss);
                notifyDataSetChanged();
            }
        });

        return convertView;
    }

    /**存放控件*/
    public final class ViewHolder{
        public TextView type;
        public TextView name;
        public TextView id;
        public ImageView imgAdd;
    }
}
