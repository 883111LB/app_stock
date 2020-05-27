package com.cvicse.stock.chart.control.new_control;

import android.util.SparseArray;

import com.android.haitong.R;
import com.cvicse.stock.chart.chart.GCombinedChart;
import com.cvicse.stock.chart.components.GYAxis;
import com.cvicse.stock.chart.data.new_data.FiveDayDataHelperNew;
import com.cvicse.stock.chart.formatter.CommonValueFormatter;
import com.cvicse.stock.chart.ui.StockFiveDayChart;
import com.cvicse.stock.chart.utils.ChartUtils;
import com.cvicse.stock.chart.utils.MyUtils;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.mitake.core.bean.exchange.TimeZone;
import com.mitake.core.response.ChartResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 五日图控制类
 * Created by liu_zlu on 2017/3/2 16:42
 */
public class StockFiveDayChartControlNew {
    // 五日图图表
    private StockFiveDayChart graphicView;

    // 五日图顶部图表
    private GCombinedChart topchart;

    //五日图下部指标图表
    private GCombinedChart bottomchart;

    // 图表的x轴
    private XAxis xAxisBottom, xAxisTop;

    // 图表的左侧边栏
    private YAxis yAxisLeftBottom, yAxisLeftTop;

    // 图表的右侧边栏
    private GYAxis yAxisRightBottom, yAxisRightTop;

    // 五日图数据帮助类
    private FiveDayDataHelperNew iDataHelper ;

    private SparseArray<String> mSparseArray = null;
    private ArrayList<String> dateDays = new ArrayList<>();

    public StockFiveDayChartControlNew(StockFiveDayChart graphicView, FiveDayDataHelperNew iDataHelper){
        this.graphicView = graphicView;
        topchart = graphicView.topchart;
        bottomchart = graphicView.bottomchart;
        this.iDataHelper = iDataHelper;
        xAxisTop = topchart.getXAxis();
        yAxisLeftTop = topchart.getAxisLeft();
        yAxisRightTop = (GYAxis) topchart.getAxisRight();

        xAxisBottom = bottomchart.getXAxis();
        yAxisLeftBottom = bottomchart.getAxisLeft();
        yAxisRightBottom = (GYAxis) bottomchart.getAxisRight();
        init();
    }

    public void notifyDataSetChanged(){
//        setXLabels();
        //设置数据范围( 刻度线 )
        yAxisLeftTop.setAxisMaximum(iDataHelper.getDataMax());
        yAxisLeftTop.setAxisMinimum(iDataHelper.getDataMin());
        yAxisRightTop.setAxisMinimum(iDataHelper.getPercentMin());
        yAxisRightTop.setAxisMaximum(iDataHelper.getPercentMax());

        xAxisTop.setAxisMinimum(iDataHelper.getXMin());
        xAxisTop.setAxisMaximum(iDataHelper.getXMax());
        xAxisBottom.setAxisMinimum(iDataHelper.getXMin());
        xAxisBottom.setAxisMaximum(iDataHelper.getXMax());

        topchart.setData(iDataHelper.getTopChartData());
        bottomchart.setData(iDataHelper.getBottomChartData());

        graphicView.postDelayed(new Runnable() {
            @Override
            public void run() {
                bottomchart.setAutoScaleMinMaxEnabled(true);
                topchart.setAutoScaleMinMaxEnabled(true);
                topchart.notifyDataSetChanged();
                bottomchart.notifyDataSetChanged();
                topchart.invalidate();
                bottomchart.invalidate();
            }
        }, 100);
    }

    private void setXLabels() {
        //获取五日图的五天日期
        ArrayList<String> dateDay = iDataHelper.getDateDay();
        Map<String, List<TimeZone>> listMap = iDataHelper.getTradeTime();

        if( null == dateDay || dateDay.isEmpty() || null == listMap || listMap.isEmpty() ){
            dateDays.add("/");
            return ;
        }
        mSparseArray = new SparseArray<>();
        dateDays.clear();
        // 计算五日每日总根数
        if( null != listMap && !listMap.isEmpty()){
            for (int i=0,size = dateDay.size();i<size;i++) {
                dateDays.add(dateDay.get(i));
                List<TimeZone> timeZoneList = listMap.get(dateDay.get(i));
                // 计算每日总根数
                int sum=0;
                if(null!=timeZoneList && !timeZoneList.isEmpty()){
                    for (TimeZone timeZone : timeZoneList) {
                        int closeH = Integer.parseInt(timeZone.getCloseHhMm())/100;
                        int closeM = Integer.parseInt(timeZone.getCloseHhMm())%100;
                        int openH = Integer.parseInt(timeZone.getOpenHhMm())/100;
                        int openM = Integer.parseInt(timeZone.getOpenHhMm())%100;
                        sum += (closeH-openH)*60+(closeM-openM);
                    }
                    mSparseArray.put(sum*(i+1),"1");
                }
            }
        }
    }

    private void init(){
        setTopSetting();
        setBottomSetting();

        //格式化数据
        yAxisLeftTop.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                if( null==iDataHelper.getQuoteItem()  || null ==iDataHelper.getQuoteItem().subtype ){
                    return "";
                }
                return CommonValueFormatter.formatValue(iDataHelper.getQuoteItem(),value);
            }
        });

        yAxisRightTop.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return CommonValueFormatter.formatPercent(value);
            }
        });

        xAxisTop.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                if( null == mSparseArray ){
                    setXLabels();
                }
                return mSparseArray.get((int) value);
            }
        });

        xAxisBottom.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                int position = (int) value;
                if( null == mSparseArray ){
                    setXLabels();
                }
                if(value >= 0) return mSparseArray.get((int) value);
                position = -position >= dateDays.size() ? (dateDays.size()) : -position;
                return dateDays.get(position-1);
            }
        });

        topchart.setMarkerRightFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                if( null!= iDataHelper){
                    return iDataHelper.getTopRightLable((int) value);
                }
                return "";
            }
        });
        topchart.setMarkerLeftFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                if( null!=iDataHelper ){
                    return iDataHelper.getTopLeftLable((int) value);
                }
                return "";
            }
        });

        bottomchart.setMarkerBottomFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                if( null!= iDataHelper){
                    return iDataHelper.getBottomLable((int) value);
                }
                return "";
            }
        });
        bottomchart.setMarkerLeftFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                if( null!=iDataHelper ){
                    return iDataHelper.getBottomLeftLable((int) value);
                }
                return "";
            }
        });
    }

    private void setTopSetting(){
        topchart = graphicView.getTopChart();
        topchart.setDrawBorders(true);
        topchart.setBorderWidth(1);
        topchart.setBorderColor(graphicView.getResources().getColor(R.color.minute_grayLine));
        topchart.setDragEnabled(true);
        topchart.setScaleYEnabled(false);
        topchart.setDescription(null);
        Legend topchartLegend = topchart.getLegend();
        topchartLegend.setEnabled(false);
        setTopXAxisSetting();
        setTopAxisLeftSetting();
        setTopAxisRightSetting();
    }

    private void setTopXAxisSetting(){
        xAxisTop.setDrawLabels(false);
        xAxisTop.setDrawGridLines(true);
        xAxisTop.setDrawAxisLine(false);
        //bar x y轴
        xAxisTop.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxisTop.setGridColor(graphicView.getResources().getColor(R.color.minute_grayLine));
    }

    private void setTopAxisLeftSetting(){
        yAxisLeftTop.setLabelCount(3);
        yAxisLeftTop.setDrawGridLines(true);
        yAxisLeftTop.setDrawAxisLine(false);
        yAxisLeftTop.setDrawLabels(true);
        ((GYAxis) yAxisLeftTop).setShowOnlyMinMaxEnabled(true);
        yAxisLeftTop.setGridColor(graphicView.getResources().getColor(R.color.minute_grayLine));
        yAxisLeftTop.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
    }

    /**
     * 添加限制线
     */
    public void setTopAxisLimitLine(ChartResponse chartResponse) {
        String label = iDataHelper.getTopAxisLimitLine(chartResponse);
        // 参数：数值，名称
        if (null != label && !"".equals(label)) {
            LimitLine lim = new LimitLine(Float.parseFloat(label), "");
            lim.setLineWidth(0.5f);
            lim.setLineColor(graphicView.getResources().getColor(R.color.text_gray));
            lim.enableDashedLine(10f,5f,0);  //设置虚线
            lim.setLabelPosition(LimitLine.LimitLabelPosition.LEFT_TOP);

            yAxisLeftTop.removeAllLimitLines();// 先清除，避免重复绘制
            yAxisLeftTop.addLimitLine(lim);
        }
    }

    private void setTopAxisRightSetting(){
        yAxisRightTop.setLabelCount(3);
        //     yAxisRightTop.setDrawLabels(false);
        yAxisRightTop.setDrawGridLines(false);
        yAxisRightTop.setDrawAxisLine(false);
        ((GYAxis) yAxisRightTop).setShowOnlyMinMaxEnabled(true);
        yAxisRightTop.setGridColor(graphicView.getResources().getColor(R.color.minute_grayLine));
    }

    private void setBottomSetting(){
        bottomchart.setDrawBorders(true);
        bottomchart.setBorderWidth(1);
        bottomchart.setBorderColor(graphicView.getResources().getColor(R.color.minute_grayLine));
        bottomchart.setDescription(null);
        bottomchart.setDragEnabled(true);
        bottomchart.setScaleYEnabled(false);
        Legend bottomchartLegend = bottomchart.getLegend();
        bottomchartLegend.setEnabled(false);
        setBottomXAxisSetting();
        setBottomAxisLeftSetting();
        setBottomAxisRightSetting();
    }

    private void setBottomXAxisSetting(){
        xAxisBottom.setAvoidFirstLastClipping(true);
        xAxisBottom.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxisBottom.setGridColor(graphicView.getResources().getColor(R.color.minute_grayLine));
    }
    private void setBottomAxisLeftSetting(){
        yAxisLeftBottom.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                if(value == 0){
                    return iDataHelper.unitStr;
                }
                return MyUtils.getVolNum(value/100);
            }
        });
        yAxisLeftBottom.setDrawAxisLine(false);
        yAxisLeftBottom.setDrawLabels(true);
        yAxisLeftBottom.setSpaceTop(0);
        yAxisLeftBottom.setDrawGridLines(false);
        // ((GYAxis)yAxisLeftBottom).setRelative(false);
        yAxisLeftBottom.setAxisMinimum(0);
        yAxisLeftBottom.resetAxisMaximum();
        yAxisLeftBottom.setLabelCount(6);
        ((GYAxis) yAxisLeftBottom).setShowOnlyMinMaxEnabled(true);
        ((GYAxis) yAxisLeftBottom).setAverage(false);
    }
    private void setBottomAxisRightSetting(){
        yAxisRightBottom.setDrawLabels(false);
        yAxisRightBottom.setDrawGridLines(false);
        yAxisRightBottom.setDrawAxisLine(false);
    }

    public void setLand(boolean isLand){
        if(!isLand){
            topchart.setScaleEnabled(false);
            bottomchart.setScaleEnabled(false);
            yAxisLeftTop.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART);
            yAxisRightTop.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART);
            yAxisLeftBottom.setEnabled(false);
            topchart.setmMinBottomOffset(0);
            topchart.setmMinTopOffset(0);
            bottomchart.setmMinTopOffset(0);
            bottomchart.setmMinBottomOffset(100);
        } else {
            topchart.setmMinBottomOffset(100);
            bottomchart.setmMinTopOffset(0);
            bottomchart.setmMinBottomOffset(100);
            yAxisRightBottom.setNeedsOffset(true);
            yAxisLeftTop.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
            yAxisRightTop.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
            ChartUtils.showLeft(topchart,bottomchart);
            ChartUtils.showRight(topchart,bottomchart);
        }
    }
}

