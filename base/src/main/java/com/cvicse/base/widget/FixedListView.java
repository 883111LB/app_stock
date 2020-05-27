package com.cvicse.base.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * Created by liu_zlu on 2017/2/7 19:29
 */
public class FixedListView extends ListView {
    public FixedListView(Context context) {
        this(context,null);
    }

    public FixedListView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public FixedListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE>>2,MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
