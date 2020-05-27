package com.cvicse.stock.chart.ui;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.haitong.R;
import com.cvicse.stock.chart.chart.GCombinedChart;
import com.cvicse.stock.chart.data.OHLCItemBo;
import com.cvicse.stock.chart.helper.StockMinuteChartHelper;
import com.cvicse.stock.chart.listener.CoupleChartGestureListener;
import com.cvicse.stock.chart.listener.GViewPortHandlerListener;
import com.cvicse.stock.chart.listener.OnChartListener;
import com.cvicse.stock.chart.listener.OnGChartGestureListener;
import com.cvicse.stock.chart.listener.OnMinuteSelectedListener;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.mitake.core.QuoteItem;

import java.util.ArrayList;

/**
 * 分时图
 * Created by liuzilu on 2017/2/11.
 */
public class StockMinuteChart extends RelativeLayout {
    public GCombinedChart topchart;
    public GCombinedChart bottomchart;
    // 切换横屏
    private TextView textView;

    private boolean frist = true;

    //分时图帮助类
    private StockMinuteChartHelper mGpHelper;

    // 分时五日高亮选择监听
    private OnMinuteSelectedListener onChartSelectedListener;

    //横屏点击事件监听
    private OnChartListener onChartListener;

    private OnGChartGestureListener onGChartGestureListener;

    public StockMinuteChart(Context context) {
        super(context);
        init(context);
    }

    public StockMinuteChart(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public StockMinuteChart(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        LayoutParams param = new LayoutParams(LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        topchart = new GCombinedChart(context);
        param.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        topchart.setLayoutParams(param);
        topchart.setId(R.id.top_chart);
        this.addView(topchart);

        LayoutParams param2 = new LayoutParams(LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        bottomchart = new GCombinedChart(context);
        param2.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        param2.addRule(RelativeLayout.BELOW,R.id.top_chart);
        bottomchart.setLayoutParams(param2);
        bottomchart.setId(R.id.bottom_chart);
        bottomchart.setIsBottom(true);
        this.addView(bottomchart);

        textView = new TextView(context);
        textView.setText("切换横屏");
        textView.setTextSize(11);
        textView.setTextColor(Color.WHITE);
        textView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onChartListener != null){
                    onChartListener.onChangeLanded();
                }
            }
        });
        LayoutParams param3 = new LayoutParams(LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        param3.addRule(RelativeLayout.ALIGN_TOP,R.id.bottom_chart);
        param3.addRule(RelativeLayout.ALIGN_RIGHT,R.id.bottom_chart);
        param3.setMargins(0,10,30,0);
        textView.setPadding(10,0,10,5);
        textView.setLayoutParams(param3);

        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setCornerRadius(10);
        gradientDrawable.setColor(0xff2d527d);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            textView.setBackground(gradientDrawable);
        } else {
            textView.setBackgroundDrawable(gradientDrawable);
        }
        this.addView(textView);
        mGpHelper = new StockMinuteChartHelper(this);

        initChart();
    }

    private void initChart() {
        // ChartGestureListener手势监听器,可以使得 chart与手势操作进行交互。
       bottomchart.setOnChartGestureListener(new CoupleChartGestureListener(bottomchart){
            @Override
            public void onChartDoubleTapped(MotionEvent me) {
                super.onChartDoubleTapped(me);
            }
        });

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
                if(onChartSelectedListener != null){
                    onChartSelectedListener.onValueSelected((int) e.getX(), mGpHelper.mDataHelper.getChartItemsList().get((int) e.getX()),false);
                }
            }

            @Override
            public void onNothingSelected() {
                bottomchart.highlightValues(null);
                if(onChartSelectedListener != null){
                    onChartSelectedListener.onValueSelected(0, mGpHelper.mDataHelper.getLastData(),true);
                }
            }
        });
        bottomchart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e,Highlight h) {
                topchart.highlightValues(new Highlight[]{h});
                if(onChartSelectedListener != null){
                    onChartSelectedListener.onValueSelected((int) e.getX(), mGpHelper.mDataHelper.getChartItemsList().get((int) e.getX()),false);
                }
            }

            @Override
            public void onNothingSelected() {
                topchart.highlightValues(null);
                if(onChartSelectedListener != null){
                    onChartSelectedListener.onValueSelected(0, mGpHelper.mDataHelper.getLastData(),true);
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
     * 设置分时图数据
     */
    public void setData(ArrayList<OHLCItemBo> kLineDatas) {
        mGpHelper.addRequestData(kLineDatas);

        if(onChartSelectedListener != null){
            onChartSelectedListener.onValueSelected(0, mGpHelper.mDataHelper.getLastData(),true);
        }
    }

    public void setLand(boolean land){
        mGpHelper.setLand(land);
        if(land){
            textView.setVisibility(View.GONE);
        }
    }

    public void setOnChartListener(OnChartListener onChartListener) {
        this.onChartListener = onChartListener;
    }

    public void setOnChartSelectedListener(OnMinuteSelectedListener onMinuteSelectedListener){
        this.onChartSelectedListener = onMinuteSelectedListener;
    }

    public void setOnGChartGestureListener(OnGChartGestureListener onGChartGestureListener) {
        this.onGChartGestureListener = onGChartGestureListener;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if(frist){
            frist = false;
            float height = getMeasuredHeight();
            LayoutParams layoutParams = (LayoutParams) topchart.getLayoutParams();
            layoutParams.height= (int) (height*0.65f);
            topchart.setLayoutParams(layoutParams);
        }
    }
}
