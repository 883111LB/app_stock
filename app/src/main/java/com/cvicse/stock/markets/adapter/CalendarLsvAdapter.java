package com.cvicse.stock.markets.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cvicse.stock.R;
import com.cvicse.stock.base.PBaseAdapter;
import com.cvicse.stock.utils.TextUtils;
import com.mitake.core.NewShareItem;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ruan_ytai on 17-2-27.
 */

public class CalendarLsvAdapter extends PBaseAdapter {

    public static final String JRSG = "今日申购";
    public static final String JRSS = "今日上市";
    public static final String JRZQ = "今日中签";
    public static final String JJFX = "即将发行";
    public static final String WSS = "未上市";

    private final int TYPE_1 = 0;
    private final int TYPE_2 = 1;

    private String[] sgArr = {"申购代码", "发行价", "发行市盈率", "申购上限"};
    private String[] ssArr = {"股票代码", "发行价", "发行市盈率", "中签率"};
    private String[] zqArr = {"股票代码", "发行价", "发行市盈率", "上市日期"};
    private String[] fxArr = {"申购代码", "发行价", "发行市盈率", "申购日期"};
    private String[] wssArr = {"股票代码", "发行价", "中签率", "上市日期"};

    private ArrayList<NewShareItem> newShareItemList;
    private String type;

    public CalendarLsvAdapter(Context context, String type) {
        super(context);
        this.type = type;
    }

    public void setData(ArrayList<NewShareItem> newShareItemList) {
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
    public NewShareItem getItem(int position) {
        if(position >= 1){
            return newShareItemList.get(position - 1 );
        }

        return null;

    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder1 viewHolder1 = null;
        ViewHolder2 viewHolder2 = null;
        int resType = getItemViewType(position);
        if (convertView == null) {
            if (resType == TYPE_1) {
                convertView = mLayoutInflater.inflate(R.layout.item_calendar_lsv_title, parent,false);
                viewHolder1 = new ViewHolder1(convertView);
                convertView.setTag(viewHolder1);
            } else if (resType == TYPE_2) {
                convertView = mLayoutInflater.inflate(R.layout.item_calendar_lsv_item, parent,false);
                viewHolder2 = new ViewHolder2(convertView);
                convertView.setTag( viewHolder2);
            }

        } else {
            if (resType == TYPE_1) {
                viewHolder1 = (ViewHolder1) convertView.getTag();
            } else if (resType == TYPE_2) {
                viewHolder2 = (ViewHolder2) convertView.getTag();
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

            case JRSS:
                if (position == 0) {
                    initTitle(viewHolder1, ssArr);
                } else {
                    initItem(viewHolder2, position);
                }
                break;

            case JRZQ:
                if (position == 0) {
                    initTitle(viewHolder1, zqArr);
                } else {
                    initItem(viewHolder2, position);
                }
                break;

            case JJFX:
                if (position == 0) {
                    initTitle(viewHolder1, fxArr);
                } else {
                    initItem(viewHolder2, position);
                }
                break;

            case WSS:
                if (position == 0) {
                    initTitle(viewHolder1, wssArr);
                } else {
                    initItem(viewHolder2, position);
                }
                break;

            default:
                break;
        }

        return convertView;
    }

    private void initTitle(ViewHolder1 viewHolder1, String[] arr) {
        if (arr != null) {
            viewHolder1.mTevCode.setText(arr[0]);
            viewHolder1.mTevPrice.setText(arr[1]);
            viewHolder1.mTevPercent.setText(arr[2]);
            viewHolder1.mTevCommon.setText(arr[3]);
        }
    }

    private void initItem(ViewHolder2 viewHolder2, int position) {
        switch (type) {
            case JRSG:
                if ("Y".equals(newShareItemList.get(position-1).getKeyCode())){
                    viewHolder2.tevSetColor.setVisibility(View.VISIBLE);
                }else {
                    viewHolder2.tevSetColor.setVisibility(View.GONE);
                }
                viewHolder2.mTevCompany.setText(newShareItemList.get(position - 1).getSecuabbr());
                viewHolder2.mTevCode.setText(newShareItemList.get(position - 1).getApplyCode());
                viewHolder2.mTevPrice.setText(newShareItemList.get(position - 1).getIssuePrice());
                viewHolder2.mTevPercent.setText(newShareItemList.get(position - 1).getPeaIssue());
                //申购上限
                viewHolder2.mTevCommon.setText(newShareItemList.get(position - 1).getCapplyShare());
                break;

            case JRSS:
                if ("Y".equals(newShareItemList.get(position-1).getKeyCode())){
                    viewHolder2.tevSetColor.setVisibility(View.VISIBLE);
                }else {
                    viewHolder2.tevSetColor.setVisibility(View.GONE);
                }
                viewHolder2.mTevCompany.setText(newShareItemList.get(position - 1).getSecuabbr());
                viewHolder2.mTevCode.setText(newShareItemList.get(position - 1).getApplyCode());
                viewHolder2.mTevPrice.setText(newShareItemList.get(position - 1).getIssuePrice());
                viewHolder2.mTevPercent.setText(newShareItemList.get(position - 1).getPeaIssue());
                //中签率
                //viewHolder2.mTevCommon.setText(newShareItemList.get(position - 1).getAllotrateon());
                TextUtils.setTextPercent(viewHolder2.mTevCommon,
                        newShareItemList.get(position - 1).getAllotrateon());
                break;

            case JRZQ:
                if ("Y".equals(newShareItemList.get(position-1).getKeyCode())){
                    viewHolder2.tevSetColor.setVisibility(View.VISIBLE);
                }else {
                    viewHolder2.tevSetColor.setVisibility(View.GONE);
                }
                System.out.println(newShareItemList.get(position - 1).getSecuabbr()+newShareItemList.get(position-1).getKeyCode()+"+++++");
                viewHolder2.mTevCompany.setText(newShareItemList.get(position - 1).getSecuabbr());
                //股票代码、即交易代码
                viewHolder2.mTevCode.setText(newShareItemList.get(position - 1).getTradingCode());
                viewHolder2.mTevPrice.setText(newShareItemList.get(position - 1).getIssuePrice());
                viewHolder2.mTevPercent.setText(newShareItemList.get(position - 1).getPeaIssue());
                //上市日期
                viewHolder2.mTevCommon.setText(newShareItemList.get(position - 1).getListingDate());
                break;

            case JJFX:
                if ("Y".equals(newShareItemList.get(position-1).getKeyCode())){
                    viewHolder2.tevSetColor.setVisibility(View.VISIBLE);
                }else {
                    viewHolder2.tevSetColor.setVisibility(View.GONE);
                }
                viewHolder2.mTevCompany.setText(newShareItemList.get(position - 1).getSecuabbr());
                viewHolder2.mTevCode.setText(newShareItemList.get(position - 1).getApplyCode());
                viewHolder2.mTevPrice.setText(newShareItemList.get(position - 1).getIssuePrice());
                viewHolder2.mTevPercent.setText(newShareItemList.get(position - 1).getPeaIssue());
                //申购日期
                viewHolder2.mTevCommon.setText(newShareItemList.get(position - 1).getBookStartDateOn());
                break;

            case WSS:
                if ("Y".equals(newShareItemList.get(position-1).getKeyCode())){
                    viewHolder2.tevSetColor.setVisibility(View.VISIBLE);
                }else {
                    viewHolder2.tevSetColor.setVisibility(View.GONE);
                }
                viewHolder2.mTevCompany.setText(newShareItemList.get(position - 1).getSecuabbr());
                viewHolder2.mTevCode.setText(newShareItemList.get(position - 1).getApplyCode());
                viewHolder2.mTevPrice.setText(newShareItemList.get(position - 1).getIssuePrice());
                //中签率
                TextUtils.setTextPercent(viewHolder2.mTevPercent,newShareItemList.get(position - 1).getAllotrateon());
                //上市日期
                viewHolder2.mTevCommon.setText(newShareItemList.get(position - 1).getListingDate());
                break;

            default:
                break;

        }


    }

    static class ViewHolder1 {

        @BindView(R.id.tev_code) TextView mTevCode;
        @BindView(R.id.tev_price) TextView mTevPrice;
        @BindView(R.id.tev_percent) TextView mTevPercent;
        @BindView(R.id.tev_common) TextView mTevCommon;

        ViewHolder1(View view) {
            ButterKnife.bind(this, view);
        }
    }

    static class ViewHolder2 {
        @BindView(R.id.tev_setcolor) TextView tevSetColor;
        @BindView(R.id.tev_company) TextView mTevCompany;
        @BindView(R.id.tev_code) TextView mTevCode;
        @BindView(R.id.tev_price) TextView mTevPrice;
        @BindView(R.id.tev_percent) TextView mTevPercent;
        @BindView(R.id.tev_common) TextView mTevCommon;

        ViewHolder2(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
