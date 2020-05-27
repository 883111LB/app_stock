package com.cvicse.stock.markets.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.cvicse.annotations.MVPInject;
import com.cvicse.base.utils.SizeUtils;
import com.cvicse.stock.R;
import com.cvicse.stock.base.PBaseFragment;
import com.cvicse.stock.common.ColorUtils;
import com.cvicse.stock.markets.adapter.new_adapter.RankingListAdapterCFF;
import com.cvicse.stock.markets.adapter.new_adapter.RankingListAdapterFutures;
import com.cvicse.stock.markets.adapter.new_adapter.RankingListAllAdapter;
import com.cvicse.stock.markets.data.Param;
import com.cvicse.stock.markets.helper.RankingHelper;
import com.cvicse.stock.markets.presenter.contract.RankingListFuturesContract;
import com.cvicse.stock.pulltorefresh.PullToRefreshBase;
import com.cvicse.stock.pulltorefresh.PullToRefreshHSListView;
import com.cvicse.stock.view.CHScrollView;
import com.cvicse.stock.view.HVListView;
import com.mitake.core.response.CateSortingResponse;

import butterknife.BindView;

/**
 * 排序页面
 * Created by tang_xqing on 2018/7/30.
 */
public class RankingListFuturesFragment extends PBaseFragment implements RankingListFuturesContract.View {
    private static final String STOCK_TYPE = "stockType";

    @MVPInject
    RankingListFuturesContract.Presenter mPresenter;

    @BindView(R.id.lly_sort_type)
    LinearLayout llySortType;
    @BindView(R.id.lsv_content)
    PullToRefreshHSListView lsvContent;
    @BindView(R.id.scroll_title)
    CHScrollView scrollTitle;

    private boolean isAddvalue = false;
    private int currentPage;
    private String currentParam;
    private Param mParam;
    private String[][] rankingKey;
    private RankingHelper mRankingHelper;  // 排序字段帮助类
    private RankingListAdapterCFF mAdapterCFF;  // 期货
    private RankingListAdapterFutures mAdapterFutures;  // 全球、外汇
    private RankingListAllAdapter mAdapterAddValue;  // 5分钟涨幅榜
//    private RankingListAdapterAddValue mAdapterAddValue;  // 5分钟涨幅榜

    private static final int status_nomal = 0;
    private static final int status_up = 1;
    private static final int status_down = 2;

    public static RankingListFuturesFragment newInstance() {
        RankingListFuturesFragment rankingListFuturesFragment = new RankingListFuturesFragment();
        return rankingListFuturesFragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_ranking;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        mRankingHelper = null == mRankingHelper ?  new RankingHelper() : mRankingHelper;

        initView();
        initClick();
    }

    private void initClick() {
        lsvContent.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                onItemClickk(position);
            }
        });

        lsvContent.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                if( currentPage >=1 ){
                    String[] param = currentParam.split(",");
                    if( param.length >=4 ){
                        currentParam = (currentPage - 1) + "," + param[1] + "," + param[2] + "," + param[3]+ "," + param[4];
                    }
                }
                mPresenter.requestPullDownRanking(currentParam);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                String[] param = currentParam.split(",");
                if( param.length >=4 ){
                    currentParam = (currentPage + 1) + "," + param[1] + "," + param[2] + "," + param[3]+ "," + param[4];
                }
                mPresenter.requestPullUpRanking(currentParam);
            }
        });
    }

    @Override
    protected void initData() {

    }

    private void initView() {
        if( null == lsvContent ) {
            return;
        }
        ((HVListView) lsvContent.getRefreshableView()).setScrollView(scrollTitle);
        lsvContent.setMode(PullToRefreshBase.Mode.BOTH);

        addViews();
        if (null != mParam) {
            String type = mParam.getType();
            String stockCode = mParam.getStockCode();
            if( "cff".equals(type)|| "dce".equals(type) || "zce".equals(type)||"shfe".equals(type)||"ine".equals(type)){
                mAdapterCFF = new RankingListAdapterCFF(getContext(),stockCode);
                lsvContent.setAdapter(mAdapterCFF);
                isAddvalue = false;
            }else if(  "fe".equals(type) || "gi".equals(type) ){
                mAdapterFutures = new RankingListAdapterFutures(getContext(),stockCode);
                lsvContent.setAdapter(mAdapterFutures);
                isAddvalue = false;
            }else if("addvalue".equals(type)){
                mAdapterAddValue = new RankingListAllAdapter(getContext(),stockCode);
                lsvContent.setAdapter(mAdapterAddValue);
                isAddvalue = true;
            }

            currentParam = mParam.getSortParam();
            mPresenter.init(stockCode,isAddvalue);
            mPresenter.requestRanking(mParam.getSortParam());
        }
    }

    /**
     * 动态添加排序字段
     */
    private void addViews() {
        if (null == rankingKey) {
            return;
        }
        removeAllViews();
        int flag = 0;
        if (null != mParam) {
            String type = mParam.getType();
            // 期货的排序字段默认是第4个，五分钟涨幅默认是第5个,其他排序默认第0个
            if("cff".equals(type)|| "dce".equals(type) || "zce".equals(type)||"shfe".equals(type)||"ine".equals(type)) {
                flag = 4;
            } else if("addvalue".equals(type)) {
                flag = 5;
            }
        }
        for (int i = 0; i < rankingKey[1].length; i++) {
            TextView textView = new TextView(getContext());
            textView.setText(rankingKey[1][i]);
            if (flag == i) {
                textView.setText(rankingKey[1][i] + "↓");
                textView.setTextColor(ColorUtils.BLUE);
                textView.setTag(status_down);
            } else {
                textView.setTextColor(ColorUtils.GRAY);
                textView.setTag(status_nomal);
            }
            textView.setGravity(Gravity.CENTER);
            textView.setLayoutParams(new LinearLayout.LayoutParams(SizeUtils.dp2px(getContext(),100), ViewGroup.LayoutParams.MATCH_PARENT));
            if(!TextUtils.isEmpty(rankingKey[0][i])){
                initClickListener(textView, i);
            }
            llySortType.addView(textView);
        }
    }

    private void initClickListener(final TextView textView, final int index) {
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAllViewNormal();
                currentPage = 0;
                textView.setTextColor(ColorUtils.BLUE);
                Integer tag = (Integer) textView.getTag();

                switch (tag) {
                    case status_nomal:
                        textView.setTag(status_down);
                        setCurrentParam(index, "1");
                        textView.setText(rankingKey[1][index] + "↓");
                        break;
                    case status_down:
                        textView.setTag(status_up);
                        setCurrentParam(index, "0");
                        textView.setText(rankingKey[1][index] + "↑");
                        break;
                    case status_up:
                        textView.setTag(status_down);
                        setCurrentParam(index, "1");
                        textView.setText(rankingKey[1][index] + "↓");
                        break;
                    default:
                        break;
                }
                mPresenter.requestRanking(currentParam);
            }
        });
    }

    /**
     * 跳转行情页面
     * @param position
     */
    private void onItemClickk(int position){
        if( position >= 1 ){
//            if( null != mAdapterCFF ){
//                StockDetailActivity.startActivity(this.getActivity(), mAdapterCFF.getData(), position - 1);
//            }else if( null != mAdapterFutures) {
//                StockDetailActivity.startActivity(this.getActivity(), mAdapterFutures.getData(), position - 1);
//            }else if(null != mAdapterAddValue){
//                StockDetailActivity.startActivity(this.getActivity(), mAdapterAddValue.getData(), position - 1);
//            }
            if( null != mAdapterCFF ){
                StockDetailActivity.startActivity(this.getActivity(), mAdapterCFF.getData(), position - 1);
            }
            if( null != mAdapterFutures) {
                StockDetailActivity.startActivity(this.getActivity(), mAdapterFutures.getData(), position - 1);
            }
            if(null != mAdapterAddValue){
                StockDetailActivity.startActivity(this.getActivity(), mAdapterAddValue.getData(), position - 1);
            }
        }
    }

    /** 设置排序参数
     * @param index
     * @param order 排序顺序  1-倒序 0-正序
     */
    public void setCurrentParam(int index, String order) {
        currentParam = currentPage + ",20," + rankingKey[0][index] + "," + order + ",1";
    }

    public void setStockType(Param param) {
        mParam = param;
        mRankingHelper = null == mRankingHelper ?  new RankingHelper() : mRankingHelper;
        rankingKey = mRankingHelper.getRankingKey(param.getType());
        initView();
    }

    /**
     * 清除所有子view
     */
    private void removeAllViews() {
        llySortType.removeAllViews();
    }

    /**
     * 将所有控件转化为不按它排行状态
     */
    private void setAllViewNormal() {
        for (int i = 0; i < llySortType.getChildCount(); i++) {
            TextView mTextView = (TextView) llySortType.getChildAt(i);
            mTextView.setText(rankingKey[1][i]);
            mTextView.setTextColor(ColorUtils.GRAY);
        }
    }

    /**
     * 请求排序接口成功
     * @param cateSortingResponse
     */
    @Override
    public void requestRankingSuccess(CateSortingResponse cateSortingResponse) {
        lsvContent.onRefreshComplete();
        setAdapterData(cateSortingResponse);
    }

    /**
     * 下拉刷新请求成功
     * @param cateSortingResponse
     */
    @Override
    public void requestPullDownRankingSuccess(CateSortingResponse cateSortingResponse) {
        lsvContent.onRefreshComplete();
        if( null == cateSortingResponse.list || cateSortingResponse.list.isEmpty() ){
            return;
        }
        if( currentPage>=1 ){
            currentPage = currentPage -1;
        }else{
            currentPage = 0;
        }
        setAdapterData(cateSortingResponse);
    }

    /**
     * 上拉加载请求成功
     * @param cateSortingResponse
     */
    @Override
    public void requestPullUpRankingSuccess(CateSortingResponse cateSortingResponse) {
        lsvContent.onRefreshComplete();
        if( null == cateSortingResponse.list || cateSortingResponse.list.isEmpty() ){
            return;
        }
        currentPage = currentPage+1;
        setAdapterData(cateSortingResponse);
    }

    /**
     * 根据不同类型适配器进行赋值
     * @param cateSortingResponse
     */
    private void setAdapterData(CateSortingResponse cateSortingResponse){
//        if( null != mAdapterCFF ){
//            mAdapterCFF.setData(cateSortingResponse.list);
//        }else if(null != mAdapterFutures){
//            mAdapterFutures.setData(cateSortingResponse.list);
//        }else if( null != mAdapterAddValue ){
//            mAdapterAddValue.setData(cateSortingResponse);
//        }
        if( null != mAdapterCFF ){
            mAdapterCFF.setData(cateSortingResponse.list);
        }
        if(null != mAdapterFutures){
            mAdapterFutures.setData(cateSortingResponse.list);
        }
        if( null != mAdapterAddValue ){
            mAdapterAddValue.setData(cateSortingResponse.list, cateSortingResponse.addValueModel);
        }
    }

    @Override
    public void requestRankingFail() {
        lsvContent.onRefreshComplete();
    }
}
