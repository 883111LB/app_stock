package com.cvicse.stock.markets.ui.stockdetail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.ViewStub;

import com.cvicse.annotations.MVPInject;
import com.cvicse.stock.R;
import com.cvicse.stock.base.PBaseActivity;
import com.cvicse.stock.base.ToolBar;
import com.cvicse.stock.markets.data.MarginTradingBo;
import com.cvicse.stock.markets.helper.StockBlockTradeHelper;
import com.cvicse.stock.markets.helper.StockChartsBuySellHelper;
import com.cvicse.stock.markets.helper.StockIntearActiveHelper;
import com.cvicse.stock.markets.helper.StockMarginTradeHelper;
import com.cvicse.stock.markets.helper.StockSummaryBigEventHelper;
import com.cvicse.stock.markets.presenter.marketdetail.contract.StockSummaryContract;
import com.mitake.core.BonusFinance;
import com.mitake.core.ForecastRating;
import com.mitake.core.Forecastyear;
import com.mitake.core.NewIndex;
import com.mitake.core.TradeDetail;
import com.mitake.core.request.F10Type;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;


/**
 * 大事提醒(包括大事提醒、龙虎榜、大宗交易、融资融券、董秘问答、年报预披露界面)
 */
public class StockSummaryBigEventActivity extends PBaseActivity implements StockSummaryContract.View {
    @MVPInject
    StockSummaryContract.Presenter presenter;

    @BindView(R.id.topbar)
    ToolBar topbar;
    @BindView(R.id.vs_big_event)
    ViewStub vsBigEvent;    // 大事提醒
    @BindView(R.id.vs_block_trade)
    ViewStub vsBlockTrade;  // 大宗交易
    @BindView(R.id.vs_chart_buy_sell)
    ViewStub vsChartBuySell;  // 龙虎榜
    @BindView(R.id.vs_stock_margintrade)
    ViewStub vsStockMargintrade;  // 融资融券
    @BindView(R.id.vs_stock_intearactive)
    ViewStub vsStockIntearActive;  // 董秘问答

    private String mCode;
    private String mName;
    private String mApiType = F10Type.IMPORT_NOTICE_DATA;
    private boolean flag = false;

    private static final String SUMMARY_CODE = "summary_code";
    private static final String SUMMARY_NAME = "summary_name";
    private static final String SUMMARY_APITYPE = "summary_apiType";

    private StockSummaryBigEventHelper mBigeventhelper; // 大事提醒
    private StockBlockTradeHelper mBlocktradehelper; // 大宗交易
    private StockChartsBuySellHelper mBuySellHelper;  // 龙虎榜
    private StockMarginTradeHelper mMarginTradeHelper;  // 个股融资融券
    private StockIntearActiveHelper mIntearActiveHelper;//董秘问答


    @Override
    protected int getLayoutId() {
        return R.layout.activity_stock_summary_big_event;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        mName = getIntent().getStringExtra(SUMMARY_NAME);
        mCode = getIntent().getStringExtra(SUMMARY_CODE);
        mApiType = getIntent().getStringExtra(SUMMARY_APITYPE);

        if (F10Type.IMPORT_NOTICE_DATA.equals(mApiType) || F10Type.IMPORT_NOTICE_TITLE.equals(mApiType)) {
            topbar.setTitle(mName + " - 大事提醒");
            mBigeventhelper = new StockSummaryBigEventHelper(vsBigEvent.inflate(), this);
        }else if (F10Type.D_CHARTS5SELLS.equals(mApiType) || F10Type.D_CHARTS5BUYS.equals(mApiType)) {
            topbar.setTitle(mName + " - 龙虎榜");
            mBuySellHelper = new StockChartsBuySellHelper(vsChartBuySell.inflate(), this);
        } else if ("/finmrgninfoshare".equals(mApiType)) {
            topbar.setTitle(mName + " - 融资融券");
            mMarginTradeHelper = new StockMarginTradeHelper(vsStockMargintrade.inflate(),this);
        } else if (F10Type.NEWS_INTEARACTIVE.equals(mApiType)){
            topbar.setTitle("董秘问答");
            mIntearActiveHelper=new StockIntearActiveHelper(vsStockIntearActive.inflate(),this,mName);
        }else {
            topbar.setTitle("大宗交易");
            mBlocktradehelper = new StockBlockTradeHelper(vsBlockTrade.inflate(), this, mName);
        }

        // 大事提醒，请求更多
        if (null != mBigeventhelper) {
            mBigeventhelper.setiRequestBigEvent(new IRequestBigEvent() {
                @Override
                public void requestBigEvent(String apitype) {
                    presenter.queryImportantNotice(apitype);
                }

                @Override
                public void loadBigEvent(String apiType) {
                    // 加载更多
                    presenter.loadImportantNotice(apiType);
                }
            });
        }
    }

    @Override
    protected void initData() {
        presenter.init(mCode);
        if (null != mBigeventhelper) {
            presenter.queryImportantNotice(mApiType);
        }else if (null != mBuySellHelper) {
            presenter.requestCharts5BuysSells();
        } else if(null != mBlocktradehelper){
            presenter.requestBlockTrade();
        }else if (null!=mIntearActiveHelper){
            presenter.requestIntearActive();
        }else{
            presenter.requestStockMarginTrade();
        }
    }

    public static void startActivity(Context context, String code, String name, String apiType) {
        Intent intent = new Intent(context, StockSummaryBigEventActivity.class);
        intent.putExtra(SUMMARY_CODE, code);
        intent.putExtra(SUMMARY_NAME, name);
        intent.putExtra(SUMMARY_APITYPE, apiType);
        context.startActivity(intent);
    }

    /**
     * 大事提醒
     * @param infoList
     */
    @Override
    public void onImportantNoticeSuccess(List<HashMap<String, Object>> infoList) {
        if (null != mBigeventhelper) {
            mBigeventhelper.setBigEventData(infoList);
        }
    }

    /**
     * 请求大宗交易
     * @param infoList
     */
    @Override
    public void onBlockTradeSuccess(List<HashMap<String, Object>> infoList) {
        if (null != mBlocktradehelper) {
            mBlocktradehelper.setBlockTradeData(infoList);
        }
    }

    /**
     *董秘问答
     *  @param infoList
     */
    @Override
    public void onIntearActiveSuccess(List<HashMap<String, Object>> infoList) {
        if (null != mIntearActiveHelper) {
            mIntearActiveHelper.setIntearActiveData(infoList);
        }
    }

    /**
     * 龙虎榜-买入前5营业部
     * @param buyList
     */
    @Override
    public void onCharts5BuysSuccess(List<HashMap<String, Object>> buyList) {
        if( null!= mBuySellHelper){
            mBuySellHelper.onCharts5BuysSuccess(buyList);
        }
    }

    /**
     * 龙虎榜-卖出前5营业部
     * @param sellsList
     */
    @Override
    public void onCharts5SellsSuccess(List<HashMap<String, Object>> sellsList) {
        if(null != mBuySellHelper){
            mBuySellHelper.onCharts5SellsSuccess(sellsList);
        }
    }

    /**
     * 个股融资融券数据
     * @param marginTradingBoList
     */
    @Override
    public void onStockMarginTrade(List<MarginTradingBo> marginTradingBoList) {
        if(null != mMarginTradeHelper){
            mMarginTradeHelper .onStockMarginTradeData(marginTradingBoList);
        }
    }

    @Override
    public void onNewIndexSuccess(NewIndex newIndex) {

    }

    @Override
    public void onBonusFinanceSuccess(ArrayList<BonusFinance> bonusFinances) {

    }

    @Override
    public void onTradeDetailSuccess(TradeDetail tradeDetail) {

    }

    @Override
    public void onForecastyearSuccess(Forecastyear forecastyear) {

    }

    @Override
    public void onForecastRatingSuccess(ForecastRating forecastRating) {

    }

    public interface IRequestBigEvent {
        void requestBigEvent(String apitype);

        void loadBigEvent(String apiType);
    }
}
