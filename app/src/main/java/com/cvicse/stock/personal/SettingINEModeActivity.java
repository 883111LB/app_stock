package com.cvicse.stock.personal;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cvicse.stock.BaseApplication;
import com.cvicse.stock.R;
import com.cvicse.stock.base.PBaseActivity;
import com.cvicse.stock.base.ToolBar;
import com.cvicse.stock.common.Setting.Setting;

import butterknife.BindView;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;


/** 上期所level设置
 * Created by liu_bin on 19-10-8.
 */
public class SettingINEModeActivity extends PBaseActivity implements View.OnClickListener {

    @BindView(R.id.topbar) ToolBar mTopbar;

    @BindView(R.id.img_zce_selectfirst) ImageView mImgSelectfirst;
    @BindView(R.id.rel_zce_firstmode) RelativeLayout mRelFirstmode;

    @BindView(R.id.img_zce_sectedsecond) ImageView mImgSectedsecond;
    @BindView(R.id.rel_zce_secondmode) RelativeLayout mRelSecondmode;

    @BindView(R.id.tev_level_1) TextView tevLevel1;
    @BindView(R.id.tev_level_2) TextView tevLevel2;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_settingzcemode;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        mTopbar.setTitle("上期所原油权限设置");
        tevLevel1.setText("Level-1");
        tevLevel2.setText("Level-2");

        mRelFirstmode.setOnClickListener(this);
        mRelSecondmode.setOnClickListener(this);
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

        if(Setting.isIneLevel1()){
            mImgSelectfirst.setVisibility(VISIBLE);
            mImgSectedsecond.setVisibility(GONE);
        } else {
            mImgSelectfirst.setVisibility(GONE);
            mImgSectedsecond.setVisibility(VISIBLE );
        }
    }

    @Override
    protected void initData() {
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rel_zce_firstmode:
                if(Setting.isIneLevel1()){
                    return;
                }
                updateUI(R.id.img_zce_selectfirst);
                break;

            case R.id.rel_zce_secondmode:
                if(Setting.isIneLevel2()){
                    return;
                }
                updateUI(R.id.img_zce_sectedsecond);
                break;
            default:
                break;
        }
        Setting.toggleINELevel();
    }

    private void updateUI(int id) {
        mImgSelectfirst.setVisibility(id == R.id.img_zce_selectfirst ? VISIBLE : GONE);
        mImgSectedsecond.setVisibility(id == R.id.img_zce_sectedsecond ? VISIBLE : GONE);

        // 更新权限
        ((BaseApplication)BaseApplication.getInstance()).notifiPermission();
    }
}
