package com.cvicse.stock.markets.ui.stockdetail;

import android.os.Bundle;
import android.view.ViewStub;
import android.widget.TextView;

import com.cvicse.annotations.MVPInject;
import com.cvicse.stock.R;
import com.cvicse.stock.base.PBaseFragment;
import com.cvicse.stock.markets.helper.StockFinanceHKHelper;
import com.cvicse.stock.markets.helper.StockFinanceSHSZHelper;
import com.cvicse.stock.markets.presenter.marketdetail.contract.StockFinanceContract;
import com.cvicse.stock.utils.TextUtils;
import com.mitake.core.MainFinaDataNas;
import com.mitake.core.MainFinaIndexHas;
import com.mitake.core.QuoteItem;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;

/**
 * 财务页面
 * Created by liu_zlu on 2017/2/10 11:47
 */
public class StockFinanceFragment extends PBaseFragment implements StockFinanceContract.View {
    private static final String STOCKID = "STOCKID";

    @MVPInject
    StockFinanceContract.Presenter presenter;

    @BindView(R.id.tev_mainFinaIndexDate)
    TextView tevMainFinaIndexDate;
    @BindView(R.id.tev_basiceps)
    TextView tevBasiceps;
    @BindView(R.id.tev_reserveps)
    TextView tevReserveps;
    @BindView(R.id.tev_bvps)
    TextView tevBvps;
    @BindView(R.id.tev_totaloperincomeps)
    TextView tevTotaloperincomeps;
    @BindView(R.id.tev_ebitps)
    TextView tevEbitps;
    @BindView(R.id.tev_retainedearningps)
    TextView tevRetainedearningps;
    @BindView(R.id.tev_netcashflowoperps)
    TextView tevNetcashflowoperps;
    @BindView(R.id.tev_netcashflowps)
    TextView tevNetcashflowps;
    @BindView(R.id.tev_weightedroe)
    TextView tevWeightedroe;
    @BindView(R.id.tev_roa_ebit)
    TextView tevRoaEbit;
    @BindView(R.id.tev_grossprofitmargin_)
    TextView tevGrossprofitmargin;
    @BindView(R.id.tev_profitmargin_)
    TextView tevProfitmargin;
    @BindView(R.id.tev_tltota)
    TextView tevTltota;
    @BindView(R.id.tev_tatoshe)
    TextView tevTatoshe;
    @BindView(R.id.tev_currentratio)
    TextView tevCurrentratio;
    @BindView(R.id.tev_quickratio)
    TextView tevQuickratio;
    @BindView(R.id.tev_ebittoie)
    TextView tevEbittoie;
    @BindView(R.id.tev_inventoryturnover)
    TextView tevInventoryturnover;
    @BindView(R.id.tev_accountrecturnover)
    TextView tevAccountrecturnover;
    @BindView(R.id.tev_fixedassetturnover)
    TextView tevFixedassetturnover;
    @BindView(R.id.tev_totalassetturnover)
    TextView tevTotalassetturnover;
    @BindView(R.id.tev_operrevenueyoy_)
    TextView tevOperrevenueyoy;
    @BindView(R.id.tev_operprofityoy)
    TextView tevOperprofityoy;
    @BindView(R.id.tev_netprofitparentcomyoy_)
    TextView tevNetprofitparentcomyoy;
    @BindView(R.id.tev_netcashflowoperyoy)
    TextView tevNetcashflowoperyoy;
    @BindView(R.id.tev_roeyoy)
    TextView tevRoeyoy;
    @BindView(R.id.tev_netassetyoy)
    TextView tevNetassetyoy;
    @BindView(R.id.tev_totalassetyoy)
    TextView tevTotalassetyoy;

    @BindView(R.id.vs_finance_shsz)
    ViewStub vsFinanceShsz;    //沪深财务报表

    @BindView(R.id.vs_finance_hk)
    ViewStub vsFinanceHK;    //港股财务报表

    private QuoteItem mQuoteItem;
    private StockFinanceSHSZHelper mFinanceSHSZHelper; // 沪深财务报表帮助类
    private StockFinanceHKHelper mFinanceHKHelper;  // 港股财务报表帮助类

    public static StockFinanceFragment newInstance(QuoteItem quoteItem) {
        StockFinanceFragment stockFinanceFragment = new StockFinanceFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(STOCKID, quoteItem);
        stockFinanceFragment.setArguments(bundle);
        return stockFinanceFragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_stock_finance;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        mQuoteItem = (QuoteItem) getArguments().getParcelable(STOCKID);

        if( "hk".equals(mQuoteItem.market) ){
            mFinanceHKHelper = new StockFinanceHKHelper(vsFinanceHK.inflate(),getContext(), mQuoteItem);
        }else{
            mFinanceSHSZHelper = new StockFinanceSHSZHelper(vsFinanceShsz.inflate(),getContext(), mQuoteItem);
        }
        presenter.init(mQuoteItem);
    }

    @Override
    protected void initData() {
        presenter.queryFinance();
    }

    /**
     * 查询财务指标成功
     *
     * @param mainFinaIndexHas
     */
    @Override
    public void onMainFinaIndexHasSuccess(MainFinaIndexHas mainFinaIndexHas) {
        if (mainFinaIndexHas == null) {
            return;
        }
        TextUtils.setText(tevMainFinaIndexDate, mainFinaIndexHas.REPORTTITLE_);
        TextUtils.setText(tevBasiceps, mainFinaIndexHas.BasicEPS);
        TextUtils.setText(tevReserveps, mainFinaIndexHas.RESERVEPS_);
        TextUtils.setText(tevBvps, mainFinaIndexHas.BVPS_);
        TextUtils.setText(tevTotaloperincomeps, mainFinaIndexHas.TotalOperIncomePS);
        TextUtils.setText(tevEbitps, mainFinaIndexHas.EBITPS_);
        TextUtils.setText(tevRetainedearningps, mainFinaIndexHas.RetainedEarningPS);
        TextUtils.setText(tevNetcashflowoperps, mainFinaIndexHas.NetCashFlowOperPS);
        TextUtils.setText(tevNetcashflowps, mainFinaIndexHas.NETCASHFLOWPS_);
        TextUtils.setText(tevWeightedroe, mainFinaIndexHas.WEIGHTEDROE_);
        TextUtils.setText(tevRoaEbit, mainFinaIndexHas.ROA_EBIT_);
        TextUtils.setText(tevGrossprofitmargin, mainFinaIndexHas.GROSSPROFITMARGIN_);
        TextUtils.setText(tevProfitmargin, mainFinaIndexHas.PROFITMARGIN_);
        TextUtils.setText(tevTltota, mainFinaIndexHas.TLToTA_);
        TextUtils.setText(tevTatoshe, mainFinaIndexHas.TAToSHE_);
        TextUtils.setText(tevCurrentratio, mainFinaIndexHas.CurrentRatio);
        TextUtils.setText(tevQuickratio, mainFinaIndexHas.QuickRatio);
        TextUtils.setText(tevEbittoie, mainFinaIndexHas.EBITToIE_);
        TextUtils.setText(tevInventoryturnover, mainFinaIndexHas.InventoryTurnover);
        TextUtils.setText(tevAccountrecturnover, mainFinaIndexHas.ACCOUNTRECTURNOVER_);
        TextUtils.setText(tevFixedassetturnover, mainFinaIndexHas.FixedAssetTurnover);
        TextUtils.setText(tevTotalassetturnover, mainFinaIndexHas.TotalAssetTurnover);
        TextUtils.setText(tevOperrevenueyoy, mainFinaIndexHas.OperRevenueYOY);
        TextUtils.setText(tevOperprofityoy, mainFinaIndexHas.OperProfitYOY);
        TextUtils.setText(tevNetprofitparentcomyoy, mainFinaIndexHas.NETPROFITPARENTCOMYOY_);
        TextUtils.setText(tevNetcashflowoperyoy, mainFinaIndexHas.NetCashFlowOperYOY);
        TextUtils.setText(tevRoeyoy, mainFinaIndexHas.ROEYOY_);
        TextUtils.setText(tevNetassetyoy, mainFinaIndexHas.NetAssetYOY);
        TextUtils.setText(tevTotalassetyoy, mainFinaIndexHas.TotalAssetYOY);
    }

    /**
     * 查询财务报表成功
     *
     * @param
     */
    @Override
    public void onMainFinaDataNasSuccess(List<MainFinaDataNas> mainFinaDataNasList) {
        if (null == mainFinaDataNasList || mainFinaDataNasList.isEmpty()) {
            return;
        }
        mFinanceHKHelper.onMainFinaDataNasSuccess(mainFinaDataNasList);
    }

    /**
     * 主要指标
     *
     * @param infoList
     */
    @Override
    public void onProfinmainindexSuccess(HashMap<String, Object> infoList) {
        mFinanceSHSZHelper.onProfinmainindexSuccess(infoList);
    }

    /**
     * 利润表
     *
     * @param infoList
     */
    @Override
    public void onProincstatementnewSuccess(HashMap<String, Object> infoList) {
        mFinanceSHSZHelper.onProincstatementnewSuccess(infoList);
    }

    /**
     * 资产负债表
     *
     * @param infoList
     */
    @Override
    public void onProbalsheetnewSuccess(HashMap<String, Object> infoList) {
        mFinanceSHSZHelper.onProbalsheetnewSuccess(infoList);
    }

    /**
     * 现金流量表
     *
     * @param infoList
     */
    @Override
    public void onProcfstatementnewSuccess(HashMap<String, Object> infoList) {
        mFinanceSHSZHelper.onProcfstatementnewSuccess(infoList);
    }

}
