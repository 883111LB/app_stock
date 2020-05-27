package com.cvicse.stock.markets.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.cvicse.annotations.MVPInject;
import com.cvicse.base.utils.ToastUtils;
import com.cvicse.stock.R;
import com.cvicse.stock.base.PBaseActivity;
import com.cvicse.stock.base.ToolBar;
import com.cvicse.stock.common.ColorUtils;
import com.cvicse.stock.markets.adapter.new_adapter.RankingListAllAdapter;
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
import static com.cvicse.stock.markets.data.ParamUtil.RANKING_TYPE_All_HLT;
import static com.cvicse.stock.markets.data.ParamUtil.RANKING_TYPE_All_HS;
import static com.cvicse.stock.markets.data.ParamUtil.RANKING_TYPE_All_HS_KCB;
import static com.cvicse.stock.markets.data.ParamUtil.RANKING_TYPE_All_XSB;
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
public class RankingListAllActivity extends PBaseActivity implements RankingListContract.View, AdapterView.OnItemClickListener {

    @MVPInject
    RankingListContract.Presenter presenter;

    @BindView(R.id.topbar) ToolBar toolBar;
    @BindView(R.id.scroll_title) CHScrollView scrollTitle;
    @BindView(R.id.lsv_content) PullToRefreshListView lsvContent;
    @BindView(R.id.tev_lastPrice) TextView tevLastPrice;// 最新价
    @BindView(R.id.tev_changeRate) TextView tevChangeRate;// 涨跌比率
    @BindView(R.id.tev_change) TextView tevChange;// 涨跌
    @BindView(R.id.tev_volume) TextView tevNowVolume;// 总手
    @BindView(R.id.tev_amount) TextView tevAmount;// 成交金额
    @BindView(R.id.tev_turnoverRate) TextView tevTurnoverRate;// 换手率
    @BindView(R.id.tev_volumeRatio) TextView tevVolumeRatio;// 量比
    @BindView(R.id.tev_amplitudeRate) TextView tevAmplitudeRate;// 振幅比率
    @BindView(R.id.tev_highPrice) TextView tevHighPrice;// 最高价
    @BindView(R.id.tev_lowPrice) TextView tevLowPrice;// 最低价
//    @BindView(R.id.tev_kaipan) TextView tevKaipan;
    @BindView(R.id.tev_preClosePrice) TextView tev_preClosePrice;// 昨收价
    @BindView(R.id.tev_pe) TextView tevPe;// pe(动态市盈率)
//    @BindView(R.id.tev_bj_orderRatio) TextView tev_bj_orderRatio;// 新三板委比
    @BindView(R.id.tev_pe2) TextView tevPe2;// 静态市盈率
    @BindView(R.id.tev_totalValue) TextView tevTotalValue;// 总市值
    @BindView(R.id.tev_flowValue) TextView tevFlowValue;// 流通市值
    @BindView(R.id.tev_pb_ip) TextView tevPb_Ip;// 市净率
    @BindView(R.id.tev_month_rate) TextView tev_month_rate;// 本月涨幅
    @BindView(R.id.tev_year_rate) TextView tev_year_rate;// 本年涨幅
    @BindView(R.id.tev_recent_month) TextView tev_recent_month;// 近一月涨幅
    @BindView(R.id.tev_recent_year) TextView tev_recent_year;// 近一年涨幅
    /**
     * 全部
     */
    private TextView tev_state;// 交易状态
    private TextView tev_subtype;// 次类别
    private TextView tev_openPrice;// 今开价
    private TextView tev_nowVolume;// 当前成交量
    private TextView tev_limitUp;// 涨停价
    private TextView tev_limitDown;// 跌停价
    private TextView tev_averageValue;// 均价
    private TextView tev_buyVolume;// 外盘量
    private TextView tev_sellVolume;// 内盘量
    private TextView tev_netAsset;// 净资产
    private TextView tev_capitalization;// 总股本
    private TextView tev_ciShares;// 流通股
    private TextView tev_receipts;// 动态每股收益
    private TextView tev_orderRatio;// 沪深委比
    private TextView tev_etrustDiff;// 委差
    private TextView tev_ultralargeflow;// 超大单净流入
    private TextView tev_largeflow;// 大单净流入
    private TextView tev_medflow;// 中单净流入
    private TextView tev_smallflow;// 小单净流入
    private TextView tev_bbd;// 大单净差
    private TextView tev_bbd5;// 五日大单净差
    private TextView tev_bbd10;// 十日大单净差
    private TextView tev_ddx;// 主力动向
    private TextView tev_ddx5;// 五日主力动向
    private TextView tev_ddx10;// 十日主力动向
    private TextView tev_ddy;// 涨跌动因
    private TextView tev_ddy5;// 五日涨跌动因
    private TextView tev_ddy10;// 十日涨跌动因
    private TextView tev_netCapInflow;// 主力净流入
    private TextView tev_chg5minutes;// 五分钟涨跌幅
    private TextView tev_mainInflow5;// 5日主力资金净流入
    private TextView tev_mainInflow10;// 10日主力资金净流入
    private TextView tev_mainInflow20;// 20日主力资金净流入
    private TextView tev_rMainInflow5;// 5日主力资金净流入占比
    private TextView tev_rMainInflow10;// 10日主力资金净流入占比
    private TextView tev_rMainInflow20;// 20日主力资金净流入占比主力动向
    private TextView tev_afterVolume;// 盘后成交量
    private TextView tev_afterAmount;// 盘后成交额
    private TextView tev_drCurrent;// 当前份额57
    private TextView tev_drPreClosing;// 前收盘份额58

    private final static int TAG_UP = 0; //升序
    private final static int TAG_DOWN = 1;  //降序
    private final static int TAG_NORMAL = 2;//不以当前属性排序

    private int status_openPrice = TAG_NORMAL; //开盘状态
    private int status_highPrice = TAG_NORMAL; //最高状态，这三个可点击排序
    private int status_lowPrice = TAG_NORMAL; //最低状态
    private int status_lastPrice = TAG_NORMAL; //最新价状态
    private int status_changeRate = TAG_NORMAL; //涨幅状态,(默认以它降序排序)，涨跌比率
    private int status_change = TAG_NORMAL; //涨跌状态
    private int status_nowVolume = TAG_NORMAL; //成交量状态13
    private int status_amount = TAG_NORMAL; //成交额状态
    private int status_turnoverRate = TAG_NORMAL; //换手率状态
    private int status_volumeRatio = TAG_NORMAL; //量比
    private int status_amplitudeRate = TAG_NORMAL; //振幅状态
    private int status_preClosePrice = TAG_NORMAL;//昨收价
    private int status_pe = TAG_NORMAL; //市盈率
    private int status_pe2 = TAG_NORMAL; //市盈率 静态市盈率42
    private int status_pb_ip = TAG_NORMAL; //市净率
//    private int status_bj_ratio = TAG_NORMAL; //委比 (新三板委比 40）
    private int status_total = TAG_NORMAL; //总市值
    private int status_flow = TAG_NORMAL; //流通值
//    private int status_fund = TAG_NORMAL; //主力净流入状态
    private int status_monthRate = TAG_NORMAL; //本月涨跌幅状态
    private int status_yearRate = TAG_NORMAL; //本年涨跌幅状态
    private int status_recentMRate = TAG_NORMAL; //近一月涨跌幅状态
    private int status_recentYRate = TAG_NORMAL; //近一年涨跌幅状态
    /**
     * 全部排序状态-通用
     */
    private int status_state = TAG_NORMAL; //交易状态0
    private int status_subtype = TAG_NORMAL; //次类别6
    private int status_nowVolume1 = TAG_NORMAL; //当前成交量14
    private int status_limitUp = TAG_NORMAL; //涨停价16
    private int status_limitDown = TAG_NORMAL; //跌停价17
    private int status_averageValue = TAG_NORMAL; //均价18
    private int status_buyVolume = TAG_NORMAL; //外盘量
    private int status_sellVolume = TAG_NORMAL; //内盘量
    private int status_netAsset = TAG_NORMAL; //净资产
    private int status_capital = TAG_NORMAL; //总股本
    private int status_ciShares = TAG_NORMAL; //流通股
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
    private int status_ddy10 = TAG_NORMAL; //十日涨跌动因
    private int status_netCapInflow = TAG_NORMAL; //主力净流入
    private int status_chg5minutes = TAG_NORMAL; //五分钟涨跌幅
    private int status_mainInflow5 = TAG_NORMAL; //5日主力资金净流入
    private int status_mainInflow10 = TAG_NORMAL; //10日主力资金净流入
    private int status_mainInflow20 = TAG_NORMAL; //20日主力资金净流入
    private int status_rMainInflow5 = TAG_NORMAL; //5日主力资金净流入占比
    private int status_rMainInflow10 = TAG_NORMAL; //10日主力资金净流入占比
    private int status_rMainInflow20 = TAG_NORMAL; //20日主力资金净流入占比
    /**
     * 全部排序状态-科创版
     */
    private int status_afterVolume = TAG_NORMAL; // 盘后成交量72
    private int status_afterAmount = TAG_NORMAL; //盘后成交额 73
    /**
     * 全部排序状态-沪伦通
     */
    private int status_drCurrent = TAG_NORMAL; // 当前份额 57
    private int status_drPreClosing = TAG_NORMAL; //前收盘份额 58
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
    // 40为新三板委比
    private String[] bj_ratioParams = {"0,20,40,1,1", "0,20,40,0,1"};
    // 26为 总市值
    private String[] totalParams = {"0,20,26,1,1","0,20,26,0,1"};
    // 27为 流通市值
    private String[] flowParams = {"0,20,27,1,1","0,20,27,0,1"};
    // -47为主力净流入
//    private String[] fundsParams = { "0,20,-47,1,1","0,20,-47,0,1"};
    // 86为本月涨跌幅
    private String[] monthChangeRateParams = { "0,20,86,1,1","0,20,86,0,1"};
    // 87为本年涨跌幅
    private String[] yearChangeRateParams = { "0,20,87,1,1","0,20,87,0,1"};
    // 88为近一月涨跌幅
    private String[] recentMonthCRParams = { "0,20,88,1,1","0,20,88,0,1"};
    // 89为近一年涨跌幅
    private String[] recentYearCRParams = { "0,20,89,1,1","0,20,89,0,1"};

    /**
     * 全部排序参数-通用
     */
    // 交易状态 quote_STATE:0
    private String[] stateParams = { "0,20,0,1,1","0,20,0,0,1"};
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
    // 十日涨跌动因 addValue_DDY10:-42
    private String[] ddy10Params = { "0,20,-42,1,1","0,20,-42,0,1"};
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
     * 全部排序参数-科创版
     */
    // 盘后成交量 quote_AFTER_HOURS_VOLUME:72
    private String[] afterVolumeParams = { "0,20,72,1,1","0,20,72,0,1"};
    // 盘后成交额 quote_AFTER_HOURS_AMOUNT:73
    private String[] afterAmountParams = { "0,20,73,1,1","0,20,73,0,1"};
    /**
     * 全部排序参数-沪伦通
     */
    // 当前份额 quote_DR_CURRENT_SHARE:57
    private String[] drCurrentParams = { "0,20,57,1,1","0,20,57,0,1"};
    // 前收盘份额 quote_DR_PREVIOUS_CLOSING_SHARE:58
    private String[] drPreClosingParams = { "0,20,58,1,1","0,20,58,0,1"};

    /**
     * 四种排序参数
     */
    private String[][] sortParams;
    // 沪深市场 涨幅榜-新增本月，本年，近月，近年涨跌幅
//    private String[][] raiseSortParams_HS = new String[][]{
//            monthChangeRateParams, yearChangeRateParams, recentMonthCRParams, recentYearCRParams,
//            lastPriceParams, changeRateParams, changeParams, nowVolumeParams, amountParams,
//            turnoverRateParams, volumeRatioParams, amplitudeRateParams, highPriceParams,
//            lowPriceParams, openPriceParams, preClosePriceRateParams, peRateParams, pe2RateParams,pbRateParams,orderRatioParams,totalParams,flowParams};
//
//    private String[][] raiseSortParams = new String[][]{
//            lastPriceParams, changeRateParams, changeParams, nowVolumeParams, amountParams,
//            turnoverRateParams, volumeRatioParams, amplitudeRateParams, highPriceParams,
//            lowPriceParams, openPriceParams, preClosePriceRateParams, peRateParams, pe2RateParams,pbRateParams,orderRatioParams,totalParams,flowParams};
//
//    // 跌幅榜-沪深市场
//    private String[][] declineSortParams_HS = new String[][]{
//            monthChangeRateParams, yearChangeRateParams, recentMonthCRParams, recentYearCRParams,
//            lastPriceParams, changeRateParams, changeParams, nowVolumeParams, amountParams,
//            turnoverRateParams, volumeRatioParams, amplitudeRateParams, highPriceParams,
//            lowPriceParams, openPriceParams, preClosePriceRateParams, peRateParams, pe2RateParams,pbRateParams,orderRatioParams,totalParams,flowParams};
//    private String[][] declineSortParams = new String[][]{
//            lastPriceParams, changeRateParams, changeParams, nowVolumeParams, amountParams,
//            turnoverRateParams, volumeRatioParams, amplitudeRateParams, highPriceParams,
//            lowPriceParams, openPriceParams, preClosePriceRateParams, peRateParams, pe2RateParams,pbRateParams,orderRatioParams,totalParams,flowParams};
//    // 换手率
//    private String[][] turnoverRateSortParams = new String[][]{
//            lastPriceParams, turnoverRateParams, changeRateParams, changeParams, nowVolumeParams,
//            amountParams, volumeRatioParams, amplitudeRateParams, highPriceParams, lowPriceParams,
//            openPriceParams, preClosePriceRateParams, peRateParams,pe2RateParams,pbRateParams,orderRatioParams,totalParams,flowParams};
//    // 换手率-沪深市场
//    private String[][] turnoverRateSortParams_hs = new String[][]{
//            monthChangeRateParams, yearChangeRateParams, recentMonthCRParams, recentYearCRParams,
//            lastPriceParams, turnoverRateParams, changeRateParams, changeParams, nowVolumeParams,
//            amountParams, volumeRatioParams, amplitudeRateParams, highPriceParams, lowPriceParams,
//            openPriceParams, preClosePriceRateParams, peRateParams,pe2RateParams,pbRateParams,orderRatioParams,totalParams,flowParams};
//    // 成交额榜
//    private String[][] amountSortParams = new String[][]{
//            lastPriceParams, amountParams, changeRateParams, changeParams, nowVolumeParams,
//            turnoverRateParams, volumeRatioParams, amplitudeRateParams, highPriceParams, lowPriceParams,
//            openPriceParams, preClosePriceRateParams, peRateParams, pe2RateParams,pbRateParams,orderRatioParams,totalParams,flowParams};
//    // 成交额榜-沪深市场
//    private String[][] amountSortParams_hs = new String[][]{
//            monthChangeRateParams, yearChangeRateParams, recentMonthCRParams, recentYearCRParams,
//            lastPriceParams, amountParams, changeRateParams, changeParams, nowVolumeParams,
//            turnoverRateParams, volumeRatioParams, amplitudeRateParams, highPriceParams, lowPriceParams,
//            openPriceParams, preClosePriceRateParams, peRateParams, pe2RateParams,pbRateParams,orderRatioParams,totalParams,flowParams};

    // 涨幅 全排序字段（通用）
    private String[][] raiseAllParams_T = new String[][]{
            stateParams, subtypeParams,
            lastPriceParams, highPriceParams, lowPriceParams, openPriceParams, preClosePriceRateParams,
            changeRateParams, nowVolumeParams, nowVolumeParams1, turnoverRateParams, limitUpParams,
            limitDownParams, averageValueParams, changeParams, amountParams, volumeRatioParams,
            buyVolumeParams, sellVolumeParams, totalParams,
            flowParams, netAssetParams, peRateParams, pbRateParams, capitalizationParams,
            ciSharesParams,
            amplitudeRateParams, receiptsParams, orderRatioParams, etrustDiffParams,
            pe2RateParams, monthChangeRateParams, yearChangeRateParams, recentMonthCRParams, recentYearCRParams
    };
    // 涨幅 全排序字段（沪深）（通用+增值指标）（使用者：sh1001/sh1002/sh1005;sz1001-1005）
    private String[][] raiseAllParams_SH = new String[][]{
            stateParams, subtypeParams,
            lastPriceParams, highPriceParams, lowPriceParams, openPriceParams, preClosePriceRateParams,
            changeRateParams, nowVolumeParams, nowVolumeParams1, turnoverRateParams, limitUpParams,
            limitDownParams, averageValueParams, changeParams, amountParams, volumeRatioParams,
            buyVolumeParams, sellVolumeParams, totalParams,
            flowParams, netAssetParams, peRateParams, pbRateParams, capitalizationParams,
            ciSharesParams,
            amplitudeRateParams, receiptsParams, orderRatioParams, etrustDiffParams,
            pe2RateParams, monthChangeRateParams, yearChangeRateParams, recentMonthCRParams, recentYearCRParams,
            ultralargeflowParams, largeflowParams, medflowParams, smallflowParams, bbdParams,
            bbd5Params, bbd10Params, ddxParams, ddx5Params, ddx10Params,
            ddyParams, ddy5Params, ddy10Params, netCapInflowParams, chg5minutesParams,
            mainInflow5Params, mainInflow10Params, mainInflow20Params, rMainInflow5Params, rMainInflow10Params,
            rMainInflow20Params
    };
    // 涨幅 全排序字段（沪深-科创版）（通用+增值指标+科创版）（使用者：sh1006）
    private String[][] raiseAllParams_SH_KCB = new String[][]{
            stateParams, subtypeParams,
            lastPriceParams, highPriceParams, lowPriceParams, openPriceParams, preClosePriceRateParams,
            changeRateParams, nowVolumeParams, nowVolumeParams1, turnoverRateParams, limitUpParams,
            limitDownParams, averageValueParams, changeParams, amountParams, volumeRatioParams,
            buyVolumeParams, sellVolumeParams, totalParams,
            flowParams, netAssetParams, peRateParams, pbRateParams, capitalizationParams,
            ciSharesParams,
            amplitudeRateParams, receiptsParams, orderRatioParams, etrustDiffParams,
            pe2RateParams, monthChangeRateParams, yearChangeRateParams, recentMonthCRParams, recentYearCRParams,
            ultralargeflowParams, largeflowParams, medflowParams, smallflowParams, bbdParams,
            bbd5Params, bbd10Params, ddxParams, ddx5Params, ddx10Params,
            ddyParams, ddy5Params, ddy10Params, netCapInflowParams, chg5minutesParams,
            mainInflow5Params, mainInflow10Params, mainInflow20Params, rMainInflow5Params, rMainInflow10Params,
            rMainInflow20Params, afterVolumeParams, afterAmountParams
    };
    // 涨幅 全排序字段（沪伦通）（通用+沪伦通）
    private String[][] raiseAllParams_HLT = new String[][]{
            stateParams, subtypeParams,
            lastPriceParams, highPriceParams, lowPriceParams, openPriceParams, preClosePriceRateParams,
            changeRateParams, nowVolumeParams, nowVolumeParams1, turnoverRateParams, limitUpParams,
            limitDownParams, averageValueParams, changeParams, amountParams, volumeRatioParams,
            buyVolumeParams, sellVolumeParams, totalParams,
            flowParams, netAssetParams, peRateParams, pbRateParams, capitalizationParams,
            ciSharesParams,
            amplitudeRateParams, receiptsParams, orderRatioParams, etrustDiffParams,
            pe2RateParams, monthChangeRateParams, yearChangeRateParams, recentMonthCRParams, recentYearCRParams,
            drCurrentParams, drPreClosingParams
    };
    // 涨幅 新三板全排序字段（通用、委比字段不一样）
    private String[][] raiseAllParams_XSB = new String[][]{
            stateParams, subtypeParams,
            lastPriceParams, highPriceParams, lowPriceParams, openPriceParams, preClosePriceRateParams,
            changeRateParams, nowVolumeParams, nowVolumeParams1, turnoverRateParams, limitUpParams,
            limitDownParams, averageValueParams, changeParams, amountParams, volumeRatioParams,
            buyVolumeParams, sellVolumeParams, totalParams,
            flowParams, netAssetParams, peRateParams, pbRateParams, capitalizationParams,
            ciSharesParams,
            amplitudeRateParams, receiptsParams, bj_ratioParams, etrustDiffParams,
            pe2RateParams, monthChangeRateParams, yearChangeRateParams, recentMonthCRParams, recentYearCRParams
    };
    /**
     * 四种初始化排序状态
     */
    private int[] status;
    // 沪深市场 涨幅榜-新增本月，本年，近月，近年涨跌幅
//    private int[] raise_status_ZF = new int[]{
//            status_monthRate, status_yearRate, status_recentMRate, status_recentYRate,
//            status_lastPrice, status_changeRate, status_change, status_nowVolume, status_amount,
//            status_turnoverRate, status_volumeRatio, status_amplitudeRate, status_highPrice,
//            status_lowPrice, status_openPrice, status_preClosePrice, status_pe,status_pe2,status_pb_ip,status_orderRatio,status_total,status_flow};
//
//    private int[] raise_status = new int[]{
//            status_lastPrice, status_changeRate, status_change, status_nowVolume, status_amount,
//            status_turnoverRate, status_volumeRatio, status_amplitudeRate, status_highPrice,
//            status_lowPrice, status_openPrice, status_preClosePrice, status_pe,status_pe2,status_pb_ip,status_orderRatio,status_total,status_flow};
//
//    private int[] decline_status = new int[]{
//            status_lastPrice, status_changeRate, status_change, status_nowVolume, status_amount,
//            status_turnoverRate, status_volumeRatio, status_amplitudeRate, status_highPrice,
//            status_lowPrice, status_openPrice, status_preClosePrice, status_pe,status_pe2,status_pb_ip,status_orderRatio,status_total,status_flow};
//    // 跌幅榜-沪深市场
//    private int[] decline_status_hs = new int[]{
//            status_monthRate, status_yearRate, status_recentMRate, status_recentYRate,
//            status_lastPrice, status_changeRate, status_change, status_nowVolume, status_amount,
//            status_turnoverRate, status_volumeRatio, status_amplitudeRate, status_highPrice,
//            status_lowPrice, status_openPrice, status_preClosePrice, status_pe,status_pe2,status_pb_ip,status_orderRatio,status_total,status_flow};
//    // 换手率
//    private int[] turnoverRate_status = new int[]{
//            status_lastPrice, status_turnoverRate, status_changeRate, status_change, status_nowVolume,
//            status_amount, status_volumeRatio, status_amplitudeRate, status_highPrice,
//            status_lowPrice, status_openPrice, status_preClosePrice, status_pe,status_pe2,status_pb_ip,status_orderRatio,status_total,status_flow};
//    // 换手率-沪深市场
//    private int[] turnoverRate_status_hs = new int[]{
//            status_monthRate, status_yearRate, status_recentMRate, status_recentYRate,
//            status_lastPrice, status_turnoverRate, status_changeRate, status_change, status_nowVolume,
//            status_amount, status_volumeRatio, status_amplitudeRate, status_highPrice,
//            status_lowPrice, status_openPrice, status_preClosePrice, status_pe,status_pe2,status_pb_ip,status_orderRatio,status_total,status_flow};
//    // 成交额榜
//    private int[] amount_status = new int[]{
//            status_lastPrice, status_amount, status_changeRate, status_change, status_nowVolume,
//            status_turnoverRate, status_volumeRatio, status_amplitudeRate, status_highPrice,
//            status_lowPrice, status_openPrice, status_preClosePrice, status_pe,status_pe2,status_pb_ip,status_orderRatio,status_total,status_flow};
//    // 成交额榜_沪深市场
//    private int[] amount_status_hs = new int[]{
//            status_monthRate, status_yearRate, status_recentMRate, status_recentYRate,
//            status_lastPrice, status_amount, status_changeRate, status_change, status_nowVolume,
//            status_turnoverRate, status_volumeRatio, status_amplitudeRate, status_highPrice,
//            status_lowPrice, status_openPrice, status_preClosePrice, status_pe,status_pe2,status_pb_ip,status_orderRatio,status_total,status_flow};

    // 全排序字段（通用）
    private int[] raise_status_t_all = new int[]{
            status_state, status_subtype,
            status_lastPrice, status_highPrice, status_lowPrice, status_openPrice, status_preClosePrice,
            status_changeRate,status_nowVolume, status_nowVolume1, status_turnoverRate, status_limitUp,
            status_limitDown, status_averageValue, status_change, status_amount, status_volumeRatio,
            status_buyVolume, status_sellVolume, status_total,
            status_flow, status_netAsset, status_pe, status_pb_ip, status_capital,
            status_ciShares,
            status_amplitudeRate, status_receipts, status_orderRatio, status_etrustDiff,
            status_pe2, status_monthRate, status_yearRate, status_recentMRate, status_recentYRate
    };
    // 全排序字段（沪深）（通用+增值指标）（使用者：sh1001/sh1002/sh1005;sz1001-1005）
    private int[] raise_status_sh_all = new int[]{
            status_state, status_subtype,
            status_lastPrice, status_highPrice, status_lowPrice, status_openPrice, status_preClosePrice,
            status_changeRate,status_nowVolume, status_nowVolume1, status_turnoverRate, status_limitUp,
            status_limitDown, status_averageValue, status_change, status_amount, status_volumeRatio,
            status_buyVolume, status_sellVolume, status_total,
            status_flow, status_netAsset, status_pe, status_pb_ip, status_capital,
            status_ciShares,
            status_amplitudeRate, status_receipts, status_orderRatio, status_etrustDiff,
            status_pe2, status_monthRate, status_yearRate, status_recentMRate, status_recentYRate,
            status_ultralargeflow, status_largeflow, status_medflow, status_smallflow, status_bbd,
            status_bbd5, status_bbd10, status_ddx, status_ddx5, status_ddx10,
            status_ddy, status_ddy5, status_ddy10, status_netCapInflow, status_chg5minutes,
            status_mainInflow5, status_mainInflow10, status_mainInflow20, status_rMainInflow5, status_rMainInflow10,
            status_rMainInflow20
    };
    // 全排序字段（沪深-科创版）（通用+增值指标+科创版）（使用者：sh1006）
    private int[] raise_status_kcb_all = new int[]{
            status_state, status_subtype,
            status_lastPrice, status_highPrice, status_lowPrice, status_openPrice, status_preClosePrice,
            status_changeRate,status_nowVolume, status_nowVolume1, status_turnoverRate, status_limitUp,
            status_limitDown, status_averageValue, status_change, status_amount, status_volumeRatio,
            status_buyVolume, status_sellVolume, status_total,
            status_flow, status_netAsset, status_pe, status_pb_ip, status_capital,
            status_ciShares,
            status_amplitudeRate, status_receipts, status_orderRatio, status_etrustDiff,
            status_pe2, status_monthRate, status_yearRate, status_recentMRate, status_recentYRate,
            status_ultralargeflow, status_largeflow, status_medflow, status_smallflow, status_bbd,
            status_bbd5, status_bbd10, status_ddx, status_ddx5, status_ddx10,
            status_ddy, status_ddy5, status_ddy10, status_netCapInflow, status_chg5minutes,
            status_mainInflow5, status_mainInflow10, status_mainInflow20, status_rMainInflow5, status_rMainInflow10,
            status_rMainInflow20, status_afterVolume, status_afterAmount
    };
    // 全排序字段（沪伦通）（通用+沪伦通）
    private int[] raise_status_hlt_all = new int[]{
            status_state, status_subtype,
            status_lastPrice, status_highPrice, status_lowPrice, status_openPrice, status_preClosePrice,
            status_changeRate,status_nowVolume, status_nowVolume1, status_turnoverRate, status_limitUp,
            status_limitDown, status_averageValue, status_change, status_amount, status_volumeRatio,
            status_buyVolume, status_sellVolume, status_total,
            status_flow, status_netAsset, status_pe, status_pb_ip, status_capital,
            status_ciShares,
            status_amplitudeRate, status_receipts, status_orderRatio, status_etrustDiff,
            status_pe2, status_monthRate, status_yearRate, status_recentMRate, status_recentYRate,
            status_drCurrent, status_drPreClosing
    };

    // 沪深市场 涨幅榜
//    private String[] titleNomalTextRaise_ZF = {"本月涨幅", "本年涨幅", "近一月涨幅", "近一年涨幅", "最新", "涨幅", "涨跌", "成交量", "成交额", "换手率", "量比", "振幅", "最高", "最低", "开盘", "昨收", "市盈(动)","市盈(静)","市净率","委比","总市值","流通值"};
//    private String[] titleUpTextRaise_ZF = {"本月涨幅↑", "本年涨幅↑", "近一月涨幅↑", "近一年涨幅↑", "最新↑", "涨幅↑", "涨跌↑", "成交量↑", "成交额↑", "换手率↑", "量比↑", "振幅↑", "最高↑", "最低↑", "开盘↑", "昨收↑", "市盈(动)↑","市盈(静)↑","市净率↑","委比↑","总市值↑","流通值↑"};
//    private String[] titleDownTextRaise_ZF = {"本月涨幅↓", "本年涨幅↓", "近一月涨幅↓", "近一年涨幅↓", "最新↓", "涨幅↓", "涨跌↓", "成交量↓", "成交额↓", "换手率↓", "量比↓", "振幅↓", "最高↓", "最低↓", "开盘↓", "昨收↓", "市盈(动)↓","市盈(静)↓","市净率↓","委比↓","总市值↓","流通值↓"};
//
//    private String[] titleNomalTextRaise = {"最新", "涨幅", "涨跌", "成交量", "成交额", "换手率", "量比", "振幅", "最高", "最低", "开盘", "昨收", "市盈(动)","市盈(静)","市净率","委比","总市值","流通值"};
//    private String[] titleUpTextRaise = {"最新↑", "涨幅↑", "涨跌↑", "成交量↑", "成交额↑", "换手率↑", "量比↑", "振幅↑", "最高↑", "最低↑", "开盘↑", "昨收↑", "市盈(动)↑","市盈(静)↑","市净率↑","委比↑","总市值↑","流通值↑"};
//    private String[] titleDownTextRaise = {"最新↓", "涨幅↓", "涨跌↓", "成交量↓", "成交额↓", "换手率↓", "量比↓", "振幅↓", "最高↓", "最低↓", "开盘↓", "昨收↓", "市盈(动)↓","市盈(静)↓","市净率↓","委比↓","总市值↓","流通值↓"};
//
//    private String[] titleNomalTextDecline = {"最新", "涨幅", "涨跌", "成交量", "成交额", "换手率", "量比", "振幅", "最高", "最低", "开盘", "昨收", "市盈率","市盈(静)","市净率","委比","总市值","流通值"};
//    private String[] titleUpTextDecline = {"最新↑", "涨幅↑", "涨跌↑", "成交量↑", "成交额↑", "换手率↑", "量比↑", "振幅↑", "最高↑", "最低↑", "开盘↑", "昨收↑", "市盈(动)↑","市盈(静)↑","市净率↑","委比↑","总市值↑","流通值↑"};
//    private String[] titleDownTextDecline = {"最新↓", "涨幅↓", "涨跌↓", "成交量↓", "成交额↓", "换手率↓", "量比↓", "振幅↓", "最高↓", "最低↓", "开盘↓", "昨收↓", "市盈(动)↓","市盈(静)↓","市净率↓","委比↓","总市值↓","流通值↓"};
//    // 沪深市场 跌幅榜
//    private String[] titleNomalTextDecline_HS = {"本月涨幅", "本年涨幅", "近一月涨幅", "近一年涨幅", "最新", "涨幅", "涨跌", "成交量", "成交额", "换手率", "量比", "振幅", "最高", "最低", "开盘", "昨收", "市盈率","市盈(静)","市净率","委比","总市值","流通值"};
//    private String[] titleUpTextDecline_HS = {"本月涨幅↑", "本年涨幅↑", "近一月涨幅↑", "近一年涨幅↑", "最新↑", "涨幅↑", "涨跌↑", "成交量↑", "成交额↑", "换手率↑", "量比↑", "振幅↑", "最高↑", "最低↑", "开盘↑", "昨收↑", "市盈(动)↑","市盈(静)↑","市净率↑","委比↑","总市值↑","流通值↑"};
//    private String[] titleDownTextDecline_HS = {"本月涨幅↓", "本年涨幅↓", "近一月涨幅↓", "近一年涨幅↓", "最新↓", "涨幅↓", "涨跌↓", "成交量↓", "成交额↓", "换手率↓", "量比↓", "振幅↓", "最高↓", "最低↓", "开盘↓", "昨收↓", "市盈(动)↓","市盈(静)↓","市净率↓","委比↓","总市值↓","流通值↓"};
//    // 换手率
//    private String[] titleNomalTextRate = {"最新", "换手率", "涨幅", "涨跌", "成交量", "成交额", "量比", "振幅", "最高", "最低", "开盘", "昨收", "市盈率","市盈(静)","市净率","委比","总市值","流通值"};
//    private String[] titleUpTextRate = {"最新↑", "换手率↑", "涨幅↑", "涨跌↑", "成交量↑", "成交额↑", "量比↑", "振幅↑", "最高↑", "最低↑", "开盘↑", "昨收↑","市盈(动)↑","市盈(静)↑","市净率↑","委比↑","总市值↑","流通值↑"};
//    private String[] titleDownTextRate = {"最新↓", "换手率↓", "涨幅↓", "涨跌↓", "成交量↓", "成交额↓", "量比↓", "振幅↓", "最高↓", "最低↓", "开盘↓", "昨收↓","市盈(动)↓","市盈(静)↓","市净率↓","委比↓","总市值↓","流通值↓"};
//    // 换手率 沪深市场
//    private String[] titleNomalTextRate_HS = {"本月涨幅", "本年涨幅", "近一月涨幅", "近一年涨幅", "最新", "换手率", "涨幅", "涨跌", "成交量", "成交额", "量比", "振幅", "最高", "最低", "开盘", "昨收", "市盈率","市盈(静)","市净率","委比","总市值","流通值"};
//    private String[] titleUpTextRate_HS = {"本月涨幅↑", "本年涨幅↑", "近一月涨幅↑", "近一年涨幅↑", "最新↑", "换手率↑", "涨幅↑", "涨跌↑", "成交量↑", "成交额↑", "量比↑", "振幅↑", "最高↑", "最低↑", "开盘↑", "昨收↑","市盈(动)↑","市盈(静)↑","市净率↑","委比↑","总市值↑","流通值↑"};
//    private String[] titleDownTextRate_HS = {"本月涨幅↓", "本年涨幅↓", "近一月涨幅↓", "近一年涨幅↓", "最新↓", "换手率↓", "涨幅↓", "涨跌↓", "成交量↓", "成交额↓", "量比↓", "振幅↓", "最高↓", "最低↓", "开盘↓", "昨收↓","市盈(动)↓","市盈(静)↓","市净率↓","委比↓","总市值↓","流通值↓"};
//    // 成交额榜
//    private String[] titleNomalTextAmount = {"最新", "成交额", "涨幅", "涨跌", "成交量", "换手率", "量比", "振幅", "最高", "最低", "开盘", "昨收", "市盈率","市盈(静)","市净率","委比","总市值","流通值"};
//    private String[] titleUpTextAmount = {"最新↑", "成交额↑", "涨幅↑", "涨跌↑", "成交量↑", "换手率↑", "量比↑", "振幅↑", "最高↑", "最低↑", "开盘↑", "昨收↑", "市盈(动)↑","市盈(静)↑","市净率↑","委比↑","总市值↑","流通值↑"};
//    private String[] titleDownTextAmount = {"最新↓", "成交额↓", "涨幅↓", "涨跌↓", "成交量↓", "换手率↓", "量比↓", "振幅↓", "最高↓", "最低↓", "开盘↓", "昨收↓","市盈(动)↓","市盈(静)↓","市净率↓","委比↓","总市值↓","流通值↓"};
//    // 成交额榜_沪深市场
//    private String[] titleNomalTextAmount_HS = {"本月涨幅", "本年涨幅", "近一月涨幅", "近一年涨幅", "最新", "成交额", "涨幅", "涨跌", "成交量", "换手率", "量比", "振幅", "最高", "最低", "开盘", "昨收", "市盈率","市盈(静)","市净率","委比","总市值","流通值"};
//    private String[] titleUpTextAmount_HS = {"本月涨幅↑", "本年涨幅↑", "近一月涨幅↑", "近一年涨幅↑", "最新↑", "成交额↑", "涨幅↑", "涨跌↑", "成交量↑", "换手率↑", "量比↑", "振幅↑", "最高↑", "最低↑", "开盘↑", "昨收↑", "市盈(动)↑","市盈(静)↑","市净率↑","委比↑","总市值↑","流通值↑"};
//    private String[] titleDownTextAmount_HS = {"本月涨幅↓", "本年涨幅↓", "近一月涨幅↓", "近一年涨幅↓", "最新↓", "成交额↓", "涨幅↓", "涨跌↓", "成交量↓", "换手率↓", "量比↓", "振幅↓", "最高↓", "最低↓", "开盘↓", "昨收↓","市盈(动)↓","市盈(静)↓","市净率↓","委比↓","总市值↓","流通值↓"};

    // 全排序字段（通用）
    private String[] titleAllNomalTextT = {
            "交易状态", "次类别", " 最新价", "最高价", "最低价", "今开价", "昨收价",
            "涨跌比率", "总手", "当前成交量", "换手率", "涨停价", "跌停价", "均价", "涨跌", "成交金额", "量比",
            "外盘量", "内盘量", "总市值", "流通市值", "净资产", "pe(动态市盈率)", "市净率", "总股本",
            "流通股", "振幅比率", "动态每股收益", "沪深委比", "委差",
            "静态市盈率", "本月涨跌幅", "本年涨跌幅", "近一月涨跌幅", "近一年涨跌幅"
    };
    private String[] titleAllUpTextT = {
            "交易状态↑", "次类别↑", " 最新价↑", "最高价↑", "最低价↑", "今开价↑", "昨收价↑",
            "涨跌比率↑", "总手↑", "当前成交量↑", "换手率↑", "涨停价↑", "跌停价↑", "均价↑", "涨跌↑", "成交金额↑", "量比↑",
            "外盘量↑", "内盘量↑", "总市值↑", "流通市值↑", "净资产↑", "pe(动态市盈率)↑", "市净率↑", "总股本↑",
            "流通股↑", "振幅比率↑", "动态每股收益↑", "沪深委比↑", "委差↑",
            "静态市盈率↑", "本月涨跌幅↑", "本年涨跌幅↑", "近一月涨跌幅↑", "近一年涨跌幅↑"
    };
    private String[] titleAllDownTextT = {
            "交易状态↓", "次类别↓", " 最新价↓", "最高价↓", "最低价↓", "今开价↓", "昨收价↓",
            "涨跌比率↓", "总手↓", "当前成交量↓", "换手率↓", "涨停价↓", "跌停价↓", "均价↓", "涨跌↓", "成交金额↓", "量比↓",
            "外盘量↓", "内盘量↓", "总市值↓", "流通市值↓", "净资产↓", "pe(动态市盈率)↓", "市净率↓", "总股本↓",
            "流通股↓", "振幅比率↓", "动态每股收益↓", "沪深委比↓", "委差↓",
            "静态市盈率↓", "本月涨跌幅↓", "本年涨跌幅↓", "近一月涨跌幅↓", "近一年涨跌幅↓"
    };
    // 全排序字段（沪深）（通用+增值指标）（使用：sh1001/sh1002/sh1005;sz1001-1005）
    private String[] titleAllNomalTextTZ = {
            "交易状态", "次类别", " 最新价", "最高价", "最低价", "今开价", "昨收价",
            "涨跌比率", "总手", "当前成交量", "换手率", "涨停价", "跌停价", "均价", "涨跌", "成交金额", "量比",
            "外盘量", "内盘量", "总市值", "流通市值", "净资产", "pe(动态市盈率)", "市净率", "总股本",
            "流通股", "振幅比率", "动态每股收益", "沪深委比", "委差",
            "静态市盈率", "本月涨跌幅", "本年涨跌幅", "近一月涨跌幅", "近一年涨跌幅", "超大单净流入", "大单净流入", "中单净流入", "小单净流入", "大单净差",
            "五日大单净差", "十日大单净差", "主力动向", "五日主力动向", "十日主力动向", "涨跌动因", "五日涨跌动因", "十日涨跌动因", "主力净流入", "五分钟涨跌幅",
            "5日主力资金净流入", "10日主力资金净流入", "20日主力资金净流入", "5日主力资金净流入占比", "10日主力资金净流入占比", "20日主力资金净流入占比"
    };
    private String[] titleAllUpTextTZ = {
            "交易状态↑", "次类别↑", " 最新价↑", "最高价↑", "最低价↑", "今开价↑", "昨收价↑",
            "涨跌比率↑", "总手↑", "当前成交量↑", "换手率↑", "涨停价↑", "跌停价↑", "均价↑", "涨跌↑", "成交金额↑", "量比↑",
            "外盘量↑", "内盘量↑", "总市值↑", "流通市值↑", "净资产↑", "pe(动态市盈率)↑", "市净率↑", "总股本↑",
            "流通股↑", "振幅比率↑", "动态每股收益↑", "沪深委比↑", "委差↑",
            "静态市盈率↑", "本月涨跌幅↑", "本年涨跌幅↑", "近一月涨跌幅↑", "近一年涨跌幅↑", "超大单净流入↑", "大单净流入↑", "中单净流入↑", "小单净流入↑", "大单净差↑",
            "五日大单净差↑", "十日大单净差↑", "主力动向↑", "五日主力动向↑", "十日主力动向↑", "涨跌动因↑", "五日涨跌动因↑", "十日涨跌动因↑", "主力净流入↑", "五分钟涨跌幅↑",
            "5日主力资金净流入↑", "10日主力资金净流入↑", "20日主力资金净流入↑", "5日主力资金净流入占比↑", "10日主力资金净流入占比↑", "20日主力资金净流入占比"
    };
    private String[] titleAllDownTextTZ = {
            "交易状态↓", "次类别↓", " 最新价↓", "最高价↓", "最低价↓", "今开价↓", "昨收价↓",
            "涨跌比率↓", "总手↓", "当前成交量↓", "换手率↓", "涨停价↓", "跌停价↓", "均价↓", "涨跌↓", "成交金额↓", "量比↓",
            "外盘量↓", "内盘量↓", "总市值↓", "流通市值↓", "净资产↓", "pe(动态市盈率)↓", "市净率↓", "总股本↓",
            "流通股↓", "振幅比率↓", "动态每股收益↓", "沪深委比↓", "委差↓",
            "静态市盈率↓", "本月涨跌幅↓", "本年涨跌幅↓", "近一月涨跌幅↓", "近一年涨跌幅↓", "超大单净流入↓", "大单净流入↓", "中单净流入↓", "小单净流入↓", "大单净差↓",
            "五日大单净差↓", "十日大单净差↓", "主力动向↓", "五日主力动向↓", "十日主力动向↓", "涨跌动因↓", "五日涨跌动因↓", "十日涨跌动因↓", "主力净流入↓", "五分钟涨跌幅↓",
            "5日主力资金净流入↓", "10日主力资金净流入↓", "20日主力资金净流入↓", "5日主力资金净流入占比↓", "10日主力资金净流入占比↓", "20日主力资金净流入占比"
    };
    // 全排序字段（沪深-科创版）（通用+增值指标+科创版）（使用：sh1006）
    private String[] titleAllNomalTextTZK = {
            "交易状态", "次类别", " 最新价", "最高价", "最低价", "今开价", "昨收价",
            "涨跌比率", "总手", "当前成交量", "换手率", "涨停价", "跌停价", "均价", "涨跌", "成交金额", "量比",
            "外盘量", "内盘量", "总市值", "流通市值", "净资产", "pe(动态市盈率)", "市净率", "总股本",
            "流通股", "振幅比率", "动态每股收益", "沪深委比", "委差",
            "静态市盈率", "本月涨跌幅", "本年涨跌幅", "近一月涨跌幅", "近一年涨跌幅", "超大单净流入", "大单净流入", "中单净流入", "小单净流入", "大单净差",
            "五日大单净差", "十日大单净差", "主力动向", "五日主力动向", "十日主力动向", "涨跌动因", "五日涨跌动因", "十日涨跌动因", "主力净流入", "五分钟涨跌幅",
            "5日主力资金净流入", "10日主力资金净流入", "20日主力资金净流入", "5日主力资金净流入占比", "10日主力资金净流入占比", "20日主力资金净流入占比",
            "盘后成交量", "盘后成交额"
    };
    private String[] titleAllUpTextTZK = {
            "交易状态↑", "次类别↑", " 最新价↑", "最高价↑", "最低价↑", "今开价↑", "昨收价↑",
            "涨跌比率↑", "总手↑", "当前成交量↑", "换手率↑", "涨停价↑", "跌停价↑", "均价↑", "涨跌↑", "成交金额↑", "量比↑",
            "外盘量↑", "内盘量↑", "总市值↑", "流通市值↑", "净资产↑", "pe(动态市盈率)↑", "市净率↑", "总股本↑",
            "流通股↑", "振幅比率↑", "动态每股收益↑", "沪深委比↑", "委差↑",
            "静态市盈率↑", "本月涨跌幅↑", "本年涨跌幅↑", "近一月涨跌幅↑", "近一年涨跌幅↑", "超大单净流入↑", "大单净流入↑", "中单净流入↑", "小单净流入↑", "大单净差↑",
            "五日大单净差↑", "十日大单净差↑", "主力动向↑", "五日主力动向↑", "十日主力动向↑", "涨跌动因↑", "五日涨跌动因↑", "十日涨跌动因↑", "主力净流入↑", "五分钟涨跌幅↑",
            "5日主力资金净流入↑", "10日主力资金净流入↑", "20日主力资金净流入↑", "5日主力资金净流入占比↑", "10日主力资金净流入占比↑", "20日主力资金净流入占比",
            "盘后成交量↑", "盘后成交额↑"
    };
    private String[] titleAllDownTextTZK = {
            "交易状态↓", "次类别↓", " 最新价↓", "最高价↓", "最低价↓", "今开价↓", "昨收价↓",
            "涨跌比率↓", "总手↓", "当前成交量↓", "换手率↓", "涨停价↓", "跌停价↓", "均价↓", "涨跌↓", "成交金额↓", "量比↓",
            "外盘量↓", "内盘量↓", "总市值↓", "流通市值↓", "净资产↓", "pe(动态市盈率)↓", "市净率↓", "总股本↓",
            "流通股↓", "振幅比率↓", "动态每股收益↓", "沪深委比↓", "委差↓",
            "静态市盈率↓", "本月涨跌幅↓", "本年涨跌幅↓", "近一月涨跌幅↓", "近一年涨跌幅↓", "超大单净流入↓", "大单净流入↓", "中单净流入↓", "小单净流入↓", "大单净差↓",
            "五日大单净差↓", "十日大单净差↓", "主力动向↓", "五日主力动向↓", "十日主力动向↓", "涨跌动因↓", "五日涨跌动因↓", "十日涨跌动因↓", "主力净流入↓", "五分钟涨跌幅↓",
            "5日主力资金净流入↓", "10日主力资金净流入↓", "20日主力资金净流入↓", "5日主力资金净流入占比↓", "10日主力资金净流入占比↓", "20日主力资金净流入占比",
            "盘后成交量↓", "盘后成交额↓"
    };
    // 全排序字段（沪伦通）（通用+沪伦通）
    private String[] titleAllNomalTextHLT = {
            "交易状态", "次类别", " 最新价", "最高价", "最低价", "今开价", "昨收价",
            "涨跌比率", "总手", "当前成交量", "换手率", "涨停价", "跌停价", "均价", "涨跌", "成交金额", "量比",
            "外盘量", "内盘量", "总市值", "流通市值", "净资产", "pe(动态市盈率)", "市净率", "总股本",
            "流通股", "振幅比率", "动态每股收益", "沪深委比", "委差",
            "静态市盈率", "本月涨跌幅", "本年涨跌幅", "近一月涨跌幅", "近一年涨跌幅",
            "当前份额", "前收盘份额"
    };
    private String[] titleAllUpTextHLT = {
            "交易状态↑", "次类别↑", " 最新价↑", "最高价↑", "最低价↑", "今开价↑", "昨收价↑",
            "涨跌比率↑", "总手↑", "当前成交量↑", "换手率↑", "涨停价↑", "跌停价↑", "均价↑", "涨跌↑", "成交金额↑", "量比↑",
            "外盘量↑", "内盘量↑", "总市值↑", "流通市值↑", "净资产↑", "pe(动态市盈率)↑", "市净率↑", "总股本↑",
            "流通股↑", "振幅比率↑", "动态每股收益↑", "沪深委比↑", "委差↑",
            "静态市盈率↑", "本月涨跌幅↑", "本年涨跌幅↑", "近一月涨跌幅↑", "近一年涨跌幅↑",
            "当前份额↑", "前收盘份额↑"
    };
    private String[] titleAllDownTextHLT = {
            "交易状态↓", "次类别↓", " 最新价↓", "最高价↓", "最低价↓", "今开价↓", "昨收价↓",
            "涨跌比率↓", "总手↓", "当前成交量↓", "换手率↓", "涨停价↓", "跌停价↓", "均价↓", "涨跌↓", "成交金额↓", "量比↓",
            "外盘量↓", "内盘量↓", "总市值↓", "流通市值↓", "净资产↓", "pe(动态市盈率)↓", "市净率↓", "总股本↓",
            "流通股↓", "振幅比率↓", "动态每股收益↓", "沪深委比↓", "委差↓",
            "静态市盈率↓", "本月涨跌幅↓", "本年涨跌幅↓", "近一月涨跌幅↓", "近一年涨跌幅↓",
            "当前份额↓", "前收盘份额↓"
    };

    private String[] titleNomalText;
    private String[] titleUpText;
    private String[] titleDownText;

    private TextView[] mTextViews;

    public static final String KEY_STOCK_TYPE = "stocktype";
    public static final String KEY_RANKING_TYPE = "rankingtype";

    private RankingListAllAdapter adapter;
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
        Intent intent = new Intent(context, RankingListAllActivity.class);
        intent.putExtra(KEY_STOCK_TYPE, stockType);
        intent.putExtra(KEY_RANKING_TYPE, rankingType);
        context.startActivity(intent);
    }


    @Override
    protected int getLayoutId() {
        stockType = getIntent().getStringExtra(KEY_STOCK_TYPE);
        rankingType = getIntent().getStringExtra(KEY_RANKING_TYPE);

        int layoutId;
        // 根据不同的排序字段加载不同的布局
        switch (rankingType) {
            case RANKING_TYPE_All_HS:// 全部排序栏位1（沪深）通用+增值指标
                layoutId = R.layout.activity_ranking_raise_sh_all;
                titleNomalText = titleAllNomalTextTZ;
                titleUpText = titleAllUpTextTZ;
                titleDownText = titleAllDownTextTZ;
                break;
            case RANKING_TYPE_All_HS_KCB:// 沪深市场涨幅榜-SH1006 通用+科创版
                layoutId = R.layout.activity_ranking_raise_kcb_all;
                titleNomalText = titleAllNomalTextTZK;
                titleUpText = titleAllUpTextTZK;
                titleDownText = titleAllDownTextTZK;
                break;
            case RANKING_TYPE_All_HLT:// 通用+沪伦通
                layoutId = R.layout.activity_ranking_raise_hlt_all;
                titleNomalText = titleAllNomalTextHLT;
                titleUpText = titleAllUpTextHLT;
                titleDownText = titleAllDownTextHLT;
                break;
            default:
                // 其他，通用
//            case RANKING_TYPE_RAISE_HSSC:// 沪深市场涨幅榜
//            case RANKING_TYPE_All_XSB:// 涨幅榜(全部，通用）（新三板：使用通用栏位，委比使用新三板委比字段）
//            case RANKING_TYPE_RAISE:// 涨幅榜(全部，通用）
//            case RANKING_TYPE_DECLINE:// 跌幅榜
//            case RANKING_TYPE_DECLINE_HSSC:// 跌幅榜-沪深市场
//            case RANKING_TYPE_TURNOVERRATE:// 换手率
//            case RANKING_TYPE_TURNOVERRATE_HSSC:// 换手率-沪深市场
//            case RANKING_TYPE_AMOUNT:// 成交额榜
//            case RANKING_TYPE_AMOUNT_HSSC:// 成交额榜-沪深市场
                layoutId = R.layout.activity_ranking_raise_common_all;
                titleNomalText = titleAllNomalTextT;
                titleUpText = titleAllUpTextT;
                titleDownText = titleAllDownTextT;
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
            case RANKING_TYPE_All_HS:// 沪深市场
                refreshRankingType = RANKING_TYPE_RAISE_CODE;
                // sh1001/sh1002/sh1005;sz1001-1005
                initBindViews();
                sortParams = raiseAllParams_SH;// 排序参数
                status = raise_status_sh_all;// 状态
                mTextViews = new TextView[]{
                        tev_state, tev_subtype,
                        tevLastPrice, tevHighPrice, tevLowPrice, tev_openPrice, tev_preClosePrice,
                        tevChangeRate, tevNowVolume, tev_nowVolume, tevTurnoverRate, tev_limitUp,
                        tev_limitDown, tev_averageValue, tevChange, tevAmount, tevVolumeRatio,
                        tev_buyVolume, tev_sellVolume, tevTotalValue,
                        tevFlowValue, tev_netAsset, tevPe, tevPb_Ip, tev_capitalization,
                        tev_ciShares,
                        tevAmplitudeRate, tev_receipts, tev_orderRatio, tev_etrustDiff,
                        tevPe2, tev_month_rate, tev_year_rate, tev_recent_month, tev_recent_year,
                        tev_ultralargeflow, tev_largeflow, tev_medflow, tev_smallflow, tev_bbd,
                        tev_bbd5, tev_bbd10, tev_ddx, tev_ddx5, tev_ddx10,
                        tev_ddy, tev_ddy5, tev_ddy10, tev_netCapInflow, tev_chg5minutes,
                        tev_mainInflow5, tev_mainInflow10, tev_mainInflow20, tev_rMainInflow5, tev_rMainInflow10,
                        tev_rMainInflow20
                };
                break;
            case RANKING_TYPE_All_HS_KCB:// 沪深市场-科创版
                refreshRankingType = RANKING_TYPE_RAISE_CODE;
                // sh1006
                initBindViewsKCB();
                sortParams = raiseAllParams_SH_KCB;// 排序参数
                status = raise_status_kcb_all;// 状态
                mTextViews = new TextView[]{
                        tev_state, tev_subtype,
                        tevLastPrice, tevHighPrice, tevLowPrice, tev_openPrice, tev_preClosePrice,
                        tevChangeRate, tevNowVolume, tev_nowVolume, tevTurnoverRate, tev_limitUp,
                        tev_limitDown, tev_averageValue, tevChange, tevAmount, tevVolumeRatio,
                        tev_buyVolume, tev_sellVolume, tevTotalValue,
                        tevFlowValue, tev_netAsset, tevPe, tevPb_Ip, tev_capitalization,
                        tev_ciShares,
                        tevAmplitudeRate, tev_receipts, tev_orderRatio, tev_etrustDiff,
                        tevPe2, tev_month_rate, tev_year_rate, tev_recent_month, tev_recent_year,
                        tev_ultralargeflow, tev_largeflow, tev_medflow, tev_smallflow, tev_bbd,
                        tev_bbd5, tev_bbd10, tev_ddx, tev_ddx5, tev_ddx10,
                        tev_ddy, tev_ddy5, tev_ddy10, tev_netCapInflow, tev_chg5minutes,
                        tev_mainInflow5, tev_mainInflow10, tev_mainInflow20, tev_rMainInflow5, tev_rMainInflow10,
                        tev_rMainInflow20, tev_afterVolume, tev_afterAmount
                };
                break;
            case RANKING_TYPE_All_HLT:// 全部-沪伦通
                refreshRankingType = RANKING_TYPE_RAISE_CODE;
                initBindViewsHLT();
                sortParams = raiseAllParams_HLT;// 排序参数
                status = raise_status_hlt_all;// 状态
                mTextViews = new TextView[]{
                        tev_state, tev_subtype,
                        tevLastPrice, tevHighPrice, tevLowPrice, tev_openPrice, tev_preClosePrice,
                        tevChangeRate, tevNowVolume, tev_nowVolume, tevTurnoverRate, tev_limitUp,
                        tev_limitDown, tev_averageValue, tevChange, tevAmount, tevVolumeRatio,
                        tev_buyVolume, tev_sellVolume, tevTotalValue,
                        tevFlowValue, tev_netAsset, tevPe, tevPb_Ip, tev_capitalization,
                        tev_ciShares,
                        tevAmplitudeRate, tev_receipts, tev_orderRatio, tev_etrustDiff,
                        tevPe2, tev_month_rate, tev_year_rate, tev_recent_month, tev_recent_year,
                        tev_drCurrent, tev_drPreClosing
                };
                break;
            case RANKING_TYPE_All_XSB:// 涨幅榜（新三板全部，通用）委比和普通不一样
                initBindViewsCommon();
                refreshRankingType = RANKING_TYPE_RAISE_CODE;
                sortParams = raiseAllParams_XSB;
                status = raise_status_t_all;
                mTextViews = new TextView[]{
                        tev_state, tev_subtype,
                        tevLastPrice, tevHighPrice, tevLowPrice, tev_openPrice, tev_preClosePrice,
                        tevChangeRate, tevNowVolume, tev_nowVolume, tevTurnoverRate, tev_limitUp,
                        tev_limitDown, tev_averageValue, tevChange, tevAmount, tevVolumeRatio,
                        tev_buyVolume, tev_sellVolume, tevTotalValue,
                        tevFlowValue, tev_netAsset, tevPe, tevPb_Ip, tev_capitalization,
                        tev_ciShares,
                        tevAmplitudeRate, tev_receipts, tev_orderRatio, tev_etrustDiff,
                        tevPe2, tev_month_rate, tev_year_rate, tev_recent_month, tev_recent_year};
                break;
            case RANKING_TYPE_RAISE_HSSC:// 沪深市场
            case RANKING_TYPE_RAISE:// 涨幅榜（全部，通用）
                initBindViewsCommon();
                refreshRankingType = RANKING_TYPE_RAISE_CODE;
                sortParams = raiseAllParams_T;
                status = raise_status_t_all;
                mTextViews = new TextView[]{
                        tev_state, tev_subtype,
                        tevLastPrice, tevHighPrice, tevLowPrice, tev_openPrice, tev_preClosePrice,
                        tevChangeRate, tevNowVolume, tev_nowVolume, tevTurnoverRate, tev_limitUp,
                        tev_limitDown, tev_averageValue, tevChange, tevAmount, tevVolumeRatio,
                        tev_buyVolume, tev_sellVolume, tevTotalValue,
                        tevFlowValue, tev_netAsset, tevPe, tevPb_Ip, tev_capitalization,
                        tev_ciShares,
                        tevAmplitudeRate, tev_receipts, tev_orderRatio, tev_etrustDiff,
                        tevPe2, tev_month_rate, tev_year_rate, tev_recent_month, tev_recent_year};
                break;
            case RANKING_TYPE_DECLINE:// 跌幅榜
            case RANKING_TYPE_DECLINE_HSSC:// 跌幅榜-沪深市场
                tevChangeRate.setText("涨跌比率↑");
                initBindViewsCommon();
                refreshRankingType = RANKING_TYPE_DECLINE_CODE;
                sortParams = raiseAllParams_T;
                status = raise_status_t_all;
                mTextViews = new TextView[]{
                        tev_state, tev_subtype,
                        tevLastPrice, tevHighPrice, tevLowPrice, tev_openPrice, tev_preClosePrice,
                        tevChangeRate, tevNowVolume, tev_nowVolume, tevTurnoverRate, tev_limitUp,
                        tev_limitDown, tev_averageValue, tevChange, tevAmount, tevVolumeRatio,
                        tev_buyVolume, tev_sellVolume, tevTotalValue,
                        tevFlowValue, tev_netAsset, tevPe, tevPb_Ip, tev_capitalization,
                        tev_ciShares,
                        tevAmplitudeRate, tev_receipts, tev_orderRatio, tev_etrustDiff,
                        tevPe2, tev_month_rate, tev_year_rate, tev_recent_month, tev_recent_year};
                break;
//            case RANKING_TYPE_DECLINE:// 跌幅榜
//                refreshRankingType = RANKING_TYPE_DECLINE_CODE;
//                sortParams = declineSortParams;
//                status = decline_status;
//                mTextViews = new TextView[]{tevLastPrice, tevChangeRate, tevChange, tevNowVolume,
//                        tevAmount, tevTurnoverRate, tevVolumeRatio, tevAmplitudeRate, tevHighPrice,
//                        tevLowPrice, tevKaipan, tev_preClosePrice, tevPe,tevPe2,tevPb_Ip, tev_orderRatio,tevTotalValue,tevFlowValue};
//                break;
//            case RANKING_TYPE_DECLINE_HSSC:// 跌幅榜-沪深市场
//                refreshRankingType = RANKING_TYPE_DECLINE_CODE;
//                sortParams = declineSortParams_HS;
//                status = decline_status_hs;
//                mTextViews = new TextView[]{
//                        tev_month_rate, tev_year_rate, tev_recent_month, tev_recent_year,
//                        tevLastPrice, tevChangeRate, tevChange, tevNowVolume,
//                        tevAmount, tevTurnoverRate, tevVolumeRatio, tevAmplitudeRate, tevHighPrice,
//                        tevLowPrice, tevKaipan, tev_preClosePrice, tevPe,tevPe2,tevPb_Ip, tev_orderRatio,tevTotalValue,tevFlowValue};
//                break;
            case RANKING_TYPE_TURNOVERRATE:// 换手率
            case RANKING_TYPE_TURNOVERRATE_HSSC:// 换手率-沪深市场
                initBindViewsCommon();
                tevChangeRate.setText("涨跌比率");
                tevChangeRate.setTextColor(ColorUtils.GRAY);
                tevTurnoverRate.setText("换手率↓");
                tevTurnoverRate.setTextColor(ColorUtils.BLUE);
                refreshRankingType = RANKING_TYPE_TURNOVERRATE_CODE;
                sortParams = raiseAllParams_T;
                status = raise_status_t_all;
                mTextViews = new TextView[]{
                        tev_state, tev_subtype,
                        tevLastPrice, tevHighPrice, tevLowPrice, tev_openPrice, tev_preClosePrice,
                        tevChangeRate, tevNowVolume, tev_nowVolume, tevTurnoverRate, tev_limitUp,
                        tev_limitDown, tev_averageValue, tevChange, tevAmount, tevVolumeRatio,
                        tev_buyVolume, tev_sellVolume, tevTotalValue,
                        tevFlowValue, tev_netAsset, tevPe, tevPb_Ip, tev_capitalization,
                        tev_ciShares,
                        tevAmplitudeRate, tev_receipts, tev_orderRatio, tev_etrustDiff,
                        tevPe2, tev_month_rate, tev_year_rate, tev_recent_month, tev_recent_year};
                break;
            case RANKING_TYPE_AMOUNT:// 成交额榜
            case RANKING_TYPE_AMOUNT_HSSC:// 成交额榜-沪深市场
                initBindViewsCommon();
                tevChangeRate.setText("涨跌比率");
                tevChangeRate.setTextColor(ColorUtils.GRAY);
                tevAmount.setText("成交金额↓");
                tevAmount.setTextColor(ColorUtils.BLUE);
                refreshRankingType = RANKING_TYPE_AMOUNT_CODE;
                sortParams = raiseAllParams_T;
                status = raise_status_t_all;
                mTextViews = new TextView[]{
                        tev_state, tev_subtype,
                        tevLastPrice, tevHighPrice, tevLowPrice, tev_openPrice, tev_preClosePrice,
                        tevChangeRate, tevNowVolume, tev_nowVolume, tevTurnoverRate, tev_limitUp,
                        tev_limitDown, tev_averageValue, tevChange, tevAmount, tevVolumeRatio,
                        tev_buyVolume, tev_sellVolume, tevTotalValue,
                        tevFlowValue, tev_netAsset, tevPe, tevPb_Ip, tev_capitalization,
                        tev_ciShares,
                        tevAmplitudeRate, tev_receipts, tev_orderRatio, tev_etrustDiff,
                        tevPe2, tev_month_rate, tev_year_rate, tev_recent_month, tev_recent_year};
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
        adapter = new RankingListAllAdapter(this, rankingType);
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
                        SearchHistoryActivity.startActivity(RankingListAllActivity.this);
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
            StockDetailActivity.startActivity(RankingListAllActivity.this,
                    adapter.getData(),
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
            adapter.setData(quoteItemlist, addValueModels);
        } else {
            ToastUtils.showLongToast("查询结果为空");
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
     */
    @Override
    public void onRequestPullupSuccess(ArrayList<QuoteItem> quoteItemlist, ArrayList<AddValueModel> addValueModels) {
        lsvContent.onRefreshComplete();
        if (quoteItemlist != null && quoteItemlist.size() > 0) {
            currentPage = currentPage + 1;
            adapter.setData(quoteItemlist, addValueModels);
        } else {
            ToastUtils.showLongToast("查询结果为空");
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
            adapter.setData(quoteItemList, addValueModels);
        } else {
            ToastUtils.showLongToast("查询结果为空");
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
    /**
     * 初始化控件(全部排序栏位-通用）
     */
    private void initBindViewsCommon() {
        tev_state = (TextView) findViewById(R.id.tev_state);// 交易状态
        tev_subtype = (TextView) findViewById(R.id.tev_subtype);// 次类别
        tev_openPrice = (TextView) findViewById(R.id.tev_openPrice);// 今开价
        tev_nowVolume = (TextView) findViewById(R.id.tev_nowVolume);// 当前成交量
        tev_limitUp = (TextView) findViewById(R.id.tev_limitUp);// 涨停价
        tev_limitDown = (TextView) findViewById(R.id.tev_limitDown);// 跌停价
        tev_averageValue = (TextView) findViewById(R.id.tev_averageValue);// 均价
        tev_averageValue = (TextView) findViewById(R.id.tev_averageValue);// 均价
        tev_buyVolume = (TextView) findViewById(R.id.tev_buyVolume);// 外盘量
        tev_sellVolume = (TextView) findViewById(R.id.tev_sellVolume);// 内盘量
        tev_netAsset = (TextView) findViewById(R.id.tev_netAsset);// 净资产
        tev_capitalization = (TextView) findViewById(R.id.tev_capitalization);// 总股本
        tev_ciShares = (TextView) findViewById(R.id.tev_ciShares);// 流通股
        tev_receipts = (TextView) findViewById(R.id.tev_receipts);// 动态每股收益
        tev_orderRatio = (TextView) findViewById(R.id.tev_orderRatio);// 沪深委比
        tev_etrustDiff = (TextView) findViewById(R.id.tev_etrustDiff);// 委差
    }
    /**
     * 初始化控件(全部排序栏位-增值指标）
     */
    private void initBindViewsZZZB() {
        tev_ultralargeflow = (TextView) findViewById(R.id.tev_ultralargeflow);// 超大单净流入
        tev_largeflow = (TextView) findViewById(R.id.tev_largeflow);// 大单净流入
        tev_medflow = (TextView) findViewById(R.id.tev_medflow);// 中单净流入
        tev_smallflow = (TextView) findViewById(R.id.tev_smallflow);// 小单净流入
        tev_bbd = (TextView) findViewById(R.id.tev_bbd);// 大单净差
        tev_bbd5 = (TextView) findViewById(R.id.tev_bbd5);// 五日大单净差
        tev_bbd10 = (TextView) findViewById(R.id.tev_bbd10);// 十日大单净差
        tev_ddx = (TextView) findViewById(R.id.tev_ddx);// 主力动向
        tev_ddx5 = (TextView) findViewById(R.id.tev_ddx5);// 五日主力动向
        tev_ddx10 = (TextView) findViewById(R.id.tev_ddx10);// 十日主力动向
        tev_ddy = (TextView) findViewById(R.id.tev_ddy);// 涨跌动因
        tev_ddy5 = (TextView) findViewById(R.id.tev_ddy5);// 五日涨跌动因
        tev_ddy10 = (TextView) findViewById(R.id.tev_ddy10);// 十日涨跌动因
        tev_netCapInflow = (TextView) findViewById(R.id.tev_netCapInflow);// 主力净流入
        tev_chg5minutes = (TextView) findViewById(R.id.tev_chg5minutes);// 五分钟涨跌幅
        tev_mainInflow5 = (TextView) findViewById(R.id.tev_mainInflow5);// 5日主力资金净流入
        tev_mainInflow10 = (TextView) findViewById(R.id.tev_mainInflow10);// 10日主力资金净流入
        tev_mainInflow20 = (TextView) findViewById(R.id.tev_mainInflow20);// 20日主力资金净流入
        tev_rMainInflow5 = (TextView) findViewById(R.id.tev_rMainInflow5);// 5日主力资金净流入占比
        tev_rMainInflow10 = (TextView) findViewById(R.id.tev_rMainInflow10);// 10日主力资金净流入占比
        tev_rMainInflow20 = (TextView) findViewById(R.id.tev_rMainInflow20);// 20日主力资金净流入占比主力动向
    }

    /**
     * 初始化控件(全部排序栏位-通用+增值指标）
     */
    private void initBindViews() {
        initBindViewsCommon();
        initBindViewsZZZB();
    }
    /**
     * 初始化控件(全部排序栏位-通用+增值指标+科创版）
     */
    private void initBindViewsKCB() {
        initBindViews();
        tev_afterVolume = (TextView) findViewById(R.id.tev_afterVolume);
        tev_afterAmount = (TextView) findViewById(R.id.tev_afterAmount);
    }
    /**
     * 初始化控件(全部排序栏位-通用+沪伦通）
     */
    private void initBindViewsHLT() {
        initBindViews();
        tev_drCurrent = (TextView) findViewById(R.id.tev_drCurrent);
        tev_drPreClosing = (TextView) findViewById(R.id.tev_drPreClosing);
    }
}
