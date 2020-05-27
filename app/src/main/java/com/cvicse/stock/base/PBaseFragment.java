package com.cvicse.stock.base;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cvicse.base.mvp.IView;
import com.cvicse.base.ui.BaseFragment;
import com.cvicse.inject.MvpKnife;

import butterknife.ButterKnife;

/**
 * Created by liu_zlu on 2017/1/3 16:40
 */
public abstract class PBaseFragment extends BaseFragment {

    private View viewContain;

    protected LayoutInflater layoutInflater;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(this instanceof IView){
            MvpKnife.bind(this);
        }
    }

    public View getMainView() {
        return viewContain;
    }

    /**
     *  由子类继承，提供Fragment页面布局
     * @return
     */
    @LayoutRes
    protected abstract int getLayoutId();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if(viewContain != null){
            return viewContain;
        }
        layoutInflater = inflater;
        viewContain = inflater.inflate(getLayoutId(),container,false);
        return viewContain;
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        if(isCreated){
            return;
        }
        isCreated = true;
        ButterKnife.bind(this,getView());
        initViews(savedInstanceState);
        //ButterKnife.bind(this,getView());
        initData();

        super.onViewCreated(view, savedInstanceState);
    }

    /**
     * 当Fragment可见
     */
    @Override
    protected void onVisible(){
        //super.onVisible();
    }
    /**
     * 当Fragment不可见
     */
    @Override
    protected void onInVisible(){

    }

    protected abstract void initViews(Bundle savedInstanceState);

    protected abstract void initData();


    public void showLoading(int time) {

    }

    public void stopLoading() {

    }

    public void showDialog() {

    }

    public void hideDialog() {

    }
}
