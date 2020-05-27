package com.cvicse.stock.markets.ui.other_option.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cvicse.base.utils.StringUtils;
import com.cvicse.stock.R;
import com.cvicse.stock.base.PBaseAdapter;
import com.cvicse.stock.common.ColorUtils;
import com.cvicse.stock.common.FillConfig;
import com.cvicse.stock.util.FormatUtils;
import com.cvicse.stock.utils.TextUtils;
import com.mitake.core.QuoteItem;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 大商所、郑商所期权列表adapter
 */
public class OtherOptionListAdapter extends PBaseAdapter {

    private ArrayList<QuoteItem> list;

    public OtherOptionListAdapter(Context context) {
        super(context);
    }

    public void setData(ArrayList<QuoteItem> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public QuoteItem getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if(convertView == null){
            convertView = mLayoutInflater.inflate(R.layout.item_other_option, parent, false);
            viewHolder = new ViewHolder(convertView);

            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        QuoteItem quoteItem = list.get(position);

        TextUtils.setText(viewHolder.mTevName,quoteItem.name, FillConfig.DOUBLE_LINE);
        TextUtils.setText(viewHolder.mTevId,quoteItem.id,FillConfig.DOUBLE_LINE);
        if(TextUtils.setText(viewHolder.mTevLastPrice,quoteItem.lastPrice,FillConfig.DOUBLE_LINE)){
            if(quoteItem.change.startsWith("+")){
                viewHolder.mTevLastPrice.setTextColor(ColorUtils.UP);
            }else if(quoteItem.change.startsWith("-")){
                viewHolder.mTevLastPrice.setTextColor(ColorUtils.DOWN);
            }
        }
        if(TextUtils.setTextPercent(viewHolder.mTevChangeRate,quoteItem.changeRate)){
            if(quoteItem.changeRate.startsWith("+")){
                viewHolder.mTevChangeRate.setTextColor(ColorUtils.UP);
            }else if(quoteItem.changeRate.startsWith("-")){
                viewHolder.mTevChangeRate.setTextColor(ColorUtils.DOWN);
            }
        }
        if(TextUtils.setText(viewHolder.mTevChange,quoteItem.change,FillConfig.DOUBLE_LINE)){
            if(quoteItem.change.startsWith("+")){
                viewHolder.mTevChange.setTextColor(ColorUtils.UP);
            }else if(quoteItem.change.startsWith("-")){
                viewHolder.mTevChange.setTextColor(ColorUtils.DOWN);
            }
        }
        TextUtils.setText(viewHolder.mAverageValue,quoteItem.averageValue, FillConfig.DOUBLE_LINE);// 均价
        TextUtils.setText(viewHolder.mClose,quoteItem.close, FillConfig.DOUBLE_LINE);// 今收价
        TextUtils.setText(viewHolder.mCurrDelta,quoteItem.currDelta, FillConfig.DOUBLE_LINE);// 今虚实度
        TextUtils.setText(viewHolder.mExePrice,quoteItem.exePrice, FillConfig.DOUBLE_LINE);// 执行价格
        TextUtils.setText(viewHolder.mLowPrice,quoteItem.lowPrice, FillConfig.DOUBLE_LINE);// 最低价
        TextUtils.setText(viewHolder.mOpenPrice,quoteItem.openPrice, FillConfig.DOUBLE_LINE);// 今开价
        TextUtils.setText(viewHolder.mPreDelta,quoteItem.preDelta, FillConfig.DOUBLE_LINE);// 昨虚实度
        TextUtils.setText(viewHolder.mPreSettlement,quoteItem.preSettlement, FillConfig.DOUBLE_LINE);// 昨结算
        TextUtils.setText(viewHolder.mPresetPrice,quoteItem.presetPrice, FillConfig.DOUBLE_LINE);// 前结算价
        TextUtils.setText(viewHolder.mSettlement,quoteItem.settlement, FillConfig.DOUBLE_LINE);// 今结算价
        TextUtils.setText(viewHolder.mEn,quoteItem.en, FillConfig.DOUBLE_LINE);// 期货品种
        TextUtils.setText(viewHolder.mLimitDown,quoteItem.limitDown, FillConfig.DOUBLE_LINE);// 跌停价
        TextUtils.setText(viewHolder.mLimitUp,quoteItem.limitUP, FillConfig.DOUBLE_LINE);// 涨停价
        TextUtils.setText(viewHolder.mPreClosePrice,quoteItem.preClosePrice, FillConfig.DOUBLE_LINE);// 昨收价

        viewHolder.mLnlName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nameItemClick.onNameItemClick(position);
            }
        });
        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.tev_name) TextView mTevName;
        @BindView(R.id.tev_id) TextView mTevId;
        @BindView(R.id.tev_lastPrice) TextView mTevLastPrice;
        @BindView(R.id.tev_changeRate) TextView mTevChangeRate;
        @BindView(R.id.tev_change) TextView mTevChange;
        @BindView(R.id.tev_averageValue) TextView mAverageValue;// 均价
        @BindView(R.id.tev_close) TextView mClose;// 今收价
        @BindView(R.id.tev_currDelta) TextView mCurrDelta;// 今虚实度
        @BindView(R.id.tev_exePrice) TextView mExePrice;// 执行价格
        @BindView(R.id.tev_lowPrice) TextView mLowPrice;// 最低价
        @BindView(R.id.tev_openPrice) TextView mOpenPrice;// 今开价
        @BindView(R.id.tev_preDelta) TextView mPreDelta;// 昨虚实度
        @BindView(R.id.tev_preSettlement) TextView mPreSettlement;// 昨结算
        @BindView(R.id.tev_presetPrice) TextView mPresetPrice;// 前结算价
        @BindView(R.id.tev_settlement) TextView mSettlement;// 今结算价
        @BindView(R.id.tev_en) TextView mEn;// 期货品种
        @BindView(R.id.tev_limitDown) TextView mLimitDown;// 跌停价
        @BindView(R.id.tev_limitUp) TextView mLimitUp;// 涨停价
        @BindView(R.id.tev_preClosePrice) TextView mPreClosePrice;// 昨收价

        @BindView(R.id.lnl_name) LinearLayout mLnlName;// 左边名称
        @BindView(R.id.lnl_info) LinearLayout mLnlInfo;// 右边内容

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    /**
     * 左侧名称点击事件监听类
     */
    private NameItemClick nameItemClick;

    public void setNameItemClick(NameItemClick nameItemClick){
        this.nameItemClick = nameItemClick;
    }

    public interface NameItemClick{
        void onNameItemClick(int position);
    }
}
