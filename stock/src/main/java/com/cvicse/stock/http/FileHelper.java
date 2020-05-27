package com.cvicse.stock.http;

import android.os.Environment;
import android.os.Message;
import android.util.Log;

import com.cvicse.base.utils.DeviceUtils;
import com.cvicse.base.utils.FileUtils;
import com.cvicse.base.utils.PhoneUtils;
import com.cvicse.stock.http.netinfo.Location;
import com.cvicse.stock.http.netinfo.NetInfo;
import com.cvicse.stock.http.netinfo.Phone;
import com.cvicse.stock.http.netinfo.SendBo;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.RandomAccessFile;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * 网络监控数据提交工具
 * Created by liu_zlu on 2017/4/6 19:32
 */
public class FileHelper {

    private long lastTime = 0;
    private Gson gson = new Gson();
    private ArrayList<NetInfo> netInfos = new ArrayList<>();

    private NetHandlerThread handlerThread = new NetHandlerThread("upload_Net",this);

    private static FileHelper fileHelper;

    static {
        /**
         * 初始化
         */
        fileHelper = new FileHelper();
        //启动处理类
        fileHelper.handlerThread.start();
    }

    /**
     * 真实保存网络状态方法
     * @param netInfo
     */
    protected  void dealNetInfo(NetInfo netInfo){
        netInfos.add(netInfo);
        //每30秒处理一次数据
        long currentTime = System.currentTimeMillis();
        if(currentTime - lastTime > 4000){
            try {
                saveFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 保存网络状态信息
     * @param netInfo
     */
    public static void saveNetInfo(NetInfo netInfo){
        fileHelper.saveInner(netInfo);
    }

    /**
     * 内部保存网络状态
     * @param netInfo
     */
    private void saveInner(NetInfo netInfo){
        Message message = Message.obtain();
        message.what = 0;
        message.obj = netInfo;
        if(fileHelper != null ){
            //发送消息
            fileHelper.handlerThread.sendMessage(message);
        }
    }

    private void saveFile() throws IOException {
        File file = Environment.getExternalStorageDirectory();
        file = new File(file,"netinfo");
        if(!file.exists()){
            file.createNewFile();
        }
        RandomAccessFile writer = new RandomAccessFile(file,"rw");
        writer.seek(writer.length());
        for(NetInfo netInfo:netInfos){
            writer.write((gson.toJson(netInfo)+"\n").getBytes());
        }
        writer.close();
        netInfos.clear();
    }

    /**
     * 上传网络请求状态文件
     * @param uploadCallBack
     */
    public static void upload(UploadCallBack uploadCallBack)  {
        Message message = Message.obtain();
        message.what = 1;
        message.obj = uploadCallBack;
        fileHelper.handlerThread.handler.sendMessage(message);
    }

    protected  boolean uploadInner2() throws IOException{
        int allNum = 0;
        File file = Environment.getExternalStorageDirectory();
        file = new File(file,"netinfo");
        BufferedReader reader =  new BufferedReader(new FileReader(file));
        String str = null;
        int num = 0;
        boolean isStart = true;
        boolean isSuccess = false;
        StringBuffer stringBuffer = new StringBuffer();
        while( (str = reader.readLine()) != null ){
            num++;
            allNum++;
            Log.d("allnum = ",allNum+"");
            if( isStart ){
                isStart = false;
                stringBuffer = new StringBuffer("[");
                stringBuffer.append(str);
                continue;
            }
            stringBuffer.append(",");
            stringBuffer.append(str);
            if( num > 5 ){
                num = 0;
                isStart = true;
                stringBuffer.append("]");
                // 上传数据
                isSuccess = upload2(stringBuffer);
                if( !isSuccess ){
                    break;
                }
            }
        }
        if( !isSuccess ){
            return false;
        }
        stringBuffer.append("]");
        isSuccess = upload2(stringBuffer);
//        return false;
        if( isSuccess){
            netInfos.clear();
            file.delete();
        }
        return isSuccess;
    }

    private boolean upload2(StringBuffer stringBuffer) {
        SendBo sendBo = new SendBo();
        ArrayList<NetInfo> temps = gson.fromJson(stringBuffer.toString(),new TypeToken<ArrayList<NetInfo>>(){}.getType());
        Location location =  new Location();
        for( int i =0; i< temps.size();i++ ){
            NetInfo netInfo = temps.get(i);

            if( null == netInfo.getLocation() ){
                netInfo.setLocation(location);
                temps.set(i, netInfo);
            }
        }

        sendBo.setNetInfos(temps);
        Phone phone = new Phone();
        phone.setMac(DeviceUtils.getMacAddress());
        phone.setName(DeviceUtils.getManufacturer() + " "+DeviceUtils.getModel());
        phone.setVersion(DeviceUtils.getSDKVersion()+"");
        phone.setCarrier(PhoneUtils.getSimOperatorByMnc());
        sendBo.setPhone(phone);

        if(uploadConnection(gson.toJson(sendBo))){
//            netInfos.clear();
//            file.delete();
            return true;
        }
        return false;
    }

    protected boolean uploadInner() throws IOException {
        File file = Environment.getExternalStorageDirectory();
        file = new File(file,"netinfo");
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String string = null;
        ArrayList<NetInfo> temps = new ArrayList<>();
        temps.addAll(netInfos);

        StringBuffer stringBuffer = new StringBuffer("[");
        int count = 0;
        if(netInfos != null && netInfos.size() > 0){
            String temp = gson.toJson(netInfos);
            stringBuffer = new StringBuffer(temp.substring(0,temp.length()-1));
            count = 1;
        }
        // 读取文件信息
        while ((string = reader.readLine()) != null){
            if(count != 0){
                stringBuffer.append(",");
            }
            count = 1;
            stringBuffer.append(string);
            //temps.add(gson.fromJson(string,NetInfo.class));
        }
        stringBuffer.append("]");

        SendBo sendBo = new SendBo();
        temps = gson.fromJson(stringBuffer.toString(),new TypeToken<ArrayList<NetInfo>>(){}.getType());
        sendBo.setNetInfos(temps);
        Phone phone = new Phone();
        phone.setMac(DeviceUtils.getMacAddress());
        //phone.setName(DeviceUtils.getManufacturer());
        phone.setName(DeviceUtils.getManufacturer() + " "+DeviceUtils.getModel());
        phone.setVersion(DeviceUtils.getSDKVersion()+"");
        phone.setCarrier(PhoneUtils.getSimOperatorByMnc());
        sendBo.setPhone(phone);

        if(uploadConnection(gson.toJson(sendBo))){
            netInfos.clear();
            file.delete();
            return true;
        }
        return false;
    }

    private boolean uploadConnection(String data) {
        HttpURLConnection httpURLConnection = null;
        Writer writer = null;
        try {
            // 本地测试
//            URL url = new URL ("http://192.168.135.22:8080/ceshi/hello");

            //URL url = new URL("http://192.168.132.38:8888/ceshi/hello");
            // 内网
            URL url = new URL("http://192.168.128.80:8081/ceshi/hello");
            // 外网
//            URL url = new URL("http://221.6.88.227:8081/ceshi/hello");

            /**
             * 外网映射地址
             */
//            URL url = new URL("http://153.37.190.164:8081/ceshi/hello");
            httpURLConnection = (HttpURLConnection ) url.openConnection();
            httpURLConnection.setConnectTimeout(10000);
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);

            // 此处getOutputStream会隐含的进行connect
            writer = new OutputStreamWriter(httpURLConnection.getOutputStream());
            // 写入的数据存储在内存缓冲区中
            writer.write(/*"send="+*/data);
            writer.flush();

            if(httpURLConnection.getResponseCode() == 200){
                // 在调用getInputStream()函数时才把准备好的http请求正式发送到服务器
                InputStreamReader in = new InputStreamReader(httpURLConnection.getInputStream());
                BufferedReader reader = new BufferedReader(in);
                String code = reader.readLine();
                if(code.contains("1")){
                    return true;
                }
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if(writer != null){
                    writer.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            if(httpURLConnection != null){
                httpURLConnection.disconnect();
            }
        }
        return false;
    }

    public static String getSize(){
        File file = Environment.getExternalStorageDirectory();
        file = new File(file,"netinfo");
        if(!file.exists()){
            return "0";
        }
        return FileUtils.getFileSize(file);
    }

    public interface UploadCallBack {
        void onCallback(boolean success);
    }
}
