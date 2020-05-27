package com.cvicse.stock.chart.ui.new_ui;

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
import com.cvicse.stock.chart.data.MinuteEntity;
import com.cvicse.stock.chart.data.OHLCItemBo;
import com.cvicse.stock.chart.helper.new_helper.BottomMDialogHlper;
import com.cvicse.stock.chart.helper.new_helper.MLineMiddleHelper;
import com.cvicse.stock.chart.helper.new_helper.StockMinuteChartHelperNew;
import com.cvicse.stock.chart.listener.CoupleChartGestureListener;
import com.cvicse.stock.chart.listener.GBarLineChartTouchListener;
import com.cvicse.stock.chart.listener.GViewPortHandlerListener;
import com.cvicse.stock.chart.listener.OnChartListener;
import com.cvicse.stock.chart.listener.OnGChartGestureListener;
import com.cvicse.stock.chart.listener.OnMinuteSelectedListener;
import com.cvicse.stock.util.FormatUtils;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.mitake.core.QuoteItem;
import com.mitake.core.response.ChartResponse;
import com.stock.config.MSetting;

import java.util.ArrayList;

/**
 * 分时图（顶部图、中间图、底部图）
 * 目前使用此布局
 * Created by liuzilu on 2017/2/11.
 */
public class StockMinuteChartNew extends LinearLayout {
    public GCombinedChart topchart;
    public GCombinedChart bottomchart;
    private RelativeLayout lelCenter;

    private float sum = 0;

    private boolean frist = true;
    private boolean isLand = false;
    // 量比是否显示的标志
    private boolean showVolRatioFlag = false;

    //分时图帮助类
    private StockMinuteChartHelperNew mGpHelper;
    // 指标设置帮助类
    private BottomMDialogHlper bottomMDialogHlper;
    // 包括切换横屏的操作封装类
    private MLineMiddleHelper mLineMiddleHelper;

    // 分时五日高亮选择监听
    private OnMinuteSelectedListener onChartSelectedListener;

    //横屏点击事件监听
    private OnChartListener onChartListener;

    private OnGChartGestureListener onGChartGestureListener;

    public StockMinuteChartNew(Context context) {
        super(context);
        init(context);
    }

    public StockMinuteChartNew(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public StockMinuteChartNew(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        setOrientation(VERTICAL);

        LayoutParams param = new LayoutParams(LayoutParams.MATCH_PARENT,0);
        param.weight = 2;
        topchart = new GCombinedChart(context);
        topchart.setLayoutParams(param);
        topchart.setId(R.id.top_chart);
//        topchart.setmMinLeftOffset(0f);// 图表左侧到父控件边框的距离
        this.addView(topchart);

        lelCenter = new RelativeLayout(context);
        LayoutInflater.from(context).inflate(R.layout.layout_chart_mline_detail,lelCenter);
        LinearLayout.LayoutParams layoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT, SizeUtils.dp2px(context,18));
        lelCenter.setLayoutParams(layoutParams);
        this.addView(lelCenter);

        LayoutParams param2 = new LayoutParams(LayoutParams.MATCH_PARENT,0);
        param2.weight = 1;
        bottomchart = new GCombinedChart(context);
        bottomchart.setLayoutParams(param2);
        bottomchart.setId(R.id.bottom_chart);
        bottomchart.setIsBottom(true);
//        bottomchart.setmMinLeftOffset(0f);// 图表左侧到父控件边框的距离
        this.addView(bottomchart);

        mGpHelper = new StockMinuteChartHelperNew(this);
        mLineMiddleHelper = new MLineMiddleHelper(lelCenter);
        bottomMDialogHlper = new BottomMDialogHlper(getContext());

        initChart();
    }

    private void initChart() {
        bottomMDialogHlper.setOnChartListener(new OnChartListener() {
            @Override
            public void onChangeLanded() {

            }

            @Override
            public void onKTypeClick() {
                super.onKTypeClick();
                setMSubType(MSetting.getMTypeAsType());
            }
        });

        mLineMiddleHelper.setOnChartListener(new OnChartListener() {
            @Override
            public void onChangeLanded() {
                if( null != onChartListener){
                    //点击横屏按钮监控
                    onChartListener.onChangeLanded();
                }
            }

            @Override
            public void onKTypeClick() {
                super.onKTypeClick();
                if(isLand){
                    MSetting.setMSubNext();
                    setMSubType(MSetting.getMTypeAsType());
                } else {
                    bottomMDialogHlper.show();
                }
            }
        });

        // ChartGestureListener手势监听器,可以使得 chart与手势操作进行交互。
        bottomchart.setOnChartGestureListener(new CoupleChartGestureListener(bottomchart){
            @Override
            public void onChartDoubleTapped(MotionEvent me) {
                super.onChartDoubleTapped(me);
            }

            @Override
            public void onChartSingleTapped(MotionEvent me) {
                super.onChartSingleTapped(me);
                // 单击进行切换指标
                MSetting.setMSubNext();
                setMSubType(MSetting.getMTypeAsType());
            }
        });

        // 设置手势滑动事件
        topchart.setOnChartGestureListener(new CoupleChartGestureListener(topchart){
            @Override
            public void onChartDoubleTapped(MotionEvent me) {
                super.onChartDoubleTapped(me);
                if(onGChartGestureListener != null){
                    onGChartGestureListener.onChartDoubleTapped();
                }
            }
        });

        // 高亮监听（被点击时顶图上时间、价、幅、均、量发生变动）
        topchart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                bottomchart.highlightValues(new Highlight[]{h});
                int position = (int) e.getX();
                MinuteEntity mLineEntity = mGpHelper.getMSubItem(position);

                if( null != onChartSelectedListener ){
                    onChartSelectedListener.onValueSelected(position, mGpHelper.getChartItems(position),false);

                    // TODO: 2018/3/20
                    onChartSelectedListener.onValueSelected(position,mLineEntity);
                }
                updataMSub(mLineEntity);
            }

            @Override
            public void onNothingSelected() {
                bottomchart.setHighlightPerDragEnabled(false);  // 能否拖拽高亮线
                topchart.setHighlightPerDragEnabled(false);
                bottomchart.highlightValues(null);

                MinuteEntity mLineEntity = mGpHelper.getMSubLastItem();
                if( null != onChartSelectedListener){
                    onChartSelectedListener.onValueSelected(0, mGpHelper.mDataHelper.getLastData(),true);

                    // TODO: 2018/3/20
                    onChartSelectedListener.onValueSelected(0,mLineEntity);
                }
                updataMSub(mLineEntity);
            }
        });

        // 设置数值选择监听
        bottomchart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e,Highlight h) {
                topchart.highlightValues(new Highlight[]{h});
                int position = (int) e.getX();
                MinuteEntity mLineEntity = mGpHelper.getMSubItem(position);

                if( null != onChartSelectedListener){
                    onChartSelectedListener.onValueSelected(position, mGpHelper.getChartItems(position),false);

                    // TODO: 2018/3/20
                    onChartSelectedListener.onValueSelected(position,mLineEntity);
                }
                updataMSub(mLineEntity);
            }

            @Override
            public void onNothingSelected() {
                bottomchart.setHighlightPerDragEnabled(false);
                topchart.setHighlightPerDragEnabled(false);
                topchart.highlightValues(null);

                MinuteEntity mLineEntity = mGpHelper.getMSubLastItem();
                if( null!= onChartSelectedListener){
                    onChartSelectedListener.onValueSelected(0, mGpHelper.mDataHelper.getLastData(),true);

                    // TODO: 2018/3/20
                    onChartSelectedListener.onValueSelected(0,mLineEntity);
                }
                updataMSub(mLineEntity);
            }
        });

        // 点击切换指标图
        bottomchart.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Math.abs(((GBarLineChartTouchListener)bottomchart.getOnTouchListener()).mDecelerationVelocity.getX()) < 10 && !topchart.valuesToHighlight()){
                    MSetting.setMSubNext();
                    setMSubType(MSetting.getMTypeAsType());
                }
            }
        });

        // 上下表联动
        bottomchart.getViewPortHandler().setViewPortHandlerListener(new GViewPortHandlerListener(topchart));
        topchart.getViewPortHandler().setViewPortHandlerListener(new GViewPortHandlerListener(bottomchart));
    }

    public GCombinedChart getTopChart(){
        return topchart;
    }

    public GCombinedChart getBottomChart(){
        return bottomchart;
    }

    public void setQuoteItem(QuoteItem quoteItem){
        mGpHelper.setQuoteItem(quoteItem);
    }

    /**
     * 显示量比
     */
    public void showVolRatio(){
        showVolRatioFlag = true;
    }

    public void setData(ChartResponse chartResponse) {
        mGpHelper.addRequestData(chartResponse);

        OHLCItemBo lastData = mGpHelper.mDataHelper.getLastData();
        if( null != onChartSelectedListener){
            onChartSelectedListener.onValueSelected(0, lastData,true);
        }
        if(null != lastData && MSubType.VOLUME == MSetting.getMTypeAsType() ){
            MinuteEntity minuteEntity = new MinuteEntity();
            minuteEntity.volume =  Float.parseFloat(!FormatUtils.isStandard(lastData.tradeVolume) ? "0":lastData.tradeVolume);
            mLineMiddleHelper.setMSubData(minuteEntity);
        } else if (null != lastData && MSubType.VOLRatio == MSetting.getMTypeAsType() ) {
            // 量比
            MinuteEntity minuteEntity = new MinuteEntity();
            minuteEntity.volume =  Float.parseFloat(!FormatUtils.isStandard(lastData.volRatio) ? "0":lastData.volRatio);
            mLineMiddleHelper.setMSubData(minuteEntity);
        }
    }

    /**
     * 走势副图指标数据
     */
    public void setMSubData(ArrayList<MinuteEntity> mSubEntities){
        mGpHelper.addRequestMSubData(mSubEntities);

        MinuteEntity minuteEntity = mGpHelper.getMSubLastItem();

        //重置最后选择的数据
        OHLCItemBo lastData = mGpHelper.mDataHelper.getLastData();

        if( null != onChartSelectedListener ){
            onChartSelectedListener.onValueSelected(0,minuteEntity);
            // 成交量
            onChartSelectedListener.onValueSelected(0, lastData,true);
        }
        if( MSubType.VOLUME == MSetting.getMTypeAsType() ){
            minuteEntity = new MinuteEntity();
            minuteEntity.volume =  Float.parseFloat(!FormatUtils.isStandard(lastData.tradeVolume) ? "0": lastData.tradeVolume);
            mLineMiddleHelper.setMSubData(minuteEntity);
        }

        updataMSub( minuteEntity);
    }

    public void setLand(boolean land){
        this.isLand = land;
        mGpHelper.setLand(land);
        mLineMiddleHelper.setLand(land);
        updataMSub( mGpHelper.getMSubLastItem());
    }

    public void setMSubType(StockMinuteChartNew.MSubType mSubType){
        // 重新请求走势副图接口
        onChartListener.onKTypeClick();

        mGpHelper.setMSubType(mSubType);
        mLineMiddleHelper.setMSubType(mSubType);

        MinuteEntity mLineEntity = mGpHelper.getMSubLastItem();

        if( null != onChartSelectedListener){
            onChartSelectedListener.onValueSelected(0,mLineEntity);
            // 成交量
            onChartSelectedListener.onValueSelected(0, mGpHelper.mDataHelper.getLastData(),true);
        }
        updataMSub(mLineEntity);
    }

    public void setOnChartListener(OnChartListener onChartListener) {
        this.onChartListener = onChartListener;
    }

    /**
     * 高亮监听
     */
    public void setOnChartSelectedListener(OnMinuteSelectedListener onMinuteSelectedListener){
        this.onChartSelectedListener = onMinuteSelectedListener;
    }

    public void setOnGChartGestureListener(OnGChartGestureListener onGChartGestureListener) {
        this.onGChartGestureListener = onGChartGestureListener;
    }

    private void updataMSub(MinuteEntity minuteEntity) {
            mLineMiddleHelper.setMSubData(minuteEntity);
    }

    /**
     * 更新页面
     */
    public void updateTopSetting(){
        mGpHelper.updateTopSetting();
        if( null != onChartSelectedListener ){
            onChartSelectedListener.onValueSelected(0, mGpHelper.getMSubLastItem());
        }
    }

    /**
     * 走势副图指标
     */
    public enum MSubType {
        VOLUME,
        DDX,
        DDY,
        DDZ,
        BBD,
        RATIOBS,
        CAPTIALGAME,
        ORDERNUM,
        BIGNETVOLUME,
        VOLRatio;// 量比

        public String toString(){
            if(this == VOLUME){
                return "VOL";
            }
            if(this == DDX){
                return "DDX";
            }
            if(this == DDY){
                return "DDY";
            }
            if(this == DDZ){
                return "DDZ";
            }
            if(this == BBD){
                return "大单金额";
            }
            if(this == RATIOBS){
                return "ratioBS";
            }
            if(this == CAPTIALGAME){
                return "资金博弈";
            }
            if(this == ORDERNUM){
                return "成交单数";
            }
            if (this==BIGNETVOLUME){
                return "大单净量";
            }
            if (this==VOLRatio){
                return "量比";
            }
            return "";
        }

        public int intValue(){
            if(this == VOLUME){
                return 0;
            }
            if(this == DDX){
                return 1;
            }
            if(this == DDY){
                return 2;
            }
            if(this == DDZ){
                return 3;
            }
            if(this == BBD){
                return 4;
            }
            if(this == RATIOBS){
                return 5;
            }
            if(this == CAPTIALGAME){
                return 6;
            }
            if( this == ORDERNUM ){
                return 7;
            }
            if (this == BIGNETVOLUME){
                return 8;
            }
            if (this == VOLRatio){// 量比
                return 9;
            }
            return 0;
        }
    }
}
