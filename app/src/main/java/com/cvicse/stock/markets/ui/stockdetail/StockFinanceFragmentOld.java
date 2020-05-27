package com.cvicse.stock.markets.ui.stockdetail;

import android.os.Bundle;
import android.widget.TextView;

import com.cvicse.annotations.MVPInject;
import com.cvicse.stock.R;
import com.cvicse.stock.base.PBaseFragment;
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
public class StockFinanceFragmentOld extends PBaseFragment implements StockFinanceContract.View{
    private static final String STOCKID = "STOCKID";

    @BindView(R.id.tev_mainFinaIndexDate) TextView tevMainFinaIndexDate;
    @BindView(R.id.tev_basiceps) TextView tevBasiceps;
    @BindView(R.id.tev_reserveps) TextView tevReserveps;
    @BindView(R.id.tev_bvps) TextView tevBvps;
    @BindView(R.id.tev_totaloperincomeps) TextView tevTotaloperincomeps;
    @BindView(R.id.tev_ebitps) TextView tevEbitps;
    @BindView(R.id.tev_retainedearningps) TextView tevRetainedearningps;
    @BindView(R.id.tev_netcashflowoperps) TextView tevNetcashflowoperps;
    @BindView(R.id.tev_netcashflowps) TextView tevNetcashflowps;
    @BindView(R.id.tev_weightedroe) TextView tevWeightedroe;
    @BindView(R.id.tev_roa_ebit) TextView tevRoaEbit;
    @BindView(R.id.tev_grossprofitmargin_) TextView tevGrossprofitmargin;
    @BindView(R.id.tev_profitmargin_) TextView tevProfitmargin;
    @BindView(R.id.tev_tltota) TextView tevTltota;
    @BindView(R.id.tev_tatoshe) TextView tevTatoshe;
    @BindView(R.id.tev_currentratio) TextView tevCurrentratio;
    @BindView(R.id.tev_quickratio) TextView tevQuickratio;
    @BindView(R.id.tev_ebittoie) TextView tevEbittoie;
    @BindView(R.id.tev_inventoryturnover) TextView tevInventoryturnover;
    @BindView(R.id.tev_accountrecturnover) TextView tevAccountrecturnover;
    @BindView(R.id.tev_fixedassetturnover) TextView tevFixedassetturnover;
    @BindView(R.id.tev_totalassetturnover) TextView tevTotalassetturnover;
    @BindView(R.id.tev_operrevenueyoy_) TextView tevOperrevenueyoy;
    @BindView(R.id.tev_operprofityoy) TextView tevOperprofityoy;
    @BindView(R.id.tev_netprofitparentcomyoy_) TextView tevNetprofitparentcomyoy;
    @BindView(R.id.tev_netcashflowoperyoy) TextView tevNetcashflowoperyoy;
    @BindView(R.id.tev_roeyoy) TextView tevRoeyoy;
    @BindView(R.id.tev_netassetyoy) TextView tevNetassetyoy;
    @BindView(R.id.tev_totalassetyoy) TextView tevTotalassetyoy;
    @BindView(R.id.tev_mainFinaDataDate) TextView tevMainFinaDataDate;
    @BindView(R.id.tev_basiceps_report) TextView tevBasicepsReport;
    @BindView(R.id.tev_bvps_report) TextView tevBvpsReport;
    @BindView(R.id.tev_netcashflowoperps_report) TextView tevNetcashflowoperpsReport;
    @BindView(R.id.tev_weightedroe_report) TextView tevWeightedroeReport;
    @BindView(R.id.tev_roa_report) TextView tevRoaReport;
    @BindView(R.id.tev_totaloperrevenue_report) TextView tevTotaloperrevenueReport;
    @BindView(R.id.tev_operprofit_report) TextView tevOperprofitReport;
    @BindView(R.id.tev_netprofit_report) TextView tevNetprofitReport;
    @BindView(R.id.tev_totalasset_report) TextView tevTotalassetReport;
    @BindView(R.id.tev_totalliab_report) TextView tevTotalliabReport;
    @BindView(R.id.tev_totalshequity_report) TextView tevTotalshequityReport;
    @BindView(R.id.tev_netcashflowoper_report) TextView tevNetcashflowoperReport;
    @BindView(R.id.tev_netcashflowinv_report) TextView tevNetcashflowinvReport;
    @BindView(R.id.tev_netcashflowfina_report) TextView tevNetcashflowfinaReport;
    @BindView(R.id.tev_cashequinetincr_report) TextView tevCashequinetincrReport;

    @MVPInject
    StockFinanceContract.Presenter presenter;
    public static StockFinanceFragmentOld newInstance(QuoteItem quoteItem) {
        StockFinanceFragmentOld stockFinanceFragment = new StockFinanceFragmentOld();
        Bundle bundle = new Bundle();
        bundle.putParcelable(STOCKID, quoteItem);
        stockFinanceFragment.setArguments(bundle);
        return stockFinanceFragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_stock_finance2;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {

        presenter.init((QuoteItem) getArguments().getParcelable(STOCKID));
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
        if(mainFinaIndexHas == null){
            return;
        }
        TextUtils.setText(tevMainFinaIndexDate,mainFinaIndexHas.REPORTTITLE_);
        TextUtils.setText(tevBasiceps,mainFinaIndexHas.BasicEPS);
        TextUtils.setText(tevReserveps,mainFinaIndexHas.RESERVEPS_);
        TextUtils.setText(tevBvps,mainFinaIndexHas.BVPS_);
        TextUtils.setText(tevTotaloperincomeps,mainFinaIndexHas.TotalOperIncomePS);
        TextUtils.setText(tevEbitps,mainFinaIndexHas.EBITPS_);
        TextUtils.setText(tevRetainedearningps,mainFinaIndexHas.RetainedEarningPS);
        TextUtils.setText(tevNetcashflowoperps,mainFinaIndexHas.NetCashFlowOperPS);
        TextUtils.setText(tevNetcashflowps,mainFinaIndexHas.NETCASHFLOWPS_);
        TextUtils.setText(tevWeightedroe,mainFinaIndexHas.WEIGHTEDROE_);
        TextUtils.setText(tevRoaEbit,mainFinaIndexHas.ROA_EBIT_);
        TextUtils.setText(tevGrossprofitmargin,mainFinaIndexHas.GROSSPROFITMARGIN_);
        TextUtils.setText(tevProfitmargin,mainFinaIndexHas.PROFITMARGIN_);
        TextUtils.setText(tevTltota,mainFinaIndexHas.TLToTA_);
        TextUtils.setText(tevTatoshe,mainFinaIndexHas.TAToSHE_);
        TextUtils.setText(tevCurrentratio,mainFinaIndexHas.CurrentRatio);
        TextUtils.setText(tevQuickratio,mainFinaIndexHas.QuickRatio);
        TextUtils.setText(tevEbittoie,mainFinaIndexHas.EBITToIE_);
        TextUtils.setText(tevInventoryturnover,mainFinaIndexHas.InventoryTurnover);
        TextUtils.setText(tevAccountrecturnover,mainFinaIndexHas.ACCOUNTRECTURNOVER_);
        TextUtils.setText(tevFixedassetturnover,mainFinaIndexHas.FixedAssetTurnover);
        TextUtils.setText(tevTotalassetturnover,mainFinaIndexHas.TotalAssetTurnover);
        TextUtils.setText(tevOperrevenueyoy,mainFinaIndexHas.OperRevenueYOY);
        TextUtils.setText(tevOperprofityoy,mainFinaIndexHas.OperProfitYOY);
        TextUtils.setText(tevNetprofitparentcomyoy,mainFinaIndexHas.NETPROFITPARENTCOMYOY_);
        TextUtils.setText(tevNetcashflowoperyoy,mainFinaIndexHas.NetCashFlowOperYOY);
        TextUtils.setText(tevRoeyoy,mainFinaIndexHas.ROEYOY_);
        TextUtils.setText(tevNetassetyoy,mainFinaIndexHas.NetAssetYOY);
        TextUtils.setText(tevTotalassetyoy,mainFinaIndexHas.TotalAssetYOY);
    }

    @Override
    public void onMainFinaDataNasSuccess(List<MainFinaDataNas> mainFinaDataNasList) {

    }

    /**
     * 查询财务报表成功
     *
     * @param mainFinaDataNas
     */
//    @Override
    public void onMainFinaDataNasSuccess(MainFinaDataNas mainFinaDataNas) {
        if(mainFinaDataNas == null){
            return;
        }
        TextUtils.setText(tevMainFinaDataDate,mainFinaDataNas.REPORTTITLE_);
        TextUtils.setText(tevBasicepsReport,mainFinaDataNas.BasicEPS);
        TextUtils.setText(tevBvpsReport,mainFinaDataNas.BVPS_);
        TextUtils.setText(tevNetcashflowoperpsReport,mainFinaDataNas.NETCASHFLOWOPERPS_);
        TextUtils.setText(tevWeightedroeReport,mainFinaDataNas.WEIGHTEDROE_);
        TextUtils.setText(tevRoaReport,mainFinaDataNas.ROA_);
        TextUtils.setText(tevTotaloperrevenueReport,mainFinaDataNas.TotalOperRevenue);
        TextUtils.setText(tevOperprofitReport,mainFinaDataNas.OperProfit);
        TextUtils.setText(tevNetprofitReport,mainFinaDataNas.NetProfit);
        TextUtils.setText(tevTotalassetReport,mainFinaDataNas.TotalAsset);
        TextUtils.setText(tevTotalliabReport,mainFinaDataNas.TotalLiab);
        TextUtils.setText(tevTotalshequityReport,mainFinaDataNas.TotalSHEquity);
        TextUtils.setText(tevNetcashflowoperReport,mainFinaDataNas.NetCashFlowOper);
        TextUtils.setText(tevNetcashflowinvReport,mainFinaDataNas.NetCashFlowInv);
        TextUtils.setText(tevNetcashflowfinaReport,mainFinaDataNas.NetCashFlowFina);
        TextUtils.setText(tevCashequinetincrReport,mainFinaDataNas.CashEquiNetIncr);
    }

    @Override
    public void onProfinmainindexSuccess(HashMap<String, Object> infoList) {

    }

    @Override
    public void onProincstatementnewSuccess(HashMap<String, Object> infoList) {

    }

    @Override
    public void onProbalsheetnewSuccess(HashMap<String, Object> infoList) {

    }

    @Override
    public void onProcfstatementnewSuccess(HashMap<String, Object> infoList) {

    }
}
