package com.cvicse.stock.markets.adapter.new_adapter;

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
import com.mitake.core.AddValueModel;
import com.mitake.core.QuoteItem;
import com.mitake.core.util.FormatUtility;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.cvicse.stock.markets.data.ParamUtil.RANKING_TYPE_All_HLT;
import static com.cvicse.stock.markets.data.ParamUtil.RANKING_TYPE_All_HS_KCB;


/** 排序适配器（沪深、港股、新三板）
 * Created by ruan_ytai on 17-1-17.
 */

public class RankingListAllAdapter extends PBaseAdapter {
    private ArrayList<QuoteItem> rankingList;
    private ArrayList<AddValueModel> addValueModels;
    private String rankingType;

    public RankingListAllAdapter(Context context, String rankingType) {
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
    public void setData(ArrayList<QuoteItem> rankingList, ArrayList<AddValueModel> addValueModels) {
        this.rankingList = DataConvert.QuoteItemList(rankingList);
        this.addValueModels = addValueModels;
        int length = addValueModels == null ? 0 : addValueModels.size();
        for (int i = length - 1; i > -1; i--) {
            assert addValueModels != null;
            AddValueModel valueModel = addValueModels.get(i);
            if (null == valueModel) {
                addValueModels.remove(i);
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
            convertView = mLayoutInflater.inflate(R.layout.ranking_list_item_all, parent, false);
            holder = new ViewHolder(convertView);

            convertView.setTag(holder);//绑定ViewHolder对象
        } else {
            holder = (ViewHolder) convertView.getTag();//取出ViewHolder对象
        }

        /**设置TextView显示的内容，即我们存放在动态数组中的数据*/
        QuoteItem quoteItem = rankingList.get(position);
        // 通用字段
        TextUtils.setText(holder.tev_name, quoteItem.name, FillConfig.DEFALUT);// 名称
        TextUtils.setText(holder.tev_id, quoteItem.id, FillConfig.DEFALUT);// 代码
        TextUtils.setText(holder.tev_state, quoteItem.status, FillConfig.DEFALUT);// 交易状态
        TextUtils.setText(holder.tev_subtype, quoteItem.subtype, FillConfig.DEFALUT);// 次类别
        TextUtils.setText(holder.tev_subtype, quoteItem.subtype, FillConfig.DEFALUT);// 最新价
        setTevLastPrice(holder, quoteItem);//最新价
        setHighPrice(holder, quoteItem); // 最高价
        setLowPrice(holder, quoteItem); // 最低价
        TextUtils.setText(holder.tev_openPrice, quoteItem.openPrice, FillConfig.DEFALUT);//今开价
        TextUtils.setText(holder.tev_preClosePrice, quoteItem.preClosePrice, FillConfig.DEFALUT);//昨收
        setPercent(holder, quoteItem);   // 涨跌比率
        setVolume(holder, quoteItem);  // 总手
        setNowVolume(holder, quoteItem); // 当前成交量
        TextUtils.setTextPercent(holder.tev_turnoverRate, quoteItem.turnoverRate);//换手率
        TextUtils.setText(holder.tev_limitUp, quoteItem.limitUP, FillConfig.DEFALUT);//涨停价
        TextUtils.setText(holder.tev_limitDown, quoteItem.limitDown, FillConfig.DEFALUT);//跌停价
        TextUtils.setText(holder.tev_averageValue, quoteItem.averageValue, FillConfig.DEFALUT);//均价
        setChange(holder, quoteItem);  // 涨跌
        TextUtils.setText(holder.tev_amount, FormatUtils.format(quoteItem.amount), FillConfig.DEFALUT);// 成交金额
        TextUtils.setText(holder.tev_volumeRatio, quoteItem.volumeRatio, FillConfig.DEFALUT);// 量比
        TextUtils.setText(holder.tev_buyVolume, quoteItem.buyVolume, FillConfig.DEFALUT);// 外盘量
        TextUtils.setText(holder.tev_sellVolume, quoteItem.sellVolume, FillConfig.DEFALUT);// 内盘量
        TextUtils.setText(holder.tev_totalValue, FormatUtility.formatVolume(quoteItem.totalValue,
                quoteItem.market, quoteItem.subtype), FillConfig.DEFALUT);// 总市值
        TextUtils.setText(holder.tev_flowValue, FormatUtility.formatVolume(quoteItem.flowValue,
                quoteItem.market, quoteItem.subtype), FillConfig.DEFALUT);// 流通市值
        TextUtils.setText(holder.tev_netAsset, quoteItem.netAsset, FillConfig.DEFALUT);// 净资产
        TextUtils.setText(holder.tev_pe, quoteItem.pe, FillConfig.DEFALUT);// pe(动态市盈率)
        TextUtils.setText(holder.tev_pb_ip, quoteItem.pb, FillConfig.DEFALUT);// 市净率
        TextUtils.setText(holder.tev_capitalization, quoteItem.capitalization, FillConfig.DEFALUT);// 总股本
        TextUtils.setText(holder.tev_ciShares, quoteItem.circulatingShares, FillConfig.DEFALUT);// 流通股
        TextUtils.setTextPercent(holder.tev_amplitudeRate, quoteItem.amplitudeRate);// 振幅比率
        TextUtils.setTextPercent(holder.tev_receipts, quoteItem.receipts);// 动态每股收益
        TextUtils.setTextPercent(holder.tev_receipts, quoteItem.receipts);// 动态每股收益
//        holder.tev_bj_orderRatio.setText(FillConfig.DEFALUT);// 新三板委比（默认-）
        TextUtils.setTextPercent(holder.tev_orderRatio, quoteItem.orderRatio);// 沪深委比
        TextUtils.setTextPercent(holder.tev_etrustDiff, quoteItem.entrustDiff);// 委差
        TextUtils.setTextPercent(holder.tev_etrustDiff, quoteItem.entrustDiff);// 委差
        TextUtils.setText(holder.tev_pe2, quoteItem.pe2, FillConfig.DEFALUT);// 静态市盈率
        setChangeRate(holder.tev_month_rate, quoteItem.monthChangeRate);//本月涨幅
        setChangeRate(holder.tev_year_rate, quoteItem.yearChangeRate);// 本年涨幅
        setChangeRate(holder.tev_recent_month, quoteItem.recentMonthChangeRate);// 近一月涨幅
        setChangeRate(holder.tev_recent_year, quoteItem.recentYearChangeRate);// 近一年涨幅
        // 增值指标字段
        if (null != addValueModels && position < addValueModels.size()) {
            setZZZBVisibility(holder);// 设置增值指标字段显示
            AddValueModel model = addValueModels.get(position);
            TextUtils.setText(holder.tev_ultralargeflow, model.ultraLargeNetInflow, FillConfig.DEFALUT);//超大单净流入
            TextUtils.setText(holder.tev_largeflow, model.largeNetInflow, FillConfig.DEFALUT);// 大单净流入
            TextUtils.setText(holder.tev_medflow, model.mediumNetInflow, FillConfig.DEFALUT);// 中单净流入
            TextUtils.setText(holder.tev_smallflow, model.smallNetInflow, FillConfig.DEFALUT);// 小单净流入
            TextUtils.setText(holder.tev_bbd, model.BBD, FillConfig.DEFALUT);// 大单净差
            TextUtils.setText(holder.tev_bbd5, model.BBD5, FillConfig.DEFALUT);// 五日大单净差
            TextUtils.setText(holder.tev_bbd10, model.BBD10, FillConfig.DEFALUT);// 十日大单净差
            TextUtils.setText(holder.tev_ddx, model.DDX, FillConfig.DEFALUT);// 主力动向
            TextUtils.setText(holder.tev_ddx5, model.DDX5, FillConfig.DEFALUT);// 五日主力动向
            TextUtils.setText(holder.tev_ddx10, model.DDX10, FillConfig.DEFALUT);// 十日主力动向
            TextUtils.setText(holder.tev_ddy, model.DDY, FillConfig.DEFALUT);// 涨跌动因
            TextUtils.setText(holder.tev_ddy5, model.DDY5, FillConfig.DEFALUT);// 五日涨跌动因
            TextUtils.setText(holder.tev_ddy10, model.DDY10, FillConfig.DEFALUT);// 十日涨跌动因
            TextUtils.setText(holder.tev_netCapInflow, model.netCapitalInflow, FillConfig.DEFALUT);// 主力净流入
            TextUtils.setText(holder.tev_chg5minutes, model.fiveMinutesChangeRate, FillConfig.DEFALUT);// 五分钟涨跌幅
            TextUtils.setText(holder.tev_mainInflow5, model.mainforceMoneyNetInflow5, FillConfig.DEFALUT);// 5日主力资金净流入
            TextUtils.setText(holder.tev_mainInflow10, model.mainforceMoneyNetInflow10, FillConfig.DEFALUT);// 10日主力资金净流入
            TextUtils.setText(holder.tev_mainInflow20, model.mainforceMoneyNetInflow20, FillConfig.DEFALUT);// 20日主力资金净流入
            TextUtils.setText(holder.tev_rMainInflow5, model.ratioMainforceMoneyNetInflow5, FillConfig.DEFALUT);// 5日主力资金净流入占比
            TextUtils.setText(holder.tev_rMainInflow10, model.ratioMainforceMoneyNetInflow10, FillConfig.DEFALUT);// 10日主力资金净流入占比
            TextUtils.setText(holder.tev_rMainInflow20, model.ratioMainforceMoneyNetInflow20, FillConfig.DEFALUT);// 20日主力资金净流入占比主力动向
        }

        // 科创版
        if (RANKING_TYPE_All_HS_KCB.equals(rankingType)) {
            holder.tev_afterVolume.setVisibility(View.VISIBLE);
            holder.tev_afterAmount.setVisibility(View.VISIBLE);
            TextUtils.setText(holder.tev_afterVolume, quoteItem.afterHoursVolume, FillConfig.DEFALUT);// 盘后成交量
            TextUtils.setText(holder.tev_afterAmount, quoteItem.afterHoursAmount, FillConfig.DEFALUT);// 盘后成交额
        }
        // 沪伦通
        if (RANKING_TYPE_All_HLT.equals(rankingType)) {
            holder.tev_drCurrent.setVisibility(View.VISIBLE);
            holder.tev_drPreClosing.setVisibility(View.VISIBLE);
            TextUtils.setText(holder.tev_drCurrent, quoteItem.DRCurrentShare, FillConfig.DEFALUT);// 当前份额
            TextUtils.setText(holder.tev_drPreClosing, quoteItem.DRPreviousClosingShare, FillConfig.DEFALUT);// 前收盘份额
        }
//        //换手率榜
//        if (RANKING_TYPE_TURNOVERRATE.equals(rankingType) || RANKING_TYPE_TURNOVERRATE_HSSC.equals(rankingType)) {
//            setTevChangeRate(holder, quoteItem); //涨幅
//            setTevChange(holder, quoteItem); //涨跌
//            setVolume(holder, quoteItem);  // 成交量
//            setHighPrice(holder, quoteItem); // 最高价
//            setLowPrice(holder, quoteItem); // 最低价
//
//            //换手率
//            TextUtils.setTextPercent(holder.tev_changeRate, quoteItem.turnoverRate);
//            TextUtils.setText(holder.tev_turnoverRate, FormatUtils.format(quoteItem.amount), FillConfig.DEFALUT);
//            if (RANKING_TYPE_TURNOVERRATE_HSSC.equals(rankingType)) {
//                // 沪深市场多4个字段
//                holder.tev_month_rate.setVisibility(View.VISIBLE);
//                holder.tev_year_rate.setVisibility(View.VISIBLE);
//                holder.tev_recent_month.setVisibility(View.VISIBLE);
//                holder.tev_recent_year.setVisibility(View.VISIBLE);
//                setChangeRate(holder.tev_month_rate, quoteItem.monthChangeRate);
//                setChangeRate(holder.tev_year_rate, quoteItem.yearChangeRate);
//                setChangeRate(holder.tev_recent_month, quoteItem.recentMonthChangeRate);
//                setChangeRate(holder.tev_recent_year, quoteItem.recentYearChangeRate);
//            }
//        } else if (RANKING_TYPE_AMOUNT.equals(rankingType) || RANKING_TYPE_AMOUNT_HSSC.equals(rankingType)) {
//            //成交额榜
//            setTevChangeRate(holder, quoteItem);  //涨幅
//            setTevChange(holder, quoteItem);  //涨跌
//            setVolume(holder, quoteItem);  // 成交量
//            setHighPrice(holder, quoteItem); // 最高价
//            setLowPrice(holder, quoteItem); // 最低价
//            //成交额
//            TextUtils.setText(holder.tev_changeRate, FormatUtils.format(quoteItem.amount), FillConfig.DEFALUT);
//            TextUtils.setTextPercent(holder.tev_turnoverRate, quoteItem.turnoverRate);
//            if (RANKING_TYPE_AMOUNT_HSSC.equals(rankingType)) {
//                // 沪深市场多4个字段
//                holder.tev_month_rate.setVisibility(View.VISIBLE);
//                holder.tev_year_rate.setVisibility(View.VISIBLE);
//                holder.tev_recent_month.setVisibility(View.VISIBLE);
//                holder.tev_recent_year.setVisibility(View.VISIBLE);
//                setChangeRate(holder.tev_month_rate, quoteItem.monthChangeRate);
//                setChangeRate(holder.tev_year_rate, quoteItem.yearChangeRate);
//                setChangeRate(holder.tev_recent_month, quoteItem.recentMonthChangeRate);
//                setChangeRate(holder.tev_recent_year, quoteItem.recentYearChangeRate);
//            }
//        } else {
//            /**
//             * 涨幅榜、跌幅榜
//             后台返回的change(涨跌)字段带"+","-",changeRate（涨幅,即涨跌比率）字段处理后也带符号
//             最新、涨幅、涨跌颜色一致
//             最高、最低颜色根据昨收价判断
//             */
//            setPercent(holder, quoteItem);   // 涨幅
//            setChange(holder, quoteItem);  // 涨跌
//            setNowVolume(holder, quoteItem); //成交量
//            setHighPrice(holder, quoteItem); // 最高价
//            setLowPrice(holder, quoteItem); // 最低价
//
//            TextUtils.setText(holder.tev_amount, FormatUtils.format(quoteItem.amount), FillConfig.DEFALUT);
//            TextUtils.setTextPercent(holder.tev_turnoverRate, quoteItem.turnoverRate);
//            setChangeRate(holder.tev_month_rate, quoteItem.monthChangeRate);
//            setChangeRate(holder.tev_year_rate, quoteItem.yearChangeRate);
//            setChangeRate(holder.tev_recent_month, quoteItem.recentMonthChangeRate);
//            setChangeRate(holder.tev_recent_year, quoteItem.recentYearChangeRate);
//        }
        return convertView;
    }
    /**
     * 设置当前成交量
     */
    private void setNowVolume(ViewHolder holder, QuoteItem quoteItem) {
        if (!StringUtils.isEmpty(quoteItem.volume)) {
            TextUtils.setText(holder.tev_nowVolume, FormatUtility.formatVolume(quoteItem.nowVolume,
                    quoteItem.market, quoteItem.subtype), FillConfig.DEFALUT);
        } else {
            holder.tev_nowVolume.setText(FillConfig.DEFALUT);
        }
    }

    /**
     * 设置涨跌
     */
    private void setChange(ViewHolder holder, QuoteItem quoteItem) {
        if (TextUtils.setText(holder.tev_change, quoteItem.change, FillConfig.DEFALUT) && quoteItem.change != null) {
            if ("-".equals(quoteItem.upDownFlag)) {
                holder.tev_change.setTextColor(ColorUtils.DOWN);
            } else if ("+".equals(quoteItem.upDownFlag)) {
                holder.tev_change.setTextColor(ColorUtils.UP);
            }
        } else {
            holder.tev_change.setTextColor(ColorUtils.DEFALUT());
        }
    }

    /**
     * 设置涨幅 （涨跌比率）
     */
    private void setPercent(ViewHolder holder, QuoteItem quoteItem) {
        if (TextUtils.setTextPercent(holder.tev_changeRate, quoteItem.changeRate)) {
            if ("-".equals(quoteItem.upDownFlag)) {
                holder.tev_changeRate.setTextColor(ColorUtils.DOWN);
            } else if ("+".equals(quoteItem.upDownFlag)) {
                holder.tev_changeRate.setTextColor(ColorUtils.UP);
            }
        } else {
            holder.tev_changeRate.setTextColor(ColorUtils.DEFALUT());
        }
    }

    /**
     * 设置最低价
     */
    private void setLowPrice(ViewHolder holder, QuoteItem quoteItem) {
        if (TextUtils.setText(holder.tev_lowPrice, quoteItem.lowPrice, FillConfig.DEFALUT)
                && quoteItem.preClosePrice != null
                && FormatUtils.isStandard(quoteItem.lowPrice)
                && FormatUtils.isStandard(quoteItem.preClosePrice)
                ) {
            if (Double.parseDouble(quoteItem.lowPrice) > Double.parseDouble(quoteItem.preClosePrice)) {
                holder.tev_lowPrice.setTextColor(ColorUtils.UP);
            } else if (Double.parseDouble(quoteItem.lowPrice) < Double.parseDouble(quoteItem.preClosePrice)) {
                holder.tev_lowPrice.setTextColor(ColorUtils.DOWN);
            } else {
                holder.tev_lowPrice.setTextColor(ColorUtils.DEFALUT());
            }
        } else {
            holder.tev_lowPrice.setTextColor(ColorUtils.DEFALUT());
        }
    }

    /**
     * 设置最高价
     */
    private void setHighPrice(ViewHolder holder, QuoteItem quoteItem) {
        if (TextUtils.setText(holder.tev_highPrice, quoteItem.highPrice, FillConfig.DEFALUT) &&
                quoteItem.preClosePrice != null &&
                FormatUtils.isStandard(quoteItem.highPrice)
                && FormatUtils.isStandard(quoteItem.preClosePrice)
                ) {
            if (Double.parseDouble(quoteItem.highPrice) > Double.parseDouble(quoteItem.preClosePrice)) {
                holder.tev_highPrice.setTextColor(ColorUtils.UP);
            } else if (Double.parseDouble(quoteItem.highPrice) < Double.parseDouble(quoteItem.preClosePrice)) {
                holder.tev_highPrice.setTextColor(ColorUtils.DOWN);
            } else {
                holder.tev_highPrice.setTextColor(ColorUtils.DEFALUT());
            }
        } else {
            holder.tev_highPrice.setTextColor(ColorUtils.DEFALUT());
        }
    }

    /**
     * 成交量 （总手）
     */
    private void setVolume(ViewHolder holder, QuoteItem quoteItem) {
        if (!StringUtils.isEmpty(quoteItem.volume)) {
            TextUtils.setText(holder.tev_volume, FormatUtility.formatVolume(quoteItem.volume,
                    quoteItem.market, quoteItem.subtype), FillConfig.DEFALUT);
        } else {
            holder.tev_volume.setText(FillConfig.DEFALUT);
        }
    }

//    /**
//     * 涨跌
//     */
//    private void setTevChange(ViewHolder holder, QuoteItem quoteItem) {
//        if (TextUtils.setText(holder.tev_change, quoteItem.change, FillConfig.DEFALUT) && quoteItem.change != null) {
//            if ("-".equals(quoteItem.upDownFlag)) {
//                holder.tev_change.setTextColor(ColorUtils.DOWN);
//            } else if ("+".equals(quoteItem.upDownFlag)) {
//                holder.tev_change.setTextColor(ColorUtils.UP);
//            }
//        } else {
//            holder.tev_change.setTextColor(ColorUtils.DEFALUT());
//        }
//    }

//    /**
//     * 涨幅
//     */
//    private void setTevChangeRate(ViewHolder holder, QuoteItem quoteItem) {
//        if (TextUtils.setTextPercent(holder.tev_changeRate, quoteItem.changeRate) && quoteItem.changeRate != null) {
//            if ("-".equals(quoteItem.upDownFlag)) {
//                holder.tev_changeRate.setTextColor(ColorUtils.DOWN);
//            } else if ("+".equals(quoteItem.upDownFlag)) {
//                holder.tev_changeRate.setTextColor(ColorUtils.UP);
//            }
//        } else {
//            holder.tev_changeRate.setTextColor(ColorUtils.DEFALUT());
//        }
//    }
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

        if (TextUtils.setText(holder.tev_lastPrice, quoteItem.lastPrice, FillConfig.DEFALUT) &&
                null != quoteItem.change && null != quoteItem.upDownFlag
                && TextUtils.setText(holder.tev_change, quoteItem.change, FillConfig.DEFALUT)) {
            if ("-".equals(quoteItem.upDownFlag)) {
                holder.tev_lastPrice.setTextColor(ColorUtils.DOWN);
            } else if ("+".equals(quoteItem.upDownFlag)) {
                holder.tev_lastPrice.setTextColor(ColorUtils.UP);
            }
        } else {
            holder.tev_lastPrice.setTextColor(ColorUtils.DEFALUT());
        }
    }
    /**
     * 设置增值指标字段显示
     */
    private void setZZZBVisibility(ViewHolder holder) {
        holder.tev_ultralargeflow.setVisibility(View.VISIBLE);
        holder.tev_largeflow.setVisibility(View.VISIBLE);
        holder.tev_medflow.setVisibility(View.VISIBLE);
        holder.tev_smallflow.setVisibility(View.VISIBLE);
        holder.tev_bbd.setVisibility(View.VISIBLE);
        holder.tev_bbd5.setVisibility(View.VISIBLE);
        holder.tev_bbd10.setVisibility(View.VISIBLE);
        holder.tev_ddx.setVisibility(View.VISIBLE);
        holder.tev_ddx5.setVisibility(View.VISIBLE);
        holder.tev_ddx10.setVisibility(View.VISIBLE);
        holder.tev_ddy.setVisibility(View.VISIBLE);
        holder.tev_ddy5.setVisibility(View.VISIBLE);
        holder.tev_ddy10.setVisibility(View.VISIBLE);
        holder.tev_netCapInflow.setVisibility(View.VISIBLE);
        holder.tev_chg5minutes.setVisibility(View.VISIBLE);
        holder.tev_mainInflow5.setVisibility(View.VISIBLE);
        holder.tev_mainInflow10.setVisibility(View.VISIBLE);
        holder.tev_mainInflow20.setVisibility(View.VISIBLE);
        holder.tev_rMainInflow5.setVisibility(View.VISIBLE);
        holder.tev_rMainInflow10.setVisibility(View.VISIBLE);
        holder.tev_rMainInflow20.setVisibility(View.VISIBLE);
    }
    class ViewHolder {
        @BindView(R.id.tev_state) TextView tev_state; // 交易状态
        @BindView(R.id.tev_id) TextView tev_id;//代码
        @BindView(R.id.tev_name) TextView tev_name;//名称
        @BindView(R.id.tev_subtype) TextView tev_subtype; // 次类别
        @BindView(R.id.tev_lastPrice) TextView tev_lastPrice;//最新价
        @BindView(R.id.tev_highPrice) TextView tev_highPrice;//最高价
        @BindView(R.id.tev_lowPrice) TextView tev_lowPrice;//最低价
        @BindView(R.id.tev_openPrice) TextView tev_openPrice;//今开价
        @BindView(R.id.tev_preClosePrice) TextView tev_preClosePrice;//昨收
        @BindView(R.id.tev_changeRate) TextView tev_changeRate;//涨跌比率
        @BindView(R.id.tev_volume) TextView tev_volume;//总手
        @BindView(R.id.tev_nowVolume) TextView tev_nowVolume;//当前成交量
        @BindView(R.id.tev_turnoverRate) TextView tev_turnoverRate;//换手率
        @BindView(R.id.tev_limitUp) TextView tev_limitUp;//涨停价
        @BindView(R.id.tev_limitDown) TextView tev_limitDown;//跌停价
        @BindView(R.id.tev_averageValue) TextView tev_averageValue;//均价
        @BindView(R.id.tev_change) TextView tev_change;//涨跌
        @BindView(R.id.tev_amount) TextView tev_amount;//成交金额
        @BindView(R.id.tev_volumeRatio) TextView tev_volumeRatio;//量比
        @BindView(R.id.tev_buyVolume) TextView tev_buyVolume;//外盘量
        @BindView(R.id.tev_sellVolume) TextView tev_sellVolume;//内盘量
        @BindView(R.id.tev_totalValue) TextView tev_totalValue;//总市值
        @BindView(R.id.tev_flowValue) TextView tev_flowValue;//流通市值
        @BindView(R.id.tev_netAsset) TextView tev_netAsset;//净资产
        @BindView(R.id.tev_pe) TextView tev_pe;//pe(动态市盈率)
        @BindView(R.id.tev_pb_ip) TextView tev_pb_ip;//市净率
        @BindView(R.id.tev_capitalization) TextView tev_capitalization;//总股本
        @BindView(R.id.tev_ciShares) TextView tev_ciShares;//流通股
        @BindView(R.id.tev_amplitudeRate) TextView tev_amplitudeRate;//振幅比率
        @BindView(R.id.tev_receipts) TextView tev_receipts;//动态每股收益
//        @BindView(R.id.tev_bj_orderRatio) TextView tev_bj_orderRatio;//新三板委比
        @BindView(R.id.tev_orderRatio) TextView tev_orderRatio;//沪深委比
        @BindView(R.id.tev_etrustDiff) TextView tev_etrustDiff;//委差
        @BindView(R.id.tev_pe2) TextView tev_pe2;//静态市盈率
        @BindView(R.id.tev_month_rate) TextView tev_month_rate; // 本月涨幅
        @BindView(R.id.tev_year_rate) TextView tev_year_rate; // 本年涨幅
        @BindView(R.id.tev_recent_month) TextView tev_recent_month; // 近一月涨幅
        @BindView(R.id.tev_recent_year) TextView tev_recent_year; // 近一年涨幅
        @BindView(R.id.tev_ultralargeflow) TextView tev_ultralargeflow; // 超大单净流入
        @BindView(R.id.tev_largeflow) TextView tev_largeflow; // 大单净流入
        @BindView(R.id.tev_medflow) TextView tev_medflow; // 中单净流入
        @BindView(R.id.tev_smallflow) TextView tev_smallflow; // 小单净流入
        @BindView(R.id.tev_bbd) TextView tev_bbd; // 大单净差
        @BindView(R.id.tev_bbd5) TextView tev_bbd5; // 五日大单净差
        @BindView(R.id.tev_bbd10) TextView tev_bbd10; // 十日大单净差
        @BindView(R.id.tev_ddx) TextView tev_ddx; // 主力动向
        @BindView(R.id.tev_ddx5) TextView tev_ddx5; // 五日主力动向
        @BindView(R.id.tev_ddx10) TextView tev_ddx10; // 十日主力动向
        @BindView(R.id.tev_ddy) TextView tev_ddy; // 涨跌动因
        @BindView(R.id.tev_ddy5) TextView tev_ddy5; // 五日涨跌动因
        @BindView(R.id.tev_ddy10) TextView tev_ddy10; // 十日涨跌动因
        @BindView(R.id.tev_netCapInflow) TextView tev_netCapInflow; // 主力净流入
        @BindView(R.id.tev_chg5minutes) TextView tev_chg5minutes; // 五分钟涨跌幅
        @BindView(R.id.tev_mainInflow5) TextView tev_mainInflow5; // 5日主力资金净流入
        @BindView(R.id.tev_mainInflow10) TextView tev_mainInflow10; // 10日主力资金净流入
        @BindView(R.id.tev_mainInflow20) TextView tev_mainInflow20; // 20日主力资金净流入
        @BindView(R.id.tev_rMainInflow5) TextView tev_rMainInflow5; // 5日主力资金净流入占比
        @BindView(R.id.tev_rMainInflow10) TextView tev_rMainInflow10; // 10日主力资金净流入占比
        @BindView(R.id.tev_rMainInflow20) TextView tev_rMainInflow20; // 20日主力资金净流入占比主力动向
        @BindView(R.id.tev_afterVolume) TextView tev_afterVolume; // 盘后成交量
        @BindView(R.id.tev_afterAmount) TextView tev_afterAmount; // 盘后成交额
        @BindView(R.id.tev_drCurrent) TextView tev_drCurrent; // 当前份额
        @BindView(R.id.tev_drPreClosing) TextView tev_drPreClosing; // 前收盘份额

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
