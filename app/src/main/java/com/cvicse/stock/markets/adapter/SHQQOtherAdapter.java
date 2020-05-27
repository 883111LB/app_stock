package com.cvicse.stock.markets.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cvicse.stock.BaseApplication;
import com.cvicse.stock.R;
import com.cvicse.stock.base.PBaseAdapter;
import com.cvicse.stock.common.ColorUtils;
import com.cvicse.stock.util.FormatUtils;
import com.cvicse.stock.utils.TextUtils;
import com.cvicse.stock.view.CHScrollView;
import com.cvicse.stock.view.CHScrollViewHlper;
import com.mitake.core.QuoteItem;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;



/**
 * Created by ding_syi on 17-2-16.
 */
public class SHQQOtherAdapter extends PBaseAdapter {
    private ArrayList<QuoteItem> myStockBeanList;

    private CHScrollViewHlper chScrollViewHlper;

    private int listSize = 0;

    int colorRed = ContextCompat.getColor(BaseApplication.getInstance(), R.color.text_red);
    int colorGreen = ContextCompat.getColor(BaseApplication.getInstance(), R.color.text_green);

    public SHQQOtherAdapter(Context context, CHScrollViewHlper chScrollViewHlper) {
        super(context);
        this.chScrollViewHlper = chScrollViewHlper;
    }

    public void setData(ArrayList<QuoteItem> myStockBeanList) {

        if( null == myStockBeanList || myStockBeanList.isEmpty() ){
            return;
        }
        this.myStockBeanList = myStockBeanList;
        myStockBeanList.remove(myStockBeanList.size()-1);
        listSize = myStockBeanList.size();
        notifyDataSetChanged();
    }

    public ArrayList<QuoteItem> getData(){
        return myStockBeanList;
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
        ViewHolder viewHolder;
        //观察convertView随ListView滚动情况
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.option_list_item, parent, false);
            viewHolder = new ViewHolder(convertView);

            chScrollViewHlper.addHViews(viewHolder.mItemScroll);
            convertView.setTag(viewHolder);//绑定ViewHolder对象
        } else {
            viewHolder = (ViewHolder) convertView.getTag();//取出ViewHolder对象
        }
        /**设置TextView显示的内容，即我们存放在动态数组中的数据*/

        //倒叙排序
        QuoteItem quoteItem = myStockBeanList.get(position);


        TextUtils.setText(viewHolder.mStockName,quoteItem.name,"--");
        TextUtils.setText(viewHolder.mStockId,quoteItem.id,"--");

        if(TextUtils.setText(viewHolder.mLastprice,quoteItem.lastPrice,"--")){
            if(quoteItem.changeRate.startsWith("-")){
                viewHolder.mLastprice.setTextColor(ColorUtils.DOWN);
            }else if(quoteItem.changeRate.startsWith("+")){
                viewHolder.mLastprice.setTextColor(ColorUtils.UP);
            }
        }else{
            viewHolder.mLastprice.setTextColor(ColorUtils.DEFALUT());
        }

        if(TextUtils.setTextPercent(viewHolder.mChangerate,quoteItem.changeRate)){
            if(quoteItem.changeRate.startsWith("-")){
                viewHolder.mChangerate.setTextColor(ColorUtils.DOWN);
            }else if(quoteItem.changeRate.startsWith("+")){
                viewHolder.mChangerate.setTextColor(ColorUtils.UP);
            }
        }else{
            viewHolder.mChangerate.setTextColor(ColorUtils.DEFALUT());
        }

        if(TextUtils.setText(viewHolder.mChange,quoteItem.change,"--")){
            if(quoteItem.change.startsWith("-")){
                viewHolder.mChange.setTextColor(ColorUtils.DOWN);
            }else if(quoteItem.change.startsWith("+")){
                viewHolder.mChange.setTextColor(ColorUtils.UP);
            }
        }else{
            viewHolder.mChange.setTextColor(ColorUtils.DEFALUT());
        }

        if(TextUtils.setText(viewHolder.mBuyprice,quoteItem.buyPrice,"--")){
            if(quoteItem.changeRate.startsWith("-")){
                viewHolder.mBuyprice.setTextColor(ColorUtils.DOWN);
            }else if(quoteItem.changeRate.startsWith("+")){
                viewHolder.mBuyprice.setTextColor(ColorUtils.UP);
            }
        }else{
            viewHolder.mBuyprice.setTextColor(ColorUtils.DEFALUT());
        }

        if(TextUtils.setText(viewHolder.mSellprice,quoteItem.sellPrice,"--")){
            if(quoteItem.changeRate.startsWith("-")){
                viewHolder.mSellprice.setTextColor(ColorUtils.DOWN);
            }else if(quoteItem.changeRate.startsWith("+")){
                viewHolder.mSellprice.setTextColor(ColorUtils.UP);
            }
        }else{
            viewHolder.mSellprice.setTextColor(ColorUtils.DEFALUT());
        }

        if(TextUtils.setText(viewHolder.mVolume, FormatUtils.getVol(quoteItem.volume),"--")){
            viewHolder.mVolume.setTextColor(ColorUtils.Yellow);
        }else{
            viewHolder.mVolume.setTextColor(ColorUtils.DEFALUT());
        }

        if(TextUtils.setText(viewHolder.mAmount, FormatUtils.getVol(quoteItem.amount),"--")){
            viewHolder.mAmount.setTextColor(ColorUtils.Yellow);
        }else{
            viewHolder.mAmount.setTextColor(ColorUtils.DEFALUT());
        }

        if(TextUtils.setText(viewHolder.mPeninterest,quoteItem.openInterest,"--")){
            if(quoteItem.changeRate.startsWith("-")){
                viewHolder.mPeninterest.setTextColor(ColorUtils.DOWN);
            }else if(quoteItem.changeRate.startsWith("+")){
                viewHolder.mPeninterest.setTextColor(ColorUtils.UP);
            }
        }else{
            viewHolder.mPeninterest.setTextColor(ColorUtils.DEFALUT());
        }

      /*  if(gouItem.exePrice.equals(guItem.exePrice) && gouItem.exePrice!= null ){
            TextUtils.setText(viewHolder.tevExePrice,Long.parseLong(gouItem.exePrice)/10000.0+"","--");
        }*/
        return convertView;
    }


    static class ViewHolder {
        /**
         * 名称
         */
        @BindView(R.id.tev_name) TextView mStockName;
        /**
         * id
         */
        @BindView(R.id.tev_id) TextView mStockId;
        /**
         * 最新
         */
        @BindView(R.id.tev_lastPrice) TextView mLastprice;
        /**
         * 涨幅
         */
        @BindView(R.id.tev_changeRate) TextView mChangerate;
        /**
         * 涨跌
         */
        @BindView(R.id.tev_change) TextView mChange;
        /**
         * 买价
         */
        @BindView(R.id.tev_buyprices) TextView mBuyprice;
        /**
         * 卖价
         */
        @BindView(R.id.tev_sellprices) TextView mSellprice;
        /**
         * 总量
         */
        @BindView(R.id.tev_volume) TextView mVolume;
        /**
         * 金额
         */
        @BindView(R.id.tev_amount) TextView mAmount;

        /**
         * 持仓量
         */
        @BindView(R.id.tev_openinterest) TextView mPeninterest;

        @BindView(R.id.item_scroll) CHScrollView mItemScroll;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

}
