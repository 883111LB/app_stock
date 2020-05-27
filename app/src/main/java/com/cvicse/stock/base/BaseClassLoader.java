package com.cvicse.stock.base;

/**
 * Created by liu_zlu on 2017/3/2 12:19
 */
public class BaseClassLoader extends ClassLoader {

    public BaseClassLoader(){

    }

    @Override
    protected Class<?> findClass(String className) throws ClassNotFoundException {
        return super.findClass(className);
    }
}
