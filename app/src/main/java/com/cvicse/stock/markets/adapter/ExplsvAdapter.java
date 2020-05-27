package com.cvicse.stock.markets.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.cvicse.stock.R;
import com.cvicse.stock.base.PBaseExpandableListAdapter;
import com.cvicse.stock.common.ColorUtils;
import com.cvicse.stock.common.FillConfig;
import com.cvicse.stock.markets.data.ParamUtil;
import com.cvicse.stock.markets.ui.RankingListAllActivity;
import com.cvicse.stock.markets.ui.RankingListFuturesActivity;
import com.cvicse.stock.util.FormatUtils;
import com.mitake.core.AddValueModel;
import com.mitake.core.QuoteItem;
import com.mitake.core.response.CateSortingResponse;

import java.util.List;


/**
 * Created by ruan_ytai on 17-1-12.
 */
public class ExplsvAdapter extends PBaseExpandableListAdapter implements View.OnClickListener{

    private String[] group = {"涨幅榜", "跌幅榜", "换手率", "成交额","五分钟涨幅"};
    private String[] group_hs = {ParamUtil.RANKING_TYPE_RAISE_HSSC, ParamUtil.RANKING_TYPE_DECLINE_HSSC,
            ParamUtil.RANKING_TYPE_TURNOVERRATE_HSSC, ParamUtil.RANKING_TYPE_AMOUNT_HSSC,"五分钟涨幅"};
    private String[] groupHK = {"涨幅榜", "跌幅榜", "换手率", "成交额"};
    private String stockType;
    private List<QuoteItem>[]  datas = new List[4];

    private CateSortingResponse[] mCateSortingResponseList = new CateSortingResponse[5];

    //第二个参数为股票类型
    public ExplsvAdapter(Context context,String stockType){
        super(context);
        this.stockType = stockType;
        if( stockType.equals("港股") ){
            group = groupHK;
            mCateSortingResponseList = new CateSortingResponse[4];
        }
    }

    public void setData(CateSortingResponse cateSortingResponse,int position){
        this.mCateSortingResponseList[position] =  cateSortingResponse;
        notifyDataSetChanged();
    }

    public void setData(List<QuoteItem> quoteItems,int positon){
        this.datas[positon] = quoteItems;
        notifyDataSetChanged();
    }
    /**
     * Gets the number of groups.
     * 获取组的个数
     */
    @Override
    public int getGroupCount() {
        return group.length;
    }

    /**
     * Gets the number of children in a specified group.
     * 获取指定组中的子元素个数
     */
    @Override
    public int getChildrenCount(int groupPosition) {
//        return datas[groupPosition] == null?0:datas[groupPosition].size();
        CateSortingResponse cateSortingResponse = mCateSortingResponseList[groupPosition];
        return cateSortingResponse == null?0: null == cateSortingResponse.list ?0 :cateSortingResponse.list.size();
    }

    /**
     * Gets the data associated with the given group.
     * 获取指定组中的数据
     */
    @Override
//    public List<QuoteItem> getGroup(int groupPosition) {
//        return datas[groupPosition];
    public CateSortingResponse getGroup(int groupPosition) {
        return mCateSortingResponseList[groupPosition];
    }

    /**
     * Gets the data associated with the given child within the given group.
     * 获取指定组中的指定子元素数据
     */
    @Override
    public QuoteItem getChild(int groupPosition, int childPosition) {
//        return datas[groupPosition].get(childPosition);
        return mCateSortingResponseList[groupPosition].list.get(childPosition);
    }

    /**
     * 获取指定组的ID，这个组ID必须是唯一的p
     */
    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    /**
     *获取指定组中的指定元素ID
     */
    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    /**
     *组合子元素是否持有稳定的ID，也就是底层数据的改变不会影响到它们
     */
    @Override
    public boolean hasStableIds() {
        return false;
    }

    /**
     *获得父项显示的view
     * boolean isExpanded 该组是展开状态还是收缩状态
     */
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        GroupHolder groupHolder;
        if(convertView == null){
            convertView = mLayoutInflater.inflate(R.layout.expendlist_gropup, parent,false);
            groupHolder = new GroupHolder();
            groupHolder.imgSign = (ImageView) convertView.findViewById(R.id.img_sign);
            groupHolder.tevType = (TextView) convertView.findViewById(R.id.tev_type);
            groupHolder.frlImg = (FrameLayout) convertView.findViewById(R.id.frm_img);
            groupHolder.imgMore = (ImageView) convertView.findViewById(R.id.img_more);
            convertView.setTag(groupHolder);
        } else {
            groupHolder = (GroupHolder) convertView.getTag();
        }

        //是否展开状态，加载不同的图片
        if(isExpanded){
            groupHolder.imgSign.setImageResource(R.drawable.img_below_indicator_1);
        }else {
            groupHolder.imgSign.setImageResource(R.drawable.img_right_indicator_1);
        }
        groupHolder.tevType.setText(group[groupPosition]);
        groupHolder.frlImg.setOnClickListener(this);

        //position与imgMore关联起来
        groupHolder.frlImg.setTag(groupPosition);
        return convertView;
    }

    /** 组位置，子元素位置，子元素是否处于组中的最后一个
     *获取子项显示的view
     */
    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ItemHolder itemHolder;
        if(convertView == null){
            convertView = mLayoutInflater.inflate(R.layout.expendlist_item, parent,false);
            itemHolder = new ItemHolder();
            itemHolder.tevCompany = (TextView) convertView.findViewById(R.id.tev_company);
            itemHolder.tevCode = (TextView) convertView.findViewById(R.id.tev_code);
            itemHolder.tevNewestPrice = (TextView) convertView.findViewById(R.id.tev_newestprice);
            itemHolder.tevRaiseRange = (TextView) convertView.findViewById(R.id.tev_raiserange);
            convertView.setTag(itemHolder);
        }else {
            itemHolder = (ItemHolder) convertView.getTag();
        }

//        QuoteItem quoteItem = datas[groupPosition].get(childPosition);
        QuoteItem quoteItem = mCateSortingResponseList[groupPosition].list.get(childPosition);
        //股票名字
        itemHolder.tevCompany.setText(quoteItem.name);
        //股票代码
        itemHolder.tevCode.setText(quoteItem.id);
        //最新价
        itemHolder.tevNewestPrice.setText(quoteItem.lastPrice);

        //涨跌比率、换手率、成交额
        String needData = null;
        int changeRateColor ,lastPricecolor;
        changeRateColor = lastPricecolor = FormatUtils.getLastPriceColor(quoteItem.upDownFlag,ColorUtils.DEFALUT());
        // 0,1,2,3为group数组中位置，即代表排行榜的类型
        switch(groupPosition){
            case 0:
                needData = FormatUtils.formatPercent(quoteItem.changeRate);
                break;
            case 1:
                needData = FormatUtils.formatPercent(quoteItem.changeRate);
                break;
            case 2:
                changeRateColor = ColorUtils.DEFALUT();
                needData = FormatUtils.formatPercent(quoteItem.turnoverRate);
                break;
            case 3:
                changeRateColor = ColorUtils.DEFALUT();
                needData = FormatUtils.format(quoteItem.amount);
                break;
            case 4:
                if (null != mCateSortingResponseList[groupPosition].addValueModel) {
                    AddValueModel addValueModel = mCateSortingResponseList[groupPosition].addValueModel.get(childPosition);
                    needData = FormatUtils.formatPercent(addValueModel.getFiveMinutesChangeRate());
                    if( addValueModel.getFiveMinutesChangeRate().startsWith(FillConfig.SUBTRACT)){
                        changeRateColor = ColorUtils.DOWN;
                    }else{
                        changeRateColor = ColorUtils.UP;
                    }
                }
            default:
                break;
        }
        itemHolder.tevRaiseRange.setText(needData);
        itemHolder.tevRaiseRange.setTextColor(changeRateColor);
        itemHolder.tevNewestPrice.setTextColor(lastPricecolor);
        return convertView;
    }

    /**
     *子选项可否被选中,如果需要设置子项的点击事件，需要返回true
     */
    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    /**
     * Called when a view has been clicked.
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        int position = (int) v.getTag();
         if( 4 == position ){
            // 5分钟涨幅榜
             RankingListFuturesActivity.actionStart(mContext,"5分钟涨幅");
        }else {
             if (stockType.equals("港股")) {
                 RankingListAllActivity.actionStart(mContext,stockType,group[position]);
             } else {
                 RankingListAllActivity.actionStart(mContext,stockType,group_hs[position]);
             }
        }
    }

    class GroupHolder{
        ImageView imgSign;
        TextView tevType;
        ImageView imgMore;
        FrameLayout frlImg;
    }

    class ItemHolder{
        TextView tevCompany;
        TextView tevCode;
        TextView tevNewestPrice;
        TextView tevRaiseRange;
    }
}
