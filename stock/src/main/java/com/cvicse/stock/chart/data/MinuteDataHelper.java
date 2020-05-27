package com.cvicse.stock.chart.data;

import android.util.Log;

import com.cvicse.base.utils.ListUtils;
import com.cvicse.base.utils.NumberUtils;
import com.cvicse.base.utils.StringUtils;
import com.cvicse.stock.chart.theme.ThemeManager;
import com.cvicse.stock.chart.utils.MyUtils;
import com.cvicse.stock.chart.utils.TimezoneUtils;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.CombinedData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.mitake.core.OHLCItem;
import com.mitake.core.QuoteItem;
import com.mitake.core.disklrucache.L;
import com.mitake.core.util.FormatUtility;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * 分时图数据帮助类
 * Created by liu_zlu on 2017/1/22 11:12
 */
public class MinuteDataHelper {

    private ArrayList<MinuteEntity> minuteEntities;
    protected ArrayList<String> datelist = new ArrayList<>();
    private CombinedData bottomData;
    private CombinedData topData;
    public LimitLine limitLine;
    public String unitStr;
    private ArrayList<OHLCItemBo> ohlcItems;

    public MinuteDataHelper(){
    }

    public QuoteItem getQuoteItem(){
        return quoteItem;
    }
    private ArrayList<Integer> colors = new ArrayList<>();

    public void setQuoteItem(QuoteItem quoteItem){
        if(this.quoteItem  != null ){
            int date = NumberUtils.parseInt(quoteItem.datetime);
            int date1 = NumberUtils.parseInt(this.quoteItem.datetime);
            if(date < date1){
                return;
            }
        }
        this.quoteItem = quoteItem;
        timezoneEntity = TimezoneUtils.getEntity(quoteItem);
        if(ohlcItems != null && !StringUtils.isEmpty(quoteItem.preClosePrice)){
            postData(ohlcItems);
        }
    }

    public void addRequestData(ArrayList<OHLCItemBo> ohlcItems){
        this.ohlcItems = ohlcItems;
        if(quoteItem != null && !StringUtils.isEmpty(quoteItem.preClosePrice)){
            postData(ohlcItems);
        }
    }

    public void postData(ArrayList<OHLCItemBo> ohlcItems) {
        chartItemsList(ohlcItems);

        if(chartItemsList == null || chartItemsList.size() == 0){
            return ;
        }
        minuteEntities = new ArrayList<>();
        MinuteEntity minuteEntity;
        datelist.clear();
        int length = chartItemsList.size();
        float openPrice = NumberUtils.parseFloat(quoteItem.openPrice);
        float lastCjprice = 0;
        OHLCItemBo ohlcitem = null;
        for(int i = 0;i < length;i++ ){
            ohlcitem = chartItemsList.get(i);
            minuteEntity = new MinuteEntity();
            //minuteEntity.close = NumberUtils.parseFloat(ohlcitem.closePrice);
            minuteEntity.datetime = ohlcitem.datetime;
            minuteEntity.timeStr = ohlcitem.time;

            ohlcitem.change = FormatUtility.formatChange(this.quoteItem.preClosePrice, ohlcitem.closePrice, this.quoteItem.market, this.quoteItem.subtype);
            ohlcitem.changeRate = StringUtils.isEmpty(quoteItem.preClosePrice) ? "0": FormatUtility.formatChangeRate(this.quoteItem.preClosePrice, ohlcitem.change, this.quoteItem.market, this.quoteItem.subtype);
            minuteEntity.volume = NumberUtils.parseFloat(ohlcitem.tradeVolume);
            minuteEntity.averagePrice = NumberUtils.parseFloat(minuteEntity.averagePriceStr = FormatUtility.formatPrice(ohlcitem.averagePrice, this.quoteItem.market, this.quoteItem.subtype));
            minuteEntity.cjprice = NumberUtils.parseFloat(FormatUtility.formatPrice(ohlcitem.closePrice, this.quoteItem.market, this.quoteItem.subtype));
            minuteEntities.add(minuteEntity);
            if(i == 0){
                if(openPrice < minuteEntity.cjprice){
                    colors.add(ThemeManager.colorRed());
                } else {
                    colors.add(ThemeManager.colorGreen());
                }
            } else {
                if(minuteEntity.cjprice > lastCjprice){
                    colors.add(ThemeManager.colorRed());
                } else {
                    colors.add(ThemeManager.colorGreen());
                }
            }
            lastCjprice = minuteEntity.cjprice;
        }
        length = minuteEntities.size();
        float max = 0;
        for(int i = 0;i < length ;i++){
            minuteEntity = minuteEntities.get(i);
            datelist.add(minuteEntity.datetime);
            max = Math.max(minuteEntity.volume,max);
        }
        unitStr = MyUtils.getVolUnit(max);
      //  unit = 1;
      /*  if ("万手".equals(unitStr)) {
            unit = 4;
        } else if ("亿手".equals(unitStr)) {
            unit = 8;
        }*/
       createChartData();
    }

    public int getCount() {
        return minuteEntities.size();
    }

    public int getXLength(){
        if(timezoneEntity == null){
            return 0;
        }
        return timezoneEntity.TradeTime;
    }

    public void createChartData() {
        ArrayList<BarEntry> barEntries = new ArrayList<>();
        ArrayList<Entry> linecjEntries = new ArrayList<Entry>();
        ArrayList<Entry> lineAverageEntries = new ArrayList<Entry>();
        ArrayList<BarEntry> barTopEntries = new ArrayList<>();
        for(int i = 0; i < minuteEntities.size(); i++){

            MinuteEntity minuteEntity = minuteEntities.get(i);
            barEntries.add(new BarEntry(i, minuteEntity.volume));
            lineAverageEntries.add(new Entry(i, minuteEntity.averagePrice));
            linecjEntries.add(new Entry(i,minuteEntity.cjprice));
           // barTopEntries.add(i,new BarEntry());
        }
        //axisLeftBar.setValueFormatter(new VolFormatter((int) Math.pow(10, u)));
        createBarData(barEntries);
        createTopData(lineAverageEntries,linecjEntries,null);
    }


    public CombinedData createBarData(ArrayList<BarEntry> barEntries) {
        bottomData = new CombinedData();
        BarData barData = new BarData();
        BarDataSet barDataSet = new BarDataSet(barEntries, "成交量");
        barDataSet.setHighlightEnabled(true);
        barDataSet.setHighLightColor(ThemeManager.colorWhiteBlack());
        barDataSet.setDrawValues(false);
        barDataSet.setColors(colors);
        barData.addDataSet(barDataSet);
        bottomData.setData(barData);
        return bottomData;
    }

   public CombinedData createTopData(ArrayList<Entry> lineAverageEntries,ArrayList<Entry> lineCloseEntries,ArrayList<BarEntry> barEntries) {
       topData = new CombinedData();
       LineData lineData = new LineData();
       LineDataSet lineDataSet = new LineDataSet(lineAverageEntries, "均价");
       lineDataSet.setColor(ThemeManager.colorYellow());
       lineDataSet.setDrawCircles(false);
       lineDataSet.setHighlightEnabled(false);
       lineDataSet.setDrawValues(false);
       lineDataSet.setDrawHorizontalHighlightIndicator(true);

       LineDataSet lineCloseDataSet = new LineDataSet(lineCloseEntries, "成交价");
       lineCloseDataSet.setColor(ThemeManager.colorWhiteBlack());
       lineCloseDataSet.setDrawCircles(false);
       lineCloseDataSet.setHighlightEnabled(true);
       lineCloseDataSet.setHighLightColor(ThemeManager.colorWhiteBlack());
       lineCloseDataSet.setDrawValues(false);
       lineCloseDataSet.setDrawFilled(true);

       lineData.addDataSet(lineCloseDataSet);
       lineData.addDataSet(lineDataSet);

       topData.setData(lineData);
       return topData;
    }

    public float getPercentMin(){
        return -PricePercentText;
    }

    public float getPercentMax() {
        return PricePercentText;
    }


    public float getDataMin(){
        return bottomPriceText == null || bottomPriceText.equals("-") ? 0:
                NumberUtils.parseFloat(bottomPriceText);

    }

    public float getDataMax() {
        return topPriceText == null ? 0:NumberUtils.parseFloat(topPriceText);
    }


    public CombinedData getTopChartData() {
        return topData;
    }

    public CombinedData getBottomChartData() {
        return bottomData;
    }

    public String getBottomLable(int position){
        MinuteEntity minuteEntity = minuteEntities.get(position);
        if(minuteEntity.bottomLable == null){
            if(minuteEntity.timeStr == null){
                return "";
            }
            minuteEntity.bottomLable = minuteEntity.timeStr;
        }
        return minuteEntity.bottomLable;
    }

    public String getBottomLeftLable(int position){
        MinuteEntity minuteEntity = minuteEntities.get(position);
        if(minuteEntity.bleftLable == null){
            minuteEntity.bleftLable = MyUtils.getVol(minuteEntity.volume);
        }
        return minuteEntity.bleftLable;
    }

    public String getTopLeftLable(int position){
        MinuteEntity minuteEntity = minuteEntities.get(position);
        return minuteEntity.averagePriceStr;
    }
    public String getTopRightLable(int position){
        MinuteEntity minuteEntity = minuteEntities.get(position);
        if(minuteEntity.trightLable == null){
            float center = (getDataMax()+getDataMin())/2f;
            DecimalFormat df = new DecimalFormat("0.00");
            minuteEntity.trightLable = df.format((minuteEntity.averagePrice-center)/ minuteEntity.averagePrice*100)+"%";
        }
        return minuteEntity.trightLable;
    }
    public ArrayList<OHLCItemBo> getChartItemsList() {
        return chartItemsList;
    }

    public OHLCItemBo getLastData() {
        return chartItemsList == null || chartItemsList.size() == 0 ? null:chartItemsList.get(chartItemsList.size()-1);
    }

    /**
     * 处理后的数据
     */
    private ArrayList<OHLCItemBo> chartItemsList;
    private QuoteItem quoteItem;
    private long maxVolume;
    private long maxClosePrice;
    private long minClosePrice;

    private String topPriceText = "0";
    private String MidPreClose;
    private String bottomPriceText;
    private float PricePercentText;

    public TimezoneEntity timezoneEntity;

    private void chartItemsList(ArrayList<OHLCItemBo> chartItemsListSrc){
        this.chartItemsList = ListUtils.copy(chartItemsListSrc);
        this.maxClosePrice = this.maxVolume = 0L;
        this.minClosePrice = 9223372036854775807L;
        OHLCItemBo ohlcItem = null;
        for(int i = 0; i < this.chartItemsList.size(); ++i) {
            if(null == this.chartItemsList.get(i)) {
                L.e("数据错误");
                return;
            }
            ohlcItem = this.chartItemsList.get(i);
            this.maxVolume = Math.max(this.maxVolume, Long.parseLong(ohlcItem.tradeVolume));
            long tempClose = Long.parseLong(ohlcItem.closePrice);
            long tempAverage = Long.parseLong(ohlcItem.averagePrice);
            this.maxClosePrice = Math.max(Math.max(this.maxClosePrice,tempClose),tempAverage);
            this.minClosePrice = Math.min(Math.min(this.minClosePrice,tempClose),tempAverage);
        }

        if(this.quoteItem.srcPreClosePrice != null && !this.quoteItem.srcPreClosePrice.equals("")) {
            float middlePrice = 0;
            if(this.quoteItem.srcPreClosePrice != null && !this.quoteItem.srcPreClosePrice.equals("")) {
                middlePrice = NumberUtils.parseFloat(this.quoteItem.srcPreClosePrice);
            } else {
                middlePrice = (float)((this.maxClosePrice + this.minClosePrice) / 2L);
            }

            if(middlePrice == 0.0F && FormatUtility.couldNum(this.quoteItem.lastPrice)) {
                middlePrice = NumberUtils.parseFloat(this.quoteItem.lastPrice);
            }

            if(timezoneEntity != null) {
                int i;

                int lastTime = 0;
                if(this.chartItemsList != null && this.chartItemsList.size() > 0) {
                    lastTime = Integer.parseInt(this.chartItemsList.get(this.chartItemsList.size() - 1).datetime.substring(8, 12));
                }

                if(this.quoteItem.datetime != null) {
                    lastTime = Math.max(lastTime,Integer.parseInt(this.quoteItem.datetime.substring(8, 12)));
                }

                i = 0;
                if(lastTime - timezoneEntity.openTime1 >= 0L && lastTime - timezoneEntity.closeTime1 < 0L) {
                    i = (lastTime/100 - timezoneEntity.openTime1/100)*60+(lastTime%100 - timezoneEntity.openTime1%100) ;
                    if(i == 0) {
                        i = 1;
                    }
                }

                if(lastTime - timezoneEntity.closeTime1 >= 0L && lastTime - timezoneEntity.openTime2 < 0L) {
                    i = timezoneEntity.FirstPlateTime;
                }

                if(lastTime - timezoneEntity.openTime2 >= 0L && lastTime - timezoneEntity.closeTime2 < 0L) {
                    i = timezoneEntity.FirstPlateTime + (lastTime/100 - timezoneEntity.openTime2/100)*60+(lastTime%100 - timezoneEntity.openTime2%100) ;
                }

                if(lastTime - timezoneEntity.closeTime2 >= 0L) {
                    i = timezoneEntity.TradeTime;
                }

               /* int averagePrice;
                if(this.chartItemsList != null && this.chartItemsList.size() > 0) {
                    this.removeCache.clear();
                    if(this.chartItemsList.size() >= i) {
                        String volumeRatial = "";
                        int price;
                        if(this.chartItemsList.size() <= timezoneEntity.FirstPlateTime) {
                            for(price = 0; price < this.chartItemsList.size() - 1; ++price) {
                                if(volumeRatial.equals("")) {
                                    volumeRatial = this.chartItemsList.get(price).datetime;
                                }

                                if(Long.parseLong(volumeRatial) - Long.parseLong(this.chartItemsList.get(price + 1).datetime) >= 0L) {
                                    this.removeCache.add(Integer.valueOf(price + 1));
                                } else {
                                    volumeRatial = "";
                                }
                            }
                        }

                        if(this.chartItemsList.size() > timezoneEntity.FirstPlateTime && this.chartItemsList.size() < timezoneEntity.TradeTime) {
                            for(price = 0; price < timezoneEntity.FirstPlateTime - 1; ++price) {
                                if(volumeRatial.equals("")) {
                                    volumeRatial = this.chartItemsList.get(price).datetime;
                                }

                                if(Long.parseLong(volumeRatial) - Long.parseLong(this.chartItemsList.get(price + 1).datetime) >= 0L) {
                                    this.removeCache.add(Integer.valueOf(price + 1));
                                } else {
                                    volumeRatial = "";
                                }
                            }

                            for(price = timezoneEntity.FirstPlateTime - 1; price < this.chartItemsList.size() - 1; ++price) {
                                if(volumeRatial.equals("")) {
                                    volumeRatial = this.chartItemsList.get(price).datetime;
                                }

                                if(Long.parseLong(volumeRatial) - Long.parseLong(this.chartItemsList.get(price + 1).datetime) >= 0L) {
                                    this.removeCache.add(Integer.valueOf(price + 1));
                                } else {
                                    volumeRatial = "";
                                }
                            }
                        }

                        if(this.chartItemsList.size() >= timezoneEntity.TradeTime) {
                            for(price = 0; price < timezoneEntity.FirstPlateTime - 1; ++price) {
                                if(volumeRatial.equals("")) {
                                    volumeRatial = this.chartItemsList.get(price).datetime;
                                }

                                if(Long.parseLong(volumeRatial) - Long.parseLong(this.chartItemsList.get(price + 1).datetime) >= 0L) {
                                    this.removeCache.add(Integer.valueOf(price + 1));
                                } else {
                                    volumeRatial = "";
                                }
                            }

                            for(price = timezoneEntity.FirstPlateTime - 1; price < timezoneEntity.TradeTime - 1; ++price) {
                                if(volumeRatial.equals("")) {
                                    volumeRatial = this.chartItemsList.get(price).datetime;
                                }

                                if(Long.parseLong(volumeRatial) - Long.parseLong(this.chartItemsList.get(price + 1).datetime) >= 0L) {
                                    this.removeCache.add(Integer.valueOf(price + 1));
                                } else {
                                    volumeRatial = "";
                                }
                            }

                            for(price = timezoneEntity.TradeTime; price < this.chartItemsList.size(); ++price) {
                                this.removeCache.add(Integer.valueOf(price));
                            }
                        }

                        for(price = this.removeCache.size() - 1; price >= 0; --price) {
                            averagePrice = this.removeCache.get(price).intValue();
                            this.chartItemsList.remove(averagePrice);
                        }

                        this.removeCache.clear();
                    }
                }*/

                if(this.chartItemsList != null && this.chartItemsList.size() > 0 /*&& this.chartItemsList.size() <= i*/) {

                    ArrayList<OHLCItemBo> arrayListTemp = new ArrayList<>((int)Math.ceil(i*1.5));
                    for(int j = 0;j < i ;j++){
                        arrayListTemp.add(null);
                    }
                    int currentH = 0;
                    int currentM = 0;
                    StringBuffer stringBuffer = new StringBuffer();
                    for(OHLCItemBo ohlcItemTemp:this.chartItemsList){
                        int time = Integer.parseInt(ohlcItemTemp.datetime.substring(8, 12));
                        currentH = time/100;
                        currentM = time%100;
                        stringBuffer.setLength(0);
                        if(currentH < 10){
                            stringBuffer.append("0");
                        }
                        stringBuffer.append(currentH);
                        stringBuffer.append(":");
                        if(currentM < 10){
                            stringBuffer.append("0");
                        }
                        stringBuffer.append(currentM);
                        ohlcItemTemp.time = stringBuffer.toString();
                        if(time >= timezoneEntity.openTime1 && time <= timezoneEntity.closeTime1){
                            int curTime = (currentH - timezoneEntity.openHour1)*60+currentM- timezoneEntity.openM1;
                            if(curTime < timezoneEntity.FirstPlateTime && curTime < i){
                                arrayListTemp.set(curTime,ohlcItemTemp);
                            }
                            continue;
                        }

                        if(time >= timezoneEntity.openTime2 && time <= timezoneEntity.closeTime2){
                            int curTime = (currentH - timezoneEntity.openHour2)*60+currentM- timezoneEntity.openM2 + timezoneEntity.FirstPlateTime;
                            if(curTime < timezoneEntity.TradeTime && curTime < i){
                                arrayListTemp.set(curTime,ohlcItemTemp);
                            }
                            continue;
                        }
                    }
                    int length = arrayListTemp.size();
                    OHLCItemBo ohlcItemTemp = null;
                    OHLCItemBo oldItem;
                    OHLCItem ohlcItem1 = null;
                    ohlcItemTemp = arrayListTemp.get(0);
                    if(ohlcItemTemp == null){
                        ohlcItem1 = new OHLCItem();
                        ohlcItem1.averagePrice = (int)middlePrice + "";
                        ohlcItem1.closePrice = (int)middlePrice + "";
                        ohlcItem1.tradeVolume = "0";
                        arrayListTemp.set(0,new OHLCItemBo(ohlcItem1));
                    } else {
                        if(ohlcItemTemp.closePrice == null){
                            ohlcItemTemp.closePrice = (int)middlePrice + "";
                        }
                    }
                    for(int j = 1;j< length;j++ ){
                         ohlcItemTemp = arrayListTemp.get(j);
                        oldItem = arrayListTemp.get(j - 1);
                        if(ohlcItemTemp == null){
                            ohlcItem1 = new OHLCItem();
                            ohlcItem1.averagePrice = oldItem.averagePrice;
                            ohlcItem1.closePrice = oldItem.closePrice;
                            ohlcItem1.time = autoComplete(j,timezoneEntity);
                            ohlcItem1.tradeVolume = "0";
                            arrayListTemp.set(j,new OHLCItemBo(ohlcItem1));
                        } else {
                            if(ohlcItemTemp.averagePrice == null){
                                ohlcItemTemp.averagePrice = oldItem.averagePrice;
                            }
                            if(ohlcItemTemp.closePrice == null){
                                ohlcItemTemp.closePrice = oldItem.closePrice;
                            }
                            if(ohlcItemTemp.tradeVolume == null){
                                ohlcItemTemp.tradeVolume = "0";
                            }
                        }
                    }
                    this.chartItemsList = arrayListTemp;
                }

                float var34 = (float)this.maxClosePrice - middlePrice;
                float var35 = middlePrice - (float)this.minClosePrice;
                var34 = var34 > var35?var34:var35;
                this.topPriceText = middlePrice == 0.0F?"-":FormatUtility.formatPrice((int)(middlePrice + var34) + "", this.quoteItem.market, this.quoteItem.subtype);
                if(this.quoteItem.srcPreClosePrice != null && !this.quoteItem.srcPreClosePrice.equals("")) {
                    this.MidPreClose = middlePrice == 0.0F?"-":FormatUtility.formatPrice(this.quoteItem.srcPreClosePrice, this.quoteItem.market, this.quoteItem.subtype);
                } else {
                    this.MidPreClose = FormatUtility.formatPrice((int)((this.maxClosePrice + this.minClosePrice) / 2L) + "", this.quoteItem.market, this.quoteItem.subtype);
                }

                this.bottomPriceText = middlePrice == 0.0F?"-":FormatUtility.formatPrice((int)(middlePrice - var34) + "", this.quoteItem.market, this.quoteItem.subtype);
                DecimalFormat var36 = new DecimalFormat("#.###");
                String pricePercentText = middlePrice == 0.0F?"-":FormatUtility.fourOutButFiveInWithPoint(var36.format((double)(var34 / middlePrice * 100.0F)), 2).toString();
                PricePercentText = NumberUtils.parseFloat(pricePercentText);
            }
        }
    }

 /*   public SparseArray<String> getXLabels(){
        initTime();
    }*/

    private void initTime(){

    }

    private String autoComplete(int position,TimezoneEntity timezoneEntity){
        int currentH = 0;
        int currentM = 0;
        if(position < timezoneEntity.FirstPlateTime){
            currentH = position/60 + timezoneEntity.openHour1;
            if((position%60 + timezoneEntity.openM1) >= 60){
                currentH++;
                currentM = position%60 + timezoneEntity.openM1 - 60;
            } else {
                currentM = position%60 + timezoneEntity.openM1;
            }
        } else {
            currentH = (position-timezoneEntity.FirstPlateTime)/60 + timezoneEntity.openHour2;
            currentM = (position-timezoneEntity.FirstPlateTime)%60 + timezoneEntity.openM2;
            if(currentM >= 60){
                currentH++;
                currentM = currentM - 60;
            }
        }
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(currentH);
        stringBuffer.append(":");
        if(currentM < 10){
            stringBuffer.append("0");
        }
        stringBuffer.append(currentM);
        Log.e("currentM",stringBuffer.toString());
        return stringBuffer.toString();
    }
}
