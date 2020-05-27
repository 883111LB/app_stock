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
 * Created by ruan_ytai on 17-1-4.
 */

public class RefreshRateActivity extends PBaseActivity implements View.OnClickListener {

    public static final String CHOOSE_RATE = "choose_rate";

    @BindView(R.id.topbar) ToolBar mTopbar;

    @BindView(R.id.img_norefresh) ImageView mImgNorefresh;
    @BindView(R.id.rel_norefresh) RelativeLayout mRelNorefresh;

    @BindView(R.id.img_realtime_refresh) ImageView mImgRelRealtimeRefresh;
    @BindView(R.id.rel_realtime_refresh) RelativeLayout mRelRealtimeRefresh;

    @BindView(R.id.img_five) ImageView mImgFive;
    @BindView(R.id.rel_fivesecondrefresh) RelativeLayout mRelFivesecondrefresh;

    @BindView(R.id.img_fifteen) ImageView mImgFifteen;
    @BindView(R.id.rel_fifteenrefresh) RelativeLayout mRelFifteenrefresh;

    @BindView(R.id.img_thirty) ImageView mImgThirty;
    @BindView(R.id.rel_thirtyrefresh) RelativeLayout mRelThirtyrefresh;

    @BindView(R.id.img_sixty) ImageView mImgSixty;
    @BindView(R.id.rel_sixtyrefresh) RelativeLayout mRelSixtyrefresh;
    //20205013添加
    @BindView(R.id.img_two) ImageView mImgTwo;
    @BindView(R.id.rel_tworefresh) RelativeLayout mRelTworefresh;

    @BindView(R.id.img_three) ImageView mImgThree;
    @BindView(R.id.rel_threerefresh) RelativeLayout mRelThreerefresh;



    @Override
    protected int getLayoutId() {
        return R.layout.activity_refreshrate;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        mRelNorefresh.setOnClickListener(this);
        mRelRealtimeRefresh.setOnClickListener(this);
        mRelFivesecondrefresh.setOnClickListener(this);
        mRelFifteenrefresh.setOnClickListener(this);
        mRelThirtyrefresh.setOnClickListener(this);
        mRelSixtyrefresh.setOnClickListener(this);
        //20205013添加
        mRelTworefresh.setOnClickListener(this);
        mRelThreerefresh.setOnClickListener(this);

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

        init();//初始化选择的频率
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rel_norefresh:
                updateClick(R.id.rel_norefresh,-1);
                break;

            case R.id.rel_realtime_refresh:
                updateClick(R.id.rel_realtime_refresh,0);
                break;
                //20205013添加
            case R.id.rel_threerefresh:
                updateClick(R.id.rel_threerefresh,3);
                break;
            case R.id.rel_tworefresh:
                updateClick(R.id.rel_tworefresh,2);
                break;

            case R.id.rel_fivesecondrefresh:
                updateClick(R.id.rel_fivesecondrefresh,5);
                break;

            case R.id.rel_fifteenrefresh:
                updateClick(R.id.rel_fifteenrefresh,15);
                break;

            case R.id.rel_thirtyrefresh:
                updateClick(R.id.rel_thirtyrefresh,30);
                break;

            case R.id.rel_sixtyrefresh:
                updateClick(R.id.rel_sixtyrefresh,60);
                break;

            default:
                break;
        }

    }

    private void updateClick(int id,int rate) {
        updateUI(id);
        Setting.setRefreshRate(rate);
    }

    private void updateUI(int id) {
        mImgNorefresh.setVisibility(id == R.id.rel_norefresh ? View.VISIBLE : GONE);
        mImgRelRealtimeRefresh.setVisibility(id == R.id.rel_realtime_refresh ? View.VISIBLE : GONE);
        mImgFive.setVisibility(id == R.id.rel_fivesecondrefresh ? View.VISIBLE : GONE);
        mImgFifteen.setVisibility(id == R.id.rel_fifteenrefresh ? View.VISIBLE : GONE);
        mImgThirty.setVisibility(id == R.id.rel_thirtyrefresh ? View.VISIBLE : GONE);
        mImgSixty.setVisibility(id == R.id.rel_sixtyrefresh ? View.VISIBLE : GONE);

        //20205013添加
        mImgTwo.setVisibility(id == R.id.rel_tworefresh ? View.VISIBLE : GONE);
        mImgThree.setVisibility(id == R.id.rel_threerefresh ? View.VISIBLE : GONE);
    }

    private void init() {
        int rate = Setting.getRefreshRate();
        switch (rate) {
            case -1:
                updateUI(R.id.rel_norefresh);
                break;

            case 0:
                updateUI(R.id.rel_realtime_refresh);
                break;
            //20205013添加
            case 2:
                updateUI(R.id.rel_sixtyrefresh);
                break;

            case 3:
                updateUI(R.id.rel_sixtyrefresh);
                break;

            case 5:
                updateUI(R.id.rel_fivesecondrefresh);
                break;

            case 15:
                updateUI(R.id.rel_fifteenrefresh);
                break;

            case 30:
                updateUI(R.id.rel_thirtyrefresh);
                break;

            case 60:
                updateUI(R.id.rel_sixtyrefresh);
                break;

            default:
                break;
        }
    }

}
