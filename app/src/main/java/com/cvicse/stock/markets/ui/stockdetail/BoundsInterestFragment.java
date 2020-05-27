package com.cvicse.stock.markets.ui.stockdetail;

import android.os.Bundle;
import android.widget.TextView;

import com.cvicse.annotations.MVPInject;
import com.cvicse.stock.R;
import com.cvicse.stock.base.PBaseFragment;
import com.cvicse.stock.common.FillConfig;
import com.cvicse.stock.markets.presenter.marketdetail.contract.BoundsInterestContract;
import com.cvicse.stock.utils.TextUtils;

import java.util.HashMap;

import butterknife.BindView;

/**
 * 付息页面
 * Created by liu_zlu on 2017/2/15 22:09
 */
public class BoundsInterestFragment extends PBaseFragment implements BoundsInterestContract.View {

    @MVPInject
    BoundsInterestContract.Presenter presenter;

    private static final String STOCKID = "stockId";

    //债券名称
    @BindView(R.id.tev_boundsname) TextView tevBoundsname;
    //债券简称
    @BindView(R.id.tev_abbreviation) TextView tevAbbreviation;
    //债券代码
    @BindView(R.id.tev_code) TextView tevCode;
    //付息年度
    @BindView(R.id.tev_interest_rate_year) TextView tevInterestRateYear;
    //每百元面值所得利息
    @BindView(R.id.tev_hundred_interest) TextView tevHundredInterest;

    //本期利息(%)
    @BindView(R.id.tev_period_interest) TextView tevPeriodInterest;
    //债券登记日
    @BindView(R.id.tev_registration_date) TextView tevRegistrationDate;
    //利息支付日
    @BindView(R.id.tev_payment_day) TextView tevPaymentDay;
    //除息基准日
    @BindView(R.id.tev_base_date) TextView tevBaseDate;
    //集中兑付日
    @BindView(R.id.tev_redemption_date) TextView tevRedemptionDate;


    public static BoundsInterestFragment newInstance(String stockId) {
        BoundsInterestFragment boundsInterestFragment = new BoundsInterestFragment();
        Bundle bundle = new Bundle();
        bundle.putString(STOCKID, stockId);
        boundsInterestFragment.setArguments(bundle);
        return boundsInterestFragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_bunds_interest;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        presenter.init(getArguments().getString(STOCKID));
    }

    @Override
    protected void initData() {
        presenter.queryBundInterestPay();
    }

    /**
     * 查询债券付息情况成功
     */
    @Override
    public void onqueryBundInterestPaySuccess(HashMap<String, Object> info) {
        if (info == null || info.size() <= 0) {
            return;
        }

        TextUtils.setText(tevBoundsname, (String) info.get("BONDNAME"), FillConfig.DEFALUT);
        TextUtils.setText(tevAbbreviation, (String) info.get("BONDSNAME"), FillConfig.DEFALUT);
        TextUtils.setText(tevCode, (String) info.get("SYMBOL"), FillConfig.DEFALUT);
        TextUtils.setText(tevInterestRateYear, (String) info.get("PERPAYDATEYEAR"), FillConfig.DEFALUT);
        TextUtils.setText(tevHundredInterest, (String) info.get("PRETAXINT"), FillConfig.DEFALUT);

        TextUtils.setText(tevPeriodInterest, (String) info.get("IPRATE"), FillConfig.DEFALUT);
        TextUtils.setText(tevRegistrationDate, (String) info.get("EQURECORDDATE"), FillConfig.DEFALUT);
        TextUtils.setText(tevPaymentDay, (String) info.get("PERPAYDATE"), FillConfig.DEFALUT);
        TextUtils.setText(tevBaseDate, (String) info.get("XDRDATE"), FillConfig.DEFALUT);
        TextUtils.setText(tevRedemptionDate, (String) info.get("REPAYDATE"), FillConfig.DEFALUT);

    }

    /**
     * 查询债券付息情况失败
     */
    @Override
    public void onqueryBundInterestPayFail() {

    }

}
