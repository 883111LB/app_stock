package com.cvicse.stock.markets.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.cvicse.annotations.MVPInject;
import com.cvicse.stock.R;
import com.cvicse.stock.base.PBaseFragment;
import com.cvicse.stock.markets.adapter.SHQQOtherAdapter;
import com.cvicse.stock.markets.presenter.contract.SHQQAllContract;
import com.cvicse.stock.pulltorefresh.PullToRefreshBase;
import com.cvicse.stock.pulltorefresh.PullToRefreshListView;
import com.cvicse.stock.view.CHScrollView;
import com.cvicse.stock.view.CHScrollViewHlper;
import com.mitake.core.QuoteItem;

import java.util.ArrayList;

import butterknife.BindView;

import static com.cvicse.stock.markets.ui.SHQQTFragment.KEY_STOCK_ID;


/**
 * 上证期权认购Fragment
 * Created by ding_syi on 17-2-16.
 */
public class SHQQCallFragment extends PBaseFragment implements SHQQAllContract.View{

    @BindView(R.id.item_scroll_title) CHScrollView mItemScrollTitle;
    @BindView(R.id.ptr_list) PullToRefreshListView mScrollList;

    private SHQQOtherAdapter mSHQQOtherAdapter;

    private String stockId;

    @MVPInject
    SHQQAllContract.Presenter mPresenter;

    public static SHQQCallFragment newInstance(String stockID){
        Bundle bundle = new Bundle();
        bundle.putString(KEY_STOCK_ID,stockID);

        SHQQCallFragment shqqCallFragment = new SHQQCallFragment();
        shqqCallFragment.setArguments(bundle);
        return  shqqCallFragment;
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_shqqother;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {

        stockId = getArguments().getString(KEY_STOCK_ID,"");
        mPresenter.init(stockId,"_CALL");
        CHScrollViewHlper chScrollViewHlper = new CHScrollViewHlper();

        chScrollViewHlper.addHViews(mItemScrollTitle);
        mSHQQOtherAdapter = new SHQQOtherAdapter(getActivity(),chScrollViewHlper);
        mScrollList.setMode(PullToRefreshBase.Mode.BOTH);
        mScrollList.setAdapter(mSHQQOtherAdapter);
        mScrollList.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {

            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                mPresenter.requestSHQQAll();
                mScrollList.onRefreshComplete();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                mPresenter.requestSHQQAllMore();
                mScrollList.onRefreshComplete();
            }
        });
        mScrollList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position >0){
                    StockDetailActivity.startActivity(getActivity(),mSHQQOtherAdapter.getData(),position-1);
                }
            }
        });
    }

    @Override
    protected void initData() {
        mPresenter.requestSHQQAll();
    }

    /**
     * 查询认购期权成功
     *
     * @param list
     */
    @Override
    public void onRequestSHQQSuccess(ArrayList<QuoteItem> list) {
        mScrollList.onRefreshComplete();
        mSHQQOtherAdapter.setData(list);
    }

    /**
     * 查询认购期权失败
     */
    @Override
    public void onRequestSHQQFail() {

    }
}

