package com.cvicse.stock.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.os.Process;
import android.os.SystemClock;

import com.mitake.core.AppInfo;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

/** 异常处理，收集异常信息
 * Created by tang_xqing on 2018/8/21.
 */
public class CrashHandler implements Thread.UncaughtExceptionHandler {

    //上下文
    private Context mContext;

    //单例模式
    private static CrashHandler sInstance = new CrashHandler();

    private CrashHandler() {}

    public static CrashHandler getInstance() {
        return sInstance;
    }

    /**
     * 初始化方法
     * @param context
     */
    public void init(Context context) {
        //将当前实例设为系统默认的异常处理器
        Thread.setDefaultUncaughtExceptionHandler(this);
        //获取Context，方便内部使用
        mContext = context.getApplicationContext();
    }

    /**
     * 捕获异常回掉
     *
     * @param thread 当前线程
     * @param ex     异常信息
     */
    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        //导出异常信息到SD卡
        dumpExceptionToSDCard(ex);
        //上传异常信息到服务器
//        uploadExceptionToServer(ex);
        //延时1秒杀死进程
        SystemClock.sleep(2000);
        Process.killProcess(Process.myPid());
    }

    /**
     * 导出异常信息到SD卡
     * @param ex
     */
    private void dumpExceptionToSDCard(Throwable ex) {
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            return;
        }
        //获取当前时间
        long current = System.currentTimeMillis();
        String time = new SimpleDateFormat("yyyy-MM-dd").format(new Date(current));
        //以当前时间创建log文件
        File file = new File(FileUtils.crashFiles, FileUtils.CRASH_FILE_NAME+ time + FileUtils.CRASH_FILE_NAME_SUFFIX);
        try {
            //输出流操作
            PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(file,true)));
            //导出手机信息和异常信息
            PackageManager pm = mContext.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(mContext.getPackageName(), PackageManager.GET_ACTIVITIES);
            pw.println("发生异常时间：" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(current)));
            pw.println("应用版本：" + pi.versionName);
            pw.println("应用版本号：" + pi.versionCode);
            pw.println("sdk版本号：" + AppInfo.sdk_version);
            pw.println("android版本号：" + Build.VERSION.RELEASE);
            pw.println("android版本号API：" + Build.VERSION.SDK_INT);
            pw.println("手机制造商:" + Build.MANUFACTURER);
            pw.println("手机型号：" + Build.MODEL);
            pw.println("异常信息：" );
            StackTraceElement[] ses = ex.getStackTrace();
            for (StackTraceElement se : ses) {
                pw.println(se);
            }
            pw.println();
            Throwable cause = ex.getCause();
            while (cause != null) {
                cause.printStackTrace(pw);
                cause = cause.getCause();
            }
            pw.println();
            ex.printStackTrace(pw);
            pw.close();  //关闭输出流
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 上传异常信息到服务器
     *
     * @param ex
     */
    private void uploadExceptionToServer(Throwable ex) {
       /* Error error = new Error(ex.getMessage());
        error.save(new SaveListener<String>() {
            @Override
            public void done(String objectId, BmobException e) {

            }
        });*/
    }
}
