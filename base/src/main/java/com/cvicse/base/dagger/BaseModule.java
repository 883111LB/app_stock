package com.cvicse.base.dagger;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.cvicse.base.exception.BaseException;
import com.cvicse.base.leak.Releaseable;
import com.cvicse.base.mvp.IMVPHelper;
import com.cvicse.base.mvp.IPresenter;
import com.cvicse.base.mvp.IView;
import com.cvicse.base.mvp.MVPHelper;
import com.cvicse.base.utils.ReflectUtils;
import com.tencent.bugly.crashreport.CrashReport;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.IdentityHashMap;

/**
 * Created by liu_zlu on 2016/7/23.
 */
public class BaseModule {
    public static IdentityHashMap<Class<?>,Class[]> identityHashMap = new IdentityHashMap<>();

    private static Handler handler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };
    public  static <T extends IPresenter> T  getPersenter(IView baseView, Class<T> tClass) {
        if(!(baseView instanceof IMVPHelper)){
            throw  new BaseException("请实现IMVPHelper");
        }
        MVPHelper mvpHelper = ((IMVPHelper)baseView).getMVPHelper();
        IPresenter iPresenter = (T) mvpHelper.getPresenter();
        Class viewClass = baseView.getClass();
        T t = null;
        boolean isRestore = false;
        if(iPresenter != null && tClass.isInstance(iPresenter)){
            t = (T) iPresenter;
            isRestore = true;
        }
        if(t == null){
            try {
                t = tClass.newInstance();
                mvpHelper.setPresenter(t);
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        if(t == null){
            throw new RuntimeException(tClass.getName()+"创建实例失败");
        }
        /**
         * 设置View代理
         */
        Class vClass[] = identityHashMap.get(viewClass);
        if(vClass == null){
            vClass = ReflectUtils.getInterface(viewClass,IView.class);
            identityHashMap.put(viewClass,vClass);
        }
        ProxyHandler proxyHandler = new ProxyHandler(baseView,viewClass);
        mvpHelper.setVProxyReleaseable(proxyHandler);
        t.setView(ReflectUtils.create(proxyHandler,vClass));
        /**
         * 设置Presenter代理
         */
        Class pClass[] = identityHashMap.get(tClass);
        if(pClass == null){
            pClass = ReflectUtils.getInterface(tClass,IPresenter.class);
            identityHashMap.put(tClass,pClass);
        }
        proxyHandler = new ProxyHandler(t,tClass);
        mvpHelper.setPProxyReleaseable(proxyHandler);
        if(isRestore){
            t.onRestore();
        }
        return t;//(T) ReflectUtils.create(proxyHandler,pClass);
    }

    public static class ProxyHandler implements InvocationHandler,Releaseable
    {
        private Object proxied;

        public ProxyHandler(Object proxied,Class<?> tClass )
        {
            this.proxied = proxied;
            if(proxied == null){
                throw new RuntimeException("请对"+tClass.getName()+"的实现类添加无参构造函数" );
            }
        }

        public Object invoke(Object proxy,final Method method,final Object[] args ) throws Throwable
        {
            //在转调具体目标对象之前，可以执行一些功能处理

            if(proxied == null){
                return null;
            }
            if(Thread.currentThread() == Looper.getMainLooper().getThread()){
                //转调具体目标对象的方法
                return method.invoke(proxied, args);
            } else {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            if(proxied != null){
                                method.invoke(proxied, args);
                            }
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        } catch (InvocationTargetException e) {
                            //throw new RuntimeException(e);
                            CrashReport.postCatchedException(e);
                        }
                    }
                });
                return null;
            }

        }

        @Override
        public void release() {
            proxied = null;
        }
    }

}
