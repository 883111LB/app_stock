package com.cvicse.stock.markets.helper;

import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.TextView;

import com.cvicse.base.utils.StringUtils;
import com.cvicse.stock.BaseApplication;
import com.cvicse.stock.R;
import com.cvicse.stock.common.ColorUtils;
import com.cvicse.stock.util.FormatUtils;
import com.cvicse.stock.utils.TextUtils;
import com.mitake.core.bean.HSAmountItem;
import com.mitake.core.response.HKMarInfoResponse;

import butterknife.BindView;
import butterknife.ButterKnife;

/** 港股通每日额度
 * Created by tang_xqing on 2018/5/16.
 */
public class HKTAmountHelper {

    @BindView(R.id.tev_initial_hhkg)
    TextView tevInitialHhkg;
    @BindView(R.id.tev_initial_shkg)
    TextView tevInitialShkg;
    @BindView(R.id.tev_initial_hg)
    TextView tevInitialHg;
    @BindView(R.id.tev_initial_sg)
    TextView tevInitialSg;
    @BindView(R.id.tev_remain_hhkg)
    TextView tevRemainHhkg;
    @BindView(R.id.tev_remain_shkg)
    TextView tevRemainShkg;
    @BindView(R.id.tev_remain_hg)
    TextView tevRemainHg;
    @BindView(R.id.tev_remain_sg)
    TextView tevRemainSg;
    @BindView(R.id.tev_status_hhkg)
    TextView tevStatusHhkg;
    @BindView(R.id.tev_status_shkg)
    TextView tevStatusShkg;
    @BindView(R.id.tev_status_hg)
    TextView tevStatusHg;
    @BindView(R.id.tev_status_sg)
    TextView tevStatusSg;

    int colorGray = ContextCompat.getColor(BaseApplication.getInstance(), R.color.text_gray_light);

    public HKTAmountHelper(View view) {
        ButterKnife.bind(this,view);
    }

    /**
     * 沪深港通
     * @param response
     */
    public void setHKMarInfoData(HKMarInfoResponse response) {
        if (response != null) {
            //沪港初始额度、剩余额度、沪港状态
            TextUtils.setText(tevInitialHhkg, FormatUtils.format(response.shInitialQuota), "--");
            TextUtils.setText(tevRemainHhkg, FormatUtils.format(response.shRemainQuota), "--");
            setStatusText(tevStatusHhkg, response.shStatus);

            //深港初始额度、剩余额度、深港状态
            TextUtils.setText(tevInitialShkg, FormatUtils.format(response.szInitialQuota), "--");
            TextUtils.setText(tevRemainShkg, FormatUtils.format(response.szRemainQuota), "--");
            setStatusText(tevStatusShkg, response.szStatus);
        }
    }

    /**
     * 沪深股通
     * @param hsAmountItem
     */
    public void setHSAmount(HSAmountItem hsAmountItem){
        if( null == hsAmountItem ){
            return;
        }
        TextUtils.setText(tevInitialHg, FormatUtils.format(hsAmountItem.shInitialQuota), "--");
        TextUtils.setText(tevRemainHg, FormatUtils.format(hsAmountItem.shRemainQuota), "--");

        TextUtils.setText(tevInitialSg, FormatUtils.format(hsAmountItem.szInitialQuota), "--");
        TextUtils.setText(tevRemainSg, FormatUtils.format(hsAmountItem.szRemainQuota), "--");
    }

    private void setStatusText(TextView textView, String data) {
        textView.setText(data);
        String text = null;
        if ("1".equals(data)) {
            text = "不可用";
            textView.setTextColor(ColorUtils.UP);
        } else if ("2".equals(data)) {
            text = "可用";
            textView.setTextColor(ColorUtils.DOWN);
        }
        if (StringUtils.isEmpty(data) || "0".equals(data)) {
            text = "源没有";
            textView.setTextColor(colorGray);
        }
        TextUtils.setText(textView, text, "--");
    }
}
