package com.cvicse.base.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.ArrayList;

/**
 * Created by liu_zlu on 2017/3/21 09:42
 */
public class ExtendedRadioGroup extends LinearLayout implements View.OnClickListener{

    private ArrayList<View> views = new ArrayList<>();
    private OnSelectedChangeListener onSelectedChangeListener;
    private int selectPosition = 0;
    public ExtendedRadioGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public ExtendedRadioGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ExtendedRadioGroup(Context context) {
        super(context);
    }

  /*  @Override
    public void addView(View child, int index, ViewGroup.LayoutParams params) {
        views.add(child);
        child.setOnClickListener(this);
        super.addView(child, index, params);
    }*/

    /**
     * Called when a view has been clicked.
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        selectView(v);
    }

    private void selectView(View view){
        for(View temp:views){
            if(temp != view){
                temp.setSelected(false);
            }
        }
        selectPosition = views.indexOf(view);
        if(onSelectedChangeListener != null){
            onSelectedChangeListener.onCheckedChanged(this,view,view.getId());
        }
        view.setSelected(true);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        searchView(this);
    }

    private void searchView(View view){
        if(!(view instanceof ViewGroup)) return;
        ViewGroup viewGroup = (ViewGroup) view;
        int length = viewGroup.getChildCount();
        for(int i = 0;i < length;i++){
            view = viewGroup.getChildAt(i);
            if(view.getTag() == null){
                searchView(view);
                continue;
            }
            if(view.getTag().equals("1_2_1")){
                view.setOnClickListener(this);
                views.add(view);
                continue;
            } else {
                searchView(view);
            }
        }
    }

    public void setSelectPosition(int position){
        if(position<0 || position >= views.size()) return;
        selectView(views.get(position));
    }

    public int getSelectPosition(){
        return selectPosition;
    }
    public void setOnSelectedChangeListener(OnSelectedChangeListener onSelectedChangeListener) {
        this.onSelectedChangeListener = onSelectedChangeListener;
    }

    public interface OnSelectedChangeListener {

        public void onCheckedChanged(ExtendedRadioGroup group, View view, int checkedId);
    }
}