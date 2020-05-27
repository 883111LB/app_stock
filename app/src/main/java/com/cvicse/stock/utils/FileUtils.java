package com.cvicse.stock.utils;

import android.os.Environment;

import com.cvicse.stock.BaseApplication;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by ruan_ytai on 17-1-10.
 */

public class FileUtils {
    // app根目录
    public static final String APP_DIRS = "Stock";

    private static final String APP_IMAGE = "image";

    public static final String APP_FILE = "files";

    private static final String CRASH_PATH = "crash_log";  // 异常文件目录
    public static final String CRASH_FILE_NAME = "stock_crash";
    public static final String CRASH_FILE_NAME_SUFFIX = ".trace"; //文件名后缀

    private static final File appDirs = getAppDirs();

    public static final File doucmentsFiles = createFile(null,APP_FILE);
    public static final File imagesFiles = createFile(null,APP_IMAGE);
    public static final File crashFiles = createFile(null,CRASH_PATH);

    public static File byteToFile(byte[] bytes,String id){
        if(bytes == null || bytes.length < 0){
            return null ;
        }
        File file = BaseApplication.getInstance().getFilesDir();
        File image = new File(file.getAbsolutePath()+"/image");
        if(!image.exists()){
            image.mkdirs();
        }

        File fileId = new File(image.getAbsolutePath()+"/"+id);
        FileOutputStream outputStream = null;
        BufferedOutputStream bufferedOutputStream = null;
        try {

            if(fileId.exists()){
                fileId.delete();
            }
            fileId.createNewFile();
            outputStream = new FileOutputStream(fileId);
            bufferedOutputStream = new BufferedOutputStream(outputStream);
            bufferedOutputStream.write(bytes);
            bufferedOutputStream.flush();
        } catch (IOException e) {
            fileId = null;
        } finally {
            if(outputStream != null){
                try {
                    outputStream.close();
                } catch (IOException e) {
                    fileId = null;
                }
            }
            if(bufferedOutputStream != null){
                try {
                    bufferedOutputStream.close();
                } catch (IOException e) {
                    fileId = null;
                }
            }
        }
        return fileId;
    }

    /**
     * 第一次进来，返回null，创建文件夹
     */
    public static File getFile(String id){
        File file = Environment.getExternalStorageDirectory();
        File image = new File(file.getAbsolutePath()+"/image");
        if(!image.exists()){
            image.mkdirs();
            return null;
        }

        File fileId = new File(image.getAbsolutePath()+"/"+id);
        if(fileId.exists()){
            return fileId;
        }
        return null;
    }

    /**
     * 得到APP根目录
     * @return
     */
    public static File getAppDirs(){
        File newFile = new File(Environment.getExternalStorageDirectory(),APP_DIRS);
        if(!newFile.exists() ){
            newFile.mkdirs();
        }
        return newFile;
    }

    /**
     * 创建文件夹
     * @param dirPath 根目录
     * @param name  文件夹名
     * @return
     */
    public static File createFile(String dirPath,String name){
        File appDirs = getAppDirs();
        File newFile = new File(appDirs.getAbsolutePath(),name);
        if( !newFile.exists() ){
            newFile.mkdirs();
        }
        return newFile;
    }

}
