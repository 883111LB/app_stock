package com.cvicse.stock.chart.data;

import com.github.mikephil.charting.data.CandleData;
import com.github.mikephil.charting.interfaces.datasets.ICandleDataSet;

import java.util.List;

/**
 * Created by liu_zlu on 2017/1/21 21:57
 */
public class GCandleData extends CandleData {
    public GCandleData() {
        super();
    }

    public GCandleData(List<ICandleDataSet> dataSets) {
        super(dataSets);
    }

    public GCandleData(ICandleDataSet... dataSets) {
        super(dataSets);
    }
}
