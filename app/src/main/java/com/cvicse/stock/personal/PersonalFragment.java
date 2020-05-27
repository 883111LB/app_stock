package com.cvicse.stock.personal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cvicse.stock.R;
import com.cvicse.stock.base.PBaseFragment;
import com.cvicse.stock.base.ToolBar;

import butterknife.BindView;

/**
 * Created by liu_zlu on 2017/1/3 16:51
 */
public class PersonalFragment extends PBaseFragment implements View.OnClickListener {

    @BindView(R.id.topbar)
    ToolBar mTopbar;
    @BindView(R.id.img_right)
    ImageView mImgRight;
    @BindView(R.id.lv_setting)
    TextView mLvSetting;
    @BindView(R.id.rel_setting)
    RelativeLayout mRelSetting;
    private View view;

    public static PersonalFragment newInstance() {
        return new PersonalFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_personal_main;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        mRelSetting.setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rel_setting:
                Intent intent = new Intent(getActivity(), SettingDetailActivity.class);
                startActivity(intent);
                break;

            default:
                break;
        }
    }
}
