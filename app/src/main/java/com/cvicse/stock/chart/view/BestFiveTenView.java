package com.cvicse.stock.chart.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import com.cvicse.base.utils.SizeUtils;
import com.cvicse.base.utils.StringUtils;
import com.cvicse.stock.chart.theme.ThemeColor;
import com.cvicse.stock.common.ColorUtils;
import com.cvicse.stock.util.FormatUtils;
import com.mitake.core.QuoteItem;
import com.mitake.core.util.FormatUtility;

import java.util.ArrayList;

/**
 * 五档、十档控件
 * Created by liu_zlu on 2017/2/22 16:35
 */
public class BestFiveTenView extends View {

    private Paint textPaint;
    private Paint linePaint;

    private int textSize = 12;
    private QuoteItem quoteItem;
    private int width = 0;
    private int hight = 0;

    private float lineHigth = 3;

    private float divhight;

    private float textHight = 0;
    private String buyPrices[] = {
            "一", "一", "一", "一", "一",
            "一", "一", "一", "一", "一"
    };
    private String sellPrices[] = {
            "一", "一", "一", "一", "一",
            "一", "一", "一", "一", "一"
    };
    private String buyVolumes[] = {
            "一", "一", "一", "一", "一",
            "一", "一", "一", "一", "一"
    };
    private String sellVolumes[] = {
            "一", "一", "一", "一", "一",
            "一", "一", "一", "一", "一"
    };

    private int[] buyColors = {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1};
    private int[] sellColors = {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1};
    private int defalutColor = ThemeColor.blackWhite();

    //默认为五档，五条数据
    private int count = 5;

    float helfwidth = 0;
    private int strLength = 0;

    public BestFiveTenView(Context context) {
        super(context);
        init();
    }

    public BestFiveTenView(Context context, AttributeSet attributeset) {
        super(context, attributeset);
        init();
    }

    public BestFiveTenView(Context context, AttributeSet attributeset, int i1) {
        super(context, attributeset, i1);
        init();
    }
    /**
     * 设置为5档
     */
    public void setFive(){
        count = 5;
        invalidate();
    }

    /**
     * 设置为10档
     */
    public void setTen(){
        count = 10;
        invalidate();
    }
    private boolean isLand;

    /**
     * 只对十档时有效
     */
    public void setLand(boolean isLand){
        this.isLand = isLand;
        textPaint.setTextSize(SizeUtils.dp2px(getContext(),10));
    }

    private void init() {
        textPaint = new Paint();
        textPaint.setAntiAlias(true);
        textPaint.setTextSize(SizeUtils.dp2px(getContext(),12));
        textPaint.setColor(ThemeColor.blackWhite());
        linePaint = new Paint();
        linePaint.setColor(ThemeColor.blackWhite());
        Rect rect = new Rect();
        textPaint.getTextBounds("erd3", 0, 4, rect);
        textHight = rect.height();
    }

    public void setData(QuoteItem quoteitem) {
        if(quoteitem == null){
            return;
        }
        for(int i = 0;i < count;i++){
            buyPrices[i] = "一";
            sellPrices[i] = "一";
            buyVolumes[i] = "一";
            sellVolumes[i] = "一";
        }
        double closePrice = 0;
        //获取昨收价
//        if(!StringUtils.isEmpty(quoteitem.preClosePrice) && !quoteitem.preClosePrice.equals("--") && !quoteitem.preClosePrice.equals("-")&& !quoteitem.preClosePrice.equals("一")){
        if(FormatUtils.isStandard(quoteitem.preClosePrice)){  // new
            closePrice = Double.parseDouble(quoteitem.preClosePrice);
        }

        strLength = 0;
        //买价数组
        ArrayList<String> buyPriceList =  quoteitem.buyPrices;
        if(buyPriceList != null && buyPriceList.size()>0){
            int minLength = count > buyPriceList.size() ? buyPriceList.size() : count;
            int position = 0;
            for(int i = 0;i < minLength;i++){
                position = -i-1+minLength;
                buyPrices[position] = buyPriceList.get(i);
                strLength = Math.max(strLength,StringUtils.length(buyPrices[position]));
//                if(StringUtils.isEmpty(buyPrices[position]) || buyPrices[position].equals("-")||buyPrices[position].equals("--")||"一".equals(sellPrices[position])){
                if(!FormatUtils.isStandard(buyPrices[position])){  // new
                    buyColors[i] = -1;
                } else {
                    double temp = Double.parseDouble(buyPrices[position]);
                    if(temp > closePrice){
                        buyColors[position] = ColorUtils.UP;
                    } else if(temp < closePrice){
                        buyColors[position] = ColorUtils.DOWN;
                    } else {
                        buyColors[position] = -1;
                    }
                }
            }
        }

        //卖价数组
        ArrayList<String> sellPriceeList =  quoteitem.sellPrices;
        if(sellPriceeList != null && sellPriceeList.size()>0){
            int minLength = count > sellPriceeList.size() ? sellPriceeList.size() : count;
            int position = 0;
            for(int i = 0;i < minLength;i++){
                position = count-i-1;
                sellPrices[position] = sellPriceeList.get(i);
                strLength = Math.max(strLength,StringUtils.length(sellPrices[position]));
//                if(StringUtils.isEmpty(sellPrices[position]) || sellPrices[position].equals("-")||sellPrices[position].equals("--")||"一".equals(sellPrices[position])){  // old
                if(!FormatUtils.isStandard(sellPrices[position])){  // new
                    sellColors[position] = -1;
                } else {
                    if(!"一".equals(sellPrices[position] )){
                        double temp = Double.parseDouble(sellPrices[position]);
                        if(temp > closePrice){
                            sellColors[position] = ColorUtils.UP;
                        } else if(temp < closePrice){
                            sellColors[position] = ColorUtils.DOWN;
                        } else {
                            sellColors[position] = -1;
                        }
                    }

                }
            }
        }

        //买量数组
        ArrayList<String> buyVolumeList = null;
       /* if(count == 10){*/
            //买量
            buyVolumeList =  quoteitem.buyVolumes;
        /*}else {
            buyVolumeList =  quoteitem.buySingleVolumes;
        }*/

        if(buyVolumeList != null && buyVolumeList.size()>0){
            int minLength = count > buyVolumeList.size() ? buyVolumeList.size() : count;
            String temp = "";
            int position = 0;
            for(int i = 0;i < minLength;i++){
                position = -i-1+minLength;
                temp = buyVolumeList.get(i);
                //不为空&&部位-或者--
//                if(!StringUtils.isEmpty(temp) && !(temp.equals("-") || temp.equals("--")||temp.equals("一"))){  // old
                if(FormatUtils.isStandard(temp)){  // new
                    buyVolumes[position] = FormatUtility.formatVolume(temp,quoteitem.market,quoteitem.subtype);
                }
            }
        }

        //卖量数组
        ArrayList<String> sellVolumeList = null;
       /* if(count == 10){*/
            //卖量
            sellVolumeList =  quoteitem.sellVolumes;
        /*}else {
            sellVolumeList =  quoteitem.sellSingleVolumes;
        }*/
        if(sellVolumeList != null && sellVolumeList.size()>0){
            int minLength = count > sellVolumeList.size() ? sellVolumeList.size() : count;
            String temp = "";
            int position = 0;
            for(int i = 0;i < minLength;i++){
                position = count-i-1;
                temp = sellVolumeList.get(i);
                //   if(!StringUtils.isEmpty(temp) && !(temp.equals("-") || temp.equals("--")||temp.equals("一"))){  // old
                if(FormatUtils.isStandard(temp)){  // new
                    sellVolumes[position] = FormatUtility.formatVolume(temp,quoteitem.market,quoteitem.subtype);
                }
            }
        }
        if(strLength == 7){
            textPaint.setTextSize(SizeUtils.dp2px(getContext(),10));
        } else {
            textPaint.setTextSize(SizeUtils.dp2px(getContext(),12));
        }
        invalidate();
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawLine(canvas);
        drawValues(canvas);
    }

    private void drawLine(Canvas canvas) {
        canvas.drawRect(0,(hight-lineHigth)/2,width,(hight+lineHigth)/2,linePaint);
    }

    private void drawValues(Canvas canvas) {
        drawFlags(canvas);
        drawBuyPrices(canvas);
        drawSellPrices(canvas);
        drawBuyVolumes(canvas);
        drawSellVolumes(canvas);
    }

    private void drawSellVolumes(Canvas canvas) {
        textPaint.setTextAlign(Paint.Align.RIGHT);
        textPaint.setColor(defalutColor);
        for(int i = 0;i < count;i++){
            canvas.drawText(sellVolumes[i],width-5,divhight*i+divhight*0.75f,textPaint);
        }
    }

    private void drawBuyVolumes(Canvas canvas) {
        textPaint.setTextAlign(Paint.Align.RIGHT);
        textPaint.setColor(defalutColor);
        for(int i = 0;i < count;i++){
            canvas.drawText(buyVolumes[i],width-5,divhight*(i+count)+divhight*0.9f,textPaint);
        }
    }

    private void drawBuyPrices(Canvas canvas) {
        textPaint.setTextAlign(Paint.Align.LEFT);
        for(int i = 0;i < count;i++){
            if(buyColors[i] != -1){
                textPaint.setColor(buyColors[i]);
            } else {
                textPaint.setColor(defalutColor);
            }
            canvas.drawText(buyPrices[i],helfwidth*2/3-5,divhight*(i+count)+divhight*0.9f,textPaint);
        }
    }

    private void drawSellPrices(Canvas canvas) {
        textPaint.setTextAlign(Paint.Align.LEFT);
        for(int i = 0;i < count;i++){
            if(sellColors[i] != -1){
                textPaint.setColor(sellColors[i]);
            } else {
                textPaint.setColor(defalutColor);
            }
            canvas.drawText(sellPrices[i],helfwidth*2/3-5,divhight*i+divhight*0.75f,textPaint);
        }
    }

    private void drawFlags(Canvas canvas) {
        int length = count*2;
        textPaint.setTextAlign(Paint.Align.LEFT);
        textPaint.setColor(defalutColor);
        for(int i = 0;i < length;i++){
            if(i < count){
                canvas.drawText("卖"+ (count-i),20,divhight*i+divhight*0.75f,textPaint);
            } else {
                canvas.drawText("买"+ (i +1- count),20,divhight*i+divhight*0.9f,textPaint);
            }
        }
    }

    public void setTextSize(int size) {
        if (textSize != size) {
            textSize = size;
            textPaint.setTextSize(textSize);
            invalidate();
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        int max = 1;
        this.hight = h*max;
        this.width = w*max;
        helfwidth = w/2;
        divhight = h/(count*2);
    }
}