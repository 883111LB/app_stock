package com.cvicse.stock.personal;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.cvicse.stock.R;
import com.cvicse.stock.base.PBaseActivity;
import com.cvicse.stock.base.ToolBar;
import com.cvicse.stock.common.Setting.Setting;

import butterknife.BindView;

import static android.view.View.GONE;

/**
 * Created by ruan_ytai on 17-2-13.
 */

public class SettingTimeoutReminderActivity extends PBaseActivity implements View.OnClickListener{
    @BindView(R.id.topbar) ToolBar mTopbar;

    @BindView(R.id.img_five_second) ImageView mImgFiveSecond;
    @BindView(R.id.rel_five_second) RelativeLayout mRelFiveSecond;

    @BindView(R.id.img_eight_second) ImageView mImgEightSecond;
    @BindView(R.id.rel_eight_second) RelativeLayout mRelEightSecond;

    @BindView(R.id.img_ten_second) ImageView mImgTenSecond;
    @BindView(R.id.rel_ten_second) RelativeLayout mRelTenSecond;

    @BindView(R.id.img_twelve_second) ImageView mImgTwelveSecond;
    @BindView(R.id.rel_twelve_second) RelativeLayout mRelTwelveSecond;

    @BindView(R.id.img_fifteen_second) ImageView mImgFifteenSecond;
    @BindView(R.id.rel_fifteen_second) RelativeLayout mRelFifteenSecond;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_setting_timeoutreminder;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        initDefaultSetting();

        mRelFiveSecond.setOnClickListener(this);
        mRelEightSecond.setOnClickListener(this);
        mRelTenSecond.setOnClickListener(this);
        mRelTwelveSecond.setOnClickListener(this);
        mRelFifteenSecond.setOnClickListener(this);

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

    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.rel_five_second:
                updateClick(R.id.rel_five_second,5);
                break;

            case R.id.rel_eight_second:
                updateClick(R.id.rel_eight_second,8);
                break;

            case R.id.rel_ten_second:
                updateClick(R.id.rel_ten_second,10);
                break;

            case R.id.rel_twelve_second:
                updateClick(R.id.rel_twelve_second,12);
                break;

            case R.id.rel_fifteen_second:
                updateClick(R.id.rel_fifteen_second,15);
                break;

            default:
                break;
        }

    }
    private void updateClick(int id,int timeOut){
        Setting.setTimeOut(timeOut);
        updateUI(id);
    }
    private void updateUI(int id){
        mImgFiveSecond.setVisibility(id == R.id.rel_five_second ? View.VISIBLE : GONE);
        mImgEightSecond.setVisibility(id == R.id.rel_eight_second ? View.VISIBLE : GONE);
        mImgTenSecond.setVisibility(id == R.id.rel_ten_second ? View.VISIBLE : GONE);
        mImgTwelveSecond.setVisibility(id == R.id.rel_twelve_second ? View.VISIBLE : GONE);
        mImgFifteenSecond.setVisibility(id == R.id.rel_fifteen_second ? View.VISIBLE : GONE);
    }


    private void initDefaultSetting(){
        int timeOut = Setting.getTimeOut();
        switch(timeOut){
            case 5:
                updateUI(R.id.rel_five_second);
                break;

            case 8:
                updateUI(R.id.rel_eight_second);
                break;

            case 10:
                updateUI(R.id.rel_ten_second);
                break;

            case 20:
                updateUI(R.id.rel_twelve_second);
                break;

            case 30:
                updateUI(R.id.rel_fifteen_second);
                break;

            default:
                break;
        }
    }


}
