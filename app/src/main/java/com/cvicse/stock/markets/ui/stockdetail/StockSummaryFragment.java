package com.cvicse.stock.markets.ui.stockdetail;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cvicse.annotations.MVPInject;
import com.cvicse.base.utils.ScreenUtils;
import com.cvicse.stock.R;
import com.cvicse.stock.base.PBaseFragment;
import com.cvicse.stock.markets.data.MarginTradingBo;
import com.cvicse.stock.markets.presenter.marketdetail.contract.StockSummaryContract;
import com.cvicse.stock.utils.TextUtils;
import com.mitake.core.BonusFinance;
import com.mitake.core.ForecastRating;
import com.mitake.core.ForecastRatingItem;
import com.mitake.core.Forecastyear;
import com.mitake.core.Importantnotice;
import com.mitake.core.NewIndex;
import com.mitake.core.QuoteItem;
import com.mitake.core.TradeDetail;
import com.mitake.core.request.F10Type;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;

import static com.cvicse.stock.R.id.tev_bigEvent_content;
import static com.cvicse.stock.R.id.tev_bigEvent_date;

/**
 * 概要页面
 * Created by liu_zlu on 2017/2/9 10:26
 */
public class StockSummaryFragment extends PBaseFragment implements StockSummaryContract.View {
    private static final String STOCKID = "STOCKID";
    /**
     * 列表类型包括新闻、公共、研报
     */
    private static final String TYPE = "TYPE";
    /**
     * 最新指标日期
     */
    @BindView(R.id.tev_indexdate)
    TextView tevIndexdate;
    /**
     * 每股收益
     */
    @BindView(R.id.tev_basiceps)
    TextView tevBasiceps;
    /**
     * 扣非每股收益
     */
    @BindView(R.id.tev_cutbasiceps)
    TextView tevCutbasiceps;
    /**
     * 每股净资产
     */
    @BindView(R.id.tev_bvps)
    TextView tevBvps;
    /**
     * 每股资本公积金
     */
    @BindView(R.id.tev_reserveps)
    TextView tevReserveps;
    /**
     * 每股经营性现金流
     */
    @BindView(R.id.tev_netcashflowoperps)
    TextView tevNetcashflowoperps;
    /**
     * 每股未分配利润
     */
    @BindView(R.id.tev_retainedearningps)
    TextView tevRetainedearningps;
    /**
     * 净资产收益率
     */
    @BindView(R.id.tev_annuroe)
    TextView tevAnnuroe;
    /**
     * 毛利率（％）
     */
    @BindView(R.id.tev_grossprofitmargin)
    TextView tevGrossprofitmargin;
    /**
     * 营业收入
     */
    @BindView(R.id.tev_operrevenue)
    TextView tevOperrevenue;
    /**
     * 营收同比增
     */
    @BindView(R.id.tev_operrevenueyoy)
    TextView tevOperrevenueyoy;
    /**
     * 净利润
     */
    @BindView(R.id.tev_netprofitparentcom)
    TextView tevNetprofitparentcom;
    /**
     * 每股资本公积金
     */
    @BindView(R.id.tev_netprofitparentcomyoy)
    TextView tevNetprofitparentcomyoy;
    /**
     * 净利润同比增
     */
    @BindView(R.id.tev_netprofitcutparentcom)
    TextView tevNetprofitcutparentcom;
    /**
     * 扣非净利润同比增
     */
    @BindView(R.id.tev_netprofitcutparentcomyoy)
    TextView tevNetprofitcutparentcomyoy;
    /**
     * 总股本
     */
    @BindView(R.id.tev_totalshare)
    TextView tevTotalshare;
    /**
     * 流通股份合计
     */
    @BindView(R.id.tev_totalsharel)
    TextView tevTotalsharel;
    /**
     * 分红配送
     */
    @BindView(R.id.lel_dividends_distribution)
    LinearLayout lelDividendsDistribution;
    /**
     * 交易日期
     */
    @BindView(R.id.tev_buydate)
    TextView tevBuydate;
    /**
     * 融资买入额(元)
     */
    @BindView(R.id.tev_buyamountfina)
    TextView tevBuyamountfina;
    /**
     * 融资偿还额(元)
     */
    @BindView(R.id.tev_payamountfina)
    TextView tevPayamountfina;
    /**
     * 融资余额(元)
     */
    @BindView(R.id.tev_amountfina)
    TextView tevAmountfina;
    /**
     * 融券卖出量(股)
     */
    @BindView(R.id.tev_sellvolstock)
    TextView tevSellvolstock;
    /**
     * 融券偿还量(股)
     */
    @BindView(R.id.tev_payvolstock)
    TextView tevPayvolstock;
    /**
     * 融券余额(元)
     */
    @BindView(R.id.tev_amountstock)
    TextView tevAmountstock;
    /**
     * 机构预测日期
     */
    @BindView(R.id.tev_forecastInfoDate)
    TextView tevForecastInfoDate;
    /**
     * 多少家机构预测
     */
    @BindView(R.id.tev_predict)
    TextView tevPredict;
    /**
     * 预测数据
     */
    @BindView(R.id.tev_predict_value)
    TextView tevPredictValue;
    /**
     * 主营收入
     */
    @BindView(R.id.tev_avgcorerevenue)
    TextView tevAvgcorerevenue;
    /**
     * 每股收益
     */
    @BindView(R.id.tev_neteps)
    TextView tevNeteps;
    /**
     * 净利润
     */
    @BindView(R.id.tev_avgprofit)
    TextView tevAvgprofit;
    /**
     * 机构评等日期
     */
    @BindView(R.id.tev_forecastRatingDate)
    TextView tevForecastRatingDate;

    /**
     * 机构评等
     */
    @BindView(R.id.relativeLayoutNoForecastRatingData)
    LinearLayout relativeLayoutNoForecastRatingData;
    /**
     * 机构评等头部
     */
    @BindView(R.id.tev_forecastrating_header)
    TextView tevForecastratingHeader;
    /**
     * 机构评等
     */
    @BindView(R.id.lel_forecastrating_header)
    LinearLayout lelForecastratingHeader;

    @BindView(R.id.abstract4)
    TextView abstract4;
    @BindView(R.id.abstract5)
    TextView abstract5;
    @BindView(R.id.abstract6)
    TextView abstract6;

    @BindView(R.id.abstract22)
    TextView abstract22;
    @BindView(R.id.abstract21)
    TextView abstract21;
    @BindView(R.id.abstract20)
    TextView abstract20;
    @BindView(R.id.abstract19)
    TextView abstract19;

    @BindView(R.id.tev_bigEvent)
    TextView tevBigEvent;   // 大事提醒
    @BindView(R.id.lel_bigEvent)
    LinearLayout lelBigEvent;
    @BindView(R.id.tev_charts)
    TextView tevCharts;  // 龙虎榜
    @BindView(R.id.tev_block_trade)
    TextView tevBlockTrade;  // 大宗交易
    @BindView(R.id.tev_margin_trade)
    TextView tevMarginTrade;    // 融资融券
    @BindView(R.id.tev_intear_active)
    TextView tevIntearActive;    // 董秘问答
    @BindView(R.id.tev_expt_proindic)
    TextView tevExptProindic;    // 年报预披露

    @MVPInject
    StockSummaryContract.Presenter presenter;

    private QuoteItem quoteItem;

    public static StockSummaryFragment newInstance(QuoteItem quoteItem) {
        StockSummaryFragment marketNewsFragment = new StockSummaryFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(STOCKID, quoteItem);
        marketNewsFragment.setArguments(bundle);
        return marketNewsFragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_stock_summary;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        this.quoteItem = getArguments().getParcelable(STOCKID);

        if( !"1".equals(quoteItem.su) && !"1".equals(quoteItem.bu)){
            tevMarginTrade.setEnabled(false);
            tevMarginTrade.setClickable(false);
        }

        // 大事提醒
        tevBigEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StockSummaryBigEventActivity.startActivity(getActivity(), quoteItem.id, quoteItem.name, F10Type.IMPORT_NOTICE_DATA);
            }
        });

        // 龙虎榜
        tevCharts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StockSummaryBigEventActivity.startActivity(getActivity(), quoteItem.id, quoteItem.name, F10Type.D_CHARTS5BUYS);
            }
        });

        // 大宗交易
        tevBlockTrade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StockSummaryBigEventActivity.startActivity(getActivity(), quoteItem.id, quoteItem.name, null);
            }
        });

      // 融资融券
        tevMarginTrade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                StockMarginTradeActivty.actionActiivty(getActivity());
                StockSummaryBigEventActivity.startActivity(getActivity(), quoteItem.id, quoteItem.name, "/finmrgninfoshare");
            }
        });

        // 董秘问答
        tevIntearActive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StockSummaryBigEventActivity.startActivity(getActivity(), quoteItem.id, quoteItem.name, F10Type.NEWS_INTEARACTIVE);
            }
        });
        //年报预披露
        tevExptProindic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //getActivity(),quoteItem.id,quoteItem.name,F10Type.EXPT_PERFORMANCE
                StockExptProindicActivity.startActivity(getActivity(),quoteItem.id,quoteItem.name,F10Type.EXPT_PERFORMANCE);
            }
        });
    }

    @Override
    protected void initData() {
        presenter.init(quoteItem.id);
        presenter.queryMarketSummary();
        presenter.queryImportantNotice(F10Type.IMPORT_NOTICE_DATA);
    }

    /**
     * 获取最新指标成功
     *
     * @param newIndex
     */
    @Override
    public void onNewIndexSuccess(NewIndex newIndex) {
        if (newIndex == null) {
            return;
        }
        TextUtils.setText(tevBasiceps, newIndex.basicEPS);
        TextUtils.setText(tevCutbasiceps, newIndex.cutBasicEPS);
        TextUtils.setText(tevBvps, newIndex.BVPS_);
        TextUtils.setText(tevReserveps, newIndex.reservePS);
        TextUtils.setText(tevNetcashflowoperps, newIndex.netCashFlowOperPS);
        TextUtils.setText(tevRetainedearningps, newIndex.retainedEarningPS);
        TextUtils.setText(tevAnnuroe, newIndex.annuROE);
        TextUtils.setText(tevGrossprofitmargin, newIndex.grossProfitMargin);
        TextUtils.setText(tevOperrevenue, newIndex.operRevenue);
        TextUtils.setText(tevOperrevenueyoy, newIndex.operRevenueYOY);
        TextUtils.setText(tevNetprofitparentcom, newIndex.netProfitParentCom);
        TextUtils.setText(tevNetprofitparentcomyoy, newIndex.netProfitParentComYOY);
        TextUtils.setText(tevNetprofitcutparentcom, newIndex.netProfitCutParentCom);
        TextUtils.setText(tevNetprofitcutparentcomyoy, newIndex.netProfitCutParentComYOY);
        TextUtils.setText(tevTotalshare, newIndex.totalShare);
        TextUtils.setText(tevIndexdate, newIndex.REPTITLE_);

    }

    /**
     * 大事提醒
     *
     * @param infoList
     */
    @Override
    public void onImportantNoticeSuccess(List<HashMap<String, Object>> infoList) {
        if (infoList == null || infoList.isEmpty()) {
            return;
        }
        lelBigEvent.removeAllViews();
        int size = infoList.size() > 2 ? 2 : infoList.size();
        for (int i = 0; i < size; i++) {
            if (null != lelBigEvent) {
                HashMap<String, Object> objectHashMap = infoList.get(i);
                ViewGroup viewGroup = (ViewGroup) layoutInflater.inflate(R.layout.item_lv_big_event, lelBigEvent, false);
                String tradedate = (String) objectHashMap.get("TRADEDATE");
                TextUtils.setText((TextView) viewGroup.findViewById(tev_bigEvent_date), tradedate.substring(5, 10) + "\n" + tradedate.substring(0, 4));
                TextView tevContent = (TextView) viewGroup.findViewById(tev_bigEvent_content);
                TextUtils.setText(tevContent, (String) objectHashMap.get("TEXT"));

                View bigEventV2 = (View) viewGroup.findViewById(R.id.bigEvent_v2);
                bigEventV2.getLayoutParams().height = tevContent.getHeight();
                lelBigEvent.addView(viewGroup);
            }
        }
    }

    /**
     * 获取大事提醒成功
     * @param importantnotices
     */
//    @Override
    public void onImportantNoticeSuccess(ArrayList<Importantnotice> importantnotices) {
        if (importantnotices == null || importantnotices.size() == 0) {
            return;
        }
     /*   lelBigEvent.removeAllViews();
        for(Importantnotice importantnotice:importantnotices){
            if(lelBigEvent != null){
                ViewGroup viewGroup = (ViewGroup) layoutInflater.inflate(R.layout.item_lel_importantnotice,lelBigEvent,false);
                TextUtils.setText((TextView)viewGroup.findViewById(R.id.tev_time),importantnotice.PUBDATE_);
                TextUtils.setText((TextView)viewGroup.findViewById(R.id.tev_detail),importantnotice.diviScheme);
                lelBigEvent.addView(viewGroup);
            }
        }*/
    }

    /**
     * 获取分红配送成功
     *
     * @param bonusFinances
     */
    @Override
    public void onBonusFinanceSuccess(ArrayList<BonusFinance> bonusFinances) {
        if (bonusFinances == null || bonusFinances.size() == 0) {
            abstract4.setVisibility(View.GONE);
            abstract5.setVisibility(View.GONE);
            abstract6.setVisibility(View.GONE);
            lelDividendsDistribution.addView(TextUtils.getNotingTextView(lelDividendsDistribution.getContext(),
                    "暂无此证券分红配送信息"));
            return;
        }
        abstract4.setVisibility(View.VISIBLE);
        abstract5.setVisibility(View.VISIBLE);
        abstract6.setVisibility(View.VISIBLE);
        lelDividendsDistribution.removeAllViews();
        for (BonusFinance bonusFinance : bonusFinances) {
            if (lelDividendsDistribution != null) {
                ViewGroup viewGroup = (ViewGroup) layoutInflater.inflate(R.layout.item_lel_bonusfinance, lelDividendsDistribution, false);
                TextUtils.setText((TextView) viewGroup.findViewById(R.id.tev_date), bonusFinance.NoticeDate);
                TextUtils.setText((TextView) viewGroup.findViewById(R.id.tev_program), bonusFinance.DiviScheme);
                TextUtils.setText((TextView) viewGroup.findViewById(R.id.tev_date_right), bonusFinance.ExDiviDate);
                lelDividendsDistribution.addView(viewGroup);
            }
        }
    }

    /**
     * 获取融资证券成功
     *
     * @param tradeDetail
     */
    @Override
    public void onTradeDetailSuccess(TradeDetail tradeDetail) {
        if (tradeDetail == null) {
            return;
        }
        TextUtils.setText(tevBuydate, tradeDetail.tradingDay);
        TextUtils.setText(tevBuyamountfina, tradeDetail.buyAmountFinance);
        TextUtils.setText(tevPayamountfina, tradeDetail.payAmountFinance);
        TextUtils.setText(tevAmountfina, tradeDetail.amountFinance);
        TextUtils.setText(tevSellvolstock, tradeDetail.sellVolumeStock);
        TextUtils.setText(tevPayvolstock, tradeDetail.payVolumeStock);
        TextUtils.setText(tevAmountstock, tradeDetail.amountStock);
    }

    /**
     * 获取机构预测成功
     *
     * @param forecastyear
     */
    @Override
    public void onForecastyearSuccess(Forecastyear forecastyear) {
        if (forecastyear == null) {
            return;
        }
        TextUtils.setText(tevPredictValue, forecastyear.FORECASTYEAR_ + "年(均值)");
        TextUtils.setText(tevAvgcorerevenue, forecastyear.AVGCOREREVENUE_);
        TextUtils.setText(tevNeteps, forecastyear.NETEPS_);
        TextUtils.setText(tevAvgprofit, forecastyear.AVGPROFIT_);
        TextUtils.setText(tevForecastInfoDate, forecastyear.STATISTICDATE_);
        TextUtils.setText(tevPredict, "最近六个月,共有" + forecastyear.FORECASTCOUNT_ + "家机构对" + quoteItem.name + "业绩做出预测");
    }

    /**
     * 获取机构评等成功
     *
     * @param forecastRating
     */
    @Override
    public void onForecastRatingSuccess(ForecastRating forecastRating) {
        if (forecastRating == null) {
            return;
        }
        ArrayList<ForecastRatingItem> list = forecastRating.list;
        if (relativeLayoutNoForecastRatingData.getChildCount() > 2) {
            relativeLayoutNoForecastRatingData.removeViews(2, relativeLayoutNoForecastRatingData.getChildCount() - 2);
        }
        if (list == null || list.size() == 0) {
            //tevForecastratingHeader
            TextUtils.setText(tevForecastratingHeader, "此证券暂无机构评级");
            lelForecastratingHeader.setVisibility(View.GONE);
            return;
        }
        TextUtils.setText(tevForecastRatingDate, forecastRating.DATETITLE_);
        lelForecastratingHeader.setVisibility(View.VISIBLE);
        HashMap<String, Integer> hashMap = new HashMap<>();
        for (ForecastRatingItem forecastRatingItem : list) {
            String key = forecastRatingItem.INVRATINGDESC_;
            Integer integer = hashMap.get(key);
            if (integer == null) {
                hashMap.put(key, Integer.valueOf(1));
            } else {
                hashMap.put(key, integer++);
            }
            if (relativeLayoutNoForecastRatingData != null) {
                ViewGroup viewGroup = (ViewGroup) layoutInflater.inflate(R.layout.item_lel_forecastrating, relativeLayoutNoForecastRatingData, false);
                //研究机构
                TextUtils.setText((TextView) viewGroup.findViewById(R.id.tev_organization), forecastRatingItem.CHINAMEABBR_);
                //最新评级
                TextUtils.setText((TextView) viewGroup.findViewById(R.id.tev_new_rate), forecastRatingItem.INVRATINGDESC_);
                //上次评级
                TextUtils.setText((TextView) viewGroup.findViewById(R.id.tev_last_rate), forecastRatingItem.LAST_INVRATINGDESC_);
                //评级时间
                TextUtils.setText((TextView) viewGroup.findViewById(R.id.tev_time), forecastRatingItem.WRITINGDATE_);
                relativeLayoutNoForecastRatingData.addView(viewGroup);
            }
        }
        int max = 0;
        String tag = "";
        for (String key : hashMap.keySet()) {
            Integer integer = hashMap.get(key);
            if (max < integer) {
                tag = key;
            }
        }
        TextUtils.setText(tevForecastratingHeader, "最近六个月，共有" + forecastRating.list.size() + "家机构评级为" + tag + "为主");
    }

    @Override
    public void onBlockTradeSuccess(List<HashMap<String, Object>> infoList) {

    }

    @Override
    public void onIntearActiveSuccess(List<HashMap<String, Object>> infoList) {

    }

    @Override
    public void onCharts5BuysSuccess(List<HashMap<String, Object>> buyList) {

    }

    @Override
    public void onCharts5SellsSuccess(List<HashMap<String, Object>> sellsList) {

    }

    @Override
    public void onStockMarginTrade(List<MarginTradingBo> marginTradingBoList) {

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        int screenWidth = ScreenUtils.getScreenWidth();
        int halfWidth = screenWidth >> 1;
        int quaterWidth = halfWidth >> 1;
        abstract4.setWidth(quaterWidth);
        abstract5.setWidth(halfWidth);
        abstract6.setWidth(quaterWidth);

        abstract19.setWidth(quaterWidth - 40);
        abstract20.setWidth(quaterWidth - 40);
        abstract21.setWidth(quaterWidth - 40);
        abstract22.setWidth(quaterWidth - 40);

        tevPredictValue.setWidth(quaterWidth + 40);
        tevAvgcorerevenue.setWidth(quaterWidth + 40);
        tevNeteps.setWidth(quaterWidth + 40);
        tevAvgprofit.setWidth(quaterWidth + 40);
    }
}
