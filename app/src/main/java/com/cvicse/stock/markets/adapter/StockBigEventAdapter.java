package com.cvicse.stock.markets.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cvicse.stock.R;
import com.cvicse.stock.base.PBaseAdapter;
import com.cvicse.stock.utils.TextUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 大事提醒
 * Created by tang_xqing on 2018/4/23.
 */
public class StockBigEventAdapter extends PBaseAdapter {
    private String type;
    private List<HashMap<String, Object>> infoList = new ArrayList<>();

    public StockBigEventAdapter(Context context, String type) {
        super(context);
        this.type = type;
    }

    public void setData(String type,List<HashMap<String, Object>> infoList) {
        if(null == infoList){
           return;
        }
        this.type = type;
        this.infoList.addAll(infoList);
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
        convertView = mLayoutInflater.inflate(R.layout.item_lv_big_event, parent,false);
        viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        HashMap<String, Object> objectHashMap = infoList.get(position);
        String reptitle = (String) objectHashMap.get("REPTITLE");
        String tradedate = (String) objectHashMap.get("TRADEDATE");
        String text = (String) objectHashMap.get("TEXT");

        if("title".equals(type)){
            viewHolder.tevBigEventType.setVisibility(View.VISIBLE);
            TextUtils.setText(viewHolder.tevBigEventType,getTitleByType(reptitle));
        }else{
            viewHolder.tevBigEventType.setVisibility(View.GONE);
        }

        viewHolder.tevBigEventDate.setVisibility(View.VISIBLE);
        // 设置时间
        if( 0 != position ){
            String lastDate = (String) infoList.get(position - 1).get("TRADEDATE");
            if( tradedate.equals(lastDate) ){
                viewHolder.tevBigEventDate.setVisibility(View.INVISIBLE);
            }
            String lastReptitle = (String) infoList.get(position - 1).get("REPTITLE");
            if( "title".equals(type) && reptitle.equals(lastReptitle)){
                viewHolder.tevBigEventType.setVisibility(View.GONE);
            }
        }

        TextUtils.setText(viewHolder.tevBigEventDate,tradedate.substring(5,10)+"\n"+tradedate.substring(0,4));
        // 设置内容
        TextUtils.setText(viewHolder.tevBigEventContent,text);

        int height = viewHolder.tevBigEventContent.getHeight();
        viewHolder.bigEventV2.getLayoutParams().height = height;

        return convertView;
    }

    /**
     * 根据标题代码，获得相对应的名称
     * @param reptitle
     * @return
     */
    private String getTitleByType(String reptitle){
        String title = "融资融券";
        switch (reptitle){
            case "1":title = "融资融券";break;
            case "2":title = "大宗交易";break;
            case "3":title = "分红扩股";break;
            case "4":title = "限售解禁";break;
        }
        return title;
    }

    /**
     * 清除数据
     */
    public void clearData() {
        if( null != infoList ){
            infoList.clear();
        }
    }

    class ViewHolder {
        @BindView(R.id.tev_bigEvent_type)
        TextView tevBigEventType;
        @BindView(R.id.tev_bigEvent_date)
        TextView tevBigEventDate;
        @BindView(R.id.bigEvent_v1)
        View bigEventV1;
        @BindView(R.id.bigEvent_v2)
        View bigEventV2;
        @BindView(R.id.tev_bigEvent_content)
        TextView tevBigEventContent;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
