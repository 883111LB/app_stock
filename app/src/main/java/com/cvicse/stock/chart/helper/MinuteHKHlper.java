package com.cvicse.stock.chart.helper;

import android.support.design.widget.TabLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cvicse.stock.R;
import com.cvicse.stock.chart.ui.StockMinuteFragment;
import com.cvicse.stock.common.ColorUtils;
import com.cvicse.stock.utils.TextUtils;
import com.mitake.core.BrokerInfoItem;
import com.mitake.core.response.QuoteResponse;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by liu_zlu on 2017/3/23 19:37
 * 港股股票
 */
public class MinuteHKHlper {

    @BindView(R.id.tabLayout_hk) TabLayout tabLayoutHk;
    @BindView(R.id.tev_btokerinfo_1) TextView tevBtokerinfo1;
    @BindView(R.id.tev_btokerinfo_2) TextView tevBtokerinfo2;
    @BindView(R.id.tev_btokerinfo_3) TextView tevBtokerinfo3;
    @BindView(R.id.tev_btokerinfo_4) TextView tevBtokerinfo4;
    @BindView(R.id.tev_btokerinfo_5) TextView tevBtokerinfo5;
    @BindView(R.id.tev_btokerinfo_6) TextView tevBtokerinfo6;
    @BindView(R.id.tev_btokerinfo_7) TextView tevBtokerinfo7;
    @BindView(R.id.tev_btokerinfo_8) TextView tevBtokerinfo8;
    @BindView(R.id.tev_btokerinfo_9) TextView tevBtokerinfo9;
    @BindView(R.id.tev_btokerinfo_10) TextView tevBtokerinfo10;
    @BindView(R.id.tev_btokerinfo_11) TextView tevBtokerinfo11;
    @BindView(R.id.tev_btokerinfo_12) TextView tevBtokerinfo12;
    @BindView(R.id.tev_btokerinfo_13) TextView tevBtokerinfo13;
    @BindView(R.id.tev_btokerinfo_14) TextView tevBtokerinfo14;
    @BindView(R.id.tev_btokerinfo_15) TextView tevBtokerinfo15;
    @BindView(R.id.tev_btokerinfo_16) TextView tevBtokerinfo16;
    @BindView(R.id.tev_btokerinfo_17) TextView tevBtokerinfo17;
    @BindView(R.id.tev_btokerinfo_18) TextView tevBtokerinfo18;
    @BindView(R.id.tev_btokerinfo_19) TextView tevBtokerinfo19;
    @BindView(R.id.tev_btokerinfo_20) TextView tevBtokerinfo20;
    @BindView(R.id.lel_btokerinfo) LinearLayout lelBtokerinfo;

    private ArrayList<TextView> textViews = new ArrayList<>();
    private QuoteResponse quoteResponse;
    private String[] tableTitleArray = {"经纪买盘","经纪卖盘"};
    private StockMinuteFragment stockMinuteFragment;
    private boolean buy = true;

    private ArrayList<BrokerInfoItem> brokerInfoListBuy;
    private ArrayList<BrokerInfoItem> brokerInfoListSell;

    public MinuteHKHlper(LinearLayout linearLayout, StockMinuteFragment stockMinuteFragment) {
        this.stockMinuteFragment = stockMinuteFragment;
        View view = LayoutInflater.from(linearLayout.getContext()).inflate(R.layout.layout_hk_chart_more, linearLayout);
        ButterKnife.bind(this, view);
        initViews();
    }

    private void initViews() {
        brokerInfoListBuy = new ArrayList<>();
        brokerInfoListSell =  new ArrayList<>();

        for(int i=0;i<tableTitleArray.length;i++){
            tabLayoutHk.addTab(tabLayoutHk.newTab().setText(tableTitleArray[i]));
        }

        tabLayoutHk.setTabMode(TabLayout.MODE_FIXED);//固定模式
        tabLayoutHk.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(tab.getPosition() == 0){
                    buy = true;
                } else {
                    buy = false;
                }
                update();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        textViews.add(tevBtokerinfo1);
        textViews.add(tevBtokerinfo2);
        textViews.add(tevBtokerinfo3);
        textViews.add(tevBtokerinfo4);
        textViews.add(tevBtokerinfo5);
        textViews.add(tevBtokerinfo6);
        textViews.add(tevBtokerinfo7);
        textViews.add(tevBtokerinfo8);
        textViews.add(tevBtokerinfo9);
        textViews.add(tevBtokerinfo10);
        textViews.add(tevBtokerinfo11);
        textViews.add(tevBtokerinfo12);
        textViews.add(tevBtokerinfo13);
        textViews.add(tevBtokerinfo14);
        textViews.add(tevBtokerinfo15);
        textViews.add(tevBtokerinfo16);
        textViews.add(tevBtokerinfo17);
        textViews.add(tevBtokerinfo18);
        textViews.add(tevBtokerinfo19);
    }

    public void setData(QuoteResponse quoteResponse){
        this.quoteResponse = quoteResponse;
        update();
    }
    private void update(){
        if(quoteResponse == null) {
            return;
        }
        if( null != quoteResponse.BrokerInfoListBuy ){
            brokerInfoListBuy = quoteResponse.BrokerInfoListBuy;
        }
        if( null != quoteResponse.BrokerInfoListSell ){
            brokerInfoListSell = quoteResponse.BrokerInfoListSell;
        }

        ArrayList<BrokerInfoItem> brokerInfoItems;
        if(buy){
//            brokerInfoItems = quoteResponse.BrokerInfoListBuy;
            brokerInfoItems = brokerInfoListBuy;
        } else {
//            brokerInfoItems = quoteResponse.BrokerInfoListSell;
            brokerInfoItems = brokerInfoListSell;
        }
        int length = brokerInfoItems == null ? -1:brokerInfoItems.size()-1;
        for(int i = 0;i < 19;i++){
            if(i > length){
                textViews.get(i).setText("");
            } else {
                String id = null;
                if(brokerInfoItems.get(i) != null){
                    id = brokerInfoItems.get(i).id;
                    TextUtils.setText(textViews.get(i),id,"");
                    if(null != id && id.contains("S")){
                        textViews.get(i).setTextColor(ColorUtils.UP);
                    } else {
                        textViews.get(i).setTextColor(ColorUtils.DEFALUT());
                    }
                } else {
                    textViews.get(i).setText("");
                    textViews.get(i).setTextColor(ColorUtils.DEFALUT());
                }
            }
        }
    }
}
