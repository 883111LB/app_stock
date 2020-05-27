package com.cvicse.base.mvp;

import android.support.annotation.CallSuper;
import android.util.Log;

/**
 * Created by liu_zlu on 2016/11/14 17:28
 */
public class BasePresenter{

    private boolean isRestore = false;

    @CallSuper
    public void onRestore() {
        isRestore = true;
    }
    @CallSuper
    public boolean isRestore(){
        return isRestore;
    }

    @CallSuper
    public void onCreated() {

    }

    @CallSuper
    public void onResume(){
        Log.e(this.toString(),"onResume");
    }
    @CallSuper
    public void onPause(){
        Log.e(this.toString(),"onPause");
    }
    @CallSuper
    public void onDestroy() {

    }


}
