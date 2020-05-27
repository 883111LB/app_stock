package com.cvicse.base.mvp;

import android.os.Bundle;
import android.os.Parcelable;

import com.cvicse.base.leak.Releaseable;

import java.io.Serializable;

/**
 * Created by liu_zlu on 2016/11/17 15:05
 */

public class MVPHelper {

    private static String mvpKey = "MVPHelper_Presenter";
    private static String DATATYPE = "presenter_data_type";

    private static int SERIALIZABLE_TYPE = 1;
    private static int PARCELABLE_TYPE = 2;
    private IPresenter iPresenter;

    private Releaseable PProxyReleaseable;
    private Releaseable VProxyReleaseable;

    private IPresenterListener iPresenterListener;
    public void onDestory(){
        if(iPresenter != null){
            iPresenter.onDestroy();
            iPresenter = null;
        }
        if(PProxyReleaseable != null){
            PProxyReleaseable.release();
        }
        if(VProxyReleaseable != null){
            VProxyReleaseable.release();
        }
    }
    public void onCreated(){
        if(iPresenter != null){
            iPresenter.onCreated();
        }
    }
    public void onResume(){
        if(iPresenter != null){
            iPresenter.onResume();
        }
    }

    public void onPause(){
        if(iPresenter != null){
            iPresenter.onPause();
        }
    }
    public void setPProxyReleaseable(Releaseable PProxyReleaseable) {
        this.PProxyReleaseable = PProxyReleaseable;
    }

    public void setVProxyReleaseable(Releaseable VProxyReleaseable) {
        this.VProxyReleaseable = VProxyReleaseable;
    }

    public void setPresenter(IPresenter iPresenter){
        if(iPresenter == null){
            return;
        }
        this.iPresenter = iPresenter;
        if(iPresenterListener != null){
            iPresenterListener.onSetted(iPresenter);
        }
    }
    public IPresenter getPresenter(){
        return iPresenter;
    }

    /**
     * 保存presenter数据
     * @param outState
     */
    public void onSaveInstanceState(Bundle outState){
        if(!MVPConfig.isRecovery || iPresenter == null){
            return;
        }
        if(iPresenter instanceof Parcelable){
            outState.putInt(DATATYPE,SERIALIZABLE_TYPE);
            outState.putParcelable(mvpKey, (Parcelable) iPresenter);
            return;
        }
        if(iPresenter instanceof Serializable){
            outState.putInt(DATATYPE,PARCELABLE_TYPE);
            outState.putSerializable(mvpKey, (Serializable) iPresenter);
            return;
        }
    }

    /**
     * 创建MVPHelper
     * @param savedInstanceState
     * @param object
     * @return
     */
    public static MVPHelper create(Bundle savedInstanceState,Object object,IPresenterListener ...iPresenterListeners) {
        if(!IView.class.isAssignableFrom(object.getClass())){
            return null;
        }

        MVPHelper mvpHelper = new MVPHelper();
        if(iPresenterListeners != null && iPresenterListeners.length != 0){
            mvpHelper.iPresenterListener = iPresenterListeners[0];
        }
        if(!MVPConfig.isRecovery ||savedInstanceState == null){
            return mvpHelper;
        }
        int dataType = savedInstanceState.getInt(DATATYPE);
        IPresenter iPresenter;
        if(dataType == SERIALIZABLE_TYPE){
            iPresenter = (IPresenter) savedInstanceState.getSerializable(mvpKey);
            mvpHelper.setPresenter(iPresenter);
            return mvpHelper;
        }
        if(dataType == PARCELABLE_TYPE){
            iPresenter = savedInstanceState.getParcelable(mvpKey);
            mvpHelper.setPresenter(iPresenter);
            return mvpHelper;
        }
        return mvpHelper;
    }
    public interface IPresenterListener{
        void onSetted(IPresenter ipresenter);
    }
}
