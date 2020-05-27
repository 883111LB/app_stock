package com.cvicse.stock.chart.data;

import android.text.TextUtils;

import com.cvicse.base.utils.NumberUtils;
import com.cvicse.base.utils.StringUtils;
import com.cvicse.stock.chart.formatter.CommonValueFormatter;
import com.cvicse.stock.chart.theme.ThemeManager;
import com.cvicse.stock.chart.utils.MyUtils;
import com.cvicse.stock.chart.utils.Utility;
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
import com.mitake.core.disklrucache.L;
import com.mitake.core.util.FormatUtility;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * 五日图数据帮助类
 * 数据处理，提供高亮标志数据
 * 其中对数据进行补点
 * Created by liu_zlu on 2017/3/2 17:20
 */
public class FiveDayDataHelper {
    // 时间区域
    private TimezoneEntity timezoneEntity;
    // 处理过的数据
    private ArrayList<MinuteEntity> minuteEntities;
    // public int unit = 1;
    // 底部图表数据
    private CombinedData bottomData;
    // 顶部图表数据
    private CombinedData topData;
    // 成交量单位
    public String unitStr;

    // 数据的涨跌颜色
    private ArrayList<Integer> colors = new ArrayList<>();

    // 原始数据
    private ArrayList<OHLCItemBo> ohlcItems;

    public FiveDayDataHelper() {

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
        if(minuteEntity.bleftLable == null){
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
        if(minuteEntity.trightLable == null){
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
//        timezoneEntity = TimezoneUtils.getEntity(quoteItem);  // old 2018-8-2

        //ohlcItems这是原始数据
        if( null!= ohlcItems && !StringUtils.isEmpty(quoteItem.preClosePrice)){
            postData(ohlcItems);
        }
    }

    //添加基本数据
    public void addRequestData(ArrayList<OHLCItemBo> ohlcItems){
        this.ohlcItems = ohlcItems;
        //要保证ohlcItems和quoteItem同时有数据
        if( null!=quoteItem  && !StringUtils.isEmpty(quoteItem.preClosePrice)){
            postData(ohlcItems);
        }
    }

    /**
     * 进行数据计算
     * @param ohlcItems
     */
    public void postData(ArrayList<OHLCItemBo> ohlcItems) {
        if (null == ohlcItems || ohlcItems.isEmpty()) {
            return;
        }
//        timezoneEntity = TimezoneUtils.getTimezoneEntity();
        chartItemsList(ohlcItems);

        minuteEntities = new ArrayList<>();
        MinuteEntity minuteEntity;
        String time = "";
        OHLCItemBo ohlcitem = null;
        float lastCjprice = 0;
        for (int i = 0;i < this.chartItemsList.size();i++) {
            ohlcitem = this.chartItemsList.get(i);
            minuteEntity = new MinuteEntity();
            minuteEntity.datetime = ohlcitem.datetime;
            minuteEntity.volume = CommonValueFormatter.isNorm(ohlcitem.tradeVolume) ?  Float.parseFloat(ohlcitem.tradeVolume): 0.0f;

            String market = this.quoteItem.market;
            float value = NumberUtils.parseFloat(ohlcitem.closePrice) - NumberUtils.parseFloat(ohlcitem.reference_price);
            ohlcitem.change = CommonValueFormatter.formatValue(market,quoteItem.subtype,value);
            ohlcitem.changeRate = CommonValueFormatter.formatValue(market,quoteItem.subtype,(NumberUtils.parseFloat(ohlcitem.change) /  NumberUtils.parseFloat(ohlcitem.reference_price)) *100 );

            minuteEntity.averagePrice = NumberUtils.parseFloat(FormatUtility.formatPrice(ohlcitem.averagePrice, this.quoteItem.market, this.quoteItem.subtype));  // odl 2018-8-2
            minuteEntity.averagePriceStr = FormatUtility.formatPrice(ohlcitem.closePrice, this.quoteItem.market, this.quoteItem.subtype);  //old 2018-8-2
//            minuteEntity.averagePrice = NumberUtils.parseFloat(ohlcitem.averagePrice);
//            minuteEntity.averagePriceStr = ohlcitem.averagePrice;
            minuteEntity.cjpriceStr=ohlcitem.closePrice;
            minuteEntity.cjprice = NumberUtils.parseFloat(minuteEntity.cjpriceStr);

            time = ohlcitem.datetime.substring(8);
            ohlcitem.time = time.substring(0,2)+":"+time.substring(2);
            minuteEntity.time = Integer.parseInt(time);
            minuteEntities.add(minuteEntity);
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
        float max = 0;
        for (int i = 0; i < minuteEntities.size(); i++) {
            minuteEntity = minuteEntities.get(i);
            max = Math.max(minuteEntity.volume, max);
        }
        unitStr = MyUtils.getVolUnit(max/100);
        createChartData();
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
    public void createChartData() {
        ArrayList<BarEntry> barEntries = new ArrayList<>();
        ArrayList<Entry> linecjEntries = new ArrayList<Entry>();
        ArrayList<Entry> arrayList = new ArrayList<>();
        int lastTime = 0;
        Entry lastEntry = null;
        Entry preEntry = null;
        for (int i = 0; i < minuteEntities.size(); i++) {
            MinuteEntity minuteEntity = minuteEntities.get(i);
            barEntries.add(new BarEntry(i, minuteEntity.volume));

            float averagePrice = minuteEntity.averagePrice;
            if(lastTime > minuteEntity.time){//保证每天数据的连贯性，如15:30和第二天9:30要把两边数据分开
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
        //axisLeftBar.setValueFormatter(new VolFormatter((int) Math.pow(10, u)));
        createBarData(barEntries);
        createTopData(arrayList,linecjEntries);
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

    public int getTradeTime(){
//        return TradeTime;
        return null == timezoneEntity ? 0 : timezoneEntity.totalTimes;
    }

    /**
     * 返回五日图五天日期数据
     * @return
     */
    public ArrayList<String> getDateDay(){
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add(databeginText);
        arrayList.add(dataSecondText);
        arrayList.add(dataThirdText);
        arrayList.add(dataForthText);
        arrayList.add(dataFiveText);
//        return arrayList;   //old 2018-8-2
        return null== timezoneEntity ? null: timezoneEntity.dayList;
    }

    /**
     * 返回最小涨跌比率
     * @return
     */
    public float getPercentMin() {
        return -getFloat(PricePercentText);
    }

    /**
     * 返回最大涨跌比率
     * @return
     */
    public float getPercentMax() {
        return getFloat(PricePercentText);
    }

    public int getXMin(){
        return 0;
    }

    /**
     * 根据交易时间，得到每个刻度间的距离
     * @return
     */
    public int getXMax(){
//        return( this.TradeTime + 1) * 5;   //old 2018-8-2
        return null == timezoneEntity ? 0 : timezoneEntity.totalTimes;
    }

    /**
     * 返回左侧最小
     * @return
     */
    public float getDataMin() {
        return  getFloat(bottomPriceText);
    }

    /**
     * 返回左侧最大
     * @return
     */
    public float getDataMax() {
        return getFloat(topPriceText);
    }

    private float getFloat(String num){
        return StringUtils.isEmpty(num) ? 0:NumberUtils.parseFloat(num);
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

    private boolean ifAddedDay = false;
    private ArrayList<OHLCItemBo> chartItemsList;
    private QuoteItem quoteItem;
    private ArrayList<Integer> removeCache;
    private float maxVolume;
    private float maxClosePrice;
    private float minClosePrice;
    private String topPriceText;
    private String bottomPriceText;
    private String PricePercentText;

    // 五日时间，第一天
    private String databeginText;
    //五日时间，第二天
    private String dataSecondText;
    //五日时间，第三天
    private String dataThirdText;
    // 五日时间，第四天
    private String dataForthText;
    // 五日时间，第五天
    private String dataFiveText;

    private int HughPlateTime;
    private int FirstPlateTime;
    private int TradeTime;

    /**
     * 后台返回的数据不连续，（只有分时，五日不连续），填补缺失数据和过滤多余数据
     */
    private void chartItemsList(ArrayList<OHLCItemBo> chartItemsList) {
        this.chartItemsList = chartItemsList;
        this.removeCache = new ArrayList();

        if (( null== this.chartItemsList) || this.chartItemsList.isEmpty() || ( null==this.chartItemsList.get(0) ) ||   null==this.chartItemsList.get(0).datetime ) {
            return;
        }

        /******************** 计算中间值、最大值、最小值********************/
        float preCloceFloat = caluData();

        /******************** 计算五日时间********************/
        caluFiveDate();

        for (int i = this.removeCache.size() - 1; i >= 0; i--) {
            int k =  this.removeCache.get(i).intValue();
            this.chartItemsList.remove(k);
        }
        this.removeCache.clear();

        /******************** 保证数据完整性，对数据进行补点  ********************/
        complementPointData(preCloceFloat);
    }

    /**
     * 根据不同市场，计算每天根数、五日时间
     */
    private void caluFiveDate() {
        this.removeCache.clear();
        long str = -1;
        long timeTemp = 0;
        int dataSize = this.chartItemsList.size();
        for (int i = 0; i < dataSize - 1; i++) {
            if (str == -1) {
                str =  this.chartItemsList.get(i).datetimeL;
            }
            if (timeTemp - (timeTemp = this.chartItemsList.get(i + 1).datetimeL) >= 0L)
                this.removeCache.add(Integer.valueOf(i + 1));
            else {
                str = -1;
            }
        }
        dataSize = this.removeCache.size();
        for (int k = dataSize - 1; k >= 0; k--) {
            int temp = this.removeCache.get(k).intValue();
            this.chartItemsList.remove(temp);
        }
        this.removeCache.clear();
        dataSize = this.chartItemsList.size();
        String market = this.quoteItem.market+"" ;
        String subtype = this.quoteItem.subtype+"";
        switch (market) {
            case "hk":
                this.HughPlateTime = 60;
                this.FirstPlateTime = 150;
                this.TradeTime = 330;      // 交易时间。
                this.FirstPlateTime += 1;
                this.TradeTime += 2;
                break;
            case "sz":
                this.FirstPlateTime = 120;
                this.HughPlateTime = 90;
                this.TradeTime = 240;
                this.FirstPlateTime += 1;
                this.TradeTime += 2;
                break;
            case "sh":
                this.FirstPlateTime=120;
                this.HughPlateTime=90;
                this.TradeTime="1311".equals(subtype)?270:240;
                this.FirstPlateTime += 1;
                this.TradeTime += 2;
                break;
            default:
                this.FirstPlateTime = 120;
                this.HughPlateTime = 90;
                this.TradeTime = "tf".equals(subtype) ? 270 : 240;  // 国债交易数据根数不一样
                this.FirstPlateTime += 1;
                this.TradeTime += 2;
        }

        this.databeginText = (this.dataSecondText = this.dataThirdText = this.dataForthText = this.dataFiveText = "                     ");
        this.maxClosePrice = (this.maxVolume = 0L);
        this.minClosePrice = 9223372036854775807L;
        try {
           /******************** 计算五日日期开始  ********************/
            int tmpDay = 4;
            int startFiveDay = -1;
            String nextFormateTime = this.chartItemsList.get(dataSize-1).datetime.substring(0, 8);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");
            for (int i = dataSize - 1; i >= 1; i--) {
                if ((tmpDay == 0) && (startFiveDay == -1)) {
                    startFiveDay = i;
                } else {
                    if (startFiveDay != -1) {
                        break;
                    }

                    String nowFormateTime =  this.chartItemsList.get(i).datetime.substring(0, 8);
                    if (!nextFormateTime.equals(nowFormateTime)) {
                        tmpDay--;
                    }
                    nextFormateTime = nowFormateTime;
                }
            }
            if (startFiveDay == -1) {
                startFiveDay = 0;
            }

            // 国债期货开盘时间不一样
            String[] temp = timezoneEntity.openingTimeONE.split(":");
            String nowFormateTime = this.chartItemsList.get(startFiveDay).datetime.substring(0, 8)
                    + "" + temp[0] + "" + temp[1];
            int tempOpen = Integer.parseInt(temp[0]+temp[1]); // 开盘时间

            Date dt1 = sdf.parse(nowFormateTime);
            int tmpcaseDate = 0;
            int tmpDayOffset = 0;
            int startDay = Integer.parseInt( this.chartItemsList.get(startFiveDay).datetime.substring(0, 8));

            for (int i = 0; i < dataSize; i++) {
                String datetime = this.chartItemsList.get(i).datetime;
                String parseDate = datetime;
                Date dt2 = sdf.parse(parseDate);
                long time = dt2.getTime() - dt1.getTime();
                time /= 60000L;

                boolean inDayLimit = false;

                if (Integer.parseInt(datetime.substring(0, 8)) >= startDay) {
//                    inDayLimit = 930 <= Integer.parseInt(this.chartItemsList.get(i).datetime.substring(8, 12));
                    // 不能写固定值930
                    inDayLimit = tempOpen <= Integer.parseInt(datetime.substring(8, 12));
                }

                if (!inDayLimit && (i > 0) &&
                        (Long.parseLong(datetime) < Long.parseLong( this.chartItemsList.get(i - 1).datetime))) {
                    inDayLimit = true;
                }

                if (inDayLimit) {
                    int dayOffset = (int) time / 1440;
                    if (tmpDayOffset < dayOffset) {
                        tmpDayOffset = dayOffset;
                        tmpcaseDate++;
                    }

                    switch (tmpcaseDate) {
                        case 0:
                            this.databeginText = datetime;
                            break;
                        case 1:
                            this.dataSecondText = datetime;
                            break;
                        case 2:
                            this.dataThirdText = datetime;
                            break;
                        case 3:
                            this.dataForthText = datetime;
                            break;
                        case 4:
                            this.dataFiveText = datetime;
                            break;
                    }
                } else {
                    // 舍弃错误数据
                    this.removeCache.add(Integer.valueOf(i));
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        /******************** 计算五日日期结束  ********************/
    }

    /**
     * 计算中间值，以及最大、最小值
     */
    private float caluData() {
        /******************** 计算中间值，以及最大、最小值开始  ********************/
        OHLCItemBo ohlcItem = null;
        float closeTemp = 0;
        float averageTemp = 0;
        for (int i = 0; i < this.chartItemsList.size(); i++) {
            ohlcItem = this.chartItemsList.get(i);

            this.maxVolume = Math.max(this.maxVolume,ohlcItem.tradeVolumeL);  // old
//            this.maxVolume = Math.max(this.maxVolume,isStandard(ohlcItem.tradeVolume) ? Double.parseDouble(ohlcItem.tradeVolume) : 0.0 ); // new

            closeTemp = this.chartItemsList.get(i).closePriceF;
            averageTemp = this.chartItemsList.get(i).averagePriceF;

//            this.maxClosePrice = Math.max(maxClosePrice, Math.max(closeTemp,averageTemp));  //old
//            this.minClosePrice = Math.min(minClosePrice, Math.min(closeTemp,averageTemp));

            maxClosePrice = 0.0 == averageTemp ? Math.max(maxClosePrice,closeTemp): Math.max(Math.max(maxClosePrice,closeTemp),averageTemp);  // new
            minClosePrice = 0.0 == averageTemp ? Math.min(minClosePrice,closeTemp): Math.min(Math.min(minClosePrice,closeTemp),averageTemp);
        }

        float preCloceFloat = (maxClosePrice + this.minClosePrice) / 2f;
        if ((null !=quoteItem.preClosePrice ) && (!TextUtils.isEmpty(quoteItem.preClosePrice))) {
//            preCloceFloat = Float.parseFloat(this.quoteItem.srcPreClosePrice);   // old 2018-8-2
            preCloceFloat = Float.parseFloat(this.quoteItem.preClosePrice);
        }
        float tmp1 = this.maxClosePrice - preCloceFloat;
        float tmp2 = preCloceFloat -  this.minClosePrice;

        tmp1 = tmp1 > tmp2 ? tmp1 : tmp2;

//        String topPrice = market.equals("cff") ? (preCloceFloat + tmp1) + "" :(preCloceFloat + tmp1) + "";   // old 2018-8-2
//        String bottomPrice = market.equals( "cff") ? (preCloceFloat - tmp1) + "" : (preCloceFloat - tmp1) + "";  // old 2018-8-2
//        this.topPriceText = (preCloceFloat == 0.0F ? "-" : FormatUtility.formatPrice(topPrice, market, subtype));
//        this.bottomPriceText = (preCloceFloat == 0.0F ? "-" : FormatUtility.formatPrice( bottomPrice, market, subtype));
        String topPrice = (preCloceFloat + tmp1) + "";
        String bottomPrice =(preCloceFloat - tmp1) + "";

        this.topPriceText = (preCloceFloat == 0.0F ? "-" : topPrice);
        this.bottomPriceText = (preCloceFloat == 0.0F ? "-" : bottomPrice);

        DecimalFormat df = new DecimalFormat("0.00");
        this.PricePercentText = df.format(tmp1 / preCloceFloat * 100.0F);
        /******************** 计算中间值，以及最大、最小值结束  ********************/

        return preCloceFloat;
    }

    /**
     * 保证数据完整性，对数据进行补点
     */
    private void complementPointData(float preCloceFloat) {
        ArrayList<String> dayList = new ArrayList<>();
        dayList.add(databeginText);
        dayList.add(dataSecondText);
        dayList.add(dataThirdText);
        dayList.add(dataForthText);
        dayList.add(dataFiveText);

        String openingTimeONE = timezoneEntity.openingTimeONE;
        String CloseTimeONE = timezoneEntity.CloseTimeONE;
        String openingTimeTWO = timezoneEntity.openingTimeTWO;
        String CloseTimeTWO = timezoneEntity.CloseTimeTWO;

        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        try {
            Date openingTimeONEformat = format.parse(openingTimeONE);
            Date CloseTimeONEformat = format.parse(CloseTimeONE);
            Date openingTimeTWOformat = format.parse(openingTimeTWO);
            Date CloseTimeTWOformat = format.parse(CloseTimeTWO);

            int mFirstPlateTime = (int) (CloseTimeONEformat.getTime() - openingTimeONEformat.getTime()) / 1000 / 60;
            mFirstPlateTime++;
            int mSecondPlateTime = (int) (CloseTimeTWOformat.getTime() - openingTimeTWOformat.getTime()) / 1000 / 60;
            mSecondPlateTime++;
            int mTradeTime = mFirstPlateTime + mSecondPlateTime;

            Date nowTime = new Date();
            if ((this.chartItemsList != null) && (this.chartItemsList.size() > 0)) {
                nowTime = Utility.calculateStringEndWithHHmm(Stringinsert(((OHLCItemBo) this.chartItemsList.get(this.chartItemsList.size() - 1)).datetime.substring(8, 12), ":", 2));
            }
            if (this.quoteItem.datetime != null) {
                nowTime = Utility.calculateStringEndWithHHmm(Stringinsert(this.quoteItem.datetime.substring(8, 12), ":", 2));
            }

            int realDays = 0;
            for (int i = 0; i < dayList.size(); i++) {
                if (!dayList.get(i).equals("                     ")) {
                    realDays++;
                }
            }
            //作用为如果没有今天的数据，把今天当做最后一天，
            if ((this.quoteItem != null) && (this.quoteItem.datetime != null) &&
                    (realDays > 0) && (realDays < 5) && (! dayList.get(realDays - 1).substring(0, 8).equals(this.quoteItem.datetime.substring(0, 8)))) {
                realDays++;
                switch (realDays) {
                    case 2:
                        this.dataSecondText = this.quoteItem.datetime;
                        break;
                    case 3:
                        this.dataThirdText = this.quoteItem.datetime;
                        break;
                    case 4:
                        this.dataForthText = this.quoteItem.datetime;
                        break;
                    case 5:
                        this.dataFiveText = this.quoteItem.datetime;
                }

                dayList.clear();
                dayList.add(this.databeginText);
                dayList.add(this.dataSecondText);
                dayList.add(this.dataThirdText);
                dayList.add(this.dataForthText);
                dayList.add(this.dataFiveText);

                this.ifAddedDay = true;
            }

            int todayTimes = 0;
            if (realDays > 0) {
                todayTimes = (realDays - 1) * mTradeTime;
            }
            String lastRealDay = "                     ";
            for (int i = dayList.size() - 1; i >= 0; i--) {
                if (!dayList.get(i).equals("                     ")) {
                    lastRealDay = dayList.get(i);
                    break;
                }
            }
            //todayTimes 所有天的总次数
            if ((this.quoteItem.datetime != null) && (!this.quoteItem.datetime.startsWith(lastRealDay.substring(0, 8)))) {
                todayTimes += mTradeTime;
            }
            if ((nowTime.getTime() - openingTimeONEformat.getTime() > 0L) && (nowTime.getTime() - CloseTimeONEformat.getTime() < 0L)) {
                todayTimes += (int) ((nowTime.getTime() - openingTimeONEformat.getTime()) / 1000L / 60L);
            }
            if ((nowTime.getTime() - CloseTimeONEformat.getTime() > 0L) && (nowTime.getTime() - openingTimeTWOformat.getTime() < 0L)) {
                todayTimes += mFirstPlateTime;
            }
            if ((nowTime.getTime() - openingTimeTWOformat.getTime() > 0L) && (nowTime.getTime() - CloseTimeTWOformat.getTime() < 0L)) {
                todayTimes += (int) (mFirstPlateTime + (nowTime.getTime() - openingTimeTWOformat.getTime()) / 1000L / 60L);
            }
            if (nowTime.getTime() - CloseTimeTWOformat.getTime() >= 0L) {
                todayTimes += mTradeTime;
            }

            /******************** 进入数据填补  ********************/
            if ((this.chartItemsList != null) && (this.chartItemsList.size() > 0) &&
                    (this.chartItemsList.size() < todayTimes)) {
                int k = 0;
                for (int day = 0; day < dayList.size(); day++) {
                    int paddingTime = day * mTradeTime;

                    if (dayList.get(day).equals("                     ")) {
                        break;
                    }
                    for (int i = this.chartItemsList.size() - 1; i >= 0; i--) {
                        if ( dayList.get(day).substring(0, 8).equals(this.chartItemsList.get(i).datetime.substring(0, 8))) {
                            k = i;
                        }
                    }
                    /******************** 早上数据填补开始 ********************/
                    OHLCItemBo zeroItem = this.chartItemsList.get(k);
                    if (!zeroItem.datetime.substring(8, zeroItem.datetime.length()).equals(openingTimeONE.replace(":", ""))) {
                        OHLCItem newItem = new OHLCItem();
                        if (day == 0) {
                            newItem.averagePrice = preCloceFloat + "";
//                            newItem.closePrice = (int)preCloceFloat + "";
                            newItem.closePrice = preCloceFloat + "";
                            newItem.datetime = dayList.get(day).substring(0, 8) + openingTimeONE.replace(":", "");
                            newItem.tradeVolume = "0";
                        } else {
                            OHLCItemBo oldItem = this.chartItemsList.get(k - 1);
                            if (this.ifAddedDay)
                                newItem.averagePrice = oldItem.closePrice;
                            else {
                                newItem.averagePrice = oldItem.averagePrice;
                            }

                            newItem.closePrice = oldItem.closePrice;
                            newItem.datetime = dayList.get(day).substring(0, 8) + openingTimeONE.replace(":", "");
                            newItem.tradeVolume = "0";
                        }

                        if (this.chartItemsList.size() == todayTimes) {
                            this.chartItemsList.remove(this.chartItemsList.size() - 1);
                            this.chartItemsList.add(new OHLCItemBo(newItem));
                        } else if (this.chartItemsList.size() < todayTimes) {
                            this.chartItemsList.add(paddingTime, new OHLCItemBo(newItem));
                        }
                    }

                    k++;
                    if (k >= this.chartItemsList.size()) {
                        k--;
                    }
                    int start = 1 + paddingTime;
                    int end = mFirstPlateTime + paddingTime;
                    int lastDayTimes = todayTimes - paddingTime;
                    if ((day == realDays - 1) && (lastDayTimes < mFirstPlateTime)) {
                        end = lastDayTimes + paddingTime;
                    }

                    for (int i = start; i < end; i++) {
                        Date mDate = new Date();
                        mDate.setTime(openingTimeONEformat.getTime() + (i - paddingTime) * 1000 * 60);
                        String bTime = dayList.get(day).substring(0, 8) + Utility.calculateDateEndWithHHmm(mDate);
                        OHLCItemBo mainItem = this.chartItemsList.get(k);
                        if (null == mainItem) {
                            L.e("走势线数据异常");
                            break;
                        }
                        String datetime = mainItem.datetime;
                        if (datetime.substring(8, datetime.length()).startsWith(openingTimeTWO.substring(0, 2))) {
                            mainItem = this.chartItemsList.get(k - 1);
                            datetime = mainItem.datetime;
                        }

                        if (!datetime.equals(bTime)) {
                            OHLCItemBo oldItem = this.chartItemsList.get(i - 1);
                            OHLCItem newItem = new OHLCItem();
                            newItem.averagePrice = oldItem.averagePrice;
                            newItem.closePrice = oldItem.closePrice;
                            newItem.datetime = bTime;
                            newItem.tradeVolume = "0";
                            if (this.chartItemsList.size() < todayTimes) {
                                this.chartItemsList.add(i, new OHLCItemBo(newItem));
                            }
                        }
                        k++;
                        if (k >= this.chartItemsList.size()) {
                            k--;
                        }
                    }
                    /******************** 早上数据填补结束，下午开始  ********************/

                    start = mFirstPlateTime + paddingTime;
                    end = mTradeTime + paddingTime;
                    if ((day == realDays - 1) && (lastDayTimes < mTradeTime)) {
                        end = lastDayTimes + paddingTime;
                    }

                    if (start < end) {
                        int x = start;
                        OHLCItemBo mainItem0 = this.chartItemsList.get(k);
                        String datetime0 = mainItem0.datetime;

                        Date date = new Date();
                        date.setTime(CloseTimeONEformat.getTime() + 60000L);

                        Date date2 = new Date();
                        date2.setTime(CloseTimeTWOformat.getTime() + 60000L);
                        if ((datetime0.endsWith(Utility.calculateDateEndWithHHmm(date))) ||
                                (datetime0
                                        .endsWith(Utility.calculateDateEndWithHHmm(date2)))) {
                            OHLCItemBo item = mainItem0;
                            this.chartItemsList.get(x - 1).averagePrice = item.averagePrice;
                            this.chartItemsList.get(x - 1).closePrice = item.closePrice;
                            this.chartItemsList.get(x - 1).tradeVolume = (Integer.parseInt( this.chartItemsList.get(x - 1).tradeVolume) + Integer.parseInt(item.tradeVolume) + "");
                            this.chartItemsList.remove(mainItem0);
                            if (k >= this.chartItemsList.size()) {
                                k--;
                            }
                        }
                    }

                    for (int i = start; i < end; i++) {
                        Date mDate = new Date();
                        mDate.setTime(openingTimeTWOformat.getTime() + (i - paddingTime - mFirstPlateTime) * 1000 * 60);
                        String bTime = ( dayList.get(day)).substring(0, 8) + Utility.calculateDateEndWithHHmm(mDate);
                        OHLCItemBo mainItem = this.chartItemsList.get(k);
                        if (null == mainItem) {
                            L.e("走势线数据异常");
                            break;
                        }
                        String datetime = mainItem.datetime;
                        if (datetime.substring(8, datetime.length()).startsWith(openingTimeONE.substring(0, 2))) {
                            mainItem = this.chartItemsList.get(k - 1);
                            datetime = mainItem.datetime;
                        }

                        if (!datetime.equals(bTime)) {
                            OHLCItemBo oldItem = this.chartItemsList.get(i - 1);
                            OHLCItem newItem = new OHLCItem();
                            newItem.averagePrice = oldItem.averagePrice;
                            newItem.closePrice = oldItem.closePrice;
                            newItem.datetime = bTime;
                            newItem.tradeVolume = "0";
                            if (this.chartItemsList.size() < todayTimes) {
                                this.chartItemsList.add(i, new OHLCItemBo(newItem));
                            }
                        }
                        k++;
                        if (k >= this.chartItemsList.size())
                            k--;
                    }
                    /******************** 下午数据填补结束  ********************/
                }
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }
        // 设置五天日期
        setFiveDate();
    }

    /**
     * 设置五天日期
     */
    private void setFiveDate() {
        this.databeginText = this.databeginText.substring(4, 8);
        this.dataSecondText = this.dataSecondText.substring(4, 8);
        this.dataThirdText = this.dataThirdText.substring(4, 8);
        this.dataForthText = this.dataForthText.substring(4, 8);
        this.dataFiveText = this.dataFiveText.substring(4, 8);

        this.databeginText = Stringinsert(this.databeginText, "/", 2);
        this.dataSecondText = Stringinsert(this.dataSecondText, "/", 2);
        this.dataThirdText = Stringinsert(this.dataThirdText, "/", 2);
        this.dataForthText = Stringinsert(this.dataForthText, "/", 2);
        this.dataFiveText = Stringinsert(this.dataFiveText, "/", 2);
    }

    public String Stringinsert(String a, String b, int t) {
        if ( null==a ) {
            a = "       ";
        }
        String aa = a.substring(0, t);
        String bb = a.substring(t, a.length());
        return aa + b + bb;
    }
}