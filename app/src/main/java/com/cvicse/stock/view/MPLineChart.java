package com.cvicse.stock.view;

import android.content.Context;
import android.graphics.Color;

import com.cvicse.stock.R;
import com.cvicse.stock.chart.theme.ThemeManager;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.List;

/** 折线图
 * Created by tang_xqing on 2018/4/24.
 */
public class MPLineChart {
    private Context mContext;
    private LineChart lineChart;
    private XAxis xAxis;  // x轴
    private YAxis yAxis;  // y轴
    private NewMarkerView newMarkerView;

    private float granularity =  1f;
    private int lableCount;
    private List<Entry> entryList;

    public MPLineChart(Context context){
        this.mContext = context;
    }

    private void initLineChart(){
        lineChart = new LineChart(mContext);
        lineChart.setNoDataText("暂无数据");// 没有数据的时候默认显示的文字
//        lineChart.setNoDataTextColor(ThemeManager.colorWhiteBlack());
        lineChart.animateX(1500);  // x轴动画
        lineChart.setTouchEnabled(true);  // 触摸
        lineChart.setDragEnabled(true);  // 拖拽
        lineChart.setScaleEnabled(true);
        lineChart.setScaleYEnabled(false);
        lineChart.getDescription().setEnabled(false);  // 描述
//        lineChart.setDraw(true);
        lineChart.setPinchZoom(true); // 按比例缩放
        lineChart.setDrawGridBackground(false);
//        lineChart.setFitsSystemWindows(true);  // 是两侧的柱图完全显示
//        lineChart.setExtraBottomOffset(20);//距视图窗口底部的偏移，类似与paddingbottom
//        lineChart.setExtraTopOffset(20);//距视图窗口顶部的偏移，类似与paddingtop
//        lineChart.setExtraOffsets(80,10,10,80);  // 上下左右偏移量。窗体外，布局内
        lineChart.setViewPortOffsets(60,10,60,80);  // 设置图表显示的偏移量，不建议使用，因为会影响图表的自动计算。在这不设置，x轴不显示标签
//        lineChart.setDragOffsetX(50);
//        lineChart.setDragOffsetY(50);

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
        newMarkerView.setChartView(lineChart); // 将LineChart与MarkView进行绑定，MarkView能计算是否超出LineChart显示范围
        lineChart.setMarker(newMarkerView);
    }

    /**
     * 设置图例
     */
    private void setLegend() {
        Legend legend = lineChart.getLegend();
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT); // 图例水平居中
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP); // 垂直上方
        legend.setDrawInside(false); // 绘制在chart外侧
        legend.setDirection(Legend.LegendDirection.LEFT_TO_RIGHT);  //
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        legend.setForm(Legend.LegendForm.CIRCLE); // 图例窗体形状
        legend.setFormSize(12f);   // 图例的图形的大小
        legend.setTextSize(12f);

        legend.setEnabled(false);  // 不展示
    }

    /**
     * 设置X轴
     */
    private void setXAxis(){
        xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(true);  // 绘制网格线
//        xAxis.setDrawAxisLine(true); // 设置x轴上每个点对应的线
        xAxis.setDrawLabels(true);
//        xAxis.setAvoidFirstLastClipping(true); //如果设置为true，则在绘制时会避免“剪掉”在x轴上的图表或屏幕边缘的第一个和最后一个坐标轴标签项。
//        xAxis.setSpaceMax(1f);  // x轴最左多出空n个坐标
        // xAxis.setSpaceMin(0.1f);  // 让左侧x轴不从0点开始

        xAxis.setGranularity(granularity);  //设置最小间隔
        xAxis.setGranularityEnabled(true);
//        xAxis.setGridLineWidth(10f);  // 网格线线宽
        xAxis.setAxisLineWidth(1f);  // x轴轴线宽度
        xAxis.setCenterAxisLabels(false);  // 标签居中

//        xAxis.setYOffset(50);
        xAxis.setTextColor(ThemeManager.colorXAxis());

        xAxis.setAxisMaximum(lableCount);
        xAxis.setAxisMinimum(0);
        xAxis.setSpaceMin(0.1f);
        xAxis.setSpaceMax(0.1f);
//        xAxis.setLabelCount(lableCount);

    }

    /**
     * 设置y轴
     */
    private void setYAxis(){
        lineChart.getAxisRight().setEnabled(false);
        yAxis = lineChart.getAxisLeft(); // 左侧Y轴
        yAxis.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART);
//        yAxis.setSpaceTop(20f);  // y轴最大值离顶点的距离
//        yAxis.setSpaceBottom(20f);
//        yAxis.setXOffset(50);
        yAxis.setDrawLabels(true);
        yAxis.setDrawAxisLine(true);
        yAxis.setDrawGridLines(true);
        yAxis.enableGridDashedLine(5f,5f,0f);  // 虚线 (虚线长度，间距，起点)
//        yAxis.setDrawZeroLine(true);
//        yAxis.setZeroLineWidth(1f);
//        yAxis.setZeroLineColor(ColorUtils.GRAY);
        yAxis.setTextColor(ThemeManager.colorYAxisLeft());
/*
        yAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                String format = new DecimalFormat(pattern + unit).format(value);
                return format;
            }
        });*/
    }

    public void setData(List<Entry> entryList){
        this.entryList = entryList;
        lableCount = entryList.size();
        initLineChart();
    }

    public void createChart(){
        addLineChartata(entryList);
    }

    /**
     * 添加数据
     */
    private void addLineChartata(List<Entry> entryList) {
        createLineChart(entryList);
    }

    /**
     * 创建折线图
     */
    private void createLineChart(List<Entry> entryList) {
        LineDataSet lineDataSet = new LineDataSet(entryList,"折线");
        lineDataSet.setColor(Color.BLUE);
        lineDataSet.setDrawValues(false);  // 是否绘制数值
//       lineDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);  //设置是曲线还是直线

        lineDataSet.setDrawCircles(false);
//        lineDataSet.setCircleColor(Color.BLACK);    //可以设置节点的颜色

//        lineDataSet.setDrawCircleHole(false);
//        lineDataSet.setCircleColorHole(Color.GREEN);  //设置节点中心圆的颜色
//        lineDataSet.setCircleSize(10f);  //设置节点的大小

        lineDataSet.setHighlightEnabled(true);

        LineData lineData = new LineData();
        lineData.addDataSet(lineDataSet);

//        lineChart.setVisibleXRangeMaximum(6); // 设置屏幕可见条数
        lineChart.setData(lineData);
        lineChart.notifyDataSetChanged();
        lineChart.invalidate();  // 图表被刷新，所提供的数据重新绘制。
    }

    public void setGranularity(int value){
        granularity = value;
    }

    public LineChart getLineChart(){
        return lineChart;
    }
}
