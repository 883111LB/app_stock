package com.cvicse.stock.markets.helper;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.cvicse.stock.R;
import com.cvicse.stock.markets.ui.stockdetail.StockFinanceActivity;
import com.cvicse.stock.utils.TextUtils;
import com.mitake.core.QuoteItem;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

/**财务报表-- 沪深
 * Created by tang_xqing on 2018/4/28.
 */
public class StockFinanceSHSZHelper {
    @BindView(R.id.tev_mainFinaDataDate)
    TextView tevMainFinaDataDate;
    @BindView(R.id.tev_totalliab_report)
    TextView tevTotalliabReport;
    @BindView(R.id.money)
    TextView tevMoney;
    @BindView(R.id.datatitle_zyzb)
    TextView datatitleZyzb;
    @BindView(R.id.tev_epsdiluted)
    TextView tevEpsdiluted;
    @BindView(R.id.tev_naps)
    TextView tevNaps;
    @BindView(R.id.tev_upps)
    TextView tevUpps;
    @BindView(R.id.tev_crps)
    TextView tevCrps;
    @BindView(R.id.tev_sgpmargin)
    TextView tevSgpmargin;
    @BindView(R.id.tev_opprort)
    TextView tevOpprort;
    @BindView(R.id.tev_snpmargin)
    TextView tevSnpmargin;
    @BindView(R.id.tev_roeweighted)
    TextView tevRoeweighted;
    @BindView(R.id.tev_roediluted)
    TextView tevRoediluted;
    @BindView(R.id.tev_currentrt)
    TextView tevCurrentrt;
    @BindView(R.id.tev_quickrt)
    TextView tevQuickrt;
    @BindView(R.id.tev_opncfps)
    TextView tevOpncfps;
    @BindView(R.id.datatitle_lrb)
    TextView datatitleLrb;
    @BindView(R.id.tev_bizinco)
    TextView tevBizinco;
    @BindView(R.id.tev_bizcost)
    TextView tevBizcost;
    @BindView(R.id.tev_manaexpe)
    TextView tevManaexpe;
    @BindView(R.id.tev_salesexpe)
    TextView tevSalesexpe;
    @BindView(R.id.tev_finexpe)
    TextView tevFinexpe;
    @BindView(R.id.tev_perprofit)
    TextView tevPerprofit;
    @BindView(R.id.tev_inveinco)
    TextView tevInveinco;
    @BindView(R.id.tev_nonoperincomen)
    TextView tevNonoperincomen;
    @BindView(R.id.tev_totprofit)
    TextView tevTotprofit;
    @BindView(R.id.tev_parenetp)
    TextView tevParenetp;
    @BindView(R.id.datatitle_zcfzb)
    TextView datatitleZcfzb;
    @BindView(R.id.tev_totliabsharequi)
    TextView tevTotliabsharequi;
    @BindView(R.id.tev_curfds)
    TextView tevCurfds;
    @BindView(R.id.tev_tradfinasset)
    TextView tevTradfinasset;
    @BindView(R.id.tev_inve)
    TextView tevInve;
    @BindView(R.id.tev_accorece)
    TextView tevAccorece;
    @BindView(R.id.tev_otherece)
    TextView tevOtherece;
    @BindView(R.id.tev_fixedassenet)
    TextView tevFixedassenet;
    @BindView(R.id.tev_avaisellasse)
    TextView tevAvaisellasse;
    @BindView(R.id.tev_intaasset)
    TextView tevIntaasset;
    @BindView(R.id.tev_shorttermborr)
    TextView tevShorttermborr;
    @BindView(R.id.tev_advapaym)
    TextView tevAdvapaym;
    @BindView(R.id.tev_accopaya)
    TextView tevAccopaya;
    @BindView(R.id.tev_totalcurrliab)
    TextView tevTotalcurrliab;
    @BindView(R.id.tev_sunevennoncliab)
    TextView tevSunevennoncliab;
    @BindView(R.id.tev_totliab)
    TextView tevTotliab;
    @BindView(R.id.tev_paresharrigh)
    TextView tevParesharrigh;
    @BindView(R.id.tev_capisurp)
    TextView tevCapisurp;
    @BindView(R.id.tev_goodwill)
    TextView tevGoodWill;
    @BindView(R.id.datatitle_xjllb)
    TextView datatitleXjllb;
    @BindView(R.id.tev_bizcashinfl)
    TextView tevBizcashinfl;
    @BindView(R.id.tev_bizcashoutf)
    TextView tevBizcashoutf;
    @BindView(R.id.tev_mananetr)
    TextView tevMananetr;
    @BindView(R.id.tev_invcashinfl)
    TextView tevInvcashinfl;
    @BindView(R.id.tev_invcashoutf)
    TextView tevInvcashoutf;
    @BindView(R.id.tev_incnetcashflow)
    TextView tevIncnetcashflow;
    @BindView(R.id.tev_fincashinfl)
    TextView tevFincashinfl;
    @BindView(R.id.tev_fincashoutf)
    TextView tevFincashoutf;
    @BindView(R.id.tev_finnetcflow)
    TextView tevFinnetcflow;
    @BindView(R.id.tev_cashnetr)
    TextView tevCashnetr;
    @BindView(R.id.tev_epsbasic)
    TextView tevEpsbasic;

    private Context mContext;
    private QuoteItem quoteItem;

    public StockFinanceSHSZHelper(View view, Context context, QuoteItem quoteItem){
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

    /**
     * 主要指标
     * @param infoList
     */
    public void onProfinmainindexSuccess(HashMap<String, Object> infoList) {
        TextUtils.setText(tevMainFinaDataDate, (String) infoList.get("REPORTTITLE"));
        TextUtils.setText(tevEpsbasic, (String) infoList.get("EPSBASIC"));
        TextUtils.setText(tevEpsdiluted, (String) infoList.get("EPSDILUTED"));
        TextUtils.setText(tevNaps, (String) infoList.get("NAPS"));
        TextUtils.setText(tevUpps, (String) infoList.get("UPPS"));
        TextUtils.setText(tevCrps, (String) infoList.get("CRPS"));
        TextUtils.setText(tevSgpmargin, (String) infoList.get("SGPMARGIN"));
        TextUtils.setText(tevOpprort,(String) infoList.get("OPPRORT"));
        TextUtils.setText(tevSnpmargin,(String) infoList.get("SNPMARGIN"));
        TextUtils.setText(tevRoeweighted, (String) infoList.get("ROEWEIGHTED"));
        TextUtils.setText(tevRoediluted, (String) infoList.get("ROEDILUTED"));
        TextUtils.setText(tevCurrentrt, (String) infoList.get("CURRENTRT"));
        TextUtils.setText(tevQuickrt, (String) infoList.get("QUICKRT"));
        TextUtils.setText(tevOpncfps, (String) infoList.get("OPNCFPS"));
    }

    /**
     * 利润表
     * @param infoList
     */
    public void onProincstatementnewSuccess(HashMap<String, Object> infoList) {
        TextUtils.setText(tevBizinco, (String) infoList.get("BIZINCO"));
        TextUtils.setText(tevBizcost, (String) infoList.get("BIZCOST"));
        TextUtils.setText(tevManaexpe, (String) infoList.get("MANAEXPE"));
        TextUtils.setText(tevSalesexpe, (String) infoList.get("SALESEXPE"));
        TextUtils.setText(tevFinexpe, (String) infoList.get("FINEXPE"));
        TextUtils.setText(tevPerprofit, (String) infoList.get("PERPROFIT"));
        TextUtils.setText(tevInveinco, (String) infoList.get("INVEINCO"));
        TextUtils.setText(tevNonoperincomen, (String) infoList.get("NONOPERINCOMEN"));
        TextUtils.setText(tevTotprofit, (String) infoList.get("TOTPROFIT"));
        TextUtils.setText(tevParenetp, (String) infoList.get("PARENETP"));
    }

    /**
     * 资产负债表
     * @param infoList
     */
    public void onProbalsheetnewSuccess(HashMap<String, Object> infoList) {
        TextUtils.setText(tevTotliabsharequi, (String) infoList.get("TOTLIABSHAREQUI"));
        TextUtils.setText(tevTotalliabReport, (String) infoList.get("TOTCURRASSET"));
        TextUtils.setText(tevCurfds, (String) infoList.get("CURFDS"));
        TextUtils.setText(tevTradfinasset, (String) infoList.get("TRADFINASSET"));
        TextUtils.setText(tevInve, (String) infoList.get("INVE"));
        TextUtils.setText(tevAccorece, (String) infoList.get("ACCORECE"));
        TextUtils.setText(tevOtherece, (String) infoList.get("OTHERRECE"));
        TextUtils.setText(tevFixedassenet, (String) infoList.get("FIXEDASSENET"));
        TextUtils.setText(tevAvaisellasse, (String) infoList.get("AVAISELLASSE"));
        TextUtils.setText(tevIntaasset, (String) infoList.get("INTAASSET"));
        TextUtils.setText(tevShorttermborr, (String) infoList.get("SHORTTERMBORR"));
        TextUtils.setText(tevAdvapaym, (String) infoList.get("ADVAPAYM"));
        TextUtils.setText(tevAccopaya, (String) infoList.get("ACCOPAYA"));
        TextUtils.setText(tevTotalcurrliab, (String) infoList.get("TOTALCURRLIAB"));
        TextUtils.setText(tevSunevennoncliab, (String) infoList.get("SUNEVENNONCLIAB"));
        TextUtils.setText(tevTotliab, (String) infoList.get("TOTLIAB"));
        TextUtils.setText(tevParesharrigh, (String) infoList.get("PARESHARRIGH"));
        TextUtils.setText(tevCapisurp, (String) infoList.get("CAPISURP"));
        TextUtils.setText(tevGoodWill,(String)infoList.get("GOODWILL"));
    }

    /**
     * 现金流量表
     * @param infoList
     */
    public void onProcfstatementnewSuccess(HashMap<String, Object> infoList) {
        TextUtils.setText(tevBizcashinfl, (String) infoList.get("BIZCASHINFL"));
        TextUtils.setText(tevBizcashoutf, (String) infoList.get("BIZCASHOUTF"));
        TextUtils.setText(tevMananetr, (String) infoList.get("MANANETR"));
        TextUtils.setText(tevInvcashinfl, (String) infoList.get("INVCASHINFL"));
        TextUtils.setText(tevInvcashoutf, (String) infoList.get("INVCASHOUTF"));
        TextUtils.setText(tevIncnetcashflow, (String) infoList.get("INVNETCASHFLOW"));
        TextUtils.setText(tevFincashinfl, (String) infoList.get("FINCASHINFL"));
        TextUtils.setText(tevFincashoutf, (String) infoList.get("FINCASHOUTF"));
        TextUtils.setText(tevFinnetcflow, (String) infoList.get("FINNETCFLOW"));
        TextUtils.setText(tevCashnetr, (String) infoList.get("CASHNETR"));
    }
}
