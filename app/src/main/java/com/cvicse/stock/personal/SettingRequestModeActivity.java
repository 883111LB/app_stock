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


/** Level设置
 * Created by ruan_ytai on 17-1-4.
 */

public class SettingRequestModeActivity extends PBaseActivity implements View.OnClickListener {

    @BindView(R.id.topbar) ToolBar mTopbar;

    @BindView(R.id.img_selectfirst) ImageView mImgSelectfirst;
    @BindView(R.id.rel_firstmode) RelativeLayout mRelFirstmode;

    @BindView(R.id.img_sectedsecond) ImageView mImgSectedsecond;
    @BindView(R.id.rel_secondmode) RelativeLayout mRelSecondmode;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_settingrequestmode;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {

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

        if(Setting.isLevel1()){
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
            case R.id.rel_firstmode:
                if(Setting.isLevel1()){
                    return;
                }

                updateUI(R.id.img_selectfirst);
                break;

            case R.id.rel_secondmode:
                if(Setting.isLevel2()){
                    return;
                }

                updateUI(R.id.img_sectedsecond);
                break;
            default:
                break;
        }
        Setting.toggleLevel();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 更新权限
        ((BaseApplication)BaseApplication.getInstance()).notifiPermission();
    }

    private void updateUI(int id) {
        mImgSelectfirst.setVisibility(id == R.id.img_selectfirst ? VISIBLE : GONE);
        mImgSectedsecond.setVisibility(id == R.id.img_sectedsecond ? VISIBLE : GONE);
        // 更新权限
        ((BaseApplication)BaseApplication.getInstance()).notifiPermission();
    }
}
