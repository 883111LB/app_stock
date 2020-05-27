package com.cvicse.stock.util;

import android.graphics.Color;

import com.mitake.core.util.FormatUtility;
import com.stock.config.FillConfig;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by liu_zlu on 2017/1/13 19:58
 */
public class FormatUtils {
    /**
     *
     *  四舍五入。格式化为单位字符串
     * @param val 具体值
     * @param num 保留小数点位数，默认保留一位小数
     * @return
     */
    public static String format(String val,int ...num){
        if( !isStandard(val)){
            return FillConfig.DEFALUT;
        }
        // 判断是否为单位字符串
        if( isContainChinese(val) ){
            return val;
        }
        val = val.replace(",","");   // 数据为“11,074,358,290”
        // 当后台返回的val为"2.4095364E7"
        float temp = formatByScientificNotation(val);
        String symbol = getSymbol(val);
        if(FillConfig.SUBTRACT.equals(symbol)){   //负数
            temp = Math.abs(Float.parseFloat(val));
        }

        if(temp < 9999){
            return symbol+temp;
        }

        StringBuffer stringBuffer = new StringBuffer("###0.00");
        if(num != null && num.length > 0 && num[0] > 1){
            for(int i=0;i< num[0];i++){
                stringBuffer.append("0");
            }
        }
        NumberFormat numberFormat = new DecimalFormat(stringBuffer.toString());
        numberFormat.setRoundingMode(RoundingMode.HALF_UP);

        val = temp+"";
//        float valum = Float.parseFloat(val)/10000.0f;
        float valum = temp/10000.0f;
        String unit=""; // 单位

        if(valum > 10000){
            valum = valum / 10000.0f;
            if(valum > 1000){
                valum = valum/ 1000.0f;
                if( valum > 1000 ){
                    if( valum / 10000.0f > 100 ){
                        val = valum / 10000.0f + "";
                        unit = "百万亿";
                    }else{
                        val = valum / 100.0f + "";
                        unit = "万亿";
                    }
                }else{
                    val = valum + "";
                    unit = "千亿";
                }
            } else {
                val = (valum + "");
                unit = "亿";
            }
            if(numberFormat != null){
                val = numberFormat.format(Double.parseDouble(val));
            }
        } else {
            if(valum/1000f > 1){
                val = valum/1000f + "";
                unit = "千万";
            } else {
                val = valum + "";
                unit = "万";
            }
        }
        if( null!= numberFormat){
            val = numberFormat.format(Double.parseDouble(val));
        }
        return symbol+val+unit;
    }

    public static String getVol(String val){
        return getVol(val,0);
    }

    public static String getVol(String val,int num){
        if( !isStandard(val)){
            return FillConfig.DEFALUT;
        }

        float temp = Float.parseFloat(val)/100;
        val = temp+"";
        return format(val,num);
    }
    public static String formatPrice(float num,String market,String subtype){
        return FormatUtility.formatPrice((int)num + "", market, subtype);
    }

    public static String formatPrice(String num,String market,String subtype){
        return FormatUtility.formatPrice(num, market, subtype);
    }

    public static String formatHundred(String param){
        if(  !isStandard(param)){
            return FillConfig.DEFALUT;
        }

        param = Float.parseFloat(param)/100 + "";
        return getVol(param);
    }

    /**
     * 转换为百分号格式
     * @param param
     * @return
     */
    public static String formatPercent (String param){
        if( !isStandard(param)){
            return FillConfig.DEFALUT;
        }
        return param+FillConfig.PRECENT;
    }

    public static String formatVolume(String param,String market,String subtype){
        if(!isStandard(param)){
            return FillConfig.DEFALUT;
        }
        if(subtype.equals("1300")&& market.equals("sz")){
            float num = Float.parseFloat(param)/10f;
            return format(num+"");
        }
        return format(param);
    }

    public static int getLastPriceColor(String upDownFlag,int defalutColor){
        int color = defalutColor;
        if("+".equals(upDownFlag) || "-".equals(upDownFlag)){
            if("+".equals(upDownFlag)){
                color = Color.parseColor("#ffdd0000");
            }else{
                color =Color.parseColor("#2d7c2d");;
            }
        }
        return color;
    }

    /**
     * 判断字符是否规范
     * @return false-不规范  true--规范
     */
    public static boolean isStandard(String param ){
        if( null==param  || FillConfig.SIGNLE_LINE.equals(param)|| FillConfig.NULL.equals(param) || FillConfig.EMPTY.equals(param)
                || FillConfig.DEFALUT.equals(param)|| FillConfig.DOUBLE_LINE.equals(param) || FillConfig.DEFALUT2.equals(param)
                || FillConfig.INFINITE.equals(param)){
            return false;
        }
        return true;
    }

    /**
     * 将时间字符串解析为Date
     * @param string   20180122
     * @param pattern  格式
     * @return
     */
    public static Date parseDate(String string, String pattern ){
        Date date = null;
        if( null == string || null == pattern ){
            return date;
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        try {
            date = simpleDateFormat.parse(string);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static String formatDate(String date,String pattern){
        if( !isStandard(date) ){
            return FillConfig.DEFALUT;
        }
        return formatDate(new Date(Long.valueOf(date)),pattern);
    }

    /**
     * 将Date格式化为字符串
     * @param date
     * @param pattern
     * @return
     */
    public static String formatDate(Date date , String pattern){
        String format = FillConfig.DEFALUT;
        if( null == date || null == pattern ){
            return format;
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        format = simpleDateFormat.format(date);
        return format;
    }

    /**
     * 是否包含中文
     * @param str
     * @return
     */
    public static boolean isContainChinese(String str) {
        Pattern p = Pattern.compile("[\\u4e00-\\u9fa5]");
        Matcher m = p.matcher(str);
        if (m.find()) {
            return true;
        }
        return false;
    }

    /**
     * 将科学计数法字符串转换为数值
     * @param str
     * @return
     */
    public static float formatByScientificNotation(String str){
        if(str.contains("E")){
            String[] index = str.split("E");
            return (long)(Math.pow(10,Double.parseDouble(index[1])) * Float.parseFloat(index[0]));
        }
        return Float.parseFloat(str);
    }

    /**
     * 得到数值字符串符号
     * @param str
     * @return  +、-
     */
    public static String getSymbol(String str){
        if(!isStandard(str)){
            return FillConfig.EMPTY;
        }
        if(str.contains("-") && str.startsWith("-")){
            return FillConfig.SUBTRACT;
        }else if(str.contains("+") && str.startsWith("+")){
          return FillConfig.PLUS;
        }
        return FillConfig.EMPTY;
    }

    /**
     * 动态设置文字大小
     * @param str
     * @return
     */
    public static float setDynamicSize(String str){
        if( !isStandard(str) ){
            return 30;
        }
        if(str.length()==8){
            return 25;
        }else if(str.length()>=9){
            return 22;
        }else{
            return 30;
        }
    }

    /**
     * 根据单位进行转换
     * @param unit 单位
     * @param value 数值
     * @return
     */
    public static double formatDataByUnit(String unit, double value){
        if( !FormatUtils.isStandard(unit) || "元".equals(unit) ){
            return value;
        }
        if( "万亿".equals(unit)){
            return (value / Math.pow(10,12));
        }else  if( "千亿".equals(unit)){
            return (value / Math.pow(10,11));
        }else  if( "百亿".equals(unit)){
            return (value / Math.pow(10,10));
        }else  if( "十亿".equals(unit)){
            return (value / Math.pow(10,9));
        }else  if( "亿".equals(unit)){
            return (value / Math.pow(10,8));
        }else  if( "千万".equals(unit)){
            return (value / Math.pow(10,7));
        }else  if( "百万".equals(unit)){
            return (value / Math.pow(10,6));
        }else   if( "十万".equals(unit)){
            return (value / Math.pow(10,5));
        }else  if( "万".equals(unit)){
            return (value / Math.pow(10,4));
        }else  if( "千".equals(unit)){
            return (value / Math.pow(10,3));
        }else  if( "百".equals(unit)){
            return (value / Math.pow(10,2));
        }else  if( "十".equals(unit)){
            return (value / Math.pow(10,1));
        }
        return  value;
    }

    /**
     * 将带单位的文本转换为数值
     * @return
     */
    public static double formatDateByChinest(String valueStr){
        if(!FormatUtils.isStandard(valueStr)){
            return 0;
        }
        if( valueStr.contains("万亿") ){
            return getValue(valueStr,2,12);
        }else if( valueStr.contains("千亿") ){
            return getValue(valueStr,2,11);
        }else if( valueStr.contains("百亿") ){
            return getValue(valueStr,2,10);
        }else if( valueStr.contains("十亿") ){
            return getValue(valueStr,2,9);
        }else if( valueStr.contains("亿") ){
            return getValue(valueStr,1,8);
        }else if( valueStr.contains("千万") ){
            return getValue(valueStr,2,7);
        }else if( valueStr.contains("百万") ){
            return getValue(valueStr,2,6);
        }else if( valueStr.contains("十万") ){
            return getValue(valueStr,1,5);
        }else if( valueStr.contains("万") ){
            return getValue(valueStr,1,4);
        }else if(valueStr.contains("千") ){
            return getValue(valueStr,1,3);
        }else if(valueStr.contains("百") ){
            return getValue(valueStr,1,2);
        }else if(valueStr.contains("十") ){
            return getValue(valueStr,1,1);
        }else if(valueStr.contains("元") ){
            return getValue(valueStr,1,0);
        }
        try {
            return new DecimalFormat("###.####").parse(valueStr).doubleValue();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static String getUnit(String unitStr){
        String unit = "";
        if(unitStr.toString().contains("万亿")){
            unit = "万亿";
        }else if(unitStr.toString().contains("千亿")){
            unit = "千亿";
        }else if(unitStr.toString().contains("亿")){
            unit = "亿";
        }else if(unitStr.toString().contains("万")){
            unit = "万";
        }else if(unitStr.toString().contains("元")) {
            unit = "元";
        }
        return unit;
    }

    public static double getValue(String text, int subNum, int powNum){
        if( !FormatUtils.isStandard(text)){
            return 0;
        }
        text = text.substring(0,text.length() - subNum);
        double value = 0;
        try {
            value = new DecimalFormat("###,###,###,###,###,###.####").parse(text).doubleValue();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return (value*Math.pow(10,powNum));
    }
}
