package com.cvicse.base.skin.utils;

import android.content.Context;
import android.support.annotation.XmlRes;

/**
 * Created by liu_zlu on 2017/2/28 21:59
 */
public class StyleUtils {

    public void init(@XmlRes final int[] ids, final Context context){
        new Thread(new Runnable() {
            @Override
            public void run() {
                for(int id:ids){
                    context.getResources().getXml(id);
                }
            }
        }).start();
    }
}
