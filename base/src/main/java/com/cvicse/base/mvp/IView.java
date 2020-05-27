package com.cvicse.base.mvp;

/**
 * Created by liu_zlu on 2016/7/18.
 */
public interface IView {

    void showToast(String message);

    void showLoading(int time);

    void stopLoading();

    void showDialog();

    void hideDialog();
}
