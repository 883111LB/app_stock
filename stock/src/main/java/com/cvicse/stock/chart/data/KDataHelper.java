package com.cvicse.stock.chart.data;

import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.NonNull;

import com.cvicse.base.utils.NumberUtils;
import com.cvicse.base.utils.StringUtils;
import com.cvicse.stock.chart.formatter.CommonValueFormatter;
import com.cvicse.stock.chart.helper.StockTechState;
import com.cvicse.stock.chart.theme.ThemeManager;
import com.cvicse.stock.chart.utils.MyUtils;
import com.github.mikephil.charting.charts.ScatterChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.CandleData;
import com.github.mikephil.charting.data.CandleDataSet;
import com.github.mikephil.charting.data.CandleEntry;
import com.github.mikephil.charting.data.CombinedData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.ScatterData;
import com.github.mikephil.charting.data.ScatterDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.mitake.core.MarketType;
import com.mitake.core.OHLCItem;
import com.mitake.core.QuoteItem;
import com.mitake.core.parser.FQItem;
import com.mitake.core.parser.GBItem;
import com.mitake.core.util.FormatUtility;
import com.stock.config.FillConfig;
import com.stock.config.KSetting;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * k线图数据处理类
 * Created by liu_zlu on 2017/1/17 16:46
 */
public final class KDataHelper {
    private QuoteItem quoteItem;
    private ArrayList<GBItem> gbItems;
    private CopyOnWriteArrayList<OHLCItem> ohlcItems;
    private ArrayList<KLineEntity> kLineEntities; // 处理后的数据

    private CombinedData topCombinedData; // 顶部图表数据
    private CombinedData barCombinedData;
    private CombinedData kdjCombinedData;
    private CombinedData macdCombinedData;
    private CombinedData rsiCombinedData;
    private CombinedData bollCombinedData;
    private CombinedData obvCombinedData;
    private CombinedData wrCombinedData;
    private CombinedData dmiCombinedData;
    private CombinedData dmaCombinedData;
    private CombinedData sarCombinedData;
    private CombinedData vrCombinedData;
    private CombinedData crCombinedData;
    private CombinedData amoCombinedData;
    private CombinedData biasCombinedData;
    private CombinedData bbiCombinedData;
    private CombinedData cciCombinedData;
    private CombinedData mtmCombinedData;
    private CombinedData rocCombinedData;
    private CombinedData brarCombinedData;
    private CombinedData nviPviCombinedData;
    private CombinedData psyCombinedData;

    private Map<String,FQItem> mOhlcSubRDayMap;  // 日
    private Map<String,List<FQItem>> mOhlcSubRMonthMap;  // 月
    private Map<String,List<FQItem>> mOhlcSubRYearMap;  // 年
    private Map<String,List<FQItem>> mOhlcSubRWeekMap;  // 周

    public int unit = 1; // 成交量
    public int tranPriceUnit = 1; // 成交额
    public int obvUnit = 1;
    public float obvMax = 0;

    private StockTechState typeHpler; // K线相关状态

    public KDataHelper(StockTechState typeHpler){
        this.typeHpler = typeHpler;
    }

    public QuoteItem getQuoteItem() {
        return quoteItem;
    }

    /**
     * 设置k线图 （包含计算各个指标图的数据，创建指标图）
     * @param ohlcItems
     * @param quoteItem
     */
    public void addRequestData(CopyOnWriteArrayList<OHLCItem> ohlcItems, QuoteItem quoteItem, ArrayList<GBItem> gbItems){
        this.quoteItem = quoteItem;
        this.gbItems = gbItems;
        this.ohlcItems = ohlcItems;
        if( null==ohlcItems  || ohlcItems.isEmpty()){
            return ;
        }
        ArrayList<KLineEntity> oldList = new ArrayList<>(); // 旧数据
        ArrayList<KLineEntity> newList = new ArrayList<>(); // 新数据

        KLineEntity kLineEntity;
        for (int i = 0,size = ohlcItems.size(); i < size; i++) {
            OHLCItem ohlcitem = ohlcItems.get(i);
            kLineEntity = new KLineEntity();
            kLineEntity.close = NumberUtils.parseFloat(ohlcitem.closePrice);
            kLineEntity.reference_price = NumberUtils.parseFloat(ohlcitem.reference_price);
            kLineEntity.open = NumberUtils.parseFloat(ohlcitem.openPrice);
            String datetime = ohlcitem.datetime;
            kLineEntity. date = datetime.substring(0,4)+"/"+ datetime.substring(4,6)+"/"+ datetime.substring(6,8);
            if( 12 <= datetime.length() ){
                kLineEntity. date = kLineEntity. date +" " + datetime.substring(8,10)+":"+ datetime.substring(10,12);
            }
            if (null != ohlcitem.time && typeHpler.isMinute()){
                kLineEntity.date = kLineEntity.date +" " + ohlcitem.time.substring(0,2)+":"+ohlcitem.time.substring(2,4);
            }
            kLineEntity.high = NumberUtils.parseFloat(ohlcitem.highPrice);
            kLineEntity.low = NumberUtils.parseFloat(ohlcitem.lowPrice);
            kLineEntity.volume = NumberUtils.parseFloat(ohlcitem.tradeVolume);
            kLineEntity.tranPrice = NumberUtils.parseFloat(ohlcitem.transaction_price);

            kLineEntity.closeS = ohlcitem.closePrice;
            kLineEntity.reference_priceS = ohlcitem.reference_price;
            kLineEntity.openS = ohlcitem.openPrice;
            kLineEntity.highS = ohlcitem.highPrice;
            kLineEntity.lowS = ohlcitem.lowPrice;
            kLineEntity.volumeStr = FormatUtility.formatVolume(ohlcitem.tradeVolume);
            kLineEntity.tranPriceS = FormatUtility.formatVolume(ohlcitem.transaction_price);
            kLineEntity.iopv = ohlcitem.iopv;

            kLineEntity.isUp = kLineEntity.open < kLineEntity.close;
            // 计算涨跌、涨跌幅
            if( 0 == i ){
                kLineEntity.change = ohlcitem.change = FillConfig.DEFALUT;
                kLineEntity.changeRate = ohlcitem.changeRate = FillConfig.DEFALUT;
            }else{
                String market = this.quoteItem.market;
                float value = NumberUtils.parseFloat(ohlcitem.closePrice) - NumberUtils.parseFloat(ohlcItems.get(i-1).closePrice);
                ohlcitem.change = CommonValueFormatter.formatValue(market,quoteItem.subtype,value);
                ohlcitem.changeRate = CommonValueFormatter.formatValue(market,quoteItem.subtype,(NumberUtils.parseFloat(ohlcitem.change) /  NumberUtils.parseFloat(ohlcItems.get(i-1).closePrice)) *100 );
                kLineEntity.change = ohlcitem.change ;
                kLineEntity.changeRate = ohlcitem.changeRate;
            }
            newList.add(kLineEntity);  // new
        }
        if( null == kLineEntities ){
            kLineEntities = new ArrayList<>();
        }else{
            oldList.addAll(kLineEntities );
            if( oldList.get(0).date.equals(newList.get(newList.size()-1).date) ){
                newList.remove(newList.size()-1);
            }
        }
        kLineEntities.clear();
        if( null != newList && !newList.isEmpty() ){
            kLineEntities.addAll(newList);
        }else if( null != oldList && !oldList.isEmpty()){
            kLineEntities.addAll(oldList);
        }
        resetParams();
        float maxVolume = Float.MIN_VALUE;
        float maxTranPrice = Float.MIN_VALUE;
        obvMax = 0;
        for(int i = 0,length = kLineEntities.size();i < length ;i++){
            kLineEntity = kLineEntities.get(i);
            maxVolume = Math.max(kLineEntity.volume,maxVolume);
            maxTranPrice = Math.max(kLineEntity.tranPrice,maxTranPrice);
            kLineEntity.isUpPSY = i == 0 ? false : kLineEntity.close > kLineEntities.get(i-1).close;
            //第一次图表计算
            calculateMA(kLineEntity,i,true);
            calculateVR(kLineEntity,i,kLineEntities);
            calculateMACD(kLineEntity,i);
            calculateRSI(kLineEntity,i);
            calculateKDJ(kLineEntity,i);
            calculateOBV(kLineEntity,i);
            calculateWr(kLineEntity,i);
            calculateDMA(kLineEntity,i);
            calculateDmi(kLineEntity,i);
            calculateCR(kLineEntity,i,kLineEntities);
            calculateMTM(kLineEntity,i);
            calculateBBI(kLineEntity,i);
            calculateBIAS(kLineEntity,i);
            calculateROC(kLineEntity,i);
            calculatePSY(kLineEntity,i);
            calculateBRAR(kLineEntity,i);
            calculateNVIPVI(kLineEntity,i);
            calculateCCITPY(kLineEntity,i);
        }
        calculateBOLL(kLineEntities);
        calculateSAR();
        calculateCCI();
        calculateSub(); // 计算复权，并创建线图

        String unitStr = MyUtils.getVolUnit(maxVolume);
        if ("万手".equals(unitStr)) {
            unit = 4;
        } else if ("亿手".equals(unitStr)) {
            unit = 8;
        }
        unitStr = MyUtils.getObvUnit(maxVolume);
        if ("万".equals(unitStr)) {
            tranPriceUnit = 4;
        } else if ("亿".equals(unitStr)) {
            tranPriceUnit = 8;
        }
        unitStr = MyUtils.getObvUnit(obvMax);
        if ("万".equals(unitStr)) {
            obvUnit = 4;
        } else if ("亿".equals(unitStr)) {
            obvUnit = 8;
        }
    }

    /**
     * 添加复权数据
     */
    public void addRequestOHLCSUBData(CopyOnWriteArrayList<FQItem> ohlcSubRsList){
        if( null == ohlcSubRsList || ohlcSubRsList.isEmpty() ){
            return;
        }

        mOhlcSubRDayMap = new HashMap<>();
        mOhlcSubRMonthMap = new HashMap<>();
        mOhlcSubRYearMap = new HashMap<>();
        mOhlcSubRWeekMap = new HashMap<>();

        for (int i = 0,size = ohlcSubRsList.size(); i < size; i++) {
            FQItem ohlcSubR = ohlcSubRsList.get(i);
            String date = ohlcSubR.dateTime;  // 日期
            date = date.substring(0,4)+"/"+date.substring(4,6)+"/"+date.substring(6,8);
            mOhlcSubRDayMap.put(date,ohlcSubR);

            // 月
            List<FQItem> ohlcSubRs = null == mOhlcSubRMonthMap.get(date.substring(0, 7)) ? (new ArrayList<FQItem>()) : mOhlcSubRMonthMap.get(date.substring(0, 7)) ;
            ohlcSubRs.add(ohlcSubR);
            mOhlcSubRMonthMap.put(date.substring(0, 7),ohlcSubRs);

            // 年
            ohlcSubRs = null == mOhlcSubRYearMap.get(date.substring(0, 4)) ? (new ArrayList<FQItem>()) : mOhlcSubRYearMap.get(date.substring(0, 4)) ;
            ohlcSubRs.add(ohlcSubR);
            mOhlcSubRYearMap.put(date.substring(0,4),ohlcSubRs);

            // 周
            String weekWithToday = getWeekWithToday(date.substring(0, 10));
            ohlcSubRs = null == mOhlcSubRWeekMap.get(weekWithToday) ? (new ArrayList<FQItem>()) : mOhlcSubRWeekMap.get(weekWithToday) ;
            ohlcSubRs.add(ohlcSubR);
            mOhlcSubRWeekMap.put(weekWithToday,ohlcSubRs);
        }
        calculateSub();
    }

    /**
     * 计算复权信息
     */
    private void calculateSub() {
        if( typeHpler.isMinute() || null==kLineEntities || kLineEntities.isEmpty()){
            // 创建副图指标图
            createChartData();
            return ;
        }
        for (int i = 0,size = kLineEntities.size(); i < size; i++) {
            KLineEntity kLineEntity = kLineEntities.get(i);
            String datetime = kLineEntity.date;
            if( TechChartType.Type.DAY == typeHpler.type && null!= mOhlcSubRDayMap){
                // 日K
                FQItem ohlcSubR = mOhlcSubRDayMap.get(datetime);
                if( null != ohlcSubR ){
                    kLineEntity.kSubEntityList = null == kLineEntity.kSubEntityList ? (new ArrayList<FQItem>()) :  kLineEntity.kSubEntityList;
                    kLineEntity.kSubEntityList.add(ohlcSubR);
                }
            }else if(TechChartType.Type.WEEK == typeHpler.type && null!= mOhlcSubRWeekMap){
                // 周K
                List<FQItem> ohlcSubRs = mOhlcSubRWeekMap.get(getWeekWithToday(datetime));
                kLineEntity.kSubEntityList = ohlcSubRs;
            }else if(TechChartType.Type.MONTH == typeHpler.type && null!= mOhlcSubRMonthMap){
                // 月K
                List<FQItem> ohlcSubR =  mOhlcSubRMonthMap.get(datetime.substring(0,7));
                kLineEntity.kSubEntityList = ohlcSubR;
            }else if(null!= mOhlcSubRYearMap){
                // 年K
                List<FQItem> ohlcSubR =  mOhlcSubRYearMap.get(datetime.substring(0,4));
                kLineEntity.kSubEntityList = ohlcSubR;
            }
        }
        // 创建副图指标图
        createChartData();
    }

    /**
     * 根据日期计算所在周(一年中第几周)
     * @param date
     * @return 年+第几周
     */
    public static String getWeekWithToday(String date) {
        if( null == date ){
            return "-1";
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Calendar calendar1 =  Calendar.getInstance();
        try {
            calendar1.setTime(simpleDateFormat.parse(date));
            int week = calendar1.get(Calendar.WEEK_OF_YEAR);
            calendar1.add(Calendar.DAY_OF_MONTH,-7);
            int year = calendar1.get(Calendar.YEAR);

            if( week < calendar1.get(Calendar.WEEK_OF_YEAR) ){
                year += 1;
            }
            return year+""+week;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "-1";
    }

    /**
     * 创建图表数据（包含：创建副图各个指标图）
     */
    public void createChartData() {
        if( null == kLineEntities || kLineEntities.isEmpty() ){
            return;
        }
        int length = kLineEntities.size();
        int[] volColors = new int[length];
        ArrayList<BarEntry> barEntries = new ArrayList<>();
        ArrayList<CandleEntry> candleEntries = new ArrayList<>();
        ArrayList<Entry> line5Entries = new ArrayList<>();
        ArrayList<Entry> line10Entries = new ArrayList<>();
        ArrayList<Entry> line20Entries = new ArrayList<>();

        ArrayList<Entry> linekEntries = new ArrayList<>();
        ArrayList<Entry> linedEntries = new ArrayList<>();
        ArrayList<Entry> linejEntries = new ArrayList<>();

        int[] macdColors = new int[length];
        ArrayList<BarEntry> macdBarEntries = new ArrayList<>();
        ArrayList<Entry> linekDifEntries = new ArrayList<>();
        ArrayList<Entry> linedDeaEntries = new ArrayList<>();

        ArrayList<Entry> lineRsi1Entries = new ArrayList<>();
        ArrayList<Entry> lineRsi2Entries = new ArrayList<>();
        ArrayList<Entry> lineRsi3Entries = new ArrayList<>();

        ArrayList<Entry> lineMbEntries = new ArrayList<>();
        ArrayList<Entry> lineDnEntries = new ArrayList<>();
        ArrayList<Entry> lineUpEntries = new ArrayList<>();

        ArrayList<Entry> lineOBVEntries = new ArrayList<>();

        ArrayList<Entry> lineWr1Entries = new ArrayList<>();
        ArrayList<Entry> lineWr2Entries = new ArrayList<>();

        ArrayList<Entry> linePdiEntries = new ArrayList<>();
        ArrayList<Entry> lineMdiEntries = new ArrayList<>();
        ArrayList<Entry> lineAdxEntries = new ArrayList<>();
        ArrayList<Entry> lineAdxrEntries = new ArrayList<>();
        ArrayList<Entry> lineDif10Entries = new ArrayList<>();
        ArrayList<Entry> lineDifMa50Entries = new ArrayList<>();
        ArrayList<Entry> lineSarEntries = new ArrayList<>();

        ArrayList<Entry> lineVr13Entries = new ArrayList<>();
        ArrayList<Entry> lineVr26Entries = new ArrayList<>();

        ArrayList<Entry> lineCrMidEntries = new ArrayList<>();
        ArrayList<Entry> lineCr1Entries = new ArrayList<>();
        ArrayList<Entry> lineCr2Entries = new ArrayList<>();
        ArrayList<Entry> lineCr3Entries = new ArrayList<>();
        ArrayList<Entry> lineCr4Entries = new ArrayList<>();

        ArrayList<BarEntry> barAMOEntries = new ArrayList<>();
        ArrayList<Entry> lineAMOM1Entries = new ArrayList<>();
        ArrayList<Entry> lineAMOM2Entries = new ArrayList<>();
        ArrayList<Entry> lineAMOM3Entries = new ArrayList<>();

        ArrayList<Entry> lineMTMEntries = new ArrayList<>();
        ArrayList<Entry> lineMAMTMEntries = new ArrayList<>();

        ArrayList<Entry> lineBBIEntries = new ArrayList<>();

        ArrayList<Entry> lineBIAS6Entries = new ArrayList<>();
        ArrayList<Entry> lineBIAS12Entries = new ArrayList<>();
        ArrayList<Entry> lineBIAS24Entries = new ArrayList<>();

        ArrayList<Entry> lineROCEntries = new ArrayList<>();
        ArrayList<Entry> lineROCMAEntries = new ArrayList<>();

        ArrayList<Entry> linePSYEntries = new ArrayList<>();
        ArrayList<Entry> linePSYMAEntries = new ArrayList<>();

        ArrayList<Entry> lineAREntries = new ArrayList<>();
        ArrayList<Entry> lineBREntries = new ArrayList<>();

        ArrayList<Entry> lineNVIEntries = new ArrayList<>();
        ArrayList<Entry> linePVIEntries = new ArrayList<>();
        ArrayList<Entry> lineCCIEntries = new ArrayList<>();
        for(int i = 0;i < length;i++){
            KLineEntity kLineEntity = kLineEntities.get(i);
            barEntries.add(new BarEntry(i,kLineEntity.volume));
            if(kLineEntity.open > kLineEntity.close){
                volColors[i] = ThemeManager.colorGreen();
            } else if(kLineEntity.open < kLineEntity.close) {
                volColors[i] = ThemeManager.colorRed();
            }else{
                if( i == 0 ){
                    volColors[i] = ThemeManager.colorRed();
                }else{
                    float close = kLineEntities.get(i - 1).close;
                    if( kLineEntity.open < close ){
                        volColors[i] = ThemeManager.colorGreen();
                    }else if( kLineEntity.open > close){
                        volColors[i] = ThemeManager.colorRed();
                    }else{
                        volColors[i] = volColors[i-1];
                    }
                }
            }
            float high = NumberUtils.parseFloat(kLineEntity.high+"");
            float low = NumberUtils.parseFloat(kLineEntity.low + "");
            float open = NumberUtils.parseFloat(kLineEntity.open + "");
            float close = NumberUtils.parseFloat(kLineEntity.close + "");
            GCandleEntry candleEntry = new GCandleEntry(i, high, low, open, close);
            // 2018.5.7 增加复权标识
            if( null == kLineEntity.kSubEntityList || kLineEntity.kSubEntityList.isEmpty()){
                candleEntry.setSub(false);
            }else{
                candleEntry.setSub(true);
            }
            candleEntries.add(candleEntry);

            if (kLineEntity.MA1 != 0) {
                line5Entries.add(new Entry(i,kLineEntity.MA1 = NumberUtils.parseFloat(kLineEntity.MA1+"")));
            }
            if (kLineEntity.MA2 != 0) {
                line10Entries.add(new Entry(i,kLineEntity.MA2 = NumberUtils.parseFloat(kLineEntity.MA2+"")));
            }
            if (kLineEntity.MA3 != 0) {
                line20Entries.add(new Entry(i,kLineEntity.MA3 = NumberUtils.parseFloat(kLineEntity.MA3+"")));
            }

            if(kLineEntity.k != 0){
                linekEntries.add(new Entry(i,kLineEntity.k));
            }
            if(kLineEntity.d != 0){
                linedEntries.add(new Entry(i,kLineEntity.d));
            }
            if(kLineEntity.j != 0){
                linejEntries.add(new Entry(i,kLineEntity.j));
            }
            if(kLineEntity.macd > 0){
                macdColors[i] = ThemeManager.colorRed();
            } else {
                macdColors[i] = ThemeManager.colorGreen();
            }
            macdBarEntries.add(new BarEntry(i,kLineEntity.macd = NumberUtils.parseFloat(kLineEntity.macd+"")));

            if(kLineEntity.dif != 0){
                linekDifEntries.add(new Entry(i,kLineEntity.dif = NumberUtils.parseFloat(kLineEntity.dif+"")));
            }
            if(kLineEntity.dea != 0){
                linedDeaEntries.add(new Entry(i,kLineEntity.dea = NumberUtils.parseFloat(kLineEntity.dea+"")));
            }

            if(kLineEntity.rsi1 != 0){
                lineRsi1Entries.add(new Entry(i,kLineEntity.rsi1));
            }
            if(kLineEntity.rsi2 != 0){
                lineRsi2Entries.add(new Entry(i,kLineEntity.rsi2));
            }
            if(kLineEntity.rsi3 != 0){
                lineRsi3Entries.add(new Entry(i,kLineEntity.rsi3));
            }

            if(kLineEntity.mb != 0){
                lineMbEntries.add(new Entry(i,kLineEntity.mb = NumberUtils.parseFloat(kLineEntity.mb+"")));
            }
            if(kLineEntity.up != 0){
                lineUpEntries.add(new Entry(i,kLineEntity.up = NumberUtils.parseFloat(kLineEntity.up+"")));
            }
            if(kLineEntity.dn != 0){
                lineDnEntries.add(new Entry(i,kLineEntity.dn = NumberUtils.parseFloat(kLineEntity.dn+"")));
            }
            lineOBVEntries.add(new Entry(i,kLineEntity.obv));
            lineWr1Entries.add(new Entry(i,kLineEntity.wr5));
            lineWr2Entries.add(new Entry(i,kLineEntity.wr10));

            linePdiEntries.add(new Entry(i,kLineEntity.pdi));
            lineMdiEntries.add(new Entry(i,kLineEntity.mdi));
            lineAdxEntries.add(new Entry(i,kLineEntity.adx));
            lineAdxrEntries.add(new Entry(i,kLineEntity.adxr));

            if(kLineEntity.dif10 != 0){
                lineDif10Entries.add(new Entry(i,kLineEntity.dif10=NumberUtils.parseFloat(kLineEntity.dif10+"")));
            }
            if(kLineEntity.difma50 != 0){
                lineDifMa50Entries.add(new Entry(i,kLineEntity.difma50 = NumberUtils.parseFloat(kLineEntity.difma50+"")));
            }

            if(kLineEntity.vr13 != 0){
                lineVr13Entries.add(new Entry(i,kLineEntity.vr13));
            }
            if(kLineEntity.vr26 != 0){
                lineVr26Entries.add(new Entry(i,kLineEntity.vr26));
            }
            lineSarEntries.add(new Entry(i,kLineEntity.sar));

            if(kLineEntity.cr != 0){
                lineCrMidEntries.add(new Entry(i,kLineEntity.cr));
            }
            if(kLineEntity.crMa1 != 0){
                lineCr1Entries.add(new Entry(i,kLineEntity.crMa1));
            }
            if(kLineEntity.crMa2 != 0){
                lineCr2Entries.add(new Entry(i,kLineEntity.crMa2));
            }
            if(kLineEntity.crMa3 != 0){
                lineCr3Entries.add(new Entry(i,kLineEntity.crMa3));
            }
            if(kLineEntity.crMa4 != 0){
                lineCr4Entries.add(new Entry(i,kLineEntity.crMa4));
            }

            // AMO指标
            barAMOEntries.add(new BarEntry(i,kLineEntity.tranPrice));
            if( 0 != kLineEntity.amoM1 ){
                lineAMOM1Entries.add(new Entry(i,kLineEntity.amoM1));
            }
            if( 0 != kLineEntity.amoM2 ){
                lineAMOM2Entries.add(new Entry(i,kLineEntity.amoM2));
            }
            if( 0 != kLineEntity.amoM3 ){
                lineAMOM3Entries.add(new Entry(i,kLineEntity.amoM3));
            }

            // MTM指标
            if( 0 != kLineEntity.mtm ){
                lineMTMEntries.add(new Entry(i,kLineEntity.mtm));
            }
            if( 0 != kLineEntity.mtmMA ){
                lineMAMTMEntries.add(new Entry(i,kLineEntity.mtmMA));
            }

            if( 0 != kLineEntity.bbi){
                lineBBIEntries.add(new Entry(i,kLineEntity.bbi));
            }

            if( 0 != kLineEntity.bias6 ){
                lineBIAS6Entries.add(new Entry(i,kLineEntity.bias6));
            }
            if( 0 != kLineEntity.bias12 ){
                lineBIAS12Entries.add(new Entry(i,kLineEntity.bias12));
            }
            if( 0 != kLineEntity.bias24 ){
                lineBIAS24Entries.add(new Entry(i,kLineEntity.bias24));
            }

            if( 0!= kLineEntity.roc ){
                lineROCEntries.add(new Entry(i,kLineEntity.roc));
            }
            if( 0!= kLineEntity.rocMA ){
                lineROCMAEntries.add(new Entry(i,kLineEntity.rocMA));
            }
            if( 0!= kLineEntity.psy ){
                linePSYEntries.add(new Entry(i,kLineEntity.psy));
            }
            if( 0!= kLineEntity.psyMA ){
                linePSYMAEntries.add(new Entry(i,kLineEntity.psyMA));
            }

            if( 0 != kLineEntity.ar ){
                lineAREntries.add(new Entry(i,kLineEntity.ar));
            }
            if( 0 != kLineEntity.br ){
                lineBREntries.add(new Entry(i,kLineEntity.br));
            }
            if( 0 != kLineEntity.cci ){
                lineCCIEntries.add(new Entry(i,kLineEntity.cci));
            }
            lineNVIEntries.add(new Entry(i,kLineEntity.nvi));
            linePVIEntries.add(new Entry(i,kLineEntity.pvi));
        }

        /********************************* 创建副图各个指标图 ***************************************/
        createBarData(barEntries,volColors);
        createTopData(candleEntries,line5Entries,line10Entries,line20Entries);
        createKDJData(linekEntries,linedEntries,linejEntries);
        createMacdData(macdBarEntries,macdColors,linekDifEntries,linedDeaEntries);
        createRsiData(lineRsi1Entries,lineRsi2Entries,lineRsi3Entries);
        createBollData(lineMbEntries,lineUpEntries,lineDnEntries,candleEntries);
        createObvData(lineOBVEntries);
        createWrData(lineWr1Entries,lineWr2Entries);
        createDmiData(linePdiEntries,lineMdiEntries,lineAdxEntries,lineAdxrEntries);
        createDmaData(lineDif10Entries,lineDifMa50Entries);
        createSarData(lineSarEntries,candleEntries);
        createVrData(lineVr13Entries,lineVr26Entries);
        createCrData(lineCrMidEntries,lineCr1Entries,lineCr2Entries,lineCr3Entries,lineCr4Entries);
        createAMOData(barAMOEntries,lineAMOM1Entries,lineAMOM2Entries,lineAMOM3Entries,volColors);
        createBIASData(lineBIAS6Entries,lineBIAS12Entries,lineBIAS24Entries);
        createBBIData(lineBBIEntries);
        createMTMData(lineMTMEntries,lineMAMTMEntries);
        createROCData(lineROCEntries,lineROCMAEntries);
        createBRARData(lineBREntries,lineAREntries);
        createNVIPVIData(lineNVIEntries,linePVIEntries);
        createPSYData(linePSYEntries,linePSYMAEntries);
        createCCIData(lineCCIEntries);
    }

    private CombinedData createCCIData(ArrayList<Entry> lineCCIEntries) {
        cciCombinedData = new CombinedData();
        LineDataSet lineDataSet = new LineDataSet(lineCCIEntries,"CCI");
        lineDataSet.setDrawCircles(false);
        lineDataSet.setDrawValues(false);
        lineDataSet.setColor(ThemeManager.colorWhiteBlack());
        LineData lineData = new LineData();
        lineData.addDataSet(lineDataSet);
        cciCombinedData.setData(lineData);
        return cciCombinedData;
    }

    private CombinedData createPSYData(ArrayList<Entry> linePSYEntries, ArrayList<Entry> linePSYMAEntries) {
        psyCombinedData = new CombinedData();
        LineDataSet lineDataSetPSY = new LineDataSet(linePSYEntries,"PSY");
        lineDataSetPSY.setDrawCircles(false);
        lineDataSetPSY.setDrawValues(false);
        lineDataSetPSY.setColor(ThemeManager.colorWhiteBlack());
        LineDataSet lineDataSetPSYMA = new LineDataSet(linePSYMAEntries,"PSYMA");
        lineDataSetPSYMA.setDrawCircles(false);
        lineDataSetPSYMA.setDrawValues(false);
        lineDataSetPSYMA.setColor(ThemeManager.colorYellow());
        LineData lineData = new LineData();
        lineData.addDataSet(lineDataSetPSY);
        lineData.addDataSet(lineDataSetPSYMA);
        psyCombinedData.setData(lineData);
        return psyCombinedData;
    }

    private CombinedData createNVIPVIData(ArrayList<Entry> lineNVIEntries, ArrayList<Entry> linePVIEntries) {
        nviPviCombinedData = new CombinedData();
        LineDataSet lineDataSetPVI = new LineDataSet(linePVIEntries,"PVI");
        lineDataSetPVI.setDrawCircles(false);
        lineDataSetPVI.setDrawValues(false);
        lineDataSetPVI.setColor(ThemeManager.colorWhiteBlack());

        LineDataSet lineDataSetNVI = new LineDataSet(lineNVIEntries,"NVI");
        lineDataSetNVI.setDrawCircles(false);
        lineDataSetNVI.setDrawValues(false);
        lineDataSetNVI.setColor(ThemeManager.colorYellow());
        LineData lineData = new LineData();
        lineData.addDataSet(lineDataSetNVI);
        lineData.addDataSet(lineDataSetPVI);
        nviPviCombinedData.setData(lineData);
        return nviPviCombinedData;
    }

    private CombinedData createBRARData(ArrayList<Entry> lineBREntries, ArrayList<Entry> lineAREntries) {
        brarCombinedData = new CombinedData();
        LineDataSet lineDataSetBR= new LineDataSet(lineBREntries,"BR");
        lineDataSetBR.setDrawCircles(false);
        lineDataSetBR.setDrawValues(false);
        lineDataSetBR.setColor(ThemeManager.colorWhiteBlack());

        LineDataSet lineDataSetAR= new LineDataSet(lineAREntries,"AR");
        lineDataSetAR.setDrawCircles(false);
        lineDataSetAR.setDrawValues(false);
        lineDataSetAR.setColor(ThemeManager.colorYellow());
        LineData lineData = new LineData();
        lineData.addDataSet(lineDataSetBR);
        lineData.addDataSet(lineDataSetAR);
        brarCombinedData.setData(lineData);
        return brarCombinedData;
    }

    private CombinedData createROCData(ArrayList<Entry> lineROCEntries, ArrayList<Entry> lineROCMAEntries) {
        rocCombinedData = new CombinedData();
        LineDataSet lineDataSetROC = new LineDataSet(lineROCEntries,"ROC");
        lineDataSetROC.setDrawCircles(false);
        lineDataSetROC.setDrawValues(false);
        lineDataSetROC.setColor(ThemeManager.colorWhiteBlack());
        LineDataSet lineDataSetROCMA = new LineDataSet(lineROCMAEntries,"ROCMA");
        lineDataSetROCMA.setDrawCircles(false);
        lineDataSetROCMA.setDrawValues(false);
        lineDataSetROCMA.setColor(ThemeManager.colorYellow());
        LineData lineData = new LineData();
        lineData.addDataSet(lineDataSetROC);
        lineData.addDataSet(lineDataSetROCMA);
        rocCombinedData.setData(lineData);
        return rocCombinedData;
    }

    private CombinedData createMTMData(ArrayList<Entry> lineMTMEntries, ArrayList<Entry> lineMAMTMEntries) {
        mtmCombinedData = new CombinedData();
        LineDataSet lineDataSetMTM = new LineDataSet(lineMTMEntries,"MTM");
        lineDataSetMTM.setDrawCircles(false);
        lineDataSetMTM.setDrawValues(false);
        lineDataSetMTM.setColor(ThemeManager.colorWhiteBlack());
        LineDataSet lineDataSetMTMMA = new LineDataSet(lineMAMTMEntries,"MTMMA");
        lineDataSetMTMMA.setDrawCircles(false);
        lineDataSetMTMMA.setDrawValues(false);
        lineDataSetMTMMA.setColor(ThemeManager.colorYellow());
        LineData lineData = new LineData();
        lineData.addDataSet(lineDataSetMTM);
        lineData.addDataSet(lineDataSetMTMMA);
        mtmCombinedData.setData(lineData);
        return mtmCombinedData;
    }

    private CombinedData createBBIData(ArrayList<Entry> lineBBIEntries) {
        bbiCombinedData = new CombinedData();
        LineDataSet lineDataSet = new LineDataSet(lineBBIEntries,"");
        lineDataSet.setDrawCircles(false);
        lineDataSet.setDrawValues(false);
        LineData lineData = new LineData();
        lineData.addDataSet(lineDataSet);
        bbiCombinedData.setData(lineData);
        return bbiCombinedData;
    }

    private CombinedData createBIASData(ArrayList<Entry> lineBIAS6Entries, ArrayList<Entry> lineBIAS12Entries, ArrayList<Entry> lineBIAS24Entries) {
        biasCombinedData = new CombinedData();
        LineDataSet lineDataBIAS6 = new LineDataSet(lineBIAS6Entries,"BIAS6");
        lineDataBIAS6.setDrawCircles(false);
        lineDataBIAS6.setDrawValues(false);
        lineDataBIAS6.setColor(ThemeManager.colorWhiteBlack());
        LineDataSet lineDataBIAS12 = new LineDataSet(lineBIAS12Entries,"BIAS6");
        lineDataBIAS12.setDrawCircles(false);
        lineDataBIAS12.setDrawValues(false);
        lineDataBIAS12.setColor(ThemeManager.colorYellow());
        LineDataSet lineDataBIAS24 = new LineDataSet(lineBIAS24Entries,"BIAS6");
        lineDataBIAS24.setDrawCircles(false);
        lineDataBIAS24.setDrawValues(false);
        lineDataBIAS24.setColor(ThemeManager.colorFuchsia());

        LineData lineData = new LineData();
        lineData.addDataSet(lineDataBIAS6);
        lineData.addDataSet(lineDataBIAS12);
        lineData.addDataSet(lineDataBIAS24);
        biasCombinedData.setData(lineData);
        return biasCombinedData;
    }

    private void createAMOData(ArrayList<BarEntry> barAMOEntries, ArrayList<Entry> lineAMOM1Entries, ArrayList<Entry> lineAMOM2Entries, ArrayList<Entry> lineAMOM3Entries,int[] volColors) {
        amoCombinedData = new CombinedData();
        BarDataSet barDataSet = new BarDataSet(barAMOEntries,"成交额");
        barDataSet.setHighlightEnabled(true);// 此处必须得写  避免冲突，否则会导致联动不显示
        barDataSet.setHighLightAlpha(255);  // 设置点击后高亮颜色透明度
        barDataSet.setHighLightColor(ThemeManager.colorWhiteBlack()); // 设置点击某个点时，横竖两条线的颜色
        barDataSet.setDrawValues(false);  // 是否绘制该 DataSet 的文本。
        barDataSet.setColors(volColors);
        BarData barData = new BarData(barDataSet);

        LineData mALineData = new LineData();
        if(KSetting.getAverage_Visiable1()){
            LineDataSet lineData5 = setMaLine(5,  lineAMOM1Entries);
            mALineData.addDataSet(lineData5);
        }
        if(KSetting.getAverage_Visiable2()){
            LineDataSet lineData10 = setMaLine(10,  lineAMOM2Entries);
            mALineData.addDataSet(lineData10);
        }
        if(KSetting.getAverage_Visiable3()){
            LineDataSet lineData30 = setMaLine(30, lineAMOM3Entries);
            mALineData.addDataSet(lineData30);
        }
        amoCombinedData.setData(barData);
        amoCombinedData.setData(mALineData);
    }

    /**
     * 创建成交量图表数据对象
     * @param barEntries 成交量数据
     * @param volColors 涨跌颜色
     */
    public CombinedData createBarData(ArrayList<BarEntry> barEntries,int[] volColors) {
        barCombinedData = new CombinedData();

        BarDataSet barDataSet = new BarDataSet(barEntries, "成交量");
        //barDataSet.setBarSpacePercent(50); //bar空隙
        barDataSet.setHighlightEnabled(true);// 此处必须得写  避免冲突，否则会导致联动不显示
        barDataSet.setHighLightAlpha(255);  // 设置点击后高亮颜色透明度
        barDataSet.setHighLightColor(ThemeManager.colorWhiteBlack()); // 设置点击某个点时，横竖两条线的颜色
        barDataSet.setDrawValues(false);  // 是否绘制该 DataSet 的文本。
        barDataSet.setColors(volColors);

        BarData barData = new BarData(barDataSet);
        barCombinedData.setData(barData);
        return barCombinedData;
    }

    /**
     * 创建顶部图表数据对象
     */
    private CombinedData createTopData(ArrayList<CandleEntry> candleEntries,ArrayList<Entry> line5Entries, ArrayList<Entry> line10Entries, ArrayList<Entry> line20Entries){
        topCombinedData = new CombinedData();
        LineData mALineData = new LineData();
        if(KSetting.getAverage_Visiable1()){
            LineDataSet lineData5 = setMaLine(5,  line5Entries);
            mALineData.addDataSet(lineData5);
        }
        if(KSetting.getAverage_Visiable2()){
            LineDataSet lineData10 = setMaLine(10,  line10Entries);
            mALineData.addDataSet(lineData10);
        }
        if(KSetting.getAverage_Visiable3()){
            LineDataSet lineData30 = setMaLine(30, line20Entries);
            mALineData.addDataSet(lineData30);
        }

        // CandleDataSet：蜡烛图数据集合
        GCandleDataSet candleDataSet = new GCandleDataSet(candleEntries, "Data Set");
        candleDataSet.setAxisDependency(YAxis.AxisDependency.LEFT);
//        set1.setColor(Color.rgb(80, 80, 80));
//        candleDataSet.setShadowColor(ThemeManager.colorFuchsia());  // 阴影色
        candleDataSet.setShadowColorSameAsCandle(true);  // 影线颜色与实体一致
        candleDataSet.setShadowWidth(0.7f);

        candleDataSet.setDecreasingColor(ThemeManager.colorGreen());  // 开盘价 > 收盘价
        candleDataSet.setDecreasingPaintStyle(Paint.Style.FILL);

        candleDataSet.setIncreasingColor(ThemeManager.colorRed());  // 开盘价 < 收盘价
        candleDataSet.setIncreasingPaintStyle(Paint.Style.FILL);

        candleDataSet.setNeutralColor(Color.RED);//当天价格不涨不跌（一字线）颜色
        // 修改源码CandleStickChartRenderer

        // candleDataSet.setDrawHorizontalHighlightIndicator(false); //是否是主图 ，true主图添加横线 高亮线，false副图不添加高亮横线
        candleDataSet.setHighLightColor(ThemeManager.colorWhiteBlack());  // 高亮颜色
        candleDataSet.setHighlightLineWidth(1);  // 选中蜡烛时的线宽
        candleDataSet.setDrawValues(false);  // //在图表中的元素上面是否显示数值
        candleDataSet.setLegendable(false);
        candleDataSet.setAxisDependency(YAxis.AxisDependency.LEFT);  //以左边坐标轴为准 还是以右边坐标轴为基准

        candleDataSet.setSub(KSetting.getKRightSubType()); // 复权类型
        candleDataSet.setSubColor(ThemeManager.colorWhiteBlack());  // 复权标识颜色
        candleDataSet.setShowSubFlag(true);   // 展示复权标识

        GCandleData candleData = new GCandleData(candleDataSet);

        topCombinedData.setData(candleData);
        topCombinedData.setData(mALineData);
        return topCombinedData;
    }

    private CombinedData createKDJData(ArrayList<Entry> linekEntries, ArrayList<Entry> linedEntries, ArrayList<Entry> linejEntries){
        kdjCombinedData = new CombinedData();
        LineDataSet lineDatak = new LineDataSet(linekEntries,"");
        LineDataSet lineDatad = new LineDataSet(linedEntries,"");
        LineDataSet lineDataj = new LineDataSet(linejEntries,"");
        lineDatak.setDrawCircles(false);
        lineDatak.setDrawValues(false);
        lineDatak.setColor(ThemeManager.colorWhiteBlack());
        lineDatad.setDrawCircles(false);
        lineDatad.setDrawValues(false);
        lineDatad.setColor(ThemeManager.colorYellow());
        lineDataj.setDrawCircles(false);
        lineDataj.setDrawValues(false);
        lineDataj.setColor(ThemeManager.colorFuchsia());
        LineData kdjLineData = new LineData();
        kdjLineData.addDataSet(lineDatak);
        kdjLineData.addDataSet(lineDatad);
        kdjLineData.addDataSet(lineDataj);
        kdjCombinedData.setData(kdjLineData);
        return kdjCombinedData;
    }

    private CombinedData createMacdData(ArrayList<BarEntry> barEntries,int[] macdColors, ArrayList<Entry> lineDifEntries, ArrayList<Entry> linejEntries){
        // 组合图
        macdCombinedData = new CombinedData();

        //BarDataSet： 柱形图数据集合
        BarDataSet barDataSet = new BarDataSet(barEntries,"");
        barDataSet.setDrawValues(false);  // 是否在点上绘制Value
        barDataSet.setColors(macdColors);  // 柱子颜色
        // BarData： 柱形图
        BarData barData = new BarData();
        barData.addDataSet(barDataSet);

        // LineDataSet：折线图数据集合
        LineDataSet lineDataDif = new LineDataSet(lineDifEntries,"");
        lineDataDif.setLineWidth(1f);  //  设置线宽（最小= 0.2F，最大= 10F）；默认1F；注意：线越细==性能越好，线越厚==性能越糟糕 。
        lineDataDif.setDrawCircles(false);  // 将绘制 LineDataSet 的线圈。默认为true。
        lineDataDif.setColor(ThemeManager.colorWhiteBlack());
        lineDataDif.setAxisDependency(YAxis.AxisDependency.LEFT);
        lineDataDif.setDrawValues(false);  // 启用/禁用 绘制所有 DataSets 数据对象包含的数据的值文本。

        LineDataSet lineDataDea = new LineDataSet(linejEntries,"");
        lineDataDea.setLineWidth(1f);
        lineDataDea.setColor(ThemeManager.colorYellow());
        lineDataDea.setDrawCircles(false);
        lineDataDea.setAxisDependency(YAxis.AxisDependency.LEFT);
        lineDataDea.setDrawValues(false);

        //LineData：折线图
        LineData kdjLineData = new LineData();
        kdjLineData.addDataSet(lineDataDif);
        kdjLineData.addDataSet(lineDataDea);

        macdCombinedData.setData(kdjLineData);
        macdCombinedData.setData(barData);
        return macdCombinedData;
    }

    private CombinedData createRsiData(ArrayList<Entry> lineRsi1fEntries, ArrayList<Entry> lineRsi2fEntries, ArrayList<Entry> lineRsi3fEntries){
        rsiCombinedData = new CombinedData();
        LineDataSet lineDataRsi1 = new LineDataSet(lineRsi1fEntries,"");
        LineDataSet lineDataRsi2 = new LineDataSet(lineRsi2fEntries,"");
        LineDataSet lineDataRsi3 = new LineDataSet(lineRsi3fEntries,"");
        lineDataRsi1.setDrawValues(false);
        lineDataRsi2.setDrawValues(false);
        lineDataRsi3.setDrawValues(false);
        lineDataRsi1.setDrawCircles(false);
        lineDataRsi1.setColor(ThemeManager.colorWhiteBlack());
        lineDataRsi2.setDrawCircles(false);
        lineDataRsi2.setColor(ThemeManager.colorYellow());
        lineDataRsi3.setDrawCircles(false);
        lineDataRsi3.setColor(ThemeManager.colorGreen());
        LineData rsiLineData = new LineData();
        rsiLineData.addDataSet(lineDataRsi1);
        rsiLineData.addDataSet(lineDataRsi2);
        rsiLineData.addDataSet(lineDataRsi3);
        rsiCombinedData.setData(rsiLineData);
        return rsiCombinedData;
    }

    private CombinedData createBollData(ArrayList<Entry> lineMbfEntries, ArrayList<Entry> lineUpfEntries, ArrayList<Entry> lineDnfEntries,ArrayList<CandleEntry> candleEntries){
        bollCombinedData = new CombinedData();

        LineDataSet lineDataMb = new LineDataSet(lineMbfEntries,"");
        lineDataMb.setDrawCircles(false);
        lineDataMb.setDrawValues(false);
        lineDataMb.setColor(ThemeManager.colorWhiteBlack());

        LineDataSet lineDataUp = new LineDataSet(lineUpfEntries,"");
        lineDataUp.setDrawCircles(false);
        lineDataUp.setDrawValues(false);
        lineDataUp.setColor(ThemeManager.colorYellow());

        LineDataSet lineDataDn = new LineDataSet(lineDnfEntries,"");
        lineDataDn.setDrawCircles(false);
        lineDataDn.setDrawValues(false);
        lineDataDn.setColor(ThemeManager.colorGreen());

        LineData rsiLineData = new LineData();
        rsiLineData.addDataSet(lineDataMb);
        rsiLineData.addDataSet(lineDataUp);
        rsiLineData.addDataSet(lineDataDn);

        // CandleDataSet：蜡烛图数据集合
        CandleDataSet set1 = new CandleDataSet(candleEntries, "Data Set");
        set1.setAxisDependency(YAxis.AxisDependency.LEFT);
//        set1.setColor(Color.rgb(80, 80, 80));
        // set1.setShadowColor(ThemeManager.colorWhiteBlack());   // 影线颜色
        set1.setShadowColorSameAsCandle(true);
        set1.setShadowWidth(0.7f);

        set1.setDecreasingColor(ThemeManager.colorGreen());  // 开盘价 > 收盘价
        // set1.setDecreasingPaintStyle(Paint.Style.FILL);
        set1.setIncreasingColor(ThemeManager.colorRed());  // 开盘价 < 收盘价
        set1.setIncreasingPaintStyle(Paint.Style.FILL);
        set1.setNeutralColor(Color.RED);//当天价格不涨不跌（一字线）颜色
        // 修改源码CandleStickChartRenderer

        set1.setDrawValues(false);
        set1.setAxisDependency(YAxis.AxisDependency.LEFT);  // //以左边坐标轴为准 还是以右边坐标轴为基准
        set1.setShowCandleBar(false);

        // CandleData：蜡烛图
        CandleData candleData = new CandleData(set1);

        bollCombinedData.setData(rsiLineData);
        bollCombinedData.setData(candleData);
        return bollCombinedData;
    }

    private CombinedData createObvData(ArrayList<Entry> lineObvEntries){
        obvCombinedData = new CombinedData();
        LineDataSet lineDataObv = new LineDataSet(lineObvEntries,"");
        lineDataObv.setDrawCircles(false);
        lineDataObv.setDrawValues(false);
        lineDataObv.setColor(ThemeManager.colorWhiteBlack());
        LineData obvLineData = new LineData();
        obvLineData.addDataSet(lineDataObv);
        obvCombinedData.setData(obvLineData);
        return obvCombinedData;
    }

    private CombinedData createDmiData(ArrayList<Entry> linePdiEntries,ArrayList<Entry> lineMdiEntries
            ,ArrayList<Entry> lineAdxEntries,ArrayList<Entry> lineAdxrEntries){
        dmiCombinedData = new CombinedData();
        LineDataSet lineDataPdi = new LineDataSet(linePdiEntries,"");
        lineDataPdi.setDrawCircles(false);
        lineDataPdi.setDrawValues(false);
        lineDataPdi.setColor(ThemeManager.colorWhiteBlack());
        LineDataSet lineDataMdi = new LineDataSet(lineMdiEntries,"");
        lineDataMdi.setDrawCircles(false);
        lineDataMdi.setDrawValues(false);
        lineDataMdi.setColor(ThemeManager.colorGreen());

        LineDataSet lineDataAdx = new LineDataSet(lineAdxEntries,"");
        lineDataAdx.setDrawCircles(false);
        lineDataAdx.setDrawValues(false);
        lineDataAdx.setColor(ThemeManager.colorYellow());
        LineDataSet lineDataAdxr = new LineDataSet(lineAdxrEntries,"");
        lineDataAdxr.setDrawCircles(false);
        lineDataAdxr.setDrawValues(false);
        lineDataAdxr.setColor(ThemeManager.colorFuchsia());

        LineData mdiLineData = new LineData();
        mdiLineData.addDataSet(lineDataPdi);
        mdiLineData.addDataSet(lineDataMdi);
        mdiLineData.addDataSet(lineDataAdx);
        mdiLineData.addDataSet(lineDataAdxr);
        dmiCombinedData.setData(mdiLineData);
        return dmiCombinedData;
    }

    private CombinedData createDmaData(ArrayList<Entry> linedif10Entries,ArrayList<Entry> linedifma50Entries){
        dmaCombinedData = new CombinedData();
        LineDataSet lineDataDif10 = new LineDataSet(linedif10Entries,"");
        lineDataDif10.setDrawCircles(false);
        lineDataDif10.setDrawValues(false);
        lineDataDif10.setColor(ThemeManager.colorWhiteBlack());
        LineDataSet lineDataDifMa50 = new LineDataSet(linedifma50Entries,"");
        lineDataDifMa50.setDrawCircles(false);
        lineDataDifMa50.setDrawValues(false);
        lineDataDifMa50.setColor(ThemeManager.colorYellow());
        LineData mdiLineData = new LineData();
        mdiLineData.addDataSet(lineDataDif10);
        mdiLineData.addDataSet(lineDataDifMa50);
        dmaCombinedData.setData(mdiLineData);
        return dmaCombinedData;
    }

    /**
     *  设置SAR 散点 图
     */
    public CombinedData createSarData(ArrayList<Entry> scatterEntries ,ArrayList<CandleEntry> candleEntries) {
        sarCombinedData = new CombinedData();
        // ScatterDataSet: 散点图数据集合
        ScatterDataSet scatterDataSet = new GScatterDataSet(kLineEntities,scatterEntries,"scatter");
        // scatterDataSet.setDrawVerticalHighlightIndicator(true);
        //scatterDataSet.setDrawHorizontalHighlightIndicator(false);//是否是主图 ，true主图添加横线 高亮线，false副图不添加高亮横线
        //scatterDataSet.setHighLightColor(KLineF.COLOR_LINE_HIGH_LIGHT);//night:Color.parseColor("#c8cfe5") day: Color.parseColor("#202840")
        //scatterDataSet.setAxisDependency(YAxis.AxisDependency.LEFT); //以左边坐标轴为准 还是以右边坐标轴为基准
        scatterDataSet.setDrawValues(false);
        //scatterDataSet.setColors(colors);
        //scatterDataSet.setColor(color);
        scatterDataSet.setScatterShapeSize(4f);//集合的大小密度像素绘制的scattershape会。这只适用于非自定义形状。
        scatterDataSet.setScatterShape(ScatterChart.ScatterShape.CIRCLE);//设置在值为的位置上绘制的形状。//SQUARE, CIRCLE, TRIANGLE, CROSS, X,--->正方形，圆，三角形，十字，X，
        //scatterDataSet.setScatterShapeHoleRadius(1f);//设置形状孔的半径（适用于正方形、圆形和三角形）        *将此设置为< = 0以去除漏洞。
        //scatterDataSet.setScatterShapeHoleColor(Color.TRANSPARENT);

        // ScatterData：散点图
        ScatterData scatterData = new ScatterData(scatterDataSet);
        sarCombinedData.setData(scatterData);

        CandleDataSet set1 = new CandleDataSet(candleEntries, "Data Set");
        set1.setAxisDependency(YAxis.AxisDependency.LEFT);
//        set1.setColor(Color.rgb(80, 80, 80));
        // set1.setShadowColor(ThemeManager.colorWhiteBlack());
        set1.setShadowColorSameAsCandle(true);
        set1.setShadowWidth(0.7f);
        set1.setDecreasingColor(ThemeManager.colorRed());
        // set1.setDecreasingPaintStyle(Paint.Style.FILL);
        set1.setIncreasingColor(Color.rgb(122, 242, 84));
        set1.setIncreasingPaintStyle(Paint.Style.FILL);
        set1.setDrawValues(false);
        set1.setAxisDependency(YAxis.AxisDependency.LEFT);
        set1.setShowCandleBar(false);
        CandleData candleData = new CandleData(set1);
        sarCombinedData.setData(candleData);
        return sarCombinedData;
    }

    private CombinedData createWrData(ArrayList<Entry> lineWr1Entries,ArrayList<Entry> lineWr2Entries){
        wrCombinedData = new CombinedData();
        LineDataSet lineDataWr1 = new LineDataSet(lineWr1Entries,"");
        lineDataWr1.setColor(ThemeManager.colorYellow());
        lineDataWr1.setDrawCircles(false);
        lineDataWr1.setDrawValues(false);
        LineDataSet lineDataWr2 = new LineDataSet(lineWr2Entries,"");
        lineDataWr2.setDrawCircles(false);
        lineDataWr2.setColor(ThemeManager.colorWhiteBlack());
        lineDataWr2.setDrawValues(false);
        LineData wrLineData = new LineData();
        wrLineData.addDataSet(lineDataWr1);
        wrLineData.addDataSet(lineDataWr2);
        wrCombinedData.setData(wrLineData);
        return wrCombinedData;
    }

    private CombinedData createVrData(ArrayList<Entry> lineVr13Entries,ArrayList<Entry> lineVr26Entries){
        vrCombinedData = new CombinedData();
        LineDataSet lineDataVr13 = new LineDataSet(lineVr13Entries,"");
        lineDataVr13.setDrawCircles(false);
        lineDataVr13.setDrawValues(false);
        lineDataVr13.setColor(ThemeManager.colorWhiteBlack());
        LineDataSet lineDataVr26 = new LineDataSet(lineVr26Entries,"");
        lineDataVr26.setDrawCircles(false);
        lineDataVr26.setDrawValues(false);
        lineDataVr26.setColor(ThemeManager.colorYellow());
        LineData mdiLineData = new LineData();
        mdiLineData.addDataSet(lineDataVr13);
        mdiLineData.addDataSet(lineDataVr26);
        vrCombinedData.setData(mdiLineData);
        return vrCombinedData;
    }

    private CombinedData createCrData(ArrayList<Entry> lineCrMidEntries,ArrayList<Entry> lineCr1Entries,
                                      ArrayList<Entry> lineCr2Entries,
                                      ArrayList<Entry> lineCr3Entries,ArrayList<Entry> lineCr4Entries){
        crCombinedData = new CombinedData();
        LineDataSet lineDataCrMid = new LineDataSet(lineCrMidEntries,"");
        lineDataCrMid.setDrawCircles(false);
        lineDataCrMid.setDrawValues(false);
        lineDataCrMid.setColor(ThemeManager.colorWhiteBlack());
        LineDataSet lineDataCr1 = new LineDataSet(lineCr1Entries,"");
        lineDataCr1.setDrawCircles(false);
        lineDataCr1.setDrawValues(false);
        lineDataCr1.setColor(ThemeManager.colorRed());

        LineDataSet lineDataCr2 = new LineDataSet(lineCr2Entries,"");
        lineDataCr2.setDrawCircles(false);
        lineDataCr2.setDrawValues(false);
        lineDataCr2.setColor(ThemeManager.colorGreen());

        LineDataSet lineDataCr3 = new LineDataSet(lineCr3Entries,"");
        lineDataCr3.setDrawCircles(false);
        lineDataCr3.setDrawValues(false);
        lineDataCr3.setColor(ThemeManager.colorYellow());

        LineDataSet lineDataCr4 = new LineDataSet(lineCr4Entries,"");
        lineDataCr4.setDrawCircles(false);
        lineDataCr4.setDrawValues(false);
        lineDataCr4.setColor(ThemeManager.colorFuchsia());
        LineData crLineData = new LineData();
        crLineData.addDataSet(lineDataCrMid);
        crLineData.addDataSet(lineDataCr1);
        crLineData.addDataSet(lineDataCr2);
        crLineData.addDataSet(lineDataCr3);
        crLineData.addDataSet(lineDataCr4);
        crCombinedData.setData(crLineData);
        return crCombinedData;
    }

    void calculateRSI(KLineEntity point,int i) {
        final float closePrice = point.getClosePrice();
        if (i == 0) {
            rsi1 = 0;
            rsi2 = 0;
            rsi3 = 0;
            rsi1ABSEma = 0;
            rsi2ABSEma = 0;
            rsi3ABSEma = 0;
            rsi1MaxEma = 0;
            rsi2MaxEma = 0;
            rsi3MaxEma = 0;
        } else {
            float Rmax = Math.max(0, closePrice - kLineEntities.get(i - 1).getClosePrice());
            float RAbs = Math.abs(closePrice - kLineEntities.get(i - 1).getClosePrice());
            rsi1MaxEma = (Rmax + (6f - 1) * rsi1MaxEma) / 6f;
            rsi1ABSEma = (RAbs + (6f - 1) * rsi1ABSEma) / 6f;

            rsi2MaxEma = (Rmax + (12f - 1) * rsi2MaxEma) / 12f;
            rsi2ABSEma = (RAbs + (12f - 1) * rsi2ABSEma) / 12f;

            rsi3MaxEma = (Rmax + (24f - 1) * rsi3MaxEma) / 24f;
            rsi3ABSEma = (RAbs + (24f - 1) * rsi3ABSEma) / 24f;

            rsi1 = (rsi1MaxEma / rsi1ABSEma) * 100;
            rsi2 = (rsi2MaxEma / rsi2ABSEma) * 100;
            rsi3 = (rsi3MaxEma / rsi3ABSEma) * 100;
        }
        point.rsi1 = rsi1;
        point.rsi2 = rsi2;
        point.rsi3 = rsi3;
    }

    void calculateKDJ(KLineEntity point,int i) {
        if(i < 8){
            point.k = 0;
            point.d = 0;
            point.j = 0;
            return;
        }
        final float closePrice = point.getClosePrice();
        int startIndex = i - 8;
        float max9 = Float.MIN_VALUE;
        float min9 = Float.MAX_VALUE;
        for (int index = startIndex; index <= i; index++) {
            max9 = Math.max(max9, kLineEntities.get(index).getHighPrice());
            min9 = Math.min(min9, kLineEntities.get(index).getLowPrice());
        }
        float rsv = 100f * (closePrice - min9) / (max9 - min9);
         /*k = (rsv + 2f * k) / 3f;
         d = (k + 2f * d) / 3f;*/

        k = (rsv+2f*k)/3f;
        d = (k + 2f * d) / 3f;

        point.j = 3f * k - 2 * d;
        point.k = k ;
        point.d = d ;
         /*point.k = k < 0 ? 0: k>100 ? 100:k;
         point.d = d < 0 ? 0: d>100 ? 100:d;*/
        //  point.j = point.j < 0 ? 0: point.j>100 ? 100:point.j;
    }

    void calculateOBV(KLineEntity point,int i) {
        if(i != 0){
            if(point.isUp){
                point.obv = kLineEntities.get(i-1).obv+point.volume;
            } else {
                point.obv = kLineEntities.get(i-1).obv-point.volume;
            }
        } else {
            if(point.isUp){
                point.obv = point.volume;
            } else {
                point.obv = -point.volume;
            }
        }
        obvMax = Math.max(point.obv,obvMax);
    }

    void calculateWr(KLineEntity point,int i) {
        float minLow = 0;
        float maxHigh = 0;
        float tempLow = 0;
        float tempHigh = 0;
        if(i > 5){
            for(int j = 5;j>-1;j--){
                tempLow = kLineEntities.get(i-j).low;
                minLow = minLow == 0? tempLow:minLow < tempLow?minLow:tempLow;

                tempHigh = kLineEntities.get(i-j).high;
                maxHigh = maxHigh == 0? tempHigh:maxHigh > tempHigh?maxHigh:tempHigh;
            }
            point.wr5 = (maxHigh - point.close)/(maxHigh - minLow)*100;
        }
        minLow = 0;
        maxHigh = 0;
        if(i > 9){
            for(int j = 9;j>-1;j--){
                tempLow = kLineEntities.get(i-j).low;
                minLow = minLow == 0? tempLow:minLow < tempLow?minLow:tempLow;

                tempHigh = kLineEntities.get(i-j).high;
                maxHigh = maxHigh == 0? tempHigh:maxHigh > tempHigh?maxHigh:tempHigh;
            }
            point.wr10 = (maxHigh - point.close)/(maxHigh - minLow)*100;
        }
    }

    void calculateDMA(KLineEntity point,int i) {
        if(i >= 49){
            point.dif10 = point.MA10 - point.MA50;
        }
        if(i >= 50){
            dma += point.dif10;
        }
        if(i >= 59){
            if(i > 59){
                dma = dma - kLineEntities.get(i-10).dif10;
            }
            point.difma50 =  dma/10f;
        }
    }

    void calculateDmi(KLineEntity point,int i) {
        float temp1 = 0;
        float temp2 = 0;
        float temp3 = 0;

        float plusDm = 0;
        float minusDm = 0;
        if(i > 0){
            temp1 = point.close - point.low;
            temp2 = Math.abs(point.high - kLineEntities.get(i-1).close);
            temp3 = Math.abs(point.low - kLineEntities.get(i-1).close);
            plusDm = point.high < kLineEntities.get(i-1).high ? 0:point.high - kLineEntities.get(i-1).high;
            minusDm = kLineEntities.get(i-1).low < point.low ? 0:kLineEntities.get(i-1).low - point.low;
            if(plusDm > minusDm){
                minusDm = 0;
            } else {
                plusDm = 0;
            }
            point.plusDm = plusDm;
            point.minusDm = minusDm;
            point.tr = Math.max(temp1,Math.max(temp2,temp3));
        }

        if(i >= 13){
            if(i == 13){
                for(int j = 0;j < 14;j++){
                    point.tr14 = point.tr14 + kLineEntities.get(j).tr;
                    point.plusDm14 = point.plusDm14 + kLineEntities.get(j).plusDm;
                    point.minusDm14 = point.minusDm14 + kLineEntities.get(j).minusDm;
                }
            } else {
                point.tr14 = kLineEntities.get(i-1).tr14 + point.tr - kLineEntities.get(i-14).tr;
                point.plusDm14 = kLineEntities.get(i-1).plusDm14 + point.plusDm - kLineEntities.get(i-14).plusDm;
                point.minusDm14 = kLineEntities.get(i-1).minusDm14 + point.minusDm - kLineEntities.get(i-14).minusDm;
            }
            point.pdi =  point.plusDm14/point.tr14 * 100;
            point.mdi =  point.minusDm14/point.tr14 * 100;
            point.dx = (Math.abs(point.pdi - point.mdi)) /(point.pdi + point.mdi)*100;
            if(i > 13){
                point.adx = (kLineEntities.get(i-1).adx*14 + point.dx - kLineEntities.get(i-14).dx)/14;
                point.adxr =  (point.adx + kLineEntities.get(i-1).adx)/2;
            }
        }
    }

    void calculateMACD(KLineEntity point,int i) {
        final float closePrice = point.getClosePrice();
        if (i == 0) {
            ema12 = 0;
            ema26 = 0;
        }
        if (i == 0){
            ema12 = closePrice+(kLineEntities.get(0).close - closePrice)*2/13;
            ema26 = closePrice+(kLineEntities.get(0).close - closePrice)*2/17;
        } else {
            ema12 = ema12 * 11f / 13f + closePrice * 2f / 13f;
            ema26 = ema26 * 25f / 27f + closePrice * 2f / 27f;
        }
        dif = ema12 - ema26;
        dea = dea * 8f / 10f + dif * 2f / 10f;
        macd = (dif - dea) * 2f;
        point.dif = dif;
        point.dea = dea;
        point.macd = macd;
    }

    void calculateMA(KLineEntity point,int i,boolean other) {
        final float closePrice = point.getClosePrice();  // 收盘价
        float tranPrice = point.tranPrice;   // 成交额
        ma1 += closePrice;
        ma2 += closePrice;
        ma3 += closePrice;
        amoM1 += tranPrice;
        amoM2 += tranPrice;
        amoM3 += tranPrice;

        if(other){
            ma10 += closePrice;
            ma20 += closePrice;
            ma50 += closePrice;
        }

        if (i >= space_ma1-1) {
            ma1 -= i < space_ma1 ? 0:kLineEntities.get(i - space_ma1).getClosePrice();
            point.MA1 = ma1 / (float) space_ma1;
            amoM1 -= i < space_ma1 ? 0 : kLineEntities.get(i - space_ma1).tranPrice;
            point.amoM1 = amoM1 / (float)space_ma1;
        }

        if (i >= space_ma2-1) {
            ma2 -= i < space_ma2 ? 0:kLineEntities.get(i - space_ma2).getClosePrice();
            point.MA2 = ma2 / (float) space_ma2;
            amoM2 -= i < space_ma2 ? 0 : kLineEntities.get(i - space_ma2).tranPrice;
            point.amoM2 = amoM2 / (float)space_ma2;
        }

        if (i >= space_ma3-1) {
            ma3 -= i < space_ma3 ? 0:kLineEntities.get(i - space_ma3).getClosePrice();
            point.MA3 = ma3 / (float) space_ma3;
            amoM3 -= i < space_ma3 ? 0 : kLineEntities.get(i - space_ma3).tranPrice;
            point.amoM3 = amoM3 / (float)space_ma3;
        }

        if (i >= 9 && other) {
            ma10 -= i < 10 ? 0:kLineEntities.get(i - 10).getClosePrice();
            point.MA10 = ma10 /  10f;
        }
        ma20 = 0;
        if (i >= 19 && other) {
            for(int j = 0;j < 20;j++){
                ma20 = ma20+kLineEntities.get(i-j).getClosePrice();
            }
            point.MA20 = ma20 /  20f;
        }
        if (i >= 49 && other) {
            ma50 -= i < 50 ? 0: kLineEntities.get(i - 50).getClosePrice();
            point.MA50 = ma50 /  50f;
        }
    }

    /**
     * 计算 BOLL 需要在计算ma之后进行
     */
    void calculateBOLL(List<KLineEntity> datas) {
        for (int i = 0,size = datas.size(); i < size; i++) {
            KLineEntity point = datas.get(i);
            final float closePrice = point.getClosePrice();
            if (i < 20) {
                point.mb = closePrice;
                point.up = 0;
                point.dn = 0;
            } else {
                int n = 20;
                float md = 0;
                for (int j = i - n + 1; j <= i; j++) {
                    float c = datas.get(j).getClosePrice();
                    float m = point.MA20;
                    float value = c - m;
                    md += value * value;
                }
                md = md / n;
                md = (float) Math.sqrt(md);
                point.mb = point.MA20;
                point.up = point.mb + 2f * md;
                point.dn = point.mb - 2f * md;
            }
        }
    }

    void calculateCR(KLineEntity point,int i,List<KLineEntity> datas) {
        point.mid = (point.high +  point.low)/2f;
        if(i == 0){
            point.crpercent = 0;
            point.crHigh = 0;
            point.crLow = 0;
        } else {
            point.crLow = Math.max(0,datas.get(i-1).mid - point.low);
            crLow = crLow + point.crLow;
            point.crHigh = Math.max(0,point.high - datas.get(i-1).mid);
            crHigh = crHigh + point.crHigh;
            if(i > 25){
                crLow = crLow - datas.get(i-26).crLow;
                crHigh = crHigh - datas.get(i-26).crHigh;
            }
        }
        if(i > 25){
            point.cr = crLow == 0? 0:crHigh/crLow*100;
        }
        if(i == 9){
            for(int j = 0;j<=i;j++){
                point.cr10sum = point.cr10sum + point.cr;
            }
            point.crMa1 = point.cr10sum/10f;
        }
        if(i > 9){
            point.cr10sum = datas.get(i-1).cr10sum + point.cr - datas.get(i-10).cr;
            point.crMa1 = point.cr10sum/10f;
        }
        if(i >= 19){
            point.crMa2 = (point.cr10sum + datas.get(i-10).cr10sum)/20f;
        }
        if(i >= 39){
            point.crMa3 = (point.cr10sum + datas.get(i-10).cr10sum +
                    datas.get(i-20).cr10sum + datas.get(i-30).cr10sum)/40f;
        }
        if(i >= 61){
            point.crMa4 = (point.cr10sum + datas.get(i-10).cr10sum +
                    datas.get(i-20).cr10sum + datas.get(i-30).cr10sum+datas.get(i-40).cr10sum+
                    datas.get(i-50).cr10sum+datas.get(i-60).cr+datas.get(i-61).cr)/62f;
        }
    }

    void calculateVR(KLineEntity point,int i,List<KLineEntity> datas) {
        if(i == 0){
            point.vrup = 0;
            point.vrdown = 0;
        } else {
            if(point.close > datas.get(i-1).close){
                point.vrup = point.volume;
                point.vrdown = 0;
            } else {
                point.vrup = 0;
                point.vrdown = point.volume;
            }
        }

        if(i >= 12){
            for(int j = 0;j < 13;j++){
                point.vr13numerator += datas.get(i-j).vrup;
                point.vr13denominator += datas.get(i-j).vrdown;
            }
        }

        if(i >=25){
            float temp1 = datas.get(i).vr13numerator + datas.get(i-13).vr13numerator;
            float temp2 =  datas.get(i).vr13denominator + datas.get(i-13).vr13denominator;
            point.vr26 = temp2 == 0 ? 0:(temp1/temp2)*100;
        }

        point.vr13 = point.vr13denominator == 0 ? 0:(point.vr13numerator/point.vr13denominator)*100;

    }

    public void calculateSAR() {
        int n = 5;
        int size = kLineEntities.size();
        if(n > size || n < 3)
            return;
        float xs = 0.02F;
        if(kLineEntities.get(n - 1).close < kLineEntities.get(n - 2).close) {
            if( kLineEntities.get(n - 2).close <=  kLineEntities.get(n - 3).close)
                kLineEntities.get(n - 1).sign = 1.0F;
            else
                kLineEntities.get(n - 1).sign = 17F;
        } else
        if(kLineEntities.get(n - 1).close > kLineEntities.get(n - 2).close) {
            if( kLineEntities.get(n - 2).close >=  kLineEntities.get(n - 3).close)
                kLineEntities.get(n - 1).sign = 0.0F;
            else
                kLineEntities.get(n - 1).sign = 16F;
        } else
        if( kLineEntities.get(n - 2).close < kLineEntities.get(n - 3).close)
            kLineEntities.get(n - 1).sign = 1.0F;
        else
        if(kLineEntities.get(n - 2).close > kLineEntities.get(n - 3).close)
            kLineEntities.get(n - 1).sign = 0.0F;
        else
            kLineEntities.get(n - 1).sign = 16F;
        if(kLineEntities.get(n - 1).sign == 1.0F ||kLineEntities.get(n - 1).sign == 17F) {
            kLineEntities.get(n - 1).sar = -1E+036F;
            for(int j = n - 1; j >= 0; j--)
                kLineEntities.get(n - 1).sar = Math.max(kLineEntities.get(n - 1).sar,kLineEntities.get(j).high);

        } else {
            kLineEntities.get(n - 1).sar = 1E+036F;
            for(int j = n - 1; j >= 0; j--)
                kLineEntities.get(n - 1).sar = Math.min(kLineEntities.get(n - 1).sar, kLineEntities.get(j).low);

        }
        for(int i = n; i < size; i++)
            if(kLineEntities.get(i - 1).sign == 0.0F || kLineEntities.get(i - 1).sign == 16F) {
                if(kLineEntities.get(i).close < kLineEntities.get(i - 1).sar) {
                    kLineEntities.get(i).sar = -1E+036F;
                    for(int j = i; j > i - n; j--)
                        kLineEntities.get(i).sar= Math.max(kLineEntities.get(i).sar, kLineEntities.get(j).high);

                    kLineEntities.get(i).sign = 17F;
                    xs = 0.02F;
                } else {
                    kLineEntities.get(i).sar = kLineEntities.get(i-1).sar + xs * (kLineEntities.get(i-1).high - kLineEntities.get(i-1).sar);
                    xs = xs >= 0.2F ? xs : xs + 0.02F;
                    kLineEntities.get(i).sign = 0.0F;
                }
            } else
            if(kLineEntities.get(i).close > kLineEntities.get(i-1).sar) {
                kLineEntities.get(i).sar = 1E+036F;
                for(int j = i; j > i - n; j--)
                    kLineEntities.get(i).sar = Math.min(kLineEntities.get(i).sar, kLineEntities.get(j).low);

                kLineEntities.get(i).sign = 16F;
                xs = 0.02F;
            } else {
                kLineEntities.get(i).sar = kLineEntities.get(i-1).sar + xs * (kLineEntities.get(i-1).low -kLineEntities.get(i-1).sar);
                xs = xs >= 0.2F ? xs : xs + 0.02F;
                kLineEntities.get(i).sign = 1.0F;
            }
    }

    /**
     * 计算MTM MTM（12日），MAMTM移动平均（6日）
     */
    public void calculateMTM(KLineEntity point,int i){
        if( i >= MTMN ){
            point.mtm = point.close - kLineEntities.get(i-MTMN).close;
            mtm += point.mtm;

            if( (i-MTMN) >= (MAMTMN-1)){
                mtm -= (i-MTMN) < MAMTMN ? 0 : kLineEntities.get(i-MAMTMN).mtm;
                point.mtmMA = mtm /(float) MAMTMN;
            }
        }
    }

    public void calculateBBI(KLineEntity point,int i){
        bbi3 += point.close;
        bbi6 += point.close;
        bbi12 += point.close;
        bbi24 += point.close;
        if( i >= (BBIN3 -1)){
            bbi3 -= i < BBIN3 ? 0 : kLineEntities.get(i-BBIN3).close;
        }
        if( i >= (BBIN6-1) ){
            bbi6 -= i < BBIN6 ? 0 : kLineEntities.get(i-BBIN6).close;
        }
        if( i >= (BBIN12 -1)){
            bbi12 -= i < BBIN12 ? 0 : kLineEntities.get(i-BBIN12).close;
        }
        if( i >= (BBIN24-1) ){
            bbi24 -= i < BBIN24 ? 0 : kLineEntities.get(i-BBIN24).close;
            point.bbi = (bbi3/BBIN3 + bbi6/BBIN6 + bbi12/BBIN12 + bbi24/BBIN24) / 4;
        }
    }

    public void calculateBIAS(KLineEntity point,int i){
        float ave =0.0f;
        bias6 += point.close;
        bias12 += point.close;
        bias24 += point.close;

        if( i >= 5 ){
            bias6 -= i < 6 ? 0 :kLineEntities.get(i-6).close;
            ave =  bias6 / 6.0f;
            point.bias6 = (point.close - ave) / ave * 100;
        }
        if( i >= 11 ){
            bias12 -= i < 12 ? 0 :kLineEntities.get(i-12).close;
            ave =  bias12 / 12.0f;
            point.bias12 = (point.close - ave) / ave * 100;
        }
        if( i >= 23 ){
            bias24 -= i < 24 ? 0 :kLineEntities.get(i-24).close;
            ave =  bias24 / 24.0f;
            point.bias24 = (point.close - ave) / ave * 100;
        }
    }

    public void calculateROC(KLineEntity point,int i){
        if( i >= 10 ){
            point.roc = (point.close - kLineEntities.get(i-10).close) / kLineEntities.get(i-10).close * 100;
            roc += point.roc;
            if( (i-10) >= 5  ){
                roc -= i < 6 ? 0 : kLineEntities.get(i-6).roc;
                point.rocMA = roc / 6.0f;
            }
        }
    }

    public void calculatePSY(KLineEntity point,int i){
        upPSY += point.isUpPSY ? 1 : 0;
        if( i>=12 ){
            upPSY -= i > 12 ? kLineEntities.get(i-12).isUpPSY ? 1:0 : 0;
            point.psy = upPSY / 12.0f * 100;

            psy += point.psy;
            if( (i-12) >= 12 ){
                psy -= (i-12) >12 ? kLineEntities.get(i-12).psy: 0;
            }
        }
    }

    public void calculateBRAR(KLineEntity point ,int i){
        point.arHO = point.high - point.open;
        point.arOL = point.open - point.low;

        if( i > 0 ){
            point.brHPC = point.high - kLineEntities.get(i-1).close;
            point.brPCL = kLineEntities.get(i-1).close - point.low;
        }

        arHO += point.arHO;
        arOL += point.arOL;
        brHPC += point.brHPC;
        brPCL += point.brPCL;
        if( i>=12 ){
            arHO -= i < 13 ? 0 : kLineEntities.get(i-13).arHO;
            arOL -= i < 13 ? 0 : kLineEntities.get(i-13).arOL;
            point.ar = arHO / arOL * 100;
            if( i>=13 ){
                brHPC -= i < 14 ? 0 : kLineEntities.get(i-13).brHPC;
                brPCL -= i < 14 ? 0 : kLineEntities.get(i-13).brPCL;
                point.br = brHPC / brPCL * 100;
            }
        }
    }

    public void  calculateNVIPVI( KLineEntity point, int i ){
        if( 0 == i ){
            return;
        }
        float pvi = 1.0f;
        if( point.volume > kLineEntities.get(i-1).volume ){
            pvi = point.close / kLineEntities.get(i-1).close;
        }
        float nvi = 1.0f;
        if( point.volume < kLineEntities.get(i-1).volume ){
            nvi = point.close / kLineEntities.get(i-1).close;
        }
        point.pvi = kLineEntities.get(i-1).pvi * pvi;
        point.nvi = kLineEntities.get(i-1).nvi * nvi;
    }

    private void calculateCCITPY(KLineEntity kLineEntity, int i) {
        kLineEntity.tpyCCI = (kLineEntity.high + kLineEntity.low + kLineEntity.close) / 3;
        maCCI += kLineEntity.tpyCCI;
        if( i >= 13 ){
            maCCI -= i < 14 ? 0 : kLineEntities.get(i-14).tpyCCI;
            kLineEntity.maCCI = maCCI / 14.0f;
        }
    }

    private void calculateCCI(){
        float md = 0.0f;
        for( int i = 13 ,size = kLineEntities.size(); i <size; i++ ){
            md = 0.0f;
            int k = (i + 1) - 14;
            KLineEntity kLineEntity = kLineEntities.get(i);
            for(int j = i; j >= k; j-- ){
                md += Math.abs( kLineEntities.get(j).tpyCCI - kLineEntity.maCCI);
            }
            md = md / 14.0f;
            kLineEntity.cci = (kLineEntity.tpyCCI - kLineEntity.maCCI) / (0.015f * md);
        }
    }

    /**
     * 设置k线图数据
     */
    @NonNull
    private LineDataSet setMaLine(int ma, ArrayList<Entry> lineEntries) {
        LineDataSet lineDataSetMa = new LineDataSet(lineEntries, "ma" + ma);
/*        if (ma == 5) {
            lineDataSetMa.setHighlightEnabled(true);
            lineDataSetMa.setDrawHorizontalHighlightIndicator(false);
            lineDataSetMa.setHighLightColor(ThemeManager.colorWhiteBlack());
        } else {*//*此处必须得写*/
        lineDataSetMa.setHighlightEnabled(false);  // 选中高亮。允许通过点击高亮突出 ChartData 对象和其 DataSets 。
        //}
        lineDataSetMa.setDrawValues(false);
        if (ma == 5) {
            //k线图5 的颜色
            lineDataSetMa.setColor(ThemeManager.colorGreen());
        } else if (ma == 10) {
            //k线图10 的颜色
            lineDataSetMa.setColor(Color.GRAY);
        } else {
            //k线图30 的颜色
            lineDataSetMa.setColor(ThemeManager.colorYellow());
        }
        lineDataSetMa.setLineWidth(1f);
        lineDataSetMa.setDrawCircles(false);
        lineDataSetMa.setAxisDependency(YAxis.AxisDependency.LEFT);
        return lineDataSetMa;
    }

    float rsi1 = 0;
    float rsi2 = 0;
    float rsi3 = 0;
    float rsi1ABSEma = 0;
    float rsi2ABSEma = 0;
    float rsi3ABSEma = 0;
    float rsi1MaxEma = 0;
    float rsi2MaxEma = 0;
    float rsi3MaxEma = 0;

    float k = 0;
    float d = 0;

    float ema12 = 0;
    float ema26 = 0;
    float dif = 0;
    float dea = 0;
    float macd = 0;

    float ma1 = 0;
    float ma2 = 0;
    float ma3 = 0;
    float ma10 = 0;
    float ma20 = 0;
    float ma50 = 0;

    float amoM1 = 0;  // AMO指标
    float amoM2 = 0;
    float amoM3 = 0;

    int space_ma1 = 5;
    int space_ma2 = 10;
    int space_ma3 = 20;

    float dma = 0;

    float crHigh = 0;
    float crLow = 0;

    float mtm = 0;  // MTM指标
    final int MTMN = 12;
    final int MAMTMN = 6;

    float bbi3 = 0;  // BBI指标
    float bbi6 = 0;
    float bbi12 = 0;
    float bbi24 = 0;
    int BBIN3 = 3;
    int BBIN6 = 6;
    int BBIN12 = 12;
    int BBIN24 = 24;

    float bias6 = 0;
    float bias12 = 0;
    float bias24 = 0;

    float roc =0 ;

    int upPSY=0;  // PSY指标上涨数
    float psy = 0;

    float arHO =0 ;  // AR指标
    float arOL = 0;
    float brHPC =0 ;  // BR指标
    float brPCL = 0;

    float maCCI = 0;  // CCI指标
    /**
     * 重置临时参数
     */
    private void resetParams(){
        rsi1 = 0;
        rsi2 = 0;
        rsi3 = 0;
        rsi1ABSEma = 0;
        rsi2ABSEma = 0;
        rsi3ABSEma = 0;
        rsi1MaxEma = 0;
        rsi2MaxEma = 0;
        rsi3MaxEma = 0;
        ema12 = 0;
        ema26 = 0;
        dif = 0;
        dea = 0;
        macd = 0;
        k = 0;
        d = 0;
        ma1 = 0;
        ma2 = 0;
        ma3 = 0;
        ma10 = 0;
        ma20 = 0;
        ma50 = 0;
        dma = 0;
        crHigh = 0;
        crLow = 0;
        mtm = 0;
        amoM1 = 0;
        amoM2 = 0;
        amoM3 = 0;
        bbi3 = 0;  // BBI指标
        bbi6 = 0;
        bbi12 = 0;
        bbi24 = 0;
        bias6 = 0;
        bias12 = 0;
        bias24 = 0;
        roc =0 ;
        upPSY=0;  // PSY指标上涨数
        psy = 0;
        arHO =0 ;  // AR指标
        arOL = 0;
        brHPC =0 ;  // BR指标
        brPCL = 0;
        maCCI = 0;
        space_ma1 = KSetting.getAverage_1();  // 均线周期
        space_ma2 = KSetting.getAverage_2();
        space_ma3 = KSetting.getAverage_3();
    }

    /**
     * 获取顶部图表数据
     * @return
     */
    public CombinedData getKTopData(){
        return topCombinedData;
    }

    public CombinedData getTopChartData() {
        return getKTopData();
    }

    /**
     * 根据副图指标，返回不同的副图
     * @return
     */
    public CombinedData getBottomChartData() {
        TechChartType.KType ktype = typeHpler.ktype;
        if(ktype == TechChartType.KType.KDJ){
            return kdjCombinedData;
        } else if(ktype == TechChartType.KType.VOLUME){
            return barCombinedData;
        } else if(ktype == TechChartType.KType.MACD){
            return macdCombinedData;
        } else if(ktype == TechChartType.KType.RSI){
            return rsiCombinedData;
        } else if(ktype == TechChartType.KType.BOLL){
            return bollCombinedData;
        }else if(ktype == TechChartType.KType.OBV){
            return obvCombinedData;
        }else if(ktype == TechChartType.KType.WR){
            return wrCombinedData;
        }else if(ktype == TechChartType.KType.DMA){
            return dmaCombinedData;
        }
//        else if(ktype == TechChartType.KType.SAR){
//            return sarCombinedData;
//        }
        else if(ktype == TechChartType.KType.VR){
            return vrCombinedData;
        }else if(ktype == TechChartType.KType.CR){
            return crCombinedData;
        }else if(ktype == TechChartType.KType.DMI){
            return dmiCombinedData;
        }else if(ktype == TechChartType.KType.BIAS ){
            return biasCombinedData;
        }else if(ktype == TechChartType.KType.BBI){
            return bbiCombinedData;
        }else if(ktype == TechChartType.KType.AMO){
            return amoCombinedData;
        }else if( ktype == TechChartType.KType.CCI ){
            return  cciCombinedData;
        }else if(ktype == TechChartType.KType.MTM ){
            return mtmCombinedData;
        }else if(ktype == TechChartType.KType.ROC){
            return rocCombinedData;
        }else if(ktype == TechChartType.KType.BRAR ){
           return brarCombinedData;
        }else if(ktype == TechChartType.KType.NVIPVI ){
            return nviPviCombinedData;
        }else if(ktype == TechChartType.KType.PSY ){
            return psyCombinedData;
        }
        return null;
    }

    /**
     * 返回上表左侧高亮数据（收盘见）
     * @param position
     * @return
     */
    public String getTopLeftLable(int position){
        KLineEntity kLineEntity = kLineEntities.get(position);
        if( null==kLineEntity.tleftLable ){
            kLineEntity.tleftLable = String.valueOf(kLineEntity.close); // new
        }
        return kLineEntity.tleftLable+"";
    }

    /**
     * 返回上表顶部高亮数据(复权信息)
     * @param position
     * @return
     */
    public String getTopTopLable(int position){
        KLineEntity kLineEntity = kLineEntities.get(position);
        if( null == kLineEntity.tTopLable ){
            if( null != kLineEntity.kSubEntityList && !kLineEntity.kSubEntityList.isEmpty() ){
                StringBuilder stringBuilder = new StringBuilder();
                for (int i = 0,size = kLineEntity.kSubEntityList.size(); i < size; i++) {
                    FQItem fqItem = kLineEntity.kSubEntityList.get(i);
                    if( i !=0 ){
                        stringBuilder.append("\n");
                    }
                    stringBuilder.append(fqItem.dateTime);
                    if( null == fqItem.increasePrice && null == fqItem.allotmentPrice && null == fqItem.bonusAmount
                            && null == fqItem.bonusProportion && null == fqItem.increaseProportion && null == fqItem.increaseVolume &&null == fqItem.allotmentProportion){
                        continue;
                    }
                    stringBuilder.append("(");
                    if( null != fqItem.increasePrice && !"0".equals(fqItem.increasePrice)){
                        stringBuilder.append("增发价").append(Float.parseFloat(fqItem.increasePrice)).append(" ");
                    }
                    if( null != fqItem.allotmentPrice && !"0".equals(fqItem.allotmentPrice)){
                        stringBuilder.append("配股价").append(Float.parseFloat(fqItem.allotmentPrice)).append(" ");
                    }
                    if( null != fqItem.bonusAmount && !"0".equals(fqItem.bonusAmount)){
                        stringBuilder.append("每股分红").append(Float.parseFloat(fqItem.bonusAmount)).append(" ");
                    }
                    if( null != fqItem.bonusProportion && !"0".equals(fqItem.bonusProportion)){
                        stringBuilder.append("送股比").append(Float.parseFloat(fqItem.bonusProportion)).append(" ");
                    }
                    if( null != fqItem.increaseProportion && !"0".equals(fqItem.increaseProportion)){
                        stringBuilder.append("转增比").append(Float.parseFloat(fqItem.increaseProportion)).append(" ");
                    }
                    if( null != fqItem.increaseVolume && !"0".equals(fqItem.increaseVolume)){
                        stringBuilder.append("增发股").append(Float.parseFloat(fqItem.increaseVolume)).append(" ");
                    }
                    if( null != fqItem.allotmentProportion && !"0".equals(fqItem.allotmentProportion)){
                        stringBuilder.append("配股比").append(Float.parseFloat(fqItem.allotmentProportion)).append(" ");
                    }
                    stringBuilder.append(")");
                }
                kLineEntity.tTopLable = stringBuilder.toString();
            }
        }
        return kLineEntity.tTopLable;
    }

    /**
     * 返回下表底部高亮数据（时间）
     */
    public String getBottomLable(int position){
        KLineEntity kLineEntity = kLineEntities.get(position);
        if( null == kLineEntity.bottomLable ){
            kLineEntity.bottomLable = kLineEntity.date;
        }
        return kLineEntity.bottomLable;
    }
    /**
     * 返回右上角高亮数据（换手率）
     */
    public String getTopRightLable(int position){
        // 只有深沪市场有换手率展示（需求483）
        if (MarketType.SH.equals(quoteItem.market) || MarketType.SZ.equals(quoteItem.market)) {
            String hsl = FormatUtility.calculateTurnoverRate(ohlcItems.get(position),gbItems);
            if (null == hsl) {
                return "基金净值：" + kLineEntities.get(position).iopv;
            } else {
                return "换手率：" + hsl + "\n基金净值：" + kLineEntities.get(position).iopv;
            }
        } else {
            return "基金净值：" + kLineEntities.get(position).iopv;
        }
    }

    public String getBottomLeftLable(int position){
        KLineEntity kLineEntity = kLineEntities.get(position);
        if(kLineEntity.bleftLable == null){
            kLineEntity.bleftLable = String.valueOf(kLineEntity.volume);  // new
        }
        return kLineEntity.bleftLable;
    }

    public int getCount() {
        int length = kLineEntities != null?kLineEntities.size():0;
        if(length == 0){
            return 0;
        }
        return length > 40?length:40;
    }

    public ArrayList<KLineEntity> getKLineEntities(){
        return kLineEntities;
    }

    public KLineEntity getLastKLineEntities(){
        if( null == kLineEntities || kLineEntities.isEmpty()){
            return null;
        }

        return kLineEntities.get(kLineEntities.size()-1);
    }

    /**
     * 底部时间格式对象
     * @return
     */
    public IAxisValueFormatter getIAxisValueFormatter() {
        return new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                if(kLineEntities == null || kLineEntities.size() == 0){
                    return "";
                }
                if(value == -1 && !(kLineEntities == null || kLineEntities.size() == 0)){
                    return kLineEntities.get(0).getDatetime();
                }
                if(kLineEntities.size() <= 40){
                    if(getCount() == value){
                        String datetime = quoteItem.datetime;
                        if( null != datetime && !"-".equals(datetime ) && !"--".equals(datetime) &&
                                !"0".equals(datetime) && !"00".equals(datetime) && !StringUtils.isEmpty(datetime)){

                            return datetime.substring(0,4)+"/"+ datetime.substring(4,6)+"/"+ datetime.substring(6,8);
                        }
                        return "";
                    }
                    return "";
                }
                if(kLineEntities.size() == getCount() && value != 0){
                    return kLineEntities.get((int) value -1).getDatetime();
                }
                return kLineEntities.get((int) value).getDatetime();
            }
        };
    }

    /**
     * 当均线周期发生改变，更新页面
     */
    public void notifyTopDataSetChanged() {
        resetParams();
        if(null ==kLineEntities ){
            return;
        }
        ArrayList<Entry> line5Entries = new ArrayList<>();
        ArrayList<Entry> line10Entries = new ArrayList<>();
        ArrayList<Entry> line20Entries = new ArrayList<>();
        for(int i = 0,length = kLineEntities.size();i < length;i++){
            KLineEntity kLineEntity = kLineEntities.get(i);
            //重新计算MA
            calculateMA(kLineEntity,i,false);

            if (kLineEntity.MA1 != 0) {
                line5Entries.add(new Entry(i,kLineEntity.MA1 = NumberUtils.parseFloat(kLineEntity.MA1+"")));
            }
            if (kLineEntity.MA2 != 0) {
                line10Entries.add(new Entry(i,kLineEntity.MA2 = NumberUtils
                        .parseFloat(kLineEntity.MA2+"")));
            }
            if (kLineEntity.MA3 != 0) {
                line20Entries.add(new Entry(i,kLineEntity.MA3 = NumberUtils
                        .parseFloat(kLineEntity.MA3+"")));
            }
        }
        LineData mALineData = new LineData();
        if(KSetting.getAverage_Visiable1()){
            LineDataSet lineData5 = setMaLine(5,  line5Entries);
            mALineData.addDataSet(lineData5);
        }
        if(KSetting.getAverage_Visiable2()){
            LineDataSet lineData10 = setMaLine(10,  line10Entries);
            mALineData.addDataSet(lineData10);
        }
        if(KSetting.getAverage_Visiable3()){
            LineDataSet lineData30 = setMaLine(30, line20Entries);
            mALineData.addDataSet(lineData30);
        }
        topCombinedData.setData(mALineData);
    }
}
