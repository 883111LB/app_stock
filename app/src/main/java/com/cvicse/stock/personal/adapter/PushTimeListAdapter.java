package com.cvicse.stock.personal.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.cvicse.stock.R;
import com.cvicse.stock.base.PBaseAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ruan_ytai on 17-1-9.
 * 推送时间列表的adapter
 */

public class PushTimeListAdapter extends PBaseAdapter implements View.OnClickListener{

    private ArrayList<String> list;
    private View view;

    public PushTimeListAdapter(Context context) {
        super(context);
    }

    public void setData(ArrayList<String> timeList) {
        list = timeList;
        notifyDataSetChanged();
    }

    public void setView(View view){
        this.view = view;
    }

    @Override
    public int getCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public String getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.list_item_setting_pushtime, parent,false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        String pushTimeStr = list.get(position);
        viewHolder.mTevPushTime.setText(pushTimeStr);

        viewHolder.mBtnDeletePushtime.setTag(position);
        viewHolder.mBtnDeletePushtime.setOnClickListener(this);

        return convertView;
    }

    @Override
    public void onClick(View v) {
        int position = (int) v.getTag();
        list.remove(position);
        if(list.size() ==0){
            view.setVisibility(View.VISIBLE);
        }else{
            view.setVisibility(View.GONE);
        }

        notifyDataSetChanged();
    }

    class ViewHolder {
        /**
         * 推送时间
         */
        @BindView(R.id.tev_push_time)
        TextView mTevPushTime;

        /**
         * 删除某项推送时间
         */
        @BindView(R.id.btn_delete_pushtime)
        Button mBtnDeletePushtime;

        public ViewHolder(View view){
            ButterKnife.bind(this,view);
        }

    }
}
