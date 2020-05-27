package com.cvicse.stock.markets.adapter.new_adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.cvicse.stock.R;
import com.cvicse.stock.base.PBaseAdapter;
import com.cvicse.stock.common.FillConfig;
import com.cvicse.stock.utils.TextUtils;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by tang_xqing on 2018/4/17.
 */

public class CompanyManagerAdapter extends PBaseAdapter {
    private  boolean isOpen = false;
    List<HashMap<String, Object>> leaderPersonInfoList;

    public CompanyManagerAdapter(Context context) {
        super(context);
    }

    public void setData(List<HashMap<String, Object>> leaderPersonInfoList) {
        this.leaderPersonInfoList = leaderPersonInfoList;
//        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return null == leaderPersonInfoList ? 0 : leaderPersonInfoList.size();
    }

    @Override
    public Object getItem(int position) {
        return null == leaderPersonInfoList ? null : leaderPersonInfoList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return null == leaderPersonInfoList ? 0 : position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (null == convertView) {
            convertView = mLayoutInflater.inflate(R.layout.item_lel_leader, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        isOpen = false;
        HashMap<String, Object> leaderPersonInfo = leaderPersonInfoList.get(position);
        TextUtils.setText(viewHolder.tevName, (String) leaderPersonInfo.get("CNAME"));
        TextUtils.setText(viewHolder.tevPositionName, (String) leaderPersonInfo.get("DUTY"));
        TextUtils.setText(viewHolder.tevRembeftax, (String) leaderPersonInfo.get("REMBEFTAX"));
        TextUtils.setText(viewHolder.tevHoldafamt, (String) leaderPersonInfo.get("HOLDAFAMT"));

        final TextView tevBeginend = viewHolder.tevBeginend;
        StringBuilder tempBuilder = new StringBuilder();
        tempBuilder.append("任职时间：").append(null == leaderPersonInfo.get("BEGINEND") ? FillConfig.DEFALUT : leaderPersonInfo.get("BEGINEND"));
        tempBuilder.append("\n简介：").append(null == leaderPersonInfo.get("MEMO") ? FillConfig.DEFALUT : leaderPersonInfo.get("MEMO"));

        TextUtils.setText(tevBeginend, tempBuilder.toString());
        final ImageView imgUpflag = viewHolder.imgUpflag;
        setImage(imgUpflag, tevBeginend);
        viewHolder.tevName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isOpen = !isOpen;
                setImage(imgUpflag, tevBeginend);
            }
        });
        return convertView;
    }

    private void setImage(ImageView imgFlag, TextView llMemo){
        if( isOpen ){
            imgFlag.setImageLevel(1);
            llMemo.setVisibility(View.VISIBLE);
        }else{
            imgFlag.setImageLevel(0);
            llMemo.setVisibility(View.GONE);
        }
    }

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if ( null==listAdapter ) {
            return;
        }
        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();

        params.height = totalHeight
                + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }

    class ViewHolder {
        @BindView(R.id.tev_name)
        TextView tevName;
        @BindView(R.id.tev_positionName)
        TextView tevPositionName;
        @BindView(R.id.tev_rembeftax)
        TextView tevRembeftax;
        @BindView(R.id.tev_holdafamt)
        TextView tevHoldafamt;
        @BindView(R.id.tev_beginend)
        TextView tevBeginend;
        @BindView(R.id.img_upflag)
        ImageView imgUpflag;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
