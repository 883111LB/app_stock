package com.cvicse.stock.portfolio;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

import com.cvicse.stock.R;

/**
 * Created by liu_zlu on 2017/1/13 13:57
 */
public class SortTextView extends TextView {
    private Drawable deafultImage,arrowDown,arrowUp;

    public final static int DEFAULT_TYPE = 0;
    public final static int UP_TYPE = 1;
    public final static int DOWN_TYPE = 2;
    private int type = DEFAULT_TYPE;
    public SortTextView(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {
        deafultImage = ContextCompat.getDrawable(context, R.drawable.ic_blue_triangle);
        deafultImage.setBounds(0,0, getDimension(context,8f),getDimension(context,15f));
        arrowDown = ContextCompat.getDrawable(context,R.drawable.ic_blue_down);
        arrowDown.setBounds(0,0, getDimension(context,8f),getDimension(context,15f));

        arrowUp = ContextCompat.getDrawable(context,R.drawable.ic_blue_up);
        arrowUp.setBounds(0,0, getDimension(context,8f),getDimension(context,15f));
        this.setCompoundDrawables(null,null,deafultImage,null);
        this.setCompoundDrawablePadding(getDimension(context,4f));
        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                toggle();
            }
        });
    }

    public SortTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public SortTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public void reset(){
        this.setCompoundDrawables(null,null,deafultImage,null);
    }

    public void toggle(){
        if(type == DEFAULT_TYPE || type == DOWN_TYPE){
            if(typeChangedListener != null){
                typeChangedListener.onChanged(UP_TYPE,type);
            }
            type = UP_TYPE;
            this.setCompoundDrawables(null,null,arrowUp,null);
        } else {
            if(typeChangedListener != null){
                typeChangedListener.onChanged(DOWN_TYPE,type);
            }
            type = DOWN_TYPE;
            this.setCompoundDrawables(null,null,arrowDown,null);
        }
    }

    private static int getDimension(Context context,float dimen){
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,dimen,context.getResources().getDisplayMetrics());
    }

    private TypeChangedListener typeChangedListener;

    public void setTypeChangedListener(TypeChangedListener typeChangedListener) {
        this.typeChangedListener = typeChangedListener;
    }

    public interface TypeChangedListener{
        void onChanged(int change,int pre);
    }
}
