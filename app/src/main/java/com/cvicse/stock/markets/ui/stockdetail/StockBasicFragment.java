package com.cvicse.stock.markets.ui.stockdetail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cvicse.annotations.MVPInject;
import com.cvicse.base.utils.ScreenUtils;
import com.cvicse.stock.R;
import com.cvicse.stock.base.PBaseFragment;
import com.cvicse.stock.markets.adapter.new_adapter.CompanyManagerAdapter;
import com.cvicse.stock.markets.presenter.marketdetail.contract.StockBasicContract;
import com.cvicse.stock.utils.TextUtils;
import com.mitake.core.CoreBusiness;
import com.mitake.core.LeaderPersonInfo;
import com.stock.config.FillConfig;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;

/**
 * 公司基本详情页面
 * Created by liu_zlu on 2017/2/9 20:55
 */
public class StockBasicFragment extends PBaseFragment implements StockBasicContract.View {

    private static final String STOCKID = "STOCKID";
    @MVPInject
    StockBasicContract.Presenter presenter;

    @BindView(R.id.companyInfoDate)
    TextView companyInfoDate;
    //公司名称
    @BindView(R.id.tev_chiname)
    TextView tevChiname;
    //上市时间
    @BindView(R.id.tev_listingdate)
    TextView tevListingdate;
    //所属行业
    @BindView(R.id.tev_infoname)
    TextView tevInfoname;
    //注册地
    @BindView(R.id.tev_provincename)
    TextView tevProvincename;
    //发行价
    @BindView(R.id.tev_regcapital)
    TextView tevRegcapital;
    //董事长
    @BindView(R.id.tev_legalrepr)
    TextView tevLegalrepr;
    //董秘
    @BindView(R.id.tev_regaddress)
    TextView tevRegaddress;
    // 质押比
    @BindView(R.id.tev_pledgeratio)
    TextView tev_pledgeratio;

    //主营业务时间
    @BindView(R.id.tev_coreBusinessDate)
    TextView tevCoreBusinessDate;
    @BindView(R.id.lel_main_bussiness)
    LinearLayout lelMainBussiness;
    @BindView(R.id.holder_name)
    TextView holder1;
    @BindView(R.id.duty)
    TextView holder2;
    @BindView(R.id.rembeftax)
    TextView holder3;
    @BindView(R.id.holdefamt)
    TextView holder4;
//    @BindView(R.id.holder5)
//    TextView holder5;

    //  董秘邮箱
    @BindView(R.id.tev_bsecretarymail)
    TextView tevBsecretarymail;
    // 首日开盘价
    @BindView(R.id.tev_listoprice)
    TextView tevListoprice;

    @BindView(R.id.lel_leader)
    LinearLayout lelLeader;
//    @BindView(R.id.lsv_leader)
//    ListView lsvLeader;

    private CompanyManagerAdapter managerAdapter;

   public static StockBasicFragment newInstance(String stockId) {
        StockBasicFragment stockBasicFragment = new StockBasicFragment();
        Bundle bundle = new Bundle();
        bundle.putString(STOCKID, stockId);
        stockBasicFragment.setArguments(bundle);
        return stockBasicFragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_stock_basic;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        presenter.init(getArguments().getString(STOCKID));
    }

    @Override
    protected void initData() {
//        managerAdapter = new CompanyManagerAdapter(getContext());
//        managerAdapter.setListViewHeightBasedOnChildren(lsvLeader);
//        lsvLeader.setAdapter(managerAdapter);

        presenter.queryBasic();
    }

    /**
     * 获取公司基本信息成功
     *
     * @param info
     */
    @Override
    public void onCompanyInfoSuccess(HashMap<String, Object> info) {
        if (null == info) {
            return;
        }

        TextUtils.setText(tevChiname, (String) info.get("COMPNAME"));
        TextUtils.setText(tevListingdate, (String) info.get("LISTDATE"));

        String fclevel2NAME = ((String) info.get("FCLEVEL2NAME"));
        String swlevel2NAME = (String) info.get("SWLEVEL2NAME");
        StringBuilder tempBuilder = new StringBuilder();
        tempBuilder.append(null == fclevel2NAME ? FillConfig.DEFALUT: fclevel2NAME).append("(证监会)、");
        tempBuilder.append(null == swlevel2NAME ? FillConfig.DEFALUT: swlevel2NAME).append("(申万)");
        TextUtils.setText(tevInfoname, tempBuilder.toString());  // 所属行业

        TextUtils.setText(tevProvincename, (String) info.get("REGADDR"));
        TextUtils.setText(tevRegcapital, (String) info.get("ISSPRICE"));
        TextUtils.setText(tevLegalrepr, (String) info.get("CHAIRMAN"));
        TextUtils.setText(tevRegaddress, (String) info.get("BSECRETARY"));
        TextUtils.setText(tevBsecretarymail, (String) info.get("BSECRETARYMAIL"));
        TextUtils.setText(tevListoprice, (String) info.get("LISTOPRICE"));
        TextUtils.setText(tev_pledgeratio, (String) info.get("PLEDGERATIO"));
    }

    /**
     * 获取主要业务成功
     *
     * @param coreBusinesses
     */
    @Override
    public void onCoreBusinessSuccess(ArrayList<CoreBusiness> coreBusinesses) {
        if (null == coreBusinesses || coreBusinesses.size() == 0) {
            lelMainBussiness.addView(TextUtils.getNotingTextView(lelMainBussiness.getContext(),
                    "暂无该股票主要业务相关信息信息"));
            return;
        }
        lelMainBussiness.removeAllViews();
        if (coreBusinesses.size() > 0) {
            TextUtils.setText(tevCoreBusinessDate, coreBusinesses.get(0).ENDDATE_);
        }
        for (CoreBusiness coreBusiness : coreBusinesses) {
            if (coreBusiness != null) {
                ViewGroup viewGroup = (ViewGroup) layoutInflater.inflate(R.layout.item_lel_mainbussiness, lelMainBussiness, false);
                TextUtils.setText((TextView) viewGroup.findViewById(R.id.tev_bussiness_nature), coreBusiness.BUSSINESSNATURE_);
                TextUtils.setText((TextView) viewGroup.findViewById(R.id.tev_operrevenue), coreBusiness.OperRevenue);
                TextUtils.setText((TextView) viewGroup.findViewById(R.id.tev_oprtcast), coreBusiness.OperCost);
                TextUtils.setText((TextView) viewGroup.findViewById(R.id.tev_operprofit), coreBusiness.OperProfit);
                TextUtils.setText((TextView) viewGroup.findViewById(R.id.tev_operrevennuetotor), coreBusiness.OPERREVENUETOTOR_);
                lelMainBussiness.addView(viewGroup);
            }
        }
    }

    /**
     * 获取管理层信息成功
     *
     * @param leaderPersonInfos
     */
    @Override
    public void onLeaderPersonInfoSuccess( List<HashMap<String,Object>> leaderPersonInfos) {
//        managerAdapter.setData(leaderPersonInfos);

        if ( null==leaderPersonInfos  || leaderPersonInfos.isEmpty()) {
            lelLeader.addView(TextUtils.getNotingTextView(lelLeader.getContext(), "暂无该股票管理层信息"));
            return;
        }
        lelLeader.removeAllViews();
        for (HashMap<String, Object> leaderPersonInfo : leaderPersonInfos) {
            if ( null != leaderPersonInfo) {
                ViewGroup viewGroup = (ViewGroup) layoutInflater.inflate(R.layout.item_lel_leader, lelLeader, false);
                TextView tevName = (TextView) viewGroup.findViewById(R.id.tev_name);
                TextUtils.setText(tevName, (String) leaderPersonInfo.get("CNAME"));
                TextUtils.setText((TextView) viewGroup.findViewById(R.id.tev_positionName), (String) leaderPersonInfo.get("DUTY"));
                TextUtils.setText((TextView) viewGroup.findViewById(R.id.tev_rembeftax), (String) leaderPersonInfo.get("REMBEFTAX"));
                TextUtils.setText((TextView) viewGroup.findViewById(R.id.tev_holdafamt), (String) leaderPersonInfo.get("HOLDAFAMT"));

                final TextView tevBeginend = (TextView) viewGroup.findViewById(R.id.tev_beginend);
                StringBuilder tempBuilder = new StringBuilder();
                tempBuilder.append("任职时间：").append(null == leaderPersonInfo.get("BEGINEND") ? com.cvicse.stock.common.FillConfig.DEFALUT : leaderPersonInfo.get("BEGINEND"));
                tempBuilder.append("\n\n简介：").append(null == leaderPersonInfo.get("MEMO") ? com.cvicse.stock.common.FillConfig.DEFALUT : leaderPersonInfo.get("MEMO"));
                TextUtils.setText(tevBeginend, tempBuilder.toString());

                final ImageView imgFlag = (ImageView) viewGroup.findViewById(R.id.img_upflag);
                imgFlag.setTag(false);
                tevName.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean flag = (boolean) imgFlag.getTag();
                        imgFlag.setTag(!flag);
                        setImage(imgFlag,tevBeginend);
                    }
                });

                setImage(imgFlag,tevBeginend);
                lelLeader.addView(viewGroup);
            }
        }
    }

    private void setImage(ImageView imgFlag, TextView llMemo){
        boolean flag = (boolean) imgFlag.getTag();
        if( flag ){
            imgFlag.setImageLevel(1);
            llMemo.setVisibility(View.VISIBLE);
        }else{
            imgFlag.setImageLevel(0);
            llMemo.setVisibility(View.GONE);
        }
    }

    public void onLeaderPersonInfoSuccess2(ArrayList<LeaderPersonInfo> leaderPersonInfos) {
      /*  if ( null==leaderPersonInfos  || leaderPersonInfos.isEmpty()) {
            lelLeader.addView(TextUtils.getNotingTextView(lelLeader.getContext(), "暂无该股票管理层信息"));
            return;
        }
        lelLeader.removeAllViews();
        for (LeaderPersonInfo leaderPersonInfo : leaderPersonInfos) {
            if ( null!= leaderPersonInfo) {
                ViewGroup viewGroup = (ViewGroup) layoutInflater.inflate(R.layout.item_lel_leader, lelLeader, false);
                TextUtils.setText((TextView) viewGroup.findViewById(R.id.tev_name), leaderPersonInfo.leaderNmae);
                TextUtils.setText((TextView) viewGroup.findViewById(R.id.tev_positionName), leaderPersonInfo.positionName);
                TextUtils.setText((TextView) viewGroup.findViewById(R.id.tev_rembeftax), leaderPersonInfo.education);
                TextUtils.setText((TextView) viewGroup.findViewById(R.id.tev_holdafamt), leaderPersonInfo.gender);
                lelLeader.addView(viewGroup);
            }
        }*/
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        int screenWidth = ScreenUtils.getScreenWidth();
        int sixWidth = screenWidth / 5;
        holder1.setWidth(sixWidth);
        holder2.setWidth(sixWidth * 2);
        holder3.setWidth(sixWidth);
        holder4.setWidth(sixWidth);
//        holder5.setWidth(sixWidth);
    }
}
