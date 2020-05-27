package com.cvicse.stock.markets.ui;

import android.os.Bundle;

import com.cvicse.annotations.MVPInject;
import com.cvicse.stock.R;
import com.cvicse.stock.base.PBaseFragment;
import com.cvicse.stock.markets.adapter.StockPriceAdapter;
import com.cvicse.stock.markets.data.MorePriceBo;
import com.cvicse.stock.markets.presenter.contract.StockPriceContract;
import com.cvicse.stock.portfolio.SortTextView;
import com.cvicse.stock.pulltorefresh.PullToRefreshListView;
import com.mitake.core.QuoteItem;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * 分价
 * Created by liuzilu on 2017/3/6.
 */

public class StockPriceFragment extends PBaseFragment implements StockPriceContract.View {
    private static final String QUOTEITEM = "QuoteItem";
    @BindView(R.id.prlsv_stock_more_price)
    PullToRefreshListView prlsvStockMorePrice;
    @BindView(R.id.stev_price) SortTextView stevPrice;
    @BindView(R.id.stev_volume) SortTextView stevVolume;
    private StockPriceAdapter stockPriceAdapter;
    @MVPInject
    StockPriceContract.Presenter presenter;

    public static StockPriceFragment newInstance(QuoteItem quoteItem) {
        StockPriceFragment sharPriceFragment = new StockPriceFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(QUOTEITEM, quoteItem);
        sharPriceFragment.setArguments(bundle);
        return sharPriceFragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_stock_more_price;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        QuoteItem quoteItem = getArguments().getParcelable(QUOTEITEM);
        presenter.init(quoteItem);
        stockPriceAdapter = new StockPriceAdapter(getActivity(),quoteItem.preClosePrice);
        prlsvStockMorePrice.setAdapter(stockPriceAdapter);
        stevPrice.setTypeChangedListener(new SortTextView.TypeChangedListener() {
            @Override
            public void onChanged(int change, int pre) {
                stevVolume.reset();
                presenter.sortPrice();
            }
        });
        stevVolume.setTypeChangedListener(new SortTextView.TypeChangedListener() {
            @Override
            public void onChanged(int change, int pre) {
                stevPrice.reset();
                presenter.sortVolume();
            }
        });
    }

    @Override
    protected void initData() {
        presenter.queryMorePrice();
    }

    /**
     * 返回股票分价信息
     *
     * @param morePriceBos
     */
    @Override
    public void onMorePriceSuccess(ArrayList<MorePriceBo> morePriceBos) {
        prlsvStockMorePrice.onRefreshComplete();
        stockPriceAdapter.setData(morePriceBos);
    }

    /**
     * 返回股票分价信息
     */
    @Override
    public void onMorePriceFail() {
        prlsvStockMorePrice.onRefreshComplete();
    }

}
