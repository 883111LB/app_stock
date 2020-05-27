package com.cvicse.stock.markets.ui.stockdetail;

import android.os.Bundle;
import android.widget.TextView;

import com.cvicse.annotations.MVPInject;
import com.cvicse.stock.R;
import com.cvicse.stock.base.PBaseFragment;
import com.cvicse.stock.common.FillConfig;
import com.cvicse.stock.markets.presenter.marketdetail.contract.FundFinanceContract;
import com.cvicse.stock.utils.TextUtils;

import java.util.HashMap;

import butterknife.BindView;

/**
 * Created by liu_zlu on 2017/2/15 20:00
 * 基金财务
 */
public class FundFinanceFragment extends PBaseFragment implements FundFinanceContract.View {

    @MVPInject
    FundFinanceContract.Presenter presenter;

    private static final String STOCKID = "stockId";

    //公布日期
    @BindView(R.id.tev_publishdate) TextView tevPublishdate;
    //期末日期
    @BindView(R.id.tev_enddate) TextView tevEnddate;
    //单位净收益
    @BindView(R.id.tev_netincome_unit) TextView tevNetincomeUnit;
    //单位资产净值
    @BindView(R.id.tev_netassets_unit) TextView tevNetassetsUnit;
    //净收益
    @BindView(R.id.tev_netincome) TextView tevNetcom;
    //资产净值
    @BindView(R.id.tev_custodian) TextView tevCustodian;
    //净值增长率
    @BindView(R.id.tev_netgrowthrate) TextView tevNetgrowthrate;


    public static FundFinanceFragment newInstance(String stockId) {
        FundFinanceFragment fundFinanceFragment = new FundFinanceFragment();
        Bundle bundle = new Bundle();
        bundle.putString(STOCKID, stockId);
        fundFinanceFragment.setArguments(bundle);
        return fundFinanceFragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_fund_finance;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        presenter.init(getArguments().getString(STOCKID));
    }

    @Override
    protected void initData() {
        presenter.queryFinaMessage();
    }

    /**
     * 查询基金财务成功
     */
    @Override
    public void onFinanceSuccess(HashMap<String, Object> info) {
        if(info == null || info.size() <= 0){
            return;
        }

        TextUtils.setText(tevPublishdate, (String) info.get("PUBLISHDATE"), FillConfig.DEFALUT);
        TextUtils.setText(tevEnddate, (String) info.get("ENDDATE"), FillConfig.DEFALUT);
        TextUtils.setText(tevNetincomeUnit, (String) info.get("UNFDDISTNETINC"), FillConfig.DEFALUT);
        TextUtils.setText(tevNetassetsUnit, (String) info.get("FINAUNFDASSNAV"), FillConfig.DEFALUT);
        TextUtils.setText(tevNetcom, (String) info.get("FDNETPROPER"), FillConfig.DEFALUT);
        TextUtils.setText(tevCustodian, (String) info.get("FINAFDASSETNAV"), FillConfig.DEFALUT);
        TextUtils.setText(tevNetgrowthrate, (String) info.get("NAVGRORATE"), FillConfig.DEFALUT);
    }

    /**
     * 查询基金财务失败
     */
    @Override
    public void onFinanceFail() {

    }

}
