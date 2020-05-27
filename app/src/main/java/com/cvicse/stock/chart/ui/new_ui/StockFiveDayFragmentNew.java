package com.cvicse.stock.chart.ui.new_ui;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.cvicse.annotations.MVPInject;
import com.cvicse.stock.R;
import com.cvicse.stock.base.ChartBaseFragment;
import com.cvicse.stock.chart.data.OHLCItemBo;
import com.cvicse.stock.chart.presenter.contract.StockDayContractNew;
import com.cvicse.stock.common.FillConfig;
import com.cvicse.stock.markets.data.TickItemBo;
import com.cvicse.stock.markets.helper.QuoteCallback;
import com.cvicse.stock.markets.ui.new_ui.StockDetailLandActivityNew;
import com.cvicse.stock.util.FormatUtils;
import com.cvicse.stock.utils.TextUtils;
import com.cvicse.stock.utils.UpDownUtils;
import com.mitake.core.OHLCItem;
import com.mitake.core.QuoteItem;
import com.mitake.core.response.OrderQuantityResponse;
import com.mitake.core.response.QuoteResponse;
import com.mitake.core.util.FormatUtility;
import com.mitake.diagram.StockChartActivityPackage.DiagramChartPackage.EntityFiveDay;
import com.mitake.diagram.StockChartActivityPackage.DiagramChartPackage.FiveDay.StockChartFiveDayV2;
import com.mitake.utility.object.QueryLineCallBack;
import com.mitake.utility.object.UIDoubleTapCallBack;
import com.mitake.variable.object.CommonInfo;
import com.mitake.variable.utility.STKItemUtility;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * 新五日图
 * Created by liu_zlu on 2017/2/22 15:42
 */
public class StockFiveDayFragmentNew extends ChartBaseFragment implements StockDayContractNew.View, QuoteCallback, QueryLineCallBack, UIDoubleTapCallBack {

    private static final String QUOTEITEM = "QuoteItem";
    @BindView(R.id.tev_time)
    TextView tevTime;
    @BindView(R.id.tev_price)
    TextView tevPrice;
    @BindView(R.id.tev_prencet)
    TextView tevPrencet;
    @BindView(R.id.tev_average_price)
    TextView tevAveragePrice;
    @BindView(R.id.tev_volume)
    TextView tevVolume;
    @BindView(R.id.stock_fiveday_chart_new)
    FrameLayout stockFivedayChartNew;

    private StockChartFiveDayV2 fiveDay;
    private FragmentManager fragmentManager;
    private QuoteResponse quoteResponse;

    @MVPInject
    StockDayContractNew.Presenter presenter;

    private QuoteItem quoteItem;

    public static StockFiveDayFragmentNew newInstance(QuoteItem quoteItem) {
        StockFiveDayFragmentNew fiveDayFragment = new StockFiveDayFragmentNew();
        Bundle bundle = new Bundle();
        bundle.putParcelable(QUOTEITEM, quoteItem);
        fiveDayFragment.setArguments(bundle);
        return fiveDayFragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_stock_fiveday_chart_new;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        quoteItem = bundle.getParcelable(QUOTEITEM);
        fragmentManager = getChildFragmentManager();
        CommonInfo.refreshSecond = 5;  // new
        initView();

        presenter.init(quoteItem);

        //高亮监听
        /*  // old
        stockFiveDayChart.setOnChartSelectedListener(new OnMinuteSelectedListener() {
            @Override
            public void onValueSelected(int position, OHLCItem ohlcItem,boolean last) {
                valueSelected(ohlcItem,last);
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
        */
        if (isLand) {
//            stockFiveDayChart.setLand(true);
            ViewGroup.LayoutParams layoutParams = getMainView().getLayoutParams();
            layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT;
            getMainView().setLayoutParams(layoutParams);
        } else {
            fiveDay.setTapCallBack(this);
        }
    }

    private void initView() {
        EntityFiveDay entity = new EntityFiveDay();
        entity.setHasPecentCode(true); // 设定是否添加右侧百分比+-号
        entity.setCouldDoubleTap(false); // 设定是否开启双击切换横竖屏
//        entity.setyFontSize(26);         // 设定线图Y轴字型大小, 包含左边股票价位、成交量及右边幅度
//        entity.setxFontSize(26);          // 设定线图X轴字型大小;线图最下方日期/时间
//        entity.setQueryLineFontSize(26);    // 设定查价框字型大小

        fiveDay = new StockChartFiveDayV2();
        fiveDay.setStockCode(quoteItem.id);
        fiveDay.setQueryLineCallBackListener(this);  // 查价线事件
        fiveDay.setStyle(entity);
//        fiveDay.setTapCallBack(this);   // 自定义双击事件

        Bundle config = new Bundle();
        config.putInt("position", 0);
        fiveDay.setArguments(config);

        fragmentManager.beginTransaction().replace(R.id.stock_fiveday_chart_new, fiveDay, "HomeTab_Portfolio").addToBackStack("HomeTab_Portfolio").commit();
    }

    @Override
    protected void initData() {
        if( null != quoteResponse ){
            update( quoteResponse);
        }
    }

    /**
     * 请求行情成功
     *
     * @param quoteItem
     */
    @Override
    public void onRequestQuote(QuoteItem quoteItem) {
        fiveDay.setSTKItem(quoteItem);
        fiveDay.refreshData();
        updateOHLCItem2(quoteItem);
    }

    /**
     * 请求走势数据成功(不需要)
     *
     * @param ohlcItems
     */
    @Override
    public void onRequestChartSuccess(ArrayList<OHLCItemBo> ohlcItems) {
        if (quoteItem != null) {
//            stockFiveDayChart.setMSubData(ohlcItems);
        }
    }

    /**
     * 请求走势数据失败
     */
    @Override
    public void onRequestChartFail() {

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

    /**
     * 实时刷新
     *
     * @param quoteResponse
     */
    @Override
    public void update(QuoteResponse quoteResponse) {
        if (presenter == null) {
            return;
        }
        /* // 原来的代码
        presenter.init(quoteResponse.quoteItems.get(0),ChartType.FIVE_DAY);
        QuoteItem quoteItem = quoteResponse.quoteItems.get(0);
        updateOHLCItem(quoteItem);
        */
        // 新代码
        this.quoteResponse = quoteResponse;
        QuoteItem quoteItem = quoteResponse.quoteItems.get(0);
        onRequestQuote(quoteItem);
    }

    private void updateOHLCItem(OHLCItem ohlcItem, boolean last) {
        if (ohlcItem == null) return;
        TextUtils.setText(tevTime, ohlcItem.time, "--");
        TextUtils.setText(tevPrice, FormatUtils.formatPrice(ohlcItem.closePrice, quoteItem.market, quoteItem.subtype), "--");
        TextUtils.setText(tevAveragePrice, FormatUtils.formatPrice(ohlcItem.averagePrice, quoteItem.market, quoteItem.subtype), "--");
        if (last) {
            String tradeVolume = FormatUtility.formatVolume(quoteItem.nowVolume, quoteItem.market, quoteItem.subtype);
            char ch = tradeVolume.charAt(0);
            try {
                Integer.parseInt(new String(new char[]{ch}));
                TextUtils.setText(tevVolume, tradeVolume + "手", "--");
            } catch (Exception e) {
                TextUtils.setText(tevVolume, tradeVolume, "--");
            }
        } else {
            String tradeVolume = FormatUtils.format(ohlcItem.tradeVolume).replace(".0", "");
            TextUtils.setText(tevVolume, tradeVolume.equals("--") ? tradeVolume : tradeVolume + "手", "--");
        }
        TextUtils.setText(tevPrencet, FormatUtils.formatPercent(ohlcItem.changeRate), FillConfig.DOUBLE_LINE);
        UpDownUtils.compare(quoteItem.openPrice, ohlcItem.closePrice, tevPrice, tevPrencet);
    }

    public void updateOHLCItem2(QuoteItem quoteItem) {
        if (quoteItem == null) return;
        String time = quoteItem.datetime;
        time = FormatUtils.formatDate(FormatUtils.parseDate(time,"yyyyMMddHHmmss"),"HH:mm");
        TextUtils.setText(tevTime, time, FillConfig.DOUBLE_LINE);

        String tradeVolume = FormatUtils.format(quoteItem.nowVolume).replace(".0", "");
        TextUtils.setText(tevVolume, FillConfig.DOUBLE_LINE.equals(tradeVolume) ? tradeVolume : tradeVolume + "手", FillConfig.DOUBLE_LINE);
        TextUtils.setText(tevAveragePrice,quoteItem.averageValue);

        TextUtils.setText(tevPrice, quoteItem.lastPrice);
        TextUtils.setText(tevPrencet, FormatUtils.formatPercent(quoteItem.changeRate));
        UpDownUtils.compare(quoteItem.preClosePrice, quoteItem.lastPrice, tevPrice, tevPrencet);
    }

    /**
     * 查价线接口回调
     */
//    @Override
  /*  public void callback(String time, boolean isQuery, String qPrice, String qVolume, String qchange, String qchangeRate, String qchangeHand, String newStr) {
        // TODO: 2017/8/25  更新TextView 时间、价(颜色)、幅(颜色)、均、量
        if( !isQuery ){
            return;
        }

        time = FormatUtils.formatDate(FormatUtils.parseDate(time,"yyyyMMddHHmm"),"HH:mm");
        TextUtils.setText(tevTime, time, "--");
        tevPrice.setTextColor(STKItemUtility.formatColor(quoteItem.preClosePrice, qPrice));
        tevPrencet.setTextColor(STKItemUtility.formatColor(quoteItem.preClosePrice, qPrice));
        TextUtils.setText( tevPrice,qPrice,"--");
        TextUtils.setText(tevPrencet, FormatUtils.formatPercent(qchangeRate), "--");
        TextUtils.setText(tevAveragePrice,"--");  // 没有对应的字段
        String tradeVolume = FormatUtils.getVol(qVolume).replace(".0", "");
        TextUtils.setText(tevVolume, tradeVolume.equals("--") ? tradeVolume : tradeVolume + "手", "--");
    }
*/
    /**
     * 自定义双击事件（横屏状态）
     * @param motionEvent
     */
    @Override
    public void callback(MotionEvent motionEvent) {
        StockDetailLandActivityNew.startActivity(getActivity(),quoteItem,1);
    }

    /**
     * 查价线接口回调
     */
    @Override
    public void callback(String time, boolean isQuery, String qq,String qPrice, String qVolume, String qchange,
                         String qchangeRate, String qchangeHand, String newStr) {
        // TODO: 2017/8/25  更新TextView 时间、价(颜色)、幅(颜色)、均、量
        if( !isQuery ){
            return;
        }

        time = FormatUtils.formatDate(FormatUtils.parseDate(time,"yyyyMMddHHmm"),"HH:mm");
        tevPrice.setTextColor(STKItemUtility.formatColor(quoteItem.preClosePrice, qPrice));
        TextUtils.setText(tevTime, time, FillConfig.DOUBLE_LINE);
        tevPrencet.setTextColor(STKItemUtility.formatColor(quoteItem.preClosePrice, qPrice));
        TextUtils.setText( tevPrice,qPrice,FillConfig.DOUBLE_LINE);
        TextUtils.setText(tevPrencet, FormatUtils.formatPercent(qchangeRate), FillConfig.DOUBLE_LINE);
        TextUtils.setText(tevAveragePrice,FillConfig.DOUBLE_LINE);  // 没有对应的字段
        String tradeVolume = FormatUtils.format(qVolume).replace(".0", "");
        TextUtils.setText(tevVolume, FillConfig.DOUBLE_LINE.equals(tradeVolume) ? tradeVolume : tradeVolume + "手", FillConfig.DOUBLE_LINE);
    }
}
