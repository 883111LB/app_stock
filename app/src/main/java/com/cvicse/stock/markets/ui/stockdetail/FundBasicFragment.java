package com.cvicse.stock.markets.ui.stockdetail;

import android.os.Bundle;
import android.widget.TextView;

import com.cvicse.annotations.MVPInject;
import com.cvicse.stock.R;
import com.cvicse.stock.base.PBaseFragment;
import com.cvicse.stock.common.FillConfig;
import com.cvicse.stock.markets.presenter.marketdetail.contract.FundBasicContract;
import com.cvicse.stock.utils.TextUtils;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;

/**
 * 基金简况
 * Created by liu_zlu on 2017/2/15 20:18
 */
public class FundBasicFragment extends PBaseFragment implements FundBasicContract.View {

    @MVPInject
    FundBasicContract.Presenter presenter;

    private static final String STOCKID = "stockId";

    //基金名称
    @BindView(R.id.tev_name) TextView tevName;
    //基金简称
    @BindView(R.id.tev_abbreviation) TextView tevAbbreviation;
    //基金代码
    @BindView(R.id.tev_code) TextView tevCode;
    //基金类型
    @BindView(R.id.tev_type) TextView tevType;
    //成立日期
    @BindView(R.id.tev_establishment_date) TextView tevEstablishmentDate;
    //托管人
    @BindView(R.id.tev_custodian) TextView tevCustodian;
    //管理人
    @BindView(R.id.tev_administrator) TextView tevAdministrator;
    //基金经理
    @BindView(R.id.tev_manager) TextView tevManager;
    //投资类型
    @BindView(R.id.tev_investment_type) TextView tevInvestmentType;
    //投资理念
    @BindView(R.id.tev_investment_philosophy) TextView tevInvestmentPhilosophy;

    //资产支持证券投资
    @BindView(R.id.tev_asset_support) TextView tevAssetSupport;
    //债券投资
    @BindView(R.id.tev_bond_investment) TextView tevBondInvestment;
    //基金投资
    @BindView(R.id.tev_fund_investment) TextView tevFundInvestment;
    //股票投资
    @BindView(R.id.tev_stock_investment) TextView tevStockInvestment;
    //资产总计
    @BindView(R.id.tev_total_assets) TextView tevTotalAssets;
    //应收以及其他资产
    @BindView(R.id.tev_other_assets) TextView tevOtherAssets;

    //期末日期
    @BindView(R.id.tev_enddate) TextView tevEnddate;
    //基金总份额
    @BindView(R.id.tev_total_fund_share) TextView tevTotalFundShare;
    //报告期总申购额
    @BindView(R.id.tev_total_purchase_amount) TextView tevTotalPurchaseAmount;
    //报告期总赎回额
    @BindView(R.id.tev_total_redemption_amount) TextView tevTotalRedemptionAmount;

    //期末日期
    @BindView(R.id.tev_industry_enddate) TextView tevIndustryEnddate;
    //行业类别
    @BindView(R.id.tev_industry_type) TextView tevIndustryType;
    //公允价值(万元)
    @BindView(R.id.tev_fair_value) TextView tevFairValue;
    //占净值比(%)
    @BindView(R.id.tev_accounting_net_ratio) TextView tevAccountingNetRatio;

    //期末日期
    @BindView(R.id.tev_stock_enddate) TextView tevStockEnddate;
    //基金简称
    @BindView(R.id.tev_stock_abbreviation) TextView tevStockAbbreviation;
    //持股(万)
    @BindView(R.id.tev_holding_shares) TextView tevHoldingShares;
    //占比(%)
    @BindView(R.id.tev_accounting_ratio) TextView tevAccountingRatio;
    //价值(万)
    @BindView(R.id.tev_stock_value) TextView tevStockValue;


    public static FundBasicFragment newInstance(String stockId) {
        FundBasicFragment fundBasicFragment = new FundBasicFragment();
        Bundle bundle = new Bundle();
        bundle.putString(STOCKID, stockId);
        fundBasicFragment.setArguments(bundle);
        return fundBasicFragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_fund_basic;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        presenter.init(getArguments().getString(STOCKID));
    }

    @Override
    protected void initData() {
        presenter.queryFundData();
    }

    /**
     * 查询基金概况成功
     */
    @Override
    public void onQueryFundBasicSuccess(HashMap<String, Object> info) {
        if(info == null || info.size() <= 0){
            return;
        }
        TextUtils.setText(tevName,(String)info.get("FDNAME"), FillConfig.DEFALUT);
        TextUtils.setText(tevAbbreviation,(String)info.get("FDSNAME"), FillConfig.DEFALUT);
        TextUtils.setText(tevCode,(String)info.get("FSYMBOL"), FillConfig.DEFALUT);
        TextUtils.setText(tevType,(String)info.get("FDTYPE"), FillConfig.DEFALUT);
        TextUtils.setText(tevEstablishmentDate,(String)info.get("FOUNDDATE"), FillConfig.DEFALUT);
        TextUtils.setText(tevCustodian,(String)info.get("TRUSTEENAME"), FillConfig.DEFALUT);
        TextUtils.setText(tevAdministrator,(String)info.get("KEEPERNAME"), FillConfig.DEFALUT);
        TextUtils.setText(tevManager,(String)info.get("MANAGERNAME"), FillConfig.DEFALUT);
        TextUtils.setText(tevInvestmentType,(String)info.get("FDINVCATEGORY"), FillConfig.DEFALUT);
        TextUtils.setText(tevInvestmentPhilosophy,(String)info.get("INVRULE"), FillConfig.DEFALUT);
    }

    /**
     * 查询资产配置成功
     */
    @Override
    public void onQueryFundAssetAllocationSuccess( List<HashMap<String, Object>> infos) {
        if(infos == null || infos.size() <= 0){
            return;
        }

        //取第一条数据
        HashMap<String,Object> map = infos.get(0);

        TextUtils.setText(tevAssetSupport,(String)map.get("ASSESECI"), FillConfig.DEFALUT);
        TextUtils.setText(tevBondInvestment,(String)map.get("BDINVE"), FillConfig.DEFALUT);
        TextUtils.setText(tevFundInvestment,(String)map.get("FDINVE"), FillConfig.DEFALUT);
        TextUtils.setText(tevStockInvestment,(String)map.get("STKINVE"), FillConfig.DEFALUT);
        TextUtils.setText(tevTotalAssets,(String)map.get("TOTASSET"), FillConfig.DEFALUT);
        TextUtils.setText(tevOtherAssets,(String)map.get("TOTASSETYOY"), FillConfig.DEFALUT);
    }

    /**
     * 查询份额结构成功
     */
    @Override
    public void onQueryFundShareStructureSuccess(List<HashMap<String, Object>> infos) {
        if(infos == null || infos.size() <= 0){
            return;
        }

        //取第一条数据
        HashMap<String,Object> map = infos.get(0);

        TextUtils.setText(tevEnddate,(String)map.get("ENDDATE"), FillConfig.DEFALUT);
        TextUtils.setText(tevTotalFundShare,(String)map.get("ENDFDSHARE"), FillConfig.DEFALUT);
        TextUtils.setText(tevTotalPurchaseAmount,(String)map.get("SUBSHARETOT"), FillConfig.DEFALUT);
        TextUtils.setText(tevTotalRedemptionAmount,(String)map.get("REDTOTSHARE"), FillConfig.DEFALUT);
    }

    /**
     * 查询行业组合成功
     */
    @Override
    public void onQueryFundIndustryPortfolioSuccess(List<HashMap<String, Object>> infos) {
        if(infos == null || infos.size() <= 0){
            return;
        }
        //取第一条数据
        HashMap<String,Object> map = infos.get(0);

        TextUtils.setText(tevIndustryEnddate,(String)map.get("ENDDATE"), FillConfig.DEFALUT);
        TextUtils.setText(tevIndustryType,(String)map.get("INDUSTRYNAME"), FillConfig.DEFALUT);
        TextUtils.setText(tevFairValue,(String)map.get("FAIRVALUE"), FillConfig.DEFALUT);
        TextUtils.setText(tevAccountingNetRatio,(String)map.get("NAVRATIO"), FillConfig.DEFALUT);

    }

    /**
     * 查询股票组合成功
     */
    @Override
    public void onQueryFundStockPortfolioSuccess(List<HashMap<String, Object>> infos) {
        if(infos == null || infos.size() <= 0){
            return;
        }

        //取第一条数据
        HashMap<String,Object> map = infos.get(0);

        TextUtils.setText(tevStockEnddate,(String)map.get("ENDDATE"), FillConfig.DEFALUT);
        TextUtils.setText(tevStockAbbreviation,(String)map.get("FDSNAME"), FillConfig.DEFALUT);
        TextUtils.setText(tevHoldingShares,(String)map.get("HOLDVOL"), FillConfig.DEFALUT);
        TextUtils.setText(tevAccountingRatio,(String)map.get("ACCTFORNAV"), FillConfig.DEFALUT);
        TextUtils.setText(tevStockValue,(String)map.get("HOLDVALUE"), FillConfig.DEFALUT);
    }
}
