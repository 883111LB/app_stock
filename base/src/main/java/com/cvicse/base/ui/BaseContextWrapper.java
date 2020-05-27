package com.cvicse.base.ui;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.Resources;

import java.lang.ref.WeakReference;
import java.lang.reflect.Field;

/**
 * Activity 封装类
 * Created by liu_zlu on 2016/11/7 11:29
 */
public class BaseContextWrapper extends ContextWrapper {

    static Field field;
    static {
        try {
            field = ContextWrapper.class.getDeclaredField("mBase");
            field.setAccessible(true);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }
    WeakReference<Context> contextWeakReference;
    private Context application;
    public BaseContextWrapper(Context context) {
        super(null);
        contextWeakReference = new WeakReference<>(context);
        application = context.getApplicationContext();
        attachBaseContext(context);
    }

    @Override
    public Resources.Theme getTheme() {
        Context context = contextWeakReference.get();
        if(context != null){
            return context.getTheme();
        }
        return super.getTheme();
    }

    @Override public Object getSystemService(String name) {
        Context context = contextWeakReference.get();
        if(context == null){
            return application.getSystemService(name);
        } else {
            if (LAYOUT_INFLATER_SERVICE.equals(name)) {
                return context.getSystemService(name);
            }
        }
        return application.getSystemService(name);
    }

    public void recycle(){
        try {
            field.set(this,BaseApplication.getInstance());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public Activity getActivityContext(){
        Activity context = (Activity) contextWeakReference.get();
        return context;
    }

    @Override
    public ClassLoader getClassLoader() {
        Activity context = (Activity) contextWeakReference.get();
        return context.getClassLoader();
    }
}
