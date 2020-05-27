package com.cvicse.base.mvp;

/**
 * Created by liu_zlu on 2016/7/18.
 */
public interface IPresenter<T>{

    void setView(T view);

    void onRestore();

    boolean isRestore();
    void onCreated();
    void onResume();

    void onPause();

    void onDestroy();
}
