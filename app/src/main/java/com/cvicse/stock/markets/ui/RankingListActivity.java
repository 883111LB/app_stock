package com.cvicse.stock.markets.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.cvicse.annotations.MVPInject;
import com.cvicse.stock.R;
import com.cvicse.stock.base.PBaseActivity;
import com.cvicse.stock.base.ToolBar;
import com.cvicse.stock.common.ColorUtils;
import com.cvicse.stock.markets.adapter.RankingListAdapter;
import com.cvicse.stock.markets.presenter.contract.RankingListContract;
import com.cvicse.stock.pulltorefresh.PullToRefreshBase;
import com.cvicse.stock.pulltorefresh.PullToRefreshListView;
import com.cvicse.stock.seachstock.SearchHistoryActivity;
import com.cvicse.stock.view.CHScrollView;
import com.cvicse.stock.view.HVListView;
import com.mitake.core.AddValueModel;
import com.mitake.core.QuoteItem;

import java.util.ArrayList;

import butterknife.BindView;

import static com.cvicse.stock.markets.data.ParamUtil.RANKING_TYPE_AMOUNT;
import static com.cvicse.stock.markets.data.ParamUtil.RANKING_TYPE_AMOUNT_CODE;
import static com.cvicse.stock.markets.data.ParamUtil.RANKING_TYPE_AMOUNT_HSSC;
import static com.cvicse.stock.markets.data.ParamUtil.RANKING_TYPE_DECLINE;
import static com.cvicse.stock.markets.data.ParamUtil.RANKING_TYPE_DECLINE_CODE;
import static com.cvicse.stock.markets.data.ParamUtil.RANKING_TYPE_DECLINE_HSSC;
import static com.cvicse.stock.markets.data.ParamUtil.RANKING_TYPE_RAISE;
import static com.cvicse.stock.markets.data.ParamUtil.RANKING_TYPE_RAISE_CODE;
import static com.cvicse.stock.markets.data.ParamUtil.RANKING_TYPE_RAISE_HSSC;
import static com.cvicse.stock.markets.data.ParamUtil.RANKING_TYPE_TURNOVERRATE;
import static com.cvicse.stock.markets.data.ParamUtil.RANKING_TYPE_TURNOVERRATE_CODE;
import static com.cvicse.stock.markets.data.ParamUtil.RANKING_TYPE_TURNOVERRATE_HSSC;
import static com.cvicse.stock.markets.data.ParamUtil.STOCK_NAME_HK;
import static com.cvicse.stock.markets.data.ParamUtil.STOCK_NAME_SHSZ;

/**
 * Created by ruan_ytai on 17-1-17.
 * 排行榜页面（沪深、港股）
 */
public class RankingListActivity extends PBaseActivity implements RankingListContract.View, AdapterView.OnItemClickListener {

    @MVPInject
    RankingListContract.Presenter presenter;

    @BindView(R.id.topbar) ToolBar toolBar;
    @BindView(R.id.scroll_title) CHScrollView scrollTitle;
    @BindView(R.id.lsv_content) PullToRefreshListView lsvContent;
    @BindView(R.id.tev_lastPrice) TextView tevLastPrice;
    @BindView(R.id.tev_changeRate) TextView tevChangeRate;
    @BindView(R.id.tev_change) TextView tevChange;
    @BindView(R.id.tev_volume) TextView tevNowVolume;
    @BindView(R.id.tev_amount) TextView tevAmount;
    @BindView(R.id.tev_turnoverRate) TextView tevTurnoverRate;
    @BindView(R.id.tev_volumeRatio) TextView tevVolumeRatio;
    @BindView(R.id.tev_amplitudeRate) TextView tevAmplitudeRate;
    @BindView(R.id.tev_highPrice) TextView tevHighPrice;
    @BindView(R.id.tev_lowPrice) TextView tevLowPrice;
    @BindView(R.id.tev_kaipan) TextView tevKaipan;
    @BindView(R.id.tev_preClosePrice) TextView tev_preClosePrice;
    @BindView(R.id.tev_pe) TextView tevPe;
    @BindView(R.id.tev_orderRatio) TextView tevOrderRatio;
    @BindView(R.id.tev_pe2) TextView tevPe2;
    @BindView(R.id.tev_totalValue) TextView tevTotalValue;
    @BindView(R.id.tev_flowValue) TextView tevFlowValue;
    @BindView(R.id.tev_pb_ip) TextView tevPb_Ip;
    @BindView(R.id.tev_month_rate) TextView tev_month_rate;// 本月涨幅
    @BindView(R.id.tev_year_rate) TextView tev_year_rate;// 本年涨幅
    @BindView(R.id.tev_recent_month) TextView tev_recent_month;// 近一月涨幅
    @BindView(R.id.tev_recent_year) TextView tev_recent_year;// 近一年涨幅

    private final static int TAG_UP = 0; //升序
    private final static int TAG_DOWN = 1;  //降序
    private final static int TAG_NORMAL = 2;//不以当前属性排序

    private int status_openPrice = TAG_NORMAL; //开盘状态
    private int status_highPrice = TAG_NORMAL; //最高状态，这三个可点击排序
    private int status_lowPrice = TAG_NORMAL; //最低状态
    private int status_lastPrice = TAG_NORMAL; //最新价状态
    private int status_changeRate = TAG_NORMAL; //涨幅状态,(默认以它降序排序)
    private int status_change = TAG_NORMAL; //涨跌状态
    private int status_nowVolume = TAG_NORMAL; //成交量状态
    private int status_amount = TAG_NORMAL; //成交额状态
    private int status_turnoverRate = TAG_NORMAL; //换手率状态
    private int status_volumeRatio = TAG_NORMAL; //量比状态
    private int status_amplitudeRate = TAG_NORMAL; //振幅状态
    private int status_preClosePrice = TAG_NORMAL;//昨收状态
    private int status_pe = TAG_NORMAL; //市盈率
    private int status_pe2 = TAG_NORMAL; //市盈率
    private int status_pb_ip = TAG_NORMAL; //市净率
    private int status_ratio = TAG_NORMAL; //委比状态
    private int status_total = TAG_NORMAL; //总市值
    private int status_flow = TAG_NORMAL; //流通值
    private int status_fund = TAG_NORMAL; //主力净流入状态
    private int status_monthRate = TAG_NORMAL; //本月涨跌幅状态
    private int status_yearRate = TAG_NORMAL; //本年涨跌幅状态
    private int status_recentMRate = TAG_NORMAL; //近一月涨跌幅状态
    private int status_recentYRate = TAG_NORMAL; //近一年涨跌幅状态
    /**
     * 全部排序状态-通用
     */
    private int status_state = TAG_NORMAL; //交易状态0
    private int status_datetime = TAG_NORMAL; //时间3
    private int status_pinyin = TAG_NORMAL; //拼音4
    private int status_market = TAG_NORMAL; //市场别5
    private int status_subtype = TAG_NORMAL; //次类别6
    private int status_nowVolume1 = TAG_NORMAL; //当前成交量14
    private int status_limitUp = TAG_NORMAL; //涨停价16
    private int status_limitDown = TAG_NORMAL; //跌停价17
    private int status_averageValue = TAG_NORMAL; //均价18
    private int status_buyPrice = TAG_NORMAL; //买一价
    private int status_sellPrice = TAG_NORMAL; //卖一价
    private int status_buyVolume = TAG_NORMAL; //外盘量
    private int status_sellVolume = TAG_NORMAL; //内盘量
    private int status_netAsset = TAG_NORMAL; //净资产
    private int status_capital = TAG_NORMAL; //总股本
    private int status_ciShares = TAG_NORMAL; //流通股
    private int status_buyPrices = TAG_NORMAL; //5档买价
    private int status_buyVolumes = TAG_NORMAL; //5档买量
    private int status_sellPrices = TAG_NORMAL; //5档卖价
    private int status_sellVolumes = TAG_NORMAL; //5档卖量
    private int status_receipts = TAG_NORMAL; //动态每股收益
    private int status_orderRatio = TAG_NORMAL; //沪深委比
    private int status_etrustDiff = TAG_NORMAL; //委差
    /**
     * 全部排序状态-增值指标
     */
    private int status_ultralargeflow = TAG_NORMAL; //超大单净流入
    private int status_largeflow = TAG_NORMAL; //大单净流入
    private int status_medflow = TAG_NORMAL; //中单净流入
    private int status_smallflow = TAG_NORMAL; //小单净流入
    private int status_bbd = TAG_NORMAL; //大单净差
    private int status_bbd5 = TAG_NORMAL; //五日大单净差
    private int status_bbd10 = TAG_NORMAL; //十日大单净差
    private int status_ddx = TAG_NORMAL; //主力动向
    private int status_ddx5 = TAG_NORMAL; //五日主力动向
    private int status_ddx10 = TAG_NORMAL; //十日主力动向
    private int status_ddy = TAG_NORMAL; //涨跌动因
    private int status_ddy5 = TAG_NORMAL; //五日涨跌动因
    private int status_netCapInflow = TAG_NORMAL; //主力净流入
    private int status_chg5minutes = TAG_NORMAL; //五分钟涨跌幅
    private int status_mainInflow5 = TAG_NORMAL; //5日主力资金净流入
    private int status_mainInflow10 = TAG_NORMAL; //10日主力资金净流入
    private int status_mainInflow20 = TAG_NORMAL; //20日主力资金净流入
    private int status_rMainInflow5 = TAG_NORMAL; //5日主力资金净流入占比
    private int status_rMainInflow10 = TAG_NORMAL; //10日主力资金净流入占比
    private int status_rMainInflow20 = TAG_NORMAL; //20日主力资金净流入占比

    /**
     * 排序接口第二个参数是拼接的，依次为页码，笔数，排序栏位，正倒序，是否显示停牌股以逗号分开
     * 正倒序(1倒序，0顺序)，是否显示停牌股(0不显示，1显示)
     */
    // 7为最新价
    private String[] lastPriceParams = {"0,20,7,1,1", "0,20,7,0,1"};
    // 8为最高价
    private String[] highPriceParams = {"0,20,8,1,1", "0,20,8,0,1"};
    // 9为最低价
    private String[] lowPriceParams = {"0,20,9,1,1", "0,20,9,0,1"};
    // 10为今开价
    private String[] openPriceParams = {"0,20,10,1,1", "0,20,10,0,1"};
    // 12为涨幅
    private String[] changeRateParams = {"0,20,12,1,1", "0,20,12,0,1"};
    // 19为涨跌
    private String[] changeParams = {"0,20,19,1,1", "0,20,19,0,1"};
    // 13为成交量（总手）
    private String[] nowVolumeParams = {"0,20,13,1,1", "0,20,13,0,1"};
    // 20为成交额
    private String[] amountParams = {"0,20,20,1,1", "0,20,20,0,1"};
    // 15为换手率
    private String[] turnoverRateParams = {"0,20,15,1,1", "0,20,15,0,1"};
    // 21为量比
    private String[] volumeRatioParams = {"0,20,21,1,1", "0,20,21,0,1"};
    // 37为振幅
    private String[] amplitudeRateParams = {"0,20,37,1,1", "0,20,37,0,1"};
    // 11为昨收
    private String[] preClosePriceRateParams = {"0,20,11,1,1", "0,20,11,0,1"};
    // 29为市盈动
    private String[] peRateParams = {"0,20,29,1,1", "0,20,29,0,1"};
    // 42位市盈率静
    private String[] pe2RateParams = {"0,20,42,1,1","0,20,42,0,1"};
    // 30位市净率
    private String[] pbRateParams = {"0,20,30,1,1","0,20,30,0,1"};
    // 40为委比
    private String[] ratioParams = {"0,20,40,1,1", "0,20,40,0,1"};
    // 26为 总市值
    private String[] totalParams = {"0,20,26,1,1","0,20,26,0,1"};
    // 27为 流通市值
    private String[] flowParams = {"0,20,27,1,1","0,20,27,0,1"};
    // -47为主力净流入
    private String[] fundsParams = { "0,20,-47,1,1","0,20,-47,0,1"};
    // 86为本月涨跌幅
    private String[] monthChangeRateParams = { "0,20,86,1,1","0,20,86,0,1"};
    // 87为本年涨跌幅
    private String[] yearChangeRateParams = { "0,20,87,1,1","0,20,87,0,1"};
    // 88为近一月涨跌幅
    private String[] recentMonthCRParams = { "0,20,88,1,1","0,20,88,0,1"};
    // 89为近一年涨跌幅
    private String[] recentYearCRParams = { "0,20,89,1,1","0,20,89,0,1"};

//    SortType.quote_PRE_CLOSE_PRICE
    /**
     * 全部排序参数-通用
     */
    // 交易状态 quote_STATE:0
    private String[] stateParams = { "0,20,0,1,1","0,20,0,0,1"};
    // 时间 quote_DATETIME:3
    private String[] datetimeParams = { "0,20,3,1,1","0,20,3,0,1"};
    // 拼音 quote_PINYIN:4
    private String[] pinyinParams = { "0,20,4,1,1","0,20,4,0,1"};
    // 市场别 quote_MARKET:5
    private String[] marketParams = { "0,20,5,1,1","0,20,5,0,1"};
    // 次类别 quote_SUBTYPE:6
    private String[] subtypeParams = { "0,20,6,1,1","0,20,6,0,1"};
    // 当前成交量 quote_NOW_VOLUME:14
    private String[] nowVolumeParams1 = { "0,20,14,1,1","0,20,14,0,1"};
    // 涨停价 quote_LIMIT_UP:16
    private String[] limitUpParams = { "0,20,16,1,1","0,20,16,0,1"};
    // 跌停价 quote_LIMIT_DOWN:17
    private String[] limitDownParams = { "0,20,17,1,1","0,20,17,0,1"};
    // 均价 quote_AVERAGE_VALUE:18
    private String[] averageValueParams = { "0,20,18,1,1","0,20,18,0,1"};
    // 买一价 quote_BUY_PRICE:22
    private String[] buyPriceParams = { "0,20,22,1,1","0,20,22,0,1"};
    // 卖一价 quote_SELL_PRICE:23
    private String[] sellPriceParams = { "0,20,23,1,1","0,20,23,0,1"};
    // 外盘量 quote_BUY_VOLUME:24
    private String[] buyVolumeParams = { "0,20,24,1,1","0,20,24,0,1"};
    // 内盘量 quote_SELL_VOLUME:25
    private String[] sellVolumeParams = { "0,20,25,1,1","0,20,25,0,1"};
    // 净资产 quote_NET_ASSET:28
    private String[] netAssetParams = { "0,20,28,1,1","0,20,28,0,1"};
    // 总股本 quote_CAPITALIZATION:31
    private String[] capitalizationParams = { "0,20,31,1,1","0,20,31,0,1"};
    // 流通股 quote_CIRCULATING_SHARES:32
    private String[] ciSharesParams = { "0,20,32,1,1","0,20,32,0,1"};
    // 5档买价 quote_BUY_PRICES:33
    private String[] buyPricesParams = { "0,20,33,1,1","0,20,33,0,1"};
    // 5档买量 quote_BUY_VOLUMES:34
    private String[] buyVolumesParams = { "0,20,34,1,1","0,20,34,0,1"};
    // 5档卖价 quote_SELL_PRICES:35
    private String[] sellPricesParams = { "0,20,35,1,1","0,20,35,0,1"};
    // 5档卖量 quote_SELL_VOLUMES:36
    private String[] sellVolumesParams = { "0,20,36,1,1","0,20,36,0,1"};
    // 动态每股收益 quote_RECEIPTS:38
    private String[] receiptsParams = { "0,20,38,1,1","0,20,38,0,1"};
    // 沪深委比 quote_ORDER_RATIO:53
    private String[] orderRatioParams = { "0,20,53,1,1","0,20,53,0,1"};
    // 委差 quote_ETRUST_DIFF:54
    private String[] etrustDiffParams = { "0,20,54,1,1","0,20,54,0,1"};
    /**
     * 全部排序参数-增值指标
     */
    // 超大单净流入 AddValue_ULTRALARGENETINFLOW:-19
    private String[] ultralargeflowParams = { "0,20,-19,1,1","0,20,-19,0,1"};
    // 大单净流入 addValue_LARGENETINFLOW:-20
    private String[] largeflowParams = { "0,20,-20,1,1","0,20,-20,0,1"};
    // 中单净流入 addValue_MEDIUMNETINFLOW:-21
    private String[] medflowParams = { "0,20,-21,1,1","0,20,-21,0,1"};
    // 小单净流入 addValue_SMALLNETINFLOW:-22
    private String[] smallflowParams = { "0,20,-22,1,1","0,20,-22,0,1"};
    // 大单净差 addValue_BBD:-34
    private String[] bbdParams = { "0,20,-34,1,1","0,20,-34,0,1"};
    // 五日大单净差 addValue_BBD5:-35
    private String[] bbd5Params = { "0,20,-35,1,1","0,20,-35,0,1"};
    // 十日大单净差 addValue_BBD10:-36
    private String[] bbd10Params = { "0,20,-36,1,1","0,20,-36,0,1"};
    // 主力动向 addValue_DDX:-37
    private String[] ddxParams = { "0,20,-37,1,1","0,20,-37,0,1"};
    // 五日主力动向 addValue_DDX5:-38
    private String[] ddx5Params = { "0,20,-38,1,1","0,20,-38,0,1"};
    // 十日主力动向 addValue_DDX10:-39
    private String[] ddx10Params = { "0,20,-39,1,1","0,20,-39,0,1"};
    // 涨跌动因 addValue_DDY:-40
    private String[] ddyParams = { "0,20,-40,1,1","0,20,-40,0,1"};
    // 五日涨跌动因 addValue_DDY5:-41
    private String[] ddy5Params = { "0,20,-41,1,1","0,20,-41,0,1"};
    // 主力净流入 addValue_NET_CAPITAL_INFLOW:-47
    private String[] netCapInflowParams = { "0,20,-47,1,1","0,20,-47,0,1"};
    // 五分钟涨跌幅 addValue_CHG5MINUTES:-48
    private String[] chg5minutesParams = { "0,20,-48,1,1","0,20,-48,0,1"};
    // 5日主力资金净流入 addValue_MAINFORCEMONEYNETINFLOW5:-58
    private String[] mainInflow5Params = { "0,20,-58,1,1","0,20,-58,0,1"};
    // 10日主力资金净流入 addValue_MAINFORCEMONEYNETINFLOW10:-59
    private String[] mainInflow10Params = { "0,20,-59,1,1","0,20,-59,0,1"};
    // 20日主力资金净流入 addValue_MAINFORCEMONEYNETINFLOW20:-60
    private String[] mainInflow20Params = { "0,20,-60,1,1","0,20,-60,0,1"};
    // 5日主力资金净流入占比 addValue_RATIOMAINFORCEMONEYNETINFLOW5:-61
    private String[] rMainInflow5Params = { "0,20,-61,1,1","0,20,-61,0,1"};
    // 10日主力资金净流入占比 addValue_RATIOMAINFORCEMONEYNETINFLOW10:-62
    private String[] rMainInflow10Params = { "0,20,-62,1,1","0,20,-62,0,1"};
    // 20日主力资金净流入占比 addValue_RATIOMAINFORCEMONEYNETINFLOW20:-63
    private String[] rMainInflow20Params = { "0,20,-63,1,1","0,20,-63,0,1"};

    /**
     * 四种排序参数
     */
    private String[][] sortParams;

    // 沪深市场 涨幅榜-新增本月，本年，近月，近年涨跌幅
    private String[][] raiseSortParams_HS = new String[][]{
            monthChangeRateParams, yearChangeRateParams, recentMonthCRParams, recentYearCRParams,
            lastPriceParams, changeRateParams, changeParams, nowVolumeParams, amountParams,
            turnoverRateParams, volumeRatioParams, amplitudeRateParams, highPriceParams,
            lowPriceParams, openPriceParams, preClosePriceRateParams, peRateParams, pe2RateParams,pbRateParams,ratioParams,totalParams,flowParams};

    private String[][] raiseSortParams = new String[][]{
            lastPriceParams, changeRateParams, changeParams, nowVolumeParams, amountParams,
            turnoverRateParams, volumeRatioParams, amplitudeRateParams, highPriceParams,
            lowPriceParams, openPriceParams, preClosePriceRateParams, peRateParams, pe2RateParams,pbRateParams,ratioParams,totalParams,flowParams};

    // 跌幅榜-沪深市场
    private String[][] declineSortParams_HS = new String[][]{
            monthChangeRateParams, yearChangeRateParams, recentMonthCRParams, recentYearCRParams,
            lastPriceParams, changeRateParams, changeParams, nowVolumeParams, amountParams,
            turnoverRateParams, volumeRatioParams, amplitudeRateParams, highPriceParams,
            lowPriceParams, openPriceParams, preClosePriceRateParams, peRateParams, pe2RateParams,pbRateParams,ratioParams,totalParams,flowParams};
    private String[][] declineSortParams = new String[][]{
            lastPriceParams, changeRateParams, changeParams, nowVolumeParams, amountParams,
            turnoverRateParams, volumeRatioParams, amplitudeRateParams, highPriceParams,
            lowPriceParams, openPriceParams, preClosePriceRateParams, peRateParams, pe2RateParams,pbRateParams,ratioParams,totalParams,flowParams};
    // 换手率
    private String[][] turnoverRateSortParams = new String[][]{
            lastPriceParams, turnoverRateParams, changeRateParams, changeParams, nowVolumeParams,
            amountParams, volumeRatioParams, amplitudeRateParams, highPriceParams, lowPriceParams,
            openPriceParams, preClosePriceRateParams, peRateParams,pe2RateParams,pbRateParams,ratioParams,totalParams,flowParams};
    // 换手率-沪深市场
    private String[][] turnoverRateSortParams_hs = new String[][]{
            monthChangeRateParams, yearChangeRateParams, recentMonthCRParams, recentYearCRParams,
            lastPriceParams, turnoverRateParams, changeRateParams, changeParams, nowVolumeParams,
            amountParams, volumeRatioParams, amplitudeRateParams, highPriceParams, lowPriceParams,
            openPriceParams, preClosePriceRateParams, peRateParams,pe2RateParams,pbRateParams,ratioParams,totalParams,flowParams};
    // 成交额榜
    private String[][] amountSortParams = new String[][]{
            lastPriceParams, amountParams, changeRateParams, changeParams, nowVolumeParams,
            turnoverRateParams, volumeRatioParams, amplitudeRateParams, highPriceParams, lowPriceParams,
            openPriceParams, preClosePriceRateParams, peRateParams, pe2RateParams,pbRateParams,ratioParams,totalParams,flowParams};
    // 成交额榜-沪深市场
    private String[][] amountSortParams_hs = new String[][]{
            monthChangeRateParams, yearChangeRateParams, recentMonthCRParams, recentYearCRParams,
            lastPriceParams, amountParams, changeRateParams, changeParams, nowVolumeParams,
            turnoverRateParams, volumeRatioParams, amplitudeRateParams, highPriceParams, lowPriceParams,
            openPriceParams, preClosePriceRateParams, peRateParams, pe2RateParams,pbRateParams,ratioParams,totalParams,flowParams};

    /**
     * 四种初始化排序状态
     */
    private int[] status;
    // 沪深市场 涨幅榜-新增本月，本年，近月，近年涨跌幅
    private int[] raise_status_ZF = new int[]{
            status_monthRate, status_yearRate, status_recentMRate, status_recentYRate,
            status_lastPrice, status_changeRate, status_change, status_nowVolume, status_amount,
            status_turnoverRate, status_volumeRatio, status_amplitudeRate, status_highPrice,
            status_lowPrice, status_openPrice, status_preClosePrice, status_pe,status_pe2,status_pb_ip,status_ratio,status_total,status_flow};

    private int[] raise_status = new int[]{
            status_lastPrice, status_changeRate, status_change, status_nowVolume, status_amount,
            status_turnoverRate, status_volumeRatio, status_amplitudeRate, status_highPrice,
            status_lowPrice, status_openPrice, status_preClosePrice, status_pe,status_pe2,status_pb_ip,status_ratio,status_total,status_flow};

    private int[] decline_status = new int[]{
            status_lastPrice, status_changeRate, status_change, status_nowVolume, status_amount,
            status_turnoverRate, status_volumeRatio, status_amplitudeRate, status_highPrice,
            status_lowPrice, status_openPrice, status_preClosePrice, status_pe,status_pe2,status_pb_ip,status_ratio,status_total,status_flow};
    // 跌幅榜-沪深市场
    private int[] decline_status_hs = new int[]{
            status_monthRate, status_yearRate, status_recentMRate, status_recentYRate,
            status_lastPrice, status_changeRate, status_change, status_nowVolume, status_amount,
            status_turnoverRate, status_volumeRatio, status_amplitudeRate, status_highPrice,
            status_lowPrice, status_openPrice, status_preClosePrice, status_pe,status_pe2,status_pb_ip,status_ratio,status_total,status_flow};
    // 换手率
    private int[] turnoverRate_status = new int[]{
            status_lastPrice, status_turnoverRate, status_changeRate, status_change, status_nowVolume,
            status_amount, status_volumeRatio, status_amplitudeRate, status_highPrice,
            status_lowPrice, status_openPrice, status_preClosePrice, status_pe,status_pe2,status_pb_ip,status_ratio,status_total,status_flow};
    // 换手率-沪深市场
    private int[] turnoverRate_status_hs = new int[]{
            status_monthRate, status_yearRate, status_recentMRate, status_recentYRate,
            status_lastPrice, status_turnoverRate, status_changeRate, status_change, status_nowVolume,
            status_amount, status_volumeRatio, status_amplitudeRate, status_highPrice,
            status_lowPrice, status_openPrice, status_preClosePrice, status_pe,status_pe2,status_pb_ip,status_ratio,status_total,status_flow};
    // 成交额榜
    private int[] amount_status = new int[]{
            status_lastPrice, status_amount, status_changeRate, status_change, status_nowVolume,
            status_turnoverRate, status_volumeRatio, status_amplitudeRate, status_highPrice,
            status_lowPrice, status_openPrice, status_preClosePrice, status_pe,status_pe2,status_pb_ip,status_ratio,status_total,status_flow};
    // 成交额榜_沪深市场
    private int[] amount_status_hs = new int[]{
            status_monthRate, status_yearRate, status_recentMRate, status_recentYRate,
            status_lastPrice, status_amount, status_changeRate, status_change, status_nowVolume,
            status_turnoverRate, status_volumeRatio, status_amplitudeRate, status_highPrice,
            status_lowPrice, status_openPrice, status_preClosePrice, status_pe,status_pe2,status_pb_ip,status_ratio,status_total,status_flow};
    // 全排序字段（沪深）（通用+增值指标）（使用：sh1001/sh1002/sh1005;sz1001-1005）
    private int[] raise_status_sh_all = new int[]{
            status_state, status_datetime, status_pinyin, status_market, status_subtype,
            status_lastPrice, status_highPrice, status_lowPrice, status_openPrice, status_preClosePrice,


            status_monthRate, status_yearRate, status_recentMRate, status_recentYRate,
            status_changeRate, status_change, status_nowVolume, status_amount, status_turnoverRate,
            status_volumeRatio, status_amplitudeRate,
             status_pe,status_pe2,status_pb_ip,status_ratio,status_total,
            status_flow,

            status_nowVolume1, status_limitUp, status_limitDown, status_averageValue,
            status_buyPrice, status_sellPrice, status_buyVolume, status_sellVolume, status_netAsset,
            status_capital, status_ciShares, status_buyPrices, status_buyVolumes, status_sellPrices,
            status_sellVolumes, status_receipts, status_orderRatio, status_etrustDiff, status_ultralargeflow,
            status_largeflow, status_medflow, status_smallflow, status_bbd, status_bbd5,
            status_bbd10, status_ddx, status_ddx5, status_ddx10, status_ddy,
            status_ddy5, status_netCapInflow, status_chg5minutes, status_mainInflow5, status_mainInflow10,
            status_mainInflow20, status_rMainInflow5, status_rMainInflow10, status_rMainInflow20
    };


    // 沪深市场 涨幅榜
    private String[] titleNomalTextRaise_ZF = {"本月涨幅", "本年涨幅", "近一月涨幅", "近一年涨幅", "最新", "涨幅", "涨跌", "成交量", "成交额", "换手率", "量比", "振幅", "最高", "最低", "开盘", "昨收", "市盈(动)","市盈(静)","市净率","委比","总市值","流通值"};
    private String[] titleUpTextRaise_ZF = {"本月涨幅↑", "本年涨幅↑", "近一月涨幅↑", "近一年涨幅↑", "最新↑", "涨幅↑", "涨跌↑", "成交量↑", "成交额↑", "换手率↑", "量比↑", "振幅↑", "最高↑", "最低↑", "开盘↑", "昨收↑", "市盈(动)↑","市盈(静)↑","市净率↑","委比↑","总市值↑","流通值↑"};
    private String[] titleDownTextRaise_ZF = {"本月涨幅↓", "本年涨幅↓", "近一月涨幅↓", "近一年涨幅↓", "最新↓", "涨幅↓", "涨跌↓", "成交量↓", "成交额↓", "换手率↓", "量比↓", "振幅↓", "最高↓", "最低↓", "开盘↓", "昨收↓", "市盈(动)↓","市盈(静)↓","市净率↓","委比↓","总市值↓","流通值↓"};

    private String[] titleNomalTextRaise = {"最新", "涨幅", "涨跌", "成交量", "成交额", "换手率", "量比", "振幅", "最高", "最低", "开盘", "昨收", "市盈(动)","市盈(静)","市净率","委比","总市值","流通值"};
    private String[] titleUpTextRaise = {"最新↑", "涨幅↑", "涨跌↑", "成交量↑", "成交额↑", "换手率↑", "量比↑", "振幅↑", "最高↑", "最低↑", "开盘↑", "昨收↑", "市盈(动)↑","市盈(静)↑","市净率↑","委比↑","总市值↑","流通值↑"};
    private String[] titleDownTextRaise = {"最新↓", "涨幅↓", "涨跌↓", "成交量↓", "成交额↓", "换手率↓", "量比↓", "振幅↓", "最高↓", "最低↓", "开盘↓", "昨收↓", "市盈(动)↓","市盈(静)↓","市净率↓","委比↓","总市值↓","流通值↓"};

    private String[] titleNomalTextDecline = {"最新", "涨幅", "涨跌", "成交量", "成交额", "换手率", "量比", "振幅", "最高", "最低", "开盘", "昨收", "市盈率","市盈(静)","市净率","委比","总市值","流通值"};
    private String[] titleUpTextDecline = {"最新↑", "涨幅↑", "涨跌↑", "成交量↑", "成交额↑", "换手率↑", "量比↑", "振幅↑", "最高↑", "最低↑", "开盘↑", "昨收↑", "市盈(动)↑","市盈(静)↑","市净率↑","委比↑","总市值↑","流通值↑"};
    private String[] titleDownTextDecline = {"最新↓", "涨幅↓", "涨跌↓", "成交量↓", "成交额↓", "换手率↓", "量比↓", "振幅↓", "最高↓", "最低↓", "开盘↓", "昨收↓", "市盈(动)↓","市盈(静)↓","市净率↓","委比↓","总市值↓","流通值↓"};
    // 沪深市场 跌幅榜
    private String[] titleNomalTextDecline_HS = {"本月涨幅", "本年涨幅", "近一月涨幅", "近一年涨幅", "最新", "涨幅", "涨跌", "成交量", "成交额", "换手率", "量比", "振幅", "最高", "最低", "开盘", "昨收", "市盈率","市盈(静)","市净率","委比","总市值","流通值"};
    private String[] titleUpTextDecline_HS = {"本月涨幅↑", "本年涨幅↑", "近一月涨幅↑", "近一年涨幅↑", "最新↑", "涨幅↑", "涨跌↑", "成交量↑", "成交额↑", "换手率↑", "量比↑", "振幅↑", "最高↑", "最低↑", "开盘↑", "昨收↑", "市盈(动)↑","市盈(静)↑","市净率↑","委比↑","总市值↑","流通值↑"};
    private String[] titleDownTextDecline_HS = {"本月涨幅↓", "本年涨幅↓", "近一月涨幅↓", "近一年涨幅↓", "最新↓", "涨幅↓", "涨跌↓", "成交量↓", "成交额↓", "换手率↓", "量比↓", "振幅↓", "最高↓", "最低↓", "开盘↓", "昨收↓", "市盈(动)↓","市盈(静)↓","市净率↓","委比↓","总市值↓","流通值↓"};
    // 换手率
    private String[] titleNomalTextRate = {"最新", "换手率", "涨幅", "涨跌", "成交量", "成交额", "量比", "振幅", "最高", "最低", "开盘", "昨收", "市盈率","市盈(静)","市净率","委比","总市值","流通值"};
    private String[] titleUpTextRate = {"最新↑", "换手率↑", "涨幅↑", "涨跌↑", "成交量↑", "成交额↑", "量比↑", "振幅↑", "最高↑", "最低↑", "开盘↑", "昨收↑","市盈(动)↑","市盈(静)↑","市净率↑","委比↑","总市值↑","流通值↑"};
    private String[] titleDownTextRate = {"最新↓", "换手率↓", "涨幅↓", "涨跌↓", "成交量↓", "成交额↓", "量比↓", "振幅↓", "最高↓", "最低↓", "开盘↓", "昨收↓","市盈(动)↓","市盈(静)↓","市净率↓","委比↓","总市值↓","流通值↓"};
    // 换手率 沪深市场
    private String[] titleNomalTextRate_HS = {"本月涨幅", "本年涨幅", "近一月涨幅", "近一年涨幅", "最新", "换手率", "涨幅", "涨跌", "成交量", "成交额", "量比", "振幅", "最高", "最低", "开盘", "昨收", "市盈率","市盈(静)","市净率","委比","总市值","流通值"};
    private String[] titleUpTextRate_HS = {"本月涨幅↑", "本年涨幅↑", "近一月涨幅↑", "近一年涨幅↑", "最新↑", "换手率↑", "涨幅↑", "涨跌↑", "成交量↑", "成交额↑", "量比↑", "振幅↑", "最高↑", "最低↑", "开盘↑", "昨收↑","市盈(动)↑","市盈(静)↑","市净率↑","委比↑","总市值↑","流通值↑"};
    private String[] titleDownTextRate_HS = {"本月涨幅↓", "本年涨幅↓", "近一月涨幅↓", "近一年涨幅↓", "最新↓", "换手率↓", "涨幅↓", "涨跌↓", "成交量↓", "成交额↓", "量比↓", "振幅↓", "最高↓", "最低↓", "开盘↓", "昨收↓","市盈(动)↓","市盈(静)↓","市净率↓","委比↓","总市值↓","流通值↓"};
    // 成交额榜
    private String[] titleNomalTextAmount = {"最新", "成交额", "涨幅", "涨跌", "成交量", "换手率", "量比", "振幅", "最高", "最低", "开盘", "昨收", "市盈率","市盈(静)","市净率","委比","总市值","流通值"};
    private String[] titleUpTextAmount = {"最新↑", "成交额↑", "涨幅↑", "涨跌↑", "成交量↑", "换手率↑", "量比↑", "振幅↑", "最高↑", "最低↑", "开盘↑", "昨收↑", "市盈(动)↑","市盈(静)↑","市净率↑","委比↑","总市值↑","流通值↑"};
    private String[] titleDownTextAmount = {"最新↓", "成交额↓", "涨幅↓", "涨跌↓", "成交量↓", "换手率↓", "量比↓", "振幅↓", "最高↓", "最低↓", "开盘↓", "昨收↓","市盈(动)↓","市盈(静)↓","市净率↓","委比↓","总市值↓","流通值↓"};
    // 成交额榜_沪深市场
    private String[] titleNomalTextAmount_HS = {"本月涨幅", "本年涨幅", "近一月涨幅", "近一年涨幅", "最新", "成交额", "涨幅", "涨跌", "成交量", "换手率", "量比", "振幅", "最高", "最低", "开盘", "昨收", "市盈率","市盈(静)","市净率","委比","总市值","流通值"};
    private String[] titleUpTextAmount_HS = {"本月涨幅↑", "本年涨幅↑", "近一月涨幅↑", "近一年涨幅↑", "最新↑", "成交额↑", "涨幅↑", "涨跌↑", "成交量↑", "换手率↑", "量比↑", "振幅↑", "最高↑", "最低↑", "开盘↑", "昨收↑", "市盈(动)↑","市盈(静)↑","市净率↑","委比↑","总市值↑","流通值↑"};
    private String[] titleDownTextAmount_HS = {"本月涨幅↓", "本年涨幅↓", "近一月涨幅↓", "近一年涨幅↓", "最新↓", "成交额↓", "涨幅↓", "涨跌↓", "成交量↓", "换手率↓", "量比↓", "振幅↓", "最高↓", "最低↓", "开盘↓", "昨收↓","市盈(动)↓","市盈(静)↓","市净率↓","委比↓","总市值↓","流通值↓"};

    // 全排序字段（沪深）（通用+增值指标）（使用：sh1001/sh1002/sh1005;sz1001-1005）
    private String[] titleAllNomalTextTZ = {
            "交易状态", "时间", "拼音", "市场别", "次类别", " 最新价", "最高价", "最低价", "今开价", "昨收价",
            "涨跌比率", "总手", "当前成交量", "换手率", "涨停价", "跌停价", "均价", "涨跌", "成交金额", "量比",
            "买一价", "卖一价", "外盘量", "内盘量", "总市值", "流通市值", "净资产", "pe(动态市盈率)", "市净率", "总股本",
            "流通股", "5档买价", "5档买量", "5档卖价", "5档卖量", "振幅比率", "动态每股收益", "新三板委比", "沪深委比", "委差",
            "静态市盈率", "本月涨跌幅", "本年涨跌幅", "近一月涨跌幅", "近一年涨跌幅", "超大单净流入", "大单净流入", "中单净流入", "小单净流入", "大单净差",
            "五日大单净差", "十日大单净差", "主力动向", "五日主力动向", "十日主力动向", "涨跌动因", "五日涨跌动因", "十日涨跌动因", "主力净流入", "五分钟涨跌幅",
            "5日主力资金净流入", "10日主力资金净流入", "20日主力资金净流入", "5日主力资金净流入占比", "10日主力资金净流入占比", "20日主力资金净流入占比"
    };
    private String[] titleAllUpTextTZ = {
            "交易状态↑", "时间↑", "拼音↑", "市场别↑", "次类别↑", " 最新价↑", "最高价↑", "最低价↑", "今开价↑", "昨收价↑",
            "涨跌比率↑", "总手↑", "当前成交量↑", "换手率↑", "涨停价↑", "跌停价↑", "均价↑", "涨跌↑", "成交金额↑", "量比↑",
            "买一价↑", "卖一价↑", "外盘量↑", "内盘量↑", "总市值↑", "流通市值↑", "净资产↑", "pe(动态市盈率)↑", "市净率↑", "总股本↑",
            "流通股↑", "5档买价↑", "5档买量↑", "5档卖价↑", "5档卖量↑", "振幅比率↑", "动态每股收益↑", "新三板委比↑", "沪深委比↑", "委差↑",
            "静态市盈率↑", "本月涨跌幅↑", "本年涨跌幅↑", "近一月涨跌幅↑", "近一年涨跌幅↑", "超大单净流入↑", "大单净流入↑", "中单净流入↑", "小单净流入↑", "大单净差↑",
            "五日大单净差↑", "十日大单净差↑", "主力动向↑", "五日主力动向↑", "十日主力动向↑", "涨跌动因↑", "五日涨跌动因↑", "十日涨跌动因↑", "主力净流入↑", "五分钟涨跌幅↑",
            "5日主力资金净流入↑", "10日主力资金净流入↑", "20日主力资金净流入↑", "5日主力资金净流入占比↑", "10日主力资金净流入占比↑", "20日主力资金净流入占比"
    };
    private String[] titleAllDownTextTZ = {
            "交易状态↓", "时间↓", "拼音↓", "市场别↓", "次类别↓", " 最新价↓", "最高价↓", "最低价↓", "今开价↓", "昨收价↓",
            "涨跌比率↓", "总手↓", "当前成交量↓", "换手率↓", "涨停价↓", "跌停价↓", "均价↓", "涨跌↓", "成交金额↓", "量比↓",
            "买一价↓", "卖一价↓", "外盘量↓", "内盘量↓", "总市值↓", "流通市值↓", "净资产↓", "pe(动态市盈率)↓", "市净率↓", "总股本↓",
            "流通股↓", "5档买价↓", "5档买量↓", "5档卖价↓", "5档卖量↓", "振幅比率↓", "动态每股收益↓", "新三板委比↓", "沪深委比↓", "委差↓",
            "静态市盈率↓", "本月涨跌幅↓", "本年涨跌幅↓", "近一月涨跌幅↓", "近一年涨跌幅↓", "超大单净流入↓", "大单净流入↓", "中单净流入↓", "小单净流入↓", "大单净差↓",
            "五日大单净差↓", "十日大单净差↓", "主力动向↓", "五日主力动向↓", "十日主力动向↓", "涨跌动因↓", "五日涨跌动因↓", "十日涨跌动因↓", "主力净流入↓", "五分钟涨跌幅↓",
            "5日主力资金净流入↓", "10日主力资金净流入↓", "20日主力资金净流入↓", "5日主力资金净流入占比↓", "10日主力资金净流入占比↓", "20日主力资金净流入占比"
    };

    private String[] titleNomalText;
    private String[] titleUpText;
    private String[] titleDownText;

    private TextView[] mTextViews;

    public static final String KEY_STOCK_TYPE = "stocktype";
    public static final String KEY_RANKING_TYPE = "rankingtype";

    private RankingListAdapter adapter;
    private String stockType;

    //下拉刷新,上拉加载，rankingType参数
    private String refreshRankingType;

    private int currentPage = 0;

    // 排行榜类型
    private String rankingType;

    /**
     * 股票类型，查询的排行榜类型(涨幅榜等)
     */
    public static void actionStart(Context context, String stockType, String rankingType) {
        Intent intent = new Intent(context, RankingListActivity.class);
        intent.putExtra(KEY_STOCK_TYPE, stockType);
        intent.putExtra(KEY_RANKING_TYPE, rankingType);
        context.startActivity(intent);
    }


    @Override
    protected int getLayoutId() {
        stockType = getIntent().getStringExtra(KEY_STOCK_TYPE);

        rankingType = getIntent().getStringExtra(KEY_RANKING_TYPE);

        int layoutId = 0;
        // 根据不同的排序字段加载不同的布局
        switch (rankingType) {
//            case RANKING_TYPE_All_HS:// 全部排序栏位1（沪深）
//                layoutId = R.layout.activity_ranking_raise;
//                titleNomalText = titleAllNomalTextTZ;
//                titleUpText = titleAllUpTextTZ;
//                titleDownText = titleAllDownTextTZ;
//                break;
            case RANKING_TYPE_RAISE_HSSC:// 沪深市场涨幅榜，新增4个字段（本月、本年、近一月、近一年涨幅）
                layoutId = R.layout.activity_ranking_raise_hsgg;
                titleNomalText = titleNomalTextRaise_ZF;
                titleUpText = titleUpTextRaise_ZF;
                titleDownText = titleDownTextRaise_ZF;
                break;
            case RANKING_TYPE_RAISE:// 涨幅榜
                layoutId = R.layout.activity_ranking_raise;
                titleNomalText = titleNomalTextRaise;
                titleUpText = titleUpTextRaise;
                titleDownText = titleDownTextRaise;
                break;

            case RANKING_TYPE_DECLINE:// 跌幅榜
                layoutId = R.layout.activity_ranking_decline;
                titleNomalText = titleNomalTextDecline;
                titleUpText = titleUpTextDecline;
                titleDownText = titleDownTextDecline;
                break;
            case RANKING_TYPE_DECLINE_HSSC:// 跌幅榜-沪深市场
                layoutId = R.layout.activity_ranking_decline_hssc;
                titleNomalText = titleNomalTextDecline_HS;
                titleUpText = titleUpTextDecline_HS;
                titleDownText = titleDownTextDecline_HS;
                break;

            case RANKING_TYPE_TURNOVERRATE:// 换手率
                layoutId = R.layout.activity_ranking_turnoverrate;
                titleNomalText = titleNomalTextRate;
                titleUpText = titleUpTextRate;
                titleDownText = titleDownTextRate;
                break;
            case RANKING_TYPE_TURNOVERRATE_HSSC:// 换手率-沪深市场
                layoutId = R.layout.activity_ranking_turnoverrate_hssc;
                titleNomalText = titleNomalTextRate_HS;
                titleUpText = titleUpTextRate_HS;
                titleDownText = titleDownTextRate_HS;
                break;
            case RANKING_TYPE_AMOUNT:// 成交额榜
                layoutId = R.layout.activity_ranking_amount;
                titleNomalText = titleNomalTextAmount;
                titleUpText = titleUpTextAmount;
                titleDownText = titleDownTextAmount;
                break;
            case RANKING_TYPE_AMOUNT_HSSC:// 成交额榜-沪深市场
                layoutId = R.layout.activity_ranking_amount_hssc;
                titleNomalText = titleNomalTextAmount_HS;
                titleUpText = titleUpTextAmount_HS;
                titleDownText = titleDownTextAmount_HS;
                break;

            default:
                break;
        }
        return layoutId;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {

        presenter.init(stockType);

        /**
         * 根据不同的xml类型初始化数据
         */
        switch (rankingType) {
            case RANKING_TYPE_RAISE_HSSC:// 沪深市场
                refreshRankingType = RANKING_TYPE_RAISE_CODE;
                sortParams = raiseSortParams_HS;// 排序参数
                status = raise_status_ZF;// 状态
                mTextViews = new TextView[]{
                        tev_month_rate, tev_year_rate, tev_recent_month, tev_recent_year,
                        tevLastPrice, tevChangeRate, tevChange, tevNowVolume,
                        tevAmount, tevTurnoverRate, tevVolumeRatio, tevAmplitudeRate, tevHighPrice,
                        tevLowPrice, tevKaipan, tev_preClosePrice, tevPe,tevPe2,tevPb_Ip, tevOrderRatio,tevTotalValue,tevFlowValue};
                break;
            case RANKING_TYPE_RAISE:
                refreshRankingType = RANKING_TYPE_RAISE_CODE;

                sortParams = raiseSortParams;
                status = raise_status;
                mTextViews = new TextView[]{tevLastPrice, tevChangeRate, tevChange, tevNowVolume,
                        tevAmount, tevTurnoverRate, tevVolumeRatio, tevAmplitudeRate, tevHighPrice,
                        tevLowPrice, tevKaipan, tev_preClosePrice, tevPe,tevPe2,tevPb_Ip, tevOrderRatio,tevTotalValue,tevFlowValue};
                break;

            case RANKING_TYPE_DECLINE:// 跌幅榜
                refreshRankingType = RANKING_TYPE_DECLINE_CODE;
                sortParams = declineSortParams;
                status = decline_status;
                mTextViews = new TextView[]{tevLastPrice, tevChangeRate, tevChange, tevNowVolume,
                        tevAmount, tevTurnoverRate, tevVolumeRatio, tevAmplitudeRate, tevHighPrice,
                        tevLowPrice, tevKaipan, tev_preClosePrice, tevPe,tevPe2,tevPb_Ip, tevOrderRatio,tevTotalValue,tevFlowValue};
                break;
            case RANKING_TYPE_DECLINE_HSSC:// 跌幅榜-沪深市场
                refreshRankingType = RANKING_TYPE_DECLINE_CODE;
                sortParams = declineSortParams_HS;
                status = decline_status_hs;
                mTextViews = new TextView[]{
                        tev_month_rate, tev_year_rate, tev_recent_month, tev_recent_year,
                        tevLastPrice, tevChangeRate, tevChange, tevNowVolume,
                        tevAmount, tevTurnoverRate, tevVolumeRatio, tevAmplitudeRate, tevHighPrice,
                        tevLowPrice, tevKaipan, tev_preClosePrice, tevPe,tevPe2,tevPb_Ip, tevOrderRatio,tevTotalValue,tevFlowValue};
                break;

            case RANKING_TYPE_TURNOVERRATE:// 换手率
                refreshRankingType = RANKING_TYPE_TURNOVERRATE_CODE;

                sortParams = turnoverRateSortParams;
                status = turnoverRate_status;
                mTextViews = new TextView[]{tevLastPrice, tevTurnoverRate, tevChangeRate, tevChange,
                        tevNowVolume, tevAmount, tevVolumeRatio, tevAmplitudeRate, tevHighPrice,
                        tevLowPrice, tevKaipan, tev_preClosePrice, tevPe,tevPe2,tevPb_Ip, tevOrderRatio,tevTotalValue,tevFlowValue};
                break;
            case RANKING_TYPE_TURNOVERRATE_HSSC:// 换手率-沪深市场
                refreshRankingType = RANKING_TYPE_TURNOVERRATE_CODE;
                sortParams = turnoverRateSortParams_hs;
                status = turnoverRate_status_hs;
                mTextViews = new TextView[]{
                        tev_month_rate, tev_year_rate, tev_recent_month, tev_recent_year,
                        tevLastPrice, tevTurnoverRate, tevChangeRate, tevChange,
                        tevNowVolume, tevAmount, tevVolumeRatio, tevAmplitudeRate, tevHighPrice,
                        tevLowPrice, tevKaipan, tev_preClosePrice, tevPe,tevPe2,tevPb_Ip, tevOrderRatio,tevTotalValue,tevFlowValue};
                break;

            case RANKING_TYPE_AMOUNT:// 成交额榜
                refreshRankingType = RANKING_TYPE_AMOUNT_CODE;
                sortParams = amountSortParams;
                status = amount_status;
                mTextViews = new TextView[]{tevLastPrice, tevAmount, tevChangeRate, tevChange,
                        tevNowVolume, tevTurnoverRate, tevVolumeRatio, tevAmplitudeRate, tevHighPrice,
                        tevLowPrice, tevKaipan, tev_preClosePrice, tevPe, tevPe2,tevPb_Ip,tevOrderRatio,tevTotalValue,tevFlowValue};
                break;
            case RANKING_TYPE_AMOUNT_HSSC:// 成交额榜-沪深市场
                refreshRankingType = RANKING_TYPE_AMOUNT_CODE;
                sortParams = amountSortParams_hs;
                status = amount_status_hs;
                mTextViews = new TextView[]{
                        tev_month_rate, tev_year_rate, tev_recent_month, tev_recent_year,
                        tevLastPrice, tevAmount, tevChangeRate, tevChange,
                        tevNowVolume, tevTurnoverRate, tevVolumeRatio, tevAmplitudeRate, tevHighPrice,
                        tevLowPrice, tevKaipan, tev_preClosePrice, tevPe, tevPe2,tevPb_Ip,tevOrderRatio,tevTotalValue,tevFlowValue};
                break;

            default:
                break;
        }

        /**
         * 设置标题
         * 沪深或者港股标题为具体的榜，如涨幅榜、跌幅榜等
         * 从更多里面进入的，标题为具体的名称，如上证A股(其实也是涨幅榜)
         */
        if (STOCK_NAME_SHSZ.equals(stockType) || STOCK_NAME_HK.equals(stockType)) {
            toolBar.setTitle(rankingType);
            if (RANKING_TYPE_RAISE_HSSC.equals(rankingType)) {
                toolBar.setTitle(RANKING_TYPE_RAISE);// 涨幅榜
            } else if (RANKING_TYPE_DECLINE_HSSC.equals(rankingType)) {
                toolBar.setTitle(RANKING_TYPE_DECLINE);// 跌幅榜
            } else if (RANKING_TYPE_AMOUNT_HSSC.equals(rankingType)) {
                toolBar.setTitle(RANKING_TYPE_AMOUNT);// 成交额
            } else if (RANKING_TYPE_TURNOVERRATE_HSSC.equals(rankingType)) {
                toolBar.setTitle(RANKING_TYPE_TURNOVERRATE);// 换手率
            }
        } else if (stockType.contains(".")) {
            if (RANKING_TYPE_RAISE_HSSC.equals(rankingType)) {
                toolBar.setTitle(RANKING_TYPE_RAISE);// 涨幅榜
            } else if (RANKING_TYPE_DECLINE_HSSC.equals(rankingType)) {
                toolBar.setTitle(RANKING_TYPE_DECLINE);// 跌幅榜
            } else if (RANKING_TYPE_AMOUNT_HSSC.equals(rankingType)) {
                toolBar.setTitle(RANKING_TYPE_AMOUNT);// 成交额
            } else if (RANKING_TYPE_TURNOVERRATE_HSSC.equals(rankingType)) {
                toolBar.setTitle(RANKING_TYPE_TURNOVERRATE);// 换手率
            } else {
                toolBar.setTitle(rankingType);
            }
        }
        else {
            toolBar.setTitle(stockType);
        }
        lsvContent.setMode(PullToRefreshBase.Mode.BOTH);
        HVListView hvListView = (HVListView) lsvContent.getRefreshableView();
        hvListView.setScrollView(scrollTitle);
        adapter = new RankingListAdapter(this, rankingType);
        lsvContent.setAdapter(adapter);
        lsvContent.setOnItemClickListener(this);
        lsvContent.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                if (currentPage >= 1) {
                    //替换字符串中第一个字符,页数改变，下拉刷新，当前页数-1，
                    /*refreshRankingType = refreshRankingType.replace(refreshRankingType.substring(0,1),
                            (currentPage-1 + ""));*/
                    String[] param = refreshRankingType.split(",");
                    if (param.length == 5) {
                        refreshRankingType = (currentPage - 1) + "," + param[1] + ","
                                + param[2] + "," + param[3] + "," + param[4];
                    }
                    presenter.requestPulldownRankingData(refreshRankingType);
                } else {
                    presenter.requestPulldownRankingData(refreshRankingType);
                }
                lsvContent.onRefreshComplete();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                //替换字符串中第一个字符,页数改变，上拉加载，当前页数+1，
                /*refreshRankingType = refreshRankingType.replace(refreshRankingType.substring(0,1),
                        (currentPage + 1 + ""));*/
                String[] param = refreshRankingType.split(",");
                if (param.length == 5) {
                    refreshRankingType = (currentPage + 1) + "," + param[1] + ","
                            + param[2] + "," + param[3] + "," + param[4];
                }
                presenter.requestPullupRankingData(refreshRankingType);
                lsvContent.onRefreshComplete();
            }
        });

        toolBar.setToolBarClickListener(new ToolBar.ToolBarClickListener() {
            @Override
            public void onClick(View view, ToolBar.Type type) {
                switch (type) {
                    case RIGHT_TYPE_1:
                        SearchHistoryActivity.startActivity(RankingListActivity.this);
                        break;
                }
            }
        });

        for (int i = 0; i < mTextViews.length; i++) {
            final int finalI = i;
            mTextViews[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setAllViewNormal();
                    switch (status[finalI]) {
                        case TAG_NORMAL: //如果本来是普通状态改为降序排序
                            currentPage = 0;
                            status[finalI] = TAG_DOWN;

                            mTextViews[finalI].setText(titleDownText[finalI]);
                            mTextViews[finalI].setTextColor(ColorUtils.BLUE);

                            refreshRankingType = sortParams[finalI][0];
                            presenter.requestSortRankingData(refreshRankingType);
                            break;

                        case TAG_DOWN: //降序排序变为升序排序
                            currentPage = 0;
                            status[finalI] = TAG_UP;

                            mTextViews[finalI].setText(titleUpText[finalI]);
                            mTextViews[finalI].setTextColor(ColorUtils.BLUE);

                            refreshRankingType = sortParams[finalI][1];
                            presenter.requestSortRankingData(refreshRankingType);
                            break;

                        case TAG_UP: //升序排序变为降序排序
                            currentPage = 0;
                            status[finalI] = TAG_DOWN;

                            mTextViews[finalI].setText(titleDownText[finalI]);
                            mTextViews[finalI].setTextColor(ColorUtils.BLUE);

                            refreshRankingType = sortParams[finalI][0];
                            presenter.requestSortRankingData(refreshRankingType);
                            break;

                        default:
                            break;
                    }
                }
            });
        }

    }

    @Override
    protected void initData() {
        presenter.requestRankingData(rankingType);
    }

    /**
     * 排行榜的点击事件
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (position >= 1) {
            StockDetailActivity.startActivity(RankingListActivity.this,
                    (ArrayList<QuoteItem>) adapter.getData(),
                    position - 1);
        }
    }

    /**
     * 请求排行榜详细数据成功
     */
    @Override
    public void onRequestSuccess(ArrayList<QuoteItem> quoteItemlist, ArrayList<AddValueModel> addValueModels) {
        lsvContent.onRefreshComplete();
        if (quoteItemlist != null && quoteItemlist.size() > 0) {
            adapter.setData(quoteItemlist);
        }
    }

    /**
     * 请求排行榜详细数据失败
     */
    @Override
    public void onRequestFail() {

    }

    /**
     * 上拉加载请求数据成功
     * @param quoteItemlist
     */
    @Override
    public void onRequestPullupSuccess(ArrayList<QuoteItem> quoteItemlist, ArrayList<AddValueModel> addValueModels) {
        lsvContent.onRefreshComplete();
        if (quoteItemlist != null && quoteItemlist.size() > 0) {
            currentPage = currentPage + 1;
            adapter.setData(quoteItemlist);
        }
    }

    /**
     * 上拉加载请求数据失败
     */
    @Override
    public void onRequestPullupFail() {

    }

    /**
     * 下拉刷新请求数据成功
     * @param quoteItemList
     */
    @Override
    public void onRequestPulldownSuccess(ArrayList<QuoteItem> quoteItemList, ArrayList<AddValueModel> addValueModels) {
        lsvContent.onRefreshComplete();
        if (quoteItemList != null && quoteItemList.size() > 0) {
            if (currentPage >= 1) {
                currentPage = currentPage - 1;
            } else {
                currentPage = 0;
            }
            adapter.setData(quoteItemList);
        }
    }

    /**
     * 下拉刷新请求数据失败
     */
    @Override
    public void onRequestPulldownFail() {
    }

    /**
     * 将所有控件转化为不按它排行状态
     */
    private void setAllViewNormal() {
        for (int i = 0; i < mTextViews.length; i++) {
            mTextViews[i].setText(titleNomalText[i]);
            mTextViews[i].setTextColor(ColorUtils.GRAY);
        }
    }
}
