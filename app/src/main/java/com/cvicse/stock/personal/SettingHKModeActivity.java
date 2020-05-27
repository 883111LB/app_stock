package com.cvicse.stock.personal;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.cvicse.stock.BaseApplication;
import com.cvicse.stock.R;
import com.cvicse.stock.base.PBaseActivity;
import com.cvicse.stock.base.ToolBar;
import com.cvicse.stock.common.Setting.Setting;
import com.cvicse.stock.view.ColorImageView;
import com.mitake.core.util.Permissions;

import butterknife.BindView;

import static android.view.View.GONE;


/**
 * 港股权限设置
 * Created by tang_xqing on 17-11-21.
 */
public class SettingHKModeActivity extends PBaseActivity implements View.OnClickListener {

    @BindView(R.id.topbar)
    ToolBar mTopbar;

    @BindView(R.id.img_hk_select_ten)
    ColorImageView imgHkSelectTen;
    @BindView(R.id.rel_hk_mode_ten)
    RelativeLayout relHkModeTen;

    @BindView(R.id.img_hk_select_aone)
    ColorImageView imgHkSelectAone;
    @BindView(R.id.rel_hk_mode_aone)
    RelativeLayout relHkModeAone;

    @BindView(R.id.img_hk_select_done)
    ColorImageView imgHkSelectDone;
    @BindView(R.id.rel_hk_mode_done)
    RelativeLayout relHkModeDone;

    @BindView(R.id.img_hk_select_shhk1)
    ColorImageView imgHkSelectShhk1;
    @BindView(R.id.rel_hk_mode_shhk1)
    RelativeLayout relHkModeShhk1;

    @BindView(R.id.img_hk_select_shhk5)
    ColorImageView imgHkSelectShhk5;
    @BindView(R.id.rel_hk_mode_shhk5)
    RelativeLayout relHkModeShhk5;

    @BindView(R.id.img_hk_select_szhk1)
    ColorImageView imgHkSelectSzhk1;
    @BindView(R.id.rel_hk_mode_szhk1)
    RelativeLayout relHkModeSzhk1;

    @BindView(R.id.img_hk_select_szhk5)
    ColorImageView imgHkSelectSzhk5;
    @BindView(R.id.rel_hk_mode_szhk5)
    RelativeLayout relHkModeSzhk5;

    private static final String HK_LEVEL_10 = Permissions.HK10;
    private static final String HK_LEVEL_A1 = Permissions.HKA1;
    private static final String HK_LEVEL_D1 = Permissions.HKD1;
    private static final String HK_LEVEL_SHHK1 = Permissions.SHHK1;
    private static final String HK_LEVEL_SHHK5 = Permissions.SHHK5;
    private static final String HK_LEVEL_SZHK1 = Permissions.SZHK1;
    private static final String HK_LEVEL_SZHK5 = Permissions.SZHK5;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_setting_hkmode;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {

        relHkModeTen.setOnClickListener(this);
        relHkModeAone.setOnClickListener(this);
        relHkModeDone.setOnClickListener(this);
        relHkModeShhk1.setOnClickListener(this);
        relHkModeShhk5.setOnClickListener(this);
        relHkModeSzhk1.setOnClickListener(this);
        relHkModeSzhk5.setOnClickListener(this);
        mTopbar.setToolBarClickListener(new ToolBar.ToolBarClickListener() {
            @Override
            public void onClick(View view, ToolBar.Type type) {
                switch(type){
                    case LEFT_TYPE:
                        onBackPressed();
                        break;
                    default:
                        break;
                }
            }
        });

        init();//初始化选择
    }

    @Override
    protected void initData() {
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rel_hk_mode_ten:
                updateClick(R.id.rel_hk_mode_ten,HK_LEVEL_10);
                break;

            case R.id.rel_hk_mode_aone:
                updateClick(R.id.rel_hk_mode_aone,HK_LEVEL_A1);
                break;

            case R.id.rel_hk_mode_done:
                updateClick(R.id.rel_hk_mode_done,HK_LEVEL_D1);
                break;

            case R.id.rel_hk_mode_shhk1:
                updateClick(R.id.rel_hk_mode_shhk1,HK_LEVEL_SHHK1);
                break;

            case R.id.rel_hk_mode_shhk5:
                updateClick(R.id.rel_hk_mode_shhk5,HK_LEVEL_SHHK5);
                break;

            case R.id.rel_hk_mode_szhk1:
                updateClick(R.id.rel_hk_mode_szhk1,HK_LEVEL_SZHK1);
                break;

            case R.id.rel_hk_mode_szhk5:
                updateClick(R.id.rel_hk_mode_szhk5,HK_LEVEL_SZHK5);
                break;

            default:
                break;
        }
    }

    private void updateClick(int id,String mode) {
        updateUI(id);
        Setting.setHKMode(mode);
        ((BaseApplication)BaseApplication.getInstance()).notifiPermission();
    }

    private void updateUI(int id) {
        imgHkSelectTen.setVisibility(id == R.id.rel_hk_mode_ten ? View.VISIBLE : GONE);
        imgHkSelectAone.setVisibility(id == R.id.rel_hk_mode_aone ? View.VISIBLE : GONE);
        imgHkSelectDone.setVisibility(id == R.id.rel_hk_mode_done ? View.VISIBLE : GONE);
        imgHkSelectShhk1.setVisibility(id == R.id.rel_hk_mode_shhk1 ? View.VISIBLE : GONE);
        imgHkSelectShhk5.setVisibility(id == R.id.rel_hk_mode_shhk5 ? View.VISIBLE : GONE);
        imgHkSelectSzhk1.setVisibility(id == R.id.rel_hk_mode_szhk1 ? View.VISIBLE : GONE);
        imgHkSelectSzhk5.setVisibility(id == R.id.rel_hk_mode_szhk5 ? View.VISIBLE : GONE);
    }

    private void init() {
        String mode = Setting.getHKMode();
        switch (mode) {
            case HK_LEVEL_10:
                updateUI(R.id.rel_hk_mode_ten);
                break;

            case HK_LEVEL_A1:
                updateUI(R.id.rel_hk_mode_aone);
                break;

            case HK_LEVEL_D1:
                updateUI(R.id.rel_hk_mode_done);
                break;

            case HK_LEVEL_SHHK1:
                updateUI(R.id.rel_hk_mode_shhk1);
                break;
            case HK_LEVEL_SHHK5:
                updateUI(R.id.rel_hk_mode_shhk5);
                break;

            case HK_LEVEL_SZHK1:
                updateUI(R.id.rel_hk_mode_szhk1);
                break;

            case HK_LEVEL_SZHK5:
                updateUI(R.id.rel_hk_mode_szhk5);
                break;

            default:
                break;
        }
    }
}
