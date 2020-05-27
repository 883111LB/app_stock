package com.cvicse.stock.markets.ui.stockdetail;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cvicse.annotations.MVPInject;
import com.cvicse.stock.R;
import com.cvicse.stock.base.PBaseFragment;
import com.cvicse.stock.markets.presenter.marketdetail.contract.HKSummaryContract;
import com.cvicse.stock.utils.TextUtils;
import com.mitake.core.BonusFinance;
import com.mitake.core.NewIndex;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * 港股摘要
 * Created by liu_zlu on 2017/2/13 16:40
 */
public class HKSummaryFragment extends PBaseFragment implements HKSummaryContract.View {

    @MVPInject
    HKSummaryContract.Presenter presenter;

    private static String STOCKID = "stockId";

    /**
     * 最新指标
     */
    @BindView(R.id.tev_indexdate) TextView tevIndexdate;
    /**
     * 每股收益
     */
    @BindView(R.id.tev_basiceps) TextView tevBasiceps;
    /**
     * 扣非每股收益
     */
    @BindView(R.id.tev_cutbasiceps) TextView tevCutbasiceps;
    /**
     * 每股净资产
     */
    @BindView(R.id.tev_bvps) TextView tevBvps;
    /**
     * 每股资本公积金
     */
    @BindView(R.id.tev_reserveps) TextView tevReserveps;
    /**
     * 每股经营性现金流
     */
    @BindView(R.id.tev_netcashflowoperps) TextView tevNetcashflowoperps;
    /**
     * 每股未分配利润
     */
    @BindView(R.id.tev_retainedearningps) TextView tevRetainedearningps;
    /**
     * 净资产收益率
     */
    @BindView(R.id.tev_annuroe) TextView tevAnnuroe;
    /**
     * 毛利率（％）
     */
    @BindView(R.id.tev_grossprofitmargin) TextView tevGrossprofitmargin;
    /**
     * 营业收入
     */
    @BindView(R.id.tev_operrevenue) TextView tevOperrevenue;
    /**
     * 营收同比增
     */
    @BindView(R.id.tev_operrevenueyoy) TextView tevOperrevenueyoy;
    /**
     * 净利润
     */
    @BindView(R.id.tev_netprofitparentcom) TextView tevNetprofitparentcom;
    /**
     * 每股资本公积金
     */
    @BindView(R.id.tev_netprofitparentcomyoy) TextView tevNetprofitparentcomyoy;
    /**
     * 净利润同比增
     */
    @BindView(R.id.tev_netprofitcutparentcom) TextView tevNetprofitcutparentcom;
    /**
     * 扣非净利润同比增
     */
    @BindView(R.id.tev_netprofitcutparentcomyoy) TextView tevNetprofitcutparentcomyoy;
    /**
     * 总股本
     */
    @BindView(R.id.tev_totalshare) TextView tevTotalshare;
    /**
     * 流通股份合计
     */
    @BindView(R.id.tev_totalsharel) TextView tevTotalsharel;
    /**
     * 分红配送
     */
    @BindView(R.id.lel_dividends_distribution) LinearLayout lelDividendsDistribution;

    @BindView(R.id.abstract4) TextView abstract4;
    @BindView(R.id.abstract5) TextView abstract5;
    @BindView(R.id.abstract6) TextView abstract6;


    public static HKSummaryFragment newInstance(String stockId) {
        HKSummaryFragment hkSummaryFragment = new HKSummaryFragment();
        Bundle bundle = new Bundle();
        bundle.putString(STOCKID, stockId);
        hkSummaryFragment.setArguments(bundle);
        return hkSummaryFragment;
    }
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_hk_summary;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        presenter.init(getArguments().getString(STOCKID));
    }

    @Override
    protected void initData() {
        presenter.queryHKSummary();
    }

    /**
     * 获取最新指标成功
     *
     * @param newIndex
     */
    @Override
    public void onNewIndexSuccess(NewIndex newIndex) {
        if(newIndex == null){
            return;
        }
        TextUtils.setText(tevIndexdate,newIndex.REPTITLE_);
        TextUtils.setText(tevBasiceps,newIndex.basicEPS);
        TextUtils.setText(tevCutbasiceps,newIndex.cutBasicEPS);
        TextUtils.setText(tevBvps,newIndex.BVPS_);
        TextUtils.setText(tevReserveps,newIndex.reservePS);
        TextUtils.setText(tevNetcashflowoperps,newIndex.netCashFlowOperPS);
        TextUtils.setText(tevRetainedearningps,newIndex.retainedEarningPS);
        TextUtils.setText(tevAnnuroe,newIndex.annuROE);
        TextUtils.setText(tevGrossprofitmargin,newIndex.grossProfitMargin);
        TextUtils.setText(tevOperrevenue,newIndex.operRevenue);
        TextUtils.setText(tevOperrevenueyoy,newIndex.operRevenueYOY);
        TextUtils.setText(tevNetprofitparentcom,newIndex.netProfitCutParentCom);
        TextUtils.setText(tevNetprofitparentcomyoy,newIndex.netProfitParentComYOY);
        TextUtils.setText(tevNetprofitcutparentcom,newIndex.netProfitCutParentCom);
        TextUtils.setText(tevNetprofitcutparentcomyoy,newIndex.netProfitCutParentComYOY);
        TextUtils.setText(tevTotalshare,newIndex.totalShare);
    }

    /**
     * 获取分红配送成功
     *
     * @param bonusFinances
     */
    @Override
    public void onBonusFinanceSuccess(ArrayList<BonusFinance> bonusFinances) {
        if(bonusFinances == null || bonusFinances.size() == 0){
            abstract4.setVisibility(View.GONE);
            abstract5.setVisibility(View.GONE);
            abstract6.setVisibility(View.GONE);
            lelDividendsDistribution.addView(TextUtils.getNotingTextView(lelDividendsDistribution.getContext(),
                    "此证券暂无分红配送信息"));
            return;
        }
        abstract4.setVisibility(View.VISIBLE);
        abstract5.setVisibility(View.VISIBLE);
        abstract6.setVisibility(View.VISIBLE);
        lelDividendsDistribution.removeAllViews();
        for(BonusFinance bonusFinance:bonusFinances){
            if(lelDividendsDistribution != null){
                ViewGroup viewGroup = (ViewGroup) layoutInflater.inflate(R.layout.item_lel_bonusfinance,lelDividendsDistribution,false);
                TextUtils.setText((TextView)viewGroup.findViewById(R.id.tev_date),bonusFinance.NoticeDate);
                TextUtils.setText((TextView)viewGroup.findViewById(R.id.tev_program),bonusFinance.DiviScheme);
                TextUtils.setText((TextView)viewGroup.findViewById(R.id.tev_date_right),bonusFinance.ExDiviDate);
                lelDividendsDistribution.addView(viewGroup);
            }
        }
    }
}
