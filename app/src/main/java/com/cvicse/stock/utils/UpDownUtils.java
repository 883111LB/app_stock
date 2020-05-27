package com.cvicse.stock.utils;

import android.widget.TextView;

import com.cvicse.base.utils.StringUtils;
import com.cvicse.stock.common.ColorUtils;

/**
 * Created by liu_zlu on 2017/4/5 17:39
 */
public class UpDownUtils {
    /**
     *  按照数字进行比较，如果src打印com展示绿色，小于展示红色
     * @param src 对照数据
     * @param com 需要比较数据
     * @param textViews 控件
     */
    public static void compare(String src, String com, TextView ...textViews){
        double comInt;
        try{
            if(StringUtils.isEmpty(com) || "null".equals(com)){
                return;
            }
            comInt =  Double.parseDouble(com);
        } catch (NumberFormatException e){
            return ;
        }
        try{
            double srcInt =  0;
            if(src != null){
                srcInt = Double.parseDouble(src);
            }
            compare(srcInt,comInt,textViews);
        } catch (NumberFormatException e){
            for(TextView textView:textViews){
                textView.setTextColor(ColorUtils.UP);
            }
        }
    }

    public static void compare(double src, double com, TextView ...textViews){
        try{
            if(com > src){
                for(TextView textView:textViews){
                    textView.setTextColor(ColorUtils.UP);
                }
            } else if(com < src){
                for(TextView textView:textViews){
                    textView.setTextColor(ColorUtils.DOWN);
                }
            } else {
                for(TextView textView:textViews){
                    textView.setTextColor(ColorUtils.DEFALUT());
                }
            }
        } catch (NumberFormatException e){
            for(TextView textView:textViews){
                textView.setTextColor(ColorUtils.UP);
            }
        }
    }

    public static void setUp(TextView ...textViews){
        for(TextView textView:textViews){
            textView.setTextColor(ColorUtils.UP);
        }
    }

    public static void setDown(TextView ...textViews){
        for(TextView textView:textViews){
            textView.setTextColor(ColorUtils.DOWN);
        }
    }
}
