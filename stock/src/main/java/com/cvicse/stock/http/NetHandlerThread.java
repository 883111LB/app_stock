package com.cvicse.stock.http;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;

import com.cvicse.stock.http.netinfo.NetInfo;

import java.io.IOException;

/**
 * 处理网络状态信息类
 * Created by liu_zlu on 2017/4/6 19:35
 */
public class NetHandlerThread extends HandlerThread {

    Handler handler;

    private FileHelper fileHelper;
    public NetHandlerThread(String name,FileHelper fileHelper) {
        super(name);
        this.fileHelper = fileHelper;
    }

    @Override
    protected void onLooperPrepared() {
        super.onLooperPrepared();
        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what){
                    case 0://保存网络状态信息
                        fileHelper.dealNetInfo((NetInfo) msg.obj);
                        break;
                    case 1://上传网络状态信息
                        FileHelper.UploadCallBack uploadCallBack = (FileHelper.UploadCallBack) msg.obj;
                        try {
//                            boolean success = fileHelper.uploadInner();  // old
                            boolean success = fileHelper.uploadInner2();  // new
                            uploadCallBack.onCallback(success);
                        } catch (IOException e) {
                            e.printStackTrace();
                            uploadCallBack.onCallback(false);
                        }
                        break;
                }
            }
        };
    }

    public void sendMessage(Message message){
        if(handler != null){
            handler.sendMessage(message);
        }
    }
}
