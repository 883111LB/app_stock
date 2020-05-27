package com.cvicse.stock.chart.utils;

import com.cvicse.stock.chart.data.TechChartType;
import com.mitake.core.request.OHLChartType;

/**
 * Created by liu_zlu on 2017/2/27 10:30
 */
public class StockTechType {
    public static final String DAY = OHLChartType.CHART_DAY;
    public static final String WEEK = OHLChartType.CHART_WEEK;
    public static final String MONTH = OHLChartType.CHART_MONTH;
    public static final String YEAR = OHLChartType.CHART_YEAR;
    public static final String ONE_MINUTE = OHLChartType.CHART_ONE;
    public static final String FIVE_MINUTE = OHLChartType.CHART_FIVE;
    public static final String FIFTEEN_MINUTE = OHLChartType.CHART_FIFTEEN;
    public static final String THIRTY_MINUTE = OHLChartType.CHART_THIRTY;
    public static final String SIXTY_MINUTE = OHLChartType.CHART_SIXTY;
    public static final String ONEHUNDREDTWENTY_MINUTE = OHLChartType.CHART_ONEHUNDREDTWENTY;

    public static TechChartType.Type asType(String type){
        switch (type) {
            case DAY:
                return TechChartType.Type.DAY;
            case WEEK:
                return TechChartType.Type.WEEK;
            case MONTH:
                return TechChartType.Type.MONTH;
            case FIVE_MINUTE:
                return TechChartType.Type.FIVE;
            case FIFTEEN_MINUTE:
                return TechChartType.Type.FIFTH;
            case THIRTY_MINUTE:
                return TechChartType.Type.THRITY;
            case SIXTY_MINUTE:
                return TechChartType.Type.SIXTY;

            case YEAR:
                return TechChartType.Type.YEAR;
            case ONE_MINUTE:
                return TechChartType.Type.ONE;
            case ONEHUNDREDTWENTY_MINUTE:
                return TechChartType.Type.ONEHUNDREDTWENTY;
        }
        return null;
    }
}
