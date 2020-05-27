/*
package com.cvicse.stock.aspectj;

import android.app.Activity;
import android.support.v4.app.Fragment;

import com.cvicse.stock.aspectj.annotation.CheckPermission;
import com.cvicse.stock.permission.PermissionsManager;
import com.cvicse.stock.permission.PermissionsResultAction;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

*/
/**
 * Created by liu_zlu on 2017/3/23 13:40
 *//*


public class PermissionAspectJ {

    @Pointcut("execution(@com.cvicse.stock.aspectj.annotation.CheckPermission * *(..)) && @annotation(checkPermission)")
    public void checkPermission(CheckPermission checkPermission) {

    }

    @Around("checkPermission(checkPermission)")
    public void check(final ProceedingJoinPoint joinPoint,final CheckPermission checkPermission) throws Throwable {
        String[] permissions = checkPermission.value();
        Activity activity = getActivity(joinPoint);
        if(activity != null){
            PermissionsManager.getInstance().requestPermissionsIfNecessaryForResult(activity, permissions,
                    new PermissionsResultAction() {
                        @Override
                        public void onGranted() {
                            try {
                                joinPoint.proceed();
                            } catch (Throwable throwable) {
                                throwable.printStackTrace();
                            }
                        }

                        @Override
                        public void onDenied(String permission) {

                        }
                    });
        } else {
            joinPoint.proceed();
        }
    }

    private Activity getActivity(JoinPoint joinPoint){
        Object target = joinPoint.getTarget();
        Activity activity = getActivityTarget(target);
        if(activity != null)return activity;
        Object[] objects = joinPoint.getArgs();
        if(objects == null || objects.length == 0)return null;
        for(Object o:objects){
            activity = getActivityTarget(o);
            if(activity != null)return activity;
        }
        return null;
    }

    private Activity getActivityTarget(Object target){
        if(target != null){
            if(target instanceof Activity){
                return (Activity) target;
            }
            if(target instanceof Fragment){
                return ((Fragment)target).getActivity();
            }
            if(target instanceof android.app.Fragment){
                return ((android.app.Fragment)target).getActivity();
            }
        }
        return null;
    }
}
*/
