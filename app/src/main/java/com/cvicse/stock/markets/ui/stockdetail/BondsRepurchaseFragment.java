package com.cvicse.stock.markets.ui.stockdetail;

import android.os.Bundle;
import android.widget.TextView;

import com.cvicse.annotations.MVPInject;
import com.cvicse.stock.R;
import com.cvicse.stock.base.PBaseFragment;
import com.cvicse.stock.common.FillConfig;
import com.cvicse.stock.markets.presenter.marketdetail.contract.BondsRepurchaseContract;
import com.cvicse.stock.utils.TextUtils;

import java.util.HashMap;

import butterknife.BindView;

/**
 * 债券回购页面
 * Created by liu_zlu on 2017/2/15 22:12
 */
public class BondsRepurchaseFragment extends PBaseFragment implements BondsRepurchaseContract.View {

    @MVPInject BondsRepurchaseContract.Presenter presenter;

    private static final String STOCKID = "STOCKID";

    //债券名称
    @BindView(R.id.tev_boundsname) TextView tevBoundsname;
    //债券简称
    @BindView(R.id.tev_abbreviation) TextView tevAbbreviation;
    //债券代码
    @BindView(R.id.tev_code) TextView tevCode;
    //调整日
    @BindView(R.id.tev_adjust_day) TextView tevAdjustDay;

    //现券面额
    @BindView(R.id.tev_current_denomination) TextView tevCurrentDenomination;
    //折算比率(%)
    @BindView(R.id.tev_conversion_ratio) TextView tevConversionRatio;
    //折换价
    @BindView(R.id.tev_discounted_price) TextView tevDiscountedPrice;
    //集中兑换期(起始-截止)
    @BindView(R.id.tev_exchange_period) TextView tevExchangePeriod;


    public static BondsRepurchaseFragment newInstance(String stockId) {
        BondsRepurchaseFragment bondsRepurchaseFragment = new BondsRepurchaseFragment();
        Bundle bundle = new Bundle();
        bundle.putString(STOCKID, stockId);
        bondsRepurchaseFragment.setArguments(bundle);
        return bondsRepurchaseFragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_bonds_repurchase;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        presenter.init(getArguments().getString(STOCKID));
    }

    @Override
    protected void initData() {
        presenter.queryBndBuyBacks();
    }

    /**
     * 请求债券回购信息成功
     *
     * @param info
     */
    @Override
    public void onQuerybndBuyBacks(HashMap<String, Object> info) {
        if (info == null || info.size() <= 0) {
            return;
        }

        TextUtils.setText(tevBoundsname, (String) info.get("BONDNAME"), FillConfig.DEFALUT);
        TextUtils.setText(tevAbbreviation, (String) info.get("BONDSNAME"), FillConfig.DEFALUT);
        TextUtils.setText(tevCode, (String) info.get("SYMBOL"), FillConfig.DEFALUT);
        TextUtils.setText(tevAdjustDay, (String) info.get("EXERENDDATE"), FillConfig.DEFALUT);

        TextUtils.setText(tevCurrentDenomination, (String) info.get("PARVALUE"), FillConfig.DEFALUT);
        TextUtils.setText(tevConversionRatio, (String) info.get("CONVERTRATE"), FillConfig.DEFALUT);
        TextUtils.setText(tevDiscountedPrice, (String) info.get("CONVERTPRC"), FillConfig.DEFALUT);
        TextUtils.setText(tevExchangePeriod, (String) info.get("REPAYDATE"), FillConfig.DEFALUT);
    }

    /**
     * 请求债券回购信息失败
     */
    @Override
    public void onQuerybndBuyBacksFail() {

    }

}
