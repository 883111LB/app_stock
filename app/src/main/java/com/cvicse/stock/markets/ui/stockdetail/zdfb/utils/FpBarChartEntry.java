/*
package com.cvicse.stock.markets.ui.stockdetail.zdfb.utils;

*/
/**
 * Created by tang_hua on 2020/2/25.
 *//*


import com.github.mikephil.charting.charts.BarLineChartBase;
import com.github.mikephil.charting.data.BarEntry;

import java.util.List;

*/
/**
 * 柱状图
 * Created by jin
 *//*


public class FpBarChartEntity extends FpBarChartEntity<BarEntry> {
    public FpBarChartEntity(BarLineChartBase chart, List<BarEntry>[] entries, String[] labels, int[] chartColor, int valueColor, float textSize) {
        super(chart, entries, labels, chartColor, valueColor, textSize);
    }

    @Override
    protected void initChart() {
        super.initChart();
        mChart.getAxisLeft().setDrawGridLines(true);
        mChart.getAxisLeft().enableGridDashedLine(10f, 15f, 0f);
        mChart.getAxisLeft().setGridLineWidth(0.5f);
        mChart.getAxisLeft().setGridColor(Color.parseColor("#f5f5f5"));
        mChart.getAxisLeft().setDrawZeroLine(false);
        mChart.getAxisRight().setDrawZeroLine(false);
        mChart.getAxisRight().setZeroLineWidth(0f);
        mChart.getAxisLeft().setZeroLineWidth(0f);
        mChart.getAxisLeft().setDrawAxisLine(false);
        mChart.getXAxis().setDrawAxisLine(false);
        mChart.getXAxis().setAxisMinimum(0);

    }

    @Override
    protected void setChartData() {
        BarDataSet barDataSet;
        if (mChart.getData() != null && mChart.getData().getDataSetCount() > 0) {
            barDataSet = (BarDataSet) mChart.getData().getDataSetByIndex(0);
            barDataSet.setValues(mEntries[0]);
            mChart.getData().notifyDataChanged();
            mChart.notifyDataSetChanged();
        } else {
            barDataSet = new BarDataSet(mEntries[0], labels == null ? "" : labels[0]);
            barDataSet.setColors(mChartColors);
            List<Integer> colors = new ArrayList<>();
            for (int color : mChartColors) {
                colors.add(color);
            }
            barDataSet.setValueTextColors(colors);
            ArrayList<IBarDataSet> dataSets = new ArrayList<>();
            dataSets.add(barDataSet);
            BarData data = new BarData(dataSets);
            data.setValueTextSize(mTextSize);
            data.setBarWidth(0.9f);
            mChart.setData(data);
        }
    }


    public void setDrawValueAboveBar(boolean aboveBar) {
        ((BarChart)mChart).setDrawValueAboveBar(aboveBar);
    }

    */
/**
     * <p>设置bar宽度</p>
     * @param barWidth float
     *//*

    public void setBarWidth(float barWidth) {
        ((BarChart)mChart).getData().setBarWidth(barWidth);
    }
}

*/
