package com.cvicse.base.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.cvicse.base.mvp.IPresenter;
import com.cvicse.base.mvp.MVPHelper;

import java.util.List;

/**
 * Fragment 状态类
 * Created by liu_zlu on 2017/2/16 11:25
 */
class FragmentState {
    MVPHelper mvpHelper;
    BaseFragment baseFragment;
    FragmentManager fragmentManager;
    /**
     * Fragment的父Fragment是否可见
     */
    boolean parentVisible = true;
    //private State state = State.NONE;
    FragmentState(BaseFragment baseFragment){
        this.baseFragment = baseFragment;
    }

    public void init(Bundle savedInstanceState){
        mvpHelper =  MVPHelper.create(savedInstanceState, baseFragment, new MVPHelper.IPresenterListener() {
            @Override
            public void onSetted(IPresenter ipresenter) {
                update();
            }
        });
        BaseFragment parentFragment = (BaseFragment) baseFragment.getParentFragment();
        //parentFragment不为null,且不显示
        if(parentFragment != null && !parentFragment.isShow()){
            baseFragment.setParentVisible(false);
        } else {
            baseFragment.setParentVisible(true);
        }
    }

    /**
     * 是否可见
     */
    private boolean isVisible = true;
    /**
     * 是否是resume状态
     */
    private boolean isResume = true;
    private boolean created = false;
    /**
     * presenter是否resume状态
     */
    private boolean hasResume = false;
    /*public boolean onCreatedVisible(){
        if(!isVisible){
            created = false;
        }
        return isVisible;
    }*/


    /**
     * Fragment onViewCreated 调用
     */
    void onCreated(){
        if(mvpHelper != null){
            mvpHelper.onCreated();
        }
        created = true;
        update();
    }

    /**
     * Fragment onPause 调用
     */
    void onPause(){
        if(!isResume){
            return;
        }
        isResume = false;
        update();
    }

    /**
     * Fragment onResume 调用
     */
    void onResume(){
        if(isResume){
            return;
        }
        isResume = true;
        update();
    }
    /**
     * Fragment onHiddenChanged 调用
     */
    void onHiddenChanged(boolean hidden){
        isVisible = !hidden;
        update();
    }
    /**
     * Fragment setUserVisibleHint 调用
     */
    void setUserVisibleHint(boolean isVisibleToUser) {
        if(isVisible && isVisibleToUser){
            return;
        }
        isVisible = isVisibleToUser;
        update();
    }


    void update(){

        if(isShow()){
            if(mvpHelper != null && !hasResume){
                hasResume = true;
                mvpHelper.onResume();
            }
            updateChild(true);
        } else {
            if(mvpHelper != null && hasResume){
                hasResume = false;
                mvpHelper.onPause();
            }
            updateChild(false);
        }
    }


    public boolean isShow(){
        return isVisible && isResume && parentVisible && created;
    }
    /**
     * Fragment onDestory 调用
     */
    void onDestory() {
        if(mvpHelper != null){
            mvpHelper.onDestory();
        }
    }

    void onSaveInstanceState(Bundle outState) {
        if(mvpHelper != null){
            mvpHelper.onSaveInstanceState(outState);
        }
    }

    /**
     * Fragment 实现setParentVisible方法，并调用FragmentState的setParentVisible
     * @param parentVisible
     */
    void setParentVisible(boolean parentVisible){
        this.parentVisible = parentVisible;
        update();
    }

    private void updateChild(boolean isVisible){
        if(isVisible){
            /**
             * 当Fragment可见时调用的方法
             */
            baseFragment.onVisible();
        } else {
            /**
             * 当Fragment不可见时调用的方法
             */
            baseFragment.onInVisible();
        }

        if(!baseFragment.isAdded()){
            return;
        }
        if(fragmentManager == null){
            fragmentManager = baseFragment.getChildFragmentManager();
        }
        List<Fragment> fragments = fragmentManager.getFragments();
        if(fragments != null){
            for(Fragment fragment:fragments){
                if(fragment != null && fragment instanceof BaseFragment){
                    ((BaseFragment)fragment).setParentVisible(isVisible);
                }
            }
        }
    }
}
