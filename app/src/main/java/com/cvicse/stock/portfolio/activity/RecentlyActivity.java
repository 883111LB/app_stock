package com.cvicse.stock.portfolio.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cvicse.annotations.MVPInject;
import com.cvicse.stock.R;
import com.cvicse.stock.base.PBaseActivity;
import com.cvicse.stock.base.ToolBar;
import com.cvicse.stock.markets.adapter.RankingListAdapter;
import com.cvicse.stock.portfolio.presenter.constract.RecentlyConstract;
import com.cvicse.stock.seachstock.SearchHistoryActivity;
import com.cvicse.stock.view.CHScrollView;
import com.cvicse.stock.view.HVListView;
import com.mitake.core.QuoteItem;

import java.util.ArrayList;

import butterknife.BindView;

import static com.cvicse.stock.markets.data.ParamUtil.RANKING_TYPE_RAISE;

/**
 * 最近浏览
 */

public class RecentlyActivity extends PBaseActivity implements RecentlyConstract.View {

    @BindView(R.id.topbar) ToolBar topBar;
    @BindView(R.id.item_scroll_title)
    LinearLayout mItemScrollTitle;
    @BindView(R.id.scroll_list)
    HVListView mScrollList;
    @BindView(R.id.tev_content)
    TextView mTevContent;
    @BindView(R.id.lel_list)
    LinearLayout mLelList;

    private RankingListAdapter mRecentlyAdapter;

    //private RecentlyAdapter mRecentlyAdapter;

    private ArrayList<QuoteItem> quoteItemList;


    @MVPInject
    RecentlyConstract.Presenter myStockRecentlyPresenter;

    public static void startActivity(Activity activity) {
        activity.startActivity(new Intent(activity, RecentlyActivity.class));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_recently;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        //MyStockDaggerHelper.getComponent(this).inject(this);


       /* chScrollViewHlper.addHViews(mItemScrollTitle);*/
        mScrollList.setScrollView((CHScrollView) mItemScrollTitle.getChildAt(1)) ;
        //mRecentlyAdapter = new RecentlyAdapter(this,chScrollViewHlper);
        mRecentlyAdapter = new RankingListAdapter(this,RANKING_TYPE_RAISE);
        mScrollList.setAdapter(mRecentlyAdapter);
        mScrollList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               /* com.cvicse.stock.markets.ui.StockDetailActivity.startActivity(RecentlyActivity.this,
                        mRecentlyAdapter.getData(),mRecentlyAdapter.getCount()-position-1);*/
                com.cvicse.stock.markets.ui.StockDetailActivity.startActivity(RecentlyActivity.this,mRecentlyAdapter.getData(),position);
            }
        });


        topBar.setToolBarClickListener(new ToolBar.ToolBarClickListener() {
            @Override
            public void onClick(View view, ToolBar.Type type) {
                switch (type){
                    case RIGHT_TYPE_1:
                        SearchHistoryActivity.startActivity(RecentlyActivity.this);
                        break;
                }
            }
        });
    }


    protected void initData() {

    }


    @Override
    public void requesetSuccess(Object result) {
        if (result != null) {
            quoteItemList = (ArrayList<QuoteItem>) result;
            if(quoteItemList.size()==0){
                showMessage();
            }else {
                mTevContent.setVisibility(View.GONE);
                mLelList.setVisibility(View.VISIBLE);
                mRecentlyAdapter.setData(quoteItemList);
            }
        } else {
            showMessage();
        }

    }

    @Override
    public void requestFail() {
        showMessage();
    }

    /**
     * 接口没有返回时，提示
     */
    public void showMessage(){
        mTevContent.setVisibility(View.VISIBLE);
        mTevContent.setText("暂无最近浏览");
        mLelList.setVisibility(View.GONE);
    }

}
