package com.cvicse.stock.markets.helper;

import android.content.Context;
import android.view.View;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.cvicse.stock.R;
import com.cvicse.stock.markets.adapter.StockBigEventAdapter;
import com.cvicse.stock.markets.ui.stockdetail.StockSummaryBigEventActivity;
import com.cvicse.stock.pulltorefresh.PullToRefreshBase;
import com.cvicse.stock.pulltorefresh.PullToRefreshListView;
import com.mitake.core.request.F10Type;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/** 大事提醒
 * Created by tang_xqing on 2018/4/23.
 */
public class StockSummaryBigEventHelper {

    @BindView(R.id.rdg_big_event)
    RadioGroup rdgBigEvent;
    @BindView(R.id.lv_big_event)
    PullToRefreshListView lvBigEvent;
    @BindView(R.id.tev_empty)    TextView tevEmpty;

    private String apiType = F10Type.IMPORT_NOTICE_DATA ;
    private String type = "date";
    private Context mContext;
    private StockBigEventAdapter bigEventAdapter;

    private StockSummaryBigEventActivity.IRequestBigEvent iRequestBigEvent;

    public StockSummaryBigEventHelper(View view,Context context){
        ButterKnife.bind(this, view);
        this.mContext = context;

        initView();
    }

    private void initView() {
        lvBigEvent.setMode(PullToRefreshBase.Mode.PULL_FROM_END);
        lvBigEvent.setEmptyView(tevEmpty);
        bigEventAdapter = new StockBigEventAdapter(mContext,type);
        lvBigEvent.setAdapter(bigEventAdapter);

        lvBigEvent.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                // 上拉，加载更多
                iRequestBigEvent.loadBigEvent(apiType);
            }
        });

        rdgBigEvent.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.rdb_date:
                        apiType = F10Type.IMPORT_NOTICE_DATA;
                        type = "date";
                        break;
                    case R.id.rdb_title:
                        apiType = F10Type.IMPORT_NOTICE_TITLE;
                        type = "title";
                        break;
                }
                // 按时间/标题 重新请求
                bigEventAdapter.clearData();
                iRequestBigEvent.requestBigEvent(apiType);
            }
        });
    }

    public void setBigEventData(List<HashMap<String,Object>> infoList){
        lvBigEvent.onRefreshComplete();
        bigEventAdapter.setData(type,infoList);
    }

    public void setiRequestBigEvent(StockSummaryBigEventActivity.IRequestBigEvent iRequestBigEvent){
        this.iRequestBigEvent = iRequestBigEvent;
    }
}
