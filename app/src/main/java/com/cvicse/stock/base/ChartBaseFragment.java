package com.cvicse.stock.base;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * 股票图基础类
 * Created by liu_zlu on 2017/4/22 15:36
 */
public abstract class ChartBaseFragment extends PBaseFragment {
    /**
     * 是否横屏
     */
    protected boolean isLand = false;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Configuration mConfiguration = this.getResources().getConfiguration(); //获取设置的配置信息
        int ori = mConfiguration.orientation; //获取屏幕方向

        if (ori == mConfiguration.ORIENTATION_LANDSCAPE) {
            isLand = true;
        } else {
            isLand = false;
        }
    }
}
