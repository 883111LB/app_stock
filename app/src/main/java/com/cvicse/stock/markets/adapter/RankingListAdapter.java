package com.cvicse.stock.markets.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cvicse.base.utils.StringUtils;
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

import static com.cvicse.stock.markets.data.ParamUtil.RANKING_TYPE_AMOUNT;
import static com.cvicse.stock.markets.data.ParamUtil.RANKING_TYPE_AMOUNT_HSSC;
import static com.cvicse.stock.markets.data.ParamUtil.RANKING_TYPE_DECLINE_HSSC;
import static com.cvicse.stock.markets.data.ParamUtil.RANKING_TYPE_RAISE_HSSC;
import static com.cvicse.stock.markets.data.ParamUtil.RANKING_TYPE_TURNOVERRATE;
import static com.cvicse.stock.markets.data.ParamUtil.RANKING_TYPE_TURNOVERRATE_HSSC;


/** 排序适配器（沪深、港股、新三板）
 * Created by ruan_ytai on 17-1-17.
 */

public class RankingListAdapter extends PBaseAdapter {
    private ArrayList<QuoteItem> rankingList;
    private String rankingType;

    public RankingListAdapter(Context context, String rankingType) {
        super(context);
        this.rankingType = rankingType;
    }

    public void setData(ArrayList<QuoteItem> rankingList) {
        this.rankingList = DataConvert.QuoteItemList(rankingList);

        int length = rankingList == null ? 0 : rankingList.size();
        for (int i = length - 1; i > -1; i--) {
            QuoteItem quoteItem = rankingList.get(i);
            if (quoteItem == null) {
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
        return rankingList == null ? 0 : rankingList.size();
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
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.ranking_list_item, parent, false);
            holder = new ViewHolder(convertView);

            convertView.setTag(holder);//绑定ViewHolder对象
        } else {
            holder = (ViewHolder) convertView.getTag();//取出ViewHolder对象
        }

        /**设置TextView显示的内容，即我们存放在动态数组中的数据*/
        QuoteItem quoteItem = rankingList.get(position);

        TextUtils.setText(holder.mTevName, quoteItem.name, FillConfig.DEFALUT);
        TextUtils.setText(holder.mTevId, quoteItem.id, FillConfig.DEFALUT);
        //最新价
        setTevLastPrice(holder, quoteItem);

        //换手率榜
        if (RANKING_TYPE_TURNOVERRATE.equals(rankingType) || RANKING_TYPE_TURNOVERRATE_HSSC.equals(rankingType)) {
            setTevChangeRate(holder, quoteItem); //涨幅
            setTevChange(holder, quoteItem); //涨跌
            setVolume(holder, quoteItem);  // 成交量
            setHighPrice(holder, quoteItem); // 最高价
            setLowPrice(holder, quoteItem); // 最低价

            //换手率
            TextUtils.setTextPercent(holder.mTevChangeRate, quoteItem.turnoverRate);
            TextUtils.setText(holder.mTevTurnoverRate, FormatUtils.format(quoteItem.amount), FillConfig.DEFALUT);
            if (RANKING_TYPE_TURNOVERRATE_HSSC.equals(rankingType)) {
                // 沪深市场多4个字段
                holder.tev_month_rate.setVisibility(View.VISIBLE);
                holder.tev_year_rate.setVisibility(View.VISIBLE);
                holder.tev_recent_month.setVisibility(View.VISIBLE);
                holder.tev_recent_year.setVisibility(View.VISIBLE);
                setChangeRate(holder.tev_month_rate, quoteItem.monthChangeRate);
                setChangeRate(holder.tev_year_rate, quoteItem.yearChangeRate);
                setChangeRate(holder.tev_recent_month, quoteItem.recentMonthChangeRate);
                setChangeRate(holder.tev_recent_year, quoteItem.recentYearChangeRate);
            }
        } else if (RANKING_TYPE_AMOUNT.equals(rankingType) || RANKING_TYPE_AMOUNT_HSSC.equals(rankingType)) {
            //成交额榜
            setTevChangeRate(holder, quoteItem);  //涨幅
            setTevChange(holder, quoteItem);  //涨跌
            setVolume(holder, quoteItem);  // 成交量
            setHighPrice(holder, quoteItem); // 最高价
            setLowPrice(holder, quoteItem); // 最低价
            //成交额
            TextUtils.setText(holder.mTevChangeRate, FormatUtils.format(quoteItem.amount), FillConfig.DEFALUT);
            TextUtils.setTextPercent(holder.mTevTurnoverRate, quoteItem.turnoverRate);
            if (RANKING_TYPE_AMOUNT_HSSC.equals(rankingType)) {
                // 沪深市场多4个字段
                holder.tev_month_rate.setVisibility(View.VISIBLE);
                holder.tev_year_rate.setVisibility(View.VISIBLE);
                holder.tev_recent_month.setVisibility(View.VISIBLE);
                holder.tev_recent_year.setVisibility(View.VISIBLE);
                setChangeRate(holder.tev_month_rate, quoteItem.monthChangeRate);
                setChangeRate(holder.tev_year_rate, quoteItem.yearChangeRate);
                setChangeRate(holder.tev_recent_month, quoteItem.recentMonthChangeRate);
                setChangeRate(holder.tev_recent_year, quoteItem.recentYearChangeRate);
            }
        } else {
            /**
             * 涨幅榜、跌幅榜
             后台返回的change(涨跌)字段带"+","-",changeRate（涨幅,即涨跌比率）字段处理后也带符号
             最新、涨幅、涨跌颜色一致
             最高、最低颜色根据昨收价判断
             */
            setPercent(holder, quoteItem);   // 涨幅
            setChange(holder, quoteItem);  // 涨跌
            setNowVolume(holder, quoteItem); //成交量
            setHighPrice(holder, quoteItem); // 最高价
            setLowPrice(holder, quoteItem); // 最低价

            TextUtils.setText(holder.mTevAmount, FormatUtils.format(quoteItem.amount), FillConfig.DEFALUT);
            TextUtils.setTextPercent(holder.mTevTurnoverRate, quoteItem.turnoverRate);

            // 沪深个股（新增4个字段）
            if (RANKING_TYPE_RAISE_HSSC.equals(rankingType) || RANKING_TYPE_DECLINE_HSSC.equals(rankingType)) {
                holder.tev_month_rate.setVisibility(View.VISIBLE);
                holder.tev_year_rate.setVisibility(View.VISIBLE);
                holder.tev_recent_month.setVisibility(View.VISIBLE);
                holder.tev_recent_year.setVisibility(View.VISIBLE);
                setChangeRate(holder.tev_month_rate, quoteItem.monthChangeRate);
                setChangeRate(holder.tev_year_rate, quoteItem.yearChangeRate);
                setChangeRate(holder.tev_recent_month, quoteItem.recentMonthChangeRate);
                setChangeRate(holder.tev_recent_year, quoteItem.recentYearChangeRate);
            }
        }

        TextUtils.setText(holder.mTevVolumeRatio, quoteItem.volumeRatio, FillConfig.DEFALUT);
        TextUtils.setTextPercent(holder.mTevAmplitudeRate, quoteItem.amplitudeRate);
        TextUtils.setText(holder.mTevKaipan, quoteItem.openPrice, FillConfig.DEFALUT);
        TextUtils.setText(holder.mTevPreClosePrice, quoteItem.preClosePrice, FillConfig.DEFALUT);
        TextUtils.setText(holder.mTevPe, quoteItem.pe, FillConfig.DEFALUT);
        TextUtils.setText(holder.tevPe2, quoteItem.pe2, FillConfig.DEFALUT);
        TextUtils.setText(holder.tevPb_Ip, quoteItem.pb, FillConfig.DEFALUT);
        TextUtils.setTextPercent(holder.mTevOrderRatio, quoteItem.orderRatio);

        TextUtils.setText(holder.tevFlowValue, FormatUtility.formatVolume(quoteItem.flowValue,
                quoteItem.market, quoteItem.subtype), FillConfig.DEFALUT);
        TextUtils.setText(holder.tevTotalValue, FormatUtility.formatVolume(quoteItem.totalValue,
                quoteItem.market, quoteItem.subtype), FillConfig.DEFALUT);

        return convertView;
    }

    /**
     * 设置当前成交量
     * @param holder
     * @param quoteItem
     */
    private void setNowVolume(ViewHolder holder, QuoteItem quoteItem) {
        if (!StringUtils.isEmpty(quoteItem.volume)) {
            TextUtils.setText(holder.mTevNowVolume, FormatUtility.formatVolume(quoteItem.volume,
                    quoteItem.market, quoteItem.subtype), FillConfig.DEFALUT);
        } else {
            holder.mTevNowVolume.setText(FillConfig.DEFALUT);
        }
    }

    /**
     * 设置涨跌
     * @param holder
     * @param quoteItem
     */
    private void setChange(ViewHolder holder, QuoteItem quoteItem) {
        if (TextUtils.setText(holder.mTevChange, quoteItem.change, FillConfig.DEFALUT) && quoteItem.change != null) {
            if ("-".equals(quoteItem.upDownFlag)) {
                holder.mTevChange.setTextColor(ColorUtils.DOWN);
            } else if ("+".equals(quoteItem.upDownFlag)) {
                holder.mTevChange.setTextColor(ColorUtils.UP);
            }
        } else {
            holder.mTevChange.setTextColor(ColorUtils.DEFALUT());
        }
    }

    /**
     * 设置涨幅
     * @param holder
     * @param quoteItem
     */
    private void setPercent(ViewHolder holder, QuoteItem quoteItem) {
        if (TextUtils.setTextPercent(holder.mTevChangeRate, quoteItem.changeRate)) {
            if ("-".equals(quoteItem.upDownFlag)) {
                holder.mTevChangeRate.setTextColor(ColorUtils.DOWN);
            } else if ("+".equals(quoteItem.upDownFlag)) {
                holder.mTevChangeRate.setTextColor(ColorUtils.UP);
            }
        } else {
            holder.mTevChangeRate.setTextColor(ColorUtils.DEFALUT());
        }
    }

    /**
     * 设置最低价
     */
    private void setLowPrice(ViewHolder holder, QuoteItem quoteItem) {
        if (TextUtils.setText(holder.mTevLowPrice, quoteItem.lowPrice, FillConfig.DEFALUT)
                && quoteItem.preClosePrice != null
                && FormatUtils.isStandard(quoteItem.lowPrice)
                && FormatUtils.isStandard(quoteItem.preClosePrice)
                ) {
            if (Double.parseDouble(quoteItem.lowPrice) > Double.parseDouble(quoteItem.preClosePrice)) {
                holder.mTevLowPrice.setTextColor(ColorUtils.UP);
            } else if (Double.parseDouble(quoteItem.lowPrice) < Double.parseDouble(quoteItem.preClosePrice)) {
                holder.mTevLowPrice.setTextColor(ColorUtils.DOWN);
            } else {
                holder.mTevLowPrice.setTextColor(ColorUtils.DEFALUT());
            }
        } else {
            holder.mTevLowPrice.setTextColor(ColorUtils.DEFALUT());
        }
    }

    /**
     * 设置最高价
     */
    private void setHighPrice(ViewHolder holder, QuoteItem quoteItem) {
        if (TextUtils.setText(holder.mTevHighPrice, quoteItem.highPrice, FillConfig.DEFALUT) &&
                quoteItem.preClosePrice != null &&
                FormatUtils.isStandard(quoteItem.highPrice)
                && FormatUtils.isStandard(quoteItem.preClosePrice)
                ) {
            if (Double.parseDouble(quoteItem.highPrice) > Double.parseDouble(quoteItem.preClosePrice)) {
                holder.mTevHighPrice.setTextColor(ColorUtils.UP);
            } else if (Double.parseDouble(quoteItem.highPrice) < Double.parseDouble(quoteItem.preClosePrice)) {
                holder.mTevHighPrice.setTextColor(ColorUtils.DOWN);
            } else {
                holder.mTevHighPrice.setTextColor(ColorUtils.DEFALUT());
            }
        } else {
            holder.mTevHighPrice.setTextColor(ColorUtils.DEFALUT());
        }
    }

    /**
     * 成交量
     */
    private void setVolume(ViewHolder holder, QuoteItem quoteItem) {
        if (!StringUtils.isEmpty(quoteItem.volume)) {
            TextUtils.setText(holder.mTevAmount, FormatUtility.formatVolume(quoteItem.volume,
                    quoteItem.market, quoteItem.subtype), FillConfig.DEFALUT);
        } else {
            holder.mTevAmount.setText(FillConfig.DEFALUT);
        }
    }

    /**
     * 涨跌
     */
    private void setTevChange(ViewHolder holder, QuoteItem quoteItem) {
        if (TextUtils.setText(holder.mTevNowVolume, quoteItem.change, FillConfig.DEFALUT) && quoteItem.change != null) {
            if ("-".equals(quoteItem.upDownFlag)) {
                holder.mTevNowVolume.setTextColor(ColorUtils.DOWN);
            } else if ("+".equals(quoteItem.upDownFlag)) {
                holder.mTevNowVolume.setTextColor(ColorUtils.UP);
            }
        } else {
            holder.mTevNowVolume.setTextColor(ColorUtils.DEFALUT());
        }
    }

    /**
     * 涨幅
     */
    private void setTevChangeRate(ViewHolder holder, QuoteItem quoteItem) {
        if (TextUtils.setTextPercent(holder.mTevChange, quoteItem.changeRate) && quoteItem.changeRate != null) {
            if ("-".equals(quoteItem.upDownFlag)) {
                holder.mTevChange.setTextColor(ColorUtils.DOWN);
            } else if ("+".equals(quoteItem.upDownFlag)) {
                holder.mTevChange.setTextColor(ColorUtils.UP);
            }
        } else {
            holder.mTevChange.setTextColor(ColorUtils.DEFALUT());
        }
    }
    /**
     * 设置涨幅(本月、本年、近一月、近一年）
     */
    @SuppressLint("SetTextI18n")
    private void setChangeRate(TextView view, String changeRate) {
        if (TextUtils.setTextPercent(view, changeRate) && changeRate != null) {
            if (changeRate.contains("-")) {
                view.setTextColor(ColorUtils.DOWN);
            } else if (changeRate.contains("+") || !"0.00".equals(changeRate)) {
                view.setTextColor(ColorUtils.UP);
            }
        } else {
            view.setTextColor(ColorUtils.DEFALUT());
        }
    }

    /**
     * 最新价
     */
    private void setTevLastPrice(ViewHolder holder, QuoteItem quoteItem) {

        if (TextUtils.setText(holder.mTevLastPrice, quoteItem.lastPrice, FillConfig.DEFALUT) &&
                null != quoteItem.change && null != quoteItem.upDownFlag
                && TextUtils.setText(holder.mTevChange, quoteItem.change, FillConfig.DEFALUT)) {
            if ("-".equals(quoteItem.upDownFlag)) {
                holder.mTevLastPrice.setTextColor(ColorUtils.DOWN);
            } else if ("+".equals(quoteItem.upDownFlag)) {
                holder.mTevLastPrice.setTextColor(ColorUtils.UP);
            }
        } else {
            holder.mTevLastPrice.setTextColor(ColorUtils.DEFALUT());
        }
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
        @BindView(R.id.tev_volume) TextView mTevNowVolume;
        //成交额
        @BindView(R.id.tev_amount) TextView mTevAmount;
        //换手率
        @BindView(R.id.tev_turnoverRate) TextView mTevTurnoverRate;
        //量比
        @BindView(R.id.tev_volumeRatio) TextView mTevVolumeRatio;
        //振幅
        @BindView(R.id.tev_amplitudeRate) TextView mTevAmplitudeRate;
        //最高
        @BindView(R.id.tev_highPrice) TextView mTevHighPrice;
        //最低
        @BindView(R.id.tev_lowPrice) TextView mTevLowPrice;
        //开盘
        @BindView(R.id.tev_kaipan) TextView mTevKaipan;
        //昨收
        @BindView(R.id.tev_preClosePrice) TextView mTevPreClosePrice;
        //市盈率
        @BindView(R.id.tev_pe) TextView mTevPe;
        //委比
        @BindView(R.id.tev_orderRatio) TextView mTevOrderRatio;
        @BindView(R.id.tev_pe2) TextView tevPe2;  // 市盈静
        @BindView(R.id.tev_totalValue) TextView tevTotalValue; // 总市值
        @BindView(R.id.tev_flowValue) TextView tevFlowValue; // 流通值
        @BindView(R.id.tev_pb_ip) TextView tevPb_Ip; // 市净率
        @BindView(R.id.tev_month_rate) TextView tev_month_rate; // 本月涨幅
        @BindView(R.id.tev_year_rate) TextView tev_year_rate; // 本年涨幅
        @BindView(R.id.tev_recent_month) TextView tev_recent_month; // 近一月涨幅
        @BindView(R.id.tev_recent_year) TextView tev_recent_year; // 近一年涨幅

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
