package com.cvicse.stock.portfolio.util;

import android.Manifest;
import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import com.cvicse.stock.utils.FileUtils;

import java.io.File;

/** 文件下载工具类
 * Created by tang_xqing on 2017/11/2.
 */
public class DownLoadUtil {
    private final String TAG = "DownLoadUtil";

    private Context context;
    private DownloadManager downloadManager;
    private DownloadCompleteReceiver downloadCompleteReceiver;

    public DownLoadUtil(Context context) {
        this.context = context;
        checkPermission();
    }

    // 判断该文件是否在下载
    private boolean isDowning(String uri) {
        boolean flag = false;
        try {
            DownloadManager.Query query = new DownloadManager.Query();

            query.setFilterByStatus(DownloadManager.STATUS_RUNNING);
            if (downloadManager == null) {
                downloadManager = ((DownloadManager) context
                        .getSystemService(Activity.DOWNLOAD_SERVICE));
            }
            Cursor c = downloadManager.query(query);
            String downingURI;
            while (c.moveToNext()) {
                downingURI = c.getString(c
                        .getColumnIndex(DownloadManager.COLUMN_URI));
                if (downingURI.equalsIgnoreCase(uri)) {
                    flag = true;
                    break;
                }
            }
            if (c != null) {
                c.close();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return flag;
    }

    public void downLoad(String url){
        registerReceiver();

        boolean downing = isDowning(url);
        if( downing ){
            // 正在下载
        }else{
            // 下载文件
            String[] split = url.split("/");
            String name = split[split.length-1];
            File file = new File(FileUtils.doucmentsFiles,name);
            if( file.exists() ){
                downloadCompleteReceiver.openFile(file.getAbsolutePath());
                return;
                //                file.delete();
            }

            // 本地存储路径
            // url = http://114.80.155.58:22225//20171102/NW0001-442539435.txt
            DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
            // 在通知栏中显示
            request.setShowRunningNotification(true);
            request.setVisibleInDownloadsUi(true);
//            String dir ="tempFilrDir";//本地文件存储目录 相对路径
            String dir =FileUtils.APP_DIRS+"/"+ FileUtils.APP_FILE;//本地文件存储目录 相对路径
            String fileName = name;//下载的文件名称
            request.setDestinationInExternalPublicDir(dir, fileName);//文件存储路径 绝对路径
            request.setTitle(fileName );//下载时在通知栏显示的文字

            //执行下载
            long downloadId = downloadManager.enqueue(request);

            //DownloadCompleteReceiver下载完成的接收器，第3步会讲到
            DownloadCompleteReceiver.mDownloadIDs.add(new Long(downloadId));

            downloadCompleteReceiver.addData(downloadId);
        }
    }

    /**
     * 注册广播
     */
    private void registerReceiver(){
        // 注册接收完成的广播
        if( null == downloadCompleteReceiver ){
            downloadCompleteReceiver = new DownloadCompleteReceiver();
            downloadCompleteReceiver.setContext(context);
            IntentFilter myIntentFilter = new IntentFilter();
            myIntentFilter.addAction(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
            // 注册广播
            context.registerReceiver(downloadCompleteReceiver, myIntentFilter);
        }
    }

    /**
     * 检查权限
     */
    private void checkPermission(){
        if(ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions((Activity) context,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},1);
        }
    }

    public void destory(){
        if( null != downloadCompleteReceiver ) {
            context.unregisterReceiver(downloadCompleteReceiver);
        }
    }
}
