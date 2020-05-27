package com.cvicse.stock.utils;

import android.content.Context;
import android.view.Gravity;
import android.widget.TextView;

import com.cvicse.base.utils.StringUtils;
import com.cvicse.stock.common.FillConfig;
import com.cvicse.stock.common.Setting.Setting;
import com.cvicse.stock.util.FormatUtils;

/**
 * Created by liu_zlu on 2017/2/9 19:20
 */
public class TextUtils {

    public static boolean setText(TextView textView,String text){
        return setText(textView,text, FillConfig.DEFALUT);
    }

    public static boolean setText(TextView textView,String text,String defalut){
        if(!StringUtils.isEmpty(text)){
            if( !FormatUtils.isStandard(text)){
                textView.setText(FillConfig.DEFALUT);
                return false;
            }else{
                textView.setText(text);
                return true;
            }
        }
        if(defalut == null){
            defalut = "";
        }
        textView.setText(defalut);
        return false;
    }

    public static boolean setTextEmpty(TextView textView,String text){
        if(!StringUtils.isEmpty(text)){
            textView.setText(text);
            return true;
        }
        textView.setText("");
        return false;
    }

    public static boolean setTextPercent(TextView textView,String text){
        if(!StringUtils.isEmpty(text)) {
            if( !FormatUtils.isStandard(text)){
                textView.setText(FillConfig.DEFALUT);
                return false;
            }else{
                textView.setText(text + "%");
                return true;
            }
        }else{
            textView.setText(FillConfig.DEFALUT);
            return false;
        }
    }

    public static TextView getNotingTextView(Context context,String text){
        TextView textView = new TextView(context);
        textView.setGravity(Gravity.CENTER);
        textView.setText(text);
        if(Setting.isNight()){
            textView.setTextColor(0xfff7fbff);
        } else {
            textView.setTextColor(0x949494);
        }

        return textView;
    }
}
