package com.cvicse.stock.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.cvicse.stock.R;

/**
 *
 * Created by liu_zlu on 2017/3/23 17:16
 */
public class ColorImageView extends ImageView {
    private int color;

    public ColorImageView(Context context) {
        this(context,null);
    }

    public ColorImageView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ColorImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray attribute = context.obtainStyledAttributes(attrs,
                R.styleable.ColorImageView);
        color = attribute.getColor(R.styleable.ColorImageView_filtercolor, Color.TRANSPARENT);
        attribute.recycle();
        if(color ==  Color.TRANSPARENT){
            return;
        }
        setColorFilter(new PorterDuffColorFilter(color, PorterDuff.Mode.SRC_IN));
    }

}
