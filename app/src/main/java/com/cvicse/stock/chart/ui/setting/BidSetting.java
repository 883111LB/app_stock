package com.cvicse.stock.chart.ui.setting;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.cvicse.stock.R;
import com.cvicse.stock.base.PBaseActivity;
import com.cvicse.stock.common.Setting.BidChartSetting;

import butterknife.BindView;

/**
 * 集合竞价设置
 * Created by tang_hua on 2020/4/9.
 */

public class BidSetting extends PBaseActivity implements View.OnClickListener {

    @BindView(R.id.tev_close)
    TextView tev_close;// 关闭页面
    @BindView(R.id.bit_open)
    TextView bit_open;// 集合竞价开启
    @BindView(R.id.bit_close)
    TextView bit_close;// 集合竞价关闭

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_bid_setting;
    }

    @SuppressLint("NewApi")
    @Override
    protected void initViews(Bundle savedInstanceState) {
        getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        getWindow().setGravity(Gravity.BOTTOM);
        // 获取本地保存的集合竞价展示状态
        String showState =  BidChartSetting.getBitchartState();// 设置集合竞价展示状态为1：展示
        if (BidChartSetting.STATE_NONE.equals(showState)) {
            // 展示状态为关闭
            bit_open.setBackground(null);
            bit_close.setBackground(getResources().getDrawable(R.drawable.shape_bg_red));
        } else {
            bit_open.setBackground(getResources().getDrawable(R.drawable.shape_bg_red));
            bit_close.setBackground(null);
        }
        tev_close.setOnClickListener(this);
        bit_open.setOnClickListener(this);
        bit_close.setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    @SuppressLint("NewApi")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tev_close:// 关闭页面
                finish();
                break;
            case R.id.bit_open:// 集合竞价开启
                bit_open.setBackground(getResources().getDrawable(R.drawable.shape_bg_red));
                bit_close.setBackground(null);
                // 展示状态设置为展示
                BidChartSetting.setBitchartState(BidChartSetting.STATE_SHOW);
                break;
            case R.id.bit_close:// 集合竞价关闭
                bit_open.setBackground(null);
                bit_close.setBackground(getResources().getDrawable(R.drawable.shape_bg_red));
                // 展示状态设置为不展示
                BidChartSetting.setBitchartState(BidChartSetting.STATE_NONE);
                break;
            default:
                break;
        }
    }
}
