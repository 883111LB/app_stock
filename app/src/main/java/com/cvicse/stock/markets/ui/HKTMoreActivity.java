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
import com.cvicse.stock.markets.presenter.contract.HKTMoreConstract;
import com.cvicse.stock.pulltorefresh.PullToRefreshBase;
import com.cvicse.stock.pulltorefresh.PullToRefreshListView;
import com.cvicse.stock.seachstock.SearchHistoryActivity;
import com.cvicse.stock.view.CHScrollView;
import com.cvicse.stock.view.HVListView;
import com.mitake.core.QuoteItem;

import java.util.ArrayList;

import butterknife.BindView;

import static com.cvicse.stock.markets.data.ParamUtil.RANKING_TYPE_RAISE;

public class HKTMoreActivity extends PBaseActivity implements HKTMoreConstract.View{
    public static String TYPE = "type";

    @BindView(R.id.topbar) ToolBar topbar;
    @BindView(R.id.tev_lastPrice) TextView stockLastPrice;
    @BindView(R.id.tev_changeRate) TextView stockChangeRate;
    @BindView(R.id.tev_change) TextView stockChange;
    @BindView(R.id.tev_volume) TextView stockNowVolume;
    @BindView(R.id.tev_amount) TextView stockAmount;
    @BindView(R.id.tev_turnoverRate) TextView stockTurnoverRate;
    @BindView(R.id.tev_volumeRatio) TextView stockVolumeRatio;
    @BindView(R.id.tev_amplitudeRate) TextView stockAmplitudeRate;
    @BindView(R.id.tev_highPrice) TextView stockHighPrice;
    @BindView(R.id.tev_lowPrice) TextView stockLowPrice;
    @BindView(R.id.tev_kaipan) TextView stockKaipan;
    @BindView(R.id.tev_preClosePrice) TextView stockPreClosePrice;
    @BindView(R.id.tev_pe) TextView stockPe;
    @BindView(R.id.tev_orderRatio) TextView stockOrderRatio;
    @BindView(R.id.tev_totalValue) TextView tevTotalValue;
    @BindView(R.id.tev_flowValue) TextView tevFlowValue;
    @BindView(R.id.scroll_title) CHScrollView itemScrollTitle;
    @BindView(R.id.lsv_content) PullToRefreshListView scrollList;

   /** 适配器*/
    //HKTMoreAdapter hktMoreAdapter;
    private RankingListAdapter adapter;

    @MVPInject
    HKTMoreConstract.Presenter presenter;

    private String[] titleNomalText = {"最新","涨幅","涨跌","成交量","成交额","换手率","量比","振幅","最高", "最低", "开盘","昨收","市盈率","委比","总市值","流通值"};
    private String[] titleUpText = {"最新↑","涨幅↑","涨跌↑","成交量↑","成交额↑","换手率↑","量比↑","振幅↑", "最高↑", "最低↑", "开盘↑","昨收↑","市盈率↑","委比↑","总市值↑","流通值↑"};
    private String[] titleDownText = {"最新↓","涨幅↓","涨跌↓","成交量↓","成交额↓","换手率↓","量比↓","振幅↓", "最高↓", "最低↓", "开盘↓","昨收↓","市盈率↓","委比↓","总市值↓","流通值↓"};

    private final static int TAG_UP = 0; //升序
    private final static int TAG_DOWN = 1;  //降序
    private final static int TAG_NORMAL = 2 ;//不以当前属性排序

    private int status_lastPrice = TAG_NORMAL; //最新价状态
    private int status_changeRate = TAG_DOWN; //涨幅状态,(默认以它降序排序)
    private int status_change = TAG_NORMAL; //涨跌状态
    private int status_nowVolume = TAG_NORMAL; //成交量状态
    private int status_amount = TAG_NORMAL; //成交额状态
    private int status_turnoverRate = TAG_NORMAL; //换手率状态
    private int status_volumeRatio = TAG_NORMAL; //量比状态
    private int status_amplitudeRate = TAG_NORMAL; //振幅状态
    private int status_highPrice = TAG_NORMAL; //最高状态
    private int status_lowPrice = TAG_NORMAL; //最低状态
    private int status_openPrice = TAG_NORMAL; //开盘状态
    private int status_preClosePrice = TAG_NORMAL;//昨收状态
    private int status_pe = TAG_NORMAL; //市盈率
    private int status_ratio = TAG_NORMAL; //委比状态
    private int status_total = TAG_NORMAL; //总市值
    private int status_flow = TAG_NORMAL; //流通值

    /**
     * 排序接口第二个参数是拼接的，依次为页码，笔数，排序栏位，正倒序，是否显示停牌股以逗号分开
     * 正倒序(1倒序，0顺序)，是否显示停牌股(0不显示，1显示)
     */
    // 7为最新价
    private String[] lastPriceParams = {"0,20,7,1,1","0,20,7,0,1"};
    // 8为最高价
    private String[] highPriceParams = {"0,20,8,1,1", "0,20,8,0,1"};
    // 9为最低价
    private String[] lowPriceParams = {"0,20,9,1,1", "0,20,9,0,1"};
    // 10为今开价
    private String[] openPriceParams = {"0,20,10,1,1", "0,20,10,0,1"};
    // 12为涨幅
    private String[] changeRateParams = {"0,20,12,1,1","0,20,12,0,1"};
    // 19为涨跌
    private String[] changeParams = {"0,20,19,1,1","0,20,19,0,1"};
    // 13为成交量
    private String[] nowVolumeParams = {"0,20,13,1,1","0,20,13,0,1"};
    // 20为成交额
    private String[] amountParams = {"0,20,20,1,1","0,20,20,0,1"};
    // 15为换手率
    private String[] turnoverRateParams = {"0,20,15,1,1","0,20,15,0,1"};
    // 21为量比
    private String[] volumeRatioParams = {"0,20,21,1,1","0,20,21,0,1"};
    // 37为振幅
    private String[] amplitudeRateParams = {"0,20,37,1,1","0,20,37,0,1"};
    // 11为昨收
    private String[] preClosePriceRateParams = {"0,20,11,1,1","0,20,11,0,1"};
    // 29为市盈率
    private String[] peRateParams = {"0,20,29,1,1","0,20,29,0,1"};
    // 40为委比
    private String[] ratioParams = {"0,20,40,1,1","0,20,40,0,1"};
    // 26为 总市值
    private String[] totalParams = {"0,20,26,1,1","0,20,26,0,1"};
    // 27为 流通市值
    private String[] flowParams = {"0,20,27,1,1","0,20,27,0,1"};

    // 排序参数
    private String[][] sortParams = new String[][]{
            lastPriceParams,changeRateParams,changeParams,nowVolumeParams,amountParams,
            turnoverRateParams,volumeRatioParams,amplitudeRateParams,highPriceParams,lowPriceParams,openPriceParams,preClosePriceRateParams,
            peRateParams,ratioParams ,totalParams,flowParams};

    // 初始化排序状态
    private int[] status = new int[]{
            status_lastPrice,status_changeRate,status_change,status_nowVolume,status_amount,
            status_turnoverRate,status_volumeRatio,status_amplitudeRate,status_highPrice,status_lowPrice,status_openPrice,status_preClosePrice,
            status_pe,status_ratio,status_total,status_flow };

    private int colorBlue = ColorUtils.BLUE;
    private int colorGray = ColorUtils.GRAY;

    private TextView[] mTextViews;

    //当前的排序参数
    private String currentRankingParam = changeRateParams[0];
    //当前页码
    private int currentPage = 0;

    private String title;

    /**
     * 页面跳转
     */
    public static void startActivity(Context context, String title) {
        Intent intent = new Intent(context, HKTMoreActivity.class);
        intent.putExtra(TYPE, title);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_ranking_raise;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        mTextViews = new TextView[]{ stockLastPrice,stockChangeRate,stockChange,stockNowVolume,stockAmount,
                stockTurnoverRate,stockVolumeRatio,stockAmplitudeRate,stockHighPrice,stockLowPrice,stockKaipan,stockPreClosePrice,
                stockPe,stockOrderRatio ,tevTotalValue,tevFlowValue};

        adapter = new RankingListAdapter(HKTMoreActivity.this,RANKING_TYPE_RAISE);
        scrollList.setAdapter(adapter);
        scrollList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position >= 1){
                    StockDetailActivity.startActivity(HKTMoreActivity.this,
                            (ArrayList<QuoteItem>) adapter.getData(),position - 1);
                }
            }
        });
        
        topbar.setToolBarClickListener(new ToolBar.ToolBarClickListener() {
            @Override
            public void onClick(View view, ToolBar.Type type) {
                switch (type){
                    case RIGHT_TYPE_1:
                        SearchHistoryActivity.startActivity(HKTMoreActivity.this);
                        break;
                }
            }
        });

        for(int i = 0;i<mTextViews.length;i++){
            final int finalI = i;
            mTextViews[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    convertAllToNomal();
                    switch(status[finalI]){
                        case TAG_NORMAL:
                            status[finalI] = TAG_DOWN;
                            currentRankingParam = sortParams[finalI][0];
                            currentPage = 0;
                            presenter.requestRankingData(title,sortParams[finalI][0]);
                            mTextViews[finalI].setText(titleDownText[finalI]);
                            mTextViews[finalI].setTextColor(colorBlue);
                            break;

                        case TAG_DOWN:
                            status[finalI] = TAG_UP;
                            currentRankingParam = sortParams[finalI][1];
                            currentPage = 0;
                            presenter.requestRankingData(title,sortParams[finalI][1]);
                            mTextViews[finalI].setText(titleUpText[finalI]);
                            mTextViews[finalI].setTextColor(colorBlue);
                            break;

                        case TAG_UP:
                            status[finalI] = TAG_DOWN;
                            currentRankingParam = sortParams[finalI][0];
                            currentPage = 0;
                            presenter.requestRankingData(title,sortParams[finalI][0]);
                            mTextViews[finalI].setText(titleDownText[finalI]);
                            mTextViews[finalI].setTextColor(colorBlue);
                            break;

                        default:
                            break;
                    }
                }
            });
        }
        ((HVListView)scrollList.getRefreshableView()).setScrollView(itemScrollTitle);
        scrollList.setMode(PullToRefreshBase.Mode.BOTH);
        scrollList.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                if(currentPage >=1){
                    //替换字符串中第一个字符,页数改变，下拉刷新，当前页数-1，
                    String[] param = currentRankingParam.split(",");
                    if(param.length == 5){
                        currentRankingParam = (currentPage-1) +","+ param[1]+","
                                + param[2]+","+ param[3] + ","+param[4];
                    }
                    presenter.requestPulldownData(title,currentRankingParam);
                }else{
                    presenter.requestPulldownData(title,currentRankingParam);
                }
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                String[] param = currentRankingParam.split(",");
                if(param.length == 5){
                    currentRankingParam = currentPage+1 +","+ param[1]+","
                            + param[2]+","+ param[3] +","+param[4];
                }
                presenter.requestPullUpData(title,currentRankingParam);
            }

        });
    }

    @Override
    protected void initData() {
        //获取标题
        title = getIntent().getStringExtra(TYPE);
        topbar.setTitle(title);

        presenter.requestRankingData(title,currentRankingParam);
    }


    /**
     * 将所有文字转换为非排序状态
     */
    private void convertAllToNomal(){
        for(int i=0;i<mTextViews.length;i++){
            mTextViews[i].setText(titleNomalText[i]);
            mTextViews[i].setTextColor(colorGray);
        }
    }

    /**
     * 请求排行榜数据成功
     *
     * @param list
     */
    @Override
    public void requestRankingDataSuccess(ArrayList<QuoteItem> list) {
        adapter.setData(list);
        scrollList.onRefreshComplete();
    }

    /**
     * 请求排行榜数据失败
     *
     * @param stockType
     * @param param
     */
    @Override
    public void requestRankingDataFail(String stockType, String param) {

    }

    /**
     * 请求下拉刷新数据成功
     *
     * @param list
     */
    @Override
    public void requestPulldownDataSuccess(ArrayList<QuoteItem> list) {
        scrollList.onRefreshComplete();
        if(list != null && list.size()>0) {
            if (currentPage >= 1) {
                currentPage = currentPage - 1;
            } else {
                currentPage = 0;
            }
            adapter.setData(list);
        }
    }

    /**
     * 请求下拉刷新数据失败
     *
     * @param stockType
     * @param param
     */
    @Override
    public void requestPulldownDataFail(String stockType, String param) {

    }

    /**
     * 请求上拉加载数据成功
     *
     * @param list
     */
    @Override
    public void requestPullUpDataSuccess(ArrayList<QuoteItem> list) {
        scrollList.onRefreshComplete();
        if(list != null && list.size()>0){
            currentPage = currentPage + 1;
            adapter.setData(list);
        }
    }

    /**
     * 请求上拉加载数据失败
     *
     * @param stockType
     * @param param
     */
    @Override
    public void requestPullUpDataFail(String stockType, String param) {

    }
}
