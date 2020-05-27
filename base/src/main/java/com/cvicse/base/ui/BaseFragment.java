package com.cvicse.base.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;

import com.cvicse.base.mvp.IMVPHelper;
import com.cvicse.base.mvp.MVPHelper;
import com.cvicse.base.skin.Tintable;

/**
 * Created by liu_zlu on 2016/7/23.
 * 基本Fragment类
 */
public abstract class BaseFragment extends Fragment implements IMVPHelper,Tintable {
    protected boolean isCreated = false;
    private Bundle savedInstanceState;
    protected FragmentState fragmentState;
    public View view;
    @Override
    public void onActivityCreated( Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.savedInstanceState = savedInstanceState;
    }

    public BaseFragment(){
        fragmentState = new FragmentState(this);
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(isCreated){
            return ;
        }
        fragmentState.init(savedInstanceState);
    }

    @Override
    public LayoutInflater getLayoutInflater(Bundle savedInstanceState) {
        return getActivity().getLayoutInflater();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.view = view;
        fragmentState.onCreated();
    }
    protected abstract void onVisible();
    protected abstract void onInVisible();
    @Override
    public void onResume() {
        super.onResume();
        fragmentState.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        fragmentState.onPause();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        fragmentState.onHiddenChanged(hidden);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        fragmentState.setUserVisibleHint(isVisibleToUser);
    }

    public void setParentVisible(boolean parentVisible){
        fragmentState.setParentVisible(parentVisible);
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        fragmentState.onDestory();
        savedInstanceState = null;
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        fragmentState.onSaveInstanceState(outState);
    }

    /**
     * 覆盖BaseView的方法,获取创建时的bundle属性，用来页面恢复
     * @return
     */
    public Bundle getSavedInstanceState(){
        return savedInstanceState;
    }

    /**
     * 覆盖BaseView的方法，提示信息
     * @param message
     */
    public void showToast(String message){
        //ToastUtils.showLongToast(message);
    }
    @Override
    public MVPHelper getMVPHelper(){
        return fragmentState.mvpHelper;
    }

    @Override
    public void tint() {

    }

    /**
     * 判断fragment是否展示
     * @return
     */
    public boolean isShow(){
        return fragmentState.isShow();
    }
}
