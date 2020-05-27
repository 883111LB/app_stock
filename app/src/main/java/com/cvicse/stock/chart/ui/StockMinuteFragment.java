package com.cvicse.stock.chart.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cvicse.annotations.MVPInject;
import com.cvicse.base.utils.NumberUtils;
import com.cvicse.base.utils.SizeUtils;
import com.cvicse.base.utils.ToastUtils;
import com.cvicse.stock.R;
import com.cvicse.stock.base.ChartBaseFragment;
import com.cvicse.stock.chart.adapter.ChartTickAdapter;
import com.cvicse.stock.chart.data.MinuteEntity;
import com.cvicse.stock.chart.helper.MinuteHKHlper;
import com.cvicse.stock.chart.helper.MinuteSZHlper;
import com.cvicse.stock.chart.listener.OnChartListener;
import com.cvicse.stock.chart.listener.OnMinuteSelectedListener;
import com.cvicse.stock.chart.presenter.contract.StockDayContract;
import com.cvicse.stock.chart.theme.ThemeManager;
import com.cvicse.stock.chart.ui.new_ui.StockMinuteChartNew;
import com.cvicse.stock.chart.ui.setting.BidSetting;
import com.cvicse.stock.chart.utils.BidChartDataUtils;
import com.cvicse.stock.chart.view.BestFiveTenView;
import com.cvicse.stock.common.FillConfig;
import com.cvicse.stock.common.Setting.BidChartSetting;
import com.cvicse.stock.common.Setting.Setting;
import com.cvicse.stock.markets.data.TickItemBo;
import com.cvicse.stock.markets.helper.QuoteCallback;
import com.cvicse.stock.markets.ui.StockDetailLandActivity;
import com.cvicse.stock.markets.ui.StockMoreDetailActivity;
import com.cvicse.stock.util.FormatUtils;
import com.cvicse.stock.utils.StockType;
import com.cvicse.stock.utils.TextUtils;
import com.cvicse.stock.utils.UpDownUtils;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.mitake.core.AppInfo;
import com.mitake.core.MarketType;
import com.mitake.core.OHLCItem;
import com.mitake.core.QuoteItem;
import com.mitake.core.bean.log.ErrorInfo;
import com.mitake.core.request.ChartType;
import com.mitake.core.request.chart.BidChartRequest;
import com.mitake.core.response.ChartResponse;
import com.mitake.core.response.IResponseInfoCallback;
import com.mitake.core.response.OrderQuantityResponse;
import com.mitake.core.response.QuoteResponse;
import com.mitake.core.response.chart.BidChartResponse;
import com.mitake.core.response.chart.BidItem;
import com.stock.config.MSetting;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import butterknife.BindView;

import static android.graphics.Color.GREEN;
import static android.graphics.Color.RED;
import static android.graphics.Color.TRANSPARENT;


/**
 * 分时图
 * Created by liu_zlu on 2017/2/22 15:42
 */
public class StockMinuteFragment extends ChartBaseFragment implements StockDayContract.View, QuoteCallback {

    private static final String QUOTEITEM = "QuoteItem";

    @MVPInject
    StockDayContract.Presenter presenter;

    @BindView(R.id.tev_time) TextView tevTime;  // 时间标示

    @BindView(R.id.tev_price) TextView tevPrice;    // 价

    @BindView(R.id.tev_prencet) TextView tevPrencet;   // 幅

    @BindView(R.id.tev_average_price) TextView tevAveragePrice;   // 均

    @BindView(R.id.tev_volume) TextView tevVolume;   // 量

    @BindView(R.id.rel_right) RelativeLayout relRight;
    @BindView(R.id.rel_bg) RelativeLayout rel_bg;// 总布局控件

    @BindView(R.id.stock_five_tick_radio) RadioGroup stockFiveTickRadio; // 总RadioGroup

    @BindView(R.id.rdb_five) RadioButton radbFive;  // 五档十档按钮

    @BindView(R.id.rdb_tick) RadioButton radTick;    // 明细按钮

    @BindView(R.id.bfv_five) BestFiveTenView bfvFive;    // 五档十档控件

    @BindView(R.id.stock_minute_chart) StockMinuteChartNew stockMinuteChart;  // 分时图控件

    @BindView(R.id.stock_bid_chart) LineChart bidChart;// 集合竞价折线图
    @BindView(R.id.lnl_bid) LinearLayout lnl_bid;// 集合竞价父控件
    @BindView(R.id.img_setting) ImageView img_setting;// 集合竞价设置按钮
    BarChart bidBarchart;  // 集合竞价副图


    @BindView(R.id.rel_detail) RelativeLayout relDetail;   // 明细总布局，默认是隐藏的

    @BindView(R.id.tev_detail_more) TextView tevDetailMore;    // 更多成交

    // 更多成交上面的布局,即明细的数据
    @BindView(R.id.lel_chart_detail) LinearLayout lelChartDetail;

    @BindView(R.id.ipov) TextView ipov;
    @BindView(R.id.tev_ipov) TextView tevIpov;    // 基金净值
    @BindView(R.id.discount) TextView discount;
    @BindView(R.id.tev_discount) TextView tevDiscount;   // 溢价率

    /**
     * 分时图下面的布局,存放委托队列数据
     * 港股为经纪买盘，经纪卖盘
     * 沪深为 买卖队列数据
     */
    @BindView(R.id.lel_more)
    LinearLayout lelMore;

    // 股票逐笔数据的adapter
    private ChartTickAdapter chartTickAdapter;

    // 分时图下面的数据帮助类,level 2 状态下才有的
    private MinuteSZHlper minuteSZHlper;

    private MinuteHKHlper minuteHKHlper;

    private QuoteItem quoteItem;

    private QuoteResponse quoteResponse;

    private ArrayList<TickItemBo> tickItemBos = new ArrayList<>();

    private static StockMinuteFragment sMinuteFragment = null;

    public static StockMinuteFragment newInstance(QuoteItem quoteItem) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(QUOTEITEM, quoteItem);
        sMinuteFragment = new StockMinuteFragment();
        sMinuteFragment.setArguments(bundle);
        return sMinuteFragment;
}

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_stockday_chart;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        quoteItem = getArguments().getParcelable(QUOTEITEM);

        if(null != quoteItem.st && quoteItem.st.contains("1120")){
            ipov.setVisibility(View.VISIBLE);
            discount.setVisibility(View.VISIBLE);
            tevIpov.setVisibility(View.VISIBLE);
            tevDiscount.setVisibility(View.VISIBLE);
        }

        presenter.init(quoteItem, ChartType.ONE_DAY, MSetting.getMSubType());
        stockMinuteChart.setQuoteItem(quoteItem);
        chartTickAdapter = new ChartTickAdapter(getActivity(), quoteItem);
        StockType.Type type = StockType.getType(quoteItem);
        // 横屏状态
        if (isLand) {
            stockMinuteChart.setLand(true);
            ViewGroup.LayoutParams layoutParams = getMainView().getLayoutParams();
            layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT;
            getMainView().setLayoutParams(layoutParams);
            if (Setting.isLevel1()) {
                bfvFive.setFive();
            } else {
                bfvFive.setTen();
                bfvFive.setLand(true);//只对十档时有效
            }
        } else {
            //非横屏状态
            stockMinuteChart.setLand(false);
            if (Setting.isLevel2()) {//如果当前用户的状态为Level2
//                if (!type.isIndex()) { //指数
                    ViewGroup.LayoutParams layoutParams = getMainView().getLayoutParams();
                    layoutParams.height = SizeUtils.dp2px(getActivity(), 400);
                    getMainView().setLayoutParams(layoutParams);
                    bfvFive.setTen();
                    if (type.isSZ() || type.isSH()) {
                        lelMore.setVisibility(View.VISIBLE);
                        minuteSZHlper = new MinuteSZHlper(lelMore, this, quoteItem);
                    }
                    if (type.isHongKong()) {
                        lelMore.setVisibility(View.VISIBLE);
                        minuteHKHlper = new MinuteHKHlper(lelMore, this);
                    }
//                }
            }
        }
        if (type!=null){
            if (type.isIndex()) {   //指数
//            radbFive.setVisibility(View.GONE);
//            bfvFive.setVisibility(View.GONE);
                tevDetailMore.setVisibility(View.GONE);
                // relRight.setVisibility(View.GONE);
            }
        }

        if (Setting.isLevel2()) {
            radbFive.setText("十档");
        }
        if (!(MarketType.SH.equals(quoteItem.market) || MarketType.SZ.equals(quoteItem.market)) ||
                "000001.sh".equals(quoteItem.id) || "399001.sz".equals(quoteItem.id) || "399006.sz".equals(quoteItem.id)) {
            // 如果不是深沪市场，隐藏集合竞价
            lnl_bid.setVisibility(View.GONE);// 隐藏图表
            img_setting.setVisibility(View.GONE);// 隐藏
        } else {
            initBit();// 初始化集合竞价相关
        }
        initListener();
        stockMinuteChart.getTopChart().setmMinLeftOffset(10f);
        stockMinuteChart.getBottomChart().setmMinLeftOffset(10f);
    }

    /**
     * 初始化集合竞价相关
     */
    private void initBit() {
        // 调整中间空行的高度
        ViewGroup para1 = lnl_bid;
        RelativeLayout lelCenter= new RelativeLayout(para1.getContext());
        LayoutInflater.from(para1.getContext()).inflate(com.android.haitong.R.layout.layout_chart_mline_detail,lelCenter);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, SizeUtils.dp2px(para1.getContext(),18));
        lelCenter.setLayoutParams(layoutParams);
        para1.addView(lelCenter);
        // 集合竞价副图
        LinearLayout.LayoutParams param2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,0);
        param2.weight = 1;
        bidBarchart = new BarChart(para1.getContext());
        bidBarchart.setLayoutParams(param2);
        bidBarchart.setId(com.android.haitong.R.id.bottom_chart);
        para1.addView(bidBarchart);

        // 初始化集合竞价上方图表
        initTopBitChart();
        // 集合竞价副图
        initTopBitBarChart();
    }
    /**
     * 初始化点击事件
     */
    private void initListener() {
        //股票长按事件
        stockMinuteChart.setOnChartSelectedListener(new OnMinuteSelectedListener() {
            @Override
            public void onValueSelected(int position, OHLCItem ohlcItem, boolean last) {
                valueSelected(ohlcItem);
            }

            @Override
            public void onValueSelected(int position, MinuteEntity mLineEntity) {

            }
        });

        // 横竖屏、指标监听事件
        stockMinuteChart.setOnChartListener(new OnChartListener() {
            @Override
            public void onChangeLanded() {
                StockDetailLandActivity.startActivity(getActivity(), quoteItem, 0);
            }

            @Override
            public void onKTypeClick() {
                // 沪深港市场显示量比
                if (MSetting.getMSubType() < 9) {
                    // 走势副图指标改变，重新请求
                    presenter.requestChartSub(MSetting.getMSubType());
                } else if (MarketType.SH.equals(quoteItem.market) || MarketType.SZ.equals(quoteItem.market)
                            || MarketType.HK.equals(quoteItem.market)) {
                    // 走势副图指标改变，重新请求
                    presenter.requestChartSub(MSetting.getMSubType());
                }

            }
        });

        //RadioGroup的点击事件
        stockFiveTickRadio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rdb_five:
                        bfvFive.setVisibility(View.VISIBLE);
                        relDetail.setVisibility(View.GONE);
                        break;
                    case R.id.rdb_tick:
                        bfvFive.setVisibility(View.GONE);
                        relDetail.setVisibility(View.VISIBLE);
                        cacuTickItem();
                        break;
                }
            }
        });

        // 更多成交点击事件
        tevDetailMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StockMoreDetailActivity.startActivity(getActivity(), quoteItem, 2);
            }
        });
        // 设置按钮（集合竞价）
        img_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), BidSetting.class));
            }
        });
    }

    @Override
    protected void initData() {
        // 调用回调的update方法
        update(quoteResponse);
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
        if(null != quoteItem.st && quoteItem.st.contains("1120")){
            ipov.setVisibility(View.VISIBLE);
            discount.setVisibility(View.VISIBLE);
            tevIpov.setVisibility(View.VISIBLE);
            tevDiscount.setVisibility(View.VISIBLE);
        }
        if (null != minuteSZHlper) {
            minuteSZHlper.updateData(quoteItem);
        }
    }

    /**
     * 请求走势数据成功
     */
    int flag = 3;// 3秒请求一次集合竞价
    @Override
    public void onRequestChartSuccess(final ChartResponse response) {
        setBidData();
        if (null == response || response.historyItems.isEmpty()) {
            return;
        }
        stockMinuteChart.setData(response);
        changeMaxValue();
    }
    private void setBidData() {
        // 如果是沪深市场(并且不是指数)，有集合竞价
        if ((MarketType.SH.equals(quoteItem.market) || MarketType.SZ.equals(quoteItem.market)) &&
                !"000001.sh".equals(quoteItem.id) && !"399001.sz".equals(quoteItem.id) && !"399006.sz".equals(quoteItem.id)) {
            // 获取本地保存的集合竞价展示状态
            String showState =  BidChartSetting.getBitchartState();
            if (BidChartSetting.STATE_NONE.equals(showState)) {
                if (lnl_bid.getVisibility() == View.GONE) {
                    // 如果原来没有展示，直接return
                    return;
                }
                // 展示状态为关闭，隐藏集合竞价图表
                lnl_bid.setVisibility(View.GONE);
                stockMinuteChart.getTopChart().setmMinLeftOffset(10f);
                stockMinuteChart.getBottomChart().setmMinLeftOffset(10f);
                return;
            }
            stockMinuteChart.getTopChart().setmMinLeftOffset(0);
            stockMinuteChart.getBottomChart().setmMinLeftOffset(0);
            lnl_bid.setVisibility(View.VISIBLE);
            // 3秒请求一次集合竞价
            if (flag == 3) {
                flag = 0;
            } else {
                flag++;
                return;
            }
            // 请求集合竞价
            BidChartRequest request = new BidChartRequest();
            request.send(quoteItem.id,new IResponseInfoCallback<BidChartResponse>() {
                @Override
                public void callback(BidChartResponse response1) {
                    // 集合竞价折线图
                    setBidTopChartData(response1);
                    // 集合竞价副图
                    setBidBottomChartData(response1);
                }
                @Override
                public void exception(ErrorInfo errorInfo) {
                    ToastUtils.showLongToast("BidChartRequest:" + errorInfo.getMessage());
                }
            });
        }
    }

    /**
     * 请求走势数据失败
     */
    @Override
    public void onRequestChartFail() {

    }

    /**
     * 请求走势副图成功
     */
    @Override
    public void onRequestChartSubSuccess(ArrayList<MinuteEntity> mSubEntities) {
        if (null == mSubEntities || mSubEntities.isEmpty()) {
            return;
        }
        stockMinuteChart.setMSubData(mSubEntities);
    }

    @Override
    public void onRequestChartSubFail(int i, String error) {
        Log.e("onRequestChartSubFail", "code =" + i + " ,message=" + error);
    }

    /**
     * 返回股票逐笔明细数据
     */
    @Override
    public void onTickItemsSuccess(ArrayList<TickItemBo> tickItemBos) {
        chartTickAdapter.setData(tickItemBos);
        lelChartDetail.removeAllViews();

        int length = tickItemBos.size();
        //level 1状态或者横屏状态显示10条
        int maxLength = AppInfo.getInfoLevel().equals("1") || isLand ? 10 : 20;

        //明细数据最多展示20条数据
        length = length > maxLength ? maxLength : length;

        for (int i = 0; i < length; i++) {
            View view = chartTickAdapter.getView(i, null, lelChartDetail);

            if (length < maxLength) {
                //level 2 时
                lelChartDetail.addView(view);
            } else {
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0);
                layoutParams.weight = 1;
                lelChartDetail.addView(view, layoutParams);
            }
        }
    }

    /**
     * 返回买卖队列
     */
    @Override
    public void onOrderQuantitySuccess(OrderQuantityResponse quoteResponse) {
        if (minuteSZHlper != null) {
            minuteSZHlper.setOrderData(quoteResponse);
        }
    }

    @Override
    public void update(QuoteResponse quoteResponse) {
        if ( null== quoteResponse ||  null== presenter) {
            return;
        }
        if( !quoteResponse.quoteItems.get(0).id.equals(quoteItem.id)){
            presenter.init(quoteResponse.quoteItems.get(0), ChartType.ONE_DAY, MSetting.getMSubType());
        }

        this.quoteResponse = quoteResponse;
        quoteItem = quoteResponse.quoteItems.get(0);

        if ( null!=minuteSZHlper ) {
            minuteSZHlper.updateData(quoteResponse.quoteItems.get(0));
        }

        if ( null!= minuteHKHlper) {
            minuteHKHlper.setData(quoteResponse);
        }

        //五档十档数据
        bfvFive.setData(quoteItem);
        onRequestQuote(quoteItem);
        updateOHLCItem(quoteItem);
        cacuTickItem();
    }

    /**
     * 计算明细
     */
    private void cacuTickItem() {
        TickItemBo tickItemBo;
        tickItemBos.clear();
        if (null == quoteItem.tradeTick) {
            return;
        }
        for (String[] strings : quoteItem.tradeTick) {
            tickItemBo = new TickItemBo();
            tickItemBo.tickFlag = strings[0];
            if (!FormatUtils.isStandard(strings[1])) {
                continue;
            }
            tickItemBo.time = strings[1].substring(0, 2) + ":" + strings[1].substring(2, 4);
            tickItemBo.volume = strings[2];
            tickItemBo.strPrice = strings[3];
            tickItemBo.price = NumberUtils.parseDouble(strings[3]);
            tickItemBos.add(tickItemBo);
        }
        onTickItemsSuccess(tickItemBos);
    }

    /**
     * todo
     * 更新指标，重新请求
     */
    public void updateSetting(boolean right, int indexType) {
        if (right) {
            presenter.requestChartSub(indexType);
        }
    }

    /**
     * 图表上方的数据
     */
    public void valueSelected(OHLCItem ohlcItem) {
        if (null == ohlcItem) return;

        TextUtils.setText(tevTime, ohlcItem.time);
        String closePrice = ohlcItem.closePrice;
        TextUtils.setText(tevPrice, closePrice);

        TextUtils.setText(tevIpov,ohlcItem.iopv);  // 基金净值
        TextUtils.setText(tevAveragePrice, ohlcItem.averagePrice);
        TextUtils.setText(tevPrencet, FormatUtils.formatPercent(ohlcItem.changeRate));
        TextUtils.setText(tevVolume, FormatUtils.isStandard(ohlcItem.tradeVolume) ? ohlcItem.tradeVolume + "手" : FillConfig.DEFALUT);
        UpDownUtils.compare(quoteItem.preClosePrice, closePrice, tevPrice, tevPrencet,tevIpov);
    }

    public void updateOHLCItem(QuoteItem quoteItem) {
        if (null == quoteItem) return;
        TextUtils.setText(tevPrice, quoteItem.lastPrice);
        TextUtils.setText(tevAveragePrice,quoteItem.averageValue);
        TextUtils.setText(tevPrencet, FormatUtils.formatPercent(quoteItem.changeRate));
        TextUtils.setText(tevVolume, FormatUtils.isStandard(quoteItem.nowVolume) ? quoteItem.nowVolume+"手":FillConfig.DEFALUT);
        TextUtils.setText(tevIpov,(null == quoteItem.IOPV ? quoteItem.preIOPV : quoteItem.IOPV));  // 基金净值
        UpDownUtils.compare(quoteItem.preClosePrice, quoteItem.lastPrice, tevPrice, tevPrencet);
    }


    /**
     * 集合竞价-折线图-初始化设置
     */
    void initTopBitChart() {
        //不显示图表网格;
        bidChart.setDrawGridBackground(false);
        //显示边框
        bidChart.setDrawBorders(true);
        bidChart.setBorderWidth(1);
        bidChart.setBorderColor(getResources().getColor(R.color.minute_grayLine));
        // 允许触摸事件
        bidChart.setTouchEnabled(false);
        bidChart.setSelected(false);
        bidChart.setEnabled(false);
        // 不允许拖动
        bidChart.setDragEnabled(false);
        // 不允许缩放
        bidChart.setPinchZoom(false);// 挤压缩放
        bidChart.setScaleXEnabled(false);// x轴
        bidChart.setScaleYEnabled(false);// y轴
        bidChart.setDoubleTapToZoomEnabled(false);// 双击缩放
        // 默认不展示描述
        Description description = new Description();
        description.setEnabled(false);
        bidChart.setDescription(description);
        // 设置外边距（边框到父控件的距离）
        bidChart.setmMinLeftOffset(10f);
        bidChart.setmMinRightOffset(0f);
        bidChart.setmMinTopOffset(0f);
        bidChart.setmMinBottomOffset(0f);
        //X轴设置显示位置在底部
        bidChart.getXAxis().setEnabled(true);
        XAxis xAxis = bidChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM_INSIDE);
        xAxis.setAxisMinimum(-0.9f); // 最小值-0.5f，为了使左侧留出点空间
        xAxis.setGranularity(1f);// 间隔为1
        xAxis.setLabelCount(201, true);
        xAxis.setAxisMaximum(201);
        xAxis.setAxisMinimum(0);
        xAxis.setDrawLabels(false);
        xAxis.setDrawGridLines(false); // 不绘制X轴网格线
        xAxis.setGridColor(getResources().getColor(com.android.haitong.R.color.minute_grayLine));// x轴颜色
        // 设置右侧y轴不显示
        bidChart.getAxisRight().setEnabled(false);
        bidChart.getAxisLeft().setEnabled(true);
        YAxis leftAxis = bidChart.getAxisLeft();
        //保证Y轴从0开始，不然会上移一点
        leftAxis.setAxisMinimum(0f);
        leftAxis.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART);
        leftAxis.setTextColor(getResources().getColor(R.color.gray));
        leftAxis.setDrawGridLines(false);
        leftAxis.setDrawAxisLine(false);
        leftAxis.setDrawLabels(false);
        leftAxis.setGridColor(getResources().getColor(com.android.haitong.R.color.minute_grayLine));
//        leftAxis.setValueFormatter(new IAxisValueFormatter() {
//            // 自定义值格式
//            @Override
//            public String getFormattedValue(float value, AxisBase axis) {
//                if ((int)value == 0) {
//                    return "";
//                }
//                return (int)value + "家";
//
//            }
//        });

        // 不绘制图例
        bidChart.getLegend().setForm(Legend.LegendForm.NONE);
    }
    /**
     * 集合竞价-副图-初始化设置
     */
    void initTopBitBarChart() {
        //不显示图表网格;
        bidBarchart.setDrawGridBackground(false);
        //显示边框
        bidBarchart.setDrawBorders(true);
        bidBarchart.setBorderWidth(1);
        bidBarchart.setBorderColor(getResources().getColor(R.color.minute_grayLine));
        // 允许触摸事件
        bidBarchart.setTouchEnabled(false);
        bidBarchart.setSelected(false);
        bidBarchart.setEnabled(false);
        // 不允许拖动
        bidBarchart.setDragEnabled(false);
        // 不允许缩放
        bidBarchart.setPinchZoom(false);// 挤压缩放
        bidBarchart.setScaleXEnabled(false);// x轴
        bidBarchart.setScaleYEnabled(false);// y轴
        bidBarchart.setDoubleTapToZoomEnabled(false);// 双击缩放
        // 默认不展示描述
        Description description = new Description();
        description.setEnabled(false);
        bidBarchart.setDescription(description);
        // 设置外边距（边框到父控件的距离）
        bidBarchart.setmMinLeftOffset(10f);
        bidBarchart.setmMinRightOffset(0f);
        bidBarchart.setmMinTopOffset(0f);
        bidBarchart.setmMinBottomOffset(15f);
        //X轴设置显示位置在底部
        bidBarchart.getXAxis().setEnabled(true);
        XAxis xAxis = bidBarchart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM_INSIDE);
        xAxis.setAxisMinimum(-0.9f); // 最小值-0.5f，为了使左侧留出点空间
        xAxis.setGranularity(1f);// 间隔为1
//        xAxis.setLabelCount(65, true);
        xAxis.setDrawLabels(true);
        xAxis.setDrawGridLines(false); // 不绘制X轴网格线
        xAxis.setGridColor(getResources().getColor(com.android.haitong.R.color.minute_grayLine));// x轴颜色
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTextColor(ThemeManager.colorXAxis());
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            // 自定义值格式
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                switch ((int)value) {
                    case 0:
                        return "09:15";
                    default:
                        return null;
                }
            }
        });


        // 设置右侧y轴不显示
        bidBarchart.getAxisRight().setEnabled(false);
        bidBarchart.getAxisLeft().setEnabled(true);
        YAxis leftAxis = bidBarchart.getAxisLeft();
        //保证Y轴从0开始，不然会上移一点
        leftAxis.setAxisMinimum(0f);
        leftAxis.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART);
        leftAxis.setTextColor(getResources().getColor(R.color.gray));
        leftAxis.setDrawGridLines(false);
        leftAxis.setDrawAxisLine(false);
        leftAxis.setDrawLabels(false);
        leftAxis.setGridColor(getResources().getColor(com.android.haitong.R.color.minute_grayLine));
        // 不绘制图例
        bidBarchart.getLegend().setForm(Legend.LegendForm.NONE);
    }

    /**
     * 添加限制线(集合竞价折线图）
     */
    public void setBidTopAxisLimitLine(Float label) {
        if (null != label) {
            // 参数：数值，名称
            LimitLine lim = new LimitLine(label, "");
            lim.setLineWidth(0.5f);
            lim.setLineColor(getResources().getColor(com.android.haitong.R.color.minute_grayLine));
            bidChart.getAxisLeft().removeAllLimitLines();// 先清除，避免重复绘制
            bidChart.getAxisLeft().addLimitLine(lim);
        }
    }

    /**
     * 加载集合竞价折线图数据
     */
    public void setBidTopChartData(BidChartResponse bidTopChartData) {
        // 走势图最大最小值
        Float max = stockMinuteChart.getTopChart().getAxisLeft().getAxisMaximum();
        Float min = stockMinuteChart.getTopChart().getAxisLeft().getAxisMinimum();
        float startValue = 0;
        if (!(max == 0 && min == 0)) {
            startValue = (max + min) / 2f;
            // 中间线
            setBidTopAxisLimitLine(startValue);
        }

        CopyOnWriteArrayList<BidItem> list = bidTopChartData.bidItems;
        if (null == list || list.size() == 0) {
            return;
        }
        /**
         * 折线图数据
         */
        List<Entry> lineEntries1 = BidChartDataUtils.getLineList(startValue, list);
        // 线
        LineDataSet dataSet1 = new LineDataSet(lineEntries1, "最新价");
        dataSet1.setColor(ThemeManager.colorWhiteBlack());//线条颜色
        dataSet1.setDrawCircles(true);//不显示圆点
        dataSet1.setCircleColor(ThemeManager.colorWhiteBlack());// 圆点颜色
        dataSet1.setCircleRadius(1f);// 圆点大小
        dataSet1.setHighlightEnabled(false); // 是否允许高亮
        dataSet1.setDrawHighlightIndicators(false); // 设置是否有拖拽高亮指示器

        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(dataSet1);
        // 放入数据
        LineData lineData = new LineData(dataSets);
        lineData.setDrawValues(false);//是否绘制线条上的文字
        bidChart.setData(lineData);
        bidChart.invalidate();
        if (max == 0 && min == 0) {
            // 中间线
            setBidTopAxisLimitLine((bidChart.getAxisLeft().getAxisMaximum() + bidChart.getAxisLeft().getAxisMinimum())/2);
        } else {
            Float bidMax = bidChart.getYChartMax();
            Float bidMin = bidChart.getYChartMin();
            // 重新计算最大最小值和中间线的value
            float big = 0;
            float small = 0;
            boolean change = false;// 是否有超出的
            if (bidMax != 0 && bidMax > max) {
                // 大
                big = bidMax - max;
                change = true;
            }
            if (bidMin != 0 && bidMin < min) {
                // 小
                small = min - bidMin;
                change = true;
            }
            if (change) {
                if ((big - small) > 0) {
                    // 取大的那一边的值
                    max = bidMax;
                    min = min - big;
                } else {
                    max = max + small;
                    min = bidMin;
                }
            }
            stockMinuteChart.getTopChart().getAxisLeft().setAxisMinimum(min);
            stockMinuteChart.getTopChart().getAxisLeft().setAxisMaximum(max);
            bidChart.getAxisLeft().setAxisMinimum(min);
            bidChart.getAxisLeft().setAxisMaximum(max);
            stockMinuteChart.getTopChart().invalidate();
            bidChart.invalidate();
        }
    }
    /**
     * 加载集合竞价副图数据
     */
    public void setBidBottomChartData(BidChartResponse bidTopChartData) {
        if (null == bidTopChartData || null == bidTopChartData.bidItems) {
            return;
        }
        CopyOnWriteArrayList<BidItem> list = bidTopChartData.bidItems;
        if (list.size() == 0) {
            return;
        }
        /**
         * 副图数据
         */
        float maxTopHeight = 0;// 买一最大值
        float maxBottomHeight = 0;// 买二最大值
        BarDataSet dataSet1;
        ArrayList<IBarDataSet> dataSets = new ArrayList<>();
        Map<String, float[]> map = BidChartDataUtils.getMap();
        for (int i = 0; i < list.size(); i++) {
            BidItem bidItem = list.get(i);
            String time = bidItem.time;
            time = time.substring(time.length() - 4);
            // 以时间后4位为key，value:{买一,买二,颜色}
            float[] floats = new float[3];
            if (null != bidItem.buy1) {
                floats[0] = Float.valueOf(bidItem.buy1);// 买一
            }
            // 上方的柱展示买二，如果买二为空，展示卖二
            if (null == bidItem.buy2 || "".equals(bidItem.buy2)) {
                if (null == bidItem.sell2 || "".equals(bidItem.sell2)) {
                    floats[1] = 0;// 卖二
                } else {
                    floats[1] = Float.valueOf(bidItem.sell2);// 卖二
                }
                floats[2] = GREEN;// 颜色：买二为空时，展示绿色
            } else {
                floats[1] = Float.valueOf(bidItem.buy2);// 买二
                floats[2] = RED;// 颜色：买二不为空时，展示红色
            }
            map.put(time, floats);
            // 记录最大值
            if (maxTopHeight < floats[0]) {
                maxTopHeight = floats[0];
            }
            if (maxBottomHeight < floats[1]) {
                maxBottomHeight = floats[1];
            }
        }
        float max = (maxTopHeight + maxBottomHeight) * 1.1f;// 展示的最大值等于最大买1加最大买2
        String[] key = BidChartDataUtils.getKeyStrings();
        for (int i = 0; i < map.size(); i++) {
            List<BarEntry> lineEntries = new ArrayList<>();
            // 想办法把第二个字段数据处理之后放进去，
            // 第二个字段值等于总值(最大值乘以2)减去buy1再减去buy2
            float[] floats = map.get(key[i]);
            if(null != floats) {
                lineEntries.add(new BarEntry(i, new float[]{floats[0], max - floats[0] - floats[1], floats[1]}));// 买一
                dataSet1 = new BarDataSet(lineEntries, "");
                dataSet1.setColors((int)floats[2], TRANSPARENT, (int)floats[2]);
                dataSet1.setHighlightEnabled(false); // 是否允许高亮
                dataSets.add(dataSet1);
            }
        }
        BarData data = new BarData(dataSets);
        data.setDrawValues(false);
        bidBarchart.setData(data);
        // 最大值
        bidBarchart.getAxisLeft().setAxisMaximum(max);
        bidBarchart.invalidate();
    }
    /**
     * 修改走势图、集合竞价折线图最大值
     */
    private void changeMaxValue() {
        if (lnl_bid.getVisibility() == View.VISIBLE) {
            float max = stockMinuteChart.getTopChart().getAxisLeft().getAxisMaximum();
            float min = stockMinuteChart.getTopChart().getAxisLeft().getAxisMinimum();
            // 集合竞价折线图最大最小值
            Float bidMax = bidChart.getAxisLeft().getAxisMaximum();
            Float bidMin = bidChart.getAxisLeft().getAxisMinimum();
            if (max == 0 && min == 0) {
                max = bidMax;
                min = bidMin;
            } else {
                // 重新计算最大最小值和中间线的value
                float big = 0;
                float small = 0;
                boolean change = false;// 是否有超出的
                if (bidMax != 0 && bidMax > max) {
                    // 大
                    big = bidMax - max;
                    change = true;
                }
                if (bidMin != 0 && bidMin < min) {
                    // 小
                    small = min - bidMin;
                    change = true;
                }
                if (change) {
                    if ((big - small) > 0) {
                        // 取大的那一边的值
                        max = bidMax;
                        min = min - big;
                    } else {
                        max = max + small;
                        min = bidMin;
                    }
                }
                stockMinuteChart.getTopChart().getAxisLeft().setAxisMinimum(min);
                stockMinuteChart.getTopChart().getAxisLeft().setAxisMaximum(max);
                bidChart.getAxisLeft().setAxisMinimum(min);
                bidChart.getAxisLeft().setAxisMaximum(max);
                stockMinuteChart.getTopChart().invalidate();
                bidChart.invalidate();
            }
        }
    }
}
