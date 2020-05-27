package com.cvicse.stock.markets.ui;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.FrameLayout;
import android.widget.ScrollView;

import com.cvicse.annotations.MVPInject;
import com.cvicse.stock.R;
import com.cvicse.stock.base.PBaseFragment;
import com.cvicse.stock.markets.data.AHQuoteBo;
import com.cvicse.stock.markets.data.DRQuoteBo;
import com.cvicse.stock.markets.helper.HKStockInfoHelper;
import com.cvicse.stock.markets.helper.StockChartHelper;
import com.cvicse.stock.markets.helper.StockOtherHlper;
import com.cvicse.stock.markets.helper.StockState;
import com.cvicse.stock.markets.helper.StockToolBarHelper;
import com.cvicse.stock.markets.helper.StockViewHelper;
import com.cvicse.stock.markets.helper.UKStockInfoHelper;
import com.cvicse.stock.markets.presenter.contract.MarketDetailContract;
import com.cvicse.stock.pulltorefresh.PullToRefreshBase;
import com.cvicse.stock.pulltorefresh.PullToRefreshScrollView;
import com.cvicse.stock.utils.MessageEvent;
import com.cvicse.stock.utils.StockType;
import com.cvicse.stock.view.StickyScrollView;
import com.mitake.core.HKOthersInfo;
import com.mitake.core.QuoteItem;
import com.mitake.core.bean.UKQuoteItem;
import com.mitake.core.bean.UpdownsItem;
import com.mitake.core.bean.quote.ConvertibleBoundItem;
import com.mitake.core.response.QuoteResponse;
import com.stock.config.KSetting;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


/**
 * 股票详情
 * Created by liu_zlu on 2017/2/7 10:55
 */
public class StockDetailFragment extends PBaseFragment implements MarketDetailContract.View {

    @MVPInject
    MarketDetailContract.Presenter presenter;

    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.psv_market)
    PullToRefreshScrollView psvMarket;
    @BindView(R.id.content_frame_tick)
    FrameLayout contentFrameTick;

    // 港股其他
    @BindView(R.id.vsb_hk_stockinfo)
    ViewStub vsb_hk_stockinfo;
    //uk独有
    @BindView(R.id.vsb_uk_stockinfo)
    ViewStub vsb_uk_stockinfo;

    public static String EVENT_STR = "新闻";
    private static final String STOCKID = "STOCKID";
    private static final String POSITION = "POSITION";

    // 港股其他,即港股分时图下面的部分vcm时间等
    private HKStockInfoHelper hkStockInfoHelper;
    //UK独有数据
    private UKStockInfoHelper ukStockInfoHelper;

    // 股票行情头部
    private StockViewHelper marketViewHelper;

    private StickyScrollView stickyScrollView;

    // 股票底部更多模块（F10模块）
    private StockOtherHlper marketOtherHlper;

    // 图表帮助类
    private StockChartHelper stockChartHelper;

    // 新图表帮助类
//    private StockChartHelperNew stockChartHelperNew;

    // 股票列表头部操作
    private StockToolBarHelper stockToolBarHelper;

    private StockState stockState = new StockState();

    private int position = 0;
    private QuoteItem mQuoteItem;

    public static StockDetailFragment newInstance(QuoteItem quoteItem, int position) {
        StockDetailFragment stockDetailFragment = new StockDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(STOCKID, quoteItem);
        bundle.putInt(POSITION, position);
        stockDetailFragment.setArguments(bundle);
        return stockDetailFragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_stock_detail;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        position = getArguments().getInt(POSITION);
        stockToolBarHelper = ((StockDetailActivity) getActivity()).getStockToolBarHelper();

        mQuoteItem = getArguments().getParcelable(STOCKID);
        position = getArguments().getInt(POSITION);
        presenter.init(mQuoteItem);

        marketViewHelper = new StockViewHelper(getView(),this, mQuoteItem);

        //图表帮助类- old
        stockChartHelper = new StockChartHelper(false, 0, getView(), mQuoteItem, getChildFragmentManager());

        // 新图表帮助类
//        stockChartHelperNew = new StockChartHelperNew(false,0,getView(),mQuoteItem,getChildFragmentManager(),getActivity());
        if (StockType.getType(mQuoteItem)!=null){
            if ( StockType.getType(mQuoteItem).isOption() || StockType.getType(mQuoteItem).isCFF()||
                    StockType.getType(mQuoteItem).isGI()|| StockType.getType(mQuoteItem).isFE()||
                    StockType.getType(mQuoteItem).isDCE()|| StockType.getType(mQuoteItem).isZCE()||
                    StockType.getType(mQuoteItem).isSHFE()|| StockType.getType(mQuoteItem).isINE()) {
                View view = ((View) tabLayout.getParent());
                ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
                layoutParams.height = (int) (layoutParams.height * 0.2);
                layoutParams.width = 1;
                view.setLayoutParams(layoutParams);
            } else {
                marketOtherHlper = new StockOtherHlper(psvMarket, tabLayout, getChildFragmentManager(), mQuoteItem);
            }
            if (StockType.getType(mQuoteItem).isHongKong()) {
                hkStockInfoHelper = new HKStockInfoHelper(vsb_hk_stockinfo.inflate());
            }
            if (StockType.getType(mQuoteItem).isUK()){
                ukStockInfoHelper=new UKStockInfoHelper(vsb_uk_stockinfo.inflate(),mQuoteItem);
            }
        }


        psvMarket.setScrollingWhileRefreshingEnabled(true);
        psvMarket.setShowViewWhileRefreshing(true);

        psvMarket.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ScrollView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ScrollView> refresw) {
                psvMarket.onRefreshComplete();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ScrollView> refreshView) {
                //发送粘性事件，就是在发送事件之后再订阅该事件也能收到该事件，跟黏性广播类似
                if (EVENT_STR.equals("新闻") || EVENT_STR.equals("公告") || EVENT_STR.equals("研报")) {
                    EventBus.getDefault().postSticky(new MessageEvent(EVENT_STR));
                }
                psvMarket.onRefreshComplete();
            }
        });

        stickyScrollView = ((StickyScrollView) psvMarket.getRefreshableView());
        stickyScrollView.setOnScrollListener(new StickyScrollView.OnScrollChangedListener() {
            @Override
            public void onScrollChanged(int l, int t, int oldl, int oldt) {
                stockToolBarHelper.updateViewSwitcher(stickyScrollView.getScrollY(), position);
            }
        });
    }

    @Override
    protected void initData() {
        if (null != marketOtherHlper) {
            marketOtherHlper.beginShow();
        }
        presenter.requestUpDownSame(mQuoteItem.id);
        presenter.queryHKStockInfo();
        presenter.requsetUKQuote();
        presenter.requestAHQuote();
        presenter.requestDRQuote();
        presenter.requestConvertibleDebt();

        // old
        stockChartHelper.setSelected(StockDetailActivity.type);
        // 新线图
//        stockChartHelperNew.setSelected(StockDetailActivity.type);
    }

    @Override
    protected void onVisible() {
        super.onVisible();
//        if(stockChartHelperNew != null && stockState.isChange()){
        if (stockChartHelper != null && stockState.isChange()) {
            stockChartHelper.setSelected(StockDetailActivity.type);  //old
            // 新线图
//            stockChartHelperNew.setSelected(StockDetailActivity.type);
        }
    }

    @Override
    protected void onInVisible() {
        super.onInVisible();
        stockState.kType = KSetting.getKTypeAsType();
        stockState.right = KSetting.getKRightSubType();
        stockState.type = StockDetailActivity.type;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && stockToolBarHelper != null) {
            stockToolBarHelper.updateViewSwitcher(stickyScrollView.getScrollY(), position);
        }
    }

    public void requestQuote(String code){
        presenter.requestQuote(code);
    }

    @Override
    public void requestQuoteDetail(ArrayList<QuoteItem> quoteItems) {
        StockDetailActivity.startActivity(this.getActivity(),quoteItems,0);
    }

    @Override
    public void onQuoteSuccess(QuoteResponse quoteResponse) {
        QuoteItem quoteItem = quoteResponse.quoteItems.get(0);
        //更新股票数据
        marketViewHelper.setQuoteDetail(quoteItem);
        //更新图表的股票当前详细
        // old
        stockChartHelper.setQuoteDetail(quoteResponse);

        // 新线图  更新图表的股票当前详细
//        stockChartHelperNew.setQuoteDetail(quoteResponse);
        if ( null!=stockToolBarHelper ) {
            stockToolBarHelper.updateToolBar(quoteItem, position);
        }
    }

    /**
     * 返回港股其他
     */
    @Override
    public void onHKStockInfoSuccess(HKOthersInfo hkOthersInfo) {
        hkStockInfoHelper.onHKStockInfoSuccess(hkOthersInfo);
    }

    /**
     * 指数涨跌平家数
     */
    @Override
    public void onRequestUpDownSameSuccess(UpdownsItem updownsItem) {
        marketViewHelper.setUpdownsItem(updownsItem);
    }

    @Override
    public void onRequestUpDownSameFail() {

    }

    /**
     * 请求AH股成功
     */
    @Override
    public void onRequestAhQuoteSuccess(AHQuoteBo ahQuoteBo) {
        marketViewHelper.setAHQuote(ahQuoteBo);
    }

    @Override
    public void onRequestAhQuoteFail() {

    }
    /**
     * 请求DR成功
     */
    @Override
    public void onRequestDRQuoteSuccess(DRQuoteBo drQuoteBo) {
        marketViewHelper.setDRQuote(drQuoteBo);
    }

    @Override
    public void onRequestDRQuoteFail() {

    }
    /**
     * 请求UK独有数据成功
     */
    @Override
    public void onUKStockInfoSuccess(List<UKQuoteItem> ukQuoteItems) {
        ukStockInfoHelper.onUKStockInfoSuccess(ukQuoteItems);
    }

    /**
     * 可转债溢价查询
     */
    @Override
    public void onRequestConvertibleDebtSuccess(List<ConvertibleBoundItem> convertibleBoundItem) {
        marketViewHelper.setConvertibleBound(convertibleBoundItem);
    }

    @Override
    public void onRequestConvertibleDebtFail() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.onPause();
        marketViewHelper.dismissDialog();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        marketViewHelper.dismissDialog();
        StockDetailFragment.EVENT_STR = "新闻";
    }
}
