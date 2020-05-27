package com.cvicse.stock.portfolio.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.cvicse.base.utils.StringUtils;
import com.cvicse.base.widget.verticalbanner.BaseBannerAdapter;
import com.cvicse.base.widget.verticalbanner.VerticalBannerView;
import com.cvicse.stock.BaseApplication;
import com.cvicse.stock.R;
import com.cvicse.stock.portfolio.data.StockBannerBean;
import com.mitake.core.QuoteItem;

import java.util.HashMap;
import java.util.List;

/**
 * Created by liu_zlu on 2017/1/12 10:24
 */
public class VerticalBannerAdapter extends BaseBannerAdapter<QuoteItem> {

    int text_red = BaseApplication.getInstance().getResources().getColor(R.color.text_red);
    int text_green = BaseApplication.getInstance().getResources().getColor(R.color.text_green);

    private HashMap<String,StockBannerBean> beanHashMap = new HashMap<>();

    public int position = 0;
    public VerticalBannerAdapter(List<QuoteItem> datas) {
        super(datas);
    }

    /**
     * 设置banner的样式
     *
     * @param parent
     */
    @Override
    public View getView(VerticalBannerView parent) {
        return LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mystock_verticalarquee,parent,false);
    }

    @Override
    public void addItem(QuoteItem quoteItem) {
        StockBannerBean stockBannerBean = beanHashMap.get(quoteItem.id);
        if(stockBannerBean != null){
            stockBannerBean.setChanged(true);
        }
        super.addItem(quoteItem);
    }

    @Override
    protected boolean isSameObject(QuoteItem object, QuoteItem comp) {
        return object.id.equals(comp.id);
    }

    /**
     * 设置banner的数据
     *
     * @param view
     * @param data
     */
    @Override
    public void setItem(View view, QuoteItem data,int position) {
        this.position = position;
        TextView tev_name = (TextView) view.findViewById(R.id.tev_name);
        TextView tev_data1 = (TextView) view.findViewById(R.id.tev_newprice);
        TextView tev_data2 = (TextView) view.findViewById(R.id.tev_change);
        TextView tev_data3 = (TextView) view.findViewById(R.id.tev_changeRate);
        StockBannerBean stockBannerBean = beanHashMap.get(data.id);
        if( null == stockBannerBean || stockBannerBean.isChanged()){
            stockBannerBean = createBannerBean(data);
            beanHashMap.put(data.id,stockBannerBean);
        }
        tev_name.setText(stockBannerBean.getName());
        tev_data1.setText(stockBannerBean.getLastPrice());
        tev_data2.setText(stockBannerBean.getChange());
        tev_data3.setText(stockBannerBean.getChangeRate());
        if(stockBannerBean.isRise()){
            tev_data1.setTextColor(text_red);
            tev_data2.setTextColor(text_red);
            tev_data3.setTextColor(text_red);
        } else {
            tev_data1.setTextColor(text_green);
            tev_data2.setTextColor(text_green);
            tev_data3.setTextColor(text_green);
        }
    }

    private StockBannerBean createBannerBean(QuoteItem quoteItem){
        String name = StringUtils.substring(quoteItem.name,0,1);
        if("上".equals(name) ){
            name = "沪";
        }
        String lastPrice = quoteItem.lastPrice;
        String change = quoteItem.change;
        String changeRate = quoteItem.changeRate;
        String id = quoteItem.id;
        long dateTime = 0;
        try {
            dateTime = Long.parseLong(quoteItem.datetime);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        StockBannerBean stockBannerBean = new StockBannerBean(name,lastPrice,change,changeRate,id,dateTime);
        return stockBannerBean;
    }
}
