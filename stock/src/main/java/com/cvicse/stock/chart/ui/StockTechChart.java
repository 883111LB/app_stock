package com.cvicse.stock.chart.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.android.haitong.R;
import com.cvicse.base.utils.SizeUtils;
import com.cvicse.stock.chart.chart.GCombinedChart;
import com.cvicse.stock.chart.chart.KGCombinedChart;
import com.cvicse.stock.chart.data.KLineEntity;
import com.cvicse.stock.chart.data.TechChartType;
import com.cvicse.stock.chart.helper.BottomKDialogHlper;
import com.cvicse.stock.chart.helper.KLineMiddleHelper;
import com.cvicse.stock.chart.helper.StockTechChartHelper;
import com.cvicse.stock.chart.listener.CoupleChartGestureListener;
import com.cvicse.stock.chart.listener.GBarLineChartTouchListener;
import com.cvicse.stock.chart.listener.GViewPortHandlerListener;
import com.cvicse.stock.chart.listener.OnChartListener;
import com.cvicse.stock.chart.listener.OnKlineSelectedListener;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.mitake.core.OHLCItem;
import com.mitake.core.QuoteItem;
import com.mitake.core.parser.FQItem;
import com.mitake.core.parser.GBItem;
import com.stock.config.KSetting;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;


/**
 * Created by liu_zlu on 2017/1/9 20:30
 */
public class StockTechChart extends LinearLayout {
    public GCombinedChart topchart;
    public GCombinedChart bottomchart;
    private RelativeLayout lelCenter;

    // 指标设置帮助类
    private BottomKDialogHlper bottomKDialogHlper;

    // 包括切换横屏的操作封装类( 指标数据 )
    private KLineMiddleHelper kLineMiddleHelper;

    private StockTechChartHelper gpHelper;

    private CoupleChartGestureListener topChartGestureListener;

    private OnChartListener onChartListener;
    private boolean isLand = false;

    public StockTechChart(Context context) {
        super(context);
        init(context);
    }

    public StockTechChart(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public StockTechChart(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        setOrientation(VERTICAL);
        LinearLayout.LayoutParams param = new LayoutParams(LayoutParams.MATCH_PARENT,0);
        param.weight = 2;
        topchart = new GCombinedChart(context);
        topchart.setLayoutParams(param);
        this.addView(topchart);

        lelCenter = new RelativeLayout(context);
        LayoutInflater.from(context).inflate(R.layout.layout_chart_kline_detail,lelCenter);
        LinearLayout.LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, SizeUtils.dp2px(context,18));
        lelCenter.setLayoutParams(layoutParams);
        this.addView(lelCenter);

        LinearLayout.LayoutParams param2 = new LayoutParams(LayoutParams.MATCH_PARENT,0);
        param2.weight = 1;
        bottomchart = new KGCombinedChart(context);
        bottomchart.setLayoutParams(param2);
        bottomchart.setIsBottom(true);
        this.addView(bottomchart);

        gpHelper = new StockTechChartHelper(this);
        kLineMiddleHelper = new KLineMiddleHelper(lelCenter);
        bottomKDialogHlper = new BottomKDialogHlper(getContext());

        // 作用就是手指滑动屏幕，离开后不会有惯性滚动
//        topchart.setDragDecelerationEnabled(false);
//        bottomchart.setDragDecelerationEnabled(false);

        initChart();
    }

    private void initChart() {
        bottomKDialogHlper.setOnChartListener(new OnChartListener() {
            @Override
            public void onChangeLanded() {

            }

            @Override
            public void onKTypeClick() {
                super.onKTypeClick();
                setKType(KSetting.getKTypeAsType());
            }
        });

        kLineMiddleHelper.setOnChartListener(new OnChartListener() {
            @Override
            public void onChangeLanded() {
                if( null != onChartListener ){
                    //点击横屏按钮监控
                    onChartListener.onChangeLanded();
                }
            }

            @Override
            public void onKTypeClick() {
                super.onKTypeClick();
                if(isLand){
                    KSetting.setKNext();
                    setKType(KSetting.getKTypeAsType());
                } else {
                    bottomKDialogHlper.show();
                }
            }
        });

        // 高亮监听（被点击时顶图上时间、价、幅、均、量发生变动）
        topchart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {

            @Override
            public void onValueSelected(Entry e, Highlight h) {
                bottomchart.highlightValues(new Highlight[]{h});  // 设置高亮显示
                int position = (int) e.getX();
                KLineEntity kLineEntity = gpHelper.getItem(position);
                if(onKlineSelectedListener != null){
                    onKlineSelectedListener.onValueSelected(position,kLineEntity);
                }
                kLineMiddleHelper.setData(kLineEntity);
            }

            @Override
            public void onNothingSelected() {
                bottomchart.setHighlightPerDragEnabled(false); // 能否拖拽高亮线(数据点与坐标的提示线)，默认是true
                topchart.setHighlightPerDragEnabled(false);
                bottomchart.highlightValues(null);
                KLineEntity kLineEntity = gpHelper.getLastItem();
                if(onKlineSelectedListener != null){
                    onKlineSelectedListener.onValueSelected(0,gpHelper.getLastItem());
                }
                kLineMiddleHelper.setData(kLineEntity);
            }
        });

        // 高亮监听（被点击时顶图上时间、价、幅、均、量发生变动）
        bottomchart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e,Highlight h) {
                topchart.highlightValues(new Highlight[]{h});
                int position = (int) e.getX();
                KLineEntity kLineEntity = gpHelper.getItem(position);
                if(onKlineSelectedListener != null){
                    onKlineSelectedListener.onValueSelected(position,kLineEntity);
                }
                kLineMiddleHelper.setData(kLineEntity);
            }

            @Override
            public void onNothingSelected() {
                bottomchart.setHighlightPerDragEnabled(false);
                topchart.setHighlightPerDragEnabled(false);
                topchart.highlightValues(null);
                KLineEntity kLineEntity = gpHelper.getLastItem();
                if(onKlineSelectedListener != null){
                    onKlineSelectedListener.onValueSelected(0,gpHelper.getLastItem());
                }
                kLineMiddleHelper.setData(kLineEntity);
            }
        });

        // ChartGestureListener手势监听器,可以使得 chart与手势操作进行交互。
        bottomchart.setOnChartGestureListener(new CoupleChartGestureListener(bottomchart){
            @Override
            public void onChartSingleTapped(MotionEvent me) {
                super.onChartSingleTapped(me);
                KSetting.setKNext();
                setKType(KSetting.getKTypeAsType());
            }
        });

        // 点击切换指标图
        bottomchart.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Math.abs(((GBarLineChartTouchListener)bottomchart.getOnTouchListener()).mDecelerationVelocity.getX()) < 10 && !topchart.valuesToHighlight()){
                    KSetting.setKNext();
                    setKType(KSetting.getKTypeAsType());
                }
            }
        });

        topChartGestureListener = new CoupleChartGestureListener(topchart);
        topchart.setOnChartGestureListener(topChartGestureListener);

        // 上下表联动
        bottomchart.getViewPortHandler().setViewPortHandlerListener(new GViewPortHandlerListener(topchart));
        topchart.getViewPortHandler().setViewPortHandlerListener(new GViewPortHandlerListener(bottomchart));
    }

    /**
     * 设置K线数据
     * @param kLineDatas
     * @param quoteItem
     */
    public void setData(CopyOnWriteArrayList<OHLCItem> kLineDatas, QuoteItem quoteItem, ArrayList<GBItem> gbItems) {
        gpHelper.addRequestData(kLineDatas,quoteItem, gbItems);

        KLineEntity kLineEntity = gpHelper.getLastItem();
        //重置最后选择的数据
        if( null!= onKlineSelectedListener){
            onKlineSelectedListener.onValueSelected(0,gpHelper.getLastItem());
        }
        kLineMiddleHelper.setData(kLineEntity);
    }

    /**
     * 设置复权数据
     */
    public void setOHLCSubData(CopyOnWriteArrayList<FQItem> ohlcSubRsList){
        gpHelper.addOHLCSubData(ohlcSubRsList);
    }

    public void clearAll(){
        bottomchart.clear();
        bottomchart.setData(null);
        topchart.clear();
        topchart.setData(null);
    }

    public void setType(TechChartType.Type type){
        gpHelper.setType(type);
    }

    /**
     * 设置K线指标，更新页面
     * @param kType
     */
    public void setKType(TechChartType.KType kType){
        gpHelper.setKType(kType);
        kLineMiddleHelper.setKType(kType);
        KLineEntity kLineEntity = gpHelper.getLastItem();
        if( null!= onKlineSelectedListener){
            onKlineSelectedListener.onValueSelected(0,gpHelper.getLastItem());
        }
        kLineMiddleHelper.setData(kLineEntity);
    }

    public void setLand(boolean land){
        this.isLand = land;
        gpHelper.setLand(land);
        kLineMiddleHelper.setLand(land);
    }

    private OnKlineSelectedListener onKlineSelectedListener;

    // 高亮监听
    public void setOnKlineSelectedListener(OnKlineSelectedListener onKlineSelectedListener) {
        this.onKlineSelectedListener = onKlineSelectedListener;
        topChartGestureListener.setOnKlineSelectedListener(onKlineSelectedListener);   // 用于加载更多的数据
    }

    public void setChangeLandListener(OnChartListener onChartListener) {
        this.onChartListener = onChartListener;
    }

    /**
     * 均线周期发生改变，更新页面
     */
    public void updateTopSetting(){
        gpHelper.updateTopSetting();
        if( null!=onKlineSelectedListener ){
            onKlineSelectedListener.onValueSelected(0,gpHelper.getLastItem());
        }
    }

    public ArrayList<KLineEntity> getKLinEntities(){
        return gpHelper.dataHelper.getKLineEntities();
    }
}
