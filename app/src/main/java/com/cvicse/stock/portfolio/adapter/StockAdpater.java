package com.cvicse.stock.portfolio.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cvicse.stock.R;
import com.cvicse.stock.base.PBaseAdapter;
import com.cvicse.stock.common.ColorUtils;
import com.cvicse.stock.common.FillConfig;
import com.cvicse.stock.util.FormatUtils;
import com.cvicse.stock.utils.DataConvert;
import com.cvicse.stock.utils.TextUtils;
import com.mitake.core.QuoteItem;
import com.mitake.core.StockInfoListItem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by ding_syi on 17-1-5.
 */
public class StockAdpater extends PBaseAdapter {

    private List<QuoteItem> myStockBeanList = new ArrayList<>();

    //备份数据
    private List<QuoteItem> myStockBackUp = new ArrayList<>();

    private HashMap<String, StockInfoListItem> mInfoListItems;

    private TextView textView;
    //成交额标志
    private boolean isAmount = false;

    //    private int showType = SHOW_CHANGE_TYPE;  //默认类型为涨跌额
    private int showType = SHOW_CHANGERATE_TYPE;  //默认类型为涨跌幅

    // 涨跌额
    public static int SHOW_CHANGE_TYPE = 1;
    //成交额
    public static int SHOW_COUNT_TYPE = 2;
    //涨跌幅
    public static int SHOW_CHANGERATE_TYPE = 3;

    public int sortType = DEFAULT;
    public static final int DEFAULT = 0;
    public static final int UP = 1;
    public static final int DOWN = 2;

    private boolean isLeft = true;

    public StockAdpater(TextView textView) {
        super(textView.getContext());
        this.textView = textView;
    }

    public void setSignData(HashMap<String, StockInfoListItem> infoListItems) {
        mInfoListItems = infoListItems;
        notifyDataSetChanged();
    }


    public void setSortType(boolean isLeft, int sortType) {
        this.isLeft = isLeft;
        this.sortType = sortType;
        notifyDataSetChanged();
    }

    /**
     * 更新 展示数据
     *
     * @param quoteItem
     */
    public int updateItem(QuoteItem quoteItem) {
        if (null  == myStockBeanList || myStockBeanList.size() == 0) {
            return -1;
        }
        int index = 0;
        int length = myStockBeanList.size();
        QuoteItem quote = null;
        for (int i = 0; i < length; i++) {
            quote = myStockBeanList.get(i);
            if ( null!= quote &&  null!=  quote.id && quote.id.equals(quoteItem.id)) {
                DataConvert.copy(quoteItem,quote);
                myStockBeanList.set(i, quoteItem);
                index = i;
                break;
            }
        }
//        int index = myStockBeanList.indexOf(quote);
        if (index > -1) {
            myStockBeanList.set(index, quoteItem);
        }
        return index;
    }

    public ArrayList<QuoteItem> getData() {
        return (ArrayList<QuoteItem>) myStockBeanList;
    }

    public void setData(List<QuoteItem> myStockBeanList) {
        myStockBackUp = myStockBeanList;

        if (myStockBackUp != null && myStockBackUp.size() > 0) {
            this.myStockBeanList = new ArrayList<>(myStockBackUp);
        } else {
            this.myStockBeanList = new ArrayList<>();
        }

        notifyDataSetChanged();
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        //观察convertView随ListView滚动情况
        if ( null == convertView) {
            convertView = mLayoutInflater.inflate(R.layout.item_mystock_list, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        QuoteItem quoteItem = myStockBeanList.get(position);
        /**设置TextView显示的内容，即我们存放在动态数组中的数据*/
        if ( null!= quoteItem &&  null!=quoteItem.name ) {
            holder.colorImageView.setVisibility(View.VISIBLE);
        }
        TextUtils.setText( holder.stockName,quoteItem.name,FillConfig.DEFALUT);

        // 添加融资、融券标识
        if( "1".equals(quoteItem.su) || "1".equals(quoteItem.bu)){
            holder.colorImageView.setVisibility(View.VISIBLE);
            holder.colorImageView.setImageResource(R.drawable.rong);
        }else if( "1".equals(quoteItem.zh) && "1".equals(quoteItem.hh) ){
            holder.colorImageView.setImageResource(R.drawable.hushen);
        }else if("1".equals(quoteItem.zh)){
            holder.colorImageView.setImageResource(R.drawable.shengang);
        }else if("1".equals(quoteItem.hh)){
            holder.colorImageView.setImageResource(R.drawable.hugang);
        }else{
            holder.colorImageView.setImageDrawable(null);
        }
        holder.stockCount.setText(quoteItem.id);
        holder.stockCountLastprice.setText(quoteItem.lastPrice);
        if (showType == SHOW_CHANGE_TYPE) {
            holder.stockAcount.setText(quoteItem.change);
        } else if (showType == SHOW_COUNT_TYPE) {
            holder.stockAcount.setText(FormatUtils.format(quoteItem.amount));
        } else {
            if (quoteItem.changeRate != null && !quoteItem.changeRate.equals(FillConfig.DEFALUT)) {
                holder.stockAcount.setText(quoteItem.changeRate + "%");
            } else {
                holder.stockAcount.setText(FillConfig.DEFALUT);
            }
        }

        if (quoteItem.change != null && quoteItem.change.startsWith("+")) {
            holder.stockCountLastprice.setTextColor(ColorUtils.UP);
            holder.stockAcount.setBackgroundResource(R.drawable.shape_bg_red);
        } else if (quoteItem.change != null && quoteItem.change.startsWith("-")) {
            holder.stockCountLastprice.setTextColor(ColorUtils.DOWN);
            holder.stockAcount.setBackgroundResource(R.drawable.shape_bg_green);
        } else {
            holder.stockCountLastprice.setTextColor(ColorUtils.DEFALUT());
            holder.stockAcount.setBackgroundResource(R.color.bg_gray_dark);
        }

        //设置背景颜色
        if (isAmount) {
            holder.stockAcount.setBackgroundResource(R.drawable.shape_bg_gray);
        }

        holder.relAcount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //涨跌幅
                if (showType == SHOW_CHANGE_TYPE) {
                    showType = SHOW_COUNT_TYPE;
                    textView.setText("成交额");
                    //为成交额时，设置背景颜色为灰色
                    isAmount = true;
                } else if (showType == SHOW_COUNT_TYPE) {
                    showType = SHOW_CHANGERATE_TYPE;
                    textView.setText("涨跌幅");
                    isAmount = false;
                } else {
                    showType = SHOW_CHANGE_TYPE;
                    textView.setText("涨跌额");
                    isAmount = false;
                }
                notifyDataSetChanged();
            }
        });
        return convertView;
    }

    private void sortData() {
        if (sortType == DEFAULT ||  null==myStockBeanList  || myStockBeanList.isEmpty()) {
            if ( null!= myStockBeanList) {
                this.myStockBeanList = new ArrayList<>(myStockBackUp);
            }
            return;
        }
        Collections.sort(myStockBeanList, comparator);
    }

    Comparator comparator = new Comparator<QuoteItem>() {
        @Override
        public int compare(QuoteItem lhs, QuoteItem rhs) {
            float lastPrice = 0;
            float lastPrice1 = 0;
            if (isLeft) {
                lastPrice = strToFloat(lhs.lastPrice);
                lastPrice1 = strToFloat(rhs.lastPrice);
            } else {
                String string1 = "";
                String string2 = "";
                if (showType == SHOW_COUNT_TYPE) {
                    string1 = lhs.amount;
                    string2 = rhs.amount;
                } else if (showType == SHOW_CHANGE_TYPE) {
                    string1 = lhs.change;
                    string2 = rhs.change;
                } else {
                    string1 = lhs.changeRate;
                    string2 = rhs.changeRate;
                }
                lastPrice = strToFloat(string1);
                lastPrice1 = strToFloat(string2);
            }
            if (sortType == UP) {
                return lastPrice > lastPrice1 ? 0 : -1;
            } else {
                return lastPrice > lastPrice1 ? -1 : 0;
            }
        }
    };

    private float strToFloat(String string) {
        if (!FormatUtils.isStandard(string)) {
            return 0;
        }
        string = string.replace("%", "");
        return Float.parseFloat(string);
    }

    @Override
    public void notifyDataSetChanged() {
        sortData();
        super.notifyDataSetChanged();
    }

    class ViewHolder {
        @BindView(R.id.stock_name)
        TextView stockName;
        @BindView(R.id.img_symbol)
        ImageView colorImageView;
        @BindView(R.id.stock_count)
        TextView stockCount;
        @BindView(R.id.stock_count_lastprice)
        TextView stockCountLastprice;
        @BindView(R.id.stock_acount)
        TextView stockAcount;
        @BindView(R.id.stock_layout)
        LinearLayout stockLayout;
        @BindView(R.id.rel_acount)
        RelativeLayout relAcount;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
