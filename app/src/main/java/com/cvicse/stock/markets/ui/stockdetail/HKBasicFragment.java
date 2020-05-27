package com.cvicse.stock.markets.ui.stockdetail;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cvicse.annotations.MVPInject;
import com.cvicse.stock.R;
import com.cvicse.stock.base.PBaseFragment;
import com.cvicse.stock.common.FillConfig;
import com.cvicse.stock.markets.presenter.marketdetail.contract.HKBasicContract;
import com.cvicse.stock.utils.TextUtils;
import com.mitake.core.network.Network;
import com.mitake.core.util.MarketSiteType;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;

/**
 * 港股简况
 * Created by liu_zlu on 2017/2/13 17:54
 */
public class HKBasicFragment extends PBaseFragment implements HKBasicContract.View {
    private static String STOCKID = "stockId";

    @MVPInject
    HKBasicContract.Presenter presenter;
    //公司名称
    @BindView(R.id.tev_chiname) TextView tevChiname;
    //英文简称
    @BindView(R.id.tev_enname) TextView tevEnname;
    //上市时间
    @BindView(R.id.tev_listingdate) TextView tevListingdate;
    //法定股本
    @BindView(R.id.tev_statutorysharecapital) TextView tevStatutorysharecapital;
    //发行股本
    @BindView(R.id.tev_issuedsharecapital) TextView tevIssuedsharecapital;
    //交易币别
    @BindView(R.id.tev_transactioncurrency) TextView tevTransactioncurrency;
    //面值
    @BindView(R.id.tev_facevalue) TextView tevFacevalue;
    //买卖单位
    @BindView(R.id.tev_tradingunit) TextView tevTradingunit;

    @BindView(R.id.holder_name) TextView holder1;
    @BindView(R.id.duty) TextView holder2;
    @BindView(R.id.rembeftax) TextView holder3;
    @BindView(R.id.holdefamt) TextView holder4;

    @BindView(R.id.lel_leader) LinearLayout lelLeader;

    public static HKBasicFragment newInstance(String stockId) {
        HKBasicFragment hkBasicFragment = new HKBasicFragment();
        Bundle bundle = new Bundle();
        bundle.putString(STOCKID, stockId);
        hkBasicFragment.setArguments(bundle);
        return hkBasicFragment;
    }
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_hk_basic;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        presenter.init(getArguments().getString(STOCKID));
    }

    @Override
    protected void initData() {
        presenter.queryBasic();
    }

    /**
     * 获取公司基本信息成功
     *
     * @param info
     */
    @Override
    public void onCompanyInfoSuccess( HashMap<String, Object> info) {
        TextUtils.setText(tevChiname,(String)info.get("CHINAME"), FillConfig.SIGNLE_LINE);
        TextUtils.setText(tevEnname,(String)info.get("SEENGNAME"), FillConfig.SIGNLE_LINE);
        TextUtils.setText(tevListingdate,(String)info.get("LISTINGDATE"), FillConfig.SIGNLE_LINE);
        TextUtils.setText(tevStatutorysharecapital,(String)info.get("AUTHCAPSK"), FillConfig.SIGNLE_LINE);
        TextUtils.setText(tevIssuedsharecapital,(String)info.get("ISSUECAPSK"), FillConfig.SIGNLE_LINE);
        TextUtils.setText(tevTransactioncurrency,(String)info.get("CURNAME"), FillConfig.SIGNLE_LINE);
        TextUtils.setText(tevTradingunit,(String)info.get("DEBTBOARDLOT"), FillConfig.SIGNLE_LINE);
        TextUtils.setText(tevFacevalue,(String)info.get("PARVALUE"), FillConfig.SIGNLE_LINE);

    }

    /**
     * 获取管理层信息成功
     *
     * @param leaderPersonInfos
     */
    @Override
    public void onLeaderPersonInfoSuccess(List<HashMap<String,Object>> leaderPersonInfos) {
        if(leaderPersonInfos == null || leaderPersonInfos.size() == 0){
            lelLeader.addView(TextUtils.getNotingTextView(lelLeader.getContext(),
                    "暂无该股票管理层信息"));
            return;
        }
        lelLeader.removeAllViews();
        for(HashMap<String,Object> info:leaderPersonInfos){
            if(info != null){
                ViewGroup viewGroup = (ViewGroup) layoutInflater.inflate(R.layout.item_lel_leader,lelLeader,false);
                ((ImageView) viewGroup.findViewById(R.id.img_upflag)).setVisibility(View.GONE);
                //姓名
                TextUtils.setText((TextView)viewGroup.findViewById(R.id.tev_name),(String)info.get("LEADERNAME"));
               //公司职务
                TextUtils.setText((TextView)viewGroup.findViewById(R.id.tev_positionName),(String)info.get("DUTY"));
                //职务类型
                TextUtils.setText((TextView)viewGroup.findViewById(R.id.tev_rembeftax),(String)info.get("DUTYTYPE"));
                ((TextView) viewGroup.findViewById(R.id.tev_rembeftax)).setGravity(View.TEXT_ALIGNMENT_CENTER);
                //就职日期
                TextUtils.setText((TextView)viewGroup.findViewById(R.id.tev_holdafamt),(String)info.get("BEGINDATE"));
                ((TextView) viewGroup.findViewById(R.id.tev_holdafamt)).setTextSize(12);
               // TextUtils.setText((TextView)viewGroup.findViewById(R.id.tev_age),leaderPersonInfo.age);
//                viewGroup.findViewById(R.id.tev_age).setVisibility(View.GONE);
                lelLeader.addView(viewGroup);
            }
        }
    }
}
