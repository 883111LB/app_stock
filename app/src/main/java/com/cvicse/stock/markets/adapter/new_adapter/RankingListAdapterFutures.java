package com.cvicse.stock.markets.adapter.new_adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cvicse.stock.R;
import com.cvicse.stock.base.PBaseAdapter;
import com.cvicse.stock.common.ColorUtils;
import com.cvicse.stock.common.FillConfig;
import com.cvicse.stock.util.FormatUtils;
import com.cvicse.stock.utils.DataConvert;
import com.cvicse.stock.utils.TextUtils;
import com.mitake.core.QuoteItem;
import com.mitake.core.util.FormatUtility;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


/** 排序适配器（全球、外汇）
 * Created by tang_xqing on 18-7-30.
 */
public class RankingListAdapterFutures extends PBaseAdapter {
    private ArrayList<QuoteItem> rankingList;
    private String rankingType;

    public RankingListAdapterFutures(Context context, String rankingType) {
        super(context);
        this.rankingType = rankingType;
    }

    public void setData(ArrayList<QuoteItem> rankingList) {
        this.rankingList = DataConvert.QuoteItemList(rankingList);
        int length = null== rankingList ? 0 : rankingList.size();
        for (int i = length - 1; i > -1; i--) {
            QuoteItem quoteItem = rankingList.get(i);
            if (null ==quoteItem ) {
                rankingList.remove(i);
            }
        }
        notifyDataSetChanged();
    }

    public ArrayList<QuoteItem> getData() {
        return rankingList;
    }

    @Override
    public int getCount() {
        return  null == rankingList  ? 0 : rankingList.size();
    }

    @Override
    public QuoteItem getItem(int position) {
        return null == rankingList  ? null : rankingList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return null == rankingList  ? 0 : position;
    }

    @Override
    public View getView(int position,View convertView, ViewGroup parent) {
        ViewHolder holder;
        //观察convertView随ListView滚动情况
        if ( null==convertView ) {
            convertView = mLayoutInflater.inflate(R.layout.ranking_list_item_futures, parent, false);
            holder = new ViewHolder(convertView);

            convertView.setTag(holder);//绑定ViewHolder对象
        } else {
            holder = (ViewHolder) convertView.getTag();//取出ViewHolder对象
        }

        /**设置TextView显示的内容，即我们存放在动态数组中的数据*/
        QuoteItem quoteItem = rankingList.get(position);

        TextUtils.setText(holder.mTevName, quoteItem.name, FillConfig.DEFALUT);
        TextUtils.setText(holder.mTevId, quoteItem.id, FillConfig.DEFALUT);

        TextUtils.setText(holder.mTevLastPrice, quoteItem.lastPrice, FillConfig.DEFALUT);//最新价

        String symbol = FormatUtils.getSymbol(quoteItem.upDownFlag);   // 涨跌符号
        setChangeRate(holder,symbol,quoteItem.changeRate);
        setChange(holder,symbol,quoteItem.change);

        setHighPrice(holder, quoteItem);  // 最高价
        setLowPrice(holder, quoteItem); // 最低价

        //成交量
        TextUtils.setText(holder.mTevVolume, FormatUtility.formatVolume(quoteItem.volume), FillConfig.DEFALUT);
        // 昨收价
        TextUtils.setText(holder.mTevPreClosePrice, quoteItem.preClosePrice, FillConfig.DEFALUT);
        // 成交额
        TextUtils.setText(holder.mTevAmount, FormatUtils.format(quoteItem.amount), FillConfig.DEFALUT);

        int priceColor = FormatUtils.getLastPriceColor(quoteItem.upDownFlag, ColorUtils.DEFALUT());
        holder.mTevLastPrice.setTextColor(priceColor);
        holder.mTevChangeRate.setTextColor(priceColor);
        holder.mTevChange.setTextColor(priceColor);

        return convertView;
    }

    /**
     * 设置涨幅
     * @param holder
     */
    private void setChangeRate(ViewHolder holder,String symbol,String changeRate) {
        if(FormatUtils.isStandard(changeRate)&& !changeRate.contains("-") && !changeRate.contains("+") ){
            changeRate = symbol + changeRate;
        }
        TextUtils.setText(holder.mTevChangeRate,changeRate+"%");
    }

    /**
     * 设置涨跌
     * @param holder
     */
    private void setChange(ViewHolder holder,String symbol,String change) {
        if( FormatUtils.isStandard(change)&& !change.contains("-") && !change.contains("+") ){
            change = symbol + change;
        }
        TextUtils.setText(holder.mTevChange,change);
    }

    /**
     * 设置最高价
     * @param holder
     * @param quoteItem
     */
    private void setHighPrice(ViewHolder holder, QuoteItem quoteItem) {
        int color = ColorUtils.DEFALUT();
        if (TextUtils.setText(holder.mTevHighPrice, quoteItem.highPrice, FillConfig.DEFALUT)
                && FormatUtils.isStandard(quoteItem.preClosePrice)
                && FormatUtils.isStandard(quoteItem.highPrice)
                ) {
            if (Double.parseDouble(quoteItem.highPrice) > Double.parseDouble(quoteItem.preClosePrice)) {
                color = ColorUtils.UP;
            } else if (Double.parseDouble(quoteItem.highPrice) < Double.parseDouble(quoteItem.preClosePrice)) {
                color = ColorUtils.DOWN;
            }
        }
        holder.mTevHighPrice.setTextColor(color);
    }

    /**
     * 设置最低价
     * @param holder
     * @param quoteItem
     */
    private void setLowPrice(ViewHolder holder, QuoteItem quoteItem) {
        int color = ColorUtils.DEFALUT();
        if (TextUtils.setText(holder.mTevLowPrice, quoteItem.lowPrice, FillConfig.DEFALUT)
                && FormatUtils.isStandard(quoteItem.preClosePrice)
                && FormatUtils.isStandard(quoteItem.lowPrice)) {
            if (Double.parseDouble(quoteItem.lowPrice) > Double.parseDouble(quoteItem.preClosePrice)) {
                color = ColorUtils.UP;
            } else if (Double.parseDouble(quoteItem.lowPrice) < Double.parseDouble(quoteItem.preClosePrice)) {
                color = ColorUtils.DOWN;
            }
        }
        holder.mTevLowPrice.setTextColor(color);
    }

    class ViewHolder {
        //名称
        @BindView(R.id.tev_name) TextView mTevName;
        //公司代码
        @BindView(R.id.tev_id) TextView mTevId;
        //最新价
        @BindView(R.id.tev_lastPrice) TextView mTevLastPrice;
        //涨幅
        @BindView(R.id.tev_changeRate) TextView mTevChangeRate;
        //涨跌
        @BindView(R.id.tev_change) TextView mTevChange;
        //成交量
        @BindView(R.id.tev_volume) TextView mTevVolume;
        //成交额
        @BindView(R.id.tev_amount) TextView mTevAmount;
        //最高
        @BindView(R.id.tev_highPrice) TextView mTevHighPrice;
        //最低
        @BindView(R.id.tev_lowPrice) TextView mTevLowPrice;
        //昨收
        @BindView(R.id.tev_closePrice) TextView mTevPreClosePrice;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
