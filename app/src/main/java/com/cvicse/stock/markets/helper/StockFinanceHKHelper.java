package com.cvicse.stock.markets.helper;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.cvicse.stock.R;
import com.cvicse.stock.markets.ui.stockdetail.StockFinanceActivity;
import com.cvicse.stock.utils.TextUtils;
import com.mitake.core.MainFinaDataNas;
import com.mitake.core.QuoteItem;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/** 财务报表--港股
 * Created by tang_xqing on 2018/4/28.
 */

public class StockFinanceHKHelper {

    @BindView(R.id.tev_mainFinaDataDate)
    TextView tevMainFinaDataDate;
    @BindView(R.id.tev_basiceps_report)
    TextView tevBasicepsReport; // 每股收益
    @BindView(R.id.tev_zyzb_reserveps)
    TextView tevZyzbReserveps;  // 每股公积金
    @BindView(R.id.tev_bvps_report)
    TextView tevBvpsReport;  // 每股净资产
    @BindView(R.id.tev_netcashflowoperps_report)
    TextView tevNetcashflowoperpsReport; // 每股现金流
    @BindView(R.id.tev_weightedroe_report)
    TextView tevWeightedroeReport;  // 净资产收益
    @BindView(R.id.tev_roa_report)
    TextView tevRoaReport;  // 总资产收益
    @BindView(R.id.tev_totaloperrevenue_report)
    TextView tevTotalOperRevenueReport;  // 营业收入
    @BindView(R.id.tev_operprofit_report)
    TextView tevOperprofitReport;  // 营业利润
    @BindView(R.id.tev_netprofit_report)
    TextView tevNetOperprofitReport;  // 净利润
    @BindView(R.id.tev_totalasset_report)
    TextView tevTotalassetReport;  // 资产合计
    @BindView(R.id.tev_totalliab_report)
    TextView tevTotalliabReport;  // 负债合计
    @BindView(R.id.tev_totalshequity_report)
    TextView tevTotalShequityReport;  // 所有者权益合计
    @BindView(R.id.tev_netcashflowoper_report)
    TextView tevTotalshequityReport;  // 经营现金流量净额
    @BindView(R.id.tev_netcashflowinv_report)
    TextView tevNetCashFlowInvReport;  // 投资现金流量净额
    @BindView(R.id.tev_netcashflowfina_report)
    TextView tevNetCashFlowFinaReport;  // 筹资现金流量净额
    @BindView(R.id.tev_cashequinetincr_report)
    TextView tevCasheQuinetincrReport;  // 现金及现金等价净增额

    @BindView(R.id.datatitle_zyzb)
    TextView datatitleZyzb;
    @BindView(R.id.datatitle_lrb)
    TextView datatitleLrb;
    @BindView(R.id.datatitle_zcfzb)
    TextView datatitleZcfzb;
    @BindView(R.id.datatitle_xjllb)
    TextView datatitleXjllb;


    private Context mContext;
    private QuoteItem quoteItem;

    public StockFinanceHKHelper(View view, Context context, QuoteItem quoteItem){
        ButterKnife.bind(this,view);
        this.mContext = context;
        this.quoteItem = quoteItem;
        initView();
    }

    private void initView() {

        datatitleZyzb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StockFinanceActivity.startActivity(mContext, quoteItem, 0);
            }
        });

        datatitleLrb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StockFinanceActivity.startActivity(mContext, quoteItem, 1);
            }
        });

        datatitleZcfzb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StockFinanceActivity.startActivity(mContext, quoteItem, 2);
            }
        });

        datatitleXjllb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StockFinanceActivity.startActivity(mContext, quoteItem, 3);
            }
        });
    }

    public void onMainFinaDataNasSuccess(List<MainFinaDataNas> mainFinaDataNasList) {
        if (null == mainFinaDataNasList || mainFinaDataNasList.isEmpty()) {
            return;
        }
        MainFinaDataNas mainFinaDataNas = mainFinaDataNasList.get(0);
        TextUtils.setText(tevMainFinaDataDate,mainFinaDataNas.REPORTTITLE_);
        TextUtils.setText(tevBasicepsReport,mainFinaDataNas.BasicEPS);
        TextUtils.setText(tevZyzbReserveps,mainFinaDataNas.RESERVEPS_);
        TextUtils.setText(tevBvpsReport,mainFinaDataNas.BVPS_);
        TextUtils.setText(tevNetcashflowoperpsReport,mainFinaDataNas.NETCASHFLOWOPERPS_);
        TextUtils.setText(tevWeightedroeReport,mainFinaDataNas.WEIGHTEDROE_);
        TextUtils.setText(tevRoaReport,mainFinaDataNas.ROA_);
        TextUtils.setText(tevTotalOperRevenueReport,mainFinaDataNas.TotalOperRevenue);
        TextUtils.setText(tevOperprofitReport,mainFinaDataNas.OperProfit);
        TextUtils.setText(tevNetOperprofitReport,mainFinaDataNas.NetProfit);
        TextUtils.setText(tevTotalassetReport,mainFinaDataNas.TotalAsset);
        TextUtils.setText(tevTotalliabReport,mainFinaDataNas.TotalLiab);
        TextUtils.setText(tevTotalShequityReport,mainFinaDataNas.TotalSHEquity);
        TextUtils.setText(tevTotalshequityReport,mainFinaDataNas.NetCashFlowOper);
        TextUtils.setText(tevNetCashFlowInvReport,mainFinaDataNas.NetCashFlowInv);
        TextUtils.setText(tevNetCashFlowFinaReport,mainFinaDataNas.NetCashFlowFina);
        TextUtils.setText(tevCasheQuinetincrReport,mainFinaDataNas.CashEquiNetIncr);
    }
}
