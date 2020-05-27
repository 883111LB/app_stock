package com.cvicse.stock.utils;

/**
 * Created by Administrator on 2015/11/3.
 */

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import com.mitake.core.MarketingItem;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * @version 20150411[李欣駿]   1.加入記錄上稿系統回傳資料的對應表。
 *          2.加入記錄畫面定時刷新秒數,-1表示不更新。
 *          Created by ruaddick on 2014/5/9.
 */
public class CommonInfo {
    /**
     * 技術線圖用,未來要改掉
     */
    public static Bundle info = new Bundle();
    /**
     * 記錄字體大小
     */
    public static final double SMALL = 1;
    public static final double BIG = 1.5;
    public static double textType = SMALL;
    /**
     * 記錄上稿系統回傳資料的對應表
     */
    public static HashMap<String, ArrayList<MarketingItem>> promotionList;

    public static int refreshSecond = -1;

    public static int mobileRefreshSecond = -1;
    public static int wifiRefreshSecond = -1;
    /**
     * 報價-產品代碼
     */
    public static String prodID;
    /**
     * 報價-使用者識別碼
     */
    public static String uniqueID;
    public static boolean hasUnique;

    public static String productName;
    /**
     * 紀錄目前模式<li>1: 模式1<li>2: 模式2<li>3: 模式3
     */
    public static int showMode;
    /**
     * 紀錄多模式模式<li>1: 模式1<li>2: 模式2<li>3: 模式3
     */
    public static int[] showMultiMode;
    /**
     * 由最外層string.xml設定
     * <li>0:走系統設定檔；
     * <li>1:系統設定不開起模式切換，強制為模式1；
     * <li>2:系統設定不開起模式切換，強制為模式2；
     * <li>3:系統設定不開起模式切換，強制為模式3；
     * <li>99:系統設定開啟所有模式切換
     * <li>若要開啟多組模式切換可用【,】隔開，EX:1,3表示系統設定可切換模式1與模式3
     */
    public static String developShowMode;
    /**
     * 音檔對應表
     */
    public static HashMap<Character, String> soundMap;
    /**
     * 紀錄目前主題/皮膚模式<li>預設:0<li>0:黑色 <li>1:白色
     */
    public static int skinMode = 0;

    public static int debugMode = 0;

    public static boolean isNewOneMode = true;

    /**
     * 設定目前主題/皮膚模式
     *
     * @param val
     */
    public static void setSkinMode(int val) {
        skinMode = val;
    }

    /**
     * 取得目前主題/皮膚模式
     *
     * @return
     */
    public static int getSkinMode() {
        return skinMode;
    }

    public static Bitmap getBitmap(String path) throws IOException {
        URL url = new URL(path);
        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
        conn.setConnectTimeout(5000);
        conn.setRequestMethod("GET");
        if(conn.getResponseCode() == 200){
            InputStream inputStream = conn.getInputStream();
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            return bitmap;
        }
        return null;

    }

}
