package com.cvicse.stock.markets.helper;

import android.content.Context;
import android.widget.FrameLayout;

import com.cvicse.stock.markets.data.FinanceChartData;
import com.cvicse.stock.util.FormatUtils;
import com.cvicse.stock.view.MPBarChart;
import com.cvicse.stock.view.MPLineChart;
import com.cvicse.stock.view.NewMarkerView;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/** 财务对比图表帮助类
 * Created by tang_xqing on 2018/4/24.
 */
public class StockFinanceChartHelper {

    private Context mContext;
    private FrameLayout frameLayout;

    private String type = "report_all";  // report_all：报告期 report_single：单季度
    private String typeName;
    private String quarterType;  // 最新、一季度、三季度、中报、年报
    private List<FinanceChartData> financeChartDataList;
    private MPBarChart mpBarChart;
    private MPLineChart mpLineChart;
    private LineChart mLineChart;
    private int granularityLine = 2;
    private String unit;  // 单位

    public StockFinanceChartHelper(Context context, FrameLayout frameLayout){
        this.mContext = context;
        this.frameLayout = frameLayout;

        mpBarChart = new MPBarChart(mContext);
        mpLineChart = new MPLineChart(mContext);
    }

    /**
     * 数据更新
     * @param infoList
     * @param typeName
     */
    public void setData(List<FinanceChartData> infoList,String typeName){
        this.typeName = typeName;
        postData(infoList);
    }

    /**
     * 数据处理
     */
    private void postData(List<FinanceChartData> infoList){
        this.financeChartDataList = new ArrayList<>();
        StringBuilder unitStr = new StringBuilder();
        for (int i =  infoList.size()-1; i >=0; i--) {
            FinanceChartData financeChartData = infoList.get(i);
            String valueStr = financeChartData.getValueStr();
            unitStr.append(valueStr).append("#");  // 得到单位
            valueStr = valueStr.contains("元") ?  valueStr.substring(0,valueStr.length() - "元".length()) : valueStr;
            // 将万亿，转换为具体数字
            financeChartData.setValue(FormatUtils.formatDateByChinest(valueStr));
            this.financeChartDataList.add(financeChartData);
        }
        // 单位统一,根据单位转换为对应的数值
        unit = FormatUtils.getUnit(unitStr.toString());
        for (int i = 0;i<this.financeChartDataList.size();i++){
            FinanceChartData financeChartData = this.financeChartDataList.get(i);
            financeChartData.setValue(FormatUtils.formatDataByUnit(unit,financeChartData.getValue()));
            financeChartData.setUnit(unit);
            this.financeChartDataList.set(i,financeChartData);
        }
        notifyData();
    }

    /**
     * 类型发生改变【报告期、单季度】
     * @param type
     */
    public void changeType(String type){
        this.type = type;
        notifyData();
    }

    public void changeQuarterType(String quarterType){
        this.quarterType = quarterType;
        notifyData();
    }

    private void notifyData(){
        this.frameLayout.removeAllViews();

        if( "_0".equals(quarterType) && "report_single".equals(type)){
            mpBarChart.setData(financeChartDataList,typeName);
            frameLayout.addView(mpBarChart.getBarChart());
            return;
        }

        setLineData();
        frameLayout.addView(mLineChart);
    }

    /**
     * 设置折线图数据，每次数据更新，线图都要重新绘制
     */
    private void setLineData(){
        // 设置x轴间隔
        List<Entry> entryList = new ArrayList<>();
        for (int i = 0; i < financeChartDataList.size(); i++) {
            float value = (float) financeChartDataList.get(i).getValue();
            entryList.add(new Entry(i, value));
        }
        mpLineChart.setData(entryList);
        mLineChart = mpLineChart.getLineChart();
//        mLineChart.getXAxis().setLabelCount(5);
//        mLineChart.getXAxis().setGranularity(5);
        initLineValueFormatter();
        mpLineChart.createChart();
    }

    /**
     * 格式化X轴、Y轴高亮时数据格式
     */
    private void initLineValueFormatter() {
        mLineChart.getXAxis().setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                int position = (int)value;
                String dateText = "";
                if( position == 0 || position < financeChartDataList.size() ){
                    dateText = financeChartDataList.get(position).getDateText();
                }
                // 因为需要分行展示，所以修改源码 XAxisRenderer.drawLabel
                return dateText;
            }
        });

        ((NewMarkerView) mLineChart.getMarker()).setCallBack(new NewMarkerView.CallBack() {
            @Override
            public void onCallBack(float x, String value) {
                int index = (int) x;
                if (index >= financeChartDataList.size()) {
                    return;
                }
                FinanceChartData financeChartData = financeChartDataList.get(index);
                ((NewMarkerView) mLineChart.getMarker()).getTvContent().setText(financeChartData.getDateText()+"\n"+typeName+": "+ financeChartData.getValueStr());
            }
        });

        mLineChart.getAxisLeft().setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                String format = new DecimalFormat("###.####" + unit).format(value);
                return format;
            }
        });
    }

}
