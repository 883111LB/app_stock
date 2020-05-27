package com.cvicse.stock.markets.ui;

import android.os.Bundle;
import android.widget.ListView;

import com.cvicse.annotations.MVPInject;
import com.cvicse.stock.R;
import com.cvicse.stock.base.PBaseFragment;
import com.cvicse.stock.markets.adapter.StockTickAdapter;
import com.cvicse.stock.markets.data.TickItemBo;
import com.cvicse.stock.markets.presenter.contract.StockTickContract;
import com.cvicse.stock.pulltorefresh.PullToRefreshBase;
import com.cvicse.stock.pulltorefresh.PullToRefreshListView;
import com.mitake.core.QuoteItem;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * 沪深-逐笔明细  其他市场-分时明细
 * Created by liuzilu on 2017/3/6.
 */
public class StockTickFragment extends PBaseFragment implements StockTickContract.View{

    private static final String QUOTEITEM = "QuoteItem";
    @BindView(R.id.prlv_stock_tick)
    PullToRefreshListView prlvStockTick;

    //逐笔明细适配器
    private StockTickAdapter stockTickAdapter;

    @MVPInject
    StockTickContract.Presenter presenter;
    public static StockTickFragment newInstance(QuoteItem quoteItem) {
        StockTickFragment stockTransactionFragment = new StockTickFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(QUOTEITEM, quoteItem);
        stockTransactionFragment.setArguments(bundle);
        return stockTransactionFragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_stock_more_tick;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        QuoteItem quoteItem = getArguments().getParcelable(QUOTEITEM);
        if(quoteItem == null){
            return ;
        }
        presenter.init(quoteItem);
        stockTickAdapter = new StockTickAdapter(getActivity(),quoteItem.preClosePrice);
        prlvStockTick.setAdapter(stockTickAdapter);
        prlvStockTick.setMode(PullToRefreshBase.Mode.BOTH);
        prlvStockTick.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                presenter.queryTickItems();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                presenter.loadTickItems();
            }
        });
    }

    @Override
    protected void initData() {
        presenter.queryTickItems();
    }

    /**
     * 返回股票逐笔数据
     *
     * @param tickItemBos
     */
    @Override
    public void onTickItemsSuccess(ArrayList<TickItemBo> tickItemBos) {
        prlvStockTick.onRefreshComplete();
        stockTickAdapter.setData(tickItemBos);
    }

    @Override
    public void onTickItemsFail() {
        prlvStockTick.onRefreshComplete();
    }
}
