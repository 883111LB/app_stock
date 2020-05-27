package com.cvicse.stock.base;

import android.app.Activity;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cvicse.base.ui.BaseContextWrapper;
import com.cvicse.stock.R;

public class ToolBar extends RelativeLayout implements View.OnClickListener{
    private  int textColor_left;
    private ImageView lImageView;
    private ImageView rImageView;
    private ImageView rImageView_1;
    private TextView titleTextView;

    private TextView tev_toolbar_left;

    private View centerView;

    public ToolBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.layout_topbar, this);
        lImageView = (ImageView) findViewById(R.id.img_toolbar_left);
        rImageView = (ImageView) findViewById(R.id.img_toolbar_right_1);
        rImageView_1 = (ImageView) findViewById(R.id.img_toolbar_right_2);
        titleTextView = (TextView) findViewById(R.id.tev_toolbar_title);
        centerView = titleTextView;
        tev_toolbar_left = (TextView) findViewById(R.id.tev_toolbar_left);
        lImageView.setOnClickListener(this);
        rImageView.setOnClickListener(this);
        rImageView_1.setOnClickListener(this);
        tev_toolbar_left.setOnClickListener(this);
        //获得自定义属性并赋值
        TypedArray typedArray = context.obtainStyledAttributes(attrs,R.styleable.ToolBar);
        int src_left = typedArray.getResourceId(R.styleable.ToolBar_src_left, 0);
        isBack = typedArray.getBoolean(R.styleable.ToolBar_is_back, true);
        int src_right_1 = typedArray.getResourceId(R.styleable.ToolBar_src_right_1, 0);
        int src_right_2 = typedArray.getResourceId(R.styleable.ToolBar_src_right_2, 0);
        String tev_title = typedArray.getString(R.styleable.ToolBar_text_title);
         int textColor_title = typedArray.getColor(R.styleable.ToolBar_textColor_title, ContextCompat.getColor(context,R.color.withe));
        float textSize_title = (int) typedArray.getDimensionPixelSize(R.styleable.ToolBar_textSize_title,dip2px(context,19));

        String tev_left = typedArray.getString(R.styleable.ToolBar_text_left);
        textColor_left = typedArray.getColor(R.styleable.ToolBar_textColor_left,ContextCompat.getColor(context,R.color.text_blue));
        float textSize_left = (int) typedArray.getDimension(R.styleable.ToolBar_textSize_left,dip2px(context,16));

        int filterColor = typedArray.getColor(R.styleable.ToolBar_filtercolor, Color.TRANSPARENT);
        typedArray.recycle();//释放资源
        if(src_left > 0){
            lImageView.setVisibility(VISIBLE);
            lImageView.setImageResource(src_left);
        }
        if(src_right_1 > 0){
            rImageView.setVisibility(VISIBLE);
            rImageView.setImageResource(src_right_1);
        }

        if(src_right_2 > 0){
            rImageView_1.setVisibility(VISIBLE);
            rImageView_1.setImageResource(src_right_2);
        }

        if(tev_left != null && !tev_left.equals("")){
            tev_toolbar_left.setVisibility(VISIBLE);
            tev_toolbar_left.setText(tev_left);
            tev_toolbar_left.setTextSize(TypedValue.COMPLEX_UNIT_PX,textSize_left);
            tev_toolbar_left.setTextColor(textColor_left);
        }
        titleTextView.setText(tev_title);
        titleTextView.setTextColor(textColor_title);
        titleTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX,textSize_title);
        setColorFilter(filterColor);
    }
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public void setTitleTextColor(int id){
        titleTextView.setTextColor(id);
    }
    public void setTitleTextColor(ColorStateList colors) {
        titleTextView.setTextColor(colors);
    }

    public void setColorFilter(int color){
        if(color == Color.TRANSPARENT){
            lImageView.setColorFilter(null);
            rImageView.setColorFilter(null);
            rImageView_1.setColorFilter(null);
            tev_toolbar_left.setTextColor(textColor_left);
            return;
        }
        lImageView.setColorFilter(color, PorterDuff.Mode.SRC_IN);
        rImageView.setColorFilter(color, PorterDuff.Mode.SRC_IN);
        rImageView_1.setColorFilter(color, PorterDuff.Mode.SRC_IN);
        tev_toolbar_left.setTextColor(color);
    }
    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        Type type = null;
        switch (v.getId()){
            case R.id.img_toolbar_left:
            case R.id.tev_toolbar_left:
                if(isBack){
                    Context context = getContext();
                    if(context instanceof Activity){
                        ((Activity)context).finish();
                    } else {
                        ((BaseContextWrapper)context).getActivityContext().finish();
                    }
                }
                type = Type.LEFT_TYPE;
                break;
            case R.id.img_toolbar_right_1:
                type = Type.RIGHT_TYPE_1;
                break;
            case R.id.img_toolbar_right_2:
                type = Type.RIGHT_TYPE_2;
                break;
        }
        if(toolBarClickListener != null && type != null){
            toolBarClickListener.onClick(v,type);
        }
    }

    public void setToolBarClickListener(ToolBarClickListener toolBarClickListener) {
        this.toolBarClickListener = toolBarClickListener;
    }

    ToolBarClickListener toolBarClickListener;
    public interface ToolBarClickListener{
        void onClick(View view, Type type);
    }

    public void setLeftText(CharSequence text){
        tev_toolbar_left.setText(text);
    }

    public void setTitle(CharSequence text){
        titleTextView.setText(text);
    }
    public enum Type {
        LEFT_TYPE,
        RIGHT_TYPE_1,
        RIGHT_TYPE_2;
    }
    boolean isBack = true;
    public void setBack(boolean isBack){
        this.isBack = isBack;
    }

    public void setCenterView(View centerView){
        LayoutParams layoutParams = (LayoutParams) this.centerView.getLayoutParams();
        this.removeView(this.centerView);
        this.centerView = centerView;
        this.addView(centerView,layoutParams);
    }

    public View setCenterId(int id){
        /*LayoutParams layoutParams = (LayoutParams) this.centerView.getLayoutParams();
        layoutParams.getRules();*/
        this.removeView(this.centerView);
        this.centerView = LayoutInflater.from(getContext()).inflate(id,this,false);
        LayoutParams layoutParams = (LayoutParams) centerView.getLayoutParams();
        layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
        this.addView(centerView,layoutParams);
        return centerView;
    }

    public View getRight1(){
        return rImageView;
    }
}
