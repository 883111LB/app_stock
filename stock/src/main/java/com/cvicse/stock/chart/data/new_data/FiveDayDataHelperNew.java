package com.cvicse.stock.chart.data.new_data;

import com.cvicse.base.utils.NumberUtils;
import com.cvicse.stock.chart.data.MinuteEntity;
import com.cvicse.stock.chart.data.OHLCItemBo;
import com.cvicse.stock.chart.data.TimezoneEntity;
import com.cvicse.stock.chart.formatter.CommonValueFormatter;
import com.cvicse.stock.chart.theme.ThemeManager;
import com.cvicse.stock.chart.utils.MyUtils;
import com.cvicse.stock.chart.utils.TimezoneUtils;
import com.cvicse.stock.util.FormatUtils;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.CombinedData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.mitake.core.OHLCItem;
import com.mitake.core.QuoteItem;
import com.mitake.core.bean.exchange.TimeZone;
import com.mitake.core.response.ChartResponse;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 五日图数据帮助类
 * 数据处理，提供高亮标志数据
 * 没有数据补点功能
 * Created by liu_zlu on 2017/3/2 17:20
 */
public class FiveDayDataHelperNew {
    private ArrayList<OHLCItemBo> chartItemsList;
    public TimezoneEntity timezoneEntity;
    private QuoteItem quoteItem;
    private float maxClosePrice;// 最大值
    private float minClosePrice;
    private float referencePrice = 0;// 中轴线值（取五日前收盘价）
    private float showMaxPrice;// 图表最大值
    private float showMinPrice;// 图表最小值
    private String PricePercentText;

    // 处理过的数据
    private ArrayList<MinuteEntity> minuteEntities;
    // 底部图表数据
    private CombinedData bottomData;
    // 顶部图表数据
    private CombinedData topData;
    // 成交量单位
    public String unitStr;

    // 数据的涨跌颜色
    private ArrayList<Integer> colors = new ArrayList<>();

    // 原始数据
    private ChartResponse chartResponse;

    public FiveDayDataHelperNew() {

    }
    public QuoteItem getQuoteItem(){
        return quoteItem;
    }

    /**
     * 提供底部标记数据
     * @param position
     * @return
     */
    public String getBottomLable(int position){
        MinuteEntity minuteEntity = minuteEntities.get(position);
        if( null==minuteEntity.bottomLable ){

            String datetime = minuteEntity.datetime;
            datetime = datetime.substring(0,4)+"/"+datetime.substring(4,6)+"/"+datetime.substring(6,8)+ " " + datetime.substring(8,10)+":"+datetime.substring(10,12);
            minuteEntity.bottomLable = datetime;
        }
        return minuteEntity.bottomLable;
    }
    /**
     * 提供底部标记左侧边栏数据
     * @param position
     * @return
     */
    public String getBottomLeftLable(int position){
        MinuteEntity minuteEntity = minuteEntities.get(position);
        if(null ==minuteEntity.bleftLable ){
            minuteEntity.bleftLable = MyUtils.getVol(minuteEntity.volume);
        }
        return minuteEntity.bleftLable;
    }

    /**
     * 提供顶部标记左侧边栏数据
     * @param position
     * @return
     */
    public String getTopLeftLable(int position){
        MinuteEntity minuteEntity = minuteEntities.get(position);
        return minuteEntity.averagePriceStr;
    }

    /**
     * 提供顶部标记右侧边栏数据
     * @param position
     * @return
     */
    public String getTopRightLable(int position){
        MinuteEntity minuteEntity = minuteEntities.get(position);
        if( null==minuteEntity.trightLable ){
            float center = (getDataMax()+getDataMin())/2f;

            DecimalFormat df = new DecimalFormat("0.00");
            minuteEntity.trightLable = df.format((minuteEntity.averagePrice-center)/ minuteEntity.averagePrice*100)+"%";
        }
        return minuteEntity.trightLable;
    }

    /**
     * 设置股票行情数据
     */
    public void setQuoteItem(QuoteItem quoteItem){
        //根据时间过滤已经返回的数据，保留最新时间
        if( null!=this.quoteItem ){
            int date = NumberUtils.parseInt(quoteItem.datetime);
            int date1 = NumberUtils.parseInt(this.quoteItem.datetime);
            if(date < date1){
                return;
            }
        }
        this.quoteItem = quoteItem;
        //ohlcItems这是原始数据
        if( null!= chartResponse && null!= quoteItem){
            postData(chartResponse);
        }
    }

    //添加基本数据
    public void addRequestData(ChartResponse chartResponse){
        this.chartResponse = chartResponse;

        //要保证ohlcItems和quoteItem同时有数据
        if( null!=quoteItem  && null!= chartResponse){
            postData(chartResponse);
        }
    }

    /**
     * 进行数据计算
     * @param chartResponse
     */
    public void postData(ChartResponse chartResponse) {
        if (null == chartResponse || null == chartResponse.historyItems|| chartResponse.historyItems.isEmpty()) {
            return;
        }
        chartItemsList(chartResponse);
        MinuteEntity minuteEntity;
        String time = "";
        OHLCItemBo ohlcitem = null;
        float lastCjprice = 0;
        String market = this.quoteItem.market;
        CopyOnWriteArrayList<OHLCItem> historyItems = chartResponse.historyItems;
        for (int i = 0, size = historyItems.size(); i < size; i++) {
            OHLCItem ohlcItem = historyItems.get(i);
            ohlcitem = new OHLCItemBo(ohlcItem);
            minuteEntity = new MinuteEntity();

            minuteEntity.datetime = ohlcitem.datetime;
            minuteEntity.volume = CommonValueFormatter.isNorm(ohlcitem.tradeVolume) ?  Float.parseFloat(ohlcitem.tradeVolume): 0.0f;

            float value = NumberUtils.parseFloat(ohlcitem.closePrice) - NumberUtils.parseFloat(ohlcitem.reference_price);
            ohlcitem.change = CommonValueFormatter.formatValue(market,quoteItem.subtype,value);
            ohlcitem.changeRate = CommonValueFormatter.formatValue(market,quoteItem.subtype,(NumberUtils.parseFloat(ohlcitem.change) /  NumberUtils.parseFloat(ohlcitem.reference_price)) *100 );

            minuteEntity.averagePrice = NumberUtils.parseFloat(ohlcitem.averagePrice);
            minuteEntity.averagePriceStr = ohlcitem.closePrice;
            minuteEntity.cjpriceStr=ohlcitem.closePrice;
            minuteEntity.cjprice = NumberUtils.parseFloat(minuteEntity.cjpriceStr);

            time = ohlcitem.datetime.substring(8);
            ohlcitem.time = time.substring(0,2)+":"+time.substring(2);
            minuteEntity.time = Integer.parseInt(time);

            minuteEntities.add(minuteEntity);
            chartItemsList.add(ohlcitem);

            if(i == 0){
                colors.add(ThemeManager.colorRed());
            } else {
                if(minuteEntity.cjprice > lastCjprice){//涨
                    colors.add(ThemeManager.colorRed());
                } else {
                    colors.add(ThemeManager.colorGreen());//跌
                }
            }
            lastCjprice = minuteEntity.cjprice;
        }
        String str = caculFive(chartResponse.dayList,timezoneEntity.tradingTime);
        createChartData(str);
    }

    /**
     * 获取数据数量
     * @return
     */
    public int getCount() {
        return minuteEntities.size();
    }

    /**
     * 创建图表数据
     */
    public void createChartData(String fiveStr) {
        float max = Float.MIN_VALUE;
        ArrayList<BarEntry> barEntries = new ArrayList<>();
        ArrayList<Entry> linecjEntries = new ArrayList<Entry>();
        ArrayList<Entry> arrayList = new ArrayList<>();
        int lastTime = 0;
        Entry lastEntry = null;
        Entry preEntry = null;
        for (int i = 0,size = minuteEntities.size(); i < size; i++) {
            MinuteEntity minuteEntity = minuteEntities.get(i);
            max = Math.max(minuteEntity.volume, max);

            barEntries.add(new BarEntry(i, minuteEntity.volume));
            float averagePrice = minuteEntity.averagePrice;
            if(lastTime > minuteEntity.time || fiveStr.contains(","+i+",")){//保证每天数据的连贯性，如15:30和第二天9:30要把两边数据分开
//            if(fiveStr.contains(","+i+",")){//保证每天数据的连贯性，如15:30和第二天9:30要把两边数据分开
                if(null != lastEntry){
                    //设置数据不连续
                    lastEntry.setContinuous(false);
                }
                if( null!=preEntry ){
                    preEntry.setContinuous(false);
                }
            }
            linecjEntries.add(preEntry = new Entry(i,minuteEntity.cjprice));
            if( averagePrice != 0.0 ){
                arrayList.add(lastEntry = new Entry(i, averagePrice));
            }
            lastTime = minuteEntity.time;
        }
        unitStr = MyUtils.getVolUnit(max/100);
        createBarData(barEntries);
        createTopData(arrayList,linecjEntries);
    }

    private String caculFive(ArrayList<String> dateDay,Map<String, List<TimeZone>> listMap){
        StringBuilder sBuilder = new StringBuilder();
        int sum = 0;
        String pattern = "yyyyMMddHHmm";
        for (int i=0,size = dateDay.size();i<size;i++) {
            List<TimeZone> timeZoneList = listMap.get(dateDay.get(i));
            // 计算每日总根数
            if(null!=timeZoneList && !timeZoneList.isEmpty()){
                for (TimeZone timeZone : timeZoneList) {
                    long close = FormatUtils.parseDate(timeZone.closeTime, pattern).getTime();
                    long open = FormatUtils.parseDate(timeZone.openTime, pattern).getTime();
                    sum += Math.abs((close-open)/1000/60);
                }
                sBuilder.append(",").append(sum);
            }
        }
        sBuilder.append(",");
        return sBuilder.toString();
    }

    /**
     * 创建底部图表数据
     * @param barEntries
     * @return
     */
    public CombinedData createBarData(ArrayList<BarEntry> barEntries) {
        bottomData = new CombinedData();
        BarData barData = new BarData();
        BarDataSet barDataSet = new BarDataSet(barEntries, "成交量");
        //barDataSet.setBarSpacePercent(50); //bar空隙
        barDataSet.setHighlightEnabled(true);
        barDataSet.setHighLightAlpha(255);
        barDataSet.setHighLightColor(ThemeManager.colorWhiteBlack());
        barDataSet.setDrawValues(false);
        // barDataSet.setColor(Color.RED);
        barDataSet.setColors(colors);
        barData.addDataSet(barDataSet);
        bottomData.setData(barData);
        return bottomData;
    }

    /**
     * 创建顶部图表数据
     * @param lineAverageEntries
     * @param linecjEntries
     * @return
     */
    public CombinedData createTopData(ArrayList<Entry> lineAverageEntries,ArrayList<Entry> linecjEntries) {
        topData = new CombinedData();
        LineData lineData = new LineData();
        LineDataSet lineCloseDataSet = new LineDataSet(linecjEntries, "成交价");
        lineCloseDataSet.setLineWidth(0);
        lineCloseDataSet.setColor(ThemeManager.colorWhiteBlack());
        lineCloseDataSet.setDrawCircles(false);
        lineCloseDataSet.setHighlightEnabled(true);
        lineCloseDataSet.setHighLightColor(ThemeManager.colorWhiteBlack());
        // lineCloseDataSet.enableDashedHighlightLine(10f, 5f, 0f);
        lineCloseDataSet.setDrawValues(false);
        lineCloseDataSet.setDrawFilled(true);  // 设置绘制阴影区域
        lineCloseDataSet.setFillAlpha(30);
        lineData.addDataSet(lineCloseDataSet);

        LineDataSet lineDataSet = new LineDataSet(lineAverageEntries, "均价");
        lineDataSet.setColor(ThemeManager.colorRed());
        lineDataSet.setDrawCircles(false);
        lineDataSet.setHighlightEnabled(false);
        // lineDataSet.enableDashedHighlightLine(10f, 5f, 0f);
        lineDataSet.setDrawValues(false);
        // lineDataSet.setDrawHorizontalHighlightIndicator(true);
        // lineDataSet.setFormLineDashEffect(new DashPathEffect(new float[]{10f, 5f}, 0f));
        lineData.addDataSet(lineDataSet);
        topData.setData(lineData);
        return topData;
    }

    /**
     * 获取所有日期交易时间段
     * @return
     */
    public Map<String, List<TimeZone>> getTradeTime(){
        if( null == timezoneEntity || null == timezoneEntity.tradingTime){
            timezoneEntity = TimezoneUtils.setTimezoneEntity(chartResponse,quoteItem);
        }
        return null == timezoneEntity ? null : timezoneEntity.tradingTime;
    }

    /**
     * 返回五日图五天日期数据
     * @return
     */
    public ArrayList<String> getDateDay(){
        return null== chartResponse ? null: chartResponse.dayList;
    }

    /**
     * 返回最小涨跌比率
     * @return
     */
    public float getPercentMin() {
        return -NumberUtils.parseFloat(PricePercentText);
    }

    /**
     * 返回最大涨跌比率
     * @return
     */
    public float getPercentMax() {
        return NumberUtils.parseFloat(PricePercentText);
    }

    public int getXMin(){
        return 0;
    }

    public int getXMax(){
        return null == chartResponse ? 0 : chartResponse.totalTimes;
    }

    /**
     * 返回左侧最小
     * @return
     */
    public float getDataMin() {
        return  showMinPrice;
    }

    /**
     * 返回左侧最大
     * @return
     */
    public float getDataMax() {
        return showMaxPrice;
    }

    /**
     * 返回顶部图表数据
     * @return
     */
    public CombinedData getTopChartData() {
        return topData;
    }

    /**
     * 返回底部图表数据
     * @return
     */
    public CombinedData getBottomChartData() {
        return bottomData;
    }

    public void reset() {

    }

    public IAxisValueFormatter getIAxisValueFormatter() {
        return null;
    }

    public ArrayList<OHLCItemBo> getChartItemsList() {
        return chartItemsList;
    }

    public OHLCItemBo getLastData() {
        return null ==chartItemsList  || chartItemsList.isEmpty() ? null:chartItemsList.get(chartItemsList.size()-1);
    }

    /**
     * 最大值、最小值、涨跌比率
     */
    private void chartItemsList(ChartResponse chartResponse) {
        minuteEntities = new ArrayList<>();
        chartItemsList = new ArrayList<>();
        maxClosePrice = chartResponse.maxClosePrice;
        minClosePrice = chartResponse.minClosePrice;
        String refStr = chartResponse.historyItems.get(0).reference_price;
        if (null != refStr) {
            referencePrice = Float.valueOf(chartResponse.historyItems.get(0).reference_price);
        }

        float preCloceFloat = (maxClosePrice + this.minClosePrice) / 2f;
        float tmp1 = this.maxClosePrice - preCloceFloat;
        float tmp2 = preCloceFloat -  this.minClosePrice;
        tmp1 = tmp1 > tmp2 ? tmp1 : tmp2;

        DecimalFormat df = new DecimalFormat("0.00");
        this.PricePercentText = df.format(tmp1 / preCloceFloat * 100.0F);

        if( null == timezoneEntity || null == timezoneEntity.tradingTime){
            timezoneEntity =  TimezoneUtils.setTimezoneEntity(chartResponse,quoteItem);
        }

        // 根据中轴线计算图表展示的最大最小值
        float maxD = maxClosePrice - referencePrice;// 最大值减中轴
        float minD = referencePrice - minClosePrice;// 中轴减最小值
        if (maxD > minD) {
            showMaxPrice = maxClosePrice;
            showMinPrice = minClosePrice - (maxD - minD);
        } else {
            showMinPrice = minClosePrice;
            showMaxPrice = maxClosePrice + (minD - maxD);
        }

    }

    /**
     * 限制线（取第一天的参考价/昨收价）
     */
    public String getTopAxisLimitLine(ChartResponse chartResponse) {
        String limit = "";
        if (null == chartResponse.historyItems || null == chartResponse.historyItems.get(0)) {
            return limit;
        }
        limit = chartResponse.historyItems.get(0).reference_price;
        return limit;
    }
}