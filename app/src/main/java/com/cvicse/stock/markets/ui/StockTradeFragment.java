package com.cvicse.stock.markets.ui;

import android.os.Bundle;
import android.widget.ListView;

import com.cvicse.annotations.MVPInject;
import com.cvicse.stock.R;
import com.cvicse.stock.base.PBaseFragment;
import com.cvicse.stock.markets.adapter.StockTradeAdapter;
import com.cvicse.stock.markets.data.TickItemBo;
import com.cvicse.stock.markets.presenter.contract.StockTradeContract;
import com.cvicse.stock.pulltorefresh.PullToRefreshBase;
import com.cvicse.stock.pulltorefresh.PullToRefreshListView;
import com.mitake.core.QuoteItem;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * 成交明细
 * Created by liu_zlu on 2017/4/6 14:22
 */
public class StockTradeFragment extends PBaseFragment implements StockTradeContract.View{

    private static final String QUOTEITEM = "QuoteItem";

    public static StockTradeFragment newInstance(QuoteItem quoteItem) {
        StockTradeFragment stockTradeFragment = new StockTradeFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(QUOTEITEM, quoteItem);
        stockTradeFragment.setArguments(bundle);
        return stockTradeFragment;
    }
    private StockTradeAdapter stockTradeAdapter;

    @MVPInject
    StockTradeContract.Presenter presenter;

    @BindView(R.id.plv_stock_trade) PullToRefreshListView plvStockTrade;
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_stock_trade;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {

        QuoteItem quoteItem = getArguments().getParcelable(QUOTEITEM);

        presenter.init(quoteItem);
        stockTradeAdapter = new StockTradeAdapter(getActivity(),quoteItem.preClosePrice);
        plvStockTrade.setAdapter(stockTradeAdapter);
        plvStockTrade.setMode(PullToRefreshBase.Mode.BOTH);
        plvStockTrade.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                presenter.queryTradeItems();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                presenter.loadTradeItems();
            }
        });
    }

    @Override
    protected void initData() {
        presenter.queryTradeItems();
    }

    /**
     * 返回股票逐笔数据
     *
     * @param tickItemBos
     */
    @Override
    public void onTickItemsSuccess(ArrayList<TickItemBo> tickItemBos) {
        plvStockTrade.onRefreshComplete();
        stockTradeAdapter.setData(tickItemBos);
    }

    /**
     * 返回股票逐笔数据
     */
    @Override
    public void onTickItemsFail() {
        plvStockTrade.onRefreshComplete();
    }
}
