package com.cvicse.base.utils;

import android.app.Activity;

import java.util.ArrayList;

/**
 * Created by liuzilu on 2017/2/1.
 */

public class ActivityManager {
    private static ArrayList<ActivityLifecycleCallbacks> mActivityLifecycleCallbacks =
            new ArrayList<>();

    static ArrayList<Activity> activities = new ArrayList<>();

    public static void addActivity(Activity activity){
        activities.add(activity);
        for(ActivityLifecycleCallbacks activityLifecycleCallbacks:mActivityLifecycleCallbacks){
            activityLifecycleCallbacks.onActivityCreated(activity);
        }
    }

    public static void removeActivity(Activity activity){
        activities.remove(activity);
        for(ActivityLifecycleCallbacks activityLifecycleCallbacks:mActivityLifecycleCallbacks){
            activityLifecycleCallbacks.onActivityDestroyed(activity);
        }
    }

    public static ArrayList<Activity> getActivitys(){
        return activities;
    }

    public static void registerActivityLifecycleCallbacks(ActivityLifecycleCallbacks activityLifecycleCallbacks){
        mActivityLifecycleCallbacks.add(activityLifecycleCallbacks);
    }

    public static interface ActivityLifecycleCallbacks {
        void onActivityCreated(Activity activity);
        void onActivityDestroyed(Activity activity);
    }
}
