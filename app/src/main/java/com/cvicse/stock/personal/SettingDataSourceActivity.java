package com.cvicse.stock.personal;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.cvicse.stock.R;
import com.cvicse.stock.base.PBaseActivity;
import com.cvicse.stock.base.ToolBar;
import com.cvicse.stock.common.Setting.Setting;
import com.cvicse.stock.view.ColorImageView;

import butterknife.BindView;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

/**
 * F10数据源
 * Created by tang_xqing on 2017/9/21.
 */

public class SettingDataSourceActivity extends PBaseActivity implements View.OnClickListener {
    @BindView(R.id.topbar)
    ToolBar topbar;

    @BindView(R.id.img_datasource_g)
    ColorImageView imgDatasourceG;
    @BindView(R.id.rel_datasource_g)
    RelativeLayout relDatasourceG;

    @BindView(R.id.img_datasource_d)
    ColorImageView imgDatasourceD;
    @BindView(R.id.rel_datasource_d)
    RelativeLayout relDatasourceD;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_datasource;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        relDatasourceD.setOnClickListener(this);
        relDatasourceG.setOnClickListener(this);

        topbar.setToolBarClickListener(new ToolBar.ToolBarClickListener() {
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
        if(Setting.isDataSourceG()){
            imgDatasourceG.setVisibility(VISIBLE);
            imgDatasourceD.setVisibility(GONE);
        } else {
            imgDatasourceG.setVisibility(GONE);
            imgDatasourceD.setVisibility(VISIBLE );
        }
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.rel_datasource_d:
                if(Setting.isDataSourceD()){
                    return;
                }
                updateClick(R.id.rel_datasource_d);
                break;
            case R.id.rel_datasource_g:
                if(Setting.isDataSourceG()){
                    return;
                }
                updateClick(R.id.rel_datasource_g);
                break;
            default:
                break;
        }
    }

    private void updateClick(int id) {
        imgDatasourceG.setVisibility(id == R.id.rel_datasource_g ? View.VISIBLE : View.GONE );
        imgDatasourceD.setVisibility(id == R.id.rel_datasource_d ? View.VISIBLE : View.GONE );

        Setting.setDataSource();
    }
}
