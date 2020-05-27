package com.cvicse.base.utils;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;

import java.lang.ref.WeakReference;
import java.util.LinkedList;
import java.util.List;

/**
 * utils 类初始化过程
 * Created by liu_zlu on 2016/11/16 11:47
 */
public class UtilsConfig {
    /**
     * BaseAppliation
     */
    private static Context initContext;


    public static List<Activity> sActivityList = new LinkedList<>();

    public static WeakReference<Activity> sTopActivityWeakRef;

    private static Application.ActivityLifecycleCallbacks mCallbacks = new Application.ActivityLifecycleCallbacks() {
        @Override
        public void onActivityCreated(Activity activity, Bundle bundle) {
            sActivityList.add(activity);
            setTopActivityWeakRef(activity);
        }

        @Override
        public void onActivityStarted(Activity activity) {
            setTopActivityWeakRef(activity);
        }

        @Override
        public void onActivityResumed(Activity activity) {
            setTopActivityWeakRef(activity);
        }

        @Override
        public void onActivityPaused(Activity activity) {

        }

        @Override
        public void onActivityStopped(Activity activity) {

        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {

        }

        @Override
        public void onActivityDestroyed(Activity activity) {
            sActivityList.remove(activity);
        }
    };

    public UtilsConfig(){
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    public static void init(Context context){
        initContext = context.getApplicationContext();
    }

    public static Context getInitContext(){
        if(initContext == null)
            throw new NullPointerException("u should init first");
        return initContext;
    }

    private static void setTopActivityWeakRef(final Activity activity) {
        if (activity.getClass() == PermissionUtils.PermissionActivity.class) return;
        if (sTopActivityWeakRef == null || !activity.equals(sTopActivityWeakRef.get())) {
            sTopActivityWeakRef = new WeakReference<>(activity);
        }
    }
}
