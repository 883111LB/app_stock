package com.cvicse.stock.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**
 * Created by liuzilu on 2017/2/4.
 */

public abstract class PBaseAdapter extends BaseAdapter {

    protected Context mContext;
    protected LayoutInflater mLayoutInflater;
    public PBaseAdapter(Context context){
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
    }

    public abstract View getView(int position, View convertView, ViewGroup parent);
}
