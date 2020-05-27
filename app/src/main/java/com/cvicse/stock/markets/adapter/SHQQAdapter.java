package com.cvicse.stock.markets.adapter;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cvicse.stock.R;
import com.cvicse.stock.base.PBaseAdapter;
import com.cvicse.stock.common.ColorUtils;
import com.cvicse.stock.common.FillConfig;
import com.cvicse.stock.markets.ui.StockDetailActivity;
import com.cvicse.stock.util.FormatUtils;
import com.cvicse.stock.utils.TextUtils;
import com.cvicse.stock.view.CHScrollView;
import com.cvicse.stock.view.CHScrollViewHlper;
import com.cvicse.stock.view.PressedLinearLayout;
import com.mitake.core.QuoteItem;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ding_syi on 17-2-9.
 */
public class SHQQAdapter extends PBaseAdapter {
    private ArrayList<QuoteItem> shqqBoListGou; //认购

    private ArrayList<QuoteItem> shqqBoListGu; //认沽

    private CHScrollViewHlper chScrollViewHlper;
    private Context context;

    private int width;

    public SHQQAdapter(Context context, CHScrollViewHlper chScrollViewHlper, int width) {
        super(context);
        this.context = context;
        this.width = width;
        this.chScrollViewHlper = chScrollViewHlper;
    }

    public void setData(ArrayList<QuoteItem> shqqBoListGou,ArrayList<QuoteItem> shqqBoListGu) {
        this.shqqBoListGou = shqqBoListGou;
        this.shqqBoListGu = shqqBoListGu;
        notifyDataSetChanged();
    }


    @Override
    public int getCount() {
        return shqqBoListGou == null ? 0 : shqqBoListGou.size();
    }


    @Override
    public Object getItem(int position) {
        return shqqBoListGou.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.item_ptrlst_shqq, parent, false);

            viewHolder = new ViewHolder(convertView);

            chScrollViewHlper.addHViews(viewHolder.mItemScrollRight);
            viewHolder.mItemScrollRight.setUserParent(true);
            viewHolder.mItemScrollLeft.setUserParent(true);
            viewHolder.mItemScrollRight.setTag(1);
            viewHolder.mItemScrollLeft.setTag(2);
            new Handler().post(new Runnable() {
                @Override
                public void run() {
                    //改变初始位置
                    viewHolder.mItemScrollLeft.setLeftOrRight("left");
                    viewHolder.mItemScrollLeft.scrollTo(width * 6, 0);
                }
            });

            chScrollViewHlper.addHViews(viewHolder.mItemScrollLeft);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        LinearLayout.LayoutParams linearParams;
        //取控件textView当前的布局参数
        linearParams = new LinearLayout.LayoutParams(width, LinearLayout.LayoutParams.WRAP_CONTENT);

        viewHolder.mGouPeninterest.setLayoutParams(linearParams);
        viewHolder.mGouAmount.setLayoutParams(linearParams);
        viewHolder.mGouVolume.setLayoutParams(linearParams);
        viewHolder.mGouSellprice.setLayoutParams(linearParams);
        viewHolder.mGouBuyprice.setLayoutParams(linearParams);
        viewHolder.mGouChange.setLayoutParams(linearParams);
        viewHolder.mGouChangerate.setLayoutParams(linearParams);
        viewHolder.mGouLastprice.setLayoutParams(linearParams);
        viewHolder.tevExePrice.setLayoutParams(linearParams);
        viewHolder.mGuLastprice.setLayoutParams(linearParams);
        viewHolder.mGuChangerate.setLayoutParams(linearParams);
        viewHolder.mGuChange.setLayoutParams(linearParams);
        viewHolder.mGuBuyprice.setLayoutParams(linearParams);
        viewHolder.mGuSellprice.setLayoutParams(linearParams);
        viewHolder.mGuVolume.setLayoutParams(linearParams);
        viewHolder.mGuAmount.setLayoutParams(linearParams);
        viewHolder.mGuPeninterest.setLayoutParams(linearParams);

        //取控件textView当前的布局参数
        linearParams = new LinearLayout.LayoutParams(width * 2,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        viewHolder.mItemScrollLeft.setLayoutParams(linearParams);
        viewHolder.mItemScrollRight.setLayoutParams(linearParams);

        QuoteItem gouItem = shqqBoListGou.get(position);
        QuoteItem guItem = shqqBoListGu.get(position);

        if(TextUtils.setText( viewHolder.mGouPeninterest,gouItem.openInterest,FillConfig.DOUBLE_LINE)){
            if(null != gouItem.changeRate && gouItem.changeRate.startsWith("-")){
                viewHolder.mGouPeninterest.setTextColor(ColorUtils.DOWN);
            }else if(null != gouItem.changeRate && gouItem.changeRate.startsWith("+")){
                viewHolder.mGouPeninterest.setTextColor(ColorUtils.UP);
            }
        }else{
            viewHolder.mGouPeninterest.setTextColor(ColorUtils.DEFALUT());
        }

        if(TextUtils.setText(viewHolder.mGouAmount, FormatUtils.format(gouItem.amount),FillConfig.DOUBLE_LINE)){
            viewHolder.mGouAmount.setTextColor(ColorUtils.Yellow);
        }else{
            viewHolder.mGouAmount.setTextColor(ColorUtils.DEFALUT());
        }

        if(TextUtils.setText(viewHolder.mGouVolume, FormatUtils.format(gouItem.volume),FillConfig.DOUBLE_LINE)){
            viewHolder.mGouVolume.setTextColor(ColorUtils.Yellow);
        }else{
            viewHolder.mGouVolume.setTextColor(ColorUtils.DEFALUT());
        }

        if(TextUtils.setText(viewHolder.mGouSellprice,gouItem.sellPrice,FillConfig.DOUBLE_LINE)){
            if(gouItem.changeRate.startsWith("-")){
                viewHolder.mGouSellprice.setTextColor(ColorUtils.DOWN);
            }else if(gouItem.changeRate.startsWith("+")){
                viewHolder.mGouSellprice.setTextColor(ColorUtils.UP);
            }
        }else{
            viewHolder.mGouSellprice.setTextColor(ColorUtils.DEFALUT());
        }

        if(TextUtils.setText(viewHolder.mGouBuyprice,gouItem.buyPrice,FillConfig.DOUBLE_LINE)){
            if(gouItem.changeRate.startsWith("-")){
                viewHolder.mGouBuyprice.setTextColor(ColorUtils.DOWN);
            }else if(gouItem.changeRate.startsWith("+")){
                viewHolder.mGouBuyprice.setTextColor(ColorUtils.UP);
            }
        }else{
            viewHolder.mGouBuyprice.setTextColor(ColorUtils.DEFALUT());
        }

        if(TextUtils.setText(viewHolder.mGouChange,gouItem.change,FillConfig.DOUBLE_LINE)){
            if(gouItem.change.startsWith("-")){
                viewHolder.mGouChange.setTextColor(ColorUtils.DOWN);
            }else if(gouItem.change.startsWith("+")){
                viewHolder.mGouChange.setTextColor(ColorUtils.UP);
            }
        }else{
            viewHolder.mGouChange.setTextColor(ColorUtils.DEFALUT());
        }

        if(TextUtils.setTextPercent(viewHolder.mGouChangerate,gouItem.changeRate)){
            if(null != gouItem.changeRate && gouItem.changeRate.startsWith("-")){
                viewHolder.mGouChangerate.setTextColor(ColorUtils.DOWN);
            }else if(null != gouItem.changeRate && gouItem.changeRate.startsWith("+")){
                viewHolder.mGouChangerate.setTextColor(ColorUtils.UP);
            }
        }else{
            viewHolder.mGouChangerate.setTextColor(ColorUtils.DEFALUT());
        }

        if(TextUtils.setText(viewHolder.mGouLastprice,gouItem.lastPrice, FillConfig.DOUBLE_LINE)){
            if(gouItem.changeRate.startsWith("-")){
                viewHolder.mGouLastprice.setTextColor(ColorUtils.DOWN);
            }else if(gouItem.changeRate.startsWith("+")){
                viewHolder.mGouLastprice.setTextColor(ColorUtils.UP);
            }
        }else{
            viewHolder.mGouLastprice.setTextColor(ColorUtils.DEFALUT());
        }


        if(TextUtils.setText( viewHolder.mGuPeninterest,guItem.openInterest,FillConfig.DOUBLE_LINE)){
            if(null != guItem.changeRate && guItem.changeRate.startsWith("-")){
                viewHolder.mGuPeninterest.setTextColor(ColorUtils.DOWN);
            }else if(null != guItem.changeRate && guItem.changeRate.startsWith("+")){
                viewHolder.mGuPeninterest.setTextColor(ColorUtils.UP);
            }
        }else{
            viewHolder.mGuPeninterest.setTextColor(ColorUtils.DEFALUT());
        }

        if(TextUtils.setText(viewHolder.mGuAmount, FormatUtils.format(guItem.amount),FillConfig.DOUBLE_LINE)){
            viewHolder.mGuAmount.setTextColor(ColorUtils.Yellow);
        }else{
            viewHolder.mGuAmount.setTextColor(ColorUtils.DEFALUT());
        }

        if(TextUtils.setText(viewHolder.mGuVolume, FormatUtils.format(guItem.volume),FillConfig.DOUBLE_LINE)){
            viewHolder.mGuVolume.setTextColor(ColorUtils.Yellow);
        }else{
            viewHolder.mGuVolume.setTextColor(ColorUtils.DEFALUT());
        }

        if(TextUtils.setText(viewHolder.mGuSellprice,gouItem.sellPrice,FillConfig.DOUBLE_LINE)){
            if(null != gouItem.changeRate && guItem.changeRate.startsWith("-")){
                viewHolder.mGuSellprice.setTextColor(ColorUtils.DOWN);
            }else if(null != gouItem.changeRate && guItem.changeRate.startsWith("+")){
                viewHolder.mGuSellprice.setTextColor(ColorUtils.UP);
            }
        }else{
            viewHolder.mGuSellprice.setTextColor(ColorUtils.DEFALUT());
        }

        if(TextUtils.setText(viewHolder.mGuBuyprice,gouItem.buyPrice,FillConfig.DOUBLE_LINE)){
            if(null != gouItem.changeRate && guItem.changeRate.startsWith("-")){
                viewHolder.mGuBuyprice.setTextColor(ColorUtils.DOWN);
            }else if(null != gouItem.changeRate && guItem.changeRate.startsWith("+")){
                viewHolder.mGuBuyprice.setTextColor(ColorUtils.UP);
            }
        }else{
            viewHolder.mGuBuyprice.setTextColor(ColorUtils.DEFALUT());
        }

        if(TextUtils.setText(viewHolder.mGuChange,gouItem.change,FillConfig.DOUBLE_LINE)){
            if(null != gouItem.changeRate && gouItem.change.startsWith("-")){
                viewHolder.mGuChange.setTextColor(ColorUtils.DOWN);
            }else if(null != gouItem.changeRate && gouItem.change.startsWith("+")){
                viewHolder.mGuChange.setTextColor(ColorUtils.UP);
            }
        }else{
            viewHolder.mGuChange.setTextColor(ColorUtils.DEFALUT());
        }

        if(TextUtils.setTextPercent(viewHolder.mGuChangerate,guItem.changeRate)){
            if(null != gouItem.changeRate && guItem.changeRate.startsWith("-")){
                viewHolder.mGuChangerate.setTextColor(ColorUtils.DOWN);
            }else if(null != gouItem.changeRate && guItem.changeRate.startsWith("+")){
                viewHolder.mGuChangerate.setTextColor(ColorUtils.UP);
            }
        }else{
            viewHolder.mGuChangerate.setTextColor(ColorUtils.DEFALUT());
        }

        if(TextUtils.setText(viewHolder.mGuLastprice,guItem.lastPrice,FillConfig.DOUBLE_LINE)){
            if(null != gouItem.changeRate && guItem.changeRate.startsWith("-")){
                viewHolder.mGuLastprice.setTextColor(ColorUtils.DOWN);
            }else if(null != gouItem.changeRate && guItem.changeRate.startsWith("+")){
                viewHolder.mGuLastprice.setTextColor(ColorUtils.UP);
            }
        }else{
            viewHolder.mGuLastprice.setTextColor(ColorUtils.DEFALUT());
        }

        if(gouItem.exePrice.equals(guItem.exePrice) && gouItem.exePrice!= null ){
//            TextUtils.setText(viewHolder.tevExePrice,Long.parseLong(gouItem.exePrice)/10000.0+"",FillConfig.DOUBLE_LINE);
            TextUtils.setText(viewHolder.tevExePrice,gouItem.exePrice);
        }

        viewHolder.mItemScrollLeft.getChildAt(0).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StockDetailActivity.startActivity((Activity)context,shqqBoListGou,position);
            }
        });

        viewHolder.mItemScrollRight.getChildAt(0).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StockDetailActivity.startActivity((Activity)context,shqqBoListGu,position);
            }
        });

        return convertView;
    }

    class ViewHolder {

        @BindView(R.id.gou_peninterest) TextView mGouPeninterest;  // 持仓量
        @BindView(R.id.gou_amount) TextView mGouAmount;
        @BindView(R.id.gou_volume) TextView mGouVolume;
        @BindView(R.id.gou_sellprice) TextView mGouSellprice;
        @BindView(R.id.gou_buyprice) TextView mGouBuyprice;
        @BindView(R.id.gou_change) TextView mGouChange;
        @BindView(R.id.gou_changerate) TextView mGouChangerate;
        @BindView(R.id.gou_lastprice) TextView mGouLastprice;

        //左边的
        @BindView(R.id.item_scroll2) CHScrollView mItemScrollLeft;
        @BindView(R.id.tev_exeprice) TextView tevExePrice;

        @BindView(R.id.gu_lastprice) TextView mGuLastprice;
        @BindView(R.id.gu_changerate) TextView mGuChangerate;
        @BindView(R.id.gu_change) TextView mGuChange;
        @BindView(R.id.gu_buyprice) TextView mGuBuyprice;
        @BindView(R.id.gu_sellprice) TextView mGuSellprice;
        @BindView(R.id.gu_volume) TextView mGuVolume;
        @BindView(R.id.gu_amount) TextView mGuAmount;
        /**
         *  持仓量
         */
        @BindView(R.id.gu_peninterest) TextView mGuPeninterest;

        //右边的
        @BindView(R.id.item_scroll) CHScrollView mItemScrollRight;
        @BindView(R.id.plr_item_left) PressedLinearLayout mPlrLeft;
        @BindView(R.id.plr_item_right) PressedLinearLayout mPlrRight;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
