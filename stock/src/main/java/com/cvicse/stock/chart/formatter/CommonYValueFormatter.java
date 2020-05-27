package com.cvicse.stock.chart.formatter;


import com.cvicse.stock.chart.data.KDataHelper;
import com.cvicse.stock.chart.data.TechChartType;
import com.cvicse.stock.chart.helper.StockTechState;
import com.cvicse.stock.chart.utils.MyUtils;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.text.DecimalFormat;

/**
 * k线图指标图Y轴数据提供类
 */
public class CommonYValueFormatter implements IAxisValueFormatter {
    private int unit;
    private int obvUnit;
    private String u = "";
    private String obvU = "";

    private DecimalFormat mFormat = new DecimalFormat("#0");
    private DecimalFormat mFormat1 = new DecimalFormat("#0.00");
    private TechChartType.KType kType = TechChartType.KType.VOLUME;

    private StockTechState typeHpler;
    private KDataHelper dataHelper;

    public CommonYValueFormatter(KDataHelper dataHelper, StockTechState typeHpler){
        this.dataHelper = dataHelper;
        this.typeHpler = typeHpler;
        notifyDataSetChanged();
    }
    public void setType(TechChartType.KType kType){
        if(kType == null){
            return;
        }
        this.kType = kType;
        switch (kType){
            case VOLUME:
                setFormat(dataHelper.unit);break;
            case OBV:
                setFormat(dataHelper.obvUnit);break;
            case AMO:
                setFormat(dataHelper.tranPriceUnit);break;
            case MACD:
            case DMI:
            case BOLL:
            case RSI:
//            case SAR:
            case DMA:
            case WR:
            case KDJ:
            case VR:
            case CR:
            case BIAS:
            case BBI:
            case CCI:
            case MTM:
            case ROC:
            case BRAR:
            case NVIPVI:
            case PSY:
                break;
        }
    }

    private void setFormat(int unit) {
        if (unit == 1) {
            mFormat = new DecimalFormat("#0");
        } else {
            mFormat = new DecimalFormat("#0.00");
        }
        this.unit = (int) Math.pow(10, unit);
        this.u= MyUtils.getVolUnit(this.unit);
    }

    private void setObvFormat(int unit) {
        if (unit == 1) {
            mFormat = new DecimalFormat("#0");
        } else {
            mFormat = new DecimalFormat("#0.00");
        }
        this.obvUnit = (int) Math.pow(10, unit);
        this.obvU= MyUtils.getObvUnit(this.unit);
    }

    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        String retStr = "";
        switch (kType){
            case VOLUME:
            case AMO:
                if(value == 0){
                    retStr =  u;
                } else {
                    value = value / unit;
                    retStr = mFormat.format(value);
                }
                break;
            case OBV:
                if(value == axis.mEntries[0]){
                    retStr =  u;  //   retStr =  obvU;  // old 2018-9-6
                } else {
                    value = value / unit;  // value = value / obvUnit;  // old 2018-9-6
                    retStr = mFormat.format(value);
                }
                break;

//            case SAR:
            case MACD:
            case DMA:
            case BOLL:
            case RSI:
            case VR:
            case CR:
            case DMI:
            case BIAS:
            case BBI:
            case CCI:
            case MTM:
            case ROC:
            case BRAR:
            case NVIPVI:
            case PSY:
                retStr = mFormat1.format(value);break;
            case KDJ:
            case WR:
                retStr = (int)value + "";break;
        }
        return retStr;
    }

    public void notifyDataSetChanged() {
        setType(typeHpler.ktype);
    }
}
