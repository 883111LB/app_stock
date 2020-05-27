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
 * 港股指数权限设置
 * Created by tang_h on 20-2-18.
 */
public class SettingHKZSModeActivity extends PBaseActivity implements View.OnClickListener {

    @BindView(R.id.topbar)
    ToolBar mTopbar;

    @BindView(R.id.img_hkzs_select_none)
    ColorImageView imgHkSelectNone;// 无权限
    @BindView(R.id.rel_hkzs_mode_none)
    RelativeLayout relHkModeNone;// 无权限

    @BindView(R.id.img_hkzs_select_az)
    ColorImageView imgHkSelectAz;// hkaz
    @BindView(R.id.rel_hkzs_mode_az)
    RelativeLayout relHkModeAz;// hkaz

    @BindView(R.id.img_hkzs_select_dz)
    ColorImageView imgHkSelectDz;// hkdz
    @BindView(R.id.rel_hkzs_mode_dz)
    RelativeLayout relHkModeDz;// hkdz

    private static final String HK_LEVEL_NONE = "";
    private static final String HK_LEVEL_AZ = Permissions.HKAZ;
    private static final String HK_LEVEL_DZ = Permissions.HKDZ;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_setting_hkzsmode;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {

        relHkModeNone.setOnClickListener(this);
        relHkModeAz.setOnClickListener(this);
        relHkModeDz.setOnClickListener(this);
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
            case R.id.rel_hkzs_mode_none:
                updateClick(R.id.rel_hkzs_mode_none,HK_LEVEL_NONE);
                break;

            case R.id.rel_hkzs_mode_az:
                updateClick(R.id.rel_hkzs_mode_az,HK_LEVEL_AZ);
                break;

            case R.id.rel_hkzs_mode_dz:
                updateClick(R.id.rel_hkzs_mode_dz,HK_LEVEL_DZ);
                break;

            default:
                break;
        }
    }

    private void updateClick(int id,String mode) {
        updateUI(id);
        Setting.setHKZSMode(mode);
        ((BaseApplication)BaseApplication.getInstance()).notifiPermission();
    }

    private void updateUI(int id) {
        imgHkSelectNone.setVisibility(id == R.id.rel_hkzs_mode_none ? View.VISIBLE : GONE);
        imgHkSelectAz.setVisibility(id == R.id.rel_hkzs_mode_az ? View.VISIBLE : GONE);
        imgHkSelectDz.setVisibility(id == R.id.rel_hkzs_mode_dz ? View.VISIBLE : GONE);
    }

    private void init() {
        String mode = Setting.getHKZSMode();
        switch (mode) {
            case HK_LEVEL_NONE:
                updateUI(R.id.rel_hkzs_mode_none);
                break;

            case HK_LEVEL_AZ:
                updateUI(R.id.rel_hkzs_mode_az);
                break;

            case HK_LEVEL_DZ:
                updateUI(R.id.rel_hkzs_mode_dz);
                break;

            default:
                break;
        }
    }
}
