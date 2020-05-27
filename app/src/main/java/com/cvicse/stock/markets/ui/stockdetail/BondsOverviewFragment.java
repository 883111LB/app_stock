package com.cvicse.stock.markets.ui.stockdetail;

import android.os.Bundle;
import android.widget.TextView;

import com.cvicse.annotations.MVPInject;
import com.cvicse.stock.R;
import com.cvicse.stock.base.PBaseFragment;
import com.cvicse.stock.common.FillConfig;
import com.cvicse.stock.markets.presenter.marketdetail.contract.BondsOverviewContract;
import com.cvicse.stock.utils.TextUtils;

import java.util.HashMap;

import butterknife.BindView;

/**
 * Created by liu_zlu on 2017/2/10 20:46
 * 债券概况
 */
public class BondsOverviewFragment extends PBaseFragment implements BondsOverviewContract.View {

    @MVPInject
    BondsOverviewContract.Presenter presenter;

    private static final String STOCKID = "STOCKID";

    //债券名称
    @BindView(R.id.tev_boundsname) TextView tevBoundsname;
    //债券简称
    @BindView(R.id.tev_abbreviation) TextView tevAbbreviation;
    //债券代码
    @BindView(R.id.tev_code) TextView tevCode;
    //债券性质
    @BindView(R.id.tev_bond_properties) TextView tevBondProperties;
    //债券信用评级
    @BindView(R.id.tev_credit_rating) TextView tevCreditRating;

    //债券面额
    @BindView(R.id.tev_denomination) TextView tevDenomination;
    //债券年限
    @BindView(R.id.tev_yearlimit) TextView tevYearlimit;
    //债券基准利率(%)
    @BindView(R.id.tev_benchmark_interest_rate) TextView tevBenchmarkInterestRate;
    //计息方式
    @BindView(R.id.tev_interest_rate_method) TextView tevInterestRateMethod;
    //付息方式
    @BindView(R.id.tev_way_interest) TextView tevWayInterest;

    //上市日期
    @BindView(R.id.tev_listingdate) TextView tevListingdate;
    //市场
    @BindView(R.id.tev_market) TextView tevMarket;
    //上市状态
    @BindView(R.id.tev_listing_status) TextView tevListingStatus;
    //兑换日
    @BindView(R.id.tev_exchangeday) TextView tevExchangeday;

    public static BondsOverviewFragment newInstance(String stockId) {
        BondsOverviewFragment bondsOverviewFragment = new BondsOverviewFragment();
        Bundle bundle = new Bundle();
        bundle.putString(STOCKID, stockId);
        bondsOverviewFragment.setArguments(bundle);
        return bondsOverviewFragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_stock_bondsoverview;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        presenter.init(getArguments().getString(STOCKID));
    }

    @Override
    protected void initData() {
        presenter.queryBondBasic();
    }

    /**
     * 请求债券概况信息成功
     *
     * @param info
     */
    @Override
    public void onQueryBondBasicSuccess(HashMap<String, Object> info) {
        if(info == null || info.size() <= 0){
            return;
        }
        TextUtils.setText(tevBoundsname, (String) info.get("BONDNAME"), FillConfig.DEFALUT);
        TextUtils.setText(tevAbbreviation, (String) info.get("BONDSNAME"), FillConfig.DEFALUT);
        TextUtils.setText(tevCode, (String) info.get("SYMBOL"), FillConfig.DEFALUT);
        TextUtils.setText(tevBondProperties, (String) info.get("BONDTYPE2"), FillConfig.DEFALUT);
        TextUtils.setText(tevCreditRating, (String) info.get("INITIALCREDITRATE"), FillConfig.DEFALUT);

        TextUtils.setText(tevDenomination, (String) info.get("PARVALUE"), FillConfig.DEFALUT);
        TextUtils.setText(tevYearlimit, (String) info.get("MATURITYYEAR"), FillConfig.DEFALUT);
        TextUtils.setText(tevBenchmarkInterestRate, (String) info.get("BASERATE"), FillConfig.DEFALUT);
        TextUtils.setText(tevInterestRateMethod, (String) info.get("CALCAMODE"), FillConfig.DEFALUT);
        TextUtils.setText(tevWayInterest, (String) info.get("PAYMENTMODE"), FillConfig.DEFALUT);

        TextUtils.setText(tevListingdate, (String) info.get("LISTDATE"), FillConfig.DEFALUT);
        TextUtils.setText(tevListingStatus, (String) info.get("LISTSTATE"), FillConfig.DEFALUT);
        TextUtils.setText(tevMarket, (String) info.get("EXCHANGENAME"), FillConfig.DEFALUT);
        TextUtils.setText(tevExchangeday, (String) info.get("PAYMENTDATE"), FillConfig.DEFALUT);
    }

}
