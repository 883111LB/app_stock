package com.cvicse.stock.base;

import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.cvicse.base.ui.BaseActivity;
import com.cvicse.inject.MvpKnife;

import butterknife.ButterKnife;

/**
 * Created by liu_zlu on 2017/1/3 16:39
 */
public abstract class PBaseActivity extends BaseActivity {

    public boolean isSkin() {
        return isSkin;
    }

    public void setSkin(boolean skin) {
        isSkin = skin;
    }

    private boolean isSkin = true;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        Configuration mConfiguration = this.getResources().getConfiguration(); //获取设置的配置信息
        int ori = mConfiguration.orientation; //获取屏幕方向
        if (ori != mConfiguration.ORIENTATION_LANDSCAPE) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        super.onCreate(savedInstanceState);
        MvpKnife.bind(this);
        setContentView(getLayoutId());
        if( null != getSupportActionBar() ){
            getSupportActionBar().hide();  // 隐藏ActionBar
        }
    }
    protected abstract int getLayoutId();

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        ButterKnife.bind(this);
        initViews(savedInstanceState);
        initData();
    }
    protected abstract void initViews(Bundle savedInstanceState);

    protected abstract void initData();
    /*@Override
    public Object getSystemService(String name) {
        if (LAYOUT_INFLATER_SERVICE.equals(name)) {
            if(mInflater != null)
                return mInflater;
            LayoutInflater mInflater = (LayoutInflater) super.getSystemService(name);
            if(isSkin){
                LayoutInflaterCompat.setFactory(mInflater,new SkinInflaterFactory());
            }
            return mInflater;
        }
        return super.getSystemService(name);
    }*/
}
