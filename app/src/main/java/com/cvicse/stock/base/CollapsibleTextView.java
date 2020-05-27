package com.cvicse.stock.base;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.cvicse.stock.R;

/**  可展开收起的TextView
 * Created by tang_xqing on 2018/4/23.
 */

public class CollapsibleTextView extends LinearLayout {
    // 默认行数
    private static final int DEFAULT_MAX_LINE_COUNT = 2;

    private static final int COLLAPSIBLE_STATE_NONE = 0;

    private static final int COLLAPSIBLE_STATE_SHRINKUP = 1;

    private static final int COLLAPSIBLE_STATE_SPREAD = 2;

    private String shrinkup ="收起";
    private String spread = "更多";
    private int mState;
    private boolean flag;
    private Context mContext;

    public CollapsibleTextView(Context context) {
        this(context,null);
    }

    public CollapsibleTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs);
    }

    private void init(Context context,AttributeSet attrs) {
        mContext = context;
        this.setOrientation(VERTICAL);
        LayoutInflater.from(context).inflate(R.layout.collapsible_textview,this);

    }
}
