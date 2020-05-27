package com.cvicse.stock.markets.ui.stockdetail;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.cvicse.annotations.MVPInject;
import com.cvicse.base.widget.FixedListView;
import com.cvicse.stock.R;
import com.cvicse.stock.base.PBaseFragment;
import com.cvicse.stock.markets.adapter.IndexRankingAdapter;
import com.cvicse.stock.markets.data.ParamUtil;
import com.cvicse.stock.markets.presenter.marketdetail.contract.IndexRankingContract;
import com.cvicse.stock.markets.ui.RankingListActivity;
import com.cvicse.stock.markets.ui.RankingListAllActivity;
import com.cvicse.stock.markets.ui.StockDetailActivity;
import com.mitake.core.CateType;
import com.mitake.core.QuoteItem;
import com.mitake.core.request.CateRankingType;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 排行页面
 * Created by liu_zlu on 2017/2/13 08:51
 */
public class IndexRankingFragment extends PBaseFragment implements IndexRankingContract.View {
    @MVPInject
    IndexRankingContract.Presenter presenter;

    @BindView(R.id.lv_stock_indexranking) FixedListView lvStockIndexranking;
    @BindView(R.id.tev_loadmore) TextView tevLoadmore;

    private static String STOCKID = "stockId";
    private static String TYPE = "type";

    public final static String TYPEINCREASE = CateRankingType.RISE_RANKING;
    public final static String TYPEDECLINE = CateRankingType.REVCATE_RANKING;
    public final static String TYPETURNOVERRATE = "SHSZTurnoverRate";
    public final static String TYPETURNOVER = "SHSZTurnover";

    private String stockId = "";
    private String type = "";
    private  IndexRankingAdapter indexRankingAdapter;

    public static IndexRankingFragment newInstance(String stockId, String type) {
        IndexRankingFragment indexRankingFragment = new IndexRankingFragment();
        Bundle bundle = new Bundle();
        bundle.putString(STOCKID, changeIndexData(stockId));
        bundle.putString(TYPE, type);
        indexRankingFragment.setArguments(bundle);
        return indexRankingFragment;
    }

    private static String changeIndexData(String stockId){
        switch (stockId){
            case "HSI.hk" : stockId = CateType.HSI;break;  // 恒生指数
            case "HSCEI.hk" : stockId = CateType.HKGQ;break;  // 国企股
            case "HSCCI.hk" : stockId = CateType.HKHC;break;  // 红筹股
        }
        return stockId;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_stock_indexranking;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        stockId= bundle.getString(STOCKID);
        type= bundle.getString(TYPE);
        presenter.init(stockId, type);
        indexRankingAdapter = new IndexRankingAdapter(getActivity(),bundle.getString(TYPE));
        lvStockIndexranking.setAdapter(indexRankingAdapter);
        lvStockIndexranking.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                StockDetailActivity.startActivity(getActivity(),indexRankingAdapter.getData(),position);
            }
        });
    }

    @Override
    protected void initData() {

    }

    @OnClick(R.id.tev_loadmore)
    public void onClick(View view){
        switch (view.getId()){
            case R.id.tev_loadmore:
                String code = "";
                switch (type){
                    case IndexRankingFragment.TYPEINCREASE:
                        // 深沪市场
                        if ("sh".equals(indexRankingAdapter.getData().get(0).market) || "sz".equals(indexRankingAdapter.getData().get(0).market)) {
                            code = ParamUtil.RANKING_TYPE_RAISE_HSSC;
                        } else {
                            code = ParamUtil.RANKING_TYPE_RAISE;
                        }
                        break;
                    case IndexRankingFragment.TYPEDECLINE:
                        // 深沪市场
                        if ("sh".equals(indexRankingAdapter.getItem(0).market) || "sz".equals(indexRankingAdapter.getData().get(0).market)) {
                            code = ParamUtil.RANKING_TYPE_DECLINE_HSSC;
                        } else {
                            code = ParamUtil.RANKING_TYPE_DECLINE;
                        }
                        break;
                    case IndexRankingFragment.TYPETURNOVERRATE:
                        code = ParamUtil.RANKING_TYPE_TURNOVERRATE;
                        break;
                    case IndexRankingFragment.TYPETURNOVER:
                        // 深沪市场
                        if ("sh".equals(indexRankingAdapter.getItem(0).market) || "sz".equals(indexRankingAdapter.getData().get(0).market)) {
                            code = ParamUtil.RANKING_TYPE_AMOUNT_HSSC;
                        } else {
                            code = ParamUtil.RANKING_TYPE_AMOUNT;
                        }
                        break;
                }
                RankingListAllActivity.actionStart(getActivity(),stockId,code);
                break;
        }
    }

    @Override
    public void onCateRankingSuccess(ArrayList<QuoteItem> quoteItems) {
        tevLoadmore.setVisibility(View.VISIBLE);
        if(null == quoteItems || quoteItems.isEmpty()){
            tevLoadmore.setText("暂无排行信息");
            return;
        }
        indexRankingAdapter.setData(quoteItems);
        tevLoadmore.setText("查看更多");
    }

    /**
     * 获取排行信息失败
     */
    @Override
    public void onCateRankingFail() {
        tevLoadmore.setVisibility(View.VISIBLE);
    }

    @Override
    public void onDataSuccess(String stockType,String rankingType) {
        RankingListActivity.actionStart(getActivity(),stockType,rankingType);
    }
}
