package com.cvicse.base.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cvicse.base.R;

import java.util.ArrayList;

/**
 * Created by liu_zlu on 2016/12/23 15:09
 */
public class BottomTabbar extends LinearLayout implements View.OnClickListener{
    private Context mContext;

    private OnSelectChangerdListener onSelectedListener;

    private ArrayList<View> viewArrayList = new ArrayList<>();
    private ArrayList<TextView> textUpViews = new ArrayList<>();
    private ArrayList<TextView> textDownViews = new ArrayList<>();
    private int selectItem = 0;
    private Drawable itemDrawable;
    private ColorStateList up_textColor;
    private ColorStateList down_textColor;
    public BottomTabbar(Context context) {
        super(context);
        init(context,null);
    }

    public BottomTabbar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs);
    }

    public BottomTabbar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        mContext = context;
        TypedArray typedarray = context.obtainStyledAttributes(attrs, R.styleable.bottom_tabar);
        if(typedarray != null){
            itemDrawable = typedarray.getDrawable(R.styleable.bottom_tabar_item_background);
            up_textColor = typedarray.getColorStateList(R.styleable.bottom_tabar_up_textColor);
            down_textColor = typedarray.getColorStateList(R.styleable.bottom_tabar_down_textColor);
        }
        typedarray.recycle();
    }

    public void setTitles(ArrayList<String[]> titles){
        viewArrayList.clear();
        LayoutInflater inflater = LayoutInflater.from(mContext);
        int size = titles.size();
        this.removeAllViews();
        ViewGroup viewGroup;
        for(int i = 0;i < size;i++){
            viewGroup = (ViewGroup) inflater.inflate(R.layout.bottom_navigation,null);
            TextView tv_chn = (TextView) viewGroup.findViewById(R.id.tv_chn);
            tv_chn.setText(titles.get(i)[0]);
            if(up_textColor != null){
                tv_chn.setTextColor(up_textColor);
            }
            textUpViews.add(tv_chn);
            TextView tv_eng = (TextView) viewGroup.findViewById(R.id.tv_eng);
            textDownViews.add(tv_eng);
            if(down_textColor != null){
                tv_eng.setTextColor(down_textColor);
            }
            tv_eng.setText(titles.get(i)[1]);
            LinearLayout.LayoutParams layoutParam = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT);
            layoutParam.weight = 1;
            viewGroup.setLayoutParams(layoutParam);
            viewGroup.setOnClickListener(this);
            viewGroup.setTag(i);
            this.addView(viewGroup);
            viewArrayList.add(viewGroup);
            if(itemDrawable != null){
                if (Build.VERSION.SDK_INT >= 16)
                    viewGroup.setBackground(itemDrawable);
                else
                    viewGroup.setBackgroundDrawable(itemDrawable);
            }
        }
        viewArrayList.get(0).setSelected(true);
    }
    public void setOnSelectedListener(OnSelectChangerdListener onSelectChangerdListener){
        this.onSelectedListener = onSelectChangerdListener;
    }

    @Override
    public void onClick(View v) {
        int currentItem = (int) v.getTag();
        if(currentItem == selectItem){
            return;
        }
        int oldItem = selectItem;
        viewArrayList.get(selectItem).setSelected(false);
        v.setSelected(true);
        selectItem = currentItem;
        if(onSelectedListener != null){
            onSelectedListener.onSelectChanged(v,selectItem,oldItem);
        }
    }

    public void setUpColor(ColorStateList color){
        for(TextView textView:textUpViews){
            textView.setTextColor(color);
        }
    }

    public void setDownColor(ColorStateList color){
        for(TextView textView:textDownViews){
            textView.setTextColor(color);
        }
    }
    public interface OnSelectChangerdListener{
        void onSelectChanged(View view, int position,int oldPosition);
    }
}
