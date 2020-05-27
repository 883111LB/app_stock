package com.cvicse.stock.markets.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ScrollView;

import com.cvicse.annotations.MVPInject;
import com.cvicse.base.utils.ToastUtils;
import com.cvicse.stock.R;
import com.cvicse.stock.base.PBaseFragment;
import com.cvicse.stock.markets.adapter.SectionAdapter;
import com.cvicse.stock.markets.presenter.contract.SectionContract;
import com.cvicse.stock.markets.presenter.contract.SectionMoreConstract;
import com.cvicse.stock.pulltorefresh.PullToRefreshBase;
import com.cvicse.stock.pulltorefresh.PullToRefreshScrollView;
import com.cvicse.stock.view.MyGridView;
import com.mitake.core.CateType;
import com.mitake.core.QuoteItem;
import com.mitake.core.bean.log.ErrorInfo;
import com.mitake.core.request.CategoryType;
import com.mitake.core.request.QuoteDetailRequest;
import com.mitake.core.response.Bankuaisorting;
import com.mitake.core.response.IResponseInfoCallback;
import com.mitake.core.response.QuoteResponse;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 板块
 */
public class SectionFragment extends PBaseFragment implements SectionContract.View {
    @MVPInject
    SectionContract.Presenter presenter;

    @BindView(R.id.ptr_section) PullToRefreshScrollView ptrSection;
    @BindView(R.id.grd_hk_trade) MyGridView grdHkTrade;
    @BindView(R.id.grd_industry) MyGridView grdTrade;
    @BindView(R.id.grd_concept) MyGridView grdNotion;
    @BindView(R.id.grd_area) MyGridView grdArea;
    @BindView(R.id.grd_trade_sw1) MyGridView grdTradeSW1;
    @BindView(R.id.grd_trade_sw2) MyGridView grdTradeSW2;
    @BindView(R.id.grd_trade_szyp) MyGridView grdTradsSzyp;
    @BindView(R.id.grd_area_szyp) MyGridView grdAreaSzyp;
    @BindView(R.id.grd_notion_szyp) MyGridView grdNotionSzyp;
    @BindView(R.id.grd_fz) MyGridView grdFz;// 方正行业

    private SectionAdapter sectionTradeAdapter;
    private SectionAdapter sectionNotionAdapter;
    private SectionAdapter sectionAreaAdapter;
    private SectionAdapter sectionHKTradeAdapter;
    private SectionAdapter sectionTradeSW1Adapter;
    private SectionAdapter sectionTradeSW2Adapter;
    private SectionAdapter sectionTradeSzypAdapter;
    private SectionAdapter sectionAreaSzypAdapter;
    private SectionAdapter sectionNotionSzypAdapter;
    private SectionAdapter sectionFzAdapter;// 方正行业

    private String FLAG_FZ = "Trade_fz";// 方正行业

    public static SectionFragment newInstance() {
        return new SectionFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_section;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        initView();
        initListeners();
    }

    private void initView() {
        sectionTradeAdapter = new SectionAdapter(getActivity());
        sectionNotionAdapter = new SectionAdapter(getActivity());
        sectionAreaAdapter = new SectionAdapter(getActivity());
        sectionHKTradeAdapter = new SectionAdapter(getActivity());
        sectionTradeSW1Adapter = new SectionAdapter(getActivity());
        sectionTradeSW2Adapter = new SectionAdapter(getActivity());
        sectionTradeSzypAdapter=new SectionAdapter(getActivity());
        sectionAreaSzypAdapter=new SectionAdapter(getActivity());
        sectionNotionSzypAdapter=new SectionAdapter(getActivity());
        sectionFzAdapter=new SectionAdapter(getActivity());// 方正行业

        grdTrade.setAdapter(sectionTradeAdapter);
        grdNotion.setAdapter(sectionNotionAdapter);
        grdArea.setAdapter(sectionAreaAdapter);
        grdHkTrade.setAdapter(sectionHKTradeAdapter);
        grdTradeSW1.setAdapter(sectionTradeSW1Adapter);
        grdTradeSW2.setAdapter(sectionTradeSW2Adapter);
        grdTradsSzyp.setAdapter(sectionTradeSzypAdapter);
        grdAreaSzyp.setAdapter(sectionAreaSzypAdapter);
        grdNotionSzyp.setAdapter(sectionNotionSzypAdapter);
        grdFz.setAdapter(sectionFzAdapter);// 方正行业
    }

    private void initListeners() {
        ptrSection.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ScrollView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ScrollView> refreshView) {
                requestSection();
                ptrSection.onRefreshComplete();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ScrollView> refreshView) {

            }
        });

        grdTrade.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bankuaisorting bankuaisorting = (Bankuaisorting) sectionTradeAdapter.getItem(position);
//                SectionDetailActivity.newIntent(getActivity(), bankuaisorting.s, bankuaisorting.n);
                toBkInfo(bankuaisorting.ns);
            }
        });

        grdNotion.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bankuaisorting bankuaisorting = (Bankuaisorting) sectionNotionAdapter.getItem(position);
                toBkInfo(bankuaisorting.ns);
//                SectionDetailActivity.newIntent(getActivity(), bankuaisorting.s, bankuaisorting.n);
            }
        });

        grdArea.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bankuaisorting bankuaisorting = (Bankuaisorting) sectionAreaAdapter.getItem(position);
                toBkInfo(bankuaisorting.ns);
//                SectionDetailActivity.newIntent(getActivity(), bankuaisorting.s, bankuaisorting.n);
            }
        });

        grdHkTrade.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bankuaisorting bankuaisorting = (Bankuaisorting) sectionHKTradeAdapter.getItem(position);
//                toBkInfo(bankuaisorting.ns);
                SectionDetailActivity.newIntent(getActivity(), bankuaisorting.s, bankuaisorting.n);
            }
        });
        grdTradeSW1.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bankuaisorting bankuaisorting = (Bankuaisorting) sectionTradeSW1Adapter.getItem(position);
                SectionDetailActivity.newIntent(getActivity(), bankuaisorting.s, bankuaisorting.n);
//                toBkInfo(bankuaisorting.ns);
            }
        });
        grdTradeSW2.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bankuaisorting bankuaisorting = (Bankuaisorting) sectionTradeSW2Adapter.getItem(position);
//                SectionDetailActivity.newIntent(getActivity(), bankuaisorting.s, bankuaisorting.n);
                toBkInfo(bankuaisorting.ns);
            }
        });

        grdTradsSzyp.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bankuaisorting bankuaisorting= (Bankuaisorting) sectionTradeSzypAdapter.getItem(position);
                SectionDetailActivity.newIntent(getActivity(),bankuaisorting.s,bankuaisorting.n);
//                toBkInfo(bankuaisorting.ns);
            }
        });

        grdAreaSzyp.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bankuaisorting bankuaisorting=(Bankuaisorting)sectionAreaSzypAdapter.getItem(position);
                SectionDetailActivity.newIntent(getActivity(),bankuaisorting.s,bankuaisorting.n);
//                toBkInfo(bankuaisorting.ns);
            }
        });

        grdNotionSzyp.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bankuaisorting bankuaisorting=(Bankuaisorting)sectionNotionSzypAdapter.getItem(position);
//                toBkInfo(bankuaisorting.ns);
                SectionDetailActivity.newIntent(getActivity(),bankuaisorting.s,bankuaisorting.n);
            }
        });
        grdFz.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bankuaisorting bankuaisorting=(Bankuaisorting)sectionFzAdapter.getItem(position);
                toBkInfo(bankuaisorting.ns);
//                SectionDetailActivity.newIntent(getActivity(),bankuaisorting.s,bankuaisorting.n);
            }
        });

    }

    @Override
    protected void initData() {
        requestSection();
    }

    private void requestSection() {
        presenter.requestHot();
        presenter.requestConcept();
        presenter.requestArea();
        presenter.requestHKTrade();
        presenter.requestTradeSW1();
        presenter.requestTradeSW2();
        presenter.requestTradeSzyp();
        presenter.requestAreaSzyp();
        presenter.requestNotionSzyp();
        presenter.requestFzSzyp();// 方正行业
    }

    @OnClick({R.id.frm_industry_add, R.id.frm_concept_add, R.id.frm_area_add, R.id.frm_hk_trade_add,
            R.id.frm_trade_sw1_add, R.id.frm_trade_sw2_add, R.id.frm_trade_szyp_add,
            R.id.frm_area_szyp_add, R.id.frm_notion_szyp_add, R.id.frm_fz_add})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.frm_industry_add:
                SectionMoreActivity.setActionIntent(getActivity(), CategoryType.CATE_TRADE);
                break;
            case R.id.frm_concept_add:
                SectionMoreActivity.setActionIntent(getActivity(), CategoryType.CATE_NOTION);
                break;
            case R.id.frm_area_add:
                SectionMoreActivity.setActionIntent(getActivity(), CategoryType.CATE_AREA);
                break;
            case R.id.frm_hk_trade_add:
                SectionMoreActivity.setActionIntent(getActivity(),CategoryType.HK_TRADE);
                break;
            case R.id.frm_trade_sw1_add:
                SectionMoreActivity.setActionIntent(getActivity(),CategoryType.TRADE_SW1);
                break;
            case R.id.frm_trade_sw2_add:
                SectionMoreActivity.setActionIntent(getActivity(),CategoryType.TRADE_SW);
                break;
            case R.id.frm_trade_szyp_add:
                SectionMoreActivity.setActionIntent(getActivity(), CategoryType.TRADE_SZYP);
                break;
            case R.id.frm_area_szyp_add:
                SectionMoreActivity.setActionIntent(getActivity(),CategoryType.AREA_SZYP);
                break;
            case R.id.frm_notion_szyp_add:
                SectionMoreActivity.setActionIntent(getActivity(),CategoryType.NOTION_SZYP);
                break;
            case R.id.frm_fz_add:// 方正行业
                SectionMoreActivity.setActionIntent(getActivity(),FLAG_FZ);
                break;
        }
    }

    /**
     * 请求行业成功
     * @param industryItemList
     */
    @Override
    public void onTradeRequest(List<Bankuaisorting> industryItemList) {
        sectionTradeAdapter.setData(industryItemList);
        ptrSection.onRefreshComplete();
    }

    /**
     * 请求概念成功
     * @param industryItemList
     */
    @Override
    public void onNotionRequest(List<Bankuaisorting> industryItemList) {
        sectionNotionAdapter.setData(industryItemList);
    }

    /**
     * 请求地域成功
     * @param industryItemList
     */
    @Override
    public void onAreaRequest(List<Bankuaisorting> industryItemList) {
        sectionAreaAdapter.setData(industryItemList);
    }

    /**
     * 请求港股行业成功
     * @param industryItemList
     */
    @Override
    public void onHKTradeRequest(List<Bankuaisorting> industryItemList) {
        sectionHKTradeAdapter.setData(industryItemList);
    }
    /**
     * 请求申万一级行业成功
     * @param industryItemList
     */
    @Override
    public void onTradeSW1Request(List<Bankuaisorting> industryItemList) {
        sectionTradeSW1Adapter.setData(industryItemList);
    }
    /**
     * 请求申万二级行业成功
     * @param industryItemList
     */
    @Override
    public void onTradeSW2Request(List<Bankuaisorting> industryItemList) {
        sectionTradeSW2Adapter.setData(industryItemList);
    }
    /**
     * 请求排行榜详细数据失败
     */
    @Override
    public void onRequestFail() {

    }
    /**
     * 请求优品行业成功
     * @param industryItemList
     */
    @Override
    public void onTradeSzypRequest(List<Bankuaisorting> industryItemList) {
        sectionTradeSzypAdapter.setData(industryItemList);
    }
    /**
     * 请求优品地区成功
     * @param industryItemList
     */
    @Override
    public void onAreaSzypRequest(List<Bankuaisorting> industryItemList) {
        sectionAreaSzypAdapter.setData(industryItemList);
    }
    /**
     * 请求优品概念成功
     * @param industryItemList
     */
    @Override
    public void onNotionSzypRequest(List<Bankuaisorting> industryItemList) {
        sectionNotionSzypAdapter.setData(industryItemList);
    }

    /**
     * 请求方正行业成功
     */
    @Override
    public void onFzRequest(List<Bankuaisorting> industryItemList) {
        sectionFzAdapter.setData(industryItemList);
    }

    public void toBkInfo(String code) {
        QuoteDetailRequest quoteDetailRequest = new QuoteDetailRequest();
        quoteDetailRequest.send(code, new IResponseInfoCallback<QuoteResponse>()
        {
            public void callback(QuoteResponse quoteResponse)
            {
                ArrayList<QuoteItem> list = quoteResponse.quoteItems;
                StockDetailActivity.startActivity(getActivity(),list,0);
            }

            public void exception(ErrorInfo errorInfo)
            {
                // TODO  错误处理
                ToastUtils.showLongToast("QuoteDetailRequest:" + errorInfo.getMessage());
            }
        });
    }
}
