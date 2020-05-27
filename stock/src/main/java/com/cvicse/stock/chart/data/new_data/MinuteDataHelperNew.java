package com.cvicse.stock.chart.data.new_data;

import com.cvicse.base.utils.NumberUtils;
import com.cvicse.stock.chart.data.MinuteEntity;
import com.cvicse.stock.chart.data.OHLCItemBo;
import com.cvicse.stock.chart.data.TimezoneEntity;
import com.cvicse.stock.chart.formatter.CommonValueFormatter;
import com.cvicse.stock.chart.helper.new_helper.StockMState;
import com.cvicse.stock.chart.theme.ThemeManager;
import com.cvicse.stock.chart.ui.new_ui.StockMinuteChartNew;
import com.cvicse.stock.chart.utils.MyUtils;
import com.cvicse.stock.chart.utils.TimezoneUtils;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.CombinedData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.mitake.core.OHLCItem;
import com.mitake.core.QuoteItem;
import com.mitake.core.bean.exchange.TimeZone;
import com.mitake.core.response.ChartResponse;
import com.mitake.core.response.chart.BidChartResponse;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 分时图数据帮助类
 * 数据处理，提供高亮标志数据
 * Created by liu_zlu on 2017/1/22 11:12
 */
public class MinuteDataHelperNew {
    private CombinedData topData;  // 顶部数据
    private CombinedData bottomData;  // 成交量
    private CombinedData botmDDXData;
    private CombinedData botmDDYData;
    private CombinedData botmDDZData;
    private CombinedData botmBBDData;
    private CombinedData botmRatioBSData;
    private CombinedData botmCaptialGameData;
    private CombinedData botmOrdernumData;
    private CombinedData botmBigNetVolume;
    private CombinedData botmVOLRatioData;// 量比

    private ArrayList<MinuteEntity> minuteEntities;
    protected ArrayList<String> datelist = new ArrayList<>();
    private ArrayList<OHLCItemBo> chartItemsList; // 处理后的数据
     private ChartResponse chartResponse;

    private QuoteItem quoteItem;
    private float maxClosePrice;
    private float minClosePrice;

    private float minShowClosePrice;// 顶部左侧最小值（展示用
    private float maxShowClosePrice;// 顶部左侧最大值（展示用

    public String unitStr;
    private String PricePercentText;

    public TimezoneEntity timezoneEntity;
    private StockMState mState;  // 线图相关状态

    private ArrayList<MinuteEntity> mSubEntities;

    public MinuteDataHelperNew(){
    }

    public MinuteDataHelperNew(StockMState mState){
        this.mState = mState;
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

        if( null != chartResponse && null != quoteItem){
            postData(chartResponse);

            // 新增2018.3.21
            postMSubData(mSubEntities);
        }
    }

    public void addRequestData(ChartResponse chartResponse){
        this.chartResponse = chartResponse;
        if( null!=quoteItem  && null!= chartResponse){
            postData(chartResponse);
        }
    }

    // TODO: 2018/3/21  走势副图指标数据
    public void addRequestSubData(ArrayList<MinuteEntity> mSubEntities){
        this.mSubEntities = mSubEntities;
        if( null != quoteItem && null != mSubEntities ){
            postMSubData(mSubEntities);
        }
    }

    public void postMSubData(ArrayList<MinuteEntity> mSubEntities){
        ArrayList<Entry> lineDDXEntries = new ArrayList<>();
        ArrayList<Entry> lineDDYEntries = new ArrayList<>();
        ArrayList<Entry> lineDDZEntries = new ArrayList<>();
        ArrayList<Entry> lineBBDEntries = new ArrayList<>();
        ArrayList<Entry> lineRatioBSEntries = new ArrayList<>();
        ArrayList<Entry> lineULEntries = new ArrayList<>();
        ArrayList<Entry> lineLEntries = new ArrayList<>();
        ArrayList<Entry> lineMEntries = new ArrayList<>();
        ArrayList<Entry> lineSEntries = new ArrayList<>();
        ArrayList<Entry> lineULNumEntries = new ArrayList<>();
        ArrayList<Entry> lineLNumEntries = new ArrayList<>();
        ArrayList<Entry> lineMNumEntries = new ArrayList<>();
        ArrayList<Entry> lineSNumEntries = new ArrayList<>();
        ArrayList<Entry> lineBNVolumeEntries = new ArrayList<>();
        ArrayList<Entry> lineAmountEntries = new ArrayList<>();


        if( null == mSubEntities || mSubEntities.isEmpty() ){
            return;
        }

        int currentH = 0;
        int currentM = 0;

        StringBuffer stringBuffer = new StringBuffer();
        // 日期格式转换 , HH:mm
        for(MinuteEntity minuteEntity:mSubEntities){
            if (minuteEntity.datetime.length() < 4) {
                continue;
            }
            int time = Integer.parseInt(minuteEntity.datetime.substring(0,4));
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
            minuteEntity.timeStr = stringBuffer.toString();
        }

        this.mSubEntities = new ArrayList<>();
        this.mSubEntities = mSubEntities;
        for ( int i = 0,size = this.mSubEntities.size(); i <size; i++  ){
            MinuteEntity entity = mSubEntities.get(i);
            lineDDXEntries.add(new Entry(i,entity.ddx));
            lineDDYEntries.add(new Entry(i,entity.ddy));
            lineDDZEntries.add(new Entry(i,entity.ddz));
            lineBBDEntries.add(new Entry(i,entity.bbd));
            lineRatioBSEntries.add(new Entry(i,entity.ratioBs));
            lineULEntries.add(new Entry(i,entity.largeMoneyInflow));
            lineLEntries.add(new Entry(i,entity.bigMoneyInflow));
            lineMEntries.add(new Entry(i,entity.midMoneyInflow));
            lineSEntries.add(new Entry(i,entity.smallMoneyInflow));
            lineULNumEntries.add(new Entry(i,entity.largeTradeNum));
            lineLNumEntries.add(new Entry(i,entity.bigTradeNum));
            lineMNumEntries.add(new Entry(i,entity.midTradeNum));
            lineSNumEntries.add(new Entry(i,entity.smallTradeNum));
            lineBNVolumeEntries.add(new Entry(i,entity.bigNetVolume));
            lineAmountEntries.add(new Entry(i,entity.volRatio));
        }

        createDDXData(lineDDXEntries);
        createDDYData(lineDDYEntries);
        createDDZData(lineDDZEntries);
        createBBDData(lineBBDEntries);
        createRatioBSData(lineRatioBSEntries);
        createCaptialGameData(lineULEntries,lineLEntries,lineMEntries,lineSEntries);
        createOrdernumData(lineULNumEntries,lineLNumEntries,lineMNumEntries,lineSNumEntries);
        createBigNetVolumeData(lineBNVolumeEntries);
        createVolRatioData(lineAmountEntries);
    }

    /**
     * 设置线图（包含计算各个指标数据、创建图）
     * @param chartResponse
     */
    public void postData(ChartResponse chartResponse) {
        if (null == chartResponse || chartResponse.historyItems.isEmpty()) {
            return;
        }
        chartItemsList(chartResponse);

        MinuteEntity minuteEntity;
        float openPrice = NumberUtils.parseFloat(quoteItem.openPrice);
        float lastCjprice = 0;
        OHLCItemBo ohlcitem = null;
        String market = this.quoteItem.market;
        for (int i = 0,size = this.chartResponse.historyItems.size();i < size;i++) {
            OHLCItem ohlcItem = this.chartResponse.historyItems.get(i);
            ohlcitem = new OHLCItemBo(ohlcItem);
            minuteEntity = new MinuteEntity();
            String time = ohlcitem.datetime;
            if (time.length() > 8) {
                time = time.substring(8);
                minuteEntity.time = Integer.parseInt(time);
                minuteEntity.datetime = ohlcitem.datetime;
                minuteEntity.timeStr = ohlcitem.time = time.substring(0,2)+":"+time.substring(2);
            }

            float value = NumberUtils.parseFloat(ohlcitem.closePrice) - NumberUtils.parseFloat(ohlcitem.reference_price);
            ohlcitem.change = CommonValueFormatter.formatValue(market,quoteItem.subtype,value);
            ohlcitem.changeRate = CommonValueFormatter.formatValue(market,quoteItem.subtype,(NumberUtils.parseFloat(ohlcitem.change) /  NumberUtils.parseFloat(ohlcitem.reference_price)) *100 );

            minuteEntity.volume = NumberUtils.parseFloat(ohlcitem.tradeVolume);
            minuteEntity.volRatio = NumberUtils.parseFloat(ohlcitem.volRatio);

            minuteEntity.averagePriceStr = ohlcitem.averagePrice;
            minuteEntity.averagePrice = NumberUtils.parseFloat(minuteEntity.averagePriceStr);

            minuteEntity.cjpriceStr=ohlcitem.closePrice;
            minuteEntity.cjprice = NumberUtils.parseFloat(minuteEntity.cjpriceStr);

            minuteEntity.ipovStr =ohlcitem.iopv;
            minuteEntity.ipov = NumberUtils.parseFloat(minuteEntity.ipovStr);

            minuteEntity.ipovPreStr =ohlcitem.iopvPre;
            minuteEntity.ipovPre = NumberUtils.parseFloat(minuteEntity.ipovPreStr);

            minuteEntities.add(minuteEntity);
            chartItemsList.add(ohlcitem);
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
        this.mSubEntities.clear();
        mSubEntities = minuteEntities;
        createChartData();
    }

    /**
     * 创建线图
     */
    public void createChartData() {
        ArrayList<Entry> linecjEntries = new ArrayList<Entry>();
        ArrayList<Entry> lineAverageEntries = new ArrayList<Entry>();
        ArrayList<Entry> lineIpovEntries = new ArrayList<Entry>();
        ArrayList<BarEntry> barEntries = new ArrayList<>();
        ArrayList<Entry> volRatioEntries = new ArrayList<>();
        float max = Float.MIN_VALUE;
        for(int i = 0,size = minuteEntities.size(); i < size; i++){
            MinuteEntity minuteEntity = minuteEntities.get(i);
            max = Math.max(minuteEntity.volume,max);
            barEntries.add(new BarEntry(i, minuteEntity.volume));
            float tempAveragePrice = minuteEntity.averagePrice;
            if( tempAveragePrice != 0.0 ){
                lineAverageEntries.add(new Entry(i, tempAveragePrice));
            }
            linecjEntries.add(new Entry(i,minuteEntity.cjprice));
            lineIpovEntries.add(new Entry(i,minuteEntity.ipov));
            // 量比
            max = Math.max(minuteEntity.volRatio, max);
            volRatioEntries.add(new Entry(i, minuteEntity.volRatio));
        }
        unitStr = MyUtils.getVolUnit(max);
        createTopData(lineAverageEntries,linecjEntries,lineIpovEntries);
        createVOLData(barEntries);
        createVolRatioData(volRatioEntries);
    }

    public CombinedData createTopData(ArrayList<Entry> lineAverageEntries,ArrayList<Entry> lineCloseEntries,ArrayList<Entry> lineIpovEntries) {
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
        lineCloseDataSet.setDrawFilled(true);  // 设置绘制阴影区域

        // ETF基金IOPV图
        if( null != quoteItem.st && quoteItem.st.contains("1120") ){
            LineDataSet lineIpovDataSet = new LineDataSet(lineIpovEntries,"ETF基金IOPV");
            lineIpovDataSet.setColor(ThemeManager.colorFuchsia());
            lineIpovDataSet.setHighLightColor(ThemeManager.colorWhiteBlack());
            lineIpovDataSet.setHighlightEnabled(true);
            lineIpovDataSet.setDrawCircles(false);
            lineIpovDataSet.setDrawHorizontalHighlightIndicator(true);
            lineIpovDataSet.setDrawCircles(false);
            lineIpovDataSet.setDrawValues(false);
            lineData.addDataSet(lineIpovDataSet);
        }

        lineData.addDataSet(lineCloseDataSet);
        lineData.addDataSet(lineDataSet);

        topData.setData(lineData);
        return topData;
    }

    public CombinedData createVOLData(ArrayList<BarEntry> barEntries) {
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

    public CombinedData createDDXData(ArrayList<Entry> linekEntries ){
        botmDDXData = new CombinedData();
        LineDataSet lineDataSet = new LineDataSet(linekEntries,"DDX");
        lineDataSet.setDrawCircles(false);
        lineDataSet.setDrawValues(false);
        lineDataSet.setColor(ThemeManager.colorWhiteBlack());

        LineData lineData = new LineData();
        lineData.addDataSet(lineDataSet);

        botmDDXData.setData(lineData);
        return botmDDXData;
    }

    /**
     * 量比
     */
    public CombinedData createVolRatioData(ArrayList<Entry> linekEntries ){
        botmVOLRatioData = new CombinedData();
        LineDataSet lineDataSet = new LineDataSet(linekEntries,"VOLRatio");
        lineDataSet.setDrawCircles(false);
        lineDataSet.setDrawValues(false);
        lineDataSet.setColor(ThemeManager.colorWhiteBlack());

        LineData lineData = new LineData();
        lineData.addDataSet(lineDataSet);

        botmVOLRatioData.setData(lineData);
        return botmVOLRatioData;
    }

    public CombinedData createDDYData(ArrayList<Entry> linekEntries ){
        botmDDYData = new CombinedData();
        LineDataSet lineDataSet = new LineDataSet(linekEntries,"DDY");
        lineDataSet.setDrawCircles(false);
        lineDataSet.setDrawValues(false);
        lineDataSet.setColor(ThemeManager.colorWhiteBlack());

        LineData lineData = new LineData();
        lineData.addDataSet(lineDataSet);

        botmDDYData.setData(lineData);
        return botmDDYData;
    }

    public CombinedData createDDZData(ArrayList<Entry> linekEntries ){
        botmDDZData = new CombinedData();
        LineDataSet lineDataSet = new LineDataSet(linekEntries,"DDZ");
        lineDataSet.setDrawCircles(false);
        lineDataSet.setDrawValues(false);
        lineDataSet.setColor(ThemeManager.colorWhiteBlack());

        LineData lineData = new LineData();
        lineData.addDataSet(lineDataSet);

        botmDDZData.setData(lineData);
        return botmDDZData;
    }

    public CombinedData createBBDData(ArrayList<Entry> linekEntries ){
        botmBBDData = new CombinedData();
        LineDataSet lineDataSet = new LineDataSet(linekEntries,"BBD");
        lineDataSet.setDrawCircles(false);
        lineDataSet.setDrawValues(false);
        lineDataSet.setColor(ThemeManager.colorWhiteBlack());

        LineData lineData = new LineData();
        lineData.addDataSet(lineDataSet);

        botmBBDData.setData(lineData);
        return botmBBDData;
    }

    public CombinedData createRatioBSData(ArrayList<Entry> linekEntries ){
        botmRatioBSData = new CombinedData();
        LineDataSet lineDataSet = new LineDataSet(linekEntries,"ratioBS");
        lineDataSet.setDrawCircles(false);
        lineDataSet.setDrawValues(false);
        lineDataSet.setColor(ThemeManager.colorWhiteBlack());

        LineData lineData = new LineData();
        lineData.addDataSet(lineDataSet);

        botmRatioBSData.setData(lineData);
        return botmRatioBSData;
    }

    public CombinedData createCaptialGameData(ArrayList<Entry> lineULEntries,ArrayList<Entry> lineLEntries,ArrayList<Entry> lineMEntries,ArrayList<Entry> lineSEntries ){
        botmCaptialGameData = new CombinedData();
        LineDataSet lineDataULSet = new LineDataSet(lineULEntries,"超大");
        lineDataULSet.setDrawCircles(false);
        lineDataULSet.setDrawValues(false);
        lineDataULSet.setColor(ThemeManager.colorWhiteBlack());

        LineDataSet lineDataLSet = new LineDataSet(lineLEntries,"大户");
        lineDataLSet.setDrawCircles(false);
        lineDataLSet.setDrawValues(false);
        lineDataLSet.setColor(ThemeManager.colorYellow());

        LineDataSet lineDataMSet = new LineDataSet(lineMEntries,"中户");
        lineDataMSet.setDrawCircles(false);
        lineDataMSet.setDrawValues(false);
        lineDataMSet.setColor(ThemeManager.colorFuchsia());

        LineDataSet lineDataSSet = new LineDataSet(lineSEntries,"小户");
        lineDataSSet.setDrawCircles(false);
        lineDataSSet.setDrawValues(false);
        lineDataSSet.setColor(ThemeManager.colorGreen());

        LineData lineData = new LineData();
        lineData.addDataSet(lineDataULSet);
        lineData.addDataSet(lineDataLSet);
        lineData.addDataSet(lineDataMSet);
        lineData.addDataSet(lineDataSSet);

        botmCaptialGameData.setData(lineData);
        return botmCaptialGameData;
    }

    public CombinedData createOrdernumData(ArrayList<Entry> lineULEntries,ArrayList<Entry> lineLEntries,ArrayList<Entry> lineMEntries,ArrayList<Entry> lineSEntries ){
        botmOrdernumData = new CombinedData();
        LineDataSet lineDataULSet = new LineDataSet(lineULEntries,"超大");
        lineDataULSet.setDrawCircles(false);
        lineDataULSet.setDrawValues(false);
        lineDataULSet.setColor(ThemeManager.colorWhiteBlack());

        LineDataSet lineDataLSet = new LineDataSet(lineLEntries,"大户");
        lineDataLSet.setDrawCircles(false);
        lineDataLSet.setDrawValues(false);
        lineDataLSet.setColor(ThemeManager.colorYellow());

        LineDataSet lineDataMSet = new LineDataSet(lineMEntries,"中户");
        lineDataMSet.setDrawCircles(false);
        lineDataMSet.setDrawValues(false);
        lineDataMSet.setColor(ThemeManager.colorFuchsia());

        LineDataSet lineDataSSet = new LineDataSet(lineSEntries,"小户");
        lineDataSSet.setDrawCircles(false);
        lineDataSSet.setDrawValues(false);
        lineDataSSet.setColor(ThemeManager.colorGreen());

        LineData lineData = new LineData();
        lineData.addDataSet(lineDataULSet);
        lineData.addDataSet(lineDataLSet);
        lineData.addDataSet(lineDataMSet);
        lineData.addDataSet(lineDataSSet);

        botmOrdernumData.setData(lineData);
        return botmOrdernumData;
    }

    /**
     * 大单净量
     */
    public CombinedData createBigNetVolumeData(ArrayList<Entry> lineBNVolumeEntries){
        botmBigNetVolume=new CombinedData();

        LineDataSet lineDataSet = new LineDataSet(lineBNVolumeEntries,"bigNetVolume");
        lineDataSet.setDrawCircles(false);
        lineDataSet.setDrawValues(false);
        lineDataSet.setColor(ThemeManager.colorWhiteBlack());

        LineData lineData = new LineData();
        lineData.addDataSet(lineDataSet);

        botmBigNetVolume.setData(lineData);
        return botmBigNetVolume;
    }

    public int getCount() {
        return minuteEntities.size();
    }

    public int getXLength(){
        return null == chartResponse ? 0 :chartResponse.totalTimes;
    }

    /**
     * 返回分时图开盘、收盘数据
     * @return
     */
    public Map<String, List<TimeZone>> getTradeTime(){
        if( null == timezoneEntity || null == timezoneEntity.tradingTime){
            timezoneEntity = TimezoneUtils.setTimezoneEntity(chartResponse,quoteItem);
        }
        return null == timezoneEntity ? null : timezoneEntity.tradingTime;
    }

    /**
     * 返回分时图日期数据
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

    /**
     * 返回左侧最小
     * @return
     */
    public float getDataMin() {
        return  minClosePrice;
    }
    /**
     * 返回左侧展示最小
     * @return
     */
    public float getShowDataMin() {
        return  minShowClosePrice;
    }

    /**
     * 返回左侧最大
     * @return
     */
    public float getDataMax() {
        return maxClosePrice;
    }

    /**
     * 返回左侧展示最大
     * @return
     */
    public float getShowDataMax() {
        return maxShowClosePrice;
    }

    public String getBottomLable(int position){
        MinuteEntity minuteEntity = minuteEntities.get(position == minuteEntities.size() ? (position-1) : position);
        if(null == minuteEntity || null==minuteEntity.bottomLable ){
            if( null== minuteEntity.datetime){
                return "";
            }
            String datetime = minuteEntity.datetime;
            datetime = datetime.substring(0,4)+"/"+datetime.substring(4,6)+"/"+datetime.substring(6,8)+ " " + datetime.substring(8,10)+":"+datetime.substring(10,12);
            minuteEntity.bottomLable = datetime;
        }
        return minuteEntity.bottomLable;
    }

    public String getBottomLeftLable(int position){
        MinuteEntity minuteEntity = minuteEntities.get(position);
        if( null==minuteEntity.bleftLable ){
            minuteEntity.bleftLable = MyUtils.getVol(minuteEntity.volume);
        }
        return minuteEntity.bleftLable;
    }

    public String getTopLeftLable(int position){
        MinuteEntity minuteEntity = minuteEntities.get(position == minuteEntities.size() ? (position-1) : position);
        return minuteEntity.cjpriceStr;
    }

    public String getTopRightLable(int position){
        MinuteEntity minuteEntity = minuteEntities.get(position == minuteEntities.size() ? (position-1) : position);
        if(minuteEntity.trightLable == null){
            float center = (getDataMax()+getDataMin())/2f;
            DecimalFormat df = new DecimalFormat("0.00");
            minuteEntity.trightLable = df.format((minuteEntity.averagePrice-center)/ minuteEntity.averagePrice*100)+"%";  // old
        }
        return minuteEntity.trightLable;
    }

    public ArrayList<OHLCItemBo> getChartItemsList() {
        return chartItemsList;
    }

    public OHLCItemBo getLastData() {
        return  null== chartItemsList || chartItemsList.isEmpty()? null:chartItemsList.get(chartItemsList.size()-1);
    }

    public List<MinuteEntity> getMSubLineEntities(){
        return mSubEntities;
    }

    /**
     * @return 走势副图指标最后一条数据
     */
    public MinuteEntity getLastMSubData(){
        return null == mSubEntities || mSubEntities.isEmpty() ? null : mSubEntities.get(mSubEntities.size()-1);
    }

    /**
     * 获取顶部图
     */
    public CombinedData getTopChartData() {
        return topData;
    }

    /**
     * 根据走势指标返回对应的底部图
     */
    public CombinedData getBottomChartData() {
        StockMinuteChartNew.MSubType mSubType = mState.mSubType;
        if(mSubType == StockMinuteChartNew.MSubType.VOLUME){
            return bottomData;
        } else if(mSubType == StockMinuteChartNew.MSubType.DDX){
            return botmDDXData;
        } else if(mSubType == StockMinuteChartNew.MSubType.DDY){
            return botmDDYData;
        } else if(mSubType == StockMinuteChartNew.MSubType.DDZ) {
            return botmDDZData;
        }else if(mSubType == StockMinuteChartNew.MSubType.BBD) {
            return botmBBDData;
        }else if(mSubType == StockMinuteChartNew.MSubType.RATIOBS) {
            return botmRatioBSData;
        }else if(mSubType == StockMinuteChartNew.MSubType.CAPTIALGAME) {
            return botmCaptialGameData;
        }else if(mSubType == StockMinuteChartNew.MSubType.ORDERNUM) {
            return botmOrdernumData;
        }else if (mSubType==StockMinuteChartNew.MSubType.BIGNETVOLUME){
            return botmBigNetVolume;
        }else if (mSubType==StockMinuteChartNew.MSubType.VOLRatio){
            return botmVOLRatioData;
        }
        return bottomData;
    }

    /**
     *修改线图设置，刷新页面
     */
    public void notifyTopDataSetChanged(){

    }

    /**
     * 最大值、最小值、涨跌比率
     * @param chartResponse
     */
    private void chartItemsList(ChartResponse chartResponse){
        mSubEntities = new ArrayList<>();
        minuteEntities = new ArrayList<>();
        chartItemsList = new ArrayList<>();

        maxClosePrice = chartResponse.maxClosePrice;
        minClosePrice = chartResponse.minClosePrice;
        // 参考价
        maxShowClosePrice = maxClosePrice;
        minShowClosePrice = minClosePrice;
        String ref = chartResponse.historyItems.get(0).reference_price;
        if (null != ref && !"".equals(ref)) {
            float refFloat = Float.parseFloat(ref);
            // 展示用最大最小值
            if ((maxClosePrice - refFloat) > (refFloat - minClosePrice)) {
                minShowClosePrice = minClosePrice - ((maxClosePrice - refFloat) - (refFloat - minClosePrice));
            } else {
                maxShowClosePrice = maxClosePrice + ((refFloat - minClosePrice) - (maxClosePrice - refFloat));
            }
        }

        float preCloceFloat = (maxClosePrice + this.minClosePrice) / 2f;
        float tmp1 = this.maxClosePrice - preCloceFloat;
        float tmp2 = preCloceFloat -  this.minClosePrice;
        tmp1 = tmp1 > tmp2 ? tmp1 : tmp2;

        DecimalFormat df = new DecimalFormat("0.00");
        this.PricePercentText = df.format(tmp1 / preCloceFloat * 100.0F);

        if( null == timezoneEntity || null == timezoneEntity.tradingTime){
            timezoneEntity = TimezoneUtils.setTimezoneEntity(chartResponse,quoteItem);
        }
    }
}
