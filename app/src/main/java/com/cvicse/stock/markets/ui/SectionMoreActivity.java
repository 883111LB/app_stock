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
import com.cvicse.stock.markets.adapter.SectionMoreAdapter;
import com.cvicse.stock.markets.presenter.contract.SectionMoreConstract;
import com.cvicse.stock.pulltorefresh.PullToRefreshBase;
import com.cvicse.stock.pulltorefresh.PullToRefreshListView;
import com.cvicse.stock.seachstock.SearchHistoryActivity;
import com.cvicse.stock.view.CHScrollView;
import com.cvicse.stock.view.HVListView;
import com.mitake.core.QuoteItem;
import com.mitake.core.bean.log.ErrorInfo;
import com.mitake.core.request.CategoryType;
import com.mitake.core.request.QuoteDetailRequest;
import com.mitake.core.response.Bankuaisorting;
import com.mitake.core.response.IResponseInfoCallback;
import com.mitake.core.response.QuoteResponse;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class SectionMoreActivity extends PBaseActivity implements SectionMoreConstract.View{
    @MVPInject
    SectionMoreConstract.Presenter presenter;

    @BindView(R.id.topbar) ToolBar toolBar;
    @BindView(R.id.ptlst_section) PullToRefreshListView ptlstSection;
    @BindView(R.id.tev_jzf) TextView mTevJzf; //涨跌幅
    @BindView(R.id.tev_ggzf) TextView mTevGgzf;
    @BindView(R.id.tev_zcje) TextView mTevZcje; //总成交额
    @BindView(R.id.tev_ggzfb) TextView mTevGgzfb;  //涨股比
    @BindView(R.id.tev_hsl) TextView mTevHsl;   //换手率
    @BindView(R.id.tev_qzf) TextView mTevQzf; //权涨幅
    @BindView(R.id.tev_zlzjjlr) TextView mTevZlzjjlr; //主力净流入
    @BindView(R.id.tev_zlzjlr) TextView mTevZlzjlr;
    @BindView(R.id.tev_zlzjlc) TextView mTevZlzjlc;
    @BindView(R.id.tev_zlzjjlr5) TextView mTevZlzjjlr5;
    @BindView(R.id.tev_zlzjjlr10) TextView mTevZlzjjlr10;
    @BindView(R.id.scroll_title) CHScrollView mScrollTitle;
    @BindView(R.id.tev_present) TextView mTevPresent;//现手
    @BindView(R.id.tev_totalHand) TextView mTevTotalHand;//总手
    @BindView(R.id.tev_hot) TextView mTevHot;//热度值
    @BindView(R.id.tev_limitUpCount) TextView mTevLimitUpCount;//涨停家数
    @BindView(R.id.tev_limitDownCount) TextView mTevLimitDownCount;//跌停家数

    public final static String TYPE = "TYPE";
    public static final String TRADE_TITLE = "行业板块";
    public static final String NOTION_TITLE = "概念板块";
    public static final String AREA_TITLE = "地域板块";
    public static final String HK_TRADE_TITLE = "港股行业";
    public static final String TRADE_SW_TITLE = "申万行业";
    public static final String TRADE_SZYP_TITLE="优品行业";
    public static final String AREA_SZYP_TITLE="优品地区";
    public static final String NOTION_SZYP_TITLE="优品概念";
    public static final String FZ_TITLE="方正行业";

    public static final String FLAG_FZ="Trade_fz";

    private static final int status_nomal = 0;
    private static final int status_up = 1;
    private static final int status_down = 2;

    private int zjf_status = status_down;
    private int ggzf_status = status_nomal;
    private int zcje_status = status_nomal;
    private int zdjs_status = status_nomal;
    private int hsl_status = status_nomal;
    private int qzf_status = status_nomal;
    private int zlzjjlr_status = status_nomal;
    private int hot_status = status_nomal;
    private int present_status = status_nomal;
    private int totalHand_status = status_nomal;
    private int limitUpCount_status=status_nomal;
    private int limitDownCount_status=status_nomal;

    public static final String NOMAL_REQUEST = "nomal";
    public static final String DOWN_REQUEST = "pulldown";
    public static final String UP_REQUEST = "pullup";

    private String[] titleNomalText = {"均涨幅","领涨幅","总成交额","涨跌家数","换手率","权涨幅","主力净入","主力资入","主力资出","5日主力资净入","10日主力资净入","现手","总手","涨停家数","跌停家数"};
    private String[] titleUpText = {"均涨幅↑","领涨幅↑","总成交额↑","涨跌家数↑","换手率↑","权涨幅↑","主力净入↑","主力资入↑","主力资出↑","5日主力资净入↑","10日主力资净入↑","现手↑","总手↑","涨停家数↑","跌停家数↑"};
    private String[] titleDownText = {"均涨幅↓","领涨幅↓","总成交额↓","涨跌家数↓","换手率↓","权涨幅↓","主力净入↓","主力资入↓","主力资出↓","5日主力资净入↓","10日主力资净入↓","现手↓","总手↓","涨停家数↓","跌停家数↓"};

    private String[] titleNomalText1 = {"均涨幅","领涨幅","总成交额","涨跌家数","换手率","权涨幅","主力净入","主力资入","主力资出","5日主力资净入","10日主力资净入","热度值","现手","总手","涨停家数","跌停家数"};
    private String[] titleUpText1 = {"均涨幅↑","领涨幅↑","总成交额↑","涨跌家数↑","换手率↑","权涨幅↑","主力净入↑","主力资入↑","主力资出↑","5日主力资净入↑","10日主力资净入↑","热度值↑","现手↑","总手↑","涨停家数↑","跌停家数↑"};
    private String[] titleDownText1 = {"均涨幅↓","领涨幅↓","总成交额↓","涨跌家数↓","换手率↓","权涨幅↓","主力净入↓","主力资入↓","主力资出↓","5日主力资净入↓","10日主力资净入↓","热度值↓","现手↓","总手↓","涨停家数↓","跌停家数↓"};

    private String[] jzf_param = {"0,100,jzf,0","0,100,jzf,1"};
    private String[] ggzf_param={"0,100,ggzfb,0","0,100,ggzfb,1"};
    private String[] zcje_param = {"0,100,zcje,0","0,100,zcje,1"};
    private String[] zdjs_param = {"0,100,zdjs,0","0,100,zdjs,1"};
    private String[] hsl_param = {"0,100,hsl,0","0,100,hsl,1"};
    private String[] qzf_param = {"0,100,qzf,0","0,100,qzf,1"};
    private String[] zlzjjlr_param = {"0,100,zlzjjlr,0","0,100,zlzjjlr,1"};
    private String[] zlzjlr_param = {"0,100,zlzjlr,0","0,100,zlzjlr,1"};
    private String[] zlzjlc_param = {"0,100,zlzjlc,0","0,100,zlzjlc,1"};
    private String[] zlzjjlr5_param = {"0,100,zlzjjlr5,0","0,100,zlzjjlr5,1"};
    private String[] zlzjjlr10_param = {"0,100,zlzjjlr10,0","0,100,zlzjjlr10,1"};
    private String[] hot_param = {"0,100,hot,0","0,100,hot,1"};
    private String[] present_param={"0,100,xcjl,0","0,100,xcjl,1"};
    private String[] totalHand_param={"0,100,zcjl,0","0,100,zcjl,1"};
    private String[] limitUpCount_param={"0,100,ztjs,0","0,100,ztjs,1"};
    private String[] limitDownCount_param={"0,100,dtjs,0","0,100,dtjs,1"};

    private int[] status_arr = {zjf_status,ggzf_status, zcje_status,zdjs_status,hsl_status,qzf_status,zlzjjlr_status,zlzjjlr_status,zlzjjlr_status,zlzjjlr_status,zlzjjlr_status,present_status,totalHand_status,limitUpCount_status,limitDownCount_status};
    private String[] [] sortParam = {jzf_param,ggzf_param,zcje_param,zdjs_param,hsl_param, qzf_param,zlzjjlr_param,zlzjlr_param,zlzjlc_param,zlzjjlr5_param,zlzjjlr10_param,present_param,totalHand_param,limitUpCount_param,limitDownCount_param};

    private int[] status_arr1 = {zjf_status,ggzf_status, zcje_status,zdjs_status,hsl_status,qzf_status,zlzjjlr_status,zlzjjlr_status,zlzjjlr_status,zlzjjlr_status,zlzjjlr_status,hot_status,present_status,totalHand_status,limitUpCount_status,limitDownCount_status};
    private String[] [] sortParam1 = {jzf_param,ggzf_param,zcje_param,zdjs_param,hsl_param, qzf_param,zlzjjlr_param,zlzjlr_param,zlzjlc_param,zlzjjlr5_param,zlzjjlr10_param,hot_param,present_param,totalHand_param,limitUpCount_param,limitDownCount_param};
    private String currentRankingParam = jzf_param[0];
    private TextView[] mTextViews;
    private TextView[] mTextViews1;

    private int currentPage = 0;
    public String typeN;

    private SectionMoreAdapter sectionAdapter;

    public static void setActionIntent(Context context, String type) {
        Intent intent = new Intent(context, SectionMoreActivity.class);
        intent.putExtra(TYPE, type);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_section_more;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        typeN = getIntent().getStringExtra(TYPE);

        //设置标题
        switch (typeN) {
            case CategoryType.CATE_TRADE:toolBar.setTitle(TRADE_TITLE);break;
            case CategoryType.CATE_NOTION:toolBar.setTitle(NOTION_TITLE);break;
            case CategoryType.CATE_AREA:toolBar.setTitle(AREA_TITLE);break;
            case CategoryType.HK_TRADE:toolBar.setTitle(HK_TRADE_TITLE);break;
            case CategoryType.TRADE_SW:toolBar.setTitle(TRADE_SW_TITLE);break;
            case CategoryType.TRADE_SZYP:toolBar.setTitle(TRADE_SZYP_TITLE);break;
            case CategoryType.AREA_SZYP:toolBar.setTitle(AREA_SZYP_TITLE);break;
            case CategoryType.NOTION_SZYP:toolBar.setTitle(NOTION_SZYP_TITLE);break;
            case FLAG_FZ:toolBar.setTitle(FZ_TITLE);break;
            default:
                break;
        }

        ((HVListView)ptlstSection.getRefreshableView()).setScrollView(mScrollTitle);
        ptlstSection.setMode(PullToRefreshBase.Mode.BOTH);//两端刷新

        sectionAdapter = new SectionMoreAdapter(this,typeN);
        ptlstSection.setAdapter(sectionAdapter);
        if (typeN.equals("Trade_szyp")||typeN.equals("Area_szyp")||typeN.equals("Notion_szyp")){
            mTevHot.setVisibility(View.VISIBLE);
            addTitleViews1();
        }else {
            mTevHot.setVisibility(View.GONE);
            addTitleViews();
        }
//        addTitleViews();
        initListener();
    }

    private void addTitleViews() {
        mTextViews = new TextView[]{ mTevJzf,mTevGgzf,mTevZcje,mTevGgzfb,mTevHsl,mTevQzf,mTevZlzjjlr,mTevZlzjlr,mTevZlzjlc,mTevZlzjjlr5,mTevZlzjjlr10,mTevPresent,mTevTotalHand,mTevLimitUpCount,mTevLimitDownCount};
//        mTextViews1 = new TextView[]{ mTevJzf,mTevGgzf,mTevZcje,mTevGgzfb,mTevHsl,mTevQzf,mTevZlzjjlr,mTevZlzjlr,mTevZlzjlc,mTevZlzjjlr5,mTevZlzjjlr10,mTevHot,mTevPresent,mTevTotalHand};
        for(int i = 0;i<mTextViews.length;i++){
            final int finalI = i;
            mTextViews[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   convertAllToNomal();
                    currentPage = 0;
                    mTextViews[finalI].setTextColor(ColorUtils.BLUE);
                   switch(status_arr[finalI]){
                       case status_nomal:
                           status_arr[finalI] = status_down;
                           currentRankingParam = sortParam[finalI][0];
                           presenter.request(typeN,sortParam[finalI][0],NOMAL_REQUEST);
                           mTextViews[finalI].setText(titleDownText[finalI]);
                           break;

                       case status_down:
                           status_arr[finalI] = status_up;
                           currentRankingParam = sortParam[finalI][1];
                           presenter.request(typeN,sortParam[finalI][1],NOMAL_REQUEST);
                           mTextViews[finalI].setText(titleUpText[finalI]);
                           break;

                       case status_up:
                           status_arr[finalI] = status_down;
                           currentRankingParam = sortParam[finalI][0];
                           presenter.request(typeN,sortParam[finalI][0],NOMAL_REQUEST);
                           mTextViews[finalI].setText(titleDownText[finalI]);
                           break;

                       default:break;
                   }
                }
            });
        }
    }
    private void addTitleViews1() {
//        mTextViews = new TextView[]{ mTevJzf,mTevGgzf,mTevZcje,mTevGgzfb,mTevHsl,mTevQzf,mTevZlzjjlr,mTevZlzjlr,mTevZlzjlc,mTevZlzjjlr5,mTevZlzjjlr10,mTevPresent,mTevTotalHand};
        mTextViews1 = new TextView[]{ mTevJzf,mTevGgzf,mTevZcje,mTevGgzfb,mTevHsl,mTevQzf,mTevZlzjjlr,mTevZlzjlr,mTevZlzjlc,mTevZlzjjlr5,mTevZlzjjlr10,mTevHot,mTevPresent,mTevTotalHand,mTevLimitUpCount,mTevLimitDownCount};
        for(int i = 0;i<mTextViews1.length;i++){
            final int finalI = i;
            mTextViews1[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    convertAllToNomal1();
                    currentPage = 0;
                    mTextViews1[finalI].setTextColor(ColorUtils.BLUE);
                    switch(status_arr1[finalI]){
                        case status_nomal:
                            status_arr1[finalI] = status_down;
                            currentRankingParam = sortParam1[finalI][0];
                            presenter.request(typeN,sortParam1[finalI][0],NOMAL_REQUEST);
                            mTextViews1[finalI].setText(titleDownText1[finalI]);
                            break;

                        case status_down:
                            status_arr1[finalI] = status_up;
                            currentRankingParam = sortParam1[finalI][1];
                            presenter.request(typeN,sortParam1[finalI][1],NOMAL_REQUEST);
                            mTextViews1[finalI].setText(titleUpText1[finalI]);
                            break;

                        case status_up:
                            status_arr1[finalI] = status_down;
                            currentRankingParam = sortParam1[finalI][0];
                            presenter.request(typeN,sortParam1[finalI][0],NOMAL_REQUEST);
                            mTextViews1[finalI].setText(titleDownText1[finalI]);
                            break;

                        default:break;
                    }
                }
            });
        }
    }
    private void initListener() {
        ptlstSection.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                if(currentPage >=1){
                    //替换字符串中第一个字符,页数改变，下拉刷新，当前页数-1，
                    String[] param = currentRankingParam.split(",");
                    if(param.length == 4){
                        currentRankingParam = (currentPage-1) +","+ param[1]+"," + param[2]+","+ param[3];
                    }
                    presenter.request(typeN,currentRankingParam,DOWN_REQUEST);

                }else{
                    presenter.request(typeN,currentRankingParam,DOWN_REQUEST);
                }
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                String[] param = currentRankingParam.split(",");
                if(param.length == 4){
                    currentRankingParam = currentPage+1 +","+ param[1]+","
                            + param[2]+","+ param[3];
                }
                presenter.request(typeN,currentRankingParam,UP_REQUEST);
            }
        });

        toolBar.setToolBarClickListener(new ToolBar.ToolBarClickListener() {
            @Override
            public void onClick(View view, ToolBar.Type type) {
                switch (type){
                    case RIGHT_TYPE_1:
                        SearchHistoryActivity.startActivity(SectionMoreActivity.this);
                        break;
                }
            }
        });

        ptlstSection.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //顶部有一个刷新的位置，position要减1，
                if(position >=1){
                    Bankuaisorting bankuaisorting = (Bankuaisorting) sectionAdapter.getItem(position -1);
                    if (typeN.equals("Trade_szyp")||typeN.equals("Area_szyp")
                            ||typeN.equals("Notion_szyp") || typeN.equals("HKTrade")) {
                        SectionDetailActivity.newIntent(SectionMoreActivity.this,bankuaisorting.s,bankuaisorting.n);
                    } else {
                        toBkInfo(bankuaisorting.ns);
                    }
                }
            }
        });
    }

    @Override
    protected void initData() {
        presenter.request(typeN,currentRankingParam,NOMAL_REQUEST);
    }

    /**
     * 请求排行榜行业详细数据成功
     * @param industryItemList
     */
    @Override
    public void onSectionSucess(List<Bankuaisorting> industryItemList) {
        sectionAdapter.setData(industryItemList);
        ptlstSection.onRefreshComplete();
    }

    /**
     * 上拉加载
     * @param industryItemList
     */
    @Override
    public void onSectionUpSucess(List<Bankuaisorting> industryItemList) {
        ptlstSection.onRefreshComplete();
        if(industryItemList != null && industryItemList.size()>0){
            currentPage = currentPage + 1;
            sectionAdapter.setData(industryItemList);
        }
    }

    /**
     * 下拉刷新
     */
    @Override
    public void onSectionDownSucess(List<Bankuaisorting> industryItemList) {
        ptlstSection.onRefreshComplete();
        if(industryItemList != null && industryItemList.size()>0) {
            if (currentPage >= 1) {
                currentPage = currentPage - 1;
            } else {
                currentPage = 0;
            }
            sectionAdapter.setData(industryItemList);
        }
    }

    @Override
    public void onRequestFail() {

    }

   private void convertAllToNomal(){
       for(int i=0;i<mTextViews.length;i++){
           mTextViews[i].setText(titleNomalText[i]);
           mTextViews[i].setTextColor(ColorUtils.GRAY);
       }
   }
    private void convertAllToNomal1(){
        for(int i=0;i<mTextViews1.length;i++){
            mTextViews1[i].setText(titleNomalText1[i]);
            mTextViews1[i].setTextColor(ColorUtils.GRAY);
        }
    }

    /**
     * 跳转到详情页
     * @param code 板块代码
     */
    public void toBkInfo(String code) {
        QuoteDetailRequest quoteDetailRequest = new QuoteDetailRequest();
        quoteDetailRequest.send(code, new IResponseInfoCallback<QuoteResponse>()
        {
            public void callback(QuoteResponse quoteResponse)
            {
                ArrayList<QuoteItem> list = quoteResponse.quoteItems;
                StockDetailActivity.startActivity(SectionMoreActivity.this,list,0);
            }

            public void exception(ErrorInfo errorInfo)
            {
                // TODO  错误处理
                ToastUtils.showLongToast("QuoteDetailRequest:" + errorInfo.getMessage());
            }
        });
    }
}
