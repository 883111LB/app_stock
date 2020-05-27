package com.cvicse.stock.utils;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.util.List;

public class MyXFormatter implements IAxisValueFormatter {
    List<String> mValues;

    public MyXFormatter(List<String> values) {
        this.mValues = values;
    }

    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        return mValues.get(Math.min(Math.max((int) value, 0), mValues.size() - 1));
    }
}