package com.cvicse.base.widget.verticalbanner;

import android.view.View;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Description:
 * <p/>
 * Created by rowandjj(chuyi)<br/>
 * Date: 16/1/6<br/>
 * Time: 下午4:59<br/>
 */
@SuppressWarnings("unused")
public abstract class BaseBannerAdapter<T> {
    private List<T> mDatas;
    private OnDataChangedListener mOnDataChangedListener;

    public BaseBannerAdapter(List<T> datas) {
        mDatas = datas;
    }

    public BaseBannerAdapter(T[] datas) {
        mDatas = new ArrayList<>(Arrays.asList(datas));
    }

    /**
     * 设置banner填充的数据
     */
    public void setData(List<T> datas) {
        this.mDatas = datas;
        notifyDataChanged();
    }

    public List<T> getDatas(){
        return mDatas;
    }
    void setOnDataChangedListener(OnDataChangedListener listener) {
        mOnDataChangedListener = listener;
    }

    public int getCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    public void notifyDataChanged() {
        mOnDataChangedListener.onChanged(true);
    }
    public void notifyDataChangedItem() {
        mOnDataChangedListener.onChanged(false);
    }
    public void addItem(T t){
        if(mDatas == null){
            mDatas = new ArrayList<>();
        }
        for(int i = 0;i < mDatas.size();i++){
            if(isSameObject(t,mDatas.get(i))){
                mDatas.set(i,t);
                notifyDataChangedItem();
                return;
            }
        }
        mDatas.add(t);
        notifyDataChanged();
    }

    protected abstract boolean isSameObject(T object,T comp);
    public T getItem(int position) {
        return mDatas.get(position);
    }

    /**
     * 设置banner的样式
     */
    public abstract View getView(VerticalBannerView parent);

    /**
     * 设置banner的数据
     */
    public abstract void setItem(View view, T data,int position);


    interface OnDataChangedListener {
        void onChanged(boolean all);
    }
}
