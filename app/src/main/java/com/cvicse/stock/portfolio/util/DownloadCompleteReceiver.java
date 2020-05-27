package com.cvicse.stock.portfolio.util;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.StrictMode;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.util.Log;

import com.cvicse.base.utils.ToastUtils;

import java.io.File;
import java.util.ArrayList;

/**调用系统下载器下载文件
 * Created by tang_xqing on 2017/11/2.
 */

public class DownloadCompleteReceiver extends BroadcastReceiver {
    public static final String TAG = "DownloadReceiver";
    public static ArrayList<Long> mDownloadIDs = new ArrayList<Long>();

    public String fileName;
    //    public Handler mHandler;
    private final int DOWNLOAD_FINISHED = 0x001;
    private Context context;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(DownloadManager.ACTION_DOWNLOAD_COMPLETE)) {
            long downId = intent.getLongExtra(
                    DownloadManager.EXTRA_DOWNLOAD_ID, -1);
            //
            if (mDownloadIDs.contains(downId)) {
                DownloadManager downloadManager = (DownloadManager) context
                        .getSystemService(Context.DOWNLOAD_SERVICE);

                DownloadManager.Query query = new DownloadManager.Query();
                // query.setFilterByStatus(DownloadManager.STATUS_SUCCESSFUL);
                query.setFilterById(downId);
                Cursor c = downloadManager.query(query);
                if (c.moveToFirst()) {
                    fileName = c.getString(c.getColumnIndex(DownloadManager.COLUMN_LOCAL_FILENAME));
//                    Message m = new Message();
//                    m.obj = fileName;
//                    m.what = DOWNLOAD_FINISHED;
//                    mHandler.sendMessage(m);

                    openFile(fileName);  // 下载完成后自动打开

                    Log.d(TAG, fileName + "完成");
                }else{
                    Log.d(TAG, "下载" + fileName + "失败");
                }
                if (c != null) {
                    c.close();
                }
                mDownloadIDs.remove(downId);
           }
        }
    }

    /**
     * 打开文件
     * @param fileName
     */
    public void openFile(String fileName) {
        File file = null;
        try {
            file = new File(fileName);
        }catch (Exception e){
            e.printStackTrace();
            return;
        }

        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);  // 增加读写权限
        Uri uri;
        if (Build.VERSION.SDK_INT >= 24) {
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());

            //data是file类型,忘了复制过来
            uri = FileProvider.getUriForFile(context, "com.chi.ssetest.fileprovider", file);
        } else {
            uri= Uri.fromFile(file);
        }
        // 得到文件名后缀
        String[] split = file.getName().split("\\.");
        String fileFormat = split[split.length - 1];
        if(TextUtils.isEmpty(fileFormat)){
            return;
        }
        if( "pdf".equals(fileFormat) ){
            intent.setDataAndType(uri,"application/pdf");
        }else  if( "xls".equals(fileFormat) || "xlsx".equals(fileFormat)){
            intent.setDataAndType(uri,"application/vnd.ms-excel");
        }else  if( "png".equals(fileFormat) || "jpg".equals(fileFormat)|| "jpeg".equals(fileFormat)|| "bmp".equals(fileFormat)){
            intent.setDataAndType(uri,"image/*.ms-excel");
        }else if("txt".equals(fileFormat)){
            intent.setDataAndType(uri, "text/plain");
        }else{
            intent.setDataAndType(uri, "application/msword");
        }
        try {
            context.startActivity(intent);
        }catch (Exception e){
            e.printStackTrace();
            ToastUtils.showShortToastSafe("没有找到应用打开该 "+fileFormat+" 类型文件");
        }
    }

    public void addData( long downloadId ){
        if( !mDownloadIDs.contains(downloadId) ){
            mDownloadIDs.add(downloadId);
        }
    }

    public void setContext(Context context){
        this.context = context;
    }
}
