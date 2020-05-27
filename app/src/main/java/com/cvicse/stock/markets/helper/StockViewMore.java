package com.cvicse.stock.markets.helper;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cvicse.stock.R;
import com.cvicse.stock.common.ColorUtils;
import com.cvicse.stock.common.FillConfig;
import com.cvicse.stock.util.FormatUtils;
import com.cvicse.stock.utils.TextUtils;
import com.cvicse.stock.utils.UpDownUtils;
import com.cvicse.stock.view.AutofitTextView;
import com.mitake.core.AppInfo;
import com.mitake.core.QuoteItem;
import com.mitake.core.util.FormatUtility;

import java.math.BigDecimal;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 股票头部更多详细
 * Created by liu_zlu on 2017/2/14 16:07
 */
public class StockViewMore {
    //涨停
    @BindView(R.id.tev_daily_limit_value) AutofitTextView tevDailyLimitValue;
    //跌停
    @BindView(R.id.tev_drop_limit_value) AutofitTextView tevDropLimitValue;
    //总股本
    @BindView(R.id.tev_total_share_capital) AutofitTextView tevTotalShareCapital;
    //流通股本
    @BindView(R.id.tev_circulating_capital) AutofitTextView tevCirculatingCapital;
    //昨收
    @BindView(R.id.tev_preclose) AutofitTextView tevPreclose;
    //昨收
    @BindView(R.id.tev_pre) TextView tevPre;
    //内盘
    @BindView(R.id.tev_Internal_disk) AutofitTextView tevInternalDisk;
    //外盘
    @BindView(R.id.tev_external_disk) AutofitTextView tevExternalDisk;
    //总市值
    @BindView(R.id.tev_total_marketvalue) AutofitTextView tevTotalMarketvalue;
    //H股市值
    @BindView(R.id.ll_hvalue) LinearLayout llHvalue;
    @BindView(R.id.tev_total_hkvalue) AutofitTextView tevTotalHKValue;
    //流通市值
    @BindView(R.id.tev_market_capitalization) AutofitTextView tevMarketCapitalization;
    //均价
    @BindView(R.id.tev_average_price) AutofitTextView tevAveragePrice;
    //委比
    @BindView(R.id.tev_commissioned_proportion) AutofitTextView tevCommissionedProportion;
    //量比
    @BindView(R.id.tev_volume_proportion) AutofitTextView tevVolumeProportion;
    //市盈(动)
    @BindView(R.id.tev_market_profit) AutofitTextView tevMarketProfit;
    //振幅
    @BindView(R.id.tev_amplitude) AutofitTextView tevAmplitude;
    // 实际占款天数
    @BindView(R.id.ll_cd) LinearLayout llCd;
    @BindView(R.id.tev_cd) AutofitTextView tevCd;

    // 回购期限
    @BindView(R.id.ll_rp) LinearLayout llRp;
    @BindView(R.id.tev_rp) AutofitTextView tevRp;
    // 可取日期
    @BindView(R.id.ll_cdd) LinearLayout llCdd;
    @BindView(R.id.tev_cdd) AutofitTextView tevCdd;

    // 可用日期
    @BindView(R.id.ll_rpd) LinearLayout llRpd;
    @BindView(R.id.tev_rpd) AutofitTextView tevRpd;

    // 最新价涨跌PB
    @BindView(R.id.ll_changeBP) LinearLayout llChangeBP;
    @BindView(R.id.tev_changeBP) AutofitTextView tevChangeBP;

    // 买入撤单笔数
    @BindView(R.id.ll_bcc) LinearLayout llBcc;
    @BindView(R.id.tev_bcc) AutofitTextView tevBcc;
    // 买入撤单数量
    @BindView(R.id.ll_bcn) LinearLayout llBcn;
    @BindView(R.id.tev_bcn) AutofitTextView tevBcn;
    // 买入撤单金额
    @BindView(R.id.ll_bca) LinearLayout llBca;
    @BindView(R.id.tev_bca) AutofitTextView tevBca;
    // 卖出撤单笔数
    @BindView(R.id.ll_scc) LinearLayout llScc;
    @BindView(R.id.tev_scc) AutofitTextView tevScc;
    // 卖出撤单数量
    @BindView(R.id.ll_scn) LinearLayout llScn;
    @BindView(R.id.tev_scn) AutofitTextView tevScn;
    // 卖出撤单金额
    @BindView(R.id.ll_sca) LinearLayout llSca;
    @BindView(R.id.tev_sca) AutofitTextView tevSca;

    // 昨结算
    @BindView(R.id.ll_preSettlement) LinearLayout llPreSettlement;
    @BindView(R.id.tev_preSettlement) AutofitTextView tevPreSettlement;
    // 持仓量
    @BindView(R.id.ll_openInterest) LinearLayout llOpenInterest;
    @BindView(R.id.tev_openInterest) AutofitTextView tevOpenInterest;
    // 日增
    @BindView(R.id.ll_position_chg) LinearLayout llPositionChg;
    @BindView(R.id.tev_position_chg) AutofitTextView tevPositionChg;
    // 今结算
    @BindView(R.id.ll_settlement) LinearLayout llSettlement;
    @BindView(R.id.tev_settlement) AutofitTextView tevSettlement;
    // 昨虚实度
    @BindView(R.id.ll_preDelta) LinearLayout llPreDelta;
    @BindView(R.id.tev_preDelta) AutofitTextView tevPreDelta;
    //今虚实度
    @BindView(R.id.ll_currDelta) LinearLayout llCurrDelta;
    @BindView(R.id.tev_currDelta) AutofitTextView tevCurrDelta;
    //委差
    @BindView(R.id.tev_entrustDiff) AutofitTextView tevEntrustDiff;

    //仓差
    @BindView(R.id.ll_posDiff) LinearLayout llPosDiff;
    @BindView(R.id.tev_posDiff) AutofitTextView tevPosDiff;

    @BindView(R.id.tev_pe2) AutofitTextView tevPe2;  // 市盈静
    @BindView(R.id.tev_ePRSRP) AutofitTextView tevEPRSRP;  // 报告期
    @BindView(R.id.tev_earningsPerShare) AutofitTextView tevEPerShare;  // 每股收益

    @BindView(R.id.ll_DRCurrentShare) LinearLayout llDRCurrentShare;
    @BindView(R.id.tev_DRCurrentShare) TextView tevDRCurrentShare;  // 当前份额

    @BindView(R.id.ll_DRPreviousClosingShare) LinearLayout llDRPreviousClosingShare;
    @BindView(R.id.tev_DRPreviousClosingShare) TextView tevDRPreviousClosingShare;  // 前收盘份额

    @BindView(R.id.ll_DRConversionBase) LinearLayout llDRConversionBase;
    @BindView(R.id.tev_DRConversionBase) TextView tevDRConversionBase;  // 转换基数

    @BindView(R.id.ll_DRDepositoryInstitutionCode) LinearLayout llDRDepositoryInstitutionCode;
    @BindView(R.id.tev_DRDepositoryInstitutionCode) TextView tevDRDepositoryInstitutionCode;  // 存托机构代码

    @BindView(R.id.ll_DRDepositoryInstitutionName) LinearLayout llDRDepositoryInstitutionName;
    @BindView(R.id.tev_DRDepositoryInstitutionName) TextView tevDRDepositoryInstitutionName;  // 存托机构名称

    @BindView(R.id.ll_DRSubjectClosingReferencePrice) LinearLayout llDRSubjectClosingReferencePrice;
    @BindView(R.id.tev_DRSubjectClosingReferencePrice) TextView tevDRSubjectClosingReferencePrice;  // 标的收盘参考价

    @BindView(R.id.ll_DR) LinearLayout llDR;
    @BindView(R.id.tev_DR) TextView tevDR;  // 沪伦通标识

    @BindView(R.id.ll_DRStockCode) LinearLayout llDRStockCode;
    @BindView(R.id.tev_DRStockCode) TextView tevDRStockCode;  // 基础证券代码

    @BindView(R.id.ll_DRStockName) LinearLayout llDRStockName;
    @BindView(R.id.tev_DRStockName) TextView tevDRStockName;  // 基础证券名称

    @BindView(R.id.ll_DRSecuritiesConversionBase) LinearLayout llDRSecuritiesConversionBase;
    @BindView(R.id.tev_DRSecuritiesConversionBase) TextView tevDRSecuritiesConversionBase;  // 基础证券转换基数

    @BindView(R.id.ll_DRListingDate) LinearLayout llDRListingDate;
    @BindView(R.id.tev_DRListingDate) TextView tevDRListingDate;  // 上市日期

    @BindView(R.id.ll_DRFirstDayReferencePrice) LinearLayout llDRFirstDayReferencePrice;
    @BindView(R.id.tev_DRFirstDayReferencePrice) TextView tevDRFirstDayReferencePrice;  // GDR上市首日参考价

    @BindView(R.id.ll_DRFlowStartDate) LinearLayout llDRFlowStartDate;
    @BindView(R.id.tev_DRFlowStartDate) TextView tevDRFlowStartDate;  // cdr初始流动性生成起始日

    @BindView(R.id.ll_DRFlowEndDate) LinearLayout llDRFlowEndDate;
    @BindView(R.id.tev_DRFlowEndDate) TextView tevDRFlowEndDate;  // cdr初始流动性生成终止日

    //科创板字段
    @BindView(R.id.ll_status) LinearLayout llStatus;
    @BindView(R.id.tev_status) TextView tevStatus;//交易状态

    @BindView(R.id.ll_subscribeUpperLimit) LinearLayout llSubscribeUpperLimit;
    @BindView(R.id.tev_subscribeUpperLimit) TextView tevSubscribeUpperLimit;//市价申报数量上限

    @BindView(R.id.ll_subscribeLowerLimit) LinearLayout llSubscribeLowerLimit;
    @BindView(R.id.tev_subscribeLowerLimit) TextView tevSubscribeLowerLimit;//市价申报数量下限

    @BindView(R.id.ll_afterHoursVolume) LinearLayout llAfterHoursVolume;
    @BindView(R.id.tev_afterHoursVolume) TextView tevAfterHoursVolume;//盘后成交量

    @BindView(R.id.ll_afterHoursAmount) LinearLayout llAfterHoursAmount;
    @BindView(R.id.tev_afterHoursAmount) TextView tevAfterHoursAmount;//盘后成交额

    @BindView(R.id.ll_afterHoursTransactionNumber) LinearLayout llAfterHoursTransactionNumber;
    @BindView(R.id.tev_afterHoursTransactionNumber) TextView tevAfterHoursTransactionNumber;//盘后成交笔数L2

    @BindView(R.id.ll_afterHoursWithdrawBuyCount) LinearLayout llAfterHoursWithdrawBuyCount;
    @BindView(R.id.tev_afterHoursWithdrawBuyCount) TextView tevAfterHoursWithdrawBuyCount;//盘后撤单买笔数L2

    @BindView(R.id.ll_afterHoursWithdrawBuyVolume) LinearLayout llAfterHoursWithdrawBuyVolume;
    @BindView(R.id.tev_afterHoursWithdrawBuyVolume) TextView tevAfterHoursWithdrawBuyVolume;//盘后撤单买数量L2

    @BindView(R.id.ll_afterHoursWithdrawSellCount) LinearLayout llAfterHoursWithdrawSellCount;
    @BindView(R.id.tev_afterHoursWithdrawSellCount) TextView tevAfterHoursWithdrawSellCount;//盘后撤单卖笔数L2

    @BindView(R.id.ll_afterHoursWithdrawSellVolume) LinearLayout llAfterHoursWithdrawSellVolume;
    @BindView(R.id.tev_afterHoursWithdrawSellVolume) TextView tevAfterHoursWithdrawSellVolume;//盘后撤单卖数量L2

    @BindView(R.id.ll_afterHoursBuyVolume) LinearLayout llAfterHoursBuyVolume;
    @BindView(R.id.tev_afterHoursBuyVolume) TextView tevAfterHoursBuyVolume;//盘后委托买入总量L2

    @BindView(R.id.ll_afterHoursSellVolume) LinearLayout llAfterHoursSellVolume;
    @BindView(R.id.tev_afterHoursSellVolume) TextView tevAfterHoursSellVolume;//盘后委托卖出总量L2

    @BindView(R.id.ll_issuedCapital) LinearLayout llIssuedCapital;
    @BindView(R.id.tev_issuedCapital) TextView tevIssuedCapital;//发行资本(注册资本)

    @BindView(R.id.ll_upf) LinearLayout llUpf;
    @BindView(R.id.tev_upf) TextView tevUpf;//盈利状态

    @BindView(R.id.ll_vote) LinearLayout llVote;
    @BindView(R.id.tev_vote) TextView tevVote;//投票权差异

    @BindView(R.id.ll_upDownLimitType) LinearLayout llUpDownLimitType;
    @BindView(R.id.tev_upDownLimitType) TextView tevUpDownLimitType;//涨跌限制标识

    @BindView(R.id.ll_limitPriceUpperLimit) LinearLayout llLimitPriceUpperLimit;
    @BindView(R.id.tev_limitPriceUpperLimit) TextView tevLimitPriceUpperLimit;//限价数量申报上限

    @BindView(R.id.ll_limitPriceLowerLimit) LinearLayout llLimitPriceLowerLimit;
    @BindView(R.id.tev_limitPriceLowerLimit) TextView tevLimitPriceLowerLimit;//限价数量申报下限

    @BindView(R.id.ll_longName) LinearLayout llLongName;
    @BindView(R.id.tev_longName) TextView tevLongName;//中文证券简称长

    //上证期权
    @BindView(R.id.ll_impliedVolatility) LinearLayout llImpliedVolatility;
    @BindView(R.id.tev_impliedVolatility) TextView tevImpliedVolatility;//隐含波动率


    @BindView(R.id.ll_inValue) LinearLayout llInValue;
    @BindView(R.id.tev_inValue) TextView tevInValue;//内在价值

    @BindView(R.id.ll_timeValue) LinearLayout llTimeValue;
    @BindView(R.id.tev_timeValue) TextView tevTimeValue;//时间价值

    @BindView(R.id.ll_leverageRatio) LinearLayout llLeverageRatio;
    @BindView(R.id.tev_leverageRatio) TextView tevLeverageRatio;//杠杆率

    @BindView(R.id.ll_premiumRate) LinearLayout llPremiumRate;
    @BindView(R.id.tev_premiumRate) TextView tevPremiumRate;//溢价率

    @BindView(R.id.ll_exerciseWay) LinearLayout llExerciseWay;
    @BindView(R.id.tev_exerciseWay) TextView tevExerciseWay;//行权方式

    @BindView(R.id.ll_realLeverage) LinearLayout llRealLeverage;
    @BindView(R.id.tev_realLeverage) TextView tevRealLeverage;//真实杠杆

    @BindView(R.id.ll_theoreticalPrice) LinearLayout llTheoreticalPrice;
    @BindView(R.id.tev_theoreticalPrice) TextView tevTheoreticalPrice;//理论价

    @BindView(R.id.ll_delta) LinearLayout llDelta;
    @BindView(R.id.tev_delta) TextView tevDelta;//delta

    @BindView(R.id.ll_gramma) LinearLayout llGramma;
    @BindView(R.id.tev_gramma) TextView tevGramma;//gramma

    @BindView(R.id.ll_theta) LinearLayout llTheta;
    @BindView(R.id.tev_theta) TextView tevTheta;//theta

    @BindView(R.id.ll_rho) LinearLayout llRho;
    @BindView(R.id.tev_rho) TextView tevRho;//rho

    @BindView(R.id.ll_vega) LinearLayout llVega;
    @BindView(R.id.tev_vega) TextView tevVega;//vega



    StockViewMore(View view){
        ButterKnife.bind(this, view);
    }

    protected void setData(QuoteItem quoteItem){
        if( null== quoteItem){
            return;
        }

        if(TextUtils.setText(tevDailyLimitValue,quoteItem.limitUP)){
            UpDownUtils.compare(quoteItem.preClosePrice,quoteItem.limitUP,tevDailyLimitValue);
        }

        if(TextUtils.setText(tevDropLimitValue,quoteItem.limitDown)){
            UpDownUtils.compare(quoteItem.preClosePrice,quoteItem.limitDown,tevDropLimitValue);
        }

        TextUtils.setText(tevTotalShareCapital, FormatUtils.format(quoteItem.capitalization));
        TextUtils.setText(tevCirculatingCapital,FormatUtils.format(quoteItem.circulatingShares));

        TextUtils.setText(tevPreclose, quoteItem.preClosePrice);

        if(TextUtils.setText(tevExternalDisk,FormatUtility.formatVolume(quoteItem.buyVolume))){
            tevExternalDisk.setTextColor(ColorUtils.UP);
        }
        if(TextUtils.setText(tevInternalDisk, FormatUtility.formatVolume(quoteItem.sellVolume))){
            tevInternalDisk.setTextColor(ColorUtils.DOWN);
        }
        //上证期权
        if ("3002".equals(quoteItem.subtype)){
            llImpliedVolatility.setVisibility(View.VISIBLE);
            llInValue.setVisibility(View.VISIBLE);
            llTimeValue.setVisibility(View.VISIBLE);
            llLeverageRatio.setVisibility(View.VISIBLE);
            llPremiumRate.setVisibility(View.VISIBLE);
            llExerciseWay.setVisibility(View.VISIBLE);
            llPreDelta.setVisibility(View.VISIBLE);
            llCurrDelta.setVisibility(View.VISIBLE);
            llRealLeverage.setVisibility(View.VISIBLE);
            llTheoreticalPrice.setVisibility(View.VISIBLE);
            llDelta.setVisibility(View.VISIBLE);
            llTheta.setVisibility(View.VISIBLE);
            llRho.setVisibility(View.VISIBLE);
            llGramma.setVisibility(View.VISIBLE);
            llVega.setVisibility(View.VISIBLE);

            TextUtils.setText(tevImpliedVolatility,FormatUtils.format(quoteItem.impliedVolatility));
            TextUtils.setText(tevInValue,FormatUtils.format(quoteItem.inValue));
            TextUtils.setText(tevTimeValue,FormatUtils.format(quoteItem.timeValue));
            TextUtils.setText(tevLeverageRatio,FormatUtils.format(quoteItem.leverageRatio));
            TextUtils.setText(tevPremiumRate,FormatUtils.format(quoteItem.premiumRate));
            TextUtils.setText(tevExerciseWay,FormatUtils.format(quoteItem.exerciseWay));
            TextUtils.setText(tevPreDelta,FormatUtils.format(quoteItem.preDelta));
            TextUtils.setText(tevCurrDelta,FormatUtils.format(quoteItem.currDelta));
            TextUtils.setText(tevRealLeverage,FormatUtils.format(quoteItem.realLeverage));
            TextUtils.setText(tevTheoreticalPrice,FormatUtils.format(quoteItem.theoreticalPrice));
            TextUtils.setText(tevDelta,FormatUtils.format(quoteItem.delta));
            TextUtils.setText(tevGramma,FormatUtils.format(quoteItem.gramma));
            TextUtils.setText(tevTheta,FormatUtils.format(quoteItem.theta));
            TextUtils.setText(tevRho,FormatUtils.format(quoteItem.rho));
            TextUtils.setText(tevVega,FormatUtils.format(quoteItem.vega));
        }
        //国债逆回购
        if ("1311".equals(quoteItem.subtype)){
            llChangeBP.setVisibility(View.VISIBLE);
            TextUtils.setText(tevChangeBP,FormatUtils.format(quoteItem.changeBP));
        }
        // 债券 1300
        if( "1300".equals(quoteItem.subtype)|| "1311".equals(quoteItem.subtype)){
            llRpd.setVisibility(View.VISIBLE);
            llCdd.setVisibility(View.VISIBLE);
            llRp.setVisibility(View.VISIBLE);
            llCd.setVisibility(View.VISIBLE);

            TextUtils.setText(tevRp,FormatUtils.format(quoteItem.rp));
            TextUtils.setText(tevCd,FormatUtils.format(quoteItem.cd));
            TextUtils.setText(tevRpd,quoteItem.rpd);
            TextUtils.setText(tevCdd,quoteItem.cdd);
        }
        // 沪伦通 1530
        if( "1530".equals(quoteItem.subtype)||"1540".equals(quoteItem.subtype) ){
            llDRCurrentShare.setVisibility(View.VISIBLE);
            llDRPreviousClosingShare.setVisibility(View.VISIBLE);
            llDRConversionBase.setVisibility(View.VISIBLE);
            llDRDepositoryInstitutionCode.setVisibility(View.VISIBLE);
            llDRDepositoryInstitutionName.setVisibility(View.VISIBLE);
            llDRSubjectClosingReferencePrice.setVisibility(View.VISIBLE);
            llDR.setVisibility(View.VISIBLE);
            llDRStockCode.setVisibility(View.VISIBLE);
            llDRStockName.setVisibility(View.VISIBLE);
            llDRSecuritiesConversionBase.setVisibility(View.VISIBLE);
            llDRListingDate.setVisibility(View.VISIBLE);
            llDRFirstDayReferencePrice.setVisibility(View.VISIBLE);
            llDRFlowStartDate.setVisibility(View.VISIBLE);
            llDRFlowEndDate.setVisibility(View.VISIBLE);

            TextUtils.setText(tevDRCurrentShare,FormatUtils.format(quoteItem.DRCurrentShare), FillConfig.DEFALUT);
            TextUtils.setText(tevDRCurrentShare, quoteItem.DRCurrentShare, FillConfig.DEFALUT);
            TextUtils.setText(tevDRPreviousClosingShare, quoteItem.DRPreviousClosingShare, FillConfig.DEFALUT);
            TextUtils.setText(tevDRConversionBase, quoteItem.DRConversionBase, FillConfig.DEFALUT);
            TextUtils.setText(tevDRDepositoryInstitutionCode, quoteItem.DRDepositoryInstitutionCode, FillConfig.DEFALUT);
            TextUtils.setText(tevDRDepositoryInstitutionName, quoteItem.DRDepositoryInstitutionName, FillConfig.DEFALUT);
            TextUtils.setText(tevDRSubjectClosingReferencePrice, quoteItem.DRSubjectClosingReferencePrice, FillConfig.DEFALUT);
            TextUtils.setText(tevDRStockCode, quoteItem.DRStockCode, FillConfig.DEFALUT);
            TextUtils.setText(tevDRStockName, quoteItem.DRStockName, FillConfig.DEFALUT);
            TextUtils.setText(tevDRSecuritiesConversionBase, quoteItem.DRSecuritiesConversionBase, FillConfig.DEFALUT);
            TextUtils.setText(tevDRListingDate, quoteItem.DRListingDate, FillConfig.DEFALUT);
//            TextUtils.setText(tevDRFirstDayReferencePrice, quoteItem.DRFirstDayReferencePrice, FillConfig.DEFALUT);
            TextUtils.setText(tevDR, quoteItem.DR, FillConfig.DEFALUT);
            TextUtils.setText(tevDRFlowStartDate, quoteItem.DRFlowStartDate, FillConfig.DEFALUT);
            TextUtils.setText(tevDRFlowEndDate, quoteItem.DRFlowEndDate, FillConfig.DEFALUT);

        }

        TextUtils.setText(tevTotalMarketvalue,FormatUtils.format(quoteItem.totalValue));
        TextUtils.setText(tevMarketCapitalization,FormatUtils.format(quoteItem.flowValue));

        String averagePrice = FormatUtils.isStandard(quoteItem.averageValue) ? quoteItem.averageValue :getAvPrice(quoteItem);
//        TextUtils.setText(tevAveragePrice, CommonValueFormatter.formatValue(quoteItem,averagePrice),FillConfig.DEFALUT);
        TextUtils.setText(tevAveragePrice,quoteItem.averageValue);

        TextUtils.setText(tevCommissionedProportion,FormatUtils.formatPercent(quoteItem.orderRatio));
        TextUtils.setText(tevVolumeProportion,quoteItem.volumeRatio);
        TextUtils.setText(tevMarketProfit,quoteItem.pe);
        TextUtils.setText(tevAmplitude,FormatUtils.formatPercent(quoteItem.amplitudeRate));

        TextUtils.setText(tevPe2,quoteItem.pe2);
        TextUtils.setText(tevEntrustDiff, quoteItem.entrustDiff);  // 委差
        TextUtils.setText(tevEPerShare,FormatUtils.format(quoteItem.earningsPerShare));// 每股收益
        TextUtils.setText(tevEPRSRP, quoteItem.earningsPerShareReportingPeriod);  // 报告期

        if("2".equals(AppInfo.getInfoLevel()) && ("sh".equals(quoteItem.market) || "sz".equals(quoteItem.market))){
            // 撤单数据
            llBcc.setVisibility(View.VISIBLE);
            llBcn.setVisibility(View.VISIBLE);
            llBca.setVisibility(View.VISIBLE);
            llScc.setVisibility(View.VISIBLE);
            llScn.setVisibility(View.VISIBLE);
            llSca.setVisibility(View.VISIBLE);
            TextUtils.setText(tevBcc,FormatUtils.format(quoteItem.buy_cancel_count));
            TextUtils.setText(tevBcn,FormatUtils.format(quoteItem.buy_cancel_num));
            TextUtils.setText(tevBca,FormatUtils.format(quoteItem.buy_cancel_amount));
            TextUtils.setText(tevScc,FormatUtils.format(quoteItem.sell_cancel_count));
            TextUtils.setText(tevScn,FormatUtils.format(quoteItem.sell_cancel_num));
            TextUtils.setText(tevSca,FormatUtils.format(quoteItem.sell_cancel_amount));
        }
        //科创板
        if ("sh".equals(quoteItem.market)){
            if ("1001".equals(quoteItem.subtype)||"1006".equals(quoteItem.subtype)||"1005".equals(quoteItem.subtype)||"1520".equals(quoteItem.subtype)||"1521".equals(quoteItem.subtype)){
                if ("2".equals(AppInfo.getInfoLevel())){
                    llAfterHoursTransactionNumber.setVisibility(View.VISIBLE);
                    llAfterHoursWithdrawBuyCount.setVisibility(View.VISIBLE);
                    llAfterHoursWithdrawBuyVolume.setVisibility(View.VISIBLE);
                    llAfterHoursWithdrawSellCount.setVisibility(View.VISIBLE);
                    llAfterHoursWithdrawSellVolume.setVisibility(View.VISIBLE);
                    llAfterHoursBuyVolume.setVisibility(View.VISIBLE);
                    llAfterHoursSellVolume.setVisibility(View.VISIBLE);

                    TextUtils.setText(tevAfterHoursTransactionNumber,quoteItem.afterHoursTransactionNumber);
                    TextUtils.setText(tevAfterHoursWithdrawBuyCount,quoteItem.afterHoursWithdrawBuyCount);
                    TextUtils.setText(tevAfterHoursWithdrawBuyVolume,quoteItem.afterHoursWithdrawBuyVolume);
                    TextUtils.setText(tevAfterHoursWithdrawSellCount,quoteItem.afterHoursWithdrawSellCount);
                    TextUtils.setText(tevAfterHoursWithdrawSellVolume,quoteItem.afterHoursWithdrawSellVolume);
                    TextUtils.setText(tevAfterHoursBuyVolume,quoteItem.afterHoursBuyVolume);
                    TextUtils.setText(tevAfterHoursSellVolume,quoteItem.afterHoursSellVolume);
                }
                llStatus.setVisibility(View.VISIBLE);
                llSubscribeUpperLimit.setVisibility(View.VISIBLE);
                llSubscribeLowerLimit.setVisibility(View.VISIBLE);
                llUpDownLimitType.setVisibility(View.VISIBLE);
                llAfterHoursVolume.setVisibility(View.VISIBLE);
                llAfterHoursAmount.setVisibility(View.VISIBLE);
                llVote.setVisibility(View.VISIBLE);
                llUpf.setVisibility(View.VISIBLE);
                llIssuedCapital.setVisibility(View.VISIBLE);
                llDR.setVisibility(View.VISIBLE);
                llLimitPriceUpperLimit.setVisibility(View.VISIBLE);
                llLimitPriceLowerLimit.setVisibility(View.VISIBLE);
                llLongName.setVisibility(View.VISIBLE);

                TextUtils.setText(tevStatus,quoteItem.status);
                TextUtils.setText(tevSubscribeUpperLimit,quoteItem.subscribeUpperLimit);
                TextUtils.setText(tevSubscribeLowerLimit,quoteItem.subscribeLowerLimit);
                TextUtils.setText(tevUpDownLimitType,quoteItem.upDownLimitType);
                TextUtils.setText(tevAfterHoursVolume,quoteItem.afterHoursVolume);
                TextUtils.setText(tevAfterHoursAmount,quoteItem.afterHoursAmount);
                TextUtils.setText(tevVote,quoteItem.vote);
                TextUtils.setText(tevUpf,quoteItem.upf);
                TextUtils.setText(tevIssuedCapital,quoteItem.issuedCapital);
                TextUtils.setText(tevDR,quoteItem.DR);
                llLimitPriceUpperLimit.setVisibility(View.VISIBLE);
                llLimitPriceLowerLimit.setVisibility(View.VISIBLE);
                llLongName.setVisibility(View.VISIBLE);
            }
            llLimitPriceUpperLimit.setVisibility(View.VISIBLE);
            llLimitPriceLowerLimit.setVisibility(View.VISIBLE);
            llLongName.setVisibility(View.VISIBLE);

            llLimitPriceUpperLimit.setVisibility(View.VISIBLE);
            llLimitPriceLowerLimit.setVisibility(View.VISIBLE);
            llLongName.setVisibility(View.VISIBLE);
        }

        // 中金所
        if( "cff".equals(quoteItem.market) ){
            llPreSettlement.setVisibility(View.VISIBLE);
            llOpenInterest.setVisibility(View.VISIBLE);
            llPositionChg.setVisibility(View.VISIBLE);
            llSettlement.setVisibility(View.VISIBLE);
            llPreDelta.setVisibility(View.VISIBLE);
            llCurrDelta.setVisibility(View.VISIBLE);

            llPosDiff.setVisibility(View.VISIBLE);
            TextUtils.setText(tevPreSettlement, quoteItem.preSettlement);
            TextUtils.setText(tevOpenInterest, quoteItem.openInterest);
            TextUtils.setText(tevPositionChg, quoteItem.position_chg);
            TextUtils.setText(tevSettlement, quoteItem.settlement);
            TextUtils.setText(tevPreDelta, quoteItem.preDelta);
            TextUtils.setText(tevCurrDelta, quoteItem.currDelta);
            TextUtils.setText(tevPosDiff, quoteItem.posDiff);
        }
        if ("hk".equals(quoteItem.market)){
            if ("1100".equals(quoteItem.subtype)){
                if (quoteItem.HKTotalValue==null||quoteItem.HKTotalValue.isEmpty()){
                    llHvalue.setVisibility(View.VISIBLE);
                    TextUtils.setText(tevTotalHKValue,"—");
                }else {
                    llHvalue.setVisibility(View.VISIBLE);
                    double hvalue=Float.valueOf(quoteItem.HKTotalValue);
                    if (hvalue>=100000000){
                        hvalue=new BigDecimal(hvalue).divide(new BigDecimal(100000000)).setScale(2,BigDecimal.ROUND_DOWN).doubleValue();
                        TextUtils.setText(tevTotalHKValue,String.valueOf(hvalue)+"亿");
                    }else if(hvalue<100000000&& hvalue>=10000000) {
                        hvalue=new BigDecimal(hvalue).divide(new BigDecimal(10000000)).setScale(2,BigDecimal.ROUND_DOWN).doubleValue();
                        TextUtils.setText(tevTotalHKValue,String.valueOf(hvalue)+"千万");
                    }else if (hvalue>10000000&&hvalue>=10000){
                        hvalue=new BigDecimal(hvalue).divide(new BigDecimal(10000)).setScale(2,BigDecimal.ROUND_DOWN).doubleValue();
                        TextUtils.setText(tevTotalHKValue,String.valueOf(hvalue)+"万");
                    }else {
                        TextUtils.setText(tevTotalHKValue,String.valueOf(hvalue));
                    }
                }
            }else {
                if (quoteItem.HKTotalValue==null||quoteItem.HKTotalValue.isEmpty()){
                    llHvalue.setVisibility(View.VISIBLE);
                    TextUtils.setText(tevTotalHKValue,"—");
                }else {
                    llHvalue.setVisibility(View.VISIBLE);
                    double hvalue=Float.valueOf(quoteItem.HKTotalValue);
                    if (hvalue>=100000000){
                        hvalue=new BigDecimal(hvalue).divide(new BigDecimal(100000000)).setScale(2,BigDecimal.ROUND_DOWN).doubleValue();
                        TextUtils.setText(tevTotalHKValue,String.valueOf(hvalue)+"亿");
                    }else if(hvalue<100000000&& hvalue>=10000000) {
                        hvalue=new BigDecimal(hvalue).divide(new BigDecimal(10000000)).setScale(2,BigDecimal.ROUND_DOWN).doubleValue();
                        TextUtils.setText(tevTotalHKValue,String.valueOf(hvalue)+"千万");
                    }else if (hvalue>10000000&&hvalue>=10000){
                        hvalue=new BigDecimal(hvalue).divide(new BigDecimal(10000)).setScale(2,BigDecimal.ROUND_DOWN).doubleValue();
                        TextUtils.setText(tevTotalHKValue,String.valueOf(hvalue)+"万");
                    }else {
                        TextUtils.setText(tevTotalHKValue,String.valueOf(hvalue));
                    }
                }
                llHvalue.setVisibility(View.VISIBLE);
                String hkTotalValue = quoteItem.HKTotalValue;
                if (null != hkTotalValue && !"".equals(hkTotalValue)) {
                    double hvalue=Float.valueOf(quoteItem.HKTotalValue);
                    if (hvalue>=100000000){
                        hvalue=new BigDecimal(hvalue).divide(new BigDecimal(100000000)).setScale(2,BigDecimal.ROUND_DOWN).doubleValue();
                        TextUtils.setText(tevTotalHKValue,String.valueOf(hvalue)+"亿");
                    }else if(hvalue<100000000&& hvalue>=10000000) {
                        hvalue=new BigDecimal(hvalue).divide(new BigDecimal(10000000)).setScale(2,BigDecimal.ROUND_DOWN).doubleValue();
                        TextUtils.setText(tevTotalHKValue,String.valueOf(hvalue)+"千万");
                    }else if (hvalue>10000000&&hvalue>=10000){
                        hvalue=new BigDecimal(hvalue).divide(new BigDecimal(10000)).setScale(2,BigDecimal.ROUND_DOWN).doubleValue();
                        TextUtils.setText(tevTotalHKValue,String.valueOf(hvalue)+"万");
                    }else {
                        TextUtils.setText(tevTotalHKValue,String.valueOf(hvalue));
                    }
                }
            }
        }else {
            llHvalue.setVisibility(View.GONE);
        }
    }

    private String getAvPrice( QuoteItem quoteItem){
        if( !FormatUtils.isStandard(quoteItem.amount) || !FormatUtils.isStandard(quoteItem.volume) ){
            return FillConfig.DEFALUT;
        }
        double avPrice = (Double.parseDouble(quoteItem.amount) / Double.parseDouble(quoteItem.volume));
        String subtype = quoteItem.subtype;
        String market = quoteItem.market;

        if( "hk".equals(market) || "bj".equals(market) ){
            return String.valueOf(avPrice);
        }
        if(null != subtype && subtype.equals("3002")){
            return String.valueOf(avPrice/1000);
        }
        if(null != subtype && subtype.equals("1300") ){
            return String.valueOf(avPrice/10);
        }
        return String.valueOf(avPrice/100);
    }
}
