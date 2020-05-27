package com.cvicse.inject;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by liu_zlu on 2017/2/24 16:29
 */
public class MvpKnife {
    static final Map<Class<?>, Constructor<?>> BINDINGS = new LinkedHashMap<>();
    public static void bind(Object target){
        Class<?> targetClass = target.getClass();
        Constructor<?> constructor = findBindingConstructorForClass(targetClass);
        if(constructor == null){
            return;
        }
        //noinspection TryWithIdenticalCatches Resolves to API 19+ only type.
        try {
            MvpBinder mvpBinder = (MvpBinder) constructor.newInstance();
            mvpBinder.bind(target);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Unable to invoke " + constructor, e);
        } catch (InstantiationException e) {
            throw new RuntimeException("Unable to invoke " + constructor, e);
        } catch (InvocationTargetException e) {
            Throwable cause = e.getCause();
            if (cause instanceof RuntimeException) {
                throw (RuntimeException) cause;
            }
            if (cause instanceof Error) {
                throw (Error) cause;
            }
            throw new RuntimeException("Unable to create binding instance.", cause);
        }
    }

    private static Constructor<?> findBindingConstructorForClass(Class<?> cls) {
        Constructor<?> bindingCtor = BINDINGS.get(cls);
        if (bindingCtor != null) {
            return bindingCtor;
        }
        String clsName = cls.getName();
        if (clsName.startsWith("android.") || clsName.startsWith("java.")) {
            return null;
        }
        try {
            Class<?> bindingClass = Class.forName(clsName + "_MvpBinder");
            //noinspection unchecked
            bindingCtor = bindingClass.getConstructor();
        } catch (ClassNotFoundException e) {
            bindingCtor = findBindingConstructorForClass(cls.getSuperclass());
        } catch (NoSuchMethodException e) {
            throw new RuntimeException("Unable to find binding constructor for " + clsName, e);
        }
        BINDINGS.put(cls, bindingCtor);
        return bindingCtor;
    }
}
