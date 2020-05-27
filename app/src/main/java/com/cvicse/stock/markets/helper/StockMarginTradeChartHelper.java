package com.cvicse.stock.markets.helper;

import android.content.Context;
import android.widget.FrameLayout;

import com.cvicse.stock.markets.data.MarginTradingBo;
import com.cvicse.stock.util.FormatUtils;
import com.cvicse.stock.view.MPLineChart;
import com.cvicse.stock.view.NewMarkerView;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/** 融资融券差值图表帮助类
 * Created by tang_xqing on 2018/8/4.
 */

public class StockMarginTradeChartHelper {
    private Context mContext;
    private FrameLayout mFrameLayout;
    private MPLineChart mMPLineChart;
    private LineChart mLineChart;
    private ArrayList<MarginTradingBo> mTradingBoList;
    private String unit;  // 单位

    public StockMarginTradeChartHelper(Context context, FrameLayout frameLayout) {
        mContext = context;
        mFrameLayout = frameLayout;
        mMPLineChart = new MPLineChart(mContext);
    }

    /**
     * 数据更新
     * @param tradingBoList
     */
    public void setMarginData( ArrayList<MarginTradingBo> tradingBoList ){
        this.mTradingBoList = new ArrayList<>();
        StringBuilder unitStr = new StringBuilder();
        for (int i =  tradingBoList.size()-1; i >=0; i--) {
            MarginTradingBo marginTradingBo = tradingBoList.get(i);
            String valueStr = marginTradingBo.getFinmrghbal();
            unitStr.append(valueStr).append("#");  // 得到单位
            valueStr = valueStr.contains("元") ?  valueStr.substring(0,valueStr.length() - "元".length()) : valueStr;
            // 将万亿，转换为具体数字
            marginTradingBo.fFinmrgnbal = FormatUtils.formatDateByChinest(valueStr);
            this.mTradingBoList.add(marginTradingBo);
        }
        // 单位统一,根据单位转换为对应的数值
        unit = FormatUtils.getUnit(unitStr.toString());
        for (int i = 0;i<this.mTradingBoList.size();i++){
            MarginTradingBo marginTradingBo = this.mTradingBoList.get(i);
            marginTradingBo.fFinmrgnbal = FormatUtils.formatDataByUnit(unit,marginTradingBo.fFinmrgnbal);
            marginTradingBo.setUnit(unit);
            this.mTradingBoList.set(i,marginTradingBo);
        }
        notifyData();
    }
    private void notifyData(){
        setLineData();
        this.mFrameLayout.removeAllViews();
        mFrameLayout.addView(mLineChart);
    }

    /**
     * 设置折线图数据，每次数据更新，线图都要重新绘制
     */
    private void setLineData(){
        List<Entry> entryList = new ArrayList<>();
        for (int i = 0; i < mTradingBoList.size(); i++) {
            float value = (float) mTradingBoList.get(i).fFinmrgnbal;   // 两融余额差值
            entryList.add(new Entry(i, value));
        }
        mMPLineChart.setData(entryList);
        mLineChart = mMPLineChart.getLineChart();
        mLineChart.getXAxis().setLabelCount(3);
        initLineValueFormatter();
        mMPLineChart.createChart();
    }

    /**
     * 格式化X轴、Y轴高亮时数据格式
     */
    private void initLineValueFormatter(){
        mLineChart.getXAxis().setAvoidFirstLastClipping(true);  // 让X坐标轴的开始结束Label避免被裁切
        mLineChart.getXAxis().setDrawGridLines(true);
        mLineChart.getXAxis().setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                int position =(int)value ;
                position = position==mTradingBoList.size() ? position-1:position;
//                if(0== position || position ==(mTradingBoList.size()-1)){
                    return mTradingBoList.get(position).tradeDate;
//                }
//                return "";
            }
        });

        mLineChart.getAxisLeft().setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                String format = new DecimalFormat("###.####" + unit).format(value);
                return format;
            }
        });

        ((NewMarkerView) mLineChart.getMarker()).setCallBack(new NewMarkerView.CallBack() {
            @Override
            public void onCallBack(float x, String value) {
                int index = (int) x;
                if (index >= mTradingBoList.size()) {
                    return;
                }
                MarginTradingBo financeChartData = mTradingBoList.get(index);
                ((NewMarkerView) mLineChart.getMarker()).getTvContent().setText(financeChartData.tradeDate+"\n两融余额: "+ financeChartData.finmrghbal);
            }
        });
    }
}
