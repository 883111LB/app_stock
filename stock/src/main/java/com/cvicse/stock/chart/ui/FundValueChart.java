package com.cvicse.stock.chart.ui;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.haitong.R;
import com.cvicse.stock.chart.chart.GCombinedChart;
import com.cvicse.stock.chart.chart.KGCombinedChart;
import com.cvicse.stock.chart.helper.FundValueChartHelper;
import com.cvicse.stock.chart.listener.CoupleChartGestureListener;
import com.cvicse.stock.chart.listener.OnChartListener;
import com.mitake.core.FundValueInfo;

/**
 * 基金净值图
 * Created by liu_zlu on 2017/4/28 14:51
 */
public class FundValueChart extends RelativeLayout {
    private FundValueChartHelper fundValueChartHelper;
    private GCombinedChart gCombinedChart;
    private TextView textView;
    private OnChartListener onChartListener;
    public FundValueChart(Context context) {
        super(context);
        finit(context);
    }

    public FundValueChart(Context context, AttributeSet attrs) {
        super(context, attrs);
        finit(context);
    }

    public FundValueChart(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        finit(context);
    }

    public GCombinedChart getChart(){
        return gCombinedChart;
    }
    private void finit(Context context) {

        LayoutParams param2 = new LayoutParams(LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        gCombinedChart = new KGCombinedChart(context);
        param2.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        param2.addRule(RelativeLayout.BELOW,R.id.top_chart);
        gCombinedChart.setLayoutParams(param2);
        gCombinedChart.setId(R.id.bottom_chart);
        this.addView(gCombinedChart);
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
        param3.setMargins(0,20,30,0);
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
        gCombinedChart.setOnChartGestureListener(new CoupleChartGestureListener(gCombinedChart));
        fundValueChartHelper = new FundValueChartHelper(this);
    }
    public void setRequestData(FundValueInfo fundValueInfo){
        fundValueChartHelper.setRequestData(fundValueInfo);
    }

    public void setLand(boolean isLand){
        fundValueChartHelper.setLand(isLand);
        if(isLand){
            textView.setVisibility(GONE);
        }
    }

    public void setOnChartListener(OnChartListener onChartListener) {
        this.onChartListener = onChartListener;
    }
}
