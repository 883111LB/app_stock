package com.cvicse.stock.chart.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.cvicse.annotations.MVPInject;
import com.cvicse.stock.R;
import com.cvicse.stock.base.ChartBaseFragment;
import com.cvicse.stock.chart.data.KLineEntity;
import com.cvicse.stock.chart.data.MKLineEntity;
import com.cvicse.stock.chart.data.TechChartType;
import com.cvicse.stock.chart.helper.TopKDialogHlper;
import com.cvicse.stock.chart.listener.OnChartListener;
import com.cvicse.stock.chart.listener.OnKlineSelectedListener;
import com.cvicse.stock.chart.listener.OnMKSelectedListener;
import com.cvicse.stock.chart.presenter.contract.StockKlineContract;
import com.cvicse.stock.chart.theme.ThemeManager;
import com.cvicse.stock.chart.utils.StockTechType;
import com.cvicse.stock.chart.view.KChoseView;
import com.cvicse.stock.markets.ui.StockDetailLandActivity;
import com.cvicse.stock.utils.TextUtils;
import com.mitake.core.OHLCItem;
import com.mitake.core.QuoteItem;
import com.mitake.core.parser.FQItem;
import com.mitake.core.parser.GBItem;
import com.mitake.core.util.FormatUtility;
import com.stock.config.KSetting;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

import butterknife.BindView;

/**
 * k线图
 * Created by liu_zlu on 2017/2/27 09:34
 */
public class StockKlineFragment extends ChartBaseFragment implements StockKlineContract.View{

    private static final String QUOTEITEM = "QuoteItem";
    private static final String KLINE_TYPE = "kline_type";

    @BindView(R.id.tev_ma_1) TextView tevMa_1;
    @BindView(R.id.tev_ma_2) TextView tevMa_2;
    @BindView(R.id.tev_ma_3) TextView tevMa_3;

    @BindView(R.id.btn_setting) Button btnSetting;//设置按钮

    //k线图控件
    @BindView(R.id.stc_kline) StockTechChart stcKline;

    //横屏状态下右侧的选项
    @BindView(R.id.lel_chose) KChoseView lelChose;

    /**
     * 均线设置弹出框
     */
    private TopKDialogHlper topKDialogHlper;

    @MVPInject
    StockKlineContract.Presenter presenter;
    public static StockKlineFragment newInstance(QuoteItem quoteItem, String ktype) {
        StockKlineFragment stockKlineFragment = new StockKlineFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(QUOTEITEM, quoteItem);
        bundle.putString(KLINE_TYPE, ktype);
        stockKlineFragment.setArguments(bundle);
        return stockKlineFragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_stockkline_chart;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        tevMa_1.setTextColor(ThemeManager.colorGreen());
        tevMa_2.setTextColor(Color.GRAY);
        tevMa_3.setTextColor(ThemeManager.colorYellow());

        String type = getArguments().getString(KLINE_TYPE);

        //初始化
        presenter.init((QuoteItem) getArguments().getParcelable(QUOTEITEM),type);

        if (isLand) {
            btnSetting.setVisibility(View.GONE);
            stcKline.setLand(true);
            ViewGroup.LayoutParams layoutParams = getMainView().getLayoutParams();
            layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT;
            getMainView().setLayoutParams(layoutParams);
        } else {
            lelChose.setVisibility(View.GONE);
            stcKline.setLand(false);
        }

        topKDialogHlper = new TopKDialogHlper(this,type);
        stcKline.setType(StockTechType.asType(type));
        initListener(type);
    }

    /**
     * 初始化事件监听
     * @param type
     */
    private void initListener(final String type) {
        //横屏切换监听
        stcKline.setChangeLandListener(new OnChartListener() {
            @Override
            public void onChangeLanded() {
                StockDetailLandActivity.startActivity(getActivity(),(QuoteItem) getArguments().getParcelable(QUOTEITEM), StockTechType.asType(type).ordinal()+2);
            }
        });

        // 高亮监听
        stcKline.setOnKlineSelectedListener(new OnKlineSelectedListener() {
            @Override
            public void onValueSelected(int position, KLineEntity kLineEntity) {
                if( 0 == position){
                    lelChose.updateView(false);
                }
                if( 0 != position ){
                    updateKLineEntity(kLineEntity,stcKline.getKLinEntities().get(position-1).closeS);
                }else{
                    updateKLineEntity(kLineEntity,null);
                }
            }

            @Override
            public void onLoadMoreData(boolean isOld) {
                // 加载更多的数据
//                presenter.loadMoreData(isOld);
            }
        });

        btnSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                topKDialogHlper.show();
            }
        });

        lelChose.setChoseListener(new KChoseView.ChoseListener() {
            @Override
            public void onChanged(TechChartType.KType kType, int subscriptionType) {
                if(subscriptionType == -1){
                    stcKline.setKType(kType);
                } else {
                    presenter.requestOHLC();
                }
            }
        });
    }

    /**设置MA值
     */
    private void updateKLineEntity(KLineEntity kLineEntity,String lastClose) {
        if( null==kLineEntity ){
            return;
        }
        QuoteItem quote =  getArguments().getParcelable(QUOTEITEM);

        if(KSetting.getAverage_Visiable1()){
            tevMa_1.setVisibility(View.VISIBLE);
        } else {
            tevMa_1.setVisibility(View.GONE);
        }
        if(KSetting.getAverage_Visiable2()){
            tevMa_2.setVisibility(View.VISIBLE);
        } else {
            tevMa_2.setVisibility(View.GONE);
        }
        if(KSetting.getAverage_Visiable3()){
            tevMa_3.setVisibility(View.VISIBLE);
        } else {
            tevMa_3.setVisibility(View.GONE);
        }
        TextUtils.setText(tevMa_1,"MA "+KSetting.getAverage_1()+":"+
                FormatUtility.formatVolumeK(kLineEntity.MA1+"",quote.market,quote.subtype));
        TextUtils.setText(tevMa_2,"MA "+KSetting.getAverage_2()+":"+
                FormatUtility.formatVolumeK(kLineEntity.MA2+"",quote.market,quote.subtype));
        TextUtils.setText(tevMa_3,"MA "+KSetting.getAverage_3()+":"+
                FormatUtility.formatVolumeK(kLineEntity.MA3+"",quote.market,quote.subtype));

        MKLineEntity mkLineEntity = new MKLineEntity();
        mkLineEntity.isUp = kLineEntity.isUp;
        mkLineEntity.datetime = kLineEntity.datetime;
        mkLineEntity.date = kLineEntity.date;
        mkLineEntity.openPrice = kLineEntity.open;
        mkLineEntity.highPrice = kLineEntity.high;
        mkLineEntity.lowPrice = kLineEntity.low;
        mkLineEntity.closePrice = kLineEntity.close;
        mkLineEntity.tradeVolume = kLineEntity.volume;
        mkLineEntity.tranPrice = kLineEntity.tranPrice;

        mkLineEntity.openPriceS = kLineEntity.openS;
        mkLineEntity.highPriceS = kLineEntity.highS;
        mkLineEntity.lowPriceS = kLineEntity.lowS;
        mkLineEntity.closePriceS = kLineEntity.closeS;
        mkLineEntity.tradeVolumeStr = kLineEntity.volumeStr;
        mkLineEntity.tranPriceS = kLineEntity.tranPriceS;
        mkLineEntity.changeS = kLineEntity.change;
        mkLineEntity.changeRateS = kLineEntity.changeRate;

        if( null != onMKSelectedListener ){
            onMKSelectedListener.onValueSelected(mkLineEntity,lastClose);
        }
    }

    @Override
    protected void initData() {
        //presenter.requestOHLC();
//        presenter.requestOHLCSub();
    }

    public void updateSetting(boolean right){
        if(right){    //复权是重新请求
            presenter.requestOHLC();
        }else{  //均线是更新页面
            stcKline.updateTopSetting();
        }
    }

    /**
     * 请求走势数据成功
     *  @param ohlcItems
     * @param quoteItem
     */
    @Override
    public void onRequestOHLCSuccess(CopyOnWriteArrayList<OHLCItem> ohlcItems,
                                     QuoteItem quoteItem, ArrayList<GBItem> gbItems) {
        if( null == ohlcItems || ohlcItems.isEmpty() ){
            return;
        }
        stcKline.setData(ohlcItems,quoteItem, gbItems);
    }

    /**
     * 请求走势数据失败
     */
    @Override
    public void onRequestChartFail() {

    }

    /**
     * 请求复权接口成功
     * @param ohlcSubRsList
     */
    @Override
    public void onRequestOHLCSubSuccess(CopyOnWriteArrayList<FQItem> ohlcSubRsList) {
        if( null == ohlcSubRsList || ohlcSubRsList.isEmpty() ){
            return;
        }
        stcKline.setOHLCSubData(ohlcSubRsList);
    }

    @Override
    public void onRequestOHLCSubFail() {

    }

    private OnMKSelectedListener onMKSelectedListener;

    public void setOnMKSelectedListener(OnMKSelectedListener onMKSelectedListener){
        this.onMKSelectedListener = onMKSelectedListener;
    }
}
