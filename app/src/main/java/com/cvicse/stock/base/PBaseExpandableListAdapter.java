package com.cvicse.stock.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.BaseExpandableListAdapter;

/**
 * Created by liu_zlu on 2017/3/10 15:55
 */
public abstract class PBaseExpandableListAdapter extends BaseExpandableListAdapter {

    protected Context mContext;
    protected LayoutInflater mLayoutInflater;
    public PBaseExpandableListAdapter(Context context){
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
    }
}
