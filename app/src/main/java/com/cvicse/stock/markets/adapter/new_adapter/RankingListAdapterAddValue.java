package com.cvicse.stock.markets.adapter.new_adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cvicse.stock.R;
import com.cvicse.stock.base.PBaseAdapter;
import com.cvicse.stock.common.ColorUtils;
import com.cvicse.stock.util.FormatUtils;
import com.cvicse.stock.utils.DataConvert;
import com.cvicse.stock.utils.TextUtils;
import com.mitake.core.AddValueModel;
import com.mitake.core.QuoteItem;
import com.mitake.core.response.CateSortingResponse;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * 排序适配器（五分钟涨幅榜）
 * Created by tang_xqing on 18-9-18.
 */
public class RankingListAdapterAddValue extends PBaseAdapter {
    private ArrayList<QuoteItem> mQuoteItems;
    private ArrayList<AddValueModel> mAddValueModels;
    private String rankingType;

    public RankingListAdapterAddValue(Context context, String rankingType) {
        super(context);
        this.rankingType = rankingType;
    }

    public void setData(CateSortingResponse cateSortingResponse) {
        this.mQuoteItems = DataConvert.QuoteItemList(cateSortingResponse.list);
        this.mAddValueModels = cateSortingResponse.addValueModel;

        int length = null == mQuoteItems ? 0 : mQuoteItems.size();
        for (int i = length - 1; i > -1; i--) {
            QuoteItem quoteItem = mQuoteItems.get(i);
            if (null == quoteItem) {
                mQuoteItems.remove(i);
            }
        }
        notifyDataSetChanged();
    }

    public ArrayList<QuoteItem> getData() {
        return mQuoteItems;
    }

    @Override
    public int getCount() {
        return null == mQuoteItems ? 0 : mQuoteItems.size();
    }

    @Override
    public QuoteItem getItem(int position) {
        return null == mQuoteItems ? null : mQuoteItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return null == mQuoteItems ? 0 : position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        //观察convertView随ListView滚动情况
        if (null == convertView) {
            convertView = mLayoutInflater.inflate(R.layout.ranking_list_item_addvalue, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);//绑定ViewHolder对象
        } else {
            holder = (ViewHolder) convertView.getTag();//取出ViewHolder对象
        }

        /**设置TextView显示的内容，即我们存放在动态数组中的数据*/
        QuoteItem quoteItem = mQuoteItems.get(position);
        AddValueModel addValueModel = mAddValueModels.get(position);

        TextUtils.setText(holder.tevName, quoteItem.name);
        TextUtils.setText(holder.tevId, quoteItem.id);
        TextUtils.setText(holder.tevLastPrice, quoteItem.lastPrice);//最新价

        String symbol = FormatUtils.getSymbol(quoteItem.upDownFlag);   // 涨跌符号
        setChangeRate(holder, symbol, quoteItem.changeRate);   // 涨幅
        setChangeRate(holder.tev_month_rate, quoteItem.monthChangeRate);   // 本月涨幅
        setChangeRate(holder.tev_year_rate, quoteItem.yearChangeRate);   // 本年涨幅
        setChangeRate(holder.tev_recent_month, quoteItem.recentMonthChangeRate);   // 近一月涨幅
        setChangeRate(holder.tev_recent_year, quoteItem.recentYearChangeRate);   // 近一年涨幅

        TextUtils.setText(holder.tevChangeRateFive, addValueModel.getFiveMinutesChangeRate()+"%");
        TextUtils.setText(holder.tevMainNetinflow5, addValueModel.getMainforceMoneyNetInflow5());
        TextUtils.setText(holder.tevMainNetinflow10, addValueModel.getMainforceMoneyNetInflow10());
        TextUtils.setText(holder.tevMainNetinflow20, addValueModel.getMainforceMoneyNetInflow20());
        TextUtils.setText(holder.tevRatioNetinflow5, addValueModel.getRatioMainforceMoneyNetInflow5());
        TextUtils.setText(holder.tevRatioNetinflow10, addValueModel.getRatioMainforceMoneyNetInflow10());
        TextUtils.setText(holder.tevRatioNetinflow20, addValueModel.getRatioMainforceMoneyNetInflow20());

        int priceColor = FormatUtils.getLastPriceColor(quoteItem.upDownFlag, ColorUtils.DEFALUT());
        holder.tevLastPrice.setTextColor(priceColor);
        holder.tevChangeRate.setTextColor(priceColor);

        if (addValueModel.getFiveMinutesChangeRate()==null){
//            System.out.println(addValueModel.fiveMinutesChangeRate +"11111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111");
        }else {
            if( addValueModel.getFiveMinutesChangeRate().contains("-") ){
                holder.tevChangeRateFive.setTextColor(ColorUtils.DOWN);
            }else if (!addValueModel.getFiveMinutesChangeRate().contains("-")&&!addValueModel.getFiveMinutesChangeRate().equals("0.00")){
                holder.tevChangeRateFive.setTextColor(ColorUtils.UP);
            }else {
                holder.tevChangeRateFive.setTextColor(ColorUtils.GRAY);
            }
//            System.out.println(addValueModel.fiveMinutesChangeRate +"11111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111");
        }
        return convertView;
    }

    /**
     * 设置涨幅
     * @param holder
     */
    private void setChangeRate(ViewHolder holder, String symbol, String changeRate) {
        if (!changeRate.contains("-") && !changeRate.contains("+")) {
            changeRate = symbol + changeRate;
        }
        TextUtils.setText(holder.tevChangeRate, changeRate + "%");
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

    class ViewHolder {
        @BindView(R.id.tev_name)
        TextView tevName;
        @BindView(R.id.tev_id)
        TextView tevId;
        @BindView(R.id.tev_lastPrice)
        TextView tevLastPrice;
        @BindView(R.id.tev_changeRate)
        TextView tevChangeRate;
        @BindView(R.id.tev_changeRate_five)
        TextView tevChangeRateFive;   // 五分钟涨跌
        @BindView(R.id.tev_main_netinflow5)
        TextView tevMainNetinflow5;   // 5日主力资金净流入
        @BindView(R.id.tev_main_netinflow10)
        TextView tevMainNetinflow10; // 10日主力资金净流入
        @BindView(R.id.tev_main_netinflow20)
        TextView tevMainNetinflow20;  // 20日主力资金净流入
        @BindView(R.id.tev_ratio_netinflow5)
        TextView tevRatioNetinflow5;  // 5日主力资金净流入占比
        @BindView(R.id.tev_ratio_netinflow10)
        TextView tevRatioNetinflow10;  // 10日主力资金净流入占比
        @BindView(R.id.tev_ratio_netinflow20)
        TextView tevRatioNetinflow20;  // 20日主力资金净流入占比
        @BindView(R.id.tev_month_rate)
        TextView tev_month_rate;  // 本月涨幅
        @BindView(R.id.tev_year_rate)
        TextView tev_year_rate;  // 本年涨幅
        @BindView(R.id.tev_recent_month)
        TextView tev_recent_month;  // 近一月涨幅
        @BindView(R.id.tev_recent_year)
        TextView tev_recent_year;  // 近一年涨幅

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
