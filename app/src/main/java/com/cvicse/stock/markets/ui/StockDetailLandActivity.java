package com.cvicse.stock.markets.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.cvicse.annotations.MVPInject;
import com.cvicse.stock.R;
import com.cvicse.stock.base.PBaseActivity;
import com.cvicse.stock.chart.data.MKLineEntity;
import com.cvicse.stock.common.ColorUtils;
import com.cvicse.stock.common.Setting.Setting;
import com.cvicse.stock.markets.helper.MKSelectedListener;
import com.cvicse.stock.markets.helper.StockChartHelper;
import com.cvicse.stock.markets.presenter.contract.StockDetailLandContract;
import com.cvicse.stock.util.FormatUtils;
import com.cvicse.stock.utils.TextUtils;
import com.cvicse.stock.utils.UpDownUtils;
import com.cvicse.stock.view.AutofitTextView;
import com.mitake.core.QuoteItem;
import com.mitake.core.response.QuoteResponse;
import com.mitake.core.util.FormatUtility;

import butterknife.BindView;

/**
 * 股票详细横屏
 * Created by liu_zlu on 2017/2/22 15:08
 */
public class StockDetailLandActivity extends PBaseActivity implements StockDetailLandContract.View,MKSelectedListener {

    private static final String QUOTEITEM = "quoteitem";
    private static final String ITEM_SELECT = "typeitemselect";
    /**
     * 股票名
     */
    @BindView(R.id.tev_stockName) AutofitTextView tevStockName;
    /**
     * 股票id编码
     */
    @BindView(R.id.tev_stockCode) TextView tevStockCode;
    /**
     * 最新价
     */
    @BindView(R.id.tev_lastprice) AutofitTextView tevLastprice;
    /**
     * 涨跌
     */
    @BindView(R.id.tev_change) TextView tevChange;
    /**
     * 涨跌比率
     */
    @BindView(R.id.tev_changeRate) TextView tevChangeRate;
    /**
     * 最高价
     */
    @BindView(R.id.tev_highPrice) TextView tevHighPrice;
    /**
     * 最低价
     */
    @BindView(R.id.tev_lowPrice) TextView tevLowPrice;
    /**
     * 开盘价
     */
    @BindView(R.id.tev_openPrice) TextView tevOpenPrice;
    /**
     * 昨天收盘价
     */
    @BindView(R.id.tev_preClosePrice) TextView tevPreClosePrice;
    /**
     * 总量
     */
    @BindView(R.id.tev_volume) TextView tevVolume;
    /**
     * 成交金额
     */
    @BindView(R.id.tev_amount) TextView tevAmount;
    /**
     * 换手率
     */
    @BindView(R.id.tev_turnoverRate) TextView tevTurnoverRate;
    /**
     * 量比
     */
    @BindView(R.id.tev_volume_ratio) TextView tevVolumeRatio;
    /**
     * 退出按钮
     */
    @BindView(R.id.img_back_button) ImageView imgBackButton;

    private StockChartHelper stockChartHelper;

    private static QuoteItem quoteItem;

    @MVPInject
    StockDetailLandContract.Presenter presenter;

    /**
     * 启动股票详细横屏页面
     * @param activity
     * @param quoteItem
     * @param item
     */
    public static void startActivity(Activity activity, QuoteItem quoteItem,int item) {
        Intent intent = new Intent(activity, StockDetailLandActivity.class);
        StockDetailLandActivity.quoteItem = quoteItem;
        intent.putExtra(ITEM_SELECT,item);
        activity.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        //取消状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        return R.layout.activity_stock_day;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        QuoteItem quoteItem = StockDetailLandActivity.quoteItem;
        presenter.init(quoteItem);

        stockChartHelper = new StockChartHelper(true,getIntent().getIntExtra(ITEM_SELECT,0),
                this.findViewById(R.id.lel_main),quoteItem, getSupportFragmentManager());

        stockChartHelper.setSelected(getIntent().getIntExtra(ITEM_SELECT,0));

        stockChartHelper.setMKLineSelectedLinseter(this);

        imgBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onQuoteSuccess(QuoteResponse response) {
        if(null== response.quoteItems  || response.quoteItems.size() == 0){
            return;
        }
        quoteItem = response.quoteItems.get(0);
        if( null!=quoteItem ){
            TextUtils.setText(tevStockName,quoteItem.name);
            TextUtils.setText(tevStockCode,quoteItem.id);
            TextUtils.setText(tevLastprice,quoteItem.lastPrice);
            TextUtils.setText(tevChange,quoteItem.change);
            TextUtils.setText(tevChangeRate,quoteItem.changeRate);
            TextUtils.setText(tevHighPrice,quoteItem.highPrice);
            TextUtils.setText(tevLowPrice,quoteItem.lowPrice);
            TextUtils.setText(tevOpenPrice,quoteItem.openPrice);
            TextUtils.setText(tevPreClosePrice,quoteItem.preClosePrice);

            TextUtils.setText(tevVolume, FormatUtility.formatVolume(quoteItem.volume,quoteItem.market,quoteItem.subtype));
            TextUtils.setText(tevAmount, FormatUtility.formatVolume(quoteItem.amount,quoteItem.market,quoteItem.subtype));  // new

            TextUtils.setText(tevTurnoverRate,FormatUtils.formatPercent(quoteItem.turnoverRate));

            TextUtils.setText(tevVolumeRatio,quoteItem.volumeRatio);

            if(null!=quoteItem.upDownFlag   &&  null!= quoteItem.changeRate){
                TextUtils.setText(tevChange,quoteItem.change);
                if("=".equals(quoteItem.upDownFlag)){
                    TextUtils.setText(tevChangeRate,quoteItem.changeRate+"%");
                    if(Setting.isNight()){
                        tevLastprice.setTextColor(ColorUtils.WHITE);
                        tevChange.setTextColor(ColorUtils.WHITE);
                        tevChangeRate.setTextColor(ColorUtils.WHITE);
                        tevHighPrice.setTextColor(ColorUtils.WHITE);
                        tevOpenPrice.setTextColor(ColorUtils.WHITE);
                        tevLowPrice.setTextColor(ColorUtils.WHITE);

                    }else{
                        tevLastprice.setTextColor(ColorUtils.BLACK);
                        tevChange.setTextColor(ColorUtils.BLACK);
                        tevChangeRate.setTextColor(ColorUtils.BLACK);
                        tevHighPrice.setTextColor(ColorUtils.BLACK);
                        tevOpenPrice.setTextColor(ColorUtils.BLACK);
                        tevLowPrice.setTextColor(ColorUtils.BLACK);
                    }

                }else{
                    TextUtils.setText(tevChangeRate,quoteItem.upDownFlag+quoteItem.changeRate+"%");

                    int color = 0;
                    if("-".equals(quoteItem.upDownFlag)){
                        color = ColorUtils.DOWN;
                    } else {
                        color = ColorUtils.UP;
                    }
                    tevLastprice.setTextColor(color);
                    tevChange.setTextColor(color);
                    tevChangeRate.setTextColor(color);
                    tevHighPrice.setTextColor(color);
                    tevOpenPrice.setTextColor(color);
                    tevLowPrice.setTextColor(color);
                }
            }
            stockChartHelper.setQuoteDetail(response);
        }
    }

    /**
     * 滑动时，顶图也发生改变
     * @param mkLineEntity
     */
    @Override
    public void onValueSelected(MKLineEntity mkLineEntity,String lastClose) {
        if( null == mkLineEntity ){
            return;
        }
        TextUtils.setText(tevLastprice,mkLineEntity.closePriceS);
        TextUtils.setText(tevHighPrice,mkLineEntity.highPriceS);
        TextUtils.setText(tevLowPrice,mkLineEntity.lowPriceS);
        TextUtils.setText(tevOpenPrice,mkLineEntity.openPriceS);
        TextUtils.setText(tevPreClosePrice,mkLineEntity.closePriceS);
        TextUtils.setText(tevVolume,mkLineEntity.tradeVolumeStr);
        TextUtils.setText(tevAmount, mkLineEntity.tranPriceS);
        TextUtils.setText(tevChange,mkLineEntity.changeS);
        TextUtils.setText(tevChangeRate,FormatUtils.formatPercent(mkLineEntity.changeRateS));

        UpDownUtils.compare(lastClose, mkLineEntity.highPriceS, tevHighPrice);
        UpDownUtils.compare(lastClose, mkLineEntity.openPriceS, tevOpenPrice);
        UpDownUtils.compare(lastClose, mkLineEntity.lowPriceS, tevLowPrice);
        UpDownUtils.compare(lastClose, mkLineEntity.closePriceS, tevChange,tevChangeRate,tevLastprice);
    }
}
