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


/**
 * Created by ruan_ytai on 17-2-13.
 */

public class SettingChangeSkinActivity extends PBaseActivity implements View.OnClickListener {

    @BindView(R.id.topbar) ToolBar mTopbar;

    @BindView(R.id.img_select_day) ImageView mImgSelectDay;
    @BindView(R.id.rel_day_mode) RelativeLayout mRelDayMode;

    @BindView(R.id.img_select_night) ImageView mImgSelectNight;
    @BindView(R.id.rel_night_mode) RelativeLayout mRelNightMode;



    @Override
    protected int getLayoutId() {
        setSkinable(true);
        return R.layout.activity_setting_change_skin;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        mRelDayMode.setOnClickListener(this);
        mRelNightMode.setOnClickListener(this);

        mTopbar.setBack(false);
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
        if(Setting.isDay()){
            mImgSelectDay.setVisibility(VISIBLE);
            mImgSelectNight.setVisibility(GONE);
        } else {
            mImgSelectDay.setVisibility(GONE);
            mImgSelectNight.setVisibility(VISIBLE );
        }
      //  restoreChoose();
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rel_day_mode:
                if(Setting.isDay()){
                    return;
                }
                updateUI(R.id.img_select_day);
                break;

            case R.id.rel_night_mode:
                if(!Setting.isDay()){
                    return;
                }
                updateUI(R.id.img_select_night);
                break;

            default:
                break;
        }

    }

    private void updateUI(int id) {
        Setting.toggleSkin();
        ((BaseApplication)BaseApplication.getInstance()).notifiStytle();
        mImgSelectDay.setVisibility(id == R.id.img_select_day ? VISIBLE : GONE);
        mImgSelectNight.setVisibility(id == R.id.img_select_night ? VISIBLE : GONE);
    }
}
