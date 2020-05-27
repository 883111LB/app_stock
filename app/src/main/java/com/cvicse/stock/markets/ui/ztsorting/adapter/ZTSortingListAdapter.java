package com.cvicse.stock.markets.ui.ztsorting.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cvicse.stock.R;
import com.cvicse.stock.base.PBaseAdapter;
import com.cvicse.stock.common.FillConfig;
import com.cvicse.stock.utils.TextUtils;
import com.mitake.core.bean.ZTSortingItem;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ZTSortingListAdapter extends PBaseAdapter {

    private List<ZTSortingItem> modelList;

    public ZTSortingListAdapter(Context context) {
        super(context);
    }
    public void setData(List<ZTSortingItem> modelList) {
        this.modelList = modelList;

        int length = modelList == null ? 0 : modelList.size();
        for (int i = length - 1; i > -1; i--) {
            ZTSortingItem model = modelList.get(i);
            if (model == null) {
                modelList.remove(i);
            }
        }
        notifyDataSetChanged();
    }
    public List<ZTSortingItem> getData() {
        return modelList;
    }
    @Override
    public int getCount() {
        return modelList == null ? 0 : modelList.size();
    }

    @Override
    public ZTSortingItem getItem(int position) {
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
            convertView = mLayoutInflater.inflate(R.layout.item_list_ztsorting, parent, false);
            holder = new ViewHolder(convertView);

            convertView.setTag(holder);//绑定ViewHolder对象
        } else {
            holder = (ViewHolder) convertView.getTag();//取出ViewHolder对象

        }

        /**设置TextView显示的内容，即我们存放在动态数组中的数据*/
        ZTSortingItem model = modelList.get(position);

        TextUtils.setText(holder.tev_name, model.name, FillConfig.DEFALUT);// 股票名称
        TextUtils.setText(holder.tev_code, model.code, FillConfig.DEFALUT);// 股票代码
        TextUtils.setText(holder.tev_lastPrice, model.lastPrice, FillConfig.DEFALUT);// 最新价
        TextUtils.setText(holder.tev_changeRate, model.changeRate, FillConfig.DEFALUT);// 涨跌幅
        TextUtils.setText(holder.tev_ztDatetime, model.ztDatetime, FillConfig.DEFALUT);// 涨停时间
        TextUtils.setText(holder.tev_buyVolumes, model.buyVolumes.get(0), FillConfig.DEFALUT);// 封板单数
        TextUtils.setText(holder.tev_preClosePrice, model.preClosePrice, FillConfig.DEFALUT);// 昨收价
        return convertView;
    }

    class ViewHolder {
        @BindView(R.id.tev_name)
        TextView tev_name;// 股票名称
        @BindView(R.id.tev_code)
        TextView tev_code;// 股票代码
        @BindView(R.id.tev_lastPrice)
        TextView tev_lastPrice;// 最新价
        @BindView(R.id.tev_changeRate)
        TextView tev_changeRate;// 涨跌幅
        @BindView(R.id.tev_ztDatetime)
        TextView tev_ztDatetime;// 涨停时间
        @BindView(R.id.tev_buyVolumes)
        TextView tev_buyVolumes;// 封板单数
        @BindView(R.id.tev_preClosePrice)
        TextView tev_preClosePrice;// 昨收价

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
