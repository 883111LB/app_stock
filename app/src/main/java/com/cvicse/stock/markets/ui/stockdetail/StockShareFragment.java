package com.cvicse.stock.markets.ui.stockdetail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cvicse.annotations.MVPInject;
import com.cvicse.base.utils.ScreenUtils;
import com.cvicse.stock.R;
import com.cvicse.stock.base.PBaseFragment;
import com.cvicse.stock.markets.presenter.marketdetail.contract.StockShareContract;
import com.cvicse.stock.utils.TextUtils;
import com.mitake.core.FundShareHolderInfo;
import com.mitake.core.FundShareHolderInfoItem;
import com.mitake.core.ShareHolderHistoryInfo;
import com.mitake.core.StockShareChangeInfo;
import com.mitake.core.StockShareInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;

/**
 * 股东页面
 * Created by liu_zlu on 2017/2/10 13:41
 */
public class StockShareFragment extends PBaseFragment implements StockShareContract.View{
    private static final String STOCKID = "STOCKID";
    private static final String TYPE = "TYPE";
    public static final String SZ = "sz";

    public static final String HONGKONG = "hongkong";

    private String type;

    @BindView(R.id.rel_stockShare) RelativeLayout relStockShare;


    @BindView(R.id.tev_totalshareur) TextView tevTotalshareur;
    @BindView(R.id.tev_urratio) TextView tevUrratio;
    @BindView(R.id.tev_totalshareul) TextView tevTotalshareul;
    @BindView(R.id.tev_ulratio) TextView tevUlratio;
    @BindView(R.id.tev_totalshare) TextView tevTotalshare;
    @BindView(R.id.tev_totalsharePercent) TextView tevTotalsharePercent;
    @BindView(R.id.tev_alistedshare) TextView tevAlistedshare;
    @BindView(R.id.tev_aratio) TextView tevAratio;
    @BindView(R.id.tev_blistedshare) TextView tevBlistedshare;
    @BindView(R.id.tev_bratio) TextView tevBratio;
    @BindView(R.id.tev_htotalshare) TextView tevHtotalshare;
    @BindView(R.id.tev_hratio) TextView tevHratio;
    @BindView(R.id.tev_totalsharel_) TextView tevTotalsharel;
    @BindView(R.id.tev_totalsharel_Percent) TextView tevTotalsharelPercent;
    @BindView(R.id.lel_stockshare_change) LinearLayout lelStockshareChange;
    @BindView(R.id.shareHolderHistoryInfoDate) TextView shareHolderHistoryInfoDate;

    @BindView(R.id.tev_enddate_1) TextView tevEnddate1;
    @BindView(R.id.tev_enddate_2) TextView tevEnddate2;
    @BindView(R.id.tev_totalsh_1) TextView tevTotalsh1;
    @BindView(R.id.tev_totalsh_2) TextView tevTotalsh2;
    @BindView(R.id.tev_pctoftotalsh_1) TextView tevPctoftotalsh1;
    @BindView(R.id.tev_pctoftotalsh_2) TextView tevPctoftotalsh2;
    @BindView(R.id.tev_avgshare_1) TextView tevAvgshare1;
    @BindView(R.id.tev_avgshare_2) TextView tevAvgshare2;
    @BindView(R.id.tev_closingprice_1) TextView tevClosingprice1;
    @BindView(R.id.tev_closingprice_2) TextView tevClosingprice2;
    @BindView(R.id.tev_avgsharem_1) TextView tevAvgsharem1;
    @BindView(R.id.tev_avgsharem_2) TextView tevAvgsharem2;
    @BindView(R.id.tev_topLiquidShareHolder) TextView tevTopLiquidShareHolder;
    @BindView(R.id.lel_topliquid_shareholder) LinearLayout lelTopliquidShareholder;
    @BindView(R.id.tev_topShareHolder) TextView tevTopShareHolder;
    @BindView(R.id.lel_TopShareHolder) LinearLayout lelTopShareHolder;
    //最新基金持股
    @BindView(R.id.fundShareHolder) TextView fundShareHolder;
    @BindView(R.id.tev_fundShareHolderInfo) TextView tevFundShareHolderInfo;
    @BindView(R.id.f10_holder_line6) View line;

    @BindView(R.id.lel_FundShareHolderInfo) LinearLayout lelFundShareHolderInfo;


    @BindView(R.id.holder_name) TextView holder1;
    @BindView(R.id.duty) TextView holder2;
    @BindView(R.id.rembeftax) TextView holder3;
    @BindView(R.id.holdefamt) TextView holder4;
    @BindView(R.id.holder5) TextView holder5;
    @BindView(R.id.holder6) TextView holder6;
    @BindView(R.id.holder7) TextView holder7;
    @BindView(R.id.holder8) TextView holder8;
    @BindView(R.id.holder9) TextView holder9;
    @BindView(R.id.holder10) TextView holder10;
    @BindView(R.id.holder11) TextView holder11;
    @BindView(R.id.holder12) TextView holder12;
    @BindView(R.id.holder13) TextView holder13;
    @BindView(R.id.holder14) TextView holder14;
    @BindView(R.id.holder15) TextView holder15;
    @BindView(R.id.holder16) TextView holder16;
    //数量
    @BindView(R.id.holder28) TextView holder28;

    @BindView(R.id.holder17) TextView holder17;
    @BindView(R.id.holder18) TextView holder18;
    @BindView(R.id.holder19) TextView holder19;
    @BindView(R.id.holderamt) TextView holder20;
    @BindView(R.id.holderrto) TextView holderrto;
    @BindView(R.id.holder21) TextView holder21;
    @BindView(R.id.holderamt2) TextView holderamt2;
    @BindView(R.id.holder22) TextView holder22;
    @BindView(R.id.holder23) TextView holder23;
    @BindView(R.id.holder24) TextView holder24;
    @BindView(R.id.holder25) TextView holder25;
    @BindView(R.id.holder26) TextView holder26;
    @BindView(R.id.holder27) TextView holder27;
    @BindView(R.id.title1) TextView title1;
    @BindView(R.id.title2) TextView title2;
    @BindView(R.id.title3) TextView title3;
    @BindView(R.id.title4) TextView title4;
    @BindView(R.id.title5) TextView title5;
    @MVPInject
    StockShareContract.Presenter presenter;
    public static StockShareFragment newInstance(String stockId,String type) {
        StockShareFragment stockShareFragment = new StockShareFragment();
        Bundle bundle = new Bundle();
        bundle.putString(STOCKID, stockId);
        bundle.putString(TYPE, type);
        stockShareFragment.setArguments(bundle);
        return stockShareFragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_stock_share;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        type = getArguments().getString(TYPE);
        presenter.init(getArguments().getString(STOCKID),type);
        if(type.equals(HONGKONG)){
            fundShareHolder.setVisibility(View.GONE);
            relStockShare.setVisibility(View.GONE);
            tevFundShareHolderInfo.setVisibility(View.GONE);
            line.setVisibility(View.GONE);
            holder25.setVisibility(View.GONE);
            holder26.setVisibility(View.GONE);
            holder27.setVisibility(View.GONE);
        }else{
            /**
             * 沪深 股本变动
             */
            holder15.setText("总股本(股)");
            holder16.setText("流通A股(股)");
            holder28.setVisibility(View.GONE);
            holder17.setText("变动原因");
        }
    }

    @Override
    protected void initData() {
        presenter.queryStockShare();
    }

    /**
     * 查询股本结构成功
     *
     * @param stockShareInfo
     */
    @Override
    public void onStockShareSuccess(StockShareInfo stockShareInfo) {
        if(stockShareInfo == null){
            return;
        }
        TextUtils.setText(tevTotalshareur,stockShareInfo.totalShareUR);
        TextUtils.setText(tevUrratio,stockShareInfo.URRatio);
        TextUtils.setText(tevTotalshareul,stockShareInfo.totalShareUL);
        TextUtils.setText(tevUlratio,stockShareInfo.ULRatio);
        TextUtils.setText(tevTotalshare,stockShareInfo.totalShare);
        TextUtils.setText(tevTotalsharePercent,"100%");
        TextUtils.setText(tevAlistedshare,stockShareInfo.AListedShare);
        TextUtils.setText(tevAratio,stockShareInfo.ARatio);
        TextUtils.setText(tevBlistedshare,stockShareInfo.BListedShare);
        TextUtils.setText(tevBratio,stockShareInfo.BRatio);
        TextUtils.setText(tevHtotalshare,stockShareInfo.HTotalShare);
        TextUtils.setText(tevHratio,stockShareInfo.HRatio);
        TextUtils.setText(tevTotalsharel,stockShareInfo.totalShareL);
        TextUtils.setText(tevTotalsharelPercent,"100%");

    }

    /**
     * 查询股本变动成功
     *
     * @param stockShareChangeInfos
     */
    @Override
    public void onStockShareChangeSuccess(ArrayList<StockShareChangeInfo> stockShareChangeInfos) {
        if(stockShareChangeInfos == null || stockShareChangeInfos.size() == 0){
            holder14.setVisibility(View.GONE);
            holder15.setVisibility(View.GONE);
            holder16.setVisibility(View.GONE);
            holder28.setVisibility(View.GONE);
            holder17.setVisibility(View.GONE);
            lelStockshareChange.addView(TextUtils.getNotingTextView(lelStockshareChange.getContext(),
                    "暂无股本变动信息"));
            return;
        }
        holder14.setVisibility(View.VISIBLE);
        holder15.setVisibility(View.VISIBLE);
        holder16.setVisibility(View.VISIBLE);
        //holder28.setVisibility(View.VISIBLE);
        holder17.setVisibility(View.VISIBLE);
        lelStockshareChange.removeAllViews();
        for (StockShareChangeInfo stockShareChangeInfo:stockShareChangeInfos){
            if(stockShareChangeInfo != null){
                ViewGroup viewGroup = (ViewGroup) layoutInflater.inflate(R.layout.item_lel_stocksharechange,lelStockshareChange,false);
                if(HONGKONG.equals(type)){
                    holder28.setVisibility(View.VISIBLE);
                    TextUtils.setText((TextView)viewGroup.findViewById(R.id.tev_last_changedate),stockShareChangeInfo.lastChangeDate);
                    TextUtils.setText((TextView)viewGroup.findViewById(R.id.tev_totalshare),stockShareChangeInfo.totalShare);
                    TextUtils.setText((TextView)viewGroup.findViewById(R.id.tev_alistedshare),stockShareChangeInfo.changedire);
                    TextUtils.setText((TextView)viewGroup.findViewById(R.id.tev_amount),stockShareChangeInfo.changeamt);
                    TextUtils.setText((TextView)viewGroup.findViewById(R.id.tev_constedsc_),stockShareChangeInfo.CONSTDESC_);
                }else{
                    TextUtils.setText((TextView)viewGroup.findViewById(R.id.tev_last_changedate),stockShareChangeInfo.lastChangeDate);
                    TextUtils.setText((TextView)viewGroup.findViewById(R.id.tev_totalshare),stockShareChangeInfo.totalShare);
                    //TextUtils.setText((TextView)viewGroup.findViewById(R.id.tev_alistedshare),stockShareChangeInfo.changedire);
                    viewGroup.findViewById(R.id.tev_alistedshare).setVisibility(View.GONE);
                    //流通A股(股)
                    TextView tevAmount = (TextView)viewGroup.findViewById(R.id.tev_amount);
                    TextUtils.setText(tevAmount,stockShareChangeInfo.aListedShare);
                    tevAmount.setGravity(Gravity.LEFT);

                    TextUtils.setText((TextView)viewGroup.findViewById(R.id.tev_constedsc_),stockShareChangeInfo.CONSTDESC_);
                }

                lelStockshareChange.addView(viewGroup);
            }
        }
    }

    /**
     * 查询股东变动成功
     *
     * @param shareHolderHistoryInfos
     */
    @Override
    public void onShareHolderHistorySuccess(ArrayList<ShareHolderHistoryInfo> shareHolderHistoryInfos) {
        if( null== shareHolderHistoryInfos || shareHolderHistoryInfos.size() == 0){
            return;
        }
        ShareHolderHistoryInfo shareHolderHistoryInfo1 = shareHolderHistoryInfos.get(0);
        TextUtils.setText(tevEnddate1,shareHolderHistoryInfo1.ENDDATE_);
        TextUtils.setText(tevTotalsh1,shareHolderHistoryInfo1.TOTALSH_);
        TextUtils.setText(tevPctoftotalsh1,shareHolderHistoryInfo1.PCTOFTOTALSH_);
        TextUtils.setText(tevAvgshare1,shareHolderHistoryInfo1.AVGSHARE_);
        TextUtils.setText(tevAvgsharem1,shareHolderHistoryInfo1.AVGSHAREM_);
        TextUtils.setText(tevClosingprice1,shareHolderHistoryInfo1.CLOSINGPRICE_);
        if(shareHolderHistoryInfos.size() > 1){
            ShareHolderHistoryInfo shareHolderHistoryInfo2 = shareHolderHistoryInfos.get(1);
            TextUtils.setText(tevEnddate2,shareHolderHistoryInfo2.ENDDATE_);
            TextUtils.setText(tevTotalsh2,shareHolderHistoryInfo2.TOTALSH_);
            TextUtils.setText(tevPctoftotalsh2,shareHolderHistoryInfo2.PCTOFTOTALSH_);
            TextUtils.setText(tevAvgshare2,shareHolderHistoryInfo2.AVGSHARE_);
            TextUtils.setText(tevAvgsharem2,shareHolderHistoryInfo2.AVGSHAREM_);
            TextUtils.setText(tevClosingprice2,shareHolderHistoryInfo2.CLOSINGPRICE_);
        }
    }

    /**
     * 查询最新十大流通股股东成功（港股为最新自然人持股）
     * @param topLiquidShareHolders
     */
    @Override
    public void onTopLiquidShareHolderSuccess(List<HashMap<String, Object>> topLiquidShareHolders) {
        if( null==topLiquidShareHolders  || topLiquidShareHolders.size() == 0){
            lelTopliquidShareholder.addView(TextUtils.getNotingTextView(lelTopliquidShareholder.getContext(),
                    "暂无十大流通股股东信息"));
            return;
        }
        lelTopliquidShareholder.removeAllViews();
        if(topLiquidShareHolders.size() > 0){
//            TextUtils.setText(tevTopLiquidShareHolder,topLiquidShareHolders.get(0).ENDDATE_);
            TextUtils.setText(tevTopLiquidShareHolder,(String) topLiquidShareHolders.get(0).get("ENDDATE"));
        }
        for(HashMap<String, Object> topLiquidShareHolder:topLiquidShareHolders){
            if(topLiquidShareHolder != null){
                ViewGroup viewGroup = (ViewGroup) layoutInflater.inflate(R.layout.item_lel_topshareholder,lelTopliquidShareholder,false);
                final TextView shName = (TextView) viewGroup.findViewById(R.id.tev_shname);
                final String shholdername = (String) topLiquidShareHolder.get("SHHOLDERNAME");
                TextUtils.setText(shName, shholdername);
                TextUtils.setText((TextView)viewGroup.findViewById(R.id.tev_sholderamt),(String) topLiquidShareHolder.get("HOLDERAMT"));
                TextUtils.setText((TextView)viewGroup.findViewById(R.id.tev_sholderrto),(String) topLiquidShareHolder.get("PCTOFFLOTSHARES"));
                TextUtils.setText((TextView)viewGroup.findViewById(R.id.tev_shcurchg),(String) topLiquidShareHolder.get("HOLDERSUMCHG"));

                shName.setTag(topLiquidShareHolder.get("SHHOLDERCODE"));
                shName.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String code = (String) shName.getTag();
                        StockShareIinvholdchgActivity.startActivity(getActivity(),code,shholdername);
                    }
                });

                lelTopliquidShareholder.addView(viewGroup);
            }
        }
    }

    /**
     * 查询最新十大股东成功（港股为最新机构持股）
     * @param topShareHolders
     */
    @Override
    public void onTopShareHolderSuccess(List<HashMap<String, Object>> topShareHolders) {
        if( null==topShareHolders  || topShareHolders.isEmpty()){
            lelTopShareHolder.addView(TextUtils.getNotingTextView(lelTopShareHolder.getContext(),
                    "暂无十大股东信息"));
            return;
        }
        lelTopShareHolder.removeAllViews();
        if(topShareHolders.size() > 0){
//            TextUtils.setText(tevTopShareHolder,topShareHolders.get(0).ENDDATE_);
            TextUtils.setText(tevTopShareHolder, (String) topShareHolders.get(0).get("ENDDATE"));
        }
        for(HashMap<String, Object> topShareHolder:topShareHolders){
            if(topShareHolder != null){
                ViewGroup viewGroup = (ViewGroup) layoutInflater.inflate(R.layout.item_lel_topshareholder,lelTopShareHolder,false);
                final TextView shName = (TextView) viewGroup.findViewById(R.id.tev_shname);
                final String shholdername = (String) topShareHolder.get("SHHOLDERNAME");
                TextUtils.setText(shName, shholdername);
                TextUtils.setText((TextView)viewGroup.findViewById(R.id.tev_sholderamt),(String) topShareHolder.get("HOLDERAMT"));
                TextUtils.setText((TextView)viewGroup.findViewById(R.id.tev_sholderrto),(String) topShareHolder.get("HOLDERRTO"));
                TextUtils.setText((TextView)viewGroup.findViewById(R.id.tev_shcurchg),(String) topShareHolder.get("CURCHG"));

                shName.setTag(topShareHolder.get("SHHOLDERCODE"));
                shName.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String code = (String) shName.getTag();
                        StockShareIinvholdchgActivity.startActivity(getActivity(),code,shholdername);
                    }
                });

                lelTopShareHolder.addView(viewGroup);
            }
        }
    }

    /**
     * 查询最新基金持股成功
     *
     * @param fundShareHolderInfo
     */
    @Override
    public void onFundShareHolderSuccess(FundShareHolderInfo fundShareHolderInfo) {
        if(fundShareHolderInfo == null || fundShareHolderInfo.list == null || fundShareHolderInfo.list.size() == 0){
            lelFundShareHolderInfo.addView(TextUtils.getNotingTextView(lelFundShareHolderInfo.getContext(),
                    "暂无基金持股信息"));
            return;
        }
        lelFundShareHolderInfo.removeAllViews();
        TextUtils.setText(tevFundShareHolderInfo,fundShareHolderInfo.ENDDATE_);
        for(FundShareHolderInfoItem fundShareHolderInfoItem:fundShareHolderInfo.list){
            ViewGroup viewGroup = (ViewGroup) layoutInflater.inflate(R.layout.item_lel_topshareholder_fund,lelFundShareHolderInfo,false);
            TextUtils.setText((TextView)viewGroup.findViewById(R.id.tev_shname),fundShareHolderInfoItem.CHINAMEABBR_);
            TextUtils.setText((TextView)viewGroup.findViewById(R.id.tev_sholderrto),fundShareHolderInfoItem.HOLDINGVOL_);
            TextUtils.setText((TextView)viewGroup.findViewById(R.id.tev_shcurchg),fundShareHolderInfoItem.PCTTOTALESHARE_);
            lelFundShareHolderInfo.addView(viewGroup);
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        int screenWidth = ScreenUtils.getScreenWidth();
        int halfWidth = screenWidth>>1;
        int quarterWidth = halfWidth >> 1;
        int oneThirdWidth = screenWidth/3;
        holder1.setWidth(halfWidth);
        holder2.setWidth(quarterWidth);
        holder3.setWidth(quarterWidth);

        holder4.setWidth(halfWidth);
        tevTotalshareur.setWidth(quarterWidth);
        tevUrratio.setWidth(quarterWidth);

        holder5.setWidth(halfWidth);
        tevTotalshareul.setWidth(quarterWidth);
        tevUlratio.setWidth(quarterWidth);

        holder6.setWidth(halfWidth);
        tevTotalshare.setWidth(quarterWidth);
        tevTotalsharePercent.setWidth(quarterWidth);

        holder7.setWidth(halfWidth);
        holder8.setWidth(quarterWidth);
        holder9.setWidth(quarterWidth);

        holder10.setWidth(halfWidth);
        tevAlistedshare.setWidth(quarterWidth);
        tevAratio.setWidth(quarterWidth);

        holder11.setWidth(halfWidth);
        tevBlistedshare.setWidth(quarterWidth);
        tevBratio.setWidth(quarterWidth);

        holder12.setWidth(halfWidth);
        tevHtotalshare.setWidth(quarterWidth);
        tevHratio.setWidth(quarterWidth);

        holder13.setWidth(halfWidth);
        tevTotalsharel.setWidth(quarterWidth);
        tevTotalsharelPercent.setWidth(quarterWidth);

        holder14.setWidth(quarterWidth);
        holder15.setWidth(quarterWidth);
        holder16.setWidth(quarterWidth);
        holder17.setWidth(quarterWidth);

        holder18.setWidth(oneThirdWidth);
        tevEnddate1.setWidth(oneThirdWidth);
        tevEnddate2.setWidth(oneThirdWidth);
        title1.setWidth(oneThirdWidth);
        tevTotalsh1.setWidth(oneThirdWidth);
        tevTotalsh2.setWidth(oneThirdWidth);
        title2.setWidth(oneThirdWidth);
        tevPctoftotalsh1.setWidth(oneThirdWidth);
        tevPctoftotalsh2.setWidth(oneThirdWidth);
        title3.setWidth(oneThirdWidth);
        tevAvgshare1.setWidth(oneThirdWidth);
        tevAvgshare2.setWidth(oneThirdWidth);
        title4.setWidth(oneThirdWidth);
        tevClosingprice1.setWidth(oneThirdWidth);
        tevClosingprice2.setWidth(oneThirdWidth);
        title5.setWidth(oneThirdWidth);
        tevAvgsharem1.setWidth(oneThirdWidth);
        tevAvgsharem2.setWidth(oneThirdWidth);
        holder19.setWidth(oneThirdWidth);
        holderrto.setWidth(oneThirdWidth);
        holder21.setWidth(oneThirdWidth);
        holder22.setWidth(oneThirdWidth);
        holder23.setWidth(oneThirdWidth);
        holder24.setWidth(oneThirdWidth);
        holder25.setWidth(oneThirdWidth);
        holder26.setWidth(oneThirdWidth);
        holder27.setWidth(oneThirdWidth);
    }

}
