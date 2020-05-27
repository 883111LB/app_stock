package com.cvicse.stock.markets.ui.subnewbondstock.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cvicse.stock.R;
import com.cvicse.stock.base.PBaseAdapter;
import com.cvicse.stock.common.ColorUtils;
import com.cvicse.stock.common.FillConfig;
import com.cvicse.stock.utils.TextUtils;
import com.mitake.core.SubNewStockRankingModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


/** 要约排序页
 * Created by tang_h
 */

public class SubNewBondStockListAdapter extends PBaseAdapter {
    private ArrayList<SubNewStockRankingModel> modelList;

    public SubNewBondStockListAdapter(Context context) {
        super(context);
    }

    public void setData(ArrayList<SubNewStockRankingModel> modelList) {
        this.modelList = modelList;

        int length = modelList == null ? 0 : modelList.size();
        for (int i = length - 1; i > -1; i--) {
            SubNewStockRankingModel model = modelList.get(i);
            if (model == null) {
                modelList.remove(i);
            }
        }
        notifyDataSetChanged();
    }

    public ArrayList<SubNewStockRankingModel> getData() {
        return modelList;
    }

    @Override
    public int getCount() {
        return modelList == null ? 0 : modelList.size();
    }

    @Override
    public SubNewStockRankingModel getItem(int position) {
        return modelList.get(position);
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
            convertView = mLayoutInflater.inflate(R.layout.item_list_subnewbondstock, parent, false);
            holder = new ViewHolder(convertView);

            convertView.setTag(holder);//绑定ViewHolder对象
        } else {
            holder = (ViewHolder) convertView.getTag();//取出ViewHolder对象

        }

        /**设置TextView显示的内容，即我们存放在动态数组中的数据*/
        SubNewStockRankingModel model = modelList.get(position);

        // 判断涨跌状态
        int changeFlag = getChangeFlag(model.getChange());

        TextUtils.setText(holder.mTevCode, model.getCode(), FillConfig.DEFALUT);// 股票代码
        TextUtils.setText(holder.mTevName, model.getName(), FillConfig.DEFALUT);// 股票名称
        TextUtils.setText(holder.mOriginalPrice, model.getOriginalPrice(), FillConfig.DEFALUT);// 发行价
        TextUtils.setText(holder.mLastestPrice, model.getLastestPrice(), FillConfig.DEFALUT);// 最新价
        setChangeColor(holder.mLastestPrice, changeFlag);// 最新价颜色
        TextUtils.setText(holder.mOriginalData, model.getOriginalData(), FillConfig.DEFALUT);// 发行日期
        TextUtils.setText(holder.mCLimitedDays, model.getContinuousLimitedDays(), FillConfig.DEFALUT);// 连续涨停天数
        TextUtils.setText(holder.mAllRate, model.getAllRate(), FillConfig.DEFALUT);// 累计涨跌幅
        TextUtils.setText(holder.mChange, model.getChange(), FillConfig.DEFALUT);// 涨跌额
        setChangeColor(holder.mChange, changeFlag);// 涨跌额颜色
        TextUtils.setText(holder.mTurnoverRate, model.getTurnoverRate(), FillConfig.DEFALUT);// 换手率
        TextUtils.setText(holder.mAmount, model.getAmount(), FillConfig.DEFALUT);// 成交额
        TextUtils.setText(holder.mPe, model.getPe(), FillConfig.DEFALUT);// 市盈率
        TextUtils.setText(holder.mTotalValue, model.getTotalValue(), FillConfig.DEFALUT);// 总市值
        TextUtils.setText(holder.mFlowValue, model.getFlowValue(), FillConfig.DEFALUT);// 流通市值
        TextUtils.setText(holder.mRate, model.getRate(), FillConfig.DEFALUT);// 当天涨幅
        setChangeColor(holder.mRate, changeFlag);// 当天涨幅颜色
        TextUtils.setText(holder.mPreClosePrice, model.getPreClosePrice(), FillConfig.DEFALUT);// 昨收价
        TextUtils.setText(holder.mMainNetInflow, model.getMainforceMoneyNetInflow(), FillConfig.DEFALUT);// 主力资金净流入

        return convertView;
    }

    /**
     * 判断涨跌
     * return: 2:跌，1:涨，0:默认
     */
    @SuppressLint("SetTextI18n")
    private int getChangeFlag(String changeText) {
        if (changeText != null) {
            if (changeText.startsWith("-")) {
                return 2;
            } else if (changeText.startsWith("+")) {
                return 1;
            }
        }
        return 0;
    }
    /**
     * 红绿颜色的 带正负号
     * @param changeFlag 2:跌，1:涨，0:默认
     */
    @SuppressLint("SetTextI18n")
    private void setChangeColor(TextView view, int changeFlag) {
        if (changeFlag == 2) {
            view.setTextColor(ColorUtils.DOWN);
        } else if (changeFlag == 1) {
            view.setTextColor(ColorUtils.UP);
        } else {
            view.setTextColor(ColorUtils.DEFALUT());
        }
    }

    class ViewHolder {
        //股票代码
        @BindView(R.id.tev_code) TextView mTevCode;
        //股票名称
        @BindView(R.id.tev_name) TextView mTevName;
        //发行价
        @BindView(R.id.tev_originalPrice) TextView mOriginalPrice;
        //最新价
        @BindView(R.id.tev_lastestPrice) TextView mLastestPrice;
        //发行日期
        @BindView(R.id.tev_originalData) TextView mOriginalData;
        //连续涨停天数
        @BindView(R.id.tev_cLimitedDays) TextView mCLimitedDays;
        //累计涨跌幅
        @BindView(R.id.tev_allRate) TextView mAllRate;
        //涨跌额
        @BindView(R.id.tev_change) TextView mChange;
        //换手率
        @BindView(R.id.tev_turnoverRate) TextView mTurnoverRate;
        //成交额
        @BindView(R.id.tev_amount) TextView mAmount;
        //市盈率
        @BindView(R.id.tev_pe) TextView mPe;
        //总市值
        @BindView(R.id.tev_totalValue) TextView mTotalValue;
        //流通市值
        @BindView(R.id.tev_flowValue) TextView mFlowValue;
        //当天涨幅
        @BindView(R.id.tev_rate) TextView mRate;
        //昨收价
        @BindView(R.id.tev_preClosePrice) TextView mPreClosePrice;
        //主力资金净流入
        @BindView(R.id.tev_mainNetInflow) TextView mMainNetInflow;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
