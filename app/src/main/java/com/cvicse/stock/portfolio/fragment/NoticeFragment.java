package com.cvicse.stock.portfolio.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.cvicse.annotations.MVPInject;
import com.cvicse.stock.R;
import com.cvicse.stock.base.PBaseFragment;
import com.cvicse.stock.portfolio.activity.StockDetailActivity;
import com.cvicse.stock.portfolio.adapter.NoticeAdapter;
import com.cvicse.stock.portfolio.presenter.constract.NoticeConstract;
import com.cvicse.stock.pulltorefresh.PullToRefreshBase;
import com.cvicse.stock.pulltorefresh.PullToRefreshListView;
import com.mitake.core.StockBulletinItem;

import java.util.List;

import butterknife.BindView;

/**
 * 自选公告
 */
public class NoticeFragment extends PBaseFragment implements NoticeConstract.View{
    @MVPInject
    NoticeConstract.PresenterNotice presenter;
    NoticeAdapter mNoticeAdapter;

    @BindView(R.id.pull_to_refresh_list)
    PullToRefreshListView mPullToRefreshList;

    @BindView(R.id.tev_content)
    TextView mTevContent;

    /**
     * 初始化NoticeFragmentFragement
     * @return
     */
    public static NoticeFragment newInstance(){
        NoticeFragment newsFragement = new NoticeFragment();
        return newsFragement;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mystock_news;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        View view = getView();
        mNoticeAdapter = new NoticeAdapter();

        mPullToRefreshList = (PullToRefreshListView)view.findViewById(R.id.pull_to_refresh_list);
        mPullToRefreshList.setAdapter(mNoticeAdapter);
        mPullToRefreshList.setMode(PullToRefreshBase.Mode.BOTH);
        mPullToRefreshList.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                presenter.pullToRefreshNotice();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                presenter.pullToLoadNotice();
            }
        });
        mPullToRefreshList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                StockBulletinItem item = (StockBulletinItem) mNoticeAdapter.getItem(position);
                StockDetailActivity.actionIntent(getActivity(), item.ID_,
                        item.TITLE_, item.PUBDATE_,null, StockDetailActivity.TYPE_BULLETIN);
            }
        });
    }

    @Override
    protected void initData() {
        presenter.pullToRefreshNotice();
    }


    @Override
    public void noticeSuccess(Object result) {
        mPullToRefreshList.onRefreshComplete();
        if(result!=null){
            List<StockBulletinItem> stockBulletinItems = (List<StockBulletinItem>) result;
            if(stockBulletinItems.size()!=0){
                mTevContent.setVisibility(View.GONE);
                mPullToRefreshList.setVisibility(View.VISIBLE);
                mNoticeAdapter.setData((List<StockBulletinItem>) result);
            }else{
                showMessage();
            }

        }else {
            showMessage();
        }
    }

    @Override
    public void noticeFail() {
        mPullToRefreshList.onRefreshComplete();
        showMessage();
    }


    void showMessage(){
        mTevContent.setVisibility(View.VISIBLE);
        mTevContent.setText("暂无公告");
        mPullToRefreshList.setVisibility(View.GONE);
    }
}
