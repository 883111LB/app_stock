package com.cvicse.stock.chart.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.os.Environment;
import android.view.View;
import android.view.View.MeasureSpec;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class Utility {
    private Utility() {
    }

    public static String getBeiJingTime() {
        SimpleDateFormat dff = new SimpleDateFormat("yyyyMMdd HHmmss");
        dff.setTimeZone(TimeZone.getTimeZone("GMT+08"));
        String ee = dff.format(new Date());
        return ee;
    }

    public static String getBeiJingTimeWithoutSecond() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT+08"));
        String ee = sdf.format(new Date());
        return ee;
    }

    public static String getBeiJingTimeWithHHmm() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT+08"));
        String ee = sdf.format(new Date());
        return ee;
    }

    public static String getBeiJingTimeWithyyyyMMdd() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT+08"));
        String ee = sdf.format(new Date());
        return ee;
    }

    public static String calculateDateEndWithHHmm(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("HHmm");
        String str = sdf.format(date);
        return str;
    }

    public static Date calculateStringEndWithHHmm(String str) {
        Date finalDate = null;
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");

        try {
            finalDate = format.parse(str);
        } catch (ParseException var4) {
            var4.printStackTrace();
        }

        return finalDate;
    }

    public static String calculateStringEndWithYYYYMMDD(String str) {
        try {
            return str.substring(0, 4) + "-" + str.substring(4, 6) + "-" + str.substring(6, 8);
        } catch (Exception var2) {
            return null;
        }
    }
}
