package com.cvicse.stock.news.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cvicse.stock.R;
import com.cvicse.stock.base.PBaseAdapter;
import com.cvicse.stock.utils.ImageLoader;
import com.mitake.core.NewsList;
import com.mitake.core.request.NewsType;

import java.util.ArrayList;

/**
 * Created by ruan_ytai on 16-12-29.
 */

public class NewsListAdapter extends PBaseAdapter
{

    private ArrayList<NewsList>  mNewsList;
    private String type;

    public NewsListAdapter(Context context,String type){
        super(context);
        this.type = type;
    }

    public void setData(ArrayList<NewsList> newsList) {
        mNewsList = newsList;
        notifyDataSetChanged();
    }

    public ArrayList<NewsList> getData(){
        return mNewsList;
    }

    @Override
    public int getCount()
    {
        return  mNewsList == null ? 0 : mNewsList.size();
    }

    @Override
    public NewsList getItem(int position) {
        if(mNewsList !=null &&mNewsList.size() >0){
            return mNewsList.get(position);
        }
       return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if(convertView == null) {
            viewHolder = new ViewHolder();
            if(type.equals(NewsType.NewsTypeImportant)){
                convertView = mLayoutInflater.inflate(R.layout.news_list_item_img, parent, false);
                viewHolder.img_important = (ImageView) convertView.findViewById(R.id.img_important);
            }else {
                convertView = mLayoutInflater.inflate(R.layout.news_list_item, parent, false);
            }
            viewHolder.tev_title = (TextView) convertView.findViewById(R.id.tv_title);
            viewHolder.tev_company = (TextView) convertView.findViewById(R.id.tv_company);
            viewHolder.tev_date = (TextView) convertView.findViewById(R.id.tv_date);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        NewsList news = mNewsList.get(position);//获取当前实例

        /**
         *加载图片
         */
        if(type.equals(NewsType.NewsTypeImportant)){
            ImageLoader.loadId(viewHolder.img_important,news.ID_);
            //viewHolder.img_important.setImageBitmap(news.getUrl());
        }
            viewHolder.tev_title.setText(news.REPORTTITLE_);
            viewHolder.tev_company.setText(news.MEDIANAME_);
            viewHolder.tev_date.setText(news.INIPUBDATE_);

            //viewHolder.tev_number.setText(news.getReadNumber());

        return convertView;
    }

    static class ViewHolder
    {
        ImageView img_important;
        TextView tev_title;
        TextView tev_company;
        TextView tev_date;
       // TextView tev_number;
    }
}
