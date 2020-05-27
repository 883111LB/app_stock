package com.cvicse.stock.chart.data;

import com.cvicse.stock.chart.theme.ThemeColor;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.data.CombinedData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.mitake.core.FundValueInfo;
import com.mitake.core.FundValueInfoItem;

import java.util.ArrayList;

/**
 * 基金净值数据帮助类
 * Created by liu_zlu on 2017/4/28 15:07
 */
public class FundValueDataHelper {

    private CombinedData combinedData;
    private ArrayList<FundValueInfoItem> fundValueInfoItems;
    private ArrayList<String> dates = new ArrayList<>();
    public void setRequestData(FundValueInfo requestData) {
        if(requestData == null || requestData.list == null || requestData.list.size() <= 0){
            return;
        }
        fundValueInfoItems = requestData.list;
        createChartData();
    }

    private String lastMonth = "";
    private void createChartData() {
        dates.clear();
        ArrayList<Entry> lineEntries = new ArrayList<>();
        FundValueInfoItem fundValueInfoItem;
        String tempMonth = null;
        for(int i = 0,length = getCount();i < length;i++) {
            fundValueInfoItem = fundValueInfoItems.get(i);
            tempMonth = fundValueInfoItem.NAVDATE.substring(5,7);
            dates.add(fundValueInfoItem.NAVDATE);
            lastMonth = tempMonth;
            lineEntries.add(new Entry(i,Float.parseFloat(fundValueInfoItem.UNITNAV)));
        }

        combinedData = new CombinedData();
        LineData lineData = new LineData();
        LineDataSet lineDataSet = new LineDataSet(lineEntries,"");
        lineDataSet.setColor(ThemeColor.blackWhite());
        lineDataSet.setDrawCircles(false);
        lineDataSet.setDrawValues(false);
        lineData.addDataSet(lineDataSet);
        combinedData.setData(lineData);
    }

    public CombinedData getChartData(){
        return combinedData;
    }
    public IAxisValueFormatter getValueFormatter() {
        return valueFormatter;
    }
    private IAxisValueFormatter valueFormatter = new IAxisValueFormatter() {
        @Override
        public String getFormattedValue(float value, AxisBase axis) {
            if(value < 0){
                return "";
            }
            return dates.get((int) value);
        }
    };

    public int getCount() {
        return fundValueInfoItems == null ? 0 : fundValueInfoItems.size();
    }

    public String getBottomLable(int value){
        if(dates == null || dates.size() <= value){
            return "";
        }
        return dates.get(value);
    }

    public String getLeftLable(int value){
        if(fundValueInfoItems == null || fundValueInfoItems.size() <= value){
            return "";
        }
        return Float.parseFloat(fundValueInfoItems.get(value).UNITNAV)+"";
    }
}
