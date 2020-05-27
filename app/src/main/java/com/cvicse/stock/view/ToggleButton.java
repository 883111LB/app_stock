package com.cvicse.stock.view;

/**
 * Created by liu_zlu on 2017/3/20 20:13
 */

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;


public class ToggleButton extends FrameLayout implements View.OnClickListener{
    private OnToggleChanged listener;
    private boolean toggleOn = true;

    private View openView;
    private View closeView;
    public ToggleButton(Context context) {
        this(context, null, 0);
    }

    public ToggleButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ToggleButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.setOnClickListener(this);
    }

    public boolean isToggleOn() {
        return toggleOn;
    }
    public void setToggleOn(boolean toggleOn) {
        setToggleOnInner(toggleOn);
        if (listener != null)
            listener.onToggle(toggleOn);
    }
    private void setToggleOnInner(boolean toggleOn) {
        if(toggleOn){
            closeView.setVisibility(INVISIBLE);
            openView.setVisibility(VISIBLE);
        } else {
            closeView.setVisibility(VISIBLE);
            openView.setVisibility(INVISIBLE);
        }
    }
    @Override
    public void addView(View child, int index, ViewGroup.LayoutParams params) {
        super.addView(child, index, params);
        if(closeView == null){
            closeView = child;
            return;
        }
        openView = child;
    }
    @Override
    public void onClick(View v) {
            if (toggleOn) {
                toggleOn = false;
            }else {
                toggleOn = true;
            }
        setToggleOnInner(toggleOn);
        if (listener != null)
            listener.onToggle(toggleOn);
    }

    public interface OnToggleChanged {
        public void onToggle(boolean on);
    }

    public void setOnToggleChanged(OnToggleChanged onToggleChanged) {
        listener = onToggleChanged;
    }
}