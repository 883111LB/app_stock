package com.cvicse.stock.markets.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cvicse.stock.R;
import com.cvicse.stock.base.PBaseAdapter;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 新债列表适配器
 * Created by tang_xqing on 18-4-25.
 */
public class BondCalLsvAdapter extends PBaseAdapter {

    public static final String JRSG = "今日申购";
    public static final String JJSG = "即将申购";
    public static final String JJSS = "即将上市";

    private String[] sgArr = {"债券代码", "正股代码", "转股价格"};
    private String[] dssArr = {"债券代码", "中签率", "上市日期"};

    private final int TYPE_1 = 0;
    private final int TYPE_2 = 1;

    private List<HashMap<String, Object>> newShareItemList;
    private String type;

    public BondCalLsvAdapter(Context context, String type) {
        super(context);
        this.type = type;
    }

    public void setData(List<HashMap<String, Object>> newShareItemList) {
        this.newShareItemList = newShareItemList;
        notifyDataSetChanged();
    }

    /**
     * item加载不同的布局
     */
    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_1;
        } else {
            return TYPE_2;
        }
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getCount() {
        return newShareItemList == null ? 0 : newShareItemList.size() + 1;
    }

    @Override
    public HashMap<String, Object> getItem(int position) {
        if (position >= 1) {
            return newShareItemList.get(position - 1);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolderTitle viewHolder1 = null;
        ViewHolderContent viewHolder2 = null;
        int resType = getItemViewType(position);
        if ( null== convertView) {
            if (resType == TYPE_1) {
                convertView = mLayoutInflater.inflate(R.layout.item_bond_cal_lsv_title, parent, false);
                viewHolder1 = new ViewHolderTitle(convertView);
                convertView.setTag(viewHolder1);
            } else if (resType == TYPE_2) {
                convertView = mLayoutInflater.inflate(R.layout.item_bond_cal_lsv_item, parent, false);
                viewHolder2 = new ViewHolderContent(convertView);
                convertView.setTag(viewHolder2);
            }
        } else {
            if (resType == TYPE_1) {
                viewHolder1 = (ViewHolderTitle) convertView.getTag();
            } else if (resType == TYPE_2) {
                viewHolder2 = (ViewHolderContent) convertView.getTag();
            }
        }

        /**
         * 根据不同的type、position，填充不同的数据
         */
        switch (type) {
            case JRSG:
                if (position == 0) {
                    initTitle(viewHolder1, sgArr);
                } else {
                    initItem(viewHolder2, position);
                }
                break;

            case JJSG:
                if (position == 0) {
                    initTitle(viewHolder1, sgArr);
                } else {
                    initItem(viewHolder2, position);
                }
                break;

            case JJSS:
                if (position == 0) {
                    initTitle(viewHolder1, dssArr);
                } else {
                    initItem(viewHolder2, position);
                }
                break;
            default:
                break;
        }

        return convertView;
    }

    private void initTitle(ViewHolderTitle viewHolder1, String[] arr) {
        if (arr != null) {
            viewHolder1.tevCode.setText(arr[0]);
            viewHolder1.tevPercent.setText(arr[1]);

            if( JJSS.equals(type) ){
                viewHolder1.tevStockCode.setVisibility(View.GONE);
                viewHolder1.tevListingDate.setVisibility(View.VISIBLE);
                viewHolder1.tevListingDate.setText(arr[2]);
            }else{
                viewHolder1.tevListingDate.setVisibility(View.GONE);
                viewHolder1.tevStockCode.setVisibility(View.VISIBLE);
                viewHolder1.tevStockCode.setText(arr[2]);
            }
        }
    }

    private void initItem(ViewHolderContent viewHolder2, int position) {
        switch (type) {
            case JRSG:  // 今日申购
            case JJSG:  // 即将申购
                viewHolder2.tevCompany.setText((CharSequence) newShareItemList.get(position - 1).get("SECUABBR"));
                viewHolder2.tevCode.setText((CharSequence) newShareItemList.get(position - 1).get("TRADINGCODE"));
                viewHolder2.tevPrice.setText((CharSequence) newShareItemList.get(position-1).get("PREFERREDPLACINGCODE"));
                viewHolder2.tevStockCompany.setText((CharSequence) newShareItemList.get(position-1).get("STOCKSECUABBR"));
                viewHolder2.tevStockCode.setText((CharSequence) newShareItemList.get(position-1).get("STOCKTRADINGCODE"));
                viewHolder2.llyStock.setVisibility(View.VISIBLE);
                viewHolder2.tevListingDate.setVisibility(View.GONE);
                break;

            case JJSS:  // 即将上市
                viewHolder2.tevCompany.setText((CharSequence) newShareItemList.get(position - 1).get("SECUABBR"));
                viewHolder2.tevCode.setText((CharSequence) newShareItemList.get(position - 1).get("TRADINGCODE"));
                viewHolder2.tevPrice.setText((CharSequence) newShareItemList.get(position-1).get("ALLOTRATEON"));
                viewHolder2.tevListingDate.setText((CharSequence) newShareItemList.get(position-1).get("LISTINGDATE"));
                viewHolder2.tevListingDate.setVisibility(View.VISIBLE);
                viewHolder2.llyStock.setVisibility(View.GONE);
                break;
            default:
                break;
        }
    }

    static class ViewHolderTitle {
        @BindView(R.id.tev_code)
        TextView tevCode;   // 申购名
        @BindView(R.id.tev_stock_code)
        TextView tevStockCode;  // 正股名
        @BindView(R.id.tev_percent)
        TextView tevPercent;  // 中签率
        @BindView(R.id.tev_listing_date)
        TextView tevListingDate;   // 上市日期

        ViewHolderTitle(View view) {
            ButterKnife.bind(this, view);
        }
    }

    static class ViewHolderContent {
        @BindView(R.id.tev_company)
        TextView tevCompany;   // 申购名
        @BindView(R.id.tev_code)
        TextView tevCode;  // 申购代码
        @BindView(R.id.tev_stock_company)
        TextView tevStockCompany;  // 正股名
        @BindView(R.id.tev_stock_code)
        TextView tevStockCode;   // 正股代码
        @BindView(R.id.tev_price)
        TextView tevPrice;   // 中签率
        @BindView(R.id.tev_listing_date)
        TextView tevListingDate;  // 上市日期
        @BindView(R.id.lly_stock)
        LinearLayout llyStock;

        ViewHolderContent(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
