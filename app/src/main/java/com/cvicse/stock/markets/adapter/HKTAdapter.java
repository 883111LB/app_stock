package com.cvicse.stock.markets.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.cvicse.stock.R;
import com.cvicse.stock.base.PBaseExpandableListAdapter;
import com.cvicse.stock.common.ColorUtils;
import com.cvicse.stock.markets.ui.AHQuoteListActivity;
import com.cvicse.stock.markets.ui.HKTMoreActivity;
import com.cvicse.stock.utils.TextUtils;
import com.mitake.core.QuoteItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ding_syi on 17-2-6.
 */
public class HKTAdapter extends PBaseExpandableListAdapter {

    public static String[] groupNames = {"港股通","港股通(沪)","港股通(深)","AH股"};
    private List<QuoteItem>[]  datas = new List[4];
    public HKTAdapter(Activity context){
           super(context);
    }

    public void setData(ArrayList<QuoteItem> quoteItems,int position){
        datas[position] = quoteItems;
        notifyDataSetChanged();
    }

    @Override
    public int getGroupCount() {
        return groupNames.length;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return datas[groupPosition] == null ? 0: datas[groupPosition].size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return datas[groupPosition];
    }

    @Override
    public QuoteItem getChild(int groupPosition, int childPosition) {
        return datas[groupPosition].get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(final int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GroupHolder groupHolder ;
        if(null==convertView){
            convertView = mLayoutInflater.inflate(R.layout.expendlist_gropup, parent,false);
            groupHolder = new GroupHolder();
            groupHolder.groupName = (TextView)convertView.findViewById(R.id.tev_type);
            groupHolder.groupMore = (FrameLayout)convertView.findViewById(R.id.frm_img);
            groupHolder.groupArrow = (ImageView)convertView.findViewById(R.id.img_sign);
            convertView.setTag(groupHolder);
        }else {
            groupHolder = (GroupHolder) convertView.getTag();
        }

        if(isExpanded){
            groupHolder.groupArrow.setImageResource(R.drawable.img_below_indicator_1);
        }else {
            groupHolder.groupArrow.setImageResource(R.drawable.img_right_indicator_1);
        }

        groupHolder.groupName.setText(groupNames[groupPosition]);
        groupHolder.groupMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( groupPosition == 3 ){
                    AHQuoteListActivity.startActivity( mContext,groupNames[groupPosition]);
                }else{
                    HKTMoreActivity.startActivity( mContext,groupNames[groupPosition]);
                }
            }
        });
        return convertView;
    }


    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildHolder childHolder = null;
        if(convertView==null){
            convertView = mLayoutInflater.inflate(R.layout.expendlist_item, parent,false);
            childHolder = new ChildHolder();
            childHolder.tevCompany = (TextView) convertView.findViewById(R.id.tev_company);
            childHolder.tevCode = (TextView) convertView.findViewById(R.id.tev_code);
            childHolder.tevNewestPrice = (TextView) convertView.findViewById(R.id.tev_newestprice);
            childHolder.tevRaiseRange = (TextView) convertView.findViewById(R.id.tev_raiserange);
            convertView.setTag(childHolder);

        }else {
            childHolder = (ChildHolder) convertView.getTag();
        }
        QuoteItem quoteItem = datas[groupPosition].get(childPosition);
        //股票名字
        childHolder.tevCompany.setText(quoteItem.name);
        //股票代码
        childHolder.tevCode.setText(quoteItem.id);
        //最新价
        childHolder.tevNewestPrice.setText(quoteItem.lastPrice);
        //涨跌比率
        childHolder.tevRaiseRange.setText(quoteItem.changeRate);
        //涨跌比率
        if(TextUtils.setTextPercent(childHolder.tevRaiseRange,quoteItem.changeRate)){
            if (quoteItem.changeRate.startsWith("-")) {
                childHolder.tevRaiseRange.setTextColor(ColorUtils.DOWN);
            } else if(quoteItem.changeRate.startsWith("+")){
                childHolder.tevRaiseRange.setTextColor(ColorUtils.UP);
            }
        }
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    /**
     * show the text on the child and group item
     */
    private class GroupHolder {
        /** 箭头*/
        ImageView groupArrow;
        /** 标题名字*/
        TextView groupName;
        /** 更多图标*/
         FrameLayout groupMore;
    }
    private class ChildHolder {
        TextView tevCompany;
        TextView tevCode;
        TextView tevNewestPrice;
        TextView tevRaiseRange;
    }
}
