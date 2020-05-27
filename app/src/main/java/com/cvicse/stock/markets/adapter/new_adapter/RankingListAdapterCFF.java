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


/**
 * 排序（期货）
 * Created by tang_xqing on 17-11-22.
 */
public class RankingListAdapterCFF extends PBaseAdapter {
    private ArrayList<QuoteItem> rankingList;
    private String rankingType;

    public RankingListAdapterCFF(Context context, String rankingType) {
        super(context);
        this.rankingType = rankingType;
    }

    public void setData(ArrayList<QuoteItem> rankingList) {
        this.rankingList = DataConvert.QuoteItemList(rankingList);

        int length =  null== rankingList ? 0 : rankingList.size();
        for (int i = length - 1; i > -1; i--) {
            QuoteItem quoteItem = rankingList.get(i);
            if ( null== quoteItem) {
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
        return  null==rankingList  ? 0 : rankingList.size();
    }

    @Override
    public QuoteItem getItem(int position) {
        return rankingList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        //观察convertView随ListView滚动情况
        if ( null== convertView) {
            convertView = mLayoutInflater.inflate(R.layout.ranking_list_item_cff, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);//绑定ViewHolder对象
        } else {
            holder = (ViewHolder) convertView.getTag();//取出ViewHolder对象
        }

        /**设置TextView显示的内容，即我们存放在动态数组中的数据*/
        QuoteItem quoteItem = rankingList.get(position);
        String symbol = FormatUtils.getSymbol(quoteItem.upDownFlag);   // 涨跌符号

        TextUtils.setText(holder.mTevName, quoteItem.name);
        TextUtils.setText(holder.mTevId, quoteItem.id);

        TextUtils.setText(holder.tev_prev_close, quoteItem.preClosePrice, FillConfig.DEFALUT);// 昨收价
        TextUtils.setText(holder.tev_open, quoteItem.openPrice, FillConfig.DEFALUT);// 开盘
        setHighPrice(holder, quoteItem);  // 最高价
        setLowPrice(holder, quoteItem); // 最低价
        TextUtils.setText(holder.tev_last, quoteItem.lastPrice, FillConfig.DEFALUT); // 最新价
        TextUtils.setText(holder.tev_avg, quoteItem.averageValue, FillConfig.DEFALUT);// 均价
        setChange(holder,symbol,quoteItem.change);// 涨跌
        setChangeRate(holder,symbol,quoteItem.changeRate);// 涨跌幅
        //成交量
        TextUtils.setText(holder.tev_volume, FormatUtility.formatVolume(quoteItem.volume), FillConfig.DEFALUT);
        TextUtils.setText(holder.tev_amount, FormatUtils.format(quoteItem.amount), FillConfig.DEFALUT);// 成交金额
        TextUtils.setText(holder.tev_now_vol, quoteItem.nowVolume, FillConfig.DEFALUT);// 当前成交量
        TextUtils.setText(holder.tev_sell_vol, quoteItem.sellVolume, FillConfig.DEFALUT);// 内盘
        TextUtils.setText(holder.tev_buy_vol, quoteItem.buyVolume, FillConfig.DEFALUT);// 外盘
        TextUtils.setText(holder.tev_tradingday, quoteItem.tradingDay, FillConfig.DEFALUT);// 交易日
        TextUtils.setText(holder.tev_settlementGroupID, quoteItem.settlementGroupID, FillConfig.DEFALUT);// 结算组代码
        TextUtils.setText(holder.tev_settlementID, quoteItem.settlementID, FillConfig.DEFALUT);// 结算编号
        TextUtils.setText(holder.tev_preSettlement, quoteItem.preSettlement, FillConfig.DEFALUT);// 昨结算
        TextUtils.setText(holder.tev_preOpenInterest, quoteItem.preInterest, FillConfig.DEFALUT);// 昨持仓量
        TextUtils.setText(holder.tev_opt, quoteItem.openInterest, FillConfig.DEFALUT);// 持仓量
        TextUtils.setText(holder.tev_position_chg, quoteItem.position_chg, FillConfig.DEFALUT);// 日增
        TextUtils.setText(holder.tev_close, quoteItem.close, FillConfig.DEFALUT);// 今收盘价
        TextUtils.setText(holder.tev_settlement, quoteItem.settlement, FillConfig.DEFALUT);// 今结算价
        TextUtils.setText(holder.tev_upperLimit, quoteItem.limitUP, FillConfig.DEFALUT);// 涨停价
        TextUtils.setText(holder.tev_downLimit, quoteItem.limitDown, FillConfig.DEFALUT);// 跌停价
        TextUtils.setText(holder.tev_preDelta, quoteItem.preDelta, FillConfig.DEFALUT);// 昨虚实度
        TextUtils.setText(holder.tev_currDelta, quoteItem.currDelta, FillConfig.DEFALUT);// 今虚实度
        TextUtils.setText(holder.tev_updateMillisec, quoteItem.updateMillisec, FillConfig.DEFALUT);// 最后修改毫秒
        TextUtils.setText(holder.tev_type, quoteItem.optionType, FillConfig.DEFALUT);// 类型
        TextUtils.setText(holder.tev_underlyingLastPx, quoteItem.underlyingLastPx, FillConfig.DEFALUT);// 标的现价
        TextUtils.setText(holder.tev_underlyingPreClose, quoteItem.underlyingPreClose, FillConfig.DEFALUT);// 标的昨收
        TextUtils.setText(holder.tev_underlyingchg, quoteItem.underlyingchg, FillConfig.DEFALUT);// 标的涨跌
        TextUtils.setText(holder.tev_tradeStatus, quoteItem.stateOfTransfer, FillConfig.DEFALUT);// 交易状态（转让状态）
        TextUtils.setText(holder.tev_change1, quoteItem.change1, FillConfig.DEFALUT);// change1
        TextUtils.setText(holder.tev_amp_rate, quoteItem.amplitudeRate, FillConfig.DEFALUT);// 振幅
        TextUtils.setText(holder.tev_callOrPut, quoteItem.optionType, FillConfig.DEFALUT);// 期权类型
        TextUtils.setText(holder.tev_excercisePx, quoteItem.exePrice, FillConfig.DEFALUT);// 行权价
        TextUtils.setText(holder.tev_premiumRate, quoteItem.premiumRate, FillConfig.DEFALUT);// 溢价率
        TextUtils.setText(holder.tev_remainingDays, quoteItem.remainDate, FillConfig.DEFALUT);// 剩余天数
        TextUtils.setText(holder.tev_impliedVolatility, quoteItem.impliedVolatility, FillConfig.DEFALUT);// 隐含波动率
        TextUtils.setText(holder.tev_riskFreeInRate, quoteItem.riskFreeInterestRate, FillConfig.DEFALUT);// 无风险利率
        TextUtils.setText(holder.tev_riskIndicator, quoteItem.riskIndicator, FillConfig.DEFALUT);// 风险指标
        TextUtils.setText(holder.tev_leverageRatio, quoteItem.leverageRatio, FillConfig.DEFALUT);// 杠杆比率
        TextUtils.setText(holder.tev_intersectionNum, quoteItem.intersectionNum, FillConfig.DEFALUT);// 交割点数

        TextUtils.setText(holder.tev_entrustDiff, quoteItem.entrustDiff, FillConfig.DEFALUT);// 委差
        TextUtils.setText(holder.tev_entrustRatio, quoteItem.orderRatio, FillConfig.DEFALUT);// 委比
        TextUtils.setText(holder.tev_currDiff, quoteItem.currDiff, FillConfig.DEFALUT);// 期现差
        TextUtils.setText(holder.tev_underlyingType, quoteItem.underlyingType, FillConfig.DEFALUT);// 标的品种
        TextUtils.setText(holder.tev_posDiff, quoteItem.posDiff, FillConfig.DEFALUT);// 仓差
        TextUtils.setText(holder.tev_deliveryDay, quoteItem.deliveryDay, FillConfig.DEFALUT);// 交割日期
        TextUtils.setText(holder.tev_totalBid, quoteItem.totalBid, FillConfig.DEFALUT);// 委买
        TextUtils.setText(holder.tev_totalAsk, quoteItem.totalAsk, FillConfig.DEFALUT);// 委卖
        // 买一价
        if (null != quoteItem.buyPrices && !quoteItem.buyPrices.isEmpty())
            TextUtils.setText(holder.tev_bidpx1, quoteItem.buyPrices.get(quoteItem.buyPrices.size() - 1), FillConfig.DEFALUT);
        // 买一量
        if (null != quoteItem.buyVolumes && !quoteItem.buyVolumes.isEmpty())
            TextUtils.setText(holder.tev_bidvol1, quoteItem.buyVolumes.get(quoteItem.buyVolumes.size() - 1), FillConfig.DEFALUT);
        // 卖一价
        if (null != quoteItem.sellPrices && !quoteItem.sellPrices.isEmpty())
            TextUtils.setText(holder.tev_askpx1, quoteItem.sellPrices.get(0), FillConfig.DEFALUT);
        // 卖一量
        if (null != quoteItem.sellVolumes && !quoteItem.sellVolumes.isEmpty())
            TextUtils.setText(holder.tev_askvol1, quoteItem.sellVolumes.get(0), FillConfig.DEFALUT);


        return convertView;
    }

    /**
     * 设置涨幅
     * @param holder
     */
    private void setChangeRate(ViewHolder holder, String symbol, String changeRate) {
        if( !changeRate.contains("-") && !changeRate.contains("+") ){
            changeRate = symbol + changeRate;
        }
        TextUtils.setText(holder.tev_chg_rate,changeRate+"%");
    }

    /**
     * 设置涨跌
     * @param holder
     */
    private void setChange(ViewHolder holder, String symbol, String change) {
        if( !change.contains("-") && !change.contains("+") ){
            change = symbol + change;
        }
        TextUtils.setText(holder.tev_change,change);
    }

    /**
     * 设置最高价
     * @param holder
     * @param quoteItem
     */
    private void setHighPrice(ViewHolder holder, QuoteItem quoteItem) {
        int color = ColorUtils.DEFALUT();
        if (TextUtils.setText(holder.tev_high, quoteItem.highPrice)
                && FormatUtils.isStandard(quoteItem.preClosePrice)
                && FormatUtils.isStandard(quoteItem.highPrice)
                ) {
            if (Double.parseDouble(quoteItem.highPrice) > Double.parseDouble(quoteItem.preClosePrice)) {
                color = ColorUtils.UP;
            } else if (Double.parseDouble(quoteItem.highPrice) < Double.parseDouble(quoteItem.preClosePrice)) {
                color = ColorUtils.DOWN;
            }
        }
        holder.tev_high.setTextColor(color);
    }

    /**
     * 设置最低价
     * @param holder
     * @param quoteItem
     */
    private void setLowPrice(ViewHolder holder, QuoteItem quoteItem) {
        int color = ColorUtils.DEFALUT();
        if (TextUtils.setText(holder.tev_low, quoteItem.lowPrice)
                && FormatUtils.isStandard(quoteItem.preClosePrice)
                && FormatUtils.isStandard(quoteItem.lowPrice)) {
            if (Double.parseDouble(quoteItem.lowPrice) > Double.parseDouble(quoteItem.preClosePrice)) {
                color = ColorUtils.UP;
            } else if (Double.parseDouble(quoteItem.lowPrice) < Double.parseDouble(quoteItem.preClosePrice)) {
                color = ColorUtils.DOWN;
            }
        }
        holder.tev_low.setTextColor(color);
    }

    static class ViewHolder {
        //名称
        @BindView(R.id.tev_name) TextView mTevName;
        //公司代码
        @BindView(R.id.tev_id) TextView mTevId;
        @BindView(R.id.tev_prev_close) TextView tev_prev_close;// 昨收
        @BindView(R.id.tev_open) TextView tev_open;// 开盘
        @BindView(R.id.tev_high) TextView tev_high;// 最高
        @BindView(R.id.tev_low) TextView tev_low;// 最低
        @BindView(R.id.tev_last) TextView tev_last;// 最新
        @BindView(R.id.tev_avg) TextView tev_avg;// 均价
        @BindView(R.id.tev_change) TextView tev_change;// 涨跌
        @BindView(R.id.tev_chg_rate) TextView tev_chg_rate;// 涨跌幅
        @BindView(R.id.tev_volume) TextView tev_volume;// 成交量
        @BindView(R.id.tev_amount) TextView tev_amount;// 成交金额
        @BindView(R.id.tev_now_vol) TextView tev_now_vol;// 当前成交量
        @BindView(R.id.tev_sell_vol) TextView tev_sell_vol;// 内盘
        @BindView(R.id.tev_buy_vol) TextView tev_buy_vol;// 外盘
        @BindView(R.id.tev_tradingday) TextView tev_tradingday;// 交易日
        @BindView(R.id.tev_settlementGroupID) TextView tev_settlementGroupID;// 结算组代码
        @BindView(R.id.tev_settlementID) TextView tev_settlementID;// 结算编号
        @BindView(R.id.tev_preSettlement) TextView tev_preSettlement;// 昨结算
        @BindView(R.id.tev_preOpenInterest) TextView tev_preOpenInterest;// 昨持仓量
        @BindView(R.id.tev_opt) TextView tev_opt;// 持仓量
        @BindView(R.id.tev_position_chg) TextView tev_position_chg;// 日增
        @BindView(R.id.tev_close) TextView tev_close;// 今收盘价
        @BindView(R.id.tev_settlement) TextView tev_settlement;// 今结算价
        @BindView(R.id.tev_upperLimit) TextView tev_upperLimit;// 涨停价
        @BindView(R.id.tev_downLimit) TextView tev_downLimit;// 跌停价
        @BindView(R.id.tev_preDelta) TextView tev_preDelta;// 昨虚实度
        @BindView(R.id.tev_currDelta) TextView tev_currDelta;// 今虚实度
        @BindView(R.id.tev_updateMillisec) TextView tev_updateMillisec;// 最后修改毫秒
        @BindView(R.id.tev_type) TextView tev_type;// 类型
        @BindView(R.id.tev_underlyingLastPx) TextView tev_underlyingLastPx;// 标的现价
        @BindView(R.id.tev_underlyingPreClose) TextView tev_underlyingPreClose;// 标的昨收
        @BindView(R.id.tev_underlyingchg) TextView tev_underlyingchg;// 标的涨跌
        @BindView(R.id.tev_tradeStatus) TextView tev_tradeStatus;// 交易状态
        @BindView(R.id.tev_change1) TextView tev_change1;// change1
        @BindView(R.id.tev_amp_rate) TextView tev_amp_rate;// 振幅
        @BindView(R.id.tev_posDiff) TextView tev_posDiff;// 仓差
        @BindView(R.id.tev_callOrPut) TextView tev_callOrPut;// 期权类型
        @BindView(R.id.tev_excercisePx) TextView tev_excercisePx;// 行权价
        @BindView(R.id.tev_premiumRate) TextView tev_premiumRate;// 溢价率
        @BindView(R.id.tev_remainingDays) TextView tev_remainingDays;// 剩余天数
        @BindView(R.id.tev_impliedVolatility) TextView tev_impliedVolatility;// 隐含波动率
        @BindView(R.id.tev_riskFreeInterestRate) TextView tev_riskFreeInRate;// 无风险利率
        @BindView(R.id.tev_riskIndicator) TextView tev_riskIndicator;// 风险指标
        @BindView(R.id.tev_leverageRatio) TextView tev_leverageRatio;// 杠杆比率
        @BindView(R.id.tev_intersectionNum) TextView tev_intersectionNum;// 交割点数
        @BindView(R.id.tev_entrustDiff) TextView tev_entrustDiff;// 委差
        @BindView(R.id.tev_entrustRatio) TextView tev_entrustRatio;// 委比
        @BindView(R.id.tev_currDiff) TextView tev_currDiff;// 期现差
        @BindView(R.id.tev_underlyingType) TextView tev_underlyingType;// 标的品种
        @BindView(R.id.tev_deliveryDay) TextView tev_deliveryDay;// 交割日期
        @BindView(R.id.tev_totalBid) TextView tev_totalBid;// 委买
        @BindView(R.id.tev_totalAsk) TextView tev_totalAsk;// 委卖
        @BindView(R.id.tev_bidpx1) TextView tev_bidpx1;// 买1价
        @BindView(R.id.tev_bidvol1) TextView tev_bidvol1;// 买1量
        @BindView(R.id.tev_askpx1) TextView tev_askpx1;// 卖1价
        @BindView(R.id.tev_askvol1) TextView tev_askvol1;// 卖1量


        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

}
