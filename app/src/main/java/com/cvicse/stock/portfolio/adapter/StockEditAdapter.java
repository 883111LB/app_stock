package com.cvicse.stock.portfolio.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cvicse.stock.R;
import com.cvicse.stock.base.PBaseAdapter;
import com.cvicse.stock.portfolio.data.MyStockEditBean;
import com.cvicse.stock.utils.MyStocksUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 自选股编辑适配器
 * Created by ding_syi on 17-1-16.
 */
public class StockEditAdapter extends PBaseAdapter {
    private List<MyStockEditBean> myStockBeanList;
    /**
     * 选择删除列position的存储
     */
    public ArrayList<String> integers = new ArrayList<>();

    public StockEditAdapter(Context context) {
        super(context);
    }

    public void setData(List<MyStockEditBean> myStockBeanList) {
        this.myStockBeanList = myStockBeanList;
        notifyDataSetChanged();
        //integers.clear();
    }
    public void drag(int from, int to){
        MyStockEditBean myStockEditBean = myStockBeanList.remove(from);
        myStockBeanList.add(to,myStockEditBean);
        notifyDataSetChanged();
    }

   public List<MyStockEditBean>getData(){
        return myStockBeanList ;
    }
    /**
     * 添加或删除具体项
     *
     * @param position
     */
    public void toggleChange(int position) {
        MyStockEditBean myStockEditBean = myStockBeanList.get(position);
        if (this.integers.contains(myStockEditBean.getId())) {
            this.integers.remove(myStockEditBean.getId());
            myStockBeanList.get(position).setIfChoose(false);

        } else {
            this.integers.add(myStockEditBean.getId());
            myStockBeanList.get(position).setIfChoose(true);
        }
        Collections.sort(this.integers);
        super.notifyDataSetChanged();
    }

    /**
     * 全选或全部清除
     */
    public void toggle() {
        Collections.sort(this.integers);
        if (integers.size() != getCount()) {
            integers.clear();
            for (int i = 0; i < this.getCount(); i++) {
                this.integers.add(myStockBeanList.get(i).getId());
            }
        } else {
            this.integers.clear();
        }
        super.notifyDataSetChanged();
    }

    /**
     * 删除所有选择项
     */
    public void deleteChose() {

        int length = myStockBeanList == null ? 0: myStockBeanList.size();
        for(int i = length -1;i > -1;i--){
            MyStockEditBean myStockEditBean = myStockBeanList.get(i);
            if(integers.contains(myStockEditBean.getId())){
                MyStocksUtils.removeSelect(myStockEditBean.getId(), myStockEditBean.getName());
                myStockBeanList.remove(myStockEditBean);
            }
        }
        super.notifyDataSetChanged();
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
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        //观察convertView随ListView滚动情况
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.item_mystock_edit, parent, false);
            holder = new ViewHolder(convertView);
            holder.lelTopView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = (int) v.getTag();
                    setTop(position);
                }
            });
            /**得到各个控件的对象*/
            convertView.setTag(holder);//绑定ViewHolder对象
        } else {
            holder = (ViewHolder) convertView.getTag();//取出ViewHolder对象
        }
        final MyStockEditBean stockItem = myStockBeanList.get(position);
        /**设置TextView显示的内容，即我们存放在动态数组中的数据*/
        holder.tevStockName.setText(stockItem.getName() == null ? "-" : stockItem.getName());
        holder.tevStockCode.setText(stockItem.getId() == null ? "-" : stockItem.getId());
        if (integers.contains(stockItem.getId())) {
            holder.viewTag.setImageResource(R.drawable.img_edit_selectd);
        } else {
            holder.viewTag.setImageResource(R.drawable.img_edit_select_no);
        }
        holder.lelTopView.setTag(position);
        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.viewTag) ImageView viewTag;
        /**
         * 股票名
         */
        @BindView(R.id.tev_stock_name) TextView tevStockName;
        /**
         * 股票编码
         */
        @BindView(R.id.tev_stock_code) TextView tevStockCode;
        @BindView(R.id.lel_top_view) LinearLayout lelTopView;
        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }


    private void setTop(int position){
        drag(position,0);
    }
}
