package com.cvicse.stock.chart.ui;

import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cvicse.annotations.MVPInject;
import com.cvicse.stock.R;
import com.cvicse.stock.base.ChartBaseFragment;
import com.cvicse.stock.chart.data.MinuteEntity;
import com.cvicse.stock.chart.listener.OnChartListener;
import com.cvicse.stock.chart.listener.OnMinuteSelectedListener;
import com.cvicse.stock.chart.presenter.contract.StockDayContract;
import com.cvicse.stock.markets.data.TickItemBo;
import com.cvicse.stock.markets.helper.QuoteCallback;
import com.cvicse.stock.markets.ui.StockDetailLandActivity;
import com.cvicse.stock.util.FormatUtils;
import com.cvicse.stock.utils.TextUtils;
import com.cvicse.stock.utils.UpDownUtils;
import com.mitake.core.OHLCItem;
import com.mitake.core.QuoteItem;
import com.mitake.core.request.ChartType;
import com.mitake.core.response.ChartResponse;
import com.mitake.core.response.OrderQuantityResponse;
import com.mitake.core.response.QuoteResponse;
import com.stock.config.FillConfig;
import com.stock.config.MSetting;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * 五日图
 * Created by liu_zlu on 2017/2/22 15:42
 */
public class StockFiveDayFragment extends ChartBaseFragment implements StockDayContract.View,QuoteCallback{

    private static final String QUOTEITEM = "QuoteItem";
    @BindView(R.id.tev_time) TextView tevTime;
    @BindView(R.id.tev_price) TextView tevPrice;
    @BindView(R.id.tev_prencet) TextView tevPrencet;
    @BindView(R.id.tev_average_price) TextView tevAveragePrice;
    @BindView(R.id.tev_volume) TextView tevVolume;

    @BindView(R.id.stock_fiveday_chart) StockFiveDayChart stockFiveDayChart;

    @MVPInject
    StockDayContract.Presenter presenter;

    private QuoteItem quoteItem;
    public static StockFiveDayFragment newInstance(QuoteItem quoteItem) {
        StockFiveDayFragment fiveDayFragment = new StockFiveDayFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(QUOTEITEM,quoteItem);
        fiveDayFragment.setArguments(bundle);
        return fiveDayFragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_stock_fiveday_chart;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        quoteItem = getArguments().getParcelable(QUOTEITEM);
        presenter.init(quoteItem, ChartType.FIVE_DAY, MSetting.getMSubType());
        presenter.requestChart();//请求走势数据

        //高亮监听
        stockFiveDayChart.setOnChartSelectedListener(new OnMinuteSelectedListener() {
            @Override
            public void onValueSelected(int position, OHLCItem ohlcItem,boolean last) {
                valueSelected(ohlcItem,last);
            }

            @Override
            public void onValueSelected(int position, MinuteEntity mLineEntity) {

            }
        });

        if (isLand) {
            stockFiveDayChart.setLand(true);
            ViewGroup.LayoutParams layoutParams = getMainView().getLayoutParams();
            layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT;
            getMainView().setLayoutParams(layoutParams);
        } else {
            stockFiveDayChart.setLand(false);
        }
        stockFiveDayChart.setQuoteItem(quoteItem);
        stockFiveDayChart.setOnChartListener(new OnChartListener() {
            @Override
            public void onChangeLanded() {
                StockDetailLandActivity.startActivity(getActivity(),(QuoteItem) getArguments().getParcelable(QUOTEITEM),1);
            }
        });
    }
    @Override
    protected void initData() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.onPause();
    }

    /**
     * 请求行情成功
     *
     * @param quoteItem
     */
    @Override
    public void onRequestQuote(QuoteItem quoteItem) {

    }


    /**
     * 请求走势数据成功
     *
     * @param response
     */
    @Override
    public void onRequestChartSuccess(ChartResponse response) {
        if(null == response || response.historyItems.isEmpty()){
            return;
        }
        stockFiveDayChart.setData(response);
    }

    /**
     * 请求走势数据失败
     */
    @Override
    public void onRequestChartFail() {

    }

    @Override
    public void onRequestChartSubSuccess(ArrayList<MinuteEntity> minuteEntities) {

    }

    @Override
    public void onRequestChartSubFail(int i, String error) {

    }

    /**
     * 返回股票逐笔数据
     *
     * @param tickItemBos
     */
    @Override
    public void onTickItemsSuccess(ArrayList<TickItemBo> tickItemBos) {

    }

    /**
     * 返回买卖队列
     *
     * @param quoteResponse
     */
    @Override
    public void onOrderQuantitySuccess(OrderQuantityResponse quoteResponse) {

    }

    @Override
    public void update(QuoteResponse quoteResponse) {
        if(null == quoteResponse || null == presenter){
            return;
        }
        if( !quoteResponse.quoteItems.get(0).id.equals(quoteItem.id) ){
            presenter.init(quoteResponse.quoteItems.get(0), ChartType.FIVE_DAY, MSetting.getMSubType());
        }
        quoteItem = quoteResponse.quoteItems.get(0);
        updateOHLCItem(quoteItem);
    }

    private void valueSelected(OHLCItem ohlcItem, boolean last){
        if( null== ohlcItem)return;
        TextUtils.setText(tevTime,ohlcItem.time);
        TextUtils.setText(tevPrice, ohlcItem.closePrice);
        TextUtils.setText(tevAveragePrice,ohlcItem.averagePrice);
        if(last){
            /*String tradeVolume = quoteItem.nowVolume;
            char ch = tradeVolume.charAt(0);
            try{
                Integer.parseInt(new String(new char[]{ch}));
                TextUtils.setText(tevVolume,tradeVolume +"手",FillConfig.DEFALUT);
            } catch (Exception e){
                TextUtils.setText(tevVolume,tradeVolume ,FillConfig.DEFALUT);
            }*/
            TextUtils.setText(tevVolume, FormatUtils.isStandard(quoteItem.nowVolume) ? quoteItem.nowVolume+"手": FillConfig.DEFALUT);
        } else {
            String tradeVolume = FormatUtils.format(ohlcItem.tradeVolume).replace(".0","");
            TextUtils.setText(tevVolume,FormatUtils.isStandard(tradeVolume) ? tradeVolume:tradeVolume+"手");
        }
        TextUtils.setText(tevPrencet,FormatUtils.formatPercent(ohlcItem.changeRate));
        UpDownUtils.compare(quoteItem.openPrice,ohlcItem.closePrice,tevPrice,tevPrencet);
    }

    public void updateOHLCItem(QuoteItem quoteItem){
        if(null ==quoteItem ) return;
        TextUtils.setText(tevPrice,quoteItem.lastPrice);
        TextUtils.setText(tevAveragePrice,quoteItem.averageValue);
        TextUtils.setText(tevPrencet,FormatUtils.formatPercent(quoteItem.changeRate));
        TextUtils.setText(tevVolume, FormatUtils.isStandard(quoteItem.nowVolume) ? quoteItem.nowVolume+"手": FillConfig.DEFALUT);
        UpDownUtils.compare(quoteItem.preClosePrice,quoteItem.lastPrice,tevPrice,tevPrencet);
    }
}
