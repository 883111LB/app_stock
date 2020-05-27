package com.cvicse.base.ui;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.ViewGroup;

import com.cvicse.base.mvp.IMVPHelper;
import com.cvicse.base.mvp.MVPHelper;
import com.cvicse.base.skin.SkinInflaterFactory;
import com.cvicse.base.utils.ActivityManager;
import com.cvicse.base.utils.KeyboardUtils;
import com.cvicse.base.utils.ViewUtils;

/**
 * 基础Activity
 * Created by liu_zlu on 2016/7/18.
 */
public abstract class BaseActivity extends ActionBarActivity implements IMVPHelper{

    private FragmentManager fragmentManager;

    private boolean isPause = false;

    protected LayoutInflater mInflater;

    private BaseContextWrapper baseContextWrapper;

    private MVPHelper mvpHelper;
    /**
     * 是否使用换肤
     */
    private boolean skinable = false;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        ActivityManager.addActivity(this);
        if(mvpHelper == null){
            mvpHelper = MVPHelper.create(savedInstanceState,this);
        }
        super.onCreate(savedInstanceState);
        if(mvpHelper != null){
            mvpHelper.onCreated();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(mvpHelper != null){
            mvpHelper.onResume();
        }
        isPause = false;
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(mvpHelper != null){
            mvpHelper.onPause();
        }
        isPause = true;
    }

    @Override
    protected void onDestroy() {
        ActivityManager.removeActivity(this);
        super.onDestroy();
        if(mvpHelper != null){
            mvpHelper.onDestory();
        }
        ViewGroup viewGroup = (ViewGroup) getWindow().getDecorView();
        ViewUtils.clearView(viewGroup);
        if(baseContextWrapper != null){
            baseContextWrapper.recycle();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if(mvpHelper != null){
            mvpHelper.onSaveInstanceState(outState);
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    public MVPHelper getMVPHelper(){
        return mvpHelper;
    }
    /**
     * 覆盖BaseView的方法，提示信息
     * @param message
     */
    public void showToast(String message){
       // ToastUtils.showLongToast(message);
    }

    public void showLoading(int time) {

    }

    public void stopLoading() {

    }

    public void showDialog() {

    }

    public void hideDialog() {

    }

    @Override
    public FragmentManager getSupportFragmentManager() {
        if(fragmentManager == null){
            fragmentManager = super.getSupportFragmentManager();
        }
        return fragmentManager;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        //点击隐藏键盘
        KeyboardUtils.touchHideSoftInput(this,event);
        return super.dispatchTouchEvent( event );
    }

    @Override
    public Object getSystemService(@Nullable String name) {
        if (LAYOUT_INFLATER_SERVICE.equals(name)) {
            if (mInflater == null) {
                baseContextWrapper = new BaseContextWrapper(this);
                mInflater = LayoutInflater.from(getBaseContext()).cloneInContext(baseContextWrapper);
                LayoutInflaterCompat.setFactory(mInflater,new SkinInflaterFactory());
            }
            return mInflater;
        }
        return super.getSystemService(name);
    }

    /**
     * 判断是否启动换肤
     * @return true 使用换肤 false 不使用
     */
    public boolean isSkinable() {
        return skinable;
    }

    /**
     * 设置是否启动换肤
     * @param skinable true 使用换肤 false 不使用
     */
    public void setSkinable(boolean skinable) {
        this.skinable = skinable;
    }
}
