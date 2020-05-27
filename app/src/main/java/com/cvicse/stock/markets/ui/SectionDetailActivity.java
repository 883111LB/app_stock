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
import com.cvicse.stock.markets.presenter.contract.SectionDetailContract;
import com.cvicse.stock.pulltorefresh.PullToRefreshBase;
import com.cvicse.stock.pulltorefresh.PullToRefreshListView;
import com.cvicse.stock.seachstock.SearchHistoryActivity;
import com.cvicse.stock.view.CHScrollView;
import com.cvicse.stock.view.HVListView;
import com.mitake.core.QuoteItem;

import java.util.ArrayList;

import butterknife.BindView;

import static com.cvicse.stock.markets.data.ParamUtil.RANKING_TYPE_RAISE;

/**
 * 板块详情
 */
public class SectionDetailActivity extends PBaseActivity implements SectionDetailContract.View {

    @BindView(R.id.topbar) ToolBar mTopBar;
    @BindView(R.id.scroll_title)  CHScrollView scrollTitle;
    @BindView(R.id.lsv_content) PullToRefreshListView scrollList;
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

    // 下拉刷新,上拉加载，rankingType参数
    private String refreshRankingType;

    private int currentPage = 0;

    @MVPInject
    SectionDetailContract.Presenter mPresenter;

    private RankingListAdapter adapter;

    public static final String STOCK_ID = "Stock_id";
    public static final String BANKKUAI_NAME = "bankuai_name";

    private String stockId;
    private String bankuaiName;

    private final static int TAG_UP = 0 ; //升序

    private final static int TAG_DOWN =1;  //降序

    private final static int TAG_NORMAL =2 ;//不以当前属性排序

    private int status_lastPrice = TAG_NORMAL; //最新价状态
    private int status_changeRate = TAG_DOWN; //涨幅状态，默认以它降序排序
    private int status_change = TAG_NORMAL; //涨跌状态
    private int status_NowVolume = TAG_NORMAL; //成交量状态
    private int status_amount = TAG_NORMAL; //成交额状态
    private int status_turnoverRate = TAG_NORMAL; //换手率状态
    private int status_volumeRatio = TAG_NORMAL; //量比状态
    private int status_amplitudeRate = TAG_NORMAL; //振幅状态
    private int status_preClosePrice = TAG_NORMAL; //昨收状态
    private int status_pe = TAG_NORMAL; //市盈率动
    private int status_pe2 = TAG_NORMAL; //市盈静
    private int status_Ratio = TAG_NORMAL; //委比状态
    private int status_total = TAG_NORMAL; //总市值
    private int status_flow = TAG_NORMAL; //流通值
    private int status_fund = TAG_NORMAL; //主力净流入状态

    /**
     * 排序接口第二个参数是拼接的，依次为页码，笔数，排序栏位，正倒序，是否显示停牌股以逗号分开
     */
    //7为最新价
    private String[] lastPriceParams = {"0,20,7,1,1","0,20,7,0,1"};
    // 12为涨幅
    private String[] changeRateParams = {"0,20,12,1,1","0,20,12,0,1"};
    // 19为涨跌
    private String[] changeParams = {"0,20,19,1,1","0,20,19,0,1"};
    // 13为成交量
    private String[] nowVolumeParams = {"0,20,13,1,1","0,20,13,0,1"};
    //20为成交额
    private String[] amountParams = {"0,20,20,1,1","0,20,20,0,1"};
    // 15为换手率
    private String[] turnoverRateParams = {"0,20,15,1,1","0,20,15,0,1"};
    // 21为量比
    private String[] volumeRatioParams = {"0,20,21,1,1","0,20,21,0,1"};
    // 37为振幅
    private String[] amplitudeRateParams = {"0,20,37,1,1","0,20,37,0,1"};
    // 11为昨收
    private String[] preClosePriceRateParams = {"0,20,11,1,1","0,20,11,0,1"};
    // 29位市盈率动
    private String[] peRateParams = {"0,20,29,1,1","0,20,29,0,1"};
    // 42位市盈率静
    private String[] pe2RateParams = {"0,20,42,1,1","0,20,42,0,1"};
    // 40为委比
    private String[] ratioParams = {"0,20,40,1,1","0,20,40,0,1"};
    // 26为 总市值
    private String[] totalParams = {"0,20,26,1,1","0,20,26,0,1"};
    // 27为 流通市值
    private String[] flowParams = {"0,20,27,1,1","0,20,27,0,1"};
    // -47为主力净流入
    private String[] fundsParams = { "0,20,-47,1,1","0,20,-47,0,1"};

    private String[][] sortParams = new String[][]{
            lastPriceParams,changeRateParams,changeParams,nowVolumeParams,amountParams,
            turnoverRateParams,volumeRatioParams,amplitudeRateParams,preClosePriceRateParams,
            peRateParams,pe2RateParams,ratioParams,totalParams,flowParams
    };

    private int[] status = new int[]{
            status_lastPrice,status_changeRate,status_change,status_NowVolume,status_amount,
            status_turnoverRate,status_volumeRatio,status_amplitudeRate,status_preClosePrice,
            status_pe,status_pe2,status_Ratio,status_total,status_flow
    };

    private String[] titleNomalText = {"最新","涨幅","涨跌","成交量","成交额","换手率","量比","振幅","昨收","市盈(动)","市盈(静)","委比","总市值","流通值"};
    private String[] titleUpText = {"最新↑","涨幅↑","涨跌↑","成交量↑","成交额↑","换手率↑","量比↑","振幅↑","昨收↑","市盈(动)↑","市盈(静)↑","委比↑","总市值↑","流通值↑"};
    private String[] titleDownText = {"最新↓","涨幅↓","涨跌↓","成交量↓","成交额↓","换手率↓","量比↓","振幅↓","昨收↓","市盈(动)↓","市盈(静)↓","委比↓","总市值↓","流通值↓"};

    private TextView[] mTextViews;

    public static void newIntent(Context context, String stockId,String banKuaiName) {
        Intent intent = new Intent(context, SectionDetailActivity.class);
        intent.putExtra(STOCK_ID, stockId);
        intent.putExtra(BANKKUAI_NAME, banKuaiName);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_ranking_raise;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        refreshRankingType = "0,20,12,1,1";
        ((HVListView)scrollList.getRefreshableView()).setScrollView(scrollTitle);

        adapter = new RankingListAdapter(SectionDetailActivity.this,RANKING_TYPE_RAISE);
        scrollList.setAdapter(adapter);

        scrollList.setMode(PullToRefreshBase.Mode.BOTH);
        scrollList.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                if(currentPage >=1){
                    //替换字符串中第一个字符,页数改变，下拉刷新，当前页数-1，
                    String[] param = refreshRankingType.split(",");
                    if(param.length == 5){
                        refreshRankingType = (currentPage-1) +","+ param[1]+","
                                + param[2]+","+ param[3]+"," +param[4];
                    }
                    mPresenter.requestPulldownData(stockId,refreshRankingType);

                }else{
                    mPresenter.requestPulldownData(stockId,refreshRankingType);
                }
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                    //替换字符串中第一个字符,页数改变，上拉加载，当前页数+1，
                    String[] param = refreshRankingType.split(",");
                    if(param.length == 5){
                        refreshRankingType = currentPage+1 +","+ param[1]+","
                                + param[2]+","+ param[3]+"," +param[4];
                    }
                    mPresenter.requestPullupData(stockId,refreshRankingType);
            }
        });

        mTopBar.setToolBarClickListener(new ToolBar.ToolBarClickListener() {
            @Override
            public void onClick(View view, ToolBar.Type type) {
                switch (type){
                    case RIGHT_TYPE_1:
                        SearchHistoryActivity.startActivity(SectionDetailActivity.this);
                        break;
                }
            }
        });

        scrollList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position >= 1){
                    StockDetailActivity.startActivity(SectionDetailActivity.this, (ArrayList<QuoteItem>) adapter.getData(),position - 1);
                }
            }
        });

        mTextViews = new TextView[]{
                tevLastPrice, tevChangeRate, tevChange, tevNowVolume,
                tevAmount, tevTurnoverRate, tevVolumeRatio, tevAmplitudeRate,tev_preClosePrice,
                tevPe,tevPe2, tevOrderRatio,tevTotalValue,tevFlowValue
        };

       for(int i=0;i<mTextViews.length;i++){
            final int finalI = i;
           mTextViews[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setAllViewNormal();
                    if(TAG_NORMAL==status[finalI]){  //如果本来是普通状态改为降序排序
                        status[finalI] =TAG_DOWN;
                        refreshRankingType = sortParams[finalI][0];
                        currentPage = 0;
                        mPresenter.request(stockId,sortParams[finalI][0]);
                        mTextViews[finalI].setText(titleDownText[finalI]);
                        mTextViews[finalI].setTextColor(ColorUtils.BLUE);
                    }else if(TAG_DOWN==status[finalI]){  //降序排序的情况
                        status[finalI] =TAG_UP;
                        refreshRankingType = sortParams[finalI][1];
                        currentPage = 0;
                        mPresenter.request(stockId,sortParams[finalI][1]);
                        mTextViews[finalI].setText(titleUpText[finalI]);
                        mTextViews[finalI].setTextColor(ColorUtils.BLUE);
                    }else {                       //降序排序变为程序排序
                        status[finalI] =TAG_DOWN;
                        refreshRankingType = sortParams[finalI][0];
                        currentPage = 0;
                        mPresenter.request(stockId,sortParams[finalI][0]);
                        mTextViews[finalI].setText(titleDownText[finalI]);
                        mTextViews[finalI].setTextColor(ColorUtils.BLUE);
                    }
                }
            });
        }
    }

    @Override
    protected void initData() {

        stockId = getIntent().getStringExtra(STOCK_ID);
        bankuaiName = getIntent().getStringExtra(BANKKUAI_NAME);
        mTopBar.setTitle(bankuaiName);

        mPresenter.request(stockId, "0,20,12,1,1");
    }

    /**
     * 请求成功
     * @param quoteItemList
     */
    @Override
    public void onRequestSuccess(ArrayList<QuoteItem> quoteItemList) {
        adapter.setData(quoteItemList);
    }

    /**
     * 请求失败
     */
    @Override
    public void onRequestFail() {

    }

    /**
     * 下拉刷新请求成功
     *
     * @param quoteItemList
     */
    @Override
    public void onRequestPulldownSuccess(ArrayList<QuoteItem> quoteItemList) {
        scrollList.onRefreshComplete();
        if(quoteItemList != null && quoteItemList.size()>0){
            if(currentPage >=1){
                currentPage = currentPage - 1;
            }else{
                currentPage = 0;
            }
            adapter.setData(quoteItemList);
        }
    }

    /**
     * 下拉刷新请求失败
     */
    @Override
    public void onRequestPulldownFail() {

    }

    /**
     * 上拉加载请求成功
     *
     * @param quoteItemList
     */
    @Override
    public void onRequestPullupSuccess(ArrayList<QuoteItem> quoteItemList) {
        scrollList.onRefreshComplete();
        if(quoteItemList != null && quoteItemList.size()>0){
            currentPage = currentPage+1;
            adapter.setData(quoteItemList);
        }

    }

    /**
     * 上拉加载请求失败
     */
    @Override
    public void onRequestPullupFail() {

    }

    /**
     * 将所有控件转化为不按它排行状态
     */
   private void setAllViewNormal(){
       for(int i=0;i<mTextViews.length;i++){
           mTextViews[i].setText(titleNomalText[i]);
           mTextViews[i].setTextColor(ColorUtils.GRAY);
       }
    }
}
