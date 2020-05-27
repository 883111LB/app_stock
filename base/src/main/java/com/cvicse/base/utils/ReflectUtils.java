package com.cvicse.base.utils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by liu_zlu on 2016/11/17 16:39
 */
public class ReflectUtils {

    /**
     * 创建动态代理保证不出现网络请求或其他数据查询回调导致的崩溃问题
     * @param tClass
     * @return
     */
    public static <T> T create(InvocationHandler proxyHandler,Class<T> ...tClass){
        return (T) Proxy.newProxyInstance(
                proxyHandler.getClass().getClassLoader(),
                tClass ,
                proxyHandler);
    }

    /**
     * 获取继承指定接口的接口集合
     * @param cls 目标类
     * @param interCls 指定接口类
     * @return
     */
    public static Class[] getInterface(Class<?> cls,Class<?> interCls){
        if(!interCls.isInterface()){
            return null;
        }
        Type[] interfaces = cls.getGenericInterfaces();
        ArrayList<Class<?>> arrayList = new ArrayList<>();
        for(Type type: interfaces){
            if(interCls.isAssignableFrom((Class<?>) type)){
                arrayList.add((Class<?>) type);
            }
        }
        if(arrayList.size() > 0){
            Class[] classes = new Class[arrayList.size()];
            int i = 0;
            for(Class clss: arrayList){
                classes[i] = clss;
                i++;
            }
            return classes;
        }
        Class parentClass = cls.getSuperclass();
        return getInterface(parentClass,interCls);
    }
}
