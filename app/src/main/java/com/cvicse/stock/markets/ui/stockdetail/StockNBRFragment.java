package com.cvicse.stock.markets.ui.stockdetail;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.cvicse.annotations.MVPInject;
import com.cvicse.base.widget.FixedListView;
import com.cvicse.stock.R;
import com.cvicse.stock.base.PBaseFragment;
import com.cvicse.stock.markets.adapter.MarketNewsAdapter;
import com.cvicse.stock.markets.data.MarketNBRBo;
import com.cvicse.stock.markets.presenter.marketdetail.contract.StockNBRContract;
import com.cvicse.stock.portfolio.activity.StockDetailActivity;
import com.cvicse.stock.utils.MessageEvent;
import com.mitake.core.QuoteItem;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * 新闻、公告、研报，公用的fragment
 * Created by liu_zlu on 2017/2/7 17:48
 */
public class StockNBRFragment extends PBaseFragment implements StockNBRContract.View{

    @MVPInject
    StockNBRContract.Presenter presenter;

    @BindView(R.id.lv_market_news) FixedListView lvMarketNews;

    private String type = "";

    private MarketNewsAdapter marketNewsAdapter;

    private static final String STOCKID = "STOCKID";

    /**
     * 列表类型包括新闻、公告、研报
     */
    private static final String TYPE = "TYPE";


    public static StockNBRFragment newInstance(QuoteItem quoteItem, String type){
        StockNBRFragment marketNewsFragment = new StockNBRFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(STOCKID,quoteItem);
        bundle.putString(TYPE,type);
        marketNewsFragment.setArguments(bundle);
        return marketNewsFragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_stock_news;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        type = getArguments().getString(TYPE);

        //EventBus注册事件
        EventBus.getDefault().register(this);

        presenter.init((QuoteItem) getArguments().getParcelable(STOCKID),type);

        marketNewsAdapter = new MarketNewsAdapter(getActivity());

        lvMarketNews.setAdapter(marketNewsAdapter);

        //列表点击事件
        lvMarketNews.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MarketNBRBo bo =  marketNewsAdapter.getItem(position);
                StockDetailActivity.actionIntent(getActivity(), bo.id, bo.title,
                        bo.date,bo.medianame, type);
            }
        });
    }

    @Override
    protected void initData() {
        presenter.queryNBR();
    }

    @Override
    public void onRequestSuccess(ArrayList<MarketNBRBo> newsArrayList) {
        marketNewsAdapter.setData(newsArrayList);
    }

    @Override
    public void onRequestFail() {

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        //取消注册事件
        EventBus.getDefault().unregister(this);
        //移除所有黏性事件
        EventBus.getDefault().removeAllStickyEvents();
    }

    /**
     * 发布事件和接收事件在同一个线程
     * 订阅黏性事件
     * @param messageEvent
     */
    @Subscribe(threadMode = ThreadMode.POSTING,sticky = true)
    public void onMoonEvent(MessageEvent messageEvent){
        if(messageEvent == null){
            return;
        }
       switch (messageEvent.getMessage()){
           case "新闻":
           case "公告":
           case "研报":

               /**
                * 请求加载更多
                */
               presenter.queryNBRMore();
           default:
               break;
       }

    }
}
