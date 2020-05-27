package com.cvicse.stock.personal;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.cvicse.stock.BaseApplication;
import com.cvicse.stock.R;
import com.cvicse.stock.base.PBaseActivity;
import com.cvicse.stock.base.ToolBar;
import com.cvicse.stock.common.Setting.Setting;

import butterknife.BindView;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;


/** 境外权限设置
 * Created by ruan_ytai on 17-1-4.
 */

public class SettingOLLActivity extends PBaseActivity implements View.OnClickListener {

    @BindView(R.id.topbar) ToolBar mTopbar;

    @BindView(R.id.img_selectfirst) ImageView mImgSelectfirst;
    @BindView(R.id.rel_firstmode) RelativeLayout mRelFirstmode;

    @BindView(R.id.img_sectedsecond) ImageView mImgSectedsecond;
    @BindView(R.id.rel_secondmode) RelativeLayout mRelSecondmode;

    @BindView(R.id.img_OLL1) ImageView img_OLL1;
    @BindView(R.id.rel_OLL1) RelativeLayout rel_OLL1;

    @BindView(R.id.img_olshl1) ImageView img_olshl1;
    @BindView(R.id.rel_olshl1) RelativeLayout rel_olshl1;

    @BindView(R.id.img_olszl1) ImageView img_olszl1;
    @BindView(R.id.rel_olszl1) RelativeLayout rel_olszl1;

    @BindView(R.id.img_OLSHL2) ImageView img_OLSHL2;
    @BindView(R.id.rel_OLSHL2) RelativeLayout rel_OLSHL2;

    @BindView(R.id.img_OLSZL2) ImageView img_OLSZL2;
    @BindView(R.id.rel_OLSZL2) RelativeLayout rel_OLSZL2;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_settingrequestmode;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {

        mRelFirstmode.setVisibility(GONE);
        mRelSecondmode.setVisibility(GONE);
        rel_OLL1.setVisibility(VISIBLE);
        rel_olshl1.setVisibility(VISIBLE);
        rel_olszl1.setVisibility(VISIBLE);
        rel_OLSHL2.setVisibility(VISIBLE);
        rel_OLSZL2.setVisibility(VISIBLE);

        rel_OLL1.setOnClickListener(this);
        rel_olshl1.setOnClickListener(this);
        rel_olszl1.setOnClickListener(this);
        rel_OLSHL2.setOnClickListener(this);
        rel_OLSZL2.setOnClickListener(this);

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

        boolean chooseFlag = false;// 是否有权限被选中（如果没有的话就默认选中oll1）
        if(Setting.isOLL1()){
            chooseFlag = true;
            img_OLL1.setVisibility(VISIBLE);
//            img_olshl1.setVisibility(VISIBLE);
//            img_olszl1.setVisibility(VISIBLE);
        }
        if(Setting.isOLSHL1()){
            chooseFlag = true;
            img_olshl1.setVisibility(VISIBLE);
        }
        if(Setting.isOLSZL1()){
            chooseFlag = true;
            img_olszl1.setVisibility(VISIBLE);
        }
        if(Setting.isOLSHL2()){
            chooseFlag = true;
            img_OLSHL2.setVisibility(VISIBLE);
        }
        if(Setting.isOLSZL2()){
            chooseFlag = true;
            img_OLSZL2.setVisibility(VISIBLE);
        }
        // 是否有权限被选中（如果没有的话就默认选中oll1）
        if (!chooseFlag) {
            img_OLL1.setVisibility(VISIBLE);
            img_olshl1.setVisibility(VISIBLE);
            img_olszl1.setVisibility(VISIBLE);
            Setting.setOLL1();
            Setting.setOLSHL1();
            Setting.setOLSZL1();
        }
    }

    @Override
    protected void initData() {

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rel_OLL1:// oll1选中的话，下面两个也一起选中
                if (GONE == img_OLL1.getVisibility()) {
                    img_OLL1.setVisibility(VISIBLE);
                    img_olshl1.setVisibility(VISIBLE);
                    img_olszl1.setVisibility(VISIBLE);
                    Setting.setOLL1();
                    Setting.setOLSHL1();
                    Setting.setOLSZL1();
                } else {
                    img_OLL1.setVisibility(GONE);
                    img_olshl1.setVisibility(GONE);
                    img_olszl1.setVisibility(GONE);
                    // 移除oll1权限（现在还没找到移除方法）
                    Setting.removeOLL1();
                    Setting.removeOLSHL1();
                    Setting.removeOLSZL1();
                }
                break;
            case R.id.rel_olshl1:
                if (GONE == img_olshl1.getVisibility()) {
                    img_olshl1.setVisibility(VISIBLE);
                    Setting.setOLSHL1();
                    if (VISIBLE == img_olszl1.getVisibility()) {
                        // olshl1和olszl1同时选中，那么oll1也选中
                        img_OLL1.setVisibility(VISIBLE);
                        Setting.setOLL1();
                    }
                } else {
                    img_olshl1.setVisibility(GONE);
                    img_OLL1.setVisibility(GONE);
                    // 移除OLSHL1权限（现在还没找到移除方法）
                    Setting.removeOLSHL1();
                    Setting.removeOLL1();
                }
                break;
            case R.id.rel_olszl1:
                if (GONE == img_olszl1.getVisibility()) {
                    img_olszl1.setVisibility(VISIBLE);
                    Setting.setOLSZL1();
                    if (VISIBLE == img_olshl1.getVisibility()) {
                        // olshl1和olszl1同时选中，那么oll1也选中
                        img_OLL1.setVisibility(VISIBLE);
                        Setting.setOLL1();
                    }
                } else {
                    img_olszl1.setVisibility(GONE);
                    img_OLL1.setVisibility(GONE);
                    // 移除OLSZL1权限（现在还没找到移除方法）
                    Setting.removeOLSZL1();
                    Setting.removeOLL1();
                }
                break;
            case R.id.rel_OLSHL2:
                if (GONE == img_OLSHL2.getVisibility()) {
                    img_OLSHL2.setVisibility(VISIBLE);
                    Setting.setOLSHL2();
                } else {
                    img_OLSHL2.setVisibility(GONE);
                    // 移除OLSZL1权限（现在还没找到移除方法）
                    Setting.removeOLSHL2();
                }
                break;
            case R.id.rel_OLSZL2:
                if (GONE == img_OLSZL2.getVisibility()) {
                    img_OLSZL2.setVisibility(VISIBLE);
                    Setting.setOLSZL2();
                } else {
                    img_OLSZL2.setVisibility(GONE);
                    // 移除OLSZL1权限（现在还没找到移除方法）
                    Setting.removeOLSZL2();
                }
                break;
            default:
                break;
        }
        // 更新权限
        ((BaseApplication)BaseApplication.getInstance()).notifiPermission();
//        Setting.toggleLevel();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 更新权限
        ((BaseApplication)BaseApplication.getInstance()).notifiPermission();
    }

    private void updateUI(int id) {
        if (id == R.id.img_OLL1) {
            img_OLL1.setVisibility(GONE == img_OLL1.getVisibility() ? VISIBLE : GONE);
        }
        if (id == R.id.img_olshl1) {
            img_olshl1.setVisibility(GONE == img_olshl1.getVisibility() ? VISIBLE : GONE);
        }
        if (id == R.id.img_olszl1) {
            img_olszl1.setVisibility(GONE == img_olszl1.getVisibility() ? VISIBLE : GONE);
        }
        if (id == R.id.img_OLSHL2) {
            img_OLSHL2.setVisibility(GONE == img_OLSHL2.getVisibility() ? VISIBLE : GONE);
        }
        if (id == R.id.img_OLSZL2) {
            img_OLSZL2.setVisibility(GONE == img_OLSZL2.getVisibility() ? VISIBLE : GONE);
        }
        // 更新权限
        ((BaseApplication)BaseApplication.getInstance()).notifiPermission();
    }
}
