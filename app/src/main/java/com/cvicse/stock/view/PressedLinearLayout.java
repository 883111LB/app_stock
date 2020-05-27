package com.cvicse.stock.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.LinearLayout;

/**
 * Created by ding_syi on 17-1-15.
 */
public class PressedLinearLayout extends LinearLayout {

    public PressedLinearLayout(Context context) {
        super(context);
    }

    public PressedLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PressedLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean shouldDelayChildPressedState() {
        return true;
    }

    @Override
    public void setPressed(boolean pressed) {
        if((pressed && isPressed()) || (!pressed && !isPressed())){
            return;
        }
        super.setPressed(pressed);
        ((ViewGroup)getParent().getParent()).setPressed(pressed);
    }
}
