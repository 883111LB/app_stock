package com.cvicse.stock.markets.ui;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ExpandableListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cvicse.annotations.MVPInject;
import com.cvicse.stock.R;
import com.cvicse.stock.base.PBaseFragment;
import com.cvicse.stock.common.ColorUtils;
import com.cvicse.stock.markets.adapter.ExplsvAdapter;
import com.cvicse.stock.markets.adapter.StockTopAdapter;
import com.cvicse.stock.markets.presenter.contract.SHSZContract;
import com.cvicse.stock.pulltorefresh.PullToRefreshBase;
import com.cvicse.stock.pulltorefresh.PullToRefreshExpandableListView;
import com.cvicse.stock.view.DivideViewPager;
import com.mitake.core.NewShareList;
import com.mitake.core.QuoteItem;
import com.mitake.core.bean.UpdownsItem;
import com.mitake.core.response.CateSortingResponse;
import com.stock.config.FillConfig;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;


/**
 * Created by ruan_ytai on 17-1-12.
 * 沪深Fragment
 */
public class SHSZFragment extends PBaseFragment implements View.OnClickListener, SHSZContract.View {

    private DivideViewPager mVipSh;

    private RelativeLayout mRelCalendar;
    private TextView mTevNewstock;

    private TextView tevBndNewStock;  // 新债
    private RelativeLayout mReBndCalendar;

    private RelativeLayout mRelFund;

    private RelativeLayout mRelDataCenter;  // 数据中心

    private TextView mTevUpCount;    // 沪深A股 上涨
    private TextView mTevSameCount;  // 沪深A股 平盘
    private TextView mTevDownCount;  // 沪深A股 下跌

    @BindView(R.id.explsv_charts)
    PullToRefreshExpandableListView mExplsvCharts;

    @MVPInject
    SHSZContract.Presenter presenter;

    public static final String STOCK_TYPE_HS = "沪深A股";
    private static final String JRSG = "今日申购";
    private static final String JRSS = "今日上市";
    private static final String JRZQ = "今日中签";

    private ExplsvAdapter adapter;
    private StockTopAdapter stockTopAdapter;

    public static SHSZFragment newInstance() {
        return new SHSZFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_markets_shsz;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.layout_shsz_header, mExplsvCharts, false);
        view.setLayoutParams(new AbsListView.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT));
        mExplsvCharts.getRefreshableView().addHeaderView(view);
        mVipSh = (DivideViewPager) view.findViewById(R.id.vip_sh);
        mRelCalendar = (RelativeLayout) view.findViewById(R.id.rel_calendar);
        mTevNewstock = (TextView) view.findViewById(R.id.tev_newstock);
        mRelFund = (RelativeLayout) view.findViewById(R.id.rel_fund);
        mRelDataCenter = (RelativeLayout) view.findViewById(R.id.rel_data_center);

        tevBndNewStock = (TextView) view.findViewById(R.id.tev_bnd_newstock);
        mReBndCalendar = (RelativeLayout) view.findViewById(R.id.rel_bnd_calendar);
        mTevUpCount = (TextView)view.findViewById(R.id.tev_up_count);
        mTevSameCount = (TextView)view.findViewById(R.id.tev_same_count);
        mTevDownCount = (TextView)view.findViewById(R.id.tev_down_count);
        stockTopAdapter = new StockTopAdapter(getActivity());
        stockTopAdapter.setCount(6);
        mVipSh.setAdapter(stockTopAdapter);
        mVipSh.setOnItemClickListener(new DivideViewPager.OnItemClickListener() {
            @Override
            public void onItemClick(DivideViewPager parent, View view, int position) {
                StockDetailActivity.startActivity(getActivity(), stockTopAdapter.getData(), position);
            }
        });

        //监听组点击
        mExplsvCharts.getRefreshableView().setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                if (parent.isGroupExpanded(groupPosition)) {
                    parent.collapseGroup(groupPosition);
                } else {
                    parent.expandGroup(groupPosition);
                }
                presenter.requestRank(groupPosition);
                return true;
            }
        });
        mExplsvCharts.getRefreshableView().setGroupIndicator(null);

        //监听每个分组里子控件的点击事件
        mExplsvCharts.getRefreshableView().setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
//                StockDetailActivity.startActivity(getActivity(), (ArrayList<QuoteItem>) adapter.getGroup(groupPosition), childPosition);
                StockDetailActivity.startActivity(getActivity(),adapter.getGroup(groupPosition).list, childPosition);
                return false;
            }
        });

        adapter = new ExplsvAdapter(getContext(), STOCK_TYPE_HS);
        mExplsvCharts.getRefreshableView().setAdapter(adapter);
        mExplsvCharts.getRefreshableView().expandGroup(0);

        //PullToRefreshScrollView下拉刷新事件
        mExplsvCharts.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ExpandableListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ExpandableListView> refreshView) {
                presenter.requestUpDownSame("SHSZ");
                presenter.requestCurrentCalendar();
                presenter.requestCurrentBndNewSharesCal();
                presenter.requestRank();
                mExplsvCharts.onRefreshComplete();
            }
        });

        mReBndCalendar.setOnClickListener(this); // 新债
        mRelCalendar.setOnClickListener(this);
        mRelFund.setOnClickListener(this);
        mRelDataCenter.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        presenter.requestUpDownSame("SHSZ");
        presenter.requestCurrentCalendar();
        presenter.requestCurrentBndNewSharesCal();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rel_calendar:
                // 新股日历
                Intent calendarIntent = new Intent(getActivity(), MarketsNewStockCalendarActivity.class);  // old
//                Intent calendarIntent= new Intent(getActivity(),MarketsNewStockCalendarActivityNew.class);  // new 使用UI组件
                startActivity(calendarIntent);
                break;

            case R.id.rel_fund:
                Intent intent = new Intent(getActivity(), SHSZfundActivity.class);
                startActivity(intent);
                break;
            case R.id.rel_bnd_calendar:
                Intent bndCalendarIntent = new Intent(getActivity(), BondTradingDayActivity.class);
                startActivity(bndCalendarIntent);
                break;
            case R.id.rel_data_center:
                Intent intentDataCenter = new Intent(getActivity(),DataCenterActivity.class);
                startActivity(intentDataCenter);
                break;
            default:
                break;
        }
    }

    @Override
    public void onIndexSuccess(ArrayList<QuoteItem> result) {
        stockTopAdapter.setData(result);
    }

    /**
     * 排序列表查询成功
     */
    @Override
    public void onRequestRankSuccess(CateSortingResponse response, int position) {
        adapter.setData(response, position);
        mExplsvCharts.onRefreshComplete();
    }

//    @Override
    public void onRequestRankSuccess(List<QuoteItem> quoteItems, int positon) {
        adapter.setData(quoteItems, positon);
        mExplsvCharts.onRefreshComplete();
    }

    /**
     * 查询失败
     */
    @Override
    public void onRequestRankError() {

    }

    /**
     * 查询当天新股列表
     * infos的size为4，依次为今日申购，今日中签，未上市和即将发行
     */
    @Override
    public void onRequestCurrentCalendarSuccess(ArrayList<NewShareList> infos) {
        String text = "";
        if (infos != null && infos.size() != 0) {
            for (NewShareList list : infos) {
                if (JRSG.equals(list.getTitle()) && list.getDataSize() > 0) {
                    text = text + list.getDataSize() + "申购";
                }
                if (JRSS.equals(list.getTitle()) && list.getDataSize() > 0) {
                    text = text + list.getDataSize() + "上市";
                }
                if (JRZQ.equals(list.getTitle()) && list.getDataSize() > 0) {
                    text = text + list.getDataSize() + "中签";
                }
            }
            if ("".equals(text)) {
                mTevNewstock.setText("今日暂无");
            } else {
                mTevNewstock.setText(text);
            }

        }
    }

    /**
     * 查询新股日历失败
     */
    @Override
    public void onRequestCurrentCalendarFail() {

    }

    /**
     * 查询当日新债
     * @param bndNewCal
     */
    @Override
    public void onRequestCurrentBndNewSharesCalSuccess(HashMap<String, Object> bndNewCal) {
        String text = "";
        if (null == bndNewCal) {
            return;
        }

        List<HashMap<String, Object>> sgList = (List<HashMap<String, Object>>) bndNewCal.get("sglist");
        List<HashMap<String, Object>> jjsgList = (List<HashMap<String, Object>>) bndNewCal.get("jjsglist");
        List<HashMap<String, Object>> dssList = (List<HashMap<String, Object>>) bndNewCal.get("dsslist");

        if (null != sgList && !sgList.isEmpty()) {
            text = text + sgList.size() + "申购";
        }
        if (null != jjsgList && !jjsgList.isEmpty()) {
            text = text + jjsgList.size() + "待申购";
        }
        if (null != dssList && !dssList.isEmpty()) {
            text = text + dssList.size() + "待上市";
        }

        if ("".equals(text)) {
            tevBndNewStock.setText("今日暂无");
        } else {
            tevBndNewStock.setText(text);
        }
    }

    @Override
    public void onRequestCurrentBndNewSharesCalFail(int code, String msg) {

    }

    /**
     * 查询涨跌平家数成功
     * @param updownsItem
     */
    @Override
    public void onRequestUpDownSameSuccess(UpdownsItem updownsItem) {
        if( null == updownsItem ){
            return;
        }
        String upCount = null == updownsItem.upCount ? FillConfig.DEFALUT : updownsItem.upCount;
        String downCount = null == updownsItem.downCount? FillConfig.DEFALUT : updownsItem.downCount;
        String sameCount = null == updownsItem.sameCount ? FillConfig.DEFALUT : updownsItem.sameCount;

        SpannableStringBuilder span = new SpannableStringBuilder();
        String countText = "上涨"+upCount+"家";
        span.append(countText);
        span.setSpan(new ForegroundColorSpan(ColorUtils.UP),2,2+upCount.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        mTevUpCount.setText(span);
        span.clear();

        countText = "平盘"+sameCount+"家";
        span.append(countText);
        span.setSpan(new ForegroundColorSpan(ColorUtils.GRAY),2,2+sameCount.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        mTevSameCount.setText(span);
        span.clear();

        countText = "下跌"+downCount+"家";
        span.append(countText);
        span.setSpan(new ForegroundColorSpan(ColorUtils.DOWN),2,2+downCount.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        mTevDownCount.setText(span);
        span.clear();
    }

    @Override
    public void onRequestUpDownSameFail() {

    }
}
