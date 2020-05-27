package com.cvicse.stock.markets.ui.stockdetail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cvicse.annotations.MVPInject;
import com.cvicse.base.utils.ScreenUtils;
import com.cvicse.stock.R;
import com.cvicse.stock.base.PBaseFragment;
import com.cvicse.stock.common.FillConfig;
import com.cvicse.stock.markets.presenter.marketdetail.contract.FundSummaryContract;
import com.cvicse.stock.utils.TextUtils;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;

/**
 * 基金摘要页面
 * Created by liu_zlu on 2017/2/13 21:20
 */
public class FundSummaryFragment extends PBaseFragment implements FundSummaryContract.View {

    @MVPInject  FundSummaryContract.Presenter presenter;

    private static final String STOCKID = "stockId";

    @BindView(R.id.holder_name) TextView holder1;
    @BindView(R.id.duty) TextView holder2;
    @BindView(R.id.rembeftax) TextView holder3;
    @BindView(R.id.holdefamt) TextView holder4;

    /**
     * 基金净值
     */
    @BindView(R.id.lel_value) LinearLayout lelFundValue;

    //基金分红
    @BindView(R.id.lel_fund_net_worth) LinearLayout lelFundNetWorth;

    //基金分红
    @BindView(R.id.fund_dividends) TextView fundDividends;
    //分配说明
    @BindView(R.id.tev_dividend_instructions) TextView tevDividendInstructions;
    //红利发放日
    @BindView(R.id.tev_issue_day) TextView tevIssueDay;
    //红利转结份额日
    @BindView(R.id.tev_transfer_share_date) TextView tevTransferShareDate;
    //除息日
    @BindView(R.id.tev_ex_dividend_date) TextView tevExDividendDate;
    //进展说明
    @BindView(R.id.tev_progress_description) TextView tevProgressDescription;
    //公布日期
    @BindView(R.id.tev_publishdate) TextView tevPublishDate;
    //权益登记日
    @BindView(R.id.tev_equity_registration_date) TextView tevEquityRegistrationDate;
    //每10份基金单位派现
    @BindView(R.id.tev_unit_send) TextView tevUnitSend;

    public static FundSummaryFragment newInstance(String stockId) {
        FundSummaryFragment fundSummaryFragment = new FundSummaryFragment();
        Bundle bundle = new Bundle();
        bundle.putString(STOCKID, stockId);
        fundSummaryFragment.setArguments(bundle);
        return fundSummaryFragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_fund_summary;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        String stockID = getArguments().getString(STOCKID);
        presenter.init(stockID);
    }

    @Override
    protected void initData() {
        presenter.querySummary();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        int screenWidth = ScreenUtils.getScreenWidth();
        int quarterWidth = screenWidth >> 2;
        holder1.setWidth(quarterWidth);
        holder2.setWidth(quarterWidth);
        holder3.setWidth(quarterWidth);
        holder4.setWidth(quarterWidth);
    }

    /**
     * 获取基金净值信息成功
     *
     * @param infos
     */
    @Override
    public void onFundValueSuccess(List<HashMap<String, Object>> infos) {
        if(infos == null || infos.size()<=0){
            lelFundValue.addView(TextUtils.getNotingTextView(lelFundValue.getContext(),
                    "暂无该股票基金净值信息"));
            return;
        }
        lelFundValue.removeAllViews();

        for(HashMap map : infos){
            if(map != null){
                ViewGroup viewGroup = (ViewGroup) layoutInflater.inflate(R.layout.item_lel_fundvalue,
                        lelFundValue,false);
                TextUtils.setText((TextView)viewGroup.findViewById(R.id.tev_time),
                        (String)map.get("NAVDATE"));
                TextUtils.setText((TextView)viewGroup.findViewById(R.id.tev_accunitnav),
                        (String)map.get("ACCUNITNAV"));
                TextUtils.setText((TextView)viewGroup.findViewById(R.id.tev_unitnav),
                        (String)map.get("UNITNAV"));
                TextUtils.setText((TextView)viewGroup.findViewById(R.id.tev_growrate),
                        (String)map.get("GROWRATE"));

                lelFundValue.addView(viewGroup);
            }
        }

    }

    /**
     * 获取基金分红信息成功
     *
     * @param info
     */
    @Override
    public void onFundNetWorthSuccess(HashMap<String, Object> info) {
        if(info == null || info.size() <= 0){
            return;
        }

        TextUtils.setText(tevDividendInstructions,(String)info.get("DEFSHAREMODE"), FillConfig.DEFALUT);
        TextUtils.setText(tevIssueDay,(String)info.get("INNERDEVDATE"), FillConfig.DEFALUT);
        TextUtils.setText(tevTransferShareDate,(String)info.get("INNERDEVPSETDATE"), FillConfig.DEFALUT);
        TextUtils.setText(tevExDividendDate,(String)info.get("INNERRIGHTDATE"), FillConfig.DEFALUT);
        TextUtils.setText(tevProgressDescription,(String)info.get("ISBONUS"), FillConfig.DEFALUT);
        TextUtils.setText(tevPublishDate,(String)info.get("PUBLISHDATE"), FillConfig.DEFALUT);
        TextUtils.setText(tevEquityRegistrationDate,(String)info.get("RECORDDATE"), FillConfig.DEFALUT);
        TextUtils.setText(tevUnitSend,(String)info.get("UNITPTAXDEV"), FillConfig.DEFALUT);

    }
}
