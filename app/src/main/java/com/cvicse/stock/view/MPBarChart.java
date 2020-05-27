package com.cvicse.stock.view;

import android.content.Context;

import com.cvicse.stock.R;
import com.cvicse.stock.common.ColorUtils;
import com.cvicse.stock.markets.data.FinanceChartData;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.util.ArrayList;
import java.util.List;

/** 柱状图
 * Created by tang_xqing on 2018/4/24.
 */

public class MPBarChart {
    private Context mContext;
    private BarChart barChart;
    private XAxis xAxis;  // x轴
    private YAxis yAxis;  // y轴
    private NewMarkerView newMarkerView;

    private String typeName;
    private float groupSpace = 0.04f;
    private float barSpace = 0.03f;
    private float barWidth = 0.45f;

    private String[] titleStr;
    private List<FinanceChartData> financeChartDataList;

    public MPBarChart(Context context){
        this.mContext = context;
//        initBarChart();
    }

    private void initBarChart(){
        barChart = new BarChart(mContext);
        barChart.setVisibleXRangeMaximum(6); // 设置屏幕可见条数
        barChart.animateX(1500);  // x轴动画
        barChart.setTouchEnabled(true);
        barChart.setDragEnabled(true);
        barChart.getDescription().setEnabled(false);  // 描述
        barChart.setDrawValueAboveBar(true);
        barChart.setPinchZoom(true); // 按比例缩放
        barChart.setDrawGridBackground(false);
        barChart.setFitBars(true);  // 是两侧的柱图完全显示
        barChart.setExtraBottomOffset(15);//距视图窗口底部的偏移，类似与paddingbottom
        barChart.setExtraTopOffset(15);//距视图窗口顶部的偏移，类似与paddingtop

        setMarkView();
        setLegend();
        setYAxis();
        setXAxis();
    }

    /**
     * 设置遮罩
     */
    private void setMarkView(){
        newMarkerView = new NewMarkerView(mContext, R.layout.custom_marker_view_layout);
        newMarkerView.setCallBack(new NewMarkerView.CallBack() {
            @Override
            public void onCallBack(float x, String value) {
                int index = (int) x;
                if (index >= financeChartDataList.size()) {
                    return;
                }
                newMarkerView.getTvContent().setText(financeChartDataList.get(index).getDateText()+"\n"+typeName+": "+value);
            }
        });
        barChart.setMarker(newMarkerView);
    }

    /**
     * 设置图例
     */
    private void setLegend() {
        Legend legend = barChart.getLegend();
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT); // 图例水平居中
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP); // 垂直上方
        legend.setDrawInside(false); // 绘制在chart外侧
        legend.setDirection(Legend.LegendDirection.LEFT_TO_RIGHT);  //
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        legend.setForm(Legend.LegendForm.CIRCLE); // 图例窗体形状
        legend.setFormSize(12f);   // 图例的图形的大小
        legend.setTextSize(12f);
    }

    /**
     * 设置X轴
     */
    private void setXAxis(){
        xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(true);  // 绘制网格线
        xAxis.setDrawAxisLine(false);
        xAxis.setGranularity(1f);  //设置最小间隔
        xAxis.setCenterAxisLabels(true);  // 标签居中

        xAxis.setLabelCount(financeChartDataList.size());
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                int position = ((int)value) >= titleStr.length ? (titleStr.length-1) : ((int)value);
                position = position < 0 ? 0 : position;
                String dateText = titleStr[position];
//                return dateText.split(" ")[0]+"\n"+dateText.split(" ")[1];
                return dateText.split(" ")[0];
            }
        });
    }

    /**
     * 设置y轴
     */
    private void setYAxis(){
        yAxis = barChart.getAxisLeft(); // 左侧Y轴
        yAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        yAxis.setSpaceTop(10f);
        yAxis.setSpaceBottom(10f);
        yAxis.setDrawGridLines(false);
        yAxis.setDrawLabels(true);
        yAxis.setDrawAxisLine(true);
        yAxis.setDrawZeroLine(true);
        yAxis.setZeroLineWidth(1f);
        yAxis.setZeroLineColor(ColorUtils.GRAY);
    }

    public void setData(List<FinanceChartData> financeChartDatas,String typeName){
        this.financeChartDataList = financeChartDatas;
        this.typeName = typeName;

        initBarChart();
        addBarChartata();
    }

    /**
     * 添加数据
     */
   private void addBarChartata() {
       List<BarEntry> entryListOne = new ArrayList<>();
       List<BarEntry> entryListTwo = new ArrayList<>();
       List<BarEntry> entryListThree = new ArrayList<>();
       List<BarEntry> entryListFour = new ArrayList<>();

       StringBuilder sbDate = new StringBuilder();
       for (int i = 0; i < financeChartDataList.size(); i++) {
           FinanceChartData financeChartData = financeChartDataList.get(i);
           String date = financeChartData.getDate().substring(0, 4)+"年";

           if (!sbDate.toString().contains(date)){
               sbDate.append(date).append(",");
           }

           if("03-31".contains(financeChartData.getDate())){
               entryListOne.add(new BarEntry(i, (float) financeChartData.getValue(),date));
           }else if("06-30".contains(financeChartData.getDate())){
               entryListTwo.add(new BarEntry(i, (float) financeChartData.getValue(),date));
           }else  if("09-30".contains(financeChartData.getDate())){
               entryListThree.add(new BarEntry(i, (float) financeChartData.getValue(),date));
           }else if("12-31".contains(financeChartData.getDate())){
               entryListFour.add(new BarEntry(i, (float) financeChartData.getValue(),date));
           }
       }

       titleStr = sbDate.toString().split(",");
       createBarChart(entryListOne, entryListTwo, entryListThree, entryListFour);
    }

    /**
     * 创建柱状图
     */
    private void createBarChart(List<BarEntry> entryListOne, List<BarEntry> entryListTwo, List<BarEntry> entryListThree, List<BarEntry> entryListFour) {
        BarDataSet barDataSet = new BarDataSet(entryListOne,"一季度");
        barDataSet.setColor(ColorUtils.BAR_LEGEND_ONE);
        barDataSet.setDrawValues(false);  // 是否绘制数值

        BarDataSet barDataSet2 = new BarDataSet(entryListTwo,"二季度");
        barDataSet2.setColor(ColorUtils.BAR_LEGEND_TWO);
        barDataSet2.setDrawValues(false);  // 是否绘制数值

        BarDataSet barDataSet3 = new BarDataSet(entryListThree,"三季度");
        barDataSet3.setColor(ColorUtils.BAR_LEGEND_THREE);
        barDataSet3.setDrawValues(false);  // 是否绘制数值

        BarDataSet barDataSet4 = new BarDataSet(entryListFour,"四季度");
        barDataSet4.setColor(ColorUtils.BAR_LEGEND_FOUR);
        barDataSet4.setDrawValues(false);  // 是否绘制数值

        BarData barData = new BarData();
        barData.addDataSet(barDataSet);
        barData.addDataSet(barDataSet2);
        barData.addDataSet(barDataSet3);
        barData.addDataSet(barDataSet4);

        barChart.setData(barData);
        barChart.getBarData().setBarWidth(barWidth);

        barChart.getXAxis().setAxisMinimum(0);
//       barChart.getXAxis().setAxisMaximum(barChart.getBarData().getGroupWidth(groupSpace, barSpace) * xAxisValue.size() + xAxisValue.get(0));
        barChart.getXAxis().setAxisMaximum(barChart.getBarData().getGroupWidth(groupSpace, barSpace) *financeChartDataList.size() +0);
        barChart.groupBars(0, groupSpace, barSpace);

        barChart.invalidate();
    }

    public BarChart getBarChart(){
        return barChart;
    }
}
