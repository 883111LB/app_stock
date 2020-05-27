package com.cvicse.stock.chart.control;

import android.util.Log;

import com.android.haitong.R;
import com.cvicse.stock.chart.chart.GCombinedChart;
import com.cvicse.stock.chart.components.GYAxis;
import com.cvicse.stock.chart.data.KDataHelper;
import com.cvicse.stock.chart.data.TechChartType;
import com.cvicse.stock.chart.formatter.CommonValueFormatter;
import com.cvicse.stock.chart.formatter.CommonYValueFormatter;
import com.cvicse.stock.chart.helper.StockTechState;
import com.cvicse.stock.chart.theme.ThemeManager;
import com.cvicse.stock.chart.ui.StockTechChart;
import com.cvicse.stock.chart.utils.ChartUtils;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.CombinedData;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.mitake.core.MarketType;

/**
 * K线图控制类  设置各表属性
 * Created by liu_zlu on 2017/1/22 16:29
 */
public class StockTechChartControl {
    // k线图图表
    private StockTechChart graphicView;
    // k线图顶部图表
    private GCombinedChart topchart;
    // k线图底部图表
    private GCombinedChart bottomchart;

    // 图表x轴
    private XAxis xAxisBottom, xAxisTop;
    // 图表左侧边栏
    private YAxis yAxisLeftBottom, yAxisLeftTop;
    // 图表右侧边栏
    private YAxis yAxisRightBottom, yAxisRightTop;

    // 底部数据格式化
    private CommonYValueFormatter commonYValueFormatter;

    // k线图数据帮助类
    private KDataHelper iDataHelper ;

    // 当前图表的状态信息
    private StockTechState typeHpler;

    private boolean inviate = false;

    public StockTechChartControl(KDataHelper iDataHelper,StockTechChart graphicView, StockTechState typeHpler){
        this.iDataHelper = iDataHelper;
        this.typeHpler = typeHpler;
        this.graphicView = graphicView;
        topchart = graphicView.topchart;
        bottomchart = graphicView.bottomchart;
        commonYValueFormatter = new CommonYValueFormatter(iDataHelper,typeHpler);

        xAxisTop = topchart.getXAxis();
        yAxisLeftTop = topchart.getAxisLeft();
        yAxisRightTop = topchart.getAxisRight();

        xAxisBottom = bottomchart.getXAxis();
        yAxisLeftBottom = bottomchart.getAxisLeft();
        yAxisRightBottom = bottomchart.getAxisRight();

        init();
    }

    private void init(){
        initKLineTopSetting();
        initKLineBottomSetting();

        yAxisLeftTop.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                if( null== iDataHelper.getQuoteItem() || null==iDataHelper.getQuoteItem().subtype){
                    return "";
                }
                return CommonValueFormatter.formatValue(iDataHelper.getQuoteItem(),value);
            }
        });

        xAxisBottom.setValueFormatter(iDataHelper.getIAxisValueFormatter());

        topchart.setMarkerLeftFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                if( null != iDataHelper ){
                    return iDataHelper.getTopLeftLable((int) value);
                }
                return "";
            }
        });

        // 复权信息
        topchart.setMarkerTopFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                if( null != iDataHelper ){
                    return iDataHelper.getTopTopLable((int) value);
                }
                return null;
            }
        });

        bottomchart.setMarkerBottomFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                if( null!= iDataHelper){
                    return iDataHelper.getBottomLable((int) value);
                }
                return null;
            }
        });

        // 换手率
        topchart.setMarkerTopRightFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                if( null != iDataHelper ){
                    return iDataHelper.getTopRightLable((int)value);
                }
                return null;
            }
        });
      /*  bottomchart.setMarkerLeftFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                if(iDataHelper != null){
                    return iDataHelper.getBottomLeftLable((int) value);
                }
                return "";
            }
        });*/
    }

    /**
     * 设置上表属性（表，x,y轴）
     * http://blog.csdn.net/u010897392/article/details/50844571
     */
    public void initKLineTopSetting(){
        topchart.setmMinBottomOffset(0);
        topchart.setmMinTopOffset(0);
        bottomchart.setmMinTopOffset(0);

        topchart.setDrawBorders(true);   //设置图表内格子外的边框是否显示
        topchart.setBorderWidth(1);
        topchart.setBorderColor(graphicView.getResources().getColor(R.color.minute_grayLine));
        topchart.setDragEnabled(true);  // 是否可以拖拽
        topchart.setDescription(null);  //图表默认右下方的描述

        Legend bottomchartLegend = topchart.getLegend();  // 设置坐标线描述?? 的样式
        bottomchartLegend.setEnabled(false);

        //  The Axis 坐标轴相关的,XY轴通用 http://blog.csdn.net/u014136472/article/details/50298213
        xAxisTop.setDrawLabels(true);   //是否显示X坐标轴上的刻度，默认是true
        xAxisTop.setDrawGridLines(false); //是否显示X坐标轴上的刻度竖线，默认是true
        xAxisTop.setDrawAxisLine(false);  //是否绘制坐标轴的线，即含有坐标的那条线，默认是true
        xAxisTop.setPosition(XAxis.XAxisPosition.BOTTOM);//把坐标轴放在上下 参数有：TOP, BOTTOM, BOTH_SIDED, TOP_INSIDE or BOTTOM_INSIDE.
        xAxisTop.setGridColor(graphicView.getResources().getColor(R.color.minute_grayLine));  //X轴上的刻度竖线的颜色

        yAxisLeftTop.setDrawGridLines(true);
        yAxisLeftTop.setDrawLabels(true);
        yAxisLeftTop.setEnabled(true);   //  设置轴启用或禁用。如果false，该轴的任何部分都不会被绘制（不绘制坐标轴/便签等）
        yAxisLeftTop.setDrawLabels(true);
        yAxisLeftTop.setGridColor(graphicView.getResources().getColor(R.color.minute_grayLine));
        yAxisLeftTop.setLabelCount(5);  // 坐标的个数，第二个参数是 是否不均匀分布，true是不均匀分布
        yAxisLeftTop.removeAllLimitLines(); //  重置所有限制线,以避免重叠线

        yAxisRightTop.setDrawLabels(false);
        yAxisRightTop.setDrawGridLines(false);
        yAxisRightTop.setDrawAxisLine(false);
        ((GYAxis) yAxisLeftTop).setShowOnlyMinMaxEnabled(true);
        yAxisRightTop.setGridColor(graphicView.getResources().getColor(R.color.minute_grayLine));
    }

    /**
     * 设置下表属性（表，x,y轴）
     */
    public void initKLineBottomSetting(){
        bottomchart.setDrawBorders(true);
        bottomchart.setBorderWidth(1);
        bottomchart.setBorderColor(graphicView.getResources().getColor(R.color.minute_grayLine));
        bottomchart.setDescription(null);
        bottomchart.setDragEnabled(true);

        Legend bottomchartLegend = bottomchart.getLegend();
        bottomchartLegend.setEnabled(false);

        xAxisBottom.setEnabled(true);
        xAxisBottom.setDrawGridLines(false);
        xAxisBottom.setDrawAxisLine(true);
        xAxisBottom.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxisBottom.setGridColor(graphicView.getResources().getColor(R.color.minute_grayLine));

        yAxisLeftBottom.setValueFormatter(commonYValueFormatter);
        yAxisLeftBottom.setDrawAxisLine(false);
        yAxisLeftBottom.setDrawLabels(true);
        yAxisLeftBottom.setSpaceTop(0);

        yAxisRightBottom.setDrawLabels(false);
        yAxisRightBottom.setDrawGridLines(false);
        yAxisRightBottom.setDrawAxisLine(false);
    }

    public void setVolumSetting(){
        yAxisLeftBottom.setDrawGridLines(false);
        ((GYAxis) yAxisLeftBottom).setRelative(false);
        yAxisLeftBottom.setAxisMinimum(0);
        yAxisLeftBottom.resetAxisMaximum();
        yAxisLeftBottom.setLabelCount(2);
        ((GYAxis) yAxisLeftBottom).setShowOnlyMinMaxEnabled(true);
        ((GYAxis) yAxisLeftBottom).setAverage(false);
    }

    protected void setKDJSetting(){
        ((GYAxis) yAxisLeftBottom).setRelative(false);
        yAxisLeftBottom.setDrawGridLines(false);  // 是否绘制网格线，true-绘制
        yAxisLeftBottom.setLabelCount(3);  // 设置y轴的标签数量
//        yAxisLeftBottom.setAxisMaximum(100);  // old
//        yAxisLeftBottom.setAxisMinimum(0);
        yAxisLeftBottom.resetAxisMinimum();  // new
        yAxisLeftBottom.resetAxisMaximum();  // 重新设置Y轴坐标，自动调整

        ((GYAxis) yAxisLeftBottom).setShowOnlyMinMaxEnabled(true);  // true -- 该轴只显示它的最小值和最大值。如果force==true，这可能会被忽略/覆盖
//        ((GYAxis) yAxisLeftBottom).setAverage(true);  // 平均值  old
        ((GYAxis) yAxisLeftBottom).setAverage(false);  // 平均值 new
    }

    protected void setMacdSetting(){
        ((GYAxis) yAxisLeftBottom).setRelative(true);
        yAxisLeftBottom.setDrawGridLines(false);
        yAxisLeftBottom.setLabelCount(3);
        yAxisLeftBottom.resetAxisMinimum();
        yAxisLeftBottom.resetAxisMaximum();
        ((GYAxis) yAxisLeftBottom).setShowOnlyMinMaxEnabled(true);  // 参数如果为true Y轴坐标只显示最大值和最小值
        ((GYAxis) yAxisLeftBottom).setAverage(false);

    }

    protected void setRsiSetting(){
        ((GYAxis) yAxisLeftBottom).setRelative(false);
        yAxisLeftBottom.setDrawGridLines(false);

        yAxisLeftBottom.resetAxisMinimum();
        yAxisLeftBottom.resetAxisMaximum();
        yAxisLeftBottom.setLabelCount(2);
        ((GYAxis) yAxisLeftBottom).setShowOnlyMinMaxEnabled(true);
        ((GYAxis) yAxisLeftBottom).setAverage(false);
    }

    protected void setBollSetting(){
        ((GYAxis) yAxisLeftBottom).setRelative(false);
        yAxisLeftBottom.setDrawGridLines(false);

        yAxisLeftBottom.resetAxisMinimum();
        yAxisLeftBottom.resetAxisMaximum();
        yAxisLeftBottom.setLabelCount(2);
        ((GYAxis) yAxisLeftBottom).setShowOnlyMinMaxEnabled(true);
        ((GYAxis) yAxisLeftBottom).setAverage(false);
    }

    protected void setObvSetting(){
        ((GYAxis) yAxisLeftBottom).setRelative(false);
        yAxisLeftBottom.setDrawGridLines(false);
        yAxisLeftBottom.resetAxisMinimum();
        yAxisLeftBottom.resetAxisMaximum();
        yAxisLeftBottom.setLabelCount(2);
        ((GYAxis) yAxisLeftBottom).setShowOnlyMinMaxEnabled(true);
        ((GYAxis) yAxisLeftBottom).setAverage(false);
    }

    protected void setWrSetting(){
        ((GYAxis) yAxisLeftBottom).setRelative(false);
        yAxisLeftBottom.setDrawGridLines(false);
        yAxisLeftBottom.setLabelCount(3);

        yAxisLeftBottom.setAxisMaximum(100);
        yAxisLeftBottom.setAxisMinimum(0);
        ((GYAxis) yAxisLeftBottom).setShowOnlyMinMaxEnabled(true);
        //        ((GYAxis) yAxisLeftBottom).setAverage(true);  // 平均值  old
        ((GYAxis) yAxisLeftBottom).setAverage(false);  // 平均值 new
    }

    protected void setDmaSetting(){
        ((GYAxis) yAxisLeftBottom).setRelative(false);
        yAxisLeftBottom.setDrawGridLines(false);
        yAxisLeftBottom.resetAxisMinimum();
        yAxisLeftBottom.resetAxisMaximum();
        yAxisLeftBottom.setLabelCount(2);
        ((GYAxis) yAxisLeftBottom).setShowOnlyMinMaxEnabled(true);
        ((GYAxis) yAxisLeftBottom).setAverage(false);
    }

    /**
     * 当数据发生改变时，更新页面
     */
    public void notifyDataSetChanged(){
        yAxisLeftTop.resetAxisMaximum();
        yAxisLeftTop.resetAxisMinimum();
        xAxisTop.setAxisMinimum(-1);
        xAxisTop.setAxisMaximum(iDataHelper.getCount() == 0 ? 0:iDataHelper.getCount());
        xAxisBottom.setAxisMinimum(-1);
        xAxisBottom.setAxisMaximum(iDataHelper.getCount() == 0 ? 0:iDataHelper.getCount());

        // 重新绘制限制线
        float limit = null == iDataHelper.getLastKLineEntities() ? 0.0f : iDataHelper.getLastKLineEntities().close;
        LimitLine limitLine = new LimitLine(limit);
        limitLine.setLineWidth(1f);
        limitLine.setEnabled(true);
        limitLine.setLineColor(ThemeManager.colorRed());
        limitLine.enableDashedLine(5f, 10f, 0f);//三个参数，第一个线宽长度，第二个线段之间宽度，第三个一般为0，是个补偿
        limitLine.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_BOTTOM);//标签位置
        yAxisLeftTop.removeAllLimitLines();
        yAxisLeftTop.setDrawLimitLinesBehindData(false);
        yAxisLeftTop.addLimitLine(limitLine);

        commonYValueFormatter.notifyDataSetChanged();

        if(iDataHelper.getTopChartData() == topchart.getData()){
            bottomchart.setData(iDataHelper.getBottomChartData());
        } else {
            topchart.setData(iDataHelper.getTopChartData());
            bottomchart.setData(iDataHelper.getBottomChartData());
            if(!inviate && iDataHelper.getCount() > 1){
                inviate = true;
                int scale = postScale();
                if(scale > 1){
                    // 将坐标移动到最新位置
                    topchart.moveViewToX(iDataHelper.getCount() - 1);
                    bottomchart.moveViewToX(iDataHelper.getCount() - 1);
                }
            }

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
    }

    /**
     * 更新底部图表的设置信息（控制显示哪个指标图）
     */
    public void updateChartSetting(){
        clearBottom();
        if(typeHpler.ktype == TechChartType.KType.KDJ){
            setKDJSetting();
        } else if(typeHpler.ktype == TechChartType.KType.VOLUME || typeHpler.ktype == TechChartType.KType.AMO){
            setVolumSetting();
        } else if(typeHpler.ktype == TechChartType.KType.MACD){
            setMacdSetting();
        } else if(typeHpler.ktype == TechChartType.KType.RSI){
            setRsiSetting();
        } else if(typeHpler.ktype == TechChartType.KType.BOLL){
            setBollSetting();
        }else if(typeHpler.ktype == TechChartType.KType.OBV){
            setObvSetting();
        }else if(typeHpler.ktype == TechChartType.KType.WR){
            setWrSetting();
        }else if(typeHpler.ktype == TechChartType.KType.DMA || typeHpler.ktype == TechChartType.KType.VR ||
                typeHpler.ktype == TechChartType.KType.CR || typeHpler.ktype == TechChartType.KType.DMI ||
                typeHpler.ktype == TechChartType.KType.BIAS || typeHpler.ktype == TechChartType.KType.BBI ||
                typeHpler.ktype == TechChartType.KType.CCI ||typeHpler.ktype == TechChartType.KType.MTM ||
                typeHpler.ktype == TechChartType.KType.ROC || typeHpler.ktype == TechChartType.KType.BRAR ||
                typeHpler.ktype == TechChartType.KType.NVIPVI || typeHpler.ktype == TechChartType.KType.PSY){
            setDmaSetting();
        }
//        else if(typeHpler.ktype == TechChartType.KType.SAR){
//            setDmaSetting();
//        }
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

    public void setLand(boolean isLand){
        if(isLand){
            yAxisLeftTop.setLabelCount(5);
            ((GYAxis) yAxisLeftTop).setShowOnlyMinMaxEnabled(false);
            ChartUtils.showLeft(topchart,bottomchart);
            topchart.setFullyZoomedOut(false);
            bottomchart.setScaleEnabled(true);
            bottomchart.setFullyZoomedOut(false);
            yAxisLeftTop.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
            bottomchart.setmMinBottomOffset(100);
        } else {
            ChartUtils.setOnlyDrage(topchart,bottomchart);
            yAxisLeftTop.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART);
            yAxisLeftBottom.setEnabled(false);   // 竖屏状态下，不显示标签
            bottomchart.setmMinBottomOffset(100);
            topchart.setHighlightPerDragEnabled(true);
        }
    }

    public void clearBottom(){
        bottomchart.clear();
        bottomchart.setData(new CombinedData());
    }
    /**
     * 图表缩放
     * @return
     */
    private int postScale(){
        float scale = 4;
        if(typeHpler.isMinute()){
            scale = 20;
        }
        if(iDataHelper.getCount() > 80){
            topchart.postScale(iDataHelper.getCount(),scale);
            bottomchart.postScale(iDataHelper.getCount(),scale);
        }
        return (int) scale;
    }
}
