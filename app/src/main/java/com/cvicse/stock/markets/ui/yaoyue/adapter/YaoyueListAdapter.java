package com.cvicse.stock.markets.ui.yaoyue.adapter;

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
import com.mitake.core.bean.offer.OfferQuoteBean;
import com.mitake.core.util.FormatUtility;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.cvicse.stock.markets.data.ParamUtil.RANKING_TYPE_AMOUNT;
import static com.cvicse.stock.markets.data.ParamUtil.RANKING_TYPE_TURNOVERRATE;


/** 要约排序页
 * Created by tang_h
 */

public class YaoyueListAdapter extends PBaseAdapter {
    private ArrayList<OfferQuoteBean> offerQuoteList;

    public YaoyueListAdapter(Context context) {
        super(context);
    }

    public void setData(ArrayList<OfferQuoteBean> offerQuoteList) {
        this.offerQuoteList = offerQuoteList;

        int length = offerQuoteList == null ? 0 : offerQuoteList.size();
        for (int i = length - 1; i > -1; i--) {
            OfferQuoteBean offerQuoteBean = offerQuoteList.get(i);
            if (offerQuoteBean == null) {
                offerQuoteList.remove(i);
            }
        }
        notifyDataSetChanged();
    }

    public ArrayList<OfferQuoteBean> getData() {
        return offerQuoteList;
    }

    @Override
    public int getCount() {
        return offerQuoteList == null ? 0 : offerQuoteList.size();
    }

    @Override
    public OfferQuoteBean getItem(int position) {
        return offerQuoteList.get(position);
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
            convertView = mLayoutInflater.inflate(R.layout.item_list_yaoyue, parent, false);
            holder = new ViewHolder(convertView);

            convertView.setTag(holder);//绑定ViewHolder对象
        } else {
            holder = (ViewHolder) convertView.getTag();//取出ViewHolder对象

        }

        /**设置TextView显示的内容，即我们存放在动态数组中的数据*/
        OfferQuoteBean quoteItem = offerQuoteList.get(position);

        TextUtils.setText(holder.mTevCode, quoteItem.code, FillConfig.DEFALUT);
        TextUtils.setText(holder.mTevName, quoteItem.name, FillConfig.DEFALUT);
        TextUtils.setText(holder.mTevOfferId, quoteItem.offerId, FillConfig.DEFALUT);
        TextUtils.setText(holder.mTevOfferName, quoteItem.offerName, FillConfig.DEFALUT);
        TextUtils.setText(holder.mTevPrice, quoteItem.price, FillConfig.DEFALUT);
        TextUtils.setText(holder.mTevStartDate, quoteItem.startDate, FillConfig.DEFALUT);
        TextUtils.setText(holder.mTevEndDate, quoteItem.endDate, FillConfig.DEFALUT);

        return convertView;
    }

    class ViewHolder {
        //证券代码
        @BindView(R.id.tev_code) TextView mTevCode;
        //证券名称
        @BindView(R.id.tev_name) TextView mTevName;
        //收购编码
        @BindView(R.id.tev_offerId) TextView mTevOfferId;
        //收购人名称
        @BindView(R.id.tev_offerName) TextView mTevOfferName;
        //收购价格
        @BindView(R.id.tev_price) TextView mTevPrice;
        //收购起始日
        @BindView(R.id.tev_startDate) TextView mTevStartDate;
        //收购截止日
        @BindView(R.id.tev_endDate) TextView mTevEndDate;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
