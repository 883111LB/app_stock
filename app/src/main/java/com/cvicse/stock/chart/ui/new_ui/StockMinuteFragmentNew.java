package com.cvicse.stock.chart.ui.new_ui;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cvicse.annotations.MVPInject;
import com.cvicse.base.utils.NumberUtils;
import com.cvicse.base.utils.SizeUtils;
import com.cvicse.base.utils.StringUtils;
import com.cvicse.stock.R;
import com.cvicse.stock.base.ChartBaseFragment;
import com.cvicse.stock.chart.adapter.ChartTickAdapter;
import com.cvicse.stock.chart.data.MinuteEntity;
import com.cvicse.stock.chart.data.OHLCItemBo;
import com.cvicse.stock.chart.helper.new_helper.MinuteHKHlperNew;
import com.cvicse.stock.chart.helper.new_helper.MinuteSZHlperNew;
import com.cvicse.stock.chart.presenter.contract.StockDayContract;
import com.cvicse.stock.common.FillConfig;
import com.cvicse.stock.common.Setting.Setting;
import com.cvicse.stock.markets.data.TickItemBo;
import com.cvicse.stock.markets.helper.QuoteCallback;
import com.cvicse.stock.markets.ui.StockMoreDetailActivity;
import com.cvicse.stock.markets.ui.new_ui.StockDetailLandActivityNew;
import com.cvicse.stock.util.FormatUtils;
import com.cvicse.stock.utils.StockType;
import com.cvicse.stock.utils.TextUtils;
import com.cvicse.stock.utils.UpDownUtils;
import com.mitake.core.AppInfo;
import com.mitake.core.QuoteItem;
import com.mitake.core.request.ChartType;
import com.mitake.core.response.ChartResponse;
import com.mitake.core.response.OrderQuantityResponse;
import com.mitake.core.response.QuoteResponse;
import com.mitake.core.util.FormatUtility;
import com.mitake.diagram.StockChartActivityPackage.DiagramChartPackage.Entity;
import com.mitake.diagram.StockChartActivityPackage.DiagramChartPackage.OneDay.StockChartV2;
import com.mitake.utility.object.DetailedCallBack;
import com.mitake.utility.object.QueryLineCallBack;
import com.mitake.utility.object.UIDoubleTapCallBack;
import com.mitake.variable.object.CommonInfo;
import com.mitake.variable.utility.STKItemUtility;
import com.stock.config.MSetting;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * 新分时图
 * Created by liu_zlu on 2017/2/22 15:42
 */
public class StockMinuteFragmentNew extends ChartBaseFragment implements StockDayContract.View, QuoteCallback,QueryLineCallBack, DetailedCallBack, UIDoubleTapCallBack {

    private static final String QUOTEITEM = "QuoteItem";

    /**
     * 时间标示
     */
    @BindView(R.id.tev_time)
    TextView tevTime;

    /**
     * 价
     */
    @BindView(R.id.tev_price)
    TextView tevPrice;

    /**
     * 幅
     */
    @BindView(R.id.tev_prencet)
    TextView tevPrencet;

    /**
     * 均
     */
    @BindView(R.id.tev_average_price)
    TextView tevAveragePrice;

    /**
     * 量
     */
    @BindView(R.id.tev_volume)
    TextView tevVolume;

    @BindView(R.id.rel_right)
    RelativeLayout relRight;

    /**
     * 总RadioGroup
     */
    @BindView(R.id.stock_five_tick_radio)
    RadioGroup stockFiveTickRadio;

    /**
     * 五档十档按钮
     */
//    @BindView(R.id.rdb_five)
//    RadioButton radbFive;

    /**
     * 明细按钮
     */
    @BindView(R.id.rdb_tick)
    RadioButton radTick;

    /**
     * 五档十档控件
     */
//    @BindView(R.id.bfv_five)
//    BestFiveTenView bfvFive;

    /**
     * 分时图控件
     */
//    @BindView(R.id.stock_minute_chart) StockMinuteChart stockMinuteChart;

    /**
     * 明细总布局，默认是隐藏的
     */
    @BindView(R.id.rel_detail)
    RelativeLayout relDetail;

    /**
     * 更多成交
     */
    @BindView(R.id.tev_detail_more)
    TextView tevDetailMore;

    /**
     * 更多成交上面的布局,即明细的数据
     */
    @BindView(R.id.lel_chart_detail)
    LinearLayout lelChartDetail;

    /**
     * 分时图下面的布局,存放委托队列数据
     * 港股为经纪买盘，经纪卖盘
     * 沪深为 买卖队列数据
     */
    @BindView(R.id.lel_more)
    LinearLayout lelMore;
    /**
     * 新分时图控件
     */
    @BindView(R.id.stock_minute_chart_new)
    FrameLayout stockMinuteChartNew;

    /**
     * 股票逐笔数据的adapter
     */
    private ChartTickAdapter chartTickAdapter;

    /**
     * 分时图下面的数据帮助类,level 2 状态下才有的
     */
    private MinuteSZHlperNew minuteSZHlper;

    private MinuteHKHlperNew minuteHKHlper;

    private QuoteItem quoteItem;
    private ArrayList<TickItemBo> tickItemBos = new ArrayList<>();
    private QuoteResponse quoteResponse;

    private StockChartV2 stockChart;  // 走势图
    private FragmentManager fragmentManager;

    private boolean isAdd = false;

    @MVPInject
    StockDayContract.Presenter presenter;

    public static StockMinuteFragmentNew newInstance(QuoteItem quoteItem) {
        StockMinuteFragmentNew stockDayFragment = new StockMinuteFragmentNew();
        Bundle bundle = new Bundle();
        bundle.putParcelable(QUOTEITEM, quoteItem);
        stockDayFragment.setArguments(bundle);
        return stockDayFragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_stockday_chart_new;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        quoteItem = getArguments().getParcelable(QUOTEITEM);
        fragmentManager = getChildFragmentManager();
        CommonInfo.refreshSecond = 5;  // new
        initChartView();
//        presenter.init(quoteItem, ChartType.ONE_DAY);
        presenter.init(quoteItem, ChartType.ONE_DAY, MSetting.getMSubType());
        /*
        //股票长按事件
        stockMinuteChart.setOnChartSelectedListener(new OnMinuteSelectedListener() {
            @Override
            public void onValueSelected(int position, OHLCItem ohlcItem, boolean last) {
                valueSelected(ohlcItem);
            }
        });

        stockMinuteChart.setOnChartListener(new OnChartListener() {
            @Override
            public void onChangeLanded() {
                StockDetailLandActivity.startActivity(getActivity(),quoteItem,0);
            }
        });
        */
        StockType.Type type = StockType.getType(quoteItem);
        /**
         * 横屏状态
         */
        if (isLand) {
//            stockMinuteChart.setLand(true);   // old
            ViewGroup.LayoutParams layoutParams = getMainView().getLayoutParams();
            layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT;
            getMainView().setLayoutParams(layoutParams);
            /*
            // old
            if (Setting.isLevel1()) {
                bfvFive.setFive();
            } else {
                bfvFive.setTen();
                bfvFive.setLand(true);//只对十档时有效
            }
            */
            //bfvFive.setLand(true);
        } else {
            // TODO: 2017/9/4  非横屏状态下双击切换横屏
            stockChart.setTapCallBack(this);  // 设定自定义双击事件
            //非横屏状态
//            stockMinuteChart.setLand(false);   // old
            if (Setting.isLevel2()) {//如果当前用户的状态为Level2
                if (!type.isIndex()) {
                    ViewGroup.LayoutParams layoutParams = getMainView().getLayoutParams();
                    layoutParams.height = SizeUtils.dp2px(getActivity(), 400);
                    getMainView().setLayoutParams(layoutParams);
                    // old
//                    bfvFive.setTen();
                    if (type.isSZ() || type.isSH()) {
                        lelMore.setVisibility(View.VISIBLE);
                        minuteSZHlper = new MinuteSZHlperNew(lelMore,this,quoteItem);
                    }
                    if (type.isHongKong()) {
                        lelMore.setVisibility(View.VISIBLE);
                        minuteHKHlper = new MinuteHKHlperNew(lelMore,this);
                    }
                }
            }
        }

//        stockMinuteChart.setQuoteItem(quoteItem);  // old

        if (type.isIndex()) {
            relRight.setVisibility(View.GONE);
        }

        //RadioGroup的点击事件
        stockFiveTickRadio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rdb_five:
                        //old
//                        bfvFive.setVisibility(View.VISIBLE);
                        relDetail.setVisibility(View.GONE);
                        break;
                    case R.id.rdb_tick:
//                        bfvFive.setVisibility(View.GONE);  // old
                        relDetail.setVisibility(View.VISIBLE);
                        break;
                }
            }
        });

        chartTickAdapter = new ChartTickAdapter(getActivity(), quoteItem);

        /**
         * 更多成交点击事件
         */
        tevDetailMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StockMoreDetailActivity.startActivity(getActivity(), quoteItem, 2);
            }
        });

    }

    /**
     * 创建走势图
     */
    private void initChartView() {
        Entity entity = new Entity();
        entity.setQueryLineUpDownMove(new float[]{30,30}); // 设定查价线偏移量
        entity.setQueryType(1);   // 设定查价线模式
//        entity.setyFontSize(26);         // 设定线图Y轴字型大小, 包含左边股票价位、成交量及右边幅度
//        entity.setxFontSize(26);          // 设定线图X轴字型大小;线图最下方日期/时间
//        entity.setQueryLineFontSize(26);    // 设定查价框字型大小

        entity.setQueryLineDestorySecond(5);
        entity.setTimeTextArea(0);  // 设定时间文字的位置 1：主图与副图之间 其他：副图底部
        entity.setMidAreaHeight(0);  // 设定主图和副图之间的间距
        entity.setYMove(true);  // 设定查价线是跟随y轴移动
        entity.setCouldDoubleTap(false); //设定是否开启双击切换横竖屏

        //判断是否为指数
        if("1400".equals(quoteItem.subtype)) {
            stockChart = new StockChartV2();
        }else{
            stockChart = new StockChartV2(true,false,true,this, Integer.valueOf(AppInfo.getInfoLevel()));
        }
        stockChart.setStockCode(quoteItem.id);
        stockChart.setStyle(entity);
        stockChart.setQueryLineCallBackListener(this);  // 设定查价线接口
        stockChart.setDetailListener(this);  // 设定明细更多接口
//        stockChart.setTapCallBack(this);  // 设定自定义双击事件

        Bundle config = new Bundle();
        config.putInt("position", 0);
        stockChart.setArguments(config);

        fragmentManager.beginTransaction().replace(R.id.stock_minute_chart_new, stockChart, "HomeTab_Portfolio").addToBackStack("HomeTab_Portfolio").commit();
    }

    @Override
    protected void initData() {
        /**
         * 调用回调的update方法
         */
        update(quoteResponse);
    }

    /**
     * 请求行情成功
     * @param quoteItem
     */
    @Override
    public void onRequestQuote(QuoteItem quoteItem) {
        if( null ==  quoteItem ){
            return;
        }

        //五档十档控件设置数据  old
//        bfvFive.setMSubData(quoteItem);

        /*  // 旧代码
        if(minuteSZHlper != null){
            minuteSZHlper.updateData(quoteItem);
        }*/
        /* if(minuteHKHlper != null){
            MinuteHKHlper.update();
        }*/

        // 新代码
//        if( null == stockChart ){
//            initChartView();
//        }

        stockChart.setSTKItem(quoteItem);
        stockChart.refreshData();
        updateOHLCItem2(quoteItem);
    }

    @Override
    public void onRequestChartSuccess(ChartResponse response) {

    }

    /**
     * 请求走势数据成功 (不需要)
     *
     * @param ohlcItems
     */
    public void onRequestChartSuccess(ArrayList<OHLCItemBo> ohlcItems) {
        if (ohlcItems == null || ohlcItems.size() <= 0) {
            return;
        }
//        stockMinuteChart.setMSubData(ohlcItems);  // old
        //valueSelected(ohlcItems.get(ohlcItems.size() - 2));
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
     * 返回股票逐笔明细数据
     *
     * @param tickItemBos
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
     *
     * @param quoteResponse
     */
    @Override
    public void onOrderQuantitySuccess(OrderQuantityResponse quoteResponse) {
        if (minuteSZHlper != null) {
            minuteSZHlper.setOrderData(quoteResponse);
        }
    }

    /**
     * 实时刷新
     *
     * @param quoteResponse
     */
    @Override
    public void update(QuoteResponse quoteResponse) {
        if ( null==quoteResponse ) {
            return;
        }
        this.quoteResponse = quoteResponse;
        quoteItem = quoteResponse.quoteItems.get(0);

        if ( null== presenter) {
            return;
        }
        presenter.init(quoteItem, ChartType.ONE_DAY, MSetting.getMSubType());

        if ( null!= minuteSZHlper) {
            minuteSZHlper.updateData(quoteResponse.quoteItems.get(0));
        }

        if ( null!=minuteHKHlper ) {
            minuteHKHlper.setData(quoteResponse);
        }

        //五档十档数据  old
//        bfvFive.setMSubData(quoteItem);

        boolean added = stockChart.isAdded();
        if( added ){
            onRequestQuote(quoteItem);
        }else{
            new Handler().post(new Runnable() {
                @Override
                public void run() {
                    onRequestQuote(quoteItem);
                }
            });
        }

//        onRequestQuote(quoteItem);

        TickItemBo tickItemBo;
        tickItemBos.clear();
        if (quoteItem.tradeTick == null) {
            return;
        }
        for (String[] strings : quoteItem.tradeTick) {
            tickItemBo = new TickItemBo();
            tickItemBo.tickFlag = strings[0];
            if (StringUtils.isEmpty(strings[1]) || strings[1].equals("null") || strings[1].equals("-")) {
                continue;
            }
            tickItemBo.time = strings[1].substring(0, 2) + ":" + strings[1].substring(2, 4);
            tickItemBo.volume = FormatUtility.formatVolume(strings[2]);
            tickItemBo.strPrice = strings[3];
            tickItemBo.price = NumberUtils.parseDouble(strings[3]);
            tickItemBos.add(tickItemBo);
        }
        onTickItemsSuccess(tickItemBos);
    }


    /**
     * 图表上方的数据
     */
    public void updateOHLCItem2(QuoteItem quoteItem) {
        if (quoteItem == null) return;
        String time = quoteItem.datetime;
        time = FormatUtils.formatDate(FormatUtils.parseDate(time,"yyyyMMddHHmmss"),"HH:mm");
        TextUtils.setText(tevTime, time, FillConfig.DOUBLE_LINE);

        // FIXME: 2017/8/31  quoteItem.nowVolume值和查价线中量值不一致。待确定取值字段
        String tradeVolume = FormatUtils.format(quoteItem.nowVolume).replace(".0", "");
        TextUtils.setText(tevVolume, FillConfig.DOUBLE_LINE.equals(tradeVolume) ? tradeVolume : tradeVolume + "手", FillConfig.DOUBLE_LINE);
        TextUtils.setText(tevAveragePrice,quoteItem.averageValue);

        TextUtils.setText(tevPrice, quoteItem.lastPrice);
        TextUtils.setText(tevPrencet, FormatUtils.formatPercent(quoteItem.changeRate));
        UpDownUtils.compare(quoteItem.preClosePrice, quoteItem.lastPrice, tevPrice, tevPrencet);
    }

    /**
     * 自定义明细更多按键
     */
    @Override
    public void callback(String id, String name, String market, String subType, String s4) {
        StockMoreDetailActivity.startActivity(getActivity(), quoteItem, 2);
    }

    /**
     * 自定义双击事件（横屏状态）
     * @param motionEvent
     */
    @Override
    public void callback(MotionEvent motionEvent) {
        StockDetailLandActivityNew.startActivity(getActivity(),quoteItem,0);
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
        TextUtils.setText(tevTime, time, FillConfig.DOUBLE_LINE);
        tevPrice.setTextColor(STKItemUtility.formatColor(quoteItem.preClosePrice, qPrice));
        tevPrencet.setTextColor(STKItemUtility.formatColor(quoteItem.preClosePrice, qPrice));
        TextUtils.setText( tevPrice,qPrice,FillConfig.DOUBLE_LINE);
        TextUtils.setText(tevPrencet, FormatUtils.formatPercent(qchangeRate), FillConfig.DOUBLE_LINE);
        TextUtils.setText(tevAveragePrice,FillConfig.DOUBLE_LINE);  // 没有对应的字段

        String tradeVolume = FormatUtils.format(qVolume).replace(".0", "");
        TextUtils.setText(tevVolume, FillConfig.DOUBLE_LINE.equals(tradeVolume) ? tradeVolume : tradeVolume + "手", FillConfig.DOUBLE_LINE);
    }
}
