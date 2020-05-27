package com.cvicse.stock.chart.ui;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.haitong.R;
import com.cvicse.base.utils.SizeUtils;
import com.cvicse.stock.chart.chart.FiveCombinedChart;
import com.cvicse.stock.chart.chart.GCombinedChart;
import com.cvicse.stock.chart.helper.new_helper.StockFiveDayChartHelperNew;
import com.cvicse.stock.chart.listener.CoupleChartGestureListener;
import com.cvicse.stock.chart.listener.GViewPortHandlerListener;
import com.cvicse.stock.chart.listener.OnChartListener;
import com.cvicse.stock.chart.listener.OnGChartGestureListener;
import com.cvicse.stock.chart.listener.OnMinuteSelectedListener;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.mitake.core.OHLCItem;
import com.mitake.core.QuoteItem;
import com.mitake.core.response.ChartResponse;

/**
 * 五日图
 * Created by liu_zlu on 2017/3/2 16:41
 */
public class StockFiveDayChart  extends LinearLayout {
    public GCombinedChart topchart;
    public GCombinedChart bottomchart;

//    private StockFiveDayChartHelper mGpHelper;
    private StockFiveDayChartHelperNew mGpHelper;  //  五日图帮助类

    private TextView textView;
    private OnChartListener onChartListener;
    private float sum = 0;

    public StockFiveDayChart(Context context) {
        super(context);
        try{
            init(context);
        } catch (Exception e){
            Log.e("Exception",e.getMessage());
            e.printStackTrace();
        }
    }

    public StockFiveDayChart(Context context, AttributeSet attrs) {
        super(context, attrs);
            init(context);
    }

    public StockFiveDayChart(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        setOrientation(VERTICAL);

        LayoutParams param = new LayoutParams(LayoutParams.MATCH_PARENT,0);
        param.weight = 2;
        //上表
        topchart = new GCombinedChart(context);
        topchart.setLayoutParams(param);
        topchart.setId(R.id.top_chart);
        this.addView(topchart);

        // 中间
        LayoutParams param3 = new LayoutParams(LayoutParams.WRAP_CONTENT, SizeUtils.dp2px(context,18));
        param.weight = 2;
        param.gravity = Gravity.RIGHT;
        param3.setMargins(0,10,30,0);
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

        //下表
        LayoutParams param2 = new LayoutParams(LayoutParams.MATCH_PARENT,0);
        param2.weight = 1;
        bottomchart = new FiveCombinedChart(context);
        bottomchart.setLayoutParams(param2);
        bottomchart.setId(R.id.bottom_chart);
        bottomchart.setIsBottom(true);
        this.addView(bottomchart);

        mGpHelper = new StockFiveDayChartHelperNew(this);
        initChart();
    }

    public GCombinedChart getTopChart(){
        return topchart;
    }

    public GCombinedChart getBottomChart(){
        return bottomchart;
    }

    private void initChart() {
        //默认放空的数据
        mGpHelper.addRequestData(new ChartResponse());
//        mGpHelper.addRequestData(new ArrayList<OHLCItemBo>()); //old 2018-8-8

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

                bottomchart.highlightValues(new Highlight[]{bottomchart.getHighlighter().getHighlight(h.getXPx(),30)});
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
                topchart.highlightValues(new Highlight[]{topchart.getHighlighter().getHighlight(h.getXPx(),30)});
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

    //设置股票信息数据
    public void setQuoteItem( QuoteItem quoteItem){
        mGpHelper.setQuoteItem(quoteItem);
    }

    //设置五日线数据
    public void setData(ChartResponse chartResponse) {
        mGpHelper.addRequestData(chartResponse);
        //重置最后选择的数据
        if( null!= onChartSelectedListener){
            onChartSelectedListener.onValueSelected(0, mGpHelper.mDataHelper.getLastData(),true);
        }
    }

    public OHLCItem getItemPosition(int position){
        return mGpHelper.mDataHelper.getChartItemsList().get(position);
    }

    public void setOnChartListener(OnChartListener onChartListener) {
        this.onChartListener = onChartListener;
    }

    public void setLand(boolean land){
        mGpHelper.setLand(land);
        if(land){
            textView.setVisibility(GONE);
        }
    }

    private OnMinuteSelectedListener onChartSelectedListener;
    public void setOnChartSelectedListener(OnMinuteSelectedListener onChartSelectedListener){
        this.onChartSelectedListener = onChartSelectedListener;
    }

    OnGChartGestureListener onGChartGestureListener;

    public void setOnGChartGestureListener(OnGChartGestureListener onGChartGestureListener) {
        this.onGChartGestureListener = onGChartGestureListener;
    }

}
