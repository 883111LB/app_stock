package com.cvicse.stock.pulltorefresh;

import android.content.Context;
import android.util.AttributeSet;

import com.cvicse.stock.view.HVListView;

/**
 * Created by liu_zlu on 2017/5/7 22:26
 */
public class PullToRefreshHSListView extends PullToRefreshListView {
    public PullToRefreshHSListView(Context context) {
        super(context);
    }

    public PullToRefreshHSListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PullToRefreshHSListView(Context context, Mode mode) {
        super(context, mode);
    }

    public PullToRefreshHSListView(Context context, Mode mode, AnimationStyle style) {
        super(context, mode, style);
    }

    @Override
    protected HVListView createListView(Context context, AttributeSet attrs) {
        final HVListView lv = new HVListView(context, attrs);
        return lv;
    }
}
