package com.cvicse.stock.markets.ui.yaoyue;

import android.app.Activity;
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
import com.cvicse.stock.markets.ui.yaoyue.adapter.YaoyueListAdapter;
import com.cvicse.stock.markets.ui.yaoyue.presenter.contract.YaoyueContract;
import com.cvicse.stock.markets.ui.yaoyue.presenter.contract.YaoyueInfoContract;
import com.cvicse.stock.pulltorefresh.PullToRefreshBase;
import com.cvicse.stock.pulltorefresh.PullToRefreshListView;
import com.cvicse.stock.seachstock.SearchHistoryActivity;
import com.cvicse.stock.view.CHScrollView;
import com.cvicse.stock.view.HVListView;
import com.mitake.core.bean.offer.OfferQuoteBean;
import com.mitake.core.request.offer.OfferQuoteSort;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * 要约页面（详情页）
 * Created by tang_h on 2020-2-27.
 */
public class YYInfoActivity extends PBaseActivity implements YaoyueInfoContract.View{

    @MVPInject
    YaoyueInfoContract.Presenter presenter;

    @BindView(R.id.topbar) ToolBar toolBar;
    @BindView(R.id.scroll_title) CHScrollView scrollTitle;
    @BindView(R.id.lsv_content) PullToRefreshListView lsvContent;
    @BindView(R.id.tev_name) TextView tev_name;// 证券名称
    @BindView(R.id.tev_offer_id) TextView tev_offer_id;// 收购编码
    @BindView(R.id.tev_offer_name) TextView tev_offer_name;// 收购人名称
    @BindView(R.id.tev_price) TextView tev_price;// 收购价格
    @BindView(R.id.tev_start_date) TextView tev_start_date;// 收购起始日
    @BindView(R.id.tev_end_date) TextView tev_end_date;// 收购截止日

    private final static int TAG_UP = 0; //升序
    private final static int TAG_DOWN = 1;  //降序
    private final static int TAG_NORMAL = 2;//不以当前属性排序

    private String[] titleNomalTextRaise = {"证券名称", "收购编码", "收购人名称", "收购价格", "收购起始日", "收购截止日"};
    private String[] titleUpTextRaise = {"证券名称", "收购编码↑", "收购人名称↑", "收购价格↑", "收购起始日↑", "收购截止日↑"};
    private String[] titleDownTextRaise = {"证券名称", "收购编码↓", "收购人名称↓", "收购价格↓", "收购起始日↓", "收购截止日↓"};
    // 每个字段的排序栏位标志，选中的时候将对应的标志赋值给sortField
    private int[] sortFields = {
            OfferQuoteSort.SortField.OFFER_ID, OfferQuoteSort.SortField.OFFER_NAME,
            OfferQuoteSort.SortField.PRICE, OfferQuoteSort.SortField.START_DATE,
            OfferQuoteSort.SortField.END_DATE};

    private String[] titleNomalText;
    private String[] titleUpText;
    private String[] titleDownText;

    private TextView[] mTextViews;

    public static String code;

    private YaoyueListAdapter adapter;

    //下拉刷新,上拉加载，rankingType参数
    private String refreshRankingType;

    private int currentPage = 0;// 页码

    private int ADDFLAG_DEFAULT = 0;// 请求方式：默认
    private int ADDFLAG_REFRESH = 1;// 请求方式：下拉刷新

    /**
     * @param code1 证券代码
     */
    public static void actionStart(Activity context, String code1) {
        code = code1;
        Intent intent = new Intent(context, YYInfoActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        // 根据不同的排序字段加载不同的布局
        int layoutId = R.layout.activity_yaoyue;
        titleNomalText = titleNomalTextRaise;
        titleUpText = titleUpTextRaise;
        titleDownText = titleDownTextRaise;
        return layoutId;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {

//        presenter.init(stockType);
        // 初始化数据
        mTextViews = new TextView[]{tev_name, tev_offer_id, tev_offer_name,
                tev_price, tev_start_date, tev_end_date};

        // 刷新加载
        lsvContent.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        HVListView hvListView = (HVListView) lsvContent.getRefreshableView();
        hvListView.setScrollView(scrollTitle);
        adapter = new YaoyueListAdapter(this);
        lsvContent.setAdapter(adapter);
        lsvContent.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                // 下拉刷新
                if (currentPage == 0) {
                    // 如果页码等于0，直接刷新
                    presenter.offerQuoteRequest(ADDFLAG_REFRESH, code);
                } else {
                    presenter.offerQuoteRequest(ADDFLAG_REFRESH, code);
                }
                lsvContent.onRefreshComplete();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                // 不支持分页
            }
        });

        toolBar.setToolBarClickListener(new ToolBar.ToolBarClickListener() {
            @Override
            public void onClick(View view, ToolBar.Type type) {
                switch (type) {
                    case RIGHT_TYPE_1:
                        SearchHistoryActivity.startActivity(YYInfoActivity.this);
                        break;
                }
            }
        });

    }

    @Override
    protected void initData() {
        presenter.offerQuoteRequest(ADDFLAG_DEFAULT, code);
    }

    /**
     * 要约列表请求成功
     */
    @Override
    public void offerQuoteSuccess(ArrayList<OfferQuoteBean> result) {
        lsvContent.onRefreshComplete();
        if (result != null && result.size() > 0) {
            adapter.setData(result);
            adapter.notifyDataSetChanged();
        }
    }

    /**
     * 要约列表请求成功(下拉刷新）
     */
    @Override
    public void offerQuoteRefreshSuccess(ArrayList<OfferQuoteBean> result) {
        lsvContent.onRefreshComplete();
        if (result != null && result.size() > 0) {
            if(0 != currentPage) {
                currentPage = currentPage - 1;
            }
            adapter.setData(result);
            adapter.notifyDataSetChanged();
        }
    }

    /**
     * 要约列表请求成功（上拉加载）
     */
    @Override
    public void offerQuoteMoreSuccess(ArrayList<OfferQuoteBean> result) {
        lsvContent.onRefreshComplete();
        if (result != null && result.size() > 0) {
            currentPage = currentPage + 1;
            adapter.setData(result);
            adapter.notifyDataSetChanged();
        }
    }
}
