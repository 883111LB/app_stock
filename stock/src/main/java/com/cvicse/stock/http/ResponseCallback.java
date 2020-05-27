package com.cvicse.stock.http;

import android.os.Handler;
import android.os.Looper;

import com.cvicse.base.utils.PhoneUtils;
import com.cvicse.base.utils.ToastUtils;
import com.cvicse.stock.http.netinfo.NetInfo;
import com.mitake.core.bean.log.ErrorInfo;
import com.mitake.core.request.Request;
import com.mitake.core.response.IResponseInfoCallback;
import com.mitake.core.response.Response;

import static com.baidu.location.d.a.i;

/**
 * 封装网络请求回调
 * Created by liu_zlu on 2016/12/28 20:12
 */
//public abstract class ResponseCallback extends IResponseCallback {
public abstract class ResponseCallback extends IResponseInfoCallback {

    private static Handler handler = new Handler(Looper.getMainLooper());

    // 请求接口名
    private String requestName ;

    public ResponseCallback(Request requestName){
        this.requestName = requestName.getClass().getSimpleName();
    }

    public ResponseCallback(){
    }

    public void setRequest(Request requestName){
        this.requestName = requestName.getClass().getSimpleName();
    }

    @Override
    public void callback(final Response response) {

        //保存请求信息
        saveResult(response,200);
       /* handler.post(new Runnable() {
            @Override
            public void run() {
                onBack(response);
            }
        });*/
        handler.post(new Runnable() {
            @Override
            public void run() {
                onBack(response);
                onComplete();
            }
        });


    }

    @Override
    public void exception(final ErrorInfo errorInfo) {
        ToastUtils.showShortToastSafe(requestName+" : "+errorInfo.getMessage());

        Response response = new Response();
        response.status = errorInfo.getErr_code();
        response.message = errorInfo.getMessage();
        saveResult(response,i);
        handler.post(new Runnable() {
            @Override
            public void run() {
                onError(errorInfo.getErr_code(),errorInfo.getMessage());
                onComplete();
            }
        });
    }

    /**
     * 执行完毕回调
     */
    public void onComplete(){

    }
    public abstract void onBack(Response response);

    public abstract void onError(int i, String s);

    /**
     * 保存网络请求状态信息
     * @param response
     * @param code
     */
    private void saveResult(Response response,int code){
        finished = true;
        NetInfo netInfo = MockRunnable.threadLocal.get();
        if(netInfo == null){
            return;
        }
        netInfo.setName(requestName);
        netInfo.setState(code+"");
        //释放网络状态数据
        MockRunnable.threadLocal.remove();
        netInfo.setEndStatus(RequestStatusUtils.getCurrent());

        //netInfo.setLocation(LocationUtils.location);
        if (LocationUtils.location != null){
            netInfo.setLocation(LocationUtils.location);
        }

        if(GLocationUtils.location != null){
            netInfo.setLocation(GLocationUtils.location);
        }

        netInfo.setCaName(PhoneUtils.getSimOperatorByMnc());
        netInfo.setStartend((int) (netInfo.getEndStatus().getTime()-netInfo.getStartStatus().getTime() ));
        netInfo.setRunend((int) (netInfo.getEndStatus().getTime() -netInfo.getRunStatus().getTime()));
        FileHelper.saveNetInfo(netInfo);
    }

    private boolean finished = false;

    public boolean isFinished(){
        return finished;
    }
}
