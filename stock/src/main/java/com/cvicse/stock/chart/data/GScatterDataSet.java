package com.cvicse.stock.chart.data;

import com.cvicse.stock.chart.theme.ThemeManager;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.ScatterDataSet;

import java.util.ArrayList;
import java.util.List;

/**
 * 股票涨跌颜色重写
 * Created by liu_zlu on 2017/1/21 21:09
 */
public class GScatterDataSet extends ScatterDataSet {
    ArrayList<KLineEntity> kLineEntities;
    public GScatterDataSet(ArrayList<KLineEntity> kLineEntities,List<Entry> yVals, String label) {
        super(yVals, label);
        this.kLineEntities = kLineEntities;
    }

    @Override
    public int getColor(int index) {
        KLineEntity kLineEntity =  kLineEntities.get(index);
        if(kLineEntity.sign == 0){
            return ThemeManager.colorRed();
        } else {
            return ThemeManager.colorGreen();
        }
    }
}
