package com.cvicse.stock.chart.control.new_control;

import android.util.SparseArray;

import com.android.haitong.R;
import com.cvicse.stock.chart.chart.GCombinedChart;
import com.cvicse.stock.chart.components.GYAxis;
import com.cvicse.stock.chart.data.new_data.MinuteDataHelperNew;
import com.cvicse.stock.chart.formatter.CommonValueFormatter;
import com.cvicse.stock.chart.helper.new_helper.StockMState;
import com.cvicse.stock.chart.ui.new_ui.StockMinuteChartNew;
import com.cvicse.stock.chart.utils.ChartUtils;
import com.cvicse.stock.chart.utils.MyUtils;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.CombinedData;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.mitake.core.bean.exchange.TimeZone;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 分时图控制类
 * Created by tang_xqing on 2017/3/19.
 */
public class StockMinuteChartControlNew {
    // 分时图图表
    private StockMinuteChartNew graphicView;

    // 分时图顶部图表
    private GCombinedChart topchart;

    // 分时图底部图表
    private GCombinedChart bottomchart;

    // x轴
    private XAxis xAxisBottom, xAxisTop;

    // 左侧侧边栏
    private GYAxis yAxisLeftBottom, yAxisLeftTop;

    // 右侧侧边栏
    private GYAxis yAxisRightBottom, yAxisRightTop;

    // 分时图数据帮助类
    private MinuteDataHelperNew iDataHelper ;

    // 走势指标相关状态
    private StockMState mState;

    private SparseArray<String> xLabels = null;

    public StockMinuteChartControlNew(MinuteDataHelperNew dataHelper, StockMinuteChartNew graphicView,StockMState mState){
        this.iDataHelper = dataHelper;
        this.graphicView = graphicView;
        this.mState = mState;

        topchart = graphicView.topchart;
        bottomchart = graphicView.bottomchart;
        xAxisTop = topchart.getXAxis();
        yAxisLeftTop = (GYAxis) topchart.getAxisLeft();
        yAxisRightTop = (GYAxis) topchart.getAxisRight();

        xAxisBottom = bottomchart.getXAxis();
        yAxisLeftBottom = (GYAxis) bottomchart.getAxisLeft();
        yAxisRightBottom = (GYAxis) bottomchart.getAxisRight();
        init();
    }

    private void init(){
        //设置顶部高亮标志
        topchart.setMarkerLeftFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                if(iDataHelper != null)return iDataHelper.getTopLeftLable((int) value);
                return "";
            }
        });
        topchart.setMarkerRightFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                if( null!= iDataHelper) {
                    return iDataHelper.getTopRightLable((int) value);
                }
                return "";
            }
        });

        bottomchart.setMarkerBottomFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                if(null != iDataHelper) {
                    return iDataHelper.getBottomLable((int) value);
                }
                return "";
            }
        });

        xAxisTop.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                if( null==xLabels ){
                    setXLabels();
                }
                return null == xLabels ? "" : xLabels.get((int) value);
            }
        });

        yAxisLeftTop.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                if(null == iDataHelper.getQuoteItem() ||  null== iDataHelper.getQuoteItem().subtype){
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

        xAxisBottom.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                if( null==xLabels ){
                    setXLabels();
                }
                return null == xLabels ? "" : xLabels.get((int) value);
            }
        });

        setTopSetting();
        setBottomSetting();
    }

    /**
     * 更新走势副图指标
     */
    public void updateChartSetting(){
        clearBottom();
        if(mState.mSubType == StockMinuteChartNew.MSubType.VOLUME){
            setVolumSetting();
        } else if(mState.mSubType == StockMinuteChartNew.MSubType.DDX){
            setDDXSetting();
        } else if(mState.mSubType ==StockMinuteChartNew.MSubType.DDY){
            setDDYSetting();
        } else if(mState.mSubType == StockMinuteChartNew.MSubType.DDZ){
            setDDZSetting();
        }else if(mState.mSubType == StockMinuteChartNew.MSubType.BBD){
            setBBDSetting();
        }else if(mState.mSubType == StockMinuteChartNew.MSubType.RATIOBS){
            setRatioBSSetting();
        }else if(mState.mSubType == StockMinuteChartNew.MSubType.CAPTIALGAME){
            setCaptialGameSetting();
        }else if(mState.mSubType == StockMinuteChartNew.MSubType.ORDERNUM){
            setCaptialGameSetting();
        }else if (mState.mSubType ==StockMinuteChartNew.MSubType.BIGNETVOLUME){
            setBigNetVolumeSetting();
        }else if (mState.mSubType==StockMinuteChartNew.MSubType.VOLRatio){
            // 量比
            setVOLRatioSetting();
        }
    }

        /**
         * Y轴左侧栏赋值
         */
    public void notifyDataSetChanged(){
//        setXLabels(); //获取交易时间
        setAxisMaximum(iDataHelper.getXLength());
        yAxisLeftTop.setAxisMaximum(iDataHelper.getShowDataMax());
        yAxisLeftTop.setAxisMinimum(iDataHelper.getShowDataMin()); // Y轴左侧顶部最小值
        yAxisRightTop.setAxisMinimum(iDataHelper.getPercentMin());
        yAxisRightTop.setAxisMaximum(iDataHelper.getPercentMax());

        topchart.setData(iDataHelper.getTopChartData());
        bottomchart.setData(iDataHelper.getBottomChartData());

        graphicView.postDelayed(new Runnable() {
            @Override
            public void run() {
                topchart.setAutoScaleMinMaxEnabled(true);
                bottomchart.setAutoScaleMinMaxEnabled(true);

                topchart.notifyDataSetChanged();
                bottomchart.notifyDataSetChanged();

                topchart.invalidate();
                bottomchart.invalidate();
            }
        }, 100);
    }

    public void notifyTopDataSetChanged(){
        graphicView.postDelayed(new Runnable() {
            @Override
            public void run() {
                topchart.setAutoScaleMinMaxEnabled(true);
                topchart.notifyDataSetChanged();
                topchart.invalidate();
            }
        }, 100);
    }

    public void clearBottom(){
        bottomchart.clear();
        bottomchart.setData(new CombinedData());
    }

    /**
     * 设置第一个时间，中间时间以及最后一个时间
     */
    private void setXLabels() {
        ArrayList<String> dateDay = iDataHelper.getDateDay();
        Map<String, List<TimeZone>> listMap = iDataHelper.getTradeTime();

        if( null == dateDay || dateDay.isEmpty() || null == listMap || listMap.isEmpty() ){
            return ;
        }
        xLabels = new SparseArray<>();
        List<TimeZone> timeZoneList = listMap.get(dateDay.get(0));
        if(null != timeZoneList && !timeZoneList.isEmpty()) {
            String openHhMm = timeZoneList.get(0).getOpenHhMm();
            String closeHhMm = timeZoneList.get(timeZoneList.size() - 1).getCloseHhMm();
            xLabels.put(0, openHhMm.substring(0,2)+":"+openHhMm.substring(2,4));
            xLabels.put(iDataHelper.getXLength(), closeHhMm.substring(0,2)+":"+closeHhMm.substring(2,4));
        }
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
        xAxisTop.setAxisMinimum(0);
        //bar x y轴
        xAxisTop.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxisTop.setGridColor(graphicView.getResources().getColor(R.color.minute_grayLine));
    }

    private void setTopAxisLeftSetting(){
        // 设置左侧顶部刻度数量
        yAxisLeftTop.setLabelCount(3);
        yAxisLeftTop.setDrawGridLines(true);
        yAxisLeftTop.setDrawAxisLine(false);
        yAxisLeftTop.setDrawLabels(true);
        ((GYAxis) yAxisLeftTop).setShowOnlyMinMaxEnabled(true);
        yAxisLeftTop.setGridColor(graphicView.getResources().getColor(R.color.minute_grayLine));
        yAxisLeftTop.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART);
    }

    private void setTopAxisRightSetting(){
        // 设置右侧顶部刻度数量
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

        Legend bottomchartLegend = bottomchart.getLegend();  // 图例
        bottomchartLegend.setEnabled(false);
        xAxisBottom.setAxisMinimum(0);

        setBottomXAxisSetting();
        setBottomAxisLeftSetting();
        setBottomAxisRightSetting();
    }

    private void setBottomXAxisSetting(){
        xAxisBottom.setAvoidFirstLastClipping(true);  // 让X坐标轴的开始结束Label避免被裁切
        xAxisBottom.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxisBottom.setGridColor(graphicView.getResources().getColor(R.color.minute_grayLine));
    }

    /**
     * 设置底部，左侧y轴标签格式
     */
    private void setBottomAxisLeftSetting(){
        yAxisLeftBottom.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                if(value == 0){
                    return iDataHelper.unitStr;
                }
                return MyUtils.getVolNum(value);
//                return value+"";  // new 2018.3.22
            }
        });
        yAxisLeftBottom.setDrawAxisLine(false);
        yAxisLeftBottom.setDrawLabels(true);
        yAxisLeftBottom.setSpaceTop(0);
        yAxisLeftBottom.setDrawGridLines(false);
        yAxisLeftBottom.setAxisMinimum(0);
        yAxisLeftBottom.resetAxisMaximum();

        yAxisLeftBottom.setLabelCount(6);  // 设置左侧底部刻度数量
        yAxisLeftBottom.setShowOnlyMinMaxEnabled(true);  // true -- 该轴只显示它的最小值和最大值。如果force==true，这可能会被忽略/覆盖
    }

    private void setBottomAxisRightSetting(){
        yAxisRightBottom.setDrawLabels(false);
        yAxisRightBottom.setDrawGridLines(false);
        yAxisRightBottom.setDrawAxisLine(false);
    }

    protected void setVolumSetting(){
        yAxisLeftBottom.setDrawGridLines(false);
        ((GYAxis) yAxisLeftBottom).setRelative(false);
        yAxisLeftBottom.setAxisMinimum(0);
        yAxisLeftBottom.resetAxisMaximum();
        yAxisLeftBottom.setLabelCount(2);
        ((GYAxis) yAxisLeftBottom).setShowOnlyMinMaxEnabled(true);
        ((GYAxis) yAxisLeftBottom).setAverage(false);
    }

    protected void setDDXSetting(){
        ((GYAxis) yAxisLeftBottom).setRelative(false);
        yAxisLeftBottom.setDrawGridLines(false);  // 是否绘制网格线，true-绘制
        yAxisLeftBottom.setLabelCount(2);  // 设置y轴的标签数量
        yAxisLeftBottom.resetAxisMaximum();
        yAxisLeftBottom.resetAxisMinimum();
        ((GYAxis) yAxisLeftBottom).setShowOnlyMinMaxEnabled(true);  // true -- 该轴只显示它的最小值和最大值。如果force==true，这可能会被忽略/覆盖
        ((GYAxis) yAxisLeftBottom).setAverage(true);  // 平均值
    }

    protected void setDDYSetting(){
        setDDXSetting();
    }

    protected void setDDZSetting(){
        setDDXSetting();
    }

    protected void setBBDSetting(){
        setDDXSetting();
    }

    protected void setRatioBSSetting(){
        setDDXSetting();
    }

    protected void setCaptialGameSetting(){
        setDDXSetting();
    }

    protected void setBigNetVolumeSetting(){
        setDDXSetting();
    }

    protected void setVOLRatioSetting(){
        setDDXSetting();
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
            topchart.setmMinTopOffset(0);
            topchart.setmMinLeftOffset(0);
            topchart.setmMinBottomOffset(0);
            bottomchart.setmMinTopOffset(0);
            bottomchart.setmMinBottomOffset(100);
            bottomchart.setmMinLeftOffset(0);
            yAxisRightBottom.setNeedsOffset(true);
            yAxisLeftTop.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART);
            yAxisRightTop.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
            yAxisLeftBottom.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART);
            ChartUtils.showLeftFs(topchart,bottomchart);
            ChartUtils.showRight(topchart,bottomchart);
        }
    }

    private void setAxisMaximum(float max){
        xAxisBottom.setAxisMaximum(max);
        xAxisTop.setAxisMaximum(max);
    }
}

